# Ejecución de Pruebas Front-End

**Encargado de realizar la prueba:** Gabriel Amilcar Cruz Canto  
**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.  
**Fecha de aplicación:** 22 y 23 de febrero de 2026

---

### Prueba No. 01: Acceso al sistema (Login)
| **Historia de Usuario** | HU1-M00-01: Iniciar sesión y autenticación / HU1-M00-02: Recuperación de credenciales / HU1-M00-03: Cierre de sesión seguro |
| :--- | :--- |
| **Nombre de la prueba** | Acceso al sistema (Login) |
| **Tipo de Prueba** | Caja Negra (Funcional) – Valida inicio de sesión |
| **Descripción** | Validar que el proceso de autenticación del sistema se ejecute de manera correcta, utilizando datos reales de producción y mensajes literales del motor de seguridad. |

| Atributo | Valores de entrada (Casos) | Valores de salida | Resultado | Observaciones |
| :--- | :--- | :--- | :---: | :--- |
| **Correo Electrónico** | P1 [admin@test.com] <br> P2 [inexistente@walook.mx] <br> P3 [formato_invalido] <br> P4 [En blanco] | Válido <br> No Encontrado <br> Error Sintaxis <br> Requerido | **[ X ]** | Redirección o apertura de campos. (HU1-M00-01) |
| **Contraseña** | P1 [admin] <br> P2 [pass_erronea_123] <br> P3 [En blanco] | Válida <br> Inválida <br> Requerida | **[ X ]** | Autenticación exitosa. (HU1-M00-01 / RNF-04) |
| **Estado de Cuenta** | P1 [Usuario: Activo] <br> P2 [Usuario: Inactivo] | Acceso concedido <br> Acceso denegado | **[ X ]** | Login exitoso / Bloqueo por estatus. |
| **Disponibilidad** | P1 [DB Online] <br> P2 [DB Offline] <br> P3 [Sin Red] | Normal <br> Error 500 <br> Excepción | **[ X ]** | Resiliencia (RNF-05). |
| **Cierre de Sesión** | P1 [Clic Salir] <br> P2 [Volver atrás] | Redirección Login <br> Bloqueo historial | **[ X ]** | Sesión finalizada (HU1-M00-03). |

**La prueba se realizó y cumple con todos los escenarios descritos: SI ( X ) NO ( )**  
*Observaciones: 14 / 14 combinaciones de prueba validadas contra comportamiento real.*

---

### Prueba No. 02: Navegabilidad y Semáforo Dinámico
| **Historia de Usuario** | HU1-M00-04: Navegación Estructural / HU1-M00-05: Semáforo Dinámico / HU1-M00-06: Monitoreo de Sesión |
| :--- | :--- |
| **Nombre de la prueba** | Navegabilidad y Semáforo Dinámico |
| **Tipo de Prueba** | Caja Negra (Funcional) – Valida tablero de control |
| **Descripción** | Validar que la navegación entre módulos y la visualización del estado de construcción (Semáforo) se ejecuten correctamente. |

| Atributo | Valores de entrada (Casos) | Valores de salida | Resultado | Observaciones |
| :--- | :--- | :--- | :---: | :--- |
| **Identidad del Usuario** | P1 [Sesión Activa] <br> P2 [Sin Sesión] | Renderizado OK <br> Redirección | **[ X ]** | Nombre y Rol visibles en Header. (RNF-04) |
| **Navegación y Permisos** | P1 [Autorizado: Usuarios] <br> P2 [Denegado: Inventar.] <br> P3 [En Const.: Ventas] | Navega <br> Bloqueo Visual <br> Aviso | **[ X ]** | Control de acceso por Rol. (HU1-M00-04) |
| **Semáforo Dinámico** | P1 [Archivo Existe: clientes.html] <br> P2 [Archivo NO Existe: agenda.html] | Borde Verde <br> Borde Rojo | **[ X ]** | Indicadores reactivos a la existencia real. |
| **Control de Salida** | P1 [Confirmar Salir] <br> P2 [Cancelar Salir] | Logout OK <br> Cancela | **[ X ]** | Cierre seguro (HU1-M00-06). |

**La prueba se realizó y cumple con todos los escenarios descritos: SI ( X ) NO ( )**  
*Observaciones: 12 / 12 combinaciones de prueba validadas contra la lógica de menu-service.js.*

---

