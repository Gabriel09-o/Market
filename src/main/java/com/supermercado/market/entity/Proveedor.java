package com.supermercado.market.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/* Entidad que representa un proveedor del supermercado. */
@SQLDelete(sql = "UPDATE Proveedores SET deleted = true WHERE idProveedor = ?")
@SQLRestriction("deleted = false")

@Entity
@Table(name = "Proveedores")
@Data
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProveedor")
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 100)
    private String nit;

    @Column(length = 20)
    private String telefono;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(length = 200)
    private String direccion;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted = false;

}