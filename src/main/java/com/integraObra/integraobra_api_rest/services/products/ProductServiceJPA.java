package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.dto.products.UpdateRequestProductDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            //devolver lista vacia si no hay productos
            return List.of();
        } else {
            return products.stream()
                    .map(ProductResponseDTO::fromEntity)
                    .toList();
        }
    }

    // Metodo para obtener productos paginados con filtrado por termino y categoria usando la consulta JPQL
    @Override
    public Page<ProductResponseDTO> getProductsPaginated(String searchTerm, String category, Pageable pageable){
        String term = (searchTerm == null || searchTerm.trim().isEmpty()) ? null : searchTerm.trim();
        String cat = (category == null || category.trim().isEmpty()) ? null : category.trim();

        // Si el pageable no está paginado (por ejemplo Pageable.unpaged()), aplicamos un pageable por defecto
        Pageable effectivePageable = (pageable == null || pageable.isPaged() == false)
                ? PageRequest.of(0, 20, Sort.by("name").ascending())
                : pageable;

        // Si no hay ni categoria ni termino, devolver todos los productos paginados
        if (cat == null && term == null) {
            return productRepository.findAll(effectivePageable).map(ProductResponseDTO::fromEntity);
        }

        // Si no hay categoria pero hay termino, buscar por sku o name en todos los productos
        if (cat == null) {
            return productRepository.findBySkuContainingIgnoreCaseOrNameContainingIgnoreCase(term, term, effectivePageable)
                    .map(ProductResponseDTO::fromEntity);
        }

        // Si hay categoria (con o sin termino), usar la consulta JOIN para filtrar por categoria+termino
        Page<Product> products = productRepository.searchByCategoryAndTerm(cat, term, effectivePageable);
        return products.map(ProductResponseDTO::fromEntity);
    }

    @Override
    public boolean deleteProductById(Long productId) {
        boolean existsById = productRepository.existsById(productId);
        if (!existsById) {
            throw new NotFoundException("No se puede eliminar el producto. El ID proporcionado no existe.");
        }
        productRepository.deleteById(productId);
        return true;
    }

    @Override
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

}
