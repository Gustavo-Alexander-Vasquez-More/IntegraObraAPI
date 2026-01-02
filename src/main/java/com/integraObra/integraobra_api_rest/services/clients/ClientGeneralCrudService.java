package com.integraObra.integraobra_api_rest.services.clients;

import com.integraObra.integraobra_api_rest.dto.clients.ClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.CreateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientResponseDTO;
import com.integraObra.integraobra_api_rest.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientGeneralCrudService {

    //CREAR CLIENTE
    Client createClient(CreateClientRequestDTO createClientRequestDTO);

    //ELIMINAR CLIENTE POR ID
    boolean deleteClientById(Long id);

    //ACTUALIZAR CLIENTE POR ID
    UpdateClientResponseDTO updateClientById(Long id, UpdateClientRequestDTO updateClientRequestDTO);

    //OBTENER UN CLIENTE POR ID
    Client getClientById(Long id);

}
