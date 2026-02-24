---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** Lógica y Patrones de Código del Sistema  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 📜 HOJA TÉCNICA: LÓGICA Y PATRONES DE CÓDIGO (ERP WALOOK)


Este documento establece el estándar técnico y los patrones de diseño utilizados en el desarrollo del ERP WALOOK, asegurando consistencia entre los módulos de las Etapas 0, 1, 2 y futuras implementaciones.

---

## 🛡️ GRUPO 1: SEGURIDAD, IDENTIDAD Y AUDITORÍA

### 1.1 Autenticación y Sesión (Service Pattern)
*   **Mecanismo:** Tokens (JWT) almacenados en `sessionStorage` (`token_erp`).
*   **Persistencia de Identidad:** Uso de `localStorage` para `remember_email`.
*   **Guardias de Navegación:** Cada módulo ejecuta `AuthService.getCurrentUser()` e `init()`. Si falla, redirige a `login.html`.
*   **Seguridad de Cabecera:** Implementación de **Content Security Policy (CSP)** en el `head` de cada HTML para mitigar XSS e Inyecciones.

### 1.2 Control de Acceso basado en Roles (RBAC)
*   **Matriz de Permisos:** 4 banderas booleanas por módulo: `puede_ver`, `puede_crear`, `puede_editar`, `puede_eliminar`.
*   **Lógica de Resolución:**
    1. Busca permiso específico del usuario (`permiso_usuario`).
    2. Si no existe, hereda del rol (`permiso_rol`).
    3. Si no existe, deniega acceso (Principio de Menor Privilegio).
*   **UI Reactiva:** Los elementos se deshabilitan (`disabled=true`) o se ocultan (`display:none`) en el lado del cliente basadas en esta lógica.

### 1.3 Patrón de Auditoría (Base de Datos)
*   **Campos Obligatorios:** Todas las tablas principales deben incluir:
    *   `fecha_registro` (TIMESTAMP DEFAULT CURRENT_TIMESTAMP)
    *   `fecha_modificacion` (TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)
    *   `usuario_creacion` (VARCHAR 50)
    *   `usuario_modificacion` (VARCHAR 50)
    *   `estatus` (ENUM 'ACTIVO', 'INACTIVO') -> **Soft Delete obligatorio**.

---

## 🎨 GRUPO 2: ARQUITECTURA DE INTERFAZ Y COMPONENTES (UI/UX)

### 2.1 Header Bar e Identidad (Global Component)
*   **Ubicación:** `<header class="app-header-bar">`.
*   **Contenidos:**
    *   **Branding:** Logo + Título "OPTICA ERP" + Icono del Módulo Actual.
    *   **Status Bar (Monitor de Sesión):**
        *   `INGRESO`: Hora fija de entrada (ej: 08:30).
        *   `ACTUAL`: Reloj dinámico (HH:mm:ss).
        *   `CONEXIÓN`: Cronómetro ascendente de tiempo total de sesión.
*   **Acción Global:** Botón `SALIR ✕` (Trigger de `AuthService.logout()`).

### 2.2 Patrones de Contenedores
*   **Dashboard:** `menu-grid` con `aspect-ratio: 1/0.8` para botones tipo mosaico (Tiles).
*   **Módulos Operativos:** Layout de dos columnas con **Master-Detail**.
    *   `usuarios-list` / `tabla-maestra` (Ancho flexible).
    *   `usuario-detail` / `panel-formulario` (Ancho fijo ~450px).

### 2.3 Botones y Acciones (Color System)
| Tipo | Clase CSS | Uso | Color Sugerido |
|------|-----------|-----|----------------|
| **Principal** | `.btn-primary` | Guardar, Acceder, Enviar | Azul (#1F3A5F) |
| **Crear** | `.btn-success` | Nuevo, Agregar, Registrar | Verde (#10B981) |
| **Eliminar** | `.btn-danger` | Borrar (Baja Lógica), Anular | Rojo (#EF4444) |
| **Aviso** | `.btn-warning` | Ajustes, Mantenimiento | Naranja (#F59E0B) |
| **Auxiliar** | `.btn-secondary` | Cancelar, Volver, Limpiar | Gris (#6B7280) |
| **Acción Icono**| `.icon-action` | Ojos de password, Editar fila | Transparent/Shadow |

### 2.4 Tipografía e Iconografía
*   **Fuente:** `Inter` o `Roboto` (Sans-serif).
*   **Iconos:** `Material Symbols Outlined` (Google).
    *   `.icon-lg`: 40px (Dashboard).
    *   `.icon-sm`: 18-24px (Inline).

---

## 🔄 GRUPO 3: LÓGICA DE CÓDIGO Y COMUNICACIÓN (JS/JAVA)

### 3.1 Patrón Service (Frontend JavaScript)
*   **Estructura:** Objetos literales constantes (ej: `const ClientesService = { ... }`).
*   **Funciones Core:**
    *   `init()`: Carga sesión y permisos.
    *   `cargarDatos()`: Llamada asíncrona a API.
    *   `renderTabla()`: Construcción dinámica del DOM (XSS safe vía `textContent`).
    *   `verDetalle()`: Poblado de formulario derecho.

### 3.2 Patrón de Respuesta API (Standard Response)
Todas las respuestas del Backend deben seguir el esquema:
```json
{
  "success": true | false,
  "message": "Descripción amigable del resultado",
  "data": { ... } | [ ... ]
}
```
*   **HTTP Status Codes:**
    *   200 (OK): Lectura/Actualización exitosa.
    *   201 (Created): Inserción exitosa.
    *   400 (Bad Request): Error de validación.
    *   403 (Forbidden): Error de permisos RBAC.
    *   500 (Server Error): Excepción no controlada.

### 3.3 Validaciones y Errores
*   **Dual Layer:** Validación en el cliente (JS para UX) y en el servidor (Java para Integridad).
*   **Manejo de UI:** Los errores se muestran en un `div` con `id="errorMsg"` (clase `.alert-error`).
*   **XSS Protection:** **PROHIBIDO** el uso de `innerHTML` para datos provenientes de la API o del usuario; usar siempre `textContent` o `innerText`.

---

## ⚙️ GRUPO 4: DISEÑO DE BASE DE DATOS Y PERSISTENCIA

### 4.1 Convenciones de Tablas (Nomenclatura)
*   **Nombres:** Plural o singular consistente (ej: `usuario`, `cliente`).
*   **Claves Primarias:** `id_{nombre_tabla}` (ej: `id_usuario`).
*   **Relaciones:** Claves foráneas con prefijo `id_` para consistencia.

### 4.2 Lógica de Transacciones
*   **Uso:** Obligatorio en operaciones que afectan múltiples tablas (ej: Crear Usuario + Asignar Permiso o Registrar Movimiento + Actualizar Stock).
*   **Mecanismo:** `@Transactional` en el Service de Spring Boot.

---
**Documento de Referencia Técnica - ERP WALOOK v1.1**
**Actualización de Patrones de Diseño de Código.**

