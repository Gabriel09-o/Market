package com.supermercado.market.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.supermercado.market.security.RequiresRoleInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor // Para que spring inyecte el interceptor en el constructor de esta clase

/* Configuración de interceptores para el controlador */
public class WebConfig implements WebMvcConfigurer {

    private final RequiresRoleInterceptor requiresRoleInterceptor;

    @Override // Método para registrar el interceptor en la aplicación
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requiresRoleInterceptor); // Agrega el interceptor a la cadena de interceptores de Spring
    }
}
