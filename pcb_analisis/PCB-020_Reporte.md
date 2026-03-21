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
| Módulo Seguridad / Cripto – RF-25 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-020 / Carga de Diccionario – ConfigService.loadDict() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (2 internas + 1 externa) = **3**
*   **V(G) = Aristas – Nodos + 2** = (8 – 7 + 2) = **3**
*   **V(G) = Nodos Predicado + 1** = (2 + 1) = **3**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> F |
| **C2** | I -> N1 -> N2(F) -> N4 -> N5(T) -> N6 -> F |
| **C3** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Archivo No Encontrado | RuntimeException |
| **C2** | Error de Descifrado AES | SecurityException |
| **C3** | **Éxito (Carga)** | **SUCCESS (Dict Loaded)** |
