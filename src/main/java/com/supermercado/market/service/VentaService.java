package com.supermercado.market.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.DetalleVentaDTO;
import com.supermercado.market.dto.VentaDTO;
import com.supermercado.market.dto.VentaMapper;
import com.supermercado.market.entity.DetalleVenta;
import com.supermercado.market.entity.Empleado;
import com.supermercado.market.entity.Product;
import com.supermercado.market.entity.Venta;
import com.supermercado.market.exceptions.BadRequestException;
import com.supermercado.market.exceptions.NotFoundException;
import com.supermercado.market.repository.EmpleadoRepository;
import com.supermercado.market.repository.ProductRepository;
import com.supermercado.market.repository.VentaRepository;

/* Servicio para la gestión de ventas. */
@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private VentaMapper ventaMapper;

    /* Lista todas las ventas dentro de un rango de fechas */
    public List<VentaDTO> listarPorRangoFecha(LocalDate inicio, LocalDate fin) {
        return ventaRepository.findByFechaBetween(inicio, fin)
                .stream()
                .map(venta -> ventaMapper.toDTO(venta))
                .collect(Collectors.toList());
    }

    /* Lista todas las ventas */
    public List<VentaDTO> getAllVentas() {
        return ventaRepository.findAll().stream()
                .map(venta -> ventaMapper.toDTO(venta))
                .collect(Collectors.toList());
    }

    /* Obtiene una venta por su ID */
    public VentaDTO getVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada", id.toString()));
        return ventaMapper.toDTO(venta);
    }

    /* Elimina una venta */
    public VentaDTO deleteVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada", id.toString()));
        ventaRepository.delete(venta);
        return ventaMapper.toDTO(venta);
    }

    /* Procesa una nueva venta */
    public VentaDTO procesarVenta(VentaDTO request) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        
        if (request.getEmpleadoId() != null) {
            Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new NotFoundException("Empleado no existe", request.getEmpleadoId().toString()));
            venta.setEmpleado(empleado);
        } else {
            throw new BadRequestException("El ID del empleado es requerido");
        }
        
        /* Procesar detalles de la venta */
        double acumuladoSubtotal = 0;
        List<DetalleVenta> detallesList = new ArrayList<>();

        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new BadRequestException("La venta debe tener al menos un detalle");
        }

        /* Validar stock y calcular subtotales */
        for (DetalleVentaDTO item : request.getDetalles()) {
            Product product = productRepository.findById(item.getProductoId())
                .orElseThrow(() -> new NotFoundException("Producto no existe", item.getProductoId().toString()));

            if (product.getStock() < item.getCantidad()) {
                throw new BadRequestException("Stock insuficiente para: " + product.getNombre());
            }

            product.setStock(product.getStock() - item.getCantidad());
            productRepository.save(product);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(product);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(product.getPrecio());
            
            double subtotalDetalle = detalle.getPrecioUnitario() * detalle.getCantidad();
            detalle.setSubtotal(subtotalDetalle);
            detalle.setVenta(venta);
            
            detallesList.add(detalle);
            acumuladoSubtotal += subtotalDetalle;
        }

        venta.setSubtotal(acumuladoSubtotal);
        venta.setIva(acumuladoSubtotal * 0.19); 
        venta.setTotal(venta.getSubtotal() + venta.getIva());
        venta.setDetalles(detallesList);

        return ventaMapper.toDTO(ventaRepository.save(venta));
    }
}