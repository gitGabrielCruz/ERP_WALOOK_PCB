---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Inventarios (Parte 1)  
**VERSIÓN:** 2.1  
**FECHA:** 21 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto / Antigravity AI  

---

# 📦 ETAPA 2 - MÓDULO INVENTARIOS


## 2.3 INCISO 2.3 — MÓDULO INVENTARIOS (Inventarios.svg)

---

## INFORMACIÓN DEL MÓDULO

**Módulo:** Inventarios (M01)  
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Inventarios (Parte 1)  
**VERSIÓN:** 3.0 (Layout 50/50 & Ergonómico)  
**FECHA:** 21 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto / Antigravity AI  

---

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales (Historias de Usuario)

| ID HU | Título | Descripción | Prioridad |
|-------|--------|-------------|-----------|
| **HU-M01-01** | Registrar producto | CRUD completo con categorización jerárquica (Grupo/Familia/Marca) y datos fiscales. | Alta |
| **HU-M01-02** | Consultar producto | Búsqueda por SKU, nombre, marca o código de barras con indicadores de stock. | Alta |
| **HU-M01-03** | Editar producto | Actualización de parámetros de stock, precios y ubicaciones. | Alta |
| **HU-M01-04** | Desactivar producto | Baja lógica mediante cambio de estatus en ficha técnica (sin borrado directo). | Media |
| **HU-M01-05** | Registrar movimiento | Kardex completo (Entradas, Salidas, Devoluciones, Mermas). | Alta |
| **HU-M01-06** | Consultar kardex | Historial cronológico con saldos calculados por sucursal. | Alta |
| **HU-M01-07** | Alertas stock bajo | Semáforo visual basado en stock mínimo vs operativo. | Media |
| **HU-M01-08** | Ajuste inventario | Conciliación de stock físico vs sistema mediante movimientos de ajuste. | Alta |
| **HU-M01-09** | Datos fiscales CFDI | Gestión de claves SAT 4.0 (ClaveProdServ, Unidad, Impuesto). | Alta |

#### 1.2 Requerimientos no funcionales de diseño premium

- **RNF-07: Control de Lotes/Series:** Rastreabilidad obligatoria para productos con caducidad o electrónicos.
- **RNF-08: Gestión Multi-ubicación:** Identificación de estantes/vitrinas por sucursal.
- **RNF-09: Verdad Única (Kardex):** La existencia no es un campo estático, es el resultado de la suma algebraica de movimientos.

---

### **PANTALLA 1: Inventario de productos (TAB CATÁLOGO)**

**Título:** Inventario de productos  
**Subtítulo:** Control de stock, artículos sobre pedido, servicios y datos fiscales para CFDI

#### **Campos identificados en filtros:**

| Campo UI | Tipo | Descripción | Mapeo BD |
|----------|------|-------------|----------|
| **Buscar** | Input text | Búsqueda por nombre, SKU, Código de Barras | `LIKE` multivariable |
| **Grupo** | Select | Armazones, Lentes, Contactología, etc. | `cat_grupo` |
| **Familia** | Select | Dependiente del Grupo (ej: Oftálmico/Solar) | `cat_family` |
| **Marca** | Select | Ray-Ban, Oakley, Zeiss, etc. | `cat_marca` |
| **Estatus** | Select | Activos / Inactivos | `producto.estatus` |
| **Sucursal** | Select | Centro / Norte (Filtra stock operativo) | `sucursal` |

#### **Campos identificados en tabla maestra:**

| Columna | Tipo | Descripción | Mapeo BD |
|---------|------|-------------|----------|
| **Img** | Miniatura | Foto del producto (placeholder si no hay) | `producto.imagen_url` |
| **SKU** | String | 75XXXXX (Auto-generado) | `producto.sku` |
| **Producto** | String | Nombre + Marca | `producto.nombre` |
| **Grupo/Fam.** | String | Clasificación jerárquica | Concatenado catálogos |
| **Ubicación** | String | Ref. física (Ej: Vitrina A-2) | `producto.ubicacion` |
| **Exist. Operativa** | Integer | Stock real en sucursal seleccionada. | `v_stock_actual` |
| **Estado** | Enum | • OK (verde) / ⚠ Bajo (naranja) / ○ Sin (rojo). | Calculado vs `min` |
| **Estatus** | Activo/Inactivo | Indicador visual de estatus operativo. | `producto.estatus` |
| **Acción** | Botón | Modificar ficha técnica (Edición). | - |

#### **Campos identificados en panel de detalle:**

**COLUMNA IZQUIERDA: Identidad y Stock**
- **SKU:** (Readonly) Prefijo 75 + secuencia.
- **Nombre:** Nombre comercial descriptivo.
- **Grupo:** (Select) Arma parámetros (exige caducidad/es servicio).
- **Familia:** (Select) Sub-categoría vinculada al grupo.
- **Marca:** (Select) Catálogo externo de marcas.
- **Código de Barras:** UPC/EAN para lector láser.
- **Ubicación Física:** Texto libre (ej: Estante 4-B).
- **Imagen URL:** Link a foto para catálogo visual.
- **Stock Mínimo/Máximo:** Parámetros para alertas.
- **Costo Unitario:** Último costo de compra (actualizado por Kardex).
- **% Utilidad:** Margen deseado sobre costo.
- **Precio Venta:** (Calculado) Costo * (1 + Utilidad/100).

