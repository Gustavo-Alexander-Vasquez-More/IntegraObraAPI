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

    //OBTENER DETALLES DE CATEGORIA POR ID DE PRODUCTO
    List<CategoryDetail> findAllByProductId(Long productId);

    // JPQL para obtener pares (productId, categoryName) para un conjunto de productIds
    @Query("SELECT cd.product.id, cd.category.name FROM CategoryDetail cd WHERE cd.product.id IN :productIds")
    List<Object[]> findProductIdsAndCategoryNamesByProductIds(@Param("productIds") List<Long> productIds);

    // JPQL para obtener solo los nombres de las categorias asociadas a un producto
    @Query("SELECT DISTINCT cd.category.name FROM CategoryDetail cd WHERE cd.product.id = :productId")
    List<String> findCategoryNamesByProductId(@Param("productId") Long productId);

    List<CategoryDetail> findAllByCategoryId(Long categoryId);
}
