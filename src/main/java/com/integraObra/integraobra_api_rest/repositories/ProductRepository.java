package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);

    // Busqueda con paginacion por SKU o nombre
    Page<Product> findBySkuContainingIgnoreCaseOrNameContainingIgnoreCase(String sku, String name, Pageable pageable);

    // Consulta JPQL que busca productos por categoria (a traves de CategoryDetail) y por termino (sku o name)
    @Query(
        value = "select distinct p from CategoryDetail cd join cd.product p join cd.category c " +
                "where (:category is null or lower(c.name) = lower(:category)) " +
                "and (:term is null or (lower(p.sku) like concat('%', lower(:term), '%') or lower(p.name) like concat('%', lower(:term), '%')))",
        countQuery = "select count(distinct p) from CategoryDetail cd join cd.product p join cd.category c " +
                "where (:category is null or lower(c.name) = lower(:category)) " +
                "and (:term is null or (lower(p.sku) like concat('%', lower(:term), '%') or lower(p.name) like concat('%', lower(:term), '%')) )"
    )
    Page<Product> searchByCategoryAndTerm(@Param("category") String category,
                                         @Param("term") String term,
                                         Pageable pageable);
}
