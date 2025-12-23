package com.integraObra.integraobra_api_rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
    private String title;
    private String message;
    private int status;
}
