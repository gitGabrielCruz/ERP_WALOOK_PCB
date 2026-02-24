-- ========================================================
-- Script de Corrección de Permisos Iniciales (Seed Data)
-- Proyecto: ERP WALOOK
-- Objetivo: Asignar permisos base a roles OPTOMETRIA y TALLER
-- ========================================================

USE optica_erp;

-- 1. Permisos para OPTOMETRIA
-- Acceso a: AGENDA CITAS, EXPEDIENTE PACIENTE, CLIENTES (Lectura/Escritura)
-- Acceso a: INVENTARIO (Solo Lectura - Consultar armazones)

INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 1, 1, 0
FROM rol r, modulo m 
WHERE r.nombre='OPTOMETRIA' 
AND m.nombre IN ('AGENDA CITAS', 'EXPEDIENTE PACIENTE', 'CLIENTES');

INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 0, 0, 0
FROM rol r, modulo m 
WHERE r.nombre='OPTOMETRIA' 
AND m.nombre = 'INVENTARIO';


-- 2. Permisos para TALLER
-- Acceso a: TALLER OT (Total), INVENTARIO (Lectura), RECEPCION Y DEVOLUCION (Lectura/Escritura)

INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 1, 1, 0
FROM rol r, modulo m 
WHERE r.nombre='TALLER' 
AND m.nombre IN ('TALLER OT', 'RECEPCION Y DEVOLUCION');

INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 0, 0, 0
FROM rol r, modulo m 
WHERE r.nombre='TALLER' 
AND m.nombre IN ('INVENTARIO', 'ORDENES DE COMPRA');

-- Confirmación visual
SELECT r.nombre as ROL, m.nombre as MODULO, p.puede_ver, p.puede_crear 
FROM permiso p 
JOIN rol r ON p.id_rol = r.id_rol 
JOIN modulo m ON p.id_modulo = m.id_modulo
WHERE r.nombre IN ('OPTOMETRIA', 'TALLER')
ORDER BY r.nombre, m.nombre;
