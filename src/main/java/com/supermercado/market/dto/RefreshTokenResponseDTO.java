package com.supermercado.market.dto;

import lombok.Data;

/* DTO para la respuesta del endpoint de refrescar token. */
@Data
public class RefreshTokenResponseDTO {
    private String message;
    private String jwt;
}
