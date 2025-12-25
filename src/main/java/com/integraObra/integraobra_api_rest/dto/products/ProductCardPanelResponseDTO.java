package com.integraObra.integraobra_api_rest.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductCardPanelResponseDTO {
    private String name;
    private String cardImageUrl;
    private String sku;
    private int stock;

    public static ProductCardPanelResponseDTO fromEntity(com.integraObra.integraobra_api_rest.models.Product product) {
        return new ProductCardPanelResponseDTO(
                product.getName(),
                product.getCardImageUrl(),
                product.getSku(),
                product.getStock()
        );
    }
}
