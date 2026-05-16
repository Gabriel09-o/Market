package com.supermercado.market.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * DTO para representar un empleado.
 */
@Data
public class EmpleadoDTO {

    private String cedula;
    private String nombre;
    private String cargo;
    private LocalDate fechaIngreso;
    private double salario;

    public EmpleadoDTO() {
    }

    /**
     * Constructor para crear un empleado.
     * @param cedula Cédula del empleado.
     * @param nombre Nombre del empleado.
     * @param cargo Cargo del empleado.
     * @param fechaIngreso Fecha de ingreso del empleado.
     * @param salario Salario del empleado.
     */
    public EmpleadoDTO(String cedula, String nombre,
            String cargo, LocalDate fechaIngreso, Double salario) {

        this.cedula = cedula;
        this.nombre = nombre;
        this.cargo = cargo;
        this.fechaIngreso = fechaIngreso;
        this.salario = salario;
    }

}