USE db_omcgc_erp;
-- =========================================================================================
-- SCRIPT MAESTRO DE DATOS (DML) - RESTAURACIÓN TOTAL VALIDADA (Seguridad, Pacientes, Proveedores)
-- =========================================================================================
SET FOREIGN_KEY_CHECKS = 0;

-- #########################################################################################
-- SECCIÓN 1: LIMPIEZA ABSOLUTA
-- #########################################################################################

DELETE FROM bitacora_seguridad;
DELETE FROM cfdi;
DELETE FROM insumo_ot;
DELETE FROM historial_estado_ot;
DELETE FROM orden_trabajo;
DELETE FROM pago;
DELETE FROM detalle_venta;
DELETE FROM venta;
DELETE FROM receta;
DELETE FROM cita;
DELETE FROM expediente;

DELETE FROM movimiento_inventario;
DELETE FROM existencia;
DELETE FROM producto;
DELETE FROM cat_familia;
DELETE FROM cat_grupo;
DELETE FROM cat_marca;

DELETE FROM paciente;
DELETE FROM optometrista;
DELETE FROM proveedor;
DELETE FROM sucursal;
DELETE FROM permiso;
DELETE FROM usuario_rol;
DELETE FROM usuario;
DELETE FROM rol;
DELETE FROM modulo;

DELETE FROM cat_estado_cita;
DELETE FROM cat_estado_venta;
DELETE FROM cat_estado_ot;
DELETE FROM cat_estatus_cfdi;
DELETE FROM cat_metodo_pago;

-- #########################################################################################
-- SECCIÓN 2: DATOS SEMILLA VALIDADOS
-- #########################################################################################

-- 2.1 Catálogos Estructurales
INSERT INTO cat_estado_cita (id_estado_cita, nombre) VALUES (UUID(),'Programada'),(UUID(),'Realizada'),(UUID(),'Cancelada');
INSERT INTO cat_estado_venta (id_estado_venta, nombre) VALUES (UUID(),'Borrador'),(UUID(),'Completada'),(UUID(),'Cancelada');
INSERT INTO cat_estado_ot (id_estado_ot, nombre) VALUES (UUID(),'Creada'),(UUID(),'EnProceso'),(UUID(),'Entregada');
INSERT INTO cat_estatus_cfdi (id_estatus_cfdi, nombre) VALUES (UUID(),'Timbrada'),(UUID(),'Cancelada'),(UUID(),'Pendiente');
INSERT INTO cat_metodo_pago (id_metodo_pago, nombre) VALUES (UUID(),'Efectivo'),(UUID(),'Tarjeta Crédito'),(UUID(),'Tarjeta Débito'),(UUID(),'Transferencia');

-- 2.2 Sucursales (UUIDs fijos — alineados con AuthService.java createSuperAdminUser())
INSERT INTO sucursal (id_sucursal, nombre, telefono) VALUES 
('SUC-00000000-0000-0000-0000-000000000001', 'Sucursal Centro', '555-1234'), 
('SUC-00000000-0000-0000-0000-000000000002', 'Sucursal Norte', '555-5678');

-- 2.3 Seguridad (Roles, Módulos, Usuarios y Permisos)
INSERT INTO rol (id_rol, nombre, descripcion) VALUES 
(UUID(), 'ADMIN', 'Administrador Total del Sistema'), 
(UUID(), 'VENDEDOR', 'Ventas Mostrador y Registro Clínico'), 
(UUID(), 'OPTOMETRISTA', 'Consultas y Exámenes de la Vista'), 
(UUID(), 'ALMACEN', 'Gestión de Inventarios y Almacén'),
(UUID(), 'TALLER', 'Técnico de Laboratorio y Órdenes de Trabajo'),
(UUID(), 'CAJA', 'Encargado de Cobros, Caja y Facturación');

INSERT INTO modulo (id_modulo, nombre, descripcion) VALUES 
(UUID(), 'AGENDA CITAS', 'Gestión de Citas y Calendario Especialista'),
(UUID(), 'CLIENTES', 'Directorio de Clientes y Datos de Facturación'),
(UUID(), 'PACIENTES', 'Directorio de Pacientes y Búsqueda'),
(UUID(), 'EXPEDIENTE PACIENTE', 'Historial Clínico y Resultados de Optometría'),
(UUID(), 'VENTAS', 'Módulo de Punto de Venta y Cotizaciones'),
(UUID(), 'FACTURACION CFDI', 'Emisión y Consulta de Facturas Electrónicas'),
(UUID(), 'INVENTARIO', 'Catálogo de Productos y Control de Existencias'),
(UUID(), 'ORDENES DE COMPRA', 'Gestión de Compras a Proveedores'),
(UUID(), 'PROVEEDORES', 'Catálogo Maestro de Proveedores'),
(UUID(), 'RECEPCION Y DEVOLUCION', 'Entradas por Compra y Devoluciones'),
(UUID(), 'TALLER OT', 'Gestión de Órdenes de Trabajo de Laboratorio'),
(UUID(), 'REPORTES', 'Módulo de Reportes y Business Intelligence'),
(UUID(), 'USUARIOS, ROLES Y PERMISOS', 'Administración de Usuarios y Seguridad');

