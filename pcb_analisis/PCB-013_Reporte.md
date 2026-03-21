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
| PCB-013 / Registro de Usuario – UsuarioService.create() |

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
