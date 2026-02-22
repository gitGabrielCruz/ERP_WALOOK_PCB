# MEMORIA DE CONTINUIDAD - ERP WALOOK (PROYECTO OMCGC)
**Fecha Última Sesión:** 19 Febrero 2026
**Hash de Estado:** #CSP-FIX-LOGIN-v1.3

---

## OBJETIVO LOGRADO (HITOS CRÍTICOS)

### 1. **Reparación Lógica Inventarios (Kardex Fix)**
- **Estado:** COMPLETADO.
- **Detalle:** Se estandarizó `TipoMovimiento` (ENUM) y la gestión de `Sucursal` (FK). El backend ya no falla al guardar movimientos manuales.

### 2. **Restauración Base de Datos (Producción VPS)**
- **Estado:** COMPLETADO.
- **Detalle:** BD `graxsof3_omcgc` sincronizada 100% con scripts `creaciontablas_nube.sql` y `registrospruebas_nube.sql`. Usuario `graxsof3_omcgc` creado y operativo.

### 3. **Implementación HTTPS/SSL (Seguridad Frontend-Backend)**
- **Estado:** COMPLETADO.
- **Arquitectura Final:**
    - **Frontend:** `https://gabrielcruz.graxsoft.com` (cPanel) -> Apunta al dominio API.
    - **Backend (VPS):** `https://api-vps.graxsoft.com` (Nginx Proxy SSL 443 -> Java 9090).
    - **Certificado:** Let's Encrypt instalado y activo en VPS.
    - **ISP/DNS:** Registro `A` de `api-vps` apunta correctamente a `69.6.242.217`.

### 4. **Corrección CSP y Centralización de Entorno (19-Feb-2026)**
- **Estado:** COMPLETADO.
- **Problema:** El CSP hardcodeado en `login.html` solo permitía conexiones a localhost e IP directa, bloqueando las peticiones fetch() al dominio API con SSL.
- **Solución:** Se centralizó la configuración de entorno (URL Backend + CSP) en `api-config.js` v2.0 con detección automática del entorno (local/producción). Se eliminó el CSP estático de los HTML.
- **Archivos modificados:**
    - `frontend/assets/js/api-config.js` (v2.0) — Centraliza URL + CSP dinámico.
    - `frontend/pages/login.html` — CSP hardcodeado eliminado.

---

## CONFIGURACIÓN ACTUAL (NO TOCAR)

### **Backend (Java)**
- **Ruta Despliegue:** `/root/omcgc-erp-backend-0.0.1-SNAPSHOT.jar`
- **Servicio:** `omcgc-erp.service` (Systemd).
- **Puerto Interno:** `9090`.
- **Puerto Externo (Nginx):** `443` (SSL).
- **CORS (`SecurityConfiguration.java`):** Permite todos los orígenes (`*` pattern).

### **Frontend (JS)**
- **Archivo Clave:** `assets/js/api-config.js` (v2.0).
- **Detección de Entorno:** Automática por hostname del navegador.
- **URL Local:** `http://localhost:9090/api` (XAMPP).
- **URL Producción:** `https://api-vps.graxsoft.com/api`.
- **CSP:** Generado dinámicamente por `api-config.js`, NO está en el HTML.

### **Herramientas de Despliegue**
- **VPS_CONNECT.bat:** Conexión SSH automática al VPS (sin password). Usa `plink.exe` (auto-descarga). Ubicado en la raíz del proyecto.

---

## CREDENCIALES Y ACCESOS

*   **VPS SSH:** `ssh root@69.6.242.217 -p 22022` / `2-8[oEc=H8!NR*`
*   **VPS Dominio API:** `api-vps.graxsoft.com`
*   **Base de Datos (VPS):** `graxsof3_omcgc` / `Walook_2026!SecureDB`

---

## PENDIENTES PARA LA SIGUIENTE SESIÓN (NEXT STEPS)

1.  **Pruebas Funcionales:**
    - Verificar módulo de **Inventarios** (Crear Movimiento Manual, Kardex).
    - Probar CRUD completo de Usuarios, Proveedores y Clientes en producción.

2.  **Redirección HTTP -> HTTPS (Frontend):**
    - Subir `.htaccess` a la raíz de `public_html/` en cPanel para forzar HTTPS y redirigir `/` a `/frontend/pages/login.html`.
    - Archivo ya creado en: `omcgc/frontend/.htaccess`.

3.  **Mantenimiento:**
    - Verificar renovación automática del certificado Let's Encrypt en VPS.

---

**ESTA BITÁCORA DEBE SER LEÍDA AL INICIO DE LA PRÓXIMA SESIÓN.**
