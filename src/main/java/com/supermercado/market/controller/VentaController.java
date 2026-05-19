package com.supermercado.market.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermercado.market.dto.VentaDTO;
import com.supermercado.market.security.Permission;
import com.supermercado.market.service.AuthorizationService;
import com.supermercado.market.service.VentaService;

import jakarta.servlet.http.HttpServletRequest;

/* Controlador para gestionar las operaciones relacionadas con las ventas */
@RestController
@RequestMapping("/ventas")
public class VentaController {

    /* Servicio para gestionar las operaciones relacionadas con las ventas */
    @Autowired
    private VentaService ventaService;

    /* Servicio para gestionar la autorización de operaciones */
    @Autowired
    private AuthorizationService authorizationService;

    /* Endpoint para obtener todas las ventas */
    @GetMapping
    public List<VentaDTO> getAll(HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.SALES_READ);
        return ventaService.getAllVentas();
    }

    /* Endpoint para crear una nueva venta */
    @PostMapping
    public VentaDTO create(@RequestBody VentaDTO ventaDTO, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.SALES_CREATE);
        return ventaService.procesarVenta(ventaDTO);
    }

    /* Endpoint para obtener una venta por su ID */
    @GetMapping("/{id}")
    public VentaDTO getById(@PathVariable Long id, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.SALES_READ);
        return ventaService.getVenta(id);
    }

    /* Endpoint para eliminar una venta */
    @DeleteMapping("/{id}")
    public VentaDTO delete(@PathVariable Long id, HttpServletRequest request) {

        authorizationService.requirePermission(request, Permission.SALES_READ);
        return ventaService.deleteVenta(id);
    }

    /* Endpoint para obtener ventas por rango de fechas */
    @GetMapping("/rango")
    public List<VentaDTO> getByRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.SALES_READ);
        return ventaService.listarPorRangoFecha(inicio, fin);
    }
}
