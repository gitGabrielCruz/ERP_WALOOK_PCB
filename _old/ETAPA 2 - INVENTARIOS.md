# 📦 ETAPA 2 - MÓDULO INVENTARIOS

## 2.3 INCISO 2.3 — MÓDULO INVENTARIOS (Inventarios.svg)

---

## INFORMACIÓN DEL MÓDULO

**Módulo:** Inventarios (M01)  
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Inventarios (Parte 1)  
**VERSIÓN:** 1.0  
**FECHA:** 04 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales (Historias de Usuario)

El módulo de Inventarios implementa las siguientes Historias de Usuario del **Módulo M01** definidas en el Análisis General:

| ID HU | Título | Descripción | Prioridad |
|-------|--------|-------------|-----------|
| **HU-M01-01** | Registrar producto | Como almacén quiero dar de alta un producto para controlar inventario | Alta |
| **HU-M01-02** | Consultar producto | Como vendedor quiero buscar productos por SKU o nombre para verificar existencias | Alta |
| **HU-M01-03** | Editar producto | Como administrador quiero actualizar datos del producto para mantener información vigente | Alta |
| **HU-M01-04** | Desactivar producto | Como administrador quiero desactivar un producto para que no aparezca en ventas sin perder historial | Media |
| **HU-M01-05** | Registrar movimiento | Como almacén quiero registrar entradas/salidas para actualizar existencias | Alta |
| **HU-M01-06** | Consultar kardex | Como administrador quiero ver historial de movimientos para auditar inventario | Alta |
| **HU-M01-07** | Alertas stock bajo | Como almacén quiero recibir alertas de stock mínimo para reabastecer | Media |
| **HU-M01-08** | Ajuste inventario | Como almacén quiero ajustar existencias por conteo físico para corregir diferencias | Alta |
| **HU-M01-09** | Datos fiscales CFDI | Como facturista quiero gestionar claves SAT para emitir CFDI correctamente | Alta |

**Alcance de la Etapa 2:**
- ✅ HU-M01-01: Registrar producto (CRUD completo con datos fiscales)
- ✅ HU-M01-02: Consultar producto (Búsqueda y filtros)
- ✅ HU-M01-03: Editar producto (Formulario completo)
- ✅ HU-M01-04: Desactivar producto (Soft delete)
- ✅ HU-M01-05: Registrar movimiento (Sistema kardex completo)
- ✅ HU-M01-06: Consultar kardex (Historial de movimientos)
- ✅ HU-M01-07: Alertas stock bajo (Indicadores visuales)
- ✅ HU-M01-08: Ajuste inventario (Movimientos de ajuste)
- ✅ HU-M01-09: Datos fiscales CFDI (Claves SAT completas)

#### 1.2 Requerimientos no funcionales reales

| ID RNF | Nombre | Descripción | Aplicación en Inventarios |
|--------|--------|-------------|---------------------------|
| **RNF-02** | Seguridad básica | Control de acceso por permisos | Sistema de permisos granulares (Ver, Crear, Editar, Eliminar) |
| **RNF-03** | Integración frontend-backend | API REST | Endpoints REST completos para CRUD y movimientos |
| **RNF-04** | Consistencia visual | Diseño uniforme | Patrón Master-Detail con tabs (CATÁLOGO/CONTROL) |
| **RNF-05** | Integridad de datos | Validaciones y constraints | SKU único, validaciones fiscales SAT, stock no negativo |
| **RNF-06** | Validaciones operativas | Reglas de negocio | Formato claves SAT, cálculo de utilidad, kardex |

#### 1.3 Casos de uso reales asociados

- **CU-12:** Gestionar catálogo de productos
- **CU-13:** Controlar existencias tipo kardex
- **CU-14:** Registrar movimientos de inventario
- **CU-15:** Validar datos fiscales para CFDI

#### 1.4 Diagramas que aplican

- **Diagrama de Navegabilidad:** Módulo Inventarios (Anexo E, Figura 10)
- **Diagrama Entidad-Relación:** Tablas `producto`, `movimiento_inventario` (Anexo D, Figura 7)
- **Modelo Lógico 3FN:** Normalización de datos de productos y movimientos (Anexo D, Figura 9)
- **Diagrama de Clases:** Clases `Producto`, `MovimientoInventario` (Anexo C)

