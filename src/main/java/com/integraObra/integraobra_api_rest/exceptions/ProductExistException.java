package com.integraObra.integraobra_api_rest.exceptions;

public class ProductExistException extends RuntimeException {
    public ProductExistException(String message) {
        super(message);
    }
}
