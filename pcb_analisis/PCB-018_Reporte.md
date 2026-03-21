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
| Módulo Inventario / Financiero – RF-15 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-018 / Cálculo de PVP – InventarioService.calculatePVP() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (3 internas + 1 externa) = **4**
*   **V(G) = Aristas – Nodos + 2** = (14 – 12 + 2) = **4**
*   **V(G) = Nodos Predicado + 1** = (3 + 1) = **4**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> F |
| **C2** | I -> N1 -> N2(F) -> N4 -> N5(T) -> N6 -> F |
| **C3** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(T) -> N9 -> F |
| **C4** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(F) -> N10 -> N11 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Precio Costo <= 0 | IllegalArgumentException |
| **C2** | Margen Utilidad < 5% | IllegalArgumentException |
| **C3** | Margen Utilidad > 500% | IllegalArgumentException |
| **C4** | **Éxito (Cálculo PVP)** | **SUCCESS (Precise BigDecimal)** |
