/*
============================================================
Nombre del archivo : SmtpConfigService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/SmtpConfigService.java
Tipo              : Backend (Servicio de Seguridad y Configuración)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Gestionar el almacenamiento seguro (encriptado) de la configuración SMTP.
Implementa lógica de integridad para el manejo de credenciales de correo.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-04 (Seguridad de Credenciales):
   - Encriptación AES de la configuración SMTP en disco (.dat).
   - Validación de integridad y recuperación ante corrupción de datos.
============================================================
*/
package com.omcgc.erp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omcgc.erp.model.SmtpConfig;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class SmtpConfigService {

    private static final String CONFIG_FILE = "smtp_config.dat";
    private static final String SECRET_KEY = "WALOOK_ERP_SECURE_SMTP_KEY_2026"; // Clave interna
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Verifica el estado del archivo de configuración.
     * Retorna un código de estado para el frontend.
     */
    public String checkStatus() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            return "CONFIG_MISSING";
        }

        try {
            // Intentar leer y desencriptar para verificar integridad
            loadConfig();
            return "CONFIG_OK";
        } catch (Exception e) {
            // Si falla la lectura o desencriptación, asumimos corrupción
            System.err.println("Archivo SMTP corrupto detectado. Eliminando...");
            file.delete();
            return "CONFIG_CORRUPT";
        }
    }

    /**
     * Guarda la configuración encriptada en disco.
     */
    public void saveConfig(SmtpConfig config) throws Exception {
        String json = objectMapper.writeValueAsString(config);
        String encrypted = encrypt(json);
        Files.write(new File(CONFIG_FILE).toPath(), encrypted.getBytes());
    }

    /**
     * Carga y desencripta la configuración.
     */
    public SmtpConfig loadConfig() throws Exception {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            throw new IOException("Archivo de configuración no encontrado");
        }

        byte[] encryptedBytes = Files.readAllBytes(file.toPath());
        String decryptedJson = decrypt(new String(encryptedBytes));
        return objectMapper.readValue(decryptedJson, SmtpConfig.class);
    }

    // =========================================================
    // UTILIDADES DE ENCRIPTACIÓN (AES)
    // =========================================================

    private SecretKeySpec getKey() {
        try {
            byte[] key = SECRET_KEY.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // usar solo los primeros 128 bit
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Error generando clave AES", e);
        }
    }

    private String encrypt(String strToEncrypt) throws Exception {
        SecretKeySpec secretKey = getKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    private String decrypt(String strToDecrypt) throws Exception {
        SecretKeySpec secretKey = getKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
}
