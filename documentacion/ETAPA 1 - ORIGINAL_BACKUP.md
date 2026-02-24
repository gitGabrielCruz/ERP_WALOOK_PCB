---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** Respaldo Original de Especificaciones - ETAPA 1  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 📊 ETAPA 1: Óptica ERP — Base Funcional del Sistema


## [cite_start]1.1 INCISO 1.1 — LOGIN (Login.svg) [cite: 1]
* [cite_start]**Pantalla:** Login / Acceso al sistema [cite: 2]
* [cite_start]**Etapa:** 1 [cite: 2]
* [cite_start]**RF que cubre:** RF-01 Autenticación [cite: 4][cite_start], RF-05 Comunicación API [cite: 5][cite_start], RF-06 Persistencia básica [cite: 6]
* [cite_start]**RNF asociados:** RNF-02 Seguridad básica [cite: 8][cite_start], RNF-03 Integración frontend–backend [cite: 9][cite_start], RNF-04 Consistencia visual [cite: 10]

### [cite_start]🔴 PASO 1 — EXTRACCIÓN LITERAL DEL Informe de Final_VF [cite: 11]
* [cite_start]**1.1 Requerimientos funcionales reales (Historias de Usuario):** [cite: 12]
    * [cite_start]HU-M01-01: Iniciar sesión en el sistema. [cite: 14]
    * [cite_start]HU-M01-02: Recuperar contraseña. [cite: 15]
    * [cite_start]HU-M01-03: Cerrar sesión. [cite: 16]
    * [cite_start]HU-M01-04: Acceder a la navegación inicial según rol. [cite: 17]
* [cite_start]**1.2 Requerimientos no funcionales reales:** [cite: 18]
    * [cite_start]Control de acceso al sistema. [cite: 20]
    * [cite_start]Integridad y confidencialidad de credenciales. [cite: 21]
    * [cite_start]Persistencia de sesión. [cite: 22]
    * [cite_start]Integración Frontend–Backend. [cite: 23]
    * [cite_start]Consistencia visual inicial. [cite: 24]
* [cite_start]**1.3 Casos de uso reales asociados:** [cite: 25]
    * [cite_start]CU-01: Iniciar sesión. [cite: 26]
    * [cite_start]CU-02: Recuperar contraseña. [cite: 27]
    * [cite_start]CU-03: Cerrar sesión. [cite: 28]
    * [cite_start]CU-04: Acceso a menú inicial según rol. [cite: 29]
* [cite_start]**1.4 Diagramas que aplican:** [cite: 30]
    * [cite_start]Diagrama de Navegabilidad — acceso y menú inicial. [cite: 31]
    * [cite_start]Diagrama Relacional — módulo Usuarios / Acceso. [cite: 32]
    * [cite_start]Diagrama de Clases — Usuario, Rol, Permiso. [cite: 33]
* [cite_start]**1.5 Datos literales del diseño:** [cite: 34]
    * [cite_start]Base de datos: optica_erp [cite: 35]
    * [cite_start]Tablas involucradas: usuario, usuario_rol, rol. [cite: 36, 37, 38, 39]
    * [cite_start]Campos relevantes (usuario): id_usuario, email, password_hash, activo. [cite: 40, 42, 43, 44, 45]

### [cite_start]🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO POR ETAPAS [cite: 50]
* [cite_start]**2.1 Pertenencia:** Login pertenece a Etapa 1: ✔ CONFIRMADO. [cite: 51, 52]
* [cite_start]**2.2 RF que se cierran en 1.1:** RF-01 Autenticación [cite: 54][cite_start], RF-05 Comunicación API [cite: 55][cite_start], RF-06 Persistencia básica. [cite: 56]
* [cite_start]**2.3 Qué NO se puede tocar:** Auditoría [cite: 58][cite_start], Bitácoras [cite: 59][cite_start], Seguridad avanzada [cite: 60][cite_start], Control normativo (Reservado a Etapa 5). [cite: 61]

### [cite_start]🔴 PASO 3 — DESCOMPOSICIÓN REAL DE Login.svg (UI → LÓGICA → DATOS) [cite: 62]
| Tipo | Elemento |
| :--- | :--- |
| Campo | [cite_start]Usuario / Email [cite: 65] |
| Campo | [cite_start]Contraseña [cite: 65] |
| Botón | [cite_start]Iniciar sesión [cite: 65] |
| Enlace | [cite_start]¿Olvidaste tu contraseña? [cite: 65] |
| Acción | [cite_start]Cerrar sesión [cite: 65] |

#### [cite_start]Trazabilidad técnica por elemento: [cite: 66]
* [cite_start]**Campo Usuario / Email:** Captura correo electrónico con validación de formato y existencia en tabla `usuario` (campo `email`). [cite: 67, 68, 69, 71, 72]
* [cite_start]**Campo Contraseña:** Captura contraseña con validación de comparación de hash en tabla `usuario` (campo `password_hash`). [cite: 74, 75, 76, 78, 79]
* [cite_start]**Botón Iniciar sesión:** Acción de validar credenciales en tablas `usuario`, `usuario_rol`, `rol`. [cite: 81, 82, 84] [cite_start]Resultado: crear sesión activa, asociar rol y redirigir al menú. [cite: 85, 86, 87, 88]

### [cite_start]🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 1.1 [cite: 101]
* [cite_start]**Reglas cerradas:** [cite: 111]
    * [cite_start]Solo usuarios con `usuario.activo = true` pueden iniciar sesión. [cite: 112]
    * [cite_start]El email es identificador único y la contraseña se valida por hash. [cite: 114, 115]
    * [cite_start]La recuperación de contraseña solo aplica a usuarios activos, reemplaza `password_hash` y no altera rol ni estatus. [cite: 116, 117, 118, 119]
* [cite_start]**Flujos if / then:** [cite: 121]
    * [cite_start]IF credenciales válidas AND usuario.activo = true THEN crear sesión, cargar rol y redirigir a menú. [cite: 122, 123, 124, 125]

---

## [cite_start]1.2 INCISO 1.2 — MENÚ (Menu.svg) [cite: 150]
* [cite_start]**RF que cubre:** RF-04 Navegación base. [cite: 152, 153]
* [cite_start]**Pantalla:** Menú principal posterior al login. [cite: 191]

### 🔴 PASO 3 — DESCOMPOSICIÓN DE Menu.svg
* **Elementos UI (12 Módulos Funcionales):**
    1. LOGIN
    2. INVENTARIO
    3. AGENDA CITAS
    4. PROVEEDORES
    5. EXPEDIENTE PACIENTE
    6. CLIENTES
    7. VENTAS
    8. ORDENES DE COMPRA
    9. TALLER OT
    10. RECEPCION Y DEVOLUCION
    11. FACTURACION CFDI
    12. USUARIOS, ROLES Y PERMISOS
* **Regla general de navegación:**
    * Cada opción intenta cargar un módulo cuyo nombre corresponde al nombre del botón.
    * IF módulo NO existe THEN mostrar mensaje "El módulo/archivo XXXX no existe" y retornar al Menú principal tras confirmación.

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 1.2
* **Reglas cerradas:**
    * El menú solo se renderiza con sesión activa y su contenido depende del rol asociado.
    * Las opciones sin permiso no se muestran (o aparecen deshabilitadas según diseño).

---

## 1.3 INCISO 1.3 — USUARIOS, ROLES Y PERMISOS (UsuariosRolesPermisos.svg)
* **RF que cubre:** RF-02 Gestión de usuarios, RF-03 Roles y permisos, RF-06 Persistencia básica.

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL
* **Listado de usuarios:** Columnas para Usuario (Username), Correo, Rol (código BD: ADMIN, CAJA, etc.) y Estatus.
* **Panel Detalle:** Campos para editar datos generales y asignar Rol.
* **Permisos:** Matriz de permisos granular basada en los 12 módulos.
    * Filas: Módulos del sistema.
    * Columnas: Acciones (Ver, Crear, Editar, Eliminar).
    * Persistencia: Tabla `permiso_modulo`.

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 1.3
* **Funcionalidades:** Alta, edición, asignación de rol y configuración fina de permisos.
* **Límites explícitos:** No incluye auditoría ni bitácoras complejas en esta etapa.

---

## ⚙️ INSTRUCCIONES PARA DESARROLLADOR IA — ETAPA 1

### 1. OBJETIVO DE LA ETAPA
Implementar la base funcional mínima operativa: Autenticación, Navegación inicial (menú) y Gestión básica de usuarios/roles/permisos.

### 2. REGLAS GENERALES (OBLIGATORIAS)
* **Roles Estándar:** Usar estrictamente roles de BD (`ADMIN`, `CAJA`, `OPTOMETRIA`, `TALLER`).
* **Módulos:** Soportar gestión de permisos para los 12 módulos oficiales.
* **Navegación:** Controlar acceso a rutas según permisos activos.

### 3. MODELO DE DATOS BASE
* **Base de datos:** optica_erp.
* **Tablas Principales:**
    * `usuario`: Identidad y acceso (id, nombre/username, email, password_hash, activo).
    * `rol`: Catálogo de roles (id, nombre).
    * `usuario_rol`: Asignación de rol a usuario.
    * `modulo`: Catálogo de los 12 módulos funcionales.
    * `permiso_modulo`: Matriz de permisos (id_rol, id_modulo, ver, crear, editar, eliminar).

### [cite_start]4. CRITERIOS DE CIERRE (CHECKLIST) [cite: 603]
- [ ] [cite_start]Login funcional y sesión obligatoria. [cite: 605, 606]
- [ ] [cite_start]Menú funcional con manejo de módulos inexistentes. [cite: 607, 608]
- [ ] [cite_start]CRUD básico de usuarios operativo. [cite: 609]
- [ ] [cite_start]Roles y permisos persistentes y control de acceso por rol funcional. [cite: 610, 611]

### 5. REGLAS DE DOCUMENTACIÓN Y CODIFICACIÓN (OBLIGATORIO)

