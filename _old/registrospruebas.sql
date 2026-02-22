USE optica_erp;
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- 1) LIMPIEZA TOTAL (ORDEN FK)
-- =====================================================
DELETE FROM bitacora_seguridad;
DELETE FROM cfdi;
DELETE FROM insumo_ot;
DELETE FROM historial_estado_ot;
DELETE FROM orden_trabajo;
DELETE FROM pago;
DELETE FROM detalle_venta;
DELETE FROM venta;
DELETE FROM movimiento_inventario;
DELETE FROM existencia;
DELETE FROM producto;
DELETE FROM sucursal;
DELETE FROM receta;
DELETE FROM cita;
DELETE FROM expediente;
DELETE FROM optometrista;
DELETE FROM paciente;
DELETE FROM usuario_rol;
DELETE FROM permiso; -- Hija de Rol y Modulo
DELETE FROM rol;     -- Padre
DELETE FROM modulo;  -- Padre
DELETE FROM usuario; -- Padre
DELETE FROM cat_tipo_mov_inv;
DELETE FROM cat_metodo_pago;
DELETE FROM cat_estatus_cfdi;
DELETE FROM cat_estado_ot;
DELETE FROM cat_estado_venta;
DELETE FROM cat_estado_cita;

-- =====================================================
-- 2) CATÁLOGOS
-- =====================================================
INSERT INTO cat_estado_cita VALUES
(UUID(),'Programada'),(UUID(),'Realizada'),(UUID(),'Cancelada');

INSERT INTO cat_estado_venta VALUES
(UUID(),'Borrador'),(UUID(),'Completada'),(UUID(),'Cancelada');

INSERT INTO cat_estado_ot VALUES
(UUID(),'Creada'),(UUID(),'EnProceso'),(UUID(),'Entregada');

INSERT INTO cat_estatus_cfdi VALUES
(UUID(),'Timbrada'),(UUID(),'Cancelada'),(UUID(),'Pendiente');

INSERT INTO cat_metodo_pago VALUES
(UUID(),'Efectivo'),(UUID(),'Tarjeta'),(UUID(),'Transferencia');

INSERT INTO cat_tipo_mov_inv VALUES
(UUID(),'Entrada'),(UUID(),'Salida'),(UUID(),'Ajuste');

-- =====================================================
-- 3) SEGURIDAD (LOGIN COMPLETO CORREGIDO)
-- =====================================================

-- 3.1 MÓDULOS DEL SISTEMA (12 OFICIALES)
INSERT INTO modulo (id_modulo, nombre, descripcion, activo) VALUES
(UUID(), 'LOGIN', 'Acceso al sistema', 1),
(UUID(), 'INVENTARIO', 'Gestión de productos y stock', 1),
(UUID(), 'AGENDA CITAS', 'Agenda y programación', 1),
(UUID(), 'PROVEEDORES', 'Gestión de proveedores', 1),
(UUID(), 'EXPEDIENTE PACIENTE', 'Historial clínico y optometría', 1),
(UUID(), 'CLIENTES', 'Cartera de clientes', 1),
(UUID(), 'VENTAS', 'Punto de venta', 1),
(UUID(), 'ORDENES DE COMPRA', 'Compras y pedidos', 1),
(UUID(), 'TALLER OT', 'Laboratorio y trabajos', 1),
(UUID(), 'RECEPCION Y DEVOLUCION', 'Logística externa', 1),
(UUID(), 'FACTURACION CFDI', 'Facturación electrónica', 1),
(UUID(), 'USUARIOS, ROLES Y PERMISOS', 'Administración de seguridad', 1);

-- 3.2 ROLES ESTÁNDAR
INSERT INTO rol (id_rol, nombre, descripcion) VALUES
(UUID(),'ADMIN', 'Administrador Total'),
(UUID(),'CAJA', 'Cajero / Ventas'),
(UUID(),'OPTOMETRIA', 'Optometrista'),
(UUID(),'TALLER', 'Técnico de Taller');

