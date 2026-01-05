package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.CreateRentRequestDTO;
import com.integraObra.integraobra_api_rest.dto.rents.RentDetailResponseDTO;
import com.integraObra.integraobra_api_rest.dto.rents.RentResponseDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Client;
import com.integraObra.integraobra_api_rest.models.Rent;
import com.integraObra.integraobra_api_rest.repositories.ClientRepository;
import com.integraObra.integraobra_api_rest.repositories.RentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RentGeneralCrudServiceJPA implements RentGeneralCrudService{
    private final RentRepository rentRepository;
    private final ClientRepository clientRepository;
    private final RentDetailGeneralCrudServiceJPA rentDetailGeneralCrudServiceJPA;
    public RentGeneralCrudServiceJPA(RentRepository rentRepository, ClientRepository clientRepository, RentDetailGeneralCrudServiceJPA rentDetailGeneralCrudServiceJPA) {
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.rentDetailGeneralCrudServiceJPA = rentDetailGeneralCrudServiceJPA;
    }


    //Crear una renta para el cliente
    @Transactional // Si falla algo, se hace rollback automático de TODO
    public RentResponseDTO createRentForClient(CreateRentRequestDTO createRentRequestDTO) {
        // 1. Buscar el cliente (si no existe, lanza NotFoundException)
        Client cliente = clientRepository.findById(createRentRequestDTO.getClientId())
                .orElseThrow(() -> new NotFoundException("El ID proporcionado indica que el cliente no existe."));
        //Ver si el cliente tiene sus INE trasero y delantero, sino no tiene o es null lanza excepción
        if (cliente.getBackPhotoIne() == null || cliente.getFrontPhotoIne() == null) {
            throw new NotFoundException("El cliente no tiene sus fotos de INE registradas, para realizar la renta es necesario que las tenga.");
        }
        // 2. Crear la renta
        Rent rent = new Rent();
        rent.setClient(cliente);
        rent.setStatus(createRentRequestDTO.getStatus());
        //guardamos la renta primero para obtener su ID
        Rent savedRent = rentRepository.save(rent);
        //creamos el detalle de la renta
        List<RentDetailResponseDTO> detalleRenta= rentDetailGeneralCrudServiceJPA.createRentDetail(createRentRequestDTO.getRentDetails(), savedRent.getId());

        //armamos la respuesta teniendo en cuenta
        RentResponseDTO rentResponseDTO = new RentResponseDTO();
        rentResponseDTO.setId(savedRent.getId());
        rentResponseDTO.setClient(savedRent.getClient());
        //lISTA DE DETALLES DE RENTA (private List<RentDetailResponseDTO> rentDetails;)
        rentResponseDTO.setRentDetails(detalleRenta.stream().map(rentDetail -> {
            RentDetailResponseDTO rentDetailResponseDTO = new RentDetailResponseDTO();
            rentDetailResponseDTO.setId(rentDetail.getId());
            rentDetailResponseDTO.setProductRentItem(rentDetail.getProductRentItem());
            rentDetailResponseDTO.setDiscountRate(rentDetail.getDiscountRate());
            rentDetailResponseDTO.setTotalPriceWithDiscount(rentDetail.getTotalPriceWithDiscount());
            return rentDetailResponseDTO;
        }).toList());
        //calcular el precio total de la renta sumando los totales de cada detalle
        BigDecimal totalPrice = detalleRenta.stream()
                .map(RentDetailResponseDTO::getTotalPriceWithDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        rentResponseDTO.setTotalPrice(totalPrice);
        rentResponseDTO.setStatus(savedRent.getStatus());
        rentResponseDTO.setCreatedAt(savedRent.getCreationDate());

        return rentResponseDTO;
    }
}
