---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** Diseño de Clase para Mensajes en Pantalla  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 🗯️ DISEÑO DE CLASE PARA MENSAJES EN PANTALLA


🧩 TIPO 1 --- ERROR_VALIDACION_CAMPO 1️⃣ Propósito Indicar que un campo
específico no cumple con las reglas de validación establecidas. No es
error del sistema. No es error de base de datos. Es error de entrada del
usuario.

🎨 2️⃣ Diseño Visual Contenedor Tipo: Modal rectangular Tamaño base:
520px x 300px Fondo: #ffffff Borde: 1px solid #d1d5db Border radius: 8px
Sin sombras Sin overlay incluido (lo controla layout)

🔴 Icono Librería: Google Material Symbols Outlined Icono recomendado:
error Color: #dc2626 Tamaño visual: 24px (escala SVG 1.2) Ubicación:
Esquina superior izquierda Significado: Indica error atribuible al
usuario.

🏷 3️⃣ Título Formato: Error en el campo {nombre_campo} Ejemplo: Error en
el campo RFC Estilo: Font: Inter Tamaño: 18px Peso: 600 Color: #1f2937
Debe ser específico, nunca genérico.

📄 4️⃣ Mensaje Principal Debe explicar: Qué está mal Por qué no es válido
Ejemplo: El valor ingresado en el campo RFC no cumple con el formato
oficial. Estilo: Tamaño: 15px Color: #374151

🟥 5️⃣ Campo Afectado (Visual) Elemento visual destacado: Fondo: #fef2f2
Borde: #dc2626 Border radius: 4px Altura: 40px Contiene: RFC ingresado:
XAXX01010 Estilo texto: Tamaño: 14px Color: #dc2626 Propósito: Refuerzo
visual del campo que debe corregirse.

📘 6️⃣ Instrucciones / Formato Esperado Debe indicar: Regla formal
Ejemplo válido Ejemplo: El RFC debe contener 12 o 13 caracteres
alfanuméricos. Ejemplo válido: XAXX010101000 Estilo: Tamaño: 14px Color:
#6b7280

🔘 7️⃣ Botones Botón 1 --- Cancelar Color fondo: #e5e7eb Texto: #374151
Acción esperada: Cierra el modal No modifica formulario No limpia datos
Uso: Cuando el usuario decide abandonar la acción.

Botón 2 --- Corregir Color fondo: #dc2626 Texto: #ffffff Acción
esperada: Cierra el modal Enfoca automáticamente el campo con error Hace
scroll si es necesario Activa estado "error" del input Uso: Flujo normal
esperado.

⚙ 8️⃣ Parámetros que debe recibir la clase type: "ERROR_VALIDACION_CAMPO"

fieldName: string fieldValue: string validationMessage: string
expectedFormat?: string example?: string

primaryActionLabel?: string // default: "Corregir"
secondaryActionLabel?: string // default: "Cancelar"

onPrimaryAction: () =\> void onSecondaryAction: () =\> void

🧠 9️⃣ Reglas de Comportamiento No debe permitir cerrar con ESC si es
validación obligatoria crítica. Puede permitir clic fuera si no es
obligatorio. No debe registrar log técnico. No debe mostrar stack trace.
No debe mostrar código interno.

🧩 10️⃣ Tipo de Mensaje Clasificación interna: Severity: UserError
Category: Validation Blocking: True (mientras no se corrija) LogLevel:
None

🧭 Flujo Esperado Usuario → Guarda → Sistema detecta error → Modal
muestra campo específico → Usuario presiona "Corregir" → Modal cierra →
Focus automático en input RFC → Campo con borde rojo → Usuario corrige →
Reintenta guardar.

🎯 Resultado Este mensaje: ✔ Es específico ✔ Es pedagógico ✔ Es claro ✔
No expone detalles técnicos ✔ Guía al usuario ✔ Mantiene consistencia
visual

🧩 TIPO 2 --- CONFIRMACION_SIMPLE 1️⃣ Propósito Solicitar confirmación
explícita del usuario antes de ejecutar una acción que: Modifica datos
Elimina registros Cambia estados Ejecuta procesos irreversibles No es
error. No es advertencia técnica. Es una decisión consciente del
usuario.

