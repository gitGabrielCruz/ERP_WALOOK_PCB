---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 1 - Índice Maestro  
**VERSIÓN:** 1.0  
**FECHA:** 04 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 📊 ETAPA 1 - ÍNDICE MAESTRO

## ÓPTICA ERP — BASE FUNCIONAL DEL SISTEMA

---

## 📋 INTRODUCCIÓN

La **Etapa 1** constituye la base funcional mínima operativa del Sistema Web ERP en la nube para OMCGC - WALOOK MÉXICO, S.A. de C.V. Esta etapa implementa los componentes esenciales de autenticación, navegación y gestión de usuarios que permiten el acceso seguro y controlado al sistema.

### Objetivos de la Etapa 1

1. ✅ Implementar sistema de autenticación seguro
2. ✅ Crear menú de navegación principal
3. ✅ Desarrollar gestión de usuarios, roles y permisos
4. ✅ Establecer control de acceso basado en roles (RBAC)
5. ✅ Garantizar persistencia de sesión
6. ✅ Implementar Bitácora de Auditoría (Trazabilidad Forense)
7. ✅ Privacy Engine: Buscador sobre datos cifrados

### Alcance

**Módulos incluidos:**
- Login / Autenticación
- Menú Principal
- Usuarios, Roles y Permisos
- Bitácora de Auditoría

**Requerimientos Funcionales cerrados:**
- RF-01: Autenticación
- RF-02: Gestión de usuarios
- RF-03: Roles y permisos
- RF-04: Navegación base
- RF-05: Comunicación API
- RF-06: Persistencia básica
- RF-07: Trazabilidad y Auditoría (Bitácora)
- RF-08: Privacy Engine (Búsqueda Ciega/Cifrada)

**Requerimientos No Funcionales:**
- RNF-02: Seguridad básica
- RNF-03: Integración frontend-backend
- RNF-04: Consistencia visual

---

## 📁 ESTRUCTURA DE LA DOCUMENTACIÓN

### Módulos Documentados

| # | Módulo | Archivo | Estado | Líneas Aprox. |
|---|--------|---------|--------|---------------|
| **1.1** | **Login** | `ETAPA 1 - LOGIN.md` | ✅ Completo | ~300 |
| **1.2** | **Menú Principal** | `ETAPA 1 - MENU.md` | ✅ Completo | ~200 |
| **1.3** | **Usuarios, Roles y Permisos** | `ETAPA 1 - USUARIOS.md` | ✅ Completo | ~400 |
| **1.4** | **Bitácora de Auditoría** | `ETAPA 1 - USUARIOS.md` | ✅ Completo | ~350 |

### Código Fuente

| Tipo | Archivo | Descripción |
|------|---------|-------------|
| **Código Completo** | `ETAPA 1 - CODIGO_FUENTE.md` | Todo el código fuente: Frontend (HTML, CSS, JS), Backend (Java), Base de Datos (SQL) |

---

## 🎯 METODOLOGÍA DE DOCUMENTACIÓN

Cada módulo sigue la metodología de **4 pasos**:

### **PASO 1:** Extracción Literal del Diseño
- Historias de Usuario (HU)
- Requerimientos No Funcionales (RNF)
- Casos de Uso (CU)
- Diagramas aplicables
- Datos literales del diseño SVG

### **PASO 2:** Cruce con Plan de Desarrollo
- Confirmación de pertenencia a Etapa 1
- RF que se cierran
- Funcionalidades reservadas a etapas futuras

### **PASO 3:** Descomposición UI → Lógica → Datos
- Elementos de interfaz identificados
- Trazabilidad técnica por elemento
- Mapeo a base de datos
- Flujos de datos completos

### **PASO 4:** Contenido Operativo
- Reglas de negocio cerradas
- Flujos if/then
- Validaciones específicas
- Endpoints API REST
- Criterios de aceptación

---

## 🗂️ CONTENIDO POR MÓDULO

### 1.1 LOGIN

**Archivo:** `ETAPA 1 - LOGIN.md`

**Pantalla:** Login.svg  
**RF Asociados:** RF-01, RF-05, RF-06  
**RNF Asociados:** RNF-02, RNF-03, RNF-04  

**Elementos UI:**
- Campo: Usuario / Email
- Campo: Contraseña
- Botón: Iniciar sesión
- Enlace: ¿Olvidaste tu contraseña?
- Acción: Cerrar sesión

**Tablas BD:**
- `usuario`
- `usuario_rol`
- `rol`

**Casos de Uso:**
- CU-01: Iniciar sesión
- CU-02: Recuperar contraseña
- CU-03: Cerrar sesión
- CU-04: Acceso a menú según rol

---

### 1.2 MENÚ PRINCIPAL

**Archivo:** `ETAPA 1 - MENU.md`

**Pantalla:** Menu.svg  
**RF Asociados:** RF-04  

