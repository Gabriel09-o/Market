package com.supermercado.market.dto;

import java.util.List;

import lombok.Data;

/* DTO para la solicitud de creación de una nueva venta. */
@Data
public class VentaDTO {

    private Long id;
    private Long empleadoId;
    private double subtotal;
    private double iva;
    private double total;
    private List<DetalleVentaDTO> detalles;

    public VentaDTO() {
    }

    /* Constructor para la creación de una instancia de VentaDTO. */
    public VentaDTO(Long id, Long empleadoId, double subtotal,
            double iva, double total,
            List<DetalleVentaDTO> detalles) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.detalles = detalles;
    }

}