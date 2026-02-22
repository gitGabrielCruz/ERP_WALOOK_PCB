/*
============================================================
Nombre del archivo : LogPattern.java
Ruta              : backend/src/main/java/com/omcgc/erp/model/LogPattern.java
Tipo              : Backend (Model)

Propósito:
Representar un patrón de mensaje de auditoría estandarizado para
la Matriz Maestra Universal.
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
