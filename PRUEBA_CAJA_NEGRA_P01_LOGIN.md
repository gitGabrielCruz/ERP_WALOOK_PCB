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
| **Atributo: Correo Electrónico:**<br>P1 [admin@test.com]<br>P2 [inexistente@walook.mx]<br>P3 [formato_invalido]<br>P4 [En blanco] | Válido<br>No Encontrado<br>Error Sintaxis<br>Requerido | [X]<br>[ ]<br>[ ]<br>[ ] | **Redirección o apertura de campos.** (HU1-M00-01) | [ ]<br>[X]<br>[X]<br>[X] | **"Fallo de autenticación: Identidad no encontrada..."**<br>**"Error: El formato de correo no es válido."**<br>**"Debe ingresar un correo electrónico."** |
| **Atributo: Contraseña:**<br>P1 [admin]<br>P2 [pass_erronea_123]<br>P3 [En blanco] | Válida<br>Inválida<br>Requerida | [X]<br>[ ]<br>[ ] | **Autenticación exitosa.** (HU1-M00-01 / RNF-04) | [ ]<br>[X]<br>[X] | **"Fallo de autenticación: Las credenciales no son válidas."**<br>**"Debe ingresar una contraseña."** |
| **Atributo: Estado de Cuenta:**<br>P1 [Usuario: Activo]<br>P2 [Usuario: Inactivo] | Acceso concedido<br>Acceso denegado | [X]<br>[ ] | **"Login exitoso"** (HU1-M00-01 / RNF-04) | [ ]<br>[X] | **"Login fallido: El usuario existe pero está marcado como INACTIVO."** |
| **Atributo: Disponibilidad:**<br>P1 [Base de Datos: Online]<br>P2 [Base de Datos: Offline]<br>P3 [Sin Conexión de Red] | Normal<br>Error 500<br>Excepción | [X]<br>[ ]<br>[ ] | **Operación normal del sistema.** (RNF-05: Resiliencia) | [ ]<br>[X]<br>[X] | **"Error de conexion con la base de datos..."**<br>**"Error de conectividad: No es posible establecer comunicación..."** |
| **Atributo: Cierre de Sesión:**<br>P1 [Clic en Salir]<br>P2 [Intento volver atrás] | Redirección Login<br>Bloqueo historial | [X]<br>[ ] | **"Sesion finalizada con éxito."** (HU1-M00-03) | [ ]<br>[X] | **El sistema limpia el sessionStorage y redirige.** |

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
| 02 | SI | SI | 100% Cobertura Menú/Semáforo |
| 03 | SI | SI | 100% Cobertura Usuarios/Permisos |
| 04 | SI | SI | 100% Cobertura Clientes/Fiscal |
| 05 | SI | SI | 100% Cobertura Proveedores/OC |
| 06 | SI | SI | 100% Cobertura Inventarios/Kardex |
| **Totales:** | **6** | **6** | **% de aprobación:** 100% |
