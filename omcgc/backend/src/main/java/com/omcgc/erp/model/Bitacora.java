/*
============================================================
Nombre del archivo : Bitacora.java
Ruta              : backend/src/main/java/com/omcgc/erp/model/Bitacora.java
Tipo              : Backend (Model / Entidad)

Propósito:
Representar un registro de auditoría. Los campos 'accion', 'ipOrigen' 
y 'detalles' se transportan y almacenan cifrados para privacidad total.
============================================================
*/
package com.omcgc.erp.model;

import java.sql.Timestamp;

public class Bitacora {
    private String idBitacora;
    private String idUsuario;
    private String nombreUsuario; // Campo transitorio para visualización
    private String accion; // AES Encrypted
    private String ipOrigen; // AES Encrypted
    private String detalles; // AES Encrypted
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
