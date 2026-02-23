---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Proveedores  
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Proveedores  
**VERSIÓN:** 1.1  
**FECHA:** 22 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto / Antigravity AI  

---

# 🏢 ETAPA 2 - MÓDULO PROVEEDORES

## 2.2 INCISO 2.2 — MÓDULO PROVEEDORES (Proveedores.svg)

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales (Historias de Usuario)

El módulo de Proveedores implementa las siguientes Historias de Usuario del **Módulo M05** definidas en el Análisis General:

| ID HU | Título | Descripción | Prioridad |
|-------|--------|-------------|-----------|
| **HU-M05-01** | Registrar proveedor | Como compras quiero dar de alta un proveedor para gestionar órdenes de compra | Alta |
| **HU-M05-02** | Consultar proveedor | Como compras quiero buscar proveedores por RFC o razón social para acceder a su información | Alta |
| **HU-M05-03** | Editar proveedor | Como administrador quiero actualizar datos del proveedor para mantener información vigente | Alta |
| **HU-M05-04** | Desactivar proveedor | Como administrador quiero desactivar un proveedor para que no se generen nuevas OC sin perder su historial | Media |
| **HU-M05-05** | Crear OC | Como compras quiero generar una Orden de Compra para solicitar mercancía al proveedor | Alta |
| **HU-M05-06** | Aprobar OC | Como administrador quiero aprobar una OC para autorizar la compra | Alta |
| **HU-M05-07** | Recepción OC | Como almacén quiero recibir mercancía de OC para actualizar inventario | Alta |
| **HU-M05-08** | Devolución prov. | Como almacén quiero devolver ítems por defecto para ajustar inventario | Media |

**Alcance de la Etapa 2:**
- ✅ HU-M05-01: Registrar proveedor (CRUD completo)
- ✅ HU-M05-02: Consultar proveedor (Búsqueda y filtros)
- ✅ HU-M05-03: Editar proveedor (Formulario editable)
- ✅ HU-M05-04: Desactivar proveedor (Soft delete)
- ✅ HU-M05-05: Crear OC (Generación de órdenes de compra)
- ⏳ HU-M05-06: Aprobar OC (Reservado para Etapa 3 - Gestión transaccional)
- ⏳ HU-M05-07: Recepción OC (Reservado para Etapa 3 - Integración con Inventarios)
- ⏳ HU-M05-08: Devolución prov. (Reservado para Etapa 3 - Gestión transaccional)

#### 1.2 Requerimientos no funcionales reales

| ID RNF | Nombre | Descripción | Aplicación en Proveedores |
|--------|--------|-------------|---------------------------|
| **RNF-02** | Seguridad básica | Control de acceso por permisos | Sistema de permisos granulares (Ver, Crear, Editar, Eliminar) |
| **RNF-03** | Integración frontend-backend | API REST | Endpoints REST completos para CRUD y OC |
| **RNF-04** | Consistencia visual | Diseño uniforme | Patrón Master-Detail consistente con Clientes. Responsividad con scrollbars horizontal/vertical en tablas móviles. |
| **RNF-05** | Integridad de datos | Validaciones y constraints | RFC único, validación de condiciones de pago |
| **RNF-06** | Validaciones operativas | Reglas de negocio | Formato RFC, email, teléfono, estado de proveedor |


#### 1.3 Casos de uso reales asociados

- **CU-09:** Gestionar catálogo de proveedores
- **CU-10:** Crear y gestionar órdenes de compra
- **CU-11:** Validar condiciones de pago

#### 1.4 Diagramas que aplican

- **Diagrama de Navegabilidad:** Módulo Proveedores (Anexo E, Figura 10)
- **Diagrama Entidad-Relación:** Tablas `proveedor`, `orden_compra` (Anexo D, Figura 7)
- **Modelo Lógico 3FN:** Normalización de datos de proveedor y OC (Anexo D, Figura 9)
- **Diagrama de Clases:** Clases `Proveedor`, `OrdenCompra` (Anexo C)

#### 1.5 Datos literales del diseño (Pantalla Proveedores.svg)

**Base de datos:** `db_omcgc_erp`

**Tablas principales:** `proveedor`, `orden_compra`, `detalle_oc`

**Campos identificados en la pantalla:**

