### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 2.3

#### 4.1 Matriz de Control de Permisos - INVENTARIOS

**Tabla de Control de Acceso y Comportamiento UI - TAB CATÁLOGO**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **1** | **Acceso al módulo** | `puede_ver` | Carga `inventarios.html` normalmente | Redirige a `menu.html` + Toast "Acceso Denegado" | `GET /api/productos` retorna 403 |
| **2** | **Barra de búsqueda** | `puede_ver` | Habilitada, permite buscar | *(No aplica, requiere acceso)* | Solo lectura |
| **3** | **Filtros (SKU, Categoría, Estatus)** | `puede_ver` | Habilitados, permiten filtrar | *(No aplica, requiere acceso)* | Solo lectura |
| **4** | **Tabla de listado** | `puede_ver` | Muestra todos los productos | *(No aplica, requiere acceso)* | Solo lectura |
| **5** | **Botón "Ver" (tabla)** | `puede_ver` | Habilitado, abre detalle | *(No aplica, requiere acceso)* | Solo lectura |
| **6** | **Botón "Nuevo"** | `puede_crear` | Habilitado, limpia formulario y permite captura | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `POST /api/productos` retorna 403 |
| **7** | **Formulario de detalle (inputs)** | `puede_editar` | Inputs habilitados, permite edición | `disabled=true` en TODOS los inputs + clase `.input-readonly` | `PUT /api/productos/{id}` retorna 403 |
| **8** | **Botón "Guardar"** | `puede_editar` | Visible y habilitado | `display: none` (oculto) | `PUT /api/productos/{id}` retorna 403 |
| **9** | **Botón "Cancelar"** | `puede_ver` | Siempre habilitado | Siempre habilitado | No requiere validación |
| **10** | **Botón "Duplicar"** | `puede_crear` | Habilitado, copia datos a nuevo registro | `disabled=true` + clase `.btn-disabled` | `POST /api/productos` retorna 403 |
| **11** | **Botón "Eliminar"** | `puede_eliminar` | Habilitado, cambia estatus a "Inactivo" | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `DELETE /api/productos/{id}` retorna 403 |
| **12** | **Selector "Estatus"** | `puede_eliminar` | Habilitado, permite cambiar Activo/Inactivo | `disabled=true` + tooltip "No tiene permiso para cambiar estatus" | `PUT /api/productos/{id}` valida permiso |
| **13** | **Botón "Ajuste / Conteo"** | `puede_editar` | Habilitado, abre modal de ajuste | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `POST /api/movimientos` retorna 403 |
| **14** | **Botón "Movimientos"** | `puede_ver` | Habilitado, cambia a tab CONTROL | Habilitado (solo lectura) | Tab switch |
| **15** | **Botón "Kardex"** | `puede_ver` | Habilitado, muestra kardex del producto | Habilitado (solo lectura) | `GET /api/movimientos?producto={id}` |
| **16** | **Botón "Importar"** | `puede_crear` | Habilitado, importa desde CSV/Excel | `disabled=true` + clase `.btn-disabled` | `POST /api/productos/importar` retorna 403 |
| **17** | **Botón "Exportar"** | `puede_ver` | Habilitado, exporta a CSV/Excel | Habilitado (solo lectura) | Solo lectura |
| **18** | **Badge "MODO SOLO LECTURA"** | `!puede_editar` | No se muestra | Se muestra en formulario | Visual |

**Tabla de Control de Acceso y Comportamiento UI - TAB CONTROL**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **19** | **Tab "CONTROL"** | `puede_ver` | Habilitado, permite acceso | Habilitado (solo lectura) | Solo lectura |
| **20** | **Botón "Nuevo movimiento"** | `puede_crear` | Habilitado, abre formulario | `disabled=true` + clase `.btn-disabled` | `POST /api/movimientos` retorna 403 |
| **21** | **Formulario de movimiento** | `puede_crear` | Habilitado, permite captura | Oculto o deshabilitado | `POST /api/movimientos` retorna 403 |
| **22** | **Botón "Registrar movimiento"** | `puede_crear` | Visible y habilitado | `display: none` (oculto) | `POST /api/movimientos` retorna 403 |
| **23** | **Tabla Kardex** | `puede_ver` | Muestra todos los movimientos | Muestra todos (solo lectura) | Solo lectura |

