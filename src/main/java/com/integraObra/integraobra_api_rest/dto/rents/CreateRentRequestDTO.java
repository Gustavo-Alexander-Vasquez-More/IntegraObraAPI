package com.integraObra.integraobra_api_rest.dto.rents;

import com.integraObra.integraobra_api_rest.utils.RentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateRentRequestDTO {
    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Long clientId;
    @NotNull( message = " La lista de detalles de renta no puede ser nulo")
    @NotEmpty( message = " La lista de detalles de renta no puede estar vacía")
    @Valid
    private List<RentDetailItemDTO> rentDetails;
    @Enumerated(EnumType.STRING)
    private RentStatus status;
}