-- 3.3 USUARIO MASTER
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en) VALUES
(
 UUID(),
 'Administrador General',
 'graxsoft_soporte@hotmail.com',
 'admin', -- Nota: En produccion usar Hash BCrypt real
 1,
 NOW()
);

-- 3.4 ASIGNACIÓN DE ROL MASTER
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol)
SELECT UUID(), u.id_usuario, r.id_rol
FROM usuario u, rol r
WHERE u.email='graxsoft_soporte@hotmail.com'
AND r.nombre='ADMIN';

-- 3.5 PERMISOS DEMO (EJEMPLO PARA ADMIN - ACCESO TOTAL)
-- Se cruza el ROL 'ADMIN' con TODOS los módulos con todos los flags en TRUE
INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(), 
    r.id_rol, 
    NULL, -- Es plantilla de rol
    m.id_modulo, 
    1, 1, 1, 1
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'ADMIN';

-- PERMISOS DEMO PARA CAJA (Solo Ventas e Inventario lectura)
INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 1, 1, 0
FROM rol r, modulo m WHERE r.nombre='CAJA' AND m.nombre='VENTAS';

INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 0, 0, 0
FROM rol r, modulo m WHERE r.nombre='CAJA' AND m.nombre='INVENTARIO';

-- =====================================================
-- 4) SUCURSALES
-- =====================================================
INSERT INTO sucursal VALUES
(UUID(),'Sucursal Centro','9990000001'),
(UUID(),'Sucursal Norte','9990000002');

-- =====================================================
-- 5) PRODUCTOS
-- =====================================================
INSERT INTO producto VALUES
(UUID(),'LENTE-GRAD-01','Lente Graduado 01','LENTE_GRADUADO',900,0.16,1),
(UUID(),'LENTE-GRAD-02','Lente Graduado 02','LENTE_GRADUADO',925,0.16,1),
(UUID(),'ARMAZON-01','Armazón Básico','ARMAZON',450,0.16,1),
(UUID(),'ESTUC-01','Estuche','ACCESORIO',90,0.16,1),
(UUID(),'LIMPIA-01','Kit Limpieza','ACCESORIO',120,0.16,1),
(UUID(),'CORD-01','Cordón','ACCESORIO',35,0.16,1),
(UUID(),'EXAM-01','Examen Visual','SERVICIO',250,0.16,1),
(UUID(),'AJUST-01','Ajuste','SERVICIO',150,0.16,1),
(UUID(),'MICA-01','Mica','INSUMO_TALLER',120,0.16,1),
(UUID(),'TORN-01','Tornillería','INSUMO_TALLER',25,0.16,1);

-- EXISTENCIAS
INSERT INTO existencia
SELECT UUID(), p.id_producto, s.id_sucursal, 50, 5
FROM producto p CROSS JOIN sucursal s;

-- =====================================================
-- 6) PACIENTES (20)
-- =====================================================
INSERT INTO paciente
SELECT
 UUID(),
 CONCAT('Paciente',LPAD(n,2,'0')),
 CONCAT('Apellido',LPAD(n,2,'0')),
 CONCAT('999100',LPAD(n,4,'0')),
 CONCAT('pac',LPAD(n,2,'0'),'@demo.local'),
 CURDATE()
FROM (
 SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10
 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15
 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20
) t;

-- =====================================================
-- 7) OPTOMETRISTAS (3)
-- =====================================================
INSERT INTO optometrista VALUES
(UUID(),'Optometrista A','CED-001','9992000001','optoA@demo.local'),
(UUID(),'Optometrista B','CED-002','9992000002','optoB@demo.local'),
(UUID(),'Optometrista C','CED-003','9992000003','optoC@demo.local');

-- =====================================================
-- 8) EXPEDIENTE + CITA + RECETA (20)
-- =====================================================
INSERT INTO expediente
SELECT UUID(), id_paciente, CURDATE(), 'Expediente demo'
FROM paciente;

INSERT INTO cita
SELECT
 UUID(),
 p.id_paciente,
 (SELECT id_optometrista FROM optometrista ORDER BY nombre LIMIT 1),
 CURDATE(),
 '09:00:00',
 (SELECT id_estado_cita FROM cat_estado_cita WHERE nombre='Realizada'),
 'Examen visual',
 NOW()
