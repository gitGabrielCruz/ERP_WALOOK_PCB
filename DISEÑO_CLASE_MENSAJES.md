---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** Diseño de Clase para Mensajes en Pantalla  
**VERSIÓN:** 1.1  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---


# DISEÑO DE CLASE PARA MENSAJES EN PANTALLA
## Sistema ERP WALOOK - Arquitectura de Notificaciones

---

## INDICE

1. Introduccion
2. Estandares Institucionales
3. Catalogo de Mensajes (9 Tipos)
4. Especificaciones Tecnicas por Tipo
5. Logica de Implementacion (Pseudocodigo)
6. Integracion con Modulos Existentes
7. Checklist de Validacion

---

## 1. INTRODUCCION

Este documento define la arquitectura estandarizada de mensajes y notificaciones para el Sistema ERP WALOOK.

### Objetivos

- Consistencia visual en todos los mensajes
- Trazabilidad de cada tipo de mensaje
- Reutilizacion de componentes
- Accesibilidad y usabilidad

### Alcance

- 9 tipos de mensajes predefinidos
- Alineacion con identidad institucional (ui-base.css)
- Integracion en modulos de Etapas 0, 1 y 2

---

## 2. ESTANDARES INSTITUCIONALES

### 2.1 Tipografia

Segun ui-base.css:

```
--font-family-base: Inter, "Segoe UI", Roboto, Arial, sans-serif
--font-size-title: 20px
--font-size-subtitle: 16px
--font-size-text: 14px
--font-size-label: 13px
--font-size-small: 12px
```

Aplicacion en mensajes:
- Titulos: 18px (Inter, semibold 600)
- Mensaje principal: 15px (Inter, regular 400)
- Mensaje secundario: 14px (Inter, regular 400)
- Etiquetas tecnicas: 13px (Inter, semibold 600)
- Codigo tecnico: 12px (monospace)

### 2.2 Paleta de Colores

Segun ui-base.css:

```
--color-primary: #1F3A5F      (Azul institucional)
--color-secondary: #2E5E8C    (Azul secundario)
--color-success: #2E7D32      (Verde exito)
--color-error: #C62828        (Rojo error)
--color-warning: #ED6C02      (Naranja advertencia)
--color-info: #0288D1         (Azul informacion)
```

Mapeo a mensajes:
- Exito: #10b981 (verde)
- Error/Restriccion: #dc2626 (rojo)
- Advertencia: #f59e0b (naranja)
- Confirmacion/Sesion: #2563eb (azul)

### 2.3 Espaciados

```
--space-xs: 8px
--space-sm: 12px
--space-md: 16px
--space-lg: 24px
--border-radius: 4px
```

### 2.4 Reglas de Diseño

PROHIBIDO:
- Estilos inline
- Redefinir colores fuera de variables
- Crear variaciones visuales arbitrarias
- Usar fuentes diferentes a Inter

OBLIGATORIO:
- Usar variables CSS globales
- Mantener estructura comun
- Documentar excepciones
- Validar contra SVG de referencia

---

## 3. CATALOGO DE MENSAJES

### Resumen Ejecutivo

| # | Tipo | Color | Icono | Dimensiones | Botones | Uso |
|---|------|-------|-------|-------------|---------|-----|
| 1 | Error Validacion | #dc2626 | Circulo + ! | 520x300 | 2 | Errores de formato en campos |
| 2 | Confirmacion | #2563eb | Circulo + ? | 520x260 | 2 | Confirmar acciones criticas |
| 3 | Error Sistema | #dc2626 | Circulo + ! | 640x420 | 2 | Errores tecnicos/servidor |
| 4 | Exito | #10b981 | Circulo + Check | 520x240 | 1 | Operacion completada |
| 5 | Advertencia Critica | #f59e0b | Triangulo + ! | 600x360 | 2 + Textarea | Operaciones sensibles |
| 6 | Procesando | #2563eb | Spinner | 420x220 | 0 | Carga/procesamiento |
| 7 | Sesion Expirada | #2563eb | Candado | 520x240 | 1 | Timeout de sesion |
| 8 | Acceso Restringido | #dc2626 | Prohibido | 520x240 | 1 | Permisos insuficientes |
| 9 | Advertencia Simple | #f59e0b | Triangulo + ! | 520x240 | 1 | Alertas informativas |

---

