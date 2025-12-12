package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);
    Page<Product> findBySkuContainingIgnoreCaseOrNameContainingIgnoreCase(String sku, String name, Pageable pageable); //Busqueda con paginacion con criterios de SKU o nombre

}
