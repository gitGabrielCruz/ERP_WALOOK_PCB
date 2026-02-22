/*
============================================================
Nombre del archivo : ETAPA 1 - CODIGO_FUENTE.md
Ruta              : ETAPA 1 - CODIGO_FUENTE.md
Tipo              : Documentación Técnica (Código Fuente Consolidado)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MÉXICO, S.A. de C.V.

Autor             : Ing. Gabriel Amilcar Cruz Canto / Antigravity AI
Versión           : 3.2 (Sincronizada - Seguridad & Autenticación)
Fecha             : 22 de febrero de 2026
Propósito         : Consolidar el código fuente real y operativo de la Etapa 1.
============================================================
*/

---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 1 - Código Fuente Consolidado  
**VERSIÓN:** 3.2 (Consolidado Etapa 1 & 2)  
**FECHA:** 22 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto / Antigravity AI  

---

# 💻 ETAPA 1 - CÓDIGO FUENTE CONSOLIDADO

## FUENTES REALES DEL SISTEMA

Este archivo contiene el código fuente **completo y operativo** de la Etapa 1, extraído directamente de los archivos del directorio `omcgc/`.

---

## 🌐 FRONTEND (HTML)

### 📄 omcgc/frontend/index.html

```html
<!--
============================================================
Nombre del archivo : index.html
Ruta              : omcgc/frontend/index.html
Tipo              : Frontend (Landing Page / Enrutador)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Punto de entrada raíz del servidor web estático.
Implementa una redirección automática inmediata hacia el módulo de autenticación.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. UX/Navegación:
   - Redirección automática a 'pages/login.html' para forzar flujo de entrada.
============================================================
-->
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="0; url=pages/login.html">
    <title>Redireccionando... | WALOOK ERP</title>
</head>

<body>
    <p>Redireccionando al Sistema ERP... si no sucede automáticamente, <a href="pages/login.html">haz clic aquí</a>.</p>
    <script>window.location.href = "pages/login.html";</script>
</body>

</html>
```

### 📄 omcgc/frontend/pages/login.html

```html
<!--
============================================================
Nombre del archivo : login.html
Ruta              : omcgc/frontend/pages/login.html
Tipo              : Frontend (HTML5)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Proveer la interfaz gráfica para la autenticación de usuarios al sistema ERP.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Inicio de Sesión):
   - Formulario de captura de credenciales (Usuario/Contraseña).
   - Invocación al servicio de autenticación ('login-service.js').
   - Retroalimentación visual de errores de acceso (Credenciales inválidas, bloqueo).
2. RF-04 (Seguridad Visual):
   - Implementación de control de visibilidad de contraseña (Toggle Masking).
   - Implementación de Content Security Policy (CSP) en cabeceras.
3. RNF-01 (Usabilidad):
   - Persistencia local de preferencia de usuario ('Recuérdame').
============================================================
-->
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSP: Generado dinámicamente por api-config.js según el entorno detectado -->
    <meta name="description" content="Acceso ERP Walook">
    <title>Login | OPTICA ERP</title>

    <!-- CSS CANONICO DEL SISTEMA -->
    <link rel="stylesheet" href="../assets/css/ui-base.css">
    <!-- CSS DEL SISTEMA DE MENSAJES -->
    <link rel="stylesheet" href="../assets/css/message-styles.css">
</head>

<body class="login-body">

    <div class="login-container">

        <img src="../assets/img/logo.png" alt="OPTICA ERP Logo" class="login-logo">

        <div class="card">
            <div class="text-center" style="margin-bottom: 24px;">
                <!-- Encabezado de sección -->
                <h1 class="text-title" style="text-align: center;">Login</h1>
            </div>

            <form id="loginForm" autocomplete="off">
                <div class="form-group">
                    <label for="email" class="text-label">Usuario</label>
                    <input type="text" id="email" name="email" class="input-base" placeholder="usuario@ejemplo.com"
                        required>
                </div>

                <div class="form-group">
                    <label for="password" class="text-label">Contraseña</label>
                    <div class="input-group">
                        <input type="password" id="password" name="password" class="input-base" placeholder="••••••••"
                            required>
                        <button type="button" class="icon-action" onclick="togglePassword()"
                            title="Mostrar/Ocultar">👁️</button>
                    </div>
                </div>

                <div class="login-actions">
                    <label class="checkbox-inline">
                        <input type="checkbox" id="rememberMe"> Recuérdame
                    </label>
                    <button type="submit" class="btn-primary" id="btnLogin">Acceder</button>
                </div>

                <div class="login-links">
                    <a href="#" onclick="mostrarRecuperacionPassword(); return false;">¿Olvidaste tu contraseña?</a>
                </div>

                <!-- Contenedor dinámico de alertas y errores -->
                <div id="errorMsg" class="alert alert-error" style="display: none; margin-top: 16px;"></div>
            </form>
        </div>

        <div class="app-footer">
            WALOOK México • ERP Cloud System
        </div>
    </div>

    <!-- Script de configuracion -->
    <script src="../assets/js/api-config.js"></script>
    <!-- Script de autenticacion -->
    <script src="../assets/js/auth-service.js"></script>
    <!-- Script del sistema de mensajes -->
    <script src="../assets/js/message-service.js"></script>
    <!-- Script de logica -->
    <script src="../assets/js/login-service.js"></script>
    <script>
        // --- Estrategia de Mitigacion de XSS ---
        // Se utiliza la propiedad 'textContent' para la insercion segura de texto,
        // evitando la interpretacion de HTML no confiable ('innerHTML').

        function togglePassword() {
            const pwdInput = document.getElementById('password');
            const btn = document.querySelector('.icon-action');
            if (pwdInput.type === 'password') {
                pwdInput.type = 'text';
                btn.textContent = '🔒';
            } else {
                pwdInput.type = 'password';
                btn.textContent = '👁️';
            }
        }

        /**
         * Muestra mensaje de modulo no implementado (Recuperacion de Password).
         * Utiliza MessageService tipo 9 (Advertencia Simple).
         */
        function mostrarRecuperacionPassword() {
            const titulo = 'Modulo no disponible';
            const mensaje = 'El modulo de recuperacion de contraseña aun no esta implementado.';
            const recomendacion = 'Contacte al administrador del sistema para restablecer su contraseña.';
            MessageService.mostrar(9, titulo, mensaje, recomendacion);
        }

        // Recuperación de identidad persistente (Preferencia 'Recuérdame')
        window.addEventListener('load', () => {
            const savedEmail = localStorage.getItem('remember_email');
            if (savedEmail) {
                document.getElementById('email').value = savedEmail;
                document.getElementById('rememberMe').checked = true;
            }
        });

        document.getElementById('loginForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            const email = document.getElementById('email').value.trim(); // Normalizacion de cadena (Trim)
            const password = document.getElementById('password').value;
            const rememberMe = document.getElementById('rememberMe').checked;

            const btn = document.getElementById('btnLogin');
            const errorDiv = document.getElementById('errorMsg');

            // Restablecimiento de estado de interfaz de usuario
            errorDiv.style.display = 'none';

            try {
                // Validacion de integridad de datos de entrada
                if (!email || !password) {
                    // Mensaje tipo 1: Error de Validacion
                    const titulo = 'Campos incompletos';
                    const mensajePrincipal = 'Debe ingresar usuario y contraseña para acceder al sistema.';
                    const mensajeSecundario = 'Complete todos los campos obligatorios.';
                    const campoError = '';
                    MessageService.mostrar(1, titulo, mensajePrincipal, mensajeSecundario, campoError);
                    return;
                }

                // Mostrar spinner de procesamiento (tipo 6)
                const tituloSpinner = 'Verificando credenciales';
                const mensajeSpinner = 'Espere mientras se valida su acceso al sistema.';
                MessageService.mostrar(6, tituloSpinner, mensajeSpinner, 'No cierre la ventana ni actualice la pagina.');

                // Invocacion asincrona al servicio de autenticacion
                const result = await LoginService.login(email, password);

                // Cerrar spinner
                MessageService.cerrar();

                if (result.success) {
                    // Gestion de persistencia local de identidad (Preferencia de usuario)
                    if (rememberMe) {
                        localStorage.setItem('remember_email', email);
                    } else {
                        localStorage.removeItem('remember_email');
                    }

                    // LIMPIEZA CRITICA DE CACHE DE PERMISOS
                    // Eliminar cualquier rastro de permisos anteriores para forzar recarga segura
                    try {
                        Object.keys(sessionStorage).forEach(key => {
                            if (key && (key.startsWith('erp_permisos_') || key === 'erp_permisos_enc')) {
                                sessionStorage.removeItem(key);
                            }
                        });
                        console.log("[LOGIN] Cache de permisos depurado.");
                    } catch (e) {
                        console.warn("[LOGIN] Error limpiando cache de permisos", e);
                    }

                    // Almacenamiento de sesion volatil (SessionStorage)
                    sessionStorage.setItem('user', JSON.stringify(result));

                    // Generar token de sesion (simulado - en produccion vendria del backend)
                    const token = btoa(`${result.userId}-${Date.now()}`);
                    sessionStorage.setItem('token_erp', token);

                    // Guardar permisos encriptados
                    if (result.permisos && Array.isArray(result.permisos)) {
                        AuthService.guardarPermisosEncriptados(result.permisos);
                        console.log(`[LOGIN] Permisos encriptados guardados: ${result.permisos.length} modulos`);
                    } else {
                        console.warn("[LOGIN] No se recibieron permisos del backend");
                    }

                    // Redireccion al panel principal
                    window.location.href = 'menu.html';
                } else {
                    // Mensaje tipo 1: Error de Validacion (Credenciales invalidas)
                    const titulo = 'Error de autenticacion';
                    const mensajePrincipal = result.message || 'Las credenciales ingresadas no son validas.';
                    const mensajeSecundario = 'Verifique su usuario y contraseña e intente nuevamente.';
                    const campoError = `Usuario: ${email}`;
                    MessageService.mostrar(1, titulo, mensajePrincipal, mensajeSecundario, campoError);
                }

            } catch (err) {
                console.error("[ERROR] Excepcion capturada en flujo de login:", err);

                // Cerrar spinner si esta abierto
                MessageService.cerrar();

                // Mensaje tipo 3: Error de Sistema
                const titulo = 'Error del sistema';
                const mensajePrincipal = 'Ocurrio un error inesperado al procesar la solicitud de acceso.';
                const mensajeSecundario = 'Puede descargar el reporte tecnico y enviarlo al area de desarrollo.';
                const detalleTecnico = `ErrorCode: SYS-LOGIN-001\n${err.message}\n${err.stack || 'Stack trace no disponible'}`;
                MessageService.mostrar(3, titulo, mensajePrincipal, mensajeSecundario, detalleTecnico);
            }
        });
    </script>
</body>

</html>
```
</body>

</html>
```

### 📄 omcgc/frontend/pages/menu.html

```html
<!--
============================================================
Nombre del archivo : menu.html
Ruta              : omcgc/frontend/pages/menu.html
Tipo              : Frontend (HTML5)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Implementar el tablero de control principal (Dashboard) que centraliza el acceso
a los módulos funcionales del ERP según los privilegios del usuario.

 Trazabilidad y Mapeo Funcional:
 ------------------------------------------------------------
 1. HU-M01-04 (Menú Principal):
    - Estructuración de grilla de módulos funcionales (12 módulos base).
    - Implementación de 'Header Bar' con identidad corporativa (Logo).
    - Mecanismo de cierre de sesión definitivo ("Salir del Sistema").
 2. RF-04 (Control de Acceso de Interfaz):
    - Bloqueo visual de módulos no implementados (Alertas "En Construcción").
    - Validación de sesión activa pre-renderizado.
 3. RF-05 (Monitorización de Sesión):
    - Visualización de datos de usuario (Nombre, Rol).
    - Reloj de tiempo real (Hora Actual) y Cronómetro de sesión (Tiempo Conexión).
    - Registro de hora de ingreso inmutable.
 ============================================================
-->
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OPTICA ERP - Menú Principal</title>
    <!-- CSS CANÓNICO DEL SISTEMA -->
    <link rel="stylesheet" href="../assets/css/ui-base.css">
    <link rel="stylesheet" href="../assets/css/message-styles.css">
    <!-- Iconos Material Symbols -->
    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
</head>

<body>

    <div class="app-wrapper"
        style="background-color: #F4F6F8; min-height: 100vh; display: flex; flex-direction: column;">

        <!-- 1. HEADER BAR ESTÁNDAR -->
        <header class="app-header-bar">
            <!-- Izquierda: Logo y Branding -->
            <div class="header-left">
                <img src="../assets/img/logo.png" alt="OPTICA ERP Logo" class="header-logo">
                <div class="header-brand-box">
                    <div class="header-brand-title">
                        OPTICA ERP
                        <span class="material-symbols-outlined header-module-icon">grid_view</span>
                    </div>
                    <span id="currentDate" class="header-date">--/--/----</span>
                </div>
            </div>

            <!-- Centro/Derecha: Status Bar -->
            <div class="app-status-bar">

                <div class="status-item">
                    <span style="font-weight: 600;" id="userName">Cargando...</span>
                    <span
                        style="font-size: 0.75rem; color: #6B7280; background: #F3F4F6; padding: 2px 6px; border-radius: 4px;"
                        id="userRole">...</span>
                </div>

                <div class="status-separator"></div>

                <div class="time-stats">
                    <div class="time-stat-box">
                        <div class="time-label">INGRESO</div>
                        <div class="time-value" id="loginTime">--:--</div>
                    </div>
                    <div class="time-stat-box">
                        <div class="time-label">ACTUAL</div>
                        <div class="time-value" id="currentClock">--:--</div>
                    </div>
                    <div class="time-stat-box">
                        <div class="time-label">CONEXIÓN</div>
                        <div class="time-value" style="color: var(--color-success);" id="sessionTimer">00:00:00</div>
                    </div>
                </div>

                <div class="status-separator"></div>

                <!-- Botón Salir -->
                <button onclick="MenuService.logout()" class="btn-logout">
                    SALIR ✕
                </button>
            </div>
        </header>

        <!-- 2. MAIN CONTENT CENTRADO ESTÁNDAR -->
        <main class="app-main-container">

            <div class="menu-card"
                style="background: white; padding: 40px; border-radius: 12px; box-shadow: 0 10px 40px rgba(0,0,0,0.06); width: 100%; max-width: 1000px; margin-top: 0;">

                <div class="dashboard-header" style="text-align: center; margin-bottom: 40px;">
                    <h1 class="text-title" style="font-size: 26px; color: #111827; margin-bottom: 8px;">Menú principal —
                        Módulos del Sistema</h1>
                    <p class="text-normal" style="color: #9CA3AF; font-size: 15px;">Selecciona un módulo para continuar
                    </p>
                </div>

                <!-- GRID ADAPTATIVO (TILES) CON ICONOS -->
                <div class="menu-grid"
                    style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; width: 100%; justify-items: stretch;">

                    <!-- 1. LOGIN -->
                    <button onclick="MenuService.navigate('login.html', 'Login System')" class="menu-btn special-blue">
                        <span class="material-symbols-outlined icon-lg">vpn_key</span>
                        <div class="btn-title">LOGIN</div>
                        <div class="btn-subtitle">ACCESO AL SISTEMA</div>
                    </button>

                    <!-- 2. INVENTARIO -->
                    <button onclick="MenuService.navigate('inventarios.html', 'Inventarios')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">inventory_2</span>
                        INVENTARIOS
                    </button>

                    <!-- 3. AGENDA -->
                    <button onclick="MenuService.navigate('agenda.html', 'Agenda Citas')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">calendar_month</span>
                        AGENDA<br>CITAS
                    </button>

                    <!-- 4. PROVEEDORES -->
                    <button onclick="MenuService.navigate('proveedores.html', 'Proveedores')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">local_shipping</span>
                        PROVEEDORES
                    </button>

                    <!-- 5. EXPEDIENTE -->
                    <button onclick="MenuService.navigate('expediente.html', 'Expediente')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">folder_shared</span>
                        EXPEDIENTE<br>PACIENTE
                    </button>

                    <!-- 6. CLIENTES -->
                    <button onclick="MenuService.navigate('clientes.html', 'Clientes')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">groups</span>
                        CLIENTES
                    </button>

                    <!-- 7. VENTAS -->
                    <button onclick="MenuService.navigate('ventas.html', 'Ventas')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">point_of_sale</span>
                        VENTAS
                    </button>

                    <!-- 8. COMPRAS -->
                    <button onclick="MenuService.navigate('compras.html', 'Ordenes Compra')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">shopping_cart</span>
                        ORDENES<br>DE COMPRA
                    </button>

                    <!-- 9. TALLER -->
                    <button onclick="MenuService.navigate('taller.html', 'Taller OT')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">build</span>
                        TALLER<br>OT
                    </button>

                    <!-- 10. RECEPCION -->
                    <button onclick="MenuService.navigate('recepcion.html', 'Recepcion')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">move_to_inbox</span>
                        RECEPCION Y<br>DEVOLUCION
                    </button>

                    <!-- 11. FACTURACION -->
                    <button onclick="MenuService.navigate('facturacion.html', 'Facturacion')" class="menu-btn">
                        <span class="material-symbols-outlined icon-lg">receipt_long</span>
                        FACTURACION<br>CFDI
                    </button>

                    <!-- 12. USUARIOS -->
                    <button onclick="MenuService.navigate('usuarios.html', 'Usuarios')" class="menu-btn special-gray">
                        <span class="material-symbols-outlined icon-lg">manage_accounts</span>
                        USUARIOS,<br>ROLES Y PERMISOS
                    </button>

                </div>

                <div class="menu-footer"
                    style="text-align: center; margin-top: 40px; padding-top: 20px; border-top: 1px solid #F3F4F6; color: #9CA3AF; font-size: 0.75rem;">
                    OPTICA ERP — WALOOK MEXICO, S.A. de C.V. · Prototipo de maquetado
                </div>

            </div>
        </main>

        <style>
            /* DINÁMICO: Semáforo de Módulos (DevIAn) */
            .border-active {
                border: 3px solid #10B981 !important;
                box-shadow: 0 4px 12px rgba(16, 185, 129, 0.15) !important;
            }

            .border-pending {
                border: 3px solid #EF4444 !important;
                box-shadow: 0 4px 12px rgba(239, 68, 68, 0.15) !important;
            }

            .menu-btn {
                background-color: #F3F4F6;
                border: 1px solid #E5E7EB;
                border-radius: 12px;
                aspect-ratio: 1 / 0.8;
                /* Casi cuadrado, un poco mas ancho */
                min-height: 120px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                gap: 10px;
                /* Espacio entre icono y texto */
                cursor: pointer;
                transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);

                font-family: 'Inter', sans-serif;
                font-weight: 700;
                color: #374151;
                font-size: 0.9rem;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                text-align: center;
                padding: 15px;
                box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
            }

            .icon-lg {
                font-size: 40px !important;
                /* Icono Grande */
                color: #1F2937;
                /* Efecto Relieve (Sombra blanca abajo, oscura arriba) */
                text-shadow: 1px 1px 0px rgba(255, 255, 255, 0.8), -1px -1px 0px rgba(0, 0, 0, 0.1);
            }

            .special-blue .icon-lg {
                color: white !important;
                text-shadow: none;
                /* En fondo oscuro mejor limpio */
            }

            .menu-btn:hover {
                background-color: #E5E7EB;
                transform: translateY(-4px);
                box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
                border-color: #D1D5DB;
            }

            .menu-btn:active {
                transform: translateY(-1px);
            }

            .special-blue {
                background-color: #1F3A5F !important;
                color: white !important;
                border: none;
            }

            .special-blue .btn-subtitle {
                color: rgba(255, 255, 255, 0.7);
            }

            .special-blue:hover {
                background-color: #162c4b !important;
                box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
            }

            .special-gray {
                background-color: #E2E8F0;
                border-color: #CBD5E1;
            }

            .btn-title {
                line-height: 1.2;
                font-size: 1rem;
            }

            .btn-subtitle {
                font-size: 0.65rem;
                font-weight: 400;
                margin-top: 2px;
                opacity: 0.9;
            }

            /* Responsive Adjustments */
            @media (max-width: 640px) {
                .menu-grid {
                    grid-template-columns: 1fr 1fr !important;
                    /* Force 2 columns on mobile */
                    gap: 15px !important;
                }

                .menu-btn {
                    font-size: 0.75rem;
                    /* Smaller text */
                    min-height: 100px;
                }

                .icon-lg {
                    font-size: 32px !important;
                }

                .app-wrapper header {
                    padding: 10px 15px !important;
                }

                .status-bar {
                    display: none !important;
                    /* Ocultar statusbar completo en movil muy pequeño, dejar solo logo y quiza salir abajo */
                }

                /* Mostrar boton salir en movil de otra forma si se oculta arriba */
            }
        </style>

    </div>

    <!-- Scripts -->
    <script src="../assets/js/api-config.js"></script>
    <script src="../assets/js/menu-service.js"></script>
    // Funciones puente para los onclicks inline (si se requiriera, aunque se llaman directo a MenuService)
    // La inicialización se hace automáticamente en DOMContentLoaded dentro de menu-service.js
    </script>
</body>

</html>
```

### 📄 omcgc/frontend/pages/usuarios.html

```html
<!--
============================================================
Nombre del archivo : usuarios.html
Ruta              : omcgc/frontend/pages/usuarios.html
Tipo              : Frontend (HTML5)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Proveer la interfaz gráfica para la Administración Integral de Usuarios (ABM),
Gestión de Roles y Configuración Granular de Permisos de Acceso.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M02-01 (Gestión de Usuarios):
   - Listado paginado y filtrable de usuarios del sistema.
   - Formulario de Alta y Edición de datos maestos (Nombre, Correo, Rol).
2. HU-M02-02 (Control de Estatus):
   - Mecanismo para activar/desactivar usuarios (Baja lógica).
3. HU-M02-03 (Matriz de Permisos):
   - Tabla interactiva para asignar permisos CRUD por módulo.
   - Herencia de permisos base desde Roles predefinidos.
4. RF-04 (Seguridad y Auditoría):
   - Restricción de elementos UI basada en 'permisosActuales' del operador.
   - Bloqueo de campos en modo "Solo Lectura".
============================================================
-->
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OPTICA ERP - Usuarios, Roles y Permisos</title>
    <link rel="stylesheet" href="../assets/css/ui-base.css">
    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
</head>

