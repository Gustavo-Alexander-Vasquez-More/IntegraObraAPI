package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.services.ProductServiceJPA;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductServiceJPA productServiceJPA;

    public ProductController(ProductServiceJPA productServiceJPA) {
        this.productServiceJPA = productServiceJPA;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequestDTO createProductRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productServiceJPA.createProduct(createProductRequestDTO));
    }

}
