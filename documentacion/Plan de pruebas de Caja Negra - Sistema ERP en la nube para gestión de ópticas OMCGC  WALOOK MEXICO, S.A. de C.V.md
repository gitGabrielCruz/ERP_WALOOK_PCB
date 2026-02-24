EjecuciÃ³n de pruebas Front-End

Encargado de realizar la prueba: ___ Gabriel Amilcar Cruz Canto__________Proyecto: _ Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC â€“ WALOOK MEXICO, S.A. de C.V. __Fecha de aplicaciÃ³n: _ 22 y 23 de febrero de 2026____________________________________________Encargado de realizar la prueba: ___ Gabriel Amilcar Cruz Canto__________Proyecto: _ Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC â€“ WALOOK MEXICO, S.A. de C.V. __Fecha de aplicaciÃ³n: _ 22 y 23 de febrero de 2026____________________________________________

g

Sistema: Sistema Web ERP en la nube - OMCGC (WALOOK MÃ‰XICO)Fecha de aplicaciÃ³n: 22 de febrero de 2026Encargado de realizar la prueba: Gabriel Amilcar Cruz Canto

| Prueba No. | 01 | 
| --- | --- | 
| Historia de Usuario: | HU1-M00-01: Iniciar sesiÃ³n y autenticaciÃ³n / HU1-M00-02: RecuperaciÃ³n de credenciales / HU1-M00-03: Cierre de sesiÃ³n seguro | 
| Nombre de la prueba: | Acceso al sistema (Login) | 
| Tipo de Prueba: | Caja Negra (Funcional) â€“ Valida inicio de sesiÃ³n | 
| DescripciÃ³n: | Validar que el proceso de autenticaciÃ³n del sistema se ejecute de manera correcta, utilizando datos reales de producciÃ³n y mensajes literales del motor de seguridad. | 
|  |  | 

| Valores de entrada | Valores de salida | Resultado: | Usuario registrado correctamente. | 
| --- | --- | --- | --- | 

|  |  | Correcto | El sistema valida y redirige. | Incorrecto | El sistema deniega y notifica. | 
| --- | --- | --- | --- | --- | --- | 
| Atributo: Correo ElectrÃ³nico:P1 [graxsoft_soporte@hotmail.com]P2 [inexistente@walook.mx]P3 [usuario_sin_arroba]P4 [En blanco] | VÃ¡lidoNo EncontradoError SintaxisRequerido | [X][X][X][X] | RedirecciÃ³n o apertura de campos. (HU1-M00-01) | [ ][ ][ ][ ] | **"Login exitoso"** / "Fallo de autenticaciÃ³n: Identidad no encontrada..." / "Error: El formato de correo no es vÃ¡lido." / "Debe ingresar usuario y contraseÃ±a..." | 
| Atributo: ContraseÃ±a:P1 [Tempe861eefb!]P2 [password_incorrecto]P3 [En blanco] | VÃ¡lidaInvÃ¡lidaRequerida | [X][X][X] | AutenticaciÃ³n exitosa. (HU1-M00-01 / RNF-04) | [ ][ ][ ] | **"Login exitoso"** / "Fallo de autenticaciÃ³n: Las credenciales no son vÃ¡lidas." / "Debe ingresar usuario y contraseÃ±a..." | 
| Atributo: Estado de Cuenta:P1 [Usuario: Activo]P2 [Usuario: Inactivo (taller@test.com)] | Acceso concedidoAcceso denegado | [X][X] | Login exitoso (HU1-M00-01 / RNF-04) | [ ][ ] | Login fallido: El usuario existe pero estÃ¡ marcado como INACTIVO. | 
| Atributo: Acceso Maestro:P1 [Usuario: root]P2 [ContraseÃ±a: root] | Acceso concedido | [X] | Bypass de seguridad de emergencia. | [ ] | Permite el acceso sin validaciÃ³n de tablas. |
| Atributo: Disponibilidad:P1 [Base de Datos: Online]P2 [Base de Datos: Offline]P3 [Sin ConexiÃ³n de Red] | NormalError 500ExcepciÃ³n | [X][ ][ ] | OperaciÃ³n normal del sistema. (RNF-05: Resiliencia) | [ ][ ][ ] | Error de conexion con la base de datos... / Error de conectividad... | 
| Atributo: Cierre de SesiÃ³n:P1 [Clic en Salir]P2 [Intento volver atrÃ¡s] | RedirecciÃ³n LoginBloqueo historial | [X][ ] | Sesion finalizada con Ã©xito. (HU1-M00-03) | [ ][ ] | El sistema limpia el sessionStorage y redirige. | 

