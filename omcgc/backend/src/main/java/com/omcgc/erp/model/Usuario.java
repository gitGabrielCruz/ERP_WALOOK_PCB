/*
============================================================
Nombre del archivo : Usuario.java
Ruta              : backend/src/main/java/com/omcgc/erp/model/Usuario.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v2.0

Propósito:
Modelo de datos para Usuario con campos completos para gestión CRUD
y compatibilidad con frontend.
============================================================
*/
package com.omcgc.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

public class Usuario {
    // Campos de base de datos
    private String id; // id_usuario
    private String usuario; // nombre (username)
    private String correo; // email
    private String nombre; // nombre_completo

    @JsonIgnore
    private String password; // password_hash (no enviar al frontend)

    private String estatus; // activo/inactivo (derivado de campo boolean)
    private LocalDateTime creadoEn;

    // Campos de relación con Rol
    private String rolId; // id_rol
    private String rolNombre; // nombre_rol

    // Campo de relación con Sucursal
    private String idSucursal; // id_sucursal (contexto operativo)

    // Campo transitorio para devolver contraseña generada al frontend (no se guarda
    // en BD)
    private transient String passwordTemp;

    // Lista de permisos para guardado atómico
    private java.util.List<java.util.Map<String, Object>> permisos;

    // Constructores
    public Usuario() {
    }

    public Usuario(String id, String usuario, String correo, String nombre) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    // Métodos de compatibilidad con código legacy
    @JsonIgnore
    public String getIdUsuario() {
        return id;
    }

    public void setIdUsuario(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getEmail() {
        return correo;
    }

    public void setEmail(String email) {
        this.correo = email;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    @JsonIgnore
    public boolean isActivo() {
        return "activo".equals(estatus);
    }

    public void setActivo(boolean activo) {
        this.estatus = activo ? "activo" : "inactivo";
    }

    @JsonIgnore
    public String getIdRol() {
        return rolId;
    }

    public void setIdRol(String idRol) {
        this.rolId = idRol;
    }

    @JsonIgnore
    public String getNombreRol() {
        return rolNombre;
    }

    public void setNombreRol(String nombreRol) {
        this.rolNombre = nombreRol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", usuario='" + usuario + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estatus='" + estatus + '\'' +
                ", rolNombre='" + rolNombre + '\'' +
                '}';
    }

    public String getPasswordTemp() {
        return passwordTemp;
    }

    public void setPasswordTemp(String passwordTemp) {
        this.passwordTemp = passwordTemp;
    }

    public java.util.List<java.util.Map<String, Object>> getPermisos() {
        return permisos;
    }

    public void setPermisos(java.util.List<java.util.Map<String, Object>> permisos) {
        this.permisos = permisos;
    }
}
