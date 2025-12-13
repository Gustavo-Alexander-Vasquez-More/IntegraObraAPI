package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import com.integraObra.integraobra_api_rest.services.CategoryDetailServiceJPA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/category-details")
public class CategoryDetailController {
    private final CategoryDetailServiceJPA categoryDetailServiceJPA;
    public CategoryDetailController(CategoryDetailServiceJPA categoryDetailServiceJPA) {
        this.categoryDetailServiceJPA = categoryDetailServiceJPA;
    }

    @PostMapping
    public ResponseEntity<CategoryDetail> createCategoryDetail(@RequestBody CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO) {
        return ResponseEntity.status(201).body(categoryDetailServiceJPA.createCategoryDetail(createCategoryDetailRequestDTO));
    }
}
