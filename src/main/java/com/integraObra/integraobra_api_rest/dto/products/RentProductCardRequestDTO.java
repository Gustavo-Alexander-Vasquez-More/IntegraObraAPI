package com.integraObra.integraobra_api_rest.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentProductCardRequestDTO {
    private Long id;
    private String name;
    private String cardImageUrl;
    private BigDecimal rentPrice;
    private boolean priceVisibleForRent;
    private int stock;

    public static RentProductCardRequestDTO fromEntity(com.integraObra.integraobra_api_rest.models.Product product) {
        RentProductCardRequestDTO dto = new RentProductCardRequestDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCardImageUrl(product.getCardImageUrl());
        dto.setRentPrice(product.getRentPrice());
        dto.setPriceVisibleForRent(product.isPriceVisibleForRent());
        dto.setStock(product.getStock());
        return dto;
    }

}
