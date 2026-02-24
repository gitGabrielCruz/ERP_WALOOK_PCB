/*
============================================================
Nombre del archivo : usuarios-service.js
Ruta              : omcgc/frontend/assets/js/usuarios-service.js
Tipo              : Frontend (Script JS)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Gestionar la interacción de la pantalla de Usuarios, incluyendo CRUD completo,
administración de la matriz de permisos (RBAC) y configuración segura de SMTP.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M05-01 (Gestión de Usuarios):
   - Altas, Bajas y Cambios de usuarios del sistema.
2. HU-M05-03 (Asignación de Roles y Permisos):
   - Configuración granular de ACLs por módulo.
3. RNF-02 (Seguridad):
   - Validación de sesión y privilegios antes de renderizar UI.
============================================================
*/

const UsuariosService = {
    // API Endpoints
    apiUrl: AppConfig.getEndpoint('usuarios'),
    smtpUrl: AppConfig.getEndpoint('smtp'),

    // Estado local
    currentUser: null,
    editingUserId: null,
    listaUsuarios: [],
    listaRoles: [],
    listaModulos: [],
    currentLogs: [], // Almacén para los logs filtrados actuales

    // Permisos del usuario logueado sobre ESTE módulo (Usuarios)
    permisosActuales: {
        ver: false,
        crear: false,
        editar: false,
        eliminar: false
    },

    init: async function () {
        // SRP: Delegar obtención de usuario a AuthService
        this.currentUser = AuthService.getCurrentUser();
        if (!this.currentUser) {
            window.location.href = 'login.html';
            return;
        }

        // --- VALIDACIÓN DE SEGURIDAD (Permiso de Acceso) ---
        const acceso = await this.verificarAccesoModulo("USUARIOS, ROLES Y PERMISOS");
        if (!acceso) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para ver el módulo de Usuarios.", "Contacte a su administrador.", "", () => {
                window.location.href = 'menu.html';
            });
            return;
        }

        // 1. Cargar Catálogos (Roles y Módulos)
        await Promise.all([this.cargarRoles(), this.cargarModulos()]);

        // 2. Cargar Usuarios
        this.cargarUsuarios();

        // 3. Aplicar seguridad inicial (Botón Nuevo)
        this.aplicarSeguridadGlobal();

        this.verificarEstadoSmtp();
        this.setupEventListeners();
    },

    /**
     * Consulta la Lista de Control de Acceso (ACL).
     * Ahora delega la validación maestra a AuthService.
     */
    verificarAccesoModulo: async function (nombreModulo) {
        // Reset defensivo
        this.permisosActuales = { ver: false, crear: false, editar: false, eliminar: false };

        // [SRP] Uso de AuthService para Admin Bypass y validación central
        if (AuthService.esAdmin()) {
            console.log("[SECURITY] Admin/Root Access Granted via AuthService");
            this.permisosActuales = { ver: true, crear: true, editar: true, eliminar: true };
            return true;
        }

        // ... Continúa lógica específica de fetch si no es Admin ...

        try {
            const idUsuario = this.currentUser.userId || this.currentUser.id || this.currentUser.idUsuario;
            if (!idUsuario) return false;

            // Petición segura al endpoint de permisos (Con Token)
            const response = await fetch(`${this.apiUrl}/permisos-usuario/${idUsuario}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (response.ok) {
                const listaPermisos = await response.json();

                // Filtrado por módulo
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
            console.error("[SECURITY] Fallo en verificación de ACLs:", e);
        }
        return false; // Fail-Safe: Denegar acceso ante error
    },

    aplicarSeguridadGlobal: function () {
        const btnNuevo = document.querySelector('button[onclick*="nuevo"]');
        if (btnNuevo) {
            btnNuevo.style.display = this.permisosActuales.crear ? 'inline-block' : 'none';
        }
    },

    // ==========================================
    // LÓGICA DEL FORMULARIO
    // ==========================================

    cargarDetalleUsuario: function (usuario) {
        this.editingUserId = usuario.id;

        // Llenar campos
        const fId = document.getElementById('detailIdUsuario');
        const fUser = document.getElementById('detailUsuario');
        const fNombre = document.getElementById('detailNombre');
        const fCorreo = document.getElementById('detailCorreo');
        const fRol = document.getElementById('detailRol');
        const fEstatus = document.getElementById('detailEstatus');
        const btnGuardar = document.getElementById('btnGuardar');
        const btnReset = document.getElementById('btnResetPassword');

        fId.value = usuario.id;
        fUser.value = usuario.id;
        fNombre.value = usuario.nombre;
        fCorreo.value = usuario.correo || usuario.email;

        // Rol
        const rolId = usuario.rolId;
        if (rolId) {
            fRol.value = rolId;
            this.cargarPermisosUsuario(usuario.id);
        } else {
            fRol.value = "";
            this.renderTablaPermisosVacia();
        }

        // --- Lógica de Seguridad de Rol (Visual) ---
        // Al editar, el rol inicia BLOQUEADO.
        const chkRol = document.getElementById('chkHabilitarRol');
        const txtRol = document.getElementById('txtEstadoRol');

        if (chkRol) {
            chkRol.checked = false;
            chkRol.closest('div').style.display = 'block'; // Asegurar visible
        }
        if (txtRol) {
            txtRol.textContent = 'DESACTIVADO';
            txtRol.style.color = '#EF4444';
        }
        fRol.disabled = true; // Bloqueo inicial

        // Estatus
        fEstatus.value = (usuario.estatus === 'activo' || usuario.activo === true || usuario.activo === 1) ? 'activo' : 'inactivo';

        // --- SEGURIDAD: MODO EDICIÓN ---
        if (this.permisosActuales.editar) {
            // Habilitar campos generales
            fNombre.disabled = false;
            fCorreo.disabled = false;

            // EL ROL SE MANTIENE BLOQUEADO HASTA ACTIVAR CHECKBOX
            // fRol.disabled = false; <--- ELIMINADO
            if (chkRol) chkRol.disabled = false; // Permitir usar el checkbox

            btnGuardar.style.display = 'inline-block';
            btnReset.disabled = false;

            // Seguridad Estatus (Eliminar)
            // Solo si tiene permiso ELIMINAR puede cambiar el estatus (dar de baja)
            const campoEstatus = document.getElementById('detailEstatus');
            if (this.permisosActuales.eliminar) {
                campoEstatus.disabled = false;
            } else {
                campoEstatus.disabled = true;
                campoEstatus.title = "Se requiere permiso de eliminar para modificar el estatus";
            }
        } else {
            // Modo Solo Lectura
            fNombre.disabled = true;
            fCorreo.disabled = true;
            fRol.disabled = true;
            if (chkRol) chkRol.disabled = true; // Bloquear checkbox

            fEstatus.disabled = true;
            btnGuardar.style.display = 'none'; // Ocultar guardar
            btnReset.disabled = true;
            this.showToast("Modo de solo lectura: No tiene permisos para editar usuarios", "info");
        }
    },

    cargarPermisosUsuario: async function (userId) {
        // Asignar permisos PERO primero validar si pueden editarse
        const inputs = document.querySelectorAll(`input[id^="perm_"]`);
        const canEditPerms = this.permisosActuales.editar;

        inputs.forEach(chk => {
            chk.checked = false;
            chk.disabled = !canEditPerms; // Bloquear checkboxes si no hay permiso de editar
        });

        if (!userId) return;

        try {
            const res = await fetch(`${this.apiUrl}/permisos-usuario/${userId}`);
            if (res.ok) {
                const permisos = await res.json();
                permisos.forEach(p => {
                    if (p.puede_ver) document.getElementById(`perm_${p.id_modulo}_ver`).checked = true;
                    if (p.puede_crear) document.getElementById(`perm_${p.id_modulo}_crear`).checked = true;
                    if (p.puede_editar) document.getElementById(`perm_${p.id_modulo}_editar`).checked = true;
                    if (p.puede_eliminar) document.getElementById(`perm_${p.id_modulo}_eliminar`).checked = true;
                });
            }
        } catch (e) { console.error(e); }
    },

    nuevo: function () {
        if (!this.permisosActuales.crear) {
            this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para crear usuarios.", "error");
            return;
        }

        this.editingUserId = null;
        this.limpiarFormulario();
        document.getElementById('detailUsuario').placeholder = "Se generará automáticamente";
        document.getElementById('btnResetPassword').disabled = true;

        // Habilitar todo para creación
        document.getElementById('detailNombre').disabled = false;
        document.getElementById('detailCorreo').disabled = false;
        document.getElementById('detailRol').disabled = false;
        document.getElementById('detailEstatus').disabled = false; // Al crear, estatus default activo
        document.getElementById('btnGuardar').style.display = 'inline-block';

        // Habilitar checks
        document.querySelectorAll(`input[id^="perm_"]`).forEach(c => c.disabled = false);
    },

    limpiarFormulario: function () {
        document.getElementById('detailIdUsuario').value = '';
        document.getElementById('detailUsuario').value = '';
        document.getElementById('detailNombre').value = '';
        document.getElementById('detailCorreo').value = '';
        document.getElementById('detailRol').value = '';
        document.getElementById('detailEstatus').value = 'activo';

        // Limpiar checkboxes
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(accion => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${accion}`);
                if (chk) {
                    chk.checked = false;
                    chk.disabled = false; // Reset visual, 'nuevo' o 'cargar' decidirán estado final
                }
            });
        });
    },

    _collectPermissions: function () {
        return this.listaModulos.map(mod => ({
            id_modulo: mod.id_modulo,
            puede_ver: document.getElementById(`perm_${mod.id_modulo}_ver`).checked,
            puede_crear: document.getElementById(`perm_${mod.id_modulo}_crear`).checked,
            puede_editar: document.getElementById(`perm_${mod.id_modulo}_editar`).checked,
            puede_eliminar: document.getElementById(`perm_${mod.id_modulo}_eliminar`).checked
        }));
    },

    guardar: async function () {
        // --- VALIDACIÓN DE SEGURIDAD (Ejecución) ---
        if (this.editingUserId) {
            // Es EDICIÓN
            if (!this.permisosActuales.editar) {
                this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para modificar usuarios.", "error");
                return;
            }
            // Validar ELIMINACIÓN (Cambio de Estatus)
            // ¿Cómo sabemos si cambió estatus? (Optimizacion: asumir validación UI o leer valor previo, 
            // pero si UI bloqueó, el valor no cambió. Si hackearon UI, backend debería validar, 
            // aqui validamos intento frontend).
            // Simplificación: Si intenta guardar 'inactivo' y no tiene permiso eliminar, bloquear.
            const estatusVal = document.getElementById('detailEstatus').value;
            if (estatusVal === 'inactivo' && !this.permisosActuales.eliminar) {
                this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para desactivar (eliminar) usuarios.", "error");
                return;
            }

        } else {
            // Es CREACIÓN
            if (!this.permisosActuales.crear) {
                this.showToast("⛔ ACCESO DENEGADO: No tiene permiso para crear usuarios.", "error");
                return;
            }
        }

        // Validaciones básicas
        const nombre = document.getElementById('detailNombre').value;
        const email = document.getElementById('detailCorreo').value;
        const rolId = document.getElementById('detailRol').value;
        const estatusVal = document.getElementById('detailEstatus').value;

        if (!nombre || !email || !rolId) {
            this.showToast("Por favor complete todos los campos obligatorios", "warning");
            return;
        }

        // Buscar el NOMBRE del rol (para compatibilidad legacy si fuera necesario)
        const rolObj = this.listaRoles.find(r => r.id_rol === rolId);
        const rolNombre = rolObj ? rolObj.nombre : rolId;

        const usuarioData = {
            id: this.editingUserId,
            nombre: nombre,
            correo: email,
            rolNombre: rolNombre,
            estatus: estatusVal,
            permisos: this._collectPermissions() // Enviamos todo en un solo paquete
        };

        const method = this.editingUserId ? 'PUT' : 'POST';
        const url = this.editingUserId ? `${this.apiUrl}/${this.editingUserId}` : this.apiUrl;

        try {
            // 1. Guardar Todo (Usuario + Rol + Permisos en una sola acción)
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}`
                },
                body: JSON.stringify(usuarioData)
            });

            if (response.ok) {
                const respData = await response.json();
                if (respData.data && respData.data.passwordTemp) {
                    alert(`✅ USUARIO CREADO\n\nCopie la contraseña temporal:\n\n${respData.data.passwordTemp}\n\n(También se ha enviado por correo)`);
                } else {
                    this.showToast(msg + " (Permisos actualizados)", "success");
                }

                this.cargarUsuarios();
                if (!this.editingUserId) this.limpiarFormulario();
            } else {
                let errorMsg = "Error al guardar usuario";
                try {
                    const errorData = await response.json();
                    if (errorData.message) errorMsg = errorData.message;
                } catch (e) { }
                this.showToast(errorMsg, "error");
            }
        } catch (error) {
            console.error(error);
            this.showToast("Error de conexión", "error");
        }
    },

    setupEventListeners: function () {
        // 1. Selector Rol (Cargar permisos default)
        document.getElementById('detailRol').addEventListener('change', (e) => {
            this.cargarPermisosRol(e.target.value);
        });

        // 1.1 Checkbox Seguridad Rol
        const chkRol = document.getElementById('chkHabilitarRol');
        if (chkRol) {
            chkRol.addEventListener('change', (e) => {
                const activo = e.target.checked;
                const txt = document.getElementById('txtEstadoRol');
                const select = document.getElementById('detailRol');

                if (activo) {
                    txt.textContent = 'ACTIVADO';
                    txt.style.color = '#10B981'; // Verde
                    select.disabled = false;

                    // Aviso de Seguridad
                    MessageService.mostrar(
                        1,
                        "Modificación de Roles Activada",
                        "Al cambiar el Rol, se restablecerán los permisos por defecto para el rol seleccionado.",
                        "YA PUEDE MODIFICAR LOS PERMISOS POR MÓDULO PARA ESTE USUARIO"
                    );
                } else {
                    txt.textContent = 'DESACTIVADO';
                    txt.style.color = '#EF4444'; // Rojo
                    select.disabled = true;
                }
            });
        }

        // 1. Buscador (Live Search - Mejorado a input)
        document.getElementById('searchInput').addEventListener('input', () => {
            this.filtrarUsuarios();
        });

        // 2. Filtro Rol
        document.getElementById('filterRol').addEventListener('change', () => {
            this.filtrarUsuarios();
        });

        // 3. Filtro Estatus (Botones toggle)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                // Quitar active a todos
                document.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                // Poner active al clickeado
                e.target.classList.add('active');
                this.filtrarUsuarios();
            });
        });

        // 4. Navegación por Teclado (Flechas Arriba/Abajo)
        document.addEventListener('keydown', (e) => {
            // Ignorar si el foco está en un campo de texto
            if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') return;

            const selected = document.querySelector('.selected-row');
            if (!selected) return;

            const rows = Array.from(document.querySelectorAll('#usuariosTableBody tr'));
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
                rows[nextIndex].click();
                rows[nextIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        });

        // 5. EVENTOS BITÁCORA (Live Search & Auto-filter)
        // Siguiendo la 'Biblia' (Patrón WALOOK): Búsqueda en tiempo real sin botón
        ['bitacoraDesde', 'bitacoraHasta', 'bitacoraUsuario'].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.addEventListener('change', () => this.cargarBitacora());
        });

        const inputBuscar = document.getElementById('bitacoraBuscar');
        if (inputBuscar) {
            inputBuscar.addEventListener('input', () => {
                // Debounce simple para no saturar el backend de descifrado
                clearTimeout(this.bitacoraDebounce);
                this.bitacoraDebounce = setTimeout(() => {
                    this.cargarBitacora();
                }, 400);
            });
        }
    },

    filtrarUsuarios: function () {
        const searchText = document.getElementById('searchInput').value.toLowerCase();
        const roleFilter = document.getElementById('filterRol').value;

        // Obtener estatus activo del botón
        const statusBtn = document.querySelector('.status-btn.active');
        const statusFilter = statusBtn ? statusBtn.dataset.status : 'activo';

        const filtrados = this.listaUsuarios.filter(u => {
            // 1. Filtro Texto (Nombre, Email, ID)
            const matchesText = (u.nombre || '').toLowerCase().includes(searchText) ||
                (u.correo || u.email || '').toLowerCase().includes(searchText) ||
                (u.usuario || '').toLowerCase().includes(searchText);

            // 2. Filtro Rol (Coincidencia exacta de ID o Nombre)
            // Asumimos que el value del select es el ID del rol
            const uRolId = u.rolId || u.rol || '';
            const uRolNombre = u.rolNombre || u.nombreRol || '';

            const matchesRole = roleFilter === "" ||
                uRolId === roleFilter ||
                uRolNombre === roleFilter;

            // 3. Filtro Estatus
            const uStatus = (u.estatus === 'activo' || u.activo === true || u.activo === 1) ? 'activo' : 'inactivo';
            const matchesStatus = uStatus === statusFilter;

            return matchesText && matchesRole && matchesStatus;
        });

        this.renderTabla(filtrados);
    },

    // ==========================================
    // CARGA DE CATÁLOGOS DINÁMICOS
    // ==========================================
    cargarRoles: async function () {
        try {
            const res = await fetch(`${this.apiUrl}/roles`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (res.ok) {
                this.listaRoles = await res.json();
                const select = document.getElementById('detailRol');
                const filter = document.getElementById('filterRol');

                // Limpiar (manteniendo la opcion default)
                select.innerHTML = '<option value="">Seleccionar...</option>';
                filter.innerHTML = '<option value="">Todos</option>';

                this.listaRoles.forEach(rol => {
                    // Llenar Select Detalle
                    const opt = document.createElement('option');
                    opt.value = rol.id_rol; // Usamos ID real
                    opt.textContent = rol.nombre;
                    select.appendChild(opt);

                    // Llenar Filtro
                    const optF = document.createElement('option');
                    optF.value = rol.id_rol;
                    optF.textContent = rol.nombre;
                    filter.appendChild(optF);
                });
            }
        } catch (e) {
            console.error("Error cargando roles", e);
        }
    },

    cargarModulos: async function () {
        try {
            const res = await fetch(`${this.apiUrl}/modulos`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });
            if (res.ok) {
                this.listaModulos = await res.json();
                this.renderTablaPermisosVacia();
            }
        } catch (e) {
            console.error("Error cargando modulos", e);
        }
    },

    renderTablaPermisosVacia: function () {
        const tbody = document.getElementById('permisosTableBody');
        tbody.innerHTML = '';

        this.listaModulos.forEach(mod => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${mod.nombre}</td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_ver" disabled></td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_crear" disabled></td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_editar" disabled></td>
                <td class="text-center"><input type="checkbox" id="perm_${mod.id_modulo}_eliminar" disabled></td>
            `;
            tbody.appendChild(tr);
        });
    },

    // ==========================================
    // DEFINICIÓN DE ROLES ESTÁNDAR (FALLBACK)
    // ==========================================
    // Si la BD no tiene permisos definidos para un rol, se usa esta matriz en memoria.
    // Al guardar el usuario, estos permisos se persistirán en la tabla 'permisos_usuario'.
    MATRIZ_DEFAULT: {
        'ADMIN': ['*'],
        'VENDEDOR': ['VENTAS', 'CLIENTES', 'PACIENTES', 'AGENDA CITAS', 'EXPEDIENTE PACIENTE', 'INVENTARIO'],
        'OPTOMETRISTA': ['PACIENTES', 'AGENDA CITAS', 'EXPEDIENTE PACIENTE', 'INVENTARIO'],
        'CAJA': ['VENTAS', 'CLIENTES', 'FACTURACION CFDI'],
        'TALLER': ['TALLER OT', 'RECEPCION Y DEVOLUCION', 'INVENTARIO', 'ORDENES DE COMPRA']
    },

    /**
     * Carga y renderiza los permisos estándar asociados a un Rol.
     * @param {string} rolId - UUID del rol seleccionado.
     */
    cargarPermisosRol: async function (rolId) {
        // Validación de seguridad visual
        const canEdit = this.permisosActuales.editar || this.permisosActuales.crear;

        // 1. Reset de UI (Limpiar checks)
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(act => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${act}`);
                if (chk) {
                    chk.checked = false;
                    chk.disabled = !canEdit;
                }
            });
        });

        if (!rolId) return;

        // Obtener nombre del rol para buscar en matriz default
        const rolObj = this.listaRoles.find(r => r.id_rol === rolId);
        const rolNombre = rolObj ? rolObj.nombre : '';

        try {
            // 2. Intentar cargar desde Backend (BD)
            const response = await fetch(`${this.apiUrl}/permisos/${rolId}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (response.ok) {
                const permisos = await response.json();

                if (permisos.length > 0) {
                    // CASO A: Existen permisos en BD -> Usarlos
                    permisos.forEach(p => {
                        if (p.puede_ver) document.getElementById(`perm_${p.id_modulo}_ver`).checked = true;
                        if (p.puede_crear) document.getElementById(`perm_${p.id_modulo}_crear`).checked = true;
                        if (p.puede_editar) document.getElementById(`perm_${p.id_modulo}_editar`).checked = true;
                        if (p.puede_eliminar) document.getElementById(`perm_${p.id_modulo}_eliminar`).checked = true;
                    });
                } else {
                    // CASO B: BD vacía -> Usar Matriz Default (Fallback JS)
                    this._aplicarPermisosDefault(rolNombre);
                }
            }
        } catch (e) {
            console.error("Error cargando permisos de rol:", e);
            // Ante error, intentar fallback también
            this._aplicarPermisosDefault(rolNombre);
        }
    },

    _aplicarPermisosDefault: function (rolNombre) {
        console.log(`[INFO] Aplicando matriz default para rol: ${rolNombre}`);
        const config = this.MATRIZ_DEFAULT[rolNombre] || [];
        const esAdmin = rolNombre === 'ADMIN' || rolNombre === 'Administrador';

        this.listaModulos.forEach(mod => {
            // Si es Admin o el módulo está en la lista de permitidos
            if (esAdmin || config.includes(mod.nombre)) {

                // Lógica simple de asignación default (Ver/Crear/Editar)
                // Eliminar se reserva solo para Admin generalmente o lógica especifica
                const chkVer = document.getElementById(`perm_${mod.id_modulo}_ver`);
                const chkCrear = document.getElementById(`perm_${mod.id_modulo}_crear`);
                const chkEditar = document.getElementById(`perm_${mod.id_modulo}_editar`);
                const chkEliminar = document.getElementById(`perm_${mod.id_modulo}_eliminar`);

                if (chkVer) chkVer.checked = true;

                // Admin tiene todo
                if (esAdmin) {
                    if (chkCrear) chkCrear.checked = true;
                    if (chkEditar) chkEditar.checked = true;
                    if (chkEliminar) chkEliminar.checked = true;
                } else {
                    // Roles operativos: Crear/Editar en sus módulos, pero Eliminar restringido
                    // Excepción: Inventario suele ser solo lectura para la mayoría salvo Almacén, pero aqui simplificamos
                    if (chkCrear) chkCrear.checked = true;
                    if (chkEditar) chkEditar.checked = true;
                    // Eliminar default false
                }
            }
        });
    },

    /**
     * Persiste la configuración de la matriz de permisos para un Rol.
     * Esta acción actualiza el estándar de seguridad para todos los usuarios de ese rol.
     */
    guardarPermisos: async function () {
        const rolId = document.getElementById('detailRol').value;
        if (!rolId) {
            this.showToast("Seleccione un rol para guardar sus permisos", "warning");
            return;
        }

        MessageService.mostrar(
            2,
            "Actualizar Permisos de Rol",
            `¿Actualizar la definición de permisos para el ROL ${rolId}?`,
            "Esto afectará a TODOS los usuarios con este rol.",
            "",
            async () => {
                // Callback de confirmación
                const payload = this.listaModulos.map(mod => ({
                    id_modulo: mod.id_modulo,
                    puede_ver: document.getElementById(`perm_${mod.id_modulo}_ver`).checked,
                    puede_crear: document.getElementById(`perm_${mod.id_modulo}_crear`).checked,
                    puede_editar: document.getElementById(`perm_${mod.id_modulo}_editar`).checked,
                    puede_eliminar: document.getElementById(`perm_${mod.id_modulo}_eliminar`).checked
                }));

                try {
                    const response = await fetch(`${this.apiUrl}/permisos/${rolId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}`
                        },
                        body: JSON.stringify(payload)
                    });

                    if (response.ok) {
                        this.showToast("Matriz de permisos actualizada correctamente", "success");
                    } else {
                        this.showToast("Error al actualizar permisos", "error");
                    }
                } catch (e) {
                    this.showToast("Error de conexión", "error");
                }
            }
        );
    },

    // =========================================================
    // GESTIÓN DE USUARIOS (CRUD)
    // =========================================================

    cargarUsuarios: async function () {
        try {
            const response = await fetch(this.apiUrl, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (response.ok) {
                const usuarios = await response.json();
                this.listaUsuarios = usuarios; // Guardar cache
                this.filtrarUsuarios(); // Aplicar filtros visuales actuales
            } else {
                console.error("Error cargando usuarios: " + response.status);
            }
        } catch (error) {
            console.error("Error de conexión:", error);
            this.showToast("Error de conexión con el servidor", "error");
        }
    },

    ver: function (id) {
        const usuario = this.listaUsuarios.find(u => (u.id || u.idUsuario) === id);
        if (usuario) {
            this.cargarDetalleUsuario(usuario);
        }
    },

    renderTabla: function (usuarios) {
        const tbody = document.getElementById('usuariosTableBody');
        tbody.innerHTML = '';

        if (usuarios.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay usuarios registrados</td></tr>';
            return;
        }

        usuarios.forEach(u => {
            const tr = document.createElement('tr');
            // Al hacer click en la fila, cargamos detalle y marcamos visualmente
            tr.onclick = (e) => {
                // 1. Gestión Visual (Highlight permanente)
                const allRows = document.querySelectorAll('#usuariosTableBody tr');
                allRows.forEach(row => row.classList.remove('selected-row'));
                e.currentTarget.classList.add('selected-row');

                // 2. Cargar Detalle
                this.cargarDetalleUsuario(u);
            };

            const estatusClass = (u.estatus === 'activo' || u.activo) ? 'activo' : 'inactivo';
            const estatusText = (u.estatus === 'activo' || u.activo) ? 'Activo' : 'Inactivo';

            // Mostrar Rol TAL CUAL viene de BD (sin alias)
            const rolCode = u.rolNombre || u.nombreRol;
            const rolDisplay = rolCode || 'Sin Asignar';
            const uid = u.id || u.idUsuario;

            tr.innerHTML = `
                <td>
                    <div style="font-weight: 500;">${u.nombre || 'Sin Nombre'}</div>
                    <div style="font-size: 0.75rem; color: #6B7280;">ID: ${uid}</div>
                </td>
                <td>${u.correo || u.email || 'Sin correo'}</td>
                <td><span class="badge-rol">${rolDisplay}</span></td>
                <td><span class="status-indicator ${estatusClass}"></span>${estatusText}</td>
                <td>
                   <button class="btn-secondary" style="padding: 4px 8px; font-size: 0.75rem;" onclick="event.stopPropagation(); UsuariosService.ver('${uid}')">Ver</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    },



    cargarPermisosUsuario: async function (userId) {
        // Habilitar checkboxes para edición (siempre)
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(act => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${act}`);
                if (chk) { chk.disabled = false; chk.checked = false; }
            });
        });

        if (!userId) return;

        try {
            const res = await fetch(`${this.apiUrl}/permisos-usuario/${userId}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });
            if (res.ok) {
                const permisos = await res.json();
                permisos.forEach(p => {
                    if (p.puede_ver) document.getElementById(`perm_${p.id_modulo}_ver`).checked = true;
                    if (p.puede_crear) document.getElementById(`perm_${p.id_modulo}_crear`).checked = true;
                    if (p.puede_editar) document.getElementById(`perm_${p.id_modulo}_editar`).checked = true;
                    if (p.puede_eliminar) document.getElementById(`perm_${p.id_modulo}_eliminar`).checked = true;
                });
            }
        } catch (e) {
            console.error("Error cargando permisos de usuario", e);
        }
    },

    nuevo: function () {
        if (!this.permisosActuales.crear) {
            this.showToast("⛔ No tiene permiso para crear usuarios", "error");
            return;
        }

        this.editingUserId = null;
        this.limpiarFormulario();
        document.getElementById('detailUsuario').placeholder = "Se generará automáticamente";
        document.getElementById('btnResetPassword').disabled = true;

        // Habilitar campos explícitamente por si venían de un estado disabled
        document.getElementById('detailNombre').disabled = false;
        document.getElementById('detailCorreo').disabled = false;

        // Lógica de Rol para Nuevo: Siempre habilitado, Checkbox oculto
        const chkRolContainer = document.getElementById('chkHabilitarRol').closest('div');
        if (chkRolContainer) chkRolContainer.style.display = 'none';

        document.getElementById('detailRol').disabled = false;
        document.getElementById('detailEstatus').disabled = false;
        document.getElementById('btnGuardar').style.display = 'inline-block';
    },

    limpiarFormulario: function () {
        document.getElementById('detailIdUsuario').value = '';
        document.getElementById('detailUsuario').value = '';
        document.getElementById('detailNombre').value = '';
        document.getElementById('detailCorreo').value = '';
        document.getElementById('detailRol').value = '';
        document.getElementById('detailEstatus').value = 'activo';

        // Limpiar checkboxes
        this.listaModulos.forEach(mod => {
            ['ver', 'crear', 'editar', 'eliminar'].forEach(accion => {
                const chk = document.getElementById(`perm_${mod.id_modulo}_${accion}`);
                if (chk) chk.checked = false;
            });
        });
    },

    _collectPermissions: function () {
        return this.listaModulos.map(mod => ({
            id_modulo: mod.id_modulo,
            puede_ver: document.getElementById(`perm_${mod.id_modulo}_ver`).checked,
            puede_crear: document.getElementById(`perm_${mod.id_modulo}_crear`).checked,
            puede_editar: document.getElementById(`perm_${mod.id_modulo}_editar`).checked,
            puede_eliminar: document.getElementById(`perm_${mod.id_modulo}_eliminar`).checked
        }));
    },

    guardar: async function () {
        // --- VALIDACIÓN DE SEGURIDAD ROBUSTA ---
        // 1. Validar Acción Global (Crear vs Editar)
        if (this.editingUserId) {
            if (!this.permisosActuales.editar) {
                MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para modificar la información de usuarios.");
                return;
            }
        } else {
            if (!this.permisosActuales.crear) {
                MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para crear usuarios.");
                return;
            }
        }

        // 2. Validar Desactivación (Eliminar Lógico)
        const estatusVal = document.getElementById('detailEstatus').value;
        if (this.editingUserId && estatusVal === 'inactivo' && !this.permisosActuales.eliminar) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para desactivar (eliminar) usuarios.");
            return;
        }

        // Validaciones básicas
        const nombre = document.getElementById('detailNombre').value;
        const email = document.getElementById('detailCorreo').value;
        const rolId = document.getElementById('detailRol').value;
        // estatusVal ya obtenido arriba

        if (!nombre || !email || !rolId) {
            this.showToast("Por favor complete todos los campos obligatorios", "warning");
            return;
        }

        // Buscar el NOMBRE del rol (para compatibilidad legacy si fuera necesario)
        const rolObj = this.listaRoles.find(r => r.id_rol === rolId);
        const rolNombre = rolObj ? rolObj.nombre : rolId;

        const usuarioData = {
            id: this.editingUserId,
            nombre: nombre,
            correo: email,
            rolId: rolId,      // <--- CORRECCIÓN: Enviar ID para actualizar relación
            rolNombre: rolNombre, // Mantener por compatibilidad si se usa
            estatus: estatusVal
        };

        const method = this.editingUserId ? 'PUT' : 'POST';
        const url = this.editingUserId ? `${this.apiUrl}/${this.editingUserId}` : this.apiUrl;

        try {
            // 1. Guardar Usuario
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(usuarioData)
            });

            if (response.ok) {
                const respData = await response.json();

                // Obtener ID del usuario guardado/creado
                // Si es update, usamos editingUserId. Si es create, viene en respData.data.id
                let userId = this.editingUserId;
                if (!userId && respData.data && respData.data.id) {
                    userId = respData.data.id;
                }

                // 2. Guardar Permisos (Siempre, para asegurar que la matriz de UI se persista)
                if (userId) {
                    const permisosArr = this._collectPermissions();
                    await fetch(`${this.apiUrl}/permisos-usuario/${userId}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(permisosArr)
                    });
                }

                let msg = this.editingUserId ? "Usuario actualizado correctamente" : "Usuario creado exitosamente.";
                if (respData.data && respData.data.passwordTemp) {
                    MessageService.mostrar(4, "Usuario Creado", "Usuario creado exitosamente con contraseña temporal.", `Contraseña: ${respData.data.passwordTemp} (Cópiela ahora)`);
                } else {
                    this.showToast(msg + " (Permisos actualizados)", "success");
                }

                this.cargarUsuarios();
                if (!this.editingUserId) this.limpiarFormulario();
            } else {
                let errorMsg = "Error al guardar usuario";
                try {
                    const errorData = await response.json();
                    if (errorData.message) errorMsg = errorData.message;
                } catch (e) { }
                this.showToast(errorMsg, "error");
            }
        } catch (error) {
            console.error(error);
            this.showToast("Error de conexión", "error");
        }
    },

    resetPassword: async function () {
        if (!this.editingUserId) return;

        MessageService.mostrar(
            5,
            "Restablecer Contraseña",
            "¿Está seguro de querer restablecer la contraseña?",
            "Esta acción generará una nueva contraseña y la enviará al correo del usuario. El acceso actual será revocado.",
            "",
            async (motivo) => {
                // Mostrar spinner mientras el servidor procesa y envía el correo
                MessageService.procesando("Generando nueva clave y enviando correo...");

                try {
                    const response = await fetch(`${this.apiUrl}/${this.editingUserId}/reset-password`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ motivo: motivo })
                    });
                    const data = await response.json();

                    if (response.ok && data.tempPassword) {
                        MessageService.mostrar(
                            4,
                            "Seguridad Actualizada",
                            "La contraseña ha sido restablecida y enviada por correo.",
                            `Nueva contraseña temporal: <strong>${data.tempPassword}</strong><br><br>Cópiela y entréguela al usuario si el correo no llegara.`
                        );
                    } else {
                        MessageService.mostrar(1, "Error de Seguridad", "No se pudo completar el restablecimiento.", data.message || "Error desconocido");
                    }
                } catch (e) {
                    MessageService.mostrar(3, "Error de Conexión", "No se pudo contactar con el servidor de seguridad.", e.message);
                }
            }
        );
    },

    // =========================================================
    // CONFIGURACIÓN SMTP (SEGURIDAD)
    // =========================================================

    verificarEstadoSmtp: async function () {
        const btnSmtp = document.getElementById('btnSmtpConfig');
        const btnBitacora = document.getElementById('btnBitacora');

        // Validar rol de forma robusta
        const user = this.currentUser || MenuService.getUser();
        const rol = user ? (user.rol || user.rolNombre || user.nombreRol) : '';
        const isAdmin = rol === 'Administrador' || rol === 'ADMIN';

        // Controlar visibilidad de botones Admin
        if (isAdmin) {
            if (btnSmtp) btnSmtp.style.display = 'flex';
            if (btnBitacora) btnBitacora.style.display = 'flex';
        } else {
            if (btnSmtp) btnSmtp.style.display = 'none';
            if (btnBitacora) btnBitacora.style.display = 'none';
            return;
        }

        try {
            const response = await fetch(`${this.smtpUrl}/status`);
            const data = await response.json();

            // const btn = document.getElementById('btnSmtpConfig'); // Ya definido arriba
            if (btnSmtp) {
                if (data.status === 'CONFIG_OK') {
                    btnSmtp.classList.add('status-ok');
                    btnSmtp.classList.remove('status-error');
                } else if (data.status === 'CONFIG_MISSING') {
                    // ESCENARIO 2026-F01: Si no hay config, el botón se pone rojo pero NO bloquea el sistema.
                    // Solo se notifica como advertencia informativa.
                    btnSmtp.classList.add('status-error');
                    btnSmtp.classList.remove('status-ok');
                    this.showToast("Nota: Configuración SMTP pendiente de establecer.", "warning");
                } else {
                    // Corrupción o error real.
                    btnSmtp.classList.add('status-error');
                    btnSmtp.classList.remove('status-ok');
                    this.showToast("⚠️ Alerta: Configuración SMTP corrupta detectada.", "error");
                }
            }
        } catch (e) {
            console.error("Error verificando SMTP", e);
        }
    },

    abrirSmtpModal: async function () {
        document.getElementById('smtpModal').classList.add('open');

        // Intentar cargar datos actuales
        try {
            const response = await fetch(this.smtpUrl);
            if (response.ok) {
                const config = await response.json();
                // Llenar form
                document.getElementById('smtpPerfil').value = config.proveedor || 'custom';
                document.getElementById('smtpHost').value = config.host;
                document.getElementById('smtpPort').value = config.port;
                document.getElementById('smtpUser').value = config.username;
                document.getElementById('smtpPass').value = ""; // No mostramos password real por seguridad, el usuario debe reingresarla si cambia algo. O podríamos mostrar placeholder.
                document.getElementById('smtpAuth').checked = config.auth;
                document.getElementById('smtpTls').checked = config.starttls;
            }
        } catch (e) {
            // Nueva config
        }
    },

    cerrarSmtpModal: function () {
        document.getElementById('smtpModal').classList.remove('open');
    },

    cambiarPerfilSmtp: function () {
        const perfil = document.getElementById('smtpPerfil').value;
        const host = document.getElementById('smtpHost');
        const port = document.getElementById('smtpPort');

        if (perfil === 'gmail') {
            host.value = 'smtp.gmail.com';
            port.value = '587';
            host.readOnly = true;
            port.readOnly = true;
        } else if (perfil === 'hotmail') {
            host.value = 'smtp.office365.com';
            port.value = '587';
            host.readOnly = true;
            port.readOnly = true;
        } else {
            host.value = '';
            port.value = '';
            host.readOnly = false;
            port.readOnly = false;
        }
    },

    guardarSmtp: async function () {
        const config = {
            proveedor: document.getElementById('smtpPerfil').value,
            host: document.getElementById('smtpHost').value,
            port: parseInt(document.getElementById('smtpPort').value),
            username: document.getElementById('smtpUser').value,
            password: document.getElementById('smtpPass').value,
            auth: document.getElementById('smtpAuth').checked,
            starttls: document.getElementById('smtpTls').checked
        };

        if (!config.host || !config.username || !config.password) {
            MessageService.mostrar(9, "Datos Incompletos", "Faltan datos obligatorios para la configuración SMTP.");
            return;
        }

        try {
            const response = await fetch(this.smtpUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(config)
            });

            if (response.ok) {
                this.showToast("Configuración SMTP guardada y encriptada", "success");
                this.cerrarSmtpModal();
                this.verificarEstadoSmtp(); // Actualizar semáforo
            } else {
                this.showToast("Error guardando configuración", "error");
            }
        } catch (e) {
            this.showToast("Error de red", "error");
        }
    },

    probarSmtp: async function () {
        const config = {
            proveedor: document.getElementById('smtpPerfil').value,
            host: document.getElementById('smtpHost').value,
            port: parseInt(document.getElementById('smtpPort').value),
            username: document.getElementById('smtpUser').value,
            password: document.getElementById('smtpPass').value,
            auth: document.getElementById('smtpAuth').checked,
            starttls: document.getElementById('smtpTls').checked
        };

        if (!config.username || !config.password) {
            MessageService.mostrar(9, "Datos Incompletos", "Ingrese usuario y contraseña para probar la conexión.");
            return;
        }

        this.showToast("Enviando correo de prueba...", "info");

        try {
            const response = await fetch(`${this.smtpUrl}/test`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(config)
            });

            const res = await response.json();
            if (response.ok) {
                this.showToast("✅ " + res.message, "success");
            } else {
                this.showToast("❌ Falló: " + res.message, "error");
            }
        } catch (e) {
            this.showToast("Error de conexión", "error");
        }
    },

    // =========================================================
    // AUDITORÍA Y BITÁCORA (ADMIN)
    // =========================================================

    verBitacora: function () {
        // 1. Abrir Modal
        document.getElementById('bitacoraModal').classList.add('open');

        // 2. Poblar select de usuarios (solo una vez o refrescar)
        const sel = document.getElementById('bitacoraUsuario');
        sel.innerHTML = '<option value="">Todos los usuarios</option>';
        this.listaUsuarios.forEach(u => {
            const opt = document.createElement('option');
            opt.value = u.id || u.idUsuario;
            opt.textContent = u.nombre;
            sel.appendChild(opt);
        });

        // 3. Establecer fecha por defecto (hoy)
        const hoy = new Date().toISOString().split('T')[0];
        document.getElementById('bitacoraDesde').value = hoy;
        document.getElementById('bitacoraHasta').value = hoy;

        // 4. Cargar datos iniciales
        this.cargarBitacora();
    },

    cerrarBitacoraModal: function () {
        document.getElementById('bitacoraModal').classList.remove('open');
    },

    cargarBitacora: async function () {
        const desde = document.getElementById('bitacoraDesde').value;
        const hasta = document.getElementById('bitacoraHasta').value;
        const usuario = document.getElementById('bitacoraUsuario').value;
        const buscar = document.getElementById('bitacoraBuscar').value;

        const tbody = document.getElementById('bitacoraTableBody');
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">Consultando registros...</td></tr>';

        try {
            const params = new URLSearchParams({ desde, hasta, usuario, buscar });
            const response = await fetch(`${AppConfig.getEndpoint('bitacora')}?${params}`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });

            if (response.ok) {
                const logs = await response.json();
                this.currentLogs = logs; // Guardamos para el reporte
                this.renderTablaBitacora(logs);
            } else {
                this.showToast("Error al consultar bitácora", "error");
            }
        } catch (e) {
            console.error(e);
            this.showToast("Error de conexión con el motor de auditoría", "error");
        }
    },

    renderTablaBitacora: function (logs) {
        const tbody = document.getElementById('bitacoraTableBody');
        tbody.innerHTML = '';

        if (logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 30px; color: #9CA3AF;">No se encontraron registros.</td></tr>';
            return;
        }

        logs.forEach(log => {
            const tr = document.createElement('tr');
            tr.style.cursor = "pointer";
            tr.title = "Doble clic para ver ficha técnica completa";

            // 1. GESTIÓN DE SELECCIÓN (Single Click)
            tr.onclick = (e) => {
                // Solo si la fila no está ya seleccionada, evitamos manipular el DOM innecesariamente
                if (!e.currentTarget.classList.contains('selected-row')) {
                    const parent = e.currentTarget.parentNode;
                    const selected = parent.querySelector('.selected-row');
                    if (selected) selected.classList.remove('selected-row');
                    e.currentTarget.classList.add('selected-row');
                }
            };

            // 2. GESTIÓN DE DETALLE (Doble Clic)
            // Forzamos referencia a UsuariosService para evitar fugas de contexto 'this'
            tr.ondblclick = () => UsuariosService.abrirRegistroTexto(log);

            // Análisis de Acción (Nuevo formato vs Legacy)
            let badgeClass = 'badge-secondary';
            let displayAccion = log.accion;

            if (log.accion && log.accion.includes('|')) {
                const parts = log.accion.split('|');
                const impacto = parts[0].trim();
                const code = parts[parts.length - 1].trim();
                displayAccion = code;

                if (impacto.includes('SUCCESS') || impacto.includes('ÉXITO')) badgeClass = 'badge-success';
                else if (impacto.includes('FAILURE') || impacto.includes('FALLO') || impacto.includes('ERROR')) badgeClass = 'badge-danger';
                else if (impacto.includes('WARNING') || impacto.includes('AVISO')) badgeClass = 'badge-warning';
                else if (impacto.includes('CRITICAL') || impacto.includes('CRÍTICO')) badgeClass = 'badge-danger';
                else if (impacto.includes('INFO')) badgeClass = 'badge-primary';
            }

            // Procesar Detalles
            let displayDetalles = log.detalles;
            if (log.detalles && log.detalles.includes('|')) {
                const dParts = log.detalles.split('|');
                const amigable = dParts[0].trim();
                const tecnico = dParts.slice(1).join(' | ').trim();
                displayDetalles = `<div style="color: #111827; font-weight: 500;">${amigable}</div><div style="font-size: 0.75rem; color: #6B7280; font-family: monospace; margin-top: 2px; line-height: 1.2;">${tecnico}</div>`;
            }

            const fechaStr = log.fecha ? new Date(log.fecha).toLocaleString() : 'N/A';

            tr.innerHTML = `
                <td style="font-size: 0.85rem; font-family: monospace;">${fechaStr}</td>
                <td>
                    <div style="font-weight: 500;">${log.nombreUsuario || 'SISTEMA'}</div>
                    <div style="font-size: 0.7rem; color: #9CA3AF;">ID: ${log.idUsuario || 'N/A'}</div>
                </td>
                <td><span class="badge-base ${badgeClass}" style="font-size: 0.7rem;">${displayAccion}</span></td>
                <td style="font-family: monospace; font-size: 0.85rem;">${log.ipOrigen || '0.0.0.0'}</td>
                <td style="font-size: 0.85rem;">${displayDetalles || 'Sin detalles'}</td>
            `;
            tbody.appendChild(tr);
        });
    },

    abrirRegistroTexto: function (log) {
        let displayAccion = log.accion || 'EVENTO_DESCONOCIDO';
        let displayMsj = log.detalles || 'Sin detalles registrados';
        let displayTecnico = '';

        if (log.accion && log.accion.includes('|')) {
            const parts = log.accion.split('|');
            displayAccion = parts[parts.length - 1].trim();
        }

        if (log.detalles && log.detalles.includes('|')) {
            const dParts = log.detalles.split('|');
            displayMsj = dParts[0].trim();
            displayTecnico = dParts.slice(1).join(' | ').trim();
        }

        const textoBase = `
============================================================
    REPORTE TÉCNICO DE AUDITORÍA (PRIVACY ENGINE)
============================================================
ID BITÁCORA : ${log.idBitacora || 'N/A'}
FECHA/HORA  : ${log.fecha ? new Date(log.fecha).toLocaleString() : 'N/A'}
USUARIO     : ${log.nombreUsuario || log.idUsuario || 'SISTEMA'}
IP ORIGEN   : ${log.ipOrigen || '0.0.0.0'}
------------------------------------------------------------
EVENTO      : ${displayAccion}
DETALLES    : ${displayMsj}
------------------------------------------------------------
DATOS TÉCNICOS:
${displayTecnico || 'Sin datos técnicos adicionales'}
============================================================
REPORTE GENERADO DESDE EL NÚCLEO DE SEGURIDAD ERP WALOOK
`.trim();

        // --- MOTOR DE SIMETRÍA GLOBAL (REGLA DE 60 CARACTERES) ---
        // Se procesa cada línea del reporte para asegurar uniformidad total
        const textoFinal = textoBase.split('\n').map(linea => {
            if (linea.length <= 60) return linea;

            // Si la línea desborda, se fragmenta quirúrgicamente cada 60 chars
            const chunks = [];
            for (let i = 0; i < linea.length; i += 60) {
                chunks.push(linea.substring(i, i + 60));
            }
            return chunks.join('\n');
        }).join('\n');

        MessageService.mostrar(10, "Ficha Técnica de Auditoría", textoFinal);
    },

    // =========================================================
    // UTILIDADES (Toast)
    // =========================================================
    showToast: function (message, type = 'info') {
        let msgType = 9; // Default Info/Warning
        let title = "Notificación";

        if (type === 'error') {
            msgType = 3;
            title = "Error de Sistema";
            // Si es un error de conexión, intentar parsear
            if (message.includes("conexión")) title = "Error de Conexión";
        } else if (type === 'success') {
            msgType = 4;
            title = "Operación Exitosa";
        } else if (type === 'warning') {
            msgType = 9;
            title = "Advertencia";
        }

        // Evitar bucles o recursión si MessageService fallara (no debería)
        if (typeof MessageService !== 'undefined') {
            MessageService.mostrar(msgType, title, message);
        } else {
            console.warn("MessageService no cargado, fallback console:", message);
            alert(message);
        }
    },

    generarReporteBitacora: function () {
        if (!this.currentLogs || this.currentLogs.length === 0) {
            this.showToast("No hay datos para generar el reporte", "warning");
            return;
        }

        const wrapLine = (linea) => {
            if (linea.length <= 60) return linea;
            const chunks = [];
            for (let i = 0; i < linea.length; i += 60) {
                chunks.push(linea.substring(i, i + 60));
            }
            return chunks.join('\n');
        };

        let contenido = `============================================================\n`;
        contenido += `      REPORTE DE AUDITORÍA - ERP WALOOK (EXPORTADO)\n`;
        contenido += `============================================================\n`;
        contenido += `FECHA EXPORTACIÓN : ${new Date().toLocaleString()}\n`;
        contenido += `REGISTROS TOTALES : ${this.currentLogs.length}\n`;
        contenido += `============================================================\n\n`;

        this.currentLogs.forEach((log, index) => {
            let displayAccion = log.accion || 'EVENTO_DESCONOCIDO';
            let displayMsj = log.detalles || 'Sin detalles registrados';
            let displayTecnico = '';

            if (log.accion && log.accion.includes('|')) {
                const parts = log.accion.split('|');
                displayAccion = parts[parts.length - 1].trim();
            }

            if (log.detalles && log.detalles.includes('|')) {
                const dParts = log.detalles.split('|');
                displayMsj = dParts[0].trim();
                displayTecnico = dParts.slice(1).join(' | ').trim();
            }

            const ficha = `
============================================================
FICHA [${(index + 1).toString().padStart(3, '0')}] - ID: ${log.idBitacora || 'N/A'}
------------------------------------------------------------
FECHA/HORA  : ${log.fecha ? new Date(log.fecha).toLocaleString() : 'N/A'}
USUARIO     : ${log.nombreUsuario || log.idUsuario || 'SISTEMA'}
IP ORIGEN   : ${log.ipOrigen || '0.0.0.0'}
EVENTO      : ${displayAccion}
DETALLES    : ${displayMsj}
DATOS TÉCNICOS:
${displayTecnico || 'Sin datos técnicos adicionales'}
============================================================`.trim();

            // Aplicar simetría de 60 chars a cada ficha
            const fichaFormateada = ficha.split('\n').map(l => wrapLine(l)).join('\n');
            contenido += fichaFormateada + "\n\n";
        });

        contenido += `\n------------------------------------------------------------\n`;
        contenido += `FIN DEL REPORTE - WALOOK PRIVACY ENGINE 2026\n`;

        // Descarga del archivo
        const blob = new Blob([contenido], { type: 'text/plain' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        const fechaStr = new Date().toISOString().slice(0, 10);

        a.href = url;
        a.download = `Reporte_Auditoria_${fechaStr}.txt`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);

        this.showToast("Reporte descargado exitosamente", "success");
    },
};

// Iniciar al cargar
document.addEventListener('DOMContentLoaded', () => {
    UsuariosService.init();
});
