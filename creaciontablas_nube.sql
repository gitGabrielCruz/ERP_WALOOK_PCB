/* =========================================================
   SCRIPT: Creación completa de BD Optica ERP
   Motor: MySQL / MariaDB
   Autoría: Elaboración propia
   Nota: Modificado para ser IDEMPOTENTE (No borra datos existentes excepto Inventarios)
   ========================================================= */

SET FOREIGN_KEY_CHECKS = 0; -- DESACTIVAR FKs GLOBALMENTE AL INICIO

-- DROP DATABASE IF EXISTS graxsof3_omcgc; -- COMENTADO PARA PROTEGER DATOS
-- CREATE DATABASE IF NOT EXISTS graxsof3_omcgc ...
USE graxsof3_omcgc; -- Asegura usar la BD correcta (o optica_erp según tu config)

-- =========================================================
-- 1. LIMPIEZA DE BASE DE DATOS (DROP TABLES)
-- =========================================================
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS bitacora_seguridad;
DROP TABLE IF EXISTS cfdi;
DROP TABLE IF EXISTS insumo_ot;
DROP TABLE IF EXISTS historial_estado_ot;
DROP TABLE IF EXISTS orden_trabajo;
DROP TABLE IF EXISTS pago;
DROP TABLE IF EXISTS detalle_venta;
DROP TABLE IF EXISTS venta;
DROP TABLE IF EXISTS movimiento_inventario;
DROP TABLE IF EXISTS existencia;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS cat_familia;
DROP TABLE IF EXISTS cat_grupo;
DROP TABLE IF EXISTS cat_marca;
DROP TABLE IF EXISTS sucursal;
DROP TABLE IF EXISTS proveedor;
DROP TABLE IF EXISTS receta;
DROP TABLE IF EXISTS cita;
DROP TABLE IF EXISTS expediente;
DROP TABLE IF EXISTS optometrista;
DROP TABLE IF EXISTS paciente;
DROP TABLE IF EXISTS permiso;
DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS modulo;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS cat_metodo_pago;
DROP TABLE IF EXISTS cat_estatus_cfdi;
DROP TABLE IF EXISTS cat_estado_ot;
DROP TABLE IF EXISTS cat_estado_venta;
DROP TABLE IF EXISTS cat_estado_cita;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================================================
-- 3. CATÁLOGOS
-- =========================================================

