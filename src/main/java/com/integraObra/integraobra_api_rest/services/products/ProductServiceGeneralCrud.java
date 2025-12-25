package com.integraObra.integraobra_api_rest.services.products;

import com.integraObra.integraobra_api_rest.dto.products.CreateProductRequestDTO;
import com.integraObra.integraobra_api_rest.dto.products.ProductResponseDTO;
import com.integraObra.integraobra_api_rest.dto.products.UpdateRequestProductDTO;
import com.integraObra.integraobra_api_rest.models.Product;

//SERVICIO GENERAL DE CRUD DE PRODUCTOS-SON METODOS BASICOS
public interface ProductServiceGeneralCrud {

    //CREAR PRODUCTO
    Product createProduct(CreateProductRequestDTO createProductRequestDTO);

    //OBTENER PRODUCTO POR ID
    ProductResponseDTO getProductById(Long productId);

    //ELIMINAR PRODUCTO POR ID
    boolean deleteProductById(Long productId);

    //ACTUALIZAR PRODUCTO POR ID
    ProductResponseDTO updateProduct(UpdateRequestProductDTO updateRequestProductDTO, Long productId);
}
