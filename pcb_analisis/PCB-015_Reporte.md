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
| MÃ³dulo Seguridad â€“ GestiÃ³n de Credenciales |

| **NÃºmero y nombre de la Prueba** |
| :--- |
| PCB-015 / Reset de ContraseÃ±a â€“ UsuarioService.resetPassword() |

### Paso 0: SÃºper-Etiquetado del CÃ³digo (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÃA: UsuarioService.resetPassword()
     * ESTÃNDAR: MIG v12.1 (Infraestructura de Excepciones)
     */
    public String resetPassword(String id) { // [N1: INICIO]
        // [PCB-N1] BÃºsqueda de Identidad Forense
        Usuario usuario = usuarioRepository.findById(id).orElse(null); // [N2: PROCESO]

        if (usuario == null) { // [N3] [PCB-N1] -> [SI: N4] [NO: N5]
            return null; // [N4: FIN / CONTROL DE NULIDAD]
        }

        // [PCB-N2] OrquestaciÃ³n de Credencial Temporal
        String newPass = generateTemporaryPassword(); // [N5]
        usuario.setPassword(encoder.encode(newPass)); // [N6: CIFRADO]
        usuarioRepository.save(usuario); // [N7: PERSISTENCIA]

        // [PCB-N3] Sistema de NotificaciÃ³n (Resiliencia ante Fallos de Infraestructura)
        try { // [N8: INICIO TRY]
            emailService.sendResetEmail(usuario.getCorreo(), newPass); // [N9]
        } catch (Exception e) { // [N10: CAPTURA EXCEPCIÃ“N]
            // [N10] Log: "Fallo envÃ­o email" (Continuidad del negocio asegurada)
        }

        return newPass; // [N11: FIN / Ã‰XITO OPERATIVO]
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
| **N1** | Inicio | Comienzo del mÃ©todo `resetPassword`. |
| **N2** | Proceso | LocalizaciÃ³n de usuario por su Identificador (UUID). |
| **N3 [PCB-N1]** | Predicado | Â¿El usuario existe (`usuario != null`)? |
| **N4** | Fin | TÃ©rmino por usuario inexistente (Retorno controlado). |
| **N5** | Proceso | GeneraciÃ³n de clave temporal plana. |
| **N6** | Proceso | Cifrado criptogrÃ¡fico mediante BCrypt. |
| **N7** | Proceso | Persistencia del cambio en Repositorio. |
| **N8** | Inicio Try | Apertura del bloque de notificaciÃ³n asÃ­ncrona. |
| **N9** | Proceso | Llamada al servicio de mensajerÃ­a (Email). |
| **N10** | Captura | Manejo de excepciÃ³n en el servicio de correo. |
| **N11** | Fin | Ã‰xito: Retorno de la clave temporal generada. |

### Paso 1: Grafo de Flujo (CFG - MIG Atomic)

```plantuml
@startuml
digraph CFG_PCB015 {
rankdir=TB
node [shape=circle]

I [label="Inicio\n[N1]"]
N2 [label="2"]
N3 [label="3\n[PCB-N1]"]
N4 [label="4"]
N5 [label="5"]
N6 [label="6"]
N7 [label="7"]
N8 [label="8\n[TRY]"]
N9 [label="9"]
N10 [label="10\n[CATCH]"]
N11 [label="11\n[FIN]"]
F [label="Fin"]

I -> N2
N2 -> N3
N3 -> N4 [label="True"]
N3 -> N5 [label="False"]
N5 -> N6
N6 -> N7
N7 -> N8
N8 -> N9
N9 -> N10 [label="Exception"]
N9 -> N11 [label="No Exception"]
N10 -> N11
N4 -> F
N11 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe `$V(G)$`

La métrica de complejidad se calcula mediante la fórmula formal de McCabe para grafos de flujo:

*   **V(G) = E - N + 2P**
*   **Donde:**
    *   **E (Aristas):** 14 (Conexiones entre nodos)
    *   **N (Nodos):** 13 (Puntos de control, incluye Inicio/Fin)
    *   **P (Componentes):** 1 (Unidad funcional única)
*   **Cálculo:** 14 - 13 + (2 * 1) = **3**

> [!NOTE]
> El resultado `$V(G) = 3$` coincide con la métrica simplificada de nodos predicado (`P + 1`), lo que valida la ruta crítica del grafo CFG bajo el estándar MIG v12.1.

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Fallo)** | I -> N2 -> N3(T) -> N4 -> F |
| **C2 (Ã‰xito)** | I -> N2 -> N3(F) -> N5 -> N6 -> N7 -> N8 -> N9 -> N11 -> F |
| **C3 (Bypass)** | I -> N2 -> N3(F) -> N5 -> N6 -> N7 -> N8 -> N9 -> N10 -> N11 -> F |

### Paso 4: Matriz de AutomatizaciÃ³n (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | Usuario Inexistente | `id = "F0E1D2C3-B4A5-0987-1234"` | `null` | Rama N3(T) -> N4 (Full Cover) |
| **C2** | **Reset Exitoso** | `id = "550e8400-e29b-41d4-a716-446655440000"` | `String` (Temp###!) | Rama N9 (Full Cover) |
| **C3** | Fallo Infraestructura | `id = "VALID-ID"`, `mailService: unreachable` | `String` (Bypass Email) | Rama N10 (Captura Forense) |

<br>

