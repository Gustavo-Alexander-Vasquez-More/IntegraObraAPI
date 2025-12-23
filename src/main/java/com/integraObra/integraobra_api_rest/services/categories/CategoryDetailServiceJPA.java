package com.integraObra.integraobra_api_rest.services.categories;

import com.integraObra.integraobra_api_rest.dto.categories.CreateCategoryDetailRequestDTO;
import com.integraObra.integraobra_api_rest.exceptions.CategoryExistException;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Category;
import com.integraObra.integraobra_api_rest.models.CategoryDetail;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.repositories.CategoryDetailRepository;
import com.integraObra.integraobra_api_rest.repositories.CategoryRepository;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryDetailServiceJPA implements CategoryDetailService {
    private final CategoryDetailRepository categoryDetailRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryDetailServiceJPA(CategoryDetailRepository categoryDetailRepository,
                                    CategoryRepository categoryRepository,
                                    ProductRepository productRepository) {
        this.categoryDetailRepository = categoryDetailRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    //Metodo para crear un detalle de categoria para un producto dado
    @Override
    public CategoryDetail createCategoryDetail(CreateCategoryDetailRequestDTO createCategoryDetailRequestDTO) {
       //primero verificaremos si el detalle de categoria ya existe para el producto dado
        boolean exists = categoryDetailRepository.existsByCategoryIdAndProductId(
                createCategoryDetailRequestDTO.getCategoryId(),
                createCategoryDetailRequestDTO.getProductId()
        );
        if(exists){
            throw new CategoryExistException("El detalle de categoría para el producto con ID " + createCategoryDetailRequestDTO.getProductId() +
                    " y la categoría con ID " + createCategoryDetailRequestDTO.getCategoryId() + " ya existe.");
        }

        //Buscar entidades Category y Product (CategoryDetail espera objetos)
        Category category = categoryRepository.findById(createCategoryDetailRequestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría con ID " + createCategoryDetailRequestDTO.getCategoryId() + " no encontrada."));

        Product product = productRepository.findById(createCategoryDetailRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto con ID " + createCategoryDetailRequestDTO.getProductId() + " no encontrado."));

        //Si no existe, procedemos a crear el detalle de categoria
        CategoryDetail categoryDetail=new CategoryDetail(
                category,
                product
        );
        return categoryDetailRepository.save(categoryDetail);
    }
}