#### 1.5 Datos literales del diseño (Pantallas Inventarios.svg)

**Base de datos:** `optica_erp`

**Tablas principales:** `producto`, `movimiento_inventario`

---

### **PANTALLA 1: Inventario de productos (TAB CATÁLOGO)**

**Título:** Inventario de productos  
**Subtítulo:** Control de stock, artículos sobre pedido, servicios y datos fiscales para CFDI

#### **Campos identificados en filtros:**

| Campo UI | Tipo | Descripción | Mapeo BD |
|----------|------|-------------|----------|
| **Buscar** | Input text | Búsqueda por nombre, descripción, código | Query LIKE en múltiples campos |
| **SKU** | Input text | Filtro por SKU exacto | `producto.sku` |
| **Estatus** | Select | Activos / Inactivos | `producto.estatus` |
| **Categoría** | Select | Armazones, Lentes, Accesorios, Consumibles | `producto.categoria` |

#### **Campos identificados en tabla maestra:**

| Columna | Tipo | Descripción | Mapeo BD |
|---------|------|-------------|----------|
| **SKU** | String | 7500001, 7500002, etc. | `producto.sku` (UNIQUE) |
| **Producto** | String | Nombre del producto | `producto.nombre` |
| **Categoría** | Enum | Armazones, Lentes, Accesorios, Consumibles | `producto.categoria` |
| **Exist.** | Integer | Existencia actual | Calculado (SUM movimientos) |
| **Mínimo** | Integer | Stock mínimo | `producto.stock_minimo` |
| **Estado** | Enum | OK (verde), Bajo (naranja), Sin existencias (rojo) | Calculado |
| **Ver** | Botón | Abre detalle | - |

**Datos de ejemplo en tabla:**

```
7500001 | Armazón Classic RB3016      | Armazones   | 43  | 20  | • OK
7500002 | Lente CR-39 1.50            | Lentes      | 120 | 50  | • OK
7500003 | Policarbonato fotocromático | Lentes      | 6   | 20  | ⚠ Bajo
7500004 | Estuche rígido universal    | Accesorios  | 85  | 30  | • OK
```

#### **Campos identificados en panel de detalle - COLUMNA IZQUIERDA:**

| Campo | Tipo | Descripción | Mapeo BD |
|-------|------|-------------|----------|
| **SKU** | Input text (readonly) | 7500001 | `producto.sku` |
| **Producto** | Input text | Armazón Classic RB3016 | `producto.nombre` |
| **Categoría** | Select | Armazones, Lentes, Accesorios, Consumibles | `producto.categoria` |
| **Marca** | Input text | Ray-Ban | `producto.marca` |
| **Código de barras** | Input text | 750123456789 | `producto.codigo_barras` |
| **Tipo de inventario** | Select | Stock en tienda, Disponible a pedido, Servicio | `producto.tipo_inventario` |
| **Estatus** | Select | Activo, Inactivo | `producto.estatus` |
| **Existencia total** | Input number (readonly) | 43 | Calculado |
| **Mínimo** | Input number | 20 | `producto.stock_minimo` |
| **Costo Unitario** | Input decimal | $600.00 | `producto.costo_unitario` |
| **% Utilidad** | Input number | 80 | `producto.porcentaje_utilidad` |
| **Precio Venta** | Input decimal (calculado) | $900.00 | Calculado: `costo × (1 + utilidad/100)` |

**Opciones de Categoría:**
- Armazones
- Lentes
- Accesorios
- Consumibles

**Opciones de Tipo de Inventario:**
- Stock en tienda
- Disponible a pedido
- Servicio

#### **Campos identificados en panel de detalle - COLUMNA DERECHA (Datos fiscales CFDI):**

| Campo | Tipo | Descripción | Mapeo BD |
|-------|------|-------------|----------|
| **ClaveProdServ SAT** | Input text | 85121603 | `producto.clave_prod_serv` |
| **ClaveUnidad** | Input text | H87 (Pieza) | `producto.clave_unidad` |
| **Objeto de impuesto** | Input text | 02 — Sí objeto de impuesto | `producto.objeto_impuesto` |
| **IVA aplicable** | Input number | 16 % | `producto.iva_aplicable` |

**Botón adicional:**
- **Ver ficha fiscal completa** (Abre modal con información fiscal detallada)

