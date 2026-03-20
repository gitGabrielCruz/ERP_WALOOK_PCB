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
| PCB-018 | Cálculo de PVP | Validación de Fórmula Financiera (Costo + Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Inventarios – HU-M01-02 / RNF-03 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-018 / Cálculo de PVP – InventarioService.saveProduct() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public void saveProduct(Producto p, String ip) { // [N1: INICIO]
        // [PCB-N1] Generación de Identidad
        boolean isNew = (p.getIdProducto() == null || p.getIdProducto().isEmpty()); // [N2]
        if (isNew) { p.setIdProducto(java.util.UUID.randomUUID().toString()); }

        // [PCB-N2] Generación de SKU Comercial
        if (p.getSku() == null || p.getSku().isEmpty() || p.getSku().equalsIgnoreCase("Autogenerado")) { // [N3]
            p.setSku("75" + System.currentTimeMillis()); 
        }

        // [PCB-N3] Regla 2: Cálculo Dinámico de PVP (Utilidad)
        if (p.getCostoUnitario() != null && p.getPorcentajeUtilidad() != null) { // [N4] [PCB-N3] -> [SI: N5] [NO: N6]
            BigDecimal factor = BigDecimal.ONE.add(p.getPorcentajeUtilidad().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)); // [N5]
            p.setPrecioVenta(p.getCostoUnitario().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        }

        // [N6] Persistencia y Auditoría
        inventarioRepository.save(p); // [N6]
        bitacoraService.registrarEvento(p.getIdUsuarioOperacion(), "PRO-01", ip, p.getSku(), p.getNombre()); // [N7]
    } // [N8: FIN]
```


---

### Auditoría de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegación:**
```text
[index.html] -> [com.omcgc.erp.service] -> [InventarioService]
```

Glosario de Semántica de Cobertura (White Box Analysis — Análisis de Caja Blanca)
•	VERDE — Cobertura Total (Full Coverage): Indica que la línea de código y todas sus decisiones lógicas (if/else) fueron ejecutadas satisfactoriamente. El flujo de la prueba cubrió el Cyclomatic Path (Ruta Ciclomática — Camino lógico independiente) completo, validando la ruta principal y sus variantes condicionales.
•	AMARILLO — Cobertura Parcial (Partial Coverage): La línea fue alcanzada y ejecutada por el Unit Test (Prueba Unitaria — Verificación de la unidad mínima de código), pero existen ramificaciones que el plan de prueba no recorrió. Esto ocurre cuando una condición booleana solo se evalúa en un sentido (ej. solo true), dejando caminos lógicos sin explorar.
•	ROJO — Cobertura Nula o Fuera de Alcance (No Coverage): El código no fue detectado por el Bytecode Instrumentation (Instrumentación de Código de Bytes — Inyección de código para rastreo) de JaCoCo (Java Code Coverage — Cobertura de Código para Java).
Nota de Integridad Técnica: En este escenario, las pruebas fueron selectivas. Si el algoritmo de JaCoCo detecta código que no estaba considerado en el plan de ejecución or que fue omitido por los criterios de filtrado, lo reporta como "no detectado". Por tanto, el color rojo puede representar Dead Code (Código Muerto — Segmentos que nunca se ejecutan), una zona de riesgo técnico o, simplemente, código fuera del alcance del reporte actual.

---

---

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del método `saveProduct`. |
| **N2 [PCB-N1]** | Predicado | ¿Es un producto nuevo (Creación)? |
| **N3 [PCB-N2]** | Predicado | ¿El SKU es nulo o requiere autogeneración? |
| **N4 [PCB-N3]** | Predicado | ¿Existen datos de Costo y Utilidad para el cálculo de PVP? (Evaluado como SI para este test). |
| **N5** | Proceso | Aplicación de fórmula: $PVP = Costo * (1 + Utilidad/100)$. |
| **N6** | Proceso | Persistencia del registro en `inventarioRepository`. |
| **N7** | Proceso | Registro de evento en bitácora de auditoría. |
| **N8** | Fin | Término del proceso de guardado y recálculo. |

### Paso 1: Grafo de Flujo (CFG)

```plantuml
@startuml
digraph CFG_PCB018 {
node [shape=circle]
I [label="Inicio\nN1"]
N2 [label="2\n[PCB-N1]"]
N3 [label="3\n[PCB-N2]"]
N4 [label="4\n[PCB-N3]"]
N5 [label="5\n[CALC]"]
N6 [label="6"]
N7 [label="7"]
N8 [label="8\n[FIN]"]

I -> N2
N2 -> N3
N3 -> N4
N4 -> N5 [label="True"]
N4 -> N6 [label="False"]
N5 -> N6
N6 -> N7
N7 -> N8
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G)** = Nodos Predicado + 1 = 3 + 1 = **4**

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Éxito con Cálculo)** | I -> N2 -> N3 -> N4(T) -> N5 -> N6 -> N7 -> N8 |
| **C2 (Sin Cálculo)** | I -> N2 -> N3 -> N4(F) -> N6 -> N7 -> N8 |



| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | **Cálculo de PVP Exitoso** | `costo = 100`, `util = 50` | `precioVenta = 150.00` | Líneas 42-45 (VERDE) |
| **C2** | Producto sin Utilidad | `costo = 100`, `util = null` | `precioVenta = null` (Sin cambio) | Rama N4(F) -> N6 |
| **C3** | Producto Nuevo (UUID) | `id = null` | **SUCCESS** (UUID Generado) | Rama N2(T) -> N3 |
| **C4** | SKU Autogenerado | `sku = "Autogenerado"` | **SUCCESS** (SKU: 75000010101) | Rama N3(T) -> N4 |

<br>
