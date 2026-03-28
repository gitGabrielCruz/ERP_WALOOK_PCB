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
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validación de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Cálculo de PVP | Validación de Fórmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad / Acceso – RF-05 |

| **Número y nombre de la Prueba** |
| :--- |
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public Usuario login(String email, String password) { // [N1: INICIO]
        if ("root".equals(email) && "root".equals(password)) { // [N2: PCB-N1]
            return createSuperAdminUser(); // [F: FIN]
        }

        if (!dbHealthService.isConnected()) { // [N4: PCB-N2]
            throw new RuntimeException("Error Crítico..."); // [N5: EXC]
        }

        Usuario usuario = usuarioRepository.findByEmail(email); // [N6: PCB-N3]
        if (usuario == null) {
            throw new RuntimeException("Identidad no encontrada."); // [N5: EXC]
        }

        boolean passwordMatch = passwordEncoder.matches(password, usuario.getPasswordHash()); // [N7: PCB-N4]
        if (passwordMatch) {
            if (!usuario.isActivo()) { // [N8: PCB-N5]
                throw new RuntimeException("Usuario INACTIVO."); // [N5: EXC]
            }
            return usuario; // [N9]
        } else {
            throw new RuntimeException("Credenciales inválidas."); // [N5: EXC]
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
N4 [label="4\nPCB-N2"]
N5 [label="5"]
N6 [label="6\nPCB-N3"]
N7 [label="7\nPCB-N4"]
N8 [label="8\nPCB-N5"]
N9 [label="9"]

I -> N1
N1 -> N2
N2 -> F [label="Bypass"]
N2 -> N4 [label="Falso"]
N4 -> N5 [label="Error"]
N4 -> N6 [label="Conectado"]
N6 -> N5 [label="null"]
N6 -> N7 [label="Existe"]
N7 -> N5 [label="Error Match"]
N7 -> N8 [label="Match OK"]
N8 -> N5 [label="Falso (Inactivo)"]
N8 -> N9 [label="Verdadero"]
N9 -> F
N5 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (5 internas + 1 externa) = **6**
*   **V(G) = Aristas – Nodos + 2** = (21 – 17 + 2) = **6**
*   **V(G) = Nodos Predicado + 1** = (5 + 1) = **6**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> F |
| **C2** | I -> N1 -> N2(F) -> N4 -> N5(T) -> N6 -> F |
| **C3** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(T) -> N9 -> F |
| **C4** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(F) -> N10 -> N11(T) -> N12 -> F |
| **C5** | I -> N1 -> N2(F) -> N4 -> N5(F) -> N7 -> N8(F) -> N10 -> N11(F) -> N13 -> N14(T) -> N15 -> F |
| **C6** | I -> N2(F) -> N4 -> N5(F) -> N7 -> N8(F) -> N10 -> N11(F) -> N13 -> N14(F) -> N16 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | IP No Permitida | AccessDeniedException |
| **C2** | Usuario Root Inexistente | RuntimeException |
| **C3** | Usuario No Activo | RuntimeException |
| **C4** | Password Incorrecto | RuntimeException |
| **C5** | Token Fallido | RuntimeException |
| **C6** | **Éxito (Auth Root)** | **SUCCESS (Session Active)** |
