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
| PCB-019 | Robustez de Auditoría | Normalización de IP y Saneamiento de Detalle | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Integridad de Matriz Maestra y Descifrado AES | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad / Cripto – RF-25 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-020 / Carga de Diccionario – AuditPatternService.loadDictionary() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (2 internas + 1 externa) = **3**
*   **V(G) = Aristas – Nodos + 2** = (12 – 11 + 2) = **3**
*   **V(G) = Nodos Predicado + 1** = (2 + 1) = **3**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1(Read) -> N2(Exc: FileNotFound) -> F |
| **C2** | I -> N1 -> N3(Decrypt) -> N4(Exc: IllegalBlockSize) -> F |
| **C3** | I -> N1 -> N3 -> N5(Deserialize) -> N6(Loop: Match) -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Archivo `.dat` inexistente | **Fallo Controlado** (Exception IO) |
| **C2** | Llave Maestra incorrecta / Data corrupta | **Fallo Controlado** (Exception Crypto) |
| **C3** | **Éxito (Lectura y Descifrado)** | **SUCCESS (Dictionary Ready)** |
