package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.RentDetailResponseDTO;
import com.integraObra.integraobra_api_rest.dto.rents.RentResponseDTO;
import com.integraObra.integraobra_api_rest.models.Rent;
import com.integraObra.integraobra_api_rest.repositories.RentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentPaginatedPanelServiceJPA {
    public final RentRepository rentRepository;
    public final RentGeneralCrudServiceJPA rentGeneralCrudServiceJPA;
    public final RentDetailGeneralCrudServiceJPA rentDetailGeneralCrudServiceJPA;

    public RentPaginatedPanelServiceJPA(RentRepository rentRepository, RentDetailGeneralCrudServiceJPA rentDetailGeneralCrudServiceJPA, RentGeneralCrudServiceJPA rentGeneralCrudServiceJPA) {
        this.rentRepository = rentRepository;
        this.rentDetailGeneralCrudServiceJPA = rentDetailGeneralCrudServiceJPA;
        this.rentGeneralCrudServiceJPA = rentGeneralCrudServiceJPA;
    }

    //SERVICIO PARA OBTENER TODAS LAS RENTAS PAGINADAS
    public Page<RentResponseDTO> getAllRentsPaginated(Pageable pageable) {
        Page<Rent> rentPage = rentRepository.findAll(pageable);
        return rentPage.map(rent -> {
            //Obtenemos los detalles de la renta para armar la respuesta completa
            List<RentDetailResponseDTO> rentDetails = rentDetailGeneralCrudServiceJPA.getRentDetailsByRentId(rent.getId());
            return rentGeneralCrudServiceJPA.createRentResponseDTO(rent, rentDetails);
        });
    }

    //OBTENER RENTAS POR TERMINO DE BUSQUEDA(numero de renta por prefijo) PAGINADOS
    public Page<RentResponseDTO> getRentsPaginatedWithFilterInPanel(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllRentsPaginated(pageable);
        } else {
            String term = searchTerm.trim();
            Page<Rent> rentPage = rentRepository.findByNumberRentStartingWith(term, pageable);
            return rentPage.map(rent -> {
                //Obtenemos los detalles de la renta para armar la respuesta completa
                List<RentDetailResponseDTO> rentDetails = rentDetailGeneralCrudServiceJPA.getRentDetailsByRentId(rent.getId());
                return rentGeneralCrudServiceJPA.createRentResponseDTO(rent, rentDetails);
            });
        }
    }
}
