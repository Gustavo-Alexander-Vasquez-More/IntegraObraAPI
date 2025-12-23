package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryRequestDTO;
import com.integraObra.integraobra_api_rest.dto.categories.UpdateCategoryRequestDTO;
import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.services.categories.CategoryServiceJPA;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryServiceJPA categoryServiceJPA;

    public CategoryController(CategoryServiceJPA categoryServiceJPA) {
        this.categoryServiceJPA = categoryServiceJPA;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
        return ResponseEntity.ok(categoryServiceJPA.createCategory(createCategoryRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategoryByName(@PathVariable Long id) {
        return ResponseEntity.ok(categoryServiceJPA.deleteCategory(id));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryServiceJPA.getAllCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody UpdateCategoryRequestDTO updateCategoryRequestDTO, @PathVariable Long id) {
        return ResponseEntity.ok(categoryServiceJPA.updateCategory(updateCategoryRequestDTO, id));
    }
}