#### **Botones identificados (TAB CATÁLOGO):**

| Botón | Color | Descripción |
|-------|-------|-------------|
| **Guardar** | Azul | Crear/Actualizar producto |
| **Cancelar** | Gris | Limpiar formulario |
| **Nuevo** | Verde | Nuevo producto |
| **Duplicar** | Azul claro | Duplicar producto |
| **Eliminar** | Rojo | Desactivar producto |
| **Ajuste / Conteo** | Naranja | Ajuste de inventario |
| **Movimientos** | Outline | Cambiar a tab CONTROL |
| **Kardex** | Outline | Ver kardex del producto |
| **Importar** | Outline | Importar desde CSV/Excel |
| **Exportar** | Outline | Exportar a CSV/Excel |
| **Ir a Reportes de Rotación / Valorización** | Link | Navegar a reportes |
| **Volver** | Link | Regresar a menú |

---

### **PANTALLA 2: Inventarios - CONTROL (TAB CONTROL)**

**Título:** Inventarios  
**Subtítulo:** Catálogo de Productos y Control de Existencias  
**Tab activo:** CONTROL

#### **Campos identificados en selector de tipo:**

| Campo | Tipo | Valores |
|-------|------|---------|
| **Tipo de movimiento** | Select | Entrada por compra, Salida por venta, Devolución proveedor, Merma, Ajuste inventario |
| **Nuevo movimiento** | Button (verde) | Abre formulario de registro |

**Opciones de Tipo de Movimiento:**
1. Entrada por compra
2. Salida por venta
3. Devolución a proveedor
4. Devolución de cliente
5. Merma
6. Ajuste de inventario
7. Traspaso entre sucursales

#### **Campos identificados en formulario de movimiento:**

**Título del formulario:** Registrar Movimiento de Inventario

| Campo | Tipo | Descripción | Mapeo BD |
|-------|------|-------------|----------|
| **ID Mov** | Input text (readonly) | MOV-00124 (Auto-generado) | `movimiento_inventario.id_movimiento` |
| **Folio** | Input text | FC-A-12345 (Factura/Documento externo) | `movimiento_inventario.folio` |
| **Tipo** | Select | Entrada por compra, Salida venta, etc. | `movimiento_inventario.tipo` |
| **Fecha** | Date | 03/02/2026 | `movimiento_inventario.fecha` |
| **Proveedor** | Select | Ópticos del Centro S.A. (condicional) | `movimiento_inventario.id_proveedor` |
| **Producto** | Select searchable | Armazón Ray-Ban RB5154 | `movimiento_inventario.id_producto` |
| **Cantidad** | Input number | 1 (con botones +/-) | `movimiento_inventario.cantidad` |
| **Costo unitario** | Input decimal | $0.00 | `movimiento_inventario.costo_unitario` |
| **Saldo** | Display (readonly) | $0.00 (Calculado: cantidad × costo) | Calculado |
| **Observaciones** | Textarea | (opcional) | `movimiento_inventario.observaciones` |

#### **Botones de acción (CONTROL):**

| Botón | Color | Acción |
|-------|-------|--------|
| **Registrar movimiento** | Verde | Crear movimiento y actualizar stock |
| **Cancelar** | Gris | Limpiar formulario |

#### **Campos identificados en tabla Kardex:**

**Título:** Kardex - Historial de Movimientos

| Columna | Tipo | Descripción |
|---------|------|-------------|
| **ID Mov** | String | MOV-00124 |
| **Folio** | String | FC-A-1234 |
| **Fecha** | Date | 03/02/2026 |
| **Tipo** | String | Entrada compra, Salida venta, etc. |
| **Producto** | String | Armazón Ray-Ban |
| **Entrada** | Integer | +50 (verde) |
| **Salida** | Integer | -5 (rojo) |
| **Saldo** | Integer | 95 |
| **Usuario** | String | admin |

**Datos de ejemplo en Kardex:**

```
MOV-00124 | FC-A-1234  | 03/02/2026 | Entrada compra | Armazón Ray-Ban | +50  | -    | 95  | admin
MOV-00123 | VTA-00089  | 01/02/2026 | Salida venta   | Armazón Ray-Ban | -    | -5   | 45  | caja01
MOV-00122 | MER-00015  | 28/01/2026 | Merma          | Armazón Ray-Ban | -    | -2   | 50  | almacen
```

