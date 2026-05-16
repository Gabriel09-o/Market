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
import com.supermercado.market.service.ProductsServices;

/**
 * Controlador para gestionar las operaciones relacionadas con los productos.
 */
@RestController
@RequestMapping("/productos")
public class ProductsController {

    /**
     * Servicio para gestionar las operaciones relacionadas con los productos.
     */
    @Autowired
    private ProductsServices productsServices;

    /**
     * Obtiene todos los productos.
     * @return Lista de productos.
     */
    @GetMapping
    public List<ProductDTO> getAllProductos() {
        return productsServices.getAllProducts();
    }

    /**
     * Obtiene un producto por su ID.
     * @param idProducto ID del producto a obtener.
     * @return Producto encontrado.
     */
    @GetMapping("/{idProducto}")
    public ProductDTO getProductos(@PathVariable Long idProducto) {
        return productsServices.getProduct(idProducto);
    }

    /**
     * Crea un nuevo producto.
     * @param productDTO DTO con los datos del producto a crear.
     * @return Producto creado.
     */
    @PostMapping()
    public ProductDTO createProducto(@RequestBody ProductDTO productDTO) {

        return productsServices.saveProduct(productDTO);

    }

    /**
     * Actualiza un producto existente.
     * @param idProducto ID del producto a actualizar.
     * @param productDTO DTO con los datos del producto a actualizar.
     * @return Producto actualizado.
     */
    @PutMapping("/{idProducto}")
    public ProductDTO updateProducto(@PathVariable Long idProducto, @RequestBody ProductDTO productDTO) {
        return productsServices.updateProduct(idProducto, productDTO);
    }

    /**
     * Elimina un producto.
     * @param idProducto ID del producto a eliminar.
     * @return Producto eliminado.
     */
    @DeleteMapping("/{idProducto}")
    public ProductDTO deleteProducto(@PathVariable Long idProducto) {
        return productsServices.deleteProduct(idProducto);
    }
}
