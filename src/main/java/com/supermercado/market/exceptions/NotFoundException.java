package com.supermercado.market.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* Excepción que se lanza cuando no se encuentra un recurso solicitado. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String resourceName, String id) {
        super(String.format("Element not found: %s - ID: %s", resourceName, id));
    }

}
