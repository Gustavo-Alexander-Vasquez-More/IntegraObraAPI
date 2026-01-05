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
    private final SequenceNumRentService sequenceNumRentService;
    public RentGeneralCrudServiceJPA(RentRepository rentRepository, ClientRepository clientRepository, RentDetailGeneralCrudServiceJPA rentDetailGeneralCrudServiceJPA, SequenceNumRentService sequenceNumRentService) {
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.rentDetailGeneralCrudServiceJPA = rentDetailGeneralCrudServiceJPA;
        this.sequenceNumRentService = sequenceNumRentService;
    }

    public Rent createRent(Client cliente, CreateRentRequestDTO createRentRequestDTO) {
        Rent rent = new Rent();
        rent.setClient(cliente);
        rent.setStatus(createRentRequestDTO.getStatus());
        // Generar y asignar el número secuencial de renta (se hace dentro de la misma transacción con lock)
        Long nextNumber = sequenceNumRentService.generateNextNumber();
        rent.setNumberRent(nextNumber);
        //guardamos la renta primero para obtener su ID
        return rentRepository.save(rent);
    }

    public  RentResponseDTO createRentResponseDTO(Rent rent, List<RentDetailResponseDTO> rentDetails) {
        //armamos la respuesta con todos los datos
        RentResponseDTO rentResponseDTO = new RentResponseDTO();
        rentResponseDTO.setId(rent.getId());
        rentResponseDTO.setNumberRent(rent.getNumberRent());
        rentResponseDTO.setClient(rent.getClient());
        // Lista de detalles ya convertidos por el servicio de detalles, pero la copiamos para asegurar la forma esperada
        rentResponseDTO.setRentDetails(rentDetails.stream().map(rentDetail -> {
            RentDetailResponseDTO rentDetailResponseDTO = new RentDetailResponseDTO();
            rentDetailResponseDTO.setId(rentDetail.getId());
            rentDetailResponseDTO.setProductRentItem(rentDetail.getProductRentItem());
            rentDetailResponseDTO.setDiscountRate(rentDetail.getDiscountRate());
            rentDetailResponseDTO.setTotalPriceWithDiscount(rentDetail.getTotalPriceWithDiscount());
            // Asegurar que también incluimos daysRented y quantity en la respuesta
            rentDetailResponseDTO.setDaysRented(rentDetail.getDaysRented());
            rentDetailResponseDTO.setQuantity(rentDetail.getQuantity());
            return rentDetailResponseDTO;
        }).toList());
        //calcular el precio total de la renta sumando los totales de cada detalle
        BigDecimal totalPrice = rentDetails.stream()
                .map(RentDetailResponseDTO::getTotalPriceWithDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        rentResponseDTO.setTotalPrice(totalPrice);
        rentResponseDTO.setStatus(rent.getStatus());
        rentResponseDTO.setCreatedAt(rent.getCreationDate());

        return rentResponseDTO;
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
        Rent savedRent = createRent(cliente, createRentRequestDTO);
        //creamos el detalle de la renta
        List<RentDetailResponseDTO> detalleRenta= rentDetailGeneralCrudServiceJPA.createRentDetail(createRentRequestDTO.getRentDetails(), savedRent.getId());
        //Creamos la respuesta completa
        return createRentResponseDTO(savedRent, detalleRenta);
    }
}
