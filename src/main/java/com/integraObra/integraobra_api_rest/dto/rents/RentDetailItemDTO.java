package com.integraObra.integraobra_api_rest.dto.rents;

import com.integraObra.integraobra_api_rest.utils.RentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentDetailItemDTO {
    @NotNull(message = "El ID del producto no puede ser nulo.")
    private Long productId;
    @NotNull(message = "La cantidad no puede ser nula.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private Integer quantity;
    @NotNull(message = "Los días de renta no pueden ser nulos.")
    @Min(value = 1, message = "Los días de renta deben ser al menos 1.")
    private Integer daysRented;
}