**Elementos UI (12 Módulos):**
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

**Reglas:**
- Menú solo se renderiza con sesión activa
- Contenido depende del rol asociado
- Opciones sin permiso no se muestran

---

### 1.3 USUARIOS, ROLES Y PERMISOS

**Archivo:** `ETAPA 1 - USUARIOS.md`

**Pantalla:** UsuariosRolesPermisos.svg  
**RF Asociados:** RF-02, RF-03, RF-06  

**Elementos UI:**
- Listado de usuarios (Username, Correo, Rol, Estatus)
- Panel de detalle (Datos generales, Asignación de rol)
- Matriz de permisos (12 módulos × 4 acciones)

**Tablas BD:**
- `usuario`
- `rol`
- `usuario_rol`
- `modulo`
- `permiso` (con soporte para permisos por rol y por usuario)

**Funcionalidades:**
- Alta de usuarios
- Edición de usuarios
- Asignación de roles
- Configuración granular de permisos

---

## 💻 CÓDIGO FUENTE

**Archivo:** `ETAPA 1 - CODIGO_FUENTE.md`

**Contenido:**
- **Frontend:**
  - `login.html`
  - `menu.html`
  - `usuarios.html`
  - `login.css`, `menu.css`, `usuarios.css`
  - `login-service.js`, `menu-service.js`, `usuarios-service.js`
  - `api-config.js`

- **Backend:**
  - `src/main/java/com/omcgc/erp/controller/UsuarioController.java`
  - `src/main/java/com/omcgc/erp/controller/RolController.java`
  - `src/main/java/com/omcgc/erp/controller/PermisoController.java`
  - `src/main/java/com/omcgc/erp/service/UsuarioService.java`
  - `src/main/java/com/omcgc/erp/repository/UsuarioRepository.java`
  - `src/main/java/com/omcgc/erp/service/AuthService.java`
  - `src/main/java/com/omcgc/erp/service/BitacoraService.java`
  - `src/main/java/com/omcgc/erp/service/AuditPatternService.java`
  - `src/main/java/com/omcgc/erp/repository/BitacoraRepository.java`

- **Base de Datos:**
  - Scripts SQL de creación de tablas
  - Scripts de datos iniciales
  - Stored procedures (si aplica)

---

## 📊 MODELO DE DATOS BASE

### Tablas Principales

| Tabla | Descripción | Campos Clave |
|-------|-------------|--------------|
| **usuario** | Identidad y acceso | id_usuario, email, password_hash, activo |
| **rol** | Catálogo de roles | id_rol, nombre (ADMIN, VENDEDOR, OPTOMETRISTA, CAJA, TALLER, ALMACEN) |
| **usuario_rol** | Asignación rol-usuario | id_usuario, id_rol |
| **modulo** | Catálogo de 12 módulos | id_modulo, nombre |
| **permiso** | Matriz de permisos | id_rol, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar |
| **bitacora_seguridad** | Trazabilidad de eventos | id_bitacora, id_usuario, accion (cifrada), detalles (cifrada), fecha |

### Algoritmo de Resolución de Permisos

**Waterfall Permission Check:**
1. Buscar permiso específico del usuario (`id_usuario`)
2. Si no existe, heredar permiso del rol (`id_rol`)
3. Si no existe, denegar acceso por defecto

---

## ✅ CRITERIOS DE CIERRE DE ETAPA 1

- [x] Login funcional y sesión obligatoria
- [x] Menú funcional con manejo de módulos inexistentes
- [x] CRUD básico de usuarios operativo
- [x] Roles y permisos persistentes
- [x] Control de acceso por rol funcional
- [x] Documentación completa de los 3 módulos
- [x] Código fuente consolidado en archivo único

---

## 📈 PROGRESO DE LA ETAPA

| Componente | Estado | Progreso |
|------------|--------|----------|
| **Documentación** | ✅ Completa | 100% |
| **Código Frontend** | ✅ Completo | 100% |
| **Código Backend** | ✅ Completo | 100% |
| **Base de Datos** | ✅ Completa | 100% |
| **Pruebas** | ✅ Completas | 100% |

**Total Etapa 1:** **100%** ✅

---

## 🔗 NAVEGACIÓN

- **Siguiente:** [ETAPA 2 - Gestión Operativa Principal](ETAPA 2.md)
- **Anterior:** [ETAPA 0 - Análisis General](ETAPA_0.md)

---

## 📝 CONTROL DE VERSIONES

| Versión | Fecha | Autor | Cambios |
|---------|-------|-------|---------|
| 1.0 | 04/02/2026 | Ing. Gabriel Amilcar Cruz Canto | Creación del índice maestro de Etapa 1 |

---

**Última actualización:** 04 de febrero de 2026, 03:25 AM  
**Estado:** Documentación completa  
**Progreso:** 100% ✅
