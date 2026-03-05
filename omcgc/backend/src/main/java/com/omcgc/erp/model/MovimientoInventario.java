/*
============================================================
Nombre del archivo : MovimientoInventario.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/MovimientoInventario.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Registrar la trazabilidad de cada transacción operativa que afecte el 
stock físico de la organización. Esta entidad constituye 
la base técnica para el reporte de Kardex y auditorías de existencias.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-05 (Control de Movimientos / Kardex):
   - Registro cronológico de entradas, salidas y ajustes de inventario.
2. RNF-05 (Auditoría Técnica):
   - Conservación del historial de saldo anterior y saldo actual por transacción.
============================================================
*/

package com.omcgc.erp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad de dominio mapeada a la tabla de movimientos.
 * Proporciona el registro detallado de los flujos de mercancía entre sucursales
 * y operaciones de venta o compra.
 */
public class MovimientoInventario {

   /** Identificador único del movimiento */
   private String idMovimiento;

   /** Folio administrativo generado para el comprobante */
   private String folio;

   /**
    * Categorización del flujo (ENTRADA_COMPRA, SALIDA_VENTA, AJUSTE, TRASPASO,
    * etc.)
    */
   private String tipoMovimiento;

   /** Identificador del producto afectado */
   private String idProducto;

   /** Identificador de la sucursal origen/destino */
   private String idSucursal;

   /** Magnitud numérica del cambio en stock */
   private Integer cantidad;

   /** Valor monetario registrado al momento de la transacción */
   private BigDecimal costoHistorico;

   /** Saldo de stock previo a la afectación (Auditoría) */
   private Integer existenciaAnterior;

   /** Saldo de stock resultante tras la afectación (Auditoría) */
   private Integer existenciaActual;

   /** Fecha de expiración específica para el lote */
   private LocalDate fechaVencimiento;

   /** Identificador del documento de origen (Venta, Compra, Traspaso) */
   private String origenId;

   /** Identificador del usuario que ejecutó la operación */
   private String idUsuario;

   /** Estampa de tiempo precisa del evento */
   private LocalDateTime fecha;

   /** Justificación o glosa textual de la operación */
   private String observaciones;

   // --- CAMPOS TRANSITORIOS PARA LA INTERFAZ DE USUARIO ---

   /** Nombre comercial del producto (Transitorio) */
   private String nombreProducto;
   /** SKU del producto para identificación rápida (Transitorio) */
   private String skuProducto;
   /** Nombre completo del usuario que operó (Transitorio) */
   private String nombreUsuario;

   /**
    * Constructor por defecto.
    */
   public MovimientoInventario() {
   }

   // --- ACCESORES (GETTERS) Y MUTADORES (SETTERS) ---

   public String getIdMovimiento() {
      return idMovimiento;
   }

   public void setIdMovimiento(String idMovimiento) {
      this.idMovimiento = idMovimiento;
   }

   public String getFolio() {
      return folio;
   }

   public void setFolio(String folio) {
      this.folio = folio;
   }

   public String getTipoMovimiento() {
      return tipoMovimiento;
   }

   public void setTipoMovimiento(String tipoMovimiento) {
      this.tipoMovimiento = tipoMovimiento;
   }

   public String getIdProducto() {
      return idProducto;
   }

   public void setIdProducto(String idProducto) {
      this.idProducto = idProducto;
   }

   public String getIdSucursal() {
      return idSucursal;
   }

   public void setIdSucursal(String idSucursal) {
      this.idSucursal = idSucursal;
   }

   public Integer getCantidad() {
      return cantidad;
   }

   public void setCantidad(Integer cantidad) {
      this.cantidad = cantidad;
   }

   public BigDecimal getCostoHistorico() {
      return costoHistorico;
   }

   public void setCostoHistorico(BigDecimal costoHistorico) {
      this.costoHistorico = costoHistorico;
   }

   public Integer getExistenciaAnterior() {
      return existenciaAnterior;
   }

   public void setExistenciaAnterior(Integer existenciaAnterior) {
      this.existenciaAnterior = existenciaAnterior;
   }

   public Integer getExistenciaActual() {
      return existenciaActual;
   }

   public void setExistenciaActual(Integer existenciaActual) {
      this.existenciaActual = existenciaActual;
   }

   public LocalDate getFechaVencimiento() {
      return fechaVencimiento;
   }

   public void setFechaVencimiento(LocalDate fechaVencimiento) {
      this.fechaVencimiento = fechaVencimiento;
   }

   public String getOrigenId() {
      return origenId;
   }

   public void setOrigenId(String origenId) {
      this.origenId = origenId;
   }

   public String getIdUsuario() {
      return idUsuario;
   }

   public void setIdUsuario(String idUsuario) {
      this.idUsuario = idUsuario;
   }

   public LocalDateTime getFecha() {
      return fecha;
   }

   public void setFecha(LocalDateTime fecha) {
      this.fecha = fecha;
   }

   public String getObservaciones() {
      return observaciones;
   }

   public void setObservaciones(String observaciones) {
      this.observaciones = observaciones;
   }

   public String getNombreProducto() {
      return nombreProducto;
   }

   public void setNombreProducto(String nombreProducto) {
      this.nombreProducto = nombreProducto;
   }

   public String getSkuProducto() {
      return skuProducto;
   }

   public void setSkuProducto(String skuProducto) {
      this.skuProducto = skuProducto;
   }

   public String getNombreUsuario() {
      return nombreUsuario;
   }

   public void setNombreUsuario(String nombreUsuario) {
      this.nombreUsuario = nombreUsuario;
   }
}