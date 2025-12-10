package com.integraObra.integraobra_api_rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateCategoryDetailRequestDTO {
    @NotNull(message = "El category_id no puede ser nulo")
    private Long categoryId;
    @NotNull(message = "El product_id no puede ser nulo")
    private Long productId;
}