---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO POR ETAPAS

#### 2.1 Pertenencia a Etapa 2

**Confirmación:** El módulo Inventarios pertenece a **Etapa 2: Gestión Operativa Principal** ✅

**Justificación:**
- Forma parte del RF-09 (Gestión de Inventarios)
- Representa el 7% del proyecto total (35% de la Etapa 2)
- Es prerequisito para módulos de ventas y facturación
- Incluye datos fiscales necesarios para CFDI

#### 2.2 RF que se cierran en 2.3

| RF | Descripción | Estado |
|----|-------------|--------|
| **RF-09** | Gestión de Inventarios | ✅ Se cierra completamente |
| **RF-05** | Comunicación API | ✅ Endpoints `/api/productos`, `/api/movimientos` |
| **RF-06** | Persistencia básica | ✅ CRUD completo + Kardex en BD |

#### 2.3 Qué NO se puede tocar (Reservado a etapas futuras)

| Funcionalidad | Etapa Reservada | Motivo |
|---------------|-----------------|--------|
| Facturación CFDI | Etapa 3 | Módulo de Facturación |
| Reportes de rotación | Etapa 4 | Módulo de Reportes |
| Valorización de inventario | Etapa 4 | Módulo de Reportes |
| Auditoría completa | Etapa 5 | Bitácoras avanzadas |
| Traspaso entre sucursales | Etapa 6 | Módulo de Sucursales |

---

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL (UI → LÓGICA → DATOS)

#### 3.1 Elementos de la interfaz

**TAB 1: CATÁLOGO**

**SECCIÓN SUPERIOR: Filtros de Búsqueda**

| Elemento | Tipo | Función | Evento | Acción Backend |
|----------|------|---------|--------|----------------|
| Campo "Buscar" | `<input type="text">` | Búsqueda libre por nombre, descripción, código | `keyup` (debounce 300ms) | `GET /api/productos?buscar={texto}` |
| Campo "SKU" | `<input type="text">` | Filtro exacto por SKU | `change` | `GET /api/productos?sku={sku}` |
| Selector "Estatus" | `<select>` | Filtro Activos/Inactivos | `change` | `GET /api/productos?estatus={valor}` |
| Selector "Categoría" | `<select>` | Filtro por categoría | `change` | `GET /api/productos?categoria={valor}` |

**SECCIÓN MEDIA: Tabla Maestra (Listado de Productos)**

| Columna | Tipo Dato | Ordenable | Acción |
|---------|-----------|-----------|--------|
| SKU | String | ✅ | - |
| Producto | String | ✅ | Click → Cargar detalle |
| Categoría | Enum | ✅ | - |
| Exist. | Integer | ✅ | - |
| Mínimo | Integer | ✅ | - |
| Estado | Enum | ✅ | Indicador visual (• OK, ⚠ Bajo, ○ Sin) |
| Ver | Botón | ❌ | Click → Cargar formulario detalle |

**SECCIÓN INFERIOR: Panel de Detalle (Formulario)**

**COLUMNA IZQUIERDA: Datos del Producto**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| SKU | `<input readonly>` | - | Auto-generado | `producto.sku` |
| Producto | `<input type="text" required>` | No vacío, max 200 chars | NOT NULL | `producto.nombre` |
| Categoría | `<select required>` | Selección obligatoria | ENUM válido | `producto.categoria` |
| Marca | `<input type="text">` | Max 100 chars | - | `producto.marca` |
| Código de barras | `<input type="text">` | Max 50 chars | - | `producto.codigo_barras` |
| Tipo de inventario | `<select required>` | Selección obligatoria | ENUM válido | `producto.tipo_inventario` |
| Estatus | `<select>` | Activo/Inactivo | ENUM válido | `producto.estatus` |
| Existencia total | `<input readonly>` | - | Calculado | Calculado (SUM) |
| Mínimo | `<input type="number" required>` | >= 0 | >= 0 | `producto.stock_minimo` |
| Costo Unitario | `<input type="number" required>` | > 0 | > 0 | `producto.costo_unitario` |
| % Utilidad | `<input type="number" required>` | >= 0 | >= 0 | `producto.porcentaje_utilidad` |
| Precio Venta | `<input readonly>` | - | Calculado | Calculado |

