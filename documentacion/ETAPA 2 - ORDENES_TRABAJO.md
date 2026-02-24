---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Órdenes de Trabajo (Parte 1)  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 🔧 ETAPA 2 - MÓDULO ÓRDENES DE TRABAJO


## 2.4 INCISO 2.4 — MÓDULO ÓRDENES DE TRABAJO (Taller_OT.svg)

---

## INFORMACIÓN DEL MÓDULO

**Módulo:** Órdenes de Trabajo / Taller (M03)  
**Prioridad:** Alta  
**Complejidad:** Alta  
**Peso en Etapa 2:** 35% (7% del proyecto total)  
**RF Asociado:** RF-10 (Gestión de Órdenes de Trabajo)  

---

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales (Historias de Usuario)

El módulo de Órdenes de Trabajo implementa las siguientes Historias de Usuario del **Módulo M03** definidas en el Análisis General:

| ID HU | Título | Descripción | Prioridad |
|-------|--------|-------------|-----------|
| **HU-M03-01** | Crear OT desde venta | Como vendedor quiero generar una OT desde una venta para iniciar producción | Alta |
| **HU-M03-02** | Consultar OT | Como taller quiero buscar OT por folio o paciente para trabajar en ellas | Alta |
| **HU-M03-03** | Actualizar estado OT | Como taller quiero cambiar el estado de la OT para reflejar el avance | Alta |
| **HU-M03-04** | Reservar insumos | Como taller quiero reservar insumos para asegurar disponibilidad | Alta |
| **HU-M03-05** | Control de calidad | Como supervisor quiero registrar checklist de calidad para garantizar estándares | Alta |
| **HU-M03-06** | Historial de estados | Como administrador quiero ver el historial de cambios para auditar tiempos | Media |
| **HU-M03-07** | Reproceso | Como taller quiero enviar a reproceso cuando hay defectos | Media |
| **HU-M03-08** | Entregar OT | Como recepción quiero marcar OT como entregada para cerrar el ciclo | Alta |

**Alcance de la Etapa 2:**
- ✅ HU-M03-01: Crear OT desde venta (Generación automática)
- ✅ HU-M03-02: Consultar OT (Búsqueda y filtros por estado)
- ✅ HU-M03-03: Actualizar estado OT (Flujo de estados completo)
- ✅ HU-M03-04: Reservar insumos (Gestión de inventario)
- ✅ HU-M03-05: Control de calidad (Checklist configurable)
- ✅ HU-M03-06: Historial de estados (Trazabilidad completa)
- ✅ HU-M03-07: Reproceso (Devolución a taller)
- ✅ HU-M03-08: Entregar OT (Cierre de orden)

#### 1.2 Requerimientos no funcionales reales

| ID RNF | Nombre | Descripción | Aplicación en OT |
|--------|--------|-------------|------------------|
| **RNF-02** | Seguridad básica | Control de acceso por permisos | Sistema de permisos por rol (Taller, Supervisor, Recepción) |
| **RNF-03** | Integración frontend-backend | API REST | Endpoints REST completos para CRUD y cambios de estado |
| **RNF-04** | Consistencia visual | Diseño uniforme | Patrón Master-Detail con secciones especializadas |
| **RNF-05** | Integridad de datos | Validaciones y constraints | Estados válidos, reserva de insumos, validación de stock |
| **RNF-06** | Validaciones operativas | Reglas de negocio | Flujo de estados, control de calidad obligatorio |
| **RNF-07** | Trazabilidad | Auditoría de cambios | Historial completo de estados con timestamp y usuario |

#### 1.3 Casos de uso reales asociados

- **CU-12:** Gestionar órdenes de trabajo
- **CU-13:** Controlar flujo de producción
- **CU-14:** Reservar y liberar insumos
- **CU-15:** Registrar control de calidad
- **CU-16:** Gestionar reprocesos

#### 1.4 Diagramas que aplican

