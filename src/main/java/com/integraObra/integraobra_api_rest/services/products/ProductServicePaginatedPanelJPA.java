package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.ProductCardPanelResponseDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.CategoryDetailRepository;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//SERVICIO PARA OBTENER PRODUCTOS PAGINADOS PARA EL PANEL DE GESTION DE PRODUCTOS - IMPLEMENTACION JPA
@Service
public class ProductServicePaginatedPanelJPA implements ProductServicePaginatedPanel {
    private final ProductRepository productRepository;
    private final CategoryDetailRepository categoryDetailRepository;

    public ProductServicePaginatedPanelJPA(ProductRepository productRepository, CategoryDetailRepository categoryDetailRepository) {
        this.productRepository = productRepository;
        this.categoryDetailRepository = categoryDetailRepository;
    }

    //OBTENER TODOS LOS PRODUCTOS PAGINADOS
    public Page<ProductCardPanelResponseDTO> getAllProductsPaginated(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(ProductCardPanelResponseDTO::fromEntity);
    }

    //OBTENER TODOS LOS PRODUCTOS DE UNA CATEGORIA PAGINADOS
    public Page<ProductCardPanelResponseDTO> getProductsByCategoryPaginated(Long categoryId, Pageable pageable){
        Page<Product> productsPage = productRepository.findAllByCategoryId(categoryId, pageable);
        return productsPage.map(ProductCardPanelResponseDTO::fromEntity);
    }

    //OBTENER PRODUCTOS POR TERMINO DE BUSQUEDA PAGINADOS
    public Page<ProductCardPanelResponseDTO> getProductsBySearchTermPaginated(String searchTerm, Pageable pageable) {
        // Implementar la lógica para buscar productos por término de búsqueda
        Page<Product> productsPage = productRepository.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(searchTerm, searchTerm, pageable);
        return productsPage.map(ProductCardPanelResponseDTO::fromEntity);
    }

    //OBTENER PRODUCTOS POR CATEGORIA Y TERMINO DE BUSQUEDA PAGINADOS
    public Page<ProductCardPanelResponseDTO> getProductsByCategoryAndSearchTermPaginated(Long categoryId, String searchTerm, Pageable pageable){
        Page<Product> productsPage=productRepository.findAllByCategoryIdAndSearchTerm(categoryId, searchTerm, pageable);
        return productsPage.map(ProductCardPanelResponseDTO::fromEntity);
    }

    //OBTENER PRODUCTOS PAGINADOS CON FILTROS POR CATEGORIA Y TERMINO DE BUSQUEDA SINO TODOS
    //SOLO SE USA PARA EL PANEL DE GESTION DE PRODUCTOS
    public Page<ProductCardPanelResponseDTO> getProductsPaginatedWithFilterInPanel (String searchTerm, Long categoryId, Pageable pageable){
        //SI NO HAY CATEGORIA, NI TERMINO DE BUSQUEDA, DEVUELVE TODOS LOS PRODUCTOS PAGINADOS
        if((categoryId == null) && (searchTerm==null || searchTerm.isBlank())){
            return getAllProductsPaginated(pageable);
        }
        //SI HAY CATEGORIA, PERO NO TERMINO DE BUSQUEDA, TRAE TODOS LOS PRODUCTOS DE ESA CATEGORIA
        else if((categoryId!=null) && (searchTerm==null || searchTerm.isBlank())){
            return getProductsByCategoryPaginated(categoryId, pageable);
        }
        //SI HAY TERMINO DE BUSQUEDA, PERO NO CATEGORIA, BUSCA LOS PRODUCTO POR NAME O SKU QUE COINCIDAN CON EL TERMINO
        else if((categoryId==null) && (searchTerm!=null && !searchTerm.isBlank())) {
            return getProductsBySearchTermPaginated(searchTerm, pageable);
        }
        //SI HAY CATEGORIA Y TERMINO DE BUSQUEDA, PRIMERO TRAE LOS PRODUCTOS DE ESA CATEGORIA Y LUEGO FILTRA POR TERMINO
        else {
            return getProductsByCategoryAndSearchTermPaginated(categoryId, searchTerm, pageable);
        }
    }
}