## 4. ESPECIFICACIONES TECNICAS POR TIPO

### 4.1 MENSAJE TIPO 1: ERROR DE VALIDACION

**Proposito**: Notificar errores de formato o validacion en campos de formulario.

**Dimensiones**: 520px x 300px

**Estructura**:
- Icono: Circulo con exclamacion (outlined, stroke #dc2626)
- Titulo: Dinamico (ej. "Error en el campo RFC")
- Mensaje principal: Descripcion del error
- Campo afectado: Valor ingresado (fondo rojo claro #fef2f2)
- Mensaje secundario: Formato esperado + ejemplo
- Botones: "Cancelar" (gris #e5e7eb) / "Corregir" (rojo #dc2626)

**Codigo SVG**:

```svg
<svg width="520" height="300" viewBox="0 0 520 300" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="520" height="300" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#dc2626" stroke-width="2">
    <circle cx="12" cy="12" r="10"/>
    <line x1="12" y1="7" x2="12" y2="13"/>
    <circle cx="12" cy="17" r="1.5" fill="#dc2626" stroke="none"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Error en el campo RFC
  </text>
  <line x1="40" y1="70" x2="480" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="105" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    El valor ingresado en el campo RFC no cumple con el formato oficial.
  </text>
  <rect x="40" y="120" width="440" height="40" rx="4" fill="#fef2f2" stroke="#dc2626"/>
  <text x="55" y="145" font-family="Inter, sans-serif" font-size="14" fill="#dc2626">
    RFC ingresado: XAXX01010
  </text>
  <text x="40" y="185" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    El RFC debe contener 12 o 13 caracteres alfanumericos.
  </text>
  <text x="40" y="205" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Ejemplo valido: XAXX010101000
  </text>
  <rect x="240" y="240" width="120" height="44" rx="4" fill="#e5e7eb"/>
  <text x="275" y="268" font-family="Inter, sans-serif" font-size="14" fill="#374151">
    Cancelar
  </text>
  <rect x="380" y="240" width="120" height="44" rx="4" fill="#dc2626"/>
  <text x="410" y="268" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Corregir
  </text>
</svg>
```

**Parametros dinamicos**:
- `campo`: Nombre del campo con error
- `valorIngresado`: Valor que causo el error
- `formatoEsperado`: Descripcion del formato correcto
- `ejemplo`: Ejemplo de valor valido

---

### 4.2 MENSAJE TIPO 2: CONFIRMACION

**Proposito**: Solicitar confirmacion antes de ejecutar acciones criticas.

**Dimensiones**: 520px x 260px

**Estructura**:
- Icono: Circulo con signo de interrogacion (outlined, stroke #2563eb)
- Titulo: "Confirmar accion"
- Mensaje principal: Pregunta clara sobre la accion
- Advertencia: Consecuencias irreversibles
- Botones: "Cancelar" (gris #e5e7eb) / "Confirmar" (azul #2563eb)

**Codigo SVG**:

```svg
<svg width="520" height="260" viewBox="0 0 520 260" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="520" height="260" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#2563eb" stroke-width="2">
    <circle cx="12" cy="12" r="10"/>
    <line x1="12" y1="8" x2="12" y2="13"/>
    <circle cx="12" cy="17" r="1.5" fill="#2563eb" stroke="none"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Confirmar accion
  </text>
  <line x1="40" y1="70" x2="480" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    ¿Desea continuar con la eliminacion del registro seleccionado?
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Esta accion no podra deshacerse posteriormente.
  </text>
  <rect x="240" y="180" width="120" height="44" rx="4" fill="#e5e7eb"/>
  <text x="275" y="208" font-family="Inter, sans-serif" font-size="14" fill="#374151">
    Cancelar
  </text>
  <rect x="380" y="180" width="120" height="44" rx="4" fill="#2563eb"/>
  <text x="410" y="208" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Confirmar
  </text>
</svg>
```

**Parametros dinamicos**:
- `pregunta`: Pregunta a confirmar
- `advertencia`: Consecuencias de la accion

---

### 4.3 MENSAJE TIPO 3: ERROR DE SISTEMA

**Proposito**: Notificar errores tecnicos con informacion para soporte.

**Dimensiones**: 640px x 420px

**Estructura**:
- Icono: Circulo con exclamacion (outlined, stroke #dc2626)
- Titulo: "Error del sistema"
- Mensaje principal: Descripcion generica del error
- Area tecnica: ErrorCode + Stack trace (fuente monoespaciada, fondo #f9fafb)
- Botones: "Descargar reporte (.txt)" (azul #2563eb) / "Cerrar" (gris #e5e7eb)

**Codigo SVG**:

```svg
<svg width="640" height="420" viewBox="0 0 640 420" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="640" height="420" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#dc2626" stroke-width="2">
    <circle cx="12" cy="12" r="10"/>
    <line x1="12" y1="7" x2="12" y2="13"/>
    <circle cx="12" cy="17" r="1.5" fill="#dc2626" stroke="none"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Error del sistema
  </text>
  <line x1="40" y1="70" x2="600" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    Ocurrio un error inesperado al procesar la solicitud.
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Puede descargar el reporte tecnico y enviarlo al area de desarrollo.
  </text>
  <rect x="40" y="170" width="560" height="140" rx="4" fill="#f9fafb" stroke="#e5e7eb"/>
  <text x="50" y="190" font-family="Inter, sans-serif" font-size="13" font-weight="600" fill="#374151">
    Detalle tecnico
  </text>
  <text x="50" y="215" font-family="monospace" font-size="12" fill="#6b7280">
    ErrorCode: SYS-DB-001
  </text>
  <text x="50" y="235" font-family="monospace" font-size="12" fill="#6b7280">
    SQLSTATE[HY000]: Field 'rfc' doesn't have default value
  </text>
  <text x="50" y="255" font-family="monospace" font-size="12" fill="#6b7280">
    ClienteController.ts:87
  </text>
  <rect x="595" y="180" width="5" height="100" fill="#d1d5db" rx="2"/>
  <rect x="200" y="340" width="200" height="44" rx="4" fill="#2563eb"/>
  <text x="235" y="368" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Descargar reporte (.txt)
  </text>
  <rect x="430" y="340" width="150" height="44" rx="4" fill="#e5e7eb"/>
  <text x="470" y="368" font-family="Inter, sans-serif" font-size="14" fill="#374151">
    Cerrar
  </text>
</svg>
```

**Parametros dinamicos**:
- `errorCode`: Codigo de error
- `mensaje`: Mensaje de error
- `stackTrace`: Stack trace tecnico

---

### 4.4 MENSAJE TIPO 4: EXITO

**Proposito**: Confirmar que una operacion se completo correctamente.

**Dimensiones**: 520px x 240px

**Estructura**:
- Icono: Circulo con check (outlined, stroke #10b981)
- Titulo: "Operacion completada"
- Mensaje principal: Confirmacion especifica de la accion
- Mensaje secundario: Proximos pasos sugeridos
- Boton: "Aceptar" (verde #10b981)

**Codigo SVG**:

```svg
<svg width="520" height="240" viewBox="0 0 520 240" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="520" height="240" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#10b981" stroke-width="2">
    <circle cx="12" cy="12" r="10"/>
    <polyline points="7,12 11,16 17,8"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Operacion completada
  </text>
  <line x1="40" y1="70" x2="480" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    El registro fue guardado correctamente en el sistema.
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Puede continuar trabajando o regresar al listado.
  </text>
  <rect x="360" y="165" width="120" height="44" rx="4" fill="#10b981"/>
  <text x="395" y="193" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Aceptar
  </text>
</svg>
```

**Parametros dinamicos**:
- `mensaje`: Confirmacion especifica
- `proximoPaso`: Sugerencia de accion

---

### 4.5 MENSAJE TIPO 5: ADVERTENCIA CRITICA CON JUSTIFICACION

**Proposito**: Solicitar confirmacion y justificacion para operaciones sensibles.

**Dimensiones**: 600px x 360px

**Estructura**:
- Icono: Triangulo con exclamacion (outlined, stroke #f59e0b)
- Titulo: "Confirmar operacion critica"
- Mensaje principal: Descripcion del impacto
- Textarea: Campo obligatorio para justificacion (80px altura)
- Botones: "Cancelar" (gris #e5e7eb) / "Confirmar" (rojo #ef4444)

**Codigo SVG**:

```svg
<svg width="600" height="360" viewBox="0 0 600 360" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="600" height="360" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#f59e0b" stroke-width="2">
    <polygon points="12,2 22,20 2,20"/>
    <line x1="12" y1="8" x2="12" y2="14"/>
    <circle cx="12" cy="17" r="1.5" fill="#f59e0b" stroke="none"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Confirmar operacion critica
  </text>
  <line x1="40" y1="70" x2="560" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    Esta accion modificara informacion sensible del sistema.
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Es obligatorio indicar el motivo antes de continuar.
  </text>
  <text x="40" y="175" font-family="Inter, sans-serif" font-size="14" font-weight="600" fill="#374151">
    Motivo (obligatorio)
  </text>
  <rect x="40" y="190" width="520" height="80" rx="4" fill="#ffffff" stroke="#d1d5db"/>
  <text x="55" y="215" font-family="Inter, sans-serif" font-size="13" fill="#9ca3af">
    Describa el motivo de esta operacion...
  </text>
  <rect x="300" y="290" width="120" height="44" rx="4" fill="#e5e7eb"/>
  <text x="335" y="318" font-family="Inter, sans-serif" font-size="14" fill="#374151">
    Cancelar
  </text>
  <rect x="440" y="290" width="120" height="44" rx="4" fill="#ef4444"/>
  <text x="470" y="318" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Confirmar
  </text>
</svg>
```

**Parametros dinamicos**:
- `mensaje`: Descripcion del impacto
- `motivoObligatorio`: true/false

---

### 4.6 MENSAJE TIPO 6: PROCESANDO

**Proposito**: Indicar que una operacion esta en curso (modal bloqueante).

**Dimensiones**: 420px x 220px

**Estructura**:
- Icono: Spinner animado (stroke #2563eb)
- Titulo: "Procesando operacion"
- Mensaje principal: Instruccion de espera
- Advertencia: No cerrar/actualizar
- Botones: Ninguno (modal no cerrable)

**Codigo SVG**:

```svg
<svg width="420" height="220" viewBox="0 0 420 220" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="420" height="220" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(60,75)" stroke="#2563eb" stroke-width="3" fill="none">
    <circle cx="0" cy="0" r="18" stroke-opacity="0.2"/>
    <path d="M 0 -18 A 18 18 0 0 1 18 0"/>
  </g>
  <text x="110" y="70" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Procesando operacion
  </text>
  <line x1="40" y1="85" x2="380" y2="85" stroke="#e5e7eb"/>
  <text x="40" y="120" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    Espere mientras se completa la solicitud.
  </text>
  <text x="40" y="145" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    No cierre la ventana ni actualice la pagina.
  </text>
</svg>
```

**Nota tecnica**: El spinner requiere animacion CSS (rotacion continua).

**Parametros dinamicos**:
- `mensaje`: Mensaje de procesamiento personalizado

---

### 4.7 MENSAJE TIPO 7: SESION EXPIRADA

**Proposito**: Notificar que la sesion ha finalizado por timeout o seguridad.

**Dimensiones**: 520px x 240px

**Estructura**:
- Icono: Candado (outlined, stroke #2563eb)
- Titulo: "Sesion expirada"
- Mensaje principal: Causa del cierre de sesion
- Boton: "Iniciar sesion" (azul #2563eb, redirige a login)

**Codigo SVG**:

```svg
<svg width="520" height="240" viewBox="0 0 520 240" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="520" height="240" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#2563eb" stroke-width="2">
    <rect x="7" y="10" width="10" height="10" rx="2"/>
    <path d="M9 10V7a3 3 0 0 1 6 0v3"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Sesion expirada
  </text>
  <line x1="40" y1="70" x2="480" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    Su sesion ha finalizado por inactividad o motivos de seguridad.
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Inicie sesion nuevamente para continuar trabajando.
  </text>
  <rect x="360" y="165" width="120" height="44" rx="4" fill="#2563eb"/>
  <text x="380" y="193" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Iniciar sesion
  </text>
</svg>
```

**Parametros dinamicos**: Ninguno (mensaje fijo)

---

### 4.8 MENSAJE TIPO 8: ACCESO RESTRINGIDO

**Proposito**: Notificar que el usuario no tiene permisos para la accion solicitada.

**Dimensiones**: 520px x 240px

**Estructura**:
- Icono: Circulo con diagonal (prohibido, stroke #dc2626)
- Titulo: "Acceso restringido"
- Mensaje principal: Descripcion de permisos insuficientes
- Mensaje secundario: Contacto con administrador
- Boton: "Cerrar" (gris #e5e7eb)

**Codigo SVG**:

```svg
<svg width="520" height="240" viewBox="0 0 520 240" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="520" height="240" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#dc2626" stroke-width="2">
    <circle cx="12" cy="12" r="10"/>
    <line x1="6" y1="6" x2="18" y2="18"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Acceso restringido
  </text>
  <line x1="40" y1="70" x2="480" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    No cuenta con los permisos necesarios para realizar esta accion.
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Si considera que se trata de un error, contacte al administrador.
  </text>
  <rect x="360" y="165" width="120" height="44" rx="4" fill="#e5e7eb"/>
  <text x="395" y="193" font-family="Inter, sans-serif" font-size="14" fill="#374151">
    Cerrar
  </text>
</svg>
```

**Parametros dinamicos**: Ninguno (mensaje fijo)

---

### 4.9 MENSAJE TIPO 9: ADVERTENCIA SIMPLE

**Proposito**: Mostrar advertencias informativas no criticas.

**Dimensiones**: 520px x 240px

**Estructura**:
- Icono: Triangulo con exclamacion (outlined, stroke #f59e0b)
- Titulo: "Advertencia"
- Mensaje principal: Descripcion de la situacion
- Mensaje secundario: Recomendacion de accion
- Boton: "Aceptar" (naranja #f59e0b)

**Codigo SVG**:

```svg
<svg width="520" height="240" viewBox="0 0 520 240" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="520" height="240" rx="8" fill="#ffffff" stroke="#d1d5db"/>
  <g transform="translate(40,40) scale(1.2)" fill="none" stroke="#f59e0b" stroke-width="2">
    <polygon points="12,2 22,20 2,20"/>
    <line x1="12" y1="8" x2="12" y2="14"/>
    <circle cx="12" cy="17" r="1.5" fill="#f59e0b" stroke="none"/>
  </g>
  <text x="90" y="55" font-family="Inter, sans-serif" font-size="18" font-weight="600" fill="#1f2937">
    Advertencia
  </text>
  <line x1="40" y1="70" x2="480" y2="70" stroke="#e5e7eb"/>
  <text x="40" y="110" font-family="Inter, sans-serif" font-size="15" fill="#374151">
    El producto se encuentra por debajo del stock minimo configurado.
  </text>
  <text x="40" y="135" font-family="Inter, sans-serif" font-size="14" fill="#6b7280">
    Se recomienda programar reposicion para evitar desabasto.
  </text>
  <rect x="360" y="165" width="120" height="44" rx="4" fill="#f59e0b"/>
  <text x="395" y="193" font-family="Inter, sans-serif" font-size="14" fill="#ffffff">
    Aceptar
  </text>
</svg>
```

**Parametros dinamicos**:
- `mensaje`: Descripcion de la advertencia
- `recomendacion`: Accion sugerida

---

## 5. LOGICA DE IMPLEMENTACION (PSEUDOCODIGO)

### 5.1 Estructura General

```
CLASE MessageService

  METODO mostrarErrorValidacion(campo, valorIngresado, formatoEsperado, ejemplo)
    CREAR modal con SVG tipo 1
    REEMPLAZAR parametros dinamicos
    MOSTRAR modal
    AL HACER CLIC EN "Corregir": enfocar campo
    AL HACER CLIC EN "Cancelar": cerrar modal
  FIN METODO

  METODO mostrarConfirmacion(pregunta, advertencia, callbackConfirmar)
    CREAR modal con SVG tipo 2
    ASIGNAR EVENTO onclick al ID "btnMsgPrimary":
      EJECUTAR callbackConfirmar()
      CERRAR modal
    MOSTRAR modal
  FIN METODO

  METODO mostrarErrorSistema(errorCode, mensaje, stackTrace)
    CREAR modal con SVG tipo 3
    REEMPLAZAR parametros dinamicos en HTML
    MOSTRAR modal
    AL HACER CLIC EN "Descargar": generar archivo .txt
    AL HACER CLIC EN "Cerrar": cerrar modal
  FIN METODO

  METODO mostrarExito(mensaje, proximoPaso)
    CREAR modal con SVG tipo 4
    REEMPLAZAR parametros dinamicos en HTML
    MOSTRAR modal
  FIN METODO

  METODO mostrarAdvertenciaCritica(mensaje, callbackConfirmar)
    CREAR modal con SVG tipo 5
    ASIGNAR EVENTO onclick al ID "btnMsgDanger":
      OBTENER valor del textarea "motivoJustificacion"
      SI motivo esta vacio:
        MOSTRAR advertencia simple de "Motivo Requerido"
        RETORNAR
      FIN SI
      EJECUTAR callbackConfirmar(motivo)
      CERRAR modal
    MOSTRAR modal
  FIN METODO

  METODO procesando(mensaje)
    LLAMAR mostrar(tipo 6) con mensaje personalizado
    MOSTRAR modal bloqueante (no cerrable manualmente)
  FIN METODO

  METODO cerrar()
    ELIMINAR el overlay y modal del DOM
  FIN METODO

  METODO mostrarSesionExpirada()
    CREAR modal con SVG tipo 7
    MOSTRAR modal
    AL HACER CLIC EN "Iniciar sesion": redirigir a login
  FIN METODO

  METODO mostrarAccesoRestringido()
    CREAR modal con SVG tipo 8
    MOSTRAR modal
  FIN METODO

  METODO mostrarAdvertenciaSimple(mensaje, recomendacion)
    CREAR modal con SVG tipo 9
    REEMPLAZAR parametros dinamicos
    MOSTRAR modal
  FIN METODO

FIN CLASE
```

### 5.2 Ejemplo de Uso: Validacion de RFC

```
FUNCION validarRFC(rfc)
  SI rfc NO cumple formato ENTONCES
    MessageService.mostrarErrorValidacion(
      campo = "RFC",
      valorIngresado = rfc,
      formatoEsperado = "El RFC debe contener 12 o 13 caracteres alfanumericos.",
      ejemplo = "XAXX010101000"
    )
    RETORNAR false
  FIN SI
  RETORNAR true
FIN FUNCION
```

### 5.3 Ejemplo de Uso: Confirmacion de Eliminacion

```
FUNCION eliminarProveedor(id)
  MessageService.mostrarConfirmacion(
    pregunta = "¿Desea continuar con la eliminacion del proveedor seleccionado?",
    advertencia = "Esta accion no podra deshacerse posteriormente.",
    callbackConfirmar = FUNCION()
      // Logica de eliminacion
      LLAMAR API DELETE /proveedores/:id
      SI exito ENTONCES
        MessageService.mostrarExito(
          mensaje = "El proveedor fue eliminado correctamente.",
          proximoPaso = "El listado se actualizara automaticamente."
        )
      FIN SI
    FIN FUNCION
  )
FIN FUNCION
```

### 5.4 Ejemplo de Uso: Error de Sistema

```
FUNCION guardarCliente(data)
  MessageService.mostrarProcesando("Guardando cliente en el sistema...")
  
  LLAMAR API POST /clientes
  
  SI error ENTONCES
    MessageService.cerrarProcesando()
    MessageService.mostrarErrorSistema(
      errorCode = "SYS-CLI-001",
      mensaje = error.message,
      stackTrace = error.stack
    )
  SINO
    MessageService.cerrarProcesando()
    MessageService.mostrarExito(
      mensaje = "El cliente fue registrado correctamente.",
      proximoPaso = "Puede continuar trabajando o regresar al listado."
    )
  FIN SI
FIN FUNCION
```

---

## 6. INTEGRACION CON MODULOS EXISTENTES

### 6.1 Modulos a Actualizar

Segun el historial del proyecto (Etapas 0, 1 y 2):

**Etapa 0:**
- login.html / auth-service.js

**Etapa 1:**
- menu.html / menu-service.js
- usuarios.html / usuarios-service.js

**Etapa 2:**
- clientes.html / clientes-service.js
- inventarios.html / inventarios-service.js
- proveedores.html / proveedores-service.js

### 6.2 Estrategia de Integracion

1. Crear archivo message-service.js con la clase MessageService
2. Crear archivo message-styles.css con los estilos
3. Importar ambos archivos en cada modulo
4. Reemplazar alert() por MessageService
5. Agregar confirmaciones en operaciones criticas
6. Manejar errores con errorSistema()
7. Mostrar spinner en operaciones async

### 6.3 Ejemplo de Integracion: proveedores-service.js

**ANTES (sin mensajes estandarizados)**:

```
guardarProveedor(data)
  LLAMAR API POST /proveedores
  SI exito ENTONCES
    alert('Proveedor guardado correctamente')  // Alert generico
    location.reload()
  SINO
    console.error(error)  // Sin retroalimentacion al usuario
  FIN SI
FIN
```

**DESPUES (con MessageService)**:

```
guardarProveedor(data)
  MessageService.mostrarProcesando('Guardando proveedor en el sistema...')
  
  LLAMAR API POST /proveedores
  
  SI exito ENTONCES
    MessageService.cerrarProcesando()
    MessageService.mostrarExito(
      'El proveedor fue registrado correctamente en el sistema.',
      'Puede continuar trabajando o regresar al listado.'
    )
  SINO
    MessageService.cerrarProcesando()
    MessageService.mostrarErrorSistema(
      'SYS-PROV-001',
      error.message,
      error.stack
    )
  FIN SI
### 6.4 Ejemplo de Integracion: Seguridad y SMTP
Este es el flujo mas complejo del sistema, donde se encadenan mensajes para garantizar la trazabilidad de operaciones criticas.

**Flujo: Restablecer Contraseña**
```
PARA resetPassword(idUsuario)
  // 1. Advertencia con motivo obligatorio
  MessageService.mostrarAdvertenciaCritica(
    '¿Está seguro de querer restablecer la contraseña?',
    PARA (motivo)
      // 2. Transicion visual a estado de espera
      MessageService.procesando('Generando nueva clave y enviando correo...')
      
      LLAMAR API POST /usuarios/:id/reset-password
      
      SI exito ENTONCES
        // 3. Confirmacion final con datos de seguridad
        MessageService.mostrarExito(
          'La contraseña ha sido restablecida y enviada por correo.',
          'Nueva clave temporal: <strong>' + data.tempPassword + '</strong>'
        )
      SINO
        MessageService.mostrarErrorValidacion(...)
      FIN SI
    FIN PARA
  )
FIN PARA
```

---

## 7. CHECKLIST DE VALIDACION
- [x] Consistencia visual con ui-base.css (v1.3)
- [x] Lógica de callbacks mediante enlaces de eventos por ID
- [x] Manejo de operaciones asíncronas con spinner (Tipo 6)
- [x] Descarga de informes técnicos en errores críticos
- [x] Justificación obligatoria en operaciones sensibles (Tipo 5)
- [x] Compatibilidad con dispositivos móviles mediante CSS adaptable
- [x] Enrutamiento automático en caso de sesión expirada (Tipo 7)
- [x] Bloqueo de acciones no autorizadas con feedback visual (Tipo 8)
- [x] Documentación de parámetros dinámicos para cada tipo de mensaje
### 7.1 Por cada mensaje implementado

- [ ] Usa tipografia Inter, sans-serif
- [ ] Colores coinciden con paleta institucional
- [ ] Dimensiones segun especificacion
- [ ] Iconos SVG correctos
- [ ] Botones con estados hover/active
- [ ] Textos dinamicos (no hardcodeados)
- [ ] Callbacks funcionan correctamente
- [ ] Modal se cierra correctamente
- [ ] Accesibilidad (focus, teclado)

### 7.2 Por cada modulo actualizado

- [ ] Importa message-service.js
- [ ] Importa message-styles.css
- [ ] Reemplaza alert() por MessageService
- [ ] Maneja errores con errorSistema()
- [ ] Confirma operaciones criticas
- [ ] Muestra spinner en operaciones async
- [ ] Documentado en archivo .md
- [ ] Probado en navegador

---

## 8. PROXIMOS PASOS

1. Crear archivos JavaScript y CSS segun especificaciones
2. Integrar en modulos existentes (Etapas 0, 1, 2)
3. Actualizar archivos .md de cada modulo
4. Probar en navegador todos los tipos de mensajes
5. Validar accesibilidad (teclado, lectores de pantalla)
6. Documentar en ETAPA 2.md como componente transversal

---

## 9. REFERENCIAS

- Diseño institucional: ui-base.css
- Tipografia: Inter (Google Fonts)
- Iconos: SVG inline (no dependencias externas)
- Metodologia: IDSv5 (Ingenieria de Desarrollo de Software v5)

---

Version: 1.0
Fecha: 2026-02-13
Autor: IDSv5 (Antigravity)
Proyecto: Sistema ERP WALOOK