**Encabezado Institucional:**
Todo archivo de código (backend, frontend, scripts, SQL) DEBE incluir el siguiente encabezado al inicio, respetando estrictamente los datos fijos del autor (Gabriel Amílcar Cruz Canto / ES1821003109 y grupo DS-DPT1-2502-B0-002) y detallando el propósito y trazabilidad del archivo.

```text
/*
============================================================
Nombre del archivo : <NOMBRE_ARCHIVO.ext>
Ruta              : <RUTA/DEL/ARCHIVO>
Tipo              : <SQL | Backend | Frontend | Script>

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : <vX.X>

Propósito:
<Descripción clara y concreta>

Dependencias:
- <Módulos usados>

Relación con el sistema:
- Pantallas: <Pantallas>
- Requerimientos: <HU-XX, RF-XX>
- Tablas principales: <tablas>

Notas:
<Advertencias técnicas>
============================================================
*/
```

### 6. ESPECIFICACIÓN TÉCNICA DE IMPLEMENTACIÓN (CIERRE DE ETAPA)

Este apartado documenta las decisiones de diseño definitivas, desviaciones del plan original y la arquitectura técnica final implementada para los componentes de la Etapa 1.

#### 6.1 AMPLIFICACIÓN DEL MODELO DE DATOS (Seguridad Híbrida)
Se modificó el diseño relacional original para soportar un esquema de seguridad más flexible, permitiendo excepciones a nivel de usuario.

*   **Tabla Modificada:** `permiso` (Anteriormente `permiso_rol`)
*   **Ajuste de Esquema:**
    *   `id_rol` (BIGINT, Nullable): FK hacia tabla `rol`.
    *   `id_usuario` (VARCHAR, Nullable): FK hacia tabla `usuario`. **(Nuevo Campo)**
    *   `id_modulo` (BIGINT): FK hacia tabla `modulo`.
    *   `puede_ver`, `puede_crear`, `puede_editar`, `puede_eliminar` (BOOLEAN).
*   **Regla de Integridad:** Un registro de permiso debe tener `id_rol` O `id_usuario`, pero idealmente no ambos nulos. La prioridad de lectura la define la lógica de negocio.

#### 6.2 ALGORITMO DE CONTROL DE ACCESO (LÓGICA DE NEGOCIO)
El sistema implementa un algoritmo de resolución de permisos en cascada ("Waterfall Permission Check") alojado en el Backend (`UsuarioController`) y replicado en Frontend (`UsuariosService`).

**Pseudocódigo de Resolución:**
1.  **Input:** `idUsuario`, `idModulo`.
2.  **Paso 1 (Excepción Personal):** Buscar en tabla `permiso` WHERE `id_usuario = X` AND `id_modulo = Y`.
3.  **Evaluación 1:**
    *   SI se encuentra registro -> RETORNAR permisos del registro (fin).
    *   SI NO se encuentra -> Paso 2.
4.  **Paso 2 (Herencia de Rol):** Obtener `idRol` del usuario. Buscar en tabla `permiso` WHERE `id_rol = Z` AND `id_modulo = Y`.
5.  **Evaluación 2:**
    *   SI se encuentra registro -> RETORNAR permisos del Rol.
    *   SI NO -> RETORNAR `false` (Deny All).

#### 6.3 ARQUITECTURA DE SEGURIDAD EN FRONTEND (UI SECURE LAYERS)
La seguridad no se limita al rechazo de peticiones API (Backend), sino que implementa una UX restrictiva basada en estados.

1.  **Capa 1: Navegación (MenuService)**
    *   **Evento:** Carga de `menu.html`.
    *   **Acción:** Itera sobre la lista de módulos. Para cada uno, verifica si `permiso.puede_ver === true`.
    *   **Efecto UI:**
        *   `TRUE`: Botón habilitado, clases CSS normales.
        *   `FALSE`: Se aplica clase `.disabled-module` (opacity: 0.5, grayscale: 100%) y se elimina el listener `onclick`.

2.  **Capa 2: Inicialización de Módulo (UsuariosService.init)**
    *   **Evento:** Carga de cualquier archivo `modulo.html`.
    *   **Validación Crítica:** Se llama a `verificarAccesoModulo()`. Si `permiso.puede_ver === false`, se ejecuta `window.location.href = 'menu.html'` inmediatamente.

3.  **Capa 3: Formulario Reactivo (Protección de Integridad)**
    *   **Contexto:** Visualización de detalle (`cargarDetalleUsuario`).
    *   **Estado:** Si `permisosActuales.editar === false`:
        *   Todos los `<input>` y `<select>` reciben el atributo `disabled`.
        *   El botón "Guardar" recibe `style.display = 'none'`.
        *   El botón "Reset Password" se deshabilita.
    *   **Objetivo:** Impedir la manipulación de datos en memoria antes de siquiera intentar el envío.

#### 6.4 ESPECIFICACIÓN DEL MÓDULO SMTP (Configuración)
Se añade un componente funcional completo para la gestión del servidor de correo, vital para el proceso de Recuperación de Contraseña (HU-M01-02).

*   **Ubicación:** `usuarios.html` -> Modal "Configuración SMTP" (Acceso exclusivo ROL: ADMIN).
*   **Campos Configurables:**
    *   `Host` (Servidor SMTP), `Puerto` (587/465), `Usuario`, `Password` (Cifrada en BD).
    *   `Flags`: `Auth` (Autenticación requerida), `TLS` (Cifrado de transporte).
*   **Funcionalidades Técnicas:**
    *   **Perfiles Rápidos:** Scripts en JS para pre-llenar configuraciones conocidas (Gmail: `smtp.gmail.com`, Outlook: `smtp.office365.com`).
    *   **Validación en Vivo:** Endpoint `/api/smtp/test` que intenta un handshake real con el servidor antes de guardar.
*   **Persistencia:** Tabla `configuracion_sistema` (Clave-Valor) o tabla dedicada `smtp_config` en Backend.

### 7. MAPA ESTRUCTURAL Y DE COMPONENTES

#### 7.1 Mapa de Carpetas (Entregable Etapa 1)
```text
ERP_WALOOK/
├── omcgc/
│   ├── backend/src/main/java/com/omcgc/erp/
│   │   ├── controller/      # Endpoints API (AuthController, UsuarioController)
│   │   ├── model/           # Entidades JPA (Usuario, Rol, Permiso)
│   │   ├── repository/      # Acceso a Datos (UsuarioRepository, PermisoRepository)
│   │   ├── service/         # Lógica de Negocio (UsuarioService, AuthService)
│   │   └── util/            # Utilidades (SecurityConfig)
│   ├── frontend/
│   │   ├── assets/
│   │   │   ├── css/         # ui-base.css (Estilos globales)
│   │   │   ├── js/          # Lógica Frontend
│   │   │       ├── api-config.js       # Constantes de Entorno
│   │   │       ├── menu-service.js     # Lógica Dashboard
│   │   │       ├── usuarios-service.js # Lógica CRUD Usuarios
│   │   │       └── login-service.js    # Lógica Autenticación
│   │   ├── pages/
│   │   │   ├── login.html    # UI Acceso
│   │   │   ├── menu.html     # UI Dashboard
│   │   │   └── usuarios.html # UI Gestión Usuarios (Master-Detail)
│   │   └── index.html        # Redirector
```

#### 7.2 Inventario de Archivos y Matriz de Trazabilidad (Caller/Callee)
| Archivo | Tipo | Función Principal | Caller (Quién lo llama) | Callee (A quién llama) |
| :--- | :--- | :--- | :--- | :--- |
| `login.html` | Vista (UI) | Interfaz de captura de credenciales. | Usuario (Navegador) | `login-service.js` |
| `login-service.js` | Controlador JS | Valida inputs, encripta y consume API. | `login.html` (Eventos) | API: `POST /auth/login` |
| `menu.html` | Vista (UI) | Tablero principal con botones de módulos. | `login-service.js` (Redirect) | `menu-service.js` |
| `menu-service.js` | Controlador JS | Valida sesión, aplica permisos visuales. | `menu.html` (onload) | API: `GET /usuarios/permisos` |
| `usuarios.html` | Vista (UI) | Interfaz Master-Detail para ABM Usuarios. | Usuario (Desde Menú) | `usuarios-service.js` |
| `usuarios-service.js` | Controlador JS | Orquesta init, CRUD, validaciones y UI. | `usuarios.html` (onload) | API: `GET/POST/PUT /usuarios` |
| `api-config.js` | Config | Provee `BASE_URL` centralizada. | Todos los `*-service.js` | N/A |
| `AuthController.java` | Controlador API | Recibe credenciales y emite sesión. | `login-service.js` | `AuthService.java` |
| `UsuarioController.java`| Controlador API | Endpoints REST para gestión de usuarios. | `usuarios-service.js` | `UsuarioService.java` |

---

### 8. MODELADO DE DATOS E INTERACCIÓN (ETAPA 1)

#### 8.1 Trazabilidad de Tablas e Interacción
Descripción del flujo de datos entre las tablas durante las operaciones de la Etapa 1.

| Operación | Tablas Involucradas | Tipo de Interacción | Descripción del Flujo |
| :--- | :--- | :--- | :--- |
| **Login** | `usuario`, `usuario_rol`, `rol` | RO (Read Only) | Verifica credenciales en `usuario`. Si OK, cruza `usuario_rol` para obtener el nombre del `rol` y devolverlo al frontend. |
| **Carga Menú** | `usuario`, `permiso`, `modulo` | RO (Read Only) | El sistema consulta `permiso` filtrando por el `id_rol` (o `id_usuario`) del usuario conectado para saber qué botones habilitar. |
| **Listar Usuarios** | `usuario`, `rol` | RO (Read Only) | backend hace un JOIN explicito o implicito para mostrar Nombre Usuario + Nombre Rol. |
| **Crear Usuario** | `usuario`, `usuario_rol` | RW (Read/Write) | Inserta en `usuario`. Recupera el ID generado. Inserta en `usuario_rol` la relación. |
| **Guardar Permisos** | `permiso`, `permiso_modulo` | RW (Read/Write) | Al guardar un usuario, se insertan/actualizan registros en `permiso` vinculados al `id_usuario` específico. |

