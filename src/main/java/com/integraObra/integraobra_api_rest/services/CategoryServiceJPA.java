package com.integraObra.integraobra_api_rest.services;

import com.integraObra.integraobra_api_rest.exceptions.CategoryExistException;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NotBoundException;

@Service
public class CategoryServiceJPA implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceJPA(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    //Crear nueva categoria con verificacion de existencia para evitar duplicados
    public Category createCategory(String name) {
        boolean response=categoryRepository.existsByName(name);
        if(response){
            throw new CategoryExistException("La categoría con nombre " + name + " ya existe. Por favor, elija otro nombre.");
        }else{
            Category category=new Category(name.toUpperCase().trim());
            return categoryRepository.save(category);
        }
    }

    @Override
    //Eliminar categoria por nombre con manejo de excepcion si no existe
    public String deleteCategoryByName(String name) {
        boolean response=categoryRepository.existsByName(name);
        if(!response){
            throw new NotFoundException( "La categoría con nombre " + name + " no existe. No se puede eliminar una categoría inexistente.");
        }else{
            categoryRepository.deleteByName((name));
            return "Categoría " + name + " eliminada correctamente.";
        }
    }
}
