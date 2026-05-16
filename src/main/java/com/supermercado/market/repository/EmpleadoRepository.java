package com.supermercado.market.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supermercado.market.entity.Cargo;
import com.supermercado.market.entity.Empleado;

/* Repositorio para la entidad Empleado. */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByCedula(String cedula);

    // Cambia findByCargos por findByCargo
    @Query("SELECT e FROM Empleado e WHERE e.cargo = :cargo")
    List<Empleado> findByCargos(Cargo cargo);

    List<Empleado> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);

    @Query("SELECT e FROM Empleado e WHERE e.cargo = :cargo")
    Optional<Empleado> findByCargo(Cargo cargo);

}