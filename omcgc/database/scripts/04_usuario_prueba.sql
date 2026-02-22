-- =====================================================
-- SCRIPT MÍNIMO: Solo tablas para módulo de usuarios
-- Ejecutar DESPUÉS del script principal de creación de BD
-- =====================================================

USE db_omcgc_erp;

-- Verificar que las tablas existan, si no, crearlas
-- (El script principal ya debería haberlas creado)

-- Si necesitas recrear solo las tablas de usuarios:
-- DROP TABLE IF EXISTS usuario_rol;
-- DROP TABLE IF EXISTS usuario;
-- DROP TABLE IF EXISTS rol;

-- Las tablas ya deberían existir del script principal
-- Solo vamos a insertar UN usuario de prueba

-- =====================================================
-- INSERTAR USUARIO DE PRUEBA
-- =====================================================

-- Primero, insertar un rol si no existe
INSERT INTO rol (id_rol, nombre) 
VALUES (UUID(), 'ADMIN')
ON DUPLICATE KEY UPDATE nombre='ADMIN';

-- Insertar usuario de prueba
-- Email: admin@test.com
-- Password: admin (en texto plano por ahora, el backend lo hasheará)
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en)
VALUES (
    UUID(),
    'Administrador de Prueba',
    'admin@test.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma', -- BCrypt hash de "admin"
    1,
    NOW()
)
ON DUPLICATE KEY UPDATE nombre='Administrador de Prueba';

-- Asignar rol al usuario
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol)
SELECT 
    UUID(),
    u.id_usuario,
    r.id_rol
FROM usuario u
CROSS JOIN rol r
WHERE u.email = 'admin@test.com'
  AND r.nombre = 'ADMIN'
ON DUPLICATE KEY UPDATE id_rol=VALUES(id_rol);

-- Verificar que se insertó
SELECT 
    u.id_usuario,
    u.nombre,
    u.email,
    u.activo,
    r.nombre as rol
FROM usuario u
LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
LEFT JOIN rol r ON ur.id_rol = r.id_rol
WHERE u.email = 'admin@test.com';
