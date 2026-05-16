package com.supermercado.market.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Clase que define los roles del sistema
 */

public enum UserRole {
    /**
     * Rol de administrador
     */
    ADMINISTRADOR(1L, EnumSet.allOf(Permission.class)),
    /**
     * Rol de cliente
     */
    CLIENTE(2L, EnumSet.of(
            Permission.SALES_CREATE,
            Permission.STOCK_READ,
            Permission.PRODUCTS_READ)),
    /**
     * Rol de inventario
     */
    INVENTARIO(3L, EnumSet.of(
            Permission.INVENTORY_READ,
            Permission.INVENTORY_CREATE,
            Permission.INVENTORY_UPDATE,
            Permission.STOCK_READ,
            Permission.STOCK_UPDATE,
            Permission.PRODUCTS_READ,
            Permission.PRODUCTS_CREATE,
            Permission.PRODUCTS_UPDATE)),
    /**
     * Rol de supervisor
     */
    SUPERVISOR(4L, EnumSet.of(
            Permission.SALES_READ,
            Permission.INVENTORY_READ,
            Permission.STOCK_READ,
            Permission.PRODUCTS_READ));

    /**
     * Id del rol
     */
    private final Long id;
    /**
     * Permisos del rol
     */
    private final Set<Permission> permissions;

    /**
     * Constructor de la clase UserRole
     * 
     * @param id
     * @param permissions
     */
    UserRole(Long id, Set<Permission> permissions) {
        this.id = id;
        this.permissions = Collections.unmodifiableSet(permissions);
    }

    /**
     * Metodo que permite obtener el id del rol
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que permite obtener los permisos del rol
     * 
     * @return
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Metodo que permite obtener un rol a partir de su id
     * 
     * @param id
     * @return
     */
    public static UserRole fromId(Long id) {
        // Buscamos el rol a partir del id en el enum UserRole
        // Si no lo encuentra, lanzamos una excepcion
        return Arrays.stream(values())
                .filter(role -> role.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rol no permitido"));
    }

    /**
     * Metodo que permite verificar si el rol tiene permiso para realizar la accion
     * 
     * @param permission
     * @return
     */
    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }
}
