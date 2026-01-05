package com.integraObra.integraobra_api_rest.dto.rents;

import com.integraObra.integraobra_api_rest.models.Client;
import com.integraObra.integraobra_api_rest.utils.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
//DTO para devolver la información de una renta completa
public class RentResponseDTO {
    private Long id;
    private Client client;
    private List<RentDetailResponseDTO> rentDetails;
    private BigDecimal totalPrice;
    private RentStatus status;
    private LocalDateTime createdAt;


}