#### 4.2 Reglas cerradas (Políticas de Seguridad)

```pseudocode
REGLA 1: Acceso al Módulo
    AL CARGAR inventarios.html:
        SI permisosActuales.puede_ver == FALSE THEN
            window.location.href = 'menu.html'
            mostrarToast('No tiene acceso al módulo de Inventarios', 'error')
            BREAK
        ENDIF

REGLA 2: Botón "Nuevo" (TAB CATÁLOGO)
    AL INICIALIZAR UI:
        SI permisosActuales.puede_crear == FALSE THEN
            btnNuevo.disabled = true
            btnNuevo.classList.add('btn-disabled')
            btnNuevo.title = 'No tiene permiso para crear productos'
        ENDIF

REGLA 3: Modo Edición vs Solo Lectura (TAB CATÁLOGO)
    AL HACER CLIC EN "Ver" (seleccionar producto):
        SI permisosActuales.puede_editar == FALSE THEN
            // Deshabilitar todos los inputs
            document.querySelectorAll('#formProducto input, #formProducto select').forEach(campo => {
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

REGLA 5: Cálculo Automático de Precio Venta
    AL CAMBIAR costo_unitario O porcentaje_utilidad:
        precio_venta = costo_unitario * (1 + (porcentaje_utilidad / 100))
        actualizarCampoPrecioVenta(precio_venta)

REGLA 6: Nuevo Movimiento (TAB CONTROL)
    AL HACER CLIC EN "Nuevo movimiento":
        SI permisosActuales.puede_crear == FALSE THEN
            mostrarToast('No tiene permiso para registrar movimientos', 'error')
            BREAK
        ENDIF
        
        // Generar ID automático
        id_movimiento = generarIDMovimiento()  // MOV-XXXXX
        
        // Limpiar formulario
        limpiarFormularioMovimiento()
        
        // Mostrar formulario
        mostrarFormularioMovimiento()

REGLA 7: Validación de Stock en Salidas
    AL REGISTRAR MOVIMIENTO tipo = SALIDA:
        stock_actual = obtenerStockActual(producto)
        
        SI cantidad > stock_actual THEN
            mostrarToast('Stock insuficiente. Disponible: ' + stock_actual, 'error')
            BREAK
        ENDIF

REGLA 8: Alertas de Stock Bajo
    AL CARGAR TABLA DE PRODUCTOS:
        PARA CADA producto EN lista_productos:
            stock_actual = calcularStockActual(producto)
            
            SI stock_actual == 0 THEN
                producto.estado = 'SIN_STOCK'
                producto.clase_css = 'badge-danger'
                producto.icono = '○'
            ELSE SI stock_actual < producto.stock_minimo THEN
                producto.estado = 'STOCK_BAJO'
                producto.clase_css = 'badge-warning'
                producto.icono = '⚠'
            ELSE
                producto.estado = 'OK'
                producto.clase_css = 'badge-success'
                producto.icono = '•'
            ENDIF
        FIN PARA
```

#### 4.3 Flujos if/then (Reglas de Negocio)

**Regla 1: Validación de SKU**
```pseudocode
AL CREAR PRODUCTO:
    Generar SKU automático = "75" + SECUENCIA (5 dígitos)
    
    SI sku ya existe en BD THEN
        Regenerar SKU con siguiente secuencia
    ENDIF
    
    Retornar SKU único
```

**Regla 2: Campos Obligatorios (Producto)**
```pseudocode
SI nombre == "" OR nombre == NULL THEN
    Mostrar error "El nombre del producto es obligatorio"
    No permitir guardar
ENDIF

SI categoria == "" OR categoria == NULL THEN
    Mostrar error "Debe seleccionar la categoría"
    No permitir guardar
ENDIF

SI costo_unitario <= 0 THEN
    Mostrar error "El costo unitario debe ser mayor a 0"
    No permitir guardar
ENDIF

SI clave_prod_serv == "" OR clave_prod_serv == NULL THEN
    Mostrar error "La clave de producto SAT es obligatoria"
    No permitir guardar
ENDIF
```

