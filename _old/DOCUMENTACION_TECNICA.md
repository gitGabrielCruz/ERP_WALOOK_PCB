# Documentación Técnica del Sistema (Etapa 1: Base Funcional y Autenticación)

**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC  
**Empresa:** WALOOK MEXICO, S.A. de C.V.  
**Versión del Documento:** 1.5  
**Fecha:** 1 de Febrero de 2026

---

## 1. Arquitectura del Sistema
El sistema implementa un patrón de arquitectura **Cliente-Servidor Desacoplado**, alineado con los principios de diseño de aplicaciones modernas ("12-Factor App").

*   **Capa de Presentación (Frontend)**: Aplicación Web Estática basada en estándares HTML5, CSS3 y JavaScript (ES6+).
*   **Capa de Lógica de Negocio (Backend)**: API RESTful desarrollada en Java con el framework Spring Boot.
*   **Capa de Persistencia de Datos**: Sistema Gestor de Base de Datos Relacional MySQL.

### 1.1 Infraestructura Técnica y Componentes Transversales
A continuación se describen los mecanismos implementados para garantizar la calidad, seguridad y mantenibilidad del sistema:

1.  **Seguridad en Capa de Presentación (Content Security Policy)**:
    *   Implementación de directivas CSP en las cabeceras HTTP para mitigar vulnerabilidades de tipo XSS (Cross-Site Scripting) y restringir la carga de recursos a orígenes explícitamente autorizados.
2.  **Sistema de Trazabilidad y Auditoría (Logging)**:
    *   Configuración jerárquica de registros mediante `logback-spring.xml`. Se segregan los eventos de seguridad (`/logs/auth/`), operaciones de base de datos y errores sistémicos para facilitar el análisis forense y la depuración técnica.
3.  **Mecanismo de Verificación de Estado (Health Check)**:
    *   Servicio `DatabaseHealthService` diseñado para la validación proactiva de la conectividad con el repositorio de datos, previniendo la propagación de excepciones en tiempo de ejecución.
4.  **Autenticación en Entorno de Desarrollo**:
    *   Implementación de credenciales de superusuario (`root`) codificadas estáticamente a nivel de servicio. Este mecanismo facilita la validación funcional en etapas tempranas de desarrollo, independientemente de la persistencia de datos.

### 1.2 Estructura de Configuración
La gestión de infraestructura se ha centralizado siguiendo el patrón de **Configuración Programática**:
*   **`DatabaseConfig.java`**: Actúa como *Singleton* para la gestión de conexiones a Base de Datos. Elimina la dispersión de credenciales en archivos de texto, centralizando el control en una clase fuertemente tipada.
*   **`application.properties`**: Se limita exclusivamente a configuraciones del contenedor de servlets (puerto, encoding) y flags de depuración JPA.

---

## 2. Código Fuente (Capa de Presentación)

### 2.0 Página de Aterrizaje (`index.html`)
*Ubicación: `omcgc/frontend/index.html`*
```html
<!--
============================================================
Nombre del archivo : index.html
Ruta              : omcgc/frontend/index.html
Tipo              : Frontend (Landing Page / Enrutador)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Punto de entrada raíz del servidor web estático.
Implementa una redirección automática inmediata hacia el módulo de autenticación.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. UX/Navegación:
   - Redirección automática a 'pages/login.html' para forzar flujo de entrada.
============================================================
-->
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="0; url=pages/login.html">
    <title>Redireccionando... | WALOOK ERP</title>
</head>
<body>
    <p>Redireccionando al Sistema ERP... si no sucede automáticamente, <a href="pages/login.html">haz clic aquí</a>.</p>
    <script>window.location.href = "pages/login.html";</script>
</body>
</html>
```

### 2.1 Hoja de Estilos Canónica (`ui-base.css`)
*Ubicación: `omcgc/frontend/assets/css/ui-base.css`*
```css
/*
============================================================
Nombre del archivo : ui-base.css
Ruta              : omcgc/frontend/assets/css/ui-base.css
Tipo              : Frontend (Hoja de Estilos Global)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.3

Propósito:
Establecer el marco de referencia visual canónico para el sistema, centralizando
la definición de variables de diseño, tipografía y componentes atómicos reutilizables.
============================================================
*/
/* ... [Contenido CSS Omitido, ver archivo fuente] ... */
```

### 2.2 Pantalla de Inicio de Sesión (`login.html`)
*Ubicación: `omcgc/frontend/pages/login.html`*
```html
<!--
============================================================
Nombre del archivo : login.html
Ruta              : omcgc/frontend/pages/login.html
Tipo              : Frontend (HTML5)
============================================================
-->
<!DOCTYPE html>
<html lang="es">
<head>
    <!-- ... -->
</head>
<body class="login-body">
    <!-- ... -->
</body>
</html>
```

