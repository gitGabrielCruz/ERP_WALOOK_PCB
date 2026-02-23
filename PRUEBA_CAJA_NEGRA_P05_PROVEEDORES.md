Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **22 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 05 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M05-01: Registrar proveedor / HU-M05-02: Consultar proveedor / HU-M05-03: Editar proveedor / HU-M05-04: Desactivar proveedor / HU-M05-05: Crear OC |
| **Nombre de la prueba:** | Gestión de Proveedores y Órdenes de Compra |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida gestión de abastecimiento |
| **Descripción:** | Validar el alta de proveedores, cumplimiento de RFC moral (12 caracteres) y la restricción operativa de nuevas OC según el estatus del proveedor. |

| Valores de entrada | Valores de salida | Resultado: | Gestión de proveedores operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema restringe OC a proveedores inactivos y valida RFC. | **Incorrecto** | El sistema permite OC a proveedores inactivos o RFCs inválidos. |
| **Registro de Proveedor (Moral):**<br>P1 [RFC Moral: 12 caracteres]<br>P2 [RFC Duplicado: Bloqueo]<br>P3 [Razón Social: Obligatoria] | Válido<br>Error<br>Válido | [X]<br>[X]<br>[X] | **"Proveedor guardado exitosamente."** | [ ]<br>[ ]<br>[ ] | |
| **Órdenes de Compra (OC):**<br>P1 [Crear OC: Proveedor Activo]<br>P2 [Crear OC: Proveedor Inactivo]<br>P3 [Cálculo de Totales (Iva 16%)] | Válido<br>Error<br>Válido | [X]<br>[X]<br>[X] | **"No se pueden crear OC para proveedores inactivos."** | [ ]<br>[ ]<br>[ ] | |
| **Validación de Datos:**<br>P1 [Teléfono: 10 dígitos]<br>P2 [Correo: Formato RFC-822] | Válido<br>Válido | [X]<br>[X] | **Formatos de contacto normalizados.** | [ ]<br>[ ] | |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 8 / 8 escenarios válidas |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Directorio de Proveedores con filtros de Condición de Pago]
2. [Alerta de bloqueo al intentar crear OC para proveedor Inactivo]
3. [Vista de Orden de Compra (BORRADOR) con desglose de IVA]

---

# Matriz de resultados

**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.  
**Fecha de aplicación:** 22 de febrero de 2026  
**Encargado de realizar la prueba:** Gabriel Amilcar Cruz Canto

| Prueba No. | Ejecutada | Cumple con los escenarios | Observaciones |
| :--- | :--- | :--- | :--- |
| 01 | SI | SI | 100% Cobertura Login |
| 02 | SI | SI | 100% Cobertura Menú/Semáforo |
| 03 | SI | SI | 100% Cobertura Usuarios/Permisos |
| 04 | SI | SI | 100% Cobertura Clientes/Fiscal |
| 05 | SI | SI | 100% Cobertura Proveedores/OC |
| **Totales:** | **5 / 6** | **5 / 5** | **% de aprobación:** 100% |