| Campo UI | Tipo | Descripción | Mapeo BD |
|----------|------|-------------|----------|
| **Buscar** | Input text | Búsqueda por nombre, razón social, contacto | Query LIKE en múltiples campos |
| **RFC** | Input text | Filtro por RFC | `proveedor.rfc` |
| **Estatus** | Select | Filtro Activos/Inactivos | `proveedor.estatus` |
| **Condición de pago** | Select | Filtro por condición (Contado, 15, 30, 60 días) | `proveedor.condicion_pago` |
| **RFC** | Input text | RFC del proveedor (12 caracteres) | `proveedor.rfc` |
| **Razón social** | Input text | Nombre legal del proveedor | `proveedor.razon_social` |
| **Contacto** | Input text | Nombre de persona de contacto | `proveedor.contacto` |
| **Teléfono** | Input tel | Número telefónico | `proveedor.telefono` |
| **Correo** | Input email | Email de contacto | `proveedor.correo` |
| **Condición de pago** | Select | Contado, 15 días, 30 días, 60 días | `proveedor.condicion_pago` |
| **Estatus** | Select | Activo / Inactivo | `proveedor.estatus` |

**Botones identificados:**
- Guardar (Azul - Principal)
- Cancelar
- Nuevo
- Duplicar
- Eliminar
- Crear OC
- Ver OC
- Recepciones / Devoluciones
- Exportar
- Volver

---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO POR ETAPAS

#### 2.1 Pertenencia a Etapa 2

**Confirmación:** El módulo Proveedores pertenece a **Etapa 2: Gestión Operativa Principal** ✅

**Justificación:**
- Forma parte del RF-08 (Gestión de Proveedores)
- Representa el 5% del proyecto total (20% de la Etapa 2)
- Es prerequisito para módulos de compras y recepción de mercancía

#### 2.2 RF que se cierran en 2.2

| RF | Descripción | Estado |
|----|-------------|--------|
| **RF-08** | Gestión de Proveedores | ✅ Se cierra completamente |
| **RF-05** | Comunicación API | ✅ Endpoints `/api/proveedores` |
| **RF-06** | Persistencia básica | ✅ CRUD completo en BD |

#### 2.3 Qué NO se puede tocar (Reservado a etapas futuras)

| Funcionalidad | Etapa Reservada | Motivo |
|---------------|-----------------|--------|
| Aprobación de OC | Etapa 3 | Requiere flujo de autorización |
| Recepción de mercancía | Etapa 3 | Integración con Inventarios |
| Devoluciones a proveedor | Etapa 3 | Gestión transaccional |
| Cuentas por pagar | Etapa 3 | Módulo financiero |
| Reportes de compras | Etapa 4 | Módulo de Reportes |
| Auditoría completa | Etapa 5 | Bitácoras avanzadas |

---

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL (UI → LÓGICA → DATOS)

#### 3.1 Elementos de la interfaz

**SECCIÓN SUPERIOR: Filtros y Búsqueda**

| Elemento | Tipo | Función | Evento | Acción Backend |
|----------|------|---------|--------|----------------|
| Campo "Buscar" | `<input type="text">` | Búsqueda libre por nombre, razón social, contacto | `keyup` (debounce 300ms) | `GET /api/proveedores?buscar={texto}` |
| Campo "RFC" | `<input type="text">` | Filtro exacto por RFC | `change` | `GET /api/proveedores?rfc={rfc}` |
| Selector "Estatus" | `<select>` | Filtro Activos/Inactivos | `change` | `GET /api/proveedores?estatus={valor}` |
| Selector "Condición de pago" | `<select>` | Filtro por condición de pago | `change` | `GET /api/proveedores?condicionPago={valor}` |
 
 #### 3.2 Estándares de Diseño (Homologación UX/UI)
 
 Este módulo sigue estrictamente el **Patrón WALOOK** homologado con Clientes:
 
 1.  **Tabla de Proveedores:**
     *   Diseño **Cebra (Striped)** para legibilidad (`tr:nth-child(even)`).
     *   **Selección de Fila:** Resaltado azul (`#bae6fd`) persistente al hacer clic.
     *   **Indicador de estatus:** Punto de color (`● Activo`) en lugar de badges.
     *   **Botón de acción:** Textual "Ver" (sin icono), estilo secundario.
 
 2.  **Títulos de Panel:**
     *   Fuente 1rem, negrita, color oscuro (`#111827`).
     *   Texto en **MAYÚSCULAS** ("DIRECTORIO DE PROVEEDORES").
 
 3.  **Responsividad:**
     *   Scrollbars horizontales y verticales forzados en vista móvil/tablet para tablas.

**SECCIÓN MEDIA: Tabla Maestra (Listado)**

| Columna | Tipo Dato | Ordenable | Acción |
|---------|-----------|-----------|--------|
| RFC | String | ✅ | - |
| Proveedor | String | ✅ | Click → Cargar detalle |
| Condición | Enum | ✅ | - |
| Estatus | Enum | ✅ | - |
| Acciones | Botón "Ver" | ❌ | Click → Cargar formulario detalle |

