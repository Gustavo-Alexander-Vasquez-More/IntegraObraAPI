package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
