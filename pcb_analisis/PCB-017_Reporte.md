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
| PCB-001 | Autenticación de usuario | Protocolo de Acceso y Validación de Infraestructura | 09/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-002 | Manejo de Credenciales Inválidas | Interrupción de Seguridad por Fallo de Contraseña | 09/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-003 | Registro de Producto | Validación de Integridad de Campos Obligatorios | 10/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-004 | SKU Autogenerado | Garantía de Unicidad de Identificación Comercial | 10/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-005 | Rango de Fechas (Ventas) | Filtrado de Reporte Operativo de Transacciones | 11/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-006 | Filtro de Sucursal | Segregación de Información por Punto de Venta | 11/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-007 | Kardex de Stock | Protocolo de Integridad Transaccional sobre Saldo | 12/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-008 | Integridad Fiscal | Validación de Identidad Tributaria y Unicidad RFC | 12/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-009 | Búsqueda de Clientes | Motor de Búsqueda Multi-Criterio sobre Pacientes | 13/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-010 | Saneamiento de Pacientes | Protocolo de Normalización de Atributos de Persona | 14/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-011 | Registro de Proveedor | Auditoría Estructural de Validación Forense | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-012 | Actualización de Proveedor | Validación de Excepción por RFC Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-013 | Registro de Usuario | Validación de Excepción por Correo Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-014 | Baja de Usuario | Validación de Desactivación Lógica (inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de Contraseña | Manejo de Excepción por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validación de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Cálculo de PVP | Validación de Fórmula Financiera (Utilidad) | 18/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Inventarios – RF-12 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-017 / Registro de Movimiento – InventarioService.registrarMovimiento() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÍA: InventarioService.registrarMovimiento()
     * ESTÁNDAR: MIG v12.1 (Atomic Balance Check)
     */
    @Transactional
    public void registrarMovimiento(Movimiento m, String ip) { // [N1: INICIO]
        // [N2] Baseline de Stock Actual
        Integer stockAnterior = inventarioRepository.getCurrentStock(m.getIdProducto(), m.getIdSucursal()); // [N2: PROCESO]
        m.setExistenciaAnterior(stockAnterior);

        // [PCB-N1] Cálculo de Afectación (Lógica de Factor)
        int factor = esSalida(m.getTipoMovimiento()) ? -1 : 1; // [N3: PREDICADO TERNARIO]
        Integer nuevoStock = stockAnterior + (m.getCantidad() * factor);

        // [PCB-N2] Validación Transaccional (Saldo Negro / Negativo)
        if (nuevoStock < 0) { // [N4] [PCB-N2] -> [SI: N5] [NO: N6]
            throw new RuntimeException("Stock insuficiente."); // [N5: SALIDA (EXC)]
        }

        m.setExistenciaActual(nuevoStock);

        // [PCB-N3] Generación de Folio Interno (Hardening INV-)
        // N6a: Null check | N6b: Empty check | N6c: Prefix check
        if (m.getFolio() == null || m.getFolio().trim().isEmpty() || !m.getFolio().startsWith("INV-")) { // [N6] [PCB-N3] -> [SI: N7] [NO: N8]
            m.setFolio("INV-" + System.currentTimeMillis()); // [N7]
        }

        // [N8: PROCESO - PERSISTENCIA DUAL]
        inventarioRepository.saveMovimiento(m); // [N8]
        inventarioRepository.updateExistencia(m.getIdProducto(), m.getIdSucursal(), nuevoStock); // [N9]

        // [PCB-N4] Trazabilidad en Bitácora Maestro
        bitacoraService.registrarEvento(m.getIdUsuario(), "INV-01", ip, "Póliza: " + m.getFolio(), "Stock: " + nuevoStock); // [N10]

        // [PCB-N5] Actualización de Costos Históricos (Entrada Compra)
        if ("ENTRADA_COMPRA".equals(m.getTipoMovimiento())) { // [N11] [PCB-N5] -> [SI: N12] [NO: N13]
            actualizarCostoProducto(m.getIdProducto(), m.getCostoHistorico()); // [N12]
        }
    } // [N13: FIN]
```

---

### Auditoría de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegación:**
`[index.html] -> [com.omcgc.erp.service] -> [InventarioService]`

---

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del método `registrarMovimiento`. |
| **N2** | Proceso | Obtención del stock anterior desde el repositario. |
| **N3 [PCB-N1]** | Predicado | ¿El movimiento es de tipo SALIDA? (Factor -1). |
| **N4 [PCB-N2]** | Predicado | ¿El resultado final es negativo (Nuevo Saldo < 0)? |
| **N5** | Salida | Excepción: "Stock insuficiente". |
| **N6 [PCB-N3]** | Predicado | Validación de formato de Folio `INV-`. |
| **N7** | Proceso | Generación automática de Folio por Timestamp. |
| **N8** | Proceso | Guardado del registro de movimiento. |
| **N9** | Proceso | Actualización de tabla de existencias global. |
| **N10** | Proceso | Registro de trazabilidad en Bitácora. |
| **N11 [PCB-N4]** | Predicado | ¿Es una entrada por compra para actualizar costo? |
| **N12** | Proceso | Aplicación de nuevo costo unitario al producto. |
| **N13** | Fin | Término del flujo de registro exitoso. |

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

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Nodos Predicado + 1** = 4 + 1 = **5**
(Nota: Se contabilizan N3, N4, N6 y N11 como centros de decisión).

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Fallo)** | I -> N2 -> N3 -> N4(T) -> N5 -> F |
| **C2 (Éxito)** | I -> N2 -> N3 -> N4(F) -> N6(F) -> N8 -> N9 -> N10 -> N11(F) -> N13 -> F |
| **C3 (Folio)** | I -> N2 -> N3 -> N4(F) -> N6(T) -> N7 -> N8 -> N9 -> N10 -> N11(F) -> N13 -> F |
| **C4 (Costo)** | I -> N2 -> N3 -> N4(F) -> N6(F) -> N8 -> N9 -> N10 -> N11(T) -> N12 -> N13 -> F |
| **C5 (Entrada)**| I -> N2 -> N3(Salida:False) -> N4(F) -> N6(F) -> N13 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | **Stock Insuficiente** | `saldo=5`, `cantidad=10`, `tipo=SALIDA` | `RuntimeException: Stock insuficiente.` | Rama N4(T) -> N5 (Full Cover) |
| **C2** | Registro Estándar | `saldo=100`, `cantidad=20`, `folio="INV-123"` | **SUCCESS** (Nuevo saldo: 80) | Rama N6(F) -> N8 (Full Cover) |
| **C3** | Folio Ausente | `folio = null` | **SUCCESS** (Folio Autogenn: INV-###) | Rama N6(T) -> N7 (Full Cover) |
| **C4** | **Entrada Compra** | `tipo="ENTRADA_COMPRA"`, `costo=50.0` | **SUCCESS** (Costo Actualizado) | Rama N11(T) -> N12 (Full Cover) |
| **C5** | Entrada Stock | `tipo="ENTRADA_AJUSTE"`, `cantidad=10` | **SUCCESS** (Saldo incrementado) | Rama N11(F) -> N13 (Full Cover) |

<br>
