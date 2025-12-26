package com.integraObra.integraobra_api_rest.services.categories;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;

import java.util.List;

public interface CategoryDetailService {
    CategoryDetail createCategoryDetail(CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO);
    //Metodo para obtener todos los detalles de categoria por id de Categoria
    List<CategoryDetail> findAllByCategoryId(Long categoryId);
    //Metodo para obtener todos los detalles de categoria por id de Producto
    List<CategoryDetail> findAllByProductId(Long productId);
}
