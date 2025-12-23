package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.dto.products.UpdateRequestProductDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import com.integraObra.integraobra_api_rest.repositories.CategoryDetailRepository;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import com.integraObra.integraobra_api_rest.repositories.PhotoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceJPA implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryDetailRepository categoryDetailRepository;
    private final PhotoRepository photoRepository;

    public ProductServiceJPA(ProductRepository productRepository, CategoryDetailRepository categoryDetailRepository, PhotoRepository photoRepository) {
        this.productRepository = productRepository;
        this.categoryDetailRepository = categoryDetailRepository;
        this.photoRepository = photoRepository;
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
        Pageable effectivePageable = (pageable == null || !pageable.isPaged())
                ? PageRequest.of(0, 20, Sort.by("name").ascending())
                : pageable;

        // Obtenemos Page<Product> en todas las ramas
        Page<Product> productPage;
        if (cat == null && term == null) {
            productPage = productRepository.findAll(effectivePageable);
        } else if (cat == null) {
            productPage = productRepository.findBySkuContainingIgnoreCaseOrNameContainingIgnoreCase(term, term, effectivePageable);
        } else {
            productPage = productRepository.searchByCategoryAndTerm(cat, term, effectivePageable);
        }

        // Si no hay productos, devolver page mapeada vacía
        if (productPage.isEmpty()) {
            return productPage.map(ProductResponseDTO::fromEntity); // mantengo estructura Page pero con content vacio
        }

        // Batch: obtener todas las categorias relacionadas a los productos de la página
        java.util.List<Long> productIds = productPage.stream().map(Product::getId).collect(Collectors.toList());
        java.util.List<CategoryDetail> details = categoryDetailRepository.findByProduct_IdIn(productIds);

        // Mapear productId -> List<String> categoryNames
        Map<Long, java.util.List<String>> categoriesByProduct = details.stream()
                .collect(Collectors.groupingBy(cd -> cd.getProduct().getId(),
                        Collectors.mapping(cd -> cd.getCategory().getName(), Collectors.toList())));

        // Mapear Page<Product> -> Page<ProductResponseDTO> manteniendo metadata de paginación
        return productPage.map(p -> {
            java.util.List<String> cats = categoriesByProduct.getOrDefault(p.getId(), java.util.List.of());
            return ProductResponseDTO.fromEntity(p, cats);
        });
    }

    @Override
    public boolean deleteProductById(Long productId) {
        boolean existsById = productRepository.existsById(productId);
        if (!existsById) {
            throw new NotFoundException("No se puede eliminar el producto. El ID proporcionado no existe.");
        }
        try {
            // Borrar fotos asociadas
            java.util.List<Long> ids = java.util.List.of(productId);
            // usar el metodo con propiedad anidada
            photoRepository.deleteByProduct_IdIn(ids);

            // Borrar CategoryDetails asociados
            java.util.List<CategoryDetail> details = categoryDetailRepository.findByProduct_IdIn(ids);
            if (!details.isEmpty()) {
                categoryDetailRepository.deleteAll(details);
            }

            // Finalmente borrar el producto
            productRepository.deleteById(productId);
            return true;
        } catch (DataIntegrityViolationException ex) {
            throw new com.integraObra.integraobra_api_rest.exceptions.DeletionConflictException("No se puede eliminar el producto por dependencias en la base de datos.");
        }
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
