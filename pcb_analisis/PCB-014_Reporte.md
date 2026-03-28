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
| PCB-014 | Actualización de Usuario | Saneamiento de Atributos de Identidad | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de Contraseña | Manejo de Excepción por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad / Acceso – RF-02U |

| **Número y nombre de la Prueba** |
| :--- |
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public Usuario update(Usuario usuario) { // [N1: INICIO]
        Usuario existente = usuarioRepository.findById(usuario.getId());

        if (existente == null) { // [N2: PCB-N1]
            return null; // [F: FIN]
        }

        if (usuario.getNombre() != null) { // [N3: PCB-N2]
            existente.setNombre(usuario.getNombre());
        }
        if (usuario.getCorreo() != null) { // [N4: PCB-N3]
            existente.setCorreo(usuario.getCorreo());
        }
        if (usuario.getRolId() != null) { // [N5: PCB-N4]
            existente.setRolId(usuario.getRolId());
        }
        if (usuario.getRolNombre() != null) { // [N6: PCB-N5]
            existente.setRolNombre(usuario.getRolNombre());
        }
        if (usuario.getEstatus() != null) { // [N7: PCB-N6]
            existente.setEstatus(usuario.getEstatus());
        }

        return usuarioRepository.update(existente); // [N8]
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
N4 [label="4\nPCB-N3"]
N5 [label="5\nPCB-N4"]
N6 [label="6\nPCB-N5"]
N7 [label="7\nPCB-N6"]
N8 [label="8"]

I -> N1
N1 -> N2
N2 -> F [label="Verdadero"]
N2 -> N3 [label="Falso"]
N3 -> N4 [label="Verdadero"]
N3 -> N4 [label="Falso"]
N4 -> N5 [label="Verdadero"]
N4 -> N5 [label="Falso"]
N5 -> N6 [label="Verdadero"]
N5 -> N6 [label="Falso"]
N6 -> N7 [label="Verdadero"]
N6 -> N7 [label="Falso"]
N7 -> N8 [label="Verdadero"]
N7 -> N8 [label="Falso"]
N8 -> F
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
| **C1** | I -> N1 -> N2(T) -> F (Usuario No Existe) |
| **C2** | I -> N1 -> N2(F) -> N3(T) -> N4 -> N5 -> N6 -> N7 -> N8(F) (Update Nombre) |
| **C3** | I -> N1 -> N2(F) -> N3(F) -> N4(T) -> N5 -> N6 -> N7 -> N8(F) (Update Correo) |
| **C4** | I -> N1 -> N2(F) -> N3(F) -> N4(F) -> N5(T) -> N6 -> N7 -> N8(F) (Update RolId) |
| **C5** | I -> N1 -> N2(F) -> N3(F) -> N4(F) -> N5(F) -> N6(T) -> N7 -> N8(F) (Update RolNombre) |
| **C6** | I -> N1 -> N2(F) -> N3(F) -> N4(F) -> N5(F) -> N6(F) -> N7(T) -> N8(F) (Update Estatus) |
| **C7** | I -> N1 -> N2(F) -> N3(F) -> N4(F) -> N5(F) -> N6(F) -> N7(F) -> N8(F) (Sin Cambios) |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | ID Inexistente | null (No Update) |
| **C2** | Cambio de Nombre | **SUCCESS** (Update Nombre) |
| **C3** | Cambio de Correo | **SUCCESS** (Update Correo) |
| **C4** | Cambio de Rol ID | **SUCCESS** (Update RolId) |
| **C5** | Cambio de Rol Nombre | **SUCCESS** (Update RolNombre) |
| **C6** | Cambio de Estatus | **SUCCESS** (Update Estatus) |
| **C7** | **Éxito (Nominal)** | **SUCCESS** (No Fields Changed) |