**SECCIÓN INFERIOR: Panel de Detalle (Formulario)**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| RFC | `<input type="text" maxlength="12" required>` | Formato RFC (12 chars) | UNIQUE, formato SAT | `proveedor.rfc` |
| Razón social | `<input type="text" required>` | No vacío, max 200 chars | NOT NULL | `proveedor.razon_social` |
| Contacto | `<input type="text">` | Max 100 chars | - | `proveedor.contacto` |
| Teléfono | `<input type="tel">` | Formato 10 dígitos | Regex teléfono | `proveedor.telefono` |
| Correo | `<input type="email">` | Formato email | Regex email | `proveedor.correo` |
| Condición de pago | `<select required>` | Selección obligatoria | ENUM válido | `proveedor.condicion_pago` |
| Estatus | `<select>` | Activo/Inactivo | ENUM válido | `proveedor.estatus` |

**Botones de Acción:**

| Botón | Clase CSS | Permiso Requerido | Acción | Endpoint |
|-------|-----------|-------------------|--------|----------|
| Guardar | `.btn-primary` | `puede_editar` o `puede_crear` | Crear/Actualizar proveedor | `POST /PUT /api/proveedores` |
| Cancelar | `.btn-secondary` | `puede_ver` | Limpiar formulario | - |
| Nuevo | `.btn-success` | `puede_crear` | Limpiar formulario para nuevo registro | - |
| Duplicar | `.btn-info` | `puede_crear` | Copiar datos a nuevo registro | - |
| Eliminar | `.btn-danger` | `puede_eliminar` | Soft delete (estatus=Inactivo) | `DELETE /api/proveedores/{id}` |
| Crear OC | `.btn-primary` | `puede_crear` + `estatus=ACTIVO` | Abrir formulario de OC | `POST /api/proveedores/{id}/ordenes` |
| Ver OC | `.btn-outline` | `puede_ver` | Listar órdenes de compra | `GET /api/proveedores/{id}/ordenes` |
| Recepciones / Devoluciones | `.btn-warning` | `puede_editar` | Gestionar recepciones | `POST /api/recepciones` |
| Exportar | `.btn-outline` | `puede_ver` | Exportar a CSV/Excel | `GET /api/proveedores/exportar` |
| Volver | `.btn-link` | `puede_ver` | Regresar a menú | - |

#### 3.2 Trazabilidad técnica por elemento

**Flujo de Datos: Crear Proveedor**
```
Usuario hace clic en "Nuevo"
    ↓
Verificar permiso puede_crear
    ↓
Limpiar formulario
    ↓
Usuario captura datos
    ↓
Usuario hace clic en "Guardar"
    ↓
Validaciones Frontend (formato, campos requeridos)
    ↓
POST /api/proveedores + Body JSON
    ↓
Backend: ProveedorController.createProveedor()
    ↓
Validar RFC único
    ↓
Validar formato RFC (12 caracteres para Moral)
    ↓
ProveedorService.crear()
    ↓
ProveedorRepository.save()
    ↓
Retorna 201 Created + Proveedor creado
    ↓
Frontend: recargar tabla + toast éxito
```

**Flujo de Datos: Crear Orden de Compra**
```
Usuario selecciona proveedor
    ↓
Usuario hace clic en "Crear OC"
    ↓
Verificar permiso puede_crear
    ↓
Verificar proveedor.estatus == "ACTIVO"
    ↓
SI estatus == "INACTIVO" THEN
    Mostrar error "Proveedor inactivo"
    BREAK
ENDIF
    ↓
Abrir modal/formulario de OC
    ↓
Usuario selecciona productos y cantidades
    ↓
Sistema calcula subtotal, IVA, total
    ↓
Usuario hace clic en "Guardar OC"
    ↓
POST /api/proveedores/{id}/ordenes + Body JSON
    ↓
Backend: OrdenCompraController.createOrden()
    ↓
Validar productos existen
    ↓
Validar cantidades > 0
    ↓
Generar folio OC (OC-XXXXX)
    ↓
Estado inicial: "BORRADOR"
    ↓
OrdenCompraService.crear()
    ↓
OrdenCompraRepository.save()
    ↓
Retorna 201 Created + OC creada
    ↓
Frontend: mostrar OC creada + toast éxito
```

#### 3.3 Mapeo a base de datos (SSoT y Auditoría Forense)

Siguiendo el estándar de **Gobernanza de Datos** v3.2, la tabla de proveedores incorpora la segregación por sucursal y el enlace a la bitácora cifrada.

**Tabla: `proveedor`**

