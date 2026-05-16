package com.supermercado.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.DetalleVentaDTO;
import com.supermercado.market.dto.DetalleVentaMapper;
import com.supermercado.market.entity.DetalleVenta;
import com.supermercado.market.entity.Product;
import com.supermercado.market.exceptions.NotFoundException;
import com.supermercado.market.repository.DetalleVentaRepository;
import com.supermercado.market.repository.ProductRepository;

/* Servicio para la gestión de detalles de venta. */
@Service
public class DetalleVentaServices {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    public DetalleVentaDTO registrarDetalle(DetalleVentaDTO dto) {
        Product product = productsRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new NotFoundException("Producto", dto.getProductoId().toString()));

        DetalleVenta detalle = detalleVentaMapper.toEntity(dto);
        detalle.setProducto(product);

        detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());

        return detalleVentaMapper.toDTO(detalleVentaRepository.save(detalle));
    }
}
