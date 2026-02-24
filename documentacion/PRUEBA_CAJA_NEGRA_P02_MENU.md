Desarrollo de Software

**Ejemplo:**

Sistema: **Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.**  
Fecha de aplicación: **22 de febrero de 2026**  
Encargado de realizar la prueba: **Gabriel Amilcar Cruz Canto**

---

| Prueba No. | 02 |
| :--- | :--- |
| **Historia de Usuario:** | HU1-M00-04: Navegación Estructural / HU1-M00-05: Semáforo Dinámico / HU1-M00-06: Monitoreo de Sesión |
| **Nombre de la prueba:** | Navegabilidad y Semáforo Dinámico |
| **Tipo de Prueba:** | Caja Negra (Funcional) – Valida Tablero de Control |
| **Descripción:** | Validar la integridad de la navegación, la visualización de identidad del usuario y la respuesta dinámica del semáforo de construcción de módulos. |

| Valores de entrada | Valores de salida | Resultado: | Navegación y Seguridad Visual. | | |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | **Correcto** | El menú adapta permisos y semáforos. | **Incorrecto** | El sistema deniega o bloquea acceso. |
| **Atributo: Identidad del Usuario:**<br>P1 [Sesión Activa: admin@test.com]<br>P2 [Sin Sesión / sessionStorage vacío] | Renderizado OK<br>Redirección | [X]<br>[X] | **Nombre y Rol visibles en Header.** (HU1-M00-04 / RNF-04) | [ ]<br>[ ] | **El sistema redirige a login.html (Bloqueo RNF-04).** |
| **Atributo: Navegación y Permisos:**<br>P1 [Módulo Autorizado: Usuarios]<br>P2 [Módulo Denegado: Inventarios]<br>P3 [Módulo en Construcción: Ventas] | Navega<br>Bloqueo Visual<br>Aviso | [X]<br>[ ]<br>[ ] | **Navegación exitosa a usuarios.html.** (HU1-M00-04 / RNF-04) | [ ]<br>[X]<br>[X] | **"Acceso Denegado. No tiene permisos..."** (HU1-M00-04)<br>**"Módulo en Construcción"** (Información) |
| **Atributo: Semáforo Dinámico:**<br>P1 [Archivo Físico Existe: clientes.html]<br>P2 [Archivo Físico NO Existe: agenda.html] | Borde Verde<br>Borde Rojo | [X]<br>[X] | **Indicadores visuales reaccionan a la existencia real.** (HU1-M00-05) | [ ]<br>[ ] | **Módulos pendientes muestran alerta visual roja.** |
| **Atributo: Control de Salida:**<br>P1 [Clic Salir -> Confirmar]<br>P2 [Clic Salir -> Cancelar] | Logout OK<br>Cancela | [X]<br>[X] | **El sistema cierra sesión y regresa al Login.** (HU1-M00-06 / HU1-M00-03) | [ ]<br>[ ] | **El sistema se mantiene en el Menú sin cambios.** |

---

| **La prueba se realizó y cumple con todos los escenarios descritos:** | **SI ( X ) NO ( )** | **Observaciones:** |
| :--- | :---: | :--- |
| | | 12 / 12 combinaciones de prueba validadas contra la lógica de menu-service.js. |

**Adjuntar 3 imágenes de la interfaz del sistema:**
1. [Captura: Menú Principal con Identidad de Administrador]
2. [Captura: Alerta de Módulo en Construcción (Ventas)]
3. [Captura: Modal de Confirmación de Cierre de Sesión]

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