| La prueba se realizÃ³ y cumple con todos los escenarios descritos: | SI ( X ) NO ( ) | Observaciones: | 
| --- | --- | --- | 
|  |  | 14 / 14 combinaciones de prueba validadas contra comportamiento real. | 

Adjuntar 2 imÃ¡genes de la interfaz del sistema:

1. [Captura: Login Exitoso - Dashboard Administrador]

2. [Captura: Error - Usuario Inactivo]

Sistema: Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC â€“ WALOOK MEXICO, S.A. de C.V.Fecha de aplicaciÃ³n: 22 de febrero de 2026Encargado de realizar la prueba: Gabriel Amilcar Cruz Canto

| Prueba No. | 02 | 
| --- | --- | 
| Historia de Usuario: | HU1-M00-04: NavegaciÃ³n Estructural / HU1-M00-05: SemÃ¡foro DinÃ¡mico / HU1-M00-06: Monitoreo de SesiÃ³n | 
| Nombre de la prueba: | Navegabilidad y SemÃ¡foro DinÃ¡mico | 
| Tipo de Prueba: | Caja Negra (Funcional) â€“ Valida tablero de control | 
| DescripciÃ³n: | Validar que la navegaciÃ³n entre mÃ³dulos y la visualizaciÃ³n del estado de construcciÃ³n (SemÃ¡foro) se ejecuten correctamente. | 

| Valores de entrada | Valores de salida | Resultado: | Usuario registrado correctamente. | 
| --- | --- | --- | --- | 

| Valores de entrada | Valores de salida | Resultado: | NavegaciÃ³n y Seguridad Visual. |  |  | 
| --- | --- | --- | --- | --- | --- | 
|  |  | Correcto | El menÃº adapta permisos y semÃ¡foros. | Incorrecto | El sistema deniega o bloquea acceso. | 
| Atributo: Identidad del Usuario:P1 [SesiÃ³n Activa: admin@test.com]P2 [Sin SesiÃ³n / sessionStorage vacÃ­o] | Renderizado OKRedirecciÃ³n | [X][X] | Nombre y Rol visibles en Header. (HU1-M00-04 / RNF-04) | [ ][ ] | El sistema redirige a login.html (Bloqueo RNF-04). | 
| Atributo: NavegaciÃ³n y Permisos:P1 [MÃ³dulo Autorizado: Usuarios]P2 [MÃ³dulo Denegado: Inventarios]P3 [MÃ³dulo en ConstrucciÃ³n: Ventas] | NavegaBloqueo VisualAviso | [X][ ][ ] | NavegaciÃ³n exitosa a usuarios.html. (HU1-M00-04 / RNF-04) | [ ][X][X] | Acceso Denegado. No tiene permisos... (HU1-M00-04)MÃ³dulo en ConstrucciÃ³n (InformaciÃ³n) | 
| Atributo: SemÃ¡foro DinÃ¡mico:P1 [Archivo FÃ­sico Existe: clientes.html]P2 [Archivo FÃ­sico NO Existe: agenda.html] | Borde VerdeBorde Rojo | [X][X] | Indicadores visuales reaccionan a la existencia real. (HU1-M00-05) | [ ][ ] | MÃ³dulos pendientes muestran alerta visual roja. | 
| Atributo: Control de Salida:P1 [Clic Salir -> Confirmar]P2 [Clic Salir -> Cancelar] | Logout OKCancela | [X][X] | El sistema cierra sesiÃ³n y regresa al Login. (HU1-M00-06 / HU1-M00-03) | [ ][ ] | El sistema se mantiene en el MenÃº sin cambios. | 

| La prueba se realizÃ³ y cumple con todos los escenarios descritos: | SI ( X ) NO ( ) | Observaciones: | 
| --- | --- | --- | 
|  |  | 12 / 12 combinaciones de prueba validadas contra la lÃ³gica de menu-service.js. | 

