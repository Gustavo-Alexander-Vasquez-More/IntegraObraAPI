package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.stereotype.Service;

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

}
