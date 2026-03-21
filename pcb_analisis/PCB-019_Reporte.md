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
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Auditoría – RF-20 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-019 / Robustez de Auditoría – BitacoraService.registrar() |

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
| **C1** | Payload Nulo | IllegalArgumentException |
| **C2** | Evento Nulo | IllegalArgumentException |
| **C3** | IP Nula/Vacía | **SUCCESS (Set 0.0.0.0)** |
| **C4** | Usuario Nulo | **SUCCESS (Set SYSTEM)** |
| **C5** | **Éxito (Registro)** | **SUCCESS (Audit Logged)** |
