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
import com.supermercado.market.service.ProductosProveedoresServices;

/**
 * Controlador para gestionar las asociaciones entre productos y proveedores.
 */
@RestController
@RequestMapping("/productos-proveedores")
public class ProductosProveedoresController {

    /**
     * Servicio para gestionar las operaciones relacionadas con las asociaciones entre productos y proveedores.
     */
    @Autowired
    private ProductosProveedoresServices services;

    /**
     * Obtiene todas las asociaciones entre productos y proveedores.
     * @return Lista de asociaciones.
     */
    @GetMapping
    public List<ProductosProveedoresDTO> getAll() {
        return services.getAll();
    }

    /**
     * Crea una nueva asociación entre un producto y un proveedor.
     * @param dto DTO con los datos de la asociación a crear.
     * @return Asociación creada.
     */
    @PostMapping
    public ProductosProveedoresDTO asociar(@RequestBody ProductosProveedoresDTO dto) {
        return services.save(dto);
    }

    /**
     * Elimina una asociación entre un producto y un proveedor.
     * @param id ID de la asociación a eliminar.
     */
    @DeleteMapping("/{id}")
    public void eliminarAsociacion(@PathVariable Long id) {
        services.delete(id);
    }
}
