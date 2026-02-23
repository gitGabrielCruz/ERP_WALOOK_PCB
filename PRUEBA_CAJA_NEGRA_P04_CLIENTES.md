Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **23 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 04 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M06-01: Registrar cliente / HU-M06-06: Datos Fiscales (RFC) / HU-M06-08: Unificación de Clientes (Merge) |
| **Nombre de la prueba:** | Gestión de Catálogo de Clientes y Validación Fiscal |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida Registro Comercial |
| **Descripción:** | Validar la creación de clientes, cumplimiento de formatos RFC (SAT), validación de contactos y gobernanza de acceso. |

| Valores de entrada | Valores de salida | Resultado: | Gestión de Clientes Operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema valida RFC y duplicados. | **Incorrecto** | El sistema deniega o alerta error. |
| **Atributo: Identidad Fiscal:**<br>P1 [RFC Física (13 car.)]<br>P2 [RFC Moral (12 car.)]<br>P3 [RFC Formato Inválido] | Válido<br>Válido<br>Bloqueo | [X]<br>[X]<br>[X] | **"Datos fiscales guardados correctamente."** (HU-M06-06) | [ ]<br>[ ]<br>[ ] | **"RFC de Persona MORAL debe tener 12 caracteres."**<br>**"El RFC ingresado no cumple con el formato oficial."** |
| **Atributo: Contactos e Interfaz:**<br>P1 [Teléfono (10 dígitos)]<br>P2 [Email (rfc-822)]<br>P3 [Teléfono incompleto] | Formato OK<br>Formato OK<br>Rechazo | [X]<br>[X]<br>[X] | **Validación exitosa de contacto.** (HU-M06-01 / RNF-02) | [ ]<br>[ ]<br>[ ] | **"El teléfono debe contener 10 dígitos numéricos."**<br>**"Verifique el formato del correo electrónico."** |
| **Atributo: Ciclo de Vida:**<br>P1 [Alta de Cliente Exitoso]<br>P2 [Unificación de Duplicados]<br>P3 [Baja Lógica (Desactivar)] | Alta OK<br>Merge OK<br>Estatus OK | [X]<br>[X]<br>[X] | **Integridad referencial mantenida.** (HU-M06-01 / HU-M06-08) | [ ]<br>[ ]<br>[ ] | **"No se pudo guardar la información del cliente."**<br>**"Acceso Denegado: No tiene permiso para desactivar..."** |
| **Atributo: Gobernanza de Acceso:**<br>P1 [Acceso Total (Admin)]<br>P2 [Modo Solo Lectura (Vendedor)]<br>P3 [Intento saltar permisos ACL] | Todo Activo<br>Lock UI<br>Redirect | [X]<br>[X]<br>[X] | **Visualización de Bitácora y Edición habilitada.** (RNF-04) | [ ]<br>[ ]<br>[ ] | **"No tiene permisos para ver el módulo de CLIENTES."**<br>**Botones de edición/nuevo ocultos.** |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda operativo. |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Formulario de Cliente con alerta de RFC Moral inválido]
2. [Captura: Tabla de Clientes con indicadores de estatus (Activo/Inactivo)]
3. [Captura: Modal de Auditoría Forense para el módulo de Clientes]

---

# Matriz de resultados

**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.  
**Fecha de aplicación:** 23 de febrero de 2026  
**Encargado de realizar la prueba:** Gabriel Amilcar Cruz Canto

| Prueba No. | Ejecutada | Cumple con los escenarios | Observaciones |
| :--- | :--- | :--- | :--- |
| 01 | SI | SI | 100% Cobertura P1-Pn Login |
| 02 | SI | SI | 100% Cobertura P1-Pn Menú |
| 03 | SI | SI | 100% Cobertura P1-Pn Usuarios |
| 04 | SI | SI | 100% Cobertura P1-Pn Clientes (Trazabilidad Total) |
| 05 | SI | SI | Pendiente Fidelización |
| 06 | SI | SI | Pendiente Fidelización |
| **Totales:** | **6** | **6** | **% de aprobación:** 100% |
