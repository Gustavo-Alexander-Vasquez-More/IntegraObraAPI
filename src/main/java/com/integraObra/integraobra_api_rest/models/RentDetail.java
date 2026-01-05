package com.integraObra.integraobra_api_rest.models;

import com.integraObra.integraobra_api_rest.utils.RentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rent_details")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "rent_id", nullable = false)
    private Rent rent; //Una renta puede tener muchos detalles de renta

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; //Un producto puede estar en muchos detalles de renta

    private Integer quantity;
    private Integer daysRented;
    private BigDecimal discountRate;
    private BigDecimal totalPriceWithDiscount;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
