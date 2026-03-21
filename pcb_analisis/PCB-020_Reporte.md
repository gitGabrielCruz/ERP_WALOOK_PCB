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
| PCB-020 / Carga de Diccionario â€“ AuditPatternService.loadDictionary() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: AuditPatternService.loadDictionary()
     * ESTÃNDAR: MIG v12.1 (Atomicidad de Transacciones Binarias)
     */
    @SuppressWarnings("unchecked")
    private void loadDictionary() throws Exception { // [N1: INICIO]
        // [N2] Lectura de Binario Seguro
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(FILE_PATH)); // [N2: PROCESO]

        // [PCB-N1] Protocolo de Descifrado AES-256
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
        cipher.init(Cipher.DECRYPT_MODE, generateKey());
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes); // [N3: PROCESO]

        // [N4] DeserializaciÃ³n de Objetos en Memoria
        ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        List<LogPattern> list = (List<LogPattern>) ois.readObject(); // [N4: PROCESO]

        // [PCB-N2] PoblaciÃ³n de Memoria (Map)
        patterns.clear();
        for (LogPattern lp : list) { // [N5: PREDICADO LOOP]
            patterns.put(lp.getId(), lp); // [N6: PROCESO LOOP]
        }
    } // [N7: FIN]
```

---

### AuditorÃ­a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de NavegaciÃ³n:**
```text
[index.html] -> [com.omcgc.erp.service] -> [AuditPatternService]
```

Glosario de SemÃ¡ntica de Cobertura (White Box Analysis â€” AnÃ¡lisis de Caja Blanca)
â€¢	VERDE â€” Cobertura Total (Full Coverage): Indica que la lÃ­nea de cÃ³digo y todas sus decisiones lÃ³gicas (if/else) fueron ejecutadas satisfactoriamente. El flujo de la prueba cubriÃ³ el Cyclomatic Path (Ruta CiclomÃ¡tica â€” Camino lÃ³gico independiente) completo, validando la ruta principal y sus variantes condicionales.
â€¢	AMARILLO â€” Cobertura Parcial (Partial Coverage): La lÃ­nea fue alcanzada y ejecutada por el Unit Test (Prueba Unitaria â€” VerificaciÃ³n de la unidad mÃ­nima de cÃ³digo), pero existen ramificaciones que el plan de prueba no recorriÃ³. Esto ocurre cuando una condiciÃ³n booleana solo se evalÃºa en un sentido (ej. solo true), dejando caminos lÃ³gicos sin explorar.
â€¢	ROJO â€” Cobertura Nula o Fuera de Alcance (No Coverage): El cÃ³digo no fue detectado por el Bytecode Instrumentation (InstrumentaciÃ³n de CÃ³digo de Bytes â€” InyecciÃ³n de cÃ³digo para rastreo) de JaCoCo (Java Code Coverage â€” Cobertura de CÃ³digo para Java).

---

### IdentificaciÃ³n de Nodos

| ID del Nodo | Tipo | DescripciÃ³n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del mÃ©todo `loadDictionary`. |
| **N2** | Proceso | RecuperaciÃ³n de `audit_dictionary.dat` desde el sistema de archivos. |
| **N3** | Proceso | EjecuciÃ³n de descifrado AES-256 (Nodo CrÃ­tico). |
| **N4** | Proceso | DeserializaciÃ³n del binario a lista de objetos LogPattern. |
| **N5** | Predicado | IteraciÃ³n sobre la lista para poblar el Map de memoria. |
| **N6** | Proceso | InserciÃ³n de patrÃ³n en el Diccionario (HashMap). |
| **N7** | Fin | TÃ©rmino de la inicializaciÃ³n del motor de auditorÃ­a. |

### Paso 1: Grafo de Flujo (CFG)

```plantuml
@startuml
digraph CFG_PCB020 {
node [shape=circle]
I [label="Inicio\nN1"]
N2 [label="2\n[FILE]"]
N3 [label="3\n[DECRYPT]"]
N4 [label="4\n[OBJECT]"]
N5 [label="5\n[LOOP]"]
N6 [label="6\n[PUT]"]
F [label="Fin\nN7"]

I -> N2
N2 -> N3
N3 -> N4
N4 -> N5
N5 -> N6 [label="Next"]
N6 -> N5
N5 -> F [label="End"]
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe `$V(G)$`

La métrica de complejidad se calcula mediante la fórmula formal de McCabe para grafos de flujo:

*   **V(G) = E - N + 2P**
*   **Donde:**
    *   **E (Aristas):** 8 (Conexiones entre nodos)
    *   **N (Nodos):** 7 (Puntos de control, incluye Inicio/Fin)
    *   **P (Componentes):** 1 (Unidad funcional única)
*   **Cálculo:** 8 - 7 + (2 * 1) = **3**

> [!NOTE]
> El resultado `$V(G) = 3$` coincide con la métrica simplificada de nodos predicado (`P + 1`), lo que valida la ruta crítica del grafo CFG bajo el estándar MIG v12.1.

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2 -> N3 -> N4 -> N5(End) -> F |
| **C2** | I -> N2 -> N3 -> N4 -> N5(Next) -> N6 -> N5(End) -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Log de Pruebas)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | Diccionario VacÃ­o | `dat = empty list` | `Map.size() = 0` | Rama N5 -> F (End) |
| **C2** | **Carga Exitosa (Lote)** | `dat = 28 patrones` | `Map.size() = 28` | LÃ­nea 49 (VERDE) |

<br>


