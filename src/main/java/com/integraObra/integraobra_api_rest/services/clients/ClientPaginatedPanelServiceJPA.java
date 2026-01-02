package com.integraObra.integraobra_api_rest.services.clients;

import com.integraObra.integraobra_api_rest.dto.clients.ClientRequestDTO;
import com.integraObra.integraobra_api_rest.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientPaginatedPanelServiceJPA implements  ClientPaginatedPanelService{

  private final ClientRepository clientRepository;
  public ClientPaginatedPanelServiceJPA(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
  }

  //OBTENER LOS CLIENTES PAGINADOS POR TERMINO DE BUSQUEDA(nombre, telefono o mail) SINO TODOS
    @Override
    public Page<ClientRequestDTO> getClientsPaginatedWithFilterInPanel(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return clientRepository.findAll(pageable).map(ClientRequestDTO::fromEntity);
        } else {
            String likeSearchTerm = "%" + searchTerm.toLowerCase() + "%";
            return clientRepository.findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrPhoneIgnoreCaseContaining(
                    likeSearchTerm, likeSearchTerm, likeSearchTerm, pageable
            ).map(ClientRequestDTO::fromEntity);
        }
    }

}