SET @pass = '$2a$10$X7V.j5f9g.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z'; -- Hash de ejemplo
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo) VALUES 
(UUID(), 'Administrador Principal', 'graxsoft_soporte@hotmail.com', @pass, 1),
(UUID(), 'Vendedor de Prueba', 'vendedor@test.com', @pass, 1),
(UUID(), 'Optometrista de Prueba', 'opto@test.com', @pass, 1),
(UUID(), 'Almacenista de Prueba', 'almacen@test.com', @pass, 1),
(UUID(), 'Técnico de Taller', 'taller@test.com', @pass, 1),
(UUID(), 'Cajero de Prueba', 'caja@test.com', @pass, 1),
-- SUPER ADMIN (DEBUG) - UUID fijo alineado con AuthService.java createSuperAdminUser()
-- Login bypass de desarrollo: root/root — ELIMINAR en producción
('00000000-0000-0000-0000-000000000000', 'SUPER ADMIN (DEBUG)', 'root', @pass, 1);

-- 2.3.1 Asignación de Roles
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='graxsoft_soporte@hotmail.com' AND r.nombre='ADMIN';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='vendedor@test.com' AND r.nombre='VENDEDOR';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='opto@test.com' AND r.nombre='OPTOMETRISTA';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='almacen@test.com' AND r.nombre='ALMACEN';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='taller@test.com' AND r.nombre='TALLER';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='caja@test.com' AND r.nombre='CAJA';
-- SUPER ADMIN (DEBUG): Asignación de rol ADMIN
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) 
SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='root' AND r.nombre='ADMIN';

-- 2.3.2 Matriz de Permisos por Rol
-- ADMIN: Todo a Todo
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, m.id_modulo, 1, 1, 1, 1 FROM rol r CROSS JOIN modulo m WHERE r.nombre = 'ADMIN';

-- VENDEDOR: Área comercial y clínica básica
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, m.id_modulo, 1, 1, 1, 0 FROM rol r CROSS JOIN modulo m 
WHERE r.nombre = 'VENDEDOR' AND m.nombre IN ('VENTAS', 'CLIENTES', 'PACIENTES', 'AGENDA CITAS', 'EXPEDIENTE PACIENTE', 'INVENTARIO');

-- OPTOMETRISTA: Foco en salud visual
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, m.id_modulo, 1, 1, 1, 0 FROM rol r CROSS JOIN modulo m 
WHERE r.nombre = 'OPTOMETRISTA' AND m.nombre IN ('PACIENTES', 'AGENDA CITAS', 'EXPEDIENTE PACIENTE', 'INVENTARIO');

-- CAJA: Cobros y facturación
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, m.id_modulo, 1, 1, 1, 0 FROM rol r CROSS JOIN modulo m 
WHERE r.nombre = 'CAJA' AND m.nombre IN ('VENTAS', 'CLIENTES', 'FACTURACION CFDI');

-- TALLER: Producción y logística
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, m.id_modulo, 1, 1, 1, 0 FROM rol r CROSS JOIN modulo m 
WHERE r.nombre = 'TALLER' AND m.nombre IN ('TALLER OT', 'RECEPCION Y DEVOLUCION', 'INVENTARIO', 'ORDENES DE COMPRA');

-- ALMACEN: Gestión total de stock (incluye eliminación lógica)
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT UUID(), r.id_rol, m.id_modulo, 1, 1, 1, 1 FROM rol r CROSS JOIN modulo m 
WHERE r.nombre = 'ALMACEN' AND m.nombre IN ('INVENTARIO', 'ORDENES DE COMPRA', 'PROVEEDORES', 'RECEPCION Y DEVOLUCION');

-- 2.4 Optometristas (RESTAURADOS - 3 REGISTROS)
INSERT INTO optometrista (id_optometrista, nombre, cedula, telefono, email) VALUES 
(UUID(),'Optometrista Senior','CED-001','9992000001','optoA@demo.local'),
(UUID(),'Optometrista Junior','CED-002','9992000002','optoB@demo.local'),
(UUID(),'Optometrista Fines','CED-003','9992000003','optoC@demo.local');

