package com.supermercado.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.ProductosProveedoresDTO;
import com.supermercado.market.dto.ProductosProveedoresMapper;
import com.supermercado.market.entity.ProductoProveedor;
import com.supermercado.market.repository.ProductosProveedoresRepository;

/* Servicio para la gestión de productos y proveedores. */
@Service
public class ProductosProveedoresServices {

    @Autowired
    private ProductosProveedoresRepository repository;

    @Autowired
    private ProductosProveedoresMapper mapper;

    public List<ProductosProveedoresDTO> getAll() {

        return repository.findAll().stream()
                .map(relacion -> mapper.toDTO(relacion))
                .collect(Collectors.toList());
    }

    /* Guarda una nueva relación entre producto y proveedor. */
    public ProductosProveedoresDTO save(ProductosProveedoresDTO dto) {

        ProductoProveedor relacion = mapper.gProductoProveedor(dto);

        repository.save(relacion);

        return mapper.toDTO(relacion);
    }

    /* Elimina una relación entre producto y proveedor. */
    public void delete(Long id) {

        repository.deleteById(id);
    }
}