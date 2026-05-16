package com.supermercado.market.dto;

import lombok.Data;

/**
 * DTO para representar la respuesta de inicio de sesión.
 */
@Data
public class LoginResponseDTO extends MessageResponseDTO {
    private String jwt;
}
