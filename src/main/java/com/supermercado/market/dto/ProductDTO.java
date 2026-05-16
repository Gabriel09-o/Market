package com.supermercado.market.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) es un patrón de diseño utilizado para transferir datos entre diferentes
 *  capas de una aplicación,
 */
@Data
public class ProductDTO {

    private String nombre;
    private String codigoBarras;
    private double precio;
    private int stock;
    private boolean estado;
    private CategoryDTO category;

    public ProductDTO() {
    }

    /* Constructor for creating a ProductDTO instance. */
    public ProductDTO(String nombre, String codigoBarras,
            Double precio, Integer stock) {

        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.precio = precio;
        this.stock = stock;
    }

}