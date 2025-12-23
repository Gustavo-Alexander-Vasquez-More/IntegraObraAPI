package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService{

  Product createProduct(CreateProductRequestDTO createProductRequestDTO); //Metodo para crear un nuevo producto
    List<ProductResponseDTO> getAllProducts();
  Page<ProductResponseDTO> getProductsPaginated(String searchTerm, String category, Pageable pageable); //Metodo para obtener todos los productos con opcion de filtrado por categorias y paginacion
}
