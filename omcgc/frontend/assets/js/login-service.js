/*
============================================================
Nombre del archivo : login-service.js
Ruta              : omcgc/frontend/assets/js/login-service.js
Tipo              : Frontend (Script JS)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Capa de abstracción de cliente HTTP para gestionar la comunicación asíncrona
con los servicios de autenticación del Backend.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Inicio de Sesión):
   - Envío asíncrono (Fetch API) de credenciales al endpoint '/api/auth/login'.
   - Procesamiento de respuestas JSON y códigos de estado HTTP (200, 401, 500).
   - Gestión de errores de conectividad (Network Handling).
2. RNF-02 (Seguridad de Sesión):
   - Almacenamiento volátil de sesión mediante 'sessionStorage'.
   - Limpieza segura de credenciales locales en cierre de sesión (`logout`).
============================================================
*/

const LoginService = {
    // Punto de entrada de la API REST (Backend)
    API_URL: AppConfig.getEndpoint('auth'),

    async login(email, password) {
        console.log("[INFO] Iniciando secuencia de autenticación para:", email);

        try {
            const response = await fetch(`${this.API_URL}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });

            console.log("[INFO] Código de estado HTTP recibido:", response.status);

            if (!response.ok) {
                // Registro de errores para diagnóstico técnico
                console.error(`[SYSTEM ERROR] Fallo en petición de Autenticación. Estado: ${response.status} ${response.statusText}`);

                // Gestion de codigos de estado HTTP 401/403 (No Autorizado)
                // RETORNAR objeto en lugar de lanzar excepcion
                if (response.status === 401 || response.status === 403) {
                    return {
                        success: false,
                        message: "Usuario o contraseña incorrectos."
                    };
                }

                // Intentar leer mensaje del backend otros errores
                try {
                    const errorData = await response.json();
                    console.error('[SYSTEM ERROR DETAIL]', errorData); // Registro detallado de la respuesta JSON
                    throw new Error(errorData.message || 'Error interno del servidor de aplicaciones');
                } catch (jsonError) {
                    throw new Error('Error interno del servidor (' + response.status + ')');
                }
            }

            return await response.json();
        } catch (error) {
            console.error('[EXCEPTION] Interrupción del flujo de autenticación:', error);

            let mensajeEsp = error.message;

            if (mensajeEsp.includes('NetworkError') || mensajeEsp.includes('Failed to fetch')) {
                mensajeEsp = "Error de conectividad: No es posible establecer comunicación con el servidor de aplicaciones.";
            }

            throw new Error(mensajeEsp);
        }
    },

    logout() {
        sessionStorage.removeItem('user');
        window.location.href = 'login.html';
    }
};
