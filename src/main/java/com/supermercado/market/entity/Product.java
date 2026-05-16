package com.supermercado.market.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/* Entidad que representa un producto del supermercado. */
@SQLDelete(sql = "UPDATE Products SET deleted = true WHERE idProducto = ?")
@SQLRestriction("deleted = false")

@Entity
@Table(name = "Products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "codigoBarras", unique = true, nullable = false, length = 100)
    private String codigoBarras;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stock;

    @Column(name = "estado", nullable = false)
    private boolean estado = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoria;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted = false;

}