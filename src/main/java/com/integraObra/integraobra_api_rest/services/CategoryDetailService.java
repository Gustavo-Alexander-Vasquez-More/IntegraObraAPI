package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.dto.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;

public interface CategoryDetailService {
    CategoryDetail createCategoryDetail(CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO);
}
