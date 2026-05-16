package com.supermercado.market.dto;

import lombok.Data;

/**
 * DTO para representar la relación entre productos y proveedores.
 */
@Data
public class ProductosProveedoresDTO {

    private Long id;
    private Long productoId;
    private Long proveedorId;

    public ProductosProveedoresDTO() {
    }

    /* Constructor for creating a ProductosProveedoresDTO instance. */
    public ProductosProveedoresDTO(Long id, Long productoId, Long proveedorId) {
        this.id = id;
        this.productoId = productoId;
        this.proveedorId = proveedorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }
}
