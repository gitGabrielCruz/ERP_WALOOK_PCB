Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **23 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 03 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M05-01: Registro de Personal / HU-M05-03: Matriz de Permisos (RBAC) / HU-M03-10: Auditoría Forense (Bitácora) |
| **Nombre de la prueba:** | Gestión de Usuarios y Gobernanza de Permisos |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida Administración de Identidades |
| **Descripción:** | Validar el ciclo de vida del usuario, la consistencia de la matriz de permisos y el rastro de auditoría generado por el sistema. |

| Valores de entrada | Valores de salida | Resultado: | Seguridad y Auditoría Operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema aplica reglas de negocio. | **Incorrecto** | El sistema deniega o bloquea acción. |
| **Atributo: Gestión de Cuentas:**<br>P1 [Alta: Nuevo Usuario OK]<br>P2 [Email ya registrado]<br>P3 [Campos obligatorios vacíos] | Alta Exitosa<br>Bloqueo<br>Alerta | [X]<br>[X]<br>[X] | **"Usuario creado exitosamente con clave temporal."** (HU-M05-01) | [ ]<br>[ ]<br>[ ] | **"Identidad ya registrada en el registro de usuarios."**<br>**"Por favor complete todos los campos obligatorios."** |
| **Atributo: Matriz de Permisos:**<br>P1 [Asignación de Rol Admin/Vendedor]<br>P2 [Edición Granular de ACLs]<br>P3 [Intento editar sin permisos] | Carga OK<br>Guardado OK<br>Lock UI | [X]<br>[X]<br>[X] | **Carga de matriz default por rol.** (HU-M05-03 / RNF-04) | [ ]<br>[ ]<br>[ ] | **"Modo de solo lectura: No tiene permisos..."**<br>**"Acceso Denegado. No tiene permisos para ver..."** |
| **Atributo: Estatus y Gobernanza:**<br>P1 [Cambio Estatus: Activo -> Inactivo]<br>P2 [Baja sin permiso de eliminar]<br>P3 [Intento baja último Admin] | Baja Lógica<br>Bloqueo<br>Restricción | [X]<br>[X]<br>[X] | **Usuario marcado como INACTIVO en el sistema.** (RNF-04) | [ ]<br>[ ]<br>[ ] | **"Acceso Denegado: No tiene permiso para desactivar..."**<br>**"Operación no permitida: Debe existir al menos un Admin."** |
| **Atributo: Auditoría Forense:**<br>P1 [Consulta Logs del Día]<br>P2 [Filtro por ID de Usuario]<br>P3 [Búsqueda por Texto Cifrado] | Lista Logs<br>Filtrado OK<br>Match OK | [X]<br>[X]<br>[X] | **Log generado en Bitácora con rastro de operación.** (HU-M03-10 / RNF-08) | [ ]<br>[ ]<br>[ ] | **"No se encontraron registros de auditoría..."** |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 12 / 12 combinaciones validadas. Trazabilidad vinculada a HU/RNF en cada celda operativo. |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Formulario de Alta con Contraseña Temporal Generada]
2. [Captura: Matriz de Permisos (ACL) configurada para Rol Operativo]
3. [Captura: Módulo de Bitácora filtrado por acciones del Administrador]

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
