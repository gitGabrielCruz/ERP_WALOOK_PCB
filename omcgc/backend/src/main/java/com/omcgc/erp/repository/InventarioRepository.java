/*
============================================================
Nombre del archivo : InventarioRepository.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/repository/InventarioRepository.java
Tipo              : Backend (Repositorio de Datos / Persistencia)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Proveer la capa de persistencia para el maestro de productos y las 
transacciones de inventario (Kardex). Implementa operaciones de lectura 
avanzada con JOINS y persistencia mediante tb.producto y tb.movimiento_inventario.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Gestión de Productos): 
   - Persistencia avanzada y consulta de catálogo maestro.
2. HU-M01-05 (Control de Movimientos):
   - Recuperación de trazabilidad histórica por ítem SKU y registro de transacciones.
============================================================
*/

package com.omcgc.erp.repository;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Clase cl.InventarioRepository encargada de la materialización de objetos
 * de inventario desde el motor de base de datos relacional MySQL.
 */
@Repository
public class InventarioRepository {

    /**
     * Instancia de cl.JdbcTemplate inyectada para operaciones SQL vr.jdbcTemplate
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- SECCIÓN: MAPPERS TÉCNICOS ---

    /**
     * Mapeador vr.PRODUCTO_MAPPER para la des-serialización de registros de la
     * tabla tb.producto
     */
    private static final RowMapper<Producto> PRODUCTO_MAPPER = (rs, rowNum) -> {
        Producto p = new Producto();
        p.setIdProducto(rs.getString("id_producto"));
        p.setSku(rs.getString("sku"));
        p.setNombre(rs.getString("nombre"));
        p.setIdGrupo(rs.getString("id_grupo"));
        p.setIdFamilia(rs.getString("id_familia"));
        p.setIdMarca(rs.getString("id_marca"));
        p.setCodigoBarras(rs.getString("codigo_barras"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setStockMaximo(rs.getInt("stock_maximo"));
        p.setCostoUnitario(rs.getBigDecimal("costo_unitario"));
        p.setPorcentajeUtilidad(rs.getBigDecimal("porcentaje_utilidad"));
        p.setPrecioVenta(rs.getBigDecimal("precio_venta"));
        p.setClaveProdServ(rs.getString("clave_prod_serv"));
        p.setClaveUnidad(rs.getString("clave_unidad"));
        p.setObjetoImpuesto(rs.getString("objeto_impuesto"));
        p.setIvaAplicable(rs.getBigDecimal("iva_aplicable"));
        p.setIdProveedor(rs.getString("id_proveedor"));
        p.setEstatus(rs.getString("estatus"));
        p.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        if (rs.getTimestamp("fecha_modificacion") != null) {
            p.setFechaModificacion(rs.getTimestamp("fecha_modificacion").toLocalDateTime());
        }

        try {
            p.setNombreGrupo(rs.getString("nombre_grupo"));
        } catch (Exception e) {
        }
        try {
            p.setNombreFamilia(rs.getString("nombre_familia"));
        } catch (Exception e) {
        }
        try {
            p.setNombreMarca(rs.getString("nombre_marca"));
        } catch (Exception e) {
        }
        try {
            p.setNombreProveedor(rs.getString("nombre_proveedor"));
        } catch (Exception e) {
        }
        try {
            p.setExistenciaActual(rs.getInt("existencia_actual"));
        } catch (Exception e) {
        }

        return p;
    };

    /**
     * Mapeador vr.MOVIMIENTO_MAPPER para la des-serialización de registros de la
     * tabla tb.movimiento_inventario
     */
    private static final RowMapper<MovimientoInventario> MOVIMIENTO_MAPPER = (rs, rowNum) -> {
        MovimientoInventario m = new MovimientoInventario();
        m.setIdMovimiento(rs.getString("id_movimiento"));
        m.setFolio(rs.getString("folio"));
        m.setTipoMovimiento(rs.getString("tipo_movimiento"));
        m.setIdProducto(rs.getString("id_producto"));
        m.setIdSucursal(rs.getString("id_sucursal"));
        m.setCantidad(rs.getInt("cantidad"));
        m.setCostoHistorico(rs.getBigDecimal("costo_historico"));
        m.setExistenciaAnterior(rs.getInt("existencia_anterior"));
        m.setExistenciaActual(rs.getInt("existencia_actual"));
        m.setFechaVencimiento(
                rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null);
        m.setOrigenId(rs.getString("origen_id"));
        m.setIdUsuario(rs.getString("id_usuario"));
        m.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        m.setObservaciones(rs.getString("observaciones"));

        try {
            m.setNombreProducto(rs.getString("nombre_producto"));
        } catch (Exception e) {
        }
        try {
            m.setSkuProducto(rs.getString("sku_producto"));
        } catch (Exception e) {
        }
        try {
            m.setNombreUsuario(rs.getString("nombre_usuario"));
        } catch (Exception e) {
        }

        return m;
    };

    // --- SECCIÓN: MÉTODOS DE PERSISTENCIA (PRODUCTOS) ---

    public List<Producto> findAll() {
        String vr_sql = """
                SELECT p.*, g.nombre as nombre_grupo, f.nombre as nombre_familia,
                       m.nombre as nombre_marca, pr.nombre_comercial as nombre_proveedor,
                       COALESCE(SUM(e.cantidad), 0) as existencia_actual
                FROM producto p
                JOIN cat_grupo g ON p.id_grupo = g.id_grupo
                JOIN cat_familia f ON p.id_familia = f.id_familia
                JOIN cat_marca m ON p.id_marca = m.id_marca
                LEFT JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
                LEFT JOIN existencia e ON p.id_producto = e.id_producto
                GROUP BY p.id_producto
                ORDER BY p.nombre
                """;
        return jdbcTemplate.query(vr_sql, PRODUCTO_MAPPER);
    }

    public Producto findById(String id) {
        String vr_sql = """
                SELECT p.*, g.nombre as nombre_grupo, f.nombre as nombre_familia,
                       m.nombre as nombre_marca, pr.nombre_comercial as nombre_proveedor
                FROM producto p
                JOIN cat_grupo g ON p.id_grupo = g.id_grupo
                JOIN cat_familia f ON p.id_familia = f.id_familia
                JOIN cat_marca m ON p.id_marca = m.id_marca
                LEFT JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
                WHERE p.id_producto = ?
                """;
        List<Producto> results = jdbcTemplate.query(vr_sql, PRODUCTO_MAPPER, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void save(Producto p) {
        // [DEFENSA] Integridad de Identidad (PK)
        if (p.getIdProducto() == null || p.getIdProducto().isEmpty()) {
            p.setIdProducto(UUID.randomUUID().toString());
        }

        // [DEFENSA] Integridad de SKU (Unique Not Null)
        if (p.getSku() == null || p.getSku().isEmpty() || p.getSku().equalsIgnoreCase("Autogenerado")) {
            // Fallback de generación técnica si llega vacío desde la UI
            String ts = String.valueOf(System.currentTimeMillis());
            p.setSku("75" + ts.substring(ts.length() - 8));
        }

        String sql = "INSERT INTO producto (id_producto, sku, nombre, id_grupo, id_familia, id_marca, codigo_barras, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombre=?, id_grupo=?, id_familia=?, id_marca=?, codigo_barras=?, stock_minimo=?, stock_maximo=?, costo_unitario=?, porcentaje_utilidad=?, clave_prod_serv=?, clave_unidad=?, objeto_impuesto=?, iva_aplicable=?, id_proveedor=?, estatus=?, fecha_modificacion = NOW()";
        jdbcTemplate.update(sql, p.getIdProducto(), p.getSku(), p.getNombre(), p.getIdGrupo(), p.getIdFamilia(),
                p.getIdMarca(), p.getCodigoBarras(), p.getStockMinimo(), p.getStockMaximo(), p.getCostoUnitario(),
                p.getPorcentajeUtilidad(), p.getClaveProdServ(), p.getClaveUnidad(), p.getObjetoImpuesto(),
                p.getIvaAplicable(), p.getIdProveedor(), p.getEstatus(),
                p.getNombre(), p.getIdGrupo(), p.getIdFamilia(), p.getIdMarca(), p.getCodigoBarras(),
                p.getStockMinimo(), p.getStockMaximo(), p.getCostoUnitario(), p.getPorcentajeUtilidad(),
                p.getClaveProdServ(), p.getClaveUnidad(), p.getObjetoImpuesto(), p.getIvaAplicable(),
                p.getIdProveedor(), p.getEstatus());
    }

    public void deleteById(String id) {
        jdbcTemplate.update("DELETE FROM producto WHERE id_producto = ?", id);
    }

    // --- SECCIÓN: MÉTODOS DE TRAZABILIDAD (KARDEX) ---

    public List<MovimientoInventario> findKardexByProducto(String idProducto) {
        String vr_sql = """
                SELECT m.*, p.nombre as nombre_producto, p.sku as sku_producto, u.nombre as nombre_usuario
                FROM movimiento_inventario m
                JOIN producto p ON m.id_producto = p.id_producto
                JOIN usuario u ON m.id_usuario = u.id_usuario
                WHERE m.id_producto = ?
                ORDER BY m.fecha DESC
                """;
        return jdbcTemplate.query(vr_sql, MOVIMIENTO_MAPPER, idProducto);
    }

    /**
     * Persiste un nuevo movimiento de inventario fn.saveMovimiento.
     * 
     * @param m Instancia de cl.MovimientoInventario.
     */
    public void saveMovimiento(MovimientoInventario m) {
        if (m.getIdMovimiento() == null)
            m.setIdMovimiento(UUID.randomUUID().toString());

        String vr_sql = """
                INSERT INTO movimiento_inventario (id_movimiento, folio, tipo_movimiento, id_producto,
                                                  id_sucursal, cantidad, costo_historico, existencia_anterior,
                                                  existencia_actual, fecha_vencimiento, origen_id, id_usuario, observaciones)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    folio = VALUES(folio),
                    cantidad = VALUES(cantidad),
                    tipo_movimiento = VALUES(tipo_movimiento),
                    observaciones = VALUES(observaciones),
                    costo_historico = VALUES(costo_historico),
                    origen_id = VALUES(origen_id),
                    existencia_anterior = VALUES(existencia_anterior),
                    existencia_actual = VALUES(existencia_actual),
                    fecha = NOW()
                """;

        jdbcTemplate.update(vr_sql, m.getIdMovimiento(), m.getFolio(), m.getTipoMovimiento(),
                m.getIdProducto(), m.getIdSucursal(), m.getCantidad(), m.getCostoHistorico(),
                m.getExistenciaAnterior(), m.getExistenciaActual(), m.getFechaVencimiento(),
                m.getOrigenId(), m.getIdUsuario(), m.getObservaciones());
    }

    /**
     * Actualiza la tabla existencia con el nuevo stock operativo
     * fn.updateExistencia.
     * Si no existe el registro, lo crea (INSERT ON DUPLICATE KEY UPDATE).
     */
    public void updateExistencia(String idProducto, String idSucursal, int nuevoStock) {
        String vr_sql = """
                INSERT INTO existencia (id_existencia, id_producto, id_sucursal, cantidad)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE cantidad = ?
                """;
        jdbcTemplate.update(vr_sql, UUID.randomUUID().toString(), idProducto, idSucursal, nuevoStock, nuevoStock);
    }

    /**
     * Obtiene la existencia actual operativa de un producto en una sucursal
     * fn.getCurrentStock.
     * Basado en la vista v_stock_actual.
     */
    public Integer getCurrentStock(String idProducto, String idSucursal) {
        String vr_sql = "SELECT existencia_operativa FROM v_stock_actual WHERE id_producto = ? AND id_sucursal = ?";
        try {
            return jdbcTemplate.queryForObject(vr_sql, Integer.class, idProducto, idSucursal);
        } catch (Exception e) {
            return 0;
        }
    }

    public MovimientoInventario findMovimientoById(String id) {
        String sql = "SELECT m.*, p.nombre as nombre_producto FROM movimiento_inventario m JOIN producto p ON m.id_producto = p.id_producto WHERE m.id_movimiento = ?";
        List<MovimientoInventario> results = jdbcTemplate.query(sql, MOVIMIENTO_MAPPER, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void deleteMovimientoById(String id) {
        jdbcTemplate.update("DELETE FROM movimiento_inventario WHERE id_movimiento = ?", id);
    }
}