**Cálculo de Precio Venta:**
```javascript
Precio Venta = Costo Unitario × (1 + (% Utilidad / 100))
```

**COLUMNA DERECHA: Datos Fiscales (CFDI)**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| ClaveProdServ SAT | `<input type="text" required>` | Formato SAT (8 dígitos) | Catálogo SAT válido | `producto.clave_prod_serv` |
| ClaveUnidad | `<input type="text" required>` | Formato SAT (3 chars) | Catálogo SAT válido | `producto.clave_unidad` |
| Objeto de impuesto | `<input type="text" required>` | Formato SAT (2 dígitos) | Catálogo SAT válido | `producto.objeto_impuesto` |
| IVA aplicable | `<input type="number" required>` | 0, 8, 16 | Valor válido | `producto.iva_aplicable` |

**Botones de Acción (CATÁLOGO):**

| Botón | Clase CSS | Permiso Requerido | Acción | Endpoint |
|-------|-----------|-------------------|--------|----------|
| Guardar | `.btn-primary` | `puede_editar` o `puede_crear` | Crear/Actualizar producto | `POST /PUT /api/productos` |
| Cancelar | `.btn-secondary` | `puede_ver` | Limpiar formulario | - |
| Nuevo | `.btn-success` | `puede_crear` | Limpiar formulario para nuevo registro | - |
| Duplicar | `.btn-info` | `puede_crear` | Copiar datos a nuevo registro | - |
| Eliminar | `.btn-danger` | `puede_eliminar` | Soft delete (estatus=Inactivo) | `DELETE /api/productos/{id}` |
| Ajuste / Conteo | `.btn-warning` | `puede_editar` | Abrir modal de ajuste de inventario | Modal |
| Movimientos | `.btn-outline` | `puede_ver` | Cambiar a tab CONTROL | Tab switch |
| Kardex | `.btn-outline` | `puede_ver` | Mostrar kardex del producto | `GET /api/movimientos?producto={id}` |
| Importar | `.btn-outline` | `puede_crear` | Importar desde CSV/Excel | `POST /api/productos/importar` |
| Exportar | `.btn-outline` | `puede_ver` | Exportar a CSV/Excel | `GET /api/productos/exportar` |
| Ir a Reportes | `.btn-link` | `puede_ver` | Navegar a módulo de reportes | Navegación |
| Volver | `.btn-link` | `puede_ver` | Regresar a menú | - |

**TAB 2: CONTROL**

**SECCIÓN SUPERIOR: Selector de Tipo**

| Elemento | Tipo | Función | Evento |
|----------|------|---------|--------|
| Tipo de movimiento | `<select>` | Filtro de tipo de movimiento | `change` |
| Nuevo movimiento | `<button>` | Abre formulario de registro | `click` |

**SECCIÓN MEDIA: Formulario de Movimiento**

| Campo | HTML | Validación Frontend | Validación Backend | Mapeo BD |
|-------|------|---------------------|-------------------|----------|
| ID Mov | `<input readonly>` | - | Auto-generado | `movimiento_inventario.id_movimiento` |
| Folio | `<input type="text">` | Max 50 chars | - | `movimiento_inventario.folio` |
| Tipo | `<select required>` | Selección obligatoria | ENUM válido | `movimiento_inventario.tipo` |
| Fecha | `<input type="date" required>` | No futuro | No futuro | `movimiento_inventario.fecha` |
| Producto | `<select required>` | Producto debe existir | FK válida | `movimiento_inventario.id_producto` |
| Cantidad | `<input type="number" required>` | > 0 | > 0 | `movimiento_inventario.cantidad` |
| Costo unitario | `<input type="number">` | >= 0 | >= 0 | `movimiento_inventario.costo_unitario` |
| Saldo | `<input readonly>` | - | Calculado | Calculado |

**Botones de Acción (CONTROL):**

| Botón | Clase CSS | Permiso Requerido | Acción | Endpoint |
|-------|-----------|-------------------|--------|----------|
| Registrar movimiento | `.btn-success` | `puede_crear` | Crear movimiento y actualizar stock | `POST /api/movimientos` |
| Cancelar | `.btn-secondary` | `puede_ver` | Limpiar formulario | - |

**SECCIÓN INFERIOR: Tabla Kardex**

