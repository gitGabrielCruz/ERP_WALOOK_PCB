/*
============================================================
Nombre del archivo : AuthController.java
Ruta              : omcgc/backend/src/controller/AuthController.java
Tipo              : Backend (Controlador REST)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Exponer los endpoints REST públicos para la gestión de sesiones de usuario (Login).
Actúa como capa de entrada validando peticiones HTTP antes de invocar servicios.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Inicio de Sesión):
   - Endpoint POST /api/auth/login.
   - Mapeo de credenciales JSON a objetos de dominio.
   - Retorno estandarizado de token de sesión y metadatos de usuario (Rol, ID).
============================================================
*/
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.service.AuthService;
import com.omcgc.erp.service.BitacoraService;
import com.omcgc.erp.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow frontend
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraService bitacoraService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpServletRequest request) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String ip = request.getRemoteAddr();

        try {
            Usuario usuario = authService.login(email, password);

            // REGISTRO DE EVENTO: LOGIN EXITOSO (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(usuario.getIdUsuario(), "AUTH-01", ip, null, null);

            // Obtener permisos del usuario
            List<Map<String, Object>> permisos = usuarioService.getPermissionsByUsuario(usuario.getIdUsuario());

            // Returns user data + permissions
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login exitoso",
                    "userId", usuario.getIdUsuario(),
                    "rolId", usuario.getIdRol() != null ? usuario.getIdRol() : "",
                    "nombreRol", usuario.getNombreRol() != null ? usuario.getNombreRol() : "",
                    "nombre", usuario.getNombre(),
                    "idSucursal", usuario.getIdSucursal() != null ? usuario.getIdSucursal() : "",
                    "permisos", permisos));
        } catch (RuntimeException e) {
            String errorMsg = e.getMessage();

            // Verificar si es un error de infraestructura (BD, conexion, etc.)
            if (errorMsg != null && (errorMsg.contains("Connection") ||
                    errorMsg.contains("database") ||
                    errorMsg.contains("SQLException") ||
                    errorMsg.contains("Communications link failure") ||
                    errorMsg.contains("Unable to acquire JDBC Connection"))) {

                // REGISTRO DE EVENTO: FALLO CRITICO DE DB (PATRON MAESTRO CIFRADO)
                bitacoraService.registrarEvento(null, "SYS-01", ip, "Error de infraestructura SQL", errorMsg);

                // Error de sistema (500)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message",
                        "Error de conexion con la base de datos. Verifique que el servidor MySQL este activo.",
                        "errorCode", "SYS-DB-001"));
            }

            // REGISTRO DE EVENTO: FALLO DE CREDENCIALES (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(null, "AUTH-02", ip, "Intento: " + email, errorMsg);

            // Error de autenticacion (401)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        } catch (Exception e) {
            // Cualquier otra excepcion no controlada (500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Error inesperado del servidor: " + e.getMessage(),
                    "errorCode", "SYS-GEN-001"));
        }
    }
}
