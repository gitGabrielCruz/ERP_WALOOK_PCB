---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 1 - Módulo Menú Principal  
**VERSIÓN:** 1.0  
**FECHA:** 04 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 🏠 ETAPA 1 - MÓDULO MENÚ PRINCIPAL

## 1.2 INCISO 1.2 — MENÚ (Menu.svg)

**RF que cubre:** RF-04 Navegación base  
**Pantalla:** Menú principal posterior al login  

---

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales

- Navegación a los 12 módulos funcionales del sistema
- Control de acceso basado en permisos
- Manejo de módulos no implementados

#### 1.2 Casos de uso asociados

- **CU-04:** Acceso a menú inicial según rol
- **CU-05:** Navegación entre módulos

---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO

#### 2.1 Pertenencia

Menú Principal pertenece a **Etapa 1** ✅ CONFIRMADO

#### 2.2 RF que se cierran

- RF-04: Navegación base

---

### 🔴 PASO 3 — DESCOMPOSICIÓN DE Menu.svg

#### Elementos UI (12 Módulos Funcionales)

1. **LOGIN**
2. **INVENTARIO**
3. **AGENDA CITAS**
4. **PROVEEDORES**
5. **EXPEDIENTE PACIENTE**
6. **CLIENTES**
7. **VENTAS**
8. **ORDENES DE COMPRA**
9. **TALLER OT**
10. **RECEPCION Y DEVOLUCION**
11. **FACTURACION CFDI**
12. **USUARIOS, ROLES Y PERMISOS**

#### 1.3 Estándares Visuales (Navegación - Patrón WALOOK)

El Menú y el Header establecen la base de navegación para todo el sistema.

1.  **Header Global (App Bar):**
    *   Debe estar presente en TODAS las pantallas internas.
    *   Contiene: Logo/Marca (Izq), Datos de Sesión (Usuario/Rol) y Botón "Salir" (Der).
    *   Color: Blanco (`#FFFFFF`) con sombra sutil (`box-shadow`).

2.  **Grid de Módulos (Menu Principal):**
    *   Diseño responsive (Grid flexible).
    *   Tarjetas de módulo (`module-card`):
        *   Icono central grande (Material Symbols).
        *   Título claro debajo del icono.
        *   Efecto Hover: Elevación y cambio de color sutil.


#### Regla general de navegación

- Cada opción intenta cargar un módulo cuyo nombre corresponde al nombre del botón
- **IF** módulo NO existe **THEN** mostrar mensaje "El módulo/archivo XXXX no existe" y retornar al Menú principal tras confirmación

---

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 1.2

#### Reglas cerradas

1. El menú solo se renderiza con sesión activa
2. El contenido del menú depende del rol asociado
3. Las opciones sin permiso no se muestran (o aparecen deshabilitadas según diseño)

#### Flujos if/then

```pseudocode
AL CARGAR MENÚ:
    IF sesión NO activa THEN
        Redirigir a Login
        BREAK
    ENDIF
    
    Obtener rol del usuario
    Obtener permisos del rol
    
    PARA CADA módulo EN lista_modulos:
        IF usuario tiene permiso "puede_ver" THEN
            Mostrar opción de menú
        ELSE
            Ocultar opción de menú
        ENDIF
    FIN PARA

AL HACER CLIC EN MÓDULO:
    IF módulo existe THEN
        Cargar módulo
    ELSE
        Mostrar alerta "El módulo XXXX no existe"
        Permanecer en menú
    ENDIF
```

#### Validaciones específicas

**Frontend:**
- Sesión activa requerida
- Permisos cargados correctamente
- Módulos habilitados según permisos

**Backend:**
- Validación de sesión en cada request
- Verificación de permisos antes de cargar módulo
- Retorno de lista de módulos permitidos

#### Endpoints API REST

```
GET    /api/menu/modulos        # Obtener módulos permitidos para el usuario
GET    /api/menu/permisos       # Obtener permisos del usuario actual
```

#### Criterios de Aceptación

- [x] El menú solo se muestra con sesión activa
- [x] El menú muestra solo los módulos con permiso "puede_ver"
- [x] El clic en un módulo carga la pantalla correspondiente
- [x] El sistema maneja módulos no implementados con mensaje claro
- [x] El usuario puede cerrar sesión desde el menú
- [x] El menú es responsive y funciona en dispositivos móviles

---

**Última actualización:** 04 de febrero de 2026, 03:27 AM  
**Estado:** Documentación completa  
**Código fuente:** Ver `ETAPA 1 - CODIGO_FUENTE.md`