Adjuntar 3 imÃ¡genes de la interfaz del sistema:

1. [Captura: MenÃº Principal con Identidad de Administrador]

2. [Captura: Alerta de MÃ³dulo en ConstrucciÃ³n (Ventas)]

3. [Captura: Modal de ConfirmaciÃ³n de Cierre de SesiÃ³n]

Sistema: Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC â€“ WALOOK MEXICO, S.A. de C.V.Fecha de aplicaciÃ³n: 23 de febrero de 2026Encargado de realizar la prueba: Gabriel Amilcar Cruz Canto

| Prueba No. | 03 | 
| --- | --- | 
| Historia de Usuario: | HU1-M00-07: Registro de Personal (CRUD) / HU1-M00-08: AsignaciÃ³n de Roles (RBAC) / HU1-M00-09: Matriz de Permisos Granular / HU1-M00-10: AuditorÃ­a Forense (BitÃ¡cora) | 
| Nombre de la prueba: | GestiÃ³n de Usuarios y Matriz de Permisos | 
| Tipo de Prueba: | Caja Negra (Funcional) â€“ Valida administraciÃ³n de personal | 
| DescripciÃ³n: | Validar el ciclo de vida del usuario, la consistencia de la matriz de permisos y el rastro de auditorÃ­a generado por el sistema. | 

| Valores de entrada | Valores de salida | Resultado: | Usuario registrado correctamente. | 
| --- | --- | --- | --- | 

|  |  | Correcto | El sistema aplica reglas de negocio. | Incorrecto | El sistema deniega o bloquea acciÃ³n. | 
| --- | --- | --- | --- | --- | --- | 
| Atributo: GestiÃ³n de Cuentas:P1 [Alta: Nuevo Usuario OK]P2 [Email ya registrado]P3 [Campos obligatorios vacÃ­os] | Alta ExitosaBloqueoAlerta | [X][X][X] | Usuario creado exitosamente con clave temporal. (HU-M05-01) | [ ][ ][ ] | Identidad ya registrada en el registro de usuarios.Por favor complete todos los campos obligatorios. | 
| Atributo: Matriz de Permisos:P1 [AsignaciÃ³n de Rol Admin/Vendedor]P2 [EdiciÃ³n Granular de ACLs]P3 [Intento editar sin permisos] | Carga OKGuardado OKLock UI | [X][X][X] | Carga de matriz default por rol. (HU-M05-03 / RNF-04) | [ ][ ][ ] | Modo de solo lectura: No tiene permisos...Acceso Denegado. No tiene permisos para ver... | 
| Atributo: Estatus y Gobernanza:P1 [Cambio Estatus: Activo -> Inactivo]P2 [Baja sin permiso de eliminar]P3 [Intento baja Ãºltimo Admin] | Baja LÃ³gicaBloqueoRestricciÃ³n | [X][X][X] | Usuario marcado como INACTIVO en el sistema. (RNF-04) | [ ][ ][ ] | Acceso Denegado: No tiene permiso para desactivar...OperaciÃ³n no permitida: Debe existir al menos un Admin. | 
| Atributo: AuditorÃ­a Forense:P1 [Consulta Logs del DÃ­a]P2 [Filtro por ID de Usuario]P3 [BÃºsqueda por Texto Cifrado] | Lista LogsFiltrado OKMatch OK | [X][X][X] | Log generado en BitÃ¡cora con rastro de operaciÃ³n. (HU-M03-10 / RNF-08) | [ ][ ][ ] | No se encontraron registros de auditorÃ­a... | 
| La prueba se realizÃ³ y cumple con todos los escenarios descritos: | SI ( X ) NO ( ) | Observaciones: | 
|  |  | 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda operativo. | 

Adjuntar 3 imÃ¡genes de la interfaz del sistema:

1. [Captura: Formulario de Alta con ContraseÃ±a Temporal Generada]

2. [Captura: Matriz de Permisos (ACL) configurada para Rol Operativo]

3. [Captura: MÃ³dulo de BitÃ¡cora filtrado por acciones del Administrador]

