package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
    boolean existsByCategoryIdAndProductId(Long categoryId, Long productId);

    // Permite verificar si existe alguna relacion CategoryDetail con una categoria por su nombre (ignore case)
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