**Regla 3: Validación de Claves SAT**
```pseudocode
SI clave_prod_serv != "" THEN
    Validar LENGTH(clave_prod_serv) == 8
    Validar formato: 8 dígitos numéricos
    Validar existe en catálogo SAT
    
    SI formato inválido OR no existe en catálogo THEN
        Mostrar error "Clave de producto SAT inválida"
        No permitir guardar
    ENDIF
ENDIF

SI clave_unidad != "" THEN
    Validar LENGTH(clave_unidad) == 3
    Validar existe en catálogo SAT
    
    SI formato inválido OR no existe en catálogo THEN
        Mostrar error "Clave de unidad SAT inválida"
        No permitir guardar
    ENDIF
ENDIF
```

**Regla 4: Cálculo de Precio Venta**
```pseudocode
AL GUARDAR PRODUCTO:
    SI porcentaje_utilidad >= 0 AND costo_unitario > 0 THEN
        precio_venta = costo_unitario * (1 + (porcentaje_utilidad / 100))
        
        // Redondear a 2 decimales
        precio_venta = ROUND(precio_venta, 2)
    ELSE
        precio_venta = costo_unitario
    ENDIF
    
    Guardar precio_venta en BD
```

**Regla 5: Registrar Movimiento de Inventario**
```pseudocode
AL REGISTRAR MOVIMIENTO:
    Validar producto existe en BD
    Validar cantidad > 0
    
    SI tipo IN ('ENTRADA_COMPRA', 'DEVOLUCION_CLIENTE') THEN
        // Movimiento de entrada
        Validar costo_unitario > 0
        
        SI id_proveedor != NULL THEN
            Validar proveedor existe y está activo
        ENDIF
        
    ELSE SI tipo IN ('SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 'MERMA') THEN
        // Movimiento de salida
        stock_actual = calcularStockActual(producto)
        
        SI cantidad > stock_actual THEN
            Retornar error "Stock insuficiente. Disponible: {stock_actual}"
            BREAK
        ENDIF
        
    ELSE SI tipo == 'AJUSTE' THEN
        // Ajuste puede ser positivo o negativo
        // No validar stock
    ENDIF
    
    Generar id_movimiento = "MOV-" + SECUENCIA
    
    BEGIN TRANSACTION
        INSERT INTO movimiento_inventario (
            id_movimiento,
            folio,
            tipo,
            id_producto,
            cantidad,
            costo_unitario,
            fecha,
            observaciones,
            usuario
        ) VALUES (...)
        
        // El stock se calcula automáticamente por la vista
    COMMIT TRANSACTION
    
    Retornar movimiento creado
```

**Regla 6: Cálculo de Stock Actual**
```pseudocode
FUNCION calcularStockActual(id_producto):
    entradas = SUM(cantidad WHERE tipo IN ('ENTRADA_COMPRA', 'DEVOLUCION_CLIENTE'))
    salidas = SUM(cantidad WHERE tipo IN ('SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 'MERMA'))
    ajustes = SUM(cantidad WHERE tipo = 'AJUSTE')  // Puede ser + o -
    
    stock_actual = entradas - salidas + ajustes
    
    SI stock_actual < 0 THEN
        stock_actual = 0  // No permitir stock negativo
    ENDIF
    
    RETORNAR stock_actual
FIN FUNCION
```

**Regla 7: Desactivar Producto con Stock**
```pseudocode
AL DESACTIVAR PRODUCTO:
    stock_actual = calcularStockActual(producto.sku)
    
    SI stock_actual > 0 THEN
        Mostrar advertencia "El producto tiene {stock_actual} unidades en stock. ¿Desea continuar?"
        
        SI usuario confirma THEN
            UPDATE producto SET estatus = 'INACTIVO'
            // El stock se mantiene
        ELSE
            Cancelar operación
        ENDIF
    ELSE
        UPDATE producto SET estatus = 'INACTIVO'
    ENDIF
```

#### 4.4 Validaciones específicas

**Frontend (JavaScript - inventarios-service.js):**