🎨 2️⃣ Diseño Visual Contenedor Tipo: Modal rectangular Tamaño base:
520px x 260px Fondo: #ffffff Borde: 1px solid #d1d5db Border radius: 8px
Sin sombra Sin overlay en SVG (lo controla layout)

🔵 Icono Librería: Google Material Symbols Outlined Icono recomendado:
help o info Color: #2563eb Tamaño visual: 24px Significado: Acción
deliberada que requiere confirmación.

🏷 3️⃣ Título Formato estándar: Confirmar acción O parametrizable:
Confirmar eliminación Confirmar actualización Confirmar cancelación
Estilo: Font: Inter Tamaño: 18px Peso: 600 Color: #1f2937

📄 4️⃣ Mensaje Principal Debe indicar claramente: Qué acción se va a
ejecutar Sobre qué objeto Ejemplo: ¿Desea continuar con la eliminación
del registro seleccionado? Estilo: Tamaño: 15px Color: #374151 Debe ser
claro, directo y sin ambigüedad.

📘 5️⃣ Mensaje Secundario Opcional pero recomendado si: La acción es
irreversible Tiene impacto en auditoría Ejemplo: Esta acción no podrá
deshacerse posteriormente. Estilo: Tamaño: 14px Color: #6b7280

🔘 6️⃣ Botones Botón Secundario --- Cancelar Fondo: #e5e7eb Texto:
#374151 Acción esperada: Cerrar modal No ejecutar acción No modificar
datos No generar logs Es el botón seguro.

Botón Primario --- Confirmar Fondo: #2563eb Texto: #ffffff Acción
esperada: Ejecutar operación Disparar callback principal Registrar
auditoría si aplica Cerrar modal automáticamente Es el botón de
ejecución.

⚙ 7️⃣ Parámetros que debe recibir la clase type: "CONFIRMACION_SIMPLE"

title?: string message: string secondaryMessage?: string

confirmLabel?: string // default: "Confirmar" cancelLabel?: string //
default: "Cancelar"

onConfirm: () =\> void onCancel: () =\> void

blocking?: boolean // default: true

🧠 8️⃣ Reglas de Comportamiento Puede cerrarse con ESC (si no es acción
crítica) Puede cerrarse clic fuera (configurable) No debe mostrar
información técnica No debe mostrar stack trace No debe registrar error

🧩 9️⃣ Clasificación Interna Severity: Neutral Category: Confirmation
Blocking: True LogLevel: None RequiresUserDecision: True

🧭 Flujo Esperado Usuario → Presiona "Eliminar" → Sistema muestra modal
→ Usuario presiona: Cancelar → No ocurre nada. Confirmar → Ejecuta
eliminación.

🎯 Resultado Este mensaje: ✔ Es claro ✔ Es neutral ✔ Es profesional ✔ No
es alarmista ✔ Obliga decisión consciente ✔ Mantiene coherencia con el
sistema

Perfecto. Formalizamos el Tipo 3 --- ERROR_SISTEMA_CON_REPORTE. Este es
el mensaje más delicado del sistema porque toca seguridad, trazabilidad
y soporte técnico.

🧩 TIPO 3 --- ERROR_SISTEMA_CON_REPORTE

1️⃣ Propósito Notificar que ocurrió un error interno del sistema y
permitir: Informar al usuario sin exponer información sensible. Permitir
la descarga de un archivo técnico estructurado. Facilitar trazabilidad
al desarrollador. Mantener separación entre vista usuario y detalle
técnico completo.

🎨 2️⃣ Componentes del Mensaje

🔴 Icono Librería: Google Material Symbols Outlined Icono: error Color:
#dc2626 Tamaño visual: 24px Ubicación: esquina superior izquierda
Significado: Error no atribuible al usuario.

🏷 Título Texto: Error del sistema Estilo: Font: Inter 18px Peso 600
Color #1f2937

📄 Mensaje Principal Texto recomendado: Ocurrió un error inesperado al
procesar la solicitud.

📘 Mensaje Secundario Texto: Puede descargar el reporte técnico y
enviarlo al área de desarrollo.

🧾 Área Técnica Visible Debe mostrar: Código de error Mensaje resumido
Archivo y línea NO debe mostrar: Contraseñas Tokens Datos sensibles
Variables de entorno Tipografía: monospace 12px Color #6b7280

