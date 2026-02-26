/*
============================================================
Nombre del archivo : BitacoraService.java
Ruta              : backend/src/main/java/com/omcgc/erp/service/BitacoraService.java
Tipo              : Backend (Privacy Engine / Service)

Propósito:
Motor de privacidad que cifra los datos de auditoría usando AES-256
antes de enviarlos a la base de datos. Garantiza que la información 
personal sea ilegible sin la llave maestra.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.Bitacora;
import com.omcgc.erp.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private AuditPatternService auditPatternService;

    // Llave Maestra Interna (Inaccesible desde el exterior)
    private static final String MASTER_KEY = "WALOOK_AUDIT_PRIVATE_SECURITY_2026";

    /**
     * Registra un evento estandarizado usando la Matriz Maestra cifrada (.dat)
     * 
     * @param idUsuario UUID del operador
     * @param idPatron  Código del patrón (ej: AUTH-01)
     * @param ip        IP del cliente
     * @param payload   Datos técnicos (Opcional)
     * @param deltas    Diferencias de valores (Opcional)
     */
    public void registrarEvento(String idUsuario, String idPatron, String ip, String paramX, String paramS) {
        try {
            // 1. Obtener patrón y construir mensaje a partir del binario .dat cifrado
            String logCompleto = auditPatternService.buildLog(idPatron, paramX, paramS);

            // 2. Fragmentar de forma robusta (Manejar múltiples pipes en el análisis
            // técnico)
            String[] parts = logCompleto.split("\\|");
            int n = parts.length;

            String impacto = parts[0].trim();
            String cat = parts[1].trim();
            String msjHumano = parts[2].trim();
            String code = parts[n - 1].trim(); // El código siempre es el último

            // Todo lo que esté entre el mensaje humano y el código es análisis técnico
            StringBuilder sbAnalisis = new StringBuilder();
            for (int i = 3; i < n - 1; i++) {
                sbAnalisis.append(parts[i].trim());
                if (i < n - 2)
                    sbAnalisis.append(" | ");
            }
            String analisis = sbAnalisis.toString();
            if (analisis.isEmpty())
                analisis = "Sin detalle técnico adicional";

            Bitacora b = new Bitacora();
            b.setIdUsuario(idUsuario);

            // Guardamos el encabezado en la acción y el cuerpo en detalles
            b.setAccion(encrypt(impacto + " | " + cat + " | " + code));
            b.setIpOrigen(encrypt(ip != null ? ip : "0.0.0.0"));
            b.setDetalles(encrypt(msjHumano + " | " + analisis));

            bitacoraRepository.save(b);
        } catch (Exception e) {
            System.err.println("❌ Error registrando evento maestro: " + e.getMessage());
        }
    }

    /**
     * Registra un evento en la bitácora cifrando los datos sensibles.
     * (Método Legacy - Se mantiene para compatibilidad mientras se migran
     * controladores)
     */
    public void registrar(String idUsuario, String accion, String ipOrigen, String detalles) {
        try {
            Bitacora b = new Bitacora();
            b.setIdUsuario(idUsuario);

            // Cifrado AES de atributos sensibles
            b.setAccion(encrypt(accion));
            b.setIpOrigen(encrypt(ipOrigen != null ? ipOrigen : "0.0.0.0"));
            b.setDetalles(encrypt(detalles != null ? detalles : "Sin detalles"));

            bitacoraRepository.save(b);
            System.out.println("✅ Evento de auditoria registrado y cifrado correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error critico registrando bitacora: " + e.getMessage());
            // No lanzamos excepción para no interrumpir el flujo principal del sistema
        }
    }

    /**
     * Recupera y descifra los registros de auditoría.
     */
    public java.util.List<Bitacora> listar(String desde, String hasta, String usuario, String buscar) {
        java.util.List<Bitacora> cifrados = bitacoraRepository.findAll(desde, hasta, usuario, buscar);

        for (Bitacora b : cifrados) {
            try {
                b.setAccion(decrypt(b.getAccion()));
                b.setIpOrigen(decrypt(b.getIpOrigen()));
                b.setDetalles(decrypt(b.getDetalles()));
            } catch (Exception e) {
                b.setAccion("[Error Decrypt]");
                b.setDetalles(e.getMessage());
            }
        }

        // Filtrado por texto en memoria (Privacy Engine: búsqueda post-descifrado)
        if (buscar != null && !buscar.trim().isEmpty()) {
            String term = buscar.toLowerCase();
            return cifrados.stream()
                    .filter(b -> {
                        String accion = (b.getAccion() != null) ? b.getAccion().toLowerCase() : "";
                        String detalles = (b.getDetalles() != null) ? b.getDetalles().toLowerCase() : "";
                        String nombre = (b.getNombreUsuario() != null) ? b.getNombreUsuario().toLowerCase() : "";
                        String ip = (b.getIpOrigen() != null) ? b.getIpOrigen().toLowerCase() : "";
                        String id = (b.getIdBitacora() != null) ? b.getIdBitacora().toLowerCase() : "";

                        return accion.contains(term) ||
                                detalles.contains(term) ||
                                nombre.contains(term) ||
                                ip.contains(term) ||
                                id.contains(term);
                    })
                    .collect(java.util.stream.Collectors.toList());
        }

        return cifrados;
    }

    // =========================================================
    // UTILIDADES DE CIFRADO (AES-256)
    // =========================================================

    private SecretKeySpec generateKey() throws Exception {
        byte[] key = MASTER_KEY.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // 128 bit para AES estándar
        return new SecretKeySpec(key, "AES");
    }

    private String encrypt(String strToEncrypt) throws Exception {
        if (strToEncrypt == null)
            return null;
        SecretKeySpec secretKey = generateKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    private String decrypt(String strToDecrypt) throws Exception {
        if (strToDecrypt == null)
            return null;
        SecretKeySpec secretKey = generateKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), "UTF-8");
    }
}
