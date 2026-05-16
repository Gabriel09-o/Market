package com.supermercado.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermercado.market.entity.Product;

/* Repositorio para la entidad Product. */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCodigoBarras(String codigoBarras);

}