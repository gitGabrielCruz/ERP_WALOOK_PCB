# TEST PRUEBAS DE CAJA BLANCA - AUTOMATIZADA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel AmÃ­lcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC |

<br>

| **PLAN DE PRUEBAS DE CAJA BLANCA: BACKEND (MIG-MASTER)** | | | | |
| :--- | :--- | :--- | :--- | :--- |
| **NÃºmero** | **Nombre de la Prueba Backend** | **DescripciÃ³n** | **Fecha** | **Herramienta / Responsable** |
| PCB-001 | AutenticaciÃ³n de usuario | Protocolo de Acceso y ValidaciÃ³n de Infraestructura | 09/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-002 | Manejo de Credenciales InvÃ¡lidas | InterrupciÃ³n de Seguridad por Fallo de ContraseÃ±a | 09/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-003 | Registro de Producto | ValidaciÃ³n de Integridad de Campos Obligatorios | 10/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-004 | SKU Autogenerado | GarantÃ­a de Unicidad de IdentificaciÃ³n Comercial | 10/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-005 | Rango de Fechas (Ventas) | Filtrado de Reporte Operativo de Transacciones | 11/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-006 | Filtro de Sucursal | SegregaciÃ³n de InformaciÃ³n por Punto de Venta | 11/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-007 | Kardex de Stock | Protocolo de Integridad Transaccional sobre Saldo | 12/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-008 | Integridad Fiscal | ValidaciÃ³n de Identidad Tributaria y Unicidad RFC | 12/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-009 | BÃºsqueda de Clientes | Motor de BÃºsqueda Multi-Criterio sobre Pacientes | 13/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-010 | Saneamiento de Pacientes | Protocolo de NormalizaciÃ³n de Atributos de Persona | 14/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-011 | Registro de Proveedor | AuditorÃ­a Estructural de ValidaciÃ³n Forense | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-012 | ActualizaciÃ³n de Proveedor | ValidaciÃ³n de ExcepciÃ³n por RFC Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-013 | Registro de Usuario | ValidaciÃ³n de ExcepciÃ³n por Correo Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-014 | Baja de Usuario | ValidaciÃ³n de DesactivaciÃ³n LÃ³gica (inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de ContraseÃ±a | Manejo de ExcepciÃ³n por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-016 | AutenticaciÃ³n Root | ValidaciÃ³n de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | ValidaciÃ³n de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | CÃ¡lculo de PVP | ValidaciÃ³n de FÃ³rmula Financiera (Utilidad) | 18/03/2026 | Gabriel AmÃ­lcar Cruz Canto |
| PCB-019 | Robustez de AuditorÃ­a | NormalizaciÃ³n de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | ValidaciÃ³n de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del MÃ³dulo del Sistema + Historia de usuario** |
| :--- |
| MÃ³dulo Seguridad y Acceso â€“ GestiÃ³n Privilegiada |

| **NÃºmero y nombre de la Prueba** |
| :--- |
| PCB-016 / AutenticaciÃ³n Root â€“ AuthService.login() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: AuthService.login()
     * ESTÃNDAR: MIG v12.1 (Hardening and Bypass Control)
     */
    public Usuario login(String email, String password) { // [N1: INICIO]
        // [PCB-N1] Mecanismo de AutenticaciÃ³n Privilegiada (Bypass de Emergencia)
        if ("root".equals(email) && "root".equals(password)) { // [N2] [PCB-N1] -> [SI: N3] [NO: N4]
            return createSuperAdminUser(); // [N3: FIN / ACCESO ROOT]
        }

        // [PCB-N2] DiagnÃ³stico de Conectividad Base de Datos (Seguridad de Capa 1)
        if (dbHealthService.isConnected()) { // [N4] [PCB-N2] -> [SI: N5] [NO: N6]
            System.out.println("Status DB: ONLINE"); // [N5: LOG]
        } else {
            throw new RuntimeException("Error CrÃ­tico: Sin conexiÃ³n con DB."); // [N6: SALIDA (EXC)]
        }

        // [N7] OrquestaciÃ³n de Identidad SistÃ©mica
        Usuario usuario = usuarioRepository.findByEmail(email);

        // [PCB-N3] ValidaciÃ³n de Identidad Registrada
        if (usuario != null) { // [N8] [PCB-N3] -> [SI: N9] [NO: N14]
            // [PCB-N4] VerificaciÃ³n CriptogrÃ¡fica (Hash Matching)
            boolean passwordMatch = passwordEncoder.matches(password, usuario.getPasswordHash()); // [N9]
            if (passwordMatch) { // [N10] [PCB-N4] -> [SI: N11] [NO: N13]
                // [PCB-N5] ValidaciÃ³n de Estatus Operativo (Control de SesiÃ³n)
                if (!usuario.isActivo()) { // [N11] [PCB-N5] -> [SI: N12] [NO: N15]
                    throw new RuntimeException("Usuario INACTIVO."); // [N12: SALIDA (EXC)]
                }
            } else {
                throw new RuntimeException("Credenciales invÃ¡lidas."); // [N13: SALIDA (EXC)]
            }
        } else {
            throw new RuntimeException("Identidad no encontrada."); // [N14: SALIDA (EXC)]
        }

        return usuario; // [N15: FIN / Ã‰XITO]
    }
```

---

### AuditorÃ­a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de NavegaciÃ³n:**
`[index.html] -> [com.omcgc.erp.service] -> [AuthService]`

---

### IdentificaciÃ³n de Nodos

| ID del Nodo | Tipo | DescripciÃ³n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del mÃ©todo `login`. |
| **N2 [PCB-N1]** | Predicado | Â¿Credenciales coinciden con "root"/"root"? |
| **N3** | Fin | Ã‰xito Administrativo (Root Bypass). |
| **N4 [PCB-N2]** | Predicado | Â¿La base de datos estÃ¡ disponible? |
| **N5** | Proceso | Logging de conexiÃ³n de datos activa. |
| **N6** | Salida | ExcepciÃ³n: Fallo total de infraestructura. |
| **N7** | Proceso | RecuperaciÃ³n de registro en repositorio. |
| **N8 [PCB-N3]** | Predicado | Â¿El usuario existe en la tabla de identidades? |
| **N9** | Proceso | ComparaciÃ³n de Hash irreversible (BCrypt). |
| **N10 [PCB-N4]** | Predicado | Â¿La contraseÃ±a es vÃ¡lida? |
| **N11 [PCB-N5]** | Predicado | Â¿El usuario tiene estatus bloqueado? |
| **N12** | Salida | ExcepciÃ³n: "Usuario INACTIVO". |
| **N13** | Salida | ExcepciÃ³n: "Credenciales invÃ¡lidas". |
| **N14** | Salida | ExcepciÃ³n: "Identidad no encontrada". |
| **N15** | Fin | Ã‰xito: Inicio de sesiÃ³n concedido. |

### Paso 1: Grafo de Flujo (CFG - MIG Atomic)

```plantuml
@startuml
digraph CFG_PCB016 {
rankdir=TB
node [shape=circle]

I [label="Inicio\n[N1]"]
N2 [label="2\n[ROOT]"]
N3 [label="3"]
N4 [label="4\n[DB]"]
N5 [label="5"]
N6 [label="6"]
N7 [label="7"]
N8 [label="8\n[USER]"]
N9 [label="9"]
N10 [label="10\n[PASS]"]
N11 [label="11\n[STAT]"]
N12 [label="12"]
N13 [label="13"]
N14 [label="14"]
N15 [label="15\n[FIN]"]
F [label="Fin"]

I -> N2
N2 -> N3 [label="True"]
N2 -> N4 [label="False"]
N4 -> N5 [label="True"]
N4 -> N6 [label="False"]
N5 -> N7
N7 -> N8
N8 -> N10 [label="True"]
N10 -> N11 [label="True"]
N11 -> N12 [label="True"]
N11 -> N15 [label="False"]
N10 -> N13 [label="False"]
N8 -> N14 [label="False"]

N3 -> F
N6 -> F
N12 -> F
N13 -> F
N14 -> F
N15 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe `$V(G)$`

La métrica de complejidad se calcula mediante la fórmula formal de McCabe para grafos de flujo:

*   **V(G) = E - N + 2P**
*   **Donde:**
    *   **E (Aristas):** 21 (Conexiones entre nodos)
    *   **N (Nodos):** 17 (Puntos de control, incluye Inicio/Fin)
    *   **P (Componentes):** 1 (Unidad funcional única)
*   **Cálculo:** 21 - 17 + (2 * 1) = **6**

> [!NOTE]
> El resultado `$V(G) = 6$` coincide con la métrica simplificada de nodos predicado (`P + 1`), lo que valida la ruta crítica del grafo CFG bajo el estándar MIG v12.1.

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2(T) -> N3 -> F |
| **C2** | I -> N2(F) -> N4(F) -> N6 -> F |
| **C3** | I -> N2(F) -> N4(T) -> N5 -> N7 -> N8(F) -> N14 -> F |
| **C4** | I -> N2(F) -> N4(T) -> N5 -> N7 -> N8(T) -> N9 -> N10(F) -> N13 -> F |
| **C5** | I -> N2(F) -> N4(T) -> N5 -> N7 -> N8(T) -> N9 -> N10(T) -> N11(T) -> N12 -> F |
| **C6** | I -> N2(F) -> N4(T) -> N5 -> N7 -> N8(T) -> N9 -> N10(T) -> N11(F) -> N15 -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | **Bypass Root** | `email="root"`, `pass="root"` | **SUCCESS** (SuperAdmin) | Rama N2(T) -> N3 (Full Cover) |
| **C2** | CaÃ­da CrÃ­tica DB | `db.isConnected() = false` | `RuntimeException: Error DB` | Rama N4(F) -> N6 (Full Cover) |
| **C3** | Usuario Inexistente | `email="ghost@omcgc.com"` | `RuntimeException: No encontrada` | Rama N8(F) -> N14 (Full Cover) |
| **C4** | Credencial InvÃ¡lida | `pass = "wrong-123"` | `RuntimeException: InvÃ¡lidas` | Rama N10(F) -> N13 (Full Cover) |
| **C5** | Bloqueo Operativo | `usuario.isActivo() = false` | `RuntimeException: INACTIVO` | Rama N11(T) -> N12 (Full Cover) |
| **C6** | **Acceso Exitoso** | `email="g.cruz@walook.mx"`, `pass="VALID"` | **SUCCESS** (User Object) | Rama N11(F) -> N15 (Full Cover) |

<br>

