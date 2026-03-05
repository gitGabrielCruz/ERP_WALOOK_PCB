/*
============================================================
Nombre del archivo : DatabaseConfig.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/config/DatabaseConfig.java
Tipo              : Backend (Configuración de Infraestructura)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Centralizar la configuración programática de la persistencia de datos.
Actúa como la única fuente de la verdad para los parámetros de conexión JDBC.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-05 (Mantenibilidad y Centralización):
   - Encapsulamiento de credenciales y URL de conexión según el entorno (Dev/Prod).
   - Inyección de dependencias del Driver MySQL Connector/J.
============================================================
*/
package com.omcgc.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // --- PARÁMETROS CENTRALIZADOS DE CONEXIÓN ---
    // Único punto de modificación para credenciales de infraestructura.

    // --- PARÁMETROS DE CONEXIÓN (12-FACTOR APP: ENV VARS FIRST) ---
    // Prioridad: 1. Variable de Entorno (VPS/Nube) -> 2. Valor por defecto
    // (Local/Dev)

    private String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    private boolean isProduction() {
        // cPanel y servidores web usualmente corren sobre Linux
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    private String getDbDriver() {
        return "com.mysql.cj.jdbc.Driver";
    }

    private String getDbUrl() {
        boolean prod = isProduction();
        String defaultHost = prod ? "localhost" : "127.0.0.1";
        // BD de Producción validada: graxsof3_omcgc
        String defaultDb = prod ? "graxsof3_omcgc" : "db_omcgc_erp";

        String host = getEnvOrDefault("DB_HOST", defaultHost);
        String port = getEnvOrDefault("DB_PORT", "3306");
        String dbName = getEnvOrDefault("DB_NAME", defaultDb);

        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                host, port, dbName);
    }

    private String getDbUser() {
        // Usuario de Producción validado: graxsof3_omcgc
        String defaultUser = isProduction() ? "graxsof3_omcgc" : "root";
        return getEnvOrDefault("DB_USER", defaultUser);
    }

    private String getDbPass() {
        // Password de Producción validado: Walook_2026!SecureDB
        String defaultPass = isProduction() ? "Walook_2026!SecureDB" : "";
        return getEnvOrDefault("DB_PASS", defaultPass);
    }

    /**
     * Fabrica y configura el bean de conexión a Base de Datos (DataSource).
     * Este componente es inyectado automáticamente en Repositorios y Servicios.
     *
     * @return DataSource configurado e instanciado.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getDbDriver());
        dataSource.setUrl(getDbUrl());
        dataSource.setUsername(getDbUser());
        dataSource.setPassword(getDbPass());

        System.out.println("[SYSTEM-CONFIG] Inicializando Pool de Conexiones SQL desde: " + getDbDriver());
        return dataSource;
    }
}
