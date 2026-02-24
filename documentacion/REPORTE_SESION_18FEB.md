# 📊 REPORTE DE SESIÓN TÉCNICA - ERP WALOOK (OMCGC)
**Fecha:** 18 Febrero 2026
**Hora de Corte:** 10:00 AM
**Objetivo:** Restauración de Inventarios (Kardex) y Despliegue en Producción (VPS)

---

## ✅ LOGROS PRINCIPALES (SUCCESS)

### 1. **Corrección de Lógica de Inventarios (Kardex Fix)**
- **Problema:** Errores `Data Truncated` (ENUM) y `Foreign Key Constraint` (Sucursal) impedían guardar movimientos manuales.
- **Solución:**
    - Se estandarizó el uso de `TipoMovimiento.AJUSTE` en Frontend y Backend.
    - Se implementó la gestión dinámica de **Sucursales** (`Sucursal.java`, `CatalogoController.java`).
    - El Frontend ahora obtiene el catálogo de sucursales y usa el ID real de la base de datos, evitando IDs harcodeados (`SUC-001`).

### 2. **Restauración Integral de Base de Datos (Producción)**
- **Acción:** Se ejecutaron scripts SQL completos (`creaciontablas_nube.sql`, `registrospruebas_nube.sql`) en el servidor VPS.
- **Resultado:**
    - Base de datos `graxsof3_omcgc` **100% Sincronizada** con el código.
    - Usuario `graxsof3_omcgc` recreado con permisos correctos y contraseña segura.

### 3. **Despliegue Exitoso (VPS Backend)**
- **Acción:** Se compiló y subió el artefacto `omcgc-erp-backend.jar` (v1.2).
- **Configuración:**
    - Parche de seguridad **CORS/CSP** aplicado para permitir conexiones remotas.
    - Credenciales de base de datos (`DatabaseConfig.java`) alineadas con producción.
- **Estado:** Servicio `omcgc-erp` activo y respondiendo en puerto `9090`.

### 4. **Validación Operativa**
- **Prueba:** Login exitoso desde Frontend (`http://gabrielcruz.graxsoft.com`).
- **Condición:** Acceso vía **HTTP** (no HTTPS) para evitar bloqueo de Mixed Content.

---

## ⚠️ PENDIENTES MENORES (BACKLOG TÉCNICO)

1.  **SSL/HTTPS en Backend:**
    - Configurar certificado SSL en el VPS (puerto 9090) para permitir acceso seguro (`https://`) en el futuro. (No prioritario por ahora).
    
2.  **Módulo de Ventas:**
    - Validar que el descuento de inventario automático al vender funcione con la nueva lógica de Kardex.

---

## 🔐 CREDENCIALES DE PRODUCCIÓN (ACTUALIZADAS)

*   **Servidor VPS (SSH):**
    *   IP: `69.6.242.217`
    *   Puerto SSH: `22022`
    *   Usuario: `root`
*   **Base de Datos (MariaDB):**
    *   Usuario: `graxsof3_omcgc`
    *   Pass: `Walook_2026!SecureDB`
*   **Backend:**
    *   Puerto: `9090`
    *   URL Base: `http://69.6.242.217:9090/api`

---

**Estado del Sistema:** 🟢 **OPERATIVO Y ESTABLE.**