**COLUMNA DERECHA: Fiscal y Proveedor**
- **ClaveProdServ SAT:** (8 dígitos) Validado contra catálogo cifrado.
- **ClaveUnidad SAT:** (H87, PR, E48) Validado.
- **Objeto Impuesto:** (02 - Sí objeto) Estándar CFDI 4.0.
- **IVA:** (Select) 16, 8, 0 (Exento para lentes graduados).
- **Estatus Operativo:** (Select) ACTIVO / INACTIVO (Baja lógica).
- **Proveedor Principal:** Vínculo al catálogo de proveedores.

> [!NOTE]
> **Ajuste Ergonómico (Feb 2026):** Se fijó la altura de la tabla a 980px para garantizar la visibilidad de 11 registros simultáneos sin desbordar el contenedor principal. Los paneles mantienen una simetría vertical de 50/50 de ancho.

---

### **PANTALLA 2: Inventarios - CONTROL (TAB CONTROL)**

**Título:** Gestión de Movimientos y Kardex
**Tab activo:** CONTROL

#### **Formulario de Registro de Movimiento:**
- **Tipo de Movimiento:** Entrada Compra, Salida Venta, Ajuste (+/-), Merma, Traspaso.
- **Folio Documento:** Factura o Nota de Remisión externa.
- **Lote / Serie:** (Condicional) Obligatorio si el grupo exige caducidad.
- **Fecha de Vencimiento:** (Condicional) Para productos clínicos.
- **Cantidad:** Número de unidades (Valida stock negativo en salidas).
- **Costo Unitario:** Captura manual en entradas (actualiza el maestro).
- **Observaciones:** Justificación del movimiento (ej: "Diferencia en conteo físico").

#### **Tabla Kardex (Auditoría Forense):**
Muestra el historial completo filtrado por producto/sucursal.
- `ID_MOV | FECHA | TIPO | SKU | DOC | ENTRADA | SALIDA | SALDO | USUARIO`

---

### 🔴 PASO 3 — MAPEO A BASE DE DATOS (Estructura Etapa 2)

**1. Catálogos Base (Normalización Total)**
```sql
CREATE TABLE cat_grupo (
    id_grupo CHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE, -- ARMAZONES, SERVICIOS, etc.
    exige_caducidad BOOLEAN DEFAULT FALSE,
    es_servicio BOOLEAN DEFAULT FALSE
);

CREATE TABLE cat_familia (
    id_familia CHAR(36) PRIMARY KEY,
    id_grupo CHAR(36) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_grupo) REFERENCES cat_grupo(id_grupo)
);

CREATE TABLE cat_marca (
    id_marca CHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);
```

**2. Maestro de Producto**
```sql
CREATE TABLE producto (
    id_producto CHAR(36) PRIMARY KEY,
    sku VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    id_grupo CHAR(36) NOT NULL,
    id_familia CHAR(36) NOT NULL,
    id_marca CHAR(36) NOT NULL,
    codigo_barras VARCHAR(50),
    imagen_url VARCHAR(255),
    ubicacion VARCHAR(100),
    stock_minimo INT DEFAULT 0,
    stock_maximo INT,
    costo_unitario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    porcentaje_utilidad DECIMAL(5,2) DEFAULT 0.00,
    precio_venta DECIMAL(10,2) GENERATED ALWAYS AS (costo_unitario * (1 + (porcentaje_utilidad / 100))) STORED,
    clave_prod_serv VARCHAR(8) NOT NULL,
    clave_unidad VARCHAR(3) NOT NULL,
    objeto_impuesto VARCHAR(2) NOT NULL DEFAULT '02',
    iva_aplicable DECIMAL(5,2) DEFAULT 16.00,
    id_proveedor CHAR(36),
    estatus ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);
```

**3. Movimientos (Kardex)**
```sql
CREATE TABLE movimiento_inventario (
    id_movimiento CHAR(36) PRIMARY KEY,
    folio VARCHAR(50),
    tipo_movimiento ENUM('ENTRADA_COMPRA', 'SALIDA_VENTA', 'MERMA', 'AJUSTE', 'TRASPASO') NOT NULL,
    id_producto CHAR(36) NOT NULL,
    id_sucursal CHAR(36) NOT NULL,
    cantidad INT NOT NULL,
    costo_historico DECIMAL(10,2),
    lote_serie VARCHAR(50),
    fecha_vencimiento DATE,
    id_usuario CHAR(36) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT
);
```

**4. Vista de Stock Real (Verdad Única)**
La vista realiza el balanceo dinámico por sucursal.
```sql
CREATE VIEW v_stock_actual AS
SELECT 
    p.id_producto, p.sku, p.nombre, s.id_sucursal,
    COALESCE(SUM(CASE 
        WHEN m.tipo_movimiento IN ('ENTRADA_COMPRA') THEN m.cantidad
        WHEN m.tipo_movimiento IN ('SALIDA_VENTA', 'MERMA') THEN -m.cantidad
        WHEN m.tipo_movimiento = 'AJUSTE' THEN m.cantidad
        ELSE 0 END), 0) AS existencia_operativa
FROM producto p
CROSS JOIN sucursal s
LEFT JOIN movimiento_inventario m ON p.id_producto = m.id_producto AND s.id_sucursal = m.id_sucursal
GROUP BY p.id_producto, s.id_sucursal;
```

---
**Continúa en Parte 2 (Lógica y Reglas de Negocio)...**
