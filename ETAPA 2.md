---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Gestión Operativa Principal (Índice Maestro)  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 📊 ETAPA 2: Gestión Operativa Principal del Sistema


## ÍNDICE MAESTRO DE DOCUMENTACIÓN

---

## INFORMACIÓN DEL PROYECTO

**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC  
**Empresa:** WALOOK MEXICO, S.A. de C.V.  
**Autor:** Gabriel Amílcar Cruz Canto  
**Matrícula:** ES1821003109  
**Programa:** Licenciatura en Ingeniería en Desarrollo de Software  
**Unidad Didáctica:** Proyecto Terminal I / Proyecto Terminal II  
**Periodo Académico:** PT1 – PT2 (Agosto 2025 – Enero 2026)  
**Grupo:** DS-DPT1-2502-B0-002  

**Versión del Documento:** v1.0  
**Fecha de Creación:** 04 de febrero de 2026  
**Estado:** En Desarrollo  

---

## INTRODUCCIÓN

La **Etapa 2** del Sistema ERP en la nube para gestión de ópticas OMCGC representa un avance crítico en el desarrollo del proyecto, alcanzando el **50% de completitud total** del sistema. Esta etapa se enfoca en la implementación de los **módulos operativos principales** que constituyen el núcleo funcional del negocio de las ópticas.

### Contexto y Antecedentes

Habiendo completado exitosamente la **Etapa 0** (Preparación técnica del sistema) y la **Etapa 1** (Base funcional del sistema), el proyecto cuenta ahora con una infraestructura sólida que incluye:

- ✅ Sistema de autenticación y gestión de sesiones
- ✅ Control de acceso basado en roles y permisos granulares
- ✅ Navegación principal con 12 módulos funcionales
- ✅ Gestión completa de usuarios, roles y permisos
- ✅ Arquitectura multicapa estable y escalable
- ✅ Base de datos normalizada y operativa

Sobre esta base técnica y funcional, la **Etapa 2** construye los módulos que permiten la operación diaria del negocio, integrando procesos críticos de gestión comercial y operativa.

### Objetivo de la Etapa 2

Implementar los **cuatro módulos operativos principales** del sistema ERP, proporcionando funcionalidades completas de CRUD (Create, Read, Update, Delete) para:

1. **Clientes** — Gestión integral de la base de clientes, datos fiscales y consentimientos
2. **Proveedores** — Administración de proveedores, órdenes de compra y recepción de mercancía
3. **Inventarios** — Control de productos, existencias, movimientos tipo kardex y datos fiscales CFDI
4. **Órdenes de Trabajo (Taller)** — Gestión del flujo de producción en taller óptico

Estos módulos representan el **20% del proyecto** y son fundamentales para alcanzar el **50% acumulado**, cumpliendo así con el objetivo de la primera entrega académica.

### Alcance Funcional

La Etapa 2 cubre los siguientes **Requerimientos Funcionales (RF)** y **Requerimientos No Funcionales (RNF)**:

#### Requerimientos Funcionales (RF)
- **RF-07:** Gestión de Clientes
- **RF-08:** Gestión de Proveedores
- **RF-09:** Gestión de Inventarios
- **RF-10:** Gestión de Órdenes de Trabajo

#### Requerimientos No Funcionales (RNF)
- **RNF-05:** Integridad de datos
- **RNF-06:** Validaciones operativas

Además, se mantiene la aplicación de los RNF establecidos en etapas anteriores:
- **RNF-02:** Seguridad básica (Control de acceso por permisos)
- **RNF-03:** Integración frontend-backend (API REST)
- **RNF-04:** Consistencia visual (Diseño uniforme)

---

## 📑 ESTRUCTURA DE DOCUMENTACIÓN

La documentación de la Etapa 2 está organizada en **archivos modulares** para facilitar la navegación y mantenimiento:

### **DOCUMENTOS PRINCIPALES**

| Archivo | Descripción | Estado | Líneas |
|---------|-------------|--------|--------|
| **ETAPA 2 - INDICE.md** | Este documento (Índice maestro) | ✅ Completo | ~300 |
| **ETAPA 2 - CLIENTES.md** | Documentación completa del Módulo Clientes | ✅ Completo | ~720 |
| **ETAPA 2 - PROVEEDORES.md** | Documentación completa del Módulo Proveedores | ✅ Completo | ~866 |
| **ETAPA 2 - INVENTARIOS.md** | Documentación completa del Módulo Inventarios (Parte 1) | ✅ Completo | ~650 |
| **ETAPA 2 - INVENTARIOS PARTE 2.md** | Documentación completa del Módulo Inventarios (Parte 2) | ✅ Completo | ~550 |
| **ETAPA 2 - ORDENES_TRABAJO.md** | Documentación completa del Módulo Órdenes de Trabajo | ⏳ Pendiente | ~800 (est.) |

