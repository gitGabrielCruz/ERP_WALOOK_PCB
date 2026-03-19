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
| PCB-014 | Baja de Usuario | Validación de Desactivación Lógica (status=inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad y Acceso – HU-M01-03 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-014 / Baja de Usuario – UsuarioService.delete() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public boolean delete(String id) { // [N1: INICIO]
        // [PCB-N1] Búsqueda de Identidad
        Usuario usuario = usuarioRepository.findById(id); // [N2: PROCESO]

        if (usuario == null) { // [N3] [PCB-N1] -> [SI: N4] [NO: N5] : ¿Usuario inexistente?
            return false; // [N4: FIN]
        }

        // [PCB-N2] Protocolo de Desactivación Lógica
        usuario.setEstatus("inactivo"); // [N5: PROCESO]
        usuarioRepository.update(usuario);

        return true; // [N6: FIN]
    }
```

---

### Auditoría de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegación:**
```text
[index.html] -> [com.omcgc.erp.service] -> [UsuarioService]
```

**Glosario de Colores:**
*   **VERDE**: Éxito (Línea ejecutada).
*   **AMARILLO**: Parcial (Ramas no cubiertas).
*   **ROJO**: Pendiente (No ejecutado).

---

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del método `delete`. |
| **N2** | Proceso | Localización de usuario por UUID en el Repositorio. |
| **N3 [PCB-N1]** | Predicado | ¿El usuario existe (`usuario != null`)? |
| **N4** | Fin | Retorno `false` (Usuario no existe). |
| **N5 [PCB-N2]** | Proceso | Protocolo de desactivación: status="inactivo" y persistencia. |
| **N6** | Fin | Retorno exitoso `true`. |


### Paso 1: Grafo de Flujo (CFG)

```plantuml
@startuml
digraph CFG_PCB014 {
node [shape=circle]
N1 [label="N1"]
N2 [label="N2"]
N3 [label="N3\n[PCB-N1]"]
N4 [label="N4"]
N5 [label="N5"]
N6 [label="N6"]

N1 -> N2
N2 -> N3
N3 -> N4 [label="True"]
N3 -> N5 [label="False"]
N5 -> N6
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G)**: 2 (Un solo nodo predicado de existencia).

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Éxito)** | N1 -> N2 -> N3(F) -> N5 -> N6 |
| **C2 (Excepción)** | N1 -> N2 -> N3(T) -> N4 |


### Paso 4: Matriz de Automatización (Log)

| ID / Camino | Caso de Prueba (IN) | Resultado (OUT) |
| :--- | :--- | :--- |
| **PCB-014** | `id="UUID-EXISTENTE"`, `status="activo"` | `true` (status="inactivo" en BD) |

---
*Firma: Agente DevIAn - Auditoría Estructural Certificada*