-- 2.5 Proveedores (RESTAURADOS TOTALMENTE - 25 REGISTROS)
INSERT INTO proveedor (id_proveedor, rfc, razon_social, nombre_comercial, contacto, telefono, email, condicion_pago, estatus, creado_en) VALUES
(UUID(), 'LMX840315KH3', 'Luxottica México S.A. de C.V.', 'Luxottica', 'Roberto Martínez', '5550010001', 'ventas@luxottica.com.mx', '60 días', 'ACTIVO', NOW()),
(UUID(), 'EME951102R52', 'Essilor México S.A. de C.V.', 'Essilor', 'Patricia González', '5550020002', 'contacto@essilor.com.mx', '30 días', 'ACTIVO', NOW()),
(UUID(), 'AUG980520P41', 'Augen Ópticos S.A. de C.V.', 'Laboratorios Augen', 'Ing. Carlos Ruiz', '3330030003', 'pedidos@augen.mx', '15 días', 'ACTIVO', NOW()),
(UUID(), 'JOJ0502148S6', 'Johnson & Johnson de México S.A. de C.V.', 'Johnson Vision Care', 'Dra. Ana López', '5550040004', 'acuvue@jnj.com', '45 días', 'ACTIVO', NOW()),
(UUID(), 'SAF101010QW9', 'Safilo México S.A. de C.V.', 'Safilo Group', 'Luis Hernández', '5550050005', 'ventas.mexico@safilo.com', '60 días', 'ACTIVO', NOW()),
(UUID(), 'MME990909TY5', 'Marchon México S.A. de C.V.', 'Marchon Eyewear', 'Fernanda Castillo', '5550060006', 'servicioalcliente@marchon.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'CZV020202UI8', 'Carl Zeiss Vision Mexico S. de R.L. de C.V.', 'Zeiss', 'Ricardo Alarcón', '5550070007', 'atencion.clientes@zeiss.com', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'HOY080808OP3', 'Hoya Lens Mexico S.A. de C.V.', 'Hoya', 'Mónica Soto', '5550080008', 'ventas@hoyavision.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'COP010101AS1', 'CooperVision Mexico S.A. de C.V.', 'CooperVision', 'Jorge Ramírez', '5550090009', 'contacto@coopervision.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'BAM121212DF4', 'Bausch & Lomb Mexico S.A. de C.V.', 'Bausch + Lomb', 'Elena Torres', '5550100010', 'pedidos@bausch.com', '45 días', 'ACTIVO', NOW()),
(UUID(), 'ROD700707GH7', 'Rodenstock México S.A. de C.V.', 'Rodenstock', 'Andrés Vega', '5550110011', 'info@rodenstock.mx', '30 días', 'ACTIVO', NOW()),
(UUID(), 'KEN030303JK2', 'Kenmark Eyewear Mexico', 'Kenmark', 'Sofia Méndez', '5550120012', 'sales@kenmark.com', '15 días', 'ACTIVO', NOW()),
(UUID(), 'MOD060606LZ5', 'Modo Eyewear S.A. de C.V.', 'Modo', 'Gabriel Orozco', '5550130013', 'mexico@modo.com', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'SIL090909XC8', 'Silhouette Optical Mexico', 'Silhouette', 'Daniela Ibarra', '5550140014', 'office@silhouette.mx', '60 días', 'ACTIVO', NOW()),
(UUID(), 'KER040404VB1', 'Kering Eyewear Mexico S.A. de C.V.', 'Kering', 'Miguel Ángel Paz', '5550150015', 'luxury@kering.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'MAR050505NM3', 'Marcolin Mexico', 'Marcolin', 'Laura Esquivel', '5550160016', 'showroom@marcolin.com', '60 días', 'ACTIVO', NOW()),
(UUID(), 'OPT111111QA6', 'Ópticas y Suministros del Bajío', 'Optisum', 'Pedro Infante', '4770170017', 'ventas@optisum.com', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'DIS070707WS9', 'Distribuidora Óptica del Centro', 'Distropt', 'María Félix', '4420180018', 'contacto@distropt.com', '15 días', 'ACTIVO', NOW()),
(UUID(), 'LAB020202ED4', 'Laboratorios Ópticos Profesionales', 'LaboPro', 'Cantinflas', '5550190019', 'trabajos@labopro.mx', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'IMP121212RF7', 'Importadora de Armazones S.A.', 'ImporArma', 'Tin Tan', '8180200020', 'importaciones@imporarma.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'ACC030303TG0', 'Accesorios Ópticos de Occidente', 'AccesoWest', 'Chabelo', '3330210021', 'ventas@accesowest.com', '15 días', 'ACTIVO', NOW()),
(UUID(), 'LEN080808YH2', 'Lentes Premium de México', 'PremiumLens', 'Chespirito', '5550220022', 'calidad@premiumlens.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'SOL050505UJ5', 'Soluciones Visuales Integrales', 'SVI', 'El Santo', '5550230023', 'soluciones@svi.mx', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'INS090909IK8', 'Insumos para Taller Óptico S.A.', 'InsumoOptic', 'Blue Demon', '5550240024', 'almacen@insumooptic.com', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'EQU060606OL1', 'Equipamiento Oftálmico avanzado', 'EquipOf', 'Mil Máscaras', '5550250025', 'ventas@equipof.com', '60 días', 'ACTIVO', NOW());

-- 2.6 Pacientes (RESTAURADOS TOTALMENTE - 30 REGISTROS)
INSERT INTO paciente (id_paciente, nombre, apellidos, telefono, email, rfc, tipo_persona, regimen_fiscal, uso_cfdi, domicilio_fiscal, estatus, fecha_registro) VALUES
(UUID(), 'Juan Carlos', 'Pérez López', '5512345678', 'juan.perez@email.com', 'PELJ8001019Q8', 'FISICA', '605', 'G03', 'Av. Reforma 123, CDMX', 'ACTIVO', NOW()),
(UUID(), 'María Fernanda', 'González Ruiz', '5587654321', 'maria.gonzalez@email.com', 'GORM9005051H0', 'FISICA', '605', 'D01', 'Calle Pino 45, Guadalajara', 'ACTIVO', NOW()),
(UUID(), 'Soluciones Tecnológicas S.A. de C.V.', '', '5555555555', 'contacto@solutec.com', 'STE101010KH4', 'MORAL', '601', 'G03', 'Insurgentes Sur 100, CDMX', 'ACTIVO', NOW()),
(UUID(), 'Pedro', 'Ramírez', '5544332211', 'pedro.ramirez@email.com', NULL, 'FISICA', '616', 'S01', 'Calle 5 de Mayo, Puebla', 'ACTIVO', NOW()),
(UUID(), 'Ana', 'Torres', '5599887766', 'ana.torres@email.com', 'TORA8502027K1', 'FISICA', '605', 'D02', 'Av. Vallarta 200, Zapopan', 'ACTIVO', NOW()),
(UUID(), 'Luis', 'Hernández', '5511223344', 'luis.hernandez@email.com', 'HEAL880808A12', 'FISICA', '605', 'G03', 'Calle 10, Monterrey', 'ACTIVO', NOW()),
(UUID(), 'Carla', 'Díaz', '5522334455', 'carla.diaz@email.com', 'DICA990909B23', 'FISICA', '605', 'D01', 'Calle 20, Tijuana', 'ACTIVO', NOW()),
(UUID(), 'Empresa Constructora S.A.', '', '5533445566', 'contacto@constructora.com', 'ECO121212C34', 'MORAL', '601', 'G03', 'Blvd. Agua Caliente, Tijuana', 'ACTIVO', NOW()),
(UUID(), 'Roberto', 'Sánchez', '5544556677', 'roberto.sanchez@email.com', 'SARO770707D45', 'FISICA', '605', 'G03', 'Calle 30, Mérida', 'ACTIVO', NOW()),
(UUID(), 'Lucía', 'Méndez', '5555667788', 'lucia.mendez@email.com', 'MELU880808E56', 'FISICA', '605', 'D01', 'Calle 40, Cancún', 'ACTIVO', NOW()),
(UUID(), 'Jorge', 'Medina', '5566778899', 'jorge.medina@email.com', 'MEDJ850115H32', 'FISICA', '605', 'G03', 'Av. Constitución 500, Monterrey', 'ACTIVO', NOW()),
(UUID(), 'Patricia', 'Castro', '5577889900', 'patricia.castro@email.com', 'CASP920320J45', 'FISICA', '605', 'D01', 'Calle Hidalgo 80, Querétaro', 'ACTIVO', NOW()),
(UUID(), 'Servicios Integrales S.A.', '', '5588001122', 'contacto@serviciosint.com', 'SIN150601L98', 'MORAL', '601', 'G03', 'Blvd. Bernardo Quintana 200, Querétaro', 'ACTIVO', NOW()),
(UUID(), 'Ricardo', 'Vargas', '5599112233', 'ricardo.vargas@email.com', 'VARR781105M67', 'FISICA', '605', 'G03', 'Calle Juárez 33, Toluca', 'ACTIVO', NOW()),
(UUID(), 'Elena', 'Silva', '5500223344', 'elena.silva@email.com', 'SILE950712P89', 'FISICA', '605', 'D01', 'Av. Tecnológico 400, Toluca', 'ACTIVO', NOW()),
(UUID(), 'Grupo Industrial Norte S.A. de C.V.', '', '8112345678', 'ventas@gin.com.mx', 'GIN080808Q21', 'MORAL', '601', 'G03', 'Carr. Nacional Km 20, Monterrey', 'ACTIVO', NOW()),
(UUID(), 'Fernando', 'Ríos', '3312345678', 'fernando.rios@email.com', 'RIOF830425R54', 'FISICA', '605', 'G03', 'Av. Vallarta 1000, Guadalajara', 'ACTIVO', NOW()),
(UUID(), 'Teresa', 'Ortiz', '3398765432', 'teresa.ortiz@email.com', 'ORTT890930S76', 'FISICA', '605', 'D02', 'Calle Madero 55, León', 'ACTIVO', NOW()),
(UUID(), 'Manuel', 'Gil', '4421234567', 'manuel.gil@email.com', 'GILM811210T98', 'FISICA', '605', 'G03', 'Calle Corregidora 10, Querétaro', 'ACTIVO', NOW()),
(UUID(), 'Verónica', 'Núñez', '2229876543', 'veronica.nunez@email.com', 'NUNV930215U09', 'FISICA', '605', 'D01', 'Av. Juárez 200, Puebla', 'ACTIVO', NOW()),
(UUID(), 'Distribuidora Óptica del Centro', '', '5511229988', 'contacto@distoptica.com', 'DOC180101V32', 'MORAL', '601', 'I01', 'Calle Madero 100, CDMX', 'ACTIVO', NOW()),
(UUID(), 'Gabriel', 'Soto', '9991234567', 'gabriel.soto@email.com', 'SOTG860520W54', 'FISICA', '605', 'G03', 'Paseo de Montejo 300, Mérida', 'ACTIVO', NOW()),
(UUID(), 'Adriana', 'Mejía', '6621234567', 'adriana.mejia@email.com', 'MEJA910810X76', 'FISICA', '605', 'D01', 'Blvd. Kino 150, Hermosillo', 'ACTIVO', NOW()),
(UUID(), 'Héctor', 'Vega', '6141234567', 'hector.vega@email.com', 'VEGH841005Y98', 'FISICA', '605', 'S01', 'Av. Universidad 50, Chihuahua', 'ACTIVO', NOW()),
(UUID(), 'Claudia', 'Romero', '8711234567', 'claudia.romero@email.com', 'ROMC941115Z09', 'FISICA', '605', 'G03', 'Blvd. Independencia 400, Torreón', 'ACTIVO', NOW()),
(UUID(), 'Clínica Visual del Sur S.C.', '', '9611234567', 'citas@visualsur.com', 'CVS190301A32', 'MORAL', '603', 'G03', 'Av. Central 200, Tuxtla Gutiérrez', 'ACTIVO', NOW()),
(UUID(), 'Sergio', 'Flores', '7771234567', 'sergio.flores@email.com', 'FLOS801225B54', 'FISICA', '605', 'D01', 'Av. Emiliano Zapata 30, Cuernavaca', 'ACTIVO', NOW()),
(UUID(), 'Lorena', 'Castillo', '4491234567', 'lorena.castillo@email.com', 'CASL880130C76', 'FISICA', '605', 'G03', 'Av. López Mateos 100, Aguascalientes', 'ACTIVO', NOW()),
(UUID(), 'Ignacio', 'Padilla', '4441234567', 'ignacio.padilla@email.com', 'PADI820228D98', 'FISICA', '605', 'D01', 'Av. Carranza 500, San Luis Potosí', 'ACTIVO', NOW()),
(UUID(), 'Rosa', 'Morales', '9981234567', 'rosa.morales@email.com', 'MORR960415E09', 'FISICA', '605', 'G03', 'Av. Tulum 45, Cancún', 'ACTIVO', NOW());

-- 2.7 Flujo Clínico Básico (Expedientes)
INSERT INTO expediente (id_expediente, id_paciente, fecha_apertura, notas) 
SELECT UUID(), id_paciente, CURDATE(), 'Generado automáticamente por sistema' FROM paciente;

-- #########################################################################################
-- SECCIÓN 3: INVENTARIOS (PRODUCTOS, CATÁLOGOS Y EXISTENCIAS)
-- #########################################################################################

-- 3.1 Catálogos de Inventario
INSERT INTO cat_grupo (id_grupo, nombre, exige_caducidad, es_servicio) VALUES 
(UUID(), 'ARMAZONES', 0, 0),
(UUID(), 'LENTES', 0, 0),
(UUID(), 'CONTACTOLOGIA', 1, 0),
(UUID(), 'ACCESORIOS', 0, 0),
(UUID(), 'SERVICIOS', 0, 1);

INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'OFTALMICO' FROM cat_grupo WHERE nombre = 'ARMAZONES';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'SOLAR' FROM cat_grupo WHERE nombre = 'ARMAZONES';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'MONOFOCAL' FROM cat_grupo WHERE nombre = 'LENTES';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'PROGRESIVO' FROM cat_grupo WHERE nombre = 'LENTES';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'LENTES DE CONTACTO' FROM cat_grupo WHERE nombre = 'CONTACTOLOGIA';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'ESTUCHES' FROM cat_grupo WHERE nombre = 'ACCESORIOS';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'SOLUCIONES' FROM cat_grupo WHERE nombre = 'ACCESORIOS';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'CONSULTA' FROM cat_grupo WHERE nombre = 'SERVICIOS';
INSERT INTO cat_familia (id_familia, id_grupo, nombre) 
SELECT UUID(), id_grupo, 'REPARACION' FROM cat_grupo WHERE nombre = 'SERVICIOS';

INSERT INTO cat_marca (id_marca, nombre) VALUES 
(UUID(), 'Ray-Ban'), (UUID(), 'Oakley'), (UUID(), 'Carrera'), (UUID(), 'Gucci'), 
(UUID(), 'Prada'), (UUID(), 'Essilor'), (UUID(), 'Zeiss'), (UUID(), 'Hoya'), 
(UUID(), 'Augen'), (UUID(), 'Acuvue'), (UUID(), 'Alcon'), (UUID(), 'Bausch + Lomb'), 
(UUID(), 'CooperVision'), (UUID(), 'Avaira'), (UUID(), 'Biofinity'), (UUID(), 'GENÉRICO');

-- 3.2 Maestro de Productos (50+ Registros)
-- ARMAZONES (15)
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501001', 'Ray-Ban RB2132 New Wayfarer Black', '01-RAY-RB2132-P-7501001', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Ray-Ban'), 3, 10, 1150.00, 85.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501002', 'Ray-Ban RB3447 Round Metal', '02-RAY-RB3447-P-7501002', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Ray-Ban'), 2, 8, 1350.00, 90.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501003', 'Oakley Holbrook Woodgrain Prizm', '03-OAK-HOLBR-P-7501003', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Oakley'), 2, 5, 1600.00, 75.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501004', 'Carrera 1001/S Black Gold', '04-CAR-1001S-P-7501004', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Carrera'), 2, 6, 950.00, 80.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Safilo'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501005', 'Gucci GG0006O Rectangle Black', '05-GUC-GG006-P-7501005', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Gucci'), 1, 4, 3200.00, 100.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Kering'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501006', 'Prada PR 01VV Heritage', '06-PRA-PR01V-P-7501006', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Prada'), 1, 3, 2800.00, 120.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501007', 'Ray-Ban RB5154 Clubmaster Optical', '07-RAY-RB5154-P-7501007', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Ray-Ban'), 5, 15, 1050.00, 85.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501008', 'Oakley Pitchman R Satin Black', '08-OAK-PITCH-P-7501008', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Oakley'), 2, 8, 1550.00, 80.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501009', 'Carrera 1103 Matte Blue', '09-CAR-1103M-P-7501009', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Carrera'), 3, 10, 850.00, 90.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Safilo'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501010', 'Ray-Ban RB7140 Tortoise Gold', '10-RAY-RB7140-P-7501010', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Ray-Ban'), 2, 6, 1100.00, 95.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501011', 'Oakley Gascan Polished White', '11-OAK-GASCA-P-7501011', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Oakley'), 1, 5, 1400.00, 85.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501012', 'Gucci GG0061S Round Gold', '12-GUC-GG061-P-7501012', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Gucci'), 1, 2, 4500.00, 80.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Kering'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501013', 'Ray-Ban Junior RY1531 Red', '13-RAY-RY1531-P-7501013', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Ray-Ban'), 2, 5, 600.00, 100.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501014', 'Carrera 8824 Rectangle Gunmetal', '14-CAR-8824R-P-7501014', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='OFTALMICO'), (SELECT id_marca FROM cat_marca WHERE nombre='Carrera'), 2, 8, 780.00, 90.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Safilo'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7501015', 'Oakley Frogskins Polished Black', '15-OAK-FROGS-P-7501015', (SELECT id_grupo FROM cat_grupo WHERE nombre='ARMAZONES'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLAR'), (SELECT id_marca FROM cat_marca WHERE nombre='Oakley'), 3, 10, 1200.00, 80.00, '42241701', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Luxottica'), 'ACTIVO';