### 2.3 Servicio de Lógica de Cliente (`login-service.js`)
*Ubicación: `omcgc/frontend/assets/js/login-service.js`*
```javascript
/*
============================================================
Nombre del archivo : login-service.js
Ruta              : omcgc/frontend/assets/js/login-service.js
Tipo              : Frontend (Script JS)
============================================================
*/
const LoginService = {
    // ...
};
```

---

## 3. Código Fuente (Capa de Lógica - Backend)

### 3.0 Punto de Entrada (`MainApplication.java`)
*Ubicación: `omcgc/backend/src/main/java/com/omcgc/MainApplication.java`*
```java
@SpringBootApplication
@ComponentScan(basePackages = "com.omcgc.erp")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
```

### 3.1 Servicio de Autenticación (`AuthService.java`)
*Ubicación: `omcgc/backend/src/service/AuthService.java`*
```java
@Service
public class AuthService {
    // ...
}
```

### 3.2 Servicio de Diagnóstico de Base de Datos (`DatabaseHealthService.java`)
*Ubicación: `omcgc/backend/src/service/DatabaseHealthService.java`*
```java
@Service
public class DatabaseHealthService {
    // ...
}
```

---

## 4. Configuración y Base de Datos

### 4.1 Propiedades del Aplicativo (`application.properties`)
*Ubicación: `omcgc/backend/src/main/resources/application.properties`*
```properties
server.port=9090
spring.application.name=omcgc-erp-backend
# ...
```

### 4.2 Esquema de Base de Datos (`01_schema_usuarios.sql`)
*Ubicación: `omcgc/database/scripts/01_schema_usuarios.sql`*
```sql
CREATE TABLE IF NOT EXISTS usuario ( ... );
```

### 4.3 Definición de Proyecto Maven (`pom.xml`)
*Ubicación: `omcgc/backend/pom.xml`*
```xml
<project ...>
    <!-- Dependencias y Plugins -->
</project>
```

---

## 5. Módulo de Gestión de Usuarios y SMTP Seguro (NUEVO)

### 5.0 Visión General
Este módulo extiende las capacidades administrativas del sistema permitiendo la gestión integral del ciclo de vida de los usuarios (CRUD), control de acceso granular mediante roles y permisos, y la configuración segura del servicio de correo electrónico (SMTP).

### 5.1 Frontend: Interfaz de Gestión (`usuarios.html` y `usuarios-service.js`)

*   **Diseño Responsivo**: Implementación de `Media Queries` en `ui-base.css` para adaptar el layout de 2 columnas a dispositivos móviles (apilamiento vertical), así como un Modal Flotante para configuraciones extra.
*   **Matriz de Permisos Dinámica**: Lógica en JS que marca/desmarca automáticamente los permisos por módulo (Ver, Crear, Editar, Eliminar) basándose en el Rol seleccionado, siguiendo reglas de negocio predefinidas (Admin=Todo, Facturista=Ventas/Fact, etc).
*   **Modal de SMTP**: Ventana flotante segura para la configuración del servidor de correo, visible solo para administradores. Incluye lógica de bloqueo de campos según perfil (Gmail/Hotmail).

### 5.2 Backend: Seguridad SMTP (`SmtpConfigService.java`)
*Ubicación: `omcgc/backend/src/main/java/com/omcgc/erp/service/SmtpConfigService.java`*

Este servicio implementa un **mecanismo de seguridad de alto nivel** para proteger las credenciales del servidor de correo:

1.  **Encriptación AES-128**: El archivo de configuración (`smtp_config.dat`) se almacena en disco en formato binario encriptado, siendo ilegible fuera del sistema.
2.  **Verificación de Integridad**: Al iniciar el servicio o consultar el estado, el sistema intenta desencriptar el archivo. Si detecta manipulación externa (corrupción o edición manual no autorizada), el archivo se elimina automáticamente ("Fail Secure") y se reporta el error como `CONFIG_CORRUPT`.

### 5.3 Backend: Servicio de Correo Dinámico (`EmailService.java`)
*Ubicación: `omcgc/backend/src/main/java/com/omcgc/erp/service/EmailService.java`*

*   Permite la reconfiguración en tiempo de ejecución del `JavaMailSender` sin reiniciar el servidor Tomcat/Jetty.
*   Incluye funcionalidad de **Prueba de Conexión** (`sendTestEmail`) que permite validar credenciales con un envío real antes de persistir la configuración encriptada.

### 5.4 Controlador REST (`SmtpController.java`)
Expone endpoints protegidos para la gestión de la configuración:
*   `GET /api/smtp/status`: Devuelve el estado de integridad (OK/CORRUPT/MISSING) para el semáforo visual del botón UI.
*   `POST /api/smtp`: Guarda la configuración encriptada.
*   `POST /api/smtp/test`: Ejecuta el envío de prueba con la configuración enviada en el body.
