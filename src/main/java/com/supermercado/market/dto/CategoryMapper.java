package com.supermercado.market.dto;

import org.springframework.stereotype.Component;

import com.supermercado.market.entity.Category;

/**
 * Mapeador para convertir entre entidades y DTOs de categoría.
 */
@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setNombre(category.getNombre());
        categoryDTO.setDescripcion(category.getDescripcion());

        return categoryDTO;
    }

    public Category getCategory(CategoryDTO categoryDTO) {

        Category category = new Category();
        category.setNombre(categoryDTO.getNombre());
        category.setDescripcion(categoryDTO.getDescripcion());

        return category;
    }
}