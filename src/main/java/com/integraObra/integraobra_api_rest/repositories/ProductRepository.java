package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Buscar productos por nombre o SKU que contengan el término de búsqueda (ignorar mayúsculas/minúsculas)
    Page<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name, String sku, Pageable pageable);

    // Verificar si existe un producto con un SKU específico
    boolean existsBySku(String sku);

    // Obtener productos por categoria paginados
    @Query("SELECT cd.product FROM CategoryDetail cd WHERE cd.category.id = :categoryId")
    Page<Product> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    //Obtener productos por categoria paginados y por termino de busqueda
    @Query("SELECT cd.product FROM CategoryDetail cd WHERE cd.category.id = :categoryId AND " +
            "(LOWER(cd.product.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(cd.product.sku) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Product> findAllByCategoryIdAndSearchTerm(@Param("categoryId") Long categoryId,
                                               @Param("searchTerm") String searchTerm,
                                               Pageable pageable);

    //TODOS LOS PRODUCTOS FILTRADOS POR CATEGORIA SINO NO HAY NINGUNO ARROJAR TODOS LOS PRODUCTOS
    @Query("SELECT cd.product FROM CategoryDetail cd WHERE cd.category.id = :categoryId")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);
    //Repository para productos en renta

    //obtener todos los productos que sean isForRent = true y filtrados por termino de busqueda (solo por nombre)
    @Query("SELECT p FROM Product p WHERE p.isForRent = true AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.sku) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Product> findAllRentProductsBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    //obtener todos los productos que sean isForRent = true
    @Query("SELECT p FROM Product p WHERE p.isForRent = true")
    Page<Product> findAllRentProducts(Pageable pageable);

    // Obtener productos en renta por categoria paginados
    @Query("SELECT cd.product FROM CategoryDetail cd WHERE cd.category.id = :categoryId AND cd.product.isForRent = true")
    Page<Product> findAllRentProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    //Obtener productos en renta por categoria paginados y por termino de busqueda
    @Query("SELECT cd.product FROM CategoryDetail cd WHERE cd.category.id = :categoryId AND cd.product.isForRent = true AND " +
            "(LOWER(cd.product.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(cd.product.sku) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Product> findAllRentProductsByCategoryIdAndSearchTerm(@Param("categoryId") Long categoryId,
                                               @Param("searchTerm") String searchTerm,
                                               Pageable pageable);

}
