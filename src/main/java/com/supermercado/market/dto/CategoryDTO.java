package com.supermercado.market.dto;

import lombok.Data;

/**
 * DTO para representar una categoría.
 */
@Data
public class CategoryDTO {

    private String nombre;
    private String descripcion;

    public CategoryDTO() {
    }

    public CategoryDTO(String nombre, String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
    }

}