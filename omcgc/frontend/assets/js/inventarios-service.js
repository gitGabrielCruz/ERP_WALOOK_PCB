/*
============================================================
Nombre del archivo : inventarios-service.js
Ruta              : omcgc/frontend/assets/js/inventarios-service.js
Tipo              : Frontend (Logic Controller)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v2.0 (Integración API REST Real)

Propósito:
Orquestador de lógica de negocio para la gestión de inventarios. Controla
la sincronización con la capa de persistencia (API REST), gestiona el
estado del catálogo y automatiza cálculos fiscales y comerciales.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M01-01 (Registro): Persistencia de productos y validaciones.
2. HU-M01-05 (Kardex): Historial de movimientos de inventario.
3. RF-04 (Seguridad): Control de acceso basado en roles y permisos.
============================================================
*/

const InventariosService = {
    // [SECTION 1] Configuración y Estado del Módulo
    endpoints: {
        productos: AppConfig.getEndpoint('inventarios/productos'),
        movimientos: AppConfig.getEndpoint('inventarios/movimientos'),
        catalogos: AppConfig.getEndpoint('catalogos')
    },
    productos: [],
    grupos: [],
    familias: [],
    marcas: [],
    productoSeleccionado: null,
    permisosActuales: { ver: false, crear: false, editar: false, eliminar: false },

    /**
     * Matriz de Datos Fiscales SAT Integral (Especialidad Óptica v4.0)
     */
    satMatrix: {
        productos: [
            { label: 'Armazones (Monturas de Anteojos)', clave: '42142903', unidad: 'H87', desc: 'Pieza' },
            { label: 'Micas / Lentes Graduados (Oftálmicos)', clave: '42142902', unidad: 'H87', desc: 'Pieza' },
            { label: 'Lentes de Sol / Gafas Prototipo', clave: '42142905', unidad: 'H87', desc: 'Pieza' },
            { label: 'Lentes de Contacto (Generales)', clave: '42142913', unidad: 'H87', desc: 'Pieza' },
            { label: 'Lentes de Contacto Graduados/Terapéuticos', clave: '42183065', unidad: 'H87', desc: 'Pieza' },
            { label: 'Soluciones Limpiadoras / Gotas Oftálmicas', clave: '42142914', unidad: 'H87', desc: 'Pieza' },
            { label: 'Estuches / Sujetadores / Cordones', clave: '42294520', unidad: 'H87', desc: 'Pieza' },
            { label: 'Kits de Limpieza y Mantenimiento', clave: '42142908', unidad: 'H87', desc: 'Pieza' },
            { label: 'Componentes / Refacciones de Armazón', clave: '31242101', unidad: 'H87', desc: 'Pieza' },
            { label: 'Instrumentos de Diagnóstico (Oftalmoscopios)', clave: '42294500', unidad: 'H87', desc: 'Pieza' },
            { label: 'Carteleras de Examen / Cartas de Visión', clave: '42183001', unidad: 'H87', desc: 'Pieza' }
        ],
        servicios: [
            { label: 'Examen de la Vista (Consulta Optometría)', clave: '85121603', unidad: 'E48', desc: 'Servicio' },
            { label: 'Consulta Médica Oftalmológica', clave: '85121603', unidad: 'E48', desc: 'Servicio' },
            { label: 'Evaluación de Salud Individual (Screening)', clave: '85122200', unidad: 'E48', desc: 'Servicio' },
            { label: 'Mantenimiento / Calibración de Lentes', clave: '73151520', unidad: 'E48', desc: 'Servicio' },
            { label: 'Reparación Técnica de Anteojos', clave: '73151520', unidad: 'E48', desc: 'Servicio' }
        ]
    },

    /**
     * Inyecta valores de la matriz SAT en el formulario
     */
    selectSatData: function (clave, unidad) {
        if (document.getElementById('claveProdServ')) document.getElementById('claveProdServ').value = clave;
        if (document.getElementById('claveUnidad')) document.getElementById('claveUnidad').value = unidad;
        this.actualizarDescripcionesFiscales();
        this.showToast("Datos fiscales SAT actualizados", "info");
    },

    /**
     * Resuelve las claves SAT a etiquetas legibles
     */
    actualizarDescripcionesFiscales: function () {
        const cp = document.getElementById('claveProdServ')?.value;
        const cu = document.getElementById('claveUnidad')?.value;

        const dcp = document.getElementById('descClaveProdServ');
        const dcu = document.getElementById('descClaveUnidad');

        if (dcp) dcp.textContent = cp ? (this.buscarLabelPorClave(cp, 'label') || 'Código no reconocido') : '';
        if (dcu) dcu.textContent = cu ? (this.buscarLabelPorClave(cu, 'desc', true) || 'Unidad no reconocida') : '';
    },

    buscarLabelPorClave: function (clave, campo, esUnidad = false) {
        // Buscar en productos
        const p = this.satMatrix.productos.find(i => i.clave === clave || (esUnidad && i.unidad === clave));
        if (p) return esUnidad ? p.desc : p.label;

        // Buscar en servicios
        const s = this.satMatrix.servicios.find(i => i.clave === clave || (esUnidad && i.unidad === clave));
        if (s) return esUnidad ? s.desc : s.label;

        return null;
    },

    showToast: function (mensaje, tipo) {
        if (typeof MessageService !== 'undefined') {
            MessageService.mostrar(tipo === 'error' ? 3 : 4, "Notificación", mensaje);
        } else {
            alert(mensaje);
        }
    },

    toggleSatHelper: function (event) {
        event.stopPropagation();
        const existing = document.getElementById('sat-helper-menu');
        if (existing) {
            existing.remove();
            return;
        }

        const menu = document.createElement('div');
        menu.id = 'sat-helper-menu';

        Object.assign(menu.style, {
            position: 'absolute',
            top: (event.clientY + 10) + 'px',
            left: (event.clientX - 250) + 'px',
            width: '320px',
            backgroundColor: 'white',
            border: '1px solid #e2e8f0',
            borderRadius: '8px',
            boxShadow: '0 10px 15px -3px rgba(0, 0, 0, 0.1)',
            zIndex: '3000',
            padding: '12px',
            fontFamily: 'sans-serif',
            maxHeight: '400px',
            overflowY: 'auto'
        });

        const idGrupo = document.getElementById('idGrupo').value;
        const grupo = this.grupos.find(g => g.idGrupo === idGrupo);
        const esServicio = grupo ? grupo.esServicio : false;

        let html = '<div style="font-size: 0.8rem; font-weight: 700; color: #475569; margin-bottom: 8px; border-bottom: 1px solid #f1f5f9; padding-bottom: 4px;">ASISTENTE FISCAL SAT</div>';

        const renderItems = (items, title) => {
            html += `<div style="font-size: 0.7rem; color: #94a3b8; margin: 8px 0 4px 0; text-transform: uppercase;">${title}</div>`;
            items.forEach(item => {
                html += `
                    <div style="padding: 8px; border-radius: 4px; cursor: pointer; transition: background 0.2s;" 
                         onmouseover="this.style.backgroundColor='#f8fafc'" 
                         onmouseout="this.style.backgroundColor='transparent'"
                         onclick="InventariosService.selectSatData('${item.clave}', '${item.unidad}'); document.getElementById('sat-helper-menu').remove();">
                        <div style="font-size: 0.85rem; font-weight: 600; color: #1e293b;">${item.label}</div>
                        <div style="font-size: 0.7rem; color: #64748b;">${item.clave} | ${item.unidad} (${item.desc})</div>
                    </div>
                `;
            });
        };

        if (esServicio) {
            renderItems(this.satMatrix.servicios, "Servicios Sugeridos");
            renderItems(this.satMatrix.productos, "Otros Productos");
        } else {
            renderItems(this.satMatrix.productos, "Productos Sugeridos");
            renderItems(this.satMatrix.servicios, "Otros Servicios");
        }

        menu.innerHTML = html;
        document.body.appendChild(menu);

        const closeMenu = (e) => {
            if (!menu.contains(e.target)) {
                menu.remove();
                document.removeEventListener('click', closeMenu);
            }
        };
        setTimeout(() => document.addEventListener('click', closeMenu), 10);
    },

    sugerirDatosFiscales: function (nombreFam) {
        if (!nombreFam || nombreFam.includes('Seleccione')) return;

        const n = nombreFam.toUpperCase();
        let satMatch = null;

        if (n.includes('ARMAZON') || n.includes('MONTURA')) satMatch = this.satMatrix.productos[0];
        else if (n.includes('MICA') || n.includes('GRADUADO')) satMatch = this.satMatrix.productos[1];
        else if (n.includes('SOL')) satMatch = this.satMatrix.productos[2];
        else if (n.includes('CONTACTO')) {
            satMatch = n.includes('GRAD') || n.includes('TERAP') ? this.satMatrix.productos[4] : this.satMatrix.productos[3];
        }
        else if (n.includes('SOLUCION') || n.includes('GOTAS') || n.includes('LIMPIEZA')) satMatch = this.satMatrix.productos[5];
        else if (n.includes('ESTUCHE') || n.includes('CORDON') || n.includes('SUJETADOR')) satMatch = this.satMatrix.productos[6];
        else if (n.includes('KIT')) satMatch = this.satMatrix.productos[7];
        else if (n.includes('REFACCION') || n.includes('COMPONENTE')) satMatch = this.satMatrix.productos[8];
        else if (n.includes('EXAMEN') || n.includes('VISTA')) satMatch = this.satMatrix.servicios[0];
        else if (n.includes('CONSULTA')) satMatch = this.satMatrix.servicios[1];
        else if (n.includes('REPARACION') || n.includes('MANTENIMIENTO') || n.includes('AJUSTE')) satMatch = this.satMatrix.servicios[4];

        if (satMatch) {
            this.selectSatData(satMatch.clave, satMatch.unidad);
        }
    },

    // [SECTION 2] Inicialización de Sistema
    init: async function () {
        console.log("%c[InventariosService] Inicializando integración real v2.0...", "color: #2563eb; font-weight: bold;");

        try {
            // SRP: Delegar a AuthService para obtener permisos reales encriptados
            if (typeof AuthService !== 'undefined') {
                this.permisosActuales = AuthService.obtenerPermisosModulo('INVENTARIOS');

                if (!this.permisosActuales.ver) {
                    MessageService.mostrar(8, "Acceso Denegado", "No tiene permisos para ver el módulo de Inventarios.", "Será redirigido al menú principal.", "", () => {
                        window.location.href = 'menu.html';
                    });
                    return;
                }
            }
        } catch (error) {
            console.error("[Auth] Error en validación de identidad:", error);
        }

        this.bindEvents();

        // Carga paralela de catálogos y productos
        await Promise.all([
            this.fetchCatalogos(),
            this.fetchProductos(),
            this.fetchSucursales()
        ]);

        // Aplicar reglas de seguridad visual
        this.aplicarSeguridadVisual();

        // Sincronización de Identidad y Clocks (La Biblia 2.1)
        this.renderUserInfo();
        this.startClocks();
    },

    aplicarSeguridadVisual: function () {
        const p = this.permisosActuales;
        const ifield = (id) => document.getElementById(id);

        // Control de botón "Nuevo"
        if (ifield('btnNuevo')) ifield('btnNuevo').style.display = p.crear ? 'inline-flex' : 'none';

        // Control de botón "Guardar"
        if (ifield('btnGuardar')) {
            // El botón de guardar se oculta si no puede ni crear ni editar
            ifield('btnGuardar').style.display = (p.crear || p.editar) ? 'inline-flex' : 'none';
        }

        // Control de Estatus (Inactivar/Activar)
        // Solo puede cambiar el estatus si tiene permiso de "Eliminar" (Inactivar en este ERP)
        const selectEstatus = ifield('estatus');
        if (selectEstatus) {
            selectEstatus.disabled = !p.eliminar;
            if (!p.eliminar) {
                selectEstatus.title = "No tiene permisos para inactivar/activar productos.";
            }
        }

        // Control de Bitácora (Solo Admin en sesión)
        if (ifield('btnBitacora')) {
            const user = JSON.parse(sessionStorage.getItem('user') || '{}');
            const isAdmin = user.rol === 'ADMIN' || user.rol === 'Administrador' || user.nombreRol === 'ADMIN';
            ifield('btnBitacora').style.display = isAdmin ? 'inline-flex' : 'none';
        }

        // Control de Registro de Movimientos
        if (ifield('btnRegistrarMov')) {
            ifield('btnRegistrarMov').style.display = (p.crear || p.editar) ? 'inline-flex' : 'none';
        }
    },

    // [SECTION 3] Gestores de Eventos (UI/UX Listeners)
    bindEvents: function () {
        // Enlace para cálculos automáticos de utilidad/precio
        document.getElementById('costoUnitario')?.addEventListener('input', () => this.calcularPrecioVenta());
        document.getElementById('porcentajeUtilidad')?.addEventListener('input', () => this.calcularPrecioVenta());

        // Búsqueda reactiva
        document.getElementById('searchInput')?.addEventListener('input', (e) => this.filtrarProductos(e.target.value));

        // Filtro de Grupo (UI Filter)
        document.getElementById('filterGrupo')?.addEventListener('change', (e) => this.filtrarProductos());

        // Jerarquía Grupo -> Familia en el Formulario
        document.getElementById('idGrupo')?.addEventListener('change', (e) => this.cargarFamiliasDependientes(e.target.value));

        // Sugerencia Fiscal automática al cambiar de familia
        document.getElementById('idFamilia')?.addEventListener('change', (e) => {
            const select = e.target;
            const nombreFam = select.options[select.selectedIndex].text;
            this.sugerirDatosFiscales(nombreFam);
        });

        // Actualización manual de descripciones fiscales
        document.getElementById('claveProdServ')?.addEventListener('input', () => this.actualizarDescripcionesFiscales());
        document.getElementById('claveUnidad')?.addEventListener('input', () => this.actualizarDescripcionesFiscales());

        // Navegación de Tabs
        const tabBtns = document.querySelectorAll('.tab-btn');
        tabBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                const targetTab = e.target.getAttribute('data-tab');
                this.switchTab(targetTab, e.target);
                if (targetTab === 'kardex' && this.productoSeleccionado) {
                    this.fetchKardex(this.productoSeleccionado.idProducto);
                }
            });
        });

        // Filtros de Umbral (Segmented Control)
        document.querySelectorAll('.status-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const group = btn.closest('.status-toggle');
                if (group) {
                    group.querySelectorAll('.status-btn').forEach(b => b.classList.remove('active'));
                }
                btn.classList.add('active');
                this.filtrarProductos();
            });
        });

        // Navegación por Teclado (Homologado con Clientes)
        document.addEventListener('keydown', (e) => {
            if (e.target.tagName === 'INPUT' || e.target.tagName === 'SELECT' || e.target.tagName === 'TEXTAREA') return;

            const selected = document.querySelector('#inventarioTableBody tr.selected-row');
            if (!selected) return;

            const rows = Array.from(document.querySelectorAll('#inventarioTableBody tr'));
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
    },

    // [SECTION 4] Lógica de Negocio y Persistencia (API REST)

    fetchCatalogos: async function () {
        try {
            const [resGrupos, resMarcas] = await Promise.all([
                fetch(`${this.endpoints.catalogos}/grupos`),
                fetch(`${this.endpoints.catalogos}/marcas`)
            ]);

            this.grupos = await resGrupos.json();
            this.marcas = await resMarcas.json();

            this.llenarSelect('filterGrupo', this.grupos, 'idGrupo', 'nombre', 'Todos los grupos');
            this.llenarSelect('idGrupo', this.grupos, 'idGrupo', 'nombre', 'Seleccione Grupo...');
            this.llenarSelect('idMarca', this.marcas, 'idMarca', 'nombre', 'Seleccione Marca...');
        } catch (error) {
            console.error("[API] Error al cargar catálogos:", error);
        }
    },

    cargarFamiliasDependientes: async function (idGrupo, idFamiliaSeleccionada = null) {
        const selectFam = document.getElementById('idFamilia');
        selectFam.innerHTML = '<option value="">Cargando familias...</option>';

        if (!idGrupo) {
            selectFam.innerHTML = '<option value="">Seleccione Grupo primero...</option>';
            return;
        }

        try {
            const response = await fetch(`${this.endpoints.catalogos}/familias?grupoId=${idGrupo}`);
            const familias = await response.json();
            this.llenarSelect('idFamilia', familias, 'idFamilia', 'nombre', 'Seleccione Familia...');

            if (idFamiliaSeleccionada) {
                selectFam.value = idFamiliaSeleccionada;
            }
        } catch (error) {
            console.error("[API] Error al cargar familias dependientes:", error);
        }
    },

    fetchProductos: async function () {
        try {
            const response = await fetch(this.endpoints.productos);
            this.productos = await response.json();
            this.renderTabla(this.productos);
        } catch (error) {
            console.error("[API] Error al sincronizar catálogo:", error);
        }
    },

    fetchKardex: async function (idProducto) {
        const tbody = document.getElementById('kardexTableBody');
        tbody.innerHTML = '<tr><td colspan="6" style="text-align:center">Cargando trazabilidad...</td></tr>';

        try {
            const response = await fetch(`${this.endpoints.productos}/${idProducto}/kardex`);
            const movs = await response.json();
            this.renderKardex(movs);
        } catch (error) {
            console.error("[API] Error al cargar Kardex:", error);
        }
    },

    fetchSucursales: async function () {
        try {
            // Endpoint nuevo creado en el backend
            const response = await fetch(`${this.endpoints.catalogos}/sucursales`);
            if (response.ok) {
                const sucursales = await response.json();
                if (sucursales && sucursales.length > 0) {
                    const current = sessionStorage.getItem('id_sucursal_actual');

                    // Validacion: verificar que el ID almacenado exista en la BD actual
                    // Previene FK Constraint Failure tras reset de BD o cambio de UUIDs
                    const existeEnBD = current && sucursales.some(s => s.idSucursal === current);

                    if (!current || !existeEnBD) {
                        // Reasignar al primer ID valido de la BD
                        sessionStorage.setItem('id_sucursal_actual', sucursales[0].idSucursal);
                        sessionStorage.setItem('nombre_sucursal_actual', sucursales[0].nombre);
                        if (current && !existeEnBD) {
                            console.warn(`[Inventarios] Sucursal en sesion (${current}) no existe en BD. Reasignada a: ${sucursales[0].nombre}`);
                        } else {
                            console.log(`[Inventarios] Contexto de Sucursal inicializado: ${sucursales[0].nombre}`);
                        }
                    }
                } else {
                    console.warn("[Inventarios] No hay sucursales registradas en el sistema.");
                }
            }
        } catch (e) {
            console.error("[API] Error cargando sucursales:", e);
        }
    },

    fetchProductos: async function () {
        try {
            const response = await fetch(this.endpoints.productos);
            this.productos = await response.json();
            this.renderTabla(this.productos);
        } catch (error) {
            console.error("[API] Error al sincronizar catálogo:", error);
        }
    },

    guardarProducto: async function () {
        try {
            console.log("[Inventarios] Iniciando proceso de guardado...");
            const esNuevo = !this.productoSeleccionado;

            // 1. Validación de permisos
            if (esNuevo && !this.permisosActuales.crear) {
                MessageService.mostrar(8, "Operación Bloqueante", "Su perfil no cuenta con permisos para registrar nuevos productos.");
                return;
            }
            if (!esNuevo && !this.permisosActuales.editar) {
                MessageService.mostrar(8, "Operación Bloqueante", "Su perfil no cuenta con permisos para modificar el catálogo maestro.");
                return;
            }

            // 2. Extracción y Limpieza de datos (Defensivo)
            const getV = (id) => document.getElementById(id) ? document.getElementById(id).value.trim() : '';

            const nombre = getV('nombreProducto');
            const idMarca = getV('idMarca');
            const idGrupo = getV('idGrupo');
            const idFamilia = getV('idFamilia');
            const claveProdServ = getV('claveProdServ');
            const claveUnidad = getV('claveUnidad');
            const objetoImpuesto = getV('objetoImpuesto');

            // 3. Validación de campos obligatorios (Maestros + Fiscales)
            if (!nombre || !idMarca || !idGrupo || !idFamilia || !claveProdServ || !claveUnidad || !objetoImpuesto) {
                // Si el error es fiscal, forzamos el cambio de pestaña para que el usuario vea el campo vacío
                if (!claveProdServ || !claveUnidad || !objetoImpuesto) {
                    const fiscalTabBtn = document.querySelector('[data-tab="fiscal"]');
                    if (fiscalTabBtn) fiscalTabBtn.click();
                }

                MessageService.mostrar(1, "Información Incompleta", "Los campos marcados con asterisco (*) son obligatorios para la integridad del catálogo.", "Debe completar la información en DATOS MAESTROS y FISCAL (SAT).");
                return;
            }

            // 4. Validaciones Técnicas de Tipos
            const costoUnitario = parseFloat(document.getElementById('costoUnitario').value) || 0;
            const porcentajeUtilidad = parseFloat(document.getElementById('porcentajeUtilidad').value) || 0;

            if (costoUnitario < 0) {
                MessageService.mostrar(1, "Inconsistencia de Costo", "El costo unitario no puede ser negativo.", "Corrija el valor para continuar.");
                return;
            }

            // 5. Retroalimentación Inmediata (Spinner)
            MessageService.procesando("Sincronizando ficha técnica con el servidor...");

            // 6. Preparación de Payload
            let skuFinal = getV('idProducto');
            if (skuFinal === '' || skuFinal === 'Autogenerado') {
                // Generador temporal en Frontend para evitar error de DB mientras se reinicia el Backend
                const ts = String(Date.now());
                skuFinal = "75" + ts.substring(ts.length - 8);
            }

            const productData = {
                idProducto: this.productoSeleccionado ? this.productoSeleccionado.idProducto : null,
                sku: skuFinal,
                nombre: nombre,
                idMarca: idMarca,
                idGrupo: idGrupo,
                idFamilia: idFamilia,
                costoUnitario: costoUnitario,
                porcentajeUtilidad: porcentajeUtilidad,
                stockMinimo: parseInt(getV('stockMinimo')) || 0,
                claveProdServ: getV('claveProdServ'),
                claveUnidad: getV('claveUnidad'),
                objetoImpuesto: getV('objetoImpuesto'),
                ivaAplicable: parseFloat(getV('ivaAplicable')) || 16.0,
                estatus: getV('estatus') || 'ACTIVO',
                codigoBarras: getV('codigoBarras') || null
            };

            // 7. Ejecución de Persistencia
            const response = await fetch(this.endpoints.productos, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(productData)
            });

            MessageService.cerrar();

            if (response.ok) {
                MessageService.mostrar(4, "Catálogo Actualizado", "La información se ha persistido correctamente.");
                this.limpiarFormulario();
                await this.fetchProductos();
            } else {
                const technicalError = await response.text();
                // Pasamos el error técnico como 5to parámetro para que aparezca en el bloque <pre> y en el reporte
                MessageService.mostrar(3, "Fallo en Persistencia", "El servidor rechazó la solicitud de guardado.", "Revise los detalles técnicos del motor de base de datos:", technicalError);
            }

        } catch (error) {
            if (typeof MessageService !== 'undefined') MessageService.cerrar();
            console.error("[Inventarios] Error Crítico en guardarProducto:", error);
            MessageService.mostrar(3, "Error de Ejecución", "Se produjo un fallo inesperado en la lógica del módulo.", error.stack);
        }
    },

    // [SECTION 5] Funciones de Interfaz y Renderizado Dinámico

    renderTabla: function (data) {
        const tbody = document.getElementById('inventarioTableBody');
        tbody.innerHTML = '';

        if (!data || data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; padding: 40px; color: #9CA3AF;">No se encontraron productos coincidentes.</td></tr>';
            return;
        }

        data.forEach(item => {
            const row = document.createElement('tr');

            // Selección Persistente (Estilo Clientes)
            row.onclick = (e) => {
                const allRows = document.querySelectorAll('#inventarioTableBody tr');
                allRows.forEach(r => r.classList.remove('selected-row'));
                row.classList.add('selected-row');
                this.cargarDetalle(item);
            };

            // Lógica de semáforo de stock (Biblia 2.6)
            let stockClass = 'status-ok';
            let stockText = 'SUFICIENTE';
            const exist = item.existenciaActual || 0;
            if (exist <= 0) {
                stockClass = 'status-empty';
                stockText = 'AGOTADO';
            } else if (exist < (item.stockMinimo || 0)) {
                stockClass = 'status-low';
                stockText = 'BAJO STOCK';
            }

            // Lógica de Estatus Institucional (Punto Verde/Rojo)
            const estatusRaw = item.estatus || 'ACTIVO';
            const isActivo = estatusRaw === 'ACTIVO';
            const statusClass = isActivo ? 'activo' : 'inactivo';
            const statusText = isActivo ? 'Activo' : 'Inactivo';

            row.innerHTML = `
                <td class="col-sku">
                    <span style="font-size: 0.72rem; color: #64748b; font-weight: 500;">${item.sku || 'N/A'}</span>
                </td>
                <td class="col-name">
                    <div style="display:flex; flex-direction:column; line-height:1.2;">
                        <span style="font-weight:600; color:#111827;">${item.nombre}</span>
                        <span style="font-size:0.75rem; color:#6B7280;">${item.nombreMarca || 'S/M'} | ${item.nombreGrupo || 'S/G'}</span>
                    </div>
                </td>
                <td class="col-exist" style="font-weight:700; text-align: center;">${exist}</td>
                <td class="col-status">
                    <span class="status-badge ${stockClass}">
                        ${stockText}
                    </span>
                </td>
                <td class="col-estatus">
                    <span class="status-indicator ${statusClass}"></span> ${statusText}
                </td>
                <td class="col-actions">
                    <div style="display:flex; gap:12px; align-items:center; justify-content:center;">
                        <span class="material-symbols-outlined" 
                              style="font-size: 1.2rem; color: #2563eb; cursor: pointer;" 
                              title="Modificar ficha técnica"
                              onclick="event.stopPropagation(); InventariosService.cargarDetalle(${JSON.stringify(item).replace(/"/g, '&quot;')})">edit</span>
                    </div>
                </td>
            `;
            tbody.appendChild(row);
        });
    },

    renderKardex: function (movs) {
        const tbody = document.getElementById('kardexTableBody');
        tbody.innerHTML = '';

        if (movs.length === 0) {
            this.ultimaFechaKardex = null;
            tbody.innerHTML = '<tr><td colspan="6" style="text-align:center">Sin movimientos registrados.</td></tr>';
            return;
        }

        this.ultimaFechaKardex = movs[0].fecha;

        // [ESTRATEGIA DE SALDOS DINÁMICOS]
        // Para que el Kardex "haga sentido" con el Maestro de Productos, calculamos el rastro
        // de saldos partiendo de la existencia actual del maestro hacia atrás.
        let saldoRastreado = this.productoSeleccionado.existenciaActual || 0;

        movs.forEach(m => {
            const fecha = new Date(m.fecha).toLocaleString('es-MX', {
                day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', hour12: true
            });

            // Lógica semántica de colores (Suma verde, Resta rojo)
            const isSuma = parseInt(m.cantidad) > 0;
            const tipoClass = isSuma ? 'txt-success' : 'txt-danger';

            // [REESTRUCTURACIÓN SOLICITUD 49/50 - Humanización y Limpieza]
            const idMovInstitucional = m.folio || 'S/FOLIO';
            const documentoUsuario = m.origenId || 'S/DOC';

            // Eliminamos la redundancia del tipo en la descripción (ya está en la columna Afectación)
            const conceptoDetalle = (m.observaciones || 'SIN OBSERVACIONES').toUpperCase();

            // Formateo de Afectación explícita (Referencia operativa)
            const indicadorFlujo = isSuma ? '[+] ENTRADA' : '[-] SALIDA';

            // Guardamos el saldo actual de la fila antes de retroceder
            const saldoFila = saldoRastreado;

            // Retrocedemos el saldo para la siguiente fila (inversa del movimiento)
            saldoRastreado -= m.cantidad;

            const row = `<tr>
                <td style="font-size: 0.8rem; color: #4b5563; vertical-align: middle;">${fecha}</td>
                <td style="vertical-align: middle;">
                    <div style="line-height: 1.4;">
                        <span class="kardex-id">${idMovInstitucional}</span>
                        <span class="kardex-folio">${documentoUsuario}</span>
                        <span class="kardex-concepto">${conceptoDetalle}</span>
                    </div>
                </td>
                <td style="vertical-align: middle;">
                    <span class="badge ${tipoClass}" style="font-size: 0.75rem; letter-spacing: 0.5px;">
                        ${indicadorFlujo}
                    </span>
                    <div style="font-size: 0.65rem; color: #6b7280; margin-top: 2px;">
                        ${(m.tipoMovimiento || '').replace(/_/g, ' ')}
                    </div>
                </td>
                <td class="${tipoClass}" style="font-weight: 700; text-align: center; vertical-align: middle; font-size: 0.95rem;">
                    ${m.cantidad}
                </td>
                <td style="font-weight: 700; text-align: center; vertical-align: middle; font-size: 0.95rem; color: #2563eb; background: #f0f9ff;">
                    ${saldoFila}
                </td>
                <td style="vertical-align: middle;">
                    <div style="font-size: 0.8rem; color: #111827; font-weight: 600;">${m.nombreUsuario || 'S/A'}</div>
                </td>
                <td style="vertical-align: middle; text-align: center;">
                    <div style="display: flex; gap: 8px; justify-content: center;">
                        <span class="material-symbols-outlined" 
                              style="font-size: 1.1rem; color: #2563eb; cursor: pointer;" 
                              title="Editar" 
                              onclick="InventariosService.editarMovimiento('${m.idMovimiento}')">edit</span>
                        <span class="material-symbols-outlined" 
                              style="font-size: 1.1rem; color: #dc2626; cursor: pointer;" 
                              title="Eliminar" 
                              onclick="InventariosService.eliminarMovimiento('${m.idMovimiento}')">delete</span>
                    </div>
                </td>
            </tr>`;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    },

    fetchKardex: async function (idProducto) {
        try {
            const response = await fetch(`${this.endpoints.productos}/${idProducto}/kardex`);
            if (response.ok) {
                const data = await response.json();
                this.renderKardex(data);
            } else {
                console.error("[API] Error al obtener Kardex:", response.status);
            }
        } catch (error) {
            console.error("[API] Fallo técnico al obtener Kardex:", error);
        }
    },

    cargarDetalle: async function (p) {
        this.productoSeleccionado = p;

        // Mapeo sincronizado con los nuevos IDs del HTML (La Biblia v2)
        const ifield = (id) => document.getElementById(id);

        if (ifield('idProducto')) ifield('idProducto').value = p.sku || '';
        if (ifield('nombreProducto')) ifield('nombreProducto').value = p.nombre || '';
        if (ifield('codigoBarras')) ifield('codigoBarras').value = p.codigoBarras || '';
        if (ifield('idMarca')) ifield('idMarca').value = p.idMarca || '';
        if (ifield('idGrupo')) ifield('idGrupo').value = p.idGrupo || '';

        // Cargar familias dependientes y seleccionar la correcta
        if (p.idGrupo) {
            await this.cargarFamiliasDependientes(p.idGrupo, p.idFamilia);
        }

        if (ifield('costoUnitario')) ifield('costoUnitario').value = p.costoUnitario || 0;
        if (ifield('porcentajeUtilidad')) ifield('porcentajeUtilidad').value = p.porcentajeUtilidad || 0;
        if (ifield('stockMinimo')) ifield('stockMinimo').value = p.stockMinimo || 0;
        if (ifield('existenciaActual')) ifield('existenciaActual').value = p.existenciaActual || 0;

        // Limpiar campos fiscales si están vacíos
        if (ifield('claveProdServ')) ifield('claveProdServ').value = p.claveProdServ || '';
        if (ifield('claveUnidad')) ifield('claveUnidad').value = p.claveUnidad || '';
        if (ifield('objetoImpuesto')) ifield('objetoImpuesto').value = p.objetoImpuesto || '02';
        if (ifield('ivaAplicable')) ifield('ivaAplicable').value = p.ivaAplicable || 16.00;
        if (ifield('estatus')) ifield('estatus').value = p.estatus || 'ACTIVO';

        // [ACTUALIZACIÓN HEADER PREMIUM]
        const infoBox = document.getElementById('info-box-premium');
        if (infoBox) {
            infoBox.style.display = 'block';
            document.getElementById('header_sku').textContent = p.sku || 'S/SKU';
            document.getElementById('header_nombre').textContent = p.nombre || 'SIN NOMBRE';

            const fmt = (d) => d ? new Date(d).toLocaleString('es-MX', {
                day: '2-digit', month: 'short', year: 'numeric',
                hour: '2-digit', minute: '2-digit', hour12: true
            }).toLowerCase().replace('.', '') : '-';

            document.getElementById('header_f_creacion').textContent = fmt(p.fechaCreacion);
            document.getElementById('header_f_mod').textContent = fmt(p.fechaModificacion);
        }

        // [CÓDIGO DE BARRAS REAL] Renderizar barcode desde el atributo codigoBarras del producto
        this.renderBarcode(p.codigoBarras);

        // Actualizar etiquetas descriptivas
        this.actualizarDescripcionesFiscales();

        // Recalcular precio de venta visualmente
        this.calcularPrecioVenta();

        // Control de Tabs: Si la pestaña actual es Kardex, cargar movimientos; si no, ir a Datos
        const activeTab = document.querySelector('.tab-btn.active');
        if (activeTab && activeTab.getAttribute('data-tab') === 'kardex') {
            this.fetchKardex(p.idProducto);
        } else {
            // Homologación: Al tocar un nuevo registro, usualmente volvemos a la ficha técnica
            const tabBtnDatos = document.querySelector('.tab-btn[data-tab="datos"]');
            if (tabBtnDatos) this.switchTab('datos', tabBtnDatos);
        }
    },

    // [SECTION 6] Utilidades

    /**
     * Renderiza un código de barras real utilizando JsBarcode.
     * Toma el valor del campo codigoBarras del producto y genera un SVG.
     * Si no hay código, muestra el indicador de fallback (SIN CÓDIGO).
     * @param {string|null} codigo - Valor del código de barras del producto
     */
    renderBarcode: function (codigo) {
        const svgEl = document.getElementById('barcodeReal');
        const fallbackEl = document.getElementById('barcodeFallback');

        if (!svgEl || !fallbackEl) return;

        if (codigo && codigo.trim() !== '') {
            try {
                JsBarcode(svgEl, codigo.trim(), {
                    format: 'CODE128',
                    width: 1.5,
                    height: 40,
                    displayValue: true,
                    fontSize: 10,
                    fontOptions: '600',
                    font: 'Inter, sans-serif',
                    textMargin: 2,
                    margin: 0,
                    lineColor: '#1F3A5F',
                    background: 'transparent'
                });
                svgEl.style.display = 'block';
                fallbackEl.style.display = 'none';
            } catch (e) {
                console.warn('[Barcode] Error al generar código de barras:', e.message);
                svgEl.style.display = 'none';
                fallbackEl.style.display = 'flex';
            }
        } else {
            svgEl.style.display = 'none';
            fallbackEl.style.display = 'flex';
        }
    },

    filtrarProductos: function () {
        const busqueda = document.getElementById('searchInput').value.toLowerCase();
        const grupo = document.getElementById('filterGrupo').value;

        // Capturar filtros de los dos Toggles segmentados
        const activeUmbralBtn = document.querySelector('#toggleUmbral .status-btn.active');
        const umbral = activeUmbralBtn ? activeUmbralBtn.getAttribute('data-filter') : '';

        const activeEstatusBtn = document.querySelector('#toggleEstatus .status-btn.active');
        const filterEstatus = activeEstatusBtn ? activeEstatusBtn.getAttribute('data-filter') : '';

        const filtrados = this.productos.filter(p => {
            const matchBusqueda = p.nombre.toLowerCase().includes(busqueda) || (p.sku && p.sku.toLowerCase().includes(busqueda));
            const matchGrupo = !grupo || p.idGrupo === grupo;

            let matchUmbral = true;
            if (umbral === 'OK') matchUmbral = (p.existenciaActual || 0) >= (p.stockMinimo || 0);
            if (umbral === 'BAJO') matchUmbral = (p.existenciaActual || 0) < (p.stockMinimo || 0);

            // Nuevo filtro por estatus operativo (ACTIVO/INACTIVO)
            const matchEstatus = !filterEstatus || p.estatus === filterEstatus;

            return matchBusqueda && matchGrupo && matchUmbral && matchEstatus;
        });

        this.renderTabla(filtrados);
    },

    llenarSelect: function (idSelect, data, valueKey, textKey, defaultText) {
        const select = document.getElementById(idSelect);
        if (!select) return;
        select.innerHTML = `<option value="">${defaultText}</option>`;
        data.forEach(item => {
            const opt = document.createElement('option');
            opt.value = item[valueKey];
            opt.textContent = item[textKey];
            select.appendChild(opt);
        });
    },

    calcularPrecioVenta: function () {
        const costo = parseFloat(document.getElementById('costoUnitario').value) || 0;
        const utilidad = parseFloat(document.getElementById('porcentajeUtilidad').value) || 0;
        const precio = costo * (1 + (utilidad / 100));

        document.getElementById('precioVenta').value = precio.toLocaleString('es-MX', {
            style: 'currency', currency: 'MXN'
        });
    },

    switchTab: function (tabId, btnElement) {
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        btnElement.classList.add('active');
        document.getElementById(`tab-${tabId}`).classList.add('active');
    },

    limpiarFormulario: function () {
        this.productoSeleccionado = null;
        document.getElementById('formProducto').reset();

        // Ocultar Header Premium en modo "Nuevo"
        const infoBox = document.getElementById('info-box-premium');
        if (infoBox) infoBox.style.display = 'none';
        document.getElementById('idFamilia').innerHTML = '<option value="">Seleccione Grupo primero...</option>';
        if (document.getElementById('estatus')) document.getElementById('estatus').value = 'ACTIVO';
        if (document.getElementById('codigoBarras')) document.getElementById('codigoBarras').value = '';
        this.actualizarDescripcionesFiscales();
        this.calcularPrecioVenta();

        // [BARCODE] Resetear a fallback
        this.renderBarcode(null);
    },

    abrirModalMovimiento: function () {
        if (!this.productoSeleccionado) {
            MessageService.mostrar(9, "Selección Requerida", "Debe seleccionar un producto para registrar movimientos.");
            return;
        }

        const user = JSON.parse(sessionStorage.getItem('user') || '{}');
        this.movimientoOriginal = null;

        document.getElementById('modalMovTitulo').textContent = "Nuevo Movimiento de Kardex";
        document.getElementById('formMovimiento').reset();
        document.getElementById('mov_idMovimiento').value = "";

        // Indicadores Base
        document.getElementById('mov_sku_label').textContent = this.productoSeleccionado.sku || 'S/SKU';
        document.getElementById('mov_producto_label').textContent = this.productoSeleccionado.nombre;
        document.getElementById('mov_exist_actual').textContent = this.productoSeleccionado.existenciaActual || 0;
        document.getElementById('mov_costo').value = this.productoSeleccionado.costoUnitario || 0;
        document.getElementById('mov_tipo').value = "AJUSTE";
        document.getElementById('mov_cantidad').value = 1;
        // [INTERFAZ NUEVO] Ocultar fechas (ruido visual en nuevo registro)
        document.getElementById('row_fechas_kardex').style.display = 'none';
        this.detenerRelojModal();

        this.recalcularIndicadores();
        document.getElementById('movimientoModal').classList.add('open');
    },

    iniciarRelojModal: function () {
        this.detenerRelojModal();
        const update = () => {
            const now = new Date();
            const el = document.getElementById('mov_fecha_live');
            if (el) el.textContent = now.toLocaleString('es-MX', {
                day: '2-digit', month: '2-digit', year: 'numeric',
                hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: true
            });
        };
        update();
        this.timerFechaModal = setInterval(update, 1000);
    },

    detenerRelojModal: function () {
        if (this.timerFechaModal) {
            clearInterval(this.timerFechaModal);
            this.timerFechaModal = null;
        }
    },

    recalcularIndicadores: function () {
        const actual = parseInt(document.getElementById('mov_exist_actual').textContent) || 0;
        const cant = parseInt(document.getElementById('mov_cantidad').value) || 0;
        const tipo = document.getElementById('mov_tipo').value;

        // Factores de afectación (Homo backend)
        const getFactor = (t) => {
            const salidas = ['SALIDA_VENTA', 'DEVOLUCION_PROVEEDOR', 'MERMA', 'TRASPASO'];
            return salidas.includes(t) ? -1 : 1;
        };

        let saldoBase = actual;

        // [LÓGICA DELTA] Si estamos editando, neutralizar impacto anterior para evitar duplicidad
        if (this.movimientoOriginal) {
            const factorOrig = getFactor(this.movimientoOriginal.tipoMovimiento);
            const cantOrig = this.movimientoOriginal.cantidad;
            saldoBase = actual - (cantOrig * factorOrig);
        }

        const nuevo = saldoBase + (cant * getFactor(tipo));
        document.getElementById('mov_nuevo_saldo').textContent = nuevo;

        const saldoEl = document.getElementById('mov_nuevo_saldo');
        saldoEl.style.color = nuevo < 0 ? '#ef4444' : '#2563eb';
    },

    cerrarMovimientoModal: function () {
        this.movimientoOriginal = null;
        this.detenerRelojModal();
        document.getElementById('movimientoModal').classList.remove('open');
    },

    editarMovimiento: async function (id) {
        try {
            MessageService.procesando("Cargando datos de la póliza...");
            const response = await fetch(`${this.endpoints.movimientos}/${id}`);
            MessageService.cerrar();

            if (response.ok) {
                const m = await response.json();
                this.movimientoOriginal = m;

                const user = JSON.parse(sessionStorage.getItem('user') || '{}');

                document.getElementById('modalMovTitulo').textContent = "Modificación de Movimiento de Kardex";
                document.getElementById('mov_idMovimiento').value = m.idMovimiento;

                document.getElementById('mov_sku_label').textContent = this.productoSeleccionado.sku || 'S/SKU';
                document.getElementById('mov_producto_label').textContent = this.productoSeleccionado.nombre;
                document.getElementById('mov_exist_actual').textContent = this.productoSeleccionado.existenciaActual || 0;

                document.getElementById('mov_tipo').value = m.tipoMovimiento;
                document.getElementById('mov_cantidad').value = m.cantidad;
                document.getElementById('mov_costo').value = m.costoHistorico;
                document.getElementById('mov_folio').value = m.origenId || "";
                document.getElementById('mov_obs').value = m.observaciones || "";

                // [INTERFAZ EDICIÓN] Mostrar fila de fechas para auditoría completa
                document.getElementById('row_fechas_kardex').style.display = 'grid';

                document.getElementById('lbl_fecha_ref').textContent = "FECHA ORIGINAL DE REGISTRO";
                document.getElementById('mov_fecha_ref').textContent = new Date(m.fecha).toLocaleString('es-MX', {
                    day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', hour12: true
                });

                this.iniciarRelojModal();

                this.recalcularIndicadores();
                document.getElementById('movimientoModal').classList.add('open');
            }
        } catch (error) {
            MessageService.cerrar();
            console.error("[API] Error al cargar movimiento:", error);
        }
    },

    eliminarMovimiento: function (id) {
        MessageService.mostrar(2, "Confirmar Reversión",
            "¿Desea eliminar esta póliza y revertir su impacto en el stock?",
            "Esta acción es permanente y recalculará la existencia actual del producto.",
            "", () => this.ejecutarEliminacionMovimiento(id));
    },

    ejecutarEliminacionMovimiento: async function (id) {
        try {
            MessageService.procesando("Revirtiendo movimiento...");
            const response = await fetch(`${this.endpoints.movimientos}/${id}`, {
                method: 'DELETE'
            });
            MessageService.cerrar();

            if (response.ok) {
                MessageService.mostrar(4, "Movimiento Revertido", "La póliza fue eliminada y el stock se ha recalculado correctamente.");
                await this.fetchProductos();
                if (this.productoSeleccionado) {
                    // Sincronizar objeto seleccionado con la lista fresca
                    const fresh = this.productos.find(p => p.idProducto === this.productoSeleccionado.idProducto);
                    if (fresh) this.productoSeleccionado = fresh;
                    this.fetchKardex(this.productoSeleccionado.idProducto);
                }
            } else {
                const msg = await response.text();
                MessageService.mostrar(3, "Error de Reversión", "No se pudo eliminar el movimiento.", msg);
            }
        } catch (error) {
            MessageService.cerrar();
            MessageService.mostrar(3, "Fallo técnico", "Error de red al intentar revertir el movimiento.");
        }
    },


    ejecutarGuardarMovimiento: async function () {
        const idMov = document.getElementById('mov_idMovimiento').value;
        const user = JSON.parse(sessionStorage.getItem('user') || '{}');

        // Mapeo robusto de ID de Usuario (userId es el estándar del AuthController)
        const idUsuarioActual = user.userId || user.idUsuario || user.id;

        // Mapeo robusto de Sucursal
        const idSucursal = user.idSucursal || sessionStorage.getItem('id_sucursal_actual');

        if (!idUsuarioActual) {
            MessageService.mostrar(3, "Sesión Inválida", "No se pudo identificar al usuario operador. Por favor, reingrese al sistema.");
            return;
        }

        if (!idSucursal) {
            MessageService.mostrar(1, "Error de Almacén", "No se detectó una sucursal activa para la operación.");
            return;
        }

        const mov = {
            idProducto: this.productoSeleccionado.idProducto,
            idSucursal: idSucursal,
            tipoMovimiento: document.getElementById('mov_tipo').value,
            folio: document.getElementById('mov_folio').value,
            cantidad: parseInt(document.getElementById('mov_cantidad').value),
            costoHistorico: parseFloat(document.getElementById('mov_costo').value || 0),
            idUsuario: idUsuarioActual,
            observaciones: document.getElementById('mov_obs').value
        };

        const isEdit = idMov !== "";
        const url = isEdit ? `${this.endpoints.movimientos}/${idMov}` : this.endpoints.movimientos;
        const method = isEdit ? 'PUT' : 'POST';

        try {
            MessageService.procesando("Guardando póliza...");
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(mov)
            });
            MessageService.cerrar();

            if (response.ok) {
                MessageService.mostrar(4, isEdit ? "Póliza Actualizada" : "Movimiento Registrado",
                    "El Kardex ha sido actualizado satisfactoriamente.");
                this.cerrarMovimientoModal();
                await this.fetchProductos(); // Actualizar indicadores en el catálogo maestro

                // [SINCRONIZACIÓN CRÍTICA]
                // Actualizamos el objeto seleccionado con los nuevos saldos para que el Kardex
                // renderice el rastro matemático correcto partiendo de la nueva verdad.
                if (this.productoSeleccionado) {
                    const fresh = this.productos.find(p => p.idProducto === this.productoSeleccionado.idProducto);
                    if (fresh) {
                        this.productoSeleccionado = fresh;
                        // Actualizamos la ficha técnica (pestaña Datos)
                        document.getElementById('existenciaActual').value = fresh.existenciaActual || 0;
                    }
                    this.fetchKardex(this.productoSeleccionado.idProducto);
                }
            } else {
                const msg = await response.text();
                MessageService.mostrar(3, "Error en Transacción", "El servidor rechazó el movimiento.", msg);
            }
        } catch (error) {
            MessageService.cerrar();
            MessageService.mostrar(3, "Fallo de comunicación", "No se pudo conectar con el servicio de inventarios.");
        }
    },

    // [BITÁCORA / AUDITORÍA]
    verBitacora: function () {
        document.getElementById('bitacoraModal').classList.add('open');
        const hoy = new Date().toISOString().split('T')[0];
        document.getElementById('bitacoraDesde').value = hoy;
        document.getElementById('bitacoraHasta').value = hoy;

        // [CARGA DINÁMICA V5.2] Poblar select de usuarios
        this.cargarUsuariosBitacora();

        this.cargarBitacora();
    },

    cargarUsuariosBitacora: async function () {
        try {
            const response = await fetch(AppConfig.getEndpoint('usuarios'));
            if (response.ok) {
                const usuarios = await response.json();
                const select = document.getElementById('bitacoraUsuario');
                const valAnt = select.value;
                select.innerHTML = '<option value="">Todos los usuarios</option>';
                usuarios.forEach(u => {
                    const opt = document.createElement('option');
                    opt.value = u.id; // El modelo Usuario.java usa 'id' como el campo principal
                    opt.textContent = u.nombre;
                    select.appendChild(opt);
                });
                select.value = valAnt;
            }
        } catch (e) { console.error("Error cargando usuarios:", e); }
    },

    cerrarBitacoraModal: function () {
        document.getElementById('bitacoraModal').classList.remove('open');
    },

    cargarBitacora: async function () {
        const tbody = document.getElementById('bitacoraTableBody');
        tbody.innerHTML = '<tr><td colspan="5" class="txt-center">Consultando registros...</td></tr>';

        try {
            const d = document.getElementById('bitacoraDesde').value;
            const h = document.getElementById('bitacoraHasta').value;
            const b = document.getElementById('bitacoraBuscar').value;
            const u = document.getElementById('bitacoraUsuario').value;

            const params = new URLSearchParams({
                desde: d, hasta: h, buscar: b, usuario: u
            });

            const response = await fetch(`${AppConfig.getEndpoint('bitacora')}?${params}`);
            if (response.ok) {
                const logs = await response.json();
                this.renderTablaBitacora(logs);
            }
        } catch (error) {
            console.error("Error bitácora:", error);
        }
    },

    renderTablaBitacora: function (logs) {
        const tbody = document.getElementById('bitacoraTableBody');
        tbody.innerHTML = '';
        if (logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="txt-center">No se encontraron eventos.</td></tr>';
            return;
        }

        logs.forEach(log => {
            const fecha = new Date(log.fecha).toLocaleString('es-MX', {
                day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', hour12: true
            });

            // Colores por impacto (v5.2)
            let colorStr = '#4b5563'; // Por defecto
            if (log.accion.startsWith('CORRECTO')) colorStr = '#16a34a';
            if (log.accion.startsWith('AVISO')) colorStr = '#f59e0b';
            if (log.accion.startsWith('ALERTA') || log.accion.startsWith('ERROR')) colorStr = '#dc2626';

            // Formatear detalles con saltos de línea (Audit Forense v5.2)
            const detalleLimpio = (log.detalles || 'Sin detalles').replace(/\n/g, '<br>');

            const row = `<tr>
                <td style="font-size: 0.8rem; color: #64748b;">${fecha}</td>
                <td><strong style="color: #1e293b;">${log.nombreUsuario || 'S/U'}</strong></td>
                <td><span style="font-weight: 700; color: ${colorStr}">${log.accion}</span></td>
                <td style="font-size: 0.8rem; line-height: 1.4; color: #475569;">${detalleLimpio}</td>
                <td style="font-size: 0.75rem; color: #94a3b8;">${log.ipOrigen || '0.0.0.0'}</td>
            </tr>`;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    },



    // [SECTION 7] Gestión de Identidad y Monitor de Sesión (Biblia 2.1)
    renderUserInfo: function () {
        const userStr = sessionStorage.getItem('user');
        if (userStr) {
            const user = JSON.parse(userStr);
            const nameEl = document.getElementById('userName');
            const roleEl = document.getElementById('userRole');
            const loginTimeEl = document.getElementById('loginTime');

            if (nameEl) nameEl.textContent = user.nombre || 'Usuario';
            if (roleEl) roleEl.textContent = (user.nombreRol || user.rol || 'ROL').toUpperCase();

            const loginTs = parseInt(sessionStorage.getItem('loginTimestamp')) || new Date().getTime();
            if (loginTimeEl) loginTimeEl.textContent = new Date(loginTs).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

            this.loginTs = loginTs;
        }
    },

    startClocks: function () {
        const clockEl = document.getElementById('currentClock');
        const timerEl = document.getElementById('sessionTimer');
        const dateEl = document.getElementById('currentDate');

        if (dateEl) dateEl.textContent = new Date().toLocaleDateString();

        setInterval(() => {
            const now = new Date();
            if (clockEl) clockEl.textContent = now.toLocaleTimeString();

            if (timerEl && this.loginTs) {
                const diff = now.getTime() - this.loginTs;
                const hours = Math.floor(diff / 3600000);
                const minutes = Math.floor((diff % 3600000) / 60000);
                const seconds = Math.floor((diff % 60000) / 1000);
                timerEl.textContent = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            }
        }, 1000);
    }
};

// Inyectar CSS dinámico para utilidades de texto si no existen
if (!document.getElementById('inv-util-styles')) {
    const style = document.createElement('style');
    style.id = 'inv-util-styles';
    style.innerHTML = `
        .txt-success { color: #16a34a; font-weight: bold; }
        .txt-danger { color: #dc2626; font-weight: bold; }
        .kardex-table-area { max-height: 400px; overflow-y: auto; }
    `;
    document.head.appendChild(style);
}