- **Diagrama de Navegabilidad:** Módulo Taller/OT (Anexo E, Figura 10)
- **Diagrama Entidad-Relación:** Tablas `orden_trabajo`, `insumo_ot`, `control_calidad` (Anexo D, Figura 7)
- **Diagrama de Estados:** Flujo de estados de OT (Anexo F, Figura 12)
- **Modelo Lógico 3FN:** Normalización de datos de OT (Anexo D, Figura 9)
- **Diagrama de Clases:** Clases `OrdenTrabajo`, `InsumoOT`, `ControlCalidad` (Anexo C)

#### 1.5 Datos literales del diseño (Pantalla Taller_OT.svg)

**Base de datos:** `optica_erp`

**Tablas principales:** `orden_trabajo`, `insumo_ot`, `control_calidad`, `historial_estado_ot`

---

### **PANTALLA: Taller / OT**

**Título:** Taller / OT  
**Subtítulo:** Gestión de Órdenes de Trabajo, insumos y control de calidad

#### **Campos identificados en filtros:**

| Campo UI | Tipo | Descripción | Mapeo BD |
|----------|------|-------------|----------|
| **Buscar OT** | Input text | Búsqueda por folio OT (OT-000...) | `orden_trabajo.id_ot` |
| **Estado** | Select | Filtro por estado de OT | `orden_trabajo.estado` |

**Opciones de Estado:**
- Todos
- Recibida
- EnProceso
- EnEsperaMaterial
- Reproceso
- Terminada
- Entregada

#### **Campos identificados en tabla maestra:**

| Columna | Tipo | Descripción | Mapeo BD |
|---------|------|-------------|----------|
| **OT** | String | OT-00023, OT-00022, etc. | `orden_trabajo.id_ot` (PK) |
| **Venta** | String | V-00234, V-00212, etc. | `orden_trabajo.id_venta` (FK) |
| **Paciente** | String | Juan Pérez, María Díaz, etc. | `venta.paciente` (JOIN) |
| **Estado** | Enum | EnProceso, Terminada, EnEsperaMaterial | `orden_trabajo.estado` |
| **Abrir** | Botón | Abre detalle de OT | - |

**Datos de ejemplo en tabla:**

```
OT-00023 | Venta: V-00234 | Paciente: Juan Pérez      | Estado: EnProceso
OT-00022 | Venta: V-00212 | Paciente: María Díaz      | Estado: Terminada
OT-00021 | Venta: V-00198 | Paciente: Luis Gómez      | Estado: EnEsperaMaterial
```

#### **Campos identificados en panel de detalle:**

**SECCIÓN: Detalle OT: OT-00023**

| Campo | Tipo | Descripción | Mapeo BD |
|-------|------|-------------|----------|
| **Venta** | Input text (readonly) | V-00234 | `orden_trabajo.id_venta` |
| **Paciente** | Input text (readonly) | Juan Pérez | JOIN con venta |
| **Estado** | Select | EnProceso | `orden_trabajo.estado` |
| **Especificaciones** | Textarea | "Armazón clásico, antirreflejante básico, PD = 63" | `orden_trabajo.especificaciones` |

**Botón:**
- **Guardar** (Azul)

---

#### **SECCIÓN: Insumos**

**Título:** Insumos

| Campo | Tipo | Descripción |
|-------|------|-------------|
| **Buscar insumo** | Input text | LENTE 150 (búsqueda de productos) |
| **Botón Buscar** | Button | Buscar producto en inventario |
| **Botón Agregar insumo** | Button (azul) | Agregar producto a la OT |

**Tabla de insumos:**

| Columna | Tipo | Descripción | Mapeo BD |
|---------|------|-------------|----------|
| **Producto** | String | Lente orgánico 1.50 2 2 | `insumo_ot.id_producto` (FK) |
| **Cant.** | Integer | Cantidad requerida | `insumo_ot.cantidad` |
| **Reservado** | Integer | Cantidad ya reservada | `insumo_ot.cantidad_reservada` |
| **Acciones** | Buttons | Liberar, Reservar | - |

**Datos de ejemplo:**

```
Producto                  | Cant. | Reservado | Acciones
Lente orgánico 1.50 2 2  | 2     | 2         | [Liberar]
Armazón clásico Negro 1 1| 1     | 1         | [Liberar]
Paño microfibra 1 0      | 1     | 0         | [Reservar]
```

**Botones de acción:**
- **Reservar faltantes** (Reserva todos los insumos pendientes)

