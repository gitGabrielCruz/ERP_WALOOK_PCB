Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **22 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 04 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M06-01: Registrar cliente / HU-M06-02: Consultar cliente / HU-M06-03: Editar cliente / HU-M06-04: Desactivar cliente / HU-M06-06: Datos fiscales / HU-M06-08: Unificar clientes |
| **Nombre de la prueba:** | Gestión de Catálogo de Clientes y Validación Fiscal |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida registro comercial |
| **Descripción:** | Validar el alta de clientes, cumplimiento de formatos RFC (SAT) y la integridad de datos en la unificación de duplicados. |

| Valores de entrada | Valores de salida | Resultado: | Gestión de clientes operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema valida RFC y duplicados. | **Incorrecto** | El sistema permite datos fiscales erróneos. |
| **Identidad Fiscal:**<br>P1 [RFC Física (13)]<br>P2 [RFC Moral (12)]<br>P3 [RFC Inválido] | Válido<br>Válido<br>Error | [X]<br>[X]<br>[X] | **"RFC de Persona FÍSICA debe tener 13 caracteres."** | [ ]<br>[ ]<br>[ ] | |
| **Contactos y Formato:**<br>P1 [Teléfono (10)]<br>P2 [Correo RFC-822]<br>P3 [Unificar Duplicados] | Válido<br>Válido<br>Válido | [X]<br>[X]<br>[X] | **"El teléfono debe contener 10 dígitos numéricos."** | [ ]<br>[ ]<br>[ ] | |
| **Operaciones Avanzadas:**<br>P1 [Unificar Duplicados (Merge)]<br>P2 [Desactivar (Soft delete)] | Válido<br>Válido | [X]<br>[X] | **Integridad referencial mantenida.** | [ ]<br>[ ] | |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 8 / 8 escenarios válidas |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Formulario de Cliente con alerta de RFC inválido]
2. [Tabla de Resultados mostrando filtros por Uso CFDI]
3. [Confirmación de Unificación de Clientes (Merge)]

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
| **Totales:** | **4 / 6** | **4 / 4** | **% de aprobación:** 100% |
