package com.supermercado.market.dto;

import lombok.Data;

/**
 * DTO para representar un detalle de venta.
 */
@Data
public class DetalleVentaDTO {

    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public DetalleVentaDTO() {
    }

    /**
     * Constructor para crear un detalle de venta.
     * @param id ID del detalle de venta.
     * @param productoId ID del producto.
     * @param cantidad Cantidad de productos.
     * @param precioUnitario Precio unitario.
     * @param subtotal Subtotal.
     */
    public DetalleVentaDTO(Long id, Long productoId,
            Integer cantidad, Double precioUnitario,
            Double subtotal) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}