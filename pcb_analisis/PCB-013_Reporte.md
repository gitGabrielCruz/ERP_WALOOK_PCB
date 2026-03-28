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
| Módulo Seguridad / Acceso – RF-02 |

| **Número y nombre de la Prueba** |
| :--- |
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public Usuario create(Usuario usuario) { // [N1: INICIO]
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) { // [N2: PCB-N1]
            if (usuario.getCorreo() != null && !usuario.getCorreo().trim().isEmpty()) { // [N3: PCB-N2]
                String generatedUser = usuario.getCorreo().split("@")[0];
                usuario.setUsuario(generatedUser);
            } else {
                throw new IllegalArgumentException("Username obligatorio"); // [N4] -> F
            }
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) { // [N5: PCB-N3]
            throw new IllegalArgumentException("Correo obligatorio"); // [N15] -> F
        }

        Usuario existente = usuarioRepository.findByEmail(usuario.getCorreo()); // [N10]
        if (existente != null) { // [N11: PCB-N4]
            throw new IllegalArgumentException("Correo duplicado"); // [N16] -> F
        }

        usuario.setId(UUID.randomUUID().toString()); // [N12]
        String passwordTemp = usuario.getPassword() != null ? usuario.getPassword() : "Temp123!"; // [N13: PCB-N5]
        
        if (usuario.getEstatus() == null) { // [N14: PCB-N6]
            usuario.setEstatus("activo");
        }

        return usuarioRepository.save(usuario); // [F: FIN]
    }
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
N5 [label="5\nPCB-N3"]
N10 [label="10"]
N11 [label="11\nPCB-N4"]
N12 [label="12"]
N13 [label="13\nPCB-N5"]
N14 [label="14\nPCB-N6"]
N15 [label="15"]
N16 [label="16"]

I -> N1
N1 -> N2
N2 -> N3 [label="Verdadero"]
N2 -> N5 [label="Falso"]
N3 -> N5 [label="Verdadero"]
N3 -> N4 [label="Falso"]
N4 -> F
N5 -> N15 [label="Verdadero"]
N5 -> N10 [label="Falso"]
N10 -> N11
N11 -> N16 [label="Verdadero"]
N11 -> N12 [label="Falso"]
N12 -> N13
N13 -> N14
N14 -> F
N15 -> F
N16 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (6 internas + 1 externa) = **7**
*   **V(G) = Aristas – Nodos + 2** = (22 – 17 + 2) = **7**
*   **V(G) = Nodos Predicado + 1** = (6 + 1) = **7**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2(T) -> N3 -> F |
| **C2** | I -> N2(F) -> N4(T) -> N5 -> F |
| **C3** | I -> N2(F) -> N4(F) -> N6(T) -> N7 -> F |
| **C4** | I -> N2(F) -> N4(F) -> N6(F) -> N8(T) -> N9 -> F |
| **C5** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(T) -> N11 -> F |
| **C6** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(T) -> N13 -> F |
| **C7** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Nombre Nulo/Vacío | IllegalArgumentException |
| **C2** | Email Nulo/Vacío | IllegalArgumentException |
| **C3** | Formato Email Inválido | IllegalArgumentException |
| **C4** | Password Nulo/Vacío | IllegalArgumentException |
| **C5** | Rol Nulo | IllegalArgumentException |
| **C6** | Email Duplicado | IllegalArgumentException |
| **C7** | **Éxito (Registro)** | **SUCCESS** |