```javascript
validarFormularioProducto() {
    const errores = [];
    
    // Nombre obligatorio
    if (!this.nombre || this.nombre.trim() === '') {
        errores.push('El nombre del producto es obligatorio');
    }
    
    // Categoría obligatoria
    if (!this.categoria) {
        errores.push('Debe seleccionar la categoría');
    }
    
    // Costo unitario obligatorio y positivo
    if (!this.costo_unitario || this.costo_unitario <= 0) {
        errores.push('El costo unitario debe ser mayor a 0');
    }
    
    // Porcentaje de utilidad no negativo
    if (this.porcentaje_utilidad < 0) {
        errores.push('El porcentaje de utilidad no puede ser negativo');
    }
    
    // Stock mínimo no negativo
    if (this.stock_minimo < 0) {
        errores.push('El stock mínimo no puede ser negativo');
    }
    
    // Validar claves SAT
    if (!this.clave_prod_serv || this.clave_prod_serv.length !== 8) {
        errores.push('La clave de producto SAT debe tener 8 dígitos');
    }
    
    if (!this.clave_unidad || this.clave_unidad.length !== 3) {
        errores.push('La clave de unidad SAT debe tener 3 caracteres');
    }
    
    if (!this.objeto_impuesto || this.objeto_impuesto.length !== 2) {
        errores.push('El objeto de impuesto debe tener 2 dígitos');
    }
    
    // Validar IVA
    if (![0, 8, 16].includes(this.iva_aplicable)) {
        errores.push('El IVA debe ser 0%, 8% o 16%');
    }
    
    return errores;
}

calcularPrecioVenta() {
    if (this.costo_unitario > 0 && this.porcentaje_utilidad >= 0) {
        const precio = this.costo_unitario * (1 + (this.porcentaje_utilidad / 100));
        return Math.round(precio * 100) / 100;  // Redondear a 2 decimales
    }
    return this.costo_unitario;
}

validarFormularioMovimiento() {
    const errores = [];
    
    // Tipo obligatorio
    if (!this.tipo) {
        errores.push('Debe seleccionar el tipo de movimiento');
    }
    
    // Producto obligatorio
    if (!this.id_producto) {
        errores.push('Debe seleccionar un producto');
    }
    
    // Cantidad obligatoria y positiva
    if (!this.cantidad || this.cantidad <= 0) {
        errores.push('La cantidad debe ser mayor a 0');
    }
    
    // Fecha obligatoria y no futura
    if (!this.fecha) {
        errores.push('La fecha es obligatoria');
    } else {
        const hoy = new Date();
        const fechaMovimiento = new Date(this.fecha);
        if (fechaMovimiento > hoy) {
            errores.push('La fecha no puede ser futura');
        }
    }
    
    // Validar costo unitario para entradas
    if (this.tipo === 'ENTRADA_COMPRA' && (!this.costo_unitario || this.costo_unitario <= 0)) {
        errores.push('El costo unitario es obligatorio para entradas por compra');
    }
    
    return errores;
}
```

**Backend (Java - ProductoService.java):**

