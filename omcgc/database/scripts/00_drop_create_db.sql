/*
============================================================
Nombre del archivo : 00_drop_create_db.sql
Ruta              : omcgc/database/scripts/00_drop_create_db.sql
Tipo              : Base de Datos (Utilidad DDL)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Script de utilidad para el restablecimiento completo del esquema de base de datos.
ADVERTENCIA: Destructivo. Elimina la base de datos 'optica_erp' si existe.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. Entorno de Desarrollo:
   - Facilitar el reinicio rápido del estado de persistencia (Hard Reset).
   - Configuración de Collation UTF8MB4 (Soporte Unicode completo).
============================================================
*/
DROP DATABASE IF EXISTS optica_erp;
CREATE DATABASE optica_erp CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
