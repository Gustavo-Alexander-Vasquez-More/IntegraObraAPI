package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.RentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentPaginatedPanelService {
    Page<RentResponseDTO> getAllRentsPaginated(Pageable pageable);
}
