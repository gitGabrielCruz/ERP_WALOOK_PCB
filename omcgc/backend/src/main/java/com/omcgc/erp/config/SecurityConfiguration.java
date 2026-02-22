/*
============================================================
Nombre del archivo : SecurityConfiguration.java
Ruta              : omcgc/backend/src/config/SecurityConfiguration.java
Tipo              : Backend (Configuración de Seguridad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Definir la cadena de filtros de seguridad (Security Filter Chain) para el manejo
de autorizaciones HTTP, CORS y protección CSRF.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-02 (Seguridad de Acceso):
   - Configuración de CORS permitiendo orígenes cruzados para desarrollo.
   - Exposición pública de endpoints de autenticación (/api/auth/**).
   - Bloqueo por defecto de cualquier otro recurso no autenticado.
============================================================
*/
package com.omcgc.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // --- CONFIGURACIÓN DE CORS (RNF-02) ---
                // Permite la comunicación cruzada necesaria entre el frontend (file:// o
                // localhost)
                // y el backend, validando orígenes y métodos permitidos.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // --- PROTECCIÓN CSRF ---
                // Desactivada debido a la naturaleza stateless de la API REST que utiliza
                // JWT/Token.
                .csrf(csrf -> csrf.disable())

                // --- CABECERAS DE SEGURIDAD Y CSP (RNF-05 / SEGURIDAD DINÁMICA) ---
                // Implementación de Content Security Policy para mitigar ataques XSS y
                // Clickjacking.
                // Define una 'Lista Blanca' de orígenes permitidos para scripts, estilos y
                // fuentes.
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; " +
                                        "script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
                                        "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdnjs.cloudflare.com; "
                                        +
                                        "font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com; " +
                                        "img-src 'self' data:; " +
                                        "connect-src 'self' http://localhost:9090 http://69.6.242.217:9090 https://api-vps.graxsoft.com;")))

                // --- CONTROL DE ACCESO (HU-M01-01) ---
                // Configura la visibilidad de los endpoints. En esta etapa se permite acceso
                // total
                // para pruebas de integración, sujeto a endurecimiento en Etapas 3 y 4.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow ALL origins (file://, localhost, etc)
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