```sql
CREATE TABLE proveedor (
    -- Identificación
    id_proveedor        CHAR(36) PRIMARY KEY,              -- UUID estándar
    
    -- Datos principales
    rfc                 VARCHAR(13) UNIQUE NOT NULL,       -- RFC (12 Moral, 13 Física)
    razon_social        VARCHAR(255) NOT NULL,             -- Nombre legal
    nombre_comercial    VARCHAR(255),                      -- Nombre comercial (Opcional)
    
    -- Datos de contacto
    contacto            VARCHAR(120),                      -- Persona de contacto
    telefono            VARCHAR(40),                       -- Teléfono
    email               VARCHAR(255),                      -- Correo
    
    -- Condiciones comerciales
    condicion_pago      VARCHAR(50) NOT NULL,              -- "Contado", "15 días", etc. (Flexible)
    
    -- Control y Segregación (SSoT)
    id_sucursal         VARCHAR(36) NOT NULL,              -- Sincronización Multi-Sucursal
    estatus             ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    
    -- Auditoría Forense Cifrada (AuditPatternService)
    uuid_auditoria      VARCHAR(36) UNIQUE,                -- Enlace a Bitácora Forense
    hash_forense        VARCHAR(64),                       -- Firma SHA-256 de integridad
    creado_en           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices Recomendados
    INDEX idx_rfc (rfc),
    INDEX idx_razon_social (razon_social),
    INDEX idx_estatus (estatus)
);
```

**Tabla: `orden_compra`**

```sql
CREATE TABLE orden_compra (
    -- Identificación
    id_oc               VARCHAR(20) PRIMARY KEY,           -- OC-00001, OC-00002, etc.
    folio               VARCHAR(50) UNIQUE,                -- Folio interno
    
    -- Relación con proveedor
    id_proveedor        VARCHAR(20) NOT NULL,
    
    -- Fechas
    fecha_creacion      DATE NOT NULL,
    fecha_entrega_est   DATE,                              -- Fecha estimada de entrega
    
    -- Estado
    estado              ENUM('BORRADOR', 'APROBADA', 'RECIBIDA', 'CANCELADA') DEFAULT 'BORRADOR',
    
    -- Importes
    subtotal            DECIMAL(10,2) DEFAULT 0.00,
    iva                 DECIMAL(10,2) DEFAULT 0.00,
    total               DECIMAL(10,2) DEFAULT 0.00,
    
    -- Observaciones
    observaciones       TEXT,
    
    -- Auditoría
    usuario_creacion    VARCHAR(50),
    usuario_aprobacion  VARCHAR(50),
    fecha_aprobacion    TIMESTAMP,
    
    -- Relaciones
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    
    -- Índices
    INDEX idx_proveedor (id_proveedor),
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha_creacion),
    INDEX idx_folio (folio)
);
```

**Tabla: `detalle_oc`**

```sql
CREATE TABLE detalle_oc (
    -- Identificación
    id_detalle          INT AUTO_INCREMENT PRIMARY KEY,
    id_oc               VARCHAR(20) NOT NULL,
    
    -- Producto
    id_producto         VARCHAR(20) NOT NULL,
    
    -- Cantidades
    cantidad            INT NOT NULL,
    cantidad_recibida   INT DEFAULT 0,
    
    -- Costos
    costo_unitario      DECIMAL(10,2) NOT NULL,
    subtotal            DECIMAL(10,2) NOT NULL,
    
    -- Relaciones
    FOREIGN KEY (id_oc) REFERENCES orden_compra(id_oc),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    
    -- Índices
    INDEX idx_oc (id_oc),
    INDEX idx_producto (id_producto),
    
    -- Constraints
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_costo_positivo CHECK (costo_unitario > 0)
);
```

**Relaciones:**
- `orden_compra.id_proveedor` → `proveedor.id_proveedor` (FK)
- `detalle_oc.id_oc` → `orden_compra.id_oc` (FK)
- `detalle_oc.id_producto` → `producto.id_producto` (FK - Etapa 2, Módulo Inventarios)

---

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 2.2

#### 4.1 Matriz de Control de Permisos - PROVEEDORES

