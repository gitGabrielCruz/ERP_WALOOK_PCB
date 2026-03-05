/*
============================================================
Nombre del archivo : DataInitializer.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/config/DataInitializer.java
Tipo              : Backend (Configuración / Semilla)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Garantizar la existencia de los datos base (Semilla) al iniciar la aplicación.
Implementa el enfoque "Code-First Seeding" para asegurar Roles, Módulos y
la Matriz de Permisos inicial del sistema.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. Mapeo de Seguridad Inicial:
   - Definición de 12 Módulos Oficiales del sistema.
   - Configuración de Roles Estándar (ADMIN, VENDEDOR, etc.).
   - Asignación programática de permisos base por rol.
============================================================
*/
package com.omcgc.erp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

        private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

        @Autowired
        private JdbcTemplate jdbcTemplate;

        // ==========================================
        // DEFINICIÓN DE LA MATRIZ DE CONFIGURACIÓN
        // ==========================================

        // 1. Catálogo Oficial de Modulos (12)
        private static final Map<String, String> MODULOS_OFICIALES = new LinkedHashMap<>();
        static {
                MODULOS_OFICIALES.put("LOGIN", "Acceso al sistema");
                MODULOS_OFICIALES.put("INVENTARIO", "Gestión de productos y stock");
                MODULOS_OFICIALES.put("AGENDA CITAS", "Agenda y programación");
                MODULOS_OFICIALES.put("PROVEEDORES", "Gestión de proveedores");
                MODULOS_OFICIALES.put("EXPEDIENTE PACIENTE", "Historial clínico y optometría");
                MODULOS_OFICIALES.put("CLIENTES", "Cartera de clientes");
                MODULOS_OFICIALES.put("VENTAS", "Punto de venta");
                MODULOS_OFICIALES.put("ORDENES DE COMPRA", "Compras y pedidos");
                MODULOS_OFICIALES.put("TALLER OT", "Laboratorio y trabajos");
                MODULOS_OFICIALES.put("RECEPCION Y DEVOLUCION", "Logística externa");
                MODULOS_OFICIALES.put("FACTURACION CFDI", "Facturación electrónica");
                MODULOS_OFICIALES.put("USUARIOS, ROLES Y PERMISOS", "Administración de seguridad");
        }

        // 2. Roles Estándar
        private static final List<String> ROLES_ESTANDAR = Arrays.asList("ADMIN", "VENDEDOR", "OPTOMETRISTA", "CAJA",
                        "TALLER", "ALMACEN");

        @Override
        public void run(String... args) {
                try {
                        logger.info("🟢 INICIANDO PROCESO DE SEMBRADO DE DATOS (DataSeeding)...");

                        // PASO 1: Asegurar Roles
                        asegurarRoles();

                        // PASO 2: Asegurar Módulos
                        asegurarModulos();

                        // PASO 3: Aplicar Matriz de Permisos (Resetear permisos base)
                        aplicarMatrizPermisos();

                        // PASO 4: Asegurar Proveedores de Prueba (Para visualización de scroll)
                        asegurarProveedoresPrueba();

                        logger.info("✅ PROCESO DE SEMBRADO COMPLETADO.");
                } catch (Exception e) {
                        logger.error("❌ ERROR CRÍTICO EN DATA INITIALIZER: ", e);
                        e.printStackTrace(); // Force to stderr
                }
        }

        private void asegurarRoles() {
                String sqlCheck = "SELECT COUNT(*) FROM rol WHERE nombre = ?";
                String sqlInsert = "INSERT INTO rol (id_rol, nombre, descripcion, activo) VALUES (?, ?, ?, 1)";

                for (String rol : ROLES_ESTANDAR) {
                        Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class, rol);
                        if (count == null || count == 0) {
                                jdbcTemplate.update(sqlInsert, UUID.randomUUID().toString(), rol,
                                                "Rol del sistema " + rol);
                                logger.info("➕ Rol creado: {}", rol);
                        }
                }
        }

        private void asegurarModulos() {
                String sqlCheck = "SELECT COUNT(*) FROM modulo WHERE nombre = ?";
                String sqlInsert = "INSERT INTO modulo (id_modulo, nombre, descripcion, activo) VALUES (?, ?, ?, 1)";

                for (Map.Entry<String, String> entry : MODULOS_OFICIALES.entrySet()) {
                        String nombre = entry.getKey();
                        String desc = entry.getValue();

                        Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class, nombre);
                        if (count == null || count == 0) {
                                jdbcTemplate.update(sqlInsert, UUID.randomUUID().toString(), nombre, desc);
                                logger.info("➕ Módulo creado: {}", nombre);
                        }
                }
        }

        private void aplicarMatrizPermisos() {
                logger.info("🔄 Aplicando matriz de permisos estándar...");

                // 1. ADMIN: Todo a todo
                aplicarPermisosRol("ADMIN", true, true, true, true, MODULOS_OFICIALES.keySet());

                // 2. VENDEDOR: Ventas + Paquete Clínico + Inventario (Ver)
                Set<String> modsVendedor = new HashSet<>(Arrays.asList(
                                "VENTAS", "CLIENTES", "PACIENTES", "AGENDA CITAS", "EXPEDIENTE PACIENTE", "INVENTARIO",
                                "LOGIN"));
                aplicarPermisosRol("VENDEDOR", true, true, true, false, modsVendedor);

                // 3. OPTOMETRISTA: Paquete Clínico + Inventario (Ver)
                Set<String> modsOpto = new HashSet<>(Arrays.asList(
                                "PACIENTES", "AGENDA CITAS", "EXPEDIENTE PACIENTE", "INVENTARIO", "LOGIN"));
                aplicarPermisosRol("OPTOMETRISTA", true, true, true, false, modsOpto);

                // 4. CAJA: Solo cobrar y facturar
                Set<String> modsCaja = new HashSet<>(Arrays.asList(
                                "VENTAS", "CLIENTES", "FACTURACION CFDI", "LOGIN"));
                aplicarPermisosRol("CAJA", true, true, true, false, modsCaja);

                // 5. TALLER: Su área operativa
                Set<String> modsTaller = new HashSet<>(Arrays.asList(
                                "TALLER OT", "RECEPCION Y DEVOLUCION", "INVENTARIO", "LOGIN"));
                aplicarPermisosRol("TALLER", true, true, true, false, modsTaller);

                // 6. ALMACEN: Gestión de stock
                Set<String> modsAlmacen = new HashSet<>(Arrays.asList(
                                "INVENTARIO", "ORDENES DE COMPRA", "PROVEEDORES", "RECEPCION Y DEVOLUCION", "LOGIN"));
                aplicarPermisosRol("ALMACEN", true, true, true, true, modsAlmacen);
        }

        private void aplicarPermisosRol(String nombreRol, boolean ver, boolean crear, boolean editar, boolean eliminar,
                        Set<String> modulos) {
                // 1. Obtener ID Rol
                String sqlGetRol = "SELECT id_rol FROM rol WHERE nombre = ?";
                String idRol;
                try {
                        idRol = jdbcTemplate.queryForObject(sqlGetRol, String.class, nombreRol);
                } catch (Exception e) {
                        logger.error("Rol no encontrado para permisos: {}", nombreRol);
                        return;
                }

                // 2. Mapear IDs de Módulos
                String sqlGetMod = "SELECT id_modulo FROM modulo WHERE nombre = ?";

                // Limpiamos permisos anteriores de este rol para asegurar la matriz limpia?
                // Ojo: Esto borra personalizaciones manuales.
                // Por diseño "Plantilla Backend", asumimos que el ADMIN define aquí la verdad.
                // Pero para ser gentiles, usaremos INSERT IGNORE o ON DUPLICATE KEY UPDATE.

                String sqlUpsert = """
                                    INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
                                    VALUES (?, ?, ?, ?, ?, ?, ?)
                                    ON DUPLICATE KEY UPDATE
                                        puede_ver = VALUES(puede_ver),
                                        puede_crear = VALUES(puede_crear),
                                        puede_editar = VALUES(puede_editar),
                                        puede_eliminar = VALUES(puede_eliminar)
                                """;

                for (String modNombre : modulos) {
                        try {
                                String idModulo = jdbcTemplate.queryForObject(sqlGetMod, String.class, modNombre);

                                // Refinamiento de permisos (Lógica de Negocio)
                                boolean pVer = ver;
                                boolean pCrear = crear;
                                boolean pEditar = editar;
                                boolean pEliminar = eliminar;

                                // Lógica específica hardcodeada para la matriz
                                if (nombreRol.equals("CAJA")) {
                                        // Caja solo ve Inventario, no edita
                                        if (modNombre.equals("INVENTARIO")) {
                                                pCrear = false;
                                                pEditar = false;
                                                pEliminar = false;
                                        }
                                        // Caja en Ventas hace todo menos eliminar
                                        if (modNombre.equals("VENTAS")) {
                                                pVer = true;
                                                pCrear = true;
                                                pEditar = true;
                                                pEliminar = false;
                                        }
                                }

                                jdbcTemplate.update(sqlUpsert, UUID.randomUUID().toString(), idRol, idModulo, pVer,
                                                pCrear, pEditar,
                                                pEliminar);

                        } catch (Exception e) {
                                logger.warn("No se pudo asignar permiso {} a {}: {}", modNombre, nombreRol,
                                                e.getMessage());
                        }
                }
        }

        private void asegurarProveedoresPrueba() {
                String sqlCheck = "SELECT COUNT(*) FROM proveedor";
                Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class);

                if (count != null && count <= 1) { // Solo si está vacío o solo tiene el de prueba inicial
                        logger.info("🧪 Sembrando proveedores de prueba para visualización...");
                        String sqlInsert = """
                                            INSERT INTO proveedor (id_proveedor, rfc, razon_social, nombre_comercial, contacto, telefono, email, condicion_pago, estatus)
                                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'ACTIVO')
                                        """;

                        Object[][] data = {
                                        { UUID.randomUUID().toString(), "PROV00000001", "Distribuidora Optica S.A.",
                                                        "OptiDist",
                                                        "Juan Perez", "5551234567", "ventas@optidist.com", "15 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000002", "Lentes y Armazones de Mexico",
                                                        "LensMex",
                                                        "Maria Lopez", "5557654321", "contacto@lensmex.mx", "Contado" },
                                        { UUID.randomUUID().toString(), "PROV00000003", "Suministros Oftalmicos S.A.",
                                                        "SumOf",
                                                        "Carlos Ruiz", "5559998887", "sumof@sumof.com", "30 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000004", "Vision Global Distribucion",
                                                        "VisGlo",
                                                        "Ana Garcia", "5551112233", "ventas@visglo.com", "60 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000005", "Accesorios Opticos del Norte",
                                                        "AccOpt",
                                                        "Roberto Diaz", "8112223344", "norte@accopt.com", "15 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000006", "Cristales y Micas Especiales",
                                                        "CrisMica",
                                                        "Elena Vasquez", "5554445566", "produccion@crismica.com",
                                                        "Contado" },
                                        { UUID.randomUUID().toString(), "PROV00000007",
                                                        "Importadora de Armazones Premium", "ArmPremium",
                                                        "Sergio Ramos", "5556667788", "ventas@armpremium.mx",
                                                        "30 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000008", "Gafas de Sol S.A. de C.V.",
                                                        "SunGafas",
                                                        "Laura Torres", "5558889900", "hola@sungafas.com", "15 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000009", "Maquiladora Optica Peninsular",
                                                        "MaquiPen",
                                                        "Fernando Ruiz", "9991112233", "gerencia@maquipen.com",
                                                        "Contado" },
                                        { UUID.randomUUID().toString(), "PROV00000010", "Soluciones Visuales Integral",
                                                        "SolVisInt",
                                                        "Patricia Sosa", "5552223344", "atencion@solvis.mx",
                                                        "30 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000011",
                                                        "Lentes de Contacto de Occidente", "LensContact",
                                                        "Isabel Luna", "3334445566", "ventas@lenscontact.com",
                                                        "60 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000012", "Estuches y Microfibras Elite",
                                                        "EliteAcc",
                                                        "Mario Castillo", "5553334455", "info@eliteacc.com",
                                                        "15 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000013", "Equipos Optometricos S.A.",
                                                        "EquiOpt",
                                                        "Adriana Meza", "5557776655", "soporte@equiopt.mx", "Contado" },
                                        { UUID.randomUUID().toString(), "PROV00000014", "Refacciones para Armazones",
                                                        "RefacArm",
                                                        "Hugo Sanchez", "5552221100", "partes@refacarm.com",
                                                        "30 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000015",
                                                        "Laboratorio de Lentes Digitales", "DigLab",
                                                        "Beatriz Ortiz", "5559990011", "lab@diglab.com", "15 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000016", "Moda y Vision International",
                                                        "ModVisInt",
                                                        "Raul Jimenez", "5553332211", "ventas@modvis.com", "60 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000017",
                                                        "Optica Especializada en Mayoreo", "OptiMayor",
                                                        "Claudia Rojas", "5551113344", "mayor@optimayor.mx",
                                                        "30 dias" },
                                        { UUID.randomUUID().toString(), "PROV00000018", "Insumos Opticos de Queretaro",
                                                        "InsOptQro",
                                                        "Jorge Vera", "4425556677", "ventas@insoptqro.com", "Contado" },
                                        { UUID.randomUUID().toString(), "PROV00000019", "Tecnologia Visual Avanzada",
                                                        "TevisAv",
                                                        "Monica Silva", "5550009988", "info@tevisav.com", "30 dias" }
                        };

                        for (Object[] row : data) {
                                jdbcTemplate.update(sqlInsert, row);
                        }
                        logger.info("✅ 19 proveedores sembrados correctamente.");
                }
        }
}
