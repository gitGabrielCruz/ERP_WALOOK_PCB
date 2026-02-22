/*
============================================================
Nombre del archivo : 01_schema_usuarios.sql
Ruta              : omcgc/database/scripts/01_schema_usuarios.sql
Tipo              : Base de Datos (Script SQL DDL)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Definir la estructura relacional (DDL) para el módulo de Gestión de Identidades,
estableciendo las tablas, restricciones de integridad y relaciones necesarias
para el soporte de usuarios, roles y autenticación.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Modelo de Datos de Autenticación):
   - Tabla 'usuario': Persistencia de credenciales hash y estado de actividad.
   - Tabla 'rol': Catálogo de perfiles de acceso (RBAC).
   - Tabla 'usuario_rol': Relación N:M para asignación de privilegios.
2. RNF-03 (Integridad de Datos):
   - Definición de Claves Primarias (PK) tipo UUID (CHAR 36).
   - Definición de Claves Foráneas (FK) con integridad referencial.
============================================================
*/

USE db_omcgc_erp;

-- =========================================================
-- 4. SEGURIDAD (DDL proporcionado por usuario)
-- =========================================================

-- Aseguramos que las tablas existan (si ya las creaste, esto no afecta si usas IF NOT EXISTS o si ya corriste tu script)
-- Pero para mantener este archivo como fuente de verdad del módulo Usuarios:

/* 
   NOTA: Se asume que la base de datos y tablas ya fueron creadas con el script del usuario.
   Si no, descomentar las líneas de creación. Aquí nos enfocamos en garantizar los DATOS SEMILLA
   necesarios para el Login.
*/

-- =========================================================
-- DATOS SEMILLA
-- =========================================================
-- (Eliminados por solicitud. Se usará acceso 'root' temporalmente)

