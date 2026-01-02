package com.integraObra.integraobra_api_rest.services.clients;

import com.integraObra.integraobra_api_rest.dto.clients.ClientRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientPaginatedPanelService {
    //OBTENER LOS CLIENTES PAGINADOS POR TERMINO DE BUSQUEDA(NOMBRE, TELEFONO O MAIL) SINO TODOS
    Page<ClientRequestDTO> getClientsPaginatedWithFilterInPanel(String searchTerm, Pageable pageable);
}
