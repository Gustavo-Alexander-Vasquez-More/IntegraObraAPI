package com.integraObra.integraobra_api_rest.dto.clients;

import com.integraObra.integraobra_api_rest.utils.ReputationClient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateClientRequestDTO {
    private String name;
    private String email;
    private String phone;
    private ReputationClient reputation;
    private String frontPhotoIne;
    private String backPhotoIne;
}