-- LENTES (GRADUADOS/MICAS) (10)
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502001', 'Lente CR-39 Blanco 1.50 (Par)', '16-GEN-CR39B-P-7502001', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 20, 100, 80.00, 400.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Laboratorios Augen'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502002', 'Lente Policarbonato con AR (Par)', '17-GEN-POLAR-P-7502002', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 15, 80, 180.00, 350.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Laboratorios Augen'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502003', 'Essilor Varilux Physio 3.0', '18-ESS-VARIL-P-7502003', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='PROGRESIVO'), (SELECT id_marca FROM cat_marca WHERE nombre='Essilor'), 5, 20, 3200.00, 100.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Essilor'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502004', 'Zeiss SmartLife Single Vision', '19-ZEI-SMART-P-7502004', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='Zeiss'), 5, 15, 1500.00, 120.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Zeiss'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502005', 'Hoya ID Myself Progressive', '20-HOY-IDMYS-P-7502005', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='PROGRESIVO'), (SELECT id_marca FROM cat_marca WHERE nombre='Hoya'), 2, 10, 4500.00, 80.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Hoya'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502006', 'Augen High Index 1.67 AR', '21-AUG-HI167-P-7502006', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='Augen'), 10, 30, 850.00, 200.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Laboratorios Augen'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502007', 'Transitions Signature Gen8 CR-39', '22-ESS-TRANS-P-7502007', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='Essilor'), 10, 40, 950.00, 150.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Essilor'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502008', 'Crizal Sapphire Invisible AR', '23-ESS-CRIZL-P-7502008', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='Essilor'), 10, 50, 600.00, 200.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Essilor'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502009', 'Lente Flat Top 28 CR-39 (Par)', '24-GEN-FT28C-P-7502009', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 5, 20, 150.00, 300.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Laboratorios Augen'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7502010', 'Essilor Blue UV Capture', '25-ESS-BLUUV-P-7502010', (SELECT id_grupo FROM cat_grupo WHERE nombre='LENTES'), (SELECT id_familia FROM cat_familia WHERE nombre='MONOFOCAL'), (SELECT id_marca FROM cat_marca WHERE nombre='Essilor'), 10, 30, 450.00, 180.00, '42242100', 'PR', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Essilor'), 'ACTIVO';

