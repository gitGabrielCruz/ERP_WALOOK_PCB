# Prueba No. 06: Catálogo Maestro y Control de Existencias (Kardex)

**Historia de Usuario:** HU-M01-01: Registrar producto / HU-M01-02: Consultar producto / HU-M01-05: Movimientos (Kardex) / HU-M01-07: Alertas Stock / HU-M01-09: Datos Fiscales

---

### 1. Descripción de la Prueba
Validar el alta de productos con SKU autogenerado, la integridad fiscal SAT, el cálculo automático de PVP y el motor dinámico de existencias (Kardex).

### 2. Atributos y Reglas de Negocio
| Atributo | Casos de Prueba | Resultados de Salida | Estatus |
| :--- | :--- | :--- | :---: |
| **Identidad y SAT** | P1 [SKU Prefijo 75] <br> P2 [Datos SAT Óptica] <br> P3 [Campos Vacíos] | SKU OK <br> Fiscal OK <br> Alerta de campo obligatorio | **[ X ]** |
| **Finanzas y PVP** | P1 [Cálculo PVP: Costo + Ut] <br> P2 [Costo Negativo: Bloqueo] <br> P3 [Iva Aplicable 16%] | PVP OK <br> Bloqueo (Error UI) <br> Carga OK | **[ X ]** |
| **Gobernanza Kardex** | P1 [Entrada/Salida: Alta] <br> P2 [Existencia Negativa: Bloqueo] <br> P3 [Rollback Póliza] | Saldo OK <br> Bloqueo (Stock Insuficiente) <br> Rollback OK | **[ X ]** |
| **Alertas RBAC** | P1 [Stock < Mínimo: Punto Rojo] <br> P2 [Vendedor: Solo Lectura] <br> P3 [Admin: Auditoría] | Semáforo OK <br> UI Bloqueada <br> Lista de Logs de Inventario | **[ X ]** |

### 3. Resultados Obtenidos
- **Escenarios validados:** 12 / 12 combinaciones.
- **Trazabilidad:** HU-M01, RNF-02, RNF-04 vinculadas.
- **Cumplimiento:** SI ( X ) NO ( )

---

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Ficha de producto con Datos SAT Óptica e indicador de SKU Prefijo 75]
2. [Captura: Alerta de "Inconsistencia de Costo" (Bloqueo por Costo Negativo) (P2)]
3. [Captura: Alerta de "Stock insuficiente" (Bloqueo por Existencia Negativa) (P2)]
