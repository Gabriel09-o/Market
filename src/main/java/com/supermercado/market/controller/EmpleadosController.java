package com.supermercado.market.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermercado.market.dto.EmpleadoDTO;
import com.supermercado.market.entity.Cargo;
import com.supermercado.market.service.EmpleadosServices;

import jakarta.validation.Valid;

/**
 * Controlador para gestionar las operaciones relacionadas con los empleados
 */
@RestController
@RequestMapping("/empleados")
public class EmpleadosController {

    /**
     * Servicio para gestionar las operaciones relacionadas con los empleados
     */
    @Autowired
    private EmpleadosServices empleadosServices;

    /**
     * Obtiene todos los empleados
     * @return Lista de empleados
     */
    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<EmpleadoDTO>> listarPorCargo(
            @PathVariable Cargo cargo) {
        return ResponseEntity.ok(empleadosServices.listarPorCargo(cargo));
    }

    /**
     * Obtiene los empleados por un rango de fechas de ingreso
     * @param inicio Fecha de inicio del rango
     * @param fin Fecha de fin del rango
     * @return Lista de empleados dentro del rango de fechas
     */
    @GetMapping("/fecha-ingreso")
    public ResponseEntity<List<EmpleadoDTO>> listarPorRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(empleadosServices.listarPorRangoFecha(inicio, fin));
    }

    /**
     * Crea un nuevo empleado
     * @param empleadoDTO DTO del empleado a crear
     * @return Empleado creado
     */
    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {

        EmpleadoDTO empleadoDTO1 = empleadosServices.saveEmpleado(empleadoDTO);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empleadoDTO1);
    }
}