-- CONTACTOLOGÍA (10)
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503001', 'Acuvue Oasys with Hydraclear Plus (6pk)', '26-ACU-OASYS-P-7503001', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Acuvue'), 10, 50, 550.00, 45.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Johnson Vision Care'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503002', 'Acuvue 1-Day Moist (30pk)', '27-ACU-1DAYM-P-7503002', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Acuvue'), 5, 25, 750.00, 40.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Johnson Vision Care'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503003', 'Air Optix Plus HydraGlyde (6pk)', '28-ALC-AIROP-P-7503003', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Alcon'), 8, 40, 480.00, 50.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Distribuidora Óptica del Centro'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503004', 'Dailies Total1 (30pk)', '29-ALC-DAILT-P-7503004', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Alcon'), 3, 15, 950.00, 35.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Distribuidora Óptica del Centro'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503005', 'Biofinity CooperVision (6pk)', '30-BIO-BIOFN-P-7503005', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Biofinity'), 12, 60, 420.00, 55.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='CooperVision'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503006', 'Avaira Vitality (6pk)', '31-AVA-VITAL-P-7503006', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Avaira'), 5, 30, 380.00, 60.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='CooperVision'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503007', 'PureVision 2 (6pk)', '32-BAU-PUREV-P-7503007', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Bausch + Lomb'), 6, 30, 520.00, 45.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Bausch + Lomb'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503008', 'SofLens 59 (6pk)', '33-BAU-SOFLN-P-7503008', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Bausch + Lomb'), 15, 80, 290.00, 70.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Bausch + Lomb'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503009', 'Acuvue Oasys for Astigmatism', '34-ACU-OASYA-P-7503009', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Acuvue'), 4, 20, 680.00, 40.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Johnson Vision Care'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7503010', 'Biofinity Toric (6pk)', '35-BIO-BIOFT-P-7503010', (SELECT id_grupo FROM cat_grupo WHERE nombre='CONTACTOLOGIA'), (SELECT id_familia FROM cat_familia WHERE nombre='LENTES DE CONTACTO'), (SELECT id_marca FROM cat_marca WHERE nombre='Biofinity'), 5, 25, 620.00, 45.00, '42242211', 'H87', '02', 0.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='CooperVision'), 'ACTIVO';

