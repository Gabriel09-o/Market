package com.supermercado.market.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.LoginRequestDTO;
import com.supermercado.market.dto.LoginResponseDTO;
import com.supermercado.market.dto.MessageResponseDTO;
import com.supermercado.market.dto.RefreshTokenResponseDTO;
import com.supermercado.market.dto.RegisterRequestDTO;
import com.supermercado.market.entity.Users;
import com.supermercado.market.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

/* Servicio para la autenticación de usuarios. */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    private final JwtService jwtService;

    public MessageResponseDTO register(RegisterRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Registro exitoso");

        if (usersRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Este nombre de usuario ya está en uso");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRolId(request.getRol());

        usersRepository.save(user);

        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();
        Optional<Users> user = usersRepository.findByUsername(request.getUsername());

        if (user.isEmpty() && request.getUsername() != null) {
            response.setMessage("Este usuario no se encuentra registrado");
            return response;
        }

        Users userFound = user.get();

        if (!passwordEncoder.matches(request.getPassword(), userFound.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // $2a$10$kYgtv2ktFsDmXG/TVbmcH.0gHXyEgZIzCOnNcjw4l0JkNCcrlessW

        String jwt = jwtService.generateToken(userFound.getId(), userFound.getRolId(), userFound.getUsername());

        response.setMessage("Inicio de sesión exitoso");
        response.setJwt(jwt);
        return response;
    }

    /**
     * Este método es para el refresco del token
     * 
     * @param token jwt viejo
     * @return nuevo token
     * @throws Exception
     */
    public RefreshTokenResponseDTO refreshToken(String token) throws Exception {
        String jwt = jwtService.refreshToken(token);
        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();
        response.setMessage("ok");
        response.setJwt(jwt);
        return response;
    }
}
