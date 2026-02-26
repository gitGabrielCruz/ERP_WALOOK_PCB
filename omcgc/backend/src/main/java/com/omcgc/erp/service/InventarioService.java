/*
============================================================
Nombre del archivo : InventarioService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/InventarioService.java
Tipo              : Backend (Service / Lógica de Negocio)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Implementar la inteligencia de negocio del módulo de inventarios. Gestiona 
validaciones de stock, cálculos financieros de precios y la integridad 
transaccional del Kardex operativo.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Gestión de Productos): 
   - Lógica de persistencia y cálculo dinámico de PVP.
2. HU-M01-05 (Reglas de Negocio de Stock):
   - Validación de existencias negativas y control por sucursal.
3. RNF-03 (Consistencia Financiera):
   - Actualización automática de costos en entradas por compra.
============================================================
*/

package com.omcgc.erp.service;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.model.Producto;
import com.omcgc.erp.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Servicio cl.InventarioService encargado de la orquestación operativa del
 * inventario.
 * Aplica reglas estrictas de validación antes de delegar a la persistencia.
 */
@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private BitacoraService bitacoraService;

    // --- SECCIÓN: GESTIÓN DE PRODUCTOS (MAESTRO) ---

    public List<Producto> findAllProducts() {
        return inventarioRepository.findAll();
    }

    public Producto findProductById(String id) {
        return inventarioRepository.findById(id);
    }

    /**
     * Registra o actualiza un producto aplicando reglas de cálculo fn.saveProduct.
     * 
     * @param p  Entidad cl.Producto.
     * @param ip IP de origen.
     */
    public void saveProduct(Producto p, String ip) {
        boolean isNew = (p.getIdProducto() == null || p.getIdProducto().isEmpty());
        // [REGLA ID] Generación de Identidad Única
        if (isNew) {
            p.setIdProducto(java.util.UUID.randomUUID().toString());
        }

        // [REGLA SKU] Generación de Identificatorio Comercial (75XXXXX)
        if (p.getSku() == null || p.getSku().isEmpty() || p.getSku().equalsIgnoreCase("Autogenerado")) {
            // Generador simple basado en tiempo para asegurar unicidad comercial
            String timestamp = String.valueOf(System.currentTimeMillis());
            p.setSku("75" + timestamp.substring(timestamp.length() - 8));
        }

        // REGLA 2: Cálculo Dinámico de PVP
        if (p.getCostoUnitario() != null && p.getPorcentajeUtilidad() != null) {
            BigDecimal factor = BigDecimal.ONE
                    .add(p.getPorcentajeUtilidad().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
            p.setPrecioVenta(p.getCostoUnitario().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        }

        inventarioRepository.save(p);

        // Bitácora
        String idPatron = isNew ? "PRO-01" : "PRO-02";
        bitacoraService.registrarEvento(p.getIdUsuarioOperacion(), idPatron, ip, p.getSku(), p.getNombre());
    }

    public void deleteProduct(String id) {
        inventarioRepository.deleteById(id);
    }

    // --- SECCIÓN: OPERACIONES DE INVENTARIO (KARDEX) ---

    public List<MovimientoInventario> getKardex(String idProducto) {
        return inventarioRepository.findKardexByProducto(idProducto);
    }

    public MovimientoInventario getMovimientoById(String id) {
        return inventarioRepository.findMovimientoById(id);
    }

    /**
     * Ejecuta una transacción de inventario bajo integridad fn.registrarMovimiento.
     * 
     * @param m Entidad cl.MovimientoInventario.
     */
    @Transactional
    public void registrarMovimiento(MovimientoInventario m, String ip) {
        // 1. Obtener estado actual (Baseline)
        Integer stockAnterior = inventarioRepository.getCurrentStock(m.getIdProducto(), m.getIdSucursal());
        m.setExistenciaAnterior(stockAnterior);

        // 2. Calcular afectación
        int factor = esSalida(m.getTipoMovimiento()) ? -1 : 1;
        Integer nuevoStock = stockAnterior + (m.getCantidad() * factor);

        // REGLA 4: Validación Transaccional (Anti-Negativos)
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente. Operación denegada. Existencia actual: " + stockAnterior);
        }

        m.setExistenciaActual(nuevoStock);

        // [REGLA FOLIO] Generación de Folio Interno Automático
        // Siempre se genera un folio INV- para control interno si no existe uno de
        // sistema
        if (m.getFolio() == null || m.getFolio().trim().isEmpty() || !m.getFolio().startsWith("INV-")) {
            String folioAuto = "INV-" + (System.currentTimeMillis() / 1000);
            // Si el usuario envió un folio manual, lo movemos a origenId (Referencia)
            if (m.getFolio() != null && !m.getFolio().isEmpty() && !m.getFolio().equals("S/F")) {
                m.setOrigenId(m.getFolio());
            }
            m.setFolio(folioAuto);
        }

        // 3. Persistir movimiento y actualizar existencia
        inventarioRepository.saveMovimiento(m);
        inventarioRepository.updateExistencia(m.getIdProducto(), m.getIdSucursal(), nuevoStock);

        // Trazabilidad Forense (v5.2)
        Producto pInfo = inventarioRepository.findById(m.getIdProducto());
        String prodDesc = pInfo != null ? "[" + pInfo.getSku() + "] " + pInfo.getNombre() : "ID: " + m.getIdProducto();
        String flowSign = (m.getCantidad() >= 0 ? "+" : "");
        String sucesoInv = String.format("Póliza: %s [%s]", m.getFolio(), m.getTipoMovimiento().replace("_", " "));

        String rastroDetalle = String.format(
                "PROD: %s\nDOCTO: %s\nOPERACIÓN: Se sumaron %s%d unidades.\nEVOLUCIÓN: Stock %d -> %d\nOBS: %s",
                prodDesc,
                (m.getOrigenId() != null ? m.getOrigenId() : "S/DOC"),
                flowSign,
                m.getCantidad(),
                stockAnterior,
                nuevoStock,
                (m.getObservaciones() != null && !m.getObservaciones().isEmpty() ? m.getObservaciones()
                        : "Sin observaciones"));

        // Bitácora Maestro (Uso de placeholder {S} para el suceso dinámico y análisis
        // enriquecido)
        bitacoraService.registrarEvento(m.getIdUsuario(), "INV-01", ip, sucesoInv, rastroDetalle);

        // REGLA 5: Actualización Automática de Costo en Entrada por Compra
        if ("ENTRADA_COMPRA".equals(m.getTipoMovimiento())) {
            Producto p = inventarioRepository.findById(m.getIdProducto());
            if (p != null) {
                p.setCostoUnitario(m.getCostoHistorico());
                p.setIdUsuarioOperacion(m.getIdUsuario()); // Para la bitácora de saveProduct
                saveProduct(p, ip); // Dispara recálculo de precio de venta
            }
        }
    }

    /**
     * Determina si el tipo de movimiento representa una reducción de activos
     * fn.esSalida.
     */
    private boolean esSalida(String tipo) {
        return "SALIDA_VENTA".equals(tipo) || "MERMA".equals(tipo) || "TRASPASO_SALIDA".equals(tipo);
    }

    /**
     * Elimina un movimiento y revierte su impacto en el stock.
     */
    @Transactional
    public void deleteMovimiento(String idMovimiento, String ip) {
        MovimientoInventario m = inventarioRepository.findMovimientoById(idMovimiento);
        if (m == null)
            throw new RuntimeException("Movimiento no encontrado: " + idMovimiento);

        // 1. Obtener estado actual
        Integer stockActual = inventarioRepository.getCurrentStock(m.getIdProducto(), m.getIdSucursal());

        // 2. Calcular reversión
        // Si fue una ENTRADA (sumó stock), ahora debemos RESTAR.
        // Si fue una SALIDA (restó stock), ahora debemos SUMAR.
        int factorReversion = esSalida(m.getTipoMovimiento()) ? 1 : -1;
        int nuevoStock = stockActual + (m.getCantidad() * factorReversion);

        if (nuevoStock < 0) {
            throw new RuntimeException(
                    "No se puede eliminar el movimiento. La reversión resultaría en stock negativo.");
        }

        // 3. Aplicar cambios
        inventarioRepository.deleteMovimientoById(idMovimiento);
        inventarioRepository.updateExistencia(m.getIdProducto(), m.getIdSucursal(), nuevoStock);

        // Bitácora de Reversión
        bitacoraService.registrarEvento(m.getIdUsuario(), "INV-01", ip,
                "REVERSIÓN: " + stockActual + " -> " + nuevoStock,
                "Eliminación de movimiento folio: " + (m.getFolio() != null ? m.getFolio() : idMovimiento));
    }

    /**
     * Actualiza un movimiento existente recalculando el stock operativo
     * mediante neutralización (Reverse -> Apply).
     */
    @Transactional
    public void actualizarMovimiento(MovimientoInventario n, String ip) {
        MovimientoInventario o = inventarioRepository.findMovimientoById(n.getIdMovimiento());
        if (o == null)
            throw new RuntimeException("Movimiento original no encontrado.");

        // 1. Obtener stock actual global
        Integer stockActual = inventarioRepository.getCurrentStock(o.getIdProducto(), o.getIdSucursal());

        // 2. Neutralizar impacto anterior
        int factorO = esSalida(o.getTipoMovimiento()) ? 1 : -1;
        Integer stockBase = stockActual + (o.getCantidad() * factorO);

        // 3. Aplicar nuevo impacto
        int factorN = esSalida(n.getTipoMovimiento()) ? -1 : 1;
        Integer nuevoStock = stockBase + (n.getCantidad() * factorN);

        if (nuevoStock < 0) {
            throw new RuntimeException(
                    "Error en actualización: El nuevo saldo resultaría negativo (" + nuevoStock + ").");
        }

        // 4. Actualizar datos en objeto y persistir
        n.setExistenciaAnterior(stockBase);
        n.setExistenciaActual(nuevoStock);

        // 4. Preservar Folio Interno y Documento de Referencia
        if (n.getFolio() == null || n.getFolio().trim().isEmpty() || !n.getFolio().startsWith("INV-")) {
            // Si el objeto nuevo n trae un folio (ej: desde el input del usuario)
            // y no es un INV-, lo movemos a origenId
            if (n.getFolio() != null && !n.getFolio().isEmpty() && !n.getFolio().startsWith("INV-")) {
                n.setOrigenId(n.getFolio());
            }
            n.setFolio(o.getFolio()); // Mantener el INV- original
        }

        inventarioRepository.saveMovimiento(n);
        inventarioRepository.updateExistencia(n.getIdProducto(), n.getIdSucursal(), nuevoStock);

        // Trazabilidad Forense (v5.2 - Detección de Deltas)
        Producto pInfo = inventarioRepository.findById(n.getIdProducto());
        String prodDesc = pInfo != null ? "[" + pInfo.getSku() + "] " + pInfo.getNombre() : "ID: " + n.getIdProducto();

        String sucesoInv = String.format("Edición Póliza: %s",
                (n.getFolio() != null ? n.getFolio() : n.getIdMovimiento()));

        StringBuilder deltas = new StringBuilder();
        deltas.append("PROD: ").append(prodDesc).append("\n");
        deltas.append("AJUSTES REALIZADOS:\n");

        if (!o.getCantidad().equals(n.getCantidad())) {
            deltas.append(String.format("- Cantidad: %d -> %d\n", o.getCantidad(), n.getCantidad()));
        }
        if (o.getTipoMovimiento() != null && !o.getTipoMovimiento().equals(n.getTipoMovimiento())) {
            deltas.append(String.format("- Tipo: %s -> %s\n", o.getTipoMovimiento(), n.getTipoMovimiento()));
        }

        deltas.append(String.format("IMPACTO EN EXISTENCIA: %d -> %d\n", stockActual, nuevoStock));
        deltas.append("OBS: ")
                .append(n.getObservaciones() != null && !n.getObservaciones().isEmpty() ? n.getObservaciones()
                        : "Sin observaciones");

        bitacoraService.registrarEvento(n.getIdUsuario(), "INV-01", ip, sucesoInv, deltas.toString());
    }
}
