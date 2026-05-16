package com.supermercado.market.dto;

import lombok.Data;

/* DTO para la solicitud de creación de un nuevo usuario. */
@Data
public class UserRequestDTO {
    private String username;

    private String password;

    private Long rolId;
}