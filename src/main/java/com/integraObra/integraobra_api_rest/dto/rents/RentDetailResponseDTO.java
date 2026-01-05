package com.integraObra.integraobra_api_rest.dto.rents;

import com.integraObra.integraobra_api_rest.dto.products.ProductRentItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
//DTO para devolver el detalle de la renta con el producto rentado, descuento y precio total con descuento
public class RentDetailResponseDTO {
    private Long id;
    private ProductRentItemDTO productRentItem;
    private BigDecimal discountRate;
    private Integer daysRented;
    private Integer quantity;
    private BigDecimal totalPriceWithDiscount;
}
