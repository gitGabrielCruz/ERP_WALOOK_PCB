---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Clientes  
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Clientes  
**VERSIÓN:** 1.1  
**FECHA:** 22 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto / Antigravity AI  

---

# 📊 ETAPA 2 - MÓDULO CLIENTES

## 2.1 INCISO 2.1 — MÓDULO CLIENTES (Clientes.svg)

> [!NOTE]
> **Definición de Nomenclatura:** Para fines operativos del ERP, se utilizará el término **Cliente**. El término **Paciente** se mantiene como alias técnico en la base de datos (`Paciente.java`) para asegurar la compatibilidad con el Expediente Clínico de la Etapa 4.

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales (Historias de Usuario)

El módulo de Clientes implementa las siguientes Historias de Usuario del **Módulo M06** definidas en el Análisis General:

| ID HU | Título | Descripción | Prioridad |
|-------|--------|-------------|-----------|
| **HU-M06-01** | Registrar cliente | Como recepcionista quiero dar de alta un cliente para gestionar sus datos y ventas | Alta |
| **HU-M06-02** | Consultar cliente | Como recepcionista quiero buscar clientes por nombre, RFC o teléfono para acceder a su información | Alta |
| **HU-M06-03** | Editar cliente | Como administrador quiero actualizar datos del cliente para mantener información vigente | Alta |
| **HU-M06-04** | Desactivar cliente | Como administrador quiero desactivar un cliente para que no aparezca en búsquedas sin perder su historial | Media |
| **HU-M06-05** | Historial compras | Como administrador quiero ver historial de compras del cliente para análisis comercial | Media |
| **HU-M06-06** | Datos fiscales | Como facturista quiero gestionar RFC y direcciones fiscales para emitir CFDI | Alta |
| **HU-M06-07** | Consentimientos | Como recepcionista quiero gestionar permisos de contacto para cumplir LFPDPPP | Media |
| **HU-M06-08** | Unificar clientes | Como administrador quiero unir duplicados para mantener integridad de datos | Baja |

**Alcance de la Etapa 2:**
- ✅ HU-M06-01: Registrar cliente (CRUD completo)
- ✅ HU-M06-02: Consultar cliente (Búsqueda y filtros)
- ✅ HU-M06-03: Editar cliente (Formulario editable)
- ✅ HU-M06-04: Desactivar cliente (Soft delete)
- ✅ HU-M06-06: Datos fiscales (RFC, Uso CFDI, Régimen fiscal)
- ⏳ HU-M06-05: Historial compras (Reservado para Etapa 3 - Ventas)
- ⏳ HU-M06-07: Consentimientos (Reservado para Etapa 5 - Seguridad avanzada)

#### 1.2 Requerimientos no funcionales reales

| ID RNF | Nombre | Descripción | Aplicación en Clientes |
|--------|--------|-------------|------------------------|
| **RNF-02** | Seguridad básica | Control de acceso por permisos | Sistema de permisos granulares (Ver, Crear, Editar, Eliminar) |
| **RNF-03** | Integración frontend-backend | API REST | Endpoints REST completos para CRUD |
| **RNF-04** | Consistencia visual | Diseño uniforme | Patrón Master-Detail consistente con Usuarios. Responsividad con scrollbars horizontal/vertical en tablas móviles. |
| **RNF-05** | Integridad de datos | Validaciones y constraints | RFC único, validaciones fiscales SAT |
| **RNF-06** | Validaciones operativas | Reglas de negocio | Formato RFC, email, teléfono, uso CFDI |

#### 1.3 Casos de uso reales asociados

- **CU-06:** Gestionar catálogo de clientes
- **CU-07:** Validar datos fiscales para facturación
- **CU-08:** Detectar y fusionar clientes duplicados

#### 1.4 Diagramas que aplican

- **Diagrama de Navegabilidad:** Módulo Clientes (Anexo E, Figura 10)
- **Diagrama Entidad-Relación:** Tabla `cliente` (Anexo D, Figura 7)
- **Modelo Lógico 3FN:** Normalización de datos de cliente (Anexo D, Figura 9)
- **Diagrama de Clases:** Clase `Cliente` (Anexo C)

