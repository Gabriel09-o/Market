package com.supermercado.market.dto;

import org.springframework.stereotype.Component;

import com.supermercado.market.entity.DetalleVenta;

/**
 * Mapeador para convertir entre entidades y DTOs de detalle de venta.
 */
@Component
public class DetalleVentaMapper {

    /**
     * Convierte una entidad DetalleVenta a un DTO DetalleVentaDTO.
     * @param detalle La entidad DetalleVenta a convertir.
     * @return El DTO DetalleVentaDTO resultante.
     */
    public DetalleVentaDTO toDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalle.getId());
        
        if (detalle.getProducto() != null) {
            dto.setProductoId(detalle.getProducto().getId());
        }
        
        // Mapear los demás campos
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }

    public DetalleVenta toEntity(DetalleVentaDTO dto) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(dto.getId());
        
        /**
         * Mapear el producto.
         */
        if (dto.getProductoId() != null) {
            com.supermercado.market.entity.Product product = new com.supermercado.market.entity.Product();
            product.setId(dto.getProductoId());
            detalle.setProducto(product);
        }
        // Mapear los demás campos
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());
        return detalle;
    }

}
