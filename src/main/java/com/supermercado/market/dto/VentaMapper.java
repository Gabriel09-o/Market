package com.supermercado.market.dto;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.supermercado.market.entity.Empleado;
import com.supermercado.market.entity.Venta;

/* Mapper para convertir entre entidades y DTOs de Venta. */
@Component
public class VentaMapper {

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    public VentaDTO toDTO(Venta venta) {
        if (venta == null) return null;
        
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        
        if (venta.getEmpleado() != null) {
            dto.setEmpleadoId(venta.getEmpleado().getId());
        }
        
        dto.setSubtotal(venta.getSubtotal());
        dto.setIva(venta.getIva());
        dto.setTotal(venta.getTotal());
        
        if (venta.getDetalles() != null) {
            dto.setDetalles(venta.getDetalles().stream()
                .map(detalle -> detalleVentaMapper.toDTO(detalle))
                .collect(Collectors.toList()));
        } else {
            dto.setDetalles(new ArrayList<>());
        }
        
        return dto;
    }

    /* Método para convertir un DTO de Venta en una entidad de Venta. */
    public Venta toEntity(VentaDTO dto) {
        if (dto == null) return null;
        
        Venta venta = new Venta();
        venta.setId(dto.getId());
        
        if (dto.getEmpleadoId() != null) {
            Empleado empleado = new Empleado();
            empleado.setId(dto.getEmpleadoId());
            venta.setEmpleado(empleado);
        }
        
        venta.setSubtotal(dto.getSubtotal());
        venta.setIva(dto.getIva());
        venta.setTotal(dto.getTotal());
        
        if (dto.getDetalles() != null) {
            venta.setDetalles(dto.getDetalles().stream()
                .map(detalleDto -> detalleVentaMapper.toEntity(detalleDto))
                .collect(Collectors.toList()));
        } else {
            venta.setDetalles(new ArrayList<>());
        }
        
        return venta;
    }
}
