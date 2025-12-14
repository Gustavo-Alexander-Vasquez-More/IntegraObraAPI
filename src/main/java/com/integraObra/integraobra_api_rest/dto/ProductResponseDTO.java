package com.integraObra.integraobra_api_rest.dto;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String cardImageUrl;
    private String sku;
    private int stock;
    private boolean available;
    private String description;
    private List<String> tags;
    private List<String> categories;
    private BigDecimal salePrice;
    private BigDecimal rentPrice;
    private boolean isForRent;
    private boolean isForSale;
    private boolean priceVisibleForRent;
    private boolean priceVisibleForSale;
    private LocalDateTime creationDate;
}
