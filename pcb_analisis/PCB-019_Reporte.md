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
| PCB-019 | Robustez de Auditoría | Normalización de IP y Saneamiento de Detalle | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Auditoría / Cripto – RF-20 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-019 / Robustez de Auditoría – BitacoraService.registrarEvento() |

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (4 internas + 1 externa) = **5**
*   **V(G) = Aristas – Nodos + 2** = (18 – 15 + 2) = **5**
*   **V(G) = Nodos Predicado + 1** = (4 + 1) = **5**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(Lazo For) -> N3(T) -> N4 -> F (Concatenación mútiple de pipes) |
| **C2** | I -> N1 -> N2(Lazo For) -> N3(F) -> N5 -> F (Cierre de fragmentos de log) |
| **C3** | I -> N1 -> N6(T: vacio) -> N7 -> F (Análisis Vacío -> Set Mensaje Default) |
| **C4** | I -> N1 -> N8(T: null) -> N9 -> F (IP Origen Nula -> Set 0.0.0.0) |
| **C5** | I -> N1 -> N6(F) -> N8(F) -> N10 -> F (Éxito Nominal: Registro Cifrado) |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Log con > 4 fragmentos | Formateo correcto de pipes intermedios |
| **C2** | Log con 3 fragmentos | Procesamiento de cabecera y código |
| **C3** | Análisis Técnico vacío | **SUCCESS** (Set "Sin detalle técnico adicional") |
| **C4** | IP Origen = null | **SUCCESS** (Set 0.0.0.0) |
| **C5** | **Éxito (Auditoría)** | **SUCCESS (Cifrado AES-256 Completo)** |
