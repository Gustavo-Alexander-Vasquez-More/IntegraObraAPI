package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.models.Category;

public interface CategoryService {

    Category createCategory(String name);
    String deleteCategoryByName(String name);
}
