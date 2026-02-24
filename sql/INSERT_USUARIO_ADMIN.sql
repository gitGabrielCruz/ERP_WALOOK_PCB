-- =====================================================
-- INSERTAR DATOS MÍNIMOS PARA MÓDULO DE USUARIOS
-- Ejecutar DESPUÉS de crear las tablas con tu script principal
-- =====================================================

USE optica_erp;

-- =====================================================
-- 1) INSERTAR ROL ADMIN (si no existe)
-- =====================================================
INSERT INTO rol (id_rol, nombre)
SELECT UUID(), 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ADMIN');

-- =====================================================
-- 2) INSERTAR USUARIO ADMIN (si no existe)
-- =====================================================
-- Password: admin (hasheado con BCrypt)
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en)
SELECT 
    UUID(),
    'Administrador',
    'admin@optica.mx',
    '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma',
    1,
    NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE email = 'admin@optica.mx');

-- =====================================================
-- 3) ASIGNAR ROL AL USUARIO (si no existe)
-- =====================================================
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol)
SELECT 
    UUID(),
    u.id_usuario,
    r.id_rol
FROM usuario u
CROSS JOIN rol r
WHERE u.email = 'admin@optica.mx'
  AND r.nombre = 'ADMIN'
  AND NOT EXISTS (
      SELECT 1 
      FROM usuario_rol ur2 
      WHERE ur2.id_usuario = u.id_usuario 
        AND ur2.id_rol = r.id_rol
  );

-- =====================================================
-- 4) VERIFICAR INSERCIÓN
-- =====================================================
SELECT 
    u.id_usuario,
    u.nombre,
    u.email,
    u.activo,
    r.nombre as rol
FROM usuario u
LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
LEFT JOIN rol r ON ur.id_rol = r.id_rol
WHERE u.email = 'admin@optica.mx';

-- Resultado esperado:
-- Debe mostrar 1 fila con el usuario admin@optica.mx y rol ADMIN
