# TEST PRUEBAS DE CAJA BLANCA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel Amílcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestión de ópticas OMCGC |
| **URL y Claves de acceso:** | [Configurar en ambiente de entrega] |

<br>

| **PLAN DE PRUEBAS DE CAJA BLANCA: BACKEND** | | | | |
| :--- | :--- | :--- | :--- | :--- |
| **Número** | **Nombre de la Prueba Backend** | **Descripción** | **Fecha** | **Responsable** |
| PCB-008 | Integridad Fiscal | Validación de Identidad Tributaria y Unicidad de RFC | 17/03/2026 | Gabriel Amílcar Cruz Canto |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Clientes / Pacientes – HU-M06-01 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-008 / Integridad Fiscal – ClienteService.guardarCliente() |

### Paso 0

```java
    /**
     * ESPECIFICACIÓN TÉCNICA: Validación de Integridad Fiscal y Unicidad de Identidad Tributaria.
     * OBJETIVO OPERATIVO: Asegurar calidad del dato fiscal y exclusión de duplicados.
     * IMPACTO: Soporte íntegro para procesos de facturación electrónica.
     */
    public Paciente guardarCliente(Paciente cliente) { // [N1: INICIO]
        
        // [PCB-N1] validación de obligatoriedad (Nombre)
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) { // [N2] [PCB-N1] -> [SI: N3] [NO: N4] : ¿Nombre ausente?
            throw new IllegalArgumentException("Nombre obligatorio"); // [N3: FIN (EXC)]
        }

        // [PCB-N2] evaluación de presencia de RFC
        if (cliente.getRfc() != null && !cliente.getRfc().isEmpty()) { // [N4] [PCB-N2] -> [SI: N5] [NO: N9] : ¿Proporcionó RFC?
            Paciente existente = pacienteRepository.findByRfc(cliente.getRfc()); // [N5: PROCESO]
            
            // [PCB-N3] validación de colisión de RFC (Unicidad)
            // [N6: PREDICADO] [PCB-N3] -> [SI: N8] [NO: N7] : ¿RFC detectado en otro registro?
            if (existente != null && !existente.getIdPaciente().equals(cliente.getIdPaciente())) {
                throw new IllegalArgumentException("RFC duplicado"); // [N8: FIN (EXC)]
            }
        }
        
        pacienteRepository.save(cliente); // [N7: PROCESO] -> Persistir transacción
        return cliente; // [N8: FIN]
    }
```

### Descripción breve del fragmento

El fragmento **PCB-008** implementa las reglas de negocio para el empadronamiento de pacientes. Su función es garantizar la integridad fiscal mediante la validación obligatoria del nombre y la verificación de unicidad del RFC para evitar registros redundantes que afecten la facturación. Con una complejidad $V(G)=3$, el código asegura que la base de datos de clientes mantenga un estándar de calidad apto para procesos contables.

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **Nodo 1** | Inicio | Inicio de la función `guardarCliente(Paciente cliente)` y recepción de la entidad del paciente. |
| **Nodo 2 [PCB-N1]** | Nodo predicado | Evaluación de la condición `cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()`. Verificación de metadatos de identidad obligatorios. Identificado con la etiqueta **PCB-N1**. |
| **Nodo 3** | Nodo de salida | Lanzamiento de `IllegalArgumentException("Nombre obligatorio")`. Interrupción por ausencia de metadatos de identidad primarios. |
| **Nodo 4 [PCB-N2]** | Nodo predicado | Evaluación de la condición `cliente.getRfc() != null && !cliente.getRfc().isEmpty()`. Determinación de necesidad de validación fiscal. Identificado con la etiqueta **PCB-N2**. |
| **Nodo 5** | Nodo de proceso | Ejecución de `pacienteRepository.findByRfc(cliente.getRfc())`. Consulta de preexistencia fiscal en el repositorio. |
| **Nodo 6 [PCB-N3]** | Nodo predicado | Evaluación de la condición `existente != null && !existente.getIdPaciente().equals()`. Verificación de colisión tributaria. Identificado con la etiqueta **PCB-N3**. |
| **Nodo 7** | Nodo de proceso | Ejecución de `pacienteRepository.save(cliente)`. Persistencia atómica de la transacción de padrón de pacientes. |
| **Nodo 8** | Fin | Finalización del protocolo de validación de integridad fiscal y registro de identidad tributaria. |

### Paso 1

```plantuml
@startuml
digraph CFG_PCB008 {

rankdir=TB
node [shape=circle]

I [label="Inicio"]
N1 [label="1"]
N2 [label="2\n[PCB-N1]"]
N3 [label="3"]
N4 [label="4\n[PCB-N2]"]
N5 [label="5"]
N6 [label="6\n[PCB-N3]"]
N7 [label="7"]
N8 [label="8"]
F [label="Fin"]

I -> N1
N1 -> N2
N2 -> N3 [label="Verdadero"]
N2 -> N4 [label="Falso"]
N4 -> N7 [label="Falso"]
N4 -> N5 [label="Verdadero"]
N5 -> N6
N6 -> N8 [label="Verdadero"]
N6 -> N7 [label="Falso"]
N7 -> F
N3 -> F
N8 -> F

}
@enduml
```

### Paso 2

**V(G) = Número de regiones** = (3 internas + 1 externa) = **4**
**V(G) = Aristas – Nodos + 2** = V(G) = 14 – 12 + 2 = **4**
**V(G) = Nodos Predicado + 1** = V(G) = 3 + 1 = **4**

### Paso 3

| Total de caminos | Ruta de cada camino |
| :--- | :--- |
| **Camino 1** | Inicio → 1 → 2(SÍ) → 3 → Fin |
| **Camino 2** | Inicio → 1 → 2(NO) → 4(NO) → 7 → 8 → Fin |
| **Camino 3** | Inicio → 1 → 2(NO) → 4(SÍ) → 5 → 6(SÍ) → 8 → Fin |
| **Camino 4** | Inicio → 1 → 2(NO) → 4(SÍ) → 5 → 6(NO) → 7 → 8 → Fin |

### Paso 4

| Número del camino | Caso de Prueba (IN) | Resultado esperado (OUT) |
| :--- | :--- | :--- |
| **Camino 1** | cliente.nombre = null | IllegalArgumentException: Nombre obligatorio (PCB-N1: SI) |
| **Camino 2** | cliente.nombre = "Juan Perez", cliente.rfc = null | Registro exitoso (PCB-N1: NO, PCB-N2: NO) |
| **Camino 3** | cliente.nombre = "Juan Perez", cliente.rfc = "XAXX010101000" (Existente) | IllegalArgumentException: RFC duplicado (PCB-N1: NO, PCB-N2: SI, PCB-N3: SI) |
| **Camino 4** | cliente.nombre = "Juan Perez", cliente.rfc = "XAXX010101000" (Nuevo) | Registro exitoso tras validación (PCB-N1: NO, PCB-N2: SI, PCB-N3: NO) |
