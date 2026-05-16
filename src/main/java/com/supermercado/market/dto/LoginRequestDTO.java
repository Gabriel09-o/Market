package com.supermercado.market.dto;

import lombok.Data;

/**
 * DTO para representar la solicitud de inicio de sesión.
 */
@Data
public class LoginRequestDTO {
    private String username;

    private String password;
}
