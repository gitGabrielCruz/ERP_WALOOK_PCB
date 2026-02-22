/*
============================================================
Nombre del archivo : auth-service.js
Ruta              : omcgc/frontend/assets/js/auth-service.js
Tipo              : Frontend (Script JS)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v3.0 - Sistema de Permisos Encriptados

Propósito:
Servicio centralizado de Autorización y Control de Acceso (ACL).
Valida permisos granulares (Ver, Crear, Editar, Eliminar) por módulo.
Los permisos se cargan una vez en el login y se almacenan encriptados en sessionStorage.
============================================================
*/

const AuthService = {
    // Configuración de API
    apiUrl: 'http://localhost:8080/api/usuarios',

    // Estado interno
    currentUser: null,

    /**
     * Inicializa el servicio cargando el usuario de la sesión.
     */
    init: function () {
        try {
            const stored = sessionStorage.getItem('user');
            if (stored) {
                this.currentUser = JSON.parse(stored);
            }
        } catch (e) {
            console.error("Error al leer sesión:", e);
        }
    },

    /**
     * Retorna el usuario actual o null si no hay sesión.
     */
    getCurrentUser: function () {
        if (!this.currentUser) this.init();
        return this.currentUser;
    },

    /**
     * Verifica si el usuario es Superaministrador o Root.
     */
    esAdmin: function () {
        const user = this.getCurrentUser();
        if (!user) return false;

        const rol = (user.rol || user.rolNombre || user.nombreRol || '').toUpperCase();
        return (rol === 'ADMIN' || rol === 'ADMINISTRADOR' || user.usuario === 'root');
    },

    /**
     * Permisos denegados por defecto (Política de Cero Tolerancia)
     */
    _permisosDenegados() {
        return {
            ver: false,
            crear: false,
            editar: false,
            eliminar: false
        };
    },

    /**
     * Encripta datos usando XOR con una clave
     */
    _encrypt(data, key) {
        const json = JSON.stringify(data);
        const encoded = btoa(json); // Base64
        return encoded.split('').map((c, i) =>
            String.fromCharCode(c.charCodeAt(0) ^ key.charCodeAt(i % key.length))
        ).join('');
    },

    /**
     * Desencripta datos usando XOR con una clave
     */
    _decrypt(encrypted, key) {
        try {
            const decoded = encrypted.split('').map((c, i) =>
                String.fromCharCode(c.charCodeAt(0) ^ key.charCodeAt(i % key.length))
            ).join('');
            const json = atob(decoded);
            return JSON.parse(json);
        } catch (e) {
            console.error("[AUTH] Error al desencriptar permisos:", e);
            return null;
        }
    },

    /**
     * Guarda permisos encriptados en sessionStorage
     * Se llama desde el login después de autenticar
     */
    guardarPermisosEncriptados(permisos) {
        const token = sessionStorage.getItem('token_erp');
        if (!token) {
            console.error("[AUTH] No hay token para encriptar");
            return false;
        }

        const encrypted = this._encrypt(permisos, token);
        sessionStorage.setItem('erp_permisos_enc', encrypted);
        console.log(`[AUTH] Permisos encriptados guardados: ${permisos.length} módulos`);
        return true;
    },

    /**
     * Valida si el usuario actual tiene permisos para acceder a un módulo.
     * Lee de sessionStorage encriptado (NO consulta al backend)
     * @param {string} nombreModulo - Nombre del módulo a validar (ej: 'CLIENTES')
     * @returns {Object} - Objeto con permisos { ver, crear, editar, eliminar }
     */
    obtenerPermisosModulo(nombreModulo) {
        console.log(`[AUTH] Validando acceso al módulo: ${nombreModulo}`);

        const user = this.getCurrentUser();
        if (!user) {
            console.error("[AUTH] No hay sesión activa");
            return this._permisosDenegados();
        }

        // 1. Bypass para Admin
        if (this.esAdmin()) {
            console.log(`[AUTH] Acceso Total concedido (Admin Bypass)`);
            return { ver: true, crear: true, editar: true, eliminar: true };
        }

        // 2. Obtener token de sesión (clave de encriptación)
        const token = sessionStorage.getItem('token_erp');
        if (!token) {
            console.error("[AUTH] No hay token de sesión");
            return this._permisosDenegados();
        }

        // 3. Leer permisos encriptados de sessionStorage
        const encryptedPermisos = sessionStorage.getItem('erp_permisos_enc');
        if (!encryptedPermisos) {
            console.error("[AUTH] No hay permisos en sessionStorage");
            return this._permisosDenegados();
        }

        // 4. Desencriptar permisos
        const listaPermisos = this._decrypt(encryptedPermisos, token);
        if (!listaPermisos || !Array.isArray(listaPermisos)) {
            console.error("[AUTH] Error al desencriptar permisos");
            return this._permisosDenegados();
        }

        console.log(`[AUTH] Permisos desencriptados: ${listaPermisos.length} módulos`);

        // 5. Buscar el módulo específico
        const modUpper = nombreModulo.toUpperCase().trim();
        const p = listaPermisos.find(x => {
            const m1 = x.modulo ? x.modulo.toUpperCase().trim() : '';
            const m2 = x.nombreModulo ? x.nombreModulo.toUpperCase().trim() : '';
            return m1 === modUpper || m2 === modUpper;
        });

        if (p) {
            console.log(`[AUTH] Permiso encontrado para ${nombreModulo}:`, p);
            return {
                ver: p.puede_ver === 1 || p.puede_ver === true,
                crear: p.puede_crear === 1 || p.puede_crear === true,
                editar: p.puede_editar === 1 || p.puede_editar === true,
                eliminar: p.puede_eliminar === 1 || p.puede_eliminar === true
            };
        } else {
            console.log(`[AUTH] Módulo '${nombreModulo}' NO encontrado en permisos -> DENEGADO`);
            return this._permisosDenegados();
        }
    },

    /**
     * Cierra la sesión activa y redirige al login.
     */
    logout: function () {
        console.log('[AUTH] Cerrando sesión...');
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('loginTimestamp');
        sessionStorage.removeItem('token_erp');
        sessionStorage.removeItem('erp_permisos_enc');
        window.location.href = 'login.html';
    }
};
