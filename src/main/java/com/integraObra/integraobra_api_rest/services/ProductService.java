package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.models.Product;

public interface ProductService{

  Product createProduct(CreateProductRequestDTO createProductRequestDTO);

}
