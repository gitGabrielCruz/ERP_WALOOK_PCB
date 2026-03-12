---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** Bitácora de Errores y Pendientes Técnicos  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 🐞 BITÁCORA DE ERRORES Y PENDIENTES TÉCNICOS (ETAPA 2)


## ETAPA 2: VALIDACIÓN DE MÓDULOS

| ID | Módulo | Descripción del Error | Estado | Prioridad |
|---|---|---|---|---|
| E2-001 | Seguridad | **Acceso Denegado para Root en Clientes:** El usuario maestro "root" no puede acceder al módulo de Clientes. Muestra mensaje de falta de permisos, aunque debería tener acceso total (Admin Bypass). | 🟢 Corregido | Alta |
| E2-002 | Funcionalidad (Filtros) | **Buscador y Filtrado en Clientes Inoperante:** La barra de búsqueda por Nombre y el filtro por RFC en el módulo de Clientes no están funcionando. | 🟢 Corregido | Media |
| E2-003 | UI/UX (Estilos) | **Mejora Visual Tabla de Clientes:** Falta estilo "cebra" (fila gris alternada) para lectura rápida. El estatus INACTIVO debe resaltar más (letras rojas / fondo amarillo). | 🟢 Corregido | Baja |
| E2-004 | UI/UX (Interacción) | **Interacción Click en Fila:** Habilitar que el clic en cualquier parte de la fila de la tabla cargue el detalle del cliente (igual que el botón "Ver"). | 🟢 Corregido | Media |
| E2-005 | Funcionalidad (Eliminación) | **Eliminar Funcionalidad Fusión:** El botón "Fusión" y toda la lógica relacionada (backend/frontend/documentación) debe ser eliminada completamente del alcance de Clientes. Funcionalidad descartada. | 🟢 Corregido | Nula (Eliminar) |
| E2-006 | Arquitectura (Seguridad) | **Inconsistencia en Validación de Permisos:** Lógica de permisos duplicada y dispersa en `usuarios-service.js` y `clientes-service.js`. Violación de SRP y riesgo de mantenimiento. | 🟢 Corregido | Alta |
| E2-007 | Funcionalidad (Sesión) | **Fallo Crítico de Sesión (Key Mismatch):** El nuevo `AuthService` usaba la clave `usuario_erp` mientras que el Login guardaba la sesión como `user`. Causaba expulsión inmediata tras login. | 🟢 Corregido | Crítica |

---

## 📝 NOTAS DE ANÁLISIS

### Análisis E2-001 (Root Access)

**Situación Actual en `UsuariosService.js` (Funcional):**
En `verificarAccesoModulo` de `usuarios-service.js` existe una lógica explícita de bypass:
```javascript
// [ADMIN BYPASS] Validación especial para Superusuarios
const rol = this.currentUser.rol || this.currentUser.rolNombre || this.currentUser.nombreRol;
if (rol === 'ADMIN' || rol === 'Administrador') { // <--- Aquí valida el rol del objeto sesión
    this.permisosActuales = { ver: true, crear: true, editar: true, eliminar: true };
    return true;
}
```

**Hipótesis del fallo en `ClientesService.js`:**
Es probable que el objeto `currentUser` del usuario "root" no tenga las propiedades `rol` o `rolNombre` seteadas exactamente como 'ADMIN' o 'Administrador' al momento de llegar a `ClientesService`, o que la lógica de bypass esté incompleta o falte en ese archivo específico.

**Acción Requerida:**
1. Verificar cómo se construye el objeto `currentUser` para el usuario `root` en el `login-service.js` (o backend).
2. Uniformizar la lógica de "Admin Bypass" en `ClientesService.js` (y eventualmente centralizarla para no repetir código).

### Análisis E2-002 (Filtros Clientes)

**Situación:**
Los inputs de búsqueda en `clientes.html` no disparan el refresco de la tabla o el backend no está procesando los parámetros `buscar` y `rfc` correctamente.

**Comparativa (`UsuariosService.js` vs `ClientesService.js`):**
- En **Usuarios**, el listener es `input` (tiempo real) y llama a `filtrarUsuarios()` que hace filtro en memoria (array `listaUsuarios`) o recarga del backend.
- En **Clientes**, el código actual parece usar `keyup` ('Enter') o `change`. Se debe verificar si el endpoint backend `/api/clientes` realmente recibe y procesa los `@RequestParam` o si se espera un filtrado en cliente (JS) como en Usuarios.

**Acción Requerida:**
1. Revisar `setupEventListeners` en `ClientesService.js`.
2. Verificar firma del método `listar` en `ClienteController.java`.

### Análisis E2-003 y E2-004 (UI/UX Clientes)

**E2-003 (Estilos Tabla):**
- **Striped Rows:** Agregar clase CSS o regla `:nth-child(even)` para fondo gris claro en las filas.
- **Estatus INACTIVO:** Definir clase CSS específica `.badge-danger` o similar con `color: red; background-color: yellow;` (o paleta de alerta similar).

**E2-004 (Interacción):**
- Agregar evento `onclick` al elemento `<tr>` en la función `renderTabla` de `ClientesService.js`.
- Asegurar que el evento propague o llame a `this.verDetalle(id)`.

### Análisis E2-005 (Eliminar Fusión)

**Alcance de Eliminación:**
1.  **UI:** Eliminar botón "Fusión" (`#btnFusionar`) de `clientes.html`.
2.  **Lógica JS:** Eliminar listener y función `fusionar()` de `clientes-service.js`.
3.  **Documentación:** Remover referencias a HU-M06-08, Endpoint `/api/clientes/fusionar` y Regla 6 en `ETAPA 2 - CLIENTES.md`.
4.  **Backend:** Si existiera código (placeholder), eliminar lógica asociada.




---
*Última actualización: 05/02/2026*
---

### Análisis E2-006 (Arquitectura Seguridad)

**Situación:**
Se detectó que cada servicio de módulo (`clientes-service`, `usuarios-service`) implementaba su propia lógica de validación de permisos y su propio "hack" para permitir el acceso al usuario `root` o roles `ADMIN`.

**Solución Implementada:**
*   Creación de **`auth-service.js`** (Singleton).
*   Centralización de lectura de sesión y reglas de `Admin Bypass`.
*   Refactorización de servicios para delegar la autorización a este nuevo componente.

### Análisis E2-007 (Fallo Key Session)

**Situación:**
Al implementar `auth-service.js`, se definió `sessionStorage.getItem('usuario_erp')` como estándar, pero el módulo de Login existente (`menu-service.js` y `login.js`) utilizaba la clave `'user'`.
Esto provocaba que `AuthService` retornara `null`, forzando un logout inmediato al intentar navegar.

**Solución:**
*   Se unificó el criterio para usar **`'user'`** en `auth-service.js`, alineándolo con la implementación legacy del Login.

---
*Última actualización: 05/02/2026*
