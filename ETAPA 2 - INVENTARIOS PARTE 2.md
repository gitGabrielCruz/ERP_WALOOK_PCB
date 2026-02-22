---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Módulo Inventarios (Parte 2)  
**VERSIÓN:** 1.1  
**FECHA:** 21 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto / Antigravity AI  

---

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 2.3


#### 4.1 Matriz de Control de Permisos - INVENTARIOS

**Tabla de Control de Acceso y Comportamiento UI - TAB CATÁLOGO**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **1** | **Acceso al módulo** | `puede_ver` | Carga `inventarios.html` normalmente | Redirige a `menu.html` + Toast "Acceso Denegado" | `GET /api/productos` retorna 403 |
| **2** | **Filtros (Grupo, Fam, Marca)** | `puede_ver` | Habilitados, permiten filtrar | (No aplica, requiere acceso) | Solo lectura |
| **3** | **Tabla de listado** | `puede_ver` | Muestra productos + Foto + Ubicación | (No aplica, requiere acceso) | Solo lectura |
| **4** | **Botón "Ver" (tabla)** | `puede_ver` | Habilitado, abre detalle | (No aplica, requiere acceso) | Solo lectura |
| **5** | **Botón "Nuevo"** | `puede_crear` | Habilitado, limpia formulario | `disabled=true` + tooltip | `POST /api/productos` 403 |
| **6** | **Formulario Detalle (Selects)** | `puede_editar` | Selects de Marca/Familia habilitados | `disabled=true` + modo lectura | `PUT /api/productos/{id}` 403 |
| **7** | **Campo "Ubicación"** | `puede_editar` | Editable (texto libre) | Readonly | Persistencia en BD |
| **8** | **Carga de Imagen** | `puede_editar` | Habilita selector de archivos/URL | Oculto o Readonly | Validación de formato |
| **9** | **Botón "Guardar"** | `puede_editar` | Visible y habilitado | `display: none` (oculto) | `PUT /api/productos/{id}` 403 |
| **10** | **Inactivación de Prod.** | `puede_editar` | Gestionada vía Estatus en Ficha Técnica | Oculto o Readonly | Baja lógica (ACTIVO → INACTIVO) |
| **11** | **Botón "Ajuste / Conteo"** | `puede_editar` | Abre Modal de Conciliación Física | `disabled=true` | `POST /api/movimientos` (AJUSTE) |
| **12** | **Botón "Kardex"** | `puede_ver` | Muestra historial histórico | Habilitado (solo lectura) | `GET /api/movimientos` |

**Tabla de Control de Acceso - TAB CONTROL**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **13** | **Registro de Movimiento** | `puede_crear` | Habilitado | Solo lectura de historial | `POST /api/movimientos` 403 |
| **14** | **Campo "Costo Unitario"** | `puede_editar` | Editable en Entradas | Bloqueado (toma el del maestro) | Auditoría de costo |
| **15** | **Campo "Lote/Serie"** | `puede_crear` | Habilitado si el Grupo lo exige | Oculto si no es requerido | `NOT NULL` en BD si aplica |

#### 4.2 Reglas de Negocio Maestras (Pseudocódigo)

