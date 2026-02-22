/*
============================================================
Nombre del archivo : UsuarioRepository.java
Ruta              : backend/src/main/java/com/omcgc/erp/repository/UsuarioRepository.java
Tipo              : Backend (Repositorio de Datos)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v3.0

Propósito:
Repositorio completo para gestión de usuarios con sintaxis correcta de JdbcTemplate.
============================================================
*/
package com.omcgc.erp.repository;

import com.omcgc.erp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper para mapear resultados de consultas con JOIN
     */
    private static final RowMapper<Usuario> USUARIO_ROW_MAPPER = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getString("id_usuario"));
        u.setUsuario(rs.getString("nombre"));
        u.setCorreo(rs.getString("email"));
        u.setPassword(rs.getString("password_hash"));
        u.setNombre(rs.getString("nombre"));
        u.setEstatus(rs.getBoolean("activo") ? "activo" : "inactivo");

        // Campos de rol (si existen en el JOIN)
        try {
            u.setRolId(rs.getString("id_rol"));
            u.setRolNombre(rs.getString("nombre_rol"));
        } catch (SQLException e) {
            // Ignorar si no están en la consulta
        }

        return u;
    };

    /**
     * Obtener todos los usuarios con sus roles
     */
    public List<Usuario> findAll() {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                ORDER BY u.nombre
                """;

        return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER);
    }

    /**
     * Buscar usuario por ID
     */
    public Usuario findById(String id) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                WHERE u.id_usuario = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, USUARIO_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Buscar usuario por email
     */
    public Usuario findByEmail(String email) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                WHERE u.email = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, USUARIO_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Crear nuevo usuario
     */
    public Usuario save(Usuario usuario) {
        // Generar ID si no existe
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            usuario.setId(UUID.randomUUID().toString());
        }

        String sqlUsuario = """
                INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en)
                VALUES (?, ?, ?, ?, ?, NOW())
                """;

        try {
            jdbcTemplate.update(sqlUsuario,
                    usuario.getId(),
                    usuario.getNombre(), // Corrección: Usar nombre real, no username
                    usuario.getCorreo(),
                    usuario.getPassword(),
                    usuario.isActivo() ? 1 : 0);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado: " + usuario.getCorreo());
        }

        // Asignar rol si existe
        if (usuario.getRolNombre() != null && !usuario.getRolNombre().isEmpty()) {
            asignarRol(usuario.getId(), usuario.getRolNombre());
        }

        return findById(usuario.getId());
    }

    /**
     * Actualizar usuario existente
     */
    public Usuario update(Usuario usuario) {
        String sql = """
                UPDATE usuario
                SET nombre = ?, email = ?, password_hash = ?, activo = ?
                WHERE id_usuario = ?
                """;

        try {
            jdbcTemplate.update(sql,
                    usuario.getNombre(), // Corrección: Usar nombre real, no username
                    usuario.getCorreo(),
                    usuario.getPassword(),
                    usuario.isActivo() ? 1 : 0,
                    usuario.getId());
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado: " + usuario.getCorreo());
        }

        // Actualizar rol (Prioridad: ID > Nombre)
        if (usuario.getRolId() != null && !usuario.getRolId().isEmpty()) {
            // 1. Eliminar roles anteriores
            jdbcTemplate.update("DELETE FROM usuario_rol WHERE id_usuario = ?", usuario.getId());

            // 2. Insertar nueva relación usando ID directo
            String sqlUsuarioRol = "INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) VALUES (UUID(), ?, ?)";
            jdbcTemplate.update(sqlUsuarioRol, usuario.getId(), usuario.getRolId());
        } else if (usuario.getRolNombre() != null && !usuario.getRolNombre().isEmpty()) {
            // Fallback: Buscar por nombre
            jdbcTemplate.update("DELETE FROM usuario_rol WHERE id_usuario = ?", usuario.getId());
            asignarRol(usuario.getId(), usuario.getRolNombre());
        }

        return findById(usuario.getId());
    }

    /**
     * Asignar rol a usuario
     */
    private void asignarRol(String idUsuario, String nombreRol) {
        // Buscar ID del rol
        String sqlRol = "SELECT id_rol FROM rol WHERE nombre = ?";
        try {
            String idRol = jdbcTemplate.queryForObject(sqlRol, String.class, nombreRol);

            // Insertar relación usuario-rol
            String sqlUsuarioRol = """
                    INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol)
                    VALUES (?, ?, ?)
                    """;

            jdbcTemplate.update(sqlUsuarioRol, UUID.randomUUID().toString(), idUsuario, idRol);
        } catch (EmptyResultDataAccessException e) {
            // Rol no existe, ignorar
            System.err.println("Rol no encontrado: " + nombreRol);
        }
    }

    /**
     * Desactivar usuario (soft delete)
     */
    public void delete(String id) {
        String sql = "UPDATE usuario SET activo = 0 WHERE id_usuario = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * Actualizar contraseña
     */
    public void updatePassword(String id, String passwordHash) {
        String sql = "UPDATE usuario SET password_hash = ? WHERE id_usuario = ?";
        jdbcTemplate.update(sql, passwordHash, id);
    }

    /**
     * Buscar usuarios por estatus
     */
    public List<Usuario> findByEstatus(boolean activo) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                WHERE u.activo = ?
                ORDER BY u.nombre
                """;

        return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER, activo ? 1 : 0);
    }

    /**
     * Buscar usuarios por rol
     */
    public List<Usuario> findByRol(String rolId) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                INNER JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                INNER JOIN rol r ON ur.id_rol = r.id_rol
                WHERE r.id_rol = ?
                ORDER BY u.nombre
                """;

        return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER, rolId);
    }

    // ==========================================
    // MÉTODOS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    public List<java.util.Map<String, Object>> findAllRoles() {
        return jdbcTemplate.queryForList("SELECT id_rol, nombre, descripcion FROM rol ORDER BY nombre");
    }

    public List<java.util.Map<String, Object>> findAllModules() {
        return jdbcTemplate
                .queryForList("SELECT id_modulo, nombre, descripcion FROM modulo WHERE activo = 1 ORDER BY nombre");
    }

    public List<java.util.Map<String, Object>> findPermissionsByRol(String idRol) {
        String sql = """
                SELECT p.id_permiso, m.id_modulo, m.nombre as modulo,
                       p.puede_ver, p.puede_crear, p.puede_editar, p.puede_eliminar
                FROM permiso p
                JOIN modulo m ON p.id_modulo = m.id_modulo
                WHERE p.id_rol = ?
                ORDER BY m.nombre
                """;
        return jdbcTemplate.queryForList(sql, idRol);
    }

    // Nuevo: Permisos por Usuario (Prioridad)
    public List<java.util.Map<String, Object>> findPermissionsByUsuario(String idUsuario) {
        String sql = """
                SELECT u.id_usuario, m.nombre as modulo, p.id_modulo,
                       p.puede_ver, p.puede_crear, p.puede_editar, p.puede_eliminar
                FROM usuario u
                INNER JOIN permiso p ON u.id_usuario = p.id_usuario
                INNER JOIN modulo m ON p.id_modulo = m.id_modulo
                WHERE u.id_usuario = ?
                ORDER BY m.nombre
                """;
        return jdbcTemplate.queryForList(sql, idUsuario);
    }

    public void updatePermission(String idRol, String idModulo, boolean ver, boolean crear, boolean editar,
            boolean eliminar) {
        String sql = """
                UPDATE permiso
                SET puede_ver=?, puede_crear=?, puede_editar=?, puede_eliminar=?
                WHERE id_rol=? AND id_modulo=?
                """;
        int updated = jdbcTemplate.update(sql, ver ? 1 : 0, crear ? 1 : 0, editar ? 1 : 0, eliminar ? 1 : 0, idRol,
                idModulo);

        if (updated == 0) {
            // Si no existe, insertar (defensivo)
            String sqlInsert = """
                    INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
                    VALUES (UUID(), ?, ?, ?, ?, ?, ?)
                    """;
            jdbcTemplate.update(sqlInsert, idRol, idModulo, ver ? 1 : 0, crear ? 1 : 0, editar ? 1 : 0,
                    eliminar ? 1 : 0);
        }
    }

    public void saveUserPermissions(String idUsuario, List<java.util.Map<String, Object>> permisos) {
        jdbcTemplate.update("DELETE FROM permiso WHERE id_usuario = ?", idUsuario);

        String sqlInsert = """
                INSERT INTO permiso (id_permiso, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
                VALUES (UUID(), ?, ?, ?, ?, ?, ?)
                """;

        for (java.util.Map<String, Object> p : permisos) {
            jdbcTemplate.update(sqlInsert,
                    idUsuario,
                    p.get("id_modulo"),
                    toBool(p.get("puede_ver")),
                    toBool(p.get("puede_crear")),
                    toBool(p.get("puede_editar")),
                    toBool(p.get("puede_eliminar")));
        }
    }

    private boolean toBool(Object val) {
        if (val == null)
            return false;
        if (val instanceof Boolean)
            return (Boolean) val;
        if (val instanceof String)
            return "true".equalsIgnoreCase((String) val);
        if (val instanceof Number)
            return ((Number) val).intValue() == 1;
        return false;
    }
}
