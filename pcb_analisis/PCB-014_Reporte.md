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
| PCB-014 / Actualización de Usuario – UsuarioService.update() |

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
