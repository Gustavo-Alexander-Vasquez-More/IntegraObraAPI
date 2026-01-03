package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.CreateRentRequestDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Client;
import com.integraObra.integraobra_api_rest.models.Rent;
import com.integraObra.integraobra_api_rest.repositories.ClientRepository;
import com.integraObra.integraobra_api_rest.repositories.RentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Rent createRentForClient(CreateRentRequestDTO createRentRequestDTO) {
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
        rentDetailGeneralCrudServiceJPA.createRentDetail(createRentRequestDTO.getRentDetails(), savedRent.getId());
        return savedRent;
    }
}
