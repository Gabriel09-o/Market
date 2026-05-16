package com.supermercado.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermercado.market.dto.LoginRequestDTO;
import com.supermercado.market.dto.LoginResponseDTO;
import com.supermercado.market.dto.MessageResponseDTO;
import com.supermercado.market.dto.RefreshTokenResponseDTO;
import com.supermercado.market.dto.RegisterRequestDTO;
import com.supermercado.market.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Controlador para manejar las operaciones de autenticación.
*/
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    /**
     * Servicio para manejar la lógica de autenticación.
     */
    private final AuthService authService;

    /**
     * Registra un nuevo usuario.
     * @param request DTO con los datos del usuario a registrar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        try {
            MessageResponseDTO response = authService.register(request); 
            return ResponseEntity.status(HttpStatus.CREATED).body(response); 
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }

    /**
     * Inicia sesión y obtiene un token JWT.
     * @param request DTO con los datos de inicio de sesión.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = authService.login(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    /**
     * Refresca el token JWT.
     * @param request HttpServletRequest con el encabezado de autorización.
     * @return ResponseEntity con el resultado de la operación.
     */
    @GetMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(HttpServletRequest request) {
        String autheader = request.getHeader("Authorization");
        if (autheader == null || !autheader.startsWith("Bearer ")) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String token = autheader.replace("Bearer ", "");

        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();

        try {
            response = authService.refreshToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(response); 
        } catch (RuntimeException e) {
            response.setMessage("Token expired");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
