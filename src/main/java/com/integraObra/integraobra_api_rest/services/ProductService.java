package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService{

  Product createProduct(CreateProductRequestDTO createProductRequestDTO);
  List<Product> getAllProducts();
  Product getProductById(Long id);
  Page<Product> getProductsBySkuOrName(String searchTerm, Pageable pageable);
}