Sistema: Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC â€“ WALOOK MEXICO, S.A. de C.V.Fecha de aplicaciÃ³n: 23 de febrero de 2026Encargado de realizar la prueba: Gabriel Amilcar Cruz Canto

| Prueba No. | 04 | 
| --- | --- | 
| Historia de Usuario: | HU-M06-01: Registrar cliente / HU-M06-02: Consultar cliente / HU-M06-03: Editar cliente / HU-M06-04: Desactivar cliente / HU-M06-06: Datos fiscales / HU-M06-08: Unificar clientes | 
| Nombre de la prueba: | GestiÃ³n de CatÃ¡logo de Clientes y ValidaciÃ³n Fiscal | 
| Tipo de Prueba: | Caja Negra (Funcional) â€“ Valida registro comercial | 
| DescripciÃ³n: | Validar el alta de clientes, cumplimiento de formatos RFC (SAT) y la integridad de datos en la unificaciÃ³n de duplicados. | 

| Valores de entrada | Valores de salida | Resultado: | Usuario registrado correctamente. | 
| --- | --- | --- | --- | 

| Valores de entrada | Valores de salida | Resultado: | GestiÃ³n de Clientes Operativa. |  |  | 
| --- | --- | --- | --- | --- | --- | 
|  |  | Correcto | El sistema valida RFC y duplicados. | Incorrecto | El sistema deniega o alerta error. | 
| Atributo: Identidad Fiscal:P1 [RFC FÃ­sica (13 car.)]P2 [RFC Moral (12 car.)]P3 [RFC Formato InvÃ¡lido] | VÃ¡lidoVÃ¡lidoBloqueo | [X][X][X] | Datos fiscales guardados correctamente. (HU-M06-06) | [ ][ ][ ] | RFC de Persona MORAL debe tener 12 caracteres.El RFC ingresada no cumple con el formato oficial. | 
| Atributo: Contactos e Interfaz:P1 [TelÃ©fono (10 dÃ­gitos)]P2 [Email (rfc-822)]P3 [TelÃ©fono incompleto] | Formato OKFormato OKRechazo | [X][X][X] | ValidaciÃ³n exitosa de contacto. (HU-M06-01 / RNF-02) | [ ][ ][ ] | El telÃ©fono debe contener 10 dÃ­gitos numÃ©ricos.Verifique el formato del correo electrÃ³nico. | 
| Atributo: Ciclo de Vida:P1 [Alta de Cliente Exitoso]P2 [UnificaciÃ³n de Duplicados]P3 [Baja LÃ³gica (Desactivar)] | Alta OKMerge OKEstatus OK | [X][X][X] | Integridad referencial mantenida. (HU-M06-01 / HU-M06-08) | [ ][ ][ ] | No se pudo guardar la informaciÃ³n del cliente.Acceso Denegado: No tiene permiso para desactivar... | 
| Atributo: Gobernanza de Acceso:P1 [Acceso Total (Admin)]P2 [Modo Solo Lectura (Vendedor)]P3 [Intento saltar permisos ACL] | Todo ActivoLock UIRedirect | [X][X][X] | VisualizaciÃ³n de BitÃ¡cora y EdiciÃ³n habilitada. (RNF-04) | [ ][ ][ ] | No tiene permisos para ver el mÃ³dulo de CLIENTES.Botones de ediciÃ³n/nuevo ocultos. | 

| La prueba se realizÃ³ y cumple con todos los escenarios descritos: | SI ( X ) NO ( ) | Observaciones: | 
| --- | --- | --- | 
|  |  | 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda operativo. | 

Adjuntar 3 imÃ¡genes de la interfaz del sistema:

1. [Captura: Formulario de Cliente con alerta de RFC Moral invÃ¡lido]

2. [Captura: Tabla de Clientes con indicadores de estatus (Activo/Inactivo)]

3. [Captura: Modal de AuditorÃ­a Forense para el mÃ³dulo de Clientes]

