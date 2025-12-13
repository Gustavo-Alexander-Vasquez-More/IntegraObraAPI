package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.services.CategoryServiceJPA;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryServiceJPA categoryServiceJPA;

    public CategoryController(CategoryServiceJPA categoryServiceJPA) {
        this.categoryServiceJPA = categoryServiceJPA;
    }

    @PostMapping("/{name}")
    public ResponseEntity<Category> createProduct(@Valid @PathVariable String name) {
        return ResponseEntity.status(201).body(categoryServiceJPA.createCategory(name));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryServiceJPA.deleteCategoryByName(name));
    }
}
