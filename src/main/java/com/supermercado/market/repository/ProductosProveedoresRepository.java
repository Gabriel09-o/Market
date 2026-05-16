package com.supermercado.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermercado.market.entity.ProductoProveedor;

/* Repositorio para la entidad ProductoProveedor. */
@Repository
public interface ProductosProveedoresRepository extends JpaRepository<ProductoProveedor, Long> {

    List<ProductoProveedor> findByProveedorId(Long id);
    }
