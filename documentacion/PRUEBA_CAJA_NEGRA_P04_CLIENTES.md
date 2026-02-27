# Prueba No. 04: Gestión de Catálogo de Clientes y Validación Fiscal

**Historia de Usuario:** HU-M06-01: Registrar cliente / HU-M06-02: Consultar / HU-M06-03: Editar / HU-M06-04: Desactivar / HU-M06-06: Datos fiscales / HU-M06-08: Unificar

---

### 1. Descripción de la Prueba
Validar el alta de clientes, cumplimiento de formatos RFC (SAT) y la integridad de datos en la unificación de duplicados.

### 2. Atributos y Reglas de Negocio
| Atributo | Casos de Prueba | Resultados de Salida | Estatus |
| :--- | :--- | :--- | :---: |
| **Identidad Fiscal** | P1 [RFC Física (13)] <br> P2 [RFC Moral (12)] <br> P3 [RFC Formato Inválido] | Válido (Guardado) <br> Válido (Guardado) <br> Bloqueo / Alerta SAT | **[ X ]** |
| **Contactos e Interfaz** | P1 [Teléfono (10 dígitos)] <br> P2 [Email (rfc-822)] <br> P3 [Teléfono incompleto] | Formato OK <br> Formato OK <br> Rechazo de entrada | **[ X ]** |
| **Ciclo de Vida** | P1 [Alta Exitoso] <br> P2 [Unificación Duplicados] <br> P3 [Baja Lógica] | Cliente Registrado <br> Merge de datos OK <br> Estatus Inactivo | **[ X ]** |
| **Gobernanza de Acceso** | P1 [Acceso Total Admin] <br> P2 [Modo Lectura Vendedor] <br> P3 [Salto de ACL] | Botones Activos <br> Lock UI <br> Redirección Login | **[ X ]** |

### 3. Resultados Obtenidos
- **Escenarios validados:** 12 / 12 combinaciones.
- **Trazabilidad:** HU-M06, RNF-02, RNF-04.
- **Cumplimiento:** SI ( X ) NO ( )

---

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Formulario de Cliente con alerta de RFC Moral inválido]
2. [Captura: Tabla de Clientes con indicadores de estatus (Activo/Inactivo)]
3. [Captura: Modal de Auditoría Forense para el módulo de Clientes]
