### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 2.4

#### 4.1 Matriz de Control de Permisos - ÓRDENES DE TRABAJO

**Tabla de Control de Acceso y Comportamiento UI**

| # | Elemento UI | Permiso Requerido | Si tiene permiso | Si NO tiene permiso | Validación Backend |
|---|-------------|-------------------|------------------|---------------------|-------------------|
| **1** | **Acceso al módulo** | `puede_ver` | Carga `ordenes-trabajo.html` normalmente | Redirige a `menu.html` + Toast "Acceso Denegado" | `GET /api/ordenes-trabajo` retorna 403 |
| **2** | **Barra de búsqueda** | `puede_ver` | Habilitada, permite buscar | *(No aplica, requiere acceso)* | Solo lectura |
| **3** | **Filtro Estado** | `puede_ver` | Habilitado, permite filtrar | *(No aplica, requiere acceso)* | Solo lectura |
| **4** | **Tabla de listado** | `puede_ver` | Muestra todas las OT | *(No aplica, requiere acceso)* | Solo lectura |
| **5** | **Botón "Abrir" (tabla)** | `puede_ver` | Habilitado, abre detalle | *(No aplica, requiere acceso)* | Solo lectura |
| **6** | **Botón "+ Desde venta"** | `puede_crear` | Habilitado, crea OT desde venta | `disabled=true` + clase `.btn-disabled` + tooltip "No tiene permiso" | `POST /api/ordenes-trabajo` retorna 403 |
| **7** | **Selector "Estado" (detalle)** | `puede_editar` | Habilitado, permite cambiar estado | `disabled=true` + tooltip "No tiene permiso" | `PUT /api/ordenes-trabajo/{id}` retorna 403 |
| **8** | **Campo "Especificaciones"** | `puede_editar` | Habilitado, permite editar | `disabled=true` + clase `.input-readonly` | `PUT /api/ordenes-trabajo/{id}` retorna 403 |
| **9** | **Botón "Guardar"** | `puede_editar` | Visible y habilitado | `display: none` (oculto) | `PUT /api/ordenes-trabajo/{id}` retorna 403 |
| **10** | **Botón "Agregar insumo"** | `puede_editar` | Habilitado, agrega insumo | `disabled=true` + clase `.btn-disabled` | `POST /api/ordenes-trabajo/{id}/insumos` retorna 403 |
| **11** | **Botón "Reservar"** | `puede_editar` | Habilitado, reserva insumo | `disabled=true` + clase `.btn-disabled` | `PUT /api/ordenes-trabajo/{id}/insumos/{id}/reservar` retorna 403 |
| **12** | **Botón "Liberar"** | `puede_editar` | Habilitado, libera insumo | `disabled=true` + clase `.btn-disabled` | `PUT /api/ordenes-trabajo/{id}/insumos/{id}/liberar` retorna 403 |
| **13** | **Botón "Reservar faltantes"** | `puede_editar` | Habilitado, reserva todos | `disabled=true` + clase `.btn-disabled` | `POST /api/ordenes-trabajo/{id}/reservar-faltantes` retorna 403 |
| **14** | **Checkboxes Control de Calidad** | `puede_editar` | Habilitados, permite marcar | `disabled=true` | `POST /api/ordenes-trabajo/{id}/control-calidad` retorna 403 |
| **15** | **Botón "Guardar control de calidad"** | `puede_editar` | Visible y habilitado | `display: none` (oculto) | `POST /api/ordenes-trabajo/{id}/control-calidad` retorna 403 |
| **16** | **Botón "Terminar OT"** | `puede_editar` + `control_calidad_completo` | Habilitado si control completo | `disabled=true` + tooltip según razón | `PUT /api/ordenes-trabajo/{id}/terminar` retorna 403 |
| **17** | **Botón "Enviar a reproceso"** | `puede_editar` | Habilitado, envía a reproceso | `disabled=true` + clase `.btn-disabled` | `PUT /api/ordenes-trabajo/{id}/reproceso` retorna 403 |
| **18** | **Botón "Entregar"** | `puede_editar` + `estado=TERMINADA` | Habilitado si OT terminada | `disabled=true` + tooltip "OT no terminada" | `PUT /api/ordenes-trabajo/{id}/entregar` retorna 403 |
| **19** | **Historial de estado** | `puede_ver` | Muestra historial completo | Muestra historial (solo lectura) | Solo lectura |
| **20** | **Badge "MODO SOLO LECTURA"** | `!puede_editar` | No se muestra | Se muestra en formulario | Visual |

---

**Última actualización:** 04 de febrero de 2026, 03:16 AM  
**Estado:** Documentación completa - Paso 4 iniciado  
**Archivo completo disponible en:** ETAPA 2 - ORDENES_TRABAJO.md
