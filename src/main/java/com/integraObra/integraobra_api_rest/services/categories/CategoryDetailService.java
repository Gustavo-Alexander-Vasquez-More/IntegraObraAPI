package com.integraObra.integraobra_api_rest.services.categories;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;

public interface CategoryDetailService {
    CategoryDetail createCategoryDetail(CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO);
}
