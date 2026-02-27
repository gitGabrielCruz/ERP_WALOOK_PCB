# Prueba No. 03: Gestión de Usuarios y Matriz de Permisos

**Historia de Usuario:** HU1-M00-07: Registro CRUD / HU1-M00-08: Roles (RBAC) / HU1-M00-09: Matriz Granular / HU1-M00-10: Auditoría Forense

---

### 1. Descripción de la Prueba
Validar el ciclo de vida del usuario, la consistencia de la matriz de permisos y el rastro de auditoría generado por el sistema.

### 2. Atributos y Reglas de Negocio
| Atributo | Casos de Prueba | Resultados de Salida | Estatus |
| :--- | :--- | :--- | :---: |
| **Gestión de Cuentas** | P1 [Alta OK] <br> P2 [Email ya registrado] <br> P3 [Campos vacíos] | Usuario creado (Clave temporal) <br> Bloqueo (Duplicado) <br> Alerta de obligatorios | **[ X ]** |
| **Matriz de Permisos** | P1 [Asignación de Rol] <br> P2 [Edición ACL Granular] <br> P3 [Intento sin permisos] | Permisos aplicados <br> Configuración guardada <br> UI Bloqueada/Lectura | **[ X ]** |
| **Estatus y Gobernanza** | P1 [Desactivar Usuario] <br> P2 [Baja sin permiso] <br> P3 [Baja último Admin] | Baja Lógica (Inactivo) <br> Acceso Denegado <br> Operación no permitida | **[ X ]** |
| **Auditoría Forense** | P1 [Consulta Logs del Día] <br> P2 [Filtro por ID Usuario] <br> P3 [Texto Cifrado] | Lista de Logs <br> Filtrado OK <br> Rastro persistido | **[ X ]** |

### 3. Resultados Obtenidos
- **Escenarios validados:** 12 / 12 combinaciones.
- **Trazabilidad:** HU1-M00-07 al 10, RNF-04, RNF-08.
- **Cumplimiento:** SI ( X ) NO ( )

---

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Formulario de Alta con Contraseña Temporal Generada]
2. [Captura: Matriz de Permisos (ACL) configurada para Rol Operativo]
3. [Captura: Módulo de Bitácora filtrado por acciones del Administrador]
