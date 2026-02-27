# Prueba No. 01: Acceso al sistema (Login)

**Historia de Usuario:** HU1-M00-01: Iniciar sesión y autenticación / HU1-M00-02: Recuperación de credenciales / HU1-M00-03: Cierre de sesión seguro

---

### 1. Descripción de la Prueba
Validar que el proceso de autenticación del sistema se ejecute de manera correcta, utilizando datos reales de producción y mensajes literales del motor de seguridad.

### 2. Atributos y Reglas de Negocio
| Atributo | Casos de Prueba | Resultados de Salida | Estatus |
| :--- | :--- | :--- | :---: |
| **Correo Electrónico** | P1 [admin@test.com] <br> P2 [inexistente@walook.mx] <br> P3 [formato_invalido] <br> P4 [En blanco] | Válido <br> No Encontrado <br> Error Sintaxis <br> Requerido | **[ X ]** |
| **Contraseña** | P1 [admin] <br> P2 [pass_erronea_123] <br> P3 [En blanco] | Válida <br> Inválida <br> Requerida | **[ X ]** |
| **Estado de Cuenta** | P1 [Usuario: Activo] <br> P2 [Usuario: Inactivo] | Acceso concedido <br> Acceso denegado | **[ X ]** |
| **Disponibilidad** | P1 [Base de Datos: Online] <br> P2 [Base de Datos: Offline] <br> P3 [Sin Conexión de Red] | Normal <br> Error 500 <br> Excepción (Red) | **[ X ]** |
| **Cierre de Sesión** | P1 [Clic en Salir] <br> P2 [Intento volver atrás] | Redirección Login <br> Bloqueo de historial | **[ X ]** |

### 3. Resultados Obtenidos
- **Escenarios validados:** 14 / 14 combinaciones.
- **Trazabilidad:** HU1-M00-01, HU1-M00-02, HU1-M00-03.
- **Cumplimiento:** SI ( X ) NO ( )

---

**Adjuntar 2 imágenes de la interfaz del sistema:**
1. [Captura: Login Exitoso - Dashboard Administrador]
2. [Captura: Error - Usuario Inactivo]
