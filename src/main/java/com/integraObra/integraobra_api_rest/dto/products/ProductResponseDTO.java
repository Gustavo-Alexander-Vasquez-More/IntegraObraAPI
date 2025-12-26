package com.integraObra.integraobra_api_rest.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductResponseDTO {
    private String name;
    private String cardImageUrl;
    private String sku;
    private Integer stock;
    private String description;
    private List<String> tags;
    private Double salePrice;
    private Double rentPrice;
    private Boolean isForSale;
    private Boolean isForRent;
    private Boolean priceVisibleForRent;
    private Boolean priceVisibleForSale;


    public static ProductResponseDTO fromEntity(com.integraObra.integraobra_api_rest.models.Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setName(product.getName());
        dto.setCardImageUrl(product.getCardImageUrl());
        dto.setSku(product.getSku());
        dto.setStock(product.getStock());
        dto.setDescription(product.getDescription());
        dto.setTags(product.getTags());
        // Manejar posibles nulls en los BigDecimal para evitar NullPointerException
        dto.setSalePrice(product.getSalePrice() != null ? product.getSalePrice().doubleValue() : null);
        dto.setRentPrice(product.getRentPrice() != null ? product.getRentPrice().doubleValue() : null);
        dto.setIsForSale(product.isForSale());
        dto.setIsForRent(product.isForRent());
        dto.setPriceVisibleForRent(product.isPriceVisibleForRent());
        dto.setPriceVisibleForSale(product.isPriceVisibleForSale());
        return dto;
    }
}
