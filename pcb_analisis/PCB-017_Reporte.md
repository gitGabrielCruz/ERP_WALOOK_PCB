# TEST PRUEBAS DE CAJA BLANCA - AUTOMATIZADA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel Amílcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestión de ópticas OMCGC |

<br>

| **PLAN DE PRUEBAS DE CAJA BLANCA: BACKEND (MIG-MASTER)** | | | | |
| :--- | :--- | :--- | :--- | :--- |
| **Número** | **Nombre de la Prueba Backend** | **Descripción** | **Fecha** | **Herramienta / Responsable** |
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validación de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Cálculo de PVP | Validación de Fórmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Inventario – RF-12 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-017 / Registro de Movimiento – InventarioService.createMovimiento() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (4 internas + 1 externa) = **5**
*   **V(G) = Aristas – Nodos + 2** = (18 – 15 + 2) = **5**
*   **V(G) = Nodos Predicado + 1** = (4 + 1) = **5**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> F |
| **C2** | I -> N1 -> N2(F) -> N4 -> N5(T) -> N6 -> F |
| **C3** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(T) -> N9 -> F |
| **C4** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(F) -> N10 -> N11(T) -> N12 -> F |
| **C5** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(F) -> N10 -> N11(F) -> N13 -> N14 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Producto Inexistente | RuntimeException |
| **C2** | Sucursal Inexistente | RuntimeException |
| **C3** | Stock Insuficiente (Venta) | RuntimeException |
| **C4** | Tipo de Movimiento Inválido | IllegalArgumentException |
| **C5** | **Éxito (Movimiento)** | **SUCCESS (Stock Updated)** |
