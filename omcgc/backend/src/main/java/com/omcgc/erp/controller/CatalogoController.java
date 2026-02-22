/*
============================================================
Nombre del archivo : CatalogoController.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/controller/CatalogoController.java
Tipo              : Backend (Controller / API REST)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Exponer los endpoints REST para el acceso a los catálogos maestros de 
inventario. Facilita la carga de datos auxiliares para la interfaz de usuario.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Categorización Jerárquica):
   - Endpoints para Grupos, Familias (filtradas y generales) y Marcas.
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.CatFamilia;
import com.omcgc.erp.model.CatGrupo;
import com.omcgc.erp.model.CatMarca;
import com.omcgc.erp.model.Sucursal;
import com.omcgc.erp.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador API REST cl.CatalogoController para gestión de catálogos base.
 */
@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    /**
     * Endpoint para obtener grupos maestros GET /api/catalogos/grupos.
     */
    @GetMapping("/grupos")
    public ResponseEntity<List<CatGrupo>> getGrupos() {
        return ResponseEntity.ok(catalogoService.getAllGrupos());
    }

    /**
     * Endpoint para obtener todas las familias GET /api/catalogos/familias.
     */
    @GetMapping("/familias/todas")
    public ResponseEntity<List<CatFamilia>> getAllFamilias() {
        return ResponseEntity.ok(catalogoService.getAllFamilias());
    }

    /**
     * Endpoint para familias filtradas por grupo GET /api/catalogos/familias.
     * 
     * @param grupoId Identificador del grupo solicitado.
     */
    @GetMapping("/familias")
    public ResponseEntity<List<CatFamilia>> getFamilias(@RequestParam String grupoId) {
        return ResponseEntity.ok(catalogoService.getFamiliasByGrupo(grupoId));
    }

    /**
     * Endpoint para el catálogo de marcas GET /api/catalogos/marcas.
     */
    @GetMapping("/marcas")
    public ResponseEntity<List<CatMarca>> getMarcas() {
        return ResponseEntity.ok(catalogoService.getAllMarcas());
    }

    /**
     * Endpoint para el catálogo de sucursales GET /api/catalogos/sucursales.
     * 
     * @return Listado de sucursales operativas.
     */
    @GetMapping("/sucursales")
    public ResponseEntity<List<Sucursal>> getSucursales() {
        return ResponseEntity.ok(catalogoService.getAllSucursales());
    }
}
