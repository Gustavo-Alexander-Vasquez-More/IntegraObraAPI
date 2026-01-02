package com.integraObra.integraobra_api_rest.services.clients;

import com.integraObra.integraobra_api_rest.dto.clients.ClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.CreateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientRequestDTO;
import com.integraObra.integraobra_api_rest.dto.clients.UpdateClientResponseDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Client;
import com.integraObra.integraobra_api_rest.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceJPA implements  ClientService{

    private final ClientRepository clientRepository;

    public ClientServiceJPA(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //Crear Cliente
    public Client createClient(CreateClientRequestDTO createClientRequestDTO) {
        Client client = new Client();
        client.setName(createClientRequestDTO.getName());
        client.setEmail(createClientRequestDTO.getEmail());
        client.setPhone(createClientRequestDTO.getPhone());
        client.setReputation(createClientRequestDTO.getReputation());
        client.setFrontPhotoIne(createClientRequestDTO.getFrontPhotoIne());
        client.setBackPhotoIne(createClientRequestDTO.getBackPhotoIne());
        return clientRepository.save(client);
    }

    //ELiminar cliente por id
    public boolean deleteClientById(Long id) {
        Client client=clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ID proporcionado indica que el cliente no existe."));
        clientRepository.delete(client);
        return true;
    }

    //actualizar cliente por id segun campos que le pasemos (patch)
    public UpdateClientResponseDTO updateClientById(Long id, UpdateClientRequestDTO updateClientRequestDTO) {
        Client client=clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ID proporcionado indica que el cliente no existe."));
        if(updateClientRequestDTO.getName()!=null){
            client.setName(updateClientRequestDTO.getName());
        }
        if(updateClientRequestDTO.getEmail()!=null){
            client.setEmail(updateClientRequestDTO.getEmail());
        }
        if(updateClientRequestDTO.getPhone()!=null){
            client.setPhone(updateClientRequestDTO.getPhone());
        }
        if(updateClientRequestDTO.getReputation()!=null){
            client.setReputation(updateClientRequestDTO.getReputation());
        }
        if(updateClientRequestDTO.getFrontPhotoIne()!=null){
            client.setFrontPhotoIne(updateClientRequestDTO.getFrontPhotoIne());
        }
        if(updateClientRequestDTO.getBackPhotoIne()!=null){
            client.setBackPhotoIne(updateClientRequestDTO.getBackPhotoIne());
        }
        Client updatedClient=clientRepository.save(client);
        return new UpdateClientResponseDTO(
                updatedClient.getName(),
                updatedClient.getEmail(),
                updatedClient.getPhone(),
                updatedClient.getReputation(),
                updatedClient.getFrontPhotoIne(),
                updatedClient.getBackPhotoIne()
        );
    }

    //Obtener cliente por ID
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ID proporcionado indica que el cliente no existe."));
    }

    //Obtener todos los clientes paginados
    public Page<ClientRequestDTO> getAllClientsPaged(Pageable pageable) {
        return clientRepository.findAll(pageable).map(ClientRequestDTO::fromEntity);
    }

}
