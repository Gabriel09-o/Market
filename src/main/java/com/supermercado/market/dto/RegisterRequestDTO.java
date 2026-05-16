package com.supermercado.market.dto;

import lombok.Data;

/* DTO para la solicitud de registro de un nuevo usuario. */
@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private Long rol;
}