🔘 3️⃣ Botones

Botón 1 --- Descargar reporte (.txt) Color: #2563eb Texto: blanco Acción
esperada: Generar archivo .txt Guardarlo en Descargas del usuario Nombre
estructurado Contenido completo técnico No cerrar el modal
automáticamente

Botón 2 --- Cerrar Color: #e5e7eb Acción: Cierra modal No altera estado
No elimina error No ejecuta lógica adicional

📁 4️⃣ Formato del Nombre del Archivo Formato obligatorio:
ERROR\_{ErrorCode}*{YYYYMMDD}*{HHMMSS}.txt Ejemplo:
ERROR_SYS-DB-001_20260214_193522.txt Debe generarse en horario local del
cliente.

📄 5️⃣ Contenido Interno del Archivo TXT Estructura recomendada:
======================================== REPORTE TECNICO DE ERROR DEL
SISTEMA ========================================

Codigo de error: SYS-DB-001

Fecha y hora: 2026-02-14 19:35:22

Usuario: admin

Modulo: Clientes

Operacion: Guardar registro

  -------------------
  Detalle del error
  -------------------

Mensaje tecnico: SQLSTATE\[HY000\]: Field 'rfc' doesn't have a default
value

Archivo: ClienteController.ts

Linea: 87

Tabla involucrada: cliente

Campo: rfc

Variable: cliente.rfc

Stack trace resumido: at ClienteController.guardar() at
ClienteService.create() at DatabaseAdapter.insert()

  ---------
  Entorno
  ---------

Aplicacion: Graxsoft ERP v5.0

Ambiente: Produccion

Navegador: Chrome 122

Sistema operativo: Windows 11

  -----------------
  Fin del reporte
  -----------------

⚙ 6️⃣ Parámetros que debe recibir la clase type:
"ERROR_SISTEMA_CON_REPORTE"

errorCode: string message: string technicalMessage: string

module?: string operation?: string fileName?: string lineNumber?: number
tableName?: string fieldName?: string variableName?: string

stackTrace?: string\[\] environment?: { appVersion: string environment:
string browser?: string os?: string }

user?: string

onClose: () =\> void

🧠 7️⃣ Clasificación Interna Severity: SystemError Category: Runtime
Blocking: True LogLevel: Critical DownloadableReport: True
AuditRequired: True

🔒 8️⃣ Reglas de Seguridad ✔ Nunca incluir: Passwords Tokens Claves
privadas Queries completas con datos sensibles ✔ Sanitizar: Parámetros
dinámicos Valores de usuario ✔ Permitir descarga solo: Cuando errorCode
esté definido Cuando el error no sea de seguridad crítica

🧭 9️⃣ Flujo Esperado Sistema detecta excepción → Genera errorCode único
→ Muestra modal → Usuario: Descarga reporte → genera archivo Cierra
modal → vuelve al sistema Desarrollador recibe archivo → Reproduce error
→ Corrige.

🎯 Resultado Este mensaje: ✔ Es profesional ✔ Es auditable ✔ Es seguro ✔
Separa usuario y desarrollador ✔ Facilita soporte ✔ Escala para
producción

🧩 TIPO 4 --- AVISO_EXITO (Operación Exitosa)

1️⃣ Propósito Indicar que una operación se ejecutó correctamente. No es
confirmación. No es advertencia. No es error. Es retroalimentación
positiva y clara. Debe transmitir: ✔ Finalización exitosa ✔ Seguridad ✔
Continuidad de flujo

🎨 2️⃣ Componentes del Diseño

🟢 Icono Librería: Google Material Symbols Outlined Icono recomendado:
check_circle Color: #10b981 Tamaño visual: 24px Significado: Operación
finalizada correctamente.

🏷 Título Texto estándar: Operación completada Parametrizable según
contexto: Registro guardado Movimiento registrado Producto actualizado
Cliente creado Eliminación exitosa Estilo: Font: Inter 18px Peso 600
Color #1f2937

📄 Mensaje Principal Debe indicar claramente qué ocurrió. Ejemplo: El
registro fue guardado correctamente en el sistema. Estilo: 15px Color
#374151

📘 Mensaje Secundario (Opcional) Sirve para guiar al usuario. Ejemplos:
Puede continuar trabajando o regresar al listado. El inventario fue
actualizado automáticamente. Estilo: 14px Color #6b7280

