/*
============================================================
Nombre del archivo : CatFamilia.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/CatFamilia.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Estructurar la entidad representativa del segundo nivel jerárquico 
dentro de la categorización de inventarios (Familias). Proporciona 
el nexo de unión entre los grupos raíz y las especificaciones de producto.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Categorización Jerárquica):
   - Definición de familias vinculadas operativamente a grupos específicos.
============================================================
*/

package com.omcgc.erp.model;

/**
 * Entidad de dominio mapeada a la tabla cat_familia.
 * Implementa el patrón de especialización jerárquica para la granularidad
 * de los activos del inventario.
 */
public class CatFamilia {

    /** Identificador único de la familia */
    private String idFamilia;

    /** Referencia (Foreign Key) al identificador del grupo padre */
    private String idGrupo;

    /** Nombre comercial o técnico de la familia */
    private String nombre;

    /**
     * Campo transitorio para facilitar la visualización del nombre del grupo en la
     * UI vr.nombreGrupo
     */
    private String nombreGrupo;

    /**
     * Constructor fn.CatFamilia por defecto para procesos de
     * Marshalling/Unmarshalling.
     */
    public CatFamilia() {
    }

    public String getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(String idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
}