#### 1.5 Datos literales del diseño (Pantalla Clientes.svg)

**Base de datos:** `db_omcgc_erp`

**Tabla principal:** `cliente`

**Campos identificados en la pantalla:**

| Campo UI | Tipo | Descripción | Mapeo BD |
|----------|------|-------------|----------|
| **Buscar** | Input text | Búsqueda por nombre, correo, razón social | Query LIKE en múltiples campos |
| **RFC** | Input text | Filtro por RFC | `cliente.rfc` |
| **Estatus** | Select | Filtro Activos/Inactivos | `cliente.estatus` |
| **Uso CFDI** | Select | Filtro por uso CFDI (G03, G01, P01, etc.) | `cliente.uso_cfdi` |
| **Nombre** | Input text | Nombre completo o razón social | `cliente.nombre` |
| **ID Cliente** | Input text (readonly) | Identificador único (CLT-XXXXX) | `cliente.id_cliente` (PK) |
| **Tipo persona** | Select | Física / Moral | `cliente.tipo_persona` |
| **Teléfono** | Input tel | Número telefónico | `cliente.telefono` |
| **Correo** | Input email | Email de contacto | `cliente.correo` |
| **Régimen fiscal** | Select | Catálogo SAT (612, 601, etc.) | `cliente.regimen_fiscal` |
| **Domicilio fiscal** | Textarea | Dirección fiscal completa | `cliente.domicilio_fiscal` |

**Botones identificados:**
- Guardar (Azul - Principal)
- Cancelar
- Nuevo
- Duplicar
- Eliminar
- Nuevo
- Duplicar
- Eliminar
- Volver

---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO POR ETAPAS

#### 2.1 Pertenencia a Etapa 2

**Confirmación:** El módulo Clientes pertenece a **Etapa 2: Gestión Operativa Principal** ✅

**Justificación:**
- Forma parte del RF-07 (Gestión de Clientes)
- Representa el 5% del proyecto total (20% de la Etapa 2)
- Es prerequisito para módulos transaccionales (Ventas, Facturación)

#### 2.2 RF que se cierran en 2.1

| RF | Descripción | Estado |
|----|-------------|--------|
| **RF-07** | Gestión de Clientes | ✅ Se cierra completamente |
| **RF-05** | Comunicación API | ✅ Endpoints `/api/clientes` |
| **RF-06** | Persistencia básica | ✅ CRUD completo en BD |

#### 2.3 Qué NO se puede tocar (Reservado a etapas futuras)

| Funcionalidad | Etapa Reservada | Motivo |
|---------------|-----------------|--------|
| Historial de compras | Etapa 3 | Requiere módulo de Ventas |
| Facturación CFDI | Etapa 3 | Módulo de Facturación |
| Consentimientos LFPDPPP | Etapa 5 | Seguridad avanzada |
| Reportes de clientes | Etapa 4 | Módulo de Reportes |
| Auditoría completa | Etapa 5 | Bitácoras avanzadas |

---

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL (UI → LÓGICA → DATOS)

#### 3.1 Elementos de la interfaz

**SECCIÓN SUPERIOR: Filtros y Búsqueda**

| Elemento | Tipo | Función | Evento | Acción Backend |
|----------|------|---------|--------|----------------|
| Campo "Buscar" | `<input type="text">` | Búsqueda libre por nombre, correo, razón social | `keyup` (debounce 300ms) | `GET /api/clientes?buscar={texto}` |
| Selector "Estatus" | `<select>` | Filtro Activos/Inactivos | `change` | `GET /api/clientes?estatus={valor}` |
| Selector "Uso CFDI" | `<select>` | Filtro por uso CFDI | `change` | `GET /api/clientes?usoCfdi={codigo}` |
| Campo "RFC" | `<input type="text">` | Filtro Live Search por RFC | `input` | `GET /api/clientes` (local filter) |

#### 3.2 Estándares de Diseño (Homologación UX/UI)

Este módulo sigue estricamente el **Patrón WALOOK** definido en Etapa 1:

1.  **Tabla de Clientes:**
    *   Diseño **Cebra (Striped)** para legibilidad.
    *   Filas clickeables para ver detalle.
    *   Indicador de estatus con Punto de color (`● Activo`).
    *   Botón de acción textual "Ver".

