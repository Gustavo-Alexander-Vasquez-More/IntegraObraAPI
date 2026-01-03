package com.integraObra.integraobra_api_rest.models;

import com.integraObra.integraobra_api_rest.utils.RentStatus;
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
@NoArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client; //Relacion con cliente, es decir una renta pertenece a un cliente (muchas rentas pueden pertenecer a un cliente)

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }

    //constructor solo con id del cliente y el status
    public Rent(Client client, RentStatus status) {
        this.client = client;
        this.status = status;
    }

}
