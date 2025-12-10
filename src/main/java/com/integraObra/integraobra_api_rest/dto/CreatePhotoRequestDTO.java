package com.integraObra.integraobra_api_rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreatePhotoRequestDTO {
    @NotEmpty(message = "La URL de la imagen no puede estar vacía")
    private String imageUrl;
    @NotNull(message = "isPrincipal no puede ser nulo")
    private Boolean isPrincipal;
    @NotNull(message = "El productId no puede ser nulo")
    private Long productId;
}
