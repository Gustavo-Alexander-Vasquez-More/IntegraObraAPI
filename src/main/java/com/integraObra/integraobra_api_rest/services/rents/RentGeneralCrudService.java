package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.CreateRentRequestDTO;
import com.integraObra.integraobra_api_rest.dto.rents.RentResponseDTO;

public interface RentGeneralCrudService {

    //Crear una renta para el cliente
    RentResponseDTO createRentForClient(CreateRentRequestDTO createRentRequestDTO);

}
