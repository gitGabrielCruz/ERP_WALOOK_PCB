/* 
============================================================
Nombre del archivo : message-service.js
Ruta              : omcgc/frontend/assets/js/message-service.js
Tipo              : Frontend (Servicio de Notificaciones)

Proyecto          : Sistema ERP en la nube para gestion de opticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matricula         : ES1821003109
Programa          : Licenciatura en Ingenieria en Desarrollo de Software
Unidad didactica  : Proyecto Terminal I / Proyecto Terminal II
Periodo academico : PT1 - PT2 (Agosto 2025 - Enero 2026)

Version           : v1.0

Proposito:
Proporcionar un sistema centralizado y estandarizado de mensajes y notificaciones
para toda la aplicacion. Implementa 9 tipos de mensajes predefinidos con diseno
visual consistente alineado con la identidad institucional (ui-base.css).

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RF-06 (Retroalimentacion Visual):
   - Mensajes de exito, error, advertencia y confirmacion.
   - Validacion de campos con ejemplos visuales.
   
2. RF-07 (Manejo de Errores):
   - Captura de errores tecnicos con stack trace.
   - Descarga de reportes para soporte.
   
3. RF-08 (Seguridad):
   - Notificacion de sesion expirada.
   - Bloqueo por permisos insuficientes.

4. Modulos que lo utilizan:
   - HU-M01-01 (Login): Errores de autenticacion
   - HU-M02-01 (Clientes): Validacion de RFC
   - HU-M05-01 (Proveedores): Confirmacion de eliminacion
   - HU-M03-01 (Inventarios): Advertencias de stock minimo
   - HU-M04-01 (Usuarios): Operaciones criticas con justificacion

Reglas de Uso:
------------------------------------------------------------
1. NUNCA usar alert() nativo de JavaScript.
2. SIEMPRE definir variables de texto ANTES de llamar MessageService.
3. Los textos deben ser claros, concisos y orientados a la accion.
4. Para operaciones async, mostrar spinner con procesando().
5. Errores tecnicos SIEMPRE deben incluir errorCode y stack trace.
6. Operaciones criticas REQUIEREN justificacion (advertenciaCritica).

Estructura de Parametros:
------------------------------------------------------------
Todos los metodos reciben parametros en este orden:
1. tipo (number): 1-9 segun catalogo de mensajes
2. titulo (string): Titulo del mensaje
3. mensajePrincipal (string): Descripcion principal
4. mensajeSecundario (string): Informacion adicional o ayuda
5. campoError (string, opcional): Valor que causo el error
6. callback (function, opcional): Funcion a ejecutar al confirmar

Ejemplo de Uso Correcto:
------------------------------------------------------------
// Definir variables
const titulo = 'Error en el campo RFC';
const mensajePrincipal = 'El valor ingresado no cumple con el formato oficial.';
const mensajeSecundario = 'El RFC debe contener 12 o 13 caracteres alfanumericos.';
const campoError = 'RFC ingresado: XAXX01010';

// Llamar el servicio
MessageService.mostrar(1, titulo, mensajePrincipal, mensajeSecundario, campoError);

Ejemplo de Uso INCORRECTO:
------------------------------------------------------------
// NO hacer esto:
MessageService.mostrar(1, 'Error', 'Mal', '', ''); // Textos poco descriptivos
alert('Error en RFC'); // NUNCA usar alert()
============================================================
*/

