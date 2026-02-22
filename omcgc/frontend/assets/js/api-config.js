/*
============================================================
Nombre del archivo : api-config.js
Ruta              : omcgc/frontend/assets/js/api-config.js
Tipo              : Configuración Global Frontend

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v2.0

Propósito:
Módulo centralizado de configuración del entorno de ejecución.
Gestiona de forma automática la detección del entorno (local o producción)
y aplica las directivas correspondientes de comunicación (URL del Backend)
y de seguridad (Content Security Policy). Al ser el primer script cargado
en cada página, garantiza que todas las políticas estén activas antes de
cualquier operación de red.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-02 (Seguridad de Acceso):
   - Generación dinámica de Content Security Policy (CSP) por entorno.
   - Restricción de orígenes permitidos en directiva 'connect-src'.
2. RNF-03 (Portabilidad):
   - Detección automática del entorno sin intervención manual.
   - Soporte para desarrollo local (XAMPP) y producción (VPS/cPanel).
3. RF-GLOBAL (Comunicación Frontend-Backend):
   - Centralización de la URL base del Backend.
   - Provisión de endpoints tipados por recurso.

Uso:
Se incluye este script ANTES de cualquier otro servicio en el HTML.
No requiere configuración adicional por parte del desarrollador.
============================================================
*/

const AppConfig = {

    /**
     * Determina si el entorno de ejecución corresponde a un servidor local.
     * La evaluación se realiza mediante inspección del hostname del navegador.
     * Un hostname vacío indica apertura directa del archivo (protocolo file://).
     */
    get isLocal() {
        const host = window.location.hostname;
        return host === 'localhost' ||
            host === '127.0.0.1' ||
            host === '';
    },

    /**
     * Provee la URL base del Backend según el entorno detectado.
     * - Local: servidor XAMPP/Spring Boot en puerto 9090.
     * - Producción: dominio API con certificado SSL (Nginx reverse proxy).
     */
    get BASE_URL() {
        return this.isLocal
            ? 'http://localhost:9090/api'
            : 'https://api-vps.graxsoft.com/api';
    },

    /**
     * Construye la URL completa de un endpoint REST a partir del recurso solicitado.
     * @param {string} resource - Nombre del recurso (ej: 'auth', 'usuarios', 'proveedores').
     * @returns {string} URL absoluta del endpoint.
     */
    getEndpoint(resource) {
        return `${this.BASE_URL}/${resource}`;
    },

    /**
     * Genera la directiva Content Security Policy (CSP) correspondiente al
     * entorno de ejecución. La directiva 'connect-src' se ajusta de forma
     * automática para permitir únicamente las conexiones al Backend activo.
     *
     * Directivas incluidas:
     * - default-src: restringe orígenes por defecto al propio dominio.
     * - script-src: permite scripts propios e inline (requerido por lógica embebida).
     * - style-src: permite estilos propios, inline y fuentes externas (Google Fonts, cdnjs).
     * - font-src: permite carga de fuentes desde Google Fonts y cdnjs (Font Awesome).
     * - img-src: permite imágenes propias y URIs de tipo data: (Base64).
     * - connect-src: restringe conexiones de red al Backend del entorno activo.
     */
    get CSP() {
        const connectSrc = this.isLocal
            ? "'self' http://127.0.0.1:9090 http://localhost:9090"
            : "'self' https://api-vps.graxsoft.com";

        return "default-src 'self'; " +
            "script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
            "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdnjs.cloudflare.com; " +
            "font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com; " +
            "img-src 'self' data:; " +
            "connect-src " + connectSrc + ";";
    }
};

/**
 * Inyección automática de Content Security Policy.
 * Se ejecuta de forma inmediata al cargar el script, insertando un elemento
 * <meta http-equiv="Content-Security-Policy"> al inicio del <head> del documento.
 * Al ser api-config.js el primer script referenciado en cada página, la directiva
 * CSP queda activa antes de que se ejecute cualquier operación fetch() o XMLHttpRequest.
 */
(function () {
    const cspMeta = document.createElement('meta');
    cspMeta.httpEquiv = 'Content-Security-Policy';
    cspMeta.content = AppConfig.CSP;
    document.head.prepend(cspMeta);
})();

/**
 * Compatibilidad global.
 * Expone la URL base como variable global para servicios que la consumen
 * directamente (usuarios-service.js, proveedores-service.js, etc.).
 */
const API_BASE_URL = AppConfig.BASE_URL;
