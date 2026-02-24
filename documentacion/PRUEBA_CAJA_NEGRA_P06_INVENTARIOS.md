Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **23 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 06 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M01-01: Registro de Producto / HU-M01-05: Control de Kardex / HU-M01-07: Alertas Stock / HU-M01-09: Datos Fiscales SAT |
| **Nombre de la prueba:** | Catálogo Maestro y Control de Existencias (Kardex) |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida gestión de inventarios |
| **Descripción:** | Validar el alta de productos con SKU autogenerado, la integridad fiscal SAT, el cálculo automático de PVP y el motor dinámico de existencias (Kardex). |

| Valores de entrada | Valores de salida | Resultado: | Gestión de Inventario Operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema calcula el saldo y activa semáforos. | **Incorrecto** | El sistema deniega o alerta error. |
| **Atributo: Identidad y SAT:**<br>P1 [SKU Autogenerado (Pref. 75)]<br>P2 [Datos SAT Óptica (Clave/Uni)]<br>P3 [Campos Fiscales Vacíos] | SKU OK<br>Fiscal OK<br>Alerta UI | [X]<br>[X]<br>[X] | **"La información se ha persistido correctamente."** (HU-M01-01 / HU-M01-09) | [ ]<br>[ ]<br>[ ] | **"Los campos marcados con asterisco (*) son obligatorios..."**<br>**"Fallo en Persistencia: SKU Duplicado."** |
| **Atributo: Finanzas y PVP:**<br>P1 [Cálculo PVP: Costo + Utilidad]<br>P2 [Costo Negativo: Bloqueo]<br>P3 [Iva Aplicable 16%] | PVP OK<br>Rechazo<br>Carga OK | [X]<br>[X]<br>[X] | **Precio de venta calculado y grabado.** (RNF-02) | [ ]<br>[ ]<br>[ ] | **"El costo unitario no puede ser negativo."**<br>**"Inconsistencia de utilidad: No puede ser nula."** |
| **Atributo: Gobernanza Kardex:**<br>P1 [Entrada/Salida: Alta Póliza]<br>P2 [Existencia Negativa: Bloqueo]<br>P3 [Eliminar Póliza: Reversión] | Saldo OK<br>Error<br>Rollback OK | [X]<br>[X]<br>[X] | **"El Kardex ha sido actualizado satisfactoriamente."** (HU-M01-05) | [ ]<br>[ ]<br>[ ] | **"Stock insuficiente. Máximo disponible: X"**<br>**"No se pudo conectar con el servicio de inventarios."** |
| **Atributo: Alertas RBAC:**<br>P1 [Stock < Mínimo: Punto Rojo]<br>P2 [Vendedor: Solo Lectura]<br>P3 [Admin: Auditoría Forense] | Semáforo OK<br>Lock UI<br>Lista Logs | [X]<br>[X]<br>[X] | **"Su perfil no cuenta con permisos para crear productos."** (HU-M01-07 / RNF-04) | [ ]<br>[ ]<br>[ ] | **"No tiene permisos para ver el módulo de Inventarios."**<br>**"Acceso Denegado: No cuenta con permisos para inactivar."** |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 12 / 12 combinaciones validadas. Trazabilidad total HU/RNF aplicada a lógica de Kardex y Semáforos. |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Ficha de producto con Datos SAT Óptica e indicador de SKU Prefijo 75]
2. [Captura: Tabla Kardex con historial de Pólizas y cálculo dinámico de Nuevo Saldo]
3. [Captura: Módulo de Bitácora Forense mostrando trazabilidad de ajustes manuales]

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