### **DOCUMENTOS COMPLEMENTARIOS**

| Archivo | Descripción | Estado |
|---------|-------------|--------|
| **ETAPA 2 - ESPECIFICACIONES_TECNICAS.md** | Instrucciones para IA, mapa estructural, modelado de datos | ⏳ Pendiente |
| **ETAPA 2 - PSEUDOCODIGO.md** | Pseudocódigo detallado de todos los módulos | ⏳ Pendiente |
| **ETAPA 2 - CODIGO_FUENTE.md** | Código completo (Frontend + Backend + SQL) | ⏳ Pendiente |

---

## 📋 CONTENIDO POR MÓDULO

### **2.1 MÓDULO CLIENTES**

**Archivo:** `ETAPA 2 - CLIENTES.md`

**Contenido:**
- ✅ Paso 1 — Extracción Literal del Diseño
  - 8 Historias de Usuario (HU-M06-01 a HU-M06-08)
  - 5 Requerimientos No Funcionales
  - 3 Casos de Uso
  - Mapeo completo de campos UI → BD
  
- ✅ Paso 2 — Cruce con Plan de Desarrollo
  - Confirmación de pertenencia a Etapa 2
  - RF que se cierran (RF-07, RF-05, RF-06)
  - Funcionalidades reservadas a etapas futuras
  
- ✅ Paso 3 — Descomposición UI → Lógica → Datos
  - Tabla completa de elementos UI con eventos y endpoints
  - 3 Diagramas de flujo de datos
  - Script SQL completo de la tabla `cliente`
  
- ✅ Paso 4 — Contenido Operativo
  - Matriz de Control de Permisos (16 elementos UI)
  - 6 Reglas de Seguridad en pseudocódigo
  - 6 Flujos if/then (Reglas de Negocio)
  - Validaciones Frontend y Backend
  - 8 Endpoints API REST documentados
  - 13 Criterios de Aceptación

**Estadísticas:**
- Líneas: ~720
- Tablas: 10
- Bloques de código: 8
- Diagramas: 3

---

### **2.2 MÓDULO PROVEEDORES**

**Archivo:** `ETAPA 2 - PROVEEDORES.md`

**Contenido:**
- ✅ Paso 1 — Extracción Literal del Diseño
  - 8 Historias de Usuario (HU-M05-01 a HU-M05-08)
  - 5 Requerimientos No Funcionales
  - 3 Casos de Uso
  - Mapeo completo de 3 tablas (proveedor, orden_compra, detalle_oc)
  
- ✅ Paso 2 — Cruce con Plan de Desarrollo
  - Confirmación de pertenencia a Etapa 2
  - RF que se cierran (RF-08, RF-05, RF-06)
  - Funcionalidades reservadas (Aprobación OC, Recepción, Devoluciones)
  
- ✅ Paso 3 — Descomposición UI → Lógica → Datos
  - Tabla completa de elementos UI
  - 2 Diagramas de flujo (Crear Proveedor, Crear OC)
  - 3 Scripts SQL completos
  
- ✅ Paso 4 — Contenido Operativo
  - Matriz de Control de Permisos (18 elementos UI)
  - 8 Reglas de Seguridad en pseudocódigo
  - 6 Flujos if/then (incluyendo gestión de OC)
  - Validaciones Frontend y Backend (con validación de OC)
  - 9 Endpoints API REST documentados
  - 17 Criterios de Aceptación

**Estadísticas:**
- Líneas: ~866
- Tablas BD: 3 (proveedor, orden_compra, detalle_oc)
- Endpoints: 9
- Diagramas: 2

---

### **2.3 MÓDULO INVENTARIOS**

**Archivo:** `ETAPA 2 - INVENTARIOS.md`

**Contenido (Planificado):**
- 🚧 Paso 1 — Extracción Literal del Diseño
  - 9 Historias de Usuario (HU-M01-01 a HU-M01-09)
  - Incluye datos fiscales CFDI completos
  - 2 Pantallas (CATÁLOGO y CONTROL)
  - Mapeo de tablas: producto, movimiento_inventario
  
- 🚧 Paso 2 — Cruce con Plan de Desarrollo
  - RF-09 (Gestión de Inventarios)
  - Integración con Proveedores (OC)
  
- 🚧 Paso 3 — Descomposición UI → Lógica → Datos
  - Tab CATÁLOGO: CRUD de productos con datos fiscales
  - Tab CONTROL: Sistema kardex completo
  - Scripts SQL: producto, movimiento_inventario, vista stock_actual
  
