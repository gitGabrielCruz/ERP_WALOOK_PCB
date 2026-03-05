/*
============================================================
Nombre del archivo : UsuarioService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/UsuarioService.java
Tipo              : Backend (Service Layer)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Capa de lógica de negocio para la gestión de usuarios. Implementa
validaciones, encriptación de contraseñas y reglas de negocio del módulo 1.3.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-03 (Seguridad y Acceso):
   - Ciclo de vida del usuario (CRUD).
   - Gestión de identidades y contraseñas temporales.
   - Orquestación de roles y permisos granulares.
============================================================
*/

package com.omcgc.erp.service;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Obtener todos los usuarios
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Buscar usuario por ID
     */
    public Usuario findById(String id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Crear nuevo usuario
     */
    public Usuario create(Usuario usuario) {
        // Validaciones

        // Autogenerar username si no viene en la petición (Frontend solo envía Nombre y
        // Correo)
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            if (usuario.getCorreo() != null && !usuario.getCorreo().trim().isEmpty()) {
                String generatedUser = usuario.getCorreo().split("@")[0];
                // Limpiar caracteres especiales si fuera necesario
                usuario.setUsuario(generatedUser);
            } else {
                throw new IllegalArgumentException("El nombre de usuario o correo es obligatorio");
            }
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        // Verificar si el correo ya existe
        Usuario existente = usuarioRepository.findByEmail(usuario.getCorreo());
        if (existente != null) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        // Generar ID único
        usuario.setId(UUID.randomUUID().toString());

        // Encriptar contraseña (si viene en el objeto, sino generar temporal)
        String passwordTemporal = usuario.getPassword() != null ? usuario.getPassword() : generarPasswordTemporal();
        usuario.setPassword(passwordEncoder.encode(passwordTemporal));

        // Asignar contraseña temporal para frontend
        usuario.setPasswordTemp(passwordTemporal);

        if (usuario.getEstatus() == null) {
            usuario.setEstatus("activo");
        }

        // Guardar en base de datos
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualizar usuario existente
     */
    public Usuario update(Usuario usuario) {
        Usuario existente = usuarioRepository.findById(usuario.getId());

        if (existente == null) {
            return null;
        }

        // Actualizar solo campos permitidos (no password ni usuario)
        if (usuario.getNombre() != null) {
            existente.setNombre(usuario.getNombre());
        }
        if (usuario.getCorreo() != null) {
            existente.setCorreo(usuario.getCorreo());
        }
        if (usuario.getRolId() != null) {
            existente.setRolId(usuario.getRolId());
        }
        if (usuario.getRolNombre() != null) {
            existente.setRolNombre(usuario.getRolNombre());
        }
        if (usuario.getEstatus() != null) {
            existente.setEstatus(usuario.getEstatus());
        }

        return usuarioRepository.update(existente);
    }

    /**
     * Eliminar (desactivar) usuario
     */
    public boolean delete(String id) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            return false;
        }

        // No eliminar físicamente, solo desactivar
        usuario.setEstatus("inactivo");
        usuarioRepository.update(usuario);

        return true;
    }

    /**
     * Buscar usuarios por estatus
     */
    public List<Usuario> findByEstatus(String estatus) {
        boolean activo = "activo".equalsIgnoreCase(estatus);
        return usuarioRepository.findByEstatus(activo);
    }

    /**
     * Buscar usuarios por rol
     */
    public List<Usuario> findByRol(String rolId) {
        return usuarioRepository.findByRol(rolId);
    }

    @Autowired
    private EmailService emailService;

    /**
     * Restablecer contraseña de usuario
     */
    public String resetPassword(String id) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            return null;
        }

        // 1. Generar nueva contraseña temporal
        String nuevaPassword = generarPasswordTemporal();
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.update(usuario);

        // 2. Intentar envío de correo con la nueva clave
        try {
            String subject = "🔐 Restablecimiento de Acceso - Óptica ERP";
            String body = String.format(
                    "Hola %s,\n\nSe ha generado una nueva contraseña temporal para tu acceso al sistema Óptica ERP.\n\n"
                            +
                            "Nueva Contraseña: %s\n\n" +
                            "Por tu seguridad, te recomendamos cambiarla en tu próximo inicio de sesión.\n\n" +
                            "Atentamente,\nSoporte Técnico WALOOK",
                    usuario.getNombre(), nuevaPassword);

            emailService.sendEmail(usuario.getCorreo(), subject, body);
            System.out.println("✅ Correo de reset enviado a: " + usuario.getCorreo());
        } catch (Exception e) {
            System.err.println("❌ Error enviando correo de reset: " + e.getMessage());
            // No bloqueamos el proceso si falla el correo, devolvemos la clave de todos
            // modos
        }

        System.out.println("Nueva contraseña para " + usuario.getUsuario() + ": " + nuevaPassword);
        return nuevaPassword;
    }

    /**
     * Generar contraseña temporal aleatoria
     */
    private String generarPasswordTemporal() {
        return "Temp" + UUID.randomUUID().toString().substring(0, 8) + "!";
    }

    /**
     * Validar credenciales (usado en login)
     */
    public Usuario validateCredentials(String email, String password) {
        Usuario user = usuarioRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    // ==========================================
    // MÉTODOS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    public List<java.util.Map<String, Object>> getAllRoles() {
        return usuarioRepository.findAllRoles();
    }

    public List<java.util.Map<String, Object>> getAllModules() {
        return usuarioRepository.findAllModules();
    }

    public List<java.util.Map<String, Object>> getPermissionsByRol(String idRol) {
        return usuarioRepository.findPermissionsByRol(idRol);
    }

    public void updatePermission(String idRol, String idModulo, boolean ver, boolean crear, boolean editar,
            boolean eliminar) {
        usuarioRepository.updatePermission(idRol, idModulo, ver, crear, editar, eliminar);
    }

    // Nuevo: Lógica de permisos por usuario
    public List<java.util.Map<String, Object>> getPermissionsByUsuario(String idUsuario) {
        // 1. Buscar permisos específicos
        List<java.util.Map<String, Object>> permisos = usuarioRepository.findPermissionsByUsuario(idUsuario);

        // 2. Si no tiene (legacy), devolver los del ROL
        if (permisos.isEmpty()) {
            Usuario u = findById(idUsuario);
            if (u != null && u.getRolId() != null) {
                return usuarioRepository.findPermissionsByRol(u.getRolId());
            }
        }
        return permisos;
    }

    @org.springframework.transaction.annotation.Transactional
    public Usuario saveUserWithPermissions(Usuario usuario, List<java.util.Map<String, Object>> permisos) {
        Usuario savedUser;
        // Determinar si es Update o Create
        if (usuario.getId() != null && usuarioRepository.findById(usuario.getId()) != null) {
            savedUser = update(usuario);
        } else {
            savedUser = create(usuario);
        }

        // Guardar permisos vinculados al usuario
        if (savedUser != null && permisos != null) {
            usuarioRepository.saveUserPermissions(savedUser.getId(), permisos);
        }

        return savedUser;
    }

    public void savePermissionsForUser(String idUsuario, List<java.util.Map<String, Object>> permisos) {
        usuarioRepository.saveUserPermissions(idUsuario, permisos);
    }
}
