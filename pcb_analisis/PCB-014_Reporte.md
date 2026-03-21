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
| MÃ³dulo Seguridad â€“ RF-01 |

| **NÃºmero y nombre de la Prueba** |
| :--- |
| PCB-014 / Baja de Usuario â€“ UsuarioService.delete() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: UsuarioService.delete()
     * ESTÃNDAR: MIG v12.1 (Atomic Control Flow)
     */
    public boolean delete(String id) { // [N1: INICIO]
        // [PCB-N1] BÃºsqueda de Identidad Forense
        Usuario usuario = usuarioRepository.findById(id).orElse(null); // [N2: PROCESO]

        // [PCB-N2] ValidaciÃ³n de Existencia de Objeto
        if (usuario != null) { // [N3] [PCB-N2] -> [SI: N4] [NO: N6]
            // [PCB-N3] Protocolo de DesactivaciÃ³n LÃ³gica (Soft Delete)
            usuario.setEstatus("inactivo"); // [N4]
            usuarioRepository.save(usuario); // [N5]
            return true; // [N6: FIN / RETORNO POSITIVO]
        }
        
        return false; // [N6: FIN / RETORNO NEGATIVO]
    }
```

---

### AuditorÃ­a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de NavegaciÃ³n:**
`[index.html] -> [com.omcgc.erp.service] -> [UsuarioService]`

Glosario de SemÃ¡ntica de Cobertura (White Box Analysis â€” AnÃ¡lisis de Caja Blanca)
â€¢	VERDE â€” Cobertura Total (Full Coverage)
â€¢	AMARILLO â€” Cobertura Parcial (Partial Coverage)
â€¢	ROJO â€” Cobertura Nula o Fuera de Alcance (No Coverage)

---

### IdentificaciÃ³n de Nodos

| ID del Nodo | Tipo | DescripciÃ³n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del mÃ©todo `delete`. |
| **N2** | Proceso | ObtenciÃ³n del usuario por UUID. |
| **N3 [PCB-N1]** | Predicado | Â¿El usuario existe en la base de datos? |
| **N4** | Proceso | ModificaciÃ³n de estatus a "inactivo". |
| **N5** | Proceso | Persistencia del cambio de estado. |
| **N6** | Fin | TÃ©rmino del flujo (Retorno de bandera booleana). |

### Paso 1: Grafo CFG (MIG Atomic)

```plantuml
@startuml
digraph CFG_PCB014 {
rankdir=TB
node [shape=circle]

I [label="Inicio\n[N1]"]
N2 [label="2"]
N3 [label="3\n[PCB-N1]"]
N4 [label="4"]
N5 [label="5"]
N6 [label="6\n[FIN]"]
F [label="Fin"]

I -> N2
N2 -> N3
N3 -> N4 [label="True"]
N3 -> N6 [label="False"]
N4 -> N5
N5 -> N6
N6 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe `$V(G)$`

La métrica de complejidad se calcula mediante la fórmula formal de McCabe para grafos de flujo:

*   **V(G) = E - N + 2P**
*   **Donde:**
    *   **E (Aristas):** 8 (Conexiones entre nodos)
    *   **N (Nodos):** 8 (Puntos de control, incluye Inicio/Fin)
    *   **P (Componentes):** 1 (Unidad funcional única)
*   **Cálculo:** 8 - 8 + (2 * 1) = **2**

> [!NOTE]
> El resultado `$V(G) = 2$` coincide con la métrica simplificada de nodos predicado (`P + 1`), lo que valida la ruta crítica del grafo CFG bajo el estándar MIG v12.1.

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Ã‰xito)** | I -> N2 -> N3(T) -> N4 -> N5 -> N6 -> F |
| **C2 (Fallo)** | I -> N2 -> N3(F) -> N6 -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | **Baja LÃ³gica Exitosa** | `id = "550e8400-e29b-41d4-a716-446655440000"` | `true` (status unificado: inactivo) | Rama N3(T) -> N4 (Full Cover) |
| **C2** | Usuario Inexistente | `id = "UUID-NOT-FOUND-001"` | `false` (Retorno controlado) | Rama N3(F) -> N6 (Full Cover) |

<br>

