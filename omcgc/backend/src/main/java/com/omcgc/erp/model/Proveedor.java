/*
============================================================
Nombre del archivo : Proveedor.java
Ruta              : backend/src/main/java/com/omcgc/erp/model/Proveedor.java
Tipo              : Backend (Entity)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Entidad que representa la tabla 'proveedor' en la base de datos.
Contiene la información fiscal, comercial y de contacto de los proveedores.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M05-01 (Registrar proveedor):
   - Estructura de datos para el alta de proveedores.
2. RF-08 (Gestión de proveedores):
   - Definición de campos fiscales y comerciales requeridos por el SAT y compras.
============================================================
*/

package com.omcgc.erp.model;

import java.sql.Timestamp;

public class Proveedor {

    private String idProveedor;
    private String tipoPersona; // NUEVO CAMPO
    private String rfc;
    private String razonSocial;
    private String nombreComercial;
    private String contacto;
    private String telefono;
    private String email;
    private String condicionPago;
    private String estatus;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    /** Identificador del usuario que realiza la operacion (Transient) */
    private String idUsuarioOperacion;

    // ==========================================
    // CONSTRUCTORES
    // ==========================================

    public Proveedor() {
    }

    public Proveedor(String idProveedor, String tipoPersona, String rfc, String razonSocial, String nombreComercial,
            String contacto,
            String telefono, String email, String condicionPago, String estatus) {
        this.idProveedor = idProveedor;
        this.tipoPersona = tipoPersona;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.condicionPago = condicionPago;
        this.estatus = estatus;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public String getIdUsuarioOperacion() {
        return idUsuarioOperacion;
    }

    public void setIdUsuarioOperacion(String idUsuarioOperacion) {
        this.idUsuarioOperacion = idUsuarioOperacion;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "idProveedor='" + idProveedor + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", rfc='" + rfc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", estatus='" + estatus + '\'' +
                '}';
    }
}
