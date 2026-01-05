package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.RentDetailItemDTO;
import com.integraObra.integraobra_api_rest.models.RentDetail;

import java.util.List;

public interface RentDetailGeneralCrudService {
    RentDetail createRentDetail(List<RentDetailItemDTO> rentDetailItemDTOS, Long rentId);
}
