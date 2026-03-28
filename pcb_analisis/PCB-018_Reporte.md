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
| :--- | :--- | :--- | :--- | :--- |
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validación de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Persistencia de Producto | Cálculo dinámico de PVP e Identidad | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Inventario / Financiero – RF-15 |

| **Número y nombre de la Prueba** |
| :--- |
| ### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    public void saveProduct(Producto p, String ip) { // [N1: INICIO]
        boolean isNew = (p.getIdProducto() == null || p.getIdProducto().isEmpty()); // [N2: PCB-N1]

        if (isNew) { // [N3]
            p.setIdProducto(java.util.UUID.randomUUID().toString());
        }

        if (p.getSku() == null || p.getSku().isEmpty() || p.getSku().equalsIgnoreCase("Autogenerado")) { 
            // [N4: PCB-N2] [N5: PCB-N3]
            String timestamp = String.valueOf(System.currentTimeMillis());
            p.setSku("75" + timestamp.substring(timestamp.length() - 8)); // [N6]
        }

        if (p.getCostoUnitario() != null && p.getPorcentajeUtilidad() != null) { // [N7: PCB-N4] [N8: PCB-N5]
            BigDecimal factor = BigDecimal.ONE.add(p.getPorcentajeUtilidad().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
            p.setPrecioVenta(p.getCostoUnitario().multiply(factor).setScale(2, RoundingMode.HALF_UP)); // [N9]
        }

        inventarioRepository.save(p); // [N10]
        bitacoraService.registrarEvento(p.getIdUsuarioOperacion(), isNew ? "PRO-01" : "PRO-02", ip, p.getSku(), p.getNombre());
    } // [F: FIN]
```

### Paso 1: Grafo de Control de Flujo (CFG)

```dot
@startuml
digraph G {
rankdir=TB;
node [shape=circle, fixedsize=true, width=0.5];
I [label="Inicio", shape=ellipse];
F [label="Fin", shape=ellipse];
N1 [label="1"]
N2 [label="2\nPCB-N1"]
N3 [label="3"]
N4 [label="4\nPCB-N2"]
N5 [label="5\nPCB-N3"]
N6 [label="6"]
N7 [label="7\nPCB-N4"]
N8 [label="8\nPCB-N5"]
N9 [label="9"]
N10 [label="10"]

I -> N1
N1 -> N2
N2 -> N3 [label="Verdadero"]
N2 -> N4 [label="Falso"]
N3 -> N4
N4 -> N6 [label="Verdadero"]
N4 -> N5 [label="Falso"]
N5 -> N6 [label="Verdadero"]
N5 -> N7 [label="Falso"]
N6 -> N7
N7 -> N8 [label="Verdadero"]
N7 -> N10 [label="Falso"]
N8 -> N9 [label="Verdadero"]
N8 -> N10 [label="Falso"]
N9 -> N10
N10 -> F
}
@enduml
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (5 internas + 1 externa) = **6**
*   **V(G) = Aristas – Nodos + 2** = (18 – 14 + 2) = **6**
*   **V(G) = Nodos Predicado + 1** = (5 + 1) = **6**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N1 -> N2(T) -> N3 -> N4(F) -> N7 -> F (ID Nuevo, SKU Manual) |
| **C2** | I -> N1 -> N2(F) -> N4(T) -> N5 -> N6 -> N7 -> F (ID Existe, SKU Generado) |
| **C3** | I -> N1 -> N2(F) -> N4(F) -> N7 -> N8 -> N9(T) (PVP Calculado) |
| **C4** | I -> N1 -> N2(F) -> N4(F) -> N7 -> N8 -> N9(F) (PVP Omitido - Nulos) |
| **C5** | I -> N1 -> N2(T) -> N3 -> N4(T) -> N5 -> N6 -> N7 -> F (ID Nuevo, SKU Generado) |
| **C6** | I -> N1 -> N2(F) -> N4(F) -> N7 -> N10 -> F (Éxito Nominal) |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Producto Nuevo (ID null) | UUID Generado |
| **C2** | SKU "Autogenerado" | SKU 75... Generado |
| **C3** | Costo y Utilidad válidos | PVP Calculado (BigDecimal) |
| **C4** | Costo o Utilidad nulos | PVP no se calcula |
| **C5** | Alta con SKU omitido | UUID y SKU Generados |
| **C6** | **Éxito (Registro)** | **SUCCESS (Persistido)** |
