-- =====================================================
-- SCRIPT COMPLEMENTARIO: Módulo de Usuarios
-- Agrega lo necesario para que funcione el módulo de gestión de usuarios
-- =====================================================

USE db_omcgc_erp;

-- =====================================================
-- 1) CORREGIR PASSWORD DEL USUARIO ADMIN
-- =====================================================
-- Password: admin (hasheado con BCrypt)
UPDATE usuario 
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma'
WHERE email = 'graxsoft_soporte@hotmail.com';

-- =====================================================
-- 2) CREAR TABLA MODULO (si no existe)
-- =====================================================
CREATE TABLE IF NOT EXISTS modulo (
    id_modulo CHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- =====================================================
-- 3) INSERTAR MÓDULOS DEL SISTEMA
-- =====================================================
INSERT INTO modulo (id_modulo, nombre, descripcion, activo) VALUES
(UUID(), 'Inventario', 'Gestión de productos y stock', 1),
(UUID(), 'Ventas', 'Punto de venta y cotizaciones', 1),
(UUID(), 'Agenda', 'Programación de citas', 1),
(UUID(), 'Facturación', 'Emisión de CFDI', 1),
(UUID(), 'Reportes', 'Consultas y estadísticas', 1),
(UUID(), 'Configuración', 'Administración del sistema', 1)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- =====================================================
-- 4) AGREGAR CAMPOS A TABLA PERMISO (si no existen)
-- =====================================================
-- Verificar si la tabla permiso tiene la estructura correcta
-- Si no, necesitarás recrearla o alterarla manualmente

-- Opción A: Si quieres mantener compatibilidad, agrega una nueva tabla
CREATE TABLE IF NOT EXISTS permiso_modulo (
    id_permiso_modulo CHAR(36) PRIMARY KEY,
    id_rol CHAR(36) NOT NULL,
    id_modulo CHAR(36) NOT NULL,
    puede_ver TINYINT(1) DEFAULT 0,
    puede_crear TINYINT(1) DEFAULT 0,
    puede_editar TINYINT(1) DEFAULT 0,
    puede_eliminar TINYINT(1) DEFAULT 0,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE,
    FOREIGN KEY (id_modulo) REFERENCES modulo(id_modulo) ON DELETE CASCADE,
    UNIQUE KEY unique_rol_modulo (id_rol, id_modulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- =====================================================
-- 5) ASIGNAR PERMISOS AL ROL ADMIN
-- =====================================================
INSERT INTO permiso_modulo (id_permiso_modulo, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(),
    r.id_rol,
    m.id_modulo,
    1, 1, 1, 1
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'ADMIN'
ON DUPLICATE KEY UPDATE puede_ver=1, puede_crear=1, puede_editar=1, puede_eliminar=1;

-- =====================================================
-- 6) ASIGNAR PERMISOS AL ROL CAJA
-- =====================================================
INSERT INTO permiso_modulo (id_permiso_modulo, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(),
    r.id_rol,
    m.id_modulo,
    CASE m.nombre
        WHEN 'Inventario' THEN 1
        WHEN 'Ventas' THEN 1
        WHEN 'Facturación' THEN 1
        WHEN 'Reportes' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Ventas' THEN 1
        WHEN 'Facturación' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Ventas' THEN 1
        ELSE 0
    END,
    0
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'CAJA'
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =====================================================
-- 7) ASIGNAR PERMISOS AL ROL OPTOMETRIA
-- =====================================================
INSERT INTO permiso_modulo (id_permiso_modulo, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(),
    r.id_rol,
    m.id_modulo,
    CASE m.nombre
        WHEN 'Inventario' THEN 1
        WHEN 'Ventas' THEN 1
        WHEN 'Agenda' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Agenda' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Agenda' THEN 1
        ELSE 0
    END,
    0
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'OPTOMETRIA'
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =====================================================
-- FIN DEL SCRIPT COMPLEMENTARIO
-- =====================================================

SELECT 'Script complementario ejecutado correctamente' AS Resultado;
