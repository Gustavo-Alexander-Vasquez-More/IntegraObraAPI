package com.integraObra.integraobra_api_rest.services.categories;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;

import java.util.List;

public interface CategoryDetailService {
    CategoryDetail createCategoryDetail(CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO);

    boolean deleteCategoryDetail(Long id);
}
