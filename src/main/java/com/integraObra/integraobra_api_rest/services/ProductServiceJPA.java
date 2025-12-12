package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceJPA implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceJPA(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Metodo para crear un producto
    public Product createProduct(CreateProductRequestDTO createProductRequestDTO) {
        boolean existsBySku = productRepository.existsBySku(createProductRequestDTO.getSku());
        if (existsBySku) {
            throw new ProductExistException("No se puede crear el producto. El SKU ya existe, intente con otro o verifique si el producto ya está registrado.");
        }
        Product product = new Product(
                createProductRequestDTO.getName(),
                createProductRequestDTO.getCardImageUrl(),
                createProductRequestDTO.getSku(),
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
    //Metodo para obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //Metodo para obtener un producto por su ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Producto no encontrado con el ID: " + id));
    }

    //Metodo para buscar productos por SKU o nombre con paginacion
    public Page<Product> getProductsBySkuOrName(String searchTerm, Pageable pageable) {
        // Si el término de búsqueda está vacío o es null, devolver todos los productos paginados
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        }

        // Buscar por SKU o nombre conteniendo el término (ignorando mayúsculas/minúsculas)
        String trimmedSearch = searchTerm.trim();
        return productRepository.findBySkuContainingIgnoreCaseOrNameContainingIgnoreCase(
                trimmedSearch,
                trimmedSearch,
                pageable
        );
    }
}