---

### 9. INGENIERÍA DE FLUJOS (PSEUDOCÓDIGO DETALLADO)

#### 9.1 MÓDULO LOGIN (Inciso 1.1)

**A. Diagrama de Flujo de Módulos (Control Flow)**
```pseudocode
INICIO Login
    Usuario ingresa Credenciales (User, Pass)
    Frontend valida campos no vacíos
    Frontend envía POST /api/login
    
    BACKEND:
        Buscar Usuario por Email
        SI no existe -> Retornar error 404
        SI existe:
            Comparar Hash(PassInput) con PassDB
            SI coinciden:
                Obtener Rol y Datos Base
                Generar Token/Sesión JSON
                Retornar 200 OK + JSON
            SI NO:
                Retornar 401 Unauthorized

    FRONTEND:
        Recibir Respuesta
        SI Error -> Mostrar Toast "Credenciales inválidas"
        SI OK:
            Guardar JSON en sessionStorage('user')
            Redirigir a menu.html
FIN
```

**B. Diagrama de Flujo de Datos (Data Flow)**
`[Input Form] (String)` -> `JSON {email, pass}` -> `HTTP Request` -> `Controller Java` -> `JPA Entity` -> `DB Query` -> `ResultSet` -> `Map<String, Object>` -> `JSON Response` -> `sessionStorage`.

#### 9.2 MÓDULO MENÚ (Inciso 1.2)

**A. Diagrama de Flujo de Módulos (Control Flow)**
```pseudocode
INICIO Menu Load
    Leer sessionStorage('user')
    SI no hay usuario -> Redirigir index.html (Kick out)
    
    Renderizar Info Header (Nombre, Fecha)
    
    LLAMADA ASÍNCRONA checkPermissions():
        Backend GET /permisos-usuario/{id}
        Recibir Lista[Permisos]
        PARA CADA Modulo en UI:
            Buscar permiso correspondiente en Lista
            SI permiso.puede_ver == FALSE:
                Aplicar estilo .disabled (Gris)
                Remover onclick event
                Ocultar (opcional)
    
    ESPERAR Click Usuario
        SI click en Módulo Habilitado -> Navegar a modulo.html
        SI click en Módulo Deshabilitado -> NADA (Bloqueado)
FIN
```

#### 9.3 MÓDULO USUARIOS (Inciso 1.3)

**A. Diagrama de Flujo de Módulos (Control Flow)**
```pseudocode
INICIO Usuarios Load
    1. SEGURIDAD INICIAL
       Validar permiso 'VER' módulo Usuarios
       SI NO -> Alert "Acceso Denegado" -> Goto Menu
    
    2. RENDERIZADO MASTER
       Backend GET /usuarios
       Iterar Lista y pintar Tabla HTML
    
    3. INTERACCIÓN (Seleccionar Usuario)
       Frontend llama cargarDetalle(usuario)
       
       3.1 SEGURIDAD REACTIVA
           Leer permisosActuales.editar
           SI TRUE:
               Habilitar Inputs, Mostrar Btn Guardar
           SI FALSE:
               Deshabilitar Inputs, Ocultar Btn Guardar
               
       3.2 SEGURIDAD ESTATUS (Eliminar)
           Leer permisosActuales.eliminar
           SI FALSE -> Deshabilitar Select Estatus
    
    4. ACCIÓN GUARDAR (Escritura)
       Validar permisosActuales.crear (si es nuevo) o .editar (si existe)
       SI Falla -> ALERTA "No tiene permiso" -> BREAK
       
       Recopilar Datos Formulario
       Recopilar Matriz de Checkboxes (Permisos)
       
       Backend POST/PUT /usuarios
       Backend PUT /permisos-usuario
       
       Recargar Tabla
       Mostrar Toast "Guardado Exitoso"
FIN
```

### 10. ANEXO DE CÓDIGO FUENTE (ESTADO FINAL ETAPA 1)

A continuación se presenta el código fuente completo e íntegro de los componentes críticos implementados, garantizando su reproducibilidad y auditoría.

<details>
<summary><strong>📂 10.1 UI: Master-Detail Usuarios (usuarios.html)</strong></summary>