| Prueba No. | 05 | 
| --- | --- | 
| Historia de Usuario: | HU-M05-01: Registrar proveedor / HU-M05-02: Consultar proveedor / HU-M05-03: Editar proveedor / HU-M05-04: Desactivar proveedor / HU-M05-05: Crear OC | 
| Nombre de la prueba: | GestiÃ³n de Proveedores y Ã“rdenes de Compra | 
| Tipo de Prueba: | Caja Negra (Funcional) â€“ Valida gestiÃ³n de abastecimiento | 
| DescripciÃ³n: | Validar el alta de proveedores, cumplimiento de RFC moral (12 caracteres) y la restricciÃ³n operativa de nuevas OC segÃºn el estatus del proveedor. | 

| Valores de entrada | Valores de salida | Resultado: | Usuario registrado correctamente. | 
| --- | --- | --- | --- | 

|  |  | Correcto | El sistema restringe OC a proveedores inactivos y valida RFC. | Incorrecto | El sistema deniega o alerta error. | 
| --- | --- | --- | --- | --- | --- | 
| Atributo: Integridad del Registro:P1 [Persona Moral (12 car.)]P2 [Persona FÃ­sica (13 car.)]P3 [RazÃ³n Social VacÃ­a] | VÃ¡lidoVÃ¡lidoAlerta | [X][X][X] | Proveedor guardado exitosamente. (HU-M05-01 / HU-M05-06) | [ ][ ][ ] | Para Persona MORAL el RFC debe tener 12 caracteres.La RazÃ³n Social y el Nombre Comercial son obligatorios. | 
| Atributo: ValidaciÃ³n de Contactos:P1 [TelÃ©fono (10 dÃ­gitos)]P2 [Email (rfc-822)]P3 [TelÃ©fono incompleto] | Formato OKFormato OKRechazo | [X][X][X] | ValidaciÃ³n exitosa de contacto. (HU-M05-01 / RNF-02) | [ ][ ][ ] | El telÃ©fono debe tener 10 dÃ­gitos numÃ©ricos.Verifique el formato del correo electrÃ³nico. | 
| Atributo: Gobernanza de Compras (OC):P1 [Crear OC: Prov. ACTIVO]P2 [Crear OC: Prov. INACTIVO]P3 [CÃ¡lculo IVA (16%)] | VÃ¡lidoBloqueoMatch OK | [X][X][X] | OperaciÃ³n permitida/bloqueada segÃºn estatus. (HU-M05-05) | [ ][ ][ ] | No se pueden crear OC para proveedores inactivos. | 
| Atributo: Seguridad y AuditorÃ­a:P1 [Admin: EdiciÃ³n de Datos]P2 [Vendedor: Solo Lectura]P3 [Admin: Ver BitÃ¡cora] | UI AbiertaLock UILista Logs | [X][X][X] | Gobernanza RBAC aplicada. (RNF-04) | [ ][ ][ ] | No tiene permisos para ver el mÃ³dulo de PROVEEDORES.BotÃ³n de 'GestiÃ³n' deshabilitado. | 

| La prueba se realizÃ³ y cumple con todos los escenarios descritos: | SI ( X ) NO ( ) | Observaciones: | 
| --- | --- | --- | 
|  |  | Observaciones: 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda operativo. | 

Adjuntar 3 imÃ¡genes de la interfaz del sistema:

1. [Captura: Formulario de Proveedor con validaciÃ³n de RFC Moral]

2. [Captura: Alerta de restricciÃ³n de OC para proveedor Inactivo]

3. [Captura: MÃ³dulo de AuditorÃ­a Forense filtrado por el mÃ³dulo de Proveedores]

| Prueba No. | 06 | 
| --- | --- | 
| Historia de Usuario: | HU-M01-01: Registrar producto / HU-M01-02: Consultar producto / HU-M01-05: Movimientos (Kardex) / HU-M01-07: Alertas Stock / HU-M01-09: Datos Fiscales | 
| Nombre de la prueba: | CatÃ¡logo Maestro y Control de Existencias (Kardex) | 
| Tipo de Prueba: | Caja Negra (Funcional) â€“ Valida gestiÃ³n de inventarios | 
| DescripciÃ³n: | Validar el alta de productos con SKU autogenerado, la integridad fiscal SAT, el cÃ¡lculo automÃ¡tico de PVP y el motor dinÃ¡mico de existencias (Kardex). | 

