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
| Módulo Seguridad – Gestión de Credenciales |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-015 / Reset de Contraseña – UsuarioService.resetPassword() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    /**
     * UNIDAD BAJO AUDITORÍA: UsuarioService.resetPassword()
     * ESTÁNDAR: MIG v12.1 (Infraestructura de Excepciones)
     */
    public String resetPassword(String id) { // [N1: INICIO]
        // [PCB-N1] Búsqueda de Identidad Forense
        Usuario usuario = usuarioRepository.findById(id).orElse(null); // [N2: PROCESO]

        if (usuario == null) { // [N3] [PCB-N1] -> [SI: N4] [NO: N5]
            return null; // [N4: FIN / CONTROL DE NULIDAD]
        }

        // [PCB-N2] Orquestación de Credencial Temporal
        String newPass = generateTemporaryPassword(); // [N5]
        usuario.setPassword(encoder.encode(newPass)); // [N6: CIFRADO]
        usuarioRepository.save(usuario); // [N7: PERSISTENCIA]

        // [PCB-N3] Sistema de Notificación (Resiliencia ante Fallos de Infraestructura)
        try { // [N8: INICIO TRY]
            emailService.sendResetEmail(usuario.getCorreo(), newPass); // [N9]
        } catch (Exception e) { // [N10: CAPTURA EXCEPCIÓN]
            // [N10] Log: "Fallo envío email" (Continuidad del negocio asegurada)
        }

        return newPass; // [N11: FIN / ÉXITO OPERATIVO]
    }
```

---

### Auditoría de Evidencia Digital (JaCoCo)

**Ruta del Reporte Maestro:**
`d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\omcgc\backend\target\site\jacoco\index.html`

**Estructura de Navegación:**
`[index.html] -> [com.omcgc.erp.service] -> [UsuarioService]`

Glosario de Semántica de Cobertura (White Box Analysis — Análisis de Caja Blanca)
•	VERDE — Cobertura Total (Full Coverage)
•	AMARILLO — Cobertura Parcial (Partial Coverage)
•	ROJO — Cobertura Nula o Fuera de Alcance (No Coverage)

---

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **N1** | Inicio | Comienzo del método `resetPassword`. |
| **N2** | Proceso | Localización de usuario por su Identificador (UUID). |
| **N3 [PCB-N1]** | Predicado | ¿El usuario existe (`usuario != null`)? |
| **N4** | Fin | Término por usuario inexistente (Retorno controlado). |
| **N5** | Proceso | Generación de clave temporal plana. |
| **N6** | Proceso | Cifrado criptográfico mediante BCrypt. |
| **N7** | Proceso | Persistencia del cambio en Repositorio. |
| **N8** | Inicio Try | Apertura del bloque de notificación asíncrona. |
| **N9** | Proceso | Llamada al servicio de mensajería (Email). |
| **N10** | Captura | Manejo de excepción en el servicio de correo. |
| **N11** | Fin | Éxito: Retorno de la clave temporal generada. |

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

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Nodos Predicado + 1** = 2 + 1 = **3**
(Nota: Se contabiliza 1 predicado de decisión `if` y 1 punto de bifurcación `try/catch`).

### Paso 3: Caminos Independientes

| Camino | Ruta Forense |
| :--- | :--- |
| **C1 (Fallo)** | I -> N2 -> N3(T) -> N4 -> F |
| **C2 (Éxito)** | I -> N2 -> N3(F) -> N5 -> N6 -> N7 -> N8 -> N9 -> N11 -> F |
| **C3 (Bypass)** | I -> N2 -> N3(F) -> N5 -> N6 -> N7 -> N8 -> N9 -> N10 -> N11 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| ID / Camino | Escenario de Prueba | Entradas (Inputs) | Resultado Esperado (OUT) | Evidencia JaCoCo |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | Usuario Inexistente | `id = "F0E1D2C3-B4A5-0987-1234"` | `null` | Rama N3(T) -> N4 (Full Cover) |
| **C2** | **Reset Exitoso** | `id = "550e8400-e29b-41d4-a716-446655440000"` | `String` (Temp###!) | Rama N9 (Full Cover) |
| **C3** | Fallo Infraestructura | `id = "VALID-ID"`, `mailService: unreachable` | `String` (Bypass Email) | Rama N10 (Captura Forense) |

<br>
