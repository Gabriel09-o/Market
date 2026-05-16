package com.supermercado.market.dto;

import java.util.List;

import lombok.Data;

/**
 * DTO para representar un proveedor.
 */
@Data
public class ProveedorDTO {

    private String nit;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private List<ProductosProveedoresDTO> productosProveedores;

    public ProveedorDTO() {
    }

    /* Constructor para la creación de una instancia de ProveedorDTO. */
    public ProveedorDTO(String nit, String nombre, String telefono, String email, String direccion) {
        
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }
}