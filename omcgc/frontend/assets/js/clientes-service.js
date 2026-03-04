/*
============================================================
Nombre del archivo : clientes-service.js
Ruta              : omcgc/frontend/assets/js/clientes-service.js
Tipo              : Frontend (Script JS)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Gestionar la interacción con el usuario en el módulo de Clientes,
consumir la API REST de backend y controlar la validación de formularios
y reglas de negocio visuales.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M06-01 (Registrar cliente):
   - Envío de datos fiscales y personales mediante POST.
   - Validaciones de formato RFC y emails.
2. HU-M06-02 (Consultar cliente):
   - Búsqueda filtrada por nombre, RFC y estatus.
   - Paginación y renderizado de tabla dinámica.
3. RF-04 (Control de Permisos):
   - Ocultamiento de botones (Crear, Editar) según privilegios de sesión.
============================================================
*/
const ClientesService = {
    // ==========================================
    // CONFIGURACIÓN Y ESTADO
    // ==========================================

    // API Endpoints
    apiUrl: AppConfig.getEndpoint('clientes'),
    usuariosApiUrl: AppConfig.getEndpoint('usuarios'),

    // Estado local de permisos (Privilegios sobre ESTE módulo 'CLIENTES')
    // Se inicializa en falso por seguridad (Deny by default)
    permisosActuales: {
        ver: false,
        crear: false,
        editar: false,
        eliminar: false
    },

    currentUser: null,

    // ==========================================
    // CICLO DE VIDA (INICIALIZACIÓN)
    // ==========================================

    init: async function () {
        try {
            // SRP: Delegar a AuthService
            this.currentUser = AuthService.getCurrentUser();
            if (!this.currentUser) {
                window.location.href = 'login.html';
                return;
            }

            // --- VALIDACIÓN DE SEGURIDAD CENTRALIZADA ---
            const nombreModulo = "CLIENTES";
            this.permisosActuales = AuthService.obtenerPermisosModulo(nombreModulo);

            if (!this.permisosActuales.ver) {
                MessageService.mostrar(8, "Acceso Denegado", `No tiene permisos para ver el módulo de ${nombreModulo}.`, "", "", () => {
                    window.location.href = 'menu.html';
                });
                return;
            }

            // 1. Cargar Datos
            this.cargarClientes();

            // 2. Aplicar seguridad visual
            this.aplicarSeguridadVisual();

            this.setupEventListeners();

        } catch (e) {
            console.error("Fallo al inicializar módulo Clientes:", e);
        }
    },

    aplicarSeguridadVisual: function () {
        const btnNuevo = document.getElementById('btnNuevo');
        // Regla: Si no puede crear, ocultar botón Nuevo
        if (btnNuevo) {
            btnNuevo.style.display = this.permisosActuales.crear ? 'inline-flex' : 'none';
        }

        // Bitácora para Administradores
        const btnBitacora = document.getElementById('btnVerBitacora');
        if (btnBitacora) {
            btnBitacora.style.display = (AuthService.esAdmin()) ? 'flex' : 'none';
        }
    },

    setupEventListeners: function () {
        // 1. Buscador General (Input de texto)
        document.getElementById('btnBuscar').addEventListener('input', () => {
            this.cargarClientes();
        });

        // 2. Filtro RFC (Input de texto)
        document.getElementById('filterRfc').addEventListener('input', () => {
            this.cargarClientes();
        });

        // 3. Botones de Estatus (Toggle)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                this.cargarClientes();
            });
        });

        // 4. Navegación por Teclado (Flechas Arriba/Abajo)
        document.addEventListener('keydown', (e) => {
            // Ignorar si el foco está en un campo de texto
            if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') return;

            const selected = document.querySelector('.selected-row');
            if (!selected) return;

            const rows = Array.from(document.querySelectorAll('#clientesTableBody tr'));
            const currentIndex = rows.indexOf(selected);
            let nextIndex = -1;

            if (e.key === 'ArrowDown') {
                e.preventDefault();
                nextIndex = currentIndex + 1;
            } else if (e.key === 'ArrowUp') {
                e.preventDefault();
                nextIndex = currentIndex - 1;
            }

            if (nextIndex >= 0 && nextIndex < rows.length) {
                // Simula click para activar selección y carga de detalle
                rows[nextIndex].click();
                // Scroll suave para mantener visible la fila
                rows[nextIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        });

        // Eventos Bitácora
        document.getElementById('btnVerBitacora')?.addEventListener('click', () => this.verBitacora());
    },

    // ==========================================
    // CARGA DE DATOS (LECTURA)
    // ==========================================

    cargarClientes: async function () {
        const buscar = document.getElementById('btnBuscar').value;
        const rfc = document.getElementById('filterRfc').value;
        const estatusBtn = document.querySelector('.status-btn.active');
        const estatus = estatusBtn ? estatusBtn.getAttribute('data-status').toUpperCase() : '';

        const tbody = document.getElementById('clientesTableBody');
        tbody.innerHTML = '<tr><td colspan="5" class="loading-state">Cargando registros...</td></tr>';

        try {
            // Construcción QueryString segura
            const query = `?buscar=${encodeURIComponent(buscar)}&rfc=${encodeURIComponent(rfc)}&estatus=${estatus}`;

            const response = await fetch(`${this.apiUrl}${query}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (!response.ok) throw new Error(`HTTP Error ${response.status}`);

            const data = await response.json();
            this.renderTabla(data);

        } catch (error) {
            console.error("Error fetch clientes:", error);
            tbody.innerHTML = `<tr><td colspan="5" class="error-state">Error de conexión: ${error.message}</td></tr>`;
        }
    },

    renderTabla: function (clientes) {
        const tbody = document.getElementById('clientesTableBody');
        tbody.innerHTML = '';

        if (!clientes || clientes.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="empty-state">No se encontraron registros coincidentes.</td></tr>';
            return;
        }

        clientes.forEach(c => {
            const tr = document.createElement('tr');
            // E2-004: Click en toda la fila abre detalle y marca selección visual
            tr.onclick = (e) => {
                // 1. Gestión Visual (Highlight permanente)
                const allRows = document.querySelectorAll('#clientesTableBody tr');
                allRows.forEach(row => row.classList.remove('selected-row'));
                e.currentTarget.classList.add('selected-row');

                // 2. Cargar Detalle
                this.verDetalle(c.idPaciente);
            };

            // E2-003: Estilos condicionales y Homologación con Usuarios
            // Convertimos estatus a formato Capitalizado para visualización (ACTIVO -> Activo)
            const estatusRaw = c.estatus || 'INACTIVO';
            const isActivo = estatusRaw === 'ACTIVO';

            // Clases para semáforo (punto verde/rojo)
            const statusClass = isActivo ? 'activo' : 'inactivo';
            const statusText = isActivo ? 'Activo' : 'Inactivo';

            // Botón estilo Usuarios (btn-secondary pequeño)

            // Concatenar nombre y apellidos para visualización completa
            const nombreCompleto = (c.nombre + ' ' + (c.apellidos || '')).trim();
            const nombreMostrar = nombreCompleto || c.razonSocial || 'Sin Nombre';

            // Iconos SVG para diferenciar tipos (Con estilos INLINE para asegurar tamaño)
            // Física: User icon (azul)
            const iconFisica = `<svg style="width: 16px; height: 16px; color: #2563eb; margin-right: 8px;" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>`;
            // Moral: Building icon (morado)
            const iconMoral = `<svg style="width: 16px; height: 16px; color: #9333ea; margin-right: 8px;" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path></svg>`;

            const icon = c.tipoPersona === 'MORAL' ? iconMoral : iconFisica;
            const tipoTexto = c.tipoPersona === 'MORAL' ? 'Persona Moral' : 'Persona Física';

            tr.innerHTML = `
                <td>
                    <div style="display: flex; align-items: center;">
                        <span title="${tipoTexto}" style="flex-shrink: 0;">${icon}</span>
                        <div>
                            <div style="font-weight: bold; color: #1f2937; line-height: 1.2;">${nombreMostrar}</div>
                            <!-- Opcional: Si quieres ocultar el texto de abajo, elimina o comenta la linea siguiente -->
                            <div style="font-size: 0.75rem; color: #6b7280; line-height: 1.2; margin-top: 2px;">${c.tipoPersona}</div> 
                        </div>
                    </div>
                </td>
                <td style="font-family: monospace; font-size: 0.875rem;">${c.rfc || '--'}</td>
                <td>${c.telefono || c.email || '<span style="color: #9ca3af;">Sin contacto</span>'}</td>
                <td>
                    <span class="status-indicator ${statusClass}"></span> ${statusText}
                </td>
                <td>
                    <button class="btn-secondary" style="padding: 4px 12px; font-size: 0.8rem;" onclick="event.stopPropagation(); ClientesService.verDetalle('${c.idPaciente}')">
                        Ver
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    },
    // LÓGICA DE FORMULARIO (CRUD)
    // ==========================================

    toggleTipoPersona: function () {
        const tipo = document.getElementById('detailTipoPersona').value;
        const blockFisica = document.getElementById('blockFisica');
        const blockMoral = document.getElementById('blockMoral');
        const inputRfc = document.getElementById('detailRfc');

        if (tipo === 'FISICA') {
            blockFisica.style.display = 'block';
            blockMoral.style.display = 'none';
            inputRfc.setAttribute('placeholder', 'XAXX010101000 (13 car.)');
            inputRfc.setAttribute('maxlength', '13');
        } else {
            blockFisica.style.display = 'none';
            blockMoral.style.display = 'block';
            inputRfc.setAttribute('placeholder', 'XXX010101000 (12 car.)');
            inputRfc.setAttribute('maxlength', '12');
        }
    },

    nuevo: function () {
        // --- VALIDACIÓN DE SEGURIDAD ---
        if (!this.permisosActuales.crear) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para crear clientes.");
            return;
        }
        this.limpiarFormulario();
        document.getElementById('detailTipoPersona').value = 'FISICA';
        this.toggleTipoPersona(); // Sincronizar UI
        document.getElementById('detailEstatus').value = 'ACTIVO';
    },

    verDetalle: async function (id) {
        try {
            const response = await fetch(`${this.apiUrl}/${id}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });
            if (!response.ok) throw new Error('Error recuperando entidad');

            const cliente = await response.json();

            // 1. Mapeo de datos Backend -> UI
            document.getElementById('detailIdCliente').value = cliente.idPaciente;

            // Tipo Persona y Reset UI
            const tipo = cliente.tipoPersona || 'FISICA';
            document.getElementById('detailTipoPersona').value = tipo;
            this.toggleTipoPersona();

            // Llenado de campos específicos
            if (tipo === 'FISICA') {
                document.getElementById('detailNombreFisica').value = cliente.nombre || '';
                document.getElementById('detailApellidosFisica').value = cliente.apellidos || '';
                document.getElementById('detailRazonSocial').value = '';
            } else {
                document.getElementById('detailNombreFisica').value = '';
                document.getElementById('detailApellidosFisica').value = '';
                // En Moral, usamos 'nombre' o 'razonSocial' del backend. 
                // Asumiendo que el backend devolvió el nombre en 'nombre' o 'razonSocial'.
                document.getElementById('detailRazonSocial').value = cliente.nombre || cliente.razonSocial || '';
            }

            document.getElementById('detailRfc').value = cliente.rfc || '';
            document.getElementById('detailRegimen').value = cliente.regimenFiscal || '';
            document.getElementById('detailUsoCfdi').value = cliente.usoCfdi || '';
            document.getElementById('detailDomicilio').value = cliente.domicilioFiscal || '';
            document.getElementById('detailTelefono').value = cliente.telefono || '';
            document.getElementById('detailCorreo').value = cliente.email || '';
            document.getElementById('detailEstatus').value = cliente.estatus || 'ACTIVO';

            // 2. Control de Edición (Seguridad Explícita)
            const puedeEditar = this.permisosActuales.editar;
            const puedeEliminar = this.permisosActuales.eliminar;

            // Bloqueo de campos
            const campos = [
                'detailNombreFisica', 'detailApellidosFisica', 'detailRazonSocial',
                'detailTipoPersona', 'detailRfc',
                'detailRegimen', 'detailUsoCfdi', 'detailDomicilio',
                'detailTelefono', 'detailCorreo', 'detailEstatus'
            ];

            campos.forEach(id => {
                const el = document.getElementById(id);
                if (el) el.disabled = !puedeEditar;
            });

            // Regla Específica ELIMINAR (Baja Lógica)
            const estatusInput = document.getElementById('detailEstatus');
            if (estatusInput) {
                if (puedeEditar && !puedeEliminar) {
                    estatusInput.disabled = true;
                }
            }

            // Botón Guardar
            const btnGuardar = document.getElementById('btnGuardar');
            if (btnGuardar) {
                btnGuardar.style.display = puedeEditar ? 'inline-flex' : 'none';
            }

        } catch (error) {
            MessageService.mostrar(3, "Error de Consulta", "No se pudo cargar el detalle del cliente.", error.message);
        }
    },

    guardar: async function () {
        const id = document.getElementById('detailIdCliente').value;
        const tipo = document.getElementById('detailTipoPersona').value;
        const accion = id ? 'EDICIÓN' : 'CREACIÓN';

        // --- VALIDACIÓN DE SEGURIDAD ---
        if (accion === 'EDICIÓN' && !this.permisosActuales.editar) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para editar clientes.");
            return;
        }
        if (accion === 'CREACIÓN' && !this.permisosActuales.crear) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para crear clientes.");
            return;
        }

        const method = id ? 'PUT' : 'POST';
        const url = id ? `${this.apiUrl}/${id}` : this.apiUrl;

        // 1. Construir Objeto DTO
        const cliente = {
            tipoPersona: tipo,
            rfc: document.getElementById('detailRfc').value,
            regimenFiscal: document.getElementById('detailRegimen').value,
            usoCfdi: document.getElementById('detailUsoCfdi').value,
            domicilioFiscal: document.getElementById('detailDomicilio').value,
            telefono: document.getElementById('detailTelefono').value,
            email: document.getElementById('detailCorreo').value,
            estatus: document.getElementById('detailEstatus').value
        };

        // Lógica de Nombres según Tipo
        if (tipo === 'FISICA') {
            cliente.nombre = document.getElementById('detailNombreFisica').value.trim();
            cliente.apellidos = document.getElementById('detailApellidosFisica').value.trim();
            cliente.razonSocial = null; // Limpiar si existía
        } else {
            // MORAL
            const rs = document.getElementById('detailRazonSocial').value.trim();
            cliente.nombre = rs; // Backend usa 'nombre' como principal
            cliente.razonSocial = rs;
            cliente.apellidos = ""; // Limpiar
        }

        // Auditoría
        const user = AuthService.getCurrentUser();
        cliente.idUsuarioOperacion = user ? (user.userId || user.id) : null;

        // Incluir ID en el cuerpo
        if (id) {
            cliente.idPaciente = id;
        }

        // 2. Validaciones de Negocio (Frontend)
        if (tipo === 'FISICA') {
            if (!cliente.nombre || !cliente.apellidos) {
                MessageService.mostrar(9, "Campos Requeridos", "Nombre y Apellidos son obligatorios para Persona Física.");
                return;
            }
        } else {
            if (!cliente.nombre) {
                MessageService.mostrar(9, "Campos Requeridos", "La Razón Social es obligatoria para Persona Moral.");
                return;
            }
        }

        // --- VALIDACIÓN DE RFC ---
        if (cliente.rfc) {
            cliente.rfc = cliente.rfc.toUpperCase().trim();
            const rfcPattern = /^([A-ZÑ&]{3,4}) ?(?:- ?)?(\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])) ?(?:- ?)?([A-Z\d]{2})([A\d])$/;

            if (!rfcPattern.test(cliente.rfc)) {
                MessageService.mostrar(1, "Formato de RFC Inválido", "El RFC ingresado no cumple con el formato oficial.", "Ejemplo: XAXX010101000 (Física) o XXX010101000 (Moral)", cliente.rfc);
                return;
            }

            if (tipo === 'FISICA' && cliente.rfc.length !== 13) {
                MessageService.mostrar(1, "Longitud de RFC Incorrecta", "El RFC de Persona FÍSICA debe tener 13 caracteres.", "", cliente.rfc);
                return;
            }
            if (tipo === 'MORAL' && cliente.rfc.length !== 12) {
                MessageService.mostrar(1, "Longitud de RFC Incorrecta", "El RFC de Persona MORAL debe tener 12 caracteres.", "", cliente.rfc);
                return;
            }
        }

        // --- VALIDACIÓN DE CORREO ---
        if (cliente.email) {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(cliente.email)) {
                MessageService.mostrar(1, "Correo Electrónico Inválido", "Verifique el formato del correo electrónico.", "Ejemplo: usuario@dominio.com", cliente.email);
                return;
            }
        }

        // --- VALIDACIÓN DE TELÉFONO ---
        if (cliente.telefono) {
            // Eliminar espacios y guiones para validar solo números
            const cleanPhone = cliente.telefono.replace(/[\s-]/g, '');
            if (!/^\d{10}$/.test(cleanPhone)) {
                MessageService.mostrar(1, "Teléfono Inválido", "El teléfono debe contener 10 dígitos numéricos.", "Ingrese solo números.", cliente.telefono);
                return;
            }
        }

        try {
            // 3. Persistencia
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}`
                },
                body: JSON.stringify(cliente)
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || 'Error en transacción de guardado');
            }

            MessageService.mostrar(4, "Operación Exitosa", "Cliente guardado exitosamente.");
            this.limpiarFormulario();
            this.cargarClientes();

        } catch (error) {
            MessageService.mostrar(3, "Error al Guardar", "No se pudo guardar la información del cliente.", error.message);
        }
    },

    limpiarFormulario: function () {
        document.getElementById('detailIdCliente').value = '';

        // Limpiar campos específicos
        document.getElementById('detailNombreFisica').value = '';
        document.getElementById('detailApellidosFisica').value = '';
        document.getElementById('detailRazonSocial').value = '';

        document.getElementById('detailRfc').value = '';
        document.getElementById('detailTelefono').value = '';
        document.getElementById('detailCorreo').value = '';
        document.getElementById('detailDomicilio').value = '';
        document.getElementById('detailRegimen').value = '';
        document.getElementById('detailUsoCfdi').value = '';

        // Restaurar estado de controles si tiene permisos
        if (this.permisosActuales.crear || this.permisosActuales.editar) {
            document.querySelectorAll('.usuario-detail input, .usuario-detail select, .usuario-detail textarea').forEach(i => i.disabled = false);
            document.getElementById('btnGuardar').style.display = 'inline-flex';
        }

        // Reset Tipo Persona (default Física)
        document.getElementById('detailTipoPersona').value = 'FISICA';
        this.toggleTipoPersona();
    },

    mostrarToast: function (mensaje, tipo = 'info') {
        // Fallback a MessageService
        if (tipo === 'error') {
            msgType = 3;
            title = "Error";
        } else if (tipo === 'success') {
            msgType = 4;
            title = "Éxito";
        }
        MessageService.mostrar(msgType, title, mensaje);
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
            const params = new URLSearchParams({ desde, hasta, usuario, buscar, modulo: 'CLIENTES' });
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
        let c = "REPORTE DE AUDITORÍA - CLIENTES\n\n";
        this.currentLogs.forEach(l => { c += `[${new Date(l.fecha).toLocaleString()}] ${l.nombreUsuario} - ${l.accion}: ${l.detalles}\n`; });
        const blob = new Blob([c], { type: 'text/plain' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Bitacora_Clientes.txt`;
        a.click();
    }
};

// Inicializar al cargar
document.addEventListener('DOMContentLoaded', () => {
    ClientesService.init();
});
