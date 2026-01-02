package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.RentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentDetailRepository extends JpaRepository<RentDetail, Long> {

    List<RentDetail> findByRentId(Long rentId);

}