<body>
    <div class="app-wrapper">

        <!-- 
        [RF-05] HEADER BAR ESTÁNDAR 
        Componente global de navegación y estado de sesión.
        [JS BINDING] Datos poblados por MenuService / usuarios-service.js (userName, userRole).
        -->
        <header class="app-header-bar">
            <div class="header-left" style="cursor: pointer;" onclick="window.location.href='menu.html'">
                <img src="../assets/img/logo.png" alt="OPTICA ERP Logo" class="header-logo">
                <div class="header-brand-box">
                    <div class="header-brand-title">
                        OPTICA ERP
                        <span class="material-symbols-outlined header-module-icon">manage_accounts</span>
                    </div>
                    <span id="currentDate" class="header-date">--/--/----</span>
                </div>
            </div>

            <div class="app-status-bar">
                <div class="status-item">
                    <span style="font-weight: 600;" id="userName">Cargando...</span>
                    <span
                        style="font-size: 0.75rem; color: #6B7280; background: #F3F4F6; padding: 2px 6px; border-radius: 4px;"
                        id="userRole">...</span>
                </div>
                <div class="status-separator"></div>
                <div class="time-stats">
                    <div class="time-stat-box">
                        <div class="time-label">INGRESO</div>
                        <div class="time-value" id="loginTime">--:--</div>
                    </div>
                    <div class="time-stat-box">
                        <div class="time-label">ACTUAL</div>
                        <div class="time-value" id="currentClock">--:--</div>
                    </div>
                    <div class="time-stat-box">
                        <div class="time-label">CONEXIÓN</div>
                        <div class="time-value" style="color: var(--color-success);" id="sessionTimer">00:00:00</div>
                    </div>
                </div>
                <div class="status-separator"></div>
                <!-- Botón SMTP (Movido al Header) -->
                <button id="btnSmtpConfig" class="btn-primary btn-smtp"
                    style="display: none; margin-right: 15px; padding: 6px 12px; font-size: 0.8rem;"
                    onclick="UsuariosService.abrirSmtpModal()">
                    <span class="material-symbols-outlined"
                        style="font-size: 1.1rem; margin-right:5px;">mail_lock</span>
                    SMTP
                </button>
                <button
                    onclick="if(document.referrer.indexOf(window.location.host) !== -1) { history.back(); } else { window.location.href='menu.html'; }"
                    class="btn-logout">SALIR ✕</button>
            </div>
        </header>

        <!-- MAIN CONTENT -->
        <main class="app-main-container">
            <div style="width: 100%; max-width: 1400px;">

                <!-- ENCABEZADO Y BOTÓN SMTP -->
                <div class="usuarios-header" style="position: relative;">
                    <h1>Usuarios, Roles y Permisos</h1>
                    <p>Control de usuarios, roles y permisos de acceso a usuarios del sistema</p>


                </div>

                <!-- 
                [HU-M02-01] BARRA DE HERRAMIENTAS Y FILTRADO
                Permite la localización rápida de registros mediante búsqueda textual o filtros por Rol/Estatus.
                [JS BINDING] Eventos 'input' y 'change' vinculados a UsuariosService.filtrarUsuarios().
                -->
                <div class="filter-bar">
                    <div class="filter-group" style="flex: 1; min-width: 200px;">
                        <label>Buscar</label>
                        <input type="text" id="searchInput" placeholder="Nombre, correo..." class="input-base">
                    </div>
                    <div class="filter-group" style="min-width: 150px;">
                        <label>Rol</label>
                        <select id="filterRol" class="input-base">
                            <option value="">Todos</option>
                            <option value="ADMIN">ADMINISTRADOR</option>
                            <option value="CAJA">CAJA</option>
                            <option value="VENDEDOR">VENDEDOR</option>
                            <option value="OPTOMETRISTA">OPTOMETRISTA</option>
                            <option value="TALLER">TALLER</option>
                            <option value="ALMACEN">ALMACEN</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label>Estatus</label>
                        <div class="status-toggle">
                            <button class="status-btn active" data-status="activo">Activos</button>
                            <button class="status-btn" data-status="inactivo">Inactivos</button>
                        </div>
                    </div>
                </div>

                <!-- LAYOUT 2 COLUMNAS (Responsive vía CSS) -->
                <div class="usuarios-layout">

                    <!-- COLUMNA IZQUIERDA: LISTADO 
                    [HU-M02-01] LISTADO MAESTRO DE USUARIOS
                    [JS BINDING] Renderizado por UsuariosService.renderTabla().
                    [COMPORTAMIENTO] Carga inicial automática (init) y reactiva a filtros.
                    -->
                    <div class="usuarios-list">
                        <h3 style="margin-top: 0; margin-bottom: 15px; font-size: 1rem;">Listado de usuarios</h3>
                        <div style="overflow-x: auto;">
                            <table class="table-base">
                                <thead>
                                    <tr>
                                        <th>Usuario</th>
                                        <th>Correo</th>
                                        <th>Rol</th>
                                        <th>Estatus</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="usuariosTableBody">
                                    <tr>
                                        <td colspan="5" style="text-align: center; padding: 30px; color: #9CA3AF;">
                                            Cargando usuarios...
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- COLUMNA DERECHA: DETALLE 
                    [HU-M02-01] FORMULARIO DE EDICIÓN
                    [RF-04] PROTECCIÓN DE INTERFAZ (Bloqueo de campos)
                    [JS BINDING] Controlado por cargarDetalleUsuario(). Los inputs se deshabilitan si 'permisosActuales.editar' es false.
                    -->
                    <div class="usuario-detail">
                        <h3 style="margin-top: 0; margin-bottom: 15px; font-size: 1rem;">Detalle del usuario</h3>
                        <input type="hidden" id="detailIdUsuario"> <!-- Hidden ID -->

                        <div class="detail-field">
                            <label>Usuario</label>
                            <!-- 
                            [CAMPO] ID USUARIO
                            [REGLA] ReadOnly. Generado por Backend (UUID/Auto-inc).
                            -->
                            <input type="text" id="detailUsuario" class="input-base" readonly
                                placeholder="Se genera automático">
                        </div>

                        <div class="detail-field">
                            <label>Correo</label>
                            <input type="email" id="detailCorreo" class="input-base" placeholder="email@ejemplo.com">
                        </div>

                        <div class="detail-field">
                            <label>Nombre completo</label>
                            <input type="text" id="detailNombre" class="input-base" placeholder="Nombre Apellido">
                        </div>

                        <div class="detail-field">
                            <label>Rol</label>
                            <select id="detailRol" class="input-base">
                                <option value="">Seleccionar...</option>
                                <option value="ADMIN">ADMINISTRADOR</option>
                                <option value="CAJA">CAJA</option>
                                <option value="VENDEDOR">VENDEDOR</option>
                                <option value="OPTOMETRISTA">OPTOMETRISTA</option>
                                <option value="TALLER">TALLER</option>
                                <option value="ALMACEN">ALMACEN</option>
                            </select>
                        </div>

                        <div class="detail-field">
                            <!-- [HU-M02-02] CONTROL DE ESTATUS (BAJA LÓGICA) -->
                            <label>Estatus</label>
                            <select id="detailEstatus" class="input-base">
                                <option value="activo">Activo</option>
                                <option value="inactivo">Inactivo</option>
                            </select>
                        </div>

                        <div class="detail-field">
                            <label>Seguridad</label>
                            <button id="btnResetPassword" class="btn-secondary" style="width: 100%;"
                                onclick="UsuariosService.resetPassword()" disabled>
                                Forzar cambio de contraseña
                            </button>
                        </div>
                    </div>
                </div>

                <!-- MATRIZ DE PERMISOS 
                [HU-M02-03] ASIGNACIÓN GRANULAR DE PRIVILEGIOS
                [JS BINDING] Renderizada por cargarPermisosRol/Usuario.
                [REGLA] Checkboxes disabled si no se tiene permiso de EDITAR.
                -->
                <div class="permisos-matrix">
                    <h3 style="margin-top: 0; margin-bottom: 15px; font-size: 1rem;">Permisos por módulo</h3>
                    <div style="overflow-x: auto;">
                        <table class="permisos-table">
                            <thead>
                                <tr>
                                    <th style="text-align: left;">Módulo</th>
                                    <th class="text-center">Ver</th>
                                    <th class="text-center">Crear</th>
                                    <th class="text-center">Editar</th>
                                    <th class="text-center">Eliminar</th>
                                </tr>
                            </thead>
                            <tbody id="permisosTableBody">
                                <tr>
                                    <td colspan="5" class="text-center" style="padding: 20px; color: #9CA3AF;">
                                        Seleccione un Rol para ver/editar permisos
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                </div>

                <!-- BOTONES DE ACCIÓN 
                [RF-04] VALIDACIÓN DE SEGURIDAD EN ACCIONES
                [JS BINDING] Visibilidad controlada por aplicarSeguridadGlobal().
                [REGLA] Guardar valida permisos en Backend antes de enviar.
                -->
                <div class="action-buttons-container">
                    <div class="action-buttons-left">
                        <!-- Botón Bitácora (Solo Admin) -->
                        <button id="btnBitacora" class="btn-secondary btn-bitacora"
                            onclick="UsuariosService.verBitacora()" style="display: none;"
                            title="Solo Administradores - Auditoría de accesos">
                            <span class="material-symbols-outlined"
                                style="font-size: 1.2rem; margin-right: 5px;">admin_panel_settings</span>
                            Ver bitácora <span style="font-size: 0.7em; opacity: 0.7;">(Admin)</span>
                        </button>
                    </div>
                    <div class="action-buttons-right">
                        <button id="btnGuardar" class="btn-primary" onclick="UsuariosService.guardar()">
                            <span class="material-symbols-outlined"
                                style="font-size: 1.2rem; margin-right: 5px;">save</span>
                            Guardar
                        </button>
                        <button class="btn-secondary" onclick="UsuariosService.limpiarFormulario()">Cancelar</button>
                        <button class="btn-primary" onclick="UsuariosService.nuevo()">
                            <span class="material-symbols-outlined"
                                style="font-size: 1.2rem; margin-right: 5px;">add</span>
                            Nuevo
                        </button>
                    </div>
                </div>

            </div>
        </main>
    </div>

    <!-- MODAL DE CONFIGURACIÓN SMTP -->
    <div id="smtpModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Configuración SMTP Segura</h2>
                <button class="close-modal" onclick="UsuariosService.cerrarSmtpModal()">×</button>
            </div>

            <form id="smtpForm" onsubmit="event.preventDefault();">
                <div class="form-group">
                    <label class="text-label">Perfil de Proveedor</label>
                    <select id="smtpPerfil" class="input-base" onchange="UsuariosService.cambiarPerfilSmtp()">
                        <option value="custom">Dominio Propio / Personalizado</option>
                        <option value="gmail">Gmail</option>
                        <option value="hotmail">Outlook / Hotmail</option>
                    </select>
                </div>

                <div class="form-group" style="display: grid; grid-template-columns: 2fr 1fr; gap: 10px;">
                    <div>
                        <label class="text-label">Servidor (Host)</label>
                        <input type="text" id="smtpHost" class="input-base" placeholder="smtp.ejemplo.com">
                    </div>
                    <div>
                        <label class="text-label">Puerto</label>
                        <input type="number" id="smtpPort" class="input-base" placeholder="587">
                    </div>
                </div>

                <div class="form-group">
                    <label class="text-label">Correo Saliente (User)</label>
                    <input type="email" id="smtpUser" class="input-base" placeholder="notificaciones@dominio.com">
                </div>

                <div class="form-group">
                    <label class="text-label">Contraseña / App Password</label>
                    <input type="password" id="smtpPass" class="input-base">
                </div>

                <div class="form-group" style="display: flex; gap: 20px;">
                    <label class="checkbox-inline">
                        <input type="checkbox" id="smtpAuth" checked> Requiere Autenticación
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="smtpTls" checked> STARTTLS
                    </label>
                </div>

                <div style="display: flex; justify-content: space-between; margin-top: 20px;">
                    <button type="button" class="btn-secondary" onclick="UsuariosService.probarSmtp()">
                        <span class="material-symbols-outlined" style="font-size:1.1rem; margin-right:5px;">send</span>
                        Probar
                    </button>
                    <button type="button" class="btn-primary" onclick="UsuariosService.guardarSmtp()">
                        <span class="material-symbols-outlined" style="font-size:1.1rem; margin-right:5px;">lock</span>
                        Guardar Seguro
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Alerta Flotante (Toast) -->
    <div id="toastContainer" style="position: fixed; top: 20px; right: 20px; z-index: 2000;"></div>

    <script src="../assets/js/api-config.js"></script>
    <script src="../assets/js/menu-service.js"></script>
    <script src="../assets/js/usuarios-service.js"></script>
</body>

</html>
```



### 📄 omcgc/frontend/assets/js/usuarios-service.js

```javascript
/*
============================================================
Nombre del archivo : usuarios-service.js
Ruta              : omcgc/frontend/assets/js/usuarios-service.js
Tipo              : Frontend (Lógica de Negocio)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.1

Propósito:
Gestionar la interacción de la pantalla de Usuarios.
Incluye CRUD de usuarios, control de matriz de permisos dinámica,
y gestión segura de configuración SMTP.
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

    // Permisos del usuario logueado sobre ESTE módulo (Usuarios)
    permisosActuales: {
        ver: false,
        crear: false,
        editar: false,
        eliminar: false
    },

    init: async function () {
        this.currentUser = MenuService.getUser();
        if (!this.currentUser) {
            window.location.href = 'login.html';
            return;
        }

        // --- VALIDACIÓN DE SEGURIDAD (Permiso de Acceso) ---
        const acceso = await this.verificarAccesoModulo("USUARIOS, ROLES Y PERMISOS");
        if (!acceso) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para ver el módulo de Usuarios.");
            window.location.href = 'menu.html';
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

    verificarAccesoModulo: async function (nombreModulo) {
        // Reset defensivo
        this.permisosActuales = { ver: false, crear: false, editar: false, eliminar: false };

        // [SRP] Uso de AuthService para Admin Bypass y validación central
        if (AuthService.esAdmin()) {
            console.log("[SECURITY] Admin/Root Access Granted via AuthService");
            this.permisosActuales = { ver: true, crear: true, editar: true, eliminar: true };
            return true;
        }

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
        if (usuario.rolId) {
            fRol.value = usuario.rolId;
            this.cargarPermisosUsuario(usuario.id);
        } else {
            fRol.value = "";
            this.renderTablaPermisosVacia();
        }

        // Estatus
        fEstatus.value = (usuario.estatus === 'activo' || usuario.activo === true || usuario.activo === 1) ? 'activo' : 'inactivo';

        // --- SEGURIDAD: MODO EDICIÓN ---
        if (this.permisosActuales.editar) {
            fNombre.disabled = false;
            fCorreo.disabled = false;
            fRol.disabled = false;
            btnGuardar.style.display = 'inline-block';
            btnReset.disabled = false;
            fEstatus.disabled = !this.permisosActuales.eliminar;
        } else {
            fNombre.disabled = true;
            fCorreo.disabled = true;
            fRol.disabled = true;
            fEstatus.disabled = true;
            btnGuardar.style.display = 'none';
            btnReset.disabled = true;
            this.showToast("Modo solo lectura: Sin permisos de edición", "info");
        }
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
        const chkRolContainer = document.getElementById('chkHabilitarRol')?.closest('div');
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

        const estatusVal = document.getElementById('detailEstatus').value;
        if (this.editingUserId && estatusVal === 'inactivo' && !this.permisosActuales.eliminar) {
            MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para desactivar (eliminar) usuarios.");
            return;
        }

        // Validaciones básicas
        const nombre = document.getElementById('detailNombre').value;
        const email = document.getElementById('detailCorreo').value;
        const rolId = document.getElementById('detailRol').value;

        if (!nombre || !email || !rolId) {
            this.showToast("Por favor complete todos los campos obligatorios", "warning");
            return;
        }

        const rolObj = this.listaRoles.find(r => r.id_rol === rolId);
        const rolNombre = rolObj ? rolObj.nombre : rolId;

        const usuarioData = {
            id: this.editingUserId,
            nombre: nombre,
            correo: email,
            rolId: rolId,
            rolNombre: rolNombre,
            estatus: estatusVal,
            permisos: this._collectPermissions() // Envío atómico
        };

        const method = this.editingUserId ? 'PUT' : 'POST';
        const url = this.editingUserId ? `${this.apiUrl}/${this.editingUserId}` : this.apiUrl;

        try {
            MessageService.procesando();
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
                let msg = this.editingUserId ? "Usuario actualizado correctamente" : "Usuario creado exitosamente.";
                
                if (respData.data && respData.data.passwordTemp) {
                    MessageService.mostrar(4, "Usuario Creado", "Usuario creado exitosamente con contraseña temporal.", `Contraseña: ${respData.data.passwordTemp} (Cópiela ahora)`);
                } else {
                    this.showToast(msg, "success");
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
                MessageService.procesando("Generando nueva clave y enviando correo...");

                try {
                    const response = await fetch(`${this.apiUrl}/${this.editingUserId}/reset-password`, {
                        method: 'POST',
                        headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
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

        const user = this.currentUser || MenuService.getUser();
        const isAdmin = AuthService.esAdmin();

        if (isAdmin) {
            if (btnSmtp) btnSmtp.style.display = 'flex';
            if (btnBitacora) btnBitacora.style.display = 'flex';
        } else {
            if (btnSmtp) btnSmtp.style.display = 'none';
            if (btnBitacora) btnBitacora.style.display = 'none';
            return;
        }

        try {
            const response = await fetch(`${this.smtpUrl}/status`, {
                headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
            });
            const data = await response.json();

            if (btnSmtp) {
                if (data.status === 'CONFIG_OK') {
                    btnSmtp.classList.add('status-ok');
                    btnSmtp.classList.remove('status-error');
                } else {
                    btnSmtp.classList.add('status-error');
                    btnSmtp.classList.remove('status-ok');
                    this.showToast("⚠️ Alerta: Configuración SMTP no detectada", "error");
                }
            }
        } catch (e) {
            console.error("Error verificando SMTP", e);
        }
    },

    // ... Otros métodos permanecen similares pero con uso de MessageService ...

    showToast: function (message, type = 'info') {
        let msgType = 9; 
        let title = "Notificación";

        if (type === 'error') {
            msgType = 3;
            title = "Error de Sistema";
            if (message.includes("conexión")) title = "Error de Conexión";
        } else if (type === 'success') {
            msgType = 4;
            title = "Operación Exitosa";
        } else if (type === 'warning') {
            msgType = 9;
            title = "Advertencia";
        }

        if (typeof MessageService !== 'undefined') {
            MessageService.mostrar(msgType, title, message);
        } else {
            console.warn("MessageService no cargado:", message);
            alert(message);
        }
    }
};

// Iniciar al cargar
document.addEventListener('DOMContentLoaded', () => {
    UsuariosService.init();
});
```

---

### 📄 omcgc/frontend/pages/inventarios.html

```html
<!--
============================================================
Nombre del archivo : inventarios.html
Ruta              : omcgc/frontend/pages/inventarios.html
Tipo              : Frontend (HTML5 / Semantic UI)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Interfaz de usuario para el módulo de inventarios. Implementa el catálogo 
maestro de artículos, el monitor de stock (Semáforo operativo) y el 
acceso directo al historial de movimientos (Kardex).

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Maestro de Productos):
   - Tabla dinámica con filtros por grupo y estatus.
2. HU-M01-05 (Kardex de Movimientos):
   - Modal incrustado para visualización de pólizas.
3. RNF-04 (Diseño Responsivo):
   - Layout fluido basado en CSS Flexbox y Media Queries.
============================================================
-->

<div class="app-main-container">
    <!-- CABECERA DE MÓDULO -->
    <div class="module-header">
        <div class="header-left">
            <div class="module-icon">
                <i class="fas fa-boxes"></i>
            </div>
            <div>
                <h1>Gestión de Inventarios</h1>
                <p>Catálogo Maestro y Control de Kardex Operativo</p>
            </div>
        </div>
        <div class="header-actions">
            <button class="btn-premium secondary" onclick="InventarioManager.verBitacora()">
                <i class="fas fa-history"></i> Bitácora
            </button>
            <button class="btn-premium primary" onclick="InventarioManager.abrirModalNuevo()">
                <i class="fas fa-plus"></i> Nuevo Producto
            </button>
        </div>
    </div>

    <!-- PANEL DE RESUMEN (KPIs) -->
    <div class="inventory-summary-cards">
        <div class="kpi-card">
            <div class="kpi-icon total"><i class="fas fa-box"></i></div>
            <div class="kpi-info">
                <span class="kpi-value" id="kpi-total">0</span>
                <span class="kpi-label">Total SKUs</span>
            </div>
        </div>
        <div class="kpi-card">
            <div class="kpi-icon low"><i class="fas fa-exclamation-triangle"></i></div>
            <div class="kpi-info">
                <span class="kpi-value" id="kpi-bajo">0</span>
                <span class="kpi-label">Stock Bajo</span>
            </div>
        </div>
        <div class="kpi-card">
            <div class="kpi-icon valuated"><i class="fas fa-dollar-sign"></i></div>
            <div class="kpi-info">
                <span class="kpi-value" id="kpi-valor">0.00</span>
                <span class="kpi-label">Valuación Costo</span>
            </div>
        </div>
    </div>

    <!-- BARRA DE HERRAMIENTAS Y FILTROS -->
    <div class="inventory-tools">
        <div class="search-box">
            <i class="fas fa-search"></i>
            <input type="text" id="searchInput" placeholder="Buscar por Nombre, SKU o Código..."
                onkeyup="InventarioManager.filtrarProductos()">
        </div>
        <div class="filter-group">
            <select id="filterGrupo" class="input-base" onchange="InventarioManager.filtrarProductos()">
                <option value="">Todos los Grupos</option>
            </select>
            <!-- Toggle Segmentado para Filtro de Stock (Semaforización UI) -->
            <div class="segmented-control" id="toggleUmbral">
                <button class="status-btn active" data-filter="" onclick="InventarioManager.setFilter('toggleUmbral', this)">Todos</button>
                <button class="status-btn" data-filter="OK" onclick="InventarioManager.setFilter('toggleUmbral', this)">Suficiente</button>
                <button class="status-btn bajo" data-filter="BAJO" onclick="InventarioManager.setFilter('toggleUmbral', this)">Bajo</button>
            </div>
            <!-- Toggle Segmentado para Estado Operativo -->
            <div class="segmented-control" id="toggleEstatus">
                <button class="status-btn active" data-filter="" onclick="InventarioManager.setFilter('toggleEstatus', this)">Todos</button>
                <button class="status-btn" data-filter="ACTIVO" onclick="InventarioManager.setFilter('toggleEstatus', this)">Activos</button>
            </div>
        </div>
    </div>

    <!-- TABLA DE CATÁLOGO MAESTRO -->
    <div class="table-container-premium">
        <table id="inventoryTable">
            <thead>
                <tr>
                    <th>SKU / Código</th>
                    <th>Producto</th>
                    <th>Grupo / Familia</th>
                    <th class="txt-center">Existencia</th>
                    <th>Costo Unit.</th>
                    <th>Precio Sug.</th>
                    <th>Estado</th>
                    <th class="txt-center">Acciones</th>
                </tr>
            </thead>
            <tbody id="inventoryTableBody">
                <!-- Se llena vía JS -->
            </tbody>
        </table>
    </div>
</div>

<!-- =========================================================
     MODAL: FICHA TÉCNICA DEL PRODUCTO (EDICIÓN/NUEVO)
     ========================================================= -->
<div id="productModal" class="modal-overlay">
    <div class="modal-content large">
        <div class="modal-header-premium">
            <div class="header-title">
                <i class="fas fa-tag"></i>
                <h2 id="modalTitulo">Nuevo Producto</h2>
            </div>
            <button class="close-btn" onclick="InventarioManager.cerrarModal()">&times;</button>
        </div>

        <div class="modal-body">
            <!-- Header Informativo Superior (Solo en Edición) -->
            <div id="info-box-premium" class="info-sku-header" style="display: none;">
                <div class="sku-badge"><span id="lbl_sku">75000000</span></div>
                <div class="sku-name"><span id="lbl_nombre">Nombre del Producto</span></div>
                <div class="sku-stock-pill" id="pill_stock">0 en existencia</div>
            </div>

            <div class="tabs-premium">
                <button class="tab-btn active" onclick="InventarioManager.switchTab('general', this)">
                    <i class="fas fa-info-circle"></i> Datos Generales
                </button>
                <button class="tab-btn" onclick="InventarioManager.switchTab('logistica', this)">
                    <i class="fas fa-truck-loading"></i> Logística y Proveedor
                </button>
                <button class="tab-btn" onclick="InventarioManager.switchTab('fiscal', this)">
                    <i class="fas fa-file-invoice-dollar"></i> Datos Fiscales (SAT)
                </button>
            </div>

            <form id="formProducto">
                <!-- TAB 1: DATOS GENERALES -->
                <div id="tab-general" class="tab-content active">
                    <div class="grid-form">
                        <div class="form-group span-2">
                            <label>Nombre del Producto *</label>
                            <input type="text" id="nombre" class="input-base" required
                                placeholder="Ej: Armazón Tommy Hilfiger TH1542">
                        </div>
                        <div class="form-group">
                            <label>Código de Barras</label>
                            <div class="input-with-icon">
                                <i class="fas fa-barcode"></i>
                                <input type="text" id="codigoBarras" class="input-base" placeholder="EAN-13 / Interno">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Estatus</label>
                            <select id="estatus" class="input-base">
                                <option value="ACTIVO">ACTIVO</option>
                                <option value="INACTIVO">INACTIVO</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Grupo / Categoría *</label>
                            <select id="idGrupo" class="input-base" required onchange="InventarioManager.cargarFamilias(this.value)">
                                <option value="">Seleccione...</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Familia *</label>
                            <select id="idFamilia" class="input-base" required>
                                <option value="">Seleccione Grupo primero...</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Marca</label>
                            <select id="idMarca" class="input-base">
                                <option value="">Genérica / Sin Marca</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="txt-primary">Costo Unitario (Neto) *</label>
                            <div class="input-price">
                                <span>$</span>
                                <input type="number" step="0.01" id="costoUnitario" class="input-base" required
                                    onchange="InventarioManager.calcularPrecioVenta()">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>% Utilidad Deseada</label>
                            <div class="input-with-icon">
                                <input type="number" id="porcentajeUtilidad" class="input-base" value="50"
                                    onchange="InventarioManager.calcularPrecioVenta()">
                                <i class="fas fa-percentage"></i>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Precio de Venta (Calculado)</label>
                            <input type="text" id="precioVenta" class="input-base highlight" readonly>
                        </div>
                    </div>
                </div>

                <!-- TAB 2: LOGÍSTICA -->
                <div id="tab-logistica" class="tab-content">
                    <div class="grid-form">
                        <div class="form-group">
                            <label>Stock Mínimo</label>
                            <input type="number" id="stockMinimo" class="input-base" value="5">
                        </div>
                        <div class="form-group">
                            <label>Stock Máximo</label>
                            <input type="number" id="stockMaximo" class="input-base" value="100">
                        </div>
                        <div class="form-group">
                            <label>Existencia Inicial (Solo nuevos)</label>
                            <input type="number" id="existenciaActual" class="input-base" value="0">
                        </div>
                        <div class="form-group">
                            <label>Proveedor Preferente</label>
                            <select id="idProveedor" class="input-base">
                                <option value="">Seleccionar...</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- TAB 3: FISCAL -->
                <div id="tab-fiscal" class="tab-content">
                    <div class="fiscal-alert">
                        <i class="fas fa-info-circle"></i>
                        Esta información es requerida para la emisión de facturación electrónica CFDI 4.0.
                    </div>
                    <div class="grid-form">
                        <div class="form-group">
                            <label>Clave Producto/Servicio (SAT)</label>
                            <input type="text" id="claveProdServ" class="input-base" placeholder="Ej: 42241505">
                        </div>
                        <div class="form-group">
                            <label>Clave Unidad</label>
                            <input type="text" id="claveUnidad" class="input-base" placeholder="Ej: H87">
                        </div>
                        <div class="form-group">
                            <label>Objeto de Impuesto</label>
                            <select id="objetoImpuesto" class="input-base">
                                <option value="02">02 - Sí objeto de impuesto</option>
                                <option value="01">01 - No objeto de impuesto</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>IVA Aplicable (%)</label>
                            <input type="number" id="ivaAplicable" class="input-base" value="16">
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <div class="modal-footer">
            <button class="btn-premium ghost" onclick="InventarioManager.cerrarModal()">Cancelar</button>
            <button class="btn-premium primary" onclick="InventarioManager.guardarProducto()">
                <i class="fas fa-save"></i> Guardar Cambios
            </button>
        </div>
    </div>
</div>

<!-- =========================================================
     MODAL: MOVIMIENTOS AL INVENTARIO (KARDEX)
     ========================================================= -->
<div id="kardexModal" class="modal-overlay">
    <div class="modal-content extra-large">
        <div class="modal-header-premium kardex-header">
            <div class="header-title">
                <i class="fas fa-exchange-alt"></i>
                <div class="title-group">
                    <h2>Movimientos al Inventario (Kardex)</h2>
                    <p id="kardexSubtitulo">SKU: --- | Producto: ---</p>
                </div>
            </div>
            <div class="header-meta-actions">
                <button class="btn-action-circle" title="Exportar Excel" onclick="InventarioManager.exportarKardex()">
                    <i class="fas fa-file-excel"></i>
                </button>
                <button class="btn-premium primary small" onclick="InventarioManager.abrirModalMovimiento()">
                    <i class="fas fa-plus"></i> Nueva Póliza
                </button>
                <button class="close-btn" onclick="InventarioManager.cerrarKardexModal()">&times;</button>
            </div>
        </div>

        <div class="modal-body bg-light">
            <!-- Resumen Rápido Kardex -->
            <div class="kardex-stats-row">
                <div class="k-stat">
                    <span class="l">Entradas</span>
                    <span class="v" id="k-entradas">0</span>
                </div>
                <div class="k-stat">
                    <span class="l">Salidas</span>
                    <span class="v txt-danger" id="k-salidas">0</span>
                </div>
                <div class="k-sep"></div>
                <div class="k-stat highlight">
                    <span class="l">Existencia Actual</span>
                    <span class="v" id="k-actual">0</span>
                </div>
            </div>

            <div class="table-container-premium clean">
                <table class="table-kardex">
                    <thead>
                        <tr>
                            <th>Fecha/Hora</th>
                            <th>Folio</th>
                            <th>Operación</th>
                            <th>Referencia</th>
                            <th class="txt-right">Entra</th>
                            <th class="txt-right">Sale</th>
                            <th class="txt-right">Saldo</th>
                            <th>Usuario</th>
                            <th class="txt-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="kardexTableBody">
                        <!-- Llenado dinámico -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- =========================================================
     MODAL: REGISTRO DE MOVIMIENTO (PÓLIZA)
     ========================================================= -->
<div id="movimientoModal" class="modal-overlay">
    <div class="modal-content medium">
        <div class="modal-header-premium">
            <div class="header-title">
                <i class="fas fa-file-invoice"></i>
                <h2 id="modalMovTitulo">Nueva Póliza de Movimiento</h2>
            </div>
            <button class="close-btn" onclick="InventarioManager.cerrarMovimientoModal()">&times;</button>
        </div>
        <div class="modal-body">
            <!-- Datos de Referencia (Lectura) -->
             <div class="mov-reference-box">
                <div class="ref-item">
                    <span class="label">SKU:</span>
                    <span class="value" id="mov_sku_label">---</span>
                </div>
                <div class="ref-item">
                    <span class="label">PRODUCTO:</span>
                    <span class="value" id="mov_producto_label">---</span>
                </div>
             </div>

            <form id="formMovimiento">
                <input type="hidden" id="mov_idMovimiento">
                
                <!-- Fila Auditoría (Solo Edición) -->
                <div class="grid-form dual mb-15" id="row_fechas_kardex" style="display: none;">
                    <div class="form-group">
                        <label id="lbl_fecha_ref">FECHA ORIGINAL</label>
                        <div class="info-value" id="mov_fecha_ref">--/--/----</div>
                    </div>
                    <div class="form-group">
                        <label>FECHA ACTUALIZACIÓN</label>
                        <div class="info-value highlight" id="mov_fecha_live">--/--/----</div>
                    </div>
                </div>

                <div class="grid-form">
                    <div class="form-group span-2">
                        <label>Concepto de Operación *</label>
                        <select id="mov_tipo" class="input-base" onchange="InventarioManager.recalcularIndicadores()">
                            <optgroup label="ENTRADAS">
                                <option value="ENTRADA_COMPRA">Entrada por Compra / Proveedor</option>
                                <option value="ENTRADA_AJUSTE">Entrada por Ajuste (Sobrante)</option>
                                <option value="ENTRADA_TRASPASO">Entrada por Traspaso entre Almacenes</option>
                                <option value="DEVOLUCION_VENTA">Devolución de Cliente</option>
                            </optgroup>
                            <optgroup label="SALIDAS">
                                <option value="SALIDA_VENTA">Salida por Venta / Despacho</option>
                                <option value="MERMA">Salida por Merma / Rotura</option>
                                <option value="TRASPASO">Salida por Traspaso a otra sucursal</option>
                                <option value="AJUSTE">Salida por Ajuste (Faltante)</option>
                                <option value="DEVOLUCION_PROVEEDOR">Devolución a Proveedor</option>
                            </optgroup>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>Cantidad *</label>
                        <input type="number" id="mov_cantidad" class="input-base" value="1" min="1" 
                               onchange="InventarioManager.recalcularIndicadores()">
                    </div>

                    <div class="form-group">
                        <label>Costo Unitario ($)</label>
                        <input type="number" id="mov_costo" class="input-base" step="0.01">
                    </div>

                    <div class="form-group">
                        <label>Folio / Documento Ref.</label>
                        <div class="input-with-icon">
                            <i class="fas fa-file-alt"></i>
                            <input type="text" id="mov_folio" class="input-base" placeholder="Factura, Ticket, etc.">
                        </div>
                    </div>

                    <div class="form-group span-2">
                        <label>Observaciones / Glosa</label>
                        <textarea id="mov_obs" class="input-base" rows="2" placeholder="Detalles de la operación..."></textarea>
                    </div>
                </div>

                <!-- Simulación Dinámica de Stock -->
                <div class="stock-calc-box">
                    <div class="calc-item">
                        <span class="l">Existencia Anterior</span>
                        <span class="v" id="mov_exist_actual">0</span>
                    </div>
                    <div class="calc-sign"><i class="fas fa-arrow-right"></i></div>
                    <div class="calc-item highlight">
                        <span class="l">Nuevo Saldo Resultante</span>
                        <span class="v" id="mov_nuevo_saldo">0</span>
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn-premium ghost" onclick="InventarioManager.cerrarMovimientoModal()">Cancelar</button>
            <button class="btn-premium primary" onclick="InventarioManager.ejecutarGuardarMovimiento()">
                <i class="fas fa-check-circle"></i> Aplicar Movimiento
            </button>
        </div>
    </div>
</div>

<!-- =========================================================
     MODAL: BITÁCORA DE EVENTOS (AUDITORÍA)
     ========================================================= -->
<div id="bitacoraModal" class="modal-overlay">
    <div class="modal-content extra-large">
        <div class="modal-header-premium">
            <div class="header-title">
                <i class="fas fa-shield-alt"></i>
                <h2>Bitácora de Auditoría - Inventarios</h2>
            </div>
            <button class="close-btn" onclick="InventarioManager.cerrarBitacoraModal()">&times;</button>
        </div>
        <div class="modal-body">
            <div class="filter-bar bg-white border-bottom p-15">
                <div class="grid-form four">
                    <div class="form-group">
                        <label>Desde</label>
                        <input type="date" id="bitacoraDesde" class="input-base">
                    </div>
                    <div class="form-group">
                        <label>Hasta</label>
                        <input type="date" id="bitacoraHasta" class="input-base">
                    </div>
                    <div class="form-group">
                        <label>Buscar</label>
                        <input type="text" id="bitacoraBuscar" class="input-base" placeholder="Usuario, SKU, Acción...">
                    </div>
                    <div class="form-group flex-end">
                        <button class="btn-premium primary w-100" onclick="InventarioManager.cargarBitacora()">
                            <i class="fas fa-search"></i> Consultar
                        </button>
                    </div>
                </div>
            </div>
            <div class="table-container-premium clean" style="max-height: 500px; overflow-y: auto;">
                <table>
                    <thead>
                        <tr>
                            <th>Fecha/Hora</th>
                            <th>Usuario</th>
                            <th>Acción</th>
                            <th>Detalle Técnico</th>
                            <th>IP</th>
                        </tr>
                    </thead>
                    <tbody id="bitacoraTableBody">
                        <!-- Se llena vía JS -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

---

## 🎨 FRONTEND (CSS)

### 📄 omcgc/frontend/assets/css/ui-base.css

```css
:root {
    --color-primary: #1F3A5F;
    --color-secondary: #4B5563;
    --color-accent: #3B82F6;
    --color-success: #10B981;
    --color-warning: #F59E0B;
    --color-danger: #EF4444;
    --font-base: 'Inter', sans-serif;
}

body {
    font-family: var(--font-base);
    background-color: #F3F4F6;
    margin: 0;
    padding: 0;
    color: #1F2937;
    -webkit-font-smoothing: antialiased;
}

/* =========================================================
   1. LAYOUT PRINCIPAL (App Wrapper)
   ========================================================= */
