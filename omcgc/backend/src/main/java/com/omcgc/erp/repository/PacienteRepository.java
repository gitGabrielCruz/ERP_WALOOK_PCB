/*
============================================================
Nombre del archivo : PacienteRepository.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/repository/PacienteRepository.java
Tipo              : Backend (Repositorio de Datos Spring JDBC)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Capa de acceso a datos para la entidad Paciente/Cliente.
Maneja operaciones CRUD (Create, Read, Update) y búsquedas filtradas
mediante JDBC Template y mapeo manual de filas (RowMapper).

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M06-01 (Insert):
   - Persistencia de registros de pacientes y clientes.
2. HU-M06-02 (Select con LIKE):
   - Consultas dinámicas para la búsqueda de expedientes.
============================================================
*/
package com.omcgc.erp.repository;

import com.omcgc.erp.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PacienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Paciente> PACIENTE_MAPPER = (rs, rowNum) -> {
        Paciente p = new Paciente();
        p.setIdPaciente(rs.getString("id_paciente"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setTelefono(rs.getString("telefono"));
        p.setEmail(rs.getString("email"));
        p.setRfc(rs.getString("rfc"));
        p.setTipoPersona(rs.getString("tipo_persona"));
        p.setRegimenFiscal(rs.getString("regimen_fiscal"));
        p.setUsoCfdi(rs.getString("uso_cfdi"));
        p.setDomicilioFiscal(rs.getString("domicilio_fiscal"));
        p.setEstatus(rs.getString("estatus"));
        p.setFusionadoCon(rs.getString("fusionado_con"));
        p.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        p.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        return p;
    };

    public List<Paciente> findAll() {
        return jdbcTemplate.query("SELECT * FROM paciente WHERE estatus = 'ACTIVO' ORDER BY nombre LIMIT 100",
                PACIENTE_MAPPER);
    }

    public List<Paciente> findByFiltros(String busqueda, String rfc, String estatus) {
        StringBuilder sql = new StringBuilder("SELECT * FROM paciente WHERE 1=1 ");

        if (busqueda != null && !busqueda.isEmpty()) {
            sql.append("AND (nombre LIKE '%").append(busqueda).append("%' OR email LIKE '%").append(busqueda)
                    .append("%') ");
        }
        if (rfc != null && !rfc.isEmpty()) {
            sql.append("AND rfc LIKE '%").append(rfc).append("%' ");
        }
        if (estatus != null && !estatus.isEmpty()) {
            sql.append("AND estatus = '").append(estatus).append("' ");
        }

        sql.append("ORDER BY nombre LIMIT 50");
        return jdbcTemplate.query(sql.toString(), PACIENTE_MAPPER);
    }

    public Paciente findById(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM paciente WHERE id_paciente = ?", PACIENTE_MAPPER, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Paciente findByRfc(String rfc) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM paciente WHERE rfc = ?", PACIENTE_MAPPER, rfc);
        } catch (Exception e) {
            return null;
        }
    }

    public int save(Paciente p) {
        if (p.getIdPaciente() == null) {
            p.setIdPaciente(UUID.randomUUID().toString());
        }

        // --- SANITIZACIÓN DE DATOS OPCIONALES ---
        // Convertir vacíos a NULL para evitar violaciones de CHECK constraints (ej:
        // chk_rfc_valid)
        if (p.getRfc() != null && p.getRfc().trim().isEmpty())
            p.setRfc(null);
        if (p.getEmail() != null && p.getEmail().trim().isEmpty())
            p.setEmail(null);
        if (p.getTelefono() != null && p.getTelefono().trim().isEmpty())
            p.setTelefono(null);
        if (p.getRegimenFiscal() != null && p.getRegimenFiscal().trim().isEmpty())
            p.setRegimenFiscal(null);
        if (p.getUsoCfdi() != null && p.getUsoCfdi().trim().isEmpty())
            p.setUsoCfdi(null);

        String sql = """
                    INSERT INTO paciente (id_paciente, nombre, apellidos, telefono, email, rfc, tipo_persona, regimen_fiscal, uso_cfdi, domicilio_fiscal, estatus)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE
                    nombre=?, apellidos=?, telefono=?, email=?, rfc=?, tipo_persona=?, regimen_fiscal=?, uso_cfdi=?, domicilio_fiscal=?, estatus=?
                """;
        return jdbcTemplate.update(sql,
                p.getIdPaciente(), p.getNombre(), p.getApellidos(), p.getTelefono(), p.getEmail(), p.getRfc(),
                p.getTipoPersona(), p.getRegimenFiscal(), p.getUsoCfdi(), p.getDomicilioFiscal(), p.getEstatus(),
                p.getNombre(), p.getApellidos(), p.getTelefono(), p.getEmail(), p.getRfc(), p.getTipoPersona(),
                p.getRegimenFiscal(), p.getUsoCfdi(), p.getDomicilioFiscal(), p.getEstatus());
    }
}