```html
<!--
============================================================
Nombre del archivo : usuarios.html
Ruta              : omcgc/frontend/pages/usuarios.html
Tipo              : Frontend (HTML5)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Proveer la interfaz gráfica para la Administración Integral de Usuarios (ABM),
Gestión de Roles y Configuración Granular de Permisos de Acceso.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M02-01 (Gestión de Usuarios):
   - Listado paginado y filtrable de usuarios del sistema.
   - Formulario de Alta y Edición de datos maestos (Nombre, Correo, Rol).
2. HU-M02-02 (Control de Estatus):
   - Mecanismo para activar/desactivar usuarios (Baja lógica).
3. HU-M02-03 (Matriz de Permisos):
   - Tabla interactiva para asignar permisos CRUD por módulo.
   - Herencia de permisos base desde Roles predefinidos.
4. RF-04 (Seguridad y Auditoría):
   - Restricción de elementos UI basada en 'permisosActuales' del operador.
   - Bloqueo de campos en modo "Solo Lectura".
============================================================
-->
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OPTICA ERP - Usuarios, Roles y Permisos</title>
    <link rel="stylesheet" href="../assets/css/ui-base.css">
    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
</head>

<body>
    <div class="app-wrapper">

        <!-- 
        [RF-05] HEADER BAR ESTÁNDAR 
        Componente global de navegación y estado de sesión.
        [JS BINDING] Datos poblados por MenuService / usuarios-service.js (userName, userRole).
        -->
        <header class="app-header-bar">
            <div class="header-left" style="cursor: pointer;" onclick="window.location.href='menu.html'">
                <img src="../assets/img/logo.png" alt="OPTICA ERP Logo" class="header-logo">
                <div class="header-brand-box">
                    <div class="header-brand-title">
                        OPTICA ERP
                        <span class="material-symbols-outlined header-module-icon">manage_accounts</span>
                    </div>
                    <span id="currentDate" class="header-date">--/--/----</span>
                </div>
            </div>

            <div class="app-status-bar">
                <div class="status-item">
                    <span style="font-weight: 600;" id="userName">Cargando...</span>
                    <span
                        style="font-size: 0.75rem; color: #6B7280; background: #F3F4F6; padding: 2px 6px; border-radius: 4px;"
                        id="userRole">...</span>
                </div>
                <div class="status-separator"></div>
                <div class="time-stats">
                    <div class="time-stat-box">
                        <div class="time-label">INGRESO</div>
                        <div class="time-value" id="loginTime">--:--</div>
                    </div>
                    <div class="time-stat-box">
                        <div class="time-label">ACTUAL</div>
                        <div class="time-value" id="currentClock">--:--</div>
                    </div>
                    <div class="time-stat-box">
                        <div class="time-label">CONEXIÓN</div>
                        <div class="time-value" style="color: var(--color-success);" id="sessionTimer">00:00:00</div>
                    </div>
                </div>
                <div class="status-separator"></div>
                <!-- Botón SMTP (Movido al Header) -->
                <button id="btnSmtpConfig" class="btn-primary btn-smtp"
                    style="display: none; margin-right: 15px; padding: 6px 12px; font-size: 0.8rem;"
                    onclick="UsuariosService.abrirSmtpModal()">
                    <span class="material-symbols-outlined"
                        style="font-size: 1.1rem; margin-right:5px;">mail_lock</span>
                    SMTP
                </button>
                <button
                    onclick="if(document.referrer.indexOf(window.location.host) !== -1) { history.back(); } else { window.location.href='menu.html'; }"
                    class="btn-logout">SALIR ✕</button>
            </div>
        </header>

        <!-- MAIN CONTENT -->
        <main class="app-main-container">
            <div style="width: 100%; max-width: 1400px;">

                <!-- ENCABEZADO Y BOTÓN SMTP -->
                <div class="usuarios-header" style="position: relative;">
                    <h1>Usuarios, Roles y Permisos</h1>
                    <p>Control de usuarios, roles y permisos de acceso a usuarios del sistema</p>
                </div>

                <!-- 
                [HU-M02-01] BARRA DE HERRAMIENTAS Y FILTRADO
                Permite la localización rápida de registros mediante búsqueda textual o filtros por Rol/Estatus.
                [JS BINDING] Eventos 'input' y 'change' vinculados a UsuariosService.filtrarUsuarios().
                -->
                <div class="filter-bar">
                    <div class="filter-group" style="flex: 1; min-width: 200px;">
                        <label>Buscar</label>
                        <input type="text" id="searchInput" placeholder="Nombre, correo..." class="input-base">
                    </div>
                    <div class="filter-group" style="min-width: 150px;">
                        <label>Rol</label>
                        <select id="filterRol" class="input-base">
                            <option value="">Todos</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="CAJA">CAJA</option>
                            <option value="OPTOMETRIA">OPTOMETRIA</option>
                            <option value="TALLER">TALLER</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label>Estatus</label>
                        <div class="status-toggle">
                            <button class="status-btn active" data-status="activo">Activos</button>
                            <button class="status-btn" data-status="inactivo">Inactivos</button>
                        </div>
                    </div>
                </div>

                <!-- LAYOUT 2 COLUMNAS (Responsive vía CSS) -->
                <div class="usuarios-layout">

                    <!-- COLUMNA IZQUIERDA: LISTADO 
                    [HU-M02-01] LISTADO MAESTRO DE USUARIOS
                    [JS BINDING] Renderizado por UsuariosService.renderTabla().
                    [COMPORTAMIENTO] Carga inicial automática (init) y reactiva a filtros.
                    -->
                    <div class="usuarios-list">
                        <h3 style="margin-top: 0; margin-bottom: 15px; font-size: 1rem;">Listado de usuarios</h3>
                        <div style="overflow-x: auto;">
                            <table class="table-base">
                                <thead>
                                    <tr>
                                        <th>Usuario</th>
                                        <th>Correo</th>
                                        <th>Rol</th>
                                        <th>Estatus</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="usuariosTableBody">
                                    <tr>
                                        <td colspan="5" style="text-align: center; padding: 30px; color: #9CA3AF;">
                                            Cargando usuarios...
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- COLUMNA DERECHA: DETALLE 
                    [HU-M02-01] FORMULARIO DE EDICIÓN
                    [RF-04] PROTECCIÓN DE INTERFAZ (Bloqueo de campos)
                    [JS BINDING] Controlado por cargarDetalleUsuario(). Los inputs se deshabilitan si 'permisosActuales.editar' es false.
                    -->
                    <div class="usuario-detail">
                        <h3 style="margin-top: 0; margin-bottom: 15px; font-size: 1rem;">Detalle del usuario</h3>
                        <input type="hidden" id="detailIdUsuario"> <!-- Hidden ID -->

                        <div class="detail-field">
                            <label>Usuario</label>
                            <!-- 
                            [CAMPO] ID USUARIO
                            [REGLA] ReadOnly. Generado por Backend (UUID/Auto-inc).
                            -->
                            <input type="text" id="detailUsuario" class="input-base" readonly
                                placeholder="Se genera automático">
                        </div>

                        <div class="detail-field">
                            <label>Correo</label>
                            <input type="email" id="detailCorreo" class="input-base" placeholder="email@ejemplo.com">
                        </div>

                        <div class="detail-field">
                            <label>Nombre completo</label>
                            <input type="text" id="detailNombre" class="input-base" placeholder="Nombre Apellido">
                        </div>

                        <div class="detail-field">
                            <label>Rol</label>
                            <select id="detailRol" class="input-base">
                                <option value="">Seleccionar...</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="CAJA">CAJA</option>
                                <option value="OPTOMETRIA">OPTOMETRIA</option>
                                <option value="TALLER">TALLER</option>
                            </select>
                        </div>

                        <div class="detail-field">
                            <!-- [HU-M02-02] CONTROL DE ESTATUS (BAJA LÓGICA) -->
                            <label>Estatus</label>
                            <select id="detailEstatus" class="input-base">
                                <option value="activo">Activo</option>
                                <option value="inactivo">Inactivo</option>
                            </select>
                        </div>

                        <div class="detail-field">
                            <label>Seguridad</label>
                            <button id="btnResetPassword" class="btn-secondary" style="width: 100%;"
                                onclick="UsuariosService.resetPassword()" disabled>
                                Forzar cambio de contraseña
                            </button>
                        </div>
                    </div>
                </div>

                <!-- MATRIZ DE PERMISOS 
                [HU-M02-03] ASIGNACIÓN GRANULAR DE PRIVILEGIOS
                [JS BINDING] Renderizada por cargarPermisosRol/Usuario.
                [REGLA] Checkboxes disabled si no se tiene permiso de EDITAR.
                -->
                <div class="permisos-matrix">
                    <h3 style="margin-top: 0; margin-bottom: 15px; font-size: 1rem;">Permisos por módulo</h3>
                    <div style="overflow-x: auto;">
                        <table class="permisos-table">
                            <thead>
                                <tr>
                                    <th style="text-align: left;">Módulo</th>
                                    <th class="text-center">Ver</th>
                                    <th class="text-center">Crear</th>
                                    <th class="text-center">Editar</th>
                                    <th class="text-center">Eliminar</th>
                                </tr>
                            </thead>
                            <tbody id="permisosTableBody">
                                <tr>
                                    <td colspan="5" class="text-center" style="padding: 20px; color: #9CA3AF;">
                                        Seleccione un Rol para ver/editar permisos
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                </div>

                <!-- BOTONES DE ACCIÓN 
                [RF-04] VALIDACIÓN DE SEGURIDAD EN ACCIONES
                [JS BINDING] Visibilidad controlada por aplicarSeguridadGlobal().
                [REGLA] Guardar valida permisos en Backend antes de enviar.
                -->
                <div class="action-buttons-container">
                    <div class="action-buttons-left">
                        <!-- Botón Bitácora (Solo Admin) -->
                        <button id="btnBitacora" class="btn-secondary btn-bitacora"
                            onclick="UsuariosService.verBitacora()" style="display: none;"
                            title="Solo Administradores - Auditoría de accesos">
                            <span class="material-symbols-outlined"
                                style="font-size: 1.2rem; margin-right: 5px;">admin_panel_settings</span>
                            Ver bitácora <span style="font-size: 0.7em; opacity: 0.7;">(Admin)</span>
                        </button>
                    </div>
                    <div class="action-buttons-right">
                        <button id="btnGuardar" class="btn-primary" onclick="UsuariosService.guardar()">
                            <span class="material-symbols-outlined"
                                style="font-size: 1.2rem; margin-right: 5px;">save</span>
                            Guardar
                        </button>
                        <button class="btn-secondary" onclick="UsuariosService.limpiarFormulario()">Cancelar</button>
                        <button class="btn-primary" onclick="UsuariosService.nuevo()">
                            <span class="material-symbols-outlined"
                                style="font-size: 1.2rem; margin-right: 5px;">add</span>
                            Nuevo
                        </button>
                    </div>
                </div>

            </div>
        </main>
    </div>

    <!-- MODAL DE CONFIGURACIÓN SMTP -->
    <div id="smtpModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Configuración SMTP Segura</h2>
                <button class="close-modal" onclick="UsuariosService.cerrarSmtpModal()">×</button>
            </div>

            <form id="smtpForm" onsubmit="event.preventDefault();">
                <div class="form-group">
                    <label class="text-label">Perfil de Proveedor</label>
                    <select id="smtpPerfil" class="input-base" onchange="UsuariosService.cambiarPerfilSmtp()">
                        <option value="custom">Dominio Propio / Personalizado</option>
                        <option value="gmail">Gmail</option>
                        <option value="hotmail">Outlook / Hotmail</option>
                    </select>
                </div>

                <div class="form-group" style="display: grid; grid-template-columns: 2fr 1fr; gap: 10px;">
                    <div>
                        <label class="text-label">Servidor (Host)</label>
                        <input type="text" id="smtpHost" class="input-base" placeholder="smtp.ejemplo.com">
                    </div>
                    <div>
                        <label class="text-label">Puerto</label>
                        <input type="number" id="smtpPort" class="input-base" placeholder="587">
                    </div>
                </div>

                <div class="form-group">
                    <label class="text-label">Correo Saliente (User)</label>
                    <input type="email" id="smtpUser" class="input-base" placeholder="notificaciones@dominio.com">
                </div>

                <div class="form-group">
                    <label class="text-label">Contraseña / App Password</label>
                    <input type="password" id="smtpPass" class="input-base">
                </div>

                <div class="form-group" style="display: flex; gap: 20px;">
                    <label class="checkbox-inline">
                        <input type="checkbox" id="smtpAuth" checked> Requiere Autenticación
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="smtpTls" checked> STARTTLS
                    </label>
                </div>

                <div style="display: flex; justify-content: space-between; margin-top: 20px;">
                    <button type="button" class="btn-secondary" onclick="UsuariosService.probarSmtp()">
                        <span class="material-symbols-outlined" style="font-size:1.1rem; margin-right:5px;">send</span>
                        Probar
                    </button>
                    <button type="button" class="btn-primary" onclick="UsuariosService.guardarSmtp()">
                        <span class="material-symbols-outlined" style="font-size:1.1rem; margin-right:5px;">lock</span>
                        Guardar Seguro
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Alerta Flotante (Toast) -->
    <div id="toastContainer" style="position: fixed; top: 20px; right: 20px; z-index: 2000;"></div>

    <script src="../assets/js/api-config.js"></script>
    <script src="../assets/js/menu-service.js"></script>
    <script src="../assets/js/usuarios-service.js"></script>
</body>

</html>
```
</details>

<details>
<summary><strong>📂 10.2 Control y Lógica: UsuariosService.js</strong></summary>

