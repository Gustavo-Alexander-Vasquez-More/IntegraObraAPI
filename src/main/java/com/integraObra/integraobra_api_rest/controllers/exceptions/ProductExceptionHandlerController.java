package com.integraObra.integraobra_api_rest.controllers.exceptions;

import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandlerController {

    @ExceptionHandler(ProductExistException.class)
    public ResponseEntity<String> handleProductExistException(ProductExistException ex) {
        return ResponseEntity.status(HttpStatus.valueOf( 409)).body(ex.getMessage());
    }

}
