package com.supermercado.market.service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2 // para imprimir logs
public class JwtService {
    /**
     * Llave secreta para generar la firma
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    /**
     * Tiempo de expiracion del token en ms
     */
    @Value("${security.jwt.token-expiration}")
    private Long tokenExpiration;

    /**
     * Generar firma secreta a partir de nuestra secretKey del yaml
     * 
     * @return Firma secreta para generar el jwt
     */
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generar un jwt
     * 
     * @param userId
     * @param rolId
     * @param userName
     * @return String jwt
     */
    public String generateToken(Long userId, Long rolId, String userName) {
        return Jwts.builder() // se empieza a contruir el jwt
                .claims(Map.of("userId", userId)) // Mapeamos el claim personalizado (claim: datos del payload que
                .claims(Map.of("rolId", rolId))
                .subject(userName) // A quien pertenece el token
                .issuedAt(new Date()) // Fecha de creación del token
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration)) // expiracion del token expresada en
                                                                                    // ms (fecha actual + 10 min)
                .signWith(getSigninKey()) // Firmado con (llave secreta del yaml)
                .compact(); // Contruye el string final
    }

    /**
     * Valida si el token es valido y si ha expirado o no
     * 
     * @param token
     * @return Boolean
     */
    public Boolean isTokenValid(String token) {
        try {
            // El parser intenta descifrar la firma con nuestra llave secreta
            Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("Token is invalid: " + e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Ocurrió un error inesperado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extrae todos los claims (payload) del token
     * 
     * @param <T>
     * @param token
     * @param resolver
     * @return resolver del claim
     */
    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    /**
     * Extrae el propietario del token (nombre de usuario)
     * 
     * @param token
     * @return nombre de usuario
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extraer el id del usuario
     * 
     * @param token
     * @return id del usuario
     */
    public Long extractUserId(String token) {
        return extractClaims(token, claims -> claims.get("userId", Long.class));
    }

    /**
     * Extraer el id del rol del usuario
     * 
     * @param token
     * @return id del rol del usuario
     */
    public Long extractRolId(String token) {
        return extractClaims(token, claims -> claims.get("rolId", Long.class));
    }

    public String refreshToken(String token) throws Exception {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new Exception("Token is expired " + e.getMessage());
        } catch (JwtException e) {
            throw new Exception("Token is invalid " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Server error " + e.getMessage());
        }

        return generateToken(claims.get("userId", Long.class), claims.get("rolId", Long.class), claims.getSubject());
    }
}