```javascript
/*
============================================================
Nombre del archivo : usuarios-service.js
Ruta              : frontend/assets/js/usuarios-service.js
Tipo              : Frontend (Lógica de Negocio)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.1

Propósito:
Gestionar la interacción de la pantalla de Usuarios.
Incluye CRUD de usuarios, control de matriz de permisos dinámica,
y gestión segura de configuración SMTP.
============================================================
*/

const UsuariosService = {
    // API Endpoints
    apiUrl: AppConfig.getEndpoint('usuarios'),
    smtpUrl: AppConfig.getEndpoint('smtp'),

    // Estado local
    currentUser: null,
    editingUserId: null,
    listaUsuarios: [],
    listaRoles: [],
    listaModulos: [],

    // Permisos del usuario logueado sobre ESTE módulo (Usuarios)
    permisosActuales: {
        ver: false,
        crear: false,
        editar: false,
        eliminar: false
    },

    init: async function () {
        this.currentUser = MenuService.getUser();
        if (!this.currentUser) {
            window.location.href = 'login.html';
            return;
        }

        // --- VALIDACIÓN DE SEGURIDAD (Permiso de Acceso) ---
        const acceso = await this.verificarAccesoModulo("USUARIOS, ROLES Y PERMISOS");
        if (!acceso) {
            alert("⛔ ACCESO DENEGADO\n\nNo tiene permisos para ver el módulo de Usuarios.");
            window.location.href = 'menu.html';
            return;
        }

        // 1. Cargar Catálogos (Roles y Módulos)
        await Promise.all([this.cargarRoles(), this.cargarModulos()]);

        // 2. Cargar Usuarios
        this.cargarUsuarios();

        // 3. Aplicar seguridad inicial (Botón Nuevo)
        this.aplicarSeguridadGlobal();

        this.verificarEstadoSmtp();
        this.setupEventListeners();
    },

    verificarAccesoModulo: async function (nombreModulo) {
        // Cachear permisos
        this.permisosActuales = { ver: false, crear: false, editar: false, eliminar: false };

        // Admin Bypass
        const rol = this.currentUser.rol || this.currentUser.rolNombre || this.currentUser.nombreRol;
        if (rol === 'ADMIN' || rol === 'Administrador') {
            this.permisosActuales = { ver: true, crear: true, editar: true, eliminar: true };
            return true;
        }

        try {
            const idUsuario = this.currentUser.userId || this.currentUser.id || this.currentUser.idUsuario;
            const res = await fetch(`${this.apiUrl}/permisos-usuario/${idUsuario}`);
            if (res.ok) {
                const permisos = await res.json();
                const p = permisos.find(x => x.modulo === nombreModulo || x.nombreModulo === nombreModulo);

                if (p) {
                    this.permisosActuales = {
                        ver: !!p.puede_ver,
                        crear: !!p.puede_crear,
                        editar: !!p.puede_editar,
                        eliminar: !!p.puede_eliminar
                    };
                    return this.permisosActuales.ver;
                }
            }
        } catch (e) { console.error(e); }
        return false;
    },

    aplicarSeguridadGlobal: function () {
        const btnNuevo = document.querySelector('button[onclick*="nuevo"]');
        if (btnNuevo) {
            if (this.permisosActuales.crear) {
                btnNuevo.style.display = 'inline-block';
            } else {
                btnNuevo.style.display = 'none';
            }
        }
    },

    // ==========================================
    // LÓGICA DEL FORMULARIO
    // ==========================================

    cargarDetalleUsuario: function (usuario) {
        this.editingUserId = usuario.id;

        // Llenar campos
        const fId = document.getElementById('detailIdUsuario');
        const fUser = document.getElementById('detailUsuario');
        const fNombre = document.getElementById('detailNombre');
        const fCorreo = document.getElementById('detailCorreo');
        const fRol = document.getElementById('detailRol');
        const fEstatus = document.getElementById('detailEstatus');
        const btnGuardar = document.getElementById('btnGuardar');
        const btnReset = document.getElementById('btnResetPassword');

        fId.value = usuario.id;
        fUser.value = usuario.id;
        fNombre.value = usuario.nombre;
        fCorreo.value = usuario.correo || usuario.email;

        // Rol
        const rolId = usuario.rolId;
        if (rolId) {
            fRol.value = rolId;
            this.cargarPermisosUsuario(usuario.id);
        } else {
            fRol.value = "";
            this.renderTablaPermisosVacia();
        }

        // Estatus
        fEstatus.value = (usuario.estatus === 'activo' || usuario.activo === true || usuario.activo === 1) ? 'activo' : 'inactivo';

        // --- SEGURIDAD: MODO EDICIÓN ---
        if (this.permisosActuales.editar) {
            // Habilitar campos
            fNombre.disabled = false;
            fCorreo.disabled = false;
            fRol.disabled = false;
            btnGuardar.style.display = 'inline-block';
            btnReset.disabled = false;

            // Seguridad Estatus (Eliminar)
            // Solo si tiene permiso ELIMINAR puede cambiar el estatus (Activo/Inactivo)
            fEstatus.disabled = !this.permisosActuales.eliminar;
        } else {
            // Modo Solo Lectura
            fNombre.disabled = true;
            fCorreo.disabled = true;
            fRol.disabled = true;
            fEstatus.disabled = true;
            btnGuardar.style.display = 'none'; // Ocultar guardar
            btnReset.disabled = true;
            this.showToast("Modo de solo lectura: No tiene permisos para editar usuarios", "info");
        }
    },

    cargarPermisosUsuario: async function (userId) {
        // Asignar permisos PERO primero validar si pueden editarse
        const inputs = document.querySelectorAll(`input[id^="perm_"]`);
        const canEditPerms = this.permisosActuales.editar;

        inputs.forEach(chk => {
            chk.checked = false;
            chk.disabled = !canEditPerms; // Bloquear checkboxes si no hay permiso de editar
        });

        if (!userId) return;

        try {
            const res = await fetch(`${this.apiUrl}/permisos-usuario/${userId}`);
            if (res.ok) {
                const permisos = await res.json();
                permisos.forEach(p => {
                    if (p.puede_ver) document.getElementById(`perm_${p.id_modulo}_ver`).checked = true;
                    if (p.puede_crear) document.getElementById(`perm_${p.id_modulo}_crear`).checked = true;
                    if (p.puede_editar) document.getElementById(`perm_${p.id_modulo}_editar`).checked = true;
                    if (p.puede_eliminar) document.getElementById(`perm_${p.id_modulo}_eliminar`).checked = true;
                });
            }
        } catch (e) { console.error(e); }
    },

    nuevo: function () {
        if (!this.permisosActuales.crear) {
            this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para crear usuarios.", "error");
            return;
        }

        this.editingUserId = null;
        this.limpiarFormulario();
        document.getElementById('detailUsuario').placeholder = "Se generará automáticamente";
        document.getElementById('btnResetPassword').disabled = true;

        // Habilitar todo para creación
        document.getElementById('detailNombre').disabled = false;
        document.getElementById('detailCorreo').disabled = false;
        document.getElementById('detailRol').disabled = false;
        document.getElementById('detailEstatus').disabled = false; // Al crear, estatus default activo
        document.getElementById('btnGuardar').style.display = 'inline-block';

        // Habilitar checks
        document.querySelectorAll(`input[id^="perm_"]`).forEach(c => c.disabled = false);
    },

    limpiarFormulario: function () {
        document.getElementById('detailIdUsuario').value = '';
        document.getElementById('detailUsuario').value = '';
        document.getElementById('detailNombre').value = '';
        document.getElementById('detailCorreo').value = '';
        document.getElementById('detailRol').value = '';
        document.getElementById('detailEstatus').value = 'activo';

        // Limpiar checkboxes
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(accion => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${accion}`);
                if (chk) {
                    chk.checked = false;
                    chk.disabled = false; // Reset visual, 'nuevo' o 'cargar' decidirán estado final
                }
            });
        });
    },

    _collectPermissions: function () {
        return this.listaModulos.map(mod => ({
            id_modulo: mod.id_modulo,
            puede_ver: document.getElementById(`perm_${mod.id_modulo}_ver`).checked,
            puede_crear: document.getElementById(`perm_${mod.id_modulo}_crear`).checked,
            puede_editar: document.getElementById(`perm_${mod.id_modulo}_editar`).checked,
            puede_eliminar: document.getElementById(`perm_${mod.id_modulo}_eliminar`).checked
        }));
    },

    guardar: async function () {
        // --- VALIDACIÓN DE SEGURIDAD (Ejecución) ---
        if (this.editingUserId) {
            // Es EDICIÓN
            if (!this.permisosActuales.editar) {
                this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para modificar usuarios.", "error");
                return;
            }
            // Validar ELIMINACIÓN (Cambio de Estatus)
            // ¿Cómo sabemos si cambió estatus? (Optimizacion: asumir validación UI o leer valor previo, 
            // pero si UI bloqueó, el valor no cambió. Si hackearon UI, backend debería validar, 
            // aqui validamos intento frontend).
            // Simplificación: Si intenta guardar 'inactivo' y no tiene permiso eliminar, bloquear.
            const estatusVal = document.getElementById('detailEstatus').value;
            if (estatusVal === 'inactivo' && !this.permisosActuales.eliminar) {
                this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para desactivar (eliminar) usuarios.", "error");
                return;
            }

        } else {
            // Es CREACIÓN
            if (!this.permisosActuales.crear) {
                this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para crear usuarios.", "error");
                return;
            }
        }

        // Validaciones básicas
        const nombre = document.getElementById('detailNombre').value;
        const email = document.getElementById('detailCorreo').value;
        const rolId = document.getElementById('detailRol').value;
        const estatusVal = document.getElementById('detailEstatus').value;

        if (!nombre || !email || !rolId) {
            this.showToast("Por favor complete todos los campos obligatorios", "warning");
            return;
        }

        // Buscar el NOMBRE del rol (para compatibilidad legacy si fuera necesario)
        const rolObj = this.listaRoles.find(r => r.id_rol === rolId);
        const rolNombre = rolObj ? rolObj.nombre : rolId;

        const usuarioData = {
            id: this.editingUserId,
            nombre: nombre,
            correo: email,
            rolNombre: rolNombre,
            estatus: estatusVal
        };

        const method = this.editingUserId ? 'PUT' : 'POST';
        const url = this.editingUserId ? `${this.apiUrl}/${this.editingUserId}` : this.apiUrl;

        try {
            // 1. Guardar Usuario
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(usuarioData)
            });

            if (response.ok) {
                const respData = await response.json();

                // Obtener ID del usuario guardado/creado
                let userId = this.editingUserId;
                if (!userId && respData.data && respData.data.id) {
                    userId = respData.data.id;
                }

                // 2. Guardar Permisos (Siempre, para asegurar que la matriz de UI se persista)
                if (userId) {
                    const permisosArr = this._collectPermissions();
                    await fetch(`${this.apiUrl}/permisos-usuario/${userId}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(permisosArr)
                    });
                }

                let msg = this.editingUserId ? "Usuario actualizado correctamente" : "Usuario creado exitosamente.";
                if (respData.data && respData.data.passwordTemp) {
                    alert(`✅ USUARIO CREADO\n\nCopie la contraseña temporal:\n\n${respData.data.passwordTemp}\n\n(También se ha enviado por correo)`);
                } else {
                    this.showToast(msg + " (Permisos actualizados)", "success");
                }

                this.cargarUsuarios();
                if (!this.editingUserId) this.limpiarFormulario();
            } else {
                let errorMsg = "Error al guardar usuario";
                try {
                    const errorData = await response.json();
                    if (errorData.message) errorMsg = errorData.message;
                } catch (e) { }
                this.showToast(errorMsg, "error");
            }
        } catch (error) {
            console.error(error);
            this.showToast("Error de conexión", "error");
        }
    },

    setupEventListeners: function () {
        // Listener para cambio de Rol en DETALLE (Carga permisos)
        document.getElementById('detailRol').addEventListener('change', (e) => {
            const roleId = e.target.value;
            if (roleId) this.cargarPermisosRol(roleId);
        });

        // 1. Buscador texto
        document.getElementById('searchInput').addEventListener('input', () => {
            this.filtrarUsuarios();
        });

        // 2. Filtro Rol
        document.getElementById('filterRol').addEventListener('change', () => {
            this.filtrarUsuarios();
        });

        // 3. Filtro Estatus (Botones toggle)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                // Quitar active a todos
                document.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                // Poner active al clickeado
                e.target.classList.add('active');
                this.filtrarUsuarios();
            });
        });
    },

    filtrarUsuarios: function () {
        const searchText = document.getElementById('searchInput').value.toLowerCase();
        const roleFilter = document.getElementById('filterRol').value;

        // Obtener estatus activo del botón
        const statusBtn = document.querySelector('.status-btn.active');
        const statusFilter = statusBtn ? statusBtn.dataset.status : 'activo';

        const filtrados = this.listaUsuarios.filter(u => {
            // 1. Filtro Texto (Nombre, Email, ID)
            const matchesText = (u.nombre || '').toLowerCase().includes(searchText) ||
                (u.correo || u.email || '').toLowerCase().includes(searchText) ||
                (u.usuario || '').toLowerCase().includes(searchText);

            // 2. Filtro Rol (Coincidencia exacta de ID o Nombre)
            // Asumimos que el value del select es el ID del rol
            const uRolId = u.rolId || u.rol || '';
            const uRolNombre = u.rolNombre || u.nombreRol || '';

            const matchesRole = roleFilter === "" ||
                uRolId === roleFilter ||
                uRolNombre === roleFilter;

            // 3. Filtro Estatus
            const uStatus = (u.estatus === 'activo' || u.activo === true || u.activo === 1) ? 'activo' : 'inactivo';
            const matchesStatus = uStatus === statusFilter;

            return matchesText && matchesRole && matchesStatus;
        });

        this.renderTabla(filtrados);
    },

    // ==========================================
    // CARGA DE CATÁLOGOS DINÁMICOS
    // ==========================================
    cargarRoles: async function () {
        try {
            const res = await fetch(`${this.apiUrl}/roles`);
            if (res.ok) {
                this.listaRoles = await res.json();
                const select = document.getElementById('detailRol');
                const filter = document.getElementById('filterRol');

                // Limpiar (manteniendo la opcion default)
                select.innerHTML = '<option value="">Seleccionar...</option>';
                filter.innerHTML = '<option value="">Todos</option>';

                this.listaRoles.forEach(rol => {
                    // Llenar Select Detalle
                    const opt = document.createElement('option');
                    opt.value = rol.id_rol; // Usamos ID real
                    opt.textContent = rol.nombre;
                    select.appendChild(opt);

                    // Llenar Filtro
                    const optF = document.createElement('option');
                    optF.value = rol.id_rol;
                    optF.textContent = rol.nombre;
                    filter.appendChild(optF);
                });
            }
        } catch (e) {
            console.error("Error cargando roles", e);
        }
    },

    cargarModulos: async function () {
        try {
            const res = await fetch(`${this.apiUrl}/modulos`);
            if (res.ok) {
                this.listaModulos = await res.json();
                this.renderTablaPermisosVacia();
            }
        } catch (e) {
            console.error("Error cargando modulos", e);
        }
    },

    renderTablaPermisosVacia: function () {
        const tbody = document.getElementById('permisosTableBody');
        tbody.innerHTML = '';

        this.listaModulos.forEach(mod => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${mod.nombre}</td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_ver" disabled></td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_crear" disabled></td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_editar" disabled></td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_eliminar" disabled></td>
            `;
            tbody.appendChild(tr);
        });
    },

    cargarPermisosRol: async function (rolId) {
        // Validación de seguridad visual: Si no puedes editar, no puedes cambiar roles ni ver cambios en matriz
        const canEdit = this.permisosActuales.editar || this.permisosActuales.crear;

        // Limpiar checks primero 
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(act => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${act}`);
                if (chk) {
                    chk.checked = false;
                    // Solo habilitar si tiene permiso global de editar
                    chk.disabled = !canEdit;
                }
            });
        });

        if (!rolId) return;

        try {
            const res = await fetch(`${this.apiUrl}/permisos/${rolId}`);
            if (res.ok) {
                const permisos = await res.json();
                permisos.forEach(p => {
                    if (p.puede_ver) document.getElementById(`perm_${p.id_modulo}_ver`).checked = true;
                    if (p.puede_crear) document.getElementById(`perm_${p.id_modulo}_crear`).checked = true;
                    if (p.puede_editar) document.getElementById(`perm_${p.id_modulo}_editar`).checked = true;
                    if (p.puede_eliminar) document.getElementById(`perm_${p.id_modulo}_eliminar`).checked = true;
                });
            }
        } catch (e) {
            console.error("Error cargando permisos", e);
            this.showToast("No se pudieron cargar los permisos del rol", "error");
        }
    },

    guardarPermisos: async function () {
        const rolId = document.getElementById('detailRol').value;
        if (!rolId) {
            this.showToast("Seleccione un rol para guardar sus permisos", "warning");
            return;
        }

        if (!confirm("⚠️ ¿Actualizar la definición de permisos para el ROL " + rolId + "?\n\nEsto afectará a TODOS los usuarios con este rol.")) {
            return;
        }

        const payload = this.listaModulos.map(mod => ({
            id_modulo: mod.id_modulo,
            puede_ver: document.getElementById(`perm_${mod.id_modulo}_ver`).checked,
            puede_crear: document.getElementById(`perm_${mod.id_modulo}_crear`).checked,
            puede_editar: document.getElementById(`perm_${mod.id_modulo}_editar`).checked,
            puede_eliminar: document.getElementById(`perm_${mod.id_modulo}_eliminar`).checked
        }));

        try {
            const res = await fetch(`${this.apiUrl}/permisos/${rolId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (res.ok) {
                this.showToast("Matriz de permisos actualizada correctamente", "success");
            } else {
                this.showToast("Error al actualizar permisos", "error");
            }
        } catch (e) {
            this.showToast("Error de conexión", "error");
        }
    },

    // =========================================================
    // GESTIÓN DE USUARIOS (CRUD)
    // =========================================================

    cargarUsuarios: async function () {
        try {
            const response = await fetch(this.apiUrl);
            if (response.ok) {
                const usuarios = await response.json();
                this.listaUsuarios = usuarios; // Guardar cache
                this.filtrarUsuarios(); // Aplicar filtros visuales actuales
            } else {
                console.error("Error cargando usuarios");
            }
        } catch (error) {
            console.error("Error de conexión:", error);
            this.showToast("Error de conexión con el servidor", "error");
        }
    },

    ver: function (id) {
        const usuario = this.listaUsuarios.find(u => (u.id || u.idUsuario) === id);
        if (usuario) {
            this.cargarDetalleUsuario(usuario);
        }
    },

    renderTabla: function (usuarios) {
        const tbody = document.getElementById('usuariosTableBody');
        tbody.innerHTML = '';

        if (usuarios.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay usuarios registrados</td></tr>';
            return;
        }

        usuarios.forEach(u => {
            const tr = document.createElement('tr');
            // Al hacer click en la fila, cargamos detalle (Master-Detail)
            tr.onclick = () => this.cargarDetalleUsuario(u);

            const estatusClass = (u.estatus === 'activo' || u.activo) ? 'activo' : 'inactivo';
            const estatusText = (u.estatus === 'activo' || u.activo) ? 'Activo' : 'Inactivo';

            // Mostrar Rol TAL CUAL viene de BD (sin alias)
            const rolCode = u.rolNombre || u.nombreRol;
            const rolDisplay = rolCode || 'Sin Asignar';
            const uid = u.id || u.idUsuario;

            tr.innerHTML = `
                <td>
                    <div style="font-weight: 500;">${u.nombre || 'Sin Nombre'}</div>
                    <div style="font-size: 0.75rem; color: #6B7280;">ID: ${uid}</div>
                </td>
                <td>${u.correo || u.email || 'Sin correo'}</td>
                <td><span class="badge-rol">${rolDisplay}</span></td>
                <td><span class="status-indicator ${estatusClass}"></span>${estatusText}</td>
                <td>
                   <button class="btn-secondary" style="padding: 4px 8px; font-size: 0.75rem;" onclick="event.stopPropagation(); UsuariosService.ver('${uid}')">Ver</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    },

    cargarDetalleUsuario: function (usuario) {
        this.editingUserId = usuario.id;

        // Llenar campos
        document.getElementById('detailIdUsuario').value = usuario.id;
        document.getElementById('detailUsuario').value = usuario.id;
        document.getElementById('detailNombre').value = usuario.nombre;
        document.getElementById('detailCorreo').value = usuario.correo || usuario.email;

        // Asignación de Rol por ID
        const rolId = usuario.rolId;
        if (rolId) {
            document.getElementById('detailRol').value = rolId;
            // Cargar permisos específicos del USUARIO (con fallback a rol en backend)
            this.cargarPermisosUsuario(usuario.id);
        } else {
            document.getElementById('detailRol').value = "";
            this.renderTablaPermisosVacia();
        }

        document.getElementById('detailEstatus').value = (usuario.estatus === 'activo' || usuario.activo === true || usuario.activo === 1) ? 'activo' : 'inactivo';

        // Habilitar botón reset pass
        document.getElementById('btnResetPassword').disabled = false;
    },

    cargarPermisosUsuario: async function (userId) {
        // Habilitar checkboxes para edición (siempre)
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(act => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${act}`);
                if (chk) { chk.disabled = false; chk.checked = false; }
            });
        });

        if (!userId) return;

        try {
            const res = await fetch(`${this.apiUrl}/permisos-usuario/${userId}`);
            if (res.ok) {
                const permisos = await res.json();
                permisos.forEach(p => {
                    if (p.puede_ver) document.getElementById(`perm_${p.id_modulo}_ver`).checked = true;
                    if (p.puede_crear) document.getElementById(`perm_${p.id_modulo}_crear`).checked = true;
                    if (p.puede_editar) document.getElementById(`perm_${p.id_modulo}_editar`).checked = true;
                    if (p.puede_eliminar) document.getElementById(`perm_${p.id_modulo}_eliminar`).checked = true;
                });
            }
        } catch (e) {
            console.error("Error cargando permisos de usuario", e);
        }
    },

    nuevo: function () {
        if (!this.permisosActuales.crear) {
            this.showToast("⛔ No tiene permiso para crear usuarios", "error");
            return;
        }

        this.editingUserId = null;
        this.limpiarFormulario();
        document.getElementById('detailUsuario').placeholder = "Se generará automáticamente";
        document.getElementById('btnResetPassword').disabled = true;

        // Habilitar campos explícitamente por si venían de un estado disabled
        document.getElementById('detailNombre').disabled = false;
        document.getElementById('detailCorreo').disabled = false;
        document.getElementById('detailRol').disabled = false;
        document.getElementById('detailEstatus').disabled = false;
        document.getElementById('btnGuardar').style.display = 'inline-block';
    },

    limpiarFormulario: function () {
        document.getElementById('detailIdUsuario').value = '';
        document.getElementById('detailUsuario').value = '';
        document.getElementById('detailNombre').value = '';
        document.getElementById('detailCorreo').value = '';
        document.getElementById('detailRol').value = '';
        document.getElementById('detailEstatus').value = 'activo';

        // Limpiar checkboxes
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(accion => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${accion}`);
                if (chk) chk.checked = false;
            });
        });
    },

    _collectPermissions: function () {
        return this.listaModulos.map(mod => ({
            id_modulo: mod.id_modulo,
            puede_ver: document.getElementById(`perm_${mod.id_modulo}_ver`).checked,
            puede_crear: document.getElementById(`perm_${mod.id_modulo}_crear`).checked,
            puede_editar: document.getElementById(`perm_${mod.id_modulo}_editar`).checked,
            puede_eliminar: document.getElementById(`perm_${mod.id_modulo}_eliminar`).checked
        }));
    },

    guardar: async function () {
        // --- VALIDACIÓN DE SEGURIDAD ROBUSTA ---
        // 1. Validar Acción Global (Crear vs Editar)
        if (this.editingUserId) {
            if (!this.permisosActuales.editar) {
                alert("⛔ ACCESO DENEGADO\n\nNo tiene permisos para modificar la información de usuarios.");
                return;
            }
        } else {
            if (!this.permisosActuales.crear) {
                alert("⛔ ACCESO DENEGADO\n\nNo tiene permisos para crear usuarios.");
                return;
            }
        }

        // 2. Validar Desactivación (Eliminar Lógico)
        const estatusVal = document.getElementById('detailEstatus').value;
        if (this.editingUserId && estatusVal === 'inactivo' && !this.permisosActuales.eliminar) {
            alert("⛔ ACCESO DENEGADO\n\nNo tiene permisos para desactivar (eliminar) usuarios.");
            return;
        }

        // Validaciones básicas
        const nombre = document.getElementById('detailNombre').value;
        const email = document.getElementById('detailCorreo').value;
        const rolId = document.getElementById('detailRol').value;
        // estatusVal ya obtenido arriba

        if (!nombre || !email || !rolId) {
            this.showToast("Por favor complete todos los campos obligatorios", "warning");
            return;
        }

        // Buscar el NOMBRE del rol (para compatibilidad legacy si fuera necesario)
        const rolObj = this.listaRoles.find(r => r.id_rol === rolId);
        const rolNombre = rolObj ? rolObj.nombre : rolId;

        const usuarioData = {
            id: this.editingUserId,
            nombre: nombre,
            correo: email,
            rolNombre: rolNombre,
            estatus: estatusVal
        };

        const method = this.editingUserId ? 'PUT' : 'POST';
        const url = this.editingUserId ? `${this.apiUrl}/${this.editingUserId}` : this.apiUrl;

        try {
            // 1. Guardar Usuario
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(usuarioData)
            });

            if (response.ok) {
                const respData = await response.json();

                // Obtener ID del usuario guardado/creado
                // Si es update, usamos editingUserId. Si es create, viene en respData.data.id
                let userId = this.editingUserId;
                if (!userId && respData.data && respData.data.id) {
                    userId = respData.data.id;
                }

                // 2. Guardar Permisos (Siempre, para asegurar que la matriz de UI se persista)
                if (userId) {
                    const permisosArr = this._collectPermissions();
                    await fetch(`${this.apiUrl}/permisos-usuario/${userId}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(permisosArr)
                    });
                }

                let msg = this.editingUserId ? "Usuario actualizado correctamente" : "Usuario creado exitosamente.";
                if (respData.data && respData.data.passwordTemp) {
                    alert(`✅ USUARIO CREADO\n\nCopie la contraseña temporal:\n\n${respData.data.passwordTemp}\n\n(También se ha enviado por correo)`);
                } else {
                    this.showToast(msg + " (Permisos actualizados)", "success");
                }

                this.cargarUsuarios();
                if (!this.editingUserId) this.limpiarFormulario();
            } else {
                let errorMsg = "Error al guardar usuario";
                try {
                    const errorData = await response.json();
                    if (errorData.message) errorMsg = errorData.message;
                } catch (e) { }
                this.showToast(errorMsg, "error");
            }
        } catch (error) {
            console.error(error);
            this.showToast("Error de conexión", "error");
        }
    },

    setupEventListeners: function () {
        // Listener para cambio de Rol en DETALLE (Carga permisos)
        document.getElementById('detailRol').addEventListener('change', (e) => {
            const roleId = e.target.value;
            if (roleId) this.cargarPermisosRol(roleId);
        });

        // 1. Buscador texto
        document.getElementById('searchInput').addEventListener('input', () => {
            this.filtrarUsuarios();
        });

        // 2. Filtro Rol
        document.getElementById('filterRol').addEventListener('change', () => {
            this.filtrarUsuarios();
        });

        // 3. Filtro Estatus (Botones toggle)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                // Quitar active a todos
                document.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                // Poner active al clickeado
                e.target.classList.add('active');
                this.filtrarUsuarios();
            });
        });
    },

    filtrarUsuarios: function () {
        const searchText = document.getElementById('searchInput').value.toLowerCase();
        const roleFilter = document.getElementById('filterRol').value;

        // Obtener estatus activo del botón
        const statusBtn = document.querySelector('.status-btn.active');
        const statusFilter = statusBtn ? statusBtn.dataset.status : 'activo';

        const filtrados = this.listaUsuarios.filter(u => {
            // 1. Filtro Texto (Nombre, Email, ID)
            const matchesText = (u.nombre || '').toLowerCase().includes(searchText) ||
                (u.correo || u.email || '').toLowerCase().includes(searchText) ||
                (u.usuario || '').toLowerCase().includes(searchText);

            // 2. Filtro Rol (Coincidencia exacta de ID o Nombre)
            // Asumimos que el value del select es el ID del rol
            const uRolId = u.rolId || u.rol || '';
            const uRolNombre = u.rolNombre || u.nombreRol || '';

            const matchesRole = roleFilter === "" ||
                uRolId === roleFilter ||
                uRolNombre === roleFilter;

            // 3. Filtro Estatus
            const uStatus = (u.estatus === 'activo' || u.activo === true || u.activo === 1) ? 'activo' : 'inactivo';
            const matchesStatus = uStatus === statusFilter;

            return matchesText && matchesRole && matchesStatus;
        });

        this.renderTabla(filtrados);
    },

    resetPassword: async function () {
        if (!this.editingUserId) return;

        if (confirm("¿Está seguro? Esto generará una nueva contraseña.")) {
            try {
                const response = await fetch(`${this.apiUrl}/${this.editingUserId}/reset-password`, {
                    method: 'POST'
                });
                const data = await response.json();

                if (response.ok && data.tempPassword) {
                    alert(`🔐 CONTRASEÑA RESTABLECIDA\n\nNueva contraseña:\n\n${data.tempPassword}\n\n(Cópiela ahora)`);
                } else {
                    this.showToast("Error: " + data.message, "error");
                }
            } catch (e) {
                this.showToast("Error de red", "error");
            }
        }
    },

    // =========================================================
    // CONFIGURACIÓN SMTP (SEGURIDAD)
    // =========================================================

    verificarEstadoSmtp: async function () {
        const btnSmtp = document.getElementById('btnSmtpConfig');
        const btnBitacora = document.getElementById('btnBitacora');

        // Validar rol de forma robusta
        const user = this.currentUser || MenuService.getUser();
        const rol = user ? (user.rol || user.rolNombre || user.nombreRol) : '';
        const isAdmin = rol === 'Administrador' || rol === 'ADMIN';

        // Controlar visibilidad de botones Admin
        if (isAdmin) {
            if (btnSmtp) btnSmtp.style.display = 'flex';
            if (btnBitacora) btnBitacora.style.display = 'flex';
        } else {
            if (btnSmtp) btnSmtp.style.display = 'none';
            if (btnBitacora) btnBitacora.style.display = 'none';
            return;
        }

        try {
            const response = await fetch(`${this.smtpUrl}/status`);
            const data = await response.json();

            // const btn = document.getElementById('btnSmtpConfig'); // Ya definido arriba
            if (btnSmtp) {
                if (data.status === 'CONFIG_OK') {
                    btnSmtp.classList.add('status-ok');
                    btnSmtp.classList.remove('status-error');
                } else {
                    btnSmtp.classList.add('status-error');
                    btnSmtp.classList.remove('status-ok');
                    this.showToast("⚠️ Alerta: Configuración SMTP no detectada o corrupta", "error");
                }
            }
        } catch (e) {
            console.error("Error verificando SMTP", e);
        }
    },

    abrirSmtpModal: async function () {
        document.getElementById('smtpModal').classList.add('open');

        // Intentar cargar datos actuales
        try {
            const response = await fetch(this.smtpUrl);
            if (response.ok) {
                const config = await response.json();
                // Llenar form
                document.getElementById('smtpPerfil').value = config.proveedor || 'custom';
                document.getElementById('smtpHost').value = config.host;
                document.getElementById('smtpPort').value = config.port;
                document.getElementById('smtpUser').value = config.username;
                document.getElementById('smtpPass').value = ""; // No mostramos password real por seguridad, el usuario debe reingresarla si cambia algo. O podríamos mostrar placeholder.
                document.getElementById('smtpAuth').checked = config.auth;
                document.getElementById('smtpTls').checked = config.starttls;
            }
        } catch (e) {
            // Nueva config
        }
    },

    cerrarSmtpModal: function () {
        document.getElementById('smtpModal').classList.remove('open');
    },

    cambiarPerfilSmtp: function () {
        const perfil = document.getElementById('smtpPerfil').value;
        const host = document.getElementById('smtpHost');
        const port = document.getElementById('smtpPort');

        if (perfil === 'gmail') {
            host.value = 'smtp.gmail.com';
            port.value = '587';
            host.readOnly = true;
            port.readOnly = true;
        } else if (perfil === 'hotmail') {
            host.value = 'smtp.office365.com';
            port.value = '587';
            host.readOnly = true;
            port.readOnly = true;
        } else {
            host.value = '';
            port.value = '';
            host.readOnly = false;
            port.readOnly = false;
        }
    },

    guardarSmtp: async function () {
        const config = {
            proveedor: document.getElementById('smtpPerfil').value,
            host: document.getElementById('smtpHost').value,
            port: parseInt(document.getElementById('smtpPort').value),
            username: document.getElementById('smtpUser').value,
            password: document.getElementById('smtpPass').value,
            auth: document.getElementById('smtpAuth').checked,
            starttls: document.getElementById('smtpTls').checked
        };

        if (!config.host || !config.username || !config.password) {
            alert("Faltan datos obligatorios");
            return;
        }

        try {
            const response = await fetch(this.smtpUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(config)
            });

            if (response.ok) {
                this.showToast("Configuración SMTP guardada y encriptada", "success");
                this.cerrarSmtpModal();
                this.verificarEstadoSmtp(); // Actualizar semáforo
            } else {
                this.showToast("Error guardando configuración", "error");
            }
        } catch (e) {
            this.showToast("Error de red", "error");
        }
    },

    probarSmtp: async function () {
        const config = {
            proveedor: document.getElementById('smtpPerfil').value,
            host: document.getElementById('smtpHost').value,
            port: parseInt(document.getElementById('smtpPort').value),
            username: document.getElementById('smtpUser').value,
            password: document.getElementById('smtpPass').value,
            auth: document.getElementById('smtpAuth').checked,
            starttls: document.getElementById('smtpTls').checked
        };

        if (!config.username || !config.password) {
            alert("Ingrese usuario y contraseña para probar");
            return;
        }

        this.showToast("Enviando correo de prueba...", "info");

        try {
            const response = await fetch(`${this.smtpUrl}/test`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(config)
            });

            const res = await response.json();
            if (response.ok) {
                this.showToast("✅ " + res.message, "success");
            } else {
                this.showToast("❌ Falló: " + res.message, "error");
            }
        } catch (e) {
            this.showToast("Error de conexión", "error");
        }
    },

    // ==========================================
    // FUNCIONES FUTURAS (Placeholder)
    // ==========================================
    verBitacora: function () {
        this.showToast("🚧 Funcionalidad de Auditoría en construcción (Próxima etapa)", "info");
    },

    // =========================================================
    // UTILIDADES (Toast)
    // =========================================================
    showToast: function (message, type = 'info') {
        const container = document.getElementById('toastContainer');
        const toast = document.createElement('div');
        toast.className = `alert ${type === 'error' ? 'alert-error' : 'is-valid'}`; // Reusando clases CSS existentes
        toast.style.background = type === 'success' ? '#D1FAE5' : (type === 'error' ? '#FEE2E2' : '#EFF6FF');
        toast.style.color = type === 'success' ? '#065F46' : (type === 'error' ? '#991B1B' : '#1E40AF');
        toast.style.padding = '15px 20px';
        toast.style.marginBottom = '10px';
        toast.style.borderRadius = '8px';
        toast.style.boxShadow = '0 4px 6px rgba(0,0,0,0.1)';
        toast.style.minWidth = '300px';
        toast.style.zIndex = '9999';
        toast.style.display = 'flex';
        toast.style.alignItems = 'center';
        toast.innerText = message;

        container.appendChild(toast);

        // Auto remove
        setTimeout(() => {
            toast.remove();
        }, 4000);
    }
};