2.  **Búsqueda:**
    *   Implementación de **Live Search (evento `input`)** tanto en buscador de texto como en filtro RFC.

3.  **Botones:**
    *   Iconos homologados: Nuevo (`add_circle`), Guardar (`save`).
    *   Posición inferior derecha.

**SECCIÓN MEDIA: Tabla Maestra (Listado)**

| Columna | Tipo Dato | Ordenable | Acción |
|---------|-----------|-----------|--------|
| Nombre | String | ✅ | Click → Cargar detalle |
| RFC | String | ✅ | - |
| Uso CFDI | String | ✅ | - |
| Tipo persona | Enum | ✅ | - |
| Estatus | Enum | ✅ | - |
| Acciones | Botón "Ver" | ❌ | Click → Cargar formulario detalle |

**SECCIÓN INFERIOR: Panel de Detalle (Formulario)**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| ID Cliente | `<input readonly>` | - | Auto-generado | `cliente.id_cliente` |
| Nombre/Razón social | `<input type="text" required>` | No vacío, max 200 chars | NOT NULL | `cliente.nombre` |
| RFC | `<input type="text" maxlength="13">` | Formato RFC (12-13 chars) | UNIQUE, formato SAT | `cliente.rfc` |
| Tipo persona | `<select required>` | Selección obligatoria | ENUM válido | `cliente.tipo_persona` |
| Uso CFDI | `<select>` | Catálogo SAT | Código válido SAT | `cliente.uso_cfdi` |
| Teléfono | `<input type="tel">` | Formato 10 dígitos | Regex teléfono | `cliente.telefono` |
| Correo | `<input type="email">` | Formato email | Regex email | `cliente.correo` |
| Régimen fiscal | `<select>` | Catálogo SAT | Código válido SAT | `cliente.regimen_fiscal` |
| Domicilio fiscal | `<textarea>` | Max 500 chars | - | `cliente.domicilio_fiscal` |
| Estatus | `<select>` | Activo/Inactivo | ENUM válido | `cliente.estatus` |

**Botones de Acción:**

| Botón | Clase CSS | Permiso Requerido | Acción | Endpoint |
|-------|-----------|-------------------|--------|----------|
| Guardar | `.btn-primary` | `puede_editar` o `puede_crear` | Crear/Actualizar cliente | `POST /PUT /api/clientes` |
| Cancelar | `.btn-secondary` | `puede_ver` | Limpiar formulario | - |
| Nuevo | `.btn-success` | `puede_crear` | Limpiar formulario para nuevo registro | - |
| Duplicar | `.btn-info` | `puede_crear` | Copiar datos a nuevo registro | - |
| Eliminar | `.btn-danger` | `puede_eliminar` | Soft delete (estatus=Inactivo) | `DELETE /api/clientes/{id}` |
| Eliminar | `.btn-danger` | `puede_eliminar` | Soft delete (estatus=Inactivo) | `DELETE /api/clientes/{id}` |
| Exportar | `.btn-outline` | `puede_ver` | Exportar a CSV/Excel | `GET /api/clientes/exportar` |
| Volver | `.btn-link` | `puede_ver` | Regresar a menú | - |

#### 3.2 Trazabilidad técnica por elemento

**Flujo de Datos: Búsqueda**
```
Usuario escribe en "Buscar"
    ↓
Debounce 300ms
    ↓
JavaScript: construir query params
    ↓
GET /api/clientes?buscar={texto}&rfc={rfc}&estatus={estatus}&usoCfdi={codigo}
    ↓
Backend: ClienteController.getAllClientes()
    ↓
ClienteService.buscarConFiltros()
    ↓
ClienteRepository.findByFiltros() → SQL Query con LIKE
    ↓
Retorna List<Cliente>
    ↓
Frontend: renderizar tabla
```

**Flujo de Datos: Crear Cliente**
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
POST /api/clientes + Body JSON
    ↓
Backend: ClienteController.createCliente()
    ↓
Validar RFC único
    ↓
Validar formato RFC según tipo_persona
    ↓
ClienteService.crear()
    ↓
