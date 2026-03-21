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
| PCB-017 | Registro de Movimiento | Integridad Transaccional y Pólizas Automatizadas | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Persistencia de Producto | Cálculo dinámico de PVP e Identidad | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Inventario – RF-12 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-017 / Registro de Movimiento – InventarioService.registrarMovimiento() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (9 internas + 1 externa) = **10**
*   **V(G) = Aristas – Nodos + 2** = (30 – 22 + 2) = **10**
*   **V(G) = Nodos Predicado + 1** = (9 + 1) = **10**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> F (Stock Insuficiente) |
| **C2** | I -> N1 -> N2(F) -> N4(SÍ: INV-) -> N10 -> N11 -> F (Folio ya es INV-) |
| **C3** | I -> N1 -> N2(F) -> N4(NO: null) -> N5 -> N9 -> N10 -> F (Folio es null -> Auto) |
| **C4** | I -> N1 -> N2(F) -> N4(NO: empty) -> N5 -> N9 -> N10 -> F (Folio vacío -> Auto) |
| **C5** | I -> N1 -> N2(F) -> N4(NO: "S/F") -> N5 -> N9 -> N10 -> F (Folio S/F -> Auto) |
| **C6** | I -> N1 -> N2(F) -> N4(NO) -> N6(T) -> N7 -> N8 -> N9 -> F (Folio Manual -> Auto + OrigenId) |
| **C7** | I -> N1 -> N10 -> N11(T: ENTRADA) -> N12(T: p!=null) -> N13 -> F (Entrada -> Recálculo) |
| **C8** | I -> N1 -> N10 -> N11(T: ENTRADA) -> N12(F: p=null) -> N14 -> F (Entrada -> Error Interno No Bloq) |
| **C9** | I -> N1 -> N10 -> N11(F: SALIDA) -> N14 -> F (Salida Normal) |
| **C10** | I -> N1 -> N10 -> N11(F: OTRO) -> N14 -> F (Movimiento de Ajuste) |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Venta con Stock < Cantidad | RuntimeException (Insuficiente) |
| **C2** | Folio "INV-123" | Respeta Folio Original |
| **C3** | Folio es null | Genera Folio INV-[Time] |
| **C4** | Folio "" | Genera Folio INV-[Time] |
| **C5** | Folio "S/F" | Genera Folio INV-[Time] |
| **C6** | Folio "REM-001" | Genera INV-, mueve REM-001 a OrigenId |
| **C7** | ENTRADA_COMPRA | Activa Recálculo de Costo/PVP |
| **C8** | ENTRADA a ID no existente | Ignora Recálculo (Duda Cero) |
| **C9** | SALIDA_VENTA | Descuenta Stock (Éxito) |
| **C10** | **Éxito (Nominal)** | **SUCCESS (Persistido)** |
