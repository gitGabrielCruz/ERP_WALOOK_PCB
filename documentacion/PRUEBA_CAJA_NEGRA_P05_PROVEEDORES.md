# Prueba No. 05: Gestión de Proveedores y Órdenes de Compra

**Historia de Usuario:** HU-M05-01: Registrar proveedor / HU-M05-02: Consultar / HU-M05-03: Editar / HU-M05-04: Desactivar / HU-M05-05: Crear OC

---

### 1. Descripción de la Prueba
Validar el alta de proveedores, cumplimiento de RFC moral (12 caracteres) y la restricción operativa de nuevas OC según el estatus del proveedor.

### 2. Atributos y Reglas de Negocio
| Atributo | Casos de Prueba | Resultados de Salida | Estatus |
| :--- | :--- | :--- | :---: |
| **Integridad Registro** | P1 [RFC Moral (12)] <br> P2 [RFC Física (13)] <br> P3 [Razón Social Vacía] | Válido <br> Válido <br> Alerta de Obligatorio | **[ X ]** |
| **Validación Contactos** | P1 [Teléfono (10 dígitos)] <br> P2 [Email (rfc-822)] <br> P3 [Teléfono incompleto] | Formato OK <br> Formato OK <br> Rechazo de entrada | **[ X ]** |
| **Gobernanza Compras** | P1 [Crear OC: Prov. ACTIVO] <br> P2 [Crear OC: Prov. INACTIVO] <br> P3 [Cálculo IVA 16%] | Válido (Guardado) <br> Bloqueo (Acceso Denegado) <br> Match Cómputo OK | **[ X ]** |
| **Seguridad y Auditoría** | P1 [Admin: Edición Datos] <br> P2 [Vendedor: Solo Lectura] <br> P3 [Admin: Ver Bitácora] | UI Abierta <br> Lock UI <br> Lista de Logs de Compra | **[ X ]** |

### 3. Resultados Obtenidos
- **Escenarios validados:** 12 / 12 combinaciones.
- **Trazabilidad:** HU-M05, RNF-02, RNF-04.
- **Cumplimiento:** SI ( X ) NO ( )

---

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Formulario de Proveedor con validación de RFC Moral]
2. [Captura: Alerta de restricción de OC para proveedor Inactivo]
3. [Captura: Módulo de Auditoría Forense filtrado por el módulo de Proveedores]
