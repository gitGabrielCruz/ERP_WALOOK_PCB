/*
============================================================
Nombre del archivo : MainApplication.java
Ruta              : omcgc/backend/src/com/omcgc/MainApplication.java
Tipo              : Backend (Punto de Entrada)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Clase principal de arranque del microservicio Spring Boot. Inicializa el contexto
de la aplicación, el escaneo de componentes y la configuración automática.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. Arquitectura Base:
   - Configuración de @SpringBootApplication para autoconfiguración.
   - Definición de @ComponentScan para localización de beans en paquetes hijos.
============================================================
*/
package com.omcgc.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
