package com.supermercado.market.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.supermercado.market.dto.UserRequestDTO;
import com.supermercado.market.dto.UserResponseDTO;
import com.supermercado.market.entity.Users;
import com.supermercado.market.repository.UsersRepository;
import com.supermercado.market.security.Permission;
import com.supermercado.market.security.UserRole;
import com.supermercado.market.service.AuthorizationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    /**
     * Repositorio para gestionar las operaciones relacionadas con los usuarios.
     * Codifica las contraseñas de los usuarios.
     * Servicio para gestionar la autorización de las operaciones relacionadas con los usuarios.
     */
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;

    /**
     * Obtiene todos los usuarios.
     * @param request Objeto HttpServletRequest.
     * @return Lista de usuarios.
     */
    @GetMapping
    public List<UserResponseDTO> findAll(HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_READ);
        return usersRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Crea un nuevo usuario.
     * @param requestBody DTO con los datos del usuario a crear.
     * @param request Objeto HttpServletRequest.
     * @return Usuario creado.
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO requestBody, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_CREATE);
        validateRole(requestBody.getRolId());

        /**
         * Verifica si el nombre de usuario ya está en uso.
         */
        usersRepository.findByUsername(requestBody.getUsername()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este nombre de usuario ya esta en uso");
        });

        // Crea un nuevo usuario con los datos proporcionados y lo guarda en la base de datos.
        Users user = new Users();
        user.setUsername(requestBody.getUsername());
        user.setPassword(passwordEncoder.encode(requestBody.getPassword()));
        user.setRolId(requestBody.getRolId());

        // Devuelve una respuesta con el usuario creado y un código de estado 201 (CREATED).
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(usersRepository.save(user)));
    }

    /**
     * Actualiza un usuario existente.
     * @param id ID del usuario a actualizar.
     * @param requestBody DTO con los datos del usuario a actualizar.
     * @param request Objeto HttpServletRequest.
     * @return Usuario actualizado.
     */
    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id, @RequestBody UserRequestDTO requestBody,
            HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_UPDATE);
        validateRole(requestBody.getRolId());

        /**
         * Obtiene el usuario a actualizar.
         */
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        user.setUsername(requestBody.getUsername());
        if (requestBody.getPassword() != null && !requestBody.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(requestBody.getPassword()));
        }
        user.setRolId(requestBody.getRolId());

        // Guarda el usuario actualizado en la base de datos y devuelve una respuesta con el usuario actualizado.
        return toResponse(usersRepository.save(user));
    }

    /**
     * Elimina un usuario.
     * @param id ID del usuario a eliminar.
     * @param request Objeto HttpServletRequest.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        authorizationService.requirePermission(request, Permission.USERS_DELETE);
        if (!usersRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usersRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte un objeto Users a un DTO de respuesta.
     * @param user Objeto Users.
     * @return DTO de respuesta.
     */
    private UserResponseDTO toResponse(Users user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRolId(user.getRolId());
        return response;
    }

    /**
     * Valida que el ID del rol proporcionado sea válido.
     * @param rolId ID del rol a validar.
     * @throws ResponseStatusException Si el ID del rol no es válido.
     */
    private void validateRole(Long rolId) {
        try {
            UserRole.fromId(rolId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no valido");
        }
    }
}
