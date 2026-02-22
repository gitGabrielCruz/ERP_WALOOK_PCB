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
        private static final String FILE_PATH = "src/main/resources/security/audit_dictionary.dat";
        private Map<String, LogPattern> patterns = new HashMap<>();

        @PostConstruct
        public void init() {
                try {
                        ensureDirectoryExists();
                        if (!new File(FILE_PATH).exists()) {
                                generateInitialDictionary();
                        }
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
                list.add(new LogPattern("AUTH-01", "SUCCESS", "AUTH", "Acceso concedido al sistema.",
                                "Op: Login (Inicio de sesión) | Status: IdentityVerified | Session: NewJWT"));
                list.add(new LogPattern("AUTH-02", "FAILURE", "AUTH", "Credenciales inválidas.",
                                "Op: AuthCheck (Validación de identidad) | Error: PwdMismatch (Contraseña incorrecta)"));
                list.add(new LogPattern("AUTH-03", "DENIED", "AUTH", "Cuenta desactivada por administración.",
                                "Op: AccessRule (Regla de acceso) | State: UserInactive"));
                list.add(new LogPattern("AUTH-05", "INFO", "AUTH", "La sesión ha finalizado por seguridad.",
                                "Op: TokenExpiry (Vencimiento de Token) | Reason: IdleTimeout (Inactividad)"));

                // --- CRUD UNIVERSAL (DATA) ---
                list.add(new LogPattern("CRUD-01", "SUCCESS", "DATA", "Alta de registro exitosa.",
                                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: ALTA | REF: {S}"));
                list.add(new LogPattern("CRUD-02", "WARNING", "DATA", "Información actualizada satisfactoriamente.",
                                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: CAMBIO | DETALLE: {S}"));
                list.add(new LogPattern("CRUD-03", "CANCEL", "DATA", "Cambio de estatus aplicado.",
                                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: ESTADO | FLUJO: {S}"));
                list.add(new LogPattern("CRUD-04", "FAILURE", "DATA", "Datos incorrectos en el formulario.",
                                "MÓDULO: {M} | ACCIÓN: VALIDACIÓN | CAMPO: {X} | ERROR: BadFormat"));
                list.add(new LogPattern("CRUD-05", "FAILURE", "DATA", "El registro ya existe en el sistema.",
                                "MÓDULO: {M} | ACCIÓN: DUPLICADO | CLAVE: {X} | ERROR: DuplicateKey"));

                // --- USUARIOS Y ADMIN (USR/ADM) ---
                list.add(new LogPattern("USR-30", "SUCCESS", "USER", "Nueva clave enviada por correo.",
                                "Op: PwdReset (Clave restablecida) | Target: {X} | Justificación: {S}"));
                list.add(new LogPattern("ADM-01", "WARNING", "ADMIN", "Privilegios del rol actualizados.",
                                "Op: ACL_Update (Matriz de permisos) | Impact: GlobalAccess"));

                // --- INVENTARIOS (INV) ---
                list.add(new LogPattern("INV-01", "WARNING", "INV", "Ajuste manual de inventario realizado.",
                                "Op: StockAdjust (Ajuste de existencias) | Delta: {X} | Motivo: {S}"));

                // --- PRODUCTOS (PRO) ---
                list.add(new LogPattern("PRO-01", "SUCCESS", "PRODUCT", "Nuevo producto registrado en catálogo.",
                                "SKU: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
                list.add(new LogPattern("PRO-02", "WARNING", "PRODUCT", "Ficha técnica de producto modificada.",
                                "SKU: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

                // --- CLIENTES (CLI) ---
                list.add(new LogPattern("CLI-01", "SUCCESS", "CLIENT", "Nuevo cliente registrado.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
                list.add(new LogPattern("CLI-02", "WARNING", "CLIENT", "Información de cliente actualizada.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

                // --- PROVEEDORES (PRV) ---
                list.add(new LogPattern("PRV-01", "SUCCESS", "VENDOR", "Nuevo proveedor registrado.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
                list.add(new LogPattern("PRV-02", "WARNING", "VENDOR", "Información de proveedor actualizada.",
                                "RFC: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

                // --- FINANZAS Y CFDI (FIN) ---
                list.add(new LogPattern("FIN-01", "SUCCESS", "FIN", "Factura generada y timbrada correctamente.",
                                "Op: CFDI_Sign (Timbrado XML) | Engine: {P} | SAT_Status: 200_OK | UUID: {U}"));
                list.add(new LogPattern("FIN-02", "CANCEL", "FIN", "Factura cancelada ante el SAT.",
                                "Op: CFDI_Cancel (Anulación fiscal) | Code: {C} | Status: CancelledInSAT"));
                list.add(new LogPattern("FIN-03", "SUCCESS", "FIN", "Transacción financiera completada.",
                                "Op: PaymentProcess (Registro de ingreso) | Method: {M} | Amount: {$$}"));

                // --- SISTEMA Y SEGURIDAD (SYS/SEC) ---
                list.add(new LogPattern("SYS-01", "CRITICAL", "SYSTEM", "Error de conexión con el servidor.",
                                "Op: DBConn (Fallo de base de datos) | Fault: Timeout/Refused"));
                list.add(new LogPattern("SYS-02", "CRITICAL", "SYSTEM", "El servicio externo no responde.",
                                "Op: HttpCall (Petición externa) | Target: {SAT/PAC} | Fault: Timeout"));
                list.add(new LogPattern("SYS-99", "CRITICAL", "SYSTEM", "Error interno del sistema.",
                                "Op: GlobalException (Fallo no controlado) | Type: {E} | Class: {C}"));
                list.add(new LogPattern("SEC-01", "CRITICAL", "SECURITY", "Intento de acción no autorizada.",
                                "Op: SecurityInterception (Bloqueo de seguridad) | Threat: AccessViolation"));

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
                // Mapeo flexible de placeholders según la Matriz Maestra
                if (paramX != null) {
                        tec = tec.replace("{X}", paramX)
                                        .replace("{T}", paramX)
                                        .replace("{M}", paramX) // Soporte para Módulo
                                        .replace("{P}", paramX)
                                        .replace("{f}", paramX)
                                        .replace("{K}", paramX)
                                        .replace("{qty_A -> qty_B}", paramX);
                }
                if (paramS != null) {
                        tec = tec.replace("{S}", paramS)
                                        .replace("{id}", paramS)
                                        .replace("{D}", paramS)
                                        .replace("{E}", paramS) // Soporte para Entidad
                                        .replace("{U}", paramS)
                                        .replace("{R}", paramS);
                }

                return String.format("%s | [%s] | %s | %s | %s",
                                lp.getImpacto(), lp.getCategoria(), lp.getMensajeAmigable(), tec, lp.getId());
        }
}
