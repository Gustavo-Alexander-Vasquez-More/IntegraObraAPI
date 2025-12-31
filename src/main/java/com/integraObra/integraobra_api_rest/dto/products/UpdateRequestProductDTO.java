package com.integraObra.integraobra_api_rest.dto.products;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateRequestProductDTO {
    // Para PATCH los campos deben ser opcionales. No usamos @NotEmpty/@NotNull aquí.
    private String name;
    private String cardImageUrl;
    private String sku;

    // Stock es opcional en PATCH; si viene, debe ser >= 0
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private String description;
    private List<String> tags; // Puede estar vacío u omitirse (opcional)

    // Si los precios son null se interpretan como no aplicables; DecimalMin se ignora si null
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que cero")
    private BigDecimal salePrice; // Puede ser nulo si no está a la venta

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de renta debe ser mayor que cero")
    private BigDecimal rentPrice;

    private Boolean isForRent;
    private Boolean isForSale;
    private Boolean priceVisibleForRent;
    private Boolean priceVisibleForSale;
}
