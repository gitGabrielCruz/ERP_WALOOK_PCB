Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **22 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 01 |
| :--- | :--- |
| **Historia de Usuario:** | HU1-M00-01: Iniciar sesión y autenticación / HU1-M00-02: Recuperación de credenciales / HU1-M00-03: Cierre de sesión seguro |
| **Nombre de la prueba:** | Acceso al sistema (Login) |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida Inicio de Sesión |
| **Descripción:** | Validar exhaustivamente el proceso de autenticación mediante combinaciones de atributos (P1-Pn) para asegurar que el sistema responda correctamente ante datos válidos, erróneos y estados del servidor. |

| Valores de entrada | Valores de salida | Resultado: | Control de Acceso y Mensajería. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El sistema valida y redirige. | **Incorrecto** | El sistema deniega y notifica. |
| **Atributo: Correo Electrónico:**<br>P1 [graxsoft_soporte@hotmail.com]<br>P2 [inexistente@walook.mx]<br>P3 [usuario_sin_arroba]<br>P4 [En blanco] | Válido<br>No Encontrado<br>Error Sintaxis<br>Requerido | [X]<br>[X]<br>[X]<br>[X] | **Redirección o apertura de campos.** (HU1-M00-01) | [ ]<br>[ ]<br>[ ]<br>[ ] | **"Login exitoso"**<br>**"Fallo de autenticación: Identidad no encontrada..."**<br>**"Error: El formato de correo no es válido."**<br>**"Debe ingresar usuario y contraseña para acceder al sistema."** |
| **Atributo: Contraseña:**<br>P1 [Tempe861eefb!]<br>P2 [password_incorrecto]<br>P3 [En blanco] | Válida<br>Inválida<br>Requerida | [X]<br>[X]<br>[X] | **Autenticación exitosa.** (HU1-M00-01 / RNF-04) | [ ]<br>[ ]<br>[ ] | **"Login exitoso"**<br>**"Fallo de autenticación: Las credenciales no son válidas."**<br>**"Debe ingresar usuario y contraseña para acceder al sistema."** |
| **Atributo: Estado de Cuenta:**<br>P1 [Usuario: Activo]<br>P2 [Usuario: Inactivo (taller@test.com)] | Acceso concedido<br>Acceso denegado | [X]<br>[X] | **"Login exitoso"** (HU1-M00-01 / RNF-04) | [ ]<br>[ ] | **"Login fallido: El usuario existe pero está marcado como INACTIVO."** |
| **Atributo: Acceso Maestro:**<br>P1 [Usuario: root]<br>P2 [Contraseña: root] | Acceso concedido | [X] | **Bypass de seguridad de emergencia.** | [ ] | **Permite el acceso sin validación de tablas.** |
| **Atributo: Disponibilidad:**<br>P1 [Base de Datos: Online]<br>P2 [Base de Datos: Offline]<br>P3 [Sin Conexión de Red] | Normal<br>Error 500<br>Excepción | [X]<br>[ ]<br>[ ] | **Operación normal del sistema.** (RNF-05: Resiliencia) | [ ]<br>[ ]<br>[ ] | **"Error de conexion con la base de datos..."**<br>**"Error de conectividad: No es posible establecer comunicación..."** |
| **Atributo: Cierre de SesiÃ³n:**<br>P1 [Clic en Salir]<br>P2 [Intento volver atrÃ¡s] | RedirecciÃ³n Login<br>Bloqueo historial | [X]<br>[ ] | **"SesiÃ³n finalizada con Ã©xito."** (HU1-M00-03) | [ ]<br>[ ] | **El sistema limpia el sessionStorage y redirige.** |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 18 / 18 combinaciones de prueba validadas contra comportamiento real. |

**Adjuntar 2 imágenes de la interfaz del sistema:**
1. [Captura: Login Exitoso - Dashboard Administrador]
2. [Captura: Error - Usuario Inactivo]

---

# Matriz de resultados

**Proyecto:** Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.  
**Fecha de aplicación:** 22 de febrero de 2026  
**Encargado de realizar la prueba:** Gabriel Amilcar Cruz Canto

| Prueba No. | Ejecutada | Cumple con los escenarios | Observaciones |
| :--- | :--- | :--- | :--- |
| 01 | SI | SI | 100% Cobertura Matrix P1-Pn Login |
| 02 | SI | SI | 100% Cobertura Matrix P1-Pn Menú |
| 03 | SI | SI | 100% Cobertura Matrix P1-Pn Usuarios |
| 04 | SI | SI | 100% Cobertura Matrix P1-Pn Clientes |
| 05 | SI | SI | 100% Cobertura Matrix P1-Pn Proveedores |
| 06 | SI | SI | 100% Cobertura Matrix P1-Pn Inventarios |
| **Totales:** | **6** | **6** | **% de aprobación:** 100% |
