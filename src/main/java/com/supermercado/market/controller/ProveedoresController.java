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
import com.supermercado.market.service.ProveedoresServices;

/**
 * Controlador para gestionar las operaciones relacionadas con los proveedores.
 */
@RestController
@RequestMapping("/proveedores")
public class ProveedoresController {

    /**
     * Servicio para gestionar las operaciones relacionadas con los proveedores.
     */
    @Autowired
    private ProveedoresServices proveedoresServices;

    /**
     * Obtiene todos los proveedores.
     * @return Lista de proveedores.
     */
    @GetMapping
    public List<ProveedorDTO> getAll() {
        return proveedoresServices.getAllProveedores();
    }

    /**
     * Crea un nuevo proveedor.
     * @param proveedorDTO DTO con los datos del proveedor a crear.
     * @return Proveedor creado.
     */
    @PostMapping
    public ProveedorDTO create(@RequestBody ProveedorDTO proveedorDTO) {
        return proveedoresServices.saveProveedor(proveedorDTO);
    }

    /**
     * Actualiza un proveedor existente.
     * @param id ID del proveedor a actualizar.
     * @param proveedorDTO DTO con los datos del proveedor a actualizar.
     * @return Proveedor actualizado.
     */
    @PutMapping("/{id}")
    public ProveedorDTO update(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        return proveedoresServices.updateProveedor(id, proveedorDTO);
    }

    /**
     * Elimina un proveedor.
     * @param id ID del proveedor a eliminar.
     * @return Proveedor eliminado.
     */
    @DeleteMapping("/{id}")
    public ProveedorDTO delete(@PathVariable Long id) {
        return proveedoresServices.deleteProveedor(id);
    }

    /**
     * Obtiene los productos asociados a un proveedor.
     * @param id ID del proveedor.
     * @return Lista de productos.
     */
    @GetMapping("/{id}/productos")
    public List<ProductDTO> getProductos(@PathVariable Long id) {
        return proveedoresServices.getProductosByProveedor(id);
    }
}
