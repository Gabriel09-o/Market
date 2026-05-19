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
import com.supermercado.market.security.Permission;
import com.supermercado.market.service.AuthorizationService;
import com.supermercado.market.service.ProductsServices;

import jakarta.servlet.http.HttpServletRequest;

/* Controlador para gestionar las operaciones relacionadas con los productos */
@RestController
@RequestMapping("/productos")
public class ProductsController {

    /* Servicio para gestionar las operaciones relacionadas con los productos */
    @Autowired
    private ProductsServices productsServices;

    /* Servicio para gestionar la autorización de operaciones */
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Endpoint para obtener todos los productos.
     * Se requiere el permiso PRODUCTS_READ para acceder a esta información.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return Una lista de productos.
     */
    @GetMapping
    public List<ProductDTO> getAllProductos(HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_READ);
        return productsServices.getAllProducts();
    }

    /**
     * Endpoint para obtener un producto por su ID.
     * Se requiere el permiso PRODUCTS_READ para acceder a esta información.
     * @param idProducto El ID del producto a obtener.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El producto correspondiente al ID especificado.
     */
    @GetMapping("/{idProducto}")
    public ProductDTO getProductos(@PathVariable Long idProducto, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_READ);
        return productsServices.getProduct(idProducto);
    }

    /**
     * Endpoint para crear un nuevo producto.
     * Se requiere el permiso PRODUCTS_CREATE para realizar esta operación.
     * @param productDTO El DTO con los datos del producto a crear.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El producto creado.
     */
    @PostMapping
    public ProductDTO createProducto(@RequestBody ProductDTO productDTO, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_CREATE);
        return productsServices.saveProduct(productDTO);
    }

    /**
     * Endpoint para actualizar un producto existente.
     * Se requiere el permiso PRODUCTS_UPDATE para realizar esta operación.
     * @param idProducto El ID del producto a actualizar.
     * @param productDTO El DTO con los datos actualizados del producto.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El producto actualizado.
     */
    @PutMapping("/{idProducto}")
    public ProductDTO updateProducto(@PathVariable Long idProducto, @RequestBody ProductDTO productDTO,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_UPDATE);
        return productsServices.updateProduct(idProducto, productDTO);
    }

    /**
     * Endpoint para eliminar un producto.
     * Se requiere el permiso PRODUCTS_DELETE para realizar esta operación.
     * @param idProducto El ID del producto a eliminar.
     * @param request El objeto HttpServletRequest para verificar los permisos del usuario.
     * @return El producto eliminado.
     */
    @DeleteMapping("/{idProducto}")
    public ProductDTO deleteProducto(@PathVariable Long idProducto, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_DELETE);
        return productsServices.deleteProduct(idProducto);
    }
}
