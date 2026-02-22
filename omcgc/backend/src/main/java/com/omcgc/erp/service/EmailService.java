/*
============================================================
Nombre del archivo : EmailService.java
Ruta              : backend/src/main/java/com/omcgc/erp/service/EmailService.java
Tipo              : Backend (Servicio de Correo)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Manejar el envío de correos electrónicos utilizando la configuración SMTP dinámica.
Permite reconfigurar el remitente en tiempo de ejecución sin reiniciar el servidor.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.SmtpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private SmtpConfigService smtpConfigService;

    /**
     * Construye un sender dinámico basado en la configuración guardada o
     * proporcionada.
     */
    private JavaMailSenderImpl createSender(SmtpConfig config) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getUsername());
        mailSender.setPassword(config.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(config.isAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(config.isStarttls()));

        // Timeout para evitar bloqueos largos
        props.put("mail.smtp.connectiontimeout", "5000"); // 5 segundos
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        return mailSender;
    }

    /**
     * Envía un correo utilizando la configuración almacenada.
     */
    public void sendEmail(String to, String subject, String body) throws Exception {
        SmtpConfig config = smtpConfigService.loadConfig();
        JavaMailSenderImpl sender = createSender(config);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        sender.send(message);
    }

    /**
     * Prueba una configuración SMTP específica (sin necesidad de guardarla
     * primero).
     */
    public void sendTestEmail(SmtpConfig testConfig) throws Exception {
        JavaMailSenderImpl sender = createSender(testConfig);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(testConfig.getUsername());
        message.setTo(testConfig.getUsername()); // Se envía a sí mismo para probar
        message.setSubject("Prueba de Configuración SMTP - Óptica ERP");
        message.setText(
                "Este es un correo de verificación enviado desde el sistema Óptica ERP para confirmar que la configuración SMTP es correcta.\n\nFecha: "
                        + new java.util.Date());

        sender.send(message);
    }
}
