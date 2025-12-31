package com.integraObra.integraobra_api_rest.models;

import com.integraObra.integraobra_api_rest.utils.ReputationClient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="clients")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private ReputationClient reputation;
    private String frontPhotoIne;
    private String backPhotoIne;
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
