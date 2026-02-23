Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **22 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 06 |
| :--- | :--- |
| **Historia de Usuario:** | HU-M01-01: Registrar producto / HU-M01-02: Consultar producto / HU-M01-05: Movimientos (Kardex) / HU-M01-07: Alertas Stock / HU-M01-09: Datos Fiscales |
| **Nombre de la prueba:** | Catálogo Maestro y Control de Existencias (Kardex) |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida gestión de inventarios |
| **Descripción:** | Validar la creación de productos con SKU autogenerado, el cálculo dinámico de existencia operativa (Kardex) y la activación de alertas por stock mínimo. |

| Valores de entrada | Valores de salida | Resultado: | Gestión de inventario operativa. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema calcula el saldo y activa semáforos. | **Incorrecto** | El sistema permite existencias negativas o ignora mínimos. |
| **Catálogo de Productos:**<br>P1 [Registro SKU: Prefijo 75]<br>P2 [Validación SAT: ClaveProdServ]<br>P3 [Cálculo PVP: Costo + % Utilidad] | Válido<br>Válido<br>Válido | [X]<br>[X]<br>[X] | **"La información se ha persistido correctamente."** | [ ]<br>[ ]<br>[ ] | |
| **Control de Movimientos:**<br>P1 [Entrada por Compra: Suma saldo]<br>P2 [Salida (Anti-negativos): Bloqueo]<br>P3 [Ajuste de Conteo Físico: Diferencia] | Válido<br>Error<br>Válido | [X]<br>[X]<br>[X] | **"Stock insuficiente. Máximo disponible: X"** | [ ]<br>[ ]<br>[ ] | |
| **Alertas y Semáforos:**<br>P1 [Stock < Mínimo: Punto Rojo]<br>P2 [Stock > Mínimo: Punto Verde] | Punto Rojo<br>Punto Verde | [X]<br>[X] | **Semáforos reaccionan en tiempo real al Kardex.** | [ ]<br>[ ] | |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 8 / 8 escenarios válidas |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Ficha técnica de producto con cálculo dinámico de PVP]
2. [Tabla Kardex mostrando historial de entradas y salidas]
3. [Vista de Catálogo con semáforos de stock (Rojo/Verde)]

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
| 06 | SI | SI | 100% Cobertura Inventarios/Kardex |
| **Totales:** | **6 / 6** | **6 / 6** | **% de aprobación:** 100% |
