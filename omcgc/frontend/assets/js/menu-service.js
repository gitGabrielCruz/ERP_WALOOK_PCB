/*
============================================================
Nombre del archivo : menu-service.js
Ruta              : omcgc/frontend/assets/js/menu-service.js
Tipo              : Frontend (Lógica de Tablero de Control)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Gestionar la lógica de negocio del menú principal: validación de sesión activa,
renderizado de credenciales de usuario y control de navegación condicional
hacia los distintos módulos del sistema.

Dependencias:
- sessionStorage (Persistencia de navegador)
- window.location (Navegación)

Relación con el sistema:
- Pantallas: menu.html
- Requerimientos: 
  1. HU-M01-04 (Acceso Menú): Lógica de navegación y bloqueo de módulos.
  2. RNF-04 (Control de Acceso): Verificación de 'sessionStorage' al inicio.
  3. RNF-05 (Monitorización):
     - 'startClock()': Sincronización con hora del sistema cliente.
     - 'startSessionTimer()': Cálculo diferencial (Now - LoginTime) para contador.
============================================================
*/

const MenuService = {
    // Clave de almacenamiento de sesión
    SESSION_KEY: 'user',

    /**
     * Inicializa el tablero de control.
     * Valida la sesión y renderiza la información del usuario.
     */
    init() {
        if (!this.validateSession()) {
            return; // Detener ejecución si no hay sesión
        }
        this.renderUserInfo();
        this.checkPermissions();
        this.validarExistenciaModulos(); // Semáforo dinámico (DevIAn)
    },

    async validarExistenciaModulos() {
        console.log('[MENU-UI] Iniciando validación dinámica de módulos...');

        const esProtocoloLocal = window.location.protocol === 'file:';
        if (esProtocoloLocal) {
            console.warn('[MENU-UI] Modo Local Detectado: Se usará validación por inventario de ingeniería (Fallback).');
        }

        // Lista de archivos que DevIAn confirma que existen físicamente en el disco
        const archivosExistentes = [
            'login.html',
            'inventarios.html',
            'clientes.html',
            'proveedores.html',
            'usuarios.html',
            'menu.html'
        ];

        // Iterar sobre todos los botones del menú que tienen navegación interna
        const botones = document.querySelectorAll('.menu-grid button[onclick*="navigate"]');

        for (const btn of botones) {
            try {
                // Extraer el nombre del archivo del atributo onclick (ej: 'inventarios.html')
                const onclickMatch = btn.getAttribute('onclick').match(/'([^']+)'/);
                if (!onclickMatch) continue;

                const pageFile = onclickMatch[1];
                let existe = false;

                if (esProtocoloLocal) {
                    // Si es local, confiamos en el inventario actual de ingeniería
                    existe = archivosExistentes.includes(pageFile);
                } else {
                    // Si es servidor web, hacemos la comprobación real en vivo
                    const response = await fetch(pageFile, { method: 'HEAD' });
                    existe = response.ok;
                }

                if (existe) {
                    btn.classList.add('border-active');
                    console.log(`[MENU-UI] Módulo Activo: ${pageFile}`);
                } else {
                    btn.classList.add('border-pending');
                    console.log(`[MENU-UI] Módulo Pendiente: ${pageFile}`);
                }
            } catch (error) {
                console.warn(`[MENU-UI] Error al validar módulo:`, error);
                btn.classList.add('border-pending'); // Fallback a rojo
            }
        }
    },

    // Mapeo entre archivo HTML y Nombre del Módulo en Base de Datos
    MODULE_MAP: {
        'login.html': 'LOGIN',
        'usuarios.html': 'USUARIOS, ROLES Y PERMISOS',
        'inventarios.html': 'INVENTARIOS',
        'agenda.html': 'AGENDA CITAS',
        'proveedores.html': 'PROVEEDORES',
        'expediente.html': 'EXPEDIENTE PACIENTE',
        'clientes.html': 'CLIENTES',
        'ventas.html': 'VENTAS',
        'compras.html': 'ORDENES DE COMPRA',
        'taller.html': 'TALLER OT',
        'recepcion.html': 'RECEPCION Y DEVOLUCION',
        'facturacion.html': 'FACTURACION CFDI'
    },

    async checkPermissions() {
        const user = this.getUser();
        if (!user) return;

        // Admin Bypass
        const rol = user.rol || user.rolNombre || user.nombreRol; // Compatibilidad de nombres
        if (rol === 'ADMIN' || rol === 'Administrador' || rol === 'Administrador General') {
            console.log('[MENU-SEC] ADMIN Access: All modules unlocked.');
            return;
        }

        try {
            // URL desde config global (AppConfig) o fallback correcto (Puerto 9090)
            let baseUrl = 'http://localhost:9090/api';
            if (typeof AppConfig !== 'undefined' && AppConfig.BASE_URL) {
                baseUrl = AppConfig.BASE_URL;
            } else if (typeof API_BASE_URL !== 'undefined') {
                baseUrl = API_BASE_URL;
            }

            // Obtener permisos reales (Usar 'userId' que es lo que devuelve el Login)
            const idUsuario = user.userId || user.id || user.idUsuario;
            const res = await fetch(`${baseUrl}/usuarios/permisos-usuario/${idUsuario}`);

            if (res.ok) {
                const permisos = await res.json();

                // Iterar sobre el mapa de módulos para bloquear/desbloquear
                Object.keys(this.MODULE_MAP).forEach(page => {
                    const moduleName = this.MODULE_MAP[page];

                    // Buscar permiso (puede venir como 'modulo' o 'nombreModulo')
                    const perm = permisos.find(p => p.modulo === moduleName || p.nombreModulo === moduleName);

                    // Determinar acceso (Default: Denegado si no existe, excepto LOGIN)
                    let allowed = false;
                    if (moduleName === 'LOGIN') allowed = true;
                    else if (perm && (perm.puede_ver === true || perm.puede_ver === 1)) allowed = true;

                    if (!allowed) {
                        this.disableMenuButton(page);
                    }
                });
            } else {
                console.warn("[MENU-SEC] No se pudieron cargar permisos. Status:", res.status);
            }
        } catch (e) {
            console.error("[MENU-SEC] Error validando permisos:", e);
        }
    },

    disableMenuButton(pageFile) {
        // Buscar el botón que navega a esa página
        const selector = `button[onclick*="'${pageFile}'"]`;
        const btn = document.querySelector(selector);

        if (btn) {
            // Estilos visuales de "Deshabilitado"
            btn.style.opacity = '0.6';
            btn.style.cursor = 'default';
            btn.style.filter = 'none';
            btn.style.backgroundColor = '#F3F4F6';
            btn.style.color = '#9CA3AF';
            btn.style.borderColor = '#E5E7EB';
            btn.style.boxShadow = 'none';
            btn.style.transform = 'none';

            // Anular evento Click de forma radical
            const newBtn = btn.cloneNode(true);
            newBtn.removeAttribute('onclick'); // Elimina atributo HTML
            newBtn.onclick = null; // Elimina handler JS
            newBtn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
            });
            newBtn.setAttribute('disabled', 'true');
            newBtn.title = "Acceso no disponible para su rol";

            // Reemplazar en el DOM
            btn.parentNode.replaceChild(newBtn, btn);
        }
    },

    /**
     * Verifica si existe una sesión activa.
     * Si no existe, redirige al login.
     * @returns {boolean} true si la sesión es válida.
     */
    validateSession() {
        const userStr = sessionStorage.getItem(this.SESSION_KEY);
        if (!userStr) {
            console.warn('[MENU-AUTH] Sesión no detectada. Redirigiendo a Login...');
            window.location.href = 'login.html';
            return false;
        }
        return true;
    },

    /**
     * Obtiene el objeto de usuario de la sesión.
     * @returns {Object|null} Objeto usuario o null.
     */
    getUser() {
        const userStr = sessionStorage.getItem(this.SESSION_KEY);
        return userStr ? JSON.parse(userStr) : null;
    },

    /**
     * Renderiza el nombre y rol del usuario en el DOM.
     * Inicia los relojes de tiempo real y sesión.
     */
    renderUserInfo() {
        const user = this.getUser();
        if (user) {
            const nameEl = document.getElementById('userName');
            const roleEl = document.getElementById('userRole');
            const loginTimeEl = document.getElementById('loginTime');

            if (nameEl) nameEl.textContent = user.nombre || 'Usuario';
            if (roleEl) roleEl.textContent = user.nombreRol || 'Rol Desconocido';

            // Simular fecha de ingreso si no existe (para demostración)
            if (!sessionStorage.getItem('loginTimestamp')) {
                sessionStorage.setItem('loginTimestamp', new Date().getTime());
            }

            const loginTs = parseInt(sessionStorage.getItem('loginTimestamp'));
            if (loginTimeEl) loginTimeEl.textContent = new Date(loginTs).toLocaleTimeString();

            this.startClock();
            this.startSessionTimer(loginTs);

            console.log('[MENU-UI] Datos de usuario renderizados:', user.email);
        }
    },

    startClock() {
        const clockEl = document.getElementById('currentClock');
        if (!clockEl) return;

        setInterval(() => {
            const now = new Date();
            clockEl.textContent = now.toLocaleTimeString();

            // Actualizar fecha también si existe el elemento
            const dateEl = document.getElementById('currentDate');
            if (dateEl) dateEl.textContent = now.toLocaleDateString();
        }, 1000);
    },

    startSessionTimer(startTime) {
        const timerEl = document.getElementById('sessionTimer');
        if (!timerEl) return;

        setInterval(() => {
            const now = new Date().getTime();
            const diff = now - startTime;

            const hours = Math.floor(diff / (1000 * 60 * 60));
            const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((diff % (1000 * 60)) / 1000);

            timerEl.textContent =
                `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
        }, 1000);
    },

    /**
     * Gestiona la navegación segura a los módulos.
     * Implementa la regla "Si ver no está activo, no puede entrar".
     */
    async navigate(page, moduleName) {
        // Excepción Login
        if (page === 'login.html') {
            window.location.href = 'login.html';
            return;
        }

        // 1. Validación de Construcción (Etapas)
        const ACTIVE_MODULES = ['usuarios.html', 'clientes.html', 'proveedores.html', 'inventarios.html'];

        if (ACTIVE_MODULES.includes(page)) {
            try {
                // Feedback visual: Overlay de carga explícito
                const overlay = document.createElement('div');
                overlay.id = 'permiso-loader';
                overlay.style.cssText = 'position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(255,255,255,0.8);display:flex;justify-content:center;align-items:center;z-index:9999;backdrop-filter:blur(2px);';
                overlay.innerHTML = '<div style="background:white;padding:15px 30px;border-radius:50px;box-shadow:0 4px 15px rgba(0,0,0,0.1);font-family:sans-serif;font-weight:600;color:#374151;display:flex;align-items:center gap:10px;"><span class="material-symbols-outlined spin" style="margin-right:10px;animation:spin 1s linear infinite;">sync</span>Verificando permisos...</div>';

                // Agregar animación spin si no existe
                if (!document.getElementById('anim-spin')) {
                    const style = document.createElement('style');
                    style.id = 'anim-spin';
                    style.innerHTML = '@keyframes spin { 100% { transform: rotate(360deg); } }';
                    document.head.appendChild(style);
                }

                document.body.appendChild(overlay);

                // 2. Validación de Seguridad (Permisos)
                // Usamos el nombre interno mapeado (Ej: 'CLIENTES', 'USUARIOS...')
                const internalName = this.MODULE_MAP[page] || moduleName;

                const permisos = AuthService.obtenerPermisosModulo(internalName);

                // Retirar Overlay
                overlay.remove();

                if (!permisos.ver) {
                    MessageService.mostrar(
                        8,
                        "Acceso Denegado",
                        `No tiene permisos para acceder al módulo de ${moduleName}.`,
                        "Contacte al administrador si necesita acceso a esta función."
                    );
                    return;
                }

                console.log(`[MENU-NAV] Acceso autorizado a: ${moduleName}`);
                window.location.href = page;

            } catch (e) {
                // Retirar Overlay en caso de error
                const ol = document.getElementById('permiso-loader');
                if (ol) ol.remove();

                console.error("Error validando acceso:", e);
                MessageService.mostrar(
                    3,
                    "Error de Seguridad",
                    "Error al intentar navegar al módulo.",
                    "Por favor contacte a soporte técnico.",
                    e.toString()
                );
            }
        } else {
            console.info(`[MENU-NAV] Intento de acceso a módulo inactivo: ${moduleName}`);
            MessageService.mostrar(
                9,
                "Módulo en Construcción",
                `El módulo "${moduleName}" está definido en la arquitectura, pero su implementación corresponde a una etapa posterior.`,
                "Por favor, seleccione un módulo disponible."
            );
        }
    },

    /**
     * Cierra la sesión del usuario y sale del sistema.
     */
    logout() {
        MessageService.mostrar(
            2,
            "Cerrar Sesión",
            "¿Desea finalizar su sesión y salir del sistema?",
            "Se perderán los cambios no guardados.",
            "",
            () => {
                console.log('[MENU-AUTH] Cerrando sesión y saliendo...');
                sessionStorage.removeItem(this.SESSION_KEY);
                sessionStorage.removeItem('loginTimestamp');
                window.location.href = 'login.html';
            }
        );
    }
};

// Inicialización automática al cargar el script
// Se recomienda llamar a MenuService.init() al final del body en el HTML, 
// o usar este listener si el script está en head/defer.
document.addEventListener('DOMContentLoaded', () => {
    MenuService.init();
});
