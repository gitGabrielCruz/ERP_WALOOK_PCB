/*
============================================================
Nombre del archivo : UsuarioController.java
Ruta              : backend/src/main/java/com/omcgc/erp/controller/UsuarioController.java
Tipo              : Backend (REST Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Controlador REST para la gestión completa de usuarios (CRUD), incluyendo
asignación de roles y permisos. Implementa los endpoints del módulo 1.3.

Trazabilidad:
- HU-M01-03: Gestión de Usuarios, Roles y Permisos
- Endpoints: GET, POST, PUT, DELETE /api/usuarios
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.Usuario;
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
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraService bitacoraService;

    /**
     * Obtener todos los usuarios
     * GET /api/usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuario por ID
     * GET /api/usuarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable String id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo usuario
     * POST /api/usuarios
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUsuario(@RequestBody Usuario usuario, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            // Soporte para guardado atómico (Paquete completo con permisos)
            Usuario nuevoUsuario = usuarioService.saveUserWithPermissions(usuario, usuario.getPermisos());

            // REGISTRO DE EVENTO: CREACIÓN DE USUARIO (PATRON MAESTRO CIFRADO)
            String payload = String.format("[Nombre: %s, Mail: %s, Rol: %s]",
                    nuevoUsuario.getNombre(), nuevoUsuario.getCorreo(), nuevoUsuario.getRolNombre());
            bitacoraService.registrarEvento(null, "CRUD-01", ip, payload, null);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Usuario creado exitosamente",
                            "data", nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al crear usuario: " + e.getMessage()));
        }
    }

    /**
     * Actualizar usuario existente
     * PUT /api/usuarios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUsuario(
            @PathVariable String id,
            @RequestBody Usuario usuario,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            // Obtener estado anterior para comparar atributos
            Usuario anterior = usuarioService.findById(id);
            usuario.setId(id);

            // Soporte para guardado atómico (Paquete completo con permisos)
            Usuario actualizado = usuarioService.saveUserWithPermissions(usuario, usuario.getPermisos());

            if (actualizado != null && anterior != null) {
                // DETECTAR ATRIBUTOS MODIFICADOS (TRAZABILIDAD FORENSE)
                StringBuilder deltas = new StringBuilder();

                // Comparar Nombre
                if (!anterior.getNombre().equals(actualizado.getNombre())) {
                    deltas.append("- NOMBRE: [").append(anterior.getNombre())
                            .append("] -> [").append(actualizado.getNombre()).append("]\n");
                }

                // Comparar Correo
                if (!anterior.getCorreo().equals(actualizado.getCorreo())) {
                    deltas.append("- CORREO: [").append(anterior.getCorreo())
                            .append("] -> [").append(actualizado.getCorreo()).append("]\n");
                }

                // Comparar Rol
                String rolAnt = anterior.getRolNombre() != null ? anterior.getRolNombre() : "N/A";
                String rolNue = actualizado.getRolNombre() != null ? actualizado.getRolNombre() : "N/A";
                if (!rolAnt.equals(rolNue)) {
                    deltas.append("- ROL: [").append(rolAnt)
                            .append("] -> [").append(rolNue).append("]\n");
                }

                String targetInfo = actualizado.getNombre() + " (" + actualizado.getCorreo() + ")";

                // Si hubo cambio de estatus, usamos el patrón de estatus (CRUD-03)
                if (!anterior.getEstatus().equals(actualizado.getEstatus())) {
                    String transicion = anterior.getEstatus().toUpperCase() + " -> "
                            + actualizado.getEstatus().toUpperCase();
                    bitacoraService.registrarEvento(usuario.getIdUsuario(), "CRUD-03", ip, "USUARIOS", transicion);
                } else {
                    // Si solo fueron datos, usamos el patrón de cambios (CRUD-02)
                    String detalleDeltas = deltas.length() > 0 ? deltas.toString().trim()
                            : "Cambios menores o de sistema";
                    bitacoraService.registrarEvento(usuario.getIdUsuario(), "CRUD-02", ip, "USUARIOS",
                            "REG: " + targetInfo + " | CAMBIOS:\n" + detalleDeltas);
                }

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Usuario actualizado exitosamente",
                        "data", actualizado));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            bitacoraService.registrarEvento(null, "SYS-99", ip, "Error en PUT /api/usuarios/" + id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al actualizar usuario: " + e.getMessage()));
        }
    }

    /**
     * Eliminar (desactivar) usuario
     * DELETE /api/usuarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUsuario(@PathVariable String id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            boolean eliminado = usuarioService.delete(id);

            if (eliminado) {
                // REGISTRO DE EVENTO: DESACTIVACIÓN (PATRON MAESTRO CIFRADO)
                bitacoraService.registrarEvento(null, "CRUD-03", ip, "UserID: " + id, "Active -> Inactive");

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Usuario desactivado exitosamente"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al desactivar usuario: " + e.getMessage()));
        }
    }

    /**
     * Filtrar usuarios por estatus
     * GET /api/usuarios/estatus/{estatus}
     */
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<Usuario>> getUsuariosByEstatus(@PathVariable String estatus) {
        try {
            List<Usuario> usuarios = usuarioService.findByEstatus(estatus);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Filtrar usuarios por rol
     * GET /api/usuarios/rol/{rolId}
     */
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable String rolId) {
        try {
            List<Usuario> usuarios = usuarioService.findByRol(rolId);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Restablecer contraseña de usuario
     * POST /api/usuarios/{id}/reset-password
     */
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(
            @PathVariable String id,
            @RequestBody(required = false) Map<String, String> body,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String motivo = (body != null && body.containsKey("motivo")) ? body.get("motivo") : "Sin motivo especificado";

        try {
            String nuevaPassword = usuarioService.resetPassword(id);

            if (nuevaPassword != null) {
                // REGISTRO DE EVENTO: RESET PASSWORD CON MOTIVO (PATRON MAESTRO CIFRADO)
                bitacoraService.registrarEvento(null, "USR-30", ip, "UserID: " + id, "Motivo: " + motivo);

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Contraseña restablecida correctamente. Copie la nueva contraseña.",
                        "tempPassword", nuevaPassword));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al restablecer contraseña: " + e.getMessage()));
        }
    }

    // ==========================================
    // ENDPOINTS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    @GetMapping("/roles")
    public ResponseEntity<List<Map<String, Object>>> getRoles() {
        return ResponseEntity.ok(usuarioService.getAllRoles());
    }

    @GetMapping("/modulos")
    public ResponseEntity<List<Map<String, Object>>> getModulos() {
        return ResponseEntity.ok(usuarioService.getAllModules());
    }

    @GetMapping("/permisos/{idRol}")
    public ResponseEntity<List<Map<String, Object>>> getPermisosByRol(@PathVariable String idRol) {
        return ResponseEntity.ok(usuarioService.getPermissionsByRol(idRol));
    }

    @PutMapping("/permisos/{idRol}")
    public ResponseEntity<Map<String, Object>> updatePermisos(@PathVariable String idRol,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            for (Map<String, Object> p : permisos) {
                String idModulo = (String) p.get("id_modulo");
                // Manejar posibles nulos o tipos incorrectos (Boolean vs Integer)
                boolean ver = Boolean.TRUE.equals(p.get("puede_ver"));
                boolean crear = Boolean.TRUE.equals(p.get("puede_crear"));
                boolean editar = Boolean.TRUE.equals(p.get("puede_editar"));
                boolean eliminar = Boolean.TRUE.equals(p.get("puede_eliminar"));

                usuarioService.updatePermission(idRol, idModulo, ver, crear, editar, eliminar);
            }
            return ResponseEntity.ok(Map.of("success", true, "message", "Permisos actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al guardar permisos: " + e.getMessage()));
        }
    }

    // ==========================================
    // PERMISOS POR USUARIO (PERSONALIZADOS)
    // ==========================================

    @GetMapping("/permisos-usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> getPermisosByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(usuarioService.getPermissionsByUsuario(idUsuario));
    }

    @PutMapping("/permisos-usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> updatePermisosUsuario(@PathVariable String idUsuario,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            usuarioService.savePermissionsForUser(idUsuario, permisos);
            return ResponseEntity
                    .ok(Map.of("success", true, "message", "Permisos de usuario actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al guardar permisos: " + e.getMessage()));
        }
    }
}
