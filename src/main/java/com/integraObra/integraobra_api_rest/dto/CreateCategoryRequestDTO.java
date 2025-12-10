package com.integraObra.integraobra_api_rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateCategoryRequestDTO {
    @NotEmpty(message = "La categoría no puede estar vacía")
    private String name;
}
