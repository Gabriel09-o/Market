package com.supermercado.market.dto;

import lombok.Data;

/* DTO para la respuesta del endpoint de obtener un usuario. */
@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private Long rolId;
}