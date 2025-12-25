package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.ProductCardPanelResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//SERVICIO PARA OBTENER PRODUCTOS PAGINADOS PARA EL PANEL DE GESTION DE PRODUCTOS
public interface ProductServicePaginatedPanel {
    //OBTENER TODOS LOS PRODUCTOS PAGINADOS USANDO
    Page<ProductCardPanelResponseDTO> getAllProductsPaginated(Pageable pageable);

    //OBTENER TODOS LOS PRODUCTOS PAGINADOS POR CATEGORIA
    Page<ProductCardPanelResponseDTO> getProductsByCategoryPaginated(Long id, Pageable pageable);

    //OBTENER PRODUCTOS PAGINADOS CON FILTROS POR CATEGORIA Y TERMINO DE BUSQUEDA SINO TODOS
    Page<ProductCardPanelResponseDTO> getProductsPaginatedWithFilterInPanel(String searchTerm, Long categoryId, Pageable pageable);
}
