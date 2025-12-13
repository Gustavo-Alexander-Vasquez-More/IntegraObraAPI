package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService{

  Product createProduct(CreateProductRequestDTO createProductRequestDTO);
  Page<Product> getProductsBySkuOrName(String searchTerm, String category, Pageable pageable);
}
