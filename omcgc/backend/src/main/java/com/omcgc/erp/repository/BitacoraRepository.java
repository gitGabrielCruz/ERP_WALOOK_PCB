/*
============================================================
Nombre del archivo : BitacoraRepository.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/repository/BitacoraRepository.java
Tipo              : Backend (Repository / Persistence)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Manejar la persistencia de los logs de auditoría en la tabla bitacora_seguridad.
Garantizar la integridad de los registros de eventos del sistema.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-01 (Auditoría y Trazabilidad):
   - Registro persistente de acciones de usuario y eventos técnicos.
============================================================
*/
package com.omcgc.erp.repository;

import com.omcgc.erp.model.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public class BitacoraRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Bitacora b) {
        String sql = "INSERT INTO bitacora_seguridad (id_bitacora, id_usuario, accion, ip_origen, detalles, fecha) VALUES (?, ?, ?, ?, ?, ?)";

        // Generar UUID si no existe
        if (b.getIdBitacora() == null) {
            b.setIdBitacora(UUID.randomUUID().toString());
        }

        jdbcTemplate.update(sql,
                b.getIdBitacora(),
                b.getIdUsuario(),
                b.getAccion(),
                b.getIpOrigen(),
                b.getDetalles(),
                b.getFecha() != null ? b.getFecha() : new java.sql.Timestamp(System.currentTimeMillis()));
    }

    public java.util.List<Bitacora> findAll(String fechaInicio, String fechaFin, String idUsuario, String buscar) {
        StringBuilder sql = new StringBuilder(
                "SELECT b.*, u.nombre as nombre_usuario FROM bitacora_seguridad b LEFT JOIN usuario u ON b.id_usuario = u.id_usuario WHERE 1=1 ");
        java.util.List<Object> params = new java.util.ArrayList<>();

        if (fechaInicio != null && !fechaInicio.isEmpty()) {
            sql.append(" AND DATE(b.fecha) >= ? ");
            params.add(fechaInicio);
        }
        if (fechaFin != null && !fechaFin.isEmpty()) {
            sql.append(" AND DATE(b.fecha) <= ? ");
            params.add(fechaFin);
        }
        if (idUsuario != null && !idUsuario.isEmpty()) {
            sql.append(" AND b.id_usuario = ? ");
            params.add(idUsuario);
        }

        sql.append(" ORDER BY b.fecha DESC LIMIT 1000"); // Aumentado para dar margen al filtrado en memoria
                                                         // post-descifrado

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            Bitacora b = new Bitacora();
            b.setIdBitacora(rs.getString("id_bitacora"));
            b.setIdUsuario(rs.getString("id_usuario"));
            b.setNombreUsuario(rs.getString("nombre_usuario")); // Mapear el JOIN
            b.setAccion(rs.getString("accion"));
            b.setIpOrigen(rs.getString("ip_origen"));
            b.setDetalles(rs.getString("detalles"));
            b.setFecha(rs.getTimestamp("fecha"));
            return b;
        }, params.toArray());
    }
}
