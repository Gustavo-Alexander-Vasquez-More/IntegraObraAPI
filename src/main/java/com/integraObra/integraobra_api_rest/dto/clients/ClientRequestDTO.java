package com.integraObra.integraobra_api_rest.dto.clients;

import com.integraObra.integraobra_api_rest.utils.ReputationClient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientRequestDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private ReputationClient reputation;
    private String frontPhotoIne;
    private String backPhotoIne;
    private LocalDateTime creationDate;

    //from entity to DTO
    public static ClientRequestDTO fromEntity(com.integraObra.integraobra_api_rest.models.Client client) {
        ClientRequestDTO dto = new ClientRequestDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        dto.setReputation(client.getReputation());
        dto.setFrontPhotoIne(client.getFrontPhotoIne());
        dto.setBackPhotoIne(client.getBackPhotoIne());
        dto.setCreationDate(client.getCreationDate());
        return dto;
    }
}
