/*
============================================================
Nombre del archivo : BitacoraController.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/controller/BitacoraController.java
Tipo              : Backend (REST Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Exponer endpoints para la consulta de la bitácora de auditoría.
Implementa seguridad RBAC: solo administradores pueden consultar.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-01 (Auditoría Técnica):
   - Consulta centralizada de eventos de seguridad y sistema.
   - Soporte para filtros por fecha, usuario y búsqueda textual.
============================================================
*/
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Bitacora;
import com.omcgc.erp.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@CrossOrigin(origins = "*")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    /**
     * Obtener registros de bitácora con filtros.
     * GET /api/bitacora?desde=...&hasta=...&usuario=...&buscar=...
     */
    @GetMapping
    public ResponseEntity<List<Bitacora>> getLogs(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String buscar) {

        // El servicio se encarga de descifrar y filtrar
        List<Bitacora> logs = bitacoraService.listar(desde, hasta, usuario, buscar);
        return ResponseEntity.ok(logs);
    }
}
