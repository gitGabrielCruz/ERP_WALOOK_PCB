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
| MÃ³dulo Inventarios â€“ RF-12 |

| **NÃºmero y nombre de la Prueba** |
| :--- |
| PCB-017 / Registro de Movimiento â€“ InventarioService.registrarMovimiento() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: InventarioService.registrarMovimiento()
     * ESTÃNDAR: MIG v12.1 (Atomic Balance Check)
     */
    @Transactional
    public void registrarMovimiento(Movimiento m, String ip) { // [N1: INICIO]
        // [N2] Baseline de Stock Actual
        Integer stockAnterior = inventarioRepository.getCurrentStock(m.getIdProducto(), m.getIdSucursal()); // [N2: PROCESO]
        m.setExistenciaAnterior(stockAnterior);

        // [PCB-N1] CÃ¡lculo de AfectaciÃ³n (LÃ³gica de Factor)
        int factor = esSalida(m.getTipoMovimiento()) ? -1 : 1; // [N3: PREDICADO TERNARIO]
        Integer nuevoStock = stockAnterior + (m.getCantidad() * factor);

        // [PCB-N2] ValidaciÃ³n Transaccional (Saldo Negro / Negativo)
        if (nuevoStock < 0) { // [N4] [PCB-N2] -> [SI: N5] [NO: N6]
            throw new RuntimeException("Stock insuficiente."); // [N5: SALIDA (EXC)]
        }

        m.setExistenciaActual(nuevoStock);

        // [PCB-N3] GeneraciÃ³n de Folio Interno (Hardening INV-)
        // N6a: Null check | N6b: Empty check | N6c: Prefix check
        if (m.getFolio() == null || m.getFolio().trim().isEmpty() || !m.getFolio().startsWith("INV-")) { // [N6] [PCB-N3] -> [SI: N7] [NO: N8]
            m.setFolio("INV-" + System.currentTimeMillis()); // [N7]
        }

        // [N8: PROCESO - PERSISTENCIA DUAL]
        inventarioRepository.saveMovimiento(m); // [N8]
        inventarioRepository.updateExistencia(m.getIdProducto(), m.getIdSucursal(), nuevoStock); // [N9]

        // [PCB-N4] Trazabilidad en BitÃ¡cora Maestro
        bitacoraService.registrarEvento(m.getIdUsuario(), "INV-01", ip, "PÃ³liza: " + m.getFolio(), "Stock: " + nuevoStock); // [N10]

        // [PCB-N5] ActualizaciÃ³n de Costos HistÃ³ricos (Entrada Compra)
        if ("ENTRADA_COMPRA".equals(m.getTipoMovimiento())) { // [N11] [PCB-N5] -> [SI: N12] [NO: N13]
            actualizarCostoProducto(m.getIdProducto(), m.getCostoHistorico()); // [N12]
        }
    } // [N13: FIN]
