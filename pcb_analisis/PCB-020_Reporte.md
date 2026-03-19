# TEST PRUEBAS DE CAJA BLANCA - AUTOMATIZADA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel Amílcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestión de ópticas OMCGC |

<br>

| **PLAN DE PRUEBAS DE CAJA BLANCA: BACKEND (AUTO)** | | | | |
| :--- | :--- | :--- | :--- | :--- |
| **Número** | **Nombre de la Prueba Backend** | **Descripción** | **Fecha** | **Herramienta** |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Matriz Maestra) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Auditoría y Privacidad – RNF-01 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-020 / Carga de Diccionario – AuditPatternService.loadDictionary() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    @SuppressWarnings("unchecked")
    private void loadDictionary() throws Exception { // [N1: INICIO]
        // [N2] Lectura de Binario Seguro
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(FILE_PATH)); // [N2]

        // [PCB-N1] Protocolo de Descifrado AES-256
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
        cipher.init(Cipher.DECRYPT_MODE, generateKey());
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes); // [N3] [PCB-N1]

        // [N4] Deserialización de Objetos en Memoria
        ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        List<LogPattern> list = (List<LogPattern>) ois.readObject(); // [N4]

        // [PCB-N2] Población de Memoria (Map)
        patterns.clear();
        for (LogPattern lp : list) { // [N5] [PCB-N2] -> [LOOP]
            patterns.put(lp.getId(), lp); // [N6]
        }
    } // [N7: FIN]
```

---

### Auditoría de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegación:**
```text
[index.html] -> [com.omcgc.erp.service] -> [AuditPatternService]
```

---

---

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del método `loadDictionary`. |
| **N2** | Proceso | Recuperación de `audit_dictionary.dat` desde el sistema de archivos. |
| **N3 [PCB-N1]** | Nodo Crítico | Ejecución de descifrado AES-256 (Punto de control de seguridad). |
| **N4** | Proceso | Deserialización del binario a lista de objetos `LogPattern`. |
| **N5 [LOOP]** | Predicado | Iteración sobre la lista para poblar el Map de patrones. |
| **N6** | Proceso | Insercion del patrón en memoria (`HashMap`). |
| **N7** | Fin | Término de la inicialización segura del motor de auditoría. |

### Paso 1: Grafo de Flujo (CFG)

```plantuml
@startuml
digraph CFG_PCB020 {
node [shape=circle]
I [label="Inicio\nN1"]
N2 [label="N2\n[FILE]"]
N3 [label="N3\n[DECRYPT]"]
N4 [label="N4\n[OBJECT]"]
N5 [label="N5\n[LOOP]"]
N6 [label="N6\n[PUT]"]
F [label="Fin\nN7"]

I -> N2
N2 -> N3
N3 -> N4
N4 -> N5
N5 -> N6 [label="Next"]
N6 -> N5
N5 -> F [label="End"]
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G)** = Nodos Predicado + 1 = 1 + 1 = **2** (Bucle de población).

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Success)** | I -> N2 -> N3 -> N4 -> N5 -> F |
| **C2 (Iteración)**| I -> N2 -> N3 -> N4 -> N5 -> N6 -> N5 -> F |


### Paso 4: Matriz de Automatización (Log)

| ID / Camino | Caso de Prueba (IN) | Resultado (OUT) |
| :--- | :--- | :--- |
| **PCB-020** | `audit_dictionary.dat` (Binario Cifrado) | **Map patterns** poblado con 28+ patrones de auditoría. |

Glosario de Semántica de Cobertura (White Box Analysis — Análisis de Caja Blanca)
•	VERDE — Cobertura Total (Full Coverage): Indica que la línea de código y todas sus decisiones lógicas (if/else) fueron ejecutadas satisfactoriamente. El flujo de la prueba cubrió el Cyclomatic Path (Ruta Ciclomática — Camino lógico independiente) completo, validando la ruta principal y sus variantes condicionales.
•	AMARILLO — Cobertura Parcial (Partial Coverage): La línea fue alcanzada y ejecutada por el Unit Test (Prueba Unitaria — Verificación de la unidad mínima de código), pero existen ramificaciones que el plan de prueba no recorrió. Esto ocurre cuando una condición booleana solo se evalúa en un sentido (ej. solo true), dejando caminos lógicos sin explorar.
•	ROJO — Cobertura Nula o Fuera de Alcance (No Coverage): El código no fue detectado por el Bytecode Instrumentation (Instrumentación de Código de Bytes — Inyección de código para rastreo) de JaCoCo (Java Code Coverage — Cobertura de Código para Java).
Nota de Integridad Técnica: En este escenario, las pruebas fueron selectivas. Si el algoritmo de JaCoCo detecta código que no estaba considerado en el plan de ejecución or que fue omitido por los criterios de filtrado, lo reporta como "no detectado". Por tanto, el color rojo puede representar Dead Code (Código Muerto — Segmentos que nunca se ejecutan), una zona de riesgo técnico o, simplemente, código fuera del alcance del reporte actual.

<br>
