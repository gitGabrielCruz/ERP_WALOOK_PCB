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
    },

    // Mapeo entre archivo HTML y Nombre del Módulo en Base de Datos
    MODULE_MAP: {
        'login.html': 'LOGIN',
        'usuarios.html': 'USUARIOS, ROLES Y PERMISOS',
        'inventario.html': 'INVENTARIO',
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
     * Implementa la regla de negocio de "Módulo no existente".
     * @param {string} page - Nombre del archivo HTML destino (ej: 'usuarios.html').
     * @param {string} moduleName - Nombre legible del módulo para la alerta.
     */
    navigate(page, moduleName) {
        // Redirigir botón LOGIN al login (comportamiento solicitado por diseño visual aunque sea redundante como acceso)
        if (page === 'login.html') {
            window.location.href = 'login.html'; // O podría no hacer nada si ya estamos dentro
            return;
        }

        // Validación de existencia de módulos en Etapa 1 y Etapa 2
        // Lista blanca de módulos activos
        const ACTIVE_MODULES = ['usuarios.html', 'clientes.html', 'proveedores.html'];

        if (ACTIVE_MODULES.includes(page)) {
            console.log(`[MENU-NAV] Navegando a módulo activo: ${moduleName}`);
            window.location.href = page;
        } else {
            console.info(`[MENU-NAV] Intento de acceso a módulo inactivo: ${moduleName}`);
            // Alerta amigable según especificación
            alert(`⚠️ MÓDULO EN CONSTRUCCIÓN\n\nEl módulo "${moduleName}" está definido en la arquitectura, pero su implementación corresponde a una etapa posterior.\n\nPor favor, seleccione un módulo disponible.`);
        }
    },

    /**
     * Cierra la sesión del usuario y sale del sistema.
     */
    logout() {
        if (confirm("¿Desea finalizar su sesión y salir del sistema?")) {
            console.log('[MENU-AUTH] Cerrando sesión y saliendo...');
            sessionStorage.removeItem(this.SESSION_KEY);
            sessionStorage.removeItem('loginTimestamp');

            // Intento de cerrar ventana (puede requerir permisos/config del navegador)
            // Si falla, redirige a una página de cierre o login.
            window.location.href = 'about:blank'; // Simula salir a la nada
            // Opcional: window.close(); 
            document.body.innerHTML = "<div style='display:flex;justify-content:center;align-items:center;height:100vh;background:#1F2937;color:white;flex-direction:column;'><h1>Sesión Finalizada</h1><p>Puede cerrar esta ventana.</p><button onclick='window.location.href=\"login.html\"' style='padding:10px;margin-top:20px;'>Volver a Conectar</button></div>";
        }
    }
};

// Inicialización automática al cargar el script
// Se recomienda llamar a MenuService.init() al final del body en el HTML, 
// o usar este listener si el script está en head/defer.
document.addEventListener('DOMContentLoaded', () => {
    MenuService.init();
});
