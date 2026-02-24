Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **23 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 05 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M05-01: Registrar proveedor / HU-M05-01: Consultar proveedor / HU-M05-06: Cumplimiento Fiscal (RFC) / HU-M05-05: Órdenes de Compra (OC) |
| **Nombre de la prueba:** | Gestión de Proveedores y Órdenes de Compra |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida gestión de abastecimiento |
| **Descripción:** | Validar el alta de proveedores, cumplimiento de formatos RFC, validación de contactos y gobernanza de Órdenes de Compra (OC). |

| Valores de entrada | Valores de salida | Resultado: | Gestión de proveedores operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema restringe OC a proveedores inactivos y valida RFC. | **Incorrecto** | El sistema deniega o alerta error. |
| **Atributo: Integridad del Registro:**<br>P1 [Persona Moral (12 car.)]<br>P2 [Persona Física (13 car.)]<br>P3 [Razón Social Vacía] | Válido<br>Válido<br>Alerta | [X]<br>[X]<br>[X] | **"Proveedor guardado exitosamente."** (HU-M05-01 / HU-M05-06) | [ ]<br>[ ]<br>[ ] | **"Para Persona MORAL el RFC debe tener 12 caracteres."**<br>**"La Razón Social y el Nombre Comercial son obligatorios."** |
| **Atributo: Validación de Contactos:**<br>P1 [Teléfono (10 dígitos)]<br>P2 [Email (rfc-822)]<br>P3 [Teléfono incompleto] | Formato OK<br>Formato OK<br>Rechazo | [X]<br>[X]<br>[X] | **Validación exitosa de contacto.** (HU-M05-01 / RNF-02) | [ ]<br>[ ]<br>[ ] | **"El teléfono debe tener 10 dígitos numéricos."**<br>**"Verifique el formato del correo electrónico."** |
| **Atributo: Gobernanza de Compras (OC):**<br>P1 [Crear OC: Prov. ACTIVO]<br>P2 [Crear OC: Prov. INACTIVO]<br>P3 [Cálculo IVA (16%)] | Válido<br>Bloqueo<br>Match OK | [X]<br>[X]<br>[X] | **Operación permitida/bloqueada según estatus.** (HU-M05-05) | [ ]<br>[ ]<br>[ ] | **"No se pueden crear OC para proveedores inactivos."** |
| **Atributo: Seguridad y Auditoría:**<br>P1 [Admin: Edición de Datos]<br>P2 [Vendedor: Solo Lectura]<br>P3 [Admin: Ver Bitácora] | UI Abierta<br>Lock UI<br>Lista Logs | [X]<br>[X]<br>[X] | **Gobernanza RBAC aplicada.** (RNF-04) | [ ]<br>[ ]<br>[ ] | **"No tiene permisos para ver el módulo de PROVEEDORES."**<br>**Botón de 'Gestión' deshabilitado.** |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda operativo. |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Formulario de Proveedor con validación de RFC Moral]
2. [Captura: Alerta de restricción de OC para proveedor Inactivo]
3. [Captura: Módulo de Auditoría Forense filtrado por el módulo de Proveedores]

---

# Matriz de resultados

**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.  
**Fecha de aplicación:** 23 de febrero de 2026  
**Encargado de realizar la prueba:** Gabriel Amilcar Cruz Canto

| Prueba No. | Ejecutada | Cumple con los escenarios | Observaciones |
| :--- | :--- | :--- | :--- |
| 01 | SI | SI | 100% Cobertura Matrix P1-Pn Login |
| 02 | SI | SI | 100% Cobertura Matrix P1-Pn Menú |
| 03 | SI | SI | 100% Cobertura Matrix P1-Pn Usuarios |
| 04 | SI | SI | 100% Cobertura Matrix P1-Pn Clientes |
| 05 | SI | SI | 100% Cobertura Matrix P1-Pn Proveedores |
| 06 | SI | SI | 100% Cobertura Matrix P1-Pn Inventarios |
| **Totales:** | **6** | **6** | **% de aprobación:** 100% |
