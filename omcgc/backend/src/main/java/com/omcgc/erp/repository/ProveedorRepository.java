/*
============================================================
Nombre del archivo : ProveedorRepository.java
Ruta              : backend/src/main/java/com/omcgc/erp/repository/ProveedorRepository.java
Tipo              : Backend (Repository)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Capa de acceso a datos para la entidad Proveedor.
Implementa operaciones CRUD y búsquedas personalizadas utilizando JdbcTemplate.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M05-01 (Registrar proveedor):
   - Persistencia de nuevos registros mediante INSERT SQL.
2. HU-M05-02 (Consultar proveedor):
   - Métodos de búsqueda por ID, RFC y texto libre.
3. RF-08 (Gestión de proveedores):
   - Implementación técnica del acceso a datos relacional.
============================================================
*/

package com.omcgc.erp.repository;

import com.omcgc.erp.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProveedorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapper para convertir filas de BD a objetos Java
    private final RowMapper<Proveedor> rowMapper = (rs, rowNum) -> {
        Proveedor p = new Proveedor();
        p.setIdProveedor(rs.getString("id_proveedor"));
        p.setRfc(rs.getString("rfc"));
        p.setRazonSocial(rs.getString("razon_social"));
        p.setNombreComercial(rs.getString("nombre_comercial"));
        p.setContacto(rs.getString("contacto"));
        p.setTelefono(rs.getString("telefono"));
        p.setEmail(rs.getString("email"));
        p.setCondicionPago(rs.getString("condicion_pago"));
        p.setEstatus(rs.getString("estatus"));
        p.setTipoPersona(rs.getString("tipo_persona"));
        p.setCreadoEn(rs.getTimestamp("creado_en"));
        p.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return p;
    };

    /**
     * Resumen (summary):
     * Busca todos los proveedores registrados.
     * 
     * Valor de retorno (returns):
     * 
     * @return Lista de objetos Proveedor.
     */
    public List<Proveedor> findAll() {
        String sql = "SELECT * FROM proveedor ORDER BY razon_social ASC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Resumen (summary):
     * Busca un proveedor por su ID único.
     * 
     * Parámetros (param):
     * 
     * @param id Identificador UUID del proveedor.
     * 
     *           Valor de retorno (returns):
     * @return Objeto Proveedor si existe, null en caso contrario.
     */
    public Proveedor findById(String id) {
        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Resumen (summary):
     * Busca un proveedor por su RFC exacto.
     * 
     * Parámetros (param):
     * 
     * @param rfc RFC a buscar.
     * 
     *            Valor de retorno (returns):
     * @return Objeto Proveedor si existe, null en caso contrario.
     */
    public Proveedor findByRfc(String rfc) {
        String sql = "SELECT * FROM proveedor WHERE rfc = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, rfc);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Resumen (summary):
     * Busca proveedores por criterio de texto en Razón Social, RFC o Contacto.
     * 
     * Parámetros (param):
     * 
     * @param query Texto a buscar.
     * 
     *              Valor de retorno (returns):
     * @return Lista de proveedores coincidentes.
     */
    public List<Proveedor> search(String query) {
        String sql = """
                SELECT * FROM proveedor
                WHERE razon_social LIKE ? OR rfc LIKE ? OR contacto LIKE ? OR nombre_comercial LIKE ?
                ORDER BY razon_social ASC
                """;
        String param = "%" + query + "%";
        return jdbcTemplate.query(sql, rowMapper, param, param, param, param);
    }

    /**
     * Resumen (summary):
     * Busca proveedores filtrando por estatus.
     * 
     * Parámetros (param):
     * 
     * @param estatus 'ACTIVO' o 'INACTIVO'.
     * 
     *                Valor de retorno (returns):
     * @return Lista de proveedores.
     */
    public List<Proveedor> findByEstatus(String estatus) {
        String sql = "SELECT * FROM proveedor WHERE estatus = ? ORDER BY razon_social ASC";
        return jdbcTemplate.query(sql, rowMapper, estatus);
    }

    /**
     * Resumen (summary):
     * Inserta un nuevo proveedor en la base de datos.
     * 
     * Parámetros (param):
     * 
     * @param p Objeto Proveedor con datos a insertar.
     * 
     *          Valor de retorno (returns):
     * @return 1 si se insertó correctamente, 0 si falló.
     */
    public int save(Proveedor p) {
        // Asegurar ID
        if (p.getIdProveedor() == null || p.getIdProveedor().isEmpty()) {
            p.setIdProveedor(UUID.randomUUID().toString());
        }

        String sql = """
                INSERT INTO proveedor (
                    id_proveedor, tipo_persona, rfc, razon_social, nombre_comercial,
                    contacto, telefono, email, condicion_pago, estatus
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(sql,
                p.getIdProveedor(),
                p.getTipoPersona() == null ? "MORAL" : p.getTipoPersona(),
                p.getRfc(),
                p.getRazonSocial(),
                p.getNombreComercial(),
                p.getContacto(),
                p.getTelefono(),
                p.getEmail(),
                p.getCondicionPago(),
                p.getEstatus() == null ? "ACTIVO" : p.getEstatus());
    }

    /**
     * Resumen (summary):
     * Actualiza los datos de un proveedor existente.
     * 
     * Parámetros (param):
     * 
     * @param p Objeto Proveedor con datos actualizados.
     * 
     *          Valor de retorno (returns):
     * @return Número de filas afectadas.
     */
    public int update(Proveedor p) {
        String sql = """
                UPDATE proveedor SET
                    tipo_persona = ?,
                    rfc = ?,
                    razon_social = ?,
                    nombre_comercial = ?,
                    contacto = ?,
                    telefono = ?,
                    email = ?,
                    condicion_pago = ?,
                    estatus = ?
                WHERE id_proveedor = ?
                """;

        return jdbcTemplate.update(sql,
                p.getTipoPersona(),
                p.getRfc(),
                p.getRazonSocial(),
                p.getNombreComercial(),
                p.getContacto(),
                p.getTelefono(),
                p.getEmail(),
                p.getCondicionPago(),
                p.getEstatus(),
                p.getIdProveedor());
    }

    /**
     * Resumen (summary):
     * Realiza un borrado lógico (desactivación) del proveedor.
     * 
     * Parámetros (param):
     * 
     * @param id ID del proveedor.
     * 
     *           Valor de retorno (returns):
     * @return Número de filas afectadas.
     */
    public int deleteLogical(String id) {
        String sql = "UPDATE proveedor SET estatus = 'INACTIVO' WHERE id_proveedor = ?";
        return jdbcTemplate.update(sql, id);
    }
}
