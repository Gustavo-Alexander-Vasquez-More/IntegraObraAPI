package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.RentProductCardRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.ProductCategoryDetailDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.CategoryDetailRepository;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentProductServiceCatalogoJPA {
    private final ProductRepository productRepository;
    private final CategoryDetailRepository categoryDetailRepository;

    public RentProductServiceCatalogoJPA(ProductRepository productRepository, CategoryDetailRepository categoryDetailRepository) {
        this.productRepository = productRepository;
        this.categoryDetailRepository = categoryDetailRepository;
    }

    // Helper: mapear Page<Product> a Page<RentProductCardRequestDTO> obteniendo categorias por productIds en 1 consulta
    private Page<RentProductCardRequestDTO> mapProductsPageToDtoWithCategories(Page<Product> productsPage) {
        if (productsPage == null || productsPage.isEmpty()) {
            return productsPage == null ? Page.empty() : productsPage.map(p -> {
                // sin categorias
                return RentProductCardRequestDTO.fromEntity(p, Collections.emptyList());
            });
        }

        List<Long> productIds = productsPage.stream().map(Product::getId).collect(Collectors.toList());

        List<Object[]> triples = categoryDetailRepository.findProductIdAndCategoryDetailIdAndCategoryNameByProductIds(productIds);

        // Agrupar por productId -> lista de ProductCategoryDetailDTO
        Map<Long, List<ProductCategoryDetailDTO>> categoriesByProductId = new HashMap<>();
        for (Object[] t : triples) {
            Long pId = t[0] == null ? null : ((Number) t[0]).longValue();
            Long categoryDetailId = t[1] == null ? null : ((Number) t[1]).longValue();
            String catName = t[2] == null ? null : t[2].toString();
            if (pId == null || categoryDetailId == null || catName == null) continue;
            categoriesByProductId.computeIfAbsent(pId, k -> new ArrayList<>()).add(new ProductCategoryDetailDTO(categoryDetailId, catName));
        }

        // Mapear
        return productsPage.map(product -> {
            List<ProductCategoryDetailDTO> cats = categoriesByProductId.getOrDefault(product.getId(), Collections.emptyList());
            return RentProductCardRequestDTO.fromEntity(product, cats);
        });
    }

    //OBTENER TODOS LOS PRODUCTOS EN RENTA PAGINADOS
    public Page<RentProductCardRequestDTO> getAllRentProductsPaginated(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllRentProducts(pageable);
        return mapProductsPageToDtoWithCategories(productsPage);
    }

    //OBTENER TODOS LOS PRODUCTOS EN RENTA DE UNA CATEGORIA PAGINADOS
    public Page<RentProductCardRequestDTO> getRentProductsByCategoryPaginated(Long categoryId, Pageable pageable){
        Page<Product> productsPage = productRepository.findAllRentProductsByCategoryId(categoryId, pageable);
        return mapProductsPageToDtoWithCategories(productsPage);
    }

    //OBTENER PRODUCTOS EN RENTA POR TERMINO DE BUSQUEDA PAGINADOS
    public Page<RentProductCardRequestDTO> getRentProductsBySearchTermPaginated(String searchTerm, Pageable pageable) {
        // Implementar la lógica para buscar productos por término de búsqueda
        Page<Product> productsPage = productRepository.findAllRentProductsBySearchTerm(searchTerm, pageable);
        return mapProductsPageToDtoWithCategories(productsPage);
    }

    //OBTENER PRODUCTOS EN RENTA POR CATEGORIA Y TERMINO DE BUSQUEDA PAGINADOS
    public Page<RentProductCardRequestDTO> getRentProductsByCategoryAndSearchTermPaginated(Long categoryId, String searchTerm, Pageable pageable){
        Page<Product> productsPage=productRepository.findAllRentProductsByCategoryIdAndSearchTerm(categoryId, searchTerm, pageable);
        return mapProductsPageToDtoWithCategories(productsPage);
    }

    //OBTENER PRODUCTOS EN RENTA PAGINADOS CON FILTROS POR CATEGORIA Y TERMINO DE BUSQUEDA SINO TODOS
    //SOLO SE USA PARA EL CATALOGO DE PRODUCTOS EN RENTA
    public Page<RentProductCardRequestDTO> getRentProductsPaginatedWithFilterInCatalog (String searchTerm, Long categoryId, Pageable pageable){
        //SI NO HAY CATEGORIA, NI TERMINO DE BUSQUEDA, DEVUELVE TODOS LOS PRODUCTOS EN RENTA PAGINADOS
        if((categoryId == null) && (searchTerm==null || searchTerm.isBlank())){
            return getAllRentProductsPaginated(pageable);
        }
        //SI HAY CATEGORIA, PERO NO TERMINO DE BUSQUEDA, TRAE TODOS LOS PRODUCTOS DE ESA CATEGORIA
        else if((categoryId!=null) && (searchTerm==null || searchTerm.isBlank())){
            return getRentProductsByCategoryPaginated(categoryId, pageable);
        }
        //SI HAY TERMINO DE BUSQUEDA, PERO NO CATEGORIA, BUSCA LOS PRODUCTO POR NAME O SKU QUE COINCIDAN CON EL TERMINO
        else if((categoryId==null) && (searchTerm!=null && !searchTerm.isBlank())) {
            return getRentProductsBySearchTermPaginated(searchTerm, pageable);
        }
        //SI HAY CATEGORIA Y TERMINO DE BUSQUEDA, PRIMERO TRAE LOS PRODUCTOS DE ESA CATEGORIA Y LUEGO FILTRA POR TERMINO
        else {
            return getRentProductsByCategoryAndSearchTermPaginated(categoryId, searchTerm, pageable);
        }
    }

}
