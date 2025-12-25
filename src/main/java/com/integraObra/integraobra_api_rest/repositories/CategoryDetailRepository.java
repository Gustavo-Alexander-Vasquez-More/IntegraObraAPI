package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
    boolean existsByCategoryIdAndProductId(Long categoryId, Long productId);

}
