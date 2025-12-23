package com.integraObra.integraobra_api_rest.exceptions;

public class DeletionConflictException extends RuntimeException {
    public DeletionConflictException(String message) {
        super(message);
    }
}