```java
public void validarProducto(Producto producto) throws ValidationException {
    List<String> errores = new ArrayList<>();
    
    // Nombre obligatorio
    if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
        errores.add("El nombre del producto es obligatorio");
    }
    
    // Categoría obligatoria
    if (producto.getCategoria() == null) {
        errores.add("La categoría es obligatoria");
    }
    
    // Costo unitario positivo
    if (producto.getCostoUnitario() == null || producto.getCostoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
        errores.add("El costo unitario debe ser mayor a 0");
    }
    
    // Porcentaje de utilidad no negativo
    if (producto.getPorcentajeUtilidad().compareTo(BigDecimal.ZERO) < 0) {
        errores.add("El porcentaje de utilidad no puede ser negativo");
    }
    
    // Validar claves SAT
    if (producto.getClaveProdServ() == null || producto.getClaveProdServ().length() != 8) {
        errores.add("La clave de producto SAT debe tener 8 dígitos");
    } else {
        // Validar en catálogo SAT
        if (!catalogoSATService.existeClaveProdServ(producto.getClaveProdServ())) {
            errores.add("La clave de producto SAT no existe en el catálogo");
        }
    }
    
    if (producto.getClaveUnidad() == null || producto.getClaveUnidad().length() != 3) {
        errores.add("La clave de unidad SAT debe tener 3 caracteres");
    } else {
        // Validar en catálogo SAT
        if (!catalogoSATService.existeClaveUnidad(producto.getClaveUnidad())) {
            errores.add("La clave de unidad SAT no existe en el catálogo");
        }
    }
    
    if (!errores.isEmpty()) {
        throw new ValidationException(String.join(", ", errores));
    }
}

public void validarMovimiento(MovimientoInventario movimiento) throws ValidationException {
    List<String> errores = new ArrayList<>();
    
    // Producto existe
    Producto producto = productoRepository.findById(movimiento.getIdProducto())
        .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    
    // Cantidad positiva
    if (movimiento.getCantidad() <= 0) {
        errores.add("La cantidad debe ser mayor a 0");
    }
    
    // Validar stock para salidas
    if (movimiento.getTipo().equals("SALIDA_VENTA") || 
        movimiento.getTipo().equals("DEVOLUCION_PROVEEDOR") ||
        movimiento.getTipo().equals("MERMA")) {
        
        int stockActual = calcularStockActual(producto.getSku());
        
        if (movimiento.getCantidad() > stockActual) {
            errores.add("Stock insuficiente. Disponible: " + stockActual);
        }
    }
    
    // Validar costo para entradas
    if (movimiento.getTipo().equals("ENTRADA_COMPRA")) {
        if (movimiento.getCostoUnitario() == null || 
            movimiento.getCostoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El costo unitario es obligatorio para entradas por compra");
        }
    }
    
    if (!errores.isEmpty()) {
        throw new ValidationException(String.join(", ", errores));
    }
}

private int calcularStockActual(String sku) {
    return movimientoRepository.calcularStockActual(sku);
}
```

#### 4.5 Endpoints API REST

```
GET    /api/productos                      # Listar todos con filtros opcionales
GET    /api/productos/{sku}                # Obtener por SKU
POST   /api/productos                      # Crear nuevo producto
PUT    /api/productos/{sku}                # Actualizar producto existente
DELETE /api/productos/{sku}                # Desactivar producto (soft delete)
GET    /api/productos/{sku}/stock          # Obtener stock actual del producto
GET    /api/productos/{sku}/kardex         # Obtener kardex del producto
POST   /api/productos/importar             # Importar productos desde CSV/Excel
GET    /api/productos/exportar             # Exportar a CSV/Excel
GET    /api/productos/stock-bajo           # Listar productos con stock bajo

GET    /api/movimientos                    # Listar todos los movimientos
GET    /api/movimientos/{id}               # Obtener movimiento por ID
POST   /api/movimientos                    # Registrar nuevo movimiento
GET    /api/movimientos/kardex             # Kardex general (todos los productos)
```

**Detalle de Endpoints:**

**1. GET /api/productos**
```
Query Params:
  - buscar: string (opcional) - Búsqueda en nombre, descripción
  - sku: string (opcional) - Filtro exacto por SKU
  - categoria: string (opcional) - ARMAZONES | LENTES | ACCESORIOS | CONSUMIBLES
  - estatus: string (opcional) - ACTIVO | INACTIVO
  - stockBajo: boolean (opcional) - Filtrar productos con stock bajo
  - page: int (opcional) - Número de página (default: 0)
  - size: int (opcional) - Tamaño de página (default: 20)

Response 200:
{
  "content": [Producto],
  "totalElements": int,
  "totalPages": int,
  "number": int
}
```

**2. POST /api/productos**
```
Request Body:
{
  "nombre": "Armazón Classic RB3016",
  "categoria": "ARMAZONES",
  "marca": "Ray-Ban",
  "codigo_barras": "750123456789",
  "tipo_inventario": "STOCK_TIENDA",
  "stock_minimo": 20,
  "costo_unitario": 600.00,
  "porcentaje_utilidad": 80.00,
  "clave_prod_serv": "85121603",
  "clave_unidad": "H87",
  "objeto_impuesto": "02",
  "iva_aplicable": 16.00
}

Response 201:
{
  "sku": "7500001",
  "nombre": "Armazón Classic RB3016",
  "precio_venta": 1080.00,
  ...
}
```