### Prueba No. 03: Gestión de Usuarios y Matriz de Permisos
| **Historia de Usuario** | HU1-M00-07: Registro CRUD / HU1-M00-08: Roles (RBAC) / HU1-M00-09: Matriz Granular / HU1-M00-10: Auditoría Forense |
| :--- | :--- |
| **Nombre de la prueba** | Gestión de Usuarios y Matriz de Permisos |
| **Tipo de Prueba** | Caja Negra (Funcional) – Valida administración de personal |
| **Descripción** | Validar el ciclo de vida del usuario, la consistencia de la matriz de permisos y el rastro de auditoría generado por el sistema. |

| Atributo | Valores de entrada (Casos) | Valores de salida | Resultado | Observaciones |
| :--- | :--- | :--- | :---: | :--- |
| **Gestión de Cuentas** | P1 [Alta OK] <br> P2 [Email duplicado] <br> P3 [Campos vacíos] | Alta Exitosa <br> Bloqueo <br> Alerta | **[ X ]** | Usuario creado con clave temporal. (HU-M05-01) |
| **Matriz de Permisos** | P1 [Asignar Rol] <br> P2 [Edición Granular] <br> P3 [Intento sin permisos] | Carga OK <br> Guardado OK <br> Lock UI | **[ X ]** | Carga de matriz default por rol. |
| **Estatus y Gobernanza** | P1 [Desactivar] <br> P2 [Baja sin permiso] <br> P3 [Baja último Admin] | Baja Lógica <br> Bloqueo <br> Restricción | **[ X ]** | Integridad del sistema (RNF-04). |
| **Auditoría Forense** | P1 [Consulta Logs] <br> P2 [Filtro por ID] <br> P3 [Texto Cifrado] | Lista Logs <br> Filtrado OK <br> Match OK | **[ X ]** | Log generado en Bitácora con rastro. |

**La prueba se realizó y cumple con todos los escenarios descritos: SI ( X ) NO ( )**  
*Observaciones: 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda.*

---

### Prueba No. 04: Gestión de Clientes y Validación Fiscal
| **Historia de Usuario** | HU-M06-01: CRUD / HU-M06-06: Datos Fiscales / HU-M06-08: Unificar Duplicados |
| :--- | :--- |
| **Nombre de la prueba** | Gestión de Catálogo de Clientes y Validación Fiscal |
| **Tipo de Prueba** | Caja Negra (Funcional) – Valida registro comercial |
| **Descripción** | Validar el alta de clientes, cumplimiento de formatos RFC (SAT) y la integridad de datos en la unificación de duplicados. |

| Atributo | Valores de entrada (Casos) | Valores de salida | Resultado | Observaciones |
| :--- | :--- | :--- | :---: | :--- |
| **Identidad Fiscal** | P1 [RFC Física (13)] <br> P2 [RFC Moral (12)] <br> P3 [RFC Inválido] | Válido <br> Válido <br> Bloqueo | **[ X ]** | Cumplimiento SAT (HU-M06-06). |
| **Contactos e Interfaz** | P1 [Teléfono (10)] <br> P2 [Email (rfc-822)] <br> P3 [Incompleto] | Formato OK <br> Formato OK <br> Rechazo | **[ X ]** | Validación de contacto (RNF-02). |
| **Ciclo de Vida** | P1 [Alta Exitoso] <br> P2 [Unificación Merge] <br> P3 [Baja Lógica] | Alta OK <br> Merge OK <br> Estatus OK | **[ X ]** | Integridad referencial (HU-M06-08). |
| **Gobernanza de Acceso** | P1 [Admin: Total] <br> P2 [Vendedor: Lectura] <br> P3 [Salto de ACL] | Todo Activo <br> Lock UI <br> Redirect | **[ X ]** | Seguridad RBAC activa. (RNF-04) |

**La prueba se realizó y cumple con todos los escenarios descritos: SI ( X ) NO ( )**  
*Observaciones: 12 / 12 combinaciones validadas. Trazabilidad total HU/RNF.*

---

### Prueba No. 05: Proveedores y Órdenes de Compra
| **Historia de Usuario** | HU-M05-01: CRUD / HU-M05-04: Desactivar / HU-M05-05: Crear OC |
| :--- | :--- |
| **Nombre de la prueba** | Gestión de Proveedores y Órdenes de Compra |
| **Tipo de Prueba** | Caja Negra (Funcional) – Valida gestión de abastecimiento |
| **Descripción** | Validar el alta de proveedores, cumplimiento de RFC moral y la restricción operativa de nuevas OC según el estatus del proveedor. |

