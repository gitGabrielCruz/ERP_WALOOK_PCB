# Protocolo de Testing de Caja Blanca (PCB) - Proyecto ERP_WALOOK_PCB

## 1. Alcance Definido
No se analizará todo el backend. Solo fragmentos específicos de los módulos definidos.

## 2. Catálogo Maestro de Pruebas (15 Fragmentos)

| ID | Módulo | Fragmento |
| :--- | :--- | :--- |
| **Menú** | | |
| PCB-001 | Menú | Carga del menú principal |
| PCB-002 | Menú | Validación de módulos asignados al usuario |
| PCB-003 | Menú | Acceso a módulo desde botón de menú |
| **Inventarios** | | |
| PCB-004 | Inventarios | Registrar producto |
| PCB-005 | Inventarios | Actualizar producto |
| PCB-006 | Inventarios | Eliminar producto |
| PCB-007 | Inventarios | Validación de stock mínimo |
| **Clientes** | | |
| PCB-008 | Clientes | Registrar cliente |
| PCB-009 | Clientes | Buscar cliente |
| PCB-010 | Clientes | Actualizar cliente |
| **Proveedores** | | |
| PCB-011 | Proveedores | Registrar proveedor |
| PCB-012 | Proveedores | Buscar proveedor |
| PCB-013 | Proveedores | Actualizar proveedor |
| **Usuarios** | | |
| PCB-014 | Usuarios | Validación de rol de usuario |
| PCB-015 | Usuarios | Validación de usuario activo/inactivo |

## 3. Reglas de Alcance (Pipeline)
- Cada prueba trabaja únicamente sobre **un módulo**, **un fragmento concreto** y **una función/bloque lógico delimitado**.
- **PROHIBIDO**: Analizar archivos completos, mezclar fragmentos, análisis visual de frontend o HTML puro.

## 4. Estructura de Salida (Archivo PCB)
Cada fragmento generará un análisis con:
- **A. Identificación**: ID, Módulo, Fragmento, HU, Función.
- **B. Tabla de Nodos**
- **C. Tabla de Aristas**
- **D. Complejidad Ciclomática**: $V(G) = E - N + 2$ o $V(G) = P + 1$.
- **E. Caminos Independientes**
- **F. Casos de Prueba (IN/OUT)**
- **G. Seudocódigo Estructural**
- **H. Código PlantUML (Grafo)**
- **I. Resumen Académico**

## 5. Método de Análisis
Se utilizará estrictamente el método **Basis Path Testing de McCabe**.

## 6. Comandos de Ejecución Obligatorios
La ejecución se realizará estrictamente por etapas tras la validación del Ingeniero:
- **Etapa 1 (Preparación)**: Requiere la orden `/CODIFICAR ETAPA 1`.
- **Etapa 2 (Análisis y McCabe)**: Requiere la orden `/CODIFICAR ETAPA 2`.
