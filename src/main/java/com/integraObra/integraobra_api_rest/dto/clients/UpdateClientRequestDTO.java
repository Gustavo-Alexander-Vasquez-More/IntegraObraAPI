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
    @NotEmpty(message = "El nombre no puede estar vacío.")
    private String name;
    private String email;
    @NotEmpty(message = "El número de teléfono no puede estar vacío.")
    private String phone;
    private ReputationClient reputation;
    private String frontPhotoIne;
    private String backPhotoIne;
}