**Tabla de Control de Acceso y Comportamiento UI**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **1** | **Acceso al módulo** | `puede_ver` | Carga `proveedores.html` normalmente | Redirige a `menu.html` + Toast "Acceso Denegado" | `GET /api/proveedores` retorna 403 |
| **2** | **Barra de búsqueda** | `puede_ver` | Habilitada, permite buscar | *(No aplica, requiere acceso)* | Solo lectura |
| **3** | **Filtros (RFC, Estatus, Condición)** | `puede_ver` | Habilitados, permiten filtrar | *(No aplica, requiere acceso)* | Solo lectura |
| **4** | **Tabla de listado** | `puede_ver` | Muestra todos los proveedores | *(No aplica, requiere acceso)* | Solo lectura |
| **5** | **Botón "Ver" (tabla)** | `puede_ver` | Habilitado, abre detalle | *(No aplica, requiere acceso)* | Solo lectura |
| **6** | **Botón "Nuevo"** | `puede_crear` | Habilitado, limpia formulario y permite captura | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `POST /api/proveedores` retorna 403 |
| **7** | **Formulario de detalle (inputs)** | `puede_editar` | Inputs habilitados, permite edición | `disabled=true` en TODOS los inputs + clase `.input-readonly` | `PUT /api/proveedores/{id}` retorna 403 |
| **8** | **Botón "Guardar"** | `puede_editar` | Visible y habilitado | `display: none` (oculto) | `PUT /api/proveedores/{id}` retorna 403 |
| **9** | **Botón "Cancelar"** | `puede_ver` | Siempre habilitado | Siempre habilitado | No requiere validación |
| **10** | **Botón "Duplicar"** | `puede_crear` | Habilitado, copia datos a nuevo registro | `disabled=true` + clase `.btn-disabled` | `POST /api/proveedores` retorna 403 |
| **11** | **Botón "Eliminar"** | `puede_eliminar` | Habilitado, cambia estatus a "Inactivo" | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `DELETE /api/proveedores/{id}` retorna 403 |
| **12** | **Selector "Estatus"** | `puede_eliminar` | Habilitado, permite cambiar Activo/Inactivo | `disabled=true` + tooltip "No tiene permiso para cambiar estatus" | `PUT /api/proveedores/{id}` valida permiso |
| **13** | **Botón "Crear OC"** | `puede_crear` + `estatus=ACTIVO` | Habilitado, abre formulario de OC | `disabled=true` + tooltip según razón (permiso o estatus) | `POST /api/proveedores/{id}/ordenes` retorna 403 |
| **14** | **Botón "Ver OC"** | `puede_ver` | Habilitado, muestra listado de OC | Habilitado (solo lectura) | `GET /api/proveedores/{id}/ordenes` |
| **15** | **Botón "Recepciones / Devoluciones"** | `puede_editar` | Habilitado, abre gestión de recepciones | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `POST /api/recepciones` retorna 403 |
| **16** | **Botón "Exportar"** | `puede_ver` | Habilitado, exporta a CSV/Excel | Habilitado (solo lectura) | Solo lectura |
| **17** | **Botón "Volver"** | `puede_ver` | Siempre habilitado | Siempre habilitado | No requiere validación |
| **18** | **Badge "MODO SOLO LECTURA"** | `!puede_editar` | No se muestra | Se muestra en formulario | Visual |

#### 4.2 Reglas cerradas (Políticas de Seguridad)

