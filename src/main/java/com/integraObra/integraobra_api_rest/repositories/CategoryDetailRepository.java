package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
    boolean existsByCategoryIdAndProductId(Long categoryId, Long productId);

    @Modifying
    @Transactional
    //ELIMINAR DETALLES DE CATEGORIA POR ID DE PRODUCTO
    @Query("delete from CategoryDetail  cd where cd.product.id = :productId")
    void deleteByProductId(Long productId);

    // JPQL para obtener triples (productId, categoryDetailId, categoryName, categoryId) para un conjunto de productIds
    @Query("SELECT cd.product.id, cd.id, cd.category.name, cd.category.id FROM CategoryDetail cd WHERE cd.product.id IN :productIds")
    List<Object[]> findProductIdAndCategoryDetailIdAndCategoryNameByProductIds(@Param("productIds") List<Long> productIds);

}