FROM paciente p;

INSERT INTO receta
SELECT
 UUID(),
 e.id_expediente,
 -1.00,-0.50,90,
 -0.75,-0.25,80,
 'Receta demo',
 CURDATE(),
 NULL
FROM expediente e;

-- =====================================================
-- 9) VENTAS (35)
-- =====================================================
INSERT INTO venta
SELECT
 UUID(),
 p.id_paciente,
 r.id_receta,
 (SELECT id_sucursal FROM sucursal ORDER BY nombre LIMIT 1),
 (SELECT id_estado_venta FROM cat_estado_venta WHERE nombre='Completada'),
 NOW(),
 0
FROM paciente p
JOIN receta r ON r.id_expediente IN (SELECT id_expediente FROM expediente WHERE id_paciente=p.id_paciente)
LIMIT 20;

INSERT INTO venta
SELECT
 UUID(),
 id_paciente,
 NULL,
 (SELECT id_sucursal FROM sucursal ORDER BY nombre DESC LIMIT 1),
 (SELECT id_estado_venta FROM cat_estado_venta WHERE nombre='Completada'),
 NOW(),
 0
FROM paciente
LIMIT 15;

-- =====================================================
-- 10) DETALLE_VENTA
-- =====================================================
INSERT INTO detalle_venta
SELECT UUID(), v.id_venta, p.id_producto, 1, p.precio_unit, p.precio_unit
FROM venta v
JOIN producto p ON p.categoria='LENTE_GRADUADO'
WHERE v.id_receta IS NOT NULL;

-- =====================================================
-- 11) ORDEN TRABAJO + HISTORIAL + INSUMOS
-- =====================================================
INSERT INTO orden_trabajo
SELECT UUID(), v.id_venta,
(SELECT id_estado_ot FROM cat_estado_ot WHERE nombre='Creada'),
'Lentes graduados', NOW()
FROM venta v WHERE v.id_receta IS NOT NULL;

INSERT INTO historial_estado_ot
SELECT UUID(), o.id_ot,
(SELECT id_estado_ot FROM cat_estado_ot WHERE nombre='Entregada'),
NOW(),'Finalizado'
FROM orden_trabajo o;

INSERT INTO insumo_ot
SELECT UUID(), o.id_ot, p.id_producto, 1
FROM orden_trabajo o
JOIN producto p ON p.categoria='INSUMO_TALLER';

-- =====================================================
-- 12) PAGOS
-- =====================================================
INSERT INTO pago
SELECT UUID(), v.id_venta,
(SELECT id_metodo_pago FROM cat_metodo_pago WHERE nombre='Efectivo'),
v.total,NULL,NOW()
FROM venta v;

-- =====================================================
-- 13) CFDI (10)
-- =====================================================
INSERT INTO cfdi
SELECT UUID(), CONCAT('UUID-',UUID()), v.id_venta,
(SELECT id_estatus_cfdi FROM cat_estatus_cfdi WHERE nombre='Timbrada'),
'XAXX010101000','G03',v.total,NULL,NULL
FROM venta v LIMIT 10;

-- =====================================================
-- 14) BITÁCORA SEGURIDAD (6)
-- =====================================================
INSERT INTO bitacora_seguridad VALUES
(UUID(),(SELECT id_usuario FROM usuario WHERE email='graxsoft_soporte@hotmail.com'),'LOGIN','127.0.0.1','Login admin',NOW()),
(UUID(),(SELECT id_usuario FROM usuario WHERE email='graxsoft_soporte@hotmail.com'),'LOGOUT','127.0.0.1','Logout admin',NOW()),
(UUID(),NULL,'LOGIN','127.0.0.1','Caja login',NOW()),
(UUID(),NULL,'LOGOUT','127.0.0.1','Caja logout',NOW()),
(UUID(),NULL,'LOGIN','127.0.0.1','Opto login',NOW()),
(UUID(),NULL,'LOGIN','127.0.0.1','Taller login',NOW());

SET FOREIGN_KEY_CHECKS = 1;
