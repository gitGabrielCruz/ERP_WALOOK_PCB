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
| PCB-011 | Registro de Proveedor | Auditoría Estructural de Validación Forense | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-012 | Actualización de Proveedor | Validación de Excepción por RFC Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-013 | Registro de Usuario | Validación de Excepción por Correo Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-014 | Baja de Usuario | Validación de Desactivación Lógica (inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de Contraseña | Manejo de Excepción por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad / Acceso – RF-04 |

| **Número y nombre de la Prueba** |
| :--- |
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public String resetPassword(String id) { // [N1: INICIO]
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) { // [N2: PCB-N1]
            return null; // [N3: EXC]
        }

        String nuevaPassword = generarPasswordTemporal(); // [N4]
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.update(usuario); // [N5]

        try {
            // [N6: PCB-N2] Attempt Email
            emailService.sendEmail(usuario.getCorreo(), "Reset", "Body"); 
        } catch (Exception e) {
            // [N7: EXC LOG] Handled
        }
        return nuevaPassword; // [N8]
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
N3 [label="3"]
N4 [label="4"]
N5 [label="5"]
N6 [label="6\nPCB-N2"]
N7 [label="7"]
N8 [label="8"]

I -> N1
N1 -> N2
N2 -> N3 [label="Verdadero (null)"]
N2 -> N4 [label="Falso"]
N3 -> F
N4 -> N5
N5 -> N6
N6 -> N7 [label="Error Correo"]
N6 -> N8 [label="Éxito"]
N7 -> N8
N8 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (2 internas + 1 externa) = **3**
*   **V(G) = Aristas – Nodos + 2** = (14 – 13 + 2) = **3**
*   **V(G) = Nodos Predicado + 1** = (2 + 1) = **3**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> F |
| **C2** | I -> N1 -> N2(F) -> N4 -> N5 -> N6(T) -> N7 -> F |
| **C3** | I -> N1 -> N2(F) -> N4 -> N5 -> N6(F) -> N8 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Usuario No Encontrado | RuntimeException |
| **C2** | Token Inválido/Expirado | RuntimeException |
| **C3** | **Éxito (Reset)** | **SUCCESS (Password Updated)** |