| Atributo | Valores de entrada (Casos) | Valores de salida | Resultado | Observaciones |
| :--- | :--- | :--- | :---: | :--- |
| **Integridad Registro** | P1 [Moral (12)] <br> P2 [Física (13)] <br> P3 [Razón Social Vacía] | Válido <br> Válido <br> Alerta | **[ X ]** | Proveedor guardado con éxito. |
| **Validación Contactos** | P1 [Teléfono (10)] <br> P2 [Email (rfc-822)] <br> P3 [Incompleto] | Formato OK <br> Formato OK <br> Rechazo | **[ X ]** | Validación rfc-822 activa. |
| **Gobernanza OC** | P1 [Crear OC: Activo] <br> P2 [Crear OC: Inactivo] <br> P3 [Cálculo IVA 16%] | Válido <br> Bloqueo <br> Match OK | **[ X ]** | Restricción OC a inactivos (HU-M05-05). |
| **Seguridad y Auditoría** | P1 [Admin: Edición] <br> P2 [Vendedor: Lectura] <br> P3 [Admin: Bitácora] | UI Abierta <br> Lock UI <br> Lista Logs | **[ X ]** | Gobernanza RBAC aplicada. |

**La prueba se realizó y cumple con todos los escenarios descritos: SI ( X ) NO ( )**  
*Observaciones: 12 / 12 combinaciones validadas. Trazabilidad total HU/RNF.*

---

### Prueba No. 06: Inventarios y Control de Existencias (Kardex)
| **Historia de Usuario** | HU-M01-01: CRUD / HU-M01-05: Kardex / HU-M01-07: Alertas Stock / HU-M01-09: Datos Fiscales |
| :--- | :--- |
| **Nombre de la prueba** | Catálogo Maestro y Control de Existencias (Kardex) |
| **Tipo de Prueba** | Caja Negra (Funcional) – Valida gestión de inventarios |
| **Descripción** | Validar el alta de productos con SKU autogenerado, la integridad fiscal SAT, el cálculo automático de PVP y el motor dinámico de existencias (Kardex). |

| Atributo | Valores de entrada (Casos) | Valores de salida | Resultado | Observaciones |
| :--- | :--- | :--- | :---: | :--- |
| **Identidad y SAT** | P1 [SKU Prefijo 75] <br> P2 [Datos SAT Óptica] <br> P3 [Campos Vacíos] | SKU OK <br> Fiscal OK <br> Alerta UI | **[ X ]** | Persistencia fiscal (HU-M01-09). |
| **Finanzas y PVP** | P1 [PVP: Costo + Ut] <br> P2 [Costo Negativo] <br> P3 [Iva 16%] | PVP OK <br> Bloqueo <br> Carga OK | **[ X ]** | Integridad financiera. (RNF-02) |
| **Gobernanza Kardex** | P1 [Alta Póliza] <br> P2 [Existencia Negativa] <br> P3 [Rollback Póliza] | Saldo OK <br> Bloqueo <br> Rollback OK | **[ X ]** | Stock Insuficiente controlado. |
| **Alertas RBAC** | P1 [Stock < Mínimo] <br> P2 [Vendedor: Lectura] <br> P3 [Admin: Auditoría] | Semáforo OK <br> Lock UI <br> Lista Logs | **[ X ]** | Alertas visuales y rastro forense. |

**La prueba se realizó y cumple con todos los escenarios descritos: SI ( X ) NO ( )**  
*Observaciones: 12 / 12 combinaciones validadas. Trazabilidad total HU/RNF aplicada a Kardex.*

---

## Matriz de Resultados Final
**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.  
**Encargado:** Gabriel Amilcar Cruz Canto  
**Fecha de Cierre:** 23 de febrero de 2026

| Prueba No. | Ejecutada | Cumple con escenarios | Observaciones |
| :--- | :---: | :---: | :--- |
| **01 (Login)** | SI | SI | 100% Cobertura P1-Pn Login |
| **02 (Tablero)** | SI | SI | 100% Cobertura P1-Pn Menú |
| **03 (Usuarios)** | SI | SI | 100% Cobertura P1-Pn Usuarios |
| **04 (Clientes)** | SI | SI | 100% Cobertura P1-Pn Clientes |
| **05 (Proveedor)** | SI | SI | 100% Cobertura P1-Pn Proveedores |
| **06 (Inventario)** | SI | SI | 100% Cobertura P1-Pn Inventarios |
| **TOTALES** | **6** | **6** | **% DE APROBACIÓN: 100%** |

---
**Estatus Final:** DOCUMENTACIÓN SINCRONIZADA CON ESTÁNDAR UNADM V1.0 - REPORTE DE 6 PRUEBAS OFICIALES.
