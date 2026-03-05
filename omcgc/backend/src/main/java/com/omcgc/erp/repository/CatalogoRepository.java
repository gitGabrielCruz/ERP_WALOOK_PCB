/*
============================================================
Nombre del archivo : CatalogoRepository.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/repository/CatalogoRepository.java
Tipo              : Backend (Repositorio de Datos / Persistencia)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Gestionar el acceso a datos para las tablas de catálogos base (Grupos, Familias 
y Marcas). Utiliza JdbcTemplate para garantizar un control granular 
sobre las consultas SQL y optimizar el rendimiento de las lecturas jerárquicas.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Categorización Jerárquica):
   - Recuperación de estructuras de clasificación para la alimentación de la UI.
============================================================
*/

package com.omcgc.erp.repository;

import com.omcgc.erp.model.CatFamilia;
import com.omcgc.erp.model.CatGrupo;
import com.omcgc.erp.model.CatMarca;
import com.omcgc.erp.model.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio especializado en operaciones de lectura
 * sobre catálogos maestros. Implementa el patrón Repository para desacoplar
 * el modelo de dominio de la lógica de persistencia.
 */
@Repository
public class CatalogoRepository {

    /** Inyección del motor de ejecución SQL */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- SECCIÓN: MAPPERS TÉCNICOS ---

    /**
     * Mapeador estático para transformar filas de cat_grupo a
     * objetos CatGrupo
     */
    private static final RowMapper<CatGrupo> GRUPO_MAPPER = (rs, rowNum) -> {
        CatGrupo g = new CatGrupo();
        g.setIdGrupo(rs.getString("id_grupo"));
        g.setNombre(rs.getString("nombre"));
        g.setExigeCaducidad(rs.getBoolean("exige_caducidad"));
        g.setEsServicio(rs.getBoolean("es_servicio"));
        g.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        return g;
    };

    /**
     * Mapeador estático para transformar filas de cat_familia
     * a objetos CatFamilia
     */
    private static final RowMapper<CatFamilia> FAMILIA_MAPPER = (rs, rowNum) -> {
        CatFamilia f = new CatFamilia();
        f.setIdFamilia(rs.getString("id_familia"));
        f.setIdGrupo(rs.getString("id_grupo"));
        f.setNombre(rs.getString("nombre"));
        // Validación defensiva para la recuperación del nombre del grupo vía JOIN
        try {
            f.setNombreGrupo(rs.getString("nombre_grupo"));
        } catch (Exception e) {
        }
        return f;
    };

    /**
     * Mapeador estático para persistencia de marcas cat_marca
     */
    private static final RowMapper<CatMarca> MARCA_MAPPER = (rs, rowNum) -> {
        CatMarca m = new CatMarca();
        m.setIdMarca(rs.getString("id_marca"));
        m.setNombre(rs.getString("nombre"));
        m.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        return m;
    };

    /**
     * Mapeador estático para transformar filas de sucursal
     * a objetos Sucursal
     */
    private static final RowMapper<Sucursal> SUCURSAL_MAPPER = (rs, rowNum) -> {
        Sucursal s = new Sucursal();
        s.setIdSucursal(rs.getString("id_sucursal"));
        s.setNombre(rs.getString("nombre"));
        s.setTelefono(rs.getString("telefono"));
        return s;
    };

    // --- SECCIÓN: MÉTODOS OPERATIVOS (GRUPOS) ---

    /**
     * Recupera la totalidad de grupos activos.
     * 
     * @return Lista de objetos CatGrupo ordenados alfabéticamente.
     */
    public List<CatGrupo> findAllGrupos() {
        return jdbcTemplate.query("SELECT * FROM cat_grupo ORDER BY nombre", GRUPO_MAPPER);
    }

    // --- SECCIÓN: MÉTODOS OPERATIVOS (FAMILIAS) ---

    /**
     * Obtiene todas las familias con su respectiva información de grupo.
     * 
     * @return Colección de familias con JOIN al grupo padre.
     */
    public List<CatFamilia> findAllFamilias() {
        String sql = "SELECT f.*, g.nombre as nombre_grupo FROM cat_familia f " +
                "JOIN cat_grupo g ON f.id_grupo = g.id_grupo " +
                "ORDER BY g.nombre, f.nombre";
        return jdbcTemplate.query(sql, FAMILIA_MAPPER);
    }

    /**
     * Filtra familias por su pertenencia a un grupo específico.
     * 
     * @param idGrupo Identificador UUID del grupo raíz.
     * @return Familias pertenecientes al clúster solicitado.
     */
    public List<CatFamilia> findFamiliasByGrupo(String idGrupo) {
        String sql = "SELECT f.*, g.nombre as nombre_grupo FROM cat_familia f " +
                "JOIN cat_grupo g ON f.id_grupo = g.id_grupo " +
                "WHERE f.id_grupo = ? ORDER BY f.nombre";
        return jdbcTemplate.query(sql, FAMILIA_MAPPER, idGrupo);
    }

    // --- SECCIÓN: MÉTODOS OPERATIVOS (MARCAS) ---

    /**
     * Recupera el catálogo maestro de marcas.
     * 
     * @return Listado de marcas comerciales.
     */
    public List<CatMarca> findAllMarcas() {
        return jdbcTemplate.query("SELECT * FROM cat_marca ORDER BY nombre", MARCA_MAPPER);
    }

    // --- SECCIÓN: MÉTODOS OPERATIVOS (SUCURSALES) ---

    /**
     * Recupera el catálogo de sucursales (Critical Path for Session Context).
     * 
     * @return Listado de sucursales operativas.
     */
    public List<Sucursal> findAllSucursales() {
        return jdbcTemplate.query("SELECT * FROM sucursal ORDER BY nombre", SUCURSAL_MAPPER);
    }
}
