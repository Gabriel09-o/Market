package com.supermercado.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermercado.market.dto.ProductosProveedoresDTO;
import com.supermercado.market.security.Permission;
import com.supermercado.market.service.AuthorizationService;
import com.supermercado.market.service.ProductosProveedoresServices;

import jakarta.servlet.http.HttpServletRequest;

/* Controlador para gestionar las operaciones relacionadas con los productos y proveedores */
@RestController
@RequestMapping("/productos-proveedores")
public class ProductosProveedoresController {

    /* Servicio para gestionar las operaciones relacionadas con los productos y proveedores */
    @Autowired
    private ProductosProveedoresServices services;

    /* Servicio para gestionar la autorización de operaciones */
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Endpoint para obtener todos los productos y proveedores asociados.
     * Se requiere el permiso INVENTORY_READ para acceder a esta información.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return Una lista de productos y proveedores asociados.
     */
    @GetMapping
    public List<ProductosProveedoresDTO> getAll(HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_READ);
        return services.getAll();
    }

    /**
     * Endpoint para asociar un producto con un proveedor.
     * Se requiere el permiso INVENTORY_CREATE para realizar esta operación.
     * @param dto El DTO con los datos de la asociación.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return La asociación creada.
     */
    @PostMapping
    public ProductosProveedoresDTO asociar(@RequestBody ProductosProveedoresDTO dto, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_CREATE);
        return services.save(dto);
    }

    /**
     * Endpoint para eliminar una asociación entre un producto y un proveedor.
     * Se requiere el permiso INVENTORY_UPDATE para realizar esta operación.
     * @param id El ID de la asociación a eliminar.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     */
    @DeleteMapping("/{id}")
    public void eliminarAsociacion(@PathVariable Long id, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_UPDATE);
        services.delete(id);
    }
}