```pseudocode
REGLA 1: Acceso al Módulo
    AL CARGAR proveedores.html:
        SI permisosActuales.puede_ver == FALSE THEN
            window.location.href = 'menu.html'
            mostrarToast('No tiene acceso al módulo de Proveedores', 'error')
            BREAK
        ENDIF

REGLA 2: Botón "Nuevo"
    AL INICIALIZAR UI:
        SI permisosActuales.puede_crear == FALSE THEN
            btnNuevo.disabled = true
            btnNuevo.classList.add('btn-disabled')
            btnNuevo.title = 'No tiene permiso para crear proveedores'
        ENDIF

REGLA 3: Modo Edición vs Solo Lectura
    AL HACER CLIC EN "Ver" (seleccionar proveedor):
        SI permisosActuales.puede_editar == FALSE THEN
            // Deshabilitar todos los inputs
            document.querySelectorAll('#formProveedor input, #formProveedor select').forEach(campo => {
                campo.disabled = true
                campo.classList.add('input-readonly')
            })
            
            // Ocultar botón Guardar
            btnGuardar.style.display = 'none'
            
            // Mostrar badge
            mostrarBadge('MODO SOLO LECTURA', 'warning')
        ELSE
            // Habilitar edición
            habilitarInputs()
            btnGuardar.style.display = 'block'
        ENDIF

REGLA 4: Eliminar y Cambiar Estatus
    AL INICIALIZAR UI:
        SI permisosActuales.puede_eliminar == FALSE THEN
            btnEliminar.disabled = true
            btnEliminar.classList.add('btn-disabled')
            selectEstatus.disabled = true
            selectEstatus.title = 'No tiene permiso para cambiar estatus'
        ENDIF

REGLA 5: Crear Orden de Compra (Validación Doble)
    AL HACER CLIC EN "Crear OC":
        // Validación 1: Permisos
        SI permisosActuales.puede_crear == FALSE THEN
            mostrarToast('No tiene permiso para crear órdenes de compra', 'error')
            BREAK
        ENDIF
        
        // Validación 2: Estatus del Proveedor
        SI proveedorActual.estatus == "INACTIVO" THEN
            mostrarToast('No se pueden crear OC para proveedores inactivos', 'warning')
            BREAK
        ENDIF
        
        // Continuar con creación de OC
        abrirModalCrearOC()

REGLA 6: Botón "Crear OC" - Estado Dinámico
    AL SELECCIONAR PROVEEDOR:
        SI permisosActuales.puede_crear == FALSE THEN
            btnCrearOC.disabled = true
            btnCrearOC.title = 'No tiene permiso para crear OC'
        ELSE SI proveedorActual.estatus == "INACTIVO" THEN
            btnCrearOC.disabled = true
            btnCrearOC.title = 'Proveedor inactivo, no se pueden crear OC'
            btnCrearOC.classList.add('btn-warning')
        ELSE
            btnCrearOC.disabled = false
            btnCrearOC.title = 'Crear nueva orden de compra'
            btnCrearOC.classList.remove('btn-warning')
        ENDIF

REGLA 7: Recepciones y Devoluciones
    AL HACER CLIC EN "Recepciones / Devoluciones":
        SI permisosActuales.puede_editar == FALSE THEN
            mostrarToast('No tiene permiso para gestionar recepciones', 'error')
            BREAK
        ENDIF
        
        // Continuar con gestión de recepciones

REGLA 8: Ver Órdenes de Compra (Solo Lectura)
    AL HACER CLIC EN "Ver OC":
        // Solo requiere permiso de ver
        // Siempre habilitado si tiene acceso al módulo
        abrirModalVerOC(proveedorActual.id)
```

#### 4.3 Flujos if/then (Reglas de Negocio)

**Regla 1: Validación de RFC**
```pseudocode
SI tipo_persona == "MORAL" THEN
    Validar LENGTH(rfc) == 12
    Validar formato: 3 letras + 6 dígitos (YYMMDD) + 3 caracteres
ENDIF

SI rfc ya existe en BD (excepto el mismo registro en edición) THEN
    Retornar error "RFC duplicado"
    No permitir guardar
ENDIF
```

**Regla 2: Campos Obligatorios**
```pseudocode
SI razon_social == "" OR razon_social == NULL THEN
    Mostrar error "La razón social es obligatoria"
    No permitir guardar
ENDIF

SI rfc == "" OR rfc == NULL THEN
    Mostrar error "El RFC es obligatorio"
    No permitir guardar
ENDIF

SI condicion_pago == "" OR condicion_pago == NULL THEN
    Mostrar error "Debe seleccionar la condición de pago"
    No permitir guardar
ENDIF
```

**Regla 3: Validación de Email**
```pseudocode
SI correo != "" AND correo != NULL THEN
    Validar formato email con regex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    
    SI formato inválido THEN
        Mostrar error "Formato de email inválido"
        No permitir guardar
    ENDIF
ENDIF
```

**Regla 4: Validación de Teléfono**
```pseudocode
SI telefono != "" AND telefono != NULL THEN
    Validar solo números
    Validar LENGTH(telefono) == 10
    
    SI formato inválido THEN
        Mostrar error "El teléfono debe tener 10 dígitos"
        No permitir guardar
    ENDIF
ENDIF
```

**Regla 5: Crear Orden de Compra**
```pseudocode
AL CREAR OC:
    Validar proveedor.estatus == "ACTIVO"
    
    SI estatus == "INACTIVO" THEN
        Retornar error "No se pueden crear OC para proveedores inactivos"
        BREAK
    ENDIF
    
    Validar lista de productos no vacía
    
    PARA CADA producto EN lista_productos:
        Validar producto existe en BD
        Validar cantidad > 0
        Validar costo_unitario > 0
        Calcular subtotal_linea = cantidad * costo_unitario
    FIN PARA
    
    Calcular subtotal_oc = SUMA(subtotal_linea)
    Calcular iva_oc = subtotal_oc * 0.16
    Calcular total_oc = subtotal_oc + iva_oc
    
    Generar folio_oc = "OC-" + SECUENCIA
    Estado inicial = "BORRADOR"
    
    BEGIN TRANSACTION
        INSERT INTO orden_compra (...)
        
        PARA CADA producto EN lista_productos:
            INSERT INTO detalle_oc (...)
        FIN PARA
    COMMIT TRANSACTION
    
    Retornar OC creada
```

