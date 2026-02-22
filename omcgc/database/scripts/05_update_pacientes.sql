/*
============================================================
Nombre del archivo : 05_update_pacientes.sql
Ruta              : omcgc/database/scripts/05_update_pacientes.sql
Tipo              : Base de Datos (Script SQL DDL - Migración)

Propósito:
Actualizar la tabla 'paciente' (definida originalmente) para que cumpla
con los requerimientos del Módulo de Clientes de la Etapa 2.
Esto integra los datos fiscales y de control necesarios.
============================================================
*/

USE db_omcgc_erp;

-- 1. Agregar columnas para datos fiscales y control
ALTER TABLE paciente
ADD COLUMN rfc VARCHAR(13) UNIQUE COMMENT 'RFC del cliente (Física o Moral)' AFTER apellidos,
ADD COLUMN tipo_persona ENUM('FISICA', 'MORAL') NOT NULL DEFAULT 'FISICA' AFTER rfc,
ADD COLUMN regimen_fiscal VARCHAR(10) COMMENT 'Clave del catálogo del SAT' AFTER tipo_persona,
ADD COLUMN uso_cfdi VARCHAR(10) COMMENT 'Clave uso CFDI por defecto' AFTER regimen_fiscal,
ADD COLUMN domicilio_fiscal TEXT COMMENT 'Dirección completa para facturación' AFTER email,
ADD COLUMN estatus ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO' COMMENT 'Soft delete' AFTER fecha_registro,
ADD COLUMN fusionado_con CHAR(36) DEFAULT NULL COMMENT 'ID del paciente maestro si fue fusionado',
ADD COLUMN fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
ADD COLUMN usuario_creacion CHAR(36) COMMENT 'ID del usuario que registró',
ADD COLUMN usuario_modificacion CHAR(36) COMMENT 'ID del último usuario que editó';

-- 2. Agregar índices para búsquedas rápidas (definidos en ETAPA 2)
CREATE INDEX idx_paciente_rfc ON paciente(rfc);
CREATE INDEX idx_paciente_estatus ON paciente(estatus);
CREATE INDEX idx_paciente_tipo ON paciente(tipo_persona);

-- 3. Agregar Constraints de integridad de datos (MySQL 8.0.16+)
-- Nota: Si la versión de MySQL es anterior, el CHECK será ignorado pero no fallará.
ALTER TABLE paciente
ADD CONSTRAINT chk_rfc_formato CHECK (
    (tipo_persona = 'FISICA' AND LENGTH(rfc) = 13) OR
    (tipo_persona = 'MORAL' AND LENGTH(rfc) = 12) OR
    rfc IS NULL
);

-- 4. Actualizar registros existentes (si los hubiera) para que tengan valores por defecto válidos
-- (Solo necesario si la tabla ya tiene datos sin estos campos)
UPDATE paciente SET estatus = 'ACTIVO', tipo_persona = 'FISICA' WHERE estatus IS NULL;

SELECT 'Tabla paciente actualizada con campos de Cliente correctamente.' AS Resultado;
