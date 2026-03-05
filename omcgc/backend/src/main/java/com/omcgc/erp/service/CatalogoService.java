/*
============================================================
Nombre del archivo : CatalogoService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/CatalogoService.java
Tipo              : Backend (Service / Lógica de Negocio)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Orquestar la lógica de negocio para la recuperación de catálogos base del 
inventario. Actúa como mediador entre los controladores y el repositorio 
de catálogos, asegurando la integridad de las consultas jerárquicas.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Categorización Jerárquica):
   - Provisión de Grupos, Familias y Marcas para la normalización del maestro.
============================================================
*/

package com.omcgc.erp.service;

import com.omcgc.erp.model.CatFamilia;
import com.omcgc.erp.model.CatGrupo;
import com.omcgc.erp.model.CatMarca;
import com.omcgc.erp.model.Sucursal;
import com.omcgc.erp.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar las peticiones de
 * catálogos maestros. Implementa desacoplamiento entre persistencia y
 * presentación.
 */
@Service
public class CatalogoService {

    /** Repositorio de catálogos inyectado */
    @Autowired
    private CatalogoRepository catalogoRepository;

    /**
     * Recupera el listado completo de grupos raíz.
     * 
     * @return Colección de objetos de grupo.
     */
    public List<CatGrupo> getAllGrupos() {
        return catalogoRepository.findAllGrupos();
    }

    /**
     * Recupera todas las familias independientemente del grupo.
     * 
     * @return Colección de objetos de familia.
     */
    public List<CatFamilia> getAllFamilias() {
        return catalogoRepository.findAllFamilias();
    }

    /**
     * Recupera familias filtradas por la pertenencia a un grupo jerárquico.
     * 
     * @param idGrupo Identificador UUID del grupo padre.
     * @return Colección de objetos de familia vinculada.
     */
    public List<CatFamilia> getFamiliasByGrupo(String idGrupo) {
        return catalogoRepository.findFamiliasByGrupo(idGrupo);
    }

    /**
     * Recupera el catálogo maestro de marcas comerciales.
     * 
     * @return Colección de objetos de marca.
     */
    public List<CatMarca> getAllMarcas() {
        return catalogoRepository.findAllMarcas();
    }

    /**
     * Recupera el catálogo de sucursales.
     * 
     * @return Colección de objetos de sucursal.
     */
    public List<Sucursal> getAllSucursales() {
        return catalogoRepository.findAllSucursales();
    }
}
