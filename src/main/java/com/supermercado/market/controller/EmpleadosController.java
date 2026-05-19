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
import com.supermercado.market.security.Permission;
import com.supermercado.market.service.AuthorizationService;
import com.supermercado.market.service.EmpleadosServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/* Controlador para gestionar las operaciones relacionadas con los empleados */
@RestController
@RequestMapping("/empleados")
public class EmpleadosController {

    /* Servicio para gestionar las operaciones relacionadas con los empleados */
    @Autowired
    private EmpleadosServices empleadosServices;

    /* Servicio para gestionar la autorización de operaciones */
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Endpoint para listar empleados por cargo.
     * Se requiere el permiso USERS_READ para acceder a esta información.
     * @param cargo El cargo por el cual se desea filtrar los empleados.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return Una lista de empleados que corresponden al cargo especificado.
     */
    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<EmpleadoDTO>> listarPorCargo(@PathVariable Cargo cargo,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_READ);
        return ResponseEntity.ok(empleadosServices.listarPorCargo(cargo));
    }

    /**
     * Endpoint para listar empleados por rango de fechas.
     * Se requiere el permiso USERS_READ para acceder a esta información.
     * @param inicio La fecha de inicio del rango.
     * @param fin La fecha de fin del rango.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return Una lista de empleados que corresponden al rango de fechas especificado.
     */
    @GetMapping("/fecha-ingreso")
    public ResponseEntity<List<EmpleadoDTO>> listarPorRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_READ);
        return ResponseEntity.ok(empleadosServices.listarPorRangoFecha(inicio, fin));
    }

    /**
     * Endpoint para crear un nuevo empleado.
     * Se requiere el permiso USERS_CREATE para realizar esta operación.
     * @param empleadoDTO El DTO con los datos del empleado a crear.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El empleado creado.
     */
    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_CREATE);
        EmpleadoDTO empleadoDTO1 = empleadosServices.saveEmpleado(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoDTO1);
    }
}
