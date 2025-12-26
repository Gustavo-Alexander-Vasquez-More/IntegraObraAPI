package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    //OBTENER DETALLES DE CATEGORIA POR ID DE CATEGORIA
    List<CategoryDetail> findAllByCategoryId(Long categoryId);

    //OBTENER DETALLES DE CATEGORIA POR ID DE PRODUCTO
    List<CategoryDetail> findAllByProductId(Long productId);

}