-- ACCESORIOS (10)
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504001', 'Estuche Rígido Grande Negro', '36-GEN-ESTRI-P-7504001', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='ESTUCHES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 20, 100, 35.00, 200.00, '42241511', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='AccesoWest'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504002', 'Líquido Limpiador Anti-empañante 60ml', '37-GEN-LIQLI-P-7504002', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLUCIONES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 30, 150, 18.00, 300.00, '42242300', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='AccesoWest'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504003', 'Paño Microfibra Premium 15x15', '38-GEN-MICRO-P-7504003', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLUCIONES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 50, 300, 5.00, 500.00, '42242301', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='AccesoWest'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504004', 'Solución Multipropósito Renu Fresh 355ml', '39-BAU-RENU3-P-7504004', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLUCIONES'), (SELECT id_marca FROM cat_marca WHERE nombre='Bausch + Lomb'), 12, 60, 145.00, 60.00, '42242305', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Bausch + Lomb'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504005', 'Opti-Free PureMoist 300ml', '40-ALC-OPTFR-P-7504005', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLUCIONES'), (SELECT id_marca FROM cat_marca WHERE nombre='Alcon'), 10, 50, 185.00, 55.00, '42242305', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Distribuidora Óptica del Centro'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504006', 'Cordón Sujetador Silicon Negro', '41-GEN-CORDN-P-7504006', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='ESTUCHES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 25, 120, 8.50, 400.00, '42241512', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='AccesoWest'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504007', 'Pinzas de Ajuste Óptico Precision', '42-GEN-PINZA-P-7504007', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='ESTUCHES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 2, 5, 450.00, 100.00, '42241800', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Insumos para Taller Óptico S.A.'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504008', 'Kit de Limpieza Profesional Zeiss', '43-ZEI-KITLM-P-7504008', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLUCIONES'), (SELECT id_marca FROM cat_marca WHERE nombre='Zeiss'), 5, 20, 120.00, 150.00, '42242306', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Zeiss'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504009', 'Lentes de Lectura +1.50 Ready', '44-GEN-LECT1-P-7504009', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='ESTUCHES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 5, 25, 85.00, 250.00, '42241705', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='ImporArma'), 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), '7504010', 'Oclusor de Mano para Consulta', '45-GEN-OCLUS-P-7504010', (SELECT id_grupo FROM cat_grupo WHERE nombre='ACCESORIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='SOLUCIONES'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 2, 10, 150.00, 100.00, '42242310', 'H87', '02', 16.00, (SELECT id_proveedor FROM proveedor WHERE nombre_comercial='Equipamiento Oftálmico avanzado'), 'ACTIVO';

-- SERVICIOS (5)
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), 'SRV-101', 'Consulta Optométrica Especializada', '46-GEN-CONSU-S-SRV101', (SELECT id_grupo FROM cat_grupo WHERE nombre='SERVICIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='CONSULTA'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 0, 0, 0.00, 450.00, '85121600', 'E48', '02', 0.00, NULL, 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), 'SRV-102', 'Ajuste y Limpieza de Armazón', '47-GEN-AJUST-S-SRV102', (SELECT id_grupo FROM cat_grupo WHERE nombre='SERVICIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='REPARACION'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 0, 0, 0.00, 80.00, '85121600', 'E48', '02', 16.00, NULL, 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), 'SRV-103', 'Reparación de Bisagra / Soldadura', '48-GEN-REPAR-S-SRV103', (SELECT id_grupo FROM cat_grupo WHERE nombre='SERVICIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='REPARACION'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 0, 0, 30.00, 300.00, '85121600', 'E48', '02', 16.00, NULL, 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), 'SRV-104', 'Cambio de Plaquetas (Par)', '49-GEN-PLAQT-S-SRV104', (SELECT id_grupo FROM cat_grupo WHERE nombre='SERVICIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='REPARACION'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 0, 0, 10.00, 40.00, '85121600', 'E48', '02', 16.00, NULL, 'ACTIVO';
INSERT INTO producto (id_producto, sku, nombre, codigo_barras, id_grupo, id_familia, id_marca, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) SELECT UUID(), 'SRV-105', 'Adaptación de Lentes de Contacto', '50-GEN-ADAPT-S-SRV105', (SELECT id_grupo FROM cat_grupo WHERE nombre='SERVICIOS'), (SELECT id_familia FROM cat_familia WHERE nombre='CONSULTA'), (SELECT id_marca FROM cat_marca WHERE nombre='GENÉRICO'), 0, 0, 0.00, 300.00, '85121600', 'E48', '02', 0.00, NULL, 'ACTIVO';

-- 3.3 Existencias Iniciales (Automatizadas para todos los productos físicos en ambas sucursales)
INSERT INTO existencia (id_existencia, id_producto, id_sucursal, cantidad)
SELECT UUID(), p.id_producto, s.id_sucursal, FLOOR(RAND() * 50) + 10 
FROM producto p
JOIN cat_grupo g ON p.id_grupo = g.id_grupo
CROSS JOIN sucursal s
WHERE g.es_servicio = 0;

SET FOREIGN_KEY_CHECKS = 1;

-- ===================== FIN =====================
