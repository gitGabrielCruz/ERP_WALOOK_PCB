/*
============================================================
Nombre del archivo : CatMarca.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/CatMarca.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Gestionar el catálogo maestro de marcas comerciales. Esta entidad cl.CatMarca 
permite la normalización del inventario, asegurando la consistencia de los datos
comerciales y facilitando el análisis estadístico por proveedor o branding.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Categorización Jerárquica):
   - Registro y normalización de marcas asociadas a los productos.
============================================================
*/

package com.omcgc.erp.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio cl.CatMarca representativa de la tabla tb.cat_marca.
 * Estructura de datos técnica para la gestión de identidad comercial de los
 * productos.
 */
public class CatMarca {

    /** Identificador primario de la marca vr.idMarca */
    private String idMarca;

    /** Denominación oficial de la marca vr.nombre */
    private String nombre;

    /** Registro cronológico de inserción en el sistema vr.creadoEn */
    private LocalDateTime creadoEn;

    /**
     * Constructor fn.CatMarca por defecto.
     */
    public CatMarca() {
    }

    /**
     * Retorna el ID de la marca fn.getIdMarca.
     * 
     * @return vr.idMarca
     */
    public String getIdMarca() {
        return idMarca;
    }

    /**
     * Define el ID de la marca fn.setIdMarca.
     * 
     * @param idMarca UUID asignado.
     */
    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    /**
     * Obtiene la denominación fn.getNombre.
     * 
     * @return vr.nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define la denominación formal de la marca fn.setNombre.
     * 
     * @param nombre Nombre de la marca.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Accede a la estampa de tiempo fn.getCreadoEn.
     * 
     * @return vr.creadoEn
     */
    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    /**
     * Define la fecha de registro fn.setCreadoEn.
     * 
     * @param creadoEn Timestamps de creación.
     */
    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
