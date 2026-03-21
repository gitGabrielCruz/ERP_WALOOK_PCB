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
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad / Cripto – RF-25 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-020 / Carga de Diccionario – AuditPatternService.buildLog() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (3 internas + 1 externa) = **4**
*   **V(G) = Aristas – Nodos + 2** = (14 – 12 + 2) = **4**
*   **V(G) = Nodos Predicado + 1** = (3 + 1) = **4**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> F (Patrón Inexistente) |
| **C2** | I -> N1 -> N2(F) -> N3(T) -> N4 -> F (Placeholder {X} Saneado) |
| **C3** | I -> N1 -> N2(F) -> N3(F) -> N5(T) -> N6 -> F (Placeholder {S} Saneado) |
| **C4** | I -> N1 -> N2(F) -> N3(F) -> N5(F) -> N7 -> F (Éxito Nominal) |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | ID Patrón="ERROR-99" | return "Unknown Event" |
| **C2** | ParamX != null | Inyección de Identidad/Módulo |
| **C3** | ParamS != null | Inyección de Detalle/Efecto |
| **C4** | **Éxito (Log)** | **SUCCESS (Format Correct)** |
