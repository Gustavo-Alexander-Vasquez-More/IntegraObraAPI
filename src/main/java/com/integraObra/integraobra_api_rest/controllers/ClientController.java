package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.clients.ClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.CreateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientResponseDTO;
import com.integraObra.integraobra_api_rest.models.Client;
import com.integraObra.integraobra_api_rest.services.clients.ClientGeneralCrudServiceJPA;
import com.integraObra.integraobra_api_rest.services.clients.ClientPaginatedPanelServiceJPA;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientGeneralCrudServiceJPA clientServiceJPA;
    private final ClientPaginatedPanelServiceJPA clientPaginatedPanelServiceJPA;

    public ClientController(ClientGeneralCrudServiceJPA clientServiceJPA, ClientPaginatedPanelServiceJPA clientPaginatedPanelServiceJPA) {
        this.clientPaginatedPanelServiceJPA = clientPaginatedPanelServiceJPA;
        this.clientServiceJPA = clientServiceJPA;
    }

    //CREAR CLIENTE
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody  CreateClientRequestDTO createClientRequestDTO) {
        return  ResponseEntity.status(201).body(clientServiceJPA.createClient(createClientRequestDTO));
    }

    //ELIMINAR CLIENTE POR ID
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Boolean> deleteClientById(@PathVariable  Long clientId){
        return ResponseEntity.status(200).body(clientServiceJPA.deleteClientById(clientId));
    }

    //ACTUALIZAR CLIENTE POR ID
    @PatchMapping("/{clientId}")
    public ResponseEntity<UpdateClientResponseDTO> updateClient(@Valid @RequestBody UpdateClientRequestDTO updateClientRequestDTO, @PathVariable  Long clientId){
        return ResponseEntity.status(200).body(clientServiceJPA.updateClientById(clientId, updateClientRequestDTO));
    }

    //OBTENER CLIENTE POR ID
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId){
        return ResponseEntity.status(200).body(clientServiceJPA.getClientById(clientId));
    }

    //OBTENER LOS CLIENTES PAGINADOS POR TERMINO DE BUSQUEDA(nombre, telefono o mail) SINO TODOS
    @GetMapping
    public ResponseEntity<Page<ClientRequestDTO>> getClientsPaginatedWithFilterInPanel(
            @RequestParam(required = false) String searchTerm,
            Pageable pageable
    ) {
        Page<ClientRequestDTO> clientsPage = clientPaginatedPanelServiceJPA.getClientsPaginatedWithFilterInPanel(searchTerm, pageable);
        return ResponseEntity.ok(clientsPage);
    }
}
