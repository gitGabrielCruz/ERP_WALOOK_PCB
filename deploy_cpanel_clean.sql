CREATE TABLE cat_estado_cita (
  id_estado_cita CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE cat_estado_venta (
  id_estado_venta CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE cat_estado_ot (
  id_estado_ot CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE cat_estatus_cfdi (
  id_estatus_cfdi CHAR(36) PRIMARY KEY,
  nombre VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE cat_metodo_pago (
  id_metodo_pago CHAR(36) PRIMARY KEY,
  nombre VARCHAR(20) UNIQUE NOT NULL
);
CREATE TABLE cat_tipo_mov_inv (
  id_tipo_mov CHAR(36) PRIMARY KEY,
  nombre VARCHAR(20) UNIQUE NOT NULL
);
CREATE TABLE usuario (
  id_usuario CHAR(36) PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  activo TINYINT(1) NOT NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE rol (
  id_rol CHAR(36) PRIMARY KEY,
  nombre VARCHAR(80) UNIQUE NOT NULL,
  descripcion VARCHAR(255),
  activo BOOLEAN DEFAULT TRUE
);
CREATE TABLE modulo (
  id_modulo CHAR(36) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(255),
  activo BOOLEAN DEFAULT TRUE
);
CREATE TABLE usuario_rol (
  id_usuario_rol CHAR(36) PRIMARY KEY,
  id_usuario CHAR(36) NOT NULL,
  id_rol CHAR(36) NOT NULL,
  fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);
CREATE TABLE permiso (
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
CREATE TABLE paciente (
  id_paciente CHAR(36) PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL,
  apellidos VARCHAR(160) NOT NULL,
  telefono VARCHAR(40),
  email VARCHAR(255),
  rfc VARCHAR(13) UNIQUE,
  tipo_persona ENUM('FISICA', 'MORAL') NOT NULL DEFAULT 'FISICA',
  regimen_fiscal VARCHAR(10),
  uso_cfdi VARCHAR(10),
  domicilio_fiscal TEXT,
  estatus ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
  fusionado_con CHAR(36),
  fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT chk_rfc_valid CHECK (
    (tipo_persona = 'FISICA' AND LENGTH(rfc) = 13) OR
    (tipo_persona = 'MORAL' AND LENGTH(rfc) = 12) OR
    rfc IS NULL
  )
);
CREATE TABLE optometrista (
  id_optometrista CHAR(36) PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  cedula VARCHAR(40) NOT NULL,
  telefono VARCHAR(40),
  email VARCHAR(255)
);
CREATE TABLE expediente (
  id_expediente CHAR(36) PRIMARY KEY,
  id_paciente CHAR(36) UNIQUE NOT NULL,
  fecha_apertura DATE NOT NULL,
  notas TEXT,
  FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);
CREATE TABLE cita (
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
CREATE TABLE receta (
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
CREATE TABLE proveedor (
  id_proveedor CHAR(36) PRIMARY KEY,
  rfc VARCHAR(13) UNIQUE NOT NULL,
  razon_social VARCHAR(255) NOT NULL,
  nombre_comercial VARCHAR(255),
  contacto VARCHAR(120),
  telefono VARCHAR(40),
  email VARCHAR(255),
  condicion_pago VARCHAR(50) NOT NULL,
  estatus ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE sucursal (
  id_sucursal CHAR(36) PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  telefono VARCHAR(40)
);
CREATE TABLE producto (
  id_producto CHAR(36) PRIMARY KEY,
  sku VARCHAR(60) UNIQUE NOT NULL,
  nombre VARCHAR(160) NOT NULL,
  categoria VARCHAR(80) NOT NULL,
  precio_unit DECIMAL(12,2) NOT NULL,
  iva DECIMAL(4,2) NOT NULL,
  activo TINYINT(1) NOT NULL
);
CREATE TABLE existencia (
  id_existencia CHAR(36) PRIMARY KEY,
  id_producto CHAR(36) NOT NULL,
  id_sucursal CHAR(36) NOT NULL,
  stock DECIMAL(12,3) NOT NULL,
  min_stock DECIMAL(12,3) NOT NULL,
  UNIQUE (id_producto, id_sucursal),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
);
CREATE TABLE movimiento_inventario (
  id_movimiento CHAR(36) PRIMARY KEY,
  id_producto CHAR(36) NOT NULL,
  id_sucursal CHAR(36) NOT NULL,
  id_tipo_mov CHAR(36) NOT NULL,
  cantidad DECIMAL(12,3) NOT NULL,
  motivo TEXT,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
  FOREIGN KEY (id_tipo_mov) REFERENCES cat_tipo_mov_inv(id_tipo_mov)
);
CREATE TABLE venta (
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
CREATE TABLE detalle_venta (
  id_detalle CHAR(36) PRIMARY KEY,
  id_venta CHAR(36) NOT NULL,
  id_producto CHAR(36) NOT NULL,
  cantidad DECIMAL(12,3) NOT NULL,
  precio_unit DECIMAL(12,2) NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);
CREATE TABLE pago (
  id_pago CHAR(36) PRIMARY KEY,
  id_venta CHAR(36) NOT NULL,
  id_metodo_pago CHAR(36) NOT NULL,
  monto DECIMAL(12,2) NOT NULL,
  autorizacion VARCHAR(120),
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_metodo_pago) REFERENCES cat_metodo_pago(id_metodo_pago)
);
CREATE TABLE orden_trabajo (
  id_ot CHAR(36) PRIMARY KEY,
  id_venta CHAR(36) NOT NULL,
  id_estado_ot CHAR(36) NOT NULL,
  especificaciones TEXT NOT NULL,
  creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
  FOREIGN KEY (id_estado_ot) REFERENCES cat_estado_ot(id_estado_ot)
);
CREATE TABLE historial_estado_ot (
  id_ot_hist CHAR(36) PRIMARY KEY,
  id_ot CHAR(36) NOT NULL,
  id_estado_ot CHAR(36) NOT NULL,
  cambiado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observacion TEXT,
  FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
  FOREIGN KEY (id_estado_ot) REFERENCES cat_estado_ot(id_estado_ot)
);
CREATE TABLE insumo_ot (
  id_ot_ins CHAR(36) PRIMARY KEY,
  id_ot CHAR(36) NOT NULL,
  id_producto CHAR(36) NOT NULL,
  cantidad DECIMAL(12,3) NOT NULL,
  FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);
CREATE TABLE cfdi (
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
CREATE TABLE bitacora_seguridad (
  id_bitacora CHAR(36) PRIMARY KEY,
  id_usuario CHAR(36),
  accion VARCHAR(50) NOT NULL,
  ip_origen VARCHAR(45),
  detalles TEXT,
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
