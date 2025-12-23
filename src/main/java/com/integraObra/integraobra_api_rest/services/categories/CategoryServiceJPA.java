package com.integraObra.integraobra_api_rest.services.categories;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryRequestDTO;
import com.integraObra.integraobra_api_rest.dto.categories.UpdateCategoryRequestDTO;
import com.integraObra.integraobra_api_rest.exceptions.CategoryExistException;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceJPA implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceJPA(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Crear nueva categoria con verificacion de existencia para evitar duplicados
    @Override
    public Category createCategory(CreateCategoryRequestDTO createCategoryRequestDTO) {
        boolean response=categoryRepository.existsByName(createCategoryRequestDTO.getName());
        if(response){
            throw new CategoryExistException("La categoría con nombre " + createCategoryRequestDTO.getName() + " ya existe. Por favor, elija otro nombre.");
        }else{
            Category category=new Category(createCategoryRequestDTO.getName().toUpperCase().trim());
            return categoryRepository.save(category);
        }
    }

    //Eliminar categoria por nombre con manejo de excepcion si no existe
    @Override
    public boolean deleteCategory(Long id) {
        boolean response=categoryRepository.existsById(id);
        if(!response){
            throw new NotFoundException( "La categoría no existe. No se puede eliminar una categoría inexistente.");
        }else{
            categoryRepository.deleteById((id));
            return true;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(UpdateCategoryRequestDTO updateCategoryRequestDTO, Long id) {
        Category category=categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La categoría con ID " + id + " no existe. No se puede actualizar una categoría inexistente."));
        category.setName(updateCategoryRequestDTO.getName().toUpperCase().trim());
        return categoryRepository.save(category);
    }
}