---

#### **SECCIÓN: Control de calidad**

**Título:** Control de calidad

**Checklist:**

| Item | Tipo | Descripción | Mapeo BD |
|------|------|-------------|----------|
| ☑ Medida correcta | Checkbox | Verificación de medidas | `control_calidad.medida_correcta` |
| ☐ Alineación y centrado | Checkbox | Verificación de alineación | `control_calidad.alineacion_centrado` |
| ☐ Tornillos ajustados | Checkbox | Verificación de tornillos | `control_calidad.tornillos_ajustados` |
| ☐ Limpieza final | Checkbox | Verificación de limpieza | `control_calidad.limpieza_final` |

**Campos adicionales:**

| Campo | Tipo | Descripción | Mapeo BD |
|-------|------|-------------|----------|
| **Observaciones** | Textarea | "Ajustar puente levemente" | `control_calidad.observaciones` |

**Botones:**
- **Guardar control de calidad** (Azul)
- **Agregar control** (Link)

---

#### **SECCIÓN: Historial de estado**

**Título:** Historial de estado

**Tabla de historial:**

| Columna | Tipo | Descripción | Mapeo BD |
|---------|------|-------------|----------|
| **Fecha y hora** | Timestamp | 2025-09-20 10:12 | `historial_estado_ot.fecha_cambio` |
| **Estado** | String | Recibida, EnProceso, etc. | `historial_estado_ot.estado` |
| **Observaciones** | String | (Lente 1.50) | `historial_estado_ot.observaciones` |

**Datos de ejemplo:**

```
2025-09-20 10:12 - Recibida
2025-09-21 09:05 - EnProceso
2025-09-21 12:32 - EnEsperaMaterial (Lente 1.50)
2025-09-22 08:15 - EnProceso
```

---

#### **Botones identificados (ACCIONES PRINCIPALES):**

| Botón | Color | Descripción |
|-------|-------|-------------|
| **+ Desde venta** | Azul | Crear nueva OT desde una venta |
| **Volver** | Gris | Regresar a lista |
| **Guardar** | Azul | Guardar cambios en detalle |
| **Buscar** | Azul | Buscar insumo |
| **Agregar insumo** | Azul | Agregar producto a OT |
| **Liberar** | Gris | Liberar insumo reservado |
| **Reservar** | Gris | Reservar insumo |
| **Reservar faltantes** | Azul | Reservar todos los faltantes |
| **Guardar control de calidad** | Azul | Guardar checklist |
| **Agregar control** | Link | Agregar nuevo control |
| **Terminar OT** | Azul | Marcar OT como terminada |
| **Enviar a reproceso** | Gris | Devolver a taller |
| **Entregar** | Azul | Marcar como entregada |
| **Filtrar** | Azul | Aplicar filtros |

---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO POR ETAPAS

#### 2.1 Pertenencia a Etapa 2

**Confirmación:** El módulo Órdenes de Trabajo pertenece a **Etapa 2: Gestión Operativa Principal** ✅

**Justificación:**
- Forma parte del RF-10 (Gestión de Órdenes de Trabajo)
- Representa el 7% del proyecto total (35% de la Etapa 2)
- Es el módulo más complejo de la Etapa 2
- Integra Ventas, Inventarios y Control de Calidad

#### 2.2 RF que se cierran en 2.4

| RF | Descripción | Estado |
|----|-------------|--------|
| **RF-10** | Gestión de Órdenes de Trabajo | ✅ Se cierra completamente |
| **RF-05** | Comunicación API | ✅ Endpoints `/api/ordenes-trabajo` |
| **RF-06** | Persistencia básica | ✅ CRUD completo + Historial |
| **RF-11** | Control de calidad | ✅ Checklist configurable |

#### 2.3 Qué NO se puede tocar (Reservado a etapas futuras)

| Funcionalidad | Etapa Reservada | Motivo |
|---------------|-----------------|--------|
| Facturación de OT | Etapa 3 | Módulo de Facturación |
| Reportes de producción | Etapa 4 | Módulo de Reportes |
| Análisis de tiempos | Etapa 4 | Business Intelligence |
| Notificaciones automáticas | Etapa 5 | Sistema de notificaciones |
| Integración con laboratorio externo | Etapa 6 | Integraciones externas |

