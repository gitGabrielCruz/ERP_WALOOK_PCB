/*
============================================================
Nombre del archivo : LogPattern.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/LogPattern.java
Tipo              : Backend (Model)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Representar un patrón de mensaje de auditoría estandarizado para
la Matriz Maestra Universal. Facilita la clasificación de eventos.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-01 (Auditoría y Trazabilidad):
   - Estructura de mapeo para el diccionario de auditoría persistente.
============================================================
*/
package com.omcgc.erp.model;

import java.io.Serializable;

public class LogPattern implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String impacto;
    private String categoria;
    private String mensajeAmigable;
    private String analisisTecnico;

    public LogPattern(String id, String impacto, String categoria, String mensajeAmigable, String analisisTecnico) {
        this.id = id;
        this.impacto = impacto;
        this.categoria = categoria;
        this.mensajeAmigable = mensajeAmigable;
        this.analisisTecnico = analisisTecnico;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getImpacto() {
        return impacto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMensajeAmigable() {
        return mensajeAmigable;
    }

    public String getAnalisisTecnico() {
        return analisisTecnico;
    }

    @Override
    public String toString() {
        return impacto + " | [" + categoria + "] | " + mensajeAmigable + " | " + analisisTecnico + " | " + id;
    }
}