ClienteRepository.save()
    ↓
Retorna 201 Created + Cliente creado
    ↓
Frontend: recargar tabla + toast éxito
```

**Flujo de Datos: Editar Cliente**
```
Usuario hace clic en "Ver" (tabla)
    ↓
Verificar permiso puede_ver
    ↓
GET /api/clientes/{id}
    ↓
Backend: ClienteController.getClienteById()
    ↓
ClienteRepository.findById()
    ↓
Retorna Cliente
    ↓
Frontend: poblar formulario
    ↓
Verificar permiso puede_editar
    ↓
SI puede_editar == TRUE: habilitar inputs
SI puede_editar == FALSE: disabled=true + badge "SOLO LECTURA"
    ↓
Usuario modifica datos
    ↓
Usuario hace clic en "Guardar"
    ↓
PUT /api/clientes/{id} + Body JSON
    ↓
Backend: ClienteController.updateCliente()
    ↓
Validar RFC único (excepto el mismo registro)
    ↓
ClienteService.actualizar()
    ↓
ClienteRepository.save()
    ↓
Retorna 200 OK + Cliente actualizado
    ↓
Frontend: recargar tabla + toast éxito
```

#### 3.3 Mapeo a base de datos (SSoT y Auditoría Forense)

Para garantizar la integridad y segregación de información, la tabla de clientes incorpora el patrón de **Gobernanza Multi-Sucursal** y la **Auditoría Forense Cifrada** implementada en la v3.2 del sistema.

**Tabla: `cliente`**

```sql
CREATE TABLE cliente (
    -- Identificación
    id_paciente          VARCHAR(36) PRIMARY KEY,           -- UUID/ID CLT
    
    -- Datos principales
    nombre              VARCHAR(200) NOT NULL,             -- Nombre completo o razón social
    rfc                 VARCHAR(13) UNIQUE,                -- RFC único (12 Moral, 13 Física)
    tipo_persona        ENUM('FISICA', 'MORAL') NOT NULL,  -- Tipo de persona
    
    -- Datos fiscales
    uso_cfdi            VARCHAR(10),                       -- G01, G02, G03, P01, etc.
    regimen_fiscal      VARCHAR(10),                       -- 612, 601, 626, etc.
    domicilio_fiscal    TEXT,                              -- Dirección fiscal completa
    
    -- Datos de contacto
    telefono            VARCHAR(20),                       -- Teléfono de contacto
    correo              VARCHAR(100),                      -- Email de contacto
    
    -- Control y Segregación (SSoT)
    id_sucursal         VARCHAR(36) NOT NULL,              -- Sincronización Multi-Sucursal
    estatus             ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    fusionado_con       VARCHAR(20),                       -- ID del cliente maestro si fue fusionado
    
    -- Auditoría Forense Cifrada (AuditPatternService)
    uuid_auditoria      VARCHAR(36) UNIQUE,                -- Enlace a Bitácora Forense
    hash_forense        VARCHAR(64),                       -- Firma SHA-256 de integridad
    fecha_creacion      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_creacion    VARCHAR(50),
    usuario_modificacion VARCHAR(50),
    
    -- Índices
    INDEX idx_nombre (nombre),
    INDEX idx_rfc (rfc),
    INDEX idx_estatus (estatus),
    INDEX idx_uso_cfdi (uso_cfdi),
    INDEX idx_tipo_persona (tipo_persona),
    
    -- Constraints
    CONSTRAINT chk_rfc_fisica CHECK (
        (tipo_persona = 'FISICA' AND LENGTH(rfc) = 13) OR
        (tipo_persona = 'MORAL' AND LENGTH(rfc) = 12) OR
        rfc IS NULL
    ),
    
    CONSTRAINT chk_uso_cfdi_valido CHECK (
        uso_cfdi IN ('G01', 'G02', 'G03', 'I01', 'I02', 'I03', 'I04', 'I05', 'I06', 'I07', 'I08', 'P01', 'D01', 'D02', 'D03', 'D04', 'D05', 'D06', 'D07', 'D08', 'D09', 'D10', 'S01', 'CP01', 'CN01')
        OR uso_cfdi IS NULL
    )
);
```

**Relaciones futuras (Etapas posteriores):**
- `venta.id_cliente` → `cliente.id_cliente` (Etapa 3)
- `cita.id_cliente` → `cliente.id_cliente` (Etapa 4)
- `expediente.id_cliente` → `cliente.id_cliente` (Etapa 4)

---

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 2.1

#### 4.1 Matriz de Control de Permisos - CLIENTES

**Tabla de Control de Acceso y Comportamiento UI**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **1** | **Acceso al módulo** | `puede_ver` | Carga `clientes.html` normalmente | Redirige a `menu.html` + Toast "Acceso Denegado" | `GET /api/clientes` retorna 403 |
| **2** | **Barra de búsqueda** | `puede_ver` | Habilitada, permite buscar | *(No aplica, requiere acceso)* | Solo lectura |
| **3** | **Filtros (RFC, Estatus, Uso CFDI)** | `puede_ver` | Habilitados, permiten filtrar | *(No aplica, requiere acceso)* | Solo lectura |
| **4** | **Tabla de listado** | `puede_ver` | Muestra todos los clientes | *(No aplica, requiere acceso)* | Solo lectura |
| **5** | **Botón "Ver" (tabla)** | `puede_ver` | Habilitado, abre detalle | *(No aplica, requiere acceso)* | Solo lectura |
| **6** | **Botón "Nuevo"** | `puede_crear` | Habilitado, limpia formulario y permite captura | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `POST /api/clientes` retorna 403 |
| **7** | **Formulario de detalle (inputs)** | `puede_editar` | Inputs habilitados, permite edición | `disabled=true` en TODOS los inputs + clase `.input-readonly` | `PUT /api/clientes/{id}` retorna 403 |
| **8** | **Botón "Guardar"** | `puede_editar` | Visible y habilitado | `display: none` (oculto) | `PUT /api/clientes/{id}` retorna 403 |
| **9** | **Botón "Cancelar"** | `puede_ver` | Siempre habilitado | Siempre habilitado | No requiere validación |
| **10** | **Botón "Duplicar"** | `puede_crear` | Habilitado, copia datos a nuevo registro | `disabled=true` + clase `.btn-disabled` | `POST /api/clientes` retorna 403 |
| **11** | **Botón "Eliminar"** | `puede_eliminar` | Habilitado, cambia estatus a "Inactivo" | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `DELETE /api/clientes/{id}` retorna 403 |
| **12** | **Selector "Estatus"** | `puede_eliminar` | Habilitado, permite cambiar Activo/Inactivo | `disabled=true` + tooltip "No tiene permiso para cambiar estatus" | `PUT /api/clientes/{id}` valida permiso |
| **12** | **Selector "Estatus"** | `puede_eliminar` | Habilitado, permite cambiar Activo/Inactivo | `disabled=true` + tooltip "No tiene permiso para cambiar estatus" | `PUT /api/clientes/{id}` valida permiso |
| **14** | **Botón "Exportar"** | `puede_ver` | Habilitado, exporta a CSV/Excel | Habilitado (solo lectura) | Solo lectura |
| **15** | **Botón "Volver"** | `puede_ver` | Siempre habilitado | Siempre habilitado | No requiere validación |
| **16** | **Badge "MODO SOLO LECTURA"** | `!puede_editar` | No se muestra | Se muestra en formulario | Visual |

#### 4.2 Reglas cerradas (Políticas de Seguridad)

```pseudocode
REGLA 1: Acceso al Módulo
    AL CARGAR clientes.html:
        SI permisosActuales.puede_ver == FALSE THEN
            window.location.href = 'menu.html'
            mostrarToast('No tiene acceso al módulo de Clientes', 'error')
            BREAK
        ENDIF