---

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL (UI → LÓGICA → DATOS)

#### 3.1 Elementos de la interfaz

**SECCIÓN SUPERIOR: Filtros de Búsqueda**

| Elemento | Tipo | Función | Evento | Acción Backend |
|----------|------|---------|--------|----------------|
| Campo "Buscar OT" | `<input type="text">` | Búsqueda por folio OT | `keyup` (debounce 300ms) | `GET /api/ordenes-trabajo?buscar={folio}` |
| Selector "Estado" | `<select>` | Filtro por estado | `change` | `GET /api/ordenes-trabajo?estado={estado}` |
| Botón "Filtrar" | `<button>` | Aplicar filtros | `click` | Ejecutar búsqueda |

**SECCIÓN MEDIA: Tabla Maestra (Listado de OT)**

| Columna | Tipo Dato | Ordenable | Acción |
|---------|-----------|-----------|--------|
| OT | String | ✅ | - |
| Venta | String | ✅ | - |
| Paciente | String | ✅ | - |
| Estado | Enum | ✅ | Indicador visual por color |
| Abrir | Botón | ❌ | Click → Cargar detalle |

**SECCIÓN INFERIOR: Panel de Detalle**

**SUBSECCIÓN: Detalle OT**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| Venta | `<input readonly>` | - | FK válida | `orden_trabajo.id_venta` |
| Paciente | `<input readonly>` | - | JOIN | Calculado |
| Estado | `<select>` | Estado válido | ENUM válido | `orden_trabajo.estado` |
| Especificaciones | `<textarea>` | Max 1000 chars | - | `orden_trabajo.especificaciones` |

**SUBSECCIÓN: Insumos**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| Buscar insumo | `<input type="text">` | - | - | Búsqueda en productos |
| Producto | `<select>` | Producto debe existir | FK válida | `insumo_ot.id_producto` |
| Cantidad | `<input type="number">` | > 0 | > 0 | `insumo_ot.cantidad` |
| Cantidad reservada | Display | - | Calculado | `insumo_ot.cantidad_reservada` |

**SUBSECCIÓN: Control de Calidad**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| Medida correcta | `<input type="checkbox">` | - | - | `control_calidad.medida_correcta` |
| Alineación y centrado | `<input type="checkbox">` | - | - | `control_calidad.alineacion_centrado` |
| Tornillos ajustados | `<input type="checkbox">` | - | - | `control_calidad.tornillos_ajustados` |
| Limpieza final | `<input type="checkbox">` | - | - | `control_calidad.limpieza_final` |
| Observaciones | `<textarea>` | Max 500 chars | - | `control_calidad.observaciones` |

**SUBSECCIÓN: Historial de Estado**

| Columna | Tipo Dato | Ordenable | Formato |
|---------|-----------|-----------|---------|
| Fecha y hora | Timestamp | ✅ | YYYY-MM-DD HH:MM |
| Estado | String | ❌ | Texto |
| Observaciones | String | ❌ | Texto |

**Botones de Acción:**

| Botón | Clase CSS | Permiso Requerido | Acción | Endpoint |
|-------|-----------|-------------------|--------|----------|
| + Desde venta | `.btn-primary` | `puede_crear` | Crear OT desde venta | `POST /api/ordenes-trabajo/desde-venta` |
| Volver | `.btn-secondary` | `puede_ver` | Regresar a lista | - |
| Guardar | `.btn-primary` | `puede_editar` | Guardar cambios | `PUT /api/ordenes-trabajo/{id}` |
| Agregar insumo | `.btn-primary` | `puede_editar` | Agregar producto a OT | `POST /api/ordenes-trabajo/{id}/insumos` |
| Liberar | `.btn-secondary` | `puede_editar` | Liberar insumo reservado | `PUT /api/ordenes-trabajo/{id}/insumos/{id}/liberar` |
| Reservar | `.btn-secondary` | `puede_editar` | Reservar insumo | `PUT /api/ordenes-trabajo/{id}/insumos/{id}/reservar` |
| Reservar faltantes | `.btn-primary` | `puede_editar` | Reservar todos los faltantes | `POST /api/ordenes-trabajo/{id}/reservar-faltantes` |
| Guardar control de calidad | `.btn-primary` | `puede_editar` | Guardar checklist | `POST /api/ordenes-trabajo/{id}/control-calidad` |
| Terminar OT | `.btn-primary` | `puede_editar` | Cambiar estado a Terminada | `PUT /api/ordenes-trabajo/{id}/terminar` |
| Enviar a reproceso | `.btn-secondary` | `puede_editar` | Cambiar estado a Reproceso | `PUT /api/ordenes-trabajo/{id}/reproceso` |
| Entregar | `.btn-primary` | `puede_editar` | Cambiar estado a Entregada | `PUT /api/ordenes-trabajo/{id}/entregar` |