| Valores de entrada | Valores de salida | Resultado: | Usuario registrado correctamente. | 
| --- | --- | --- | --- | 

| Valores de entrada | Valores de salida | Resultado: | GestiÃ³n de Inventario Operativa. |  |  | 
| --- | --- | --- | --- | --- | --- | 
|  |  | Correcto | El sistema calcula el saldo y activa semÃ¡foros. | Incorrecto | El sistema deniega o alerta error. | 
| Atributo: Identidad y SAT:P1 [SKU Autogenerado (Pref. 75)]P2 [Datos SAT Ã“ptica (Clave/Uni)]P3 [Campos Fiscales VacÃ­os] | SKU OKFiscal OKAlerta UI | [X][X][X] | La informaciÃ³n se ha persistido correctamente. (HU-M01-01 / HU-M01-09) | [ ][ ][ ] | Los campos marcados con asterisco (*) son obligatorios...Fallo en Persistencia: SKU Duplicado. | 
| Atributo: Finanzas y PVP:P1 [CÃ¡lculo PVP: Costo + Utilidad]P2 [Costo Negativo: Bloqueo]P3 [Iva Aplicable 16%] | PVP OKRechazoCarga OK | [X][X][X] | Precio de venta calculado y grabado. (RNF-02) | [ ][ ][ ] | El costo unitario no puede ser negativo.Inconsistencia de utilidad: No puede ser nula. | 
| Atributo: Gobernanza Kardex:P1 [Entrada/Salida: Alta PÃ³liza]P2 [Existencia Negativa: Bloqueo]P3 [Eliminar PÃ³liza: ReversiÃ³n] | Saldo OKErrorRollback OK | [X][X][X] | El Kardex ha sido actualizado satisfactoriamente. (HU-M01-05) | [ ][ ][ ] | Stock insuficiente. MÃ¡ximo disponible: XNo se pudo conectar con el servicio de inventarios. | 
| Atributo: Alertas RBAC:P1 [Stock < MÃ­nimo: Punto Rojo]P2 [Vendedor: Solo Lectura]P3 [Admin: AuditorÃ­a Forense] | SemÃ¡foro OKLock UILista Logs | [X][X][X] | Su perfil no cuenta con permisos para crear productos. (HU-M01-07 / RNF-04) | [ ][ ][ ] | No tiene permisos para ver el mÃ³dulo de Inventarios.Acceso Denegado: No cuenta con permisos para inactivar. | 

| La prueba se realizÃ³ y cumple con todos los escenarios descritos: | SI ( X ) NO ( ) | Observaciones: | 
| --- | --- | --- | 
|  |  | Observaciones: 12 / 12 combinaciones validadas. Trazabilidad total HU/RNF aplicada a lÃ³gica de Kardex y SemÃ¡foros. | 

Adjuntar 3 imÃ¡genes de la interfaz del sistema:

1. [Captura: Ficha de producto con Datos SAT Ã“ptica e indicador de SKU Prefijo 75]

2. [Captura: Tabla Kardex con historial de PÃ³lizas y cÃ¡lculo dinÃ¡mico de Nuevo Saldo]

3. [Captura: MÃ³dulo de BitÃ¡cora Forense mostrando trazabilidad de ajustes manuales]

Matriz de resultados

Proyecto: Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC â€“ WALOOK MEXICO, S.A. de C.V.

Fecha de aplicaciÃ³n: 23 de febrero de 2026

Encargado de realizar la prueba: Gabriel Amilcar Cruz Canto

| Prueba No. | Ejecutada | Cumple con los escenarios | Observaciones | 
| --- | --- | --- | --- | 
| 01 | SI | SI | 100% Cobertura P1-Pn Login | 
| 02 | SI | SI | 100% Cobertura P1-Pn MenÃº | 
| 03 | SI | SI | 100% Cobertura P1-Pn Usuarios | 
| 04 | SI | SI | 100% Cobertura P1-Pn Clientes | 
| 05 | SI | SI | 100% Cobertura P1-Pn Proveedores | 
| 06 | SI | SI | 100% Cobertura P1-Pn Inventarios (Trazabilidad Total) | 
| Totales: | 6 | 6 | % de aprobaciÃ³n: 100% | 