const MessageService = {

    /**
     * Metodo principal para mostrar mensajes.
     * 
     * @param {number} tipo - Tipo de mensaje (1-9)
     * @param {string} titulo - Titulo del mensaje
     * @param {string} mensajePrincipal - Mensaje principal
     * @param {string} mensajeSecundario - Mensaje secundario o ayuda
     * @param {string} campoError - Campo con error (opcional)
     * @param {function} callback - Funcion callback (opcional)
     */
    mostrar: function (tipo, titulo, mensajePrincipal, mensajeSecundario, campoError = '', callback = null) {

        // Crear overlay (fondo oscuro)
        const overlay = document.createElement('div');
        overlay.id = 'messageOverlay';
        overlay.className = 'message-overlay';

        // Crear contenedor del modal
        const modal = document.createElement('div');
        modal.className = 'message-modal';

        // Configurar segun tipo de mensaje
        let svgIcono = '';
        let dimensiones = { width: 520, height: 240 };
        let botones = '';
        let campoErrorHTML = '';
        let areaTextoHTML = '';

        switch (tipo) {
            case 1: // Error de Validacion
                dimensiones = { width: 520, height: 300 };
                svgIcono = this._iconoError();
                campoErrorHTML = campoError ? `<div class="message-field-error">${campoError}</div>` : '';
                botones = `
                    <button class="btn-error" onclick="MessageService.cerrar()">Aceptar</button>
                `;
                break;

            case 2: // Confirmacion
                dimensiones = { width: 520, height: 260 };
                svgIcono = this._iconoConfirmacion();
                botones = `
                    <button class="btn-cancelar" onclick="MessageService.cerrar()">Cancelar</button>
                    <button class="btn-primary" id="btnMsgPrimary">Confirmar</button>
                `;
                break;

            case 3: // Error de Sistema
                dimensiones = { width: 640, height: 420 };
                svgIcono = this._iconoError();
                campoErrorHTML = campoError ? `
                    <div class="message-technical">
                        <h3>Detalle tecnico</h3>
                        <pre>${campoError}</pre>
                    </div>
                ` : '';
                // Guardar datos en atributos data para el boton de descarga
                modal.setAttribute('data-error-titulo', titulo);
                // Si campoError está vacío, usamos mensajeSecundario como respaldo técnico para el reporte
                modal.setAttribute('data-error-detalle', campoError || mensajeSecundario || "No disponible");
                botones = `
                    <button class="btn-primary" id="btnDescargarReporte">Descargar reporte (.txt)</button>
                    <button class="btn-cancelar" onclick="MessageService.cerrar()">Cerrar</button>
                `;
                break;

            case 4: // Exito
                dimensiones = { width: 520, height: 240 };
                svgIcono = this._iconoExito();
                botones = `<button class="btn-success" onclick="MessageService.cerrar()">Aceptar</button>`;
                break;

            case 5: // Advertencia Critica con Justificacion
                dimensiones = { width: 600, height: 360 };
                svgIcono = this._iconoAdvertencia();
                areaTextoHTML = `
                    <label class="message-label">Motivo (obligatorio)</label>
                    <textarea id="motivoJustificacion" class="message-textarea" placeholder="Describa el motivo de esta operacion..."></textarea>
                `;
                botones = `
                    <button class="btn-cancelar" onclick="MessageService.cerrar()">Cancelar</button>
                    <button class="btn-danger" id="btnMsgDanger">Confirmar</button>
                `;
                break;

            case 6: // Procesando
                dimensiones = { width: 420, height: 220 };
                svgIcono = this._iconoSpinner();
                botones = ''; // Sin botones (modal bloqueante)
                break;

            case 7: // Sesion Expirada
                dimensiones = { width: 520, height: 240 };
                svgIcono = this._iconoCandado();
                botones = `<button class="btn-primary" onclick="window.location.href='pages/login.html'">Iniciar sesion</button>`;
                break;

            case 8: // Acceso Restringido
                dimensiones = { width: 520, height: 240 };
                svgIcono = this._iconoProhibido();
                botones = `<button class="btn-cancelar" onclick="MessageService.cerrar()">Cerrar</button>`;
                break;

            case 9: // Advertencia Simple
                dimensiones = { width: 520, height: 240 };
                svgIcono = this._iconoAdvertencia();
                botones = `<button class="btn-warning" onclick="MessageService.cerrar()">Aceptar</button>`;
                break;

            case 10: // Ficha Técnica (Log / Reporte)
                dimensiones = { width: 700, height: 500 };
                svgIcono = this._iconoInfo();
                botones = `<button class="btn-primary" onclick="MessageService.cerrar()">Cerrar Ficha</button>`;
                break;

            default:
                console.error('Tipo de mensaje invalido:', tipo);
                return;
        }

        // Aplicar clase de tipo para dimensiones (manejadas por CSS)
        // Esto permite que los media queries funcionen al redimensionar
        modal.classList.add(`type-${tipo}`);

        // Construir HTML del modal
        modal.innerHTML = `
            <div class="message-container">
                <div class="message-header">
                    ${svgIcono}
                    <h2 class="message-title">${titulo}</h2>
                </div>
                <hr class="message-divider">
                <p class="message-main">${mensajePrincipal}</p>
                ${mensajeSecundario ? `<p class="message-secondary">${mensajeSecundario}</p>` : ''}
                ${campoErrorHTML}
                ${areaTextoHTML}
                ${botones ? `<div class="message-buttons">${botones}</div>` : ''}
            </div>
        `;

        // Agregar modal al overlay
        overlay.appendChild(modal);

        // Mostrar en pantalla
        document.body.appendChild(overlay);

        // Event listener para boton de descarga de reporte (tipo 3)
        if (tipo === 3) {
            const btnDescargar = document.getElementById('btnDescargarReporte');
            if (btnDescargar) {
                btnDescargar.addEventListener('click', () => {
                    const tituloError = modal.getAttribute('data-error-titulo');
                    const detalleError = modal.getAttribute('data-error-detalle');
                    this._descargarReporte(tituloError, detalleError);
                });
            }
        }

        // Asignar eventos a botones (Uso de referencias reales de funcion)
        if (callback && typeof callback === 'function') {
            const bDanger = document.getElementById('btnMsgDanger');
            const bPrimary = document.getElementById('btnMsgPrimary');

            if (bDanger) {
                bDanger.onclick = () => {
                    const motivo = document.getElementById('motivoJustificacion')?.value?.trim();
                    if (tipo === 5 && !motivo) {
                        this.mostrar(9, "Motivo Requerido", "Debe indicar el motivo de la operación para continuar.");
                        return;
                    }
                    callback(motivo);
                    this.cerrar();
                };
            }

            if (bPrimary) {
                bPrimary.onclick = () => {
                    callback();
                    this.cerrar();
                };
            }
        }

        // Permitir cerrar con clic fuera del modal (excepto tipo 6 - procesando)
        if (tipo !== 6) {
            overlay.addEventListener('click', (e) => {
                if (e.target === overlay) {
                    this.cerrar();
                }
            });
        }
    },

    /**
     * Cierra el modal activo.
     */
    cerrar: function () {
        const overlay = document.getElementById('messageOverlay');
        if (overlay) {
            overlay.remove();
        }
    },

    /**
     * Muestra el spinner de procesamiento
     */
    procesando: function (mensaje = "Procesando solicitud...") {
        this.mostrar(6, "Espere un momento", mensaje, "");
    },

    /**
     * Descarga un reporte de error en formato .txt
     * @param {string} titulo - Titulo del error
     * @param {string} detalle - Detalle tecnico del error
     * @private
     */
    _descargarReporte: function (titulo, detalle) {
        const fecha = new Date();
        const userStr = sessionStorage.getItem('user') || sessionStorage.getItem('usuario_erp');
        const user = userStr ? JSON.parse(userStr) : null;
        const username = user ? (user.nombre || user.username || 'Usuario Logueado') : 'Desconocido';

        const reporte = [
            "============================================================",
            " REPORTE DE INCIDENCIA TÉCNICA - OPTICA ERP",
            "============================================================",
            "",
            `FECHA:       ${fecha.toLocaleString()} (${fecha.toISOString()})`,
            `USUARIO:     ${username}`,
            `UBICACIÓN:   ${window.location.href}`,
            `NAVEGADOR:   ${navigator.userAgent}`,
            `PLATAFORMA:  ${navigator.platform}`,
            "",
            "------------------------------------------------------------",
            " DETALLE DEL ERROR",
            "------------------------------------------------------------",
            `TÍTULO:      ${titulo}`,
            "",
            "STACK TRACE / MENSAJE TÉCNICO:",
            detalle || "No disponible",
            "",
            "============================================================",
            " Fin del reporte autogenerado",
            "============================================================"
        ].join('\n');

        const blob = new Blob([reporte], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `debug_report_${fecha.getTime()}.txt`;
        document.body.appendChild(a); // Necesario en algunos navegadores
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    },

    // ========== ICONOS SVG ==========

    /**
     * Icono de error (circulo con exclamacion)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoError: function () {
        return `
            <svg class="icon-error" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="7" x2="12" y2="13"/>
                <circle cx="12" cy="17" r="1.5" fill="currentColor" stroke="none"/>
            </svg>
        `;
    },

    /**
     * Icono de confirmacion (circulo con interrogacion)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoConfirmacion: function () {
        return `
            <svg class="icon-info" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="8" x2="12" y2="13"/>
                <circle cx="12" cy="17" r="1.5" fill="currentColor" stroke="none"/>
            </svg>
        `;
    },

    /**
     * Icono de exito (circulo con check)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoExito: function () {
        return `
            <svg class="icon-success" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <polyline points="7,12 11,16 17,8"/>
            </svg>
        `;
    },

    /**
     * Icono de advertencia (triangulo con exclamacion)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoAdvertencia: function () {
        return `
            <svg class="icon-warning" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polygon points="12,2 22,20 2,20"/>
                <line x1="12" y1="8" x2="12" y2="14"/>
                <circle cx="12" cy="17" r="1.5" fill="currentColor" stroke="none"/>
            </svg>
        `;
    },

    /**
     * Icono de spinner (animado)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoSpinner: function () {
        return `
            <svg class="icon-info spinner" width="36" height="36" viewBox="0 0 36 36" stroke="currentColor" stroke-width="3" fill="none">
                <circle cx="18" cy="18" r="18" stroke-opacity="0.2"/>
                <path d="M 18 0 A 18 18 0 0 1 36 18"/>
            </svg>
        `;
    },

    /**
     * Icono de candado
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoCandado: function () {
        return `
            <svg class="icon-info" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="7" y="10" width="10" height="10" rx="2"/>
                <path d="M9 10V7a3 3 0 0 1 6 0v3"/>
            </svg>
        `;
    },

    /**
     * Icono de prohibido (circulo con diagonal)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoProhibido: function () {
        return `
            <svg class="icon-error" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
            </svg>
        `;
    },

    /**
     * Icono de informacion (circulo con letra i)
     * @returns {string} HTML del SVG
     * @private
     */
    _iconoInfo: function () {
        return `
            <svg class="icon-info" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="16" x2="12" y2="12"/>
                <line x1="12" y1="8" x2="12.01" y2="8"/>
            </svg>
        `;
    }
};
