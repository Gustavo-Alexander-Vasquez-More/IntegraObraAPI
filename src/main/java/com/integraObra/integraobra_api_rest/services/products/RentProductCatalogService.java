package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.RentProductCardRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentProductCatalogService {
    //OBTENER TODOS LOS PRODUCTOS EN RENTA PAGINADOS
    Page<RentProductCardRequestDTO> getAllRentProductsPaginated(Pageable pageable);

    //OBTENER TODOS LOS PRODUCTOS EN RENTA PAGINADOS POR CATEGORIA
    Page<RentProductCardRequestDTO> getRentProductsByCategoryPaginated(Long id, Pageable pageable);

    //OBTENER PRODUCTOS EN RENTA PAGINADOS CON FILTROS POR CATEGORIA Y TERMINO DE BUSQUEDA SINO TODOS
    Page<RentProductCardRequestDTO> getRentProductsPaginatedWithFilterInPanel(String searchTerm, Long categoryId, Pageable pageable);
}
