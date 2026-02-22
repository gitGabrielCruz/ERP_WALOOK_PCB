/* 
============================================================
Nombre del archivo : proveedores-service.js
Ruta              : omcgc/frontend/assets/js/proveedores-service.js
Tipo              : Frontend (Lógica de Negocio y API)

Proyecto          : Sistema ERP WALOOK
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Gestionar el ciclo de vida del catálogo de proveedores (CRUD).
Permite listar, filtrar, crear, editar y consultar detalles de proveedores.
============================================================
*/

const ProveedoresService = {
    apiUrl: AppConfig.getEndpoint('proveedores'),
    proveedores: [],
    permisosActuales: {},

    /**
     * Inicializa el módulo: carga permisos, configura UI y carga datos iniciales (Homologado con Clientes).
     */
    init: async function () {
        console.log("Inicializando ProveedoresService...");

        try {
            this.currentUser = AuthService.getCurrentUser();
            if (!this.currentUser) {
                window.location.href = 'login.html';
                return;
            }

            // --- VALIDACIÓN DE SEGURIDAD CENTRALIZADA ---
            const nombreModulo = "PROVEEDORES";
            this.permisosActuales = AuthService.obtenerPermisosModulo(nombreModulo);

            if (!this.permisosActuales.ver) {
                alert(`⛔ ACCESO DENEGADO\n\nNo tiene permisos para ver el módulo de ${nombreModulo}.`);
                window.location.href = 'menu.html';
                return;
            }

        } catch (e) {
            console.warn("Error al recuperar permisos:", e);
        }

        // 2. Configuración de Listeners de Búsqueda (Homologado)
        const searchInput = document.getElementById('btnBuscar');
        if (searchInput) {
            searchInput.addEventListener('input', () => {
                this.cargarProveedores();
            });
        }

        const filterRfc = document.getElementById('filterRfc');
        if (filterRfc) {
            filterRfc.addEventListener('input', () => {
                this.renderTabla();
            });
        }

        const filterCondicion = document.getElementById('filterCondicionPago');
        if (filterCondicion) {
            filterCondicion.addEventListener('change', () => {
                this.renderTabla();
            });
        }

        // Listeners para botones de estatus (Toggle)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                this.cargarProveedores();
            });
        });

        // 3. Aplicar seguridad visual (Homologado)
        this.aplicarSeguridadVisual();

        // 4. Cargar Datos
        await this.cargarProveedores();
    },

    aplicarSeguridadVisual: function () {
        const btnNuevo = document.getElementById('btnNuevo');
        if (btnNuevo) {
            btnNuevo.style.display = this.permisosActuales.crear ? 'inline-flex' : 'none';
        }

        // Bitácora para Administradores
        const btnBitacora = document.getElementById('btnVerBitacora');
        if (btnBitacora) {
            btnBitacora.style.display = (AuthService.esAdmin()) ? 'flex' : 'none';
        }
    },

    /**
     * Recupera la lista de proveedores desde el backend con soporte de búsqueda básica.
     */
    cargarProveedores: async function () {
        const buscarEl = document.getElementById('btnBuscar');
        const estatusBtn = document.querySelector('.status-btn.active');

        const busqueda = buscarEl ? buscarEl.value : '';
        const estatus = estatusBtn ? estatusBtn.getAttribute('data-status') : '';

        const tbody = document.getElementById('proveedoresTableBody');
        if (!tbody) return;
        tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; padding:20px;">Cargando catálogo...</td></tr>';

        try {
            let url = `${this.apiUrl}`;
            const params = new URLSearchParams();
            if (busqueda) params.append('buscar', busqueda);
            if (estatus) params.append('estatus', estatus);
            params.append('_', new Date().getTime()); // Anti-cache
            if (params.toString()) url += `?${params.toString()}`;

            const headers = {
                'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}`,
                'Content-Type': 'application/json'
            };

            const response = await fetch(url, { headers: headers });

            if (!response.ok) throw new Error(`HTTP Error ${response.status}`);

            const data = await response.json();
            this.proveedores = data || [];
            this.renderTabla();

        } catch (error) {
            console.error("Error al cargar proveedores:", error);
            tbody.innerHTML = `<tr><td colspan="5" style="text-align:center; color:#DC2626; padding:20px;">Error de conexión: ${error.message}</td></tr>`;
        }
    },

    /**
     * Renderiza los datos en la tabla HTML aplicando filtros locales de UI.
     */
    renderTabla: function () {
        const tbody = document.getElementById('proveedoresTableBody');
        if (!tbody) return;
        tbody.innerHTML = '';

        const filterRfcInput = document.getElementById('filterRfc');
        const rfcFilter = filterRfcInput ? filterRfcInput.value.toUpperCase() : '';

        const filterPagoInput = document.getElementById('filterCondicionPago');
        const pagoFilter = filterPagoInput ? filterPagoInput.value : '';

        // Filtrado local (Solo RFC y Pago, el estatus ya viene del Servidor)
        let visualData = this.proveedores.filter(p => {
            const rfcVal = p.rfc ? p.rfc.toUpperCase() : '';
            if (rfcFilter && !rfcVal.includes(rfcFilter)) return false;

            if (pagoFilter && (p.condicionPago || '') !== pagoFilter) return false;

            return true;
        });

        if (visualData.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; padding:20px; color:#6B7280;">No se encontraron registros coincidentes.</td></tr>';
            return;
        }

        visualData.forEach(p => {
            const tr = document.createElement('tr');
            // Gestión de selección visual (Azul)
            tr.onclick = (e) => {
                const rows = document.querySelectorAll('#proveedoresTableBody tr');
                rows.forEach(r => r.classList.remove('selected-row'));
                e.currentTarget.classList.add('selected-row');
                this.verDetalle(p);
            };

            // Semáforo Estatus
            const estatus = p.estatus || 'INACTIVO';
            const isActivo = estatus === 'ACTIVO';
            const statusClass = isActivo ? 'activo' : 'inactivo';
            const statusText = isActivo ? 'Activo' : 'Inactivo';

            // Lógica de Iconos (Prioridad: Dato explícito > Deducción RFC)
            let tipoPersona = p.tipoPersona || (p.rfc && p.rfc.length === 13 ? 'FISICA' : 'MORAL');

            // Iconos SVG (Homologados con Clientes)
            // Física: User icon (azul #2563eb)
            const iconFisica = `<svg style="width: 16px; height: 16px; color: #2563eb; margin-right: 8px;" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>`;
            // Moral: Building icon (morado #9333ea)
            const iconMoral = `<svg style="width: 16px; height: 16px; color: #9333ea; margin-right: 8px;" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path></svg>`;

            const icon = tipoPersona === 'FISICA' ? iconFisica : iconMoral;
            const tituloTipo = tipoPersona === 'FISICA' ? 'Persona Física' : 'Persona Moral';

            tr.innerHTML = `
                <td>
                    <div style="display: flex; align-items: center;">
                        <span title="${tituloTipo}" style="flex-shrink: 0;">${icon}</span>
                        <div>
                            <div style="font-weight: bold; color: #1f2937; line-height: 1.2;">
                                ${p.nombreComercial && p.nombreComercial.trim() !== '' ? p.nombreComercial : (p.razonSocial || 'Sin Nombre')}
                            </div>
                            <!-- Subtitulo opcional: Razon Social si mostramos Nombre Comercial arriba -->
                            ${p.nombreComercial && p.nombreComercial !== p.razonSocial ?
                    `<div style="font-size: 0.75rem; color: #6b7280; line-height: 1.2; margin-top: 2px;">${p.razonSocial || ''}</div>`
                    : ''}
                        </div>
                    </div>
                </td>
                <td style="font-family: monospace; font-size: 0.875rem;">${p.rfc || '--'}</td>
                <td>${p.telefono || p.email || '<span style="color: #9ca3af;">Sin contacto</span>'}</td>
                <td>
                    <span class="status-indicator ${statusClass}"></span> ${statusText}
                </td>
                <td>
                     <button class="btn-secondary" style="padding: 4px 12px; font-size: 0.8rem;" onclick="event.stopPropagation(); ProveedoresService.verDetallePorId('${p.idProveedor}')">
                        Ver
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    },

    // Auxiliar para llamar detalle por ID (wrapper)
    verDetallePorId: function (id) {
        const p = this.proveedores.find(x => x.idProveedor === id);
        if (p) this.verDetalle(p);
    },

    /**
     * Carga los datos de un proveedor en el formulario de edición.
     */
    verDetalle: function (p) {
        // Mapeo UI
        document.getElementById('idProveedor').value = p.idProveedor;
        document.getElementById('tipoPersona').value = p.tipoPersona || (p.rfc && p.rfc.length === 13 ? 'FISICA' : 'MORAL');
        document.getElementById('nombreComercial').value = p.nombreComercial || '';
        document.getElementById('razonSocial').value = p.razonSocial || '';
        document.getElementById('rfc').value = p.rfc || '';
        document.getElementById('condicionPago').value = p.condicionPago || '';
        document.getElementById('telefono').value = p.telefono || '';
        document.getElementById('email').value = p.email || '';
        document.getElementById('contacto').value = p.contacto || '';
        document.getElementById('estatus').value = p.estatus || 'ACTIVO';

        this.ajustarValidacionesRFC(); // Actualizar UI según tipo

        // Control de Permisos
        const puedeEditar = this.permisosActuales.editar;
        const formInputs = document.querySelectorAll('#formProveedor input, #formProveedor select');

        formInputs.forEach(i => i.disabled = !puedeEditar);

        const btnGuardar = document.getElementById('btnGuardar');
        if (btnGuardar) btnGuardar.style.display = puedeEditar ? 'inline-flex' : 'none';
    },

    /**
     * Prepara el formulario para un nuevo registro.
     */
    nuevo: function () {
        if (!this.permisosActuales.crear) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para crear proveedores.");
            return;
        }
        this.limpiarFormulario();
        document.getElementById('estatus').value = 'ACTIVO';
    },

    /**
     * Envía los datos del formulario al servidor (Crear o Actualizar).
     */
    guardar: async function () {
        const id = document.getElementById('idProveedor').value;
        const accion = id ? 'EDICIÓN' : 'CREACIÓN';

        // --- VALIDACIÓN DE SEGURIDAD ---
        if (accion === 'EDICIÓN' && !this.permisosActuales.editar) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para editar proveedores.");
            return;
        }
        if (accion === 'CREACIÓN' && !this.permisosActuales.crear) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para crear proveedores.");
            return;
        }

        const method = id ? 'PUT' : 'POST';
        const url = id ? `${this.apiUrl}/${id}` : this.apiUrl;

        // 1. Construir Objeto
        const proveedor = {
            idProveedor: id ? id : null,
            tipoPersona: document.getElementById('tipoPersona').value, // Leer del select
            nombreComercial: document.getElementById('nombreComercial').value.trim(),
            razonSocial: document.getElementById('razonSocial').value.trim(),
            rfc: document.getElementById('rfc').value.trim().toUpperCase(),
            condicionPago: document.getElementById('condicionPago').value,
            telefono: document.getElementById('telefono').value.trim(),
            email: document.getElementById('email').value.trim(),
            contacto: document.getElementById('contacto').value.trim(),
            estatus: document.getElementById('estatus').value
        };

        // Auditoría
        const user = AuthService.getCurrentUser();
        proveedor.idUsuarioOperacion = user ? (user.userId || user.id) : null;

        // 2. Validaciones de Negocio

        // Validación Cruzada RFC vs Tipo Persona
        if (proveedor.rfc) {
            const longitudEsperada = proveedor.tipoPersona === 'FISICA' ? 13 : 12;
            if (proveedor.rfc.length !== longitudEsperada) {
                MessageService.mostrar(1, "Longitud de RFC Incorrecta",
                    `Para Persona ${proveedor.tipoPersona === 'FISICA' ? 'Física' : 'Moral'} el RFC debe tener ${longitudEsperada} caracteres.`);
                return;
            }
        }
        if (!proveedor.razonSocial || !proveedor.nombreComercial) {
            MessageService.mostrar(9, "Campos Requeridos", "La Razón Social y el Nombre Comercial son obligatorios.");
            return;
        }

        // RFC
        if (proveedor.rfc) {
            proveedor.rfc = proveedor.rfc.toUpperCase();
            const rfcPattern = /^([A-ZÑ&]{3,4}) ?(?:- ?)?(\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])) ?(?:- ?)?([A-Z\d]{2})([A\d])$/;

            if (!rfcPattern.test(proveedor.rfc)) {
                MessageService.mostrar(1, "Formato de RFC Inválido", "El RFC no cumple con el formato oficial.", "Ej: XAXX010101000", proveedor.rfc);
                return;
            }
            // Validacion longitud estricta
            if (proveedor.rfc.length < 12 || proveedor.rfc.length > 13) {
                MessageService.mostrar(1, "Longitud de RFC Incorrecta", "Debe tener 12 (Moral) o 13 (Física) caracteres.");
                return;
            }
        }

        // Email
        if (proveedor.email) {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(proveedor.email)) {
                MessageService.mostrar(1, "Correo Inválido", "Verifique el formato del correo electrónico.");
                return;
            }
        }

        // Telefono
        if (proveedor.telefono) {
            const cleanPhone = proveedor.telefono.replace(/[\s-]/g, '');
            if (!/^\d{10}$/.test(cleanPhone)) {
                MessageService.mostrar(1, "Teléfono Inválido", "El teléfono debe tener 10 dígitos numéricos.");
                return;
            }
        }

        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}`
                },
                body: JSON.stringify(proveedor)
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || errorData.message || 'Error en transacción.');
            }

            MessageService.mostrar(4, "Operación Exitosa", "Proveedor guardado exitosamente.");
            this.limpiarFormulario();
            this.cargarProveedores();

        } catch (error) {
            console.error(error);
            // Analizar si es error de validación (Negocio) o Error Crítico
            let tipo = 3; // Error Sistema
            let titulo = "Error del Sistema";
            let detalle = "No se pudo completar la operación.";

            const msg = error.message.toLowerCase();

            // Si el mensaje sugiere error de usuario/validación
            if (msg.includes("debe seleccionar") || msg.includes("obligatorio") ||
                msg.includes("inválido") || msg.includes("ya existe") ||
                msg.includes("formato") || msg.includes("longitud")) {
                tipo = 1; // Advertencia / Error de Usuario
                titulo = "Verifique los Datos";
                detalle = error.message; // Mostrar mensaje directo del backend
                MessageService.mostrar(tipo, titulo, detalle);
            } else {
                // Error técnico real
                MessageService.mostrar(tipo, titulo, detalle, error.message);
            }
        }
    },

    limpiarFormulario: function () {
        document.getElementById('idProveedor').value = '';
        document.getElementById('formProveedor').reset();
        document.getElementById('tipoPersona').value = 'MORAL'; // Reset a default
        this.ajustarValidacionesRFC(); // Reset UI placeholder

        // Restaurar estado
        if (this.permisosActuales.crear || this.permisosActuales.editar) {
            const formInputs = document.querySelectorAll('#formProveedor input, #formProveedor select');
            formInputs.forEach(i => i.disabled = false);
            document.getElementById('btnGuardar').style.display = 'inline-flex';
        }
        document.getElementById('estatus').value = 'ACTIVO';
    },

    cancelar: function () {
        this.nuevo();
    },

    ajustarValidacionesRFC: function () {
        const tipo = document.getElementById('tipoPersona').value;
        const inputRFC = document.getElementById('rfc');
        if (tipo === 'FISICA') {
            inputRFC.placeholder = 'XAXX010101000 (13 dígitos)';
            inputRFC.maxLength = 13;
        } else {
            inputRFC.placeholder = 'XAXX010101000 (12 dígitos)';
            inputRFC.maxLength = 12;
        }
    },

    // ==========================================
    // AUDITORÍA Y BITÁCORA (ADMIN)
    // ==========================================
    verBitacora: function () {
        document.getElementById('bitacoraModal').classList.add('open');
        const hoy = new Date().toISOString().split('T')[0];
        if (document.getElementById('bitacoraDesde')) document.getElementById('bitacoraDesde').value = hoy;
        if (document.getElementById('bitacoraHasta')) document.getElementById('bitacoraHasta').value = hoy;
        this.cargarBitacora();
    },

    cerrarBitacoraModal: function () {
        document.getElementById('bitacoraModal').classList.remove('open');
    },

    cargarBitacora: async function () {
        const desde = document.getElementById('bitacoraDesde')?.value;
        const hasta = document.getElementById('bitacoraHasta')?.value;
        const usuario = document.getElementById('bitacoraUsuario')?.value;
        const buscar = document.getElementById('bitacoraBuscar')?.value;

        const tbody = document.getElementById('bitacoraTableBody');
        if (!tbody) return;
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 20px;">Consultando registros...</td></tr>';

        try {
            const params = new URLSearchParams({ desde, hasta, usuario, buscar, modulo: 'PROVEEDORES' });
            const response = await fetch(`${AppConfig.getEndpoint('bitacora')}?${params}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (response.ok) {
                const logs = await response.json();
                this.currentLogs = logs;
                this.renderTablaBitacora(logs);
            }
        } catch (e) { console.error(e); }
    },

    renderTablaBitacora: function (logs) {
        const tbody = document.getElementById('bitacoraTableBody');
        if (!tbody) return;
        tbody.innerHTML = '';
        if (!logs || logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 20px;">No hay registros.</td></tr>';
            return;
        }

        logs.forEach(log => {
            const tr = document.createElement('tr');
            tr.style.cursor = 'pointer';
            tr.onclick = () => this.abrirRegistroTexto(log);

            let badgeClass = 'badge-secondary';
            let displayAccion = log.accion;
            if (log.accion?.includes('|')) {
                const parts = log.accion.split('|');
                const impacto = parts[0].trim();
                displayAccion = parts[parts.length - 1].trim();
                if (impacto.includes('SUCCESS')) badgeClass = 'badge-success';
                else if (impacto.includes('FAILURE') || impacto.includes('ERROR')) badgeClass = 'badge-danger';
            }

            const fechaStr = log.fecha ? new Date(log.fecha).toLocaleString() : 'N/A';
            tr.innerHTML = `
                <td style="font-size: 0.85rem; font-family: monospace;">${fechaStr}</td>
                <td><div style="font-weight: 500;">${log.nombreUsuario || 'SISTEMA'}</div></td>
                <td><span class="badge-base ${badgeClass}" style="font-size: 0.7rem;">${displayAccion}</span></td>
                <td style="font-family: monospace; font-size: 0.85rem;">${log.ipOrigen || '0.0.0.0'}</td>
                <td style="font-size: 0.85rem;">${log.detalles || 'Sin detalles'}</td>
            `;
            tbody.appendChild(tr);
        });
    },

    abrirRegistroTexto: function (log) {
        const texto = `ID: ${log.idBitacora}\nFECHA: ${new Date(log.fecha).toLocaleString()}\nUSUARIO: ${log.nombreUsuario}\nEVENTO: ${log.accion}\nDETALLES: ${log.detalles}`;
        MessageService.mostrar(10, "Detalle de Auditoría", texto);
    },

    generarReporteBitacora: function () {
        if (!this.currentLogs) return;
        let c = "REPORTE DE AUDITORÍA - PROVEEDORES\n\n";
        this.currentLogs.forEach(l => { c += `[${new Date(l.fecha).toLocaleString()}] ${l.nombreUsuario} - ${l.accion}: ${l.detalles}\n`; });
        const blob = new Blob([c], { type: 'text/plain' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Bitacora_Proveedores.txt`;
        a.click();
    }
};

// Auto-inicialización
document.addEventListener('DOMContentLoaded', () => {
    setTimeout(() => {
        if (window.location.pathname.includes('proveedores.html')) {
            ProveedoresService.init();
        }
    }, 100);
});
