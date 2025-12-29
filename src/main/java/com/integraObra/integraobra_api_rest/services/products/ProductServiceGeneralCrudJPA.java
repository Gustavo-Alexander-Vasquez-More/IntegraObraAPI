package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.dto.products.RentProductCardRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.UpdateRequestProductDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import com.integraObra.integraobra_api_rest.repositories.CategoryDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

//IMPLEMENTACION DEL SERVICIO GENERAL DE CRUD DE PRODUCTOS
public class ProductServiceGeneralCrudJPA implements ProductServiceGeneralCrud {
    private final ProductRepository productRepository;
    private final CategoryDetailRepository categoryDetailRepository;

    public ProductServiceGeneralCrudJPA(ProductRepository productRepository, CategoryDetailRepository categoryDetailRepository) {
        this.productRepository = productRepository;
        this.categoryDetailRepository = categoryDetailRepository;
    }

    //CREAR PRODUCTO
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

    //ELIMINAR PRODUCTO POR ID
    //TAMBIEN SE ELIMINAN LOS DETALLES DE CATEGORIA ASOCIADOS AL PRODUCTO DE MANERA QUE NO ARROJE UNA VIOLACION DE RESTRICCION DE CLAVE FORANEA
    @Transactional
    public boolean deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("El ID proporcionado indica que el producto no existe."));
        categoryDetailRepository.deleteByProductId(productId);
        productRepository.delete(product);
        return true;
    }

    //ACTUALIZAR PRODUCTO POR ID
    public ProductResponseDTO updateProduct(UpdateRequestProductDTO dto, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("No se puede actualizar el producto. El ID proporcionado no existe."));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            existingProduct.setName(dto.getName().toUpperCase().trim());
        }

        if (dto.getCardImageUrl() != null) {
            existingProduct.setCardImageUrl(dto.getCardImageUrl().trim());
        }

        if (dto.getSku() != null) {
            existingProduct.setSku(dto.getSku().trim());
        }

        if (dto.getStock() != null) {
            existingProduct.setStock(dto.getStock());
        }

        if (dto.getDescription() != null) {
            existingProduct.setDescription(dto.getDescription());
        }

        if (dto.getTags() != null) {
            existingProduct.setTags(dto.getTags());
        }

        if (dto.getSalePrice() != null) {
            existingProduct.setSalePrice(dto.getSalePrice());
        }

        if (dto.getRentPrice() != null) {
            existingProduct.setRentPrice(dto.getRentPrice());
        }

        // Para booleanos usa Boolean en el DTO si quieres que sean opcionales
        if (dto.getIsForRent() != null) {
            existingProduct.setForRent(dto.getIsForRent());
        }
        if (dto.getIsForSale() != null) {
            existingProduct.setForSale(dto.getIsForSale());
        }
        if (dto.getPriceVisibleForRent() != null) {
            existingProduct.setPriceVisibleForRent(dto.getPriceVisibleForRent());
        }
        if (dto.getPriceVisibleForSale() != null) {
            existingProduct.setPriceVisibleForSale(dto.getPriceVisibleForSale());
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductResponseDTO.fromEntity(updatedProduct);
    }

    //OBTENER PRODUCTO POR ID
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con el ID proporcionado."));
        return ProductResponseDTO.fromEntity(product);
    }

    //LISTA DE TODOS LOS PRODUCTOS FILTRADOS POR CATEGORIA Y SINO HAY NIGUNO ARROJAR TODOS LOS PRODUCTOS
    public List<RentProductCardRequestDTO> getAllProductsByCategoryId(Long categoryId) {
        List<Product> products;
        if (categoryId == 0) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findProductsByCategoryId(categoryId);
            if (products.isEmpty()) {
                products = productRepository.findAll();
            }
        }
        return products.stream()
                .map(RentProductCardRequestDTO::fromEntity)
                .toList();
    }


}
