/*
============================================================
Nombre del archivo : SmtpController.java
Ruta              : backend/src/main/java/com/omcgc/erp/controller/SmtpController.java
Tipo              : Backend (Controlador REST SMTP)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Exponer endpoints protegidos para la gestión de la configuración SMTP.
Solo accesible para administradores (frontend validated, but secure here too).
============================================================
*/
package com.omcgc.erp.controller;

import com.omcgc.erp.model.SmtpConfig;
import com.omcgc.erp.service.EmailService;
import com.omcgc.erp.service.SmtpConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/smtp")
@CrossOrigin(origins = "*")
public class SmtpController {

    @Autowired
    private SmtpConfigService smtpConfigService;

    @Autowired
    private EmailService emailService;

    /**
     * Obtener el estado del archivo de configuración (Para el botón semáforo)
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        String status = smtpConfigService.checkStatus();
        return ResponseEntity.ok(Map.of("status", status));
    }

    /**
     * Obtener la configuración actual desencriptada (Solo Admin)
     */
    @GetMapping
    public ResponseEntity<?> getConfig() {
        try {
            SmtpConfig config = smtpConfigService.loadConfig();
            return ResponseEntity.ok(config);
        } catch (Exception e) {
            // Si falla lectura, probablemente no existe o corrupto
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Configuración no encontrada o corrupta"));
        }
    }

    /**
     * Guardar configuración (Solo Admin)
     */
    @PostMapping
    public ResponseEntity<?> saveConfig(@RequestBody SmtpConfig config) {
        try {
            smtpConfigService.saveConfig(config);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Configuración SMTP guardada y encriptada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error guardando configuración: " + e.getMessage()));
        }
    }

    /**
     * Probar configuración (Solo Admin)
     * Recibe la config temporal, no lee la guardada necesariamente
     */
    @PostMapping("/test")
    public ResponseEntity<?> testConfig(@RequestBody SmtpConfig config) {
        try {
            emailService.sendTestEmail(config);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Correo de prueba enviado con éxito a " + config.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error en prueba SMTP: " + e.getMessage()));
        }
    }
}
