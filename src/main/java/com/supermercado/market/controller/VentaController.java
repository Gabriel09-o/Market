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
import com.supermercado.market.service.VentaService;

/**
 * Controlador para gestionar las operaciones relacionadas con las ventas.
 */
@RestController
@RequestMapping("/ventas")
public class VentaController {

    /**
     * Servicio para gestionar las operaciones relacionadas con las ventas.
     */
    @Autowired
    private VentaService ventaService;

    /**
     * Obtiene todas las ventas.
     * @return Lista de ventas.
     */
    @GetMapping
    public List<VentaDTO> getAll() {
        return ventaService.getAllVentas();
    }

    /**
     * Crea una nueva venta.
     * @param ventaDTO DTO con los datos de la venta a crear.
     * @return Venta creada.
     */
    @PostMapping
    public VentaDTO create(@RequestBody VentaDTO ventaDTO) {
        return ventaService.procesarVenta(ventaDTO);
    }

    /**
     * Obtiene una venta por su ID.
     * @param id ID de la venta.
     * @return Venta encontrada.
     */
    @GetMapping("/{id}")
    public VentaDTO getById(@PathVariable Long id) {
        return ventaService.getVenta(id);
    }

    /**
     * Elimina una venta.
     * @param id ID de la venta a eliminar.
     * @return Venta eliminada.
     */
    @DeleteMapping("/{id}")
    public VentaDTO delete(@PathVariable Long id) {
        return ventaService.deleteVenta(id);
    }

    /**
     * Obtiene las ventas por rango de fechas.
     * @param inicio Fecha de inicio.
     * @param fin Fecha de fin.
     * @return Lista de ventas.
     */
    @GetMapping("/rango")
    public List<VentaDTO> getByRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ventaService.listarPorRangoFecha(inicio, fin);
    }
}
