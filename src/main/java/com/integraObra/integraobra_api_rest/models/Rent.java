package com.integraObra.integraobra_api_rest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rents")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rent_id", nullable = false)
    private List<RentDetail> rentDetails; //Relacion con detalle de renta, es decir una renta puede tener varios detalles, porque puede tener varios productos rentados

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client; //Relacion con cliente, es decir una renta pertenece a un cliente (muchas rentas pueden pertenecer a un cliente)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