| Columna | Tipo Dato | Ordenable | Formato |
|---------|-----------|-----------|---------|
| ID Mov | String | ✅ | MOV-XXXXX |
| Folio | String | ✅ | Texto |
| Fecha | Date | ✅ | DD/MM/YYYY |
| Tipo | String | ✅ | Texto |
| Producto | String | ✅ | Texto |
| Entrada | Integer | ✅ | +XX (verde) |
| Salida | Integer | ✅ | -XX (rojo) |
| Saldo | Integer | ✅ | Número |
| Usuario | String | ✅ | Texto |

#### 3.2 Trazabilidad técnica por elemento

**Flujo de Datos: Crear Producto**
```
Usuario hace clic en "Nuevo"
    ↓
Verificar permiso puede_crear
    ↓
Limpiar formulario
    ↓
Usuario captura datos (nombre, categoría, costos, claves SAT)
    ↓
Sistema calcula Precio Venta = Costo × (1 + Utilidad%)
    ↓
Usuario hace clic en "Guardar"
    ↓
Validaciones Frontend (campos requeridos, formatos)
    ↓
POST /api/productos + Body JSON
    ↓
Backend: ProductoController.createProducto()
    ↓
Validar SKU único (auto-generado)
    ↓
Validar claves SAT en catálogos
    ↓
ProductoService.crear()
    ↓
ProductoRepository.save()
    ↓
Retorna 201 Created + Producto creado
    ↓
Frontend: recargar tabla + toast éxito
```

**Flujo de Datos: Registrar Movimiento**
```
Usuario hace clic en tab "CONTROL"
    ↓
Usuario hace clic en "Nuevo movimiento"
    ↓
Verificar permiso puede_crear
    ↓
Sistema genera ID Movimiento (MOV-XXXXX)
    ↓
Usuario captura: Folio, Tipo, Fecha, Producto, Cantidad, Costo
    ↓
Sistema calcula Saldo = Cantidad × Costo
    ↓
Usuario hace clic en "Registrar movimiento"
    ↓
Validaciones Frontend
    ↓
SI tipo = Salida THEN
    Verificar stock_actual >= cantidad
ENDIF
    ↓
POST /api/movimientos + Body JSON
    ↓
Backend: MovimientoController.createMovimiento()
    ↓
BEGIN TRANSACTION
    INSERT INTO movimiento_inventario
    ACTUALIZAR stock_actual (calculado)
COMMIT TRANSACTION
    ↓
Retorna 201 Created + Movimiento creado
    ↓
Frontend: recargar kardex + toast éxito
```

#### 3.3 Mapeo a base de datos

**Tabla: `producto`**

```sql
CREATE TABLE producto (
    -- Identificación
    sku                 VARCHAR(20) PRIMARY KEY,        -- 7500001, 7500002, etc.
    
    -- Datos principales
    nombre              VARCHAR(200) NOT NULL,
    categoria           ENUM('ARMAZONES', 'LENTES', 'ACCESORIOS', 'CONSUMIBLES', 'OTROS') NOT NULL,
    marca               VARCHAR(100),
    codigo_barras       VARCHAR(50),
    
    -- Tipo de inventario
    tipo_inventario     ENUM('STOCK_TIENDA', 'PEDIDO', 'SERVICIO') DEFAULT 'STOCK_TIENDA',
    
    -- Control de stock
    stock_minimo        INT DEFAULT 0,
    stock_maximo        INT,
    
    -- Costos y precios
    costo_unitario      DECIMAL(10,2) NOT NULL,
    porcentaje_utilidad DECIMAL(5,2) DEFAULT 0.00,
    precio_venta        DECIMAL(10,2) GENERATED ALWAYS AS (costo_unitario * (1 + (porcentaje_utilidad / 100))) STORED,
    
    -- Datos fiscales CFDI
    clave_prod_serv     VARCHAR(8) NOT NULL,            -- Catálogo SAT
    clave_unidad        VARCHAR(3) NOT NULL,            -- Catálogo SAT
    objeto_impuesto     VARCHAR(2) NOT NULL,            -- Catálogo SAT
    iva_aplicable       DECIMAL(5,2) DEFAULT 16.00,
    
    -- Relaciones
    id_proveedor        VARCHAR(20),                    -- FK a proveedor principal
    ubicacion           VARCHAR(50),
    
    -- Control
    estatus             ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    
    -- Auditoría
    fecha_creacion      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_creacion    VARCHAR(50),
    usuario_modificacion VARCHAR(50),
    
    -- Relaciones
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    
    -- Índices
    INDEX idx_nombre (nombre),
    INDEX idx_categoria (categoria),
    INDEX idx_estatus (estatus),
    INDEX idx_codigo_barras (codigo_barras),
    INDEX idx_clave_prod_serv (clave_prod_serv),
    
    -- Constraints
    CONSTRAINT chk_costo_positivo CHECK (costo_unitario > 0),
    CONSTRAINT chk_utilidad_positiva CHECK (porcentaje_utilidad >= 0),
    CONSTRAINT chk_stock_minimo CHECK (stock_minimo >= 0)
);
```

