/*
============================================================
Nombre del archivo : InventarioController.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/controller/InventarioController.java
Tipo              : Backend (Controller / API REST)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Punto de entrada API para la gestión integral del inventario. Centraliza 
las operaciones sobre el maestro de productos y el historial transaccional (Kardex).

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Maestro de Productos):
   - Registro, edición y consulta de artículos.
2. HU-M01-05 (Operaciones de Kardex):
   - Registro de movimientos y consulta de histórica.
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.model.Producto;
import com.omcgc.erp.service.InventarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador API REST cl.InventarioController para operaciones de inventario.
 */
@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // --- ENDPOINTS: MAESTRO DE PRODUCTOS ---

    /**
     * Recupera el catálogo completo de productos GET /api/inventarios/productos.
     */
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(inventarioService.findAllProducts());
    }

    /**
     * Recupera un producto específico por ID GET /api/inventarios/productos/{id}.
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable String id) {
        Producto p = inventarioService.findProductById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    /**
     * Persiste o actualiza un producto POST /api/inventarios/productos.
     */
    @PostMapping("/productos")
    public ResponseEntity<String> saveProducto(@RequestBody Producto p, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            inventarioService.saveProduct(p, ip);
            return ResponseEntity.ok("Producto procesado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar producto: " + e.getMessage());
        }
    }

    /**
     * Actualiza un producto existente PUT /api/inventarios/productos/{id}.
     */
    @PutMapping("/productos/{id}")
    public ResponseEntity<String> updateProducto(@PathVariable String id, @RequestBody Producto p,
            HttpServletRequest request) {
        try {
            p.setIdProducto(id);
            String ip = request.getRemoteAddr();
            inventarioService.saveProduct(p, ip);
            return ResponseEntity.ok("Producto actualizado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar producto: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto por ID DELETE /api/inventarios/productos/{id}.
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable String id) {
        // En una implementación de alta integridad, aquí se validaría la existencia y
        // falta de
        // movimientos históricos antes de permitir la eliminación.
        inventarioService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // --- ENDPOINTS: MOVIMIENTOS Y KARDEX ---

    /**
     * Obtiene el historial de Kardex de un artículo GET
     * /api/inventarios/productos/{id}/kardex.
     */
    @GetMapping("/productos/{id}/kardex")
    public ResponseEntity<List<MovimientoInventario>> getKardex(@PathVariable String id) {
        return ResponseEntity.ok(inventarioService.getKardex(id));
    }

    /**
     * Registra una nueva transacción de inventario POST
     * /api/inventarios/movimientos.
     */
    @PostMapping("/movimientos")
    public ResponseEntity<String> registrarMovimiento(@RequestBody MovimientoInventario m, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            inventarioService.registrarMovimiento(m, ip);
            return ResponseEntity.ok("Transacción registrada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict para errores de stock
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error sistémico: " + e.getMessage());
        }
    }

    /**
     * Elimina un movimiento y revierte el stock DELETE
     * /api/inventarios/movimientos/{id}.
     */
    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<String> eliminarMovimiento(@PathVariable String id, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            inventarioService.deleteMovimiento(id, ip);
            return ResponseEntity.ok("Movimiento eliminado y stock revertido.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar movimiento: " + e.getMessage());
        }
    }

    /**
     * Obtiene un movimiento específico por ID GET
     * /api/inventarios/movimientos/{id}.
     */
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoInventario> getMovimiento(@PathVariable String id) {
        MovimientoInventario m = inventarioService.getMovimientoById(id);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }

    /**
     * Actualiza un movimiento existente PUT /api/inventarios/movimientos/{id}.
     */
    @PutMapping("/movimientos/{id}")
    public ResponseEntity<String> actualizarMovimiento(@PathVariable String id, @RequestBody MovimientoInventario m,
            HttpServletRequest request) {
        try {
            m.setIdMovimiento(id);
            String ip = request.getRemoteAddr();
            inventarioService.actualizarMovimiento(m, ip);
            return ResponseEntity.ok("Movimiento actualizado y stock recalculado con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar: " + e.getMessage());
        }
    }
}
