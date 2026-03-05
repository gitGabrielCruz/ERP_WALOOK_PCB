/*
============================================================
Nombre del archivo : Bitacora.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Bitacora.java
Tipo              : Backend (Model / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Representar un registro de auditoría. Los campos 'accion', 'ipOrigen' 
y 'detalles' se transportan y almacenan cifrados para privacidad total.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-01 (Auditoría Técnica):
   - Estructura de persistencia para eventos del sistema.
============================================================
*/
package com.omcgc.erp.model;

import java.sql.Timestamp;

public class Bitacora {
    private String idBitacora;
    private String idUsuario;
    private String nombreUsuario; // Campo transitorio para visualización
    private String accion; // Cifrado AES
    private String ipOrigen; // Cifrado AES
    private String detalles; // Cifrado AES
    private Timestamp fecha;

    public Bitacora() {
    }

    public String getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(String idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
