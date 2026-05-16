package com.supermercado.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermercado.market.dto.CategoryDTO;
import com.supermercado.market.dto.ProductDTO;
import com.supermercado.market.service.CategoriesServices;

/**
 * Controlador para gestionar las operaciones relacionadas con las categorias
 */
@RestController
@RequestMapping("/categorias")
public class CategoriesController {

    /**
     * Servicio para gestionar las operaciones relacionadas con las categorias
     */
    @Autowired
    private CategoriesServices categoriesServices;

    /**
     * Obtiene todas las categorias
     * @return Lista de categorias
     */
    @GetMapping
    public List<CategoryDTO> getAllCategorias() {
        return categoriesServices.getAllCategories();
    }

    /**
     * Obtiene una categoria por su id
     * @param idCategoria Id de la categoria a obtener
     * @return Categoria obtenida
     */
    @GetMapping("/{idCategoria}")
    public CategoryDTO getCategorias(@PathVariable Long idCategoria) {
        return categoriesServices.getCategory(idCategoria);
    }

    /**
     * Crea una nueva categoria
     * @param categoriesDTO DTO de la categoria a crear
     * @return Categoria creada
     */
    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategoria(@RequestBody CategoryDTO categoriesDTO) {

        CategoryDTO categoriesDTO1 = categoriesServices.saveCategory(categoriesDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoriesDTO1);
    }

    /**
     * Actualiza una categoria existente
     * @param categoriesDTO DTO de la categoria a actualizar
     * @return Categoria actualizada
     */
    @PutMapping
    public CategoryDTO updateCategoria(@RequestBody CategoryDTO categoriesDTO) {
        return categoriesServices.updateCategory(categoriesDTO);
    }

    /**
     * Elimina una categoria
     * @param idCategoria Id de la categoria a eliminar
     * @return Categoria eliminada
     */
    @DeleteMapping("/{idCategoria}")
    public CategoryDTO deleteCategoria(@PathVariable Long idCategoria) {
        return categoriesServices.deleteCategory(idCategoria);
    }

    /**
     * Obtiene los productos de una categoria
     * @param idCategoria Id de la categoria a obtener los productos
     * @return Lista de productos de la categoria
     */
    @GetMapping("/{idCategoria}/productos")
    public List<ProductDTO> getProductos(@PathVariable Long idCategoria) {
        return categoriesServices.getProductos(idCategoria);
    }

}