🔘 3️⃣ Botón Botón Único --- Aceptar Fondo: #10b981 Texto: blanco Acción
esperada: Cerrar modal Continuar flujo Redirigir si aplica
(configurable)

⚙ 4️⃣ Parámetros que debe recibir la clase type: "AVISO_EXITO"

title?: string message: string secondaryMessage?: string

buttonLabel?: string // default: "Aceptar"

onClose: () =\> void

autoClose?: boolean autoCloseTime?: number redirectTo?: string

🧠 5️⃣ Clasificación Interna Severity: Success Category: Feedback
Blocking: False LogLevel: None RequiresDecision: False

📌 6️⃣ Eventos donde se puede usar El desarrollador puede usar este tipo
en los siguientes casos: Crear registro Actualizar registro Eliminar
registro Registrar movimiento Cambiar estatus Procesar transacción
Exportar archivo Guardar configuración Finalizar proceso batch Confirmar
sincronización ⚠ IMPORTANTE: La lista anterior no es limitada. Puede
utilizarse en cualquier evento siempre que: Sea una operación exitosa No
requiera confirmación adicional No implique advertencia No implique
error No requiera acción adicional obligatoria Es decir: Debe utilizarse
exclusivamente para eventos del tipo "operación completada
exitosamente". No debe usarse como: Confirmación Advertencia Mensaje
informativo neutro Resultado parcial

🧭 7️⃣ Flujo Esperado Usuario ejecuta acción → Sistema valida → Operación
correcta → Muestra AVISO_EXITO → Usuario presiona "Aceptar" → Modal
cierra → Continúa flujo.

🎯 Resultado Este mensaje: ✔ Refuerza confianza ✔ Mantiene consistencia
visual ✔ Evita ambigüedad ✔ Es reutilizable ✔ No genera ruido técnico ✔
No sobrecarga la UI

Perfecto. Formalizamos el Tipo 5 --- CONFIRMACION_AVANZADA
correctamente, incluyendo trazabilidad obligatoria.

🧩 TIPO 5 --- CONFIRMACION_AVANZADA (Operación crítica con motivo
obligatorio y registro de auditoría)

1️⃣ Propósito Solicitar confirmación explícita en operaciones que:
Modifican atributos sensibles Alteran contenido existente Eliminan
registros Inactivan registros Cambian estados críticos Afectan
integridad del sistema Este mensaje: ✔ No es opcional ✔ No puede
omitirse ✔ Es obligatorio en cambios de contenido ✔ Genera trazabilidad
formal

🎨 2️⃣ Componentes Visuales

🟠 Icono Librería: Google Material Symbols Outlined Icono recomendado:
warning Color: #f59e0b Tamaño: 24px Significado: Acción con impacto
significativo.

🏷 Título Formato estándar: Confirmar operación crítica Puede
parametrizarse según contexto: Confirmar modificación Confirmar
eliminación Confirmar inactivación Estilo: Inter 18px Peso 600 Color
#1f2937

📄 Mensaje Principal Debe indicar: Que la acción modificará datos Que
requiere motivo Ejemplo: Esta acción modificará información del sistema.
Es obligatorio indicar el motivo antes de continuar.

📝 Campo Obligatorio Label: Motivo (obligatorio) Textarea: Requerido No
permite vacío No permite solo espacios Mínimo recomendado: 10 caracteres
Máximo recomendado: 500 caracteres Validación: Si está vacío → no
permite confirmar Si es inválido → mostrar ERROR_VALIDACION_CAMPO

🔘 3️⃣ Botones

Botón Cancelar Color: #e5e7eb Acción: Cerrar modal No ejecutar operación
No registrar nada

Botón Confirmar Color: #ef4444 Acción: Validar motivo Ejecutar operación
Registrar trazabilidad obligatoria Cerrar modal

🧠 4️⃣ Clasificación Interna Severity: CriticalAction Category:
ConfirmWithAudit Blocking: True RequiresUserInput: True
RequiresAuditLog: True

