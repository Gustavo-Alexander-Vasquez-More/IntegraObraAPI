package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.RentProductCardRequestDTO;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.CategoryDetailRepository;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RentProductServiceCatalogoJPA {
    private final ProductRepository productRepository;
    private final CategoryDetailRepository categoryDetailRepository;

    public RentProductServiceCatalogoJPA(ProductRepository productRepository, CategoryDetailRepository categoryDetailRepository) {
        this.productRepository = productRepository;
        this.categoryDetailRepository = categoryDetailRepository;
    }

    //OBTENER TODOS LOS PRODUCTOS EN RENTA PAGINADOS
    public Page<RentProductCardRequestDTO> getAllRentProductsPaginated(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllRentProducts(pageable);
        return productsPage.map(RentProductCardRequestDTO::fromEntity);
    }

    //OBTENER TODOS LOS PRODUCTOS EN RENTA DE UNA CATEGORIA PAGINADOS
    public Page<RentProductCardRequestDTO> getRentProductsByCategoryPaginated(Long categoryId, Pageable pageable){
        Page<Product> productsPage = productRepository.findAllRentProductsByCategoryId(categoryId, pageable);
        return productsPage.map(RentProductCardRequestDTO::fromEntity);
    }

    //OBTENER PRODUCTOS EN RENTA POR TERMINO DE BUSQUEDA PAGINADOS
    public Page<RentProductCardRequestDTO> getRentProductsBySearchTermPaginated(String searchTerm, Pageable pageable) {
        // Implementar la lógica para buscar productos por término de búsqueda
        Page<Product> productsPage = productRepository.findAllRentProductsBySearchTerm(searchTerm, pageable);
        return productsPage.map(RentProductCardRequestDTO::fromEntity);
    }

    //OBTENER PRODUCTOS EN RENTA POR CATEGORIA Y TERMINO DE BUSQUEDA PAGINADOS
    public Page<RentProductCardRequestDTO> getRentProductsByCategoryAndSearchTermPaginated(Long categoryId, String searchTerm, Pageable pageable){
        Page<Product> productsPage=productRepository.findAllRentProductsByCategoryIdAndSearchTerm(categoryId, searchTerm, pageable);
        return productsPage.map(RentProductCardRequestDTO::fromEntity);
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