**3. POST /api/movimientos**
```
Request Body:
{
  "folio": "FC-A-12345",
  "tipo": "ENTRADA_COMPRA",
  "id_producto": "7500001",
  "cantidad": 50,
  "costo_unitario": 600.00,
  "id_proveedor": "PRV-00001",
  "fecha": "2026-02-04",
  "observaciones": "Compra mensual"
}

Response 201:
{
  "id_movimiento": "MOV-00125",
  "folio": "FC-A-12345",
  "tipo": "ENTRADA_COMPRA",
  "id_producto": "7500001",
  "cantidad": 50,
  "stock_resultante": 95,
  ...
}
```

**4. GET /api/productos/{sku}/kardex**
```
Response 200:
{
  "producto": {
    "sku": "7500001",
    "nombre": "Armazón Classic RB3016",
    "stock_actual": 95
  },
  "movimientos": [
    {
      "id_movimiento": "MOV-00124",
      "folio": "FC-A-1234",
      "fecha": "2026-02-03",
      "tipo": "ENTRADA_COMPRA",
      "entrada": 50,
      "salida": 0,
      "saldo": 95,
      "usuario": "admin"
    },
    ...
  ]
}
```

#### 4.6 Criterios de Aceptación

- [x] El usuario puede crear un producto con todos los datos (comerciales y fiscales)
- [x] El sistema valida el formato de las claves SAT (ClaveProdServ, ClaveUnidad, Objeto de impuesto)
- [x] El sistema genera SKU único automáticamente
- [x] El usuario puede buscar productos por SKU, nombre o categoría
- [x] El usuario puede editar datos de un producto existente
- [x] El usuario puede desactivar un producto (no se elimina físicamente)
- [x] El sistema calcula automáticamente el precio de venta según costo y utilidad
- [x] El sistema controla el acceso según permisos del usuario (Ver, Crear, Editar, Eliminar)
- [x] El usuario puede registrar movimientos de inventario (entradas, salidas, ajustes)
- [x] El sistema genera ID de movimiento automático (MOV-XXXXX)
- [x] El usuario puede capturar folio externo para vincular con documentos
- [x] El sistema calcula el stock actual en tiempo real mediante vista
- [x] El sistema muestra alertas visuales de stock bajo (⚠) y sin stock (○)
- [x] El sistema no permite salidas mayores al stock disponible
- [x] El usuario puede consultar el kardex completo de un producto
- [x] El kardex muestra entrada, salida y saldo por cada movimiento
- [x] Todos los cambios quedan auditados (usuario, fecha)
- [x] La interfaz tiene dos tabs: CATÁLOGO (productos) y CONTROL (movimientos)
- [x] El formulario muestra modo "SOLO LECTURA" si el usuario no tiene permiso de edición
- [x] Los botones se deshabilitan según los permisos del usuario
- [x] Las validaciones se ejecutan en frontend (UX) y backend (seguridad)
- [x] El sistema permite importar/exportar productos en CSV/Excel

---

## RESUMEN EJECUTIVO DEL MÓDULO

### Características Principales

1. **CRUD Completo de Productos** con datos fiscales CFDI
2. **Sistema Kardex** con ID automático y folio capturable
3. **Cálculo Automático** de precio venta y stock actual
4. **Alertas Visuales** de stock bajo y sin stock
5. **Validaciones SAT** de claves fiscales
6. **Control de Permisos** granular en ambos tabs
7. **Trazabilidad Completa** de todos los movimientos

### Estadísticas del Módulo

- **Líneas de documentación:** ~900
- **Tablas de base de datos:** 2 (producto, movimiento_inventario) + 1 vista (stock_actual)
- **Endpoints API REST:** 12
- **Historias de Usuario:** 9
- **Criterios de Aceptación:** 22
- **Reglas de Negocio:** 7
- **Validaciones:** 15+ (Frontend y Backend)

---

**Última actualización:** 04 de febrero de 2026, 02:45 AM  
**Estado:** Documentación completa - Pendiente implementación  
**Siguiente paso:** Documentar Módulo Órdenes de Trabajo (2.4)