REGLA 2: Botón "Nuevo"
    AL INICIALIZAR UI:
        SI permisosActuales.puede_crear == FALSE THEN
            btnNuevo.disabled = true
            btnNuevo.classList.add('btn-disabled')
            btnNuevo.title = 'No tiene permiso para crear clientes'
        ENDIF

REGLA 3: Modo Edición vs Solo Lectura
    AL HACER CLIC EN "Ver" (seleccionar cliente):
        SI permisosActuales.puede_editar == FALSE THEN
            // Deshabilitar todos los inputs
            document.querySelectorAll('#formCliente input, #formCliente select').forEach(campo => {
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

REGLA 5: Fusión de Duplicados
    AL HACER CLIC EN "Fusión de duplicados":
        SI permisosActuales.puede_eliminar == FALSE THEN
            mostrarToast('Esta función requiere permisos de administrador', 'error')
            BREAK
        ENDIF

REGLA 6: Validación al Guardar
    AL HACER CLIC EN "Guardar":
        SI permisosActuales.puede_editar == FALSE THEN
            mostrarToast('No tiene permiso para modificar clientes', 'error')
            BREAK
        ENDIF
        
        // Continuar con validaciones y guardado
```

#### 4.3 Flujos if/then (Reglas de Negocio)

**Regla 1: Validación de RFC**
```pseudocode
SI tipo_persona == "FISICA" THEN
    Validar LENGTH(rfc) == 13
    Validar formato: 4 letras + 6 dígitos (YYMMDD) + 3 caracteres
ELSE SI tipo_persona == "MORAL" THEN
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
SI nombre == "" OR nombre == NULL THEN
    Mostrar error "El nombre es obligatorio"
    No permitir guardar
ENDIF

SI tipo_persona == "" OR tipo_persona == NULL THEN
    Mostrar error "Debe seleccionar el tipo de persona"
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

**Regla 5: Uso CFDI y Régimen Fiscal**
```pseudocode
SI uso_cfdi != "" AND uso_cfdi != NULL THEN
    Validar uso_cfdi IN (catálogo SAT válido)
    
    SI código inválido THEN
        Mostrar error "Uso CFDI no válido según catálogo SAT"
        No permitir guardar
    ENDIF
ENDIF

SI regimen_fiscal != "" AND regimen_fiscal != NULL THEN
    Validar regimen_fiscal IN (catálogo SAT válido)
    Validar coherencia con tipo_persona
    
    SI inválido THEN
        Mostrar error "Régimen fiscal no válido"
        No permitir guardar
    ENDIF
ENDIF
```



#### 4.4 Validaciones específicas

**Frontend (JavaScript - clientes-service.js):**

```javascript
validarFormulario() {
    const errores = [];
    
    // Nombre obligatorio
    if (!this.nombre || this.nombre.trim() === '') {
        errores.push('El nombre es obligatorio');
    }
    
    // Tipo persona obligatorio
    if (!this.tipo_persona) {
        errores.push('Debe seleccionar el tipo de persona');
    }
    
    // Validar RFC si se proporciona
    if (this.rfc) {
        if (this.tipo_persona === 'FISICA' && this.rfc.length !== 13) {
            errores.push('RFC de persona física debe tener 13 caracteres');
        }
        if (this.tipo_persona === 'MORAL' && this.rfc.length !== 12) {
            errores.push('RFC de persona moral debe tener 12 caracteres');
        }
        
        // Formato RFC
        const rfcRegex = /^[A-ZÑ&]{3,4}\d{6}[A-Z0-9]{3}$/;
        if (!rfcRegex.test(this.rfc)) {
            errores.push('Formato de RFC inválido');
        }
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
```

**Backend (Java - ClienteService.java):**

```java
public void validarCliente(Cliente cliente) throws ValidationException {
    List<String> errores = new ArrayList<>();
    
    // Nombre obligatorio
    if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
        errores.add("El nombre es obligatorio");
    }
    
    // Tipo persona obligatorio
    if (cliente.getTipoPersona() == null) {
        errores.add("El tipo de persona es obligatorio");
    }
    
    // Validar RFC único
    if (cliente.getRfc() != null) {
        Optional<Cliente> existente = clienteRepository.findByRfc(cliente.getRfc());
        if (existente.isPresent() && !existente.get().getIdCliente().equals(cliente.getIdCliente())) {
            errores.add("El RFC ya está registrado");
        }
        
        // Validar longitud según tipo persona
        if (cliente.getTipoPersona() == TipoPersona.FISICA && cliente.getRfc().length() != 13) {
            errores.add("RFC de persona física debe tener 13 caracteres");
        }
        if (cliente.getTipoPersona() == TipoPersona.MORAL && cliente.getRfc().length() != 12) {
            errores.add("RFC de persona moral debe tener 12 caracteres");
        }
    }
    
    // Validar uso CFDI
    if (cliente.getUsoCfdi() != null && !catalogoSAT.isUsoCfdiValido(cliente.getUsoCfdi())) {
        errores.add("Uso CFDI no válido según catálogo SAT");
    }
    
    // Validar régimen fiscal
    if (cliente.getRegimenFiscal() != null && !catalogoSAT.isRegimenValido(cliente.getRegimenFiscal())) {
        errores.add("Régimen fiscal no válido según catálogo SAT");
    }
    
    if (!errores.isEmpty()) {
        throw new ValidationException(String.join(", ", errores));
    }
}
```

#### 4.5 Endpoints API REST

```
GET    /api/clientes                    # Listar todos con filtros opcionales
GET    /api/clientes/{id}               # Obtener por ID
POST   /api/clientes                    # Crear nuevo cliente
PUT    /api/clientes/{id}               # Actualizar cliente existente
DELETE /api/clientes/{id}               # Desactivar cliente (soft delete)
POST   /api/clientes/fusionar           # Fusionar clientes duplicados
GET    /api/clientes/duplicados         # Detectar posibles duplicados
GET    /api/clientes/exportar           # Exportar a CSV/Excel
```

**Detalle de Endpoints:**

**1. GET /api/clientes**
```
Query Params:
  - buscar: string (opcional) - Búsqueda en nombre, correo
  - rfc: string (opcional) - Filtro exacto por RFC
  - estatus: string (opcional) - ACTIVO | INACTIVO
  - usoCfdi: string (opcional) - Código uso CFDI
  - page: int (opcional) - Número de página (default: 0)
  - size: int (opcional) - Tamaño de página (default: 20)

Response 200:
{
  "content": [Cliente],
  "totalElements": int,
  "totalPages": int,
  "number": int
}
```

**2. POST /api/clientes**
```
Request Body:
{
  "nombre": "Juan Pérez García",
  "rfc": "PEGJ800101AB1",
  "tipo_persona": "FISICA",
  "uso_cfdi": "G03",
  "regimen_fiscal": "612",
  "telefono": "5512345678",
  "correo": "juan.perez@mail.com",
  "domicilio_fiscal": "Av. Centro 100, Col. Centro, CDMX, C.P. 06000"
}

Response 201:
{
  "id_cliente": "CLT-00045",
  "nombre": "Juan Pérez García",
  ...
}
```

**3. POST /api/clientes/fusionar**
```
Request Body:
{
  "maestroId": "CLT-00045",
  "duplicadosIds": ["CLT-00123", "CLT-00456"]
}

Response 200:
{
  "mensaje": "Clientes fusionados exitosamente",
  "clienteMaestro": "CLT-00045",
  "clientesFusionados": 2
}
```

#### 4.6 Criterios de Aceptación

- [x] El usuario puede crear un cliente con todos los datos fiscales
- [x] El sistema valida el formato del RFC según tipo de persona (12 o 13 caracteres)
- [x] No se permiten RFCs duplicados en el sistema
- [x] El usuario puede buscar clientes por nombre, RFC o uso CFDI
- [x] El usuario puede editar datos de un cliente existente
- [x] El usuario puede desactivar un cliente (no se elimina físicamente)
- [x] El sistema controla el acceso según permisos del usuario (Ver, Crear, Editar, Eliminar)
- [x] El usuario administrador puede fusionar clientes duplicados
- [x] Todos los cambios quedan auditados (usuario, fecha)
- [x] La interfaz es consistente con el módulo de Usuarios (ETAPA 1)
- [x] El formulario muestra modo "SOLO LECTURA" si el usuario no tiene permiso de edición
- [x] Los botones se deshabilitan según los permisos del usuario
- [x] Las validaciones se ejecutan en frontend (UX) y backend (seguridad)

---

**Última actualización:** 22 de febrero de 2026, 03:00 PM  
**Estado:** Sincronizado con v3.2 (Auditoría Forense)  
**Siguiente:** Módulo Proveedores (Sincronización v1.1)
