package com.integraObra.integraobra_api_rest.dto.products;

import com.integraObra.integraobra_api_rest.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class EspecificProductCatalogResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String sku;
    private String cardImageUrl;
    private boolean isForRent;
    private boolean isForSale;
    private BigDecimal rentPrice;
    private BigDecimal salePrice;
    private boolean priceVisibleForRent;
    private boolean priceVisibleForSale;
    private int stock;
    private List<ProductCategoryDetailDTO> categoryDetail;

    //from entity
    public static EspecificProductCatalogResponseDTO fromEntity(Product product, List<ProductCategoryDetailDTO> categoriesList) {
        EspecificProductCatalogResponseDTO dto = new EspecificProductCatalogResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSku(product.getSku());
        dto.setCardImageUrl(product.getCardImageUrl());
        dto.setForRent(product.isForRent());
        dto.setForSale(product.isForSale());
        dto.setRentPrice(product.getRentPrice());
        dto.setSalePrice(product.getSalePrice());
        dto.setPriceVisibleForRent(product.isPriceVisibleForRent());
        dto.setPriceVisibleForSale(product.isPriceVisibleForSale());
        dto.setStock(product.getStock());
        dto.setCategoryDetail(categoriesList);
        return dto;
    }
}
