package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceJPA {
    private final ProductRepository productRepository;

    public ProductServiceJPA(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