#### 3.2 Trazabilidad técnica por elemento

**Flujo de Datos: Crear OT desde Venta**
```
Usuario hace clic en "+ Desde venta"
    ↓
Verificar permiso puede_crear
    ↓
Abrir modal de selección de venta
    ↓
Usuario selecciona venta (V-00234)
    ↓
Sistema obtiene datos de venta (paciente, productos)
    ↓
Usuario hace clic en "Crear OT"
    ↓
POST /api/ordenes-trabajo/desde-venta + Body JSON
    ↓
Backend: OrdenTrabajoController.createFromVenta()
    ↓
Generar ID OT (OT-XXXXX)
    ↓
Estado inicial: "Recibida"
    ↓
Copiar especificaciones de venta
    ↓
Crear insumos desde productos de venta
    ↓
Registrar en historial: "Recibida"
    ↓
OrdenTrabajoService.crear()
    ↓
OrdenTrabajoRepository.save()
    ↓
Retorna 201 Created + OT creada
    ↓
Frontend: recargar tabla + toast éxito
```

**Flujo de Datos: Reservar Insumo**
```
Usuario hace clic en "Reservar" (insumo específico)
    ↓
Verificar permiso puede_editar
    ↓
Obtener cantidad requerida
    ↓
Verificar stock disponible en inventario
    ↓
SI stock_disponible >= cantidad_requerida THEN
    PUT /api/ordenes-trabajo/{id}/insumos/{id}/reservar
    ↓
    Backend: InsumoOTController.reservar()
    ↓
    BEGIN TRANSACTION
        UPDATE insumo_ot SET cantidad_reservada = cantidad
        UPDATE producto SET stock_reservado = stock_reservado + cantidad
    COMMIT TRANSACTION
    ↓
    Retorna 200 OK
    ↓
    Frontend: actualizar tabla + toast "Insumo reservado"
ELSE
    Mostrar error "Stock insuficiente. Disponible: {stock_disponible}"
ENDIF
```

**Flujo de Datos: Cambiar Estado a "Terminada"**
```
Usuario hace clic en "Terminar OT"
    ↓
Verificar permiso puede_editar
    ↓
Validar estado actual == "EnProceso"
    ↓
Validar control de calidad completo (todos los checks marcados)
    ↓
SI control_calidad incompleto THEN
    Mostrar error "Debe completar el control de calidad"
    BREAK
ENDIF
    ↓
PUT /api/ordenes-trabajo/{id}/terminar
    ↓
Backend: OrdenTrabajoController.terminar()
    ↓
BEGIN TRANSACTION
    UPDATE orden_trabajo SET estado = 'TERMINADA', fecha_terminacion = NOW()
    INSERT INTO historial_estado_ot (id_ot, estado, fecha_cambio, usuario)
COMMIT TRANSACTION
    ↓
Retorna 200 OK
    ↓
Frontend: actualizar estado + toast "OT terminada"
```

#### 3.3 Mapeo a base de datos

**Tabla: `orden_trabajo`**

