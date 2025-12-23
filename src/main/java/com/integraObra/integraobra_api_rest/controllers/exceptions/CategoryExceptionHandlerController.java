package com.integraObra.integraobra_api_rest.controllers.exceptions;

import com.integraObra.integraobra_api_rest.dto.ErrorResponse;
import com.integraObra.integraobra_api_rest.exceptions.CategoryExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryExceptionHandlerController {

    @ExceptionHandler(CategoryExistException.class)
    public ResponseEntity<ErrorResponse> handleCategoryExistException(CategoryExistException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTitle(HttpStatus.CONFLICT.getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