**Regla 6: Desactivar Proveedor con OC Pendientes**
```pseudocode
AL DESACTIVAR PROVEEDOR:
    Contar OC pendientes = COUNT(orden_compra WHERE id_proveedor = X AND estado IN ('BORRADOR', 'APROBADA'))
    
    SI oc_pendientes > 0 THEN
        Mostrar advertencia "El proveedor tiene {oc_pendientes} OC pendientes. ¿Desea continuar?"
        
        SI usuario confirma THEN
            UPDATE proveedor SET estatus = 'INACTIVO'
            // Las OC existentes se mantienen
        ELSE
            Cancelar operación
        ENDIF
    ELSE
        UPDATE proveedor SET estatus = 'INACTIVO'
    ENDIF
```

#### 4.4 Validaciones específicas

**Frontend (JavaScript - proveedores-service.js):**

```javascript
validarFormulario() {
    const errores = [];
    
    // RFC obligatorio
    if (!this.rfc || this.rfc.trim() === '') {
        errores.push('El RFC es obligatorio');
    } else if (this.rfc.length !== 12) {
        errores.push('RFC de persona moral debe tener 12 caracteres');
    } else {
        // Formato RFC
        const rfcRegex = /^[A-ZÑ&]{3}\d{6}[A-Z0-9]{3}$/;
        if (!rfcRegex.test(this.rfc)) {
            errores.push('Formato de RFC inválido');
        }
    }
    
    // Razón social obligatoria
    if (!this.razon_social || this.razon_social.trim() === '') {
        errores.push('La razón social es obligatoria');
    }
    
    // Condición de pago obligatoria
    if (!this.condicion_pago) {
        errores.push('Debe seleccionar la condición de pago');
    }
    
    // Validar email si se proporciona
    if (this.correo) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(this.correo)) {
            errores.push('Formato de email inválido');
        }
    }
    
    // Validar teléfono si se proporciona
    if (this.telefono) {
        const telRegex = /^\d{10}$/;
        if (!telRegex.test(this.telefono)) {
            errores.push('El teléfono debe tener 10 dígitos');
        }
    }
    
    return errores;
}

validarOrdenCompra() {
    const errores = [];
    
    // Validar que haya al menos un producto
    if (!this.productos || this.productos.length === 0) {
        errores.push('Debe agregar al menos un producto a la orden');
    }
    
    // Validar cada producto
    this.productos.forEach((prod, index) => {
        if (!prod.id_producto) {
            errores.push(`Producto ${index + 1}: Debe seleccionar un producto`);
        }
        if (!prod.cantidad || prod.cantidad <= 0) {
            errores.push(`Producto ${index + 1}: La cantidad debe ser mayor a 0`);
        }
        if (!prod.costo_unitario || prod.costo_unitario <= 0) {
            errores.push(`Producto ${index + 1}: El costo debe ser mayor a 0`);
        }
    });
    
    return errores;
}
```

**Backend (Java - ProveedorService.java):**

```java
public void validarProveedor(Proveedor proveedor) throws ValidationException {
    List<String> errores = new ArrayList<>();
    
    // RFC obligatorio
    if (proveedor.getRfc() == null || proveedor.getRfc().trim().isEmpty()) {
        errores.add("El RFC es obligatorio");
    } else {
        // Validar longitud
        if (proveedor.getRfc().length() != 12) {
            errores.add("RFC de persona moral debe tener 12 caracteres");
        }
        
        // Validar unicidad
        Optional<Proveedor> existente = proveedorRepository.findByRfc(proveedor.getRfc());
        if (existente.isPresent() && !existente.get().getIdProveedor().equals(proveedor.getIdProveedor())) {
            errores.add("El RFC ya está registrado");
        }
    }
    
    // Razón social obligatoria
    if (proveedor.getRazonSocial() == null || proveedor.getRazonSocial().trim().isEmpty()) {
        errores.add("La razón social es obligatoria");
    }
    
    // Condición de pago obligatoria
    if (proveedor.getCondicionPago() == null) {
        errores.add("La condición de pago es obligatoria");
    }
    
    if (!errores.isEmpty()) {
        throw new ValidationException(String.join(", ", errores));
    }
}

public void validarOrdenCompra(OrdenCompra oc) throws ValidationException {
    List<String> errores = new ArrayList<>();
    
    // Validar proveedor activo
    Proveedor proveedor = proveedorRepository.findById(oc.getIdProveedor())
        .orElseThrow(() -> new NotFoundException("Proveedor no encontrado"));
    
    if (!"ACTIVO".equals(proveedor.getEstatus())) {
        errores.add("No se pueden crear OC para proveedores inactivos");
    }
    
    // Validar que tenga detalles
    if (oc.getDetalles() == null || oc.getDetalles().isEmpty()) {
        errores.add("La orden debe tener al menos un producto");
    } else {
        // Validar cada detalle
        for (DetalleOC detalle : oc.getDetalles()) {
            if (detalle.getCantidad() <= 0) {
                errores.add("Las cantidades deben ser mayores a 0");
            }
            if (detalle.getCostoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                errores.add("Los costos deben ser mayores a 0");
            }
        }
    }
    
    if (!errores.isEmpty()) {
        throw new ValidationException(String.join(", ", errores));
    }
}
```

