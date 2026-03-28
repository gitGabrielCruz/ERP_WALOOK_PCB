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
| Módulo Proveedores – RF-08 |

| **Número y nombre de la Prueba** |
| :--- |
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    private void validarProveedor(Proveedor p, boolean esActualizacion) { // [N1: INICIO]
        if (p.getRazonSocial() == null || p.getRazonSocial().trim().isEmpty()) { // [N2: PCB-N1]
            throw new IllegalArgumentException("Razón Social obligatoria."); // [N3: EXC]
        }
        if (p.getRfc() == null || p.getRfc().trim().isEmpty()) { // [N4: PCB-N2]
            throw new IllegalArgumentException("RFC obligatorio."); // [N5: EXC]
        }
        if (p.getCondicionPago() == null || p.getCondicionPago().trim().isEmpty()) { // [N6: PCB-N3]
            throw new IllegalArgumentException("Condición de Pago obligatoria."); // [N7: EXC]
        }
        if (p.getNombreComercial() == null || p.getNombreComercial().trim().isEmpty()) { // [N8: PCB-N4]
            throw new IllegalArgumentException("Nombre Comercial obligatorio."); // [N9: EXC]
        }
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) { // [N10: PCB-N5]
            throw new IllegalArgumentException("Correo obligatorio."); // [N11: EXC]
        }
        if (!p.getEmail().matches(emailPattern)) { // [N12: PCB-N6]
            throw new IllegalArgumentException("Formato email inválido."); // [N13: EXC]
        }
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty()) { // [N14: PCB-N7]
            throw new IllegalArgumentException("Teléfono obligatorio."); // [N15: EXC]
        }
        if (p.getTelefono().replaceAll("\\D", "").length() != 10) { // [N16: PCB-N8]
            throw new IllegalArgumentException("Teléfono debe ser de 10 dígitos."); // [N17: EXC]
        }
        if (p.getRfc().length() < 12) { // [N18: PCB-N9]
            throw new IllegalArgumentException("RFC < 12 caracteres."); // [N19: EXC]
        }
        if (p.getRfc().length() > 13) { // [N20: PCB-N10]
            throw new IllegalArgumentException("RFC > 13 caracteres."); // [N21: EXC]
        }
        if (!p.getRfc().matches(rfcPattern)) { // [N22: PCB-N11]
            throw new IllegalArgumentException("Formato RFC inválido."); // [N23: EXC]
        }
        Proveedor existente = proveedorRepository.findByRfc(p.getRfc()); // [N24]
        if (existente != null) { // [N25: PCB-N12]
            if (esActualizacion) { // [N26: PCB-N13]
                if (!existente.getIdProveedor().equals(p.getIdProveedor())) { // [N27: PCB-N14]
                    throw new IllegalArgumentException("RFC duplicado."); // [N28: EXC]
                }
            } else {
                throw new IllegalArgumentException("RFC ya registrado."); // [N29: EXC]
            }
        }
    } // [N30] -> [F: FIN]
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
N4 [label="4\nPCB-N2"]
N5 [label="5"]
N6 [label="6\nPCB-N3"]
N7 [label="7"]
N8 [label="8\nPCB-N4"]
N9 [label="9"]
N10 [label="10\nPCB-N5"]
N11 [label="11"]
N12 [label="12\nPCB-N6"]
N13 [label="13"]
N14 [label="14\nPCB-N7"]
N15 [label="15"]
N16 [label="16\nPCB-N8"]
N17 [label="17"]
N18 [label="18\nPCB-N9"]
N19 [label="19"]
N20 [label="20\nPCB-N10"]
N21 [label="21"]
N22 [label="22\nPCB-N11"]
N23 [label="23"]
N24 [label="24"]
N25 [label="25\nPCB-N12"]
N26 [label="26\nPCB-N13"]
N27 [label="27\nPCB-N14"]
N28 [label="28"]
N29 [label="29"]
N30 [label="30"]

I -> N1
N1 -> N2
N2 -> N3 [label="Verdadero"]
N2 -> N4 [label="Falso"]
N3 -> F
N4 -> N5 [label="Verdadero"]
N4 -> N6 [label="Falso"]
N5 -> F
N6 -> N7 [label="Verdadero"]
N6 -> N8 [label="Falso"]
N7 -> F
N8 -> N9 [label="Verdadero"]
N8 -> N10 [label="Falso"]
N9 -> F
N10 -> N11 [label="Verdadero"]
N10 -> N12 [label="Falso"]
N11 -> F
(truncated 200+ characters)

*   **V(G) = Número de regiones** = (14 internas + 1 externa) = **15**
*   **V(G) = Aristas – Nodos + 2** = (44 – 31 + 2) = **15**
*   **V(G) = Nodos Predicado + 1** = (14 + 1) = **15**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2(T) -> N3 -> F |
| **C2** | I -> N2(F) -> N4(T) -> N5 -> F |
| **C3** | I -> N2(F) -> N4(F) -> N6(T) -> N7 -> F |
| **C4** | I -> N2(F) -> N4(F) -> N6(F) -> N8(T) -> N9 -> F |
| **C5** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(T) -> N11 -> F |
| **C6** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(T) -> N13 -> F |
| **C7** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(T) -> N15 -> F |
| **C8** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(T) -> N17 -> F |
| **C9** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(T) -> N19 -> F |
| **C10** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(T) -> N21 -> F |
| **C11** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(T) -> N23 -> F |
| **C12** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(T) -> N26(T) -> N27(T) -> N28 -> F |
| **C13** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(T) -> N26(F) -> N29 -> F |
| **C14** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(T) -> N26(T) -> N27(F) -> N30 -> F |
| **C15** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(F) -> N30 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Razón Social Nula | IllegalArgumentException |
| **C2** | RFC Nulo | IllegalArgumentException |
| **C3** | Condición Pago Nula | IllegalArgumentException |
| **C4** | Nombre Comercial Nulo | IllegalArgumentException |
| **C5** | Email Nulo | IllegalArgumentException |
| **C6** | Formato Email Inválido | IllegalArgumentException |
| **C7** | Teléfono Nulo | IllegalArgumentException |
| **C8** | Teléfono Corto | IllegalArgumentException |
| **C9** | RFC Longitud < 12 | IllegalArgumentException |
| **C10** | RFC Longitud > 13 | IllegalArgumentException |
| **C11** | Formato RFC Inválido | IllegalArgumentException |
| **C12** | RFC Duplicado (Otro) | IllegalArgumentException |
| **C13** | RFC Duplicado (Alta) | IllegalArgumentException |
| **C14** | Éxito (Actualización) | **SUCCESS** |
| **C15** | **Éxito (Alta Nueva)** | **SUCCESS** |
