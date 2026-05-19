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
import com.supermercado.market.security.Permission;
import com.supermercado.market.service.AuthorizationService;
import com.supermercado.market.service.CategoriesServices;

import jakarta.servlet.http.HttpServletRequest;

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
     * Servicio para gestionar la autorización de operaciones
     */
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Obtiene todas las categorias
     * @param request La solicitud HTTP
     * @return Una lista de categorias
     */
    @GetMapping
    public List<CategoryDTO> getAllCategorias(HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_READ);
        return categoriesServices.getAllCategories();
    }

    /**
     * Obtiene una categoria por su ID
     * @param idCategoria El ID de la categoria
     * @param request La solicitud HTTP
     * @return La categoria encontrada
     */
    @GetMapping("/{idCategoria}")
    public CategoryDTO getCategorias(@PathVariable Long idCategoria, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_READ);
        return categoriesServices.getCategory(idCategoria);
    }

    /**
     * Crea una nueva categoria
     * @param categoriesDTO La categoria a crear
     * @param request La solicitud HTTP
     * @return La categoria creada
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategoria(@RequestBody CategoryDTO categoriesDTO,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_CREATE);
        CategoryDTO categoriesDTO1 = categoriesServices.saveCategory(categoriesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesDTO1);
    }

    /**
     * Actualiza una categoria existente
     * @param categoriesDTO La categoria a actualizar
     * @param request La solicitud HTTP
     * @return La categoria actualizada
     */
    @PutMapping
    public CategoryDTO updateCategoria(@RequestBody CategoryDTO categoriesDTO, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_UPDATE);
        return categoriesServices.updateCategory(categoriesDTO);
    }

    /**
     * Elimina una categoria
     * @param idCategoria El ID de la categoria
     * @param request La solicitud HTTP
     * @return La categoria eliminada
     */
    @DeleteMapping("/{idCategoria}")
    public CategoryDTO deleteCategoria(@PathVariable Long idCategoria, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_DELETE);
        return categoriesServices.deleteCategory(idCategoria);
    }

    /**
     * Obtiene los productos de una categoria
     * @param idCategoria El ID de la categoria
     * @param request La solicitud HTTP
     * @return Una lista de productos
     */
    @GetMapping("/{idCategoria}/productos")
    public List<ProductDTO> getProductos(@PathVariable Long idCategoria, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.PRODUCTS_READ);
        return categoriesServices.getProductos(idCategoria);
    }

}
