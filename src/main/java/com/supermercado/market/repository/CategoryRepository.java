package com.supermercado.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supermercado.market.entity.Category;

/* Repositorio para la entidad Category. */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Optional<Category> findById(Long id);

    @Query("SELECT c FROM Category c WHERE c.nombre = :nombre")
    Optional<Category> findByNombre(String nombre);

    @Query("SELECT c FROM Category c WHERE c.deleted = false")
    List<Category> findAll();

    Category save(Category category);

    void delete(Category category);

}