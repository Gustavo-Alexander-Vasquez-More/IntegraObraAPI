package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceJPA implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceJPA(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Metodo para crear un producto
    @Override
    public Product createProduct(CreateProductRequestDTO createProductRequestDTO) {
        boolean existsBySku = productRepository.existsBySku(createProductRequestDTO.getSku());
        if (existsBySku) {
            throw new ProductExistException("No se puede crear el producto. El SKU ya existe, intente con otro o verifique si el producto ya está registrado.");
        }
        Product product = new Product(
                createProductRequestDTO.getName().toUpperCase().trim(),
                createProductRequestDTO.getCardImageUrl().trim(),
                createProductRequestDTO.getSku().trim(),
                createProductRequestDTO.getStock(),
                createProductRequestDTO.getDescription(),
                createProductRequestDTO.getTags(),
                createProductRequestDTO.getSalePrice(),
                createProductRequestDTO.getRentPrice(),
                createProductRequestDTO.getIsForSale(),
                createProductRequestDTO.getPriceVisibleForRent(),
                createProductRequestDTO.getPriceVisibleForSale()
        );
        return productRepository.save(product);
    }

    // Metodo para obtener productos paginados con filtrado por termino y categoria usando la consulta JPQL
    @Override
    public Page<ProductResponseDTO> getProductsPaginated(String searchTerm, String category, Pageable pageable){
        String term = (searchTerm == null || searchTerm.trim().isEmpty()) ? null : searchTerm.trim();
        String cat = (category == null || category.trim().isEmpty()) ? null : category.trim();

        // Si no hay ni categoria ni termino, devolver todos los productos paginados
        if (cat == null && term == null) {
            return productRepository.findAll(pageable).map(ProductResponseDTO::fromEntity);
        }

        // Si no hay categoria pero hay termino, buscar por sku o name en todos los productos
        if (cat == null) {
            return productRepository.findBySkuContainingIgnoreCaseOrNameContainingIgnoreCase(term, term, pageable)
                    .map(ProductResponseDTO::fromEntity);
        }

        // Si hay categoria (con o sin termino), usar la consulta JOIN para filtrar por categoria+termino
        Page<Product> products = productRepository.searchByCategoryAndTerm(cat, term, pageable);
        return products.map(ProductResponseDTO::fromEntity);
    }

}
