# TEST PRUEBAS DE CAJA BLANCA - AUTOMATIZADA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel AmÃ­lcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestiÃ³n de Ã³pticas OMCGC |

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
| PCB-018 | Cálculo de PVP | Validación de Fórmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |
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
| MÃ³dulo AuditorÃ­a y Privacidad â€“ RNF-01 |

| **NÃºmero y nombre de la Prueba** |
| :--- |
| PCB-019 / Robustez de AuditorÃ­a â€“ BitacoraService.registrarEvento() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: BitacoraService.registrarEvento()
     * ESTÃNDAR: MIG v12.1 (Captura de Flujos Excepcionales y Ciclos)
     */
    public void registrarEvento(String idUsuario, String idPatron, String ip, String paramX, String paramS) { // [N1: INICIO]
        try { // [N2: INICIO TRY]
            // [N3] ConstrucciÃ³n de Log y FragmentaciÃ³n
            String logCompleto = auditPatternService.buildLog(idPatron, paramX, paramS); // [N3: PROCESO]
            String[] parts = logCompleto.split("\\|"); // [N4: PROCESO]
            int n = parts.length;

            // [N5] ReconstrucciÃ³n de AnÃ¡lisis TÃ©cnico (Bucle)
            StringBuilder sbAnalisis = new StringBuilder();
            for (int i = 3; i < n - 1; i++) { // [N5: PREDICADO LOOP]
                sbAnalisis.append(parts[i].trim()); // [N6: PROCESO LOOP]
            }

            Bitacora b = new Bitacora(); // [N7: PROCESO]
            b.setIdUsuario(idUsuario);

            // [PCB-N1] NormalizaciÃ³n de IP Nula y Cifrado AES
            b.setIpOrigen(encrypt(ip != null ? ip : "0.0.0.0")); // [N8: PREDICADO] -> [SI: N9] [NO: N10]

            // [N11] Persistencia con Capa de Privacidad
            b.setDetalles(encrypt(parts[2] + " | " + sbAnalisis.toString())); 
            bitacoraRepository.save(b); // [N11: PROCESO]
        } catch (Exception e) { // [N12: EXCEPCIÃ“N]
            System.err.println("Error: " + e.getMessage());
        }
    } // [N13: FIN]
```

---

### AuditorÃ­a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de NavegaciÃ³n:**
```text
[index.html] -> [com.omcgc.erp.service] -> [BitacoraService]
```

Glosario de SemÃ¡ntica de Cobertura (White Box Analysis â€” AnÃ¡lisis de Caja Blanca)
â€¢	VERDE â€” Cobertura Total (Full Coverage): Indica que la lÃ­nea de cÃ³digo y todas sus decisiones lÃ³gicas (if/else) fueron ejecutadas satisfactoriamente. El flujo de la prueba cubriÃ³ el Cyclomatic Path (Ruta CiclomÃ¡tica â€” Camino lÃ³gico independiente) completo, validando la ruta principal y sus variantes condicionales.
â€¢	AMARILLO â€” Cobertura Parcial (Partial Coverage): La lÃ­nea fue alcanzada y ejecutada por el Unit Test (Prueba Unitaria â€” VerificaciÃ³n de la unidad mÃ­nima de cÃ³digo), pero existen ramificaciones que el plan de prueba no recorriÃ³. Esto ocurre cuando una condiciÃ³n booleana solo se evalÃºa en un sentido (ej. solo true), dejando caminos lÃ³gicos sin explorar.
â€¢	ROJO â€” Cobertura Nula o Fuera de Alcance (No Coverage): El cÃ³digo no fue detectado por el Bytecode Instrumentation (InstrumentaciÃ³n de CÃ³digo de Bytes â€” InyecciÃ³n de cÃ³digo para rastreo) de JaCoCo (Java Code Coverage â€” Cobertura de CÃ³digo para Java).

---

### IdentificaciÃ³n de Nodos

| ID del Nodo | Tipo | DescripciÃ³n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del protocolo de auditorÃ­a. |
| **N2** | Try | Apertura del bloque de captura robusta. |
| **N3/N4** | Proceso | ConstrucciÃ³n de log y tokenizaciÃ³n por pipe. |
| **N5** | Predicado | Control de bucle (i < n - 1) para anÃ¡lisis tÃ©cnico. |
| **N6** | Proceso | ConcatenaciÃ³n de fragmentos del log. |
| **N7** | Proceso | InstanciaciÃ³n del objeto Bitacora. |
| **N8 [PCB-N1]** | Predicado | Â¿La IP es nula? (Ternaria). |
| **N9** | Proceso | NormalizaciÃ³n a "0.0.0.0". |
| **N10** | Proceso | Uso de IP de origen real. |
| **N11** | Proceso | Cifrado AES y persistencia en repositorio. |
| **N12** | ExcepciÃ³n | Captura de falla (Catch block). |
| **N13** | Fin | TÃ©rmino del registro de evento. |

### Paso 1: Grafo de Flujo (CFG)

```plantuml
@startuml
digraph CFG_PCB019 {
node [shape=circle]
I [label="Inicio\nN1"]
N2 [label="2\n[TRY]"]
N3 [label="3/4"]
N5 [label="5\n[LOOP]"]
N6 [label="6"]
N7 [label="7"]
N8 [label="8\n[IP?]"]
N9 [label="9\n[0.0.0.0]"]
N10 [label="10\n[REAL]"]
N11 [label="11\n[SAVE]"]
N12 [label="12\n[CATCH]"]
F [label="Fin\n13"]

I -> N2
N2 -> N3
N3 -> N5
N5 -> N6 [label="Next"]
N6 -> N5
N5 -> N7 [label="End"]
N7 -> N8
N8 -> N9 [label="True (Null)"]
N8 -> N10 [label="False (Not Null)"]
N9 -> N11
N10 -> N11
N11 -> F
N2 -> N12 [style=dotted, label="Exception"]
N12 -> F
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

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2 -> N3 -> N5(End) -> N7 -> N8(True) -> N9 -> N11 -> F |
| **C2** | I -> N2 -> N3 -> N5(End) -> N7 -> N8(False) -> N10 -> N11 -> F |
| **C3** | I -> N2 -> N3 -> N5(Next) -> N6 -> N5(End) -> N7 -> N8 -> N11 -> F |
| **C4** | I -> N2 -> N12 -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Log de Pruebas)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | NormalizaciÃ³n IP Nula | `ip = null` | `ipOrigen = "0.0.0.0"` (AES) | Rama N8(T) -> N9 |
| **C2** | Registro con IP Real | `ip = "192.168.1.1"` | `ipOrigen = "192.168.1.1"` (AES)| Rama N8(F) -> N10 |
| **C3** | Procesamiento de Bucle | `parts.length > 5` | `sbAnalisis` con contenido | Rama N5 -> N6 (Next) |
| **C4** | Fallo en ConstrucciÃ³n Log | `idPatron = "INVALID"` | `System.err.println` | Bloque Catch N12 |

<br>


