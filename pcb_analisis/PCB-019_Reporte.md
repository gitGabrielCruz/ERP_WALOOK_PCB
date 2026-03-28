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
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public void registrarEvento(String idUsuario, String idPatron, String ip, String paramX, String paramS) { // [N1: INICIO]
        try {
            String logCompleto = auditPatternService.buildLog(idPatron, paramX, paramS);
            String[] parts = logCompleto.split("\\|");
            int n = parts.length;

            StringBuilder sbAnalisis = new StringBuilder();
            for (int i = 3; i < n - 1; i++) { // [N2: PCB-N1]
                sbAnalisis.append(parts[i].trim()); // [N4]
                if (i < n - 2) { // [N3: PCB-N2]
                    sbAnalisis.append(" | "); // [N5]
                }
            }
            String analisis = sbAnalisis.toString();
            if (analisis.isEmpty()) { // [N6: PCB-N3]
                analisis = "Sin detalle técnico adicional"; // [N7]
            }

            Bitacora b = new Bitacora();
            b.setIdUsuario(idUsuario);
            b.setIpOrigen(encrypt(ip != null ? ip : "0.0.0.0")); // [N8: PCB-N4] -> [N9]

            bitacoraRepository.save(b); // [N10]
        } catch (Exception e) {
            // Error handling
        }
    } // [F: FIN]
```

### Paso 1: Grafo de Control de Flujo (CFG)

```dot
@startuml
digraph G {
rankdir=TB;
node [shape=circle, fixedsize=true, width=0.5];
I [label="Inicio", shape=ellipse];
F [label="Fin", shape=ellipse];
N1 [label="1"]
N2 [label="2\nPCB-N1"]
N3 [label="3\nPCB-N2"]
N4 [label="4"]
N5 [label="5"]
N6 [label="6\nPCB-N3"]
N7 [label="7"]
N8 [label="8\nPCB-N4"]
N9 [label="9"]
N10 [label="10"]

I -> N1
N1 -> N2
N2 -> N3 [label="Verdadero"]
N2 -> N6 [label="Falso"]
N3 -> N4 [label="Verdadero"]
N3 -> N5 [label="Falso"]
N4 -> N5
N5 -> N2
N6 -> N7 [label="Verdadero"]
N6 -> N8 [label="Falso"]
N7 -> N8
N8 -> N9 [label="Verdadero"]
N8 -> N10 [label="Falso"]
N9 -> N10
N10 -> F
}
@enduml
```

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