**Tabla: `movimiento_inventario`**

```sql
CREATE TABLE movimiento_inventario (
    -- Identificación
    id_movimiento       VARCHAR(20) PRIMARY KEY,        -- MOV-XXXXX
    folio               VARCHAR(50),                    -- Folio externo (factura, nota)
    
    -- Tipo de movimiento
    tipo                ENUM('ENTRADA_COMPRA', 'SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 
                             'DEVOLUCION_CLIENTE', 'MERMA', 'AJUSTE', 'TRASPASO') NOT NULL,
    
    -- Producto y cantidad
    id_producto         VARCHAR(20) NOT NULL,           -- FK a producto (SKU)
    cantidad            INT NOT NULL,
    costo_unitario      DECIMAL(10,2),
    
    -- Relaciones condicionales
    id_proveedor        VARCHAR(20),                    -- FK (si tipo = ENTRADA_COMPRA)
    id_cliente          VARCHAR(20),                    -- FK (si tipo = DEVOLUCION_CLIENTE)
    id_orden_compra     VARCHAR(20),                    -- FK (si tipo = ENTRADA_COMPRA)
    id_venta            VARCHAR(20),                    -- FK (si tipo = SALIDA_VENTA)
    
    -- Fechas
    fecha               DATE NOT NULL,
    observaciones       TEXT,
    
    -- Auditoría
    usuario             VARCHAR(50) NOT NULL,
    fecha_registro      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Relaciones
    FOREIGN KEY (id_producto) REFERENCES producto(sku),
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_orden_compra) REFERENCES orden_compra(id_oc),
    
    -- Índices
    INDEX idx_producto (id_producto),
    INDEX idx_tipo (tipo),
    INDEX idx_fecha (fecha),
    INDEX idx_folio (folio),
    INDEX idx_usuario (usuario),
    
    -- Constraints
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_costo_no_negativo CHECK (costo_unitario >= 0)
);
```

**Vista: `stock_actual` (Calculado en tiempo real)**

```sql
CREATE VIEW stock_actual AS
SELECT 
    p.sku,
    p.nombre,
    p.categoria,
    COALESCE(SUM(
        CASE 
            WHEN m.tipo IN ('ENTRADA_COMPRA', 'DEVOLUCION_CLIENTE') THEN m.cantidad
            WHEN m.tipo IN ('SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 'MERMA') THEN -m.cantidad
            WHEN m.tipo = 'AJUSTE' THEN m.cantidad  -- Puede ser positivo o negativo
            ELSE 0
        END
    ), 0) AS existencia_actual,
    p.stock_minimo,
    p.stock_maximo,
    CASE
        WHEN COALESCE(SUM(...), 0) = 0 THEN 'SIN_STOCK'
        WHEN COALESCE(SUM(...), 0) < p.stock_minimo THEN 'STOCK_BAJO'
        ELSE 'OK'
    END AS estado_stock
FROM producto p
LEFT JOIN movimiento_inventario m ON p.sku = m.id_producto
GROUP BY p.sku;
```

**Relaciones:**
- `producto.id_proveedor` → `proveedor.id_proveedor` (FK)
- `movimiento_inventario.id_producto` → `producto.sku` (FK)
- `movimiento_inventario.id_proveedor` → `proveedor.id_proveedor` (FK condicional)
- `movimiento_inventario.id_cliente` → `cliente.id_cliente` (FK condicional)
- `movimiento_inventario.id_orden_compra` → `orden_compra.id_oc` (FK condicional)

---

**Continúa en el siguiente bloque...**
