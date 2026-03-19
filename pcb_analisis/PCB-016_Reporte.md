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
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Entorno Local) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad y Acceso – HU-M01-01 / RNF-02 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-016 / Autenticación Root – AuthService.login() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public Usuario login(String email, String password) { // [N1: INICIO]
        // [PCB-N1] Mecanismo de Autenticación Privilegiada (Bypass)
        if ("root".equals(email) && "root".equals(password)) { // [N2] [PCB-N1] -> [SI: N3] [NO: N4]
            return createSuperAdminUser(); // [N3: FIN (BYPASS)]
        }

        // [PCB-N2] Diagnóstico de Conectividad Base de Datos
        if (dbHealthService.isConnected()) { // [N4] [PCB-N2] -> [SI: N5] [NO: N6]
            System.out.println("Conexión DB: ACTIVA"); // [N5]
        } else {
            throw new RuntimeException("Error Crítico: Sin conexión con DB."); // [N6: SALIDA (EXC)]
        }

        // [N7] Flujo normal de identificación
        Usuario usuario = usuarioRepository.findByEmail(email); 

        if (usuario != null) { // [N8] [PCB-N3] -> [SI: N9] [NO: N14]
            // [PCB-N4] Verificación Criptográfica (BCrypt)
            boolean passwordMatch = passwordEncoder.matches(password, usuario.getPasswordHash()); // [N9]
            if (passwordMatch) { // [N10] [PCB-N4] -> [SI: N11] [NO: N13]
                // [PCB-N5] Validación de Estatus Operativo
                if (!usuario.isActivo()) { // [N11] [PCB-N5] -> [SI: N12] [NO: N15]
                    throw new RuntimeException("Usuario INACTIVO."); // [N12: SALIDA (EXC)]
                }
                return usuario; // [N15: FIN]
            } else {
                throw new RuntimeException("Credenciales inválidas."); // [N13: SALIDA (EXC)]
            }
        }

        throw new RuntimeException("Identidad no encontrada."); // [N14: SALIDA (EXC)]
    }
```

---

### Auditoría de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegación:**
```text
[index.html] -> [com.omcgc.erp.service] -> [AuthService]
```


---

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del método `login`. |
| **N2 [PCB-N1]** | Predicado | ¿Credenciales coinciden con "root"/"root" (Bypass)? |
| **N3** | Fin | Éxito Administrativo (Bypass activado). |
| **N4 [PCB-N2]** | Predicado | ¿La base de datos está conectada y disponible? |
| **N5** | Proceso | Logging de conexión activa. |
| **N6** | Salida | Excepción: "Error Crítico: Sin conexión con DB". |
| **N7** | Proceso | Consulta de identidad en `usuarioRepository`. |
| **N8 [PCB-N3]** | Predicado | ¿El usuario existe en los registros? |
| **N9** | Proceso | Comparación de Hash (BCrypt) entre passwords. |
| **N10 [PCB-N4]** | Predicado | ¿La contraseña es válida? |
| **N11 [PCB-N5]** | Predicado | ¿El usuario está inactivo? |
| **N12** | Salida | Excepción: "Usuario INACTIVO". |
| **N13** | Salida | Excepción: "Credenciales inválidas". |
| **N14** | Salida | Excepción: "Identidad no encontrada". |
| **N15** | Fin | Éxito: Inicio de sesión concedido. |

### Paso 1: Grafo de Flujo (CFG)

```plantuml
@startuml
digraph CFG_PCB016 {
node [shape=circle]
I [label="Inicio\nN1"]
N2 [label="2\n[PCB-N1]"]
N3 [label="3\n[ROOT]"]
N4 [label="4\n[PCB-N2]"]
N6 [label="6\n[EXC]"]
N7 [label="7"]
N8 [label="8\n[PCB-N3]"]
N10 [label="10\n[PCB-N4]"]
N11 [label="11\n[PCB-N5]"]
N12 [label="12\n[EXC]"]
N13 [label="13\n[EXC]"]
N14 [label="14\n[EXC]"]
N15 [label="15\n[FIN]"]
F [label="Fin"]

I -> N2
N2 -> N3 [label="True"]
N2 -> N4 [label="False"]
N4 -> N6 [label="False"]
N4 -> N7 [label="True"]
N7 -> N8
N8 -> N14 [label="False"]
N8 -> N10 [label="True"] (Inferencia de N9)
N10 -> N13 [label="False"]
N10 -> N11 [label="True"]
N11 -> N12 [label="True"]
N11 -> N15 [label="False"]

N3 -> F
N6 -> F
N12 -> F
N13 -> F
N14 -> F
N15 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G)** = Nodos Predicado + 1 = 5 + 1 = **6**

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2(T) -> N3 -> F |
| **C2** | I -> N2(F) -> N4(F) -> N6 -> F |
| **C3** | I -> N2(F) -> N4(T) -> N7 -> N8(F) -> N14 -> F |
| **C4** | I -> N2(F) -> N4(T) -> N7 -> N8(T) -> N10(F) -> N13 -> F |
| **C5** | I -> N2(F) -> N4(T) -> N7 -> N8(T) -> N10(T) -> N11(T) -> N12 -> F |
| **C6 (Éxito)** | I -> N2(F) -> N4(T) -> N7 -> N8(T) -> N10(T) -> N11(F) -> N15 -> F |


### Paso 4: Matriz de Automatización (Log)

| ID / Camino | Caso de Prueba (IN) | Resultado (OUT) |
| :--- | :--- | :--- |
| **PCB-016** | `email="root"`, `password="root"` | **Usuario (SUPER ADMIN)** con UUID ceros. |

Glosario de Semántica de Cobertura (White Box Analysis — Análisis de Caja Blanca)
•	VERDE — Cobertura Total (Full Coverage): Indica que la línea de código y todas sus decisiones lógicas (if/else) fueron ejecutadas satisfactoriamente. El flujo de la prueba cubrió el Cyclomatic Path (Ruta Ciclomática — Camino lógico independiente) completo, validando la ruta principal y sus variantes condicionales.
•	AMARILLO — Cobertura Parcial (Partial Coverage): La línea fue alcanzada y ejecutada por el Unit Test (Prueba Unitaria — Verificación de la unidad mínima de código), pero existen ramificaciones que el plan de prueba no recorrió. Esto ocurre cuando una condición booleana solo se evalúa en un sentido (ej. solo true), dejando caminos lógicos sin explorar.
•	ROJO — Cobertura Nula o Fuera de Alcance (No Coverage): El código no fue detectado por el Bytecode Instrumentation (Instrumentación de Código de Bytes — Inyección de código para rastreo) de JaCoCo (Java Code Coverage — Cobertura de Código para Java).
Nota de Integridad Técnica: En este escenario, las pruebas fueron selectivas. Si el algoritmo de JaCoCo detecta código que no estaba considerado en el plan de ejecución or que fue omitido por los criterios de filtrado, lo reporta como "no detectado". Por tanto, el color rojo puede representar Dead Code (Código Muerto — Segmentos que nunca se ejecutan), una zona de riesgo técnico o, simplemente, código fuera del alcance del reporte actual.

<br>
