/*
============================================================
Nombre del archivo : Producto.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Producto.java
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
Entidad maestra cl.Producto destinada a encapsular la totalidad de atributos
técnicos, comerciales, fiscales y logísticos de un artículo del inventario.
Actúa como el núcleo central de la lógica operacional del módulo M01.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Gestión de Productos): 
   - CRUD integral de la definición del producto e información SKU.
2. HU-M01-09 (Datos Fiscales SAT):
   - Soporte para claves de producto/servicio y unidades de medida (CFDI 4.0).
3. RNF-03 (Consistencia Financiera):
   - Manejo de costos unitarios y cálculo embebido de márgenes de utilidad.
============================================================
*/

package com.omcgc.erp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio fundamental cl.Producto.
 * Mapeada a la tabla tb.producto, coordina la jerarquía de categorías,
 * parámetros de stock, datos fiscales del SAT y variables financieras.
 */
public class Producto {

    // --- SECCIÓN: IDENTIDAD Y CODIFICACIÓN ---

    /** Identificador de objeto único vr.idProducto */
    private String idProducto;
    /** Stock Keeping Unit - Identificador único comercial vr.sku */
    private String sku;
    /** Denominación comercial completa del producto vr.nombre */
    private String nombre;
    /** Representación simbólica del código de barras vr.codigoBarras */
    private String codigoBarras;

    // --- SECCIÓN: CLASIFICACIÓN JERÁRQUICA ---

    /** Relación con el grupo raíz vr.idGrupo */
    private String idGrupo;
    /** Relación con la familia específica vr.idFamilia */
    private String idFamilia;
    /** Relación con el catálogo de marcas vr.idMarca */
    private String idMarca;

    /**
     * Atributos transitorios (Transient) para visualización en UI sin re-mapeo
     * vr.nombreGrupo
     */
    private String nombreGrupo;
    /**
     * Atributos transitorios (Transient) para visualización en UI sin re-mapeo
     * vr.nombreFamilia
     */
    private String nombreFamilia;
    /**
     * Atributos transitorios (Transient) para visualización en UI sin re-mapeo
     * vr.nombreMarca
     */
    private String nombreMarca;

    // --- SECCIÓN: CONTROL LOGÍSTICO (EXISTENCIAS) ---

    /** Límite inferior de stock para alertas de reabastecimiento vr.stockMinimo */
    private Integer stockMinimo;
    private Integer stockMaximo;

    /** Identificador del usuario que realiza la operacion (Transient) */
    private String idUsuarioOperacion;
    /**
     * Existencia actual operativa (Transient, calculada desde existencia)
     * vr.existenciaActual
     */
    private Integer existenciaActual;

    // --- SECCIÓN: VARIABLES FINANCIERAS ---

    /** Valor de adquisición neto por unidad vr.costoUnitario */
    private BigDecimal costoUnitario;
    /** Margen de ganancia porcentual pretendido vr.porcentajeUtilidad */
    private BigDecimal porcentajeUtilidad;
    /**
     * Valor de mercado resultante (Sincronizado vía base de datos) vr.precioVenta
     */
    private BigDecimal precioVenta;

    // --- SECCIÓN: CUMPLIMIENTO FISCAL (CFDI 4.0) ---

    /** Clave de producto o servicio según catálogo del SAT vr.claveProdServ */
    private String claveProdServ;
    /** Clave de unidad de medida comercial vr.claveUnidad */
    private String claveUnidad;
    /** Indicador de objeto de impuesto (01, 02) vr.objetoImpuesto */
    private String objetoImpuesto;
    /** Tasa impositiva aplicable (ej: 16.00) vr.ivaAplicable */
    private BigDecimal ivaAplicable;

    // --- SECCIÓN: AUDITORÍA Y ESTADO ---

    /** Identificador del proveedor preferencial vr.idProveedor */
    private String idProveedor;
    /** Nombre comercial del proveedor (Transient) vr.nombreProveedor */
    private String nombreProveedor;
    /** Estado operativo de la entidad (ACTIVO/INACTIVO) vr.estatus */
    private String estatus;
    /** Fecha de registro inicial vr.fechaCreacion */
    private LocalDateTime fechaCreacion;
    /** Fecha del último cambio registrado vr.fechaModificacion */
    private LocalDateTime fechaModificacion;

    /**
     * Constructor fn.Producto por defecto.
     */
    public Producto() {
    }

    // --- ACCESORES (GETTERS) Y MUTADORES (SETTERS) ---

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(String idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(Integer stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public String getIdUsuarioOperacion() {
        return idUsuarioOperacion;
    }

    public void setIdUsuarioOperacion(String idUsuarioOperacion) {
        this.idUsuarioOperacion = idUsuarioOperacion;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getObjetoImpuesto() {
        return objetoImpuesto;
    }

    public void setObjetoImpuesto(String objetoImpuesto) {
        this.objetoImpuesto = objetoImpuesto;
    }

    public BigDecimal getIvaAplicable() {
        return ivaAplicable;
    }

    public void setIvaAplicable(BigDecimal ivaAplicable) {
        this.ivaAplicable = ivaAplicable;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Integer getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(Integer existenciaActual) {
        this.existenciaActual = existenciaActual;
    }
}