```

---

### AuditorÃ­a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de NavegaciÃ³n:**
`[index.html] -> [com.omcgc.erp.service] -> [InventarioService]`

---

### IdentificaciÃ³n de Nodos

| ID del Nodo | Tipo | DescripciÃ³n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del mÃ©todo `registrarMovimiento`. |
| **N2** | Proceso | ObtenciÃ³n del stock anterior desde el repositario. |
| **N3 [PCB-N1]** | Predicado | Â¿El movimiento es de tipo SALIDA? (Factor -1). |
| **N4 [PCB-N2]** | Predicado | Â¿El resultado final es negativo (Nuevo Saldo < 0)? |
| **N5** | Salida | ExcepciÃ³n: "Stock insuficiente". |
| **N6 [PCB-N3]** | Predicado | ValidaciÃ³n de formato de Folio `INV-`. |
| **N7** | Proceso | GeneraciÃ³n automÃ¡tica de Folio por Timestamp. |
| **N8** | Proceso | Guardado del registro de movimiento. |
| **N9** | Proceso | ActualizaciÃ³n de tabla de existencias global. |
| **N10** | Proceso | Registro de trazabilidad en BitÃ¡cora. |
| **N11 [PCB-N4]** | Predicado | Â¿Es una entrada por compra para actualizar costo? |
| **N12** | Proceso | AplicaciÃ³n de nuevo costo unitario al producto. |
| **N13** | Fin | TÃ©rmino del flujo de registro exitoso. |

### Paso 1: Grafo CFG (MIG Atomic)

```plantuml
@startuml
digraph CFG_PCB017 {
rankdir=TB
node [shape=circle]

I [label="Inicio\n[N1]"]
N2 [label="2"]
N3 [label="3\n[PCB-N1]"]
N4 [label="4\n[PCB-N2]"]
N5 [label="5\n[EXC]"]
N6 [label="6\n[PCB-N3]"]
N7 [label="7"]
N8 [label="8"]
N9 [label="9"]
N10 [label="10"]
N11 [label="11\n[PCB-N4]"]
N12 [label="12"]
N13 [label="13\n[FIN]"]
F [label="Fin"]

I -> N2
N2 -> N3
N3 -> N4
N4 -> N5 [label="True"]
N4 -> N6 [label="False"]
N6 -> N7 [label="True"]
N6 -> N8 [label="False"]
N7 -> N8
N8 -> N9
N9 -> N10
N10 -> N11
N11 -> N12 [label="True"]
N11 -> N13 [label="False"]
N12 -> N13

N5 -> F
N13 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe `$V(G)$`

La métrica de complejidad se calcula mediante la fórmula formal de McCabe para grafos de flujo:

*   **V(G) = E - N + 2P**
*   **Donde:**
    *   **E (Aristas):** 18 (Conexiones entre nodos)
    *   **N (Nodos):** 15 (Puntos de control, incluye Inicio/Fin)
    *   **P (Componentes):** 1 (Unidad funcional única)
*   **Cálculo:** 18 - 15 + (2 * 1) = **5**

> [!NOTE]
> El resultado `$V(G) = 5$` coincide con la métrica simplificada de nodos predicado (`P + 1`), lo que valida la ruta crítica del grafo CFG bajo el estándar MIG v12.1.
(Nota: Se contabilizan N3, N4, N6 y N11 como centros de decisiÃ³n).

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Fallo)** | I -> N2 -> N3 -> N4(T) -> N5 -> F |
| **C2 (Ã‰xito)** | I -> N2 -> N3 -> N4(F) -> N6(F) -> N8 -> N9 -> N10 -> N11(F) -> N13 -> F |
| **C3 (Folio)** | I -> N2 -> N3 -> N4(F) -> N6(T) -> N7 -> N8 -> N9 -> N10 -> N11(F) -> N13 -> F |
| **C4 (Costo)** | I -> N2 -> N3 -> N4(F) -> N6(F) -> N8 -> N9 -> N10 -> N11(T) -> N12 -> N13 -> F |
| **C5 (Entrada)**| I -> N2 -> N3(Salida:False) -> N4(F) -> N6(F) -> N13 -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | **Stock Insuficiente** | `saldo=5`, `cantidad=10`, `tipo=SALIDA` | `RuntimeException: Stock insuficiente.` | Rama N4(T) -> N5 (Full Cover) |
| **C2** | Registro EstÃ¡ndar | `saldo=100`, `cantidad=20`, `folio="INV-123"` | **SUCCESS** (Nuevo saldo: 80) | Rama N6(F) -> N8 (Full Cover) |
| **C3** | Folio Ausente | `folio = null` | **SUCCESS** (Folio Autogenn: INV-###) | Rama N6(T) -> N7 (Full Cover) |
| **C4** | **Entrada Compra** | `tipo="ENTRADA_COMPRA"`, `costo=50.0` | **SUCCESS** (Costo Actualizado) | Rama N11(T) -> N12 (Full Cover) |
| **C5** | Entrada Stock | `tipo="ENTRADA_AJUSTE"`, `cantidad=10` | **SUCCESS** (Saldo incrementado) | Rama N11(F) -> N13 (Full Cover) |

<br>