```pseudocode
REGLA 1: Jerarquía de Categorización
    AL SELECCIONAR GRUPO (cat_grupo):
        LISTAR cat_familia DONDE id_grupo = seleccionado
        SI grupo.es_servicio == TRUE THEN
            OCULTAR campos de Stock (Min/Max, Ubicación)
            BLOQUEAR validación de existencia negativa
        ELSE
            MOSTRAR campos de Stock
        ENDIF
        SI grupo.exige_caducidad == TRUE THEN
            HABILITAR campos "Lote/Serie" y "Vencimiento" en Tab CONTROL
        ENDIF

REGLA 2: Cálculo Dinámico de PVP (Precio de Venta al Público)
    DADO QUE precio_venta ES GENERATED ALWAYS EN BD:
    AL CAMBIAR costo_unitario (Front o Back):
        PVP_CALCULADO = costo_unitario * (1 + (porcentaje_utilidad / 100))
        MOSTRAR PREVISUALIZACIÓN en UI inmediatamente
        // Al guardar, la BD asegura la consistencia física del cálculo

REGLA 3: Operación de Ajuste de Inventario (CONCILIACIÓN)
    AL ABRIR MODAL "AJUSTE":
        CANTIDAD_SISTEMA = v_stock_actual.existencia_operativa
        USUARIO INGRESA: CANTIDAD_FISICA
        SISTEMA CALCULA: DIFERENCIA = CANTIDAD_FISICA - CANTIDAD_SISTEMA
        
        SI DIFERENCIA != 0 THEN
            GENERAR movimiento_inventario (TIPO=AJUSTE, CANTIDAD=DIFERENCIA)
            REGISTRAR motivo: "Ajuste por conteo físico"
            ACTUALIZAR v_stock_actual automáticamente
        ENDIF

REGLA 4: Validación Transaccional de Stock (Anti-Negativos)
    AL REGISTRAR MOVIMIENTO (Salida/Merma/Traspaso):
        STOCK_DISPONIBLE = SELECT existencia_operativa FROM v_stock_actual
        SI cantidad_movimiento > STOCK_DISPONIBLE THEN
            CANCELAR OPERACIÓN
            LANZAR ERROR: "Stock insuficiente. Máximo disponible: " + STOCK_DISPONIBLE
        ENDIF

REGLA 5: Actualización Automática de Costo
    AL REGISTRAR ENTRADA_COMPRA:
        UPDATE producto SET costo_unitario = movimiento.costo_unitario
        // Esto dispara el recálculo del PVP basado en el porcentaje_utilidad pactado
```

#### 4.3 Flujos de Datos Fiscales (Estrategia .dat)

```pseudocode
REGLA: Validación de Claves SAT 4.0
    AL GUARDAR/EDITAR PRODUCTO:
        CLAVE = producto.clave_prod_serv
        
        // El sistema NO consulta SQL para evitar latencia y riesgos de inyección
        RESULTADO = SatValidationService.verify(CLAVE, "catalogo_sat_40.dat")
        
        SI RESULTADO.is_valid == FALSE THEN
            RECHAZAR GUARDADO
            MOSTRAR: "La clave SAT ingresada es inválida o no vigente"
        ENDIF
```

#### 4.4 Endpoints API REST (Sincronizados con Etapa 2)

**Catálogos (Mantenimiento):**
- `GET /api/catalogos/grupos` -> Lista grupos (Id, Nombre, Reglas)
- `GET /api/catalogos/familias?grupoId={id}` -> Lista familias filtradas
- `GET /api/catalogos/marcas` -> Lista marcas comerciales

**Productos (Maestro):**
- `POST /api/productos` -> Crea (Genera SKU 75XXXXX)
- `PUT /api/productos/{id}` -> Edita (Actualiza costo/utilidad)
- `GET /api/productos?search={txt}&grupo={id}&marca={id}` -> Búsqueda avanzada
- `DELETE /api/productos/{id}` -> Eliminación física (Restringido - Uso Admin)
- `POST /api/productos` -> Inactivación (Baja Lógica enviando estatus 'INACTIVO')

**Inventario (Operaciones):**
- `GET /api/productos/{id}/existencia` -> Retorna valor de `v_stock_actual`
- `GET /api/productos/{id}/kardex` -> Historial completo de movimientos
- `POST /api/movimientos` -> Registra (Solo Entradas/Salidas/Ajustes)

#### 4.5 Criterios de Aceptación (Definidos por Calidad)

1.  **Integridad:** El "Existencia" no puede editarse manualmente; solo mediante Movimientos.
2.  **Seguridad:** Un usuario sin `puede_editar` no puede ver los costos de compra ni el % de utilidad (Modo Privacidad).
3.  **Visual:** El semáforo de stock (Verde/Naranja/Rojo) debe reaccionar en tiempo real tras cada movimiento registrado.
4.  **Fiscal:** Todo producto debe tener `clave_prod_serv` válida para ser guardado.

---

**Última actualización:** 21 de febrero de 2026, 18:50 PM  
**Estado:** Documentación Técnica Actualizada (Layout 50/50 & Inactivación).  
**Autor:** Antigravity AI (Pair Programming con OMCGC)
