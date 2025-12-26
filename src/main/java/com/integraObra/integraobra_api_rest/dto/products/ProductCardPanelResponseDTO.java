package com.integraObra.integraobra_api_rest.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductCardPanelResponseDTO {
    private Long id;
    private String name;
    private String cardImageUrl;
    private String sku;
    private int stock;
    private List<ProductCategoryDetailDTO> categories;

    public static ProductCardPanelResponseDTO fromEntity(com.integraObra.integraobra_api_rest.models.Product product) {
        return fromEntityWithCategories(product, Collections.emptyList());
    }

    public static ProductCardPanelResponseDTO fromEntityWithCategories(com.integraObra.integraobra_api_rest.models.Product product, List<ProductCategoryDetailDTO> categoriesList) {
        ProductCardPanelResponseDTO dto = new ProductCardPanelResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCardImageUrl(product.getCardImageUrl());
        dto.setSku(product.getSku());
        dto.setStock(product.getStock());
        dto.setCategories(categoriesList == null ? new ArrayList<>() : categoriesList);
        return dto;
    }
}
