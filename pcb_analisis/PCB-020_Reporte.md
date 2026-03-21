# TEST PRUEBAS DE CAJA BLANCA - AUTOMATIZADA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel Am√≠lcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gesti√≥n de √≥pticas OMCGC |

<br>

| **PLAN DE PRUEBAS DE CAJA BLANCA: BACKEND (MIG-MASTER)** | | | | |
| :--- | :--- | :--- | :--- | :--- |
| **N˙mero** | **Nombre de la Prueba Backend** | **DescripciÛn** | **Fecha** | **Herramienta / Responsable** |
| PCB-001 | AutenticaciÛn de usuario | Protocolo de Acceso y ValidaciÛn de Infraestructura | 09/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-002 | Manejo de Credenciales Inv·lidas | InterrupciÛn de Seguridad por Fallo de ContraseÒa | 09/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-003 | Registro de Producto | ValidaciÛn de Integridad de Campos Obligatorios | 10/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-004 | SKU Autogenerado | GarantÌa de Unicidad de IdentificaciÛn Comercial | 10/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-005 | Rango de Fechas (Ventas) | Filtrado de Reporte Operativo de Transacciones | 11/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-006 | Filtro de Sucursal | SegregaciÛn de InformaciÛn por Punto de Venta | 11/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-007 | Kardex de Stock | Protocolo de Integridad Transaccional sobre Saldo | 12/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-008 | Integridad Fiscal | ValidaciÛn de Identidad Tributaria y Unicidad RFC | 12/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-009 | B˙squeda de Clientes | Motor de B˙squeda Multi-Criterio sobre Pacientes | 13/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-010 | Saneamiento de Pacientes | Protocolo de NormalizaciÛn de Atributos de Persona | 14/03/2026 | Gabriel AmÌlcar Cruz Canto |
| PCB-011 | Registro de Proveedor | AuditorÌa Estructural de ValidaciÛn Forense | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-012 | ActualizaciÛn de Proveedor | ValidaciÛn de ExcepciÛn por RFC Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-013 | Registro de Usuario | ValidaciÛn de ExcepciÛn por Correo Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-014 | Baja de Usuario | ValidaciÛn de DesactivaciÛn LÛgica (inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de ContraseÒa | Manejo de ExcepciÛn por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-016 | AutenticaciÛn Root | ValidaciÛn de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | ValidaciÛn de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | C·lculo de PVP | ValidaciÛn de FÛrmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de AuditorÌa | NormalizaciÛn de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | ValidaciÛn de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-012 | Actualizaci√≥n de Proveedor | Validaci√≥n de Excepci√≥n por RFC Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-013 | Registro de Usuario | Validaci√≥n de Excepci√≥n por Correo Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-014 | Baja de Usuario | Validaci√≥n de Desactivaci√≥n L√≥gica (inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de Contrase√±a | Manejo de Excepci√≥n por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-016 | Autenticaci√≥n Root | Validaci√≥n de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validaci√≥n de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | C√°lculo de PVP | Validaci√≥n de F√≥rmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de Auditor√≠a | Normalizaci√≥n de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validaci√≥n de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del M√≥dulo del Sistema + Historia de usuario** |
| :--- |
| M√≥dulo Auditor√≠a y Privacidad ‚Äì RNF-01 |

| **N√∫mero y nombre de la Prueba** |
| :--- |
| PCB-020 / Carga de Diccionario ‚Äì AuditPatternService.loadDictionary() |

### Paso 0: S√∫per-Etiquetado del C√≥digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITOR√çA: AuditPatternService.loadDictionary()
     * EST√ÅNDAR: MIG v12.1 (Atomicidad de Transacciones Binarias)
     */
    @SuppressWarnings("unchecked")
    private void loadDictionary() throws Exception { // [N1: INICIO]
        // [N2] Lectura de Binario Seguro
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(FILE_PATH)); // [N2: PROCESO]

        // [PCB-N1] Protocolo de Descifrado AES-256
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
        cipher.init(Cipher.DECRYPT_MODE, generateKey());
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes); // [N3: PROCESO]

        // [N4] Deserializaci√≥n de Objetos en Memoria
        ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        List<LogPattern> list = (List<LogPattern>) ois.readObject(); // [N4: PROCESO]

        // [PCB-N2] Poblaci√≥n de Memoria (Map)
        patterns.clear();
        for (LogPattern lp : list) { // [N5: PREDICADO LOOP]
            patterns.put(lp.getId(), lp); // [N6: PROCESO LOOP]
        }
    } // [N7: FIN]
```

---

### Auditor√≠a de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegaci√≥n:**
```text
[index.html] -> [com.omcgc.erp.service] -> [AuditPatternService]
```

Glosario de Sem√°ntica de Cobertura (White Box Analysis ‚Äî An√°lisis de Caja Blanca)
‚Ä¢	VERDE ‚Äî Cobertura Total (Full Coverage): Indica que la l√≠nea de c√≥digo y todas sus decisiones l√≥gicas (if/else) fueron ejecutadas satisfactoriamente. El flujo de la prueba cubri√≥ el Cyclomatic Path (Ruta Ciclom√°tica ‚Äî Camino l√≥gico independiente) completo, validando la ruta principal y sus variantes condicionales.
‚Ä¢	AMARILLO ‚Äî Cobertura Parcial (Partial Coverage): La l√≠nea fue alcanzada y ejecutada por el Unit Test (Prueba Unitaria ‚Äî Verificaci√≥n de la unidad m√≠nima de c√≥digo), pero existen ramificaciones que el plan de prueba no recorri√≥. Esto ocurre cuando una condici√≥n booleana solo se eval√∫a en un sentido (ej. solo true), dejando caminos l√≥gicos sin explorar.
‚Ä¢	ROJO ‚Äî Cobertura Nula o Fuera de Alcance (No Coverage): El c√≥digo no fue detectado por el Bytecode Instrumentation (Instrumentaci√≥n de C√≥digo de Bytes ‚Äî Inyecci√≥n de c√≥digo para rastreo) de JaCoCo (Java Code Coverage ‚Äî Cobertura de C√≥digo para Java).

---

### Identificaci√≥n de Nodos

| ID del Nodo | Tipo | Descripci√≥n |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del m√©todo `loadDictionary`. |
| **N2** | Proceso | Recuperaci√≥n de `audit_dictionary.dat` desde el sistema de archivos. |
| **N3** | Proceso | Ejecuci√≥n de descifrado AES-256 (Nodo Cr√≠tico). |
| **N4** | Proceso | Deserializaci√≥n del binario a lista de objetos LogPattern. |
| **N5** | Predicado | Iteraci√≥n sobre la lista para poblar el Map de memoria. |
| **N6** | Proceso | Inserci√≥n de patr√≥n en el Diccionario (HashMap). |
| **N7** | Fin | T√©rmino de la inicializaci√≥n del motor de auditor√≠a. |

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

### Paso 2: Complejidad Ciclom√°tica McCabe $V(G)$

*   **V(G) = Nodos Predicado + 1** = 1 + 1 = **2**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2 -> N3 -> N4 -> N5(End) -> F |
| **C2** | I -> N2 -> N3 -> N4 -> N5(Next) -> N6 -> N5(End) -> F |

### Paso 4: Matriz de Automatizaci√≥n (Log de Pruebas)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | Diccionario Vac√≠o | `dat = empty list` | `Map.size() = 0` | Rama N5 -> F (End) |
| **C2** | **Carga Exitosa (Lote)** | `dat = 28 patrones` | `Map.size() = 28` | L√≠nea 49 (VERDE) |

<br>

