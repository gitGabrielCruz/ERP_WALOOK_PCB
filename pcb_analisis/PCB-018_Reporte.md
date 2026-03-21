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
| :--- | :--- | :--- | :--- | :--- |
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validación de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Persistencia de Producto | Cálculo dinámico de PVP e Identidad | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Inventario / Financiero – RF-15 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-018 / Persistencia de Producto – InventarioService.saveProduct() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (5 internas + 1 externa) = **6**
*   **V(G) = Aristas – Nodos + 2** = (18 – 14 + 2) = **6**
*   **V(G) = Nodos Predicado + 1** = (5 + 1) = **6**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> N4(F) -> N7 -> F (ID Nuevo, SKU Manual) |
| **C2** | I -> N1 -> N2(F) -> N4(T) -> N5 -> N6 -> N7 -> F (ID Existe, SKU Generado) |
| **C3** | I -> N1 -> N2(F) -> N4(F) -> N7 -> N8 -> N9(T) (PVP Calculado) |
| **C4** | I -> N1 -> N2(F) -> N4(F) -> N7 -> N8 -> N9(F) (PVP Omitido - Nulos) |
| **C5** | I -> N1 -> N2(T) -> N3 -> N4(T) -> N5 -> N6 -> N7 -> F (ID Nuevo, SKU Generado) |
| **C6** | I -> N1 -> N2(F) -> N4(F) -> N7 -> N10 -> F (Éxito Nominal) |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Producto Nuevo (ID null) | UUID Generado |
| **C2** | SKU "Autogenerado" | SKU 75... Generado |
| **C3** | Costo y Utilidad válidos | PVP Calculado (BigDecimal) |
| **C4** | Costo o Utilidad nulos | PVP no se calcula |
| **C5** | Alta con SKU omitido | UUID y SKU Generados |
| **C6** | **Éxito (Registro)** | **SUCCESS (Persistido)** |
