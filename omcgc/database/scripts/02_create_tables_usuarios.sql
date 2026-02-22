/*
============================================================
Nombre del archivo : 02_create_tables_usuarios.sql
Ruta              : omcgc/database/scripts/02_create_tables_usuarios.sql
Tipo              : Base de Datos (Script SQL DDL)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Crear las tablas necesarias para el módulo de Gestión de Usuarios, Roles y Permisos.
============================================================
*/

USE db_omcgc_erp;

-- =========================================================
-- TABLA: rol
-- Catálogo de roles del sistema
-- =========================================================
CREATE TABLE IF NOT EXISTS rol (
    id_rol VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: usuario
-- Almacena información de usuarios del sistema
-- =========================================================
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: usuario_rol
-- Relación N:M entre usuarios y roles
-- =========================================================
CREATE TABLE IF NOT EXISTS usuario_rol (
    id_usuario VARCHAR(36) NOT NULL,
    id_rol VARCHAR(36) NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: modulo
-- Catálogo de módulos del sistema
-- =========================================================
CREATE TABLE IF NOT EXISTS modulo (
    id_modulo VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: permiso
-- Permisos específicos por módulo y rol
-- =========================================================
CREATE TABLE IF NOT EXISTS permiso (
    id_permiso VARCHAR(36) PRIMARY KEY,
    id_rol VARCHAR(36) NOT NULL,
    id_modulo VARCHAR(36) NOT NULL,
    puede_ver BOOLEAN DEFAULT FALSE,
    puede_crear BOOLEAN DEFAULT FALSE,
    puede_editar BOOLEAN DEFAULT FALSE,
    puede_eliminar BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE,
    FOREIGN KEY (id_modulo) REFERENCES modulo(id_modulo) ON DELETE CASCADE,
    UNIQUE KEY unique_rol_modulo (id_rol, id_modulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- DATOS SEMILLA - ROLES
-- =========================================================
INSERT INTO rol (id_rol, nombre, descripcion) VALUES
('ROL-001', 'Administrador', 'Acceso total al sistema'),
('ROL-002', 'Facturista', 'Gestión de ventas y facturación'),
('ROL-003', 'Recepcionista', 'Gestión de agenda y atención al cliente'),
('ROL-004', 'Almacenista', 'Gestión de inventario y proveedores')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- =========================================================
-- DATOS SEMILLA - MÓDULOS
-- =========================================================
INSERT INTO modulo (id_modulo, nombre, descripcion) VALUES
('MOD-001', 'Inventario', 'Gestión de productos y stock'),
('MOD-002', 'Ventas', 'Punto de venta y cotizaciones'),
('MOD-003', 'Agenda', 'Programación de citas'),
('MOD-004', 'Facturación', 'Emisión de CFDI'),
('MOD-005', 'Reportes', 'Consultas y estadísticas'),
('MOD-006', 'Configuración', 'Administración del sistema')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- =========================================================
-- DATOS SEMILLA - USUARIO ROOT (Super Admin)
-- Password: root (hash BCrypt)
-- =========================================================
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo) VALUES
('USR-ROOT', 'Super Administrador', 'root@optica.mx', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma', TRUE)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- Asignar rol de Administrador al usuario root
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
('USR-ROOT', 'ROL-001')
ON DUPLICATE KEY UPDATE id_rol=VALUES(id_rol);

-- =========================================================
-- DATOS SEMILLA - PERMISOS PARA ADMINISTRADOR
-- =========================================================
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) VALUES
('PERM-001', 'ROL-001', 'MOD-001', TRUE, TRUE, TRUE, TRUE),
('PERM-002', 'ROL-001', 'MOD-002', TRUE, TRUE, TRUE, TRUE),
('PERM-003', 'ROL-001', 'MOD-003', TRUE, TRUE, TRUE, TRUE),
('PERM-004', 'ROL-001', 'MOD-004', TRUE, TRUE, TRUE, TRUE),
('PERM-005', 'ROL-001', 'MOD-005', TRUE, TRUE, TRUE, TRUE),
('PERM-006', 'ROL-001', 'MOD-006', TRUE, TRUE, TRUE, TRUE)
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =========================================================
-- DATOS SEMILLA - PERMISOS PARA FACTURISTA
-- =========================================================
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) VALUES
('PERM-007', 'ROL-002', 'MOD-001', TRUE, FALSE, FALSE, FALSE),
('PERM-008', 'ROL-002', 'MOD-002', TRUE, TRUE, TRUE, FALSE),
('PERM-009', 'ROL-002', 'MOD-003', FALSE, FALSE, FALSE, FALSE),
('PERM-010', 'ROL-002', 'MOD-004', TRUE, TRUE, FALSE, FALSE),
('PERM-011', 'ROL-002', 'MOD-005', TRUE, FALSE, FALSE, FALSE),
('PERM-012', 'ROL-002', 'MOD-006', FALSE, FALSE, FALSE, FALSE)
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =========================================================
-- DATOS SEMILLA - PERMISOS PARA RECEPCIONISTA
-- =========================================================
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) VALUES
('PERM-013', 'ROL-003', 'MOD-001', TRUE, FALSE, FALSE, FALSE),
('PERM-014', 'ROL-003', 'MOD-002', TRUE, FALSE, FALSE, FALSE),
('PERM-015', 'ROL-003', 'MOD-003', TRUE, TRUE, TRUE, FALSE),
('PERM-016', 'ROL-003', 'MOD-004', FALSE, FALSE, FALSE, FALSE),
('PERM-017', 'ROL-003', 'MOD-005', FALSE, FALSE, FALSE, FALSE),
('PERM-018', 'ROL-003', 'MOD-006', FALSE, FALSE, FALSE, FALSE)
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);