📌 5️⃣ Obligación de Uso Este mensaje debe aparecer en: ✔ Modificación de
atributos en cualquier tabla: producto cliente proveedor usuario
inventario configuración ✔ Eliminación de registros ✔ Inactivación de
registros ✔ Cambios de estatus ✔ Reversiones ✔ Ajustes manuales No debe
utilizarse para: Creación inicial de registro Operaciones sin impacto
estructural Consultas

📊 6️⃣ Registro Obligatorio en Base de Datos El motivo debe guardarse en
dos lugares:

A)  En el registro afectado Campo obligatorio adicional: logEspecial
    TEXT Debe registrar: Motivo Usuario Fecha Operación Ejemplo
    guardado: \[2026-02-14 19:35:22\] Usuario: admin Motivo: Corrección
    de datos fiscales solicitada por el cliente. Operacion:
    Actualizacion de atributo rfc.

B)  En tabla de logs del sistema Tabla recomendada: system_log Campos
    mínimos: CREATE TABLE system_log ( id_log VARCHAR(20) PRIMARY KEY,
    modulo VARCHAR(100), tabla VARCHAR(100), id_registro VARCHAR(50),
    tipo_evento VARCHAR(50), descripcion TEXT, motivo TEXT, usuario
    VARCHAR(50), fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ); Ejemplo
    registro: tipo_evento: UPDATE_CRITICO modulo: Clientes tabla:
    cliente id_registro: CLI-00015 motivo: Corrección de datos fiscales
    solicitada por el cliente

⚙ 7️⃣ Parámetros que debe recibir la clase type: "CONFIRMACION_AVANZADA"

title?: string message: string requireReason: true

entityName: string entityId: string tableName: string operationType:
"UPDATE" \| "DELETE" \| "INACTIVATE" \| "STATUS_CHANGE"

onConfirm: (reason: string) =\> void onCancel: () =\> void

🧭 8️⃣ Flujo Esperado Usuario intenta modificar registro → Sistema
detecta operación crítica → Muestra CONFIRMACION_AVANZADA → Usuario
escribe motivo → Presiona Confirmar → Sistema: Ejecuta operación
Actualiza campo logEspecial del registro Inserta evento en system_log
Continúa flujo

🔒 9️⃣ Reglas de Seguridad El motivo no puede ser alterado por frontend.
Backend debe validar que el motivo exista. No permitir bypass vía API
directa. Debe registrarse aunque falle la operación.

🎯 Resultado Este mensaje: ✔ Impone responsabilidad ✔ Genera
trazabilidad ✔ Cumple auditoría ✔ Permite reconstrucción histórica ✔
Aumenta integridad del sistema ✔ Escala para producción empresarial

Perfecto. Formalizamos el Tipo 6 --- LOADING_BLOQUEANTE con
especificación completa y agregando el uso de GIF animado.

🧩 TIPO 6 --- LOADING_BLOQUEANTE (Procesando operación crítica o de
larga duración)

1️⃣ Propósito Indicar que el sistema está ejecutando una operación que:
Requiere tiempo de procesamiento No puede interrumpirse Está en
ejecución en backend Depende de recursos externos Este mensaje: ✔
Bloquea interacción ✔ No permite confirmación ✔ No permite cancelar ✔ No
permite cerrar ✔ No muestra errores

🎨 2️⃣ Componentes del Diseño

