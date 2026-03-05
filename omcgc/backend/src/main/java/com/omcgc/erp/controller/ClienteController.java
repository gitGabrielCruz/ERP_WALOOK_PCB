/*
============================================================
Nombre del archivo : ClienteController.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/controller/ClienteController.java
Tipo              : Backend (Controlador REST Spring Boot)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Exponer endpoints API REST para el módulo de Clientes.
Mapea las peticiones HTTP (GET, POST, PUT) y coordina la seguridad y respuestas JSON.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RF-06 (Manejo de Clientes):
   - Endpoint GET /api/clientes (HU-M06-02).
   - Endpoint POST /api/clientes (HU-M06-01).
   - Manejo centralizado de excepciones y respuestas HTTP.
============================================================
*/
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Paciente;
import com.omcgc.erp.service.BitacoraService;
import com.omcgc.erp.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping
    public List<Paciente> listar(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String rfc,
            @RequestParam(required = false) String estatus) {
        return clienteService.buscarClientes(buscar, rfc, estatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtener(@PathVariable String id) {
        Paciente p = clienteService.obtenerPorId(id);
        if (p != null) {
            return ResponseEntity.ok(p);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Paciente cliente, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            // Nota: Aquí se debería validar el permiso "CREAR" del usuario en sesión
            Paciente nuevo = clienteService.guardarCliente(cliente);

            // REGISTRO DE EVENTO: CREACIÓN DE CLIENTE (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(cliente.getIdUsuarioOperacion(), "CLI-01", ip, nuevo.getRfc(),
                    nuevo.getNombre());

            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // REGISTRO DE ERROR DE SISTEMA
            bitacoraService.registrarEvento(null, "SYS-99", ip, "Error en POST /api/clientes", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody Paciente cliente,
            HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        try {
            cliente.setIdPaciente(id);
            Paciente actualizado = clienteService.guardarCliente(cliente);

            // REGISTRO DE EVENTO: ACTUALIZACIÓN DE CLIENTE (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(cliente.getIdUsuarioOperacion(), "CLI-02", ip, actualizado.getRfc(),
                    actualizado.getNombre());

            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            bitacoraService.registrarEvento(null, "SYS-99", ip, "Error en PUT /api/clientes/" + id, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }
}
