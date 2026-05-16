package com.supermercado.market.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.supermercado.market.entity.Product;
import com.supermercado.market.entity.ProductoProveedor;
import com.supermercado.market.entity.Proveedor;
import com.supermercado.market.repository.ProductRepository;
import com.supermercado.market.repository.ProveedorRepository;

/* Mapper para convertir entre entidades y DTOs de la relación entre productos y proveedores. */
@Component
public class ProductosProveedoresMapper {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public ProductosProveedoresDTO toDTO(ProductoProveedor entity) {
        if (entity == null) {
            return null;
        }

        ProductosProveedoresDTO dto = new ProductosProveedoresDTO();
        dto.setId(entity.getId());

        if (entity.getProducto() != null) {
            dto.setProductoId(entity.getProducto().getId());
        }

        if (entity.getProveedor() != null) {
            dto.setProveedorId(entity.getProveedor().getId());
        }

        return dto;
    }

    public ProductoProveedor gProductoProveedor(ProductosProveedoresDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductoProveedor entity = new ProductoProveedor();
        entity.setId(dto.getId());

        if (dto.getProductoId() != null) {
            Product product = productRepository.findById(dto.getProductoId()).orElse(null);
            entity.setProducto(product);
        }

        if (dto.getProveedorId() != null) {
            Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId()).orElse(null);
            entity.setProveedor(proveedor);
        }

        return entity;
    }
}
