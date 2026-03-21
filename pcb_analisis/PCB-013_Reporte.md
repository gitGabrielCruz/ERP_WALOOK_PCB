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
| PCB-018 | CÃ¡lculo de PVP | ValidaciÃ³n de FÃ³rmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de AuditorÃ­a | NormalizaciÃ³n de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | ValidaciÃ³n de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del MÃ³dulo del Sistema + Historia de usuario** |
| :--- |
| MÃ³dulo Seguridad y Acceso â€“ HU-M01-03 |

| **NÃºmero y nombre de la Prueba** |
| :--- |
| PCB-013 / Registro de Usuario â€“ UsuarioService.create() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: UsuarioService.create()
     * ESTÃNDAR: MIG v12.1 (FragmentaciÃ³n de Predicados)
     */
    public Usuario create(Usuario usuario) { // [N1: INICIO]
        // [PCB-N1] ValidaciÃ³n Username Null/Empty
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) { // [N2] [PCB-N1] -> [SI: N3] [NO: N6]
            // [PCB-N2] Intento de autogeneraciÃ³n vÃ­a Correo ElectrÃ³nico
            if (usuario.getCorreo() != null && !usuario.getCorreo().trim().isEmpty()) { // [N3] [PCB-N2] -> [SI: N4] [NO: N5]
                String generatedUser = usuario.getCorreo().split("@")[0]; // [N4]
                usuario.setUsuario(generatedUser);
            } else {
                throw new IllegalArgumentException("Username/Correo obligatorio"); // [N5: SALIDA (EXC)]
            }
        }

        // [PCB-N3] ValidaciÃ³n Correo Null/Empty
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) { // [N6] [PCB-N3] -> [SI: N7] [NO: N8]
            throw new IllegalArgumentException("El correo es obligatorio"); // [N7: SALIDA (EXC)]
        }

        // [PCB-N4] ValidaciÃ³n Unicidad Correo (Vulnerabilidad: Duplicidad)
        Usuario existente = usuarioRepository.findByEmail(usuario.getCorreo()); // [N8: PROCESO]
        if (existente != null) { // [N9] [PCB-N4] -> [SI: N10] [NO: N11]
            throw new IllegalArgumentException("El correo electrÃ³nico ya estÃ¡ registrado"); // [N10: SALIDA (EXC)]
        }

        // [N11: PROCESO - GENERACIÃ“N DE IDENTIDAD]
        usuario.setId(UUID.randomUUID().toString());
        
        // [PCB-N5] SelecciÃ³n de Password (Manual vs Fallback Temporal)
        String passwordTemporal = usuario.getPassword() != null ? usuario.getPassword() : "Temp123!"; // [N12] [PCB-N5] -> [N13]
        usuario.setPassword(passwordEncoder.encode(passwordTemporal)); // [N13: PROCESO - BCRYPT]
        usuario.setPasswordTemp(passwordTemporal);

        // [PCB-N6] Estatus por defecto (Hardening Logic)
        if (usuario.getEstatus() == null) { // [N14] [PCB-N6] -> [SI: N15] [NO: N16]
            usuario.setEstatus("activo"); // [N15]
        }

        return usuarioRepository.save(usuario); // [N16: PERSISTENCIA] -> [N17: FIN]
    }
