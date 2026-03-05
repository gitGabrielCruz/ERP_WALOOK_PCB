/*
============================================================
Nombre del archivo : DatabaseHealthService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/DatabaseHealthService.java
Tipo              : Backend (Java Spring Boot)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versición          : v1.1

Propósito:
Proveer un servicio transversal para la monitorización activa y pasiva del
estado de la conexión a la base de datos, independiente de la lógica de negocio.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-05 (Disponibilidad y Tolerancia a Fallos):
   - Ejecución de consultas ligeras de validación (`SELECT 1`) vía JDBC.
   - Detección temprana de pérdida de conexión para prevenir saturación de logs.
   - Extracción de metadatos de conexión para auditoría técnica (URL, Driver).
============================================================
*/
package com.omcgc.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.sql.DataSource;

/**
 * Servicio reutilizable para diagnóstico de estado de la Base de Datos.
 * Puede ser inyectado en cualquier componente del sistema.
 */
@Service
public class DatabaseHealthService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    /**
     * Verifica si hay conexión activa ejecutando una consulta ligera.
     * 
     * @return true si la DB responde, false si hay error.
     */
    public boolean isConnected() {
        try {
            // "SELECT 1" es el estándar más rápido para ping en MySQL
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return result != null && result == 1;
        } catch (Exception e) {
            System.err.println("[HEALTH-CHECK] Fallo de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene detalles técnicos de la conexión para logs o debug.
     * 
     * @return String con URL, Usuario y Versión del motor.
     */
    public String getConnectionDetails() {
        if (!isConnected()) {
            return "NO CONECTADO";
        }
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            return String.format("URL: %s | User: %s | Driver: %s %s",
                    meta.getURL(),
                    meta.getUserName(),
                    meta.getDriverName(),
                    meta.getDriverVersion());
        } catch (Exception e) {
            return "Error obteniendo metadatos: " + e.getMessage();
        }
    }
}
