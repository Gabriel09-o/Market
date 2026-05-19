package com.supermercado.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermercado.market.dto.ProductDTO;
import com.supermercado.market.dto.ProveedorDTO;
import com.supermercado.market.security.Permission;
import com.supermercado.market.service.AuthorizationService;
import com.supermercado.market.service.ProveedoresServices;

import jakarta.servlet.http.HttpServletRequest;

/* Controlador para gestionar las operaciones relacionadas con los proveedores */
@RestController
@RequestMapping("/proveedores")
public class ProveedoresController {

    /* Servicio para gestionar las operaciones relacionadas con los proveedores */
    @Autowired
    private ProveedoresServices proveedoresServices;

    /* Servicio para gestionar la autorización de operaciones */
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Endpoint para obtener todos los proveedores.
     * Se requiere el permiso INVENTORY_READ para acceder a esta información.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return Una lista de proveedores.
     */
    @GetMapping
    public List<ProveedorDTO> getAll(HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_READ);
        return proveedoresServices.getAllProveedores();
    }

    /**
     * Endpoint para crear un nuevo proveedor.
     * Se requiere el permiso INVENTORY_CREATE para realizar esta operación.
     * @param proveedorDTO El DTO con los datos del proveedor a crear.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El proveedor creado.
     */
    @PostMapping
    public ProveedorDTO create(@RequestBody ProveedorDTO proveedorDTO, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_CREATE);
        return proveedoresServices.saveProveedor(proveedorDTO);
    }

    /**
     * Endpoint para actualizar un proveedor existente.
     * Se requiere el permiso INVENTORY_UPDATE para realizar esta operación.
     * @param id El ID del proveedor a actualizar.
     * @param proveedorDTO El DTO con los datos actualizados del proveedor.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El proveedor actualizado.
     */
    @PutMapping("/{id}")
    public ProveedorDTO update(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_UPDATE);
        return proveedoresServices.updateProveedor(id, proveedorDTO);
    }

    /**
     * Endpoint para eliminar un proveedor.
     * Se requiere el permiso INVENTORY_UPDATE para realizar esta operación.
     * @param id El ID del proveedor a eliminar.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El proveedor eliminado.
     */
    @DeleteMapping("/{id}")
    public ProveedorDTO delete(@PathVariable Long id, HttpServletRequest request) {

        authorizationService.requirePermission(request, Permission.INVENTORY_UPDATE);
        return proveedoresServices.deleteProveedor(id);
    }

    /**
     * Endpoint para obtener todos los productos asociados a un proveedor.
     * Se requiere el permiso INVENTORY_READ para acceder a esta información.
     * @param id El ID del proveedor.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return Una lista de productos asociados al proveedor.
     */
    @GetMapping("/{id}/productos")
    public List<ProductDTO> getProductos(@PathVariable Long id, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.INVENTORY_READ);
        return proveedoresServices.getProductosByProveedor(id);
    }
}
