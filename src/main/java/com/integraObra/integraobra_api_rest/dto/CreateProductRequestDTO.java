package com.integraObra.integraobra_api_rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateProductRequestDTO {
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;
    @NotEmpty(message = "La URL de la imageCard no puede estar vacía")
    private String cardImageUrl;
    @NotEmpty(message = "El SKU no puede estar vacío")
    private String sku;
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    @NotEmpty(message = "La descripción no puede estar vacía")
    private String description;
    private List<String> tags; // Puede estar vacío, es opcional
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que cero")
    private BigDecimal salePrice; // Puede ser nulo si no está a la venta
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de renta debe ser mayor que cero")
    private BigDecimal rentPrice;
    @NotNull(message = "isForSale no puede ser nulo")
    private Boolean isForSale;
    @NotNull(message = "priceVisibleForRent no puede ser nulo")
    private Boolean priceVisibleForRent;
    @NotNull(message = "priceVisibleForSale no puede ser nulo")
    private Boolean priceVisibleForSale;

}
