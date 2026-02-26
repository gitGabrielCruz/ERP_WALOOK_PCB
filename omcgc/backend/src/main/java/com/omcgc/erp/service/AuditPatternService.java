/*
============================================================
Nombre del archivo : AuditPatternService.java
Ruta              : backend/src/main/java/com/omcgc/erp/service/AuditPatternService.java
Tipo              : Backend (Service / Security)

Propósito:
Gestionar el diccionario cifrado de patrones de auditoría (.dat).
Maneja el cifrado AES-256 de la Matriz Maestra en formato binario.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.LogPattern;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;

@Service
public class AuditPatternService {

        private static final String MASTER_KEY = "W4L00K_4UD1T_SYST3M_S3CUR1TY_2026"; // Diferente a la de logs por
                                                                                      // seguridad
        private static final String FILE_PATH = "audit_dictionary.dat";
        private Map<String, LogPattern> patterns = new HashMap<>();

        @PostConstruct
        public void init() {
                try {
                        // [SEGURO V5.2] Generar en raíz de ejecución para evitar bloqueos
                        generateInitialDictionary();
                        loadDictionary();
                } catch (Exception e) {
                        System.err.println(
                                        "[CRITICAL] Error al inicializar diccionario de auditoría: " + e.getMessage());
                }
        }

        private void ensureDirectoryExists() throws IOException {
                Files.createDirectories(Paths.get("src/main/resources/security"));
        }

        private SecretKeySpec generateKey() throws Exception {
                byte[] key = MASTER_KEY.getBytes("UTF-8");
                MessageDigest sha = MessageDigest.getInstance("SHA-256");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16); // Usar 128 bits para compatibilidad estándar
                return new SecretKeySpec(key, "AES");
        }

        /**
         * Genera el diccionario inicial cifrado en binario (.dat)
         */
        public void generateInitialDictionary() throws Exception {
                List<LogPattern> list = new ArrayList<>();

                // --- SEGURIDAD (AUTH) ---
                list.add(new LogPattern("AUTH-01", "CORRECTO", "AUTH", "Acceso concedido al sistema: {X}",
                                "SUCESO: Login (Inicio de sesión) | IDENTIDAD: {S} | ESTADO: Identidad Verificada | SESIÓN: Nueva_JWT"));
                list.add(new LogPattern("AUTH-02", "ERROR", "AUTH", "Credenciales inválidas.",
                                "SUCESO: Validación de identidad | ERROR: Contraseña incorrecta o usuario inexistente"));
                list.add(new LogPattern("AUTH-03", "ALERTA", "AUTH", "Cuenta desactivada por administración.",
                                "SUCESO: Regla de acceso | ESTADO: Usuario Inactivo"));
                list.add(new LogPattern("AUTH-05", "AVISO", "AUTH", "La sesión ha finalizado por seguridad.",
                                "SUCESO: Vencimiento de Token | MOTIVO: Inactividad prolongada"));

                // --- CRUD UNIVERSAL (DATA) ---
                list.add(new LogPattern("CRUD-01", "CORRECTO", "DATA", "Alta de registro exitosa.",
                                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: ALTA | REF: {S}"));
                list.add(new LogPattern("CRUD-02", "AVISO", "DATA", "Información actualizada satisfactoriamente.",
                                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: CAMBIO | DETALLE: {S}"));
                list.add(new LogPattern("CRUD-03", "AVISO", "DATA", "Cambio de estatus aplicado.",
                                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: ESTADO | FLUJO: {S}"));
                list.add(new LogPattern("CRUD-04", "ERROR", "DATA", "Datos incorrectos en el formulario.",
                                "MÓDULO: {M} | ACCIÓN: VALIDACIÓN | CAMPO: {X} | ERROR: Formato no válido"));
                list.add(new LogPattern("CRUD-05", "ERROR", "DATA", "El registro ya existe en el sistema.",
                                "MÓDULO: {M} | ACCIÓN: DUPLICADO | CLAVE: {X} | ERROR: Llave duplicada"));

                // --- USUARIOS Y ADMIN (USR/ADM) ---
                list.add(new LogPattern("USR-30", "CORRECTO", "USER", "Nueva clave enviada por correo.",
                                "SUCESO: Restablecimiento de clave | DESTINO: {X} | JUSTIFICACIÓN: {S}"));
                list.add(new LogPattern("ADM-01", "AVISO", "ADMIN", "Privilegios del rol actualizados.",
                                "SUCESO: Actualización de permisos | IMPACTO: Acceso Global"));

                // --- INVENTARIOS (INV) ---
                list.add(new LogPattern("INV-01", "CORRECTO", "INV", "Movimiento de inventario: {X}",
                                "DETALLE: {S}"));

                // --- PRODUCTOS (PRO) ---
                list.add(new LogPattern("PRO-01", "CORRECTO", "PRODUCT", "Nuevo producto registrado en catálogo.",
                                "SKU: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
                list.add(new LogPattern("PRO-02", "AVISO", "PRODUCT", "Ficha técnica de producto modificada.",
                                "SKU: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

                // --- CLIENTES (CLI) ---
                list.add(new LogPattern("CLI-01", "CORRECTO", "CLIENT", "Nuevo cliente registrado.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
                list.add(new LogPattern("CLI-02", "AVISO", "CLIENT", "Información de cliente actualizada.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

                // --- PROVEEDORES (PRV) ---
                list.add(new LogPattern("PRV-01", "CORRECTO", "VENDOR", "Nuevo proveedor registrado.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
                list.add(new LogPattern("PRV-02", "AVISO", "VENDOR", "Información de proveedor actualizada.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

                // --- FINANZAS Y CFDI (FIN) ---
                list.add(new LogPattern("FIN-01", "CORRECTO", "FIN", "Factura generada y timbrada correctamente.",
                                "SUCESO: Timbrado XML | PROVEEDOR: {P} | SAT: 200_OK | UUID: {U}"));
                list.add(new LogPattern("FIN-02", "AVISO", "FIN", "Factura cancelada ante el SAT.",
                                "SUCESO: Anulación fiscal | CÓDIGO: {C} | ESTADO: Cancelado en SAT"));
                list.add(new LogPattern("FIN-03", "CORRECTO", "FIN", "Transacción financiera completada.",
                                "SUCESO: Registro de ingreso | MÉTODO: {M} | MONTO: {$$}"));

                // --- SISTEMA Y SEGURIDAD (SYS/SEC) ---
                list.add(new LogPattern("SYS-01", "ALERTA", "SYSTEM", "Error de conexión con el servidor.",
                                "SUCESO: Fallo de BD | ERROR: Tiempo agotado o conexión rechazada"));
                list.add(new LogPattern("SYS-02", "ALERTA", "SYSTEM", "El servicio externo no responde.",
                                "SUCESO: Petición HTTP | DESTINO: {SAT/PAC} | ERROR: Tiempo agotado"));
                list.add(new LogPattern("SYS-99", "ALERTA", "SYSTEM", "Error interno del sistema.",
                                "SUCESO: Excepción Global | TIPO: {E} | CLASE: {C}"));
                list.add(new LogPattern("SEC-01", "ALERTA", "SECURITY", "Intento de acción no autorizada.",
                                "SUCESO: Bloqueo de seguridad | AMENAZA: Violación de acceso"));

                // Serializar a Bytes
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(list);
                byte[] bytes = bos.toByteArray();

                // Cifrar Bytes
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, generateKey());
                byte[] encryptedBytes = cipher.doFinal(bytes);

                // Guardar a archivo binario
                try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                        fos.write(encryptedBytes);
                }
                System.out.println("[AUDIT] Diccionario binario cifrado generado en: " + FILE_PATH);
        }

        /**
         * Carga el diccionario desde el archivo binario (.dat) y lo descifra en memoria
         */
        @SuppressWarnings("unchecked")
        private void loadDictionary() throws Exception {
                byte[] encryptedBytes = Files.readAllBytes(Paths.get(FILE_PATH));

                // Descifrar
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, generateKey());
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                // Deserializar
                ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
                ObjectInputStream ois = new ObjectInputStream(bis);
                List<LogPattern> list = (List<LogPattern>) ois.readObject();

                patterns.clear();
                for (LogPattern lp : list) {
                        patterns.put(lp.getId(), lp);
                }
        }

        public LogPattern getPattern(String id) {
                return patterns.get(id);
        }

        /**
         * Construye un log completo basado en un patrón y datos dinámicos
         */
        public String buildLog(String id, String paramX, String paramS) {
                LogPattern lp = getPattern(id);
                if (lp == null)
                        return "Unknown Event | ID: " + id;

                String tec = lp.getAnalisisTecnico();
                String amigable = lp.getMensajeAmigable();

                // Mapeo flexible de placeholders según la Matriz Maestra
                if (paramX != null) {
                        tec = tec.replace("{X}", paramX)
                                        .replace("{T}", paramX)
                                        .replace("{M}", paramX)
                                        .replace("{P}", paramX)
                                        .replace("{f}", paramX)
                                        .replace("{K}", paramX);
                        amigable = amigable.replace("{X}", paramX);
                }
                if (paramS != null) {
                        tec = tec.replace("{S}", paramS)
                                        .replace("{id}", paramS)
                                        .replace("{D}", paramS)
                                        .replace("{E}", paramS)
                                        .replace("{U}", paramS)
                                        .replace("{R}", paramS)
                                        .replace("{qty_A -> qty_B}", paramS);
                        amigable = amigable.replace("{S}", paramS);
                }

                return String.format("%s | [%s] | %s | %s | %s",
                                lp.getImpacto(), lp.getCategoria(), amigable, tec, lp.getId());
        }
}
