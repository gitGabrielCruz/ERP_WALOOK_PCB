/*
============================================================
Nombre del archivo : Paciente.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Paciente.java
Tipo              : Backend (Modelo/Entidad Java)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Entidad POJO que representa la tabla 'paciente' en la base de datos.
Mapea los datos personales, fiscales y de control requeridos para
la funcionalidad del módulo de Clientes.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M06-01 / HU-M06-02 (Gestión de Pacientes/Clientes):
   - Definición de atributos personales y fiscales (CFDI).
   - Soporte para auditoría y control de estatus.
============================================================
*/
package com.omcgc.erp.model;

import java.sql.Timestamp;

public class Paciente {
    private String idPaciente;
    private String nombre; // Nombre completo o Razón Social
    private String apellidos;
    private String razonSocial; // Razón Social (para personas morales)
    private String telefono;
    private String email;

    // Datos Fiscales (Modulo Clientes)
    private String rfc;
    private String tipoPersona; // FISICA, MORAL
    private String regimenFiscal;
    private String usoCfdi;
    private String domicilioFiscal;

    // Control
    private String estatus; // ACTIVO, INACTIVO
    private String fusionadoCon;

    // Auditoría
    private Timestamp fechaRegistro;
    private Timestamp fechaModificacion;

    /** Identificador del usuario que realiza la operacion (Transient) */
    private String idUsuarioOperacion;

    // Getters and Setters

    public String getIdUsuarioOperacion() {
        return idUsuarioOperacion;
    }

    public void setIdUsuarioOperacion(String idUsuarioOperacion) {
        this.idUsuarioOperacion = idUsuarioOperacion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(String usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFusionadoCon() {
        return fusionadoCon;
    }

    public void setFusionadoCon(String fusionadoCon) {
        this.fusionadoCon = fusionadoCon;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "Paciente [id=" + idPaciente + ", nombre=" + nombre + ", rfc=" + rfc + "]";
    }
}
