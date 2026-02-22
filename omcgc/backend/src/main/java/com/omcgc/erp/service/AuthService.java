/*
============================================================
Nombre del archivo : AuthService.java
Ruta              : omcgc/backend/src/service/AuthService.java
Tipo              : Backend (Java Spring Boot)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Encapsular la lógica de negocio de autenticación y autorización, sirviendo como
intermediario entre los controladores REST y la capa de persistencia de datos.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Verificación de Identidad):
   - Consulta de usuarios por email vía `UsuarioRepository`.
   - Comparación criptográfica de contraseñas (Hash BCrypt).
   - Validación de estado activo/inactivo del usuario.
2. RNF-05 (Resiliencia):
   - Integración con `DatabaseHealthService` para validación previa ("Fail Fast").
3. RNF-02 (Seguridad en Desarrollo):
   - Implementación de credenciales estáticas de respaldo para entorno local.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DatabaseHealthService dbHealthService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario login(String email, String password) {

        // --- MECANISMO DE AUTENTICACIÓN PRIVILEGIADA (ENTORNO DE DESARROLLO) ---
        // Permite el acceso administrativo sin validar contra la base de datos
        // para facilitar pruebas de integración en etapas tempranas.
        if ("root".equals(email) && "root".equals(password)) {
            return createSuperAdminUser();
        }
        // -----------------------------------------------------------------------

        // DIAGNÓSTICO DE DISPONIBILIDAD DE BASE DE DATOS
        // Verifica la conectividad antes de intentar operaciones de lectura.
        if (dbHealthService.isConnected()) {
            System.out.println("[AUTH-DEBUG] Conexión DB: ACTIVA (" + dbHealthService.getConnectionDetails() + ")");
        } else {
            System.err.println("[AUTH-DEBUG] Conexión DB: FALLIDA");
            throw new RuntimeException("Error Crítico: El sistema no puede conectar con la Base de Datos.");
        }

        // Normal Flow
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null) {
            // TRAZABILIDAD: Confirmación de existencia de usuario en repositorio
            System.out.println("[AUTH-SYSTEM] Identidad localizada: " + email);

            // Verificación criptográfica de la contraseña proporcionada contra el hash
            // almacenado
            boolean passwordMatch = passwordEncoder.matches(password, usuario.getPasswordHash());

            if (passwordMatch) {
                if (!usuario.isActivo()) {
                    throw new RuntimeException("Login fallido: El usuario existe pero está marcado como INACTIVO.");
                }
                return usuario;
            } else {
                // EXCEPCIÓN CONTROLADA: Credenciales inválidas
                throw new RuntimeException("Fallo de autenticación: Las credenciales proporcionadas no son válidas.");
            }
        }

        // EXCEPCIÓN CONTROLADA: Identidad no registrada
        throw new RuntimeException("Fallo de autenticación: Identidad no encontrada en el registro de usuarios.");
    }

    private Usuario createSuperAdminUser() {
        // Check DB Status using Service
        String dbStatus = dbHealthService.isConnected() ? "CONECTADO" : "DESCONECTADO";

        // UUID fijo — alineado con datos semilla en 02_create_tables_usuarios.sql
        // NOTA: Eliminar este método completo al pasar a producción.
        Usuario superAdmin = new Usuario();
        superAdmin.setIdUsuario("00000000-0000-0000-0000-000000000000");
        superAdmin.setNombre("SUPER ADMIN (DEBUG) - DB: " + dbStatus);
        superAdmin.setEmail("root");
        superAdmin.setActivo(true);
        superAdmin.setIdRol("ROL-001"); // Alineado con seed: rol Administrador
        superAdmin.setNombreRol("ADMIN");
        // Sucursal por defecto: Sucursal Centro (UUID fijo alineado con
        // seed_cpanel_clean.sql)
        superAdmin.setIdSucursal("SUC-00000000-0000-0000-0000-000000000001");
        return superAdmin;
    }
}
