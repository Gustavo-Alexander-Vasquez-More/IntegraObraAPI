package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.clients.ClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.CreateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientResponseDTO;
import com.integraObra.integraobra_api_rest.models.Client;
import com.integraObra.integraobra_api_rest.services.clients.ClientServiceJPA;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientServiceJPA clientServiceJPA;

    public ClientController(ClientServiceJPA clientServiceJPA) {
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

    //OBTENER TODOS LOS CLIENTES PAGINADOS
    @GetMapping("/all-clients")
    public ResponseEntity<Page<ClientRequestDTO>> getAllClientsPaged(Pageable pageable){
        return ResponseEntity.status(200).body(clientServiceJPA.getAllClientsPaged(pageable));
    }
}
