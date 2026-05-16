package com.supermercado.market.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.ProductDTO;
import com.supermercado.market.dto.ProductsMapper;
import com.supermercado.market.entity.Category;
import com.supermercado.market.entity.Product;
import com.supermercado.market.exceptions.NotFoundException;
import com.supermercado.market.repository.CategoryRepository;
import com.supermercado.market.repository.ProductRepository;

/* Servicio para la gestión de productos. */
@Service
public class ProductsServices {

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO getProduct(Long idProducto) {

        Optional<Product> optionalProduct = productsRepository.findById(idProducto);

        if (optionalProduct.isEmpty()) {
            throw new NotFoundException("product", idProducto.toString());
        }

        Product product = optionalProduct.get();

        return productsMapper.toDTO(product, true);
    }

    /* Guarda un nuevo producto */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // Buscamos la categoría, si no existe, la creamos y guardamos
        Category category = categoryRepository.findByNombre(productDTO.getCategory().getNombre())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setNombre(productDTO.getCategory().getNombre());
                    newCategory.setDescripcion("Categoría creada automáticamente");
                    return categoryRepository.save(newCategory);
                });

        Product product = productsMapper.getProduct(productDTO);
        product.setCategoria(category);
        product.setEstado(true);

        productsRepository.save(product);

        return productsMapper.toDTO(product, true);
    }

    /* Actualiza un producto existente */
    public ProductDTO updateProduct(Long idProducto, ProductDTO productDTO) {

        Product product = productsRepository.findById(idProducto)
                .orElseThrow(() -> new NotFoundException("Product NO ENCONTRADO con id ", idProducto.toString()));

        product.setNombre(productDTO.getNombre());
        product.setPrecio(productDTO.getPrecio());
        product.setStock(productDTO.getStock());

        product.setCodigoBarras(productDTO.getCodigoBarras());

        productsRepository.save(product);

        return productsMapper.toDTO(product, true);
    }

    /* Elimina un producto */
    public ProductDTO deleteProduct(Long idProducto) {

        Optional<Product> optionalProduct = productsRepository.findById(idProducto);

        if (optionalProduct.isEmpty()) {
            throw new NotFoundException("Product NO ENCONTRADO con id ", idProducto.toString());
        }

        Product product = optionalProduct.get();

        productsRepository.delete(product);

        return productsMapper.toDTO(product, true);
    }

    /* Lista todos los productos */
    public List<ProductDTO> getAllProducts() {

        return productsRepository.findAll().stream()
                .map(product -> productsMapper.toDTO(product, true))
                .collect(Collectors.toList());

    }
}