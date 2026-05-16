package com.supermercado.market.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermercado.market.security.Permission;
import com.supermercado.market.security.UserRole;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase encargada de la autorizacion de los usuarios,
 * se encarga de verificar si el usuario tiene el permiso necesario para
 * realizar una accion
 * La autorización es el proceso de verificar si un usuario tiene los permisos
 * necesarios
 * para acceder a un recurso o realizar una acción específica en la aplicación.
 */
@Service
public class AuthorizationService {
    /**
     * Verificar si el rol tiene permiso para realizar la accion
     * 
     * @param request
     * @param permission
     */
    public void requirePermission(HttpServletRequest request, Permission permission) {
        // Obtenemos el rol a partir del id que se seteo en el filtro de jwt
        Object rolId = request.getAttribute("rolId");
        // Validamos si el rol es un long
        if (!(rolId instanceof Long)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token sin rol valido");
        }

        // Obtenemos el rol a partir del id
        UserRole role;
        try {
            // Obtenemos el rol a partir del id
            role = UserRole.fromId((Long) rolId);
            // Validamos si el rol tiene permiso para realizar la accion
        } catch (IllegalArgumentException e) {
            // si no existe el rol, lanzamos una excepcion
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol no permitido");
        }
        // si no tiene el permiso, lanzamos una excepcion
        if (!role.hasPermission(permission)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso para esta accion");
        }
    }
}
