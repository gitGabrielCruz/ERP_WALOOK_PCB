USE db_omcgc_erp;

-- Insertar 19 proveedores adicionales para pruebas de scroll
INSERT INTO proveedor (id_proveedor, rfc, razon_social, nombre_comercial, contacto, telefono, email, condicion_pago, estatus) VALUES
(UUID(), 'PROV00000001', 'Distribuidora Optica S.A.', 'OptiDist', 'Juan Perez', '5551234567', 'ventas@optidist.com', '15 dias', 'ACTIVO'),
(UUID(), 'PROV00000002', 'Lentes y Armazones de Mexico', 'LensMex', 'Maria Lopez', '5557654321', 'contacto@lensmex.mx', 'Contado', 'ACTIVO'),
(UUID(), 'PROV00000003', 'Suministros Oftalmicos S.A.', 'SumOf', 'Carlos Ruiz', '5559998887', 'sumof@sumof.com', '30 dias', 'ACTIVO'),
(UUID(), 'PROV00000004', 'Vision Global Distribucion', 'VisGlo', 'Ana Garcia', '5551112233', 'ventas@visglo.com', '60 dias', 'ACTIVO'),
(UUID(), 'PROV00000005', 'Accesorios Opticos del Norte', 'AccOptNorte', 'Roberto Diaz', '8112223344', 'norte@accopt.com', '15 dias', 'INACTIVO'),
(UUID(), 'PROV00000006', 'Cristales y Micas Especiales', 'CrisMica', 'Elena Vasquez', '5554445566', 'produccion@crismica.com', 'Contado', 'ACTIVO'),
(UUID(), 'PROV00000007', 'Importadora de Armazones Premium', 'ArmPremium', 'Sergio Ramos', '5556667788', 'ventas@armpremium.mx', '30 dias', 'ACTIVO'),
(UUID(), 'PROV00000008', 'Gafas de Sol S.A. de C.V.', 'SunGafas', 'Laura Torres', '5558889900', 'hola@sungafas.com', '15 dias', 'ACTIVO'),
(UUID(), 'PROV00000009', 'Maquiladora Optica Peninsular', 'MaquiPen', 'Fernando Ruiz', '9991112233', 'gerencia@maquipen.com', 'Contado', 'ACTIVO'),
(UUID(), 'PROV00000010', 'Soluciones Visuales Integral', 'SolVisInt', 'Patricia Sosa', '5552223344', 'atencion@solvis.mx', '30 dias', 'ACTIVO'),
(UUID(), 'PROV00000011', 'Lentes de Contacto de Occidente', 'LensContact', 'Isabel Luna', '3334445566', 'ventas@lenscontact.com', '60 dias', 'ACTIVO'),
(UUID(), 'PROV00000012', 'Estuches y Microfibras Elite', 'EliteAcc', 'Mario Castillo', '5553334455', 'info@eliteacc.com', '15 dias', 'ACTIVO'),
(UUID(), 'PROV00000013', 'Equipos Optometricos S.A.', 'EquiOpt', 'Adriana Meza', '5557776655', 'soporte@equiopt.mx', 'Contado', 'ACTIVO'),
(UUID(), 'PROV00000014', 'Refacciones para Armazones', 'RefacArm', 'Hugo Sanchez', '5552221100', 'partes@refacarm.com', '30 dias', 'ACTIVO'),
(UUID(), 'PROV00000015', 'Laboratorio de Lentes Digitales', 'DigLab', 'Beatriz Ortiz', '5559990011', 'lab@diglab.com', '15 dias', 'ACTIVO'),
(UUID(), 'PROV00000016', 'Moda y Vision International', 'ModVisInt', 'Raul Jimenez', '5553332211', 'ventas@modvis.com', '60 dias', 'ACTIVO'),
(UUID(), 'PROV00000017', 'Optica Especializada en Mayoreo', 'OptiMayor', 'Claudia Rojas', '5551113344', 'mayor@optimayor.mx', '30 dias', 'ACTIVO'),
(UUID(), 'PROV00000018', 'Insumos Opticos de Queretaro', 'InsOptQro', 'Jorge Vera', '4425556677', 'ventas@insoptqro.com', 'Contado', 'ACTIVO'),
(UUID(), 'PROV00000019', 'Tecnologia Visual Avanzada', 'TevisAv', 'Monica Silva', '5550009988', 'info@tevisav.com', '30 dias', 'ACTIVO');