🔵 Icono / Indicador Visual Tipo: GIF animado circular Reemplaza al SVG
estático Debe ser: Animación fluida Circular Color institucional azul
(#2563eb) Fondo transparente Tamaño recomendado: 36px--48px Loop
infinito Sin texto dentro Ejemplo conceptual: Un círculo con segmento
girando continuamente. Propósito: Simular actividad del sistema. ⚠
Importante: No usar animaciones agresivas. No usar colores rojos. No
usar efectos brillantes.

🏷 Título Texto estándar: Procesando operación Parametrizable: Conectando
a la base de datos Generando reporte Actualizando información
Sincronizando datos Cargando inventario Estilo: Inter 18px Peso 600
Color #1f2937

📄 Mensaje Principal Debe indicar acción clara: Espere mientras se
completa la solicitud.

📘 Mensaje Secundario Debe advertir: No cierre la ventana ni actualice
la página. Opcional: El tiempo puede variar según el volumen de
información.

🚫 3️⃣ Botones No tiene botones. No debe permitir: Cancelar Cerrar ESC
Clic fuera Es completamente bloqueante.

📌 4️⃣ Casos de Uso Debe mostrarse en: ✔ Conexión inicial a base de datos
✔ Consultas de gran escala ✔ Generación de reportes pesados ✔
Exportaciones masivas ✔ Procesamiento batch ✔ Reindexaciones ✔
Migraciones internas ✔ Actualización masiva de registros ✔
Sincronización con APIs externas ✔ Recalculo de inventarios ⚠ No usar
en: Operaciones menores Validaciones rápidas Acciones que tarden menos
de 300ms

⚙ 5️⃣ Parámetros que debe recibir la clase type: "LOADING_BLOQUEANTE"

title?: string message?: string secondaryMessage?: string

animatedIcon?: string // ruta del GIF

blockUI: true preventClose: true timeout?: number

onTimeout?: () =\> void

🧠 6️⃣ Clasificación Interna Severity: Processing Category:
BlockingOperation Blocking: True UserInteraction: Disabled LogLevel:
None

🧭 7️⃣ Flujo Esperado Usuario ejecuta acción → Sistema valida → Muestra
LOADING_BLOQUEANTE → Backend procesa → Si éxito → Oculta Loading →
Muestra AVISO_EXITO Si error → Oculta Loading → Muestra ERROR_SISTEMA

⏱ 8️⃣ Control de Tiempo Recomendación técnica: Si supera 15 segundos:
Mostrar mensaje adicional: El proceso está tomando más tiempo del
esperado. Si supera 60 segundos: Activar control de timeout Lanzar
ERROR_SISTEMA_CON_REPORTE

🔒 9️⃣ Reglas de Seguridad No mostrar información técnica No exponer
estado interno No mostrar progreso falso No permitir múltiples
instancias simultáneas Debe existir solo un loading activo

🎯 Resultado Este mensaje: ✔ Comunica proceso activo ✔ Reduce ansiedad
del usuario ✔ Evita clics repetidos ✔ Protege operaciones críticas ✔
Mantiene coherencia visual ✔ Se integra con flujo de errores y éxito

Perfecto. Formalizamos el Tipo 7 --- SESION_EXPIRADA con especificación
completa y comportamiento obligatorio.

🧩 TIPO 7 --- SESION_EXPIRADA (Expiración automática por inactividad)

1️⃣ Propósito Notificar que la sesión del usuario fue cerrada
automáticamente debido a: Tiempo máximo de inactividad configurado
Políticas de seguridad del sistema Expiración de token de autenticación
Invalidación de sesión por backend Este mensaje: ✔ Es de seguridad ✔ Es
bloqueante ✔ No es error ✔ No permite continuar ✔ Obliga reautenticación

🎨 2️⃣ Componentes del Diseño

🔐 Icono Librería: Google Material Symbols Outlined Icono recomendado:
lock Color: #2563eb Tamaño visual: 24px Significado: Acceso restringido
por seguridad.

🏷 Título Texto fijo recomendado: Sesión expirada Estilo: Inter 18px Peso
600 Color #1f2937 No debe ser parametrizable para evitar ambigüedad.

📄 Mensaje Principal Texto: Su sesión ha finalizado por inactividad o
motivos de seguridad.

📘 Mensaje Secundario Texto: Inicie sesión nuevamente para continuar
trabajando. No debe incluir: Tiempo exacto restante Tokens Información
técnica Mensajes de error

🔘 3️⃣ Botón Botón Único --- Iniciar sesión Fondo: #2563eb Texto: blanco
No debe existir botón Cancelar No debe permitir cerrar modal

⚙ 4️⃣ Acción Esperada del Botón El botón debe ejecutar la siguiente
secuencia obligatoria: Invalidar estado frontend actual Limpiar memoria
local: localStorage sessionStorage Eliminar token JWT Cerrar conexiones
abiertas: WebSocket conexiones persistentes Reiniciar estado global del
sistema Redirigir al módulo de Login Secuencia técnica recomendada:
clearSession(); disconnectSockets(); resetAppState();
redirectTo("/login");

🚫 5️⃣ Restricciones Este mensaje: No puede cerrarse con ESC No puede
cerrarse con clic fuera No puede ocultarse No puede ignorarse No permite
interacción con fondo Debe bloquear completamente la aplicación.

📌 6️⃣ Condición de Activación Debe mostrarse únicamente cuando: Se
cumple el tiempo máximo de inactividad definido en políticas El backend
responde 401 por expiración de token Se invalida sesión desde servidor
Se detecta manipulación de token No debe mostrarse en: Errores de red
Problemas de servidor Fallos de autenticación inicial

🧠 7️⃣ Clasificación Interna Severity: Security Category: SessionControl
Blocking: True RequiresReauthentication: True LogLevel: Info

🗂 8️⃣ Parámetros que debe recibir la clase type: "SESION_EXPIRADA"

timeoutDuration?: number redirectUrl?: string // default "/login"

onLogout: () =\> void

🧭 9️⃣ Flujo Esperado Usuario inactivo → Supera tiempo máximo configurado
→ Sistema invalida token → Muestra SESION_EXPIRADA → Usuario presiona
"Iniciar sesión" → Sistema limpia estado → Redirige a Login → Usuario se
autentica nuevamente.

🔒 🔟 Política de Seguridad Asociada El sistema debe tener configurado:
Tiempo máximo de inactividad (ejemplo: 15 minutos) Renovación automática
solo si hay actividad Auditoría del evento de expiración Opcional
registrar en logs: EVENT: SESSION_EXPIRED USER: admin IP: 192.168.1.20
TIMESTAMP: 2026-02-14 20:15:22

🎯 Resultado Este mensaje: ✔ Refuerza políticas de seguridad ✔ Cumple
estándares empresariales ✔ Evita acceso indebido ✔ Limpia correctamente
el estado ✔ Mantiene coherencia visual ✔ Es obligatorio en sistemas con
sesión

Perfecto. Formalizamos el Tipo 8 --- PERMISO_DENEGADO correctamente y
con enfoque de seguridad.

🧩 TIPO 8 --- PERMISO_DENEGADO (Control de acceso por rol o privilegio
insuficiente)

1️⃣ Propósito Notificar que el usuario autenticado no tiene autorización
para ejecutar la acción solicitada. Este mensaje: ✔ No es error técnico
✔ No es fallo del sistema ✔ Es restricción por política de permisos ✔ Es
de seguridad lógica Debe evitar: Exponer roles internos Mostrar
jerarquías Indicar qué permiso exacto falta Revelar estructura interna
del sistema

🎨 2️⃣ Componentes Visuales

🔴 Icono Librería: Google Material Symbols Outlined Icono recomendado:
block Color: #dc2626 Tamaño visual: 24px Significado: Acción prohibida.

🏷 Título Texto estándar: Acceso restringido No debe cambiar
dinámicamente. Estilo: Inter 18px Peso 600 Color #1f2937

📄 Mensaje Principal Texto: No cuenta con los permisos necesarios para
realizar esta acción. Debe ser claro y neutral.

📘 Mensaje Secundario Texto: Si considera que se trata de un error,
contacte al administrador. No debe incluir: Nombre del permiso Nivel
requerido Rol necesario Código interno

🔘 3️⃣ Botón Botón Único --- Cerrar Fondo: #e5e7eb Texto: #374151 Acción:
Cierra modal No ejecuta operación No redirige automáticamente

⚙ 4️⃣ Acción Esperada del Sistema Cuando se detecta falta de permiso:
Bloquear ejecución del endpoint Registrar intento en logs Mostrar
PERMISO_DENEGADO No debe: Ejecutar parcialmente la acción Permitir
bypass desde frontend Permitir manipulación vía consola

📌 5️⃣ Condición de Activación Debe mostrarse cuando: El usuario intenta
acceder a un módulo sin permiso Intenta ejecutar acción CRUD sin
privilegio Endpoint responde HTTP 403 Backend detecta rol insuficiente
No debe mostrarse en: Sesión expirada (Tipo 7) Error de autenticación
(login incorrecto) Error del sistema (Tipo 3)

🧠 6️⃣ Clasificación Interna Severity: Security Category: Authorization
Blocking: True RequiresReauthentication: False LogLevel: Warning

📊 7️⃣ Registro en Logs Debe registrarse en tabla system_log: Ejemplo:
EVENT: PERMISSION_DENIED USER: caja01 MODULO: Inventarios ACCION:
Eliminar producto TABLA: producto ID_REGISTRO: PRD-00045 TIMESTAMP:
2026-02-14 20:32:15 No debe registrarse como error crítico.

⚙ 8️⃣ Parámetros que debe recibir la clase type: "PERMISO_DENEGADO"

module?: string action?: string entityId?: string

onClose: () =\> void

🧭 9️⃣ Flujo Esperado Usuario intenta acción → Backend valida permisos →
Permiso insuficiente → Muestra PERMISO_DENEGADO → Usuario presiona
Cerrar → Sistema regresa al estado anterior.

🔒 🔟 Reglas de Seguridad No mostrar detalles técnicos. No exponer
nombre del permiso faltante. No mostrar rol requerido. No revelar
estructura interna. Debe protegerse tanto frontend como backend.

🎯 Resultado Este mensaje: ✔ Refuerza control de acceso ✔ Mantiene
confidencialidad ✔ Evita filtración de información ✔ Mantiene coherencia
visual ✔ Es fundamental en sistemas empresariales

🧩 TIPO 9 --- ADVERTENCIA (Aviso preventivo no bloqueante)

1️⃣ Propósito Informar al usuario que: Existe una condición relevante Se
aproxima un límite Se está violando una política leve Se recomienda
precaución Este mensaje: ✔ Es solo un aviso ✔ No es error ✔ No es
confirmación ✔ No bloquea el sistema ✔ No impide continuar

🎨 2️⃣ Componentes Visuales

🟠 Icono Librería: Google Material Symbols Outlined Icono recomendado:
warning Color: #f59e0b Tamaño visual: 24px Significado: Condición
preventiva o alerta moderada.

🏷 Título Texto estándar: Advertencia Puede parametrizarse: Advertencia
de inventario Advertencia de política Advertencia de configuración
Estilo: Inter 18px Peso 600 Color #1f2937

📄 Mensaje Principal Debe indicar claramente la situación detectada.
Ejemplo inventario: El producto se encuentra por debajo del stock mínimo
configurado.

📘 Mensaje Secundario Debe orientar al usuario: Se recomienda programar
reposición para evitar desabasto.

🔘 3️⃣ Botón Botón Único --- Aceptar Fondo: #f59e0b Texto: blanco Acción:
Cierra modal Permite continuar flujo normal No cancela operación

⚙ 4️⃣ Naturaleza No Bloqueante Este mensaje: No cancela acción No
revierte operación No bloquea el sistema No invalida transacción Es solo
informativo. Debe permitir: Continuar proceso Completar venta Confirmar
orden Guardar cambios

📌 5️⃣ Casos de Uso Ejemplos comunes: ✔ Al asignar producto a orden y la
cantidad baja el stock al mínimo ✔ Cuando el stock queda en límite
inferior ✔ Cuando el usuario rebasa una recomendación ✔ Cuando se
aproxima fecha de vencimiento ✔ Cuando una política leve es superada ✔
Cuando se detecta uso atípico pero permitido ✔ Cuando se modifica
configuración sensible sin ser crítica ⚠ Importante: La lista no es
limitada. Puede utilizarse en cualquier caso que: Sea preventivo No sea
error No requiera confirmación obligatoria No comprometa seguridad

🧠 6️⃣ Clasificación Interna Severity: Warning Category: PreventiveNotice
Blocking: False RequiresDecision: False LogLevel: Optional

⚙ 7️⃣ Parámetros que debe recibir la clase type: "ADVERTENCIA"

title?: string message: string secondaryMessage?: string

buttonLabel?: string // default: "Aceptar"

allowContinue: true

onClose: () =\> void

🧭 8️⃣ Flujo Esperado Usuario ejecuta acción → Sistema detecta condición
preventiva → Muestra ADVERTENCIA → Usuario presiona Aceptar → Modal se
cierra → Sistema continúa flujo normal.

🔒 9️⃣ Reglas No mostrar detalles técnicos No mostrar códigos internos No
usar rojo (eso es error) No usar botón Cancelar No generar alarma
innecesaria

🎯 Resultado Este mensaje: ✔ Reduce riesgo operativo ✔ Informa sin
bloquear ✔ Permite continuidad ✔ Mantiene consistencia visual ✔ Refuerza
políticas internas ✔ Mejora experiencia de usuario
