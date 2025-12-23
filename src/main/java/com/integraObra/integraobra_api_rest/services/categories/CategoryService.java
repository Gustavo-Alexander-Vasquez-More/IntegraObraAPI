package com.integraObra.integraobra_api_rest.services.categories;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryRequestDTO;
import com.integraObra.integraobra_api_rest.dto.categories.UpdateCategoryRequestDTO;
import com.integraObra.integraobra_api_rest.models.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CreateCategoryRequestDTO createCategoryRequestDTO);

    boolean deleteCategory(Long id);

    List<Category> getAllCategories();

    Category updateCategory(UpdateCategoryRequestDTO updateCategoryRequestDTO, Long id);

}
