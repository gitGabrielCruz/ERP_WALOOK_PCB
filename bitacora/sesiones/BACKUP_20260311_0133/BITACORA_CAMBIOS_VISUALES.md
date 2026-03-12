---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** Bitácora de Cambios Visuales y UI  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 🎨 BITÁCORA DE CAMBIOS VISUALES Y UI
Funcionales
Fecha: 2026-02-14

## Módulo Clientes
### Frontend (`clientes.html`, `clientes-service.js`)
- **Tabla de Directorio**:
    - Se agregaron íconos distintivos para Persona Física (Usuario Azul) y Persona Moral (Edificio Morado).
    - Se ajustó el tamaño de los íconos a 16px con alineación vertical centrada usando estilos inline para garantizar consistencia.
- **Formulario de Detalle**:
    - Se implementó lógica dinámica para mostrar/ocultar campos según "Tipo Persona".
    - **Persona Física**: Solicita "Nombre(s)" y "Apellidos" por separado. Valida RFC de 13 caracteres.
    - **Persona Moral**: Solicita "Razón Social" (oculta Apellidos). Valida RFC de 12 caracteres.
    - Se movió el selector "Tipo Persona" al inicio del formulario como campo disparador.
- **Validaciones**:
    - Se reforzaron las validaciones de RFC (regex y longitud exacta).
    - Se implementó `MessageService` para notificaciones de éxito y error estandarizadas.

## Módulo Proveedores
### Backend (`ProveedorController.java`)
- Se estandarizó la respuesta de errores (HTTP 500):
    - Ahora devuelve un JSON con la clave `error` en lugar de `message` para consistencia con el frontend.
    - Se agregó `e.printStackTrace()` para facilitar el debug en el log del servidor.
- Se corrigió un error de compilación en el método `update` (parámetros incorrectos).

### Frontend (`proveedores.html`, `proveedores-service.js`)
- **Tabla de Directorio**:
    - Se homologó el diseño visual con Clientes.
    - Se implementó lógica para deducir el ícono (Física/Moral) basado en la longitud del RFC (13 vs 12 caracteres).
- **Funcionalidad y Validaciones**:
    - Se reemplazaron las alertas nativas (`alert()`) por `MessageService.mostrar()`.
    - Se corrigió la importación de scripts: se agregó `message-service.js` y `message-styles.css` en `proveedores.html` para que las notificaciones funcionen visualmente.
    - Se corrigió la sintaxis en la función `guardar()` para manejar correctamente las promesas y errores del backend.
    - Se armonizaron las validaciones de RFC, Teléfono y Correo con el estándar del sistema.

## Estado Actual
- **Compilación**: Backend compilado exitosamente con `mvn clean package`.
- **Pruebas Pendientes**: Verificar visualización en navegador (requiere recarga) y funcionamiento de endpoints (requiere reinicio de servidor Java).
