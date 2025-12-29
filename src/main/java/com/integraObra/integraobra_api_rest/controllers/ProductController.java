package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.products.*;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.services.products.ProductServiceGeneralCrudJPA;
import com.integraObra.integraobra_api_rest.services.products.ProductServicePaginatedPanelJPA;
import com.integraObra.integraobra_api_rest.services.products.RentProductServiceCatalogoJPA;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductServiceGeneralCrudJPA productServiceGeneralCrud;
  private final ProductServicePaginatedPanelJPA productServicePaginatedPanel;
  private final RentProductServiceCatalogoJPA rentProductServiceCatalogoJPA;

    public ProductController(ProductServiceGeneralCrudJPA productServiceGeneralCrud, ProductServicePaginatedPanelJPA productServicePaginatedPanel, RentProductServiceCatalogoJPA rentProductServiceCatalogoJPA) {
        this.productServicePaginatedPanel = productServicePaginatedPanel;
        this.productServiceGeneralCrud = productServiceGeneralCrud;
        this.rentProductServiceCatalogoJPA = rentProductServiceCatalogoJPA;
    }

    //Endpoints para CRUD general de productos

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequestDTO createProductRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productServiceGeneralCrud.createProduct(createProductRequestDTO));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(productServiceGeneralCrud.getProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(productServiceGeneralCrud.deleteProductById(productId));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @RequestBody UpdateRequestProductDTO updateRequestProductDTO, @PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(productServiceGeneralCrud.updateProduct(updateRequestProductDTO, productId));
    }

    //Endopoint para obtener productos paginados para el panel de gestion de productos
    @GetMapping("/search")
    public ResponseEntity<Page<ProductCardPanelResponseDTO>> getProductsPaginated(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        return ResponseEntity.ok(productServicePaginatedPanel.getProductsPaginatedWithFilterInPanel(searchTerm, categoryId ,pageable));
    }

    //Endpoint para obtener productos en renta paginados para el catalogo de productos en renta
    @GetMapping("/searchRent")
    public ResponseEntity<Page<RentProductCardRequestDTO>> getRentProductsPaginated(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        return ResponseEntity.ok(rentProductServiceCatalogoJPA.getRentProductsPaginatedWithFilterInCatalog(searchTerm, categoryId ,pageable));
    }

    @GetMapping("/by-category-or-all/{categoryId}")
    public ResponseEntity<List<RentProductCardRequestDTO>> getAllProductsByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(productServiceGeneralCrud.getAllProductsByCategoryId(categoryId));
    }

}
