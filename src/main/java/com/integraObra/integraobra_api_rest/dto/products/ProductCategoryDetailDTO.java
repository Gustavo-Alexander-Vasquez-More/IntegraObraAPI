package com.integraObra.integraobra_api_rest.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductCategoryDetailDTO { //Esto es para transferir datos de detalles de categoria de productos, es decir los subtipos de categorias
    private Long categoryDetailId;
    private String name;
    private Long categoryId;
}