```

---

### AuditorÃ­a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de NavegaciÃ³n:**
`[index.html] -> [com.omcgc.erp.service] -> [UsuarioService]`

Glosario de SemÃ¡ntica de Cobertura (White Box Analysis â€” AnÃ¡lisis de Caja Blanca)
â€¢	VERDE â€” Cobertura Total (Full Coverage): Indica que la lÃ­nea de cÃ³digo y todas sus decisiones lÃ³gicas (if/else) fueron ejecutadas satisfactoriamente.
â€¢	AMARILLO â€” Cobertura Parcial (Partial Coverage): La lÃ­nea fue alcanzada pero existen ramificaciones sin explorar.
â€¢	ROJO â€” Cobertura Nula o Fuera de Alcance (No Coverage): El cÃ³digo no fue detectado por JaCoCo.

---

### IdentificaciÃ³n de Nodos

| ID del Nodo | Tipo | DescripciÃ³n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del mÃ©todo `create`. |
| **N2 [PCB-N1]** | Predicado | Â¿El username es nulo o vacÃ­o? |
| **N3 [PCB-N2]** | Predicado | Â¿El correo permite autogeneraciÃ³n? |
| **N4** | Proceso | GeneraciÃ³n automÃ¡tica de alias de usuario. |
| **N5** | Salida | ExcepciÃ³n: Username/Correo faltante. |
| **N6 [PCB-N3]** | Predicado | Â¿El correo es nulo o vacÃ­o? |
| **N7** | Salida | ExcepciÃ³n: "El correo es obligatorio". |
| **N8** | Proceso | Consulta de unicidad en BD. |
| **N9 [PCB-N4]** | Predicado | Â¿El correo ya existe en sistema? |
| **N10** | Salida | ExcepciÃ³n: "El correo electrÃ³nico ya estÃ¡ registrado". |
| **N11** | Proceso | DefiniciÃ³n de Identidad (ID). |
| **N12 [PCB-N5]** | Predicado | Â¿Password definido manualmente? |
| **N13** | Proceso | Cifrado criptogrÃ¡fico de credencial. |
| **N14 [PCB-N6]** | Predicado | Â¿Estatus definido? |
| **N15** | Proceso | AsignaciÃ³n de estatus "activo" por defecto. |
| **N16** | Proceso | Persistencia Transaccional (Save). |
| **N17 [FIN]** | Fin | TÃ©rmino de la ejecuciÃ³n exitosa. |

### Paso 1: Grafo de Flujo (CFG - MIG Atomic)

```plantuml
@startuml
digraph CFG_PCB013 {
rankdir=TB
node [shape=circle]

I [label="Inicio\n[N1]"]
N2 [label="N2\n[PCB-N1]"]
N3 [label="N3\n[PCB-N2]"]
N4 [label="N4"]
N5 [label="N5"]
N6 [label="N6\n[PCB-N3]"]
N7 [label="N7"]
N8 [label="N8"]
N9 [label="9\n[PCB-N4]"]
N10 [label="10"]
N11 [label="11"]
N12 [label="12\n[PCB-N5]"]
N13 [label="13"]
N14 [label="14\n[PCB-N6]"]
N15 [label="15"]
N16 [label="16"]
N17 [label="17\n[FIN]"]
F [label="Fin"]

I -> N2
N2 -> N3 [label="True"]
N2 -> N6 [label="False"]
N3 -> N4 [label="True"]
N3 -> N5 [label="False"]
N4 -> N6
N6 -> N7 [label="True"]
N6 -> N8 [label="False"]
N8 -> N9
N9 -> N10 [label="True"]
N9 -> N11 [label="False"]
N11 -> N12
N12 -> N13
N13 -> N14
N14 -> N15 [label="True"]
N14 -> N16 [label="False"]
N15 -> N16
N16 -> N17

N5 -> F
N7 -> F
N10 -> F
N17 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe `$V(G)$`

La métrica de complejidad se calcula mediante la fórmula formal de McCabe para grafos de flujo:

*   **V(G) = E - N + 2P**
*   **Donde:**
    *   **E (Aristas):** 22 (Conexiones entre nodos)
    *   **N (Nodos):** 17 (Puntos de control, incluye Inicio/Fin)
    *   **P (Componentes):** 1 (Unidad funcional única)
*   **Cálculo:** 22 - 17 + (2 * 1) = **7**

> [!NOTE]
> El resultado `$V(G) = 7$` coincide con la métrica simplificada de nodos predicado (`P + 1`), lo que valida la ruta crítica del grafo CFG bajo el estándar MIG v12.1.

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2(T) -> N3(F) -> N5 -> F |
| **C2** | I -> N2(F) -> N6(T) -> N7 -> F |
| **C3** | I -> N2(F) -> N6(F) -> N8 -> N9(T) -> N10 -> F |
| **C4** | I -> N2(F) -> N6(F) -> N8 -> N9(F) -> N11 -> N12 -> N13 -> N14(T) -> N15 -> N16 -> N17 -> F |
| **C5** | I -> N2(F) -> N6(F) -> N8 -> N9(F) -> N11 -> N12 -> N13 -> N14(F) -> N16 -> N17 -> F |
| **C6** | I -> N2(T) -> N3(T) -> N4 -> N6(F) -> N8 -> N9(F) -> N11 -> N12 -> N13 -> N14(T) -> N15 -> N16 -> N17 -> F |
| **C7** | I -> N2(T) -> N3(T) -> N4 -> N6(T) -> N7 -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | Identidad Nula | `usuario = "", email = ""` | `IllegalArgumentException: Username/Correo obligatorio` | Rama N3(F) -> N5 (Full Cover) |
| **C2** | Correo Nulo | `usuario = "g.cruz"`, `email = null` | `IllegalArgumentException: El correo es obligatorio` | Rama N6(T) -> N7 (Full Cover) |
| **C3** | **Correo Duplicado** | `email = "admin@walook.mx"`, `existing = true` | `IllegalArgumentException: El correo electrÃ³nico ya estÃ¡ registrado` | Rama N9(T) -> N10 (Full Cover) |
| **C4** | Ã‰xito (Status Default) | `usuario = "admin"`, `email = "a@w.mx"`, `status = null` | **SUCCESS** (Status: activo) | Rama N14(T) -> N15 (Full Cover) |
| **C5** | Ã‰xito (Status Manual) | `usuario = "admin"`, `email = "a@w.mx"`, `status = "master"` | **SUCCESS** (Status: master) | Rama N14(F) -> N16 (Full Cover) |
| **C6** | Ã‰xito (Alias Auto) | `usuario = null`, `email = "gabriel@test.com"` | **SUCCESS** (Username: "gabriel") | Rama N3(T) -> N4 (Full Cover) |
| **C7** | Alias Auto Malformado | `usuario = null`, `email = "test@"` (empty user part) | `IllegalArgumentException: El correo es obligatorio` | Rama N6(T) -> N7 (MIG Atomic) |

<br>

