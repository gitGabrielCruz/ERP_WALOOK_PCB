SET FOREIGN_KEY_CHECKS = 0;
INSERT INTO cat_estado_cita VALUES (UUID(),'Programada'),(UUID(),'Realizada'),(UUID(),'Cancelada');
INSERT INTO cat_estado_venta VALUES (UUID(),'Borrador'),(UUID(),'Completada'),(UUID(),'Cancelada');
INSERT INTO cat_estado_ot VALUES (UUID(),'Creada'),(UUID(),'EnProceso'),(UUID(),'Entregada');
INSERT INTO cat_estatus_cfdi VALUES (UUID(),'Timbrada'),(UUID(),'Cancelada'),(UUID(),'Pendiente');
INSERT INTO cat_metodo_pago VALUES (UUID(),'Efectivo'),(UUID(),'Tarjeta'),(UUID(),'Transferencia');
INSERT INTO cat_tipo_mov_inv VALUES (UUID(),'Entrada'),(UUID(),'Salida'),(UUID(),'Ajuste');
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
INSERT INTO rol (id_rol, nombre, descripcion) VALUES
(UUID(),'ADMIN', 'Administrador Total'),
(UUID(),'CAJA', 'Cajero / Ventas'),
(UUID(),'OPTOMETRIA', 'Optometrista'),
(UUID(),'TALLER', 'Técnico de Taller');
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en) VALUES
(UUID(), 'Administrador General', 'graxsoft_soporte@hotmail.com', 'Temp671b61d3!', 1, NOW()),
(UUID(), 'Cajero Test', 'test@test.com', 'Temp46cc95ee!', 1, NOW()),
(UUID(), 'Optometrista Test', 'test1@test.com', 'Temp8cadad84!', 1, NOW());
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='graxsoft_soporte@hotmail.com' AND r.nombre='ADMIN';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='test@test.com' AND r.nombre='CAJA';
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol) SELECT UUID(), u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.email='test1@test.com' AND r.nombre='OPTOMETRIA';
INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 1, 1, 1 FROM rol r CROSS JOIN modulo m WHERE r.nombre = 'ADMIN';
INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 1, 1, 0 FROM rol r, modulo m WHERE r.nombre='CAJA' AND m.nombre IN ('VENTAS', 'CLIENTES', 'INVENTARIO', 'FACTURACION CFDI');
INSERT INTO permiso (id_permiso, id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) SELECT UUID(), r.id_rol, NULL, m.id_modulo, 1, 1, 1, 0 FROM rol r, modulo m WHERE r.nombre='OPTOMETRIA' AND m.nombre IN ('AGENDA CITAS', 'EXPEDIENTE PACIENTE', 'CLIENTES', 'INVENTARIO');
INSERT INTO sucursal VALUES ('SUC-00000000-0000-0000-0000-000000000001','Sucursal Centro','9990000001'),('SUC-00000000-0000-0000-0000-000000000002','Sucursal Norte','9990000002');
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
(UUID(), 'MOD060606LZ5', 'Modo Eyewear S.A. de C.V.', 'Modo', 'Gabriel Orozco', '5550130013', 'mexico@modo.com', 'Contado', 'INACTIVO', NOW()),
(UUID(), 'SIL090909XC8', 'Silhouette Optical Mexico', 'Silhouette', 'Daniela Ibarra', '5550140014', 'office@silhouette.mx', '60 días', 'ACTIVO', NOW()),
(UUID(), 'KER040404VB1', 'Kering Eyewear Mexico S.A. de C.V.', 'Kering', 'Miguel Ángel Paz', '5550150015', 'luxury@kering.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'MAR050505NM3', 'Marcolin Mexico', 'Marcolin', 'Laura Esquivel', '5550160016', 'showroom@marcolin.com', '60 días', 'ACTIVO', NOW()),
(UUID(), 'OPT111111QA6', 'Ópticas y Suministros del Bajío', 'Optisum', 'Pedro Infante', '4770170017', 'ventas@optisum.com', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'DIS070707WS9', 'Distribuidora Óptica del Centro', 'Distropt', 'María Félix', '4420180018', 'contacto@distropt.com', '15 días', 'ACTIVO', NOW()),
(UUID(), 'LAB020202ED4', 'Laboratorios Ópticos Profesionales', 'LaboPro', 'Cantinflas', '5550190019', 'trabajos@labopro.mx', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'IMP121212RF7', 'Importadora de Armazones S.A.', 'ImporArma', 'Tin Tan', '8180200020', 'importaciones@imporarma.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'ACC030303TG0', 'Accesorios Ópticos de Occidente', 'AccesoWest', 'Chabelo', '3330210021', 'ventas@accesowest.com', '15 días', 'ACTIVO', NOW()),
(UUID(), 'LEN080808YH2', 'Lentes Premium de México', 'PremiumLens', 'Chespirito', '5550220022', 'calidad@premiumlens.com', '30 días', 'ACTIVO', NOW()),
(UUID(), 'SOL050505UJ5', 'Soluciones Visuales Integrales', 'SVI', 'El Santo', '5550230023', 'soluciones@svi.mx', 'Contado', 'INACTIVO', NOW()),
(UUID(), 'INS090909IK8', 'Insumos para Taller Óptico S.A.', 'InsumoOptic', 'Blue Demon', '5550240024', 'almacen@insumooptic.com', 'Contado', 'ACTIVO', NOW()),
(UUID(), 'EQU060606OL1', 'Equipamiento Oftálmico avanzado', 'EquipOf', 'Mil Máscaras', '5550250025', 'ventas@equipof.com', '60 días', 'ACTIVO', NOW());
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
INSERT INTO existencia SELECT UUID(), p.id_producto, s.id_sucursal, 50, 5 FROM producto p CROSS JOIN sucursal s;
INSERT INTO paciente (id_paciente, nombre, apellidos, telefono, email, rfc, tipo_persona, regimen_fiscal, uso_cfdi, domicilio_fiscal, estatus, fecha_registro) VALUES
(UUID(), 'Juan Carlos', 'Mendoza Pérez', '5556789012', 'jcmendoza85@gmail.com', 'MEPJ850314H61', 'FISICA', '605', 'D01', 'Av. Reforma 123, Col. Centro, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'María Fernanda', 'López Torres', '5543210987', 'mafer.lopez@outlook.com', 'LOTM921105M34', 'FISICA', '612', 'G03', 'Calle Pino 45, Col. Del Valle, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'Consorcio Educativo del Valle S.C.', '', '5567890123', 'compras@coedvalle.edu.mx', 'CEV9802107H0', 'MORAL', '603', 'G03', 'Blvd. Universitario 500, Campus Central', 'ACTIVO', CURDATE()),
(UUID(), 'Roberto', 'Sánchez Vidaurri', '3334567890', 'r.sanchez.v@yahoo.com.mx', 'SAVR780822K92', 'FISICA', '626', 'D01', 'Calle 5 de Mayo 89, Guadalajara, Jal.', 'ACTIVO', CURDATE()),
(UUID(), 'Tecnología y Sistemas Ágiles S.A. de C.V.', '', '8181234567', 'facturacion@agilesys.com', 'TSA150630BY2', 'MORAL', '601', 'G01', 'Parque Tecnológico Mty, Edif 4, NL', 'ACTIVO', CURDATE()),
(UUID(), 'Ana Sofía', 'Guzmán Beltrán', '5512345678', 'anasofi.guzman@icloud.com', 'GUBA990115R56', 'FISICA', '616', 'S01', 'Cda. de los Laureles 12, Querétaro, Qro.', 'ACTIVO', CURDATE()),
(UUID(), 'Constructora y Edificaciones Hércules S.A.', '', '5598765432', 'admin@hercules-const.com', 'CEH0509121J5', 'MORAL', '601', 'I04', 'Carretera Federal Km 45, Toluca, EdoMex', 'INACTIVO', CURDATE()),
(UUID(), 'Luis Alberto', 'Paredes Quezada', '9991234567', 'dr.paredes@medica.com.mx', 'PAQL700420E41', 'FISICA', '612', 'G03', 'Consultorios Medica Sur, Torre 2, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'Gabriela', 'Ibarra Salgado', '7712345678', 'gaby.ibarra90@hotmail.com', 'IASG900707CT3', 'FISICA', '605', 'D01', 'Callejón del Beso 4, Guanajuato, Gto.', 'ACTIVO', CURDATE()),
(UUID(), 'Distribuidora de Alimentos del Norte S. de R.L.', '', '8712345678', 'pagos@dialnorte.mx', 'DAN200115LP9', 'MORAL', '626', 'G03', 'Zona Industrial Norte, Bodega 5, Torreón', 'ACTIVO', CURDATE()),
(UUID(), 'José Ángel', 'Villalobos Cárdenas', '5522334455', 'angel.villa00@gmail.com', 'VICJ000510MW2', 'FISICA', '626', 'D01', 'Av. Insurgentes Sur 1200, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'Instituto de Oftalmología Visión Clara A.C.', '', '5555667788', 'contacto@visionclara.org', 'IOV101010KC2', 'MORAL', '603', 'D01', 'Hospitales Unidos, Piso 3, Puebla, Pue.', 'ACTIVO', CURDATE()),
(UUID(), 'Carmen Elena', 'Rocha Nuñez', '4421234567', 'carmen.rocha.n@gmail.com', 'RONC8209259I7', 'FISICA', '606', 'G03', 'Fracc. Juriquilla, Calle Lago 8, Qro.', 'ACTIVO', CURDATE()),
(UUID(), 'Sofware Solutions & Consulting Mexico S.A.', '', '3312345678', 'cxc@softsolut.com.mx', 'SSC190322HQ5', 'MORAL', '601', 'G03', 'Av. Vallarta 5000, Zapopan, Jal.', 'ACTIVO', CURDATE()),
(UUID(), 'Miguel Ángel', 'Rivas Orozco', '5587654321', 'mrivas.design@gmail.com', 'RIOM951212AB4', 'FISICA', '605', 'D01', 'Calle Roma 45, Col. Juárez, CDMX', 'INACTIVO', CURDATE()),
(UUID(), 'Patricia', 'Esquivel Monroy', '5576543210', 'paty_esquivel@yahoo.com', 'EUMP880228J81', 'FISICA', '612', 'G03', 'Calle 10, Num 45, Col. San Pedro', 'ACTIVO', CURDATE()),
(UUID(), 'Corporativo Jurídico Delta S.C.', '', '5565432109', 'lic.gonzalez@juridicodelta.com', 'CJD080515XY1', 'MORAL', '601', 'G03', 'Torre Mayor, Piso 15, Reforma, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'Francisco Javier', 'Méndez Soto', '9512345678', 'pancho.mendez@taller.mx', 'MESF750615RT9', 'FISICA', '621', 'G01', 'Mercado de Abastos, Local 45, Oaxaca', 'ACTIVO', CURDATE()),
(UUID(), 'Beatriz Adriana', 'Campos Vega', '2221234567', 'betty.campos@gmail.com', 'CAVB931130PL2', 'FISICA', '605', 'D01', 'Calle 5 Sur 400, Centro, Puebla', 'ACTIVO', CURDATE()),
(UUID(), 'Transportes Logísticos del Bajío S.A. de C.V.', '', '4771234567', 'logistica@tlbajio.com', 'TLB120820MN6', 'MORAL', '601', 'G03', 'Puerto Interior, Silao, Gto.', 'ACTIVO', CURDATE()),
(UUID(), 'Ricardo', 'Olivia Jiménez', '6641234567', 'richie.oj@hotmail.com', 'OIJR801010HK5', 'FISICA', '612', 'G03', 'Av. Revolución 500, Tijuana, BC', 'ACTIVO', CURDATE()),
(UUID(), 'Grupo Comercializador Elite S. de R.L. de C.V.', '', '5533334444', 'compras@gpoelite.com', 'GCE180404ZK9', 'MORAL', '601', 'G01', 'Central de Abastos, Iztapalapa, CDMX', 'INACTIVO', CURDATE()),
(UUID(), 'Verónica', 'Castro Salas', '5544445555', 'vero.castro86@gmail.com', 'CASV8601012P3', 'FISICA', '605', 'D01', 'Calle Mimosas 33, Coyoacán, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'Clínica Dental Sonrisas S.C.', '', '5599887766', 'facturas@sonrisas.dental', 'CDS150909UW4', 'MORAL', '601', 'G03', 'Av. Universidad 400, CDMX', 'ACTIVO', CURDATE()),
(UUID(), 'Eduardo', 'Manrique Zepeda', '3322110099', 'lalo.manrique@gmail.com', 'MAZE9102146T1', 'FISICA', '626', 'D01', 'Calle Hidalgo 45, Tlaquepaque, Jal.', 'ACTIVO', CURDATE());
INSERT INTO optometrista VALUES (UUID(),'Optometrista A','CED-001','9992000001','optoA@demo.local'),(UUID(),'Optometrista B','CED-002','9992000002','optoB@demo.local'),(UUID(),'Optometrista C','CED-003','9992000003','optoC@demo.local');
INSERT INTO expediente SELECT UUID(), id_paciente, CURDATE(), 'Expediente demo' FROM paciente;
INSERT INTO cita SELECT UUID(), p.id_paciente, (SELECT id_optometrista FROM optometrista ORDER BY nombre LIMIT 1), CURDATE(), '09:00:00', (SELECT id_estado_cita FROM cat_estado_cita WHERE nombre='Realizada'), 'Examen visual', NOW() FROM paciente p;
INSERT INTO receta SELECT UUID(), e.id_expediente, -1.00,-0.50,90, -0.75,-0.25,80, 'Receta demo', CURDATE(), NULL FROM expediente e;
INSERT INTO venta SELECT UUID(), p.id_paciente, r.id_receta, (SELECT id_sucursal FROM sucursal ORDER BY nombre LIMIT 1), (SELECT id_estado_venta FROM cat_estado_venta WHERE nombre='Completada'), NOW(), 0 FROM paciente p JOIN receta r ON r.id_expediente IN (SELECT id_expediente FROM expediente WHERE id_paciente=p.id_paciente) LIMIT 20;
INSERT INTO venta SELECT UUID(), id_paciente, NULL, (SELECT id_sucursal FROM sucursal ORDER BY nombre DESC LIMIT 1), (SELECT id_estado_venta FROM cat_estado_venta WHERE nombre='Completada'), NOW(), 0 FROM paciente LIMIT 15;
INSERT INTO detalle_venta SELECT UUID(), v.id_venta, p.id_producto, 1, p.precio_unit, p.precio_unit FROM venta v JOIN producto p ON p.categoria='LENTE_GRADUADO' WHERE v.id_receta IS NOT NULL;
INSERT INTO orden_trabajo SELECT UUID(), v.id_venta, (SELECT id_estado_ot FROM cat_estado_ot WHERE nombre='Creada'), 'Lentes graduados', NOW() FROM venta v WHERE v.id_receta IS NOT NULL;
INSERT INTO historial_estado_ot SELECT UUID(), o.id_ot, (SELECT id_estado_ot FROM cat_estado_ot WHERE nombre='Entregada'), NOW(),'Finalizado' FROM orden_trabajo o;
INSERT INTO insumo_ot SELECT UUID(), o.id_ot, p.id_producto, 1 FROM orden_trabajo o JOIN producto p ON p.categoria='INSUMO_TALLER';
INSERT INTO pago SELECT UUID(), v.id_venta, (SELECT id_metodo_pago FROM cat_metodo_pago WHERE nombre='Efectivo'), v.total,NULL,NOW() FROM venta v;
INSERT INTO cfdi SELECT UUID(), CONCAT('UUID-',UUID()), v.id_venta, (SELECT id_estatus_cfdi FROM cat_estatus_cfdi WHERE nombre='Timbrada'), 'XAXX010101000','G03',v.total,NULL,NULL FROM venta v LIMIT 10;
SET FOREIGN_KEY_CHECKS = 1;
