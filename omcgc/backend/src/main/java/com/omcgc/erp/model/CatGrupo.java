/*
============================================================
Nombre del archivo : CatGrupo.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/CatGrupo.java
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
Definir la entidad de persistencia para los grupos raíz del inventario. 
Esta clase cl.CatGrupo actúa como el nivel jerárquico superior en la 
categorización de productos, permitiendo segregar lógica de negocio 
crítica como el control de caducidades o la naturaleza del ítem (bien o servicio).

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Categorización Jerárquica):
   - Estructuración de grupos base para el catálogo maestro de inventarios.
2. RNF-01 (Integridad de Datos):
   - Definición de restricciones de negocio a nivel de entidad de dominio.
============================================================
*/

package com.omcgc.erp.model;

    
import java.time.LocalDateTime;

/**
 * Entidad de dominio cl.CatGrupo representativa de la tabla tb.cat_grupo.
 * Implementa las propiedades fundamentales para la discriminación de tipos
 * de inventario en el sistema ERP.
 */
public class CatGrupo {

    /** Identificador único universal (UUID) de la categoría grupo vr.idGrupo */
    private String idGrupo;

    /** Nombre descriptivo del grupo (ej: Armazones, Mantenimiento) vr.nombre */
    private String nombre;

    /** Flag booleano que determina si el grupo requiere trazabilidad de fechas de expiración vr.exigeCaducidad */
    private boolean exigeCaducidad;

    /** Determina si el grupo representa un servicio (intangible) o un producto físico vr.esServicio */
    private boolean esServicio;

    /** Estampa de tiempo de la creación del registro en el motor de persistencia vr.creadoEn */
    private LocalDateTime creadoEn;

    /**
     * Constructor fn.CatGrupo por defecto requerido por los frameworks de mapeo y serialización.
     */
    public CatGrupo() {}

    /**
     * Obtiene el identificador de la entidad fn.getIdGrupo.
     * @return vr.idGrupo en formato String (UUID).
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * Establece el identificador de la entidad fn.setIdGrupo.
     * @param idGrupo Identificador UUID proporcionado por el generador de persistencia.
     */
    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Obtiene el nombre del grupo fn.getNombre.
     * @return vr.nombre descriptivo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre del grupo fn.setNombre.
     * @param nombre Cadena de texto con el nombre comercial del grupo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Consulta el estado de la regla de caducidad fn.isExigeCaducidad.
     * @return true si los productos asociados deben validar fecha de vencimiento.
     */
    public boolean isExigeCaducidad() {
        return exigeCaducidad;
    }

    /**
     * Define si el grupo exige caducidad fn.setExigeCaducidad.
     * @param exigeCaducidad Estado lógico del requerimiento de expiración.
     */
    public void setExigeCaducidad(boolean exigeCaducidad) {
        this.exigeCaducidad = exigeCaducidad;
    }

    /**
     * Determina si la entidad representa un servicio fn.isEsServicio.
     * @return true si la naturaleza es intangible.
     */
    public boolean isEsServicio() {
        return esServicio;
    }

    /**
     * Define la naturaleza intangible del grupo fn.setEsServicio.
     * @param esServicio Estado lógico representativo de servicio.
     */
    public void setEsServicio(boolean esServicio) {
        this.esServicio = esServicio;
    }

    /**
     * Obtiene la fecha de auditoría de creación fn.getCreadoEn.
     * @return vr.creadoEn tipo LocalDateTime.
     */
    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    /**
     * Establece la fecha de creación fn.setCreadoEn.
     * @param creadoEn Timestamps del servidor de base de datos.
     */
    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
