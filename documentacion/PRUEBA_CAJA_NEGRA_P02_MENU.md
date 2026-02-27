# Prueba No. 02: Navegabilidad y Semáforo Dinámico

**Historia de Usuario:** HU1-M00-04: Navegación Estructural / HU1-M00-05: Semáforo Dinámico / HU1-M00-06: Monitoreo de Sesión

---

### 1. Descripción de la Prueba
Validar que la navegación entre módulos y la visualización del estado de construcción (Semáforo) se ejecuten correctamente.

### 2. Atributos y Reglas de Negocio
| Atributo | Casos de Prueba | Resultados de Salida | Estatus |
| :--- | :--- | :--- | :---: |
| **Identidad del Usuario** | P1 [Sesión Activa: admin@test.com] <br> P2 [Sin Sesión / sessionStorage vacío] | Renderizado OK <br> Redirección a Login.html | **[ X ]** |
| **Navegación y Permisos** | P1 [Módulo Autorizado: Usuarios] <br> P2 [Módulo Denegado: Inventarios] <br> P3 [Módulo en Construcción: Ventas] | Navega <br> Bloqueo Visual (ACL) <br> Aviso de construcción | **[ X ]** |
| **Semáforo Dinámico** | P1 [Archivo Físico Existe: clientes.html] <br> P2 [Archivo Físico NO Existe: agenda.html] | Borde Verde <br> Borde Rojo | **[ X ]** |
| **Control de Salida** | P1 [Clic Salir -> Confirmar] <br> P2 [Clic Salir -> Cancelar] | Logout OK <br> Se mantiene en el Menú | **[ X ]** |

### 3. Resultados Obtenidos
- **Escenarios validados:** 12 / 12 combinaciones.
- **Trazabilidad:** HU1-M00-04, HU1-M00-05, HU1-M00-06.
- **Cumplimiento:** SI ( X ) NO ( )

---

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Menú Principal con Identidad de Administrador]
2. [Captura: Alerta de Módulo en Construcción (Ventas)]
3. [Captura: Modal de Confirmación de Cierre de Sesión]