- 🚧 Paso 4 — Contenido Operativo
  - Matriz de permisos para ambos tabs
  - Reglas de cálculo de stock
  - Validaciones de claves SAT
  - Endpoints para productos y movimientos
  - Cálculo automático de precio venta

**Características Especiales:**
- ✅ Datos fiscales CFDI (ClaveProdServ, ClaveUnidad, Objeto de impuesto, IVA)
- ✅ Tipo de inventario (Stock en tienda, Disponible a pedido, Servicio)
- ✅ Sistema kardex con ID automático y folio capturable
- ✅ Alertas de stock bajo
- ✅ Cálculo de utilidad y precio venta

**Estadísticas Estimadas:**
- Líneas: ~900
- Tablas BD: 2 + 1 vista
- Endpoints: 12
- Tabs: 2 (CATÁLOGO, CONTROL)

---

### **2.4 MÓDULO ÓRDENES DE TRABAJO**

**Archivo:** `ETAPA 2 - ORDENES_TRABAJO.md`

**Contenido (Pendiente):**
- ⏳ Paso 1 — Extracción Literal del Diseño
- ⏳ Paso 2 — Cruce con Plan de Desarrollo
- ⏳ Paso 3 — Descomposición UI → Lógica → Datos
- ⏳ Paso 4 — Contenido Operativo

**Estadísticas Estimadas:**
- Líneas: ~800
- Tablas BD: 2-3
- Endpoints: 10

---

## 📊 RESUMEN EJECUTIVO

### Progreso General de la Etapa 2

| Módulo | Estado | Documentación | Código | Pruebas |
|--------|--------|---------------|--------|---------|
| **Clientes** | ✅ Completo | 100% | ✅ 100% | ⏳ Pendiente |
| **Proveedores** | ✅ Completo | 100% | ✅ 100% | ⏳ Pendiente |
| **Inventarios** | 🚧 En desarrollo | 10% | 🚧 20% | ⏳ Pendiente |
| **Órdenes de Trabajo** | ⏳ Pendiente | 0% | ⏳ Pendiente | ⏳ Pendiente |

**Progreso Total:** 52.5% de documentación completada

### Estadísticas Acumuladas

| Métrica | Valor |
|---------|-------|
| **Líneas de documentación** | ~1,586 (completadas) + ~1,700 (estimadas) = ~3,286 |
| **Tablas de base de datos** | 6 (cliente, proveedor, orden_compra, detalle_oc, producto, movimiento_inventario) |
| **Endpoints API REST** | 17 documentados + 12 estimados = 29 |
| **Historias de Usuario** | 25 (8 Clientes + 8 Proveedores + 9 Inventarios) |
| **Criterios de Aceptación** | 30 (13 + 17) |

---

## 🎯 PRÓXIMOS PASOS

1. ✅ **Completar documentación de Inventarios** (ETAPA 2 - INVENTARIOS.md)
2. ⏳ **Documentar Órdenes de Trabajo** (ETAPA 2 - ORDENES_TRABAJO.md)
3. ⏳ **Crear Especificaciones Técnicas** (ETAPA 2 - ESPECIFICACIONES_TECNICAS.md)
4. ⏳ **Generar Pseudocódigo Completo** (ETAPA 2 - PSEUDOCODIGO.md)
5. ⏳ **Implementar Código Fuente** (ETAPA 2 - CODIGO_FUENTE.md)
6. ⏳ **Realizar Pruebas Integrales**
7. ⏳ **Consolidar Documento Final**

---

## 📝 NOTAS IMPORTANTES

### Metodología de 4 Pasos

Cada módulo se documenta siguiendo **4 pasos rigurosos**:

1. **PASO 1 — Extracción Literal:** Identificación de todos los elementos del diseño
2. **PASO 2 — Cruce con Plan:** Validación de pertenencia y alcance
3. **PASO 3 — Descomposición:** Mapeo UI → Lógica → Datos
4. **PASO 4 — Contenido Operativo:** Permisos, reglas, validaciones, endpoints

### Consistencia entre Módulos

Todos los módulos mantienen:
- ✅ Patrón Master-Detail
- ✅ Sistema de permisos granulares (Ver, Crear, Editar, Eliminar)
- ✅ Validaciones en dos capas (Frontend + Backend)
- ✅ Soft delete (no eliminación física)
- ✅ Auditoría completa (usuario, fecha)
- ✅ API REST con nomenclatura consistente

---

**Última actualización:** 04 de febrero de 2026, 02:40 AM  
**Responsable:** Gabriel Amílcar Cruz Canto  
**Estado del proyecto:** 50% completado (Etapa 0 + Etapa 1 + Etapa 2 en desarrollo)
