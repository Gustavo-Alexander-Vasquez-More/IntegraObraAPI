package com.integraObra.integraobra_api_rest.controllers.exceptions;

import com.integraObra.integraobra_api_rest.dto.ErrorResponse;
import com.integraObra.integraobra_api_rest.exceptions.ProductExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandlerController {

    @ExceptionHandler(ProductExistException.class)
    public ResponseEntity<ErrorResponse> handleProductExistException(ProductExistException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTitle(HttpStatus.CONFLICT.getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
