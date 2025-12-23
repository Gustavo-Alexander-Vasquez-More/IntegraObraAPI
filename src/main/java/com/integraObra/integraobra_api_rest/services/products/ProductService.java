package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.dto.products.UpdateRequestProductDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequestDTO createProductRequestDTO);

    Page<ProductResponseDTO> getProductsPaginated(String searchTerm, String category, Pageable pageable);

    List<ProductResponseDTO> getAllProducts();

    boolean deleteProductById(Long productId);

    ProductResponseDTO updateProduct(UpdateRequestProductDTO updateRequestProductDTO, Long productId);
}
