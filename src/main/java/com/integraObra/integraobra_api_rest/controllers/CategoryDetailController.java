package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import com.integraObra.integraobra_api_rest.services.categories.CategoryDetailServiceJPA;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category-details")
public class CategoryDetailController {
    private final CategoryDetailServiceJPA categoryDetailServiceJPA;
    public CategoryDetailController(CategoryDetailServiceJPA categoryDetailServiceJPA) {
        this.categoryDetailServiceJPA = categoryDetailServiceJPA;
    }

    @PostMapping
    public ResponseEntity<CategoryDetail> createCategoryDetail(@Valid @RequestBody CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO) {
        return ResponseEntity.status(201).body(categoryDetailServiceJPA.createCategoryDetail(createCategoryDetailRequestDTO));
    }

    @DeleteMapping("/${id}")
    public ResponseEntity<Boolean> deleteCategoryDetail(@PathVariable Long id) {
        return ResponseEntity.ok(categoryDetailServiceJPA.deleteCategoryDetail(id));
    }

}