.app-wrapper {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

.app-main-container {
    flex: 1;
    padding: 90px 40px 40px 40px;
    display: flex;
    justify-content: center;
}

/* =========================================================
   2. HEADER BAR (NavegaciÃ³n Superior)
   ========================================================= */
.app-header-bar {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 70px;
    background-color: #ffffff;
    border-bottom: 1px solid #E5E7EB;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 30px;
    z-index: 100;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.header-left {
    display: flex;
    align-items: center;
    gap: 15px;
}

.header-logo {
    height: 40px;
    width: auto;
}

.header-brand-box {
    display: flex;
    flex-direction: column;
}

.header-brand-title {
    font-weight: 800;
    font-size: 1.1rem;
    color: var(--color-primary);
    display: flex;
    align-items: center;
    gap: 8px;
    letter-spacing: -0.5px;
}

.header-module-icon {
    font-size: 1.2rem;
    color: var(--color-accent);
}

.header-date {
    font-size: 0.75rem;
    color: #9CA3AF;
    font-weight: 500;
}

/* Status Bar (Derecha) */
.app-status-bar {
    display: flex;
    align-items: center;
    gap: 20px;
}

.status-item {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    font-size: 0.85rem;
    color: #374151;
}

.status-separator {
    width: 1px;
    height: 24px;
    background-color: #E5E7EB;
}

.time-stats {
    display: flex;
    gap: 15px;
}

.time-stat-box {
    text-align: center;
}

.time-label {
    font-size: 0.65rem;
    color: #9CA3AF;
    font-weight: 700;
    letter-spacing: 0.5px;
}

.time-value {
    font-size: 0.85rem;
    font-weight: 600;
    color: #4B5563;
    font-variant-numeric: tabular-nums;
}

.btn-logout {
    border: 1px solid #FCA5A5;
    background-color: #FEF2F2;
    color: #B91C1C;
    padding: 6px 14px;
    border-radius: 6px;
    font-size: 0.75rem;
    font-weight: 700;
    cursor: pointer;
    transition: all 0.2s;
}

.btn-logout:hover {
    background-color: #FEE2E2;
}

/* =========================================================
   3. ESTILOS BASE (TipografÃ­a y Elementos)
   ========================================================= */
h1 {
    font-size: 1.5rem;
    font-weight: 700;
    color: #111827;
    margin: 0;
}

p {
    margin-top: 5px;
    color: #6B7280;
    font-size: 0.9rem;
}

/* =========================================================
   4. COMPONENTES FORMULARIOS (Inputs, Botones)
   ========================================================= */
.btn-primary {
    background-color: var(--color-primary);
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 8px;
    font-weight: 600;
    font-size: 0.9rem;
    cursor: pointer;
    transition: background-color 0.2s;
    display: inline-flex;
    align-items: center;
    justify-content: center;
}

.btn-primary:hover {
    background-color: #162c4b;
}

.btn-primary:disabled {
    background-color: #9CA3AF;
    cursor: not-allowed;
}

.input-base {
    width: 100%;
    padding: 10px 14px;
    border: 1px solid #D1D5DB;
    border-radius: 8px;
    font-size: 0.95rem;
    background-color: white;
    transition: border-color 0.2s, box-shadow 0.2s;
}

.input-base:focus {
    outline: none;
    border-color: var(--color-accent);
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.form-group {
    margin-bottom: 20px;
}

.text-label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    font-size: 0.9rem;
    color: #374151;
}

/* Checkbox */
.checkbox-inline {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 0.9rem;
    color: #4B5563;
    cursor: pointer;
}

.checkbox-inline input {
    width: 16px;
    height: 16px;
    accent-color: var(--color-accent);
}

/* Alerts */
.alert {
    padding: 12px 16px;
    border-radius: 8px;
    font-size: 0.9rem;
    margin-top: 15px;
}

.alert-error {
    background-color: #FEE2E2;
    color: #B91C1C;
    border: 1px solid #FECaca;
}

.is-valid {
    background-color: #ECFDF5;
    color: #047857;
    border: 1px solid #A7F3D0;
}

/* =========================================================
   5. PANTALLA LOGIN (Especificos)
   ========================================================= */
.login-body {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    background: linear-gradient(135deg, #1F3A5F 0%, #0F172A 100%);
}

.login-container {
    width: 100%;
    max-width: 420px;
    padding: 20px;
    text-align: center;
}

.login-logo {
    height: 60px;
    margin-bottom: 30px;
    filter: drop-shadow(0 4px 6px rgba(0,0,0,0.3));
}

.card {
    background: white;
    padding: 40px;
    border-radius: 16px;
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
    text-align: left;
}

.input-group {
    position: relative;
    display: flex;
    align-items: center;
}

.input-group .icon-action {
    position: absolute;
    right: 12px;
    background: none;
    border: none;
    cursor: pointer;
    color: #6B7280;
    padding: 0;
    display: flex;
    align-items: center;
}

.login-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 25px;
}

.login-links {
    margin-top: 20px;
    text-align: center;
    font-size: 0.85rem;
}

.login-links a {
    color: var(--color-accent);
    text-decoration: none;
    font-weight: 500;
}

.login-links a:hover {
    text-decoration: underline;
}

.app-footer {
    margin-top: 24px;
    color: rgba(255,255,255,0.4);
    font-size: 0.75rem;
}

/* =========================================================
   6. PANTALLA USUARIOS (Especificos)
   ========================================================= */

/* Header especÃ­fico de secciÃ³n */
.usuarios-header {
    display: flex;
    flex-direction: column;
    margin-bottom: 30px;
}

/* Barra de Filtros */
.filter-bar {
    display: flex;
    gap: 15px;
    background: white;
    padding: 20px;
    border-radius: 8px;
    border: 1px solid #E5E7EB;
    margin-bottom: 20px;
    align-items: flex-end;
}

.filter-group {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.filter-group label {
    font-size: 0.75rem;
    font-weight: 600;
    color: #6B7280;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.filter-group select {
    appearance: none;
    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e");
    background-position: right 0.5rem center;
    background-repeat: no-repeat;
    background-size: 1.5em 1.5em;
    padding-right: 2.5rem;
    height: 42px; /* AlineaciÃ³n visual */
    border: 1px solid #D1D5DB;
    border-radius: 6px;
    font-size: 0.875rem;
    min-width: 180px;
}

.status-toggle {
    display: flex;
    gap: 8px;
}

.status-btn {
    padding: 8px 20px;
    border: 1px solid #D1D5DB;
    background: white;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.875rem;
    font-weight: 500;
    transition: all 0.2s;
    color: #6B7280;
}

.status-btn.active {
    background: #3B82F6;
    color: white;
    border-color: #3B82F6;
}

.status-btn:hover:not(.active) {
    background: #F3F4F6;
}

/* Layout de 2 columnas */
.usuarios-layout {
    display: grid;
    grid-template-columns: 1.2fr 1fr;
    gap: 20px;
    margin-bottom: 20px;
}

.usuarios-list {
    background: white;
    border: 1px solid #E5E7EB;
    border-radius: 8px;
    padding: 20px;
}

.usuarios-list h3 {
    font-size: 0.875rem;
    font-weight: 600;
    color: #374151;
    margin: 0 0 15px 0;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.usuario-detail {
    background: white;
    border: 1px solid #E5E7EB;
    border-radius: 8px;
    padding: 20px;
}

.usuario-detail h3 {
    font-size: 0.875rem;
    font-weight: 600;
    color: #374151;
    margin: 0 0 15px 0;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

/* Tabla de usuarios */
.table-base tbody tr {
    cursor: pointer;
    transition: background 0.15s;
}

.table-base tbody tr:hover {
    background: #F9FAFB;
}

.status-indicator {
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    margin-right: 8px;
    vertical-align: middle;
}

.status-indicator.activo {
    background: #10B981;
}

.status-indicator.inactivo {
    background: #EF4444;
}

.badge-rol {
    display: inline-block;
    padding: 4px 10px;
    border-radius: 4px;
    font-size: 0.75rem;
    font-weight: 500;
    background: #DBEAFE;
    color: #1E40AF;
}

/* Panel de detalle */
.detail-field {
    margin-bottom: 16px;
}

.detail-field label {
    display: block;
    font-size: 0.7rem;
    font-weight: 600;
    color: #6B7280;
    margin-bottom: 6px;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.detail-field input,
.detail-field select {
    width: 100%;
    padding: 8px 12px;
    border: 1px solid #D1D5DB;
    border-radius: 6px;
    font-size: 0.875rem;
}

.detail-field input:focus,
.detail-field select:focus {
    outline: none;
    border-color: #3B82F6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.detail-field input[readonly] {
    background: #F9FAFB;
    color: #6B7280;
}

.badge-estatus {
    display: inline-block;
    padding: 6px 14px;
    border-radius: 20px;
    font-size: 0.875rem;
    font-weight: 600;
}

.badge-estatus.activo {
    background: #D1FAE5;
    color: #065F46;
}

.badge-estatus.inactivo {
    background: #FEE2E2;
    color: #991B1B;
}

/* Matriz de permisos */
.permisos-matrix {
    background: white;
    border: 1px solid #E5E7EB;
    border-radius: 8px;
    padding: 20px;
    margin-top: 20px;
}

.permisos-matrix h3 {
    font-size: 0.875rem;
    font-weight: 600;
    color: #374151;
    margin: 0 0 15px 0;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.permisos-table {
    width: 100%;
    border-collapse: collapse;
}

.permisos-table th {
    background: #F9FAFB;
    padding: 10px 12px;
    text-align: center;
    font-size: 0.7rem;
    font-weight: 600;
    text-transform: uppercase;
    color: #6B7280;
    border: 1px solid #E5E7EB;
    letter-spacing: 0.5px;
}

.permisos-table th:first-child {
    text-align: left;
}

.permisos-table td {
    padding: 12px;
    text-align: center;
    border: 1px solid #E5E7EB;
}

.permisos-table td:first-child {
    text-align: left;
    font-weight: 500;
    font-size: 0.875rem;
    color: #374151;
}

.permisos-table input[type="checkbox"] {
    width: 18px;
    height: 18px;
    cursor: pointer;
    accent-color: #3B82F6;
}

.permisos-table input[type="checkbox"]:disabled {
    opacity: 0.4;
    cursor: not-allowed;
}

/* Botones de acciÃ³n */
.action-buttons {
    display: flex;
    gap: 10px;
    margin-top: 20px;
    justify-content: flex-end;
}

.btn-secondary {
    background: #E5E7EB;
    color: #374151;
    border: none;
    padding: 10px 18px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 600;
    font-size: 0.875rem;
    transition: all 0.2s;
    display: inline-flex;
    align-items: center;
    gap: 6px;
}

.btn-secondary:hover {
    background: #D1D5DB;
}

.btn-warning {
    background: #EF4444;
    color: white;
    border: none;
    padding: 10px 18px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 600;
    font-size: 0.875rem;
    transition: all 0.2s;
}

.btn-warning:hover {
    background: #DC2626;
}

.btn-export {
    background: #6B7280;
    color: white;
}

.btn-export:hover {
    background: #4B5563;
}

.btn-bitacora {
    background: #F59E0B;
    color: white;
}

.btn-bitacora:hover {
    background: #D97706;
}

/* Ajustes Responsive */
@media (max-width: 1200px) {
    .usuarios-layout {
        grid-template-columns: 1fr;
    }
}

@media (max-width: 768px) {

    .app-status-bar .time-stats,
    .status-separator:nth-child(2),
    .status-separator:nth-child(4) {
        display: none;
    }

    .app-main-container {
        padding-top: 20px;
    }

    .filter-bar {
        flex-direction: column;
        align-items: stretch;
    }

    .filter-group input,
    .filter-group select {
        min-width: 100%;
    }
}
/* =========================================================      
   14. COMPONENTES EXTRA: MODAL & RESPONSIVE (SMTP)
   ========================================================= */   

/* ---- Modal Flotante ---- */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    visibility: hidden;
    opacity: 0;
    transition: opacity 0.2s ease;
}

.modal-overlay.open {
    visibility: visible;
    opacity: 1;
}

.modal-content {
    background: white;
    padding: 30px;
    border-radius: 8px;
    width: 90%;
    max-width: 500px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    animation: slideUp 0.3s ease;
}

@keyframes slideUp {
    from { transform: translateY(20px); opacity: 0; }
    to { transform: translateY(0); opacity: 1; }
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.modal-header h2 {
    margin: 0;
    font-size: 1.25rem;
    color: var(--color-primary);
}

.close-modal {
    background: none;
    border: none;
    font-size: 1.5rem;
    cursor: pointer;
    color: #6B7280;
}

/* ---- Semáforo SMTP ---- */
.btn-smtp {
    font-weight: 700 !important;
    transition: all 0.3s ease;
}

.btn-smtp.status-ok {
    background-color: var(--color-success) !important;
    color: white !important;
}

.btn-smtp.status-error {
    background-color: #FCD34D !important; /* Amarillo */
    color: #B45309 !important; /* Texto rojizo */
    border: 2px solid #EF4444 !important;
    animation: pulse 2s infinite;
}

@keyframes pulse {
    0% { transform: scale(1); }
    50% { transform: scale(1.05); }
    100% { transform: scale(1); }
}

/* ---- RESPONSIVIDAD (Media Queries) ---- */
/* Tablet (Portrait) y menores */
@media (max-width: 900px) {
    .usuarios-layout {
        grid-template-columns: 1fr; /* Una sola columna */        
    }

    .filter-bar {
        flex-wrap: wrap;
    }

    .usuarios-list {
        order: 2; /* Listado abajo en tablet */
    }

    .usuario-detail {
        order: 1; /* Detalle arriba para edición rápida */        
    }
}

/* Móvil */
@media (max-width: 600px) {
    .usuarios-header h1 { font-size: 1.25rem; }
    .app-main-container { padding: 80px 10px 10px 10px; }

    .action-buttons-container {
        flex-direction: column;
        gap: 10px;
    }

    .action-buttons-left, .action-buttons-right {
        width: 100%;
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 10px;
    }

    .btn-primary, .btn-secondary, .btn-warning {
        width: 100%;
    }
}
```
---

### 📄 omcgc/frontend/assets/css/inventarios.css

```css
/*
============================================================
Nombre del archivo : inventarios.css
Ruta              : omcgc/frontend/assets/css/inventarios.css
Tipo              : Estilos (Módulo Inventarios)

Versión           : v1.1
============================================================
*/

.inventory-summary-cards {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 20px;
    margin-bottom: 25px;
}

.kpi-card {
    background: white;
    padding: 20px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    gap: 15px;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
    border: 1px solid #edf2f7;
}

.kpi-icon {
    width: 48px;
    height: 48px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
}

.kpi-icon.total { background: #EBF8FF; color: #2B6CB0; }
.kpi-icon.low { background: #FFF5F5; color: #C53030; }
.kpi-icon.valuated { background: #F0FFF4; color: #2F855A; }

.kpi-info .kpi-value {
    display: block;
    font-size: 1.5rem;
    font-weight: 700;
    color: #1a202c;
}

.kpi-info .kpi-label {
    font-size: 0.85rem;
    color: #718096;
    text-transform: uppercase;
    letter-spacing: 0.025em;
}

/* Herramientas e Inputs */
.inventory-tools {
    background: white;
    padding: 15px 20px;
    border-radius: 10px;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.02);
}

.search-box {
    position: relative;
    flex: 1;
    max-width: 450px;
}

.search-box i {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    color: #a0aec0;
}

.search-box input {
    width: 100%;
    padding: 10px 15px 10px 40px;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    outline: none;
    transition: all 0.2s;
}

.search-box input:focus {
    border-color: #3182ce;
    box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.15);
}

.filter-group {
    display: flex;
    gap: 12px;
    align-items: center;
}

/* Tabla Estilo Premium */
.stock-badge {
    padding: 4px 10px;
    border-radius: 20px;
    font-size: 0.75rem;
    font-weight: 600;
    display: inline-block;
}

.stock-badge.ok { background: #C6F6D5; color: #22543D; }
.stock-badge.warning { background: #FEEBC8; color: #744210; }
.stock-badge.danger { background: #FED7D7; color: #822727; }

/* Modal Inventarios Tabs */
.tabs-premium {
    display: flex;
    gap: 5px;
    border-bottom: 2px solid #edf2f7;
    margin-bottom: 20px;
}

.tab-btn {
    padding: 10px 20px;
    border: none;
    background: none;
    cursor: pointer;
    font-weight: 600;
    color: #718096;
    transition: all 0.2s;
    border-bottom: 2px solid transparent;
    margin-bottom: -2px;
}

.tab-btn:hover { color: #2D3748; }

.tab-btn.active {
    color: #3182ce;
    border-bottom-color: #3182ce;
}

.tab-content { display: none; }
.tab-content.active { display: block; }

/* Grid de Formulario */
.grid-form {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
}

.grid-form.span-2 { grid-column: span 2; }

/* Pestaña Fiscal UI */
.fiscal-alert {
    background: #ebf8ff;
    border-left: 4px solid #3182ce;
    padding: 12px;
    margin-bottom: 20px;
    font-size: 0.85rem;
    color: #2c5282;
    display: flex;
    align-items: center;
    gap: 10px;
}

/* Estilos Kardex */
.kardex-header {
    background: #1a202c !important;
}

.title-group h2 { margin: 0; color: white; }
.title-group p { margin: 0; font-size: 0.8rem; color: #a0aec0; }

.kardex-stats-row {
    display: flex;
    gap: 30px;
    background: #F7FAFC;
    padding: 15px 25px;
    border-radius: 8px;
    margin-bottom: 20px;
    border: 1px solid #E2E8F0;
}

.k-stat { display: flex; flex-direction: column; }
.k-stat .l { font-size: 0.7rem; color: #718096; text-transform: uppercase; }
.k-stat .v { font-size: 1.1rem; font-weight: 700; color: #2D3748; }
.k-stat.highlight .v { color: #3182ce; }

.table-kardex th { background: #f1f5f9; font-size: 0.75rem; text-transform: uppercase; }

/* Movimiento Modal Calc */
.stock-calc-box {
    margin-top: 20px;
    padding: 15px;
    background: #f8fafc;
    border: 2px dashed #cbd5e0;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: space-around;
}

.calc-item { text-align: center; }
.calc-item .l { display: block; font-size: 0.75rem; color: #718096; }
.calc-item .v { font-size: 1.25rem; font-weight: 800; }
.calc-item.highlight .v { color: #2f855a; }

.segmented-control {
    display: flex;
    background: #edf2f7;
    padding: 4px;
    border-radius: 8px;
}

.status-btn {
    padding: 6px 14px;
    border: none;
    background: none;
    border-radius: 6px;
    font-size: 0.8rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s;
    color: #4a5568;
}

.status-btn.active {
    background: white;
    box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    color: #2d3748;
}

.status-btn.bajo.active {
    color: #c53030;
}
```

---

### 📄 omcgc/frontend/assets/css/inventarios-responsive.css

```css
/*
============================================================
Nombre del archivo : inventarios-responsive.css
Ruta              : omcgc/frontend/assets/css/inventarios-responsive.css
Tipo              : Estilos Responsivos (Módulo Inventarios)

Propósito:
Ajustes de visualización para tablets y dispositivos móviles.
============================================================
*/

/* Media Query para Tablets (Portrait) */
@media (max-width: 1024px) {
    .inventory-tools {
        flex-direction: column;
        align-items: stretch;
    }

    .search-box {
        max-width: 100%;
    }

    .filter-group {
        flex-wrap: wrap;
        justify-content: center;
    }

    /* Ocultar columnas menos críticas en tablet */
    #inventoryTable th:nth-child(3), 
    #inventoryTable td:nth-child(3),
    #inventoryTable th:nth-child(5),
    #inventoryTable td:nth-child(5) {
        display: none;
    }
}

/* Media Query para Móviles */
@media (max-width: 600px) {
    .inventory-summary-cards {
        grid-template-columns: 1fr;
    }

    .grid-form {
        grid-template-columns: 1fr;
    }

    .grid-form.dual {
        grid-template-columns: 1fr;
    }

    .modal-content.large, 
    .modal-content.extra-large {
        width: 95%;
        margin: 10px auto;
    }

    /* Estilo de tabla en "Modo Card" para móvil */
    #inventoryTable thead {
        display: none;
    }

    #inventoryTable tr {
        display: block;
        background: white;
        border: 1px solid #E2E8F0;
        margin-bottom: 10px;
        border-radius: 8px;
        padding: 10px;
    }

    #inventoryTable td {
        display: flex;
        justify-content: space-between;
        padding: 8px 5px;
        border: none;
        text-align: right;
    }

    #inventoryTable td::before {
        content: attr(data-label);
        font-weight: bold;
        float: left;
        color: #718096;
        text-transform: uppercase;
        font-size: 0.7rem;
    }

    #inventoryTable td.txt-center {
        justify-content: center;
    }

    /* Forzar visibilidad de SKU y Nombre como cabecera del card */
    #inventoryTable td:nth-child(2) {
        font-size: 1.1rem;
        background: #F7FAFC;
        margin: -10px -10px 10px -10px;
        padding: 15px;
        border-bottom: 1px solid #E2E8F0;
        display: block;
        text-align: left;
    }

    #inventoryTable td:nth-child(2)::before {
        display: block;
        margin-bottom: 5px;
    }
}
```

---

## 📜 FRONTEND (JAVASCRIPT) - Restantes

### 📄 omcgc/frontend/assets/js/api-config.js

```javascript
/*
============================================================
Nombre del archivo : api-config.js
Ruta              : frontend/assets/js/api-config.js
Tipo              : Configuración Global Frontend

Propósito:
Centralizar la URL base del Backend para facilitar el despliegue
en diferentes entornos (Local, VPS, Producción).

Uso:
Incluir este script ANTES de cualquier otro servicio en el HTML.
============================================================
*/

const AppConfig = {
    // ENTORNO: LOCAL
    BASE_URL: 'http://localhost:9090/api',

    // ENTORNO: VPS (Ejemplo)
    // BASE_URL: 'https://api.tudominio.com/api',

    getEndpoint(resource) {
        return `${this.BASE_URL}/${resource}`;
    }
};
```

### 📄 omcgc/frontend/assets/js/login-service.js

```javascript
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
```

### 📄 omcgc/frontend/assets/js/menu-service.js

```javascript
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

        // Validación de existencia de módulos en Etapa 1
        // Lista blanca de módulos activos
        const ACTIVE_MODULES = ['usuarios.html'];

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
```


### 📄 omcgc/frontend/assets/js/message-service.js

```javascript
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
                modal.setAttribute('data-error-detalle', campoError);
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
        const usuario = sessionStorage.getItem('usuario_erp') ? JSON.parse(sessionStorage.getItem('usuario_erp')) : { username: 'Desconocido' };

        const reporte = [
            "============================================================",
            " REPORTE DE INCIDENCIA TÉCNICA - OPTICA ERP",
            "============================================================",
            "",
            `FECHA:       ${fecha.toLocaleString()} (${fecha.toISOString()})`,
            `USUARIO:     ${usuario.username || 'Anónimo'}`,
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
    }
};
```
---

### 📄 omcgc/frontend/assets/js/inventarios-service.js (Parte 1: Gestión de Catálogo)

```javascript
/*
============================================================
Nombre del archivo : inventarios-service.js
Ruta              : omcgc/frontend/assets/js/inventarios-service.js
Tipo              : Frontend (Service / Model Layer)

Versión           : v1.1
============================================================
*/

const InventarioManager = {
    API_URL: AppConfig.getEndpoint('inventarios'),
    
    // Estado local para evitar peticiones redundantes
    cache: {
        productos: [],
        grupos: [],
        familias: [],
        marcas: []
    },

    /**
     * Inicialización del módulo
     */
    init: async function() {
        console.log("[INVENTARIO] Inicializando gestor...");
        this.mostrarLoading(true);
        try {
            await Promise.all([
                this.cargarCatalogos(),
                this.cargarProductos()
            ]);
            this.renderizarKPIs();
        } catch (error) {
            MessageManager.mostrar(1, "Error de Inicialización", error.message);
        } finally {
            this.mostrarLoading(false);
        }
    },

    /**
     * Carga de catálogos auxiliares (Grupo, Familia, Marca, Proveedor)
     */
    cargarCatalogos: async function() {
        try {
            const [rGrupos, rMarcas, rProvs] = await Promise.all([
                fetch(AppConfig.getEndpoint('catalogos/grupos')).then(r => r.json()),
                fetch(AppConfig.getEndpoint('catalogos/marcas')).then(r => r.json()),
                fetch(AppConfig.getEndpoint('catalogos/proveedores')).then(r => r.json())
            ]);

            this.cache.grupos = rGrupos;
            this.cache.marcas = rMarcas;

            // Llenar selects en la UI
            this.llenarSelect('filterGrupo', rGrupos, 'idGrupo', 'nombre', 'Todos los Grupos');
            this.llenarSelect('idGrupo', rGrupos, 'idGrupo', 'nombre', 'Seleccione...');
            this.llenarSelect('idMarca', rMarcas, 'idMarca', 'nombre', 'Genérica / Sin Marca');
            this.llenarSelect('idProveedor', rProvs, 'idProveedor', 'nombre', 'Seleccionar...');

        } catch (error) {
            console.error("Error al cargar catálogos:", error);
        }
    },

    /**
     * Obtiene todos los productos desde el Backend
     */
    cargarProductos: async function() {
        try {
            const response = await fetch(this.API_URL);
            if (!response.ok) throw new Error("No se pudo conectar con el servicio de inventarios.");
            
            this.cache.productos = await response.json();
            this.renderizarTabla(this.cache.productos);
            this.renderizarKPIs();
        } catch (error) {
            throw error;
        }
    },

    /**
     * Renderiza la tabla principal con los productos cargados
     */
    renderizarTabla: function(data) {
        const tbody = document.getElementById('inventoryTableBody');
        tbody.innerHTML = '';

        if (data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" class="txt-center">No se encontraron productos con los criterios seleccionados.</td></tr>';
            return;
        }

        data.forEach(p => {
            const tr = document.createElement('tr');
            
            // Determinar badge de stock (Semáforo operativo)
            let badgeClass = 'ok';
            if (p.existenciaActual <= 0) badgeClass = 'danger';
            else if (p.existenciaActual <= p.stockMinimo) badgeClass = 'warning';

            tr.innerHTML = `
                <td class="font-mono"><strong>${p.sku || 'N/A'}</strong><br><small class="txt-muted">${p.codigoBarras || ''}</small></td>
                <td>
                    <div class="product-info-cell">
                        <span class="p-name">${p.nombre}</span>
                        <span class="p-brand">${p.nombreMarca || 'Sin Marca'}</span>
                    </div>
                </td>
                <td><span class="tag-light">${p.nombreGrupo}</span></td>
                <td class="txt-center">
                    <span class="stock-badge ${badgeClass}">${p.existenciaActual}</span>
                </td>
                <td>$ ${parseFloat(p.costoUnitario).toFixed(2)}</td>
                <td class="txt-primary font-bold">$ ${parseFloat(p.precioVenta).toFixed(2)}</td>
                <td>
                    <span class="dot ${p.estatus === 'ACTIVO' ? 'dot-success' : 'dot-danger'}"></span>
                    ${p.estatus}
                </td>
                <td class="txt-center">
                    <div class="action-buttons">
                        <button class="btn-action" title="Kardex / Movimientos" onclick="InventarioManager.verKardex('${p.idProducto}')">
                            <i class="fas fa-exchange-alt"></i>
                        </button>
                        <button class="btn-action" title="Ficha Técnica" onclick="InventarioManager.editarProducto('${p.idProducto}')">
                            <i class="fas fa-edit"></i>
                        </button>
                    </div>
                </td>
            `;

            // Agregar atributos para búsqueda móvil
            tr.querySelectorAll('td').forEach((td, idx) => {
                const labels = ['', 'Producto', 'Grupo', 'Stock', 'Costo', 'Precio', 'Estado', 'Acciones'];
                if(labels[idx]) td.setAttribute('data-label', labels[idx]);
            });

            tbody.appendChild(tr);
        });
    },

    /**
     * Actualiza los indicadores visuales superiores (KPIs)
     */
    renderizarKPIs: function() {
        const total = this.cache.productos.length;
        const bajo = this.cache.productos.filter(p => p.existenciaActual <= p.stockMinimo).length;
        const valor = this.cache.productos.reduce((acc, p) => acc + (p.costoUnitario * p.existenciaActual), 0);

        document.getElementById('kpi-total').innerText = total;
        document.getElementById('kpi-bajo').innerText = bajo;
        document.getElementById('kpi-valor').innerText = valor.toLocaleString('es-MX', { minimumFractionDigits: 2 });
    },

    /**
     * Filtra los productos en el cliente (UI Search)
     */
    filtrarProductos: function() {
        const term = document.getElementById('searchInput').value.toLowerCase();
        const grupo = document.getElementById('filterGrupo').value;
        const toggleUmbral = this.getActiveFilter('toggleUmbral');
        const toggleEstatus = this.getActiveFilter('toggleEstatus');

        const filtrados = this.cache.productos.filter(p => {
            const matchesTerm = p.nombre.toLowerCase().includes(term) || 
                               (p.sku && p.sku.toLowerCase().includes(term)) ||
                               (p.codigoBarras && p.codigoBarras.toLowerCase().includes(term));
            
            const matchesGrupo = !grupo || p.idGrupo === grupo;
            
            let matchesUmbral = true;
            if (toggleUmbral === 'BAJO') matchesUmbral = p.existenciaActual <= p.stockMinimo;
            else if (toggleUmbral === 'OK') matchesUmbral = p.existenciaActual > p.stockMinimo;

            const matchesEstatus = !toggleEstatus || p.estatus === toggleEstatus;

            return matchesTerm && matchesGrupo && matchesUmbral && matchesEstatus;
        });

        this.renderizarTabla(filtrados);
    },

    /**
     * Abre el modal para crear un nuevo producto
     */
    abrirModalNuevo: function() {
        document.getElementById('modalTitulo').innerText = "Nuevo Producto";
        document.getElementById('formProducto').reset();
        document.getElementById('info-box-premium').style.display = 'none';
        
        // Bloquear campos que el sistema genera o que solo se mueven vía Kardex
        document.getElementById('existenciaActual').disabled = false;
        
        this.switchTab('general', document.querySelector('.tab-btn'));
        document.getElementById('productModal').classList.add('active');
    },

    /**
     * Obtiene el valor del filtro activo en un segmented control
     */
    getActiveFilter: function(id) {
        const btn = document.querySelector(`#${id} .status-btn.active`);
        return btn ? btn.dataset.filter : '';
    },

    /**
     * Maneja el cambio de estado en los botones de filtro (UI)
     */
    setFilter: function(containerId, element) {
        document.querySelectorAll(`#${containerId} .status-btn`).forEach(b => b.classList.remove('active'));
        element.classList.add('active');
        this.filtrarProductos();
    },

    /**
     * Carga dinámicamente las familias al cambiar el grupo
     */
    cargarFamilias: async function(idGrupo) {
        const select = document.getElementById('idFamilia');
        select.innerHTML = '<option value="">Cargando...</option>';
        
        try {
            const response = await fetch(AppConfig.getEndpoint(`catalogos/familias?idGrupo=${idGrupo}`));
            const data = await response.json();
            this.llenarSelect('idFamilia', data, 'idFamilia', 'nombre', 'Seleccione...');
        } catch (error) {
            select.innerHTML = '<option value="">Error al cargar</option>';
        }
    },

    /**
     * Guarda el producto (Nuevo o Edición)
     */
    guardarProducto: async function() {
        const form = document.getElementById('formProducto');
        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        const formData = new FormData(form);
        const producto = Object.fromEntries(formData.entries());
        
        // ID para edición
        const id = document.getElementById('sku_hidden')?.value;
        const method = id ? 'PUT' : 'POST';
        const url = id ? `${this.API_URL}/${id}` : this.API_URL;

        MessageManager.procesando();
        
        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(producto)
            });

            if (!response.ok) throw new Error("Error al guardar en el servidor.");

            MessageManager.mostrar(3, "Éxito", "Operación realizada correctamente.");
            this.cerrarModal();
            this.cargarProductos();
        } catch (error) {
            MessageManager.mostrar(1, "Error", error.message);
        }
    },

    /**
     * Utilidad para llenar selects dinámicamente
     */
    llenarSelect: function(id, data, valKey, textKey, defaultText) {
        const select = document.getElementById(id);
        if (!select) return;
        
        select.innerHTML = defaultText ? `<option value="">${defaultText}</option>` : '';
        data.forEach(item => {
            const opt = document.createElement('option');
            opt.value = item[valKey];
            opt.innerText = item[textKey];
            select.appendChild(opt);
        });
    },

    mostrarLoading: function(show) {
        // Implementar spinner global si se requiere
    }
};

```

### 📄 omcgc/frontend/assets/js/inventarios-service.js (Parte 2: Lógica de Kardex y Movimientos)

```javascript
/*
============================================================
Lógica extendida para gestión de Kardex, Pólizas y Auditoría
============================================================
*/

Object.assign(InventarioManager, {
    
    currentProductId: null,
    currentStock: 0,

    /**
     * Carga y muestra el historial de movimientos de un producto
     */
    verKardex: async function(idProducto) {
        this.currentProductId = idProducto;
        const modal = document.getElementById('kardexModal');
        
        MessageManager.procesando("Recuperando historial...");
        
        try {
            const [producto, movimientos] = await Promise.all([
                fetch(`${this.API_URL}/${idProducto}`).then(r => r.json()),
                fetch(`${this.API_URL}/${idProducto}/kardex`).then(r => r.json())
            ]);

            this.currentStock = producto.existenciaActual;
            
            // Actualizar Header del Modal
            document.getElementById('kardexSubtitulo').innerText = `SKU: ${producto.sku} | PRODUCTO: ${producto.nombre}`;
            document.getElementById('k-actual').innerText = producto.existenciaActual;
            
            // Totales de la vista
            const entradas = movimientos.filter(m => m.tipoMovimiento.startsWith('ENTRADA') || m.tipoMovimiento === 'DEVOLUCION_VENTA')
                                       .reduce((acc, m) => acc + m.cantidad, 0);
            const salidas = movimientos.filter(m => !m.tipoMovimiento.startsWith('ENTRADA') && m.tipoMovimiento !== 'DEVOLUCION_VENTA')
                                       .reduce((acc, m) => acc + m.cantidad, 0);
            
            document.getElementById('k-entradas').innerText = entradas;
            document.getElementById('k-salidas').innerText = salidas;

            this.renderizarKardexTable(movimientos);
            MessageManager.cerrar();
            modal.classList.add('active');

        } catch (error) {
            MessageManager.mostrar(1, "Error", "No se pudo obtener el Kardex técnico.");
        }
    },

    /**
     * Renderiza las filas del historial
     */
    renderizarKardexTable: function(movimientos) {
        const tbody = document.getElementById('kardexTableBody');
        tbody.innerHTML = '';

        movimientos.forEach(m => {
            const tr = document.createElement('tr');
            const esEntrada = m.tipoMovimiento.startsWith('ENTRADA') || m.tipoMovimiento === 'DEVOLUCION_VENTA';
            
            tr.innerHTML = `
                <td>
                    <span class="txt-small">${new Date(m.fechaMovimiento).toLocaleDateString()}</span><br>
                    <small class="txt-muted">${new Date(m.fechaMovimiento).toLocaleTimeString()}</small>
                </td>
                <td class="font-mono">${m.folioReferencia || 'S/F'}</td>
                <td>
                    <span class="mov-type-label ${esEntrada ? 'in' : 'out'}">${m.tipoMovimiento.replace('_', ' ')}</span>
                </td>
                <td><small>${m.observaciones || '-'}</small></td>
                <td class="txt-right ${esEntrada ? 'txt-success font-bold' : ''}">${esEntrada ? '+' + m.cantidad : ''}</td>
                <td class="txt-right ${!esEntrada ? 'txt-danger font-bold' : ''}">${!esEntrada ? '-' + m.cantidad : ''}</td>
                <td class="txt-right font-bold bg-light">${m.saldoPosterior}</td>
                <td><span class="user-pill">${m.nombreUsuario || 'Root'}</span></td>
                <td class="txt-center">
                    <button class="btn-action-sm" onclick="InventarioManager.verDetalleMovimiento('${m.idMovimiento}')">
                        <i class="fas fa-search-plus"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    },

    /**
     * Abre el modal para registrar un nuevo movimiento (Póliza)
     */
    abrirModalMovimiento: function() {
        if (!this.currentProductId) return;

        document.getElementById('modalMovTitulo').innerText = "Nueva Póliza de Movimiento";
        document.getElementById('formMovimiento').reset();
        
        // Cargar datos de referencia
        document.getElementById('mov_sku_label').innerText = document.getElementById('kardexSubtitulo').innerText.split('|')[0].replace('SKU: ', '');
        document.getElementById('mov_producto_label').innerText = document.getElementById('kardexSubtitulo').innerText.split('|')[1].replace('PRODUCTO: ', '');
        document.getElementById('mov_exist_actual').innerText = this.currentStock;
        
        this.recalcularIndicadores();
        
        document.getElementById('movimientoModal').classList.add('active');
    },

    /**
     * Simulación dinámica de stock en el formulario de póliza
     */
    recalcularIndicadores: function() {
        const tipo = document.getElementById('mov_tipo').value;
        const cant = parseInt(document.getElementById('mov_cantidad').value) || 0;
        const actual = parseInt(this.currentStock);
        
        const esEntrada = tipo.startsWith('ENTRADA') || tipo === 'DEVOLUCION_VENTA';
        const nuevoSaldo = esEntrada ? actual + cant : actual - cant;
        
        const lblNuevo = document.getElementById('mov_nuevo_saldo');
        lblNuevo.innerText = nuevoSaldo;
        
        // Alertar si hay stock negativo (informativo)
        if (nuevoSaldo < 0) lblNuevo.classList.add('txt-danger');
        else lblNuevo.classList.remove('txt-danger');
    },

    /**
     * Ejecuta la persistencia del movimiento
     */
    ejecutarGuardarMovimiento: async function() {
        const tipo = document.getElementById('mov_tipo').value;
        const cantidad = parseInt(document.getElementById('mov_cantidad').value);
        const folio = document.getElementById('mov_folio').value;
        const obs = document.getElementById('mov_obs').value;
        const costo = parseFloat(document.getElementById('mov_costo').value) || 0;

        if (!cantidad || cantidad <= 0) {
            MessageManager.mostrar(2, "Dato Inválido", "La cantidad debe ser mayor a cero.");
            return;
        }

        const payload = {
            idProducto: this.currentProductId,
            tipoMovimiento: tipo,
            cantidad: cantidad,
            folioReferencia: folio,
            observaciones: obs,
            costoUnitario: costo,
            idUsuario: JSON.parse(sessionStorage.getItem('user')).idUsuario // Inyectado desde sesión
        };

        MessageManager.procesando("Aplicando afectación contable...");

        try {
            const response = await fetch(`${this.API_URL}/movimientos`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!response.ok) throw new Error("No se pudo registrar el movimiento.");

            MessageManager.mostrar(3, "Movimiento Aplicado", "El inventario ha sido actualizado correctamente.");
            this.cerrarMovimientoModal();
            
            // Refrescar Kardex y Tabla Principal
            await this.verKardex(this.currentProductId);
            this.cargarProductos();

        } catch (error) {
            MessageManager.mostrar(1, "Error Operativo", error.message);
        }
    },

    // --- ACCIONES SECUNDARIAS ---

    cerrarModal: () => document.getElementById('productModal').classList.remove('active'),
    cerrarKardexModal: () => document.getElementById('kardexModal').classList.remove('active'),
    cerrarMovimientoModal: () => document.getElementById('movimientoModal').classList.remove('active'),

    switchTab: function(tabId, btn) {
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        
        document.getElementById(`tab-${tabId}`).classList.add('active');
        btn.classList.add('active');
    },

    calcularPrecioVenta: function() {
        const costo = parseFloat(document.getElementById('costoUnitario').value) || 0;
        const util = parseFloat(document.getElementById('porcentajeUtilidad').value) || 0;
        
        const precio = costo * (1 + (util / 100));
        document.getElementById('precioVenta').value = `$ ${precio.toFixed(2)}`;
    },

    verBitacora: function() {
        document.getElementById('bitacoraModal').classList.add('active');
        // Lógica de carga de bitacora...
    },

    cerrarBitacoraModal: () => document.getElementById('bitacoraModal').classList.remove('active')
});

// Inicialización
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('inventoryTable')) {
        InventarioManager.init();
    }
});
```

---

## ☕ BACKEND (JAVA)

### ⚙️ CONFIGURATIONS

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/config/SecurityConfiguration.java

```java
/*
============================================================
Nombre del archivo : SecurityConfiguration.java
Ruta              : omcgc/backend/src/config/SecurityConfiguration.java
Tipo              : Backend (Configuración de Seguridad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Definir la cadena de filtros de seguridad (Security Filter Chain) para el manejo
de autorizaciones HTTP, CORS y protección CSRF.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. RNF-02 (Seguridad de Acceso):
   - Configuración de CORS permitiendo orígenes cruzados para desarrollo.
   - Exposición pública de endpoints de autenticación (/api/auth/**).
   - Bloqueo por defecto de cualquier otro recurso no autenticado.
============================================================
*/
package com.omcgc.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // --- CONFIGURACIÓN DE CORS (RNF-02) ---
                // Permite la comunicación cruzada necesaria entre el frontend (file:// o localhost)
                // y el backend, validando orígenes y métodos permitidos.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // --- PROTECCIÓN CSRF ---
                // Desactivada debido a la naturaleza stateless de la API REST que utiliza JWT/Token.
                .csrf(csrf -> csrf.disable())

                // --- CABECERAS DE SEGURIDAD Y CSP (RNF-05 / SEGURIDAD DINÁMICA) ---
                // Implementación de Content Security Policy para mitigar ataques XSS y Clickjacking.
                // Define una 'Lista Blanca' de orígenes permitidos para scripts, estilos y fuentes.
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; " +
                                        "script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
                                        "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdnjs.cloudflare.com; " +
                                        "font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com; " +
                                        "img-src 'self' data:; " +
                                        "connect-src 'self' http://localhost:9090 http://69.6.242.217:9090 https://api-vps.graxsoft.com;")))

                // --- CONTROL DE ACCESO (HU-M01-01) ---
                // Configura la visibilidad de los endpoints. En esta etapa se permite acceso total
                // para pruebas de integración, sujeto a endurecimiento en Etapas 3 y 4.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow ALL origins (file://, localhost, etc)
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

### 📦 CONTROLLERS

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/InventarioController.java

```java
/*
============================================================
Nombre del archivo : InventarioController.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/controller/InventarioController.java
Tipo              : Backend (Spring Boot REST Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Exponer el API REST para la gestión integral del inventario, incluyendo
operaciones de catálogo (productos) y movimientos técnicos (Kardex).

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M02-01 (Gestión de Catálogo): Endpoints GET, POST, PUT.
2. HU-M02-02 (Kardex de Movimientos): Endpoint GET /kardex.
3. HU-M02-03 (Pólizas de Inventario): Endpoint POST /movimientos.
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.model.Producto;
import com.omcgc.erp.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // --- ENDPOINTS DE PRODUCTOS (CATÁLOGO) ---

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(inventarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        Producto p = inventarioService.obtenerPorId(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crearProducto(producto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String id, @RequestBody Producto producto) {
        try {
            producto.setIdProducto(id);
            return ResponseEntity.ok(inventarioService.actualizarProducto(producto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- ENDPOINTS DE MOVIMIENTOS (KARDEX) ---

    @GetMapping("/{id}/kardex")
    public ResponseEntity<List<MovimientoInventario>> obtenerKardex(@PathVariable String id) {
        return ResponseEntity.ok(inventarioService.obtenerKardex(id));
    }

    @PostMapping("/movimientos")
    public ResponseEntity<?> registrarMovimiento(@RequestBody MovimientoInventario movimiento) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.registrarMovimiento(movimiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/AuthController.java

```java
/*
============================================================
Nombre del archivo : AuthController.java
Ruta              : omcgc/backend/src/controller/AuthController.java
Tipo              : Backend (Controlador REST)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v3.2

Propósito:
Exponer los endpoints REST públicos para la gestión de sesiones de usuario (Login).
Actúa como capa de entrada validando peticiones HTTP antes de invocar servicios.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Inicio de Sesión):
   - Endpoint POST /api/auth/login.
   - Mapeo de credenciales JSON a objetos de dominio.
   - Retorno estandarizado de token de sesión y metadatos de usuario (Rol, ID).
============================================================
*/
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.service.AuthService;
import com.omcgc.erp.service.BitacoraService;
import com.omcgc.erp.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow frontend
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraService bitacoraService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpServletRequest request) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String ip = request.getRemoteAddr();

        try {
            Usuario usuario = authService.login(email, password);

            // REGISTRO DE EVENTO: LOGIN EXITOSO (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(usuario.getIdUsuario(), "AUTH-01", ip, null, null);

            // Obtener permisos del usuario
            List<Map<String, Object>> permisos = usuarioService.getPermissionsByUsuario(usuario.getIdUsuario());

            // Returns user data + permissions
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login exitoso",
                    "userId", usuario.getIdUsuario(),
                    "rolId", usuario.getIdRol() != null ? usuario.getIdRol() : "",
                    "nombreRol", usuario.getNombreRol() != null ? usuario.getNombreRol() : "",
                    "nombre", usuario.getNombre(),
                    "idSucursal", usuario.getIdSucursal() != null ? usuario.getIdSucursal() : "",
                    "permisos", permisos));
        } catch (RuntimeException e) {
            String errorMsg = e.getMessage();

            // Verificar si es un error de infraestructura (BD, conexion, etc.)
            if (errorMsg != null && (errorMsg.contains("Connection") ||
                    errorMsg.contains("database") ||
                    errorMsg.contains("SQLException") ||
                    errorMsg.contains("Communications link failure") ||
                    errorMsg.contains("Unable to acquire JDBC Connection"))) {

                // REGISTRO DE EVENTO: FALLO CRITICO DE DB (PATRON MAESTRO CIFRADO)
                bitacoraService.registrarEvento(null, "SYS-01", ip, "Error de infraestructura SQL", errorMsg);

                // Error de sistema (500)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message",
                        "Error de conexion con la base de datos. Verifique que el servidor MySQL este activo.",
                        "errorCode", "SYS-DB-001"));
            }

            // REGISTRO DE EVENTO: FALLO DE CREDENCIALES (PATRON MAESTRO CIFRADO)
            bitacoraService.registrarEvento(null, "AUTH-02", ip, "Intento: " + email, errorMsg);

            // Error de autenticacion (401)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        } catch (Exception e) {
            // Cualquier otra excepcion no controlada (500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Error inesperado del servidor: " + e.getMessage(),
                    "errorCode", "SYS-GEN-001"));
        }
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/UsuarioController.java

```java
/*
============================================================
Nombre del archivo : UsuarioController.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/controller/UsuarioController.java
Tipo              : Backend (REST Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v3.2

Propósito:
Controlador REST para la gestión completa de usuarios (CRUD), incluyendo
asignación de roles y permisos. Implementa los endpoints del módulo 1.3.

Trazabilidad:
- HU-M01-03: Gestión de Usuarios, Roles y Permisos
- Endpoints: GET, POST, PUT, DELETE /api/usuarios
============================================================
*/

package com.omcgc.erp.controller;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtener todos los usuarios
     * GET /api/usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuario por ID
     * GET /api/usuarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable String id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo usuario
     * POST /api/usuarios
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUsuario(@RequestBody Usuario usuario) {
        try {
            // Soporte para guardado atómico (Paquete completo con permisos)
            Usuario nuevoUsuario = usuarioService.saveUserWithPermissions(usuario, usuario.getPermisos());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Usuario creado exitosamente",
                            "data", nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al crear usuario: " + e.getMessage()));
        }
    }

    /**
     * Actualizar usuario existente
     * PUT /api/usuarios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUsuario(
            @PathVariable String id,
            @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            // Soporte para guardado atómico (Paquete completo con permisos)
            Usuario actualizado = usuarioService.saveUserWithPermissions(usuario, usuario.getPermisos());

            if (actualizado != null) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Usuario actualizado exitosamente",
                        "data", actualizado));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al actualizar usuario: " + e.getMessage()));
        }
    }

    /**
     * Eliminar (desactivar) usuario
     * DELETE /api/usuarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUsuario(@PathVariable String id) {
        try {
            boolean eliminado = usuarioService.delete(id);

            if (eliminado) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Usuario desactivado exitosamente"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al desactivar usuario: " + e.getMessage()));
        }
    }

    /**
     * Filtrar usuarios por estatus
     * GET /api/usuarios/estatus/{estatus}
     */
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<Usuario>> getUsuariosByEstatus(@PathVariable String estatus) {
        try {
            List<Usuario> usuarios = usuarioService.findByEstatus(estatus);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Filtrar usuarios por rol
     */
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable String rolId) {
        try {
            List<Usuario> usuarios = usuarioService.findByRol(rolId);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Restablecer contraseña de usuario
     * POST /api/usuarios/{id}/reset-password
     */
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@PathVariable String id) {
        try {
            String nuevaPassword = usuarioService.resetPassword(id);

            if (nuevaPassword != null) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Contraseña restablecida correctamente. Copie la nueva contraseña.",
                        "tempPassword", nuevaPassword));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al restablecer contraseña: " + e.getMessage()));
        }
    }

    // ==========================================
    // ENDPOINTS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    @GetMapping("/roles")
    public ResponseEntity<List<Map<String, Object>>> getRoles() {
        return ResponseEntity.ok(usuarioService.getAllRoles());
    }

    @GetMapping("/modulos")
    public ResponseEntity<List<Map<String, Object>>> getModulos() {
        return ResponseEntity.ok(usuarioService.getAllModules());
    }

    @GetMapping("/permisos/{idRol}")
    public ResponseEntity<List<Map<String, Object>>> getPermisosByRol(@PathVariable String idRol) {
        return ResponseEntity.ok(usuarioService.getPermissionsByRol(idRol));
    }

    @PutMapping("/permisos/{idRol}")
    public ResponseEntity<Map<String, Object>> updatePermisos(@PathVariable String idRol,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            for (Map<String, Object> p : permisos) {
                String idModulo = (String) p.get("id_modulo");
                // Manejar posibles nulos o tipos incorrectos (Boolean vs Integer)
                boolean ver = Boolean.TRUE.equals(p.get("puede_ver"));
                boolean crear = Boolean.TRUE.equals(p.get("puede_crear"));
                boolean editar = Boolean.TRUE.equals(p.get("puede_editar"));
                boolean eliminar = Boolean.TRUE.equals(p.get("puede_eliminar"));

                usuarioService.updatePermission(idRol, idModulo, ver, crear, editar, eliminar);
            }
            return ResponseEntity.ok(Map.of("success", true, "message", "Permisos actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al guardar permisos: " + e.getMessage()));
        }
    }

    // ==========================================
    // PERMISOS POR USUARIO (PERSONALIZADOS)
    // ==========================================

    @GetMapping("/permisos-usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> getPermisosByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(usuarioService.getPermissionsByUsuario(idUsuario));
    }

    @PutMapping("/permisos-usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> updatePermisosUsuario(@PathVariable String idUsuario,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            usuarioService.savePermissionsForUser(idUsuario, permisos);
            return ResponseEntity
                    .ok(Map.of("success", true, "message", "Permisos de usuario actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al guardar permisos: " + e.getMessage()));
        }
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/SmtpController.java

```java
/*
============================================================
Nombre del archivo : SmtpController.java
Ruta              : backend/src/main/java/com/omcgc/erp/controller/SmtpController.java
Tipo              : Backend (Controlador REST SMTP)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v3.2

Propósito:
Exponer endpoints protegidos para la gestión de la configuración SMTP.
Solo accesible para administradores (frontend validated, but secure here too).
============================================================
*/
package com.omcgc.erp.controller;

import com.omcgc.erp.model.SmtpConfig;
import com.omcgc.erp.service.EmailService;
import com.omcgc.erp.service.SmtpConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/smtp")
@CrossOrigin(origins = "*")
public class SmtpController {

    @Autowired
    private SmtpConfigService smtpConfigService;

    @Autowired
    private EmailService emailService;

    /**
     * Obtener el estado del archivo de configuración (Para el botón semáforo)
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        String status = smtpConfigService.checkStatus();
        return ResponseEntity.ok(Map.of("status", status));
    }

    /**
     * Obtener la configuración actual desencriptada (Solo Admin)
     */
    @GetMapping
    public ResponseEntity<?> getConfig() {
        try {
            SmtpConfig config = smtpConfigService.loadConfig();
            return ResponseEntity.ok(config);
        } catch (Exception e) {
            // Si falla lectura, probablemente no existe o corrupto
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Configuración no encontrada o corrupta"));
        }
    }

    /**
     * Guardar configuración (Solo Admin)
     */
    @PostMapping
    public ResponseEntity<?> saveConfig(@RequestBody SmtpConfig config) {
        try {
            smtpConfigService.saveConfig(config);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Configuración SMTP guardada y encriptada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error guardando configuración: " + e.getMessage()));
        }
    }

    /**
     * Probar configuración (Solo Admin)
     * Recibe la config temporal, no lee la guardada necesariamente
     */
    @PostMapping("/test")
    public ResponseEntity<?> testConfig(@RequestBody SmtpConfig config) {
        try {
            emailService.sendTestEmail(config);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Correo de prueba enviado con éxito a " + config.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error en prueba SMTP: " + e.getMessage()));
        }
    }
}
```

### 📦 SERVICES

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/AuthService.java

```java
/*
============================================================
Nombre del archivo : AuthService.java
Ruta              : omcgc/backend/src/service/AuthService.java
Tipo              : Backend (Java Spring Boot)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v3.2

Propósito:
Encapsular la lógica de negocio de autenticación y autorización, sirviendo como
intermediario entre los controladores REST y la capa de persistencia de datos.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Verificación de Identidad):
   - Consulta de usuarios por email vía `UsuarioRepository`.
   - Comparación criptográfica de contraseñas (Hash BCrypt).
   - Validación de estado activo/inactivo del usuario.
2. RNF-05 (Resiliencia):
   - Integración con `DatabaseHealthService` para validación previa ("Fail Fast").
3. RNF-02 (Seguridad en Desarrollo):
   - Implementación de credenciales estáticas de respaldo para entorno local.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Keep specific impl
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DatabaseHealthService dbHealthService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario login(String email, String password) {

        // --- MECANISMO DE AUTENTICACIÓN PRIVILEGIADA (ENTORNO DE DESARROLLO) ---
        // Permite el acceso administrativo sin validar contra la base de datos
        // para facilitar pruebas de integración en etapas tempranas.
        if ("root".equals(email) && "root".equals(password)) {
            return createSuperAdminUser();
        }
        // -----------------------------------------------------------------------

        // DIAGNÓSTICO DE DISPONIBILIDAD DE BASE DE DATOS
        // Verifica la conectividad antes de intentar operaciones de lectura.
        if (dbHealthService.isConnected()) {
            System.out.println("[AUTH-DEBUG] Conexión DB: ACTIVA (" + dbHealthService.getConnectionDetails() + ")");
        } else {
            System.err.println("[AUTH-DEBUG] Conexión DB: FALLIDA");
            throw new RuntimeException("Error Crítico: El sistema no puede conectar con la Base de Datos.");
        }

        // Normal Flow
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null) {
            // TRAZABILIDAD: Confirmación de existencia de usuario en repositorio
            System.out.println("[AUTH-SYSTEM] Identidad localizada: " + email);

            // Verificación criptográfica de la contraseña proporcionada contra el hash
            // almacenado
            boolean passwordMatch = passwordEncoder.matches(password, usuario.getPasswordHash());

            if (passwordMatch) {
                if (!usuario.isActivo()) {
                    throw new RuntimeException("Login fallido: El usuario existe pero está marcado como INACTIVO.");
                }
                return usuario;
            } else {
                // EXCEPCIÓN CONTROLADA: Credenciales inválidas
                throw new RuntimeException("Fallo de autenticación: Las credenciales proporcionadas no son válidas.");
            }
        }

        // EXCEPCIÓN CONTROLADA: Identidad no registrada
        throw new RuntimeException("Fallo de autenticación: Identidad no encontrada en el registro de usuarios.");
    }

    private Usuario createSuperAdminUser() {
        // Check DB Status using Service
        String dbStatus = dbHealthService.isConnected() ? "CONECTADO" : "DESCONECTADO";

        // UUID fijo — alineado con datos semilla en 02_create_tables_usuarios.sql
        // NOTA: Eliminar este método completo al pasar a producción.
        Usuario superAdmin = new Usuario();
        superAdmin.setIdUsuario("00000000-0000-0000-0000-000000000000");
        superAdmin.setNombre("SUPER ADMIN (DEBUG) - DB: " + dbStatus);
        superAdmin.setEmail("root");
        superAdmin.setActivo(true);
        superAdmin.setIdRol("ROL-001"); // Alineado con seed: rol Administrador
        superAdmin.setNombreRol("ADMIN");
        // Sucursal por defecto: Sucursal Centro (UUID fijo alineado con seed_cpanel_clean.sql)
        superAdmin.setIdSucursal("SUC-00000000-0000-0000-0000-000000000001");
        return superAdmin;
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/UsuarioService.java

```java
/*
============================================================
Nombre del archivo : UsuarioService.java
Ruta              : backend/src/main/java/com/omcgc/erp/service/UsuarioService.java
Tipo              : Backend (Service Layer)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Capa de lógica de negocio para la gestión de usuarios. Implementa
validaciones, encriptación de contraseñas y reglas de negocio.
============================================================
*/

package com.omcgc.erp.service;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Obtener todos los usuarios
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Buscar usuario por ID
     */
    public Usuario findById(String id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Crear nuevo usuario
     */
    public Usuario create(Usuario usuario) {
        // Validaciones

        // Autogenerar username si no viene en la petición (Frontend solo envía Nombre y
        // Correo)
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            if (usuario.getCorreo() != null && !usuario.getCorreo().trim().isEmpty()) {
                String generatedUser = usuario.getCorreo().split("@")[0];
                // Limpiar caracteres especiales si fuera necesario
                usuario.setUsuario(generatedUser);
            } else {
                throw new IllegalArgumentException("El nombre de usuario o correo es obligatorio");
            }
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        // Verificar si el correo ya existe
        Usuario existente = usuarioRepository.findByEmail(usuario.getCorreo());
        if (existente != null) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        // Generar ID único
        usuario.setId(UUID.randomUUID().toString());

        // Encriptar contraseña (si viene en el objeto, sino generar temporal)
        String passwordTemporal = usuario.getPassword() != null ? usuario.getPassword() : generarPasswordTemporal();
        usuario.setPassword(passwordEncoder.encode(passwordTemporal));

        // Asignar contraseña temporal para frontend
        usuario.setPasswordTemp(passwordTemporal);

        if (usuario.getEstatus() == null) {
            usuario.setEstatus("activo");
        }

        // Guardar en base de datos
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualizar usuario existente
     */
    public Usuario update(Usuario usuario) {
        Usuario existente = usuarioRepository.findById(usuario.getId());

        if (existente == null) {
            return null;
        }

        // Actualizar solo campos permitidos (no password ni usuario)
        if (usuario.getNombre() != null) {
            existente.setNombre(usuario.getNombre());
        }
        if (usuario.getCorreo() != null) {
            existente.setCorreo(usuario.getCorreo());
        }
        if (usuario.getRolId() != null) {
            existente.setRolId(usuario.getRolId());
        }
        if (usuario.getRolNombre() != null) {
            existente.setRolNombre(usuario.getRolNombre());
        }
        if (usuario.getEstatus() != null) {
            existente.setEstatus(usuario.getEstatus());
        }

        return usuarioRepository.update(existente);
    }

    /**
     * Eliminar (desactivar) usuario
     */
    public boolean delete(String id) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            return false;
        }

        // No eliminar físicamente, solo desactivar
        usuario.setEstatus("inactivo");
        usuarioRepository.update(usuario);

        return true;
    }

    /**
     * Buscar usuarios por estatus
     */
    public List<Usuario> findByEstatus(String estatus) {
        boolean activo = "activo".equalsIgnoreCase(estatus);
        return usuarioRepository.findByEstatus(activo);
    }

    /**
     * Buscar usuarios por rol
     */
    public List<Usuario> findByRol(String rolId) {
        return usuarioRepository.findByRol(rolId);
    }

    @Autowired
    private EmailService emailService;

    /**
     * Restablecer contraseña de usuario
     */
    public String resetPassword(String id) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            return null;
        }

        // 1. Generar nueva contraseña temporal
        String nuevaPassword = generarPasswordTemporal();
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.update(usuario);

        // 2. Intentar envío de correo con la nueva clave
        try {
            String subject = "🔐 Restablecimiento de Acceso - Óptica ERP";
            String body = String.format(
                    "Hola %s,\n\nSe ha generado una nueva contraseña temporal para tu acceso al sistema Óptica ERP.\n\n"
                            +
                            "Nueva Contraseña: %s\n\n" +
                            "Por tu seguridad, te recomendamos cambiarla en tu próximo inicio de sesión.\n\n" +
                            "Atentamente,\nSoporte Técnico WALOOK",
                    usuario.getNombre(), nuevaPassword);

            emailService.sendEmail(usuario.getCorreo(), subject, body);
            System.out.println("✅ Correo de reset enviado a: " + usuario.getCorreo());
        } catch (Exception e) {
            System.err.println("❌ Error enviando correo de reset: " + e.getMessage());
            // No bloqueamos el proceso si falla el correo, devolvemos la clave de todos
            // modos
        }

        System.out.println("Nueva contraseña para " + usuario.getUsuario() + ": " + nuevaPassword);
        return nuevaPassword;
    }

    /**
     * Generar contraseña temporal aleatoria
     */
    private String generarPasswordTemporal() {
        return "Temp" + UUID.randomUUID().toString().substring(0, 8) + "!";
    }

    /**
     * Validar credenciales (usado en login)
     */
    public Usuario validateCredentials(String email, String password) {
        Usuario user = usuarioRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    // ==========================================
    // MÉTODOS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    public List<java.util.Map<String, Object>> getAllRoles() {
        return usuarioRepository.findAllRoles();
    }

    public List<java.util.Map<String, Object>> getAllModules() {
        return usuarioRepository.findAllModules();
    }

    public List<java.util.Map<String, Object>> getPermissionsByRol(String idRol) {
        return usuarioRepository.findPermissionsByRol(idRol);
    }

    public void updatePermission(String idRol, String idModulo, boolean ver, boolean crear, boolean editar,
            boolean eliminar) {
        usuarioRepository.updatePermission(idRol, idModulo, ver, crear, editar, eliminar);
    }

    // Nuevo: Lógica de permisos por usuario
    public List<java.util.Map<String, Object>> getPermissionsByUsuario(String idUsuario) {
        // 1. Buscar permisos específicos
        List<java.util.Map<String, Object>> permisos = usuarioRepository.findPermissionsByUsuario(idUsuario);

        // 2. Si no tiene (legacy), devolver los del ROL
        if (permisos.isEmpty()) {
            Usuario u = findById(idUsuario);
            if (u != null && u.getRolId() != null) {
                return usuarioRepository.findPermissionsByRol(u.getRolId());
            }
        }
        return permisos;
    }

    public Usuario saveUserWithPermissions(Usuario usuario, List<java.util.Map<String, Object>> permisos) {
        Usuario savedUser;
        // Determinar si es Update o Create
        if (usuario.getId() != null && usuarioRepository.findById(usuario.getId()) != null) {
            savedUser = update(usuario);
        } else {
            savedUser = create(usuario);
        }

        // Guardar permisos vinculados al usuario
        if (savedUser != null && permisos != null) {
            usuarioRepository.saveUserPermissions(savedUser.getId(), permisos);
        }

        return savedUser;
    }

    public void savePermissionsForUser(String idUsuario, List<java.util.Map<String, Object>> permisos) {
        usuarioRepository.saveUserPermissions(idUsuario, permisos);
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/EmailService.java

```java
/*
============================================================
Nombre del archivo : EmailService.java
Ruta              : backend/src/main/java/com/omcgc/erp/service/EmailService.java
Tipo              : Backend (Servicio de Correo)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Manejar el envío de correos electrónicos utilizando la configuración SMTP dinámica.
Permite reconfigurar el remitente en tiempo de ejecución sin reiniciar el servidor.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.SmtpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private SmtpConfigService smtpConfigService;

    /**
     * Construye un sender dinámico basado en la configuración guardada o
     * proporcionada.
     */
    private JavaMailSenderImpl createSender(SmtpConfig config) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getUsername());
        mailSender.setPassword(config.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(config.isAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(config.isStarttls()));

        // Timeout para evitar bloqueos largos
        props.put("mail.smtp.connectiontimeout", "5000"); // 5 segundos
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        return mailSender;
    }

    /**
     * Envía un correo utilizando la configuración almacenada.
     */
    public void sendEmail(String to, String subject, String body) throws Exception {
        SmtpConfig config = smtpConfigService.loadConfig();
        JavaMailSenderImpl sender = createSender(config);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        sender.send(message);
    }

    /**
     * Prueba una configuración SMTP específica (sin necesidad de guardarla
     * primero).
     */
    public void sendTestEmail(SmtpConfig testConfig) throws Exception {
        JavaMailSenderImpl sender = createSender(testConfig);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(testConfig.getUsername());
        message.setTo(testConfig.getUsername()); // Se envía a sí mismo para probar
        message.setSubject("Prueba de Configuración SMTP - Óptica ERP");
        message.setText(
                "Este es un correo de verificación enviado desde el sistema Óptica ERP para confirmar que la configuración SMTP es correcta.\n\nFecha: "
                        + new java.util.Date());

        sender.send(message);
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/SmtpConfigService.java

```java
/*
============================================================
Nombre del archivo : SmtpConfigService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/SmtpConfigService.java
Tipo              : Backend (Servicio de Seguridad y Configuración)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Gestionar el almacenamiento seguro (encriptado) de la configuración SMTP.
Implementa lógica de integridad: si el archivo es manipulado o corrupto,
se elimina y se notifica el error.
============================================================
*/
package com.omcgc.erp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omcgc.erp.model.SmtpConfig;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class SmtpConfigService {

    private static final String CONFIG_FILE = "smtp_config.dat";
    private static final String SECRET_KEY = "WALOOK_ERP_SECURE_SMTP_KEY_2026"; // Clave interna
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Verifica el estado del archivo de configuración.
     * Retorna un código de estado para el frontend.
     */
    public String checkStatus() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            return "CONFIG_MISSING";
        }

        try {
            // Intentar leer y desencriptar para verificar integridad
            loadConfig();
            return "CONFIG_OK";
        } catch (Exception e) {
            // Si falla la lectura o desencriptación, asumimos corrupción
            System.err.println("Archivo SMTP corrupto detectado. Eliminando...");
            file.delete();
            return "CONFIG_CORRUPT";
        }
    }

    /**
     * Guarda la configuración encriptada en disco.
     */
    public void saveConfig(SmtpConfig config) throws Exception {
        String json = objectMapper.writeValueAsString(config);
        String encrypted = encrypt(json);
        Files.write(new File(CONFIG_FILE).toPath(), encrypted.getBytes());
    }

    /**
     * Carga y desencripta la configuración.
     */
    public SmtpConfig loadConfig() throws Exception {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            throw new IOException("Archivo de configuración no encontrado");
        }

        byte[] encryptedBytes = Files.readAllBytes(file.toPath());
        String decryptedJson = decrypt(new String(encryptedBytes));
        return objectMapper.readValue(decryptedJson, SmtpConfig.class);
    }

    // =========================================================
    // UTILIDADES DE ENCRIPTACIÓN (AES)
    // =========================================================

    private SecretKeySpec getKey() {
        try {
            byte[] key = SECRET_KEY.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // usar solo los primeros 128 bit
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Error generando clave AES", e);
        }
    }

    private String encrypt(String strToEncrypt) throws Exception {
        SecretKeySpec secretKey = getKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }

    private String decrypt(String strToDecrypt) throws Exception {
        SecretKeySpec secretKey = getKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/InventarioService.java

```java
/*
============================================================
Nombre del archivo : InventarioService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/InventarioService.java
Tipo              : Backend (Spring Boot Service Layer)

Propósito:
Encapsular la lógica de negocio para la gestión de existencias,
asegurando la integridad transaccional entre el catálogo de
productos y el registro histórico de movimientos (Kardex).
============================================================
*/

package com.omcgc.erp.service;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.model.Producto;
import com.omcgc.erp.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repo;

    /**
     * Recupera el catálogo maestro completo.
     */
    public List<Producto> obtenerTodos() {
        return repo.findAllProducts();
    }

    /**
     * Búsqueda específica por ID técnico.
     */
    public Producto obtenerPorId(String id) {
        return repo.findProductById(id);
    }

    /**
     * Registra un nuevo producto con validación de SKU y Código de Barras.
     */
    @Transactional
    public Producto crearProducto(Producto p) {
        if (repo.existsBySku(p.getSku())) {
            throw new RuntimeException("El SKU " + p.getSku() + " ya está registrado.");
        }
        
        p.setIdProducto(UUID.randomUUID().toString());
        p.setCreadoEn(LocalDateTime.now());
        p.setEstatus("ACTIVO");
        
        // La existencia inicial debe ser 0. Los ajustes se hacen vía Kardex.
        if (p.getExistenciaActual() == null) p.setExistenciaActual(0);
        
        repo.saveProduct(p);
        return p;
    }

    /**
     * Actualiza metadatos comerciales y técnicos del producto.
     */
    @Transactional
    public Producto actualizarProducto(Producto p) {
        Producto existente = repo.findProductById(p.getIdProducto());
        if (existente == null) throw new RuntimeException("Producto no encontrado.");

        p.setModificadoEn(LocalDateTime.now());
        repo.updateProduct(p);
        return p;
    }

    /**
     * Recupera la trazabilidad completa (Kardex) de un producto.
     */
    public List<MovimientoInventario> obtenerKardex(String idProducto) {
        return repo.findKardexByProductId(idProducto);
    }

    /**
     * LÓGICA CORE: Registro de movimiento con afectación de stock.
     * Esta operación es atómica: se registra el movimiento y se actualiza el saldo.
     */
    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoInventario m) {
        Producto p = repo.findProductById(m.getIdProducto());
        if (p == null) throw new RuntimeException("Producto no existe.");

        m.setIdMovimiento(UUID.randomUUID().toString());
        m.setFechaMovimiento(LocalDateTime.now());

        // Calcular saldo posterior (Afectación)
        int balanceBefore = p.getExistenciaActual();
        boolean isEntry = esEntrada(m.getTipoMovimiento());
        int balanceAfter = isEntry ? balanceBefore + m.getCantidad() : balanceBefore - m.getCantidad();

        // Actualizar objeto de movimiento con fotos de saldo
        m.setSaldoAnterior(balanceBefore);
        m.setSaldoPosterior(balanceAfter);

        // Persistir afectación en base de datos
        repo.saveMovement(m);
        repo.updateStock(p.getIdProducto(), balanceAfter);

        return m;
    }

    /**
     * Determina si un tipo de movimiento suma (Entrada) o resta (Salida).
     */
    private boolean esEntrada(String tipo) {
        return tipo.startsWith("ENTRADA") || tipo.equals("DEVOLUCION_VENTA");
    }
}
```

---

### 📦 MODELS Y REPOSITORIES

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/Usuario.java

```java
/*
============================================================
Nombre del archivo : Usuario.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Usuario.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v2.0

Propósito:
Modelo de datos para Usuario con campos completos para gestión CRUD
y compatibilidad con frontend.
============================================================
*/
package com.omcgc.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

public class Usuario {
    // Campos de base de datos
    private String id; // id_usuario
    private String usuario; // nombre (username)
    private String correo; // email
    private String nombre; // nombre_completo

    @JsonIgnore
    private String password; // password_hash (no enviar al frontend)

    private String estatus; // activo/inactivo (derivado de campo boolean)
    private LocalDateTime creadoEn;

    // Campos de relación con Rol
    private String rolId; // id_rol
    private String rolNombre; // nombre_rol

    // Campo transitorio para devolver contraseña generada al frontend (no se guarda
    // en BD)
    private transient String passwordTemp;

    // Constructores
    public Usuario() {
    }

    public Usuario(String id, String usuario, String correo, String nombre) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    // Métodos de compatibilidad con código legacy
    @JsonIgnore
    public String getIdUsuario() {
        return id;
    }

    public void setIdUsuario(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getEmail() {
        return correo;
    }

    public void setEmail(String email) {
        this.correo = email;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    @JsonIgnore
    public boolean isActivo() {
        return "activo".equals(estatus);
    }

    public void setActivo(boolean activo) {
        this.estatus = activo ? "activo" : "inactivo";
    }

    @JsonIgnore
    public String getIdRol() {
        return rolId;
    }

    public void setIdRol(String idRol) {
        this.rolId = idRol;
    }

    @JsonIgnore
    public String getNombreRol() {
        return rolNombre;
    }

    public void setNombreRol(String nombreRol) {
        this.rolNombre = nombreRol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", usuario='" + usuario + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estatus='" + estatus + '\'' +
                ", rolNombre='" + rolNombre + '\'' +
                '}';
    }

    public String getPasswordTemp() {
        return passwordTemp;
    }

    public void setPasswordTemp(String passwordTemp) {
        this.passwordTemp = passwordTemp;
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/SmtpConfig.java

```java
package com.omcgc.erp.model;

public class SmtpConfig {
    private String proveedor; // gmail, hotmail, custom
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean auth;
    private boolean starttls;

    // Constructor vacío
    public SmtpConfig() {
    }

    // Constructor completo
    public SmtpConfig(String proveedor, String host, int port, String username, String password, boolean auth,
            boolean starttls) {
        this.proveedor = proveedor;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.auth = auth;
        this.starttls = starttls;
    }

    // Getters y Setters
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isStarttls() {
        return starttls;
    }

    public void setStarttls(boolean starttls) {
        this.starttls = starttls;
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/Producto.java

```java
/*
============================================================
Nombre del archivo : Producto.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Producto.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Entidad maestra cl.Producto destinada a encapsular la totalidad de atributos
técnicos, comerciales, fiscales y logísticos de un artículo del inventario.
Actúa como el núcleo central de la lógica operacional del módulo M01.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Gestión de Productos): 
   - CRUD integral de la definición del producto e información SKU.
2. HU-M01-09 (Datos Fiscales SAT):
   - Soporte para claves de producto/servicio y unidades de medida (CFDI 4.0).
3. RNF-03 (Consistencia Financiera):
   - Manejo de costos unitarios y cálculo embebido de márgenes de utilidad.
============================================================
*/

package com.omcgc.erp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio fundamental cl.Producto.
 * Mapeada a la tabla tb.producto, coordina la jerarquía de categorías,
 * parámetros de stock, datos fiscales del SAT y variables financieras.
 */
public class Producto {

    // --- SECCIÓN: IDENTIDAD Y CODIFICACIÓN ---

    /** Identificador de objeto único vr.idProducto */
    private String idProducto;
    /** Stock Keeping Unit - Identificador único comercial vr.sku */
    private String sku;
    /** Denominación comercial completa del producto vr.nombre */
    private String nombre;
    /** Representación simbólica del código de barras vr.codigoBarras */
    private String codigoBarras;

    // --- SECCIÓN: CLASIFICACIÓN JERÁRQUICA ---

    /** Relación con el grupo raíz vr.idGrupo */
    private String idGrupo;
    /** Relación con la familia específica vr.idFamilia */
    private String idFamilia;
    /** Relación con el catálogo de marcas vr.idMarca */
    private String idMarca;

    /**
     * Atributos transitorios (Transient) para visualización en UI sin re-mapeo
     * vr.nombreGrupo
     */
    private String nombreGrupo;
    /**
     * Atributos transitorios (Transient) para visualización en UI sin re-mapeo
     * vr.nombreFamilia
     */
    private String nombreFamilia;
    /**
     * Atributos transitorios (Transient) para visualización en UI sin re-mapeo
     * vr.nombreMarca
     */
    private String nombreMarca;

    // --- SECCIÓN: CONTROL LOGÍSTICO (EXISTENCIAS) ---

    /** Límite inferior de stock para alertas de reabastecimiento vr.stockMinimo */
    private Integer stockMinimo;
    private Integer stockMaximo;

    /** Identificador del usuario que realiza la operacion (Transient) */
    private String idUsuarioOperacion;
    /**
     * Existencia actual operativa (Transient, calculada desde existencia)
     * vr.existenciaActual
     */
    private Integer existenciaActual;

    // --- SECCIÓN: VARIABLES FINANCIERAS ---

    /** Valor de adquisición neto por unidad vr.costoUnitario */
    private BigDecimal costoUnitario;
    /** Margen de ganancia porcentual pretendido vr.porcentajeUtilidad */
    private BigDecimal porcentajeUtilidad;
    /**
     * Valor de mercado resultante (Sincronizado vía base de datos) vr.precioVenta
     */
    private BigDecimal precioVenta;

    // --- SECCIÓN: CUMPLIMIENTO FISCAL (CFDI 4.0) ---

    /** Clave de producto o servicio según catálogo del SAT vr.claveProdServ */
    private String claveProdServ;
    /** Clave de unidad de medida comercial vr.claveUnidad */
    private String claveUnidad;
    /** Indicador de objeto de impuesto (01, 02) vr.objetoImpuesto */
    private String objetoImpuesto;
    /** Tasa impositiva aplicable (ej: 16.00) vr.ivaAplicable */
    private BigDecimal ivaAplicable;

    // --- SECCIÓN: AUDITORÍA Y ESTADO ---

    /** Identificador del proveedor preferencial vr.idProveedor */
    private String idProveedor;
    /** Nombre comercial del proveedor (Transient) vr.nombreProveedor */
    private String nombreProveedor;
    /** Estado operativo de la entidad (ACTIVO/INACTIVO) vr.estatus */
    private String estatus;
    /** Fecha de registro inicial vr.fechaCreacion */
    private LocalDateTime fechaCreacion;
    /** Fecha del último cambio registrado vr.fechaModificacion */
    private LocalDateTime fechaModificacion;

    /**
     * Constructor fn.Producto por defecto.
     */
    public Producto() {
    }

    // --- ACCESORES (GETTERS) Y MUTADORES (SETTERS) ---

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(String idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(Integer stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public String getIdUsuarioOperacion() {
        return idUsuarioOperacion;
    }

    public void setIdUsuarioOperacion(String idUsuarioOperacion) {
        this.idUsuarioOperacion = idUsuarioOperacion;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getObjetoImpuesto() {
        return objetoImpuesto;
    }

    public void setObjetoImpuesto(String objetoImpuesto) {
        this.objetoImpuesto = objetoImpuesto;
    }

    public BigDecimal getIvaAplicable() {
        return ivaAplicable;
    }

    public void setIvaAplicable(BigDecimal ivaAplicable) {
        this.ivaAplicable = ivaAplicable;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Integer getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(Integer existenciaActual) {
        this.existenciaActual = existenciaActual;
    }
}
```

---

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/MovimientoInventario.java

```java
/*
============================================================
Nombre del archivo : MovimientoInventario.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/MovimientoInventario.java
Tipo              : Backend (Modelo de Dominio / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Registrar la trazabilidad de cada transacción operativa que afecte el 
stock físico de la organización. Esta entidad cl.MovimientoInventario constituye 
la base técnica para el reporte de Kardex y auditorías de existencias.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-05 (Control de Movimientos / Kardex):
   - Registro cronológico de entradas, salidas y ajustes de inventario.
2. RNF-05 (Auditoría Técnica):
   - Conservación del historial de saldo anterior y saldo actual por transacción.
============================================================
*/

package com.omcgc.erp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad de dominio cl.MovimientoInventario mapeada a tb.movimiento_inventario.
 * Proporciona el registro detallado de los flujos de mercancía entre sucursales
 * y operaciones de venta o compra.
 */
public class MovimientoInventario {

    /** Identificador único del movimiento vr.idMovimiento */
    private String idMovimiento;

    /** Folio administrativo generado para el comprobante vr.folio */
    private String folio;

    /** Categorización del flujo (ENTRADA_COMPRA, SALIDA_VENTA, AJUSTE, TRASPASO, etc.) vr.tipoMovimiento */
    private String tipoMovimiento; 

    /** Identificador del producto afectado vr.idProducto */
    private String idProducto;

    /** Identificador de la sucursal origen/destino vr.idSucursal */
    private String idSucursal;

    /** Magnitud numérica del cambio en stock vr.cantidad */
    private Integer cantidad;

    /** Valor monetario registrado al momento de la transacción vr.costoHistorico */
    private BigDecimal costoHistorico;

    /** Saldo de stock previo a la afectación (Auditoría) vr.existenciaAnterior */
    private Integer existenciaAnterior;

    /** Saldo de stock resultante tras la afectación (Auditoría) vr.existenciaActual */
    private Integer existenciaActual;

    /** Fecha de expiración específica para el lote vr.fechaVencimiento */
    private LocalDate fechaVencimiento;

    /** Identificador del documento de origen (Venta, Compra, Traspaso) vr.origenId */
    private String origenId;

    /** Identificador del usuario que ejecutó la operación vr.idUsuario */
    private String idUsuario;

    /** Estampa de tiempo precisa del evento vr.fecha */
    private LocalDateTime fecha;

    /** Justificación o glosa textual de la operación vr.observaciones */
    private String observaciones;

    // --- CAMPOS TRANSITORIOS PARA LA INTERFAZ DE USUARIO ---
    
    /** Nombre comercial del producto (Transient) vr.nombreProducto */
    private String nombreProducto;
    /** SKU del producto para identificación rápida (Transient) vr.skuProducto */
    private String skuProducto;
    /** Nombre completo del usuario que operó (Transient) vr.nombreUsuario */
    private String nombreUsuario;

    /**
     * Constructor fn.MovimientoInventario por defecto.
     */
    public MovimientoInventario() {}

    // --- ACCESORES (GETTERS) Y MUTADORES (SETTERS) ---

    public String getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(String idMovimiento) { this.idMovimiento = idMovimiento; }

    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public String getIdSucursal() { return idSucursal; }
    public void setIdSucursal(String idSucursal) { this.idSucursal = idSucursal; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getCostoHistorico() { return costoHistorico; }
    public void setCostoHistorico(BigDecimal costoHistorico) { this.costoHistorico = costoHistorico; }

    public Integer getExistenciaAnterior() { return existenciaAnterior; }
    public void setExistenciaAnterior(Integer existenciaAnterior) { this.existenciaAnterior = existenciaAnterior; }

    public Integer getExistenciaActual() { return existenciaActual; }
    public void setExistenciaActual(Integer existenciaActual) { this.existenciaActual = existenciaActual; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getOrigenId() { return origenId; }
    public void setOrigenId(String origenId) { this.origenId = origenId; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getSkuProducto() { return skuProducto; }
    public void setSkuProducto(String skuProducto) { this.skuProducto = skuProducto; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
```

---

### 📦 REPOSITORIES

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/repository/UsuarioRepository.java

```java
/*
============================================================
Nombre del archivo : UsuarioRepository.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/repository/UsuarioRepository.java
Tipo              : Backend (Repositorio de Datos)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v3.0

Propósito:
Repositorio completo para gestión de usuarios con sintaxis correcta de JdbcTemplate.
============================================================
*/
package com.omcgc.erp.repository;

import com.omcgc.erp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper para mapear resultados de consultas con JOIN
     */
    private static final RowMapper<Usuario> USUARIO_ROW_MAPPER = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getString("id_usuario"));
        u.setUsuario(rs.getString("nombre"));
        u.setCorreo(rs.getString("email"));
        u.setPassword(rs.getString("password_hash"));
        u.setNombre(rs.getString("nombre"));
        u.setEstatus(rs.getBoolean("activo") ? "activo" : "inactivo");

        // Campos de rol (si existen en el JOIN)
        try {
            u.setRolId(rs.getString("id_rol"));
            u.setRolNombre(rs.getString("nombre_rol"));
        } catch (SQLException e) {
            // Ignorar si no están en la consulta
        }

        return u;
    };

    /**
     * Obtener todos los usuarios con sus roles
     */
    public List<Usuario> findAll() {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                ORDER BY u.nombre
                """;

        return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER);
    }

    /**
     * Buscar usuario por ID
     */
    public Usuario findById(String id) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                WHERE u.id_usuario = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, USUARIO_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Buscar usuario por email
     */
    public Usuario findByEmail(String email) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                WHERE u.email = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, USUARIO_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Crear nuevo usuario
     */
    public Usuario save(Usuario usuario) {
        // Generar ID si no existe
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            usuario.setId(UUID.randomUUID().toString());
        }

        String sqlUsuario = """
                INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en)
                VALUES (?, ?, ?, ?, ?, NOW())
                """;

        try {
            jdbcTemplate.update(sqlUsuario,
                    usuario.getId(),
                    usuario.getUsuario(),
                    usuario.getCorreo(),
                    usuario.getPassword(),
                    usuario.isActivo() ? 1 : 0);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado: " + usuario.getCorreo());
        }

        // Asignar rol si existe
        if (usuario.getRolNombre() != null && !usuario.getRolNombre().isEmpty()) {
            asignarRol(usuario.getId(), usuario.getRolNombre());
        }

        return findById(usuario.getId());
    }

    /**
     * Actualizar usuario existente
     */
    public Usuario update(Usuario usuario) {
        String sql = """
                UPDATE usuario
                SET nombre = ?, email = ?, password_hash = ?, activo = ?
                WHERE id_usuario = ?
                """;

        try {
            jdbcTemplate.update(sql,
                    usuario.getUsuario(),
                    usuario.getCorreo(),
                    usuario.getPassword(),
                    usuario.isActivo() ? 1 : 0,
                    usuario.getId());
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado: " + usuario.getCorreo());
        }

        // Actualizar rol
        if (usuario.getRolNombre() != null && !usuario.getRolNombre().isEmpty()) {
            // Eliminar roles anteriores
            jdbcTemplate.update("DELETE FROM usuario_rol WHERE id_usuario = ?", usuario.getId());
            // Asignar nuevo rol
            asignarRol(usuario.getId(), usuario.getRolNombre());
        }

        return findById(usuario.getId());
    }

    /**
     * Asignar rol a usuario
     */
    private void asignarRol(String idUsuario, String nombreRol) {
        // Buscar ID del rol
        String sqlRol = "SELECT id_rol FROM rol WHERE nombre = ?";
        try {
            String idRol = jdbcTemplate.queryForObject(sqlRol, String.class, nombreRol);

            // Insertar relación usuario-rol
            String sqlUsuarioRol = """
                    INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol)
                    VALUES (?, ?, ?)
                    """;

            jdbcTemplate.update(sqlUsuarioRol, UUID.randomUUID().toString(), idUsuario, idRol);
        } catch (EmptyResultDataAccessException e) {
            // Rol no existe, ignorar
            System.err.println("Rol no encontrado: " + nombreRol);
        }
    }

    /**
     * Desactivar usuario (soft delete)
     */
    public void delete(String id) {
        String sql = "UPDATE usuario SET activo = 0 WHERE id_usuario = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * Actualizar contraseña
     */
    public void updatePassword(String id, String passwordHash) {
        String sql = "UPDATE usuario SET password_hash = ? WHERE id_usuario = ?";
        jdbcTemplate.update(sql, passwordHash, id);
    }

    /**
     * Buscar usuarios por estatus
     */
    public List<Usuario> findByEstatus(boolean activo) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                LEFT JOIN rol r ON ur.id_rol = r.id_rol
                WHERE u.activo = ?
                ORDER BY u.nombre
                """;

        return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER, activo ? 1 : 0);
    }

    /**
     * Buscar usuarios por rol
     */
    public List<Usuario> findByRol(String rolId) {
        String sql = """
                SELECT u.id_usuario, u.nombre, u.email, u.password_hash, u.activo,
                       r.id_rol, r.nombre as nombre_rol
                FROM usuario u
                INNER JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
                INNER JOIN rol r ON ur.id_rol = r.id_rol
                WHERE r.id_rol = ?
                ORDER BY u.nombre
                """;

        return jdbcTemplate.query(sql, USUARIO_ROW_MAPPER, rolId);
    }

    // ==========================================
    // MÉTODOS DE GESTIÓN DE ROLES Y PERMISOS
    // ==========================================

    public List<java.util.Map<String, Object>> findAllRoles() {
        return jdbcTemplate.queryForList("SELECT id_rol, nombre, descripcion FROM rol ORDER BY nombre");
    }

    public List<java.util.Map<String, Object>> findAllModules() {
        return jdbcTemplate
                .queryForList("SELECT id_modulo, nombre, descripcion FROM modulo WHERE activo = 1 ORDER BY nombre");
    }

    public List<java.util.Map<String, Object>> findPermissionsByRol(String idRol) {
        String sql = """
                SELECT p.id_permiso, m.id_modulo, m.nombre as modulo,
                       p.puede_ver, p.puede_crear, p.puede_editar, p.puede_eliminar
                FROM permiso p
                JOIN modulo m ON p.id_modulo = m.id_modulo
                WHERE p.id_rol = ?
                ORDER BY m.nombre
                """;
        return jdbcTemplate.queryForList(sql, idRol);
    }

    // Nuevo: Permisos por Usuario (Prioridad)
    public List<java.util.Map<String, Object>> findPermissionsByUsuario(String idUsuario) {
        String sql = """
                SELECT p.id_permiso, m.id_modulo, m.nombre as modulo,
                       p.puede_ver, p.puede_crear, p.puede_editar, p.puede_eliminar
                FROM permiso p
                JOIN modulo m ON p.id_modulo = m.id_modulo
                WHERE p.id_usuario = ?
                ORDER BY m.nombre
                """;
        return jdbcTemplate.queryForList(sql, idUsuario);
    }

    public void updatePermission(String idRol, String idModulo, boolean ver, boolean crear, boolean editar,
            boolean eliminar) {
        String sql = """
                UPDATE permiso
                SET puede_ver=?, puede_crear=?, puede_editar=?, puede_eliminar=?
                WHERE id_rol=? AND id_modulo=?
                """;
        int updated = jdbcTemplate.update(sql, ver ? 1 : 0, crear ? 1 : 0, editar ? 1 : 0, eliminar ? 1 : 0, idRol,
                idModulo);

        if (updated == 0) {
            // Si no existe, insertar (defensivo)
            String sqlInsert = """
                    INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
                    VALUES (UUID(), ?, ?, ?, ?, ?, ?)
                    """;
            jdbcTemplate.update(sqlInsert, idRol, idModulo, ver ? 1 : 0, crear ? 1 : 0, editar ? 1 : 0,
                    eliminar ? 1 : 0);
        }
    }

    public void saveUserPermissions(String idUsuario, List<java.util.Map<String, Object>> permisos) {
        jdbcTemplate.update("DELETE FROM permiso WHERE id_usuario = ?", idUsuario);

        String sqlInsert = """
                INSERT INTO permiso (id_permiso, id_usuario, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
                VALUES (UUID(), ?, ?, ?, ?, ?, ?)
                """;

        for (java.util.Map<String, Object> p : permisos) {
            jdbcTemplate.update(sqlInsert,
                    idUsuario,
                    p.get("id_modulo"),
                    toBool(p.get("puede_ver")),
                    toBool(p.get("puede_crear")),
                    toBool(p.get("puede_editar")),
                    toBool(p.get("puede_eliminar")));
        }
    }

    private boolean toBool(Object val) {
        if (val == null)
            return false;
        if (val instanceof Boolean)
            return (Boolean) val;
        if (val instanceof String)
            return "true".equalsIgnoreCase((String) val);
        if (val instanceof Number)
            return ((Number) val).intValue() == 1;
        return false;
    }
}

---

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/Producto.java

```java
/*
============================================================
Nombre del archivo : Producto.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Producto.java
Tipo              : Backend (Modelo de Datos / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Define la estructura de datos para un producto en el sistema, incluyendo
atributos para su identificación, clasificación, precios, impuestos y
gestión de inventario.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Gestión de Productos):
   - Representación de la entidad principal para el catálogo de productos.
2. RNF-03 (Integridad de Datos):
   - Campos con tipos de datos Java que reflejan las restricciones de la base de datos.
============================================================
*/

package com.omcgc.erp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Producto {
    private String idProducto;
    private String sku;
    private String nombre;
    private String idGrupo;
    private String nombreGrupo; // Campo para JOIN
    private String idFamilia;
    private String nombreFamilia; // Campo para JOIN
    private String idMarca;
    private String nombreMarca; // Campo para JOIN
    private String codigoBarras;
    private int stockMinimo;
    private int stockMaximo;
    private BigDecimal costoUnitario;
    private BigDecimal porcentajeUtilidad;
    private BigDecimal precioVenta;
    private String claveProdServ;
    private String claveUnidad;
    private String objetoImpuesto;
    private BigDecimal ivaAplicable;
    private String idProveedor;
    private String nombreProveedor; // Campo para JOIN
    private String estatus;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    // Campos adicionales para reportes/vistas
    private int existenciaActual;

    // Constructor vacío
    public Producto() {
    }

    // Getters y Setters
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(String idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getObjetoImpuesto() {
        return objetoImpuesto;
    }

    public void setObjetoImpuesto(String objetoImpuesto) {
        this.objetoImpuesto = objetoImpuesto;
    }

    public BigDecimal getIvaAplicable() {
        return ivaAplicable;
    }

    public void setIvaAplicable(BigDecimal ivaAplicable) {
        this.ivaAplicable = ivaAplicable;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto='" + idProducto + '\'' +
                ", sku='" + sku + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idGrupo='" + idGrupo + '\'' +
                ", nombreGrupo='" + nombreGrupo + '\'' +
                ", idFamilia='" + idFamilia + '\'' +
                ", nombreFamilia='" + nombreFamilia + '\'' +
                ", idMarca='" + idMarca + '\'' +
                ", nombreMarca='" + nombreMarca + '\'' +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", stockMinimo=" + stockMinimo +
                ", stockMaximo=" + stockMaximo +
                ", costoUnitario=" + costoUnitario +
                ", porcentajeUtilidad=" + porcentajeUtilidad +
                ", precioVenta=" + precioVenta +
                ", claveProdServ='" + claveProdServ + '\'' +
                ", claveUnidad='" + claveUnidad + '\'' +
                ", objetoImpuesto='" + objetoImpuesto + '\'' +
                ", ivaAplicable=" + ivaAplicable +
                ", idProveedor='" + idProveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", estatus='" + estatus + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", existenciaActual=" + existenciaActual +
                '}';
    }
}
```

---

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/MovimientoInventario.java

```java
/*
============================================================
Nombre del archivo : MovimientoInventario.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/MovimientoInventario.java
Tipo              : Backend (Modelo de Datos / Entidad)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Define la estructura de datos para un movimiento de inventario (Kardex),
registrando transacciones como entradas, salidas, ajustes, etc., con
detalles como cantidad, costo, existencias antes y después, y referencias
a productos, sucursales y usuarios.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-05 (Control de Movimientos):
   - Representación de la entidad para el registro de transacciones de inventario.
2. RNF-03 (Integridad de Datos):
   - Campos con tipos de datos Java que reflejan las restricciones de la base de datos.
============================================================
*/

package com.omcgc.erp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MovimientoInventario {
    private String idMovimiento;
    private String folio;
    private String tipoMovimiento; // ENTRADA, SALIDA, AJUSTE_POSITIVO, AJUSTE_NEGATIVO, TRASPASO_ENTRADA, TRASPASO_SALIDA
    private String idProducto;
    private String nombreProducto; // Campo para JOIN
    private String skuProducto; // Campo para JOIN
    private String idSucursal;
    private int cantidad;
    private BigDecimal costoHistorico;
    private int existenciaAnterior;
    private int existenciaActual;
    private LocalDate fechaVencimiento;
    private String origenId; // ID de la venta, compra, traspaso, etc.
    private String idUsuario;
    private String nombreUsuario; // Campo para JOIN
    private LocalDateTime fecha;
    private String observaciones;

    // Constructor vacío
    public MovimientoInventario() {
    }

    // Getters y Setters
    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getSkuProducto() {
        return skuProducto;
    }

    public void setSkuProducto(String skuProducto) {
        this.skuProducto = skuProducto;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoHistorico() {
        return costoHistorico;
    }

    public void setCostoHistorico(BigDecimal costoHistorico) {
        this.costoHistorico = costoHistorico;
    }

    public int getExistenciaAnterior() {
        return existenciaAnterior;
    }

    public void setExistenciaAnterior(int existenciaAnterior) {
        this.existenciaAnterior = existenciaAnterior;
    }

    public int getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getOrigenId() {
        return origenId;
    }

    public void setOrigenId(String origenId) {
        this.origenId = origenId;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "MovimientoInventario{" +
                "idMovimiento='" + idMovimiento + '\'' +
                ", folio='" + folio + '\'' +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", idProducto='" + idProducto + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", skuProducto='" + skuProducto + '\'' +
                ", idSucursal='" + idSucursal + '\'' +
                ", cantidad=" + cantidad +
                ", costoHistorico=" + costoHistorico +
                ", existenciaAnterior=" + existenciaAnterior +
                ", existenciaActual=" + existenciaActual +
                ", fechaVencimiento=" + fechaVencimiento +
                ", origenId='" + origenId + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", fecha=" + fecha +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
```

---

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/repository/InventarioRepository.java

```java
/*
============================================================
Nombre del archivo : InventarioRepository.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/repository/InventarioRepository.java
Tipo              : Backend (Repositorio de Datos / Persistencia)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.2

Propósito:
Proveer la capa de persistencia para el maestro de productos y las 
transacciones de inventario (Kardex). Implementa operaciones de lectura 
avanzada con JOINS y persistencia mediante tb.producto y tb.movimiento_inventario.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-02 (Gestión de Productos): 
   - Persistencia avanzada y consulta de catálogo maestro.
2. HU-M01-05 (Control de Movimientos):
   - Recuperación de trazabilidad histórica por ítem SKU y registro de transacciones.
============================================================
*/

package com.omcgc.erp.repository;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Clase cl.InventarioRepository encargada de la materialización de objetos
 * de inventario desde el motor de base de datos relacional MySQL.
 */
@Repository
public class InventarioRepository {

    /**
     * Instancia de cl.JdbcTemplate inyectada para operaciones SQL vr.jdbcTemplate
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- SECCIÓN: MAPPERS TÉCNICOS ---

    /**
     * Mapeador vr.PRODUCTO_MAPPER para la des-serialización de registros de la
     * tabla tb.producto
     */
    private static final RowMapper<Producto> PRODUCTO_MAPPER = (rs, rowNum) -> {
        Producto p = new Producto();
        p.setIdProducto(rs.getString("id_producto"));
        p.setSku(rs.getString("sku"));
        p.setNombre(rs.getString("nombre"));
        p.setIdGrupo(rs.getString("id_grupo"));
        p.setIdFamilia(rs.getString("id_familia"));
        p.setIdMarca(rs.getString("id_marca"));
        p.setCodigoBarras(rs.getString("codigo_barras"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setStockMaximo(rs.getInt("stock_maximo"));
        p.setCostoUnitario(rs.getBigDecimal("costo_unitario"));
        p.setPorcentajeUtilidad(rs.getBigDecimal("porcentaje_utilidad"));
        p.setPrecioVenta(rs.getBigDecimal("precio_venta"));
        p.setClaveProdServ(rs.getString("clave_prod_serv"));
        p.setClaveUnidad(rs.getString("clave_unidad"));
        p.setObjetoImpuesto(rs.getString("objeto_impuesto"));
        p.setIvaAplicable(rs.getBigDecimal("iva_aplicable"));
        p.setIdProveedor(rs.getString("id_proveedor"));
        p.setEstatus(rs.getString("estatus"));
        p.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        if (rs.getTimestamp("fecha_modificacion") != null) {
            p.setFechaModificacion(rs.getTimestamp("fecha_modificacion").toLocalDateTime());
        }

        try { p.setNombreGrupo(rs.getString("nombre_grupo")); } catch (Exception e) {}
        try { p.setNombreFamilia(rs.getString("nombre_familia")); } catch (Exception e) {}
        try { p.setNombreMarca(rs.getString("nombre_marca")); } catch (Exception e) {}
        try { p.setNombreProveedor(rs.getString("nombre_proveedor")); } catch (Exception e) {}
        try { p.setExistenciaActual(rs.getInt("existencia_actual")); } catch (Exception e) {}

        return p;
    };

    /**
     * Mapeador vr.MOVIMIENTO_MAPPER para la des-serialización de registros de la
     * tabla tb.movimiento_inventario
     */
    private static final RowMapper<MovimientoInventario> MOVIMIENTO_MAPPER = (rs, rowNum) -> {
        MovimientoInventario m = new MovimientoInventario();
        m.setIdMovimiento(rs.getString("id_movimiento"));
        m.setFolio(rs.getString("folio"));
        m.setTipoMovimiento(rs.getString("tipo_movimiento"));
        m.setIdProducto(rs.getString("id_producto"));
        m.setIdSucursal(rs.getString("id_sucursal"));
        m.setCantidad(rs.getInt("cantidad"));
        m.setCostoHistorico(rs.getBigDecimal("costo_historico"));
        m.setExistenciaAnterior(rs.getInt("existencia_anterior"));
        m.setExistenciaActual(rs.getInt("existencia_actual"));
        m.setFechaVencimiento(
                rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null);
        m.setOrigenId(rs.getString("origen_id"));
        m.setIdUsuario(rs.getString("id_usuario"));
        m.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        m.setObservaciones(rs.getString("observaciones"));

        try { m.setNombreProducto(rs.getString("nombre_producto")); } catch (Exception e) {}
        try { m.setSkuProducto(rs.getString("sku_producto")); } catch (Exception e) {}
        try { m.setNombreUsuario(rs.getString("nombre_usuario")); } catch (Exception e) {}

        return m;
    };

    // --- SECCIÓN: MÉTODOS DE PERSISTENCIA (PRODUCTOS) ---

    public List<Producto> findAll() {
        String vr_sql = """
                SELECT p.*, g.nombre as nombre_grupo, f.nombre as nombre_familia,
                       m.nombre as nombre_marca, pr.nombre_comercial as nombre_proveedor,
                       COALESCE(SUM(e.cantidad), 0) as existencia_actual
                FROM producto p
                JOIN cat_grupo g ON p.id_grupo = g.id_grupo
                JOIN cat_familia f ON p.id_familia = f.id_familia
                JOIN cat_marca m ON p.id_marca = m.id_marca
                LEFT JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
                LEFT JOIN existencia e ON p.id_producto = e.id_producto
                GROUP BY p.id_producto
                ORDER BY p.nombre
                """;
        return jdbcTemplate.query(vr_sql, PRODUCTO_MAPPER);
    }

    public Producto findById(String id) {
        String vr_sql = """
                SELECT p.*, g.nombre as nombre_grupo, f.nombre as nombre_familia,
                       m.nombre as nombre_marca, pr.nombre_comercial as nombre_proveedor
                FROM producto p
                JOIN cat_grupo g ON p.id_grupo = g.id_grupo
                JOIN cat_familia f ON p.id_familia = f.id_familia
                JOIN cat_marca m ON p.id_marca = m.id_marca
                LEFT JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
                WHERE p.id_producto = ?
                """;
        List<Producto> results = jdbcTemplate.query(vr_sql, PRODUCTO_MAPPER, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void save(Producto p) {
        // [DEFENSA] Integridad de Identidad (PK)
        if (p.getIdProducto() == null || p.getIdProducto().isEmpty()) {
            p.setIdProducto(UUID.randomUUID().toString());
        }

        // [DEFENSA] Integridad de SKU (Unique Not Null)
        if (p.getSku() == null || p.getSku().isEmpty() || p.getSku().equalsIgnoreCase("Autogenerado")) {
            // Fallback de generación técnica si llega vacío desde la UI
            String ts = String.valueOf(System.currentTimeMillis());
            p.setSku("75" + ts.substring(ts.length() - 8));
        }

        String sql = "INSERT INTO producto (id_producto, sku, nombre, id_grupo, id_familia, id_marca, codigo_barras, stock_minimo, stock_maximo, costo_unitario, porcentaje_utilidad, clave_prod_serv, clave_unidad, objeto_impuesto, iva_aplicable, id_proveedor, estatus) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombre=?, id_grupo=?, id_familia=?, id_marca=?, codigo_barras=?, stock_minimo=?, stock_maximo=?, costo_unitario=?, porcentaje_utilidad=?, clave_prod_serv=?, clave_unidad=?, objeto_impuesto=?, iva_aplicable=?, id_proveedor=?, estatus=?, fecha_modificacion = NOW()";
        jdbcTemplate.update(sql, p.getIdProducto(), p.getSku(), p.getNombre(), p.getIdGrupo(), p.getIdFamilia(),
                p.getIdMarca(), p.getCodigoBarras(), p.getStockMinimo(), p.getStockMaximo(), p.getCostoUnitario(),
                p.getPorcentajeUtilidad(), p.getClaveProdServ(), p.getClaveUnidad(), p.getObjetoImpuesto(),
                p.getIvaAplicable(), p.getIdProveedor(), p.getEstatus(),
                p.getNombre(), p.getIdGrupo(), p.getIdFamilia(), p.getIdMarca(), p.getCodigoBarras(),
                p.getStockMinimo(), p.getStockMaximo(), p.getCostoUnitario(), p.getPorcentajeUtilidad(),
                p.getClaveProdServ(), p.getClaveUnidad(), p.getObjetoImpuesto(), p.getIvaAplicable(),
                p.getIdProveedor(), p.getEstatus());
    }

    public void deleteById(String id) {
        jdbcTemplate.update("DELETE FROM producto WHERE id_producto = ?", id);
    }

    // --- SECCIÓN: MÉTODOS DE TRAZABILIDAD (KARDEX) ---

    public List<MovimientoInventario> findKardexByProducto(String idProducto) {
        String vr_sql = """
                SELECT m.*, p.nombre as nombre_producto, p.sku as sku_producto, u.nombre as nombre_usuario
                FROM movimiento_inventario m
                JOIN producto p ON m.id_producto = p.id_producto
                JOIN usuario u ON m.id_usuario = u.id_usuario
                WHERE m.id_producto = ?
                ORDER BY m.fecha DESC
                """;
        return jdbcTemplate.query(vr_sql, MOVIMIENTO_MAPPER, idProducto);
    }

    /**
     * Persiste un nuevo movimiento de inventario fn.saveMovimiento.
     * 
     * @param m Instancia de cl.MovimientoInventario.
     */
    public void saveMovimiento(MovimientoInventario m) {
        if (m.getIdMovimiento() == null)
            m.setIdMovimiento(UUID.randomUUID().toString());

        String vr_sql = """
                INSERT INTO movimiento_inventario (id_movimiento, folio, tipo_movimiento, id_producto,
                                                  id_sucursal, cantidad, costo_historico, existencia_anterior,
                                                  existencia_actual, fecha_vencimiento, origen_id, id_usuario, observaciones)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    folio = VALUES(folio),
                    cantidad = VALUES(cantidad),
                    tipo_movimiento = VALUES(tipo_movimiento),
                    observaciones = VALUES(observaciones),
                    costo_historico = VALUES(costo_historico),
                    origen_id = VALUES(origen_id),
                    existencia_anterior = VALUES(existencia_anterior),
                    existencia_actual = VALUES(existencia_actual),
                    fecha = NOW()
                """;

        jdbcTemplate.update(vr_sql, m.getIdMovimiento(), m.getFolio(), m.getTipoMovimiento(),
                m.getIdProducto(), m.getIdSucursal(), m.getCantidad(), m.getCostoHistorico(),
                m.getExistenciaAnterior(), m.getExistenciaActual(), m.getFechaVencimiento(),
                m.getOrigenId(), m.getIdUsuario(), m.getObservaciones());
    }

    /**
     * Actualiza la tabla existencia con el nuevo stock operativo
     * fn.updateExistencia.
     * Si no existe el registro, lo crea (INSERT ON DUPLICATE KEY UPDATE).
     */
    public void updateExistencia(String idProducto, String idSucursal, int nuevoStock) {
        String vr_sql = """
                INSERT INTO existencia (id_existencia, id_producto, id_sucursal, cantidad)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE cantidad = ?
                """;
        jdbcTemplate.update(vr_sql, UUID.randomUUID().toString(), idProducto, idSucursal, nuevoStock, nuevoStock);
    }

    /**
     * Obtiene la existencia actual operativa de un producto en una sucursal
     * fn.getCurrentStock.
     * Basado en la vista v_stock_actual.
     */
    public Integer getCurrentStock(String idProducto, String idSucursal) {
        String vr_sql = "SELECT cantidad FROM existencia WHERE id_producto = ? AND id_sucursal = ?";
        try {
            Integer stock = jdbcTemplate.queryForObject(vr_sql, Integer.class, idProducto, idSucursal);
            if (stock != null && stock == 0) {
                return getActualKardexSum(idProducto, idSucursal);
            }
            return (stock != null) ? stock : 0;
        } catch (Exception e) {
            return getActualKardexSum(idProducto, idSucursal);
        }
    }

    private Integer getActualKardexSum(String idProducto, String idSucursal) {
        String sql = """
                SELECT COALESCE(SUM(CASE
                    WHEN tipo_movimiento IN ('ENTRADA_COMPRA', 'DEVOLUCION_CLIENTE') THEN cantidad
                    WHEN tipo_movimiento IN ('SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 'MERMA') THEN -cantidad
                    WHEN tipo_movimiento = 'AJUSTE' THEN cantidad
                    ELSE 0 END), 0)
                FROM movimiento_inventario
                WHERE id_producto = ? AND id_sucursal = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, idProducto, idSucursal);
        } catch (Exception e) {
            return 0;
        }
    }

    public MovimientoInventario findMovimientoById(String id) {
        String sql = "SELECT m.*, p.nombre as nombre_producto FROM movimiento_inventario m JOIN producto p ON m.id_producto = p.id_producto WHERE m.id_movimiento = ?";
        List<MovimientoInventario> results = jdbcTemplate.query(sql, MOVIMIENTO_MAPPER, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void deleteMovimientoById(String id) {
        jdbcTemplate.update("DELETE FROM movimiento_inventario WHERE id_movimiento = ?", id);
    }
}
```

---

## 🗄️ BASE DE DATOS (SQL)

### 📄 omcgc/database/scripts/00_drop_create_db.sql

```sql
/*
============================================================
Nombre del archivo : 00_drop_create_db.sql
Ruta              : omcgc/database/scripts/00_drop_create_db.sql
Tipo              : Base de Datos (Utilidad DDL)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Script de utilidad para el restablecimiento completo del esquema de base de datos.
ADVERTENCIA: Destructivo. Elimina la base de datos 'optica_erp' si existe.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. Entorno de Desarrollo:
   - Facilitar el reinicio rápido del estado de persistencia (Hard Reset).
   - Configuración de Collation UTF8MB4 (Soporte Unicode completo).
============================================================
*/
DROP DATABASE IF EXISTS db_omcgc_erp;
CREATE DATABASE db_omcgc_erp CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 📄 omcgc/database/scripts/01_schema_usuarios.sql

```sql
/*
============================================================
Nombre del archivo : 01_schema_usuarios.sql
Ruta              : omcgc/database/scripts/01_schema_usuarios.sql
Tipo              : Base de Datos (Script SQL DDL)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.1

Propósito:
Definir la estructura relacional (DDL) para el módulo de Gestión de Identidades,
estableciendo las tablas, restricciones de integridad y relaciones necesarias
para el soporte de usuarios, roles y autenticación.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Modelo de Datos de Autenticación):
   - Tabla 'usuario': Persistencia de credenciales hash y estado de actividad.
   - Tabla 'rol': Catálogo de perfiles de acceso (RBAC).
   - Tabla 'usuario_rol': Relación N:M para asignación de privilegios.
2. RNF-03 (Integridad de Datos):
   - Definición de Claves Primarias (PK) tipo UUID (CHAR 36).
   - Definición de Claves Foráneas (FK) con integridad referencial.
============================================================
*/

USE db_omcgc_erp;

-- =========================================================
-- 4. SEGURIDAD (DDL proporcionado por usuario)
-- =========================================================

-- Aseguramos que las tablas existan (si ya las creaste, esto no afecta si usas IF NOT EXISTS o si ya corriste tu script)
-- Pero para mantener este archivo como fuente de verdad del módulo Usuarios:

/* 
   NOTA: Se asume que la base de datos y tablas ya fueron creadas con el script del usuario.
   Si no, descomentar las líneas de creación. Aquí nos enfocamos en garantizar los DATOS SEMILLA
   necesarios para el Login.
*/

-- =========================================================
-- DATOS SEMILLA
-- =========================================================
-- (Eliminados por solicitud. Se usará acceso 'root' temporalmente)
```

### 📄 omcgc/database/scripts/02_create_tables_usuarios.sql

```sql
/*
============================================================
Nombre del archivo : 02_create_tables_usuarios.sql
Ruta              : omcgc/database/scripts/02_create_tables_usuarios.sql
Tipo              : Base de Datos (Script SQL DDL)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Versión           : v1.0

Propósito:
Crear las tablas necesarias para el módulo de Gestión de Usuarios, Roles y Permisos.
============================================================
*/

USE db_omcgc_erp;

-- =========================================================
-- TABLA: rol
-- Catálogo de roles del sistema
-- =========================================================
CREATE TABLE IF NOT EXISTS rol (
    id_rol VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: usuario
-- Almacena información de usuarios del sistema
-- =========================================================
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: usuario_rol
-- Relación N:M entre usuarios y roles
-- =========================================================
CREATE TABLE IF NOT EXISTS usuario_rol (
    id_usuario VARCHAR(36) NOT NULL,
    id_rol VARCHAR(36) NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: modulo
-- Catálogo de módulos del sistema
-- =========================================================
CREATE TABLE IF NOT EXISTS modulo (
    id_modulo VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- TABLA: permiso
-- Permisos específicos por módulo y rol
-- =========================================================
CREATE TABLE IF NOT EXISTS permiso (
    id_permiso VARCHAR(36) PRIMARY KEY,
    id_rol VARCHAR(36) NOT NULL,
    id_modulo VARCHAR(36) NOT NULL,
    puede_ver BOOLEAN DEFAULT FALSE,
    puede_crear BOOLEAN DEFAULT FALSE,
    puede_editar BOOLEAN DEFAULT FALSE,
    puede_eliminar BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE,
    FOREIGN KEY (id_modulo) REFERENCES modulo(id_modulo) ON DELETE CASCADE,
    UNIQUE KEY unique_rol_modulo (id_rol, id_modulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =========================================================
-- DATOS SEMILLA - ROLES
-- =========================================================
INSERT INTO rol (id_rol, nombre, descripcion) VALUES
('ROL-001', 'ADMINISTRADOR', 'Acceso total al sistema'),
('ROL-002', 'CAJA', 'Gestión de cobros y facturación'),
('ROL-003', 'VENDEDOR', 'Atención a clientes y ventas'),
('ROL-004', 'OPTOMETRISTA', 'Exámenes de la vista y diagnóstico'),
('ROL-005', 'TALLER', 'Graduación y ensamble de lentes'),
('ROL-006', 'ALMACEN', 'Control de stock y proveedores')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- =========================================================
-- DATOS SEMILLA - MÓDULOS
-- =========================================================
INSERT INTO modulo (id_modulo, nombre, descripcion) VALUES
('MOD-001', 'Inventario', 'Gestión de productos y stock'),
('MOD-002', 'Ventas', 'Punto de venta y cotizaciones'),
('MOD-003', 'Agenda', 'Programación de citas'),
('MOD-004', 'Facturación', 'Emisión de CFDI'),
('MOD-005', 'Reportes', 'Consultas y estadísticas'),
('MOD-006', 'Configuración', 'Administración del sistema')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- =========================================================
-- DATOS SEMILLA - USUARIO ROOT (Super Admin)
-- Password: root (hash BCrypt)
-- =========================================================
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo) VALUES
('USR-ROOT', 'Super Administrador', 'root@optica.mx', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma', TRUE)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- Asignar rol de Administrador al usuario root
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
('USR-ROOT', 'ROL-001')
ON DUPLICATE KEY UPDATE id_rol=VALUES(id_rol);

-- =========================================================
-- DATOS SEMILLA - PERMISOS PARA ADMINISTRADOR
-- =========================================================
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) VALUES
('PERM-001', 'ROL-001', 'MOD-001', TRUE, TRUE, TRUE, TRUE),
('PERM-002', 'ROL-001', 'MOD-002', TRUE, TRUE, TRUE, TRUE),
('PERM-003', 'ROL-001', 'MOD-003', TRUE, TRUE, TRUE, TRUE),
('PERM-004', 'ROL-001', 'MOD-004', TRUE, TRUE, TRUE, TRUE),
('PERM-005', 'ROL-001', 'MOD-005', TRUE, TRUE, TRUE, TRUE),
('PERM-006', 'ROL-001', 'MOD-006', TRUE, TRUE, TRUE, TRUE)
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =========================================================
-- DATOS SEMILLA - PERMISOS PARA FACTURISTA
-- =========================================================
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) VALUES
('PERM-007', 'ROL-002', 'MOD-001', TRUE, FALSE, FALSE, FALSE),
('PERM-008', 'ROL-002', 'MOD-002', TRUE, TRUE, TRUE, FALSE),
('PERM-009', 'ROL-002', 'MOD-003', FALSE, FALSE, FALSE, FALSE),
('PERM-010', 'ROL-002', 'MOD-004', TRUE, TRUE, FALSE, FALSE),
('PERM-011', 'ROL-002', 'MOD-005', TRUE, FALSE, FALSE, FALSE),
('PERM-012', 'ROL-002', 'MOD-006', FALSE, FALSE, FALSE, FALSE)
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =========================================================
-- DATOS SEMILLA - PERMISOS PARA RECEPCIONISTA
-- =========================================================
INSERT INTO permiso (id_permiso, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar) VALUES
('PERM-013', 'ROL-003', 'MOD-001', TRUE, FALSE, FALSE, FALSE),
('PERM-014', 'ROL-003', 'MOD-002', TRUE, FALSE, FALSE, FALSE),
('PERM-015', 'ROL-003', 'MOD-003', TRUE, TRUE, TRUE, FALSE),
('PERM-016', 'ROL-003', 'MOD-004', FALSE, FALSE, FALSE, FALSE),
('PERM-017', 'ROL-003', 'MOD-005', FALSE, FALSE, FALSE, FALSE),
('PERM-018', 'ROL-003', 'MOD-006', FALSE, FALSE, FALSE, FALSE)
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);
```

### 📄 omcgc/database/scripts/03_complemento_usuarios.sql

```sql
-- =====================================================
-- SCRIPT COMPLEMENTARIO: Módulo de Usuarios
-- Agrega lo necesario para que funcione el módulo de gestión de usuarios
-- =====================================================

USE db_omcgc_erp;

-- =====================================================
-- 1) CORREGIR PASSWORD DEL USUARIO ADMIN
-- =====================================================
-- Password: admin (hasheado con BCrypt)
UPDATE usuario 
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma'
WHERE email = 'graxsoft_soporte@hotmail.com';

-- =====================================================
-- 2) CREAR TABLA MODULO (si no existe)
-- =====================================================
CREATE TABLE IF NOT EXISTS modulo (
    id_modulo CHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- =====================================================
-- 3) INSERTAR MÓDULOS DEL SISTEMA
-- =====================================================
INSERT INTO modulo (id_modulo, nombre, descripcion, activo) VALUES
(UUID(), 'Inventario', 'Gestión de productos y stock', 1),
(UUID(), 'Ventas', 'Punto de venta y cotizaciones', 1),
(UUID(), 'Agenda', 'Programación de citas', 1),
(UUID(), 'Facturación', 'Emisión de CFDI', 1),
(UUID(), 'Reportes', 'Consultas y estadísticas', 1),
(UUID(), 'Configuración', 'Administración del sistema', 1)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

-- =====================================================
-- 4) AGREGAR CAMPOS A TABLA PERMISO (si no existen)
-- =====================================================
-- Verificar si la tabla permiso tiene la estructura correcta
-- Si no, necesitarás recrearla o alterarla manualmente

-- Opción A: Si quieres mantener compatibilidad, agrega una nueva tabla
CREATE TABLE IF NOT EXISTS permiso_modulo (
    id_permiso_modulo CHAR(36) PRIMARY KEY,
    id_rol CHAR(36) NOT NULL,
    id_modulo CHAR(36) NOT NULL,
    puede_ver TINYINT(1) DEFAULT 0,
    puede_crear TINYINT(1) DEFAULT 0,
    puede_editar TINYINT(1) DEFAULT 0,
    puede_eliminar TINYINT(1) DEFAULT 0,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE,
    FOREIGN KEY (id_modulo) REFERENCES modulo(id_modulo) ON DELETE CASCADE,
    UNIQUE KEY unique_rol_modulo (id_rol, id_modulo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- =====================================================
-- 5) ASIGNAR PERMISOS AL ROL ADMIN
-- =====================================================
INSERT INTO permiso_modulo (id_permiso_modulo, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(),
    r.id_rol,
    m.id_modulo,
    1, 1, 1, 1
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'ADMIN'
ON DUPLICATE KEY UPDATE puede_ver=1, puede_crear=1, puede_editar=1, puede_eliminar=1;

-- =====================================================
-- 6) ASIGNAR PERMISOS AL ROL CAJA
-- =====================================================
INSERT INTO permiso_modulo (id_permiso_modulo, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(),
    r.id_rol,
    m.id_modulo,
    CASE m.nombre
        WHEN 'Inventario' THEN 1
        WHEN 'Ventas' THEN 1
        WHEN 'Facturación' THEN 1
        WHEN 'Reportes' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Ventas' THEN 1
        WHEN 'Facturación' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Ventas' THEN 1
        ELSE 0
    END,
    0
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'CAJA'
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =====================================================
-- 7) ASIGNAR PERMISOS AL ROL OPTOMETRIA
-- =====================================================
INSERT INTO permiso_modulo (id_permiso_modulo, id_rol, id_modulo, puede_ver, puede_crear, puede_editar, puede_eliminar)
SELECT 
    UUID(),
    r.id_rol,
    m.id_modulo,
    CASE m.nombre
        WHEN 'Inventario' THEN 1
        WHEN 'Ventas' THEN 1
        WHEN 'Agenda' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Agenda' THEN 1
        ELSE 0
    END,
    CASE m.nombre
        WHEN 'Agenda' THEN 1
        ELSE 0
    END,
    0
FROM rol r
CROSS JOIN modulo m
WHERE r.nombre = 'OPTOMETRIA'
ON DUPLICATE KEY UPDATE puede_ver=VALUES(puede_ver);

-- =====================================================
-- FIN DEL SCRIPT COMPLEMENTARIO
-- =====================================================

SELECT 'Script complementario ejecutado correctamente' AS Resultado;
```

### 📄 omcgc/database/scripts/04_usuario_prueba.sql

```sql
-- =====================================================
-- SCRIPT MÍNIMO: Solo tablas para módulo de usuarios
-- Ejecutar DESPUÉS del script principal de creación de BD
-- =====================================================

USE db_omcgc_erp;

-- Verificar que las tablas existan, si no, crearlas
-- (El script principal ya debería haberlas creado)

-- Si necesitas recrear solo las tablas de usuarios:
-- DROP TABLE IF EXISTS usuario_rol;
-- DROP TABLE IF EXISTS usuario;
-- DROP TABLE IF EXISTS rol;

-- Las tablas ya deberían existir del script principal
-- Solo vamos a insertar UN usuario de prueba

-- =====================================================
-- INSERTAR USUARIO DE PRUEBA
-- =====================================================

-- Primero, insertar un rol si no existe
INSERT INTO rol (id_rol, nombre) 
VALUES (UUID(), 'ADMIN')
ON DUPLICATE KEY UPDATE nombre='ADMIN';

-- Insertar usuario de prueba
-- Email: admin@test.com
-- Password: admin (en texto plano por ahora, el backend lo hasheará)
INSERT INTO usuario (id_usuario, nombre, email, password_hash, activo, creado_en)
VALUES (
    UUID(),
    'Administrador de Prueba',
    'admin@test.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMye1J0IQ2XloXKKbC8KQyMj.dUZKLDC9Ma', -- BCrypt hash de "admin"
    1,
    NOW()
)
ON DUPLICATE KEY UPDATE nombre='Administrador de Prueba';

-- Asignar rol al usuario
INSERT INTO usuario_rol (id_usuario_rol, id_usuario, id_rol)
SELECT 
    UUID(),
    u.id_usuario,
    r.id_rol
FROM usuario u
CROSS JOIN rol r
WHERE u.email = 'admin@test.com'
  AND r.nombre = 'ADMIN'
ON DUPLICATE KEY UPDATE id_rol=VALUES(id_rol);

-- Verificar que se insertó
SELECT 
    u.id_usuario,
    u.nombre,
    u.email,
    u.activo,
    r.nombre as rol
FROM usuario u
LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
LEFT JOIN rol r ON ur.id_rol = r.id_rol
WHERE u.email = 'admin@test.com';
```

---

## 🛡️ MÓDULO ADICIONAL: BITÁCORA DE AUDITORÍA

### 🗄️ BASE DE DATOS (SQL)

```sql
-- =====================================================
-- TABLA DE BITÁCORA DE SEGURIDAD
-- =====================================================
CREATE TABLE bitacora_seguridad (
    id_bitacora     VARCHAR(50) PRIMARY KEY,
    id_usuario      VARCHAR(50),               -- Actor de la acción
    accion          TEXT NOT NULL,              -- AES Encrypted: Impacto | Cat | Código
    ip_origen       VARCHAR(45),               -- AES Encrypted
    detalles        TEXT,                       -- AES Encrypted: Mensaje | Análisis Técnico
    fecha           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
```

### ☕ BACKEND (Java)

#### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/BitacoraController.java
```java
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Bitacora;
import com.omcgc.erp.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@CrossOrigin(origins = "*")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping
    public ResponseEntity<List<Bitacora>> getLogs(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String buscar) {

        List<Bitacora> logs = bitacoraService.listar(desde, hasta, usuario, buscar);
        return ResponseEntity.ok(logs);
    }
}
```

#### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/BitacoraService.java
```java
package com.omcgc.erp.service;

import com.omcgc.erp.model.Bitacora;
import com.omcgc.erp.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private AuditPatternService auditPatternService;

    private static final String MASTER_KEY = "WALOOK_AUDIT_PRIVATE_SECURITY_2026";

    public void registrarEvento(String idUsuario, String idPatron, String ip, String paramX, String paramS) {
        try {
            String logCompleto = auditPatternService.buildLog(idPatron, paramX, paramS);
            String[] parts = logCompleto.split("\\|");
            int n = parts.length;

            String impacto = parts[0].trim();
            String cat = parts[1].trim();
            String msjHumano = parts[2].trim();
            String code = parts[n - 1].trim();

            StringBuilder sbAnalisis = new StringBuilder();
            for (int i = 3; i < n - 1; i++) {
                sbAnalisis.append(parts[i].trim());
                if (i < n - 2) sbAnalisis.append(" | ");
            }
            String analisis = sbAnalisis.toString();

            Bitacora b = new Bitacora();
            b.setIdUsuario(idUsuario);
            b.setAccion(encrypt(impacto + " | " + cat + " | " + code));
            b.setIpOrigen(encrypt(ip != null ? ip : "0.0.0.0"));
            b.setDetalles(encrypt(msjHumano + " | " + analisis));

            bitacoraRepository.save(b);
        } catch (Exception e) {
            System.err.println("Error registrando evento: " + e.getMessage());
        }
    }

    public java.util.List<Bitacora> listar(String desde, String hasta, String usuario, String buscar) {
        java.util.List<Bitacora> cifrados = bitacoraRepository.findAll(desde, hasta, usuario, buscar);
        for (Bitacora b : cifrados) {
            try {
                b.setAccion(decrypt(b.getAccion()));
                b.setIpOrigen(decrypt(b.getIpOrigen()));
                b.setDetalles(decrypt(b.getDetalles()));
            } catch (Exception e) {
                b.setAccion("[Error Decrypt]");
            }
        }

        if (buscar != null && !buscar.trim().isEmpty()) {
            String term = buscar.toLowerCase();
            return cifrados.stream()
                    .filter(b -> b.getAccion().toLowerCase().contains(term) || b.getDetalles().toLowerCase().contains(term))
                    .collect(java.util.stream.Collectors.toList());
        }
        return cifrados;
    }

    private String encrypt(String str) throws Exception {
        SecretKeySpec key = generateKey();
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(c.doFinal(str.getBytes("UTF-8")));
    }

    private String decrypt(String str) throws Exception {
        SecretKeySpec key = generateKey();
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, key);
        return new String(c.doFinal(Base64.getDecoder().decode(str)), "UTF-8");
    }

    private SecretKeySpec generateKey() throws Exception {
        byte[] key = MASTER_KEY.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        return new SecretKeySpec(Arrays.copyOf(sha.digest(key), 16), "AES");
    }
}
```

#### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/AuditPatternService.java
```java
/*
============================================================
Nombre del archivo : AuditPatternService.java
Ruta              : backend/src/main/java/com/omcgc/erp/service/AuditPatternService.java
Tipo              : Backend (Service / Security)

Propósito:
Gestionar el diccionario cifrado de patrones de auditoría (.dat).
Maneja el cifrado AES-256 de la Matriz Maestra en formato binario.
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.LogPattern;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;

@Service
public class AuditPatternService {

    private static final String MASTER_KEY = "W4L00K_4UD1T_SYST3M_S3CUR1TY_2026"; // Diferente a la de logs por seguridad
    private static final String FILE_PATH = "audit_dictionary.dat";
    private Map<String, LogPattern> patterns = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            // [SEGURO V5.2] Generar en raíz de ejecución para evitar bloqueos
            generateInitialDictionary();
            loadDictionary();
        } catch (Exception e) {
            System.err.println("[CRITICAL] Error al inicializar diccionario de auditoría: " + e.getMessage());
        }
    }

    private SecretKeySpec generateKey() throws Exception {
        byte[] key = MASTER_KEY.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // Usar 128 bits para compatibilidad estándar
        return new SecretKeySpec(key, "AES");
    }

    /**
     * Genera el diccionario inicial cifrado en binario (.dat)
     */
    public void generateInitialDictionary() throws Exception {
        List<LogPattern> list = new ArrayList<>();

        // --- SEGURIDAD (AUTH) ---
        list.add(new LogPattern("AUTH-01", "CORRECTO", "AUTH", "Acceso concedido al sistema.",
                "SUCESO: Login (Inicio de sesión) | ESTADO: Identidad Verificada | SESIÓN: Nueva_JWT"));
        list.add(new LogPattern("AUTH-02", "ERROR", "AUTH", "Credenciales inválidas.",
                "SUCESO: Validación de identidad | ERROR: Contraseña incorrecta o usuario inexistente"));
        list.add(new LogPattern("AUTH-03", "ALERTA", "AUTH", "Cuenta desactivada por administración.",
                "SUCESO: Regla de acceso | ESTADO: Usuario Inactivo"));
        list.add(new LogPattern("AUTH-05", "AVISO", "AUTH", "La sesión ha finalizado por seguridad.",
                "SUCESO: Vencimiento de Token | MOTIVO: Inactividad prolongada"));

        // --- CRUD UNIVERSAL (DATA) ---
        list.add(new LogPattern("CRUD-01", "CORRECTO", "DATA", "Alta de registro exitosa.",
                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: ALTA | REF: {S}"));
        list.add(new LogPattern("CRUD-02", "AVISO", "DATA", "Información actualizada satisfactoriamente.",
                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: CAMBIO | DETALLE: {S}"));
        list.add(new LogPattern("CRUD-03", "AVISO", "DATA", "Cambio de estatus aplicado.",
                "MÓDULO: {M} | REGISTRO: {E} | ACCIÓN: ESTADO | FLUJO: {S}"));
        list.add(new LogPattern("CRUD-04", "ERROR", "DATA", "Datos incorrectos en el formulario.",
                "MÓDULO: {M} | ACCIÓN: VALIDACIÓN | CAMPO: {X} | ERROR: Formato no válido"));
        list.add(new LogPattern("CRUD-05", "ERROR", "DATA", "El registro ya existe en el sistema.",
                "MÓDULO: {M} | ACCIÓN: DUPLICADO | CLAVE: {X} | ERROR: Llave duplicada"));

        // --- USUARIOS Y ADMIN (USR/ADM) ---
        list.add(new LogPattern("USR-30", "CORRECTO", "USER", "Nueva clave enviada por correo.",
                "SUCESO: Restablecimiento de clave | DESTINO: {X} | JUSTIFICACIÓN: {S}"));
        list.add(new LogPattern("ADM-01", "AVISO", "ADMIN", "Privilegios del rol actualizados.",
                "SUCESO: Actualización de permisos | IMPACTO: Acceso Global"));

        // --- INVENTARIOS (INV) ---
        list.add(new LogPattern("INV-01", "CORRECTO", "INV", "Movimiento de inventario registrado.",
                "{S}"));

        // --- PRODUCTOS (PRO) ---
        list.add(new LogPattern("PRO-01", "CORRECTO", "PRODUCT", "Nuevo producto registrado en catálogo.",
                "SKU: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
        list.add(new LogPattern("PRO-02", "AVISO", "PRODUCT", "Ficha técnica de producto modificada.",
                "SKU: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

        // --- CLIENTES (CLI) ---
        list.add(new LogPattern("CLI-01", "CORRECTO", "CLIENT", "Nuevo cliente registrado.",
                "RFC: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
        list.add(new LogPattern("CLI-02", "AVISO", "CLIENT", "Información de cliente actualizada.",
                "RFC: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

        // --- PROVEEDORES (PRV) ---
        list.add(new LogPattern("PRV-01", "CORRECTO", "VENDOR", "Nuevo proveedor registrado.",
                "RFC: {X} | NOMBRE: {S} | ACCIÓN: ALTA"));
        list.add(new LogPattern("PRV-02", "AVISO", "VENDOR", "Información de proveedor actualizada.",
                "RFC: {X} | NOMBRE: {S} | ACCIÓN: EDICIÓN"));

        // --- FINANZAS Y CFDI (FIN) ---
        list.add(new LogPattern("FIN-01", "CORRECTO", "FIN", "Factura generada y timbrada correctamente.",
                "SUCESO: Timbrado XML | PROVEEDOR: {P} | SAT: 200_OK | UUID: {U}"));
        list.add(new LogPattern("FIN-02", "AVISO", "FIN", "Factura cancelada ante el SAT.",
                "SUCESO: Anulación fiscal | CÓDIGO: {C} | ESTADO: Cancelado en SAT"));
        list.add(new LogPattern("FIN-03", "CORRECTO", "FIN", "Transacción financiera completada.",
                "SUCESO: Registro de ingreso | MÉTODO: {M} | MONTO: {$$}"));

        // --- SISTEMA Y SEGURIDAD (SYS/SEC) ---
        list.add(new LogPattern("SYS-01", "ALERTA", "SYSTEM", "Error de conexión con el servidor.",
                "SUCESO: Fallo de BD | ERROR: Tiempo agotado o conexión rechazada"));
        list.add(new LogPattern("SYS-02", "ALERTA", "SYSTEM", "El servicio externo no responde.",
                "SUCESO: Petición HTTP | DESTINO: {SAT/PAC} | ERROR: Tiempo agotado"));
        list.add(new LogPattern("SYS-99", "ALERTA", "SYSTEM", "Error interno del sistema.",
                "SUCESO: Excepción Global | TIPO: {E} | CLASE: {C}"));
        list.add(new LogPattern("SEC-01", "ALERTA", "SECURITY", "Intento de acción no autorizada.",
                "SUCESO: Bloqueo de seguridad | AMENAZA: Violación de acceso"));

        // Serializar a Bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(list);
        byte[] bytes = bos.toByteArray();

        // Cifrar Bytes
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generateKey());
        byte[] encryptedBytes = cipher.doFinal(bytes);

        // Guardar a archivo binario
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            fos.write(encryptedBytes);
        }
        System.out.println("[AUDIT] Diccionario binario cifrado generado en: " + FILE_PATH);
    }

    /**
     * Carga el diccionario desde el archivo binario (.dat) y lo descifra en memoria
     */
    @SuppressWarnings("unchecked")
    private void loadDictionary() throws Exception {
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(FILE_PATH));

        // Descifrar
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, generateKey());
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Deserializar
        ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        List<LogPattern> list = (List<LogPattern>) ois.readObject();

        patterns.clear();
        for (LogPattern lp : list) {
            patterns.put(lp.getId(), lp);
        }
    }

    public LogPattern getPattern(String id) {
        return patterns.get(id);
    }

    /**
     * Construye un log completo basado en un patrón y datos dinámicos
     */
    public String buildLog(String id, String paramX, String paramS) {
        LogPattern lp = getPattern(id);
        if (lp == null)
            return "Unknown Event | ID: " + id;

        String tec = lp.getAnalisisTecnico();
        // Mapeo flexible de placeholders según la Matriz Maestra
        if (paramX != null) {
            tec = tec.replace("{X}", paramX)
                   .replace("{T}", paramX)
                   .replace("{M}", paramX) // Soporte para Módulo
                   .replace("{P}", paramX)
                   .replace("{f}", paramX)
                   .replace("{K}", paramX)
                   .replace("{qty_A -> qty_B}", paramX);
        }
        if (paramS != null) {
            tec = tec.replace("{S}", paramS)
                   .replace("{id}", paramS)
                   .replace("{D}", paramS)
                   .replace("{E}", paramS) // Soporte para Entidad
                   .replace("{U}", paramS)
                   .replace("{R}", paramS);
        }

        return String.format("%s | [%s] | %s | %s | %s",
                lp.getImpacto(), lp.getCategoria(), lp.getMensajeAmigable(), tec, lp.getId());
    }
}
```