```sql
CREATE TABLE orden_trabajo (
    -- Identificación
    id_ot               VARCHAR(20) PRIMARY KEY,           -- OT-00001, OT-00002, etc.
    
    -- Relación con venta
    id_venta            VARCHAR(20) NOT NULL,              -- FK a venta
    
    -- Estado
    estado              ENUM('RECIBIDA', 'EN_PROCESO', 'EN_ESPERA_MATERIAL', 'REPROCESO', 'TERMINADA', 'ENTREGADA') DEFAULT 'RECIBIDA',
    
    -- Especificaciones
    especificaciones    TEXT,                              -- Detalles técnicos
    
    -- Fechas
    fecha_recepcion     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio        TIMESTAMP,
    fecha_terminacion   TIMESTAMP,
    fecha_entrega       TIMESTAMP,
    
    -- Auditoría
    usuario_creacion    VARCHAR(50),
    usuario_terminacion VARCHAR(50),
    usuario_entrega     VARCHAR(50),
    
    -- Relaciones
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    
    -- Índices
    INDEX idx_venta (id_venta),
    INDEX idx_estado (estado),
    INDEX idx_fecha_recepcion (fecha_recepcion)
);
```

**Tabla: `insumo_ot`**

```sql
CREATE TABLE insumo_ot (
    -- Identificación
    id_insumo_ot        INT AUTO_INCREMENT PRIMARY KEY,
    id_ot               VARCHAR(20) NOT NULL,
    
    -- Producto
    id_producto         VARCHAR(20) NOT NULL,              -- FK a producto (SKU)
    
    -- Cantidades
    cantidad            INT NOT NULL,
    cantidad_reservada  INT DEFAULT 0,
    
    -- Auditoría
    fecha_agregado      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_agregado    VARCHAR(50),
    
    -- Relaciones
    FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
    FOREIGN KEY (id_producto) REFERENCES producto(sku),
    
    -- Índices
    INDEX idx_ot (id_ot),
    INDEX idx_producto (id_producto),
    
    -- Constraints
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_reservado_valido CHECK (cantidad_reservada >= 0 AND cantidad_reservada <= cantidad)
);
```

**Tabla: `control_calidad`**

```sql
CREATE TABLE control_calidad (
    -- Identificación
    id_control          INT AUTO_INCREMENT PRIMARY KEY,
    id_ot               VARCHAR(20) NOT NULL,
    
    -- Checklist
    medida_correcta     BOOLEAN DEFAULT FALSE,
    alineacion_centrado BOOLEAN DEFAULT FALSE,
    tornillos_ajustados BOOLEAN DEFAULT FALSE,
    limpieza_final      BOOLEAN DEFAULT FALSE,
    
    -- Observaciones
    observaciones       TEXT,
    
    -- Auditoría
    fecha_control       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_control     VARCHAR(50),
    
    -- Relaciones
    FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
    
    -- Índices
    INDEX idx_ot (id_ot),
    INDEX idx_fecha (fecha_control)
);
```

**Tabla: `historial_estado_ot`**

```sql
CREATE TABLE historial_estado_ot (
    -- Identificación
    id_historial        INT AUTO_INCREMENT PRIMARY KEY,
    id_ot               VARCHAR(20) NOT NULL,
    
    -- Estado
    estado              ENUM('RECIBIDA', 'EN_PROCESO', 'EN_ESPERA_MATERIAL', 'REPROCESO', 'TERMINADA', 'ENTREGADA') NOT NULL,
    
    -- Detalles
    observaciones       VARCHAR(500),
    
    -- Auditoría
    fecha_cambio        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario             VARCHAR(50),
    
    -- Relaciones
    FOREIGN KEY (id_ot) REFERENCES orden_trabajo(id_ot),
    
    -- Índices
    INDEX idx_ot (id_ot),
    INDEX idx_fecha (fecha_cambio),
    INDEX idx_estado (estado)
);
```

**Relaciones:**
- `orden_trabajo.id_venta` → `venta.id_venta` (FK)
- `insumo_ot.id_ot` → `orden_trabajo.id_ot` (FK)
- `insumo_ot.id_producto` → `producto.sku` (FK)
- `control_calidad.id_ot` → `orden_trabajo.id_ot` (FK)
- `historial_estado_ot.id_ot` → `orden_trabajo.id_ot` (FK)

---

**Continúa en siguiente mensaje con PASO 4...**

---

**Última actualización:** 04 de febrero de 2026, 03:08 AM  
**Estado:** Documentación en progreso (Pasos 1-3 completos)  
**Siguiente:** Paso 4 - Contenido Operativo
