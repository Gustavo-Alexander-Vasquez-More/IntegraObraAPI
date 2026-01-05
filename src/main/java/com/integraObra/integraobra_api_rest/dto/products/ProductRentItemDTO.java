package com.integraObra.integraobra_api_rest.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
//DTO para devolver la información básica de un producto rentado
public class ProductRentItemDTO {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal rentPrice;
}
