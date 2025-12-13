package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.services.ProductServiceJPA;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> getProductsBySkuOrName(@RequestParam String searchTerm, String category, Pageable pageable) {
        return ResponseEntity.ok(productServiceJPA.getProductsBySkuOrName(searchTerm, category ,pageable));
    }
}
