package com.supermercado.market.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.CategoryDTO;
import com.supermercado.market.dto.CategoryMapper;
import com.supermercado.market.dto.ProductDTO;
import com.supermercado.market.dto.ProductsMapper;
import com.supermercado.market.entity.Category;
import com.supermercado.market.entity.Product;
import com.supermercado.market.exceptions.NotFoundException;
import com.supermercado.market.repository.CategoryRepository;
import com.supermercado.market.repository.ProductRepository;

/* Servicio para la gestión de categorías. */
@Service
public class CategoriesServices {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductsMapper productsMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> categoryMapper.toDTO(category))
                .collect(Collectors.toList());
    }

    /* Obtiene una categoría por su ID. */
    public CategoryDTO getCategory(Long idCategoria) {

        Optional<Category> category = categoryRepository.findById(idCategoria);

        if (category.isEmpty()) {
            throw new NotFoundException("category", idCategoria.toString());
        }

        return categoryMapper.toDTO(category.get());
    }

    /* Guarda una nueva categoría. */
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {

        Category category = categoryMapper.getCategory(categoryDTO);

        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    /* Actualiza una categoría existente. */
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findByNombre(categoryDTO.getNombre());

        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("category NO ENCONTRADA", categoryDTO.getNombre());
        }

        Category category = categoryOptional.get();
        category.setNombre(categoryDTO.getNombre());
        category.setDescripcion(categoryDTO.getDescripcion());

        category = categoryRepository.save(category);

        return categoryMapper.toDTO(category);
    }

    /* Elimina una categoría existente. */
    public CategoryDTO deleteCategory(Long idCategoria) {
        Optional<Category> categoryOptional = categoryRepository.findById(idCategoria);

        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("category", idCategoria.toString());
        }

        Category category = categoryOptional.get();
        categoryRepository.delete(category);

        return categoryMapper.toDTO(category);
    }

    /* Obtiene los productos de una categoría específica. */
    public List<ProductDTO> getProductos(Long idCategoria) {
        Optional<Category> optionalCategory = categoryRepository.findById(idCategoria);

        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("category NO ENCONTRADA", idCategoria.toString());
        }

        Category category = optionalCategory.get();

        Product example = new Product();
        Category categoryExample = new Category();
        example.setCategoria(categoryExample);

        List<Product> productos = productRepository.findAll(Example.of(example));

        if (productos.isEmpty()) {
            throw new NotFoundException("Productos no encontrados", null);
        }
        return productos
                .stream()
                .map(producto -> productsMapper.toDTO(producto, false))
                .collect(Collectors.toList());
    }
}