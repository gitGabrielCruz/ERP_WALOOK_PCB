/*
============================================================
Nombre del archivo : clientes-service.js
Ruta              : omcgc/frontend/assets/js/clientes-service.js
Tipo              : Frontend (Script JS)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
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
            this.currentUser = MenuService.getUser();
            if (!this.currentUser) {
                window.location.href = 'login.html';
                return;
            }

            // --- VALIDACIÓN DE SEGURIDAD INICIAL ---
            // 1. Consultar matriz de permisos real en BD
            const acceso = await this.verificarAccesoModulo("CLIENTES");

            if (!acceso) {
                // Bloqueo duro de UI si no hay permiso de lectura
                document.querySelector('.app-main-container').innerHTML =
                    '<div class="alert-denied">⛔ ACCESO DENEGADO: No tiene permisos para visualizar este módulo.</div>';
                return;
            }

            // 2. Configurar Interfaz (Ocultar botones según permisos)
            this.aplicarSeguridadVisual();

            // 3. Cargar listado de registros
            this.cargarClientes();
            this.setupEventListeners();

        } catch (error) {
            console.error('[SYSTEM ERROR] Fallo inicializando módulo Clientes:', error);
            alert('Error de conexión al validar permisos.');
        }
    },

    verificarAccesoModulo: async function (nombreModulo) {
        // Reset preventivo
        this.permisosActuales = { ver: false, crear: false, editar: false, eliminar: false };

        // [ADMIN BYPASS] Validación especial para Superusuarios y Root
        // Homologado con usuarios-service.js: Si el rol es ADMIN, acceso total inmediato.
        // Esto permite que el usuario 'root' (que no tiene registro en BD de usuarios) entre.
        const rol = (this.currentUser.rol || this.currentUser.rolNombre || this.currentUser.nombreRol || '').toUpperCase();

        // Verifica variaciones de ADMIN o si es explícitamente el usuario root del sistema (hardcodeado en login)
        if (rol === 'ADMIN' || rol === 'ADMINISTRADOR' || this.currentUser.usuario === 'root') {
            console.log("[SECURITY] Admin/Root Access Granted (Bypass ACL)");
            this.permisosActuales = { ver: true, crear: true, editar: true, eliminar: true };
            return true;
        }

        try {
            // Consumo de Endpoint de Seguridad Local de Usuarios
            const idUsuario = this.currentUser.userId || this.currentUser.id || this.currentUser.idUsuario;
            if (!idUsuario) return false;

            const response = await fetch(`${this.usuariosApiUrl}/permisos-usuario/${idUsuario}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (response.ok) {
                const listaPermisos = await response.json();

                // Buscar configuración específica para este módulo
                const p = listaPermisos.find(x => x.modulo === nombreModulo || x.nombreModulo === nombreModulo);

                if (p) {
                    this.permisosActuales = {
                        ver: !!p.puede_ver,
                        crear: !!p.puede_crear,
                        editar: !!p.puede_editar,
                        eliminar: !!p.puede_eliminar
                    };
                    return this.permisosActuales.ver;
                }
            }
        } catch (e) {
            console.error("Fallo al validar ACL:", e);
        }
        return false;
    },

    aplicarSeguridadVisual: function () {
        const btnNuevo = document.getElementById('btnNuevo');
        // Regla: Si no puede crear, ocultar botón Nuevo
        if (btnNuevo) {
            btnNuevo.style.display = this.permisosActuales.crear ? 'inline-flex' : 'none';
        }
    },

    setupEventListeners: function () {
        // Búsqueda (Input en tiempo real - E2-002)
        document.getElementById('btnBuscar').addEventListener('input', () => {
            // Pequeño debounce implícito por naturaleza de UI o agregar timeout si es necesario
            this.cargarClientes();
        });

        // Filtro RFC (Live Search)
        // Antes 'change', ahora 'input' para filtrar mientras se escribe
        document.getElementById('filterRfc').addEventListener('input', () => {
            this.cargarClientes(); // Aplica debounce natural del usuario o agregar lodash.debounce si crece
        });

        // Botones de Estatus (Toggle)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                this.cargarClientes();
            });
        });
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
            // E2-004: Click en toda la fila abre detalle
            tr.onclick = () => this.verDetalle(c.idPaciente);

            // E2-003: Estilos condicionales y Homologación con Usuarios
            // Convertimos estatus a formato Capitalizado para visualización (ACTIVO -> Activo)
            const estatusRaw = c.estatus || 'INACTIVO';
            const isActivo = estatusRaw === 'ACTIVO';

            // Clases para semáforo (punto verde/rojo)
            const statusClass = isActivo ? 'activo' : 'inactivo';
            const statusText = isActivo ? 'Activo' : 'Inactivo';

            // Botón estilo Usuarios (btn-secondary pequeño)

            tr.innerHTML = `
                <td>
                    <div class="font-bold text-gray-800">${c.nombre || c.razonSocial || 'Sin Nombre'}</div>
                    <div class="text-sm text-gray-500">${c.tipoPersona}</div>
                </td>
                <td class="font-mono text-sm">${c.rfc || '--'}</td>
                <td>${c.telefono || c.email || '<span class="text-gray-400">Sin contacto</span>'}</td>
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

    // ==========================================
    // LÓGICA DE FORMULARIO (CRUD)
    // ==========================================

    nuevo: function () {
        // --- VALIDACIÓN DE SEGURIDAD ---
        if (!this.permisosActuales.crear) {
            this.mostrarToast('⛔ ACCESO DENEGADO: No tiene permisos para crear clientes.', 'error');
            return;
        }
        this.limpiarFormulario();
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
            document.getElementById('detailNombre').value = cliente.nombre;
            document.getElementById('detailTipoPersona').value = cliente.tipoPersona || 'FISICA';
            document.getElementById('detailRfc').value = cliente.rfc || '';
            document.getElementById('detailRegimen').value = cliente.regimenFiscal || '';
            document.getElementById('detailUsoCfdi').value = cliente.usoCfdi || '';
            document.getElementById('detailDomicilio').value = cliente.domicilioFiscal || '';
            document.getElementById('detailTelefono').value = cliente.telefono || '';
            document.getElementById('detailCorreo').value = cliente.email || '';
            document.getElementById('detailEstatus').value = cliente.estatus || 'ACTIVO';

            // 2. Control de Edición (Solo Lectura vs Editable)
            const inputs = document.querySelectorAll('.usuario-detail input, .usuario-detail select, .usuario-detail textarea');
            const btnGuardar = document.getElementById('btnGuardar');

            const soloLectura = !this.permisosActuales.editar;
            inputs.forEach(i => i.disabled = soloLectura);

            // Ocultar botón guardar si es solo lectura
            btnGuardar.style.display = soloLectura ? 'none' : 'inline-flex';

        } catch (error) {
            this.mostrarToast('Error cargando detalle: ' + error.message, 'error');
        }
    },

    guardar: async function () {
        const id = document.getElementById('detailIdCliente').value;
        const accion = id ? 'EDICIÓN' : 'CREACIÓN';

        // --- VALIDACIÓN DE SEGURIDAD ---
        if (accion === 'EDICIÓN' && !this.permisosActuales.editar) {
            this.mostrarToast('⛔ No tiene permisos para editar clientes.', 'error');
            return;
        }
        if (accion === 'CREACIÓN' && !this.permisosActuales.crear) {
            this.mostrarToast('⛔ No tiene permisos para rear clientes.', 'error');
            return;
        }

        const method = id ? 'PUT' : 'POST';
        const url = id ? `${this.apiUrl}/${id}` : this.apiUrl;

        // 1. Construir Objeto DTO
        const cliente = {
            nombre: document.getElementById('detailNombre').value,
            tipoPersona: document.getElementById('detailTipoPersona').value,
            rfc: document.getElementById('detailRfc').value,
            regimenFiscal: document.getElementById('detailRegimen').value,
            usoCfdi: document.getElementById('detailUsoCfdi').value,
            domicilioFiscal: document.getElementById('detailDomicilio').value,
            telefono: document.getElementById('detailTelefono').value,
            email: document.getElementById('detailCorreo').value,
            estatus: document.getElementById('detailEstatus').value
        };

        // 2. Validaciones de Negocio (Frontend)
        if (!cliente.nombre) return alert('El Nombre / Razón Social es obligatorio.');

        // Regla Fiscal: RFC Física siempre 13 caracteres
        if (cliente.rfc && cliente.tipoPersona === 'FISICA' && cliente.rfc.length !== 13)
            return alert('RFC de Persona Física debe contener exactamente 13 caracteres.');

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

            this.mostrarToast('Cliente guardado exitosamente.', 'success');
            this.limpiarFormulario();
            this.cargarClientes();

        } catch (error) {
            alert(error.message);
        }
    },

    limpiarFormulario: function () {
        document.getElementById('detailIdCliente').value = '';
        document.getElementById('detailNombre').value = '';
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
    },

    mostrarToast: function (mensaje, tipo = 'info') {
        const container = document.getElementById('toastContainer');
        const toast = document.createElement('div');
        toast.className = `toast toast-${tipo}`;
        toast.innerText = mensaje;
        container.appendChild(toast);
        setTimeout(() => toast.remove(), 3000);
    },

    // Placeholder para funcionalidad futura (Etapa 4)

};

// Inicializar al cargar
document.addEventListener('DOMContentLoaded', () => {
    ClientesService.init();
});
