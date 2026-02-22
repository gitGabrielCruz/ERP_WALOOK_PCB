/*
============================================================
Nombre del archivo : ProveedorController.java
Ruta              : backend/src/main/java/com/omcgc/erp/controller/ProveedorController.java
Tipo              : Backend (REST Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Controlador REST para exponer la API de Proveedores.
Maneja las peticiones HTTP y delega la lógica al Servicio.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M05-01 (Registrar proveedor):
   - Endpoint POST /api/proveedores para creación.
2. HU-M05-02 (Consultar proveedor):
   - Endpoint GET /api/proveedores para búsqueda y filtrado.
3. RNF-03 (API REST):
   - Exposición de interfaz de servicio estandarizada.
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.Proveedor;
import com.omcgc.erp.service.BitacoraService;
import com.omcgc.erp.service.ProveedorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*") // Permitir acceso desde cualquier origen
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private BitacoraService bitacoraService;

    /**
     * Obtener lista de proveedores (con filtros opcionales).
     * GET /api/proveedores?buscar=...&estatus=...
     */
    @GetMapping
    public ResponseEntity<List<Proveedor>> getAll(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String estatus) {

        try {
            List<Proveedor> lista;

            if (buscar != null && !buscar.isEmpty()) {
                lista = proveedorService.search(buscar);
            } else if (estatus != null && !estatus.isEmpty()) {
                lista = proveedorService.findByEstatus(estatus);
            } else {
                lista = proveedorService.findAll();
            }

            return ResponseEntity.ok(lista);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Obtener proveedor por ID.
     * GET /api/proveedores/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getById(@PathVariable String id) {
        try {
            Proveedor p = proveedorService.findById(id);
            if (p != null) {
                return ResponseEntity.ok(p);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Valida existencia de RFC (para validación asíncrona en frontend).
     * GET /api/proveedores/validar-rfc/{rfc}
     */
    @GetMapping("/validar-rfc/{rfc}")
    public ResponseEntity<Map<String, Boolean>> checkRfc(@PathVariable String rfc) {
        // Reutilizamos la lógica de búsqueda del repository indirectamente o podríamos
        // exponer un método exists
        // Por simplicidad, intentamos buscarlo
        try {
            // Un poco ineficiente buscar todo, pero válido para prototipo.
            // Idealmente: service.existsByRfc(rfc)
            // Aquí usaremos la búsqueda general que ya tenemos
            List<Proveedor> encontrados = proveedorService.search(rfc);
            boolean existe = false;
            for (Proveedor p : encontrados) {
                if (p.getRfc().equalsIgnoreCase(rfc)) {
                    existe = true;
                    break;
                }
            }
            return ResponseEntity.ok(Map.of("exists", existe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo proveedor.
     * POST /api/proveedores
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Proveedor proveedor, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            Proveedor nuevo = proveedorService.create(proveedor);

            // REGISTRO DE EVENTO: CREACIÓN DE PROVEEDOR (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(proveedor.getIdUsuarioOperacion(), "PRV-01", ip, nuevo.getRfc(),
                    nuevo.getNombreComercial());

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "message", "Proveedor registrado correctamente",
                    "data", nuevo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()));
        } catch (Exception e) {
            bitacoraService.registrarEvento(null, "SYS-99", ip, "Error en POST /api/proveedores", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "error", "Error interno: " + e.getMessage()));
        }
    }

    /**
     * Actualizar proveedor.
     * PUT /api/proveedores/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Proveedor proveedor,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            proveedor.setIdProveedor(id);
            Proveedor actualizado = proveedorService.update(proveedor);

            if (actualizado != null) {
                // REGISTRO DE EVENTO: ACTUALIZACIÓN DE PROVEEDOR (PATRON MAESTRO CIFRADO)
                bitacoraService.registrarEvento(proveedor.getIdUsuarioOperacion(), "PRV-02", ip, actualizado.getRfc(),
                        actualizado.getNombreComercial());

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Proveedor actualizado correctamente",
                        "data", actualizado));
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()));
        } catch (Exception e) {
            bitacoraService.registrarEvento(null, "SYS-99", ip, "Error en PUT /api/proveedores/" + id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "error", "Error interno: " + e.getMessage()));
        }
    }

    /**
     * Eliminar proveedor (Baja Lógica / Física según implementación servicio).
     * DELETE /api/proveedores/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            boolean eliminado = proveedorService.delete(id);
            if (eliminado) {
                // REGISTRO DE EVENTO: ELIMINACIÓN DE PROVEEDOR (PATRON MAESTRO CIFRADO)
                bitacoraService.registrarEvento(null, "CRUD-03", ip, "ID: " + id, "Baja del catalogo de proveedores");

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Proveedor eliminado correctamente"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            bitacoraService.registrarEvento(null, "SYS-99", ip, "Error en DELETE /api/proveedores/" + id,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "error", "Error interno: " + e.getMessage()));
        }
    }
}
