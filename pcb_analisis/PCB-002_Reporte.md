# TEST PRUEBAS DE CAJA BLANCA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel Amílcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestión de ópticas OMCGC |
| **URL y Claves de acceso:** | [Configurar en ambiente de entrega] |

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

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Seguridad / Acceso – HU-M01-03 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-002 / Gestión de Permisos – UsuarioService.getPermissionsByUsuario() |

### Paso 0

```java
    /**
     * ESPECIFICACIÓN TÉCNICA: Protocolo de Recuperación y Fallback de Privilegios Granulares.
     * OBJETIVO OPERATIVO: Proveer matriz de control de acceso (específicos o heredados).
     * IMPACTO: Determinación de la visibilidad de módulos en el Dashboard.
     */
    public List<java.util.Map<String, Object>> getPermissionsByUsuario(String idUsuario) { // [N1: INICIO]
        
        // 1. Buscar permisos específicos (Personalización)
        List<java.util.Map<String, Object>> permisos = usuarioRepository.findPermissionsByUsuario(idUsuario); // [N2: PROCESO]

        // [PCB-N1] evaluación de personalización (Detección de permisos específicos)
        if (permisos.isEmpty()) { // [N3] [PCB-N1] -> [SI: N4] [NO: N7] : ¿Lista de permisos vacía?
            Usuario u = findById(idUsuario); // [N4: PROCESO]
            
            // [PCB-N2] validación de integridad de cuenta (Existencia y Rol asignado)
            if (u != null && u.getRolId() != null) { // [N5] [PCB-N2] -> [SI: N6] [NO: N7] : ¿Tiene Rol asignado?
                return usuarioRepository.findPermissionsByRol(u.getRolId()); // [N6: FIN] -> Retorno de herencia
            }
        }
        return permisos; // [N7: FIN] -> Retorno de lista actual
    }
```

### Descripción breve del fragmento

El fragmento **PCB-002** implementa el motor de resolución de privilegios del ERP. Su diseño arquitectónico permite una personalización granular de permisos por usuario, con un mecanismo de *fallback* automático hacia los permisos definidos por el Rol en caso de ausencia de configuración específica. Con una complejidad $V(G)=3$, la prueba garantiza que la seguridad sea inmutable y jerárquica.

### Identificación de Nodos

| ID del Nodo | Tipo | Descripción |
| :--- | :--- | :--- |
| **Nodo 1** | Inicio | Inicio del método `getPermissionsByUsuario(String idUsuario)` y recepción del identificador de entrada. |
| **Nodo 2** | Nodo de proceso | Ejecución de `usuarioRepository.findPermissionsByUsuario(idUsuario)`. Consulta de privilegios personalizados en el repositorio. |
| **Nodo 3 [PCB-N1]** | Nodo predicado | Evaluación de la condición `if (permisos.isEmpty())`. Detección de ausencia de personalización. Identificado con la etiqueta **PCB-N1**. |
| **Nodo 4** | Nodo de proceso | Ejecución de `findById(idUsuario)` para localizar la entidad de usuario en la capa de persistencia. |
| **Nodo 5 [PCB-N2]** | Nodo predicado | Evaluación de la condición `if (u != null && u.getRolId() != null)`. Validación de integridad de la cuenta y rol asignado. Identificado con la etiqueta **PCB-N2**. |
| **Nodo 6** | Nodo de salida | Ejecución de `return usuarioRepository.findPermissionsByRol()`. Retorno de la matriz de permisos heredados del Rol. |
| **Nodo 7** | Nodo de salida | Ejecución de `return permisos`. Finalización del flujo con retorno de lista personalizada o vacía según descarte. |

### Paso 1

```plantuml
@startuml
digraph CFG_PCB002 {

rankdir=TB
node [shape=circle]

I [label="Inicio"]
N1 [label="1"]
N2 [label="2"]
N3 [label="3\nPCB-N1"]
N4 [label="4"]
N5 [label="5\nPCB-N2"]
N6 [label="6"]
N7 [label="7"]
F [label="Fin"]

I -> N1
N1 -> N2
N2 -> N3
N3 -> N4 [label="Verdadero"]
N3 -> N7 [label="Falso"]
N4 -> N5
N5 -> N6 [label="Verdadero"]
N5 -> N7 [label="Falso"]
N6 -> F
N7 -> F

}
@enduml
```

### Paso 2

**V(G) = Número de regiones** = (2 internas + 1 externa) = **3**
**V(G) = Aristas – Nodos + 2** = V(G) = 10 – 9 + 2 = **3**
**V(G) = Nodos Predicado + 1** = V(G) = 2 + 1 = **3**

### Paso 3

| Total de caminos | Ruta de cada camino |
| :--- | :--- |
| **Camino 1** | Inicio → 1 → 2 → 3(NO) → 7 → Fin |
| **Camino 2** | Inicio → 1 → 2 → 3(SÍ) → 4 → 5(NO) → 7 → Fin |
| **Camino 3** | Inicio → 1 → 2 → 3(SÍ) → 4 → 5(SÍ) → 6 → Fin |

### Paso 4

| Número del camino | Caso de Prueba (IN) | Resultado esperado (OUT) |
| :--- | :--- | :--- |
| **Camino 1** | idUsuario="USR-001", permisos.isEmpty() = false | return permisos (Lista personalizada) |
| **Camino 2** | idUsuario="USR-999", permisos.isEmpty() = true, u = null | return permisos (Lista vacía / Fallo existencia) |
| **Camino 3** | idUsuario="USR-002", permisos.isEmpty() = true, u != null, u.getRolId() != null | return usuarioRepository.findPermissionsByRol() (Herencia) |