#### 4.5 Endpoints API REST

```
GET    /api/proveedores                    # Listar todos con filtros opcionales
GET    /api/proveedores/{id}               # Obtener por ID
POST   /api/proveedores                    # Crear nuevo proveedor
PUT    /api/proveedores/{id}               # Actualizar proveedor existente
DELETE /api/proveedores/{id}               # Desactivar proveedor (soft delete)
GET    /api/proveedores/{id}/ordenes       # Listar OC del proveedor
POST   /api/proveedores/{id}/ordenes       # Crear OC para proveedor
GET    /api/proveedores/duplicados         # Detectar posibles duplicados
GET    /api/proveedores/exportar           # Exportar a CSV/Excel
```

**Detalle de Endpoints:**

**1. GET /api/proveedores**
```
Query Params:
  - buscar: string (opcional) - Búsqueda en razón social, contacto
  - rfc: string (opcional) - Filtro exacto por RFC
  - estatus: string (opcional) - ACTIVO | INACTIVO
  - condicionPago: string (opcional) - CONTADO | 15_DIAS | 30_DIAS | 60_DIAS
  - page: int (opcional) - Número de página (default: 0)
  - size: int (opcional) - Tamaño de página (default: 20)

Response 200:
{
  "content": [Proveedor],
  "totalElements": int,
  "totalPages": int,
  "number": int
}
```

**2. POST /api/proveedores**
```
Request Body:
{
  "rfc": "ABC010203XYZ",
  "razon_social": "Ópticos del Centro S.A. de C.V.",
  "contacto": "Laura Pérez",
  "telefono": "5512345678",
  "correo": "compras@opticentro.mx",
  "condicion_pago": "30_DIAS"
}

Response 201:
{
  "id_proveedor": "PRV-00015",
  "rfc": "ABC010203XYZ",
  ...
}
```

**3. POST /api/proveedores/{id}/ordenes**
```
Request Body:
{
  "fecha_entrega_est": "2026-02-15",
  "observaciones": "Entrega urgente",
  "detalles": [
    {
      "id_producto": "7500001",
      "cantidad": 50,
      "costo_unitario": 600.00
    },
    {
      "id_producto": "7500002",
      "cantidad": 100,
      "costo_unitario": 350.00
    }
  ]
}

Response 201:
{
  "id_oc": "OC-00045",
  "folio": "OC-00045",
  "estado": "BORRADOR",
  "subtotal": 65000.00,
  "iva": 10400.00,
  "total": 75400.00,
  ...
}
```

#### 4.6 Criterios de Aceptación

- [x] El usuario puede crear un proveedor con todos los datos comerciales
- [x] El sistema valida el formato del RFC (12 caracteres para persona moral)
- [x] No se permiten RFCs duplicados en el sistema
- [x] El usuario puede buscar proveedores por RFC, razón social o condición de pago
- [x] El usuario puede editar datos de un proveedor existente
- [x] El usuario puede desactivar un proveedor (no se elimina físicamente)
- [x] El sistema controla el acceso según permisos del usuario (Ver, Crear, Editar, Eliminar)
- [x] El usuario puede crear órdenes de compra para proveedores activos
- [x] El sistema genera folios automáticos para las OC (OC-XXXXX)
- [x] El sistema calcula automáticamente subtotal, IVA y total de las OC
- [x] No se permiten crear OC para proveedores inactivos
- [x] El usuario puede ver el listado de OC de un proveedor
- [x] Todos los cambios quedan auditados (usuario, fecha)
- [x] La interfaz es consistente con el módulo de Clientes
- [x] El formulario muestra modo "SOLO LECTURA" si el usuario no tiene permiso de edición
- [x] Los botones se deshabilitan según los permisos del usuario
- [x] Las validaciones se ejecutan en frontend (UX) y backend (seguridad)

---

**Última actualización:** 22 de febrero de 2026, 03:10 PM  
**Estado:** Sincronizado con v3.2 (Auditoría Forense)  
**Siguiente:** Módulo Inventarios

