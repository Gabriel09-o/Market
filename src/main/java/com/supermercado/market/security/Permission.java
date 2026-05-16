package com.supermercado.market.security;

/**
 * Clase que define los permisos del sistema
 * Los permisos son acciones específicas que los usuarios pueden realizar en el
 * sistema,
 * como leer, crear, actualizar o eliminar recursos.
 * Estos permisos se asignan a los roles de usuario para controlar el acceso a
 * diferentes funcionalidades de la aplicación.
 */
public enum Permission {
    // Permisos de usuarios
    USERS_READ,
    USERS_CREATE,
    USERS_UPDATE,
    USERS_DELETE,
    // Permisos de ventas
    SALES_READ,
    SALES_CREATE,
    // Permisos de inventario
    INVENTORY_READ,
    INVENTORY_CREATE,
    INVENTORY_UPDATE,
    // Permisos de stock
    STOCK_READ,
    STOCK_UPDATE,
    // Permisos de productos
    PRODUCTS_READ,
    PRODUCTS_CREATE,
    PRODUCTS_UPDATE,
    PRODUCTS_DELETE
}