// Iniciar al cargar
document.addEventListener('DOMContentLoaded', () => {
    UsuariosService.init();
});
```
</details>

<details>
<summary><strong>📂 10.3 Backend API: UsuarioController.java</strong></summary>

```java
/*
============================================================
Nombre del archivo : UsuarioController.java
Ruta              : backend/src/main/java/com/omcgc/erp/controller/UsuarioController.java
Tipo              : Backend (REST Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Controlador REST para la gestión completa de usuarios (CRUD), incluyendo
asignación de roles y permisos. Implementa los endpoints del módulo 1.3.

Trazabilidad:
- HU-M01-03: Gestión de Usuarios, Roles y Permisos
- Endpoints: GET, POST, PUT, DELETE /api/usuarios
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtener todos los usuarios
     * GET /api/usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuario por ID
     * GET /api/usuarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable String id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo usuario
     * POST /api/usuarios
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.create(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Usuario creado exitosamente",
                            "data", nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al crear usuario: " + e.getMessage()));
        }
    }

    /**
     * Actualizar usuario existente
     * PUT /api/usuarios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUsuario(
            @PathVariable String id,
            @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Usuario actualizado = usuarioService.update(usuario);

            if (actualizado != null) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Usuario actualizado exitosamente",
                        "data", actualizado));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al actualizar usuario: " + e.getMessage()));
        }
    }

    /**
     * Eliminar (desactivar) usuario
     * DELETE /api/usuarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUsuario(@PathVariable String id) {
        try {
            boolean eliminado = usuarioService.delete(id);

            if (eliminado) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Usuario desactivado exitosamente"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al desactivar usuario: " + e.getMessage()));
        }
    }

    /**
     * Filtrar usuarios por estatus
     * GET /api/usuarios/estatus/{estatus}
     */
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<Usuario>> getUsuariosByEstatus(@PathVariable String estatus) {
        try {
            List<Usuario> usuarios = usuarioService.findByEstatus(estatus);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Filtrar usuarios por rol
     * GET /api/usuarios/rol/{rolId}
     */
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable String rolId) {
        try {
            List<Usuario> usuarios = usuarioService.findByRol(rolId);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Restablecer contraseña de usuario
     * POST /api/usuarios/{id}/reset-password
     */
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@PathVariable String id) {
        try {
            String nuevaPassword = usuarioService.resetPassword(id);

            if (nuevaPassword != null) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Contraseña restablecida correctamente. Copie la nueva contraseña.",
                        "tempPassword", nuevaPassword));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al restablecer contraseña: " + e.getMessage()));
        }
    }

    // ==========================================
    // ENDPOINTS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    @GetMapping("/roles")
    public ResponseEntity<List<Map<String, Object>>> getRoles() {
        return ResponseEntity.ok(usuarioService.getAllRoles());
    }

    @GetMapping("/modulos")
    public ResponseEntity<List<Map<String, Object>>> getModulos() {
        return ResponseEntity.ok(usuarioService.getAllModules());
    }

    @GetMapping("/permisos/{idRol}")
    public ResponseEntity<List<Map<String, Object>>> getPermisosByRol(@PathVariable String idRol) {
        return ResponseEntity.ok(usuarioService.getPermissionsByRol(idRol));
    }

    @PutMapping("/permisos/{idRol}")
    public ResponseEntity<Map<String, Object>> updatePermisos(@PathVariable String idRol,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            for (Map<String, Object> p : permisos) {
                String idModulo = (String) p.get("id_modulo");
                // Manejar posibles nulos o tipos incorrectos (Boolean vs Integer)
                boolean ver = Boolean.TRUE.equals(p.get("puede_ver"));
                boolean crear = Boolean.TRUE.equals(p.get("puede_crear"));
                boolean editar = Boolean.TRUE.equals(p.get("puede_editar"));
                boolean eliminar = Boolean.TRUE.equals(p.get("puede_eliminar"));

                usuarioService.updatePermission(idRol, idModulo, ver, crear, editar, eliminar);
            }
            return ResponseEntity.ok(Map.of("success", true, "message", "Permisos actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al guardar permisos: " + e.getMessage()));
        }
    }

    // ==========================================
    // PERMISOS POR USUARIO (PERSONALIZADOS)
    // ==========================================

    @GetMapping("/permisos-usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> getPermisosByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(usuarioService.getPermissionsByUsuario(idUsuario));
    }

    @PutMapping("/permisos-usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> updatePermisosUsuario(@PathVariable String idUsuario,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            usuarioService.savePermissionsForUser(idUsuario, permisos);
            return ResponseEntity
                    .ok(Map.of("success", true, "message", "Permisos de usuario actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al guardar permisos: " + e.getMessage()));
        }
    }
}
```
</details>

---
**FIN DEL DOCUMENTO - ETAPA 1**