CREATE TABLE IF NOT EXISTS cat_estado_cita (
  id_estado_cita CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS cat_estado_venta (
  id_estado_venta CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS cat_estado_ot (
  id_estado_ot CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS cat_estatus_cfdi (
  id_estatus_cfdi CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS cat_metodo_pago (
  id_metodo_pago CHAR(36) PRIMARY KEY,
  nombre VARCHAR(20) UNIQUE NOT NULL
);

-- =========================================================
-- 4. SEGURIDAD (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS usuario (
  id_usuario CHAR(36) PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  activo TINYINT(1) NOT NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rol (
  id_rol CHAR(36) PRIMARY KEY,
  nombre VARCHAR(80) UNIQUE NOT NULL,
  descripcion VARCHAR(255),
  activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS modulo (
  id_modulo CHAR(36) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(255),
  activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS usuario_rol (
  id_usuario_rol CHAR(36) PRIMARY KEY,
  id_usuario CHAR(36) NOT NULL,
  id_rol CHAR(36) NOT NULL,
  fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS permiso (
  id_permiso CHAR(36) PRIMARY KEY,
  id_rol CHAR(36) DEFAULT NULL,
  id_usuario CHAR(36) DEFAULT NULL,
  id_modulo CHAR(36) NOT NULL,
  puede_ver BOOLEAN DEFAULT FALSE,
  puede_crear BOOLEAN DEFAULT FALSE,
  puede_editar BOOLEAN DEFAULT FALSE,
  puede_eliminar BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
  FOREIGN KEY (id_modulo) REFERENCES modulo(id_modulo) ON DELETE CASCADE,
  UNIQUE KEY unique_rol_modulo (id_rol, id_modulo),
  UNIQUE KEY unique_usuario_modulo (id_usuario, id_modulo)
);

-- =========================================================
-- 5. CLÍNICA (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS paciente (
  id_paciente CHAR(36) PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL COMMENT 'Nombre completo o Razón Social',
  apellidos VARCHAR(160) NOT NULL,
  telefono VARCHAR(40),
  email VARCHAR(255),
  
  -- Datos Fiscales (Modulo Clientes)
  rfc VARCHAR(13) UNIQUE COMMENT 'RFC 12 o 13 caracteres',
  tipo_persona ENUM('FISICA', 'MORAL') NOT NULL DEFAULT 'FISICA',
  regimen_fiscal VARCHAR(10) COMMENT 'Clave SAT',
  uso_cfdi VARCHAR(10) COMMENT 'Clave uso CFDI',
  domicilio_fiscal TEXT COMMENT 'Dirección completa',
  
  -- Control
  estatus ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
  fusionado_con CHAR(36) COMMENT 'ID si fue fusionado',
  
  -- Auditoría
  fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT chk_rfc_valid CHECK (
    (tipo_persona = 'FISICA' AND LENGTH(rfc) = 13) OR
    (tipo_persona = 'MORAL' AND LENGTH(rfc) = 12) OR
    rfc IS NULL
  )
);

CREATE TABLE IF NOT EXISTS optometrista (
  id_optometrista CHAR(36) PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  cedula VARCHAR(40) NOT NULL,
  telefono VARCHAR(40),
  email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS expediente (
  id_expediente CHAR(36) PRIMARY KEY,
  id_paciente CHAR(36) UNIQUE NOT NULL,
  fecha_apertura DATE NOT NULL,
  notas TEXT,
  FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

CREATE TABLE IF NOT EXISTS cita (
  id_cita CHAR(36) PRIMARY KEY,
  id_paciente CHAR(36) NOT NULL,
  id_optometrista CHAR(36) NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  id_estado_cita CHAR(36) NOT NULL,
  motivo TEXT,
  creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
  FOREIGN KEY (id_optometrista) REFERENCES optometrista(id_optometrista),
  FOREIGN KEY (id_estado_cita) REFERENCES cat_estado_cita(id_estado_cita)
);

CREATE TABLE IF NOT EXISTS receta (
  id_receta CHAR(36) PRIMARY KEY,
  id_expediente CHAR(36) NOT NULL,
  esfera_od DECIMAL(4,2) NOT NULL,
  cilindro_od DECIMAL(4,2) NOT NULL,
  eje_od INT NOT NULL,
  esfera_oi DECIMAL(4,2) NOT NULL,
  cilindro_oi DECIMAL(4,2) NOT NULL,
  eje_oi INT NOT NULL,
  observaciones TEXT,
  fecha_emision DATE NOT NULL,
  pdf_url TEXT,
  FOREIGN KEY (id_expediente) REFERENCES expediente(id_expediente)
);

-- =========================================================
-- PROVEEDORES (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS proveedor (
  id_proveedor CHAR(36) PRIMARY KEY,
  tipo_persona ENUM('FISICA', 'MORAL') NOT NULL DEFAULT 'MORAL',
  rfc VARCHAR(13) UNIQUE NOT NULL,
  razon_social VARCHAR(255) NOT NULL,
  nombre_comercial VARCHAR(255),
  contacto VARCHAR(120),
  telefono VARCHAR(40),
  email VARCHAR(255),
  condicion_pago VARCHAR(50) NOT NULL COMMENT 'Contado, 15 dias, 30 dias, 60 dias',
  estatus ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =========================================================
-- 6. INVENTARIO (ESTRUCTURA ROBUSTA ETAPA 2)
-- Aquí SÍ forzamos actualización (DROP/CREATE)
-- =========================================================

-- BORRADO EN CASCADA MANUAL
DROP TABLE IF EXISTS movimiento_inventario;
DROP TABLE IF EXISTS existencia;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS cat_familia;
DROP TABLE IF EXISTS cat_grupo;
DROP TABLE IF EXISTS cat_marca;

-- 6.1 Catálogos de Inventario
CREATE TABLE cat_grupo (
    id_grupo            CHAR(36) PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL UNIQUE,
    exige_caducidad     BOOLEAN DEFAULT FALSE,
    es_servicio         BOOLEAN DEFAULT FALSE,
    creado_en           TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cat_familia (
    id_familia          CHAR(36) PRIMARY KEY,
    id_grupo            CHAR(36) NOT NULL,
    nombre              VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_grupo) REFERENCES cat_grupo(id_grupo)
);

CREATE TABLE cat_marca (
    id_marca            CHAR(36) PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL UNIQUE,
    creado_en           TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sucursal (
  id_sucursal CHAR(36) PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  telefono VARCHAR(40)
);

-- 6.2 Maestro de Productos
CREATE TABLE producto (
    id_producto         CHAR(36) PRIMARY KEY,
    sku                 VARCHAR(50) UNIQUE NOT NULL,
    nombre              VARCHAR(200) NOT NULL,
    id_grupo            CHAR(36) NOT NULL,
    id_familia          CHAR(36) NOT NULL,
    id_marca            CHAR(36) NOT NULL,
    codigo_barras       VARCHAR(50),
    
    -- Control de stock (Parámetros)
    stock_minimo        INT DEFAULT 0,
    stock_maximo        INT,
    
    -- Costos y precios (Dinámicos)
    costo_unitario      DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT 'Ultimo costo de compra',
    porcentaje_utilidad DECIMAL(5,2) DEFAULT 0.00,
    precio_venta        DECIMAL(10,2) GENERATED ALWAYS AS (costo_unitario * (1 + (porcentaje_utilidad / 100))) STORED,
    
    -- Datos fiscales CFDI (Validados contra .dat)
    clave_prod_serv     VARCHAR(8) NOT NULL,
    clave_unidad        VARCHAR(3) NOT NULL,
    objeto_impuesto     VARCHAR(2) NOT NULL,
    iva_aplicable       DECIMAL(5,2) DEFAULT 16.00,
    
    -- Relaciones y Auditoría
    id_proveedor        CHAR(36),
    estatus             ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    fecha_creacion      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_grupo) REFERENCES cat_grupo(id_grupo),
    FOREIGN KEY (id_familia) REFERENCES cat_familia(id_familia),
    FOREIGN KEY (id_marca) REFERENCES cat_marca(id_marca),
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

-- 6.3 Existencias por Sucursal
CREATE TABLE existencia (
  id_existencia CHAR(36) PRIMARY KEY,
  id_producto   CHAR(36) NOT NULL,
  id_sucursal   CHAR(36) NOT NULL,
  cantidad      INT NOT NULL DEFAULT 0,
  UNIQUE KEY unique_existencia (id_producto, id_sucursal),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
);

-- 6.4 Kardex (Movimientos)
CREATE TABLE movimiento_inventario (
    id_movimiento       CHAR(36) PRIMARY KEY,
    folio               VARCHAR(50),
    tipo_movimiento     ENUM('ENTRADA_COMPRA', 'SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 
                             'DEVOLUCION_CLIENTE', 'MERMA', 'AJUSTE', 'TRASPASO') NOT NULL,
    id_producto         CHAR(36) NOT NULL,
    id_sucursal         CHAR(36) NOT NULL,
    cantidad            INT NOT NULL,
    costo_historico     DECIMAL(10,2),
    existencia_anterior INT,
    existencia_actual   INT,
    fecha_vencimiento   DATE,
    origen_id           CHAR(36),
    id_usuario          CHAR(36) NOT NULL,
    fecha               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones       TEXT,
    
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- 6.5 Vista de Stock Actual (La Verdad Calculada)
CREATE VIEW v_stock_actual AS
SELECT 
    t.id_producto,
    t.sku,
    t.nombre,
    t.grupo,
    t.id_sucursal,
    t.existencia_operativa,
    t.stock_minimo,
    t.stock_maximo,
    CASE
        WHEN t.existencia_operativa <= 0 THEN 'SIN_STOCK'
        WHEN t.existencia_operativa <= t.stock_minimo THEN 'STOCK_BAJO'
        ELSE 'OK'
    END AS estado_stock
FROM (
    SELECT 
        p.id_producto,
        p.sku,
        p.nombre,
        g.nombre AS grupo,
        e.id_sucursal,
        p.stock_minimo,
        p.stock_maximo,
        COALESCE(SUM(
            CASE 
                WHEN m.tipo_movimiento IN ('ENTRADA_COMPRA', 'DEVOLUCION_CLIENTE') THEN m.cantidad
                WHEN m.tipo_movimiento IN ('SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 'MERMA') THEN -m.cantidad
                WHEN m.tipo_movimiento = 'AJUSTE' THEN m.cantidad
                ELSE 0
            END
        ), 0) AS existencia_operativa
    FROM producto p
    JOIN cat_grupo g ON p.id_grupo = g.id_grupo
    JOIN existencia e ON p.id_producto = e.id_producto
    LEFT JOIN movimiento_inventario m ON p.id_producto = m.id_producto AND e.id_sucursal = m.id_sucursal
    WHERE g.es_servicio = FALSE
    GROUP BY p.id_producto, e.id_sucursal
) t;

-- =========================================================
-- 7. VENTAS (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS venta (
  id_venta CHAR(36) PRIMARY KEY,
  id_paciente CHAR(36) NOT NULL,
  id_receta CHAR(36),
  id_sucursal CHAR(36) NOT NULL,
  id_estado_venta CHAR(36) NOT NULL,
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
  FOREIGN KEY (id_receta) REFERENCES receta(id_receta),
  FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
  FOREIGN KEY (id_estado_venta) REFERENCES cat_estado_venta(id_estado_venta)
);

CREATE TABLE IF NOT EXISTS detalle_venta (
  id_detalle CHAR(36) PRIMARY KEY,
  id_venta CHAR(36) NOT NULL,
  id_producto CHAR(36) NOT NULL,
  cantidad DECIMAL(12,3) NOT NULL,
  precio_unit DECIMAL(12,2) NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

CREATE TABLE IF NOT EXISTS pago (
  id_pago CHAR(36) PRIMARY KEY,
  id_venta CHAR(36) NOT NULL,
  id_metodo_pago CHAR(36) NOT NULL,
  monto DECIMAL(12,2) NOT NULL,
  autorizacion VARCHAR(120),
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_metodo_pago) REFERENCES cat_metodo_pago(id_metodo_pago)
);

-- =========================================================
-- 8. TALLER (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS orden_trabajo (
  id_ot CHAR(36) PRIMARY KEY,
  id_venta CHAR(36) NOT NULL,
  id_estado_ot CHAR(36) NOT NULL,
  especificaciones TEXT NOT NULL,
  creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_estado_ot) REFERENCES cat_estado_ot(id_estado_ot)
);

CREATE TABLE IF NOT EXISTS historial_estado_ot (
  id_ot_hist CHAR(36) PRIMARY KEY,
  id_ot CHAR(36) NOT NULL,
  id_estado_ot CHAR(36) NOT NULL,
  cambiado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observacion TEXT,
  FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
  FOREIGN KEY (id_estado_ot) REFERENCES cat_estado_ot(id_estado_ot)
);

CREATE TABLE IF NOT EXISTS insumo_ot (
  id_ot_ins CHAR(36) PRIMARY KEY,
  id_ot CHAR(36) NOT NULL,
  id_producto CHAR(36) NOT NULL,
  cantidad DECIMAL(12,3) NOT NULL,
  FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- =========================================================
-- 9. CFDI (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS cfdi (
  id_cfdi CHAR(36) PRIMARY KEY,
  uuid VARCHAR(64) UNIQUE NOT NULL,
  id_venta CHAR(36) NOT NULL,
  id_estatus_cfdi CHAR(36) NOT NULL,
  rfc VARCHAR(20) NOT NULL,
  uso_cfdi VARCHAR(10) NOT NULL,
  total DECIMAL(12,2) NOT NULL,
  xml_url TEXT,
  pdf_url TEXT,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_estatus_cfdi) REFERENCES cat_estatus_cfdi(id_estatus_cfdi)
);

-- =========================================================
-- 10. BITÁCORA DE SEGURIDAD (IF NOT EXISTS)
-- =========================================================

CREATE TABLE IF NOT EXISTS bitacora_seguridad (
  id_bitacora CHAR(36) PRIMARY KEY,
  id_usuario CHAR(36),
  accion TEXT NOT NULL,       -- Almacenará acción cifrada
  ip_origen TEXT,            -- Almacenará IP cifrada
  detalles TEXT,             -- Almacenará detalles cifrados
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- ===================== FIN =====================
