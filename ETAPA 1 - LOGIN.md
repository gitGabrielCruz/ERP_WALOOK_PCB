---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 1 - Módulo Login  
**VERSIÓN:** 1.1 (Sincronizada con Código v3.2)  
**FECHA:** 22 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 🔐 ETAPA 1 - MÓDULO LOGIN

## 1.1 INCISO 1.1 — LOGIN (Login.svg)

**Pantalla:** Login / Acceso al sistema  
**Etapa:** 1  
**RF que cubre:** RF-01 Autenticación, RF-05 Comunicación API, RF-06 Persistencia básica  
**RNF asociados:** RNF-02 Seguridad básica, RNF-03 Integración frontend–backend, RNF-04 Consistencia visual  

---

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales (Historias de Usuario)

| ID HU | Descripción |
|-------|-------------|
| **HU-M01-01** | Iniciar sesión en el sistema |
| **HU-M01-02** | Recuperar contraseña |
| **HU-M01-03** | Cerrar sesión |
| **HU-M01-04** | Acceder a la navegación inicial según rol |

#### 1.2 Requerimientos no funcionales reales

- Control de acceso al sistema
- Integridad y confidencialidad de credenciales
- Persistencia de sesión
- Integración Frontend–Backend
- Consistencia visual inicial

#### 1.3 Casos de uso reales asociados

- **CU-01:** Iniciar sesión
- **CU-02:** Recuperar contraseña
- **CU-03:** Cerrar sesión
- **CU-04:** Acceso a menú inicial según rol

#### 1.4 Diagramas que aplican

- Diagrama de Navegabilidad — acceso y menú inicial
- Diagrama Relacional — módulo Usuarios / Acceso
- Diagrama de Clases — Usuario, Rol, Permiso

#### 1.5 Datos literales del diseño

**Base de datos:** `db_omcgc_erp`

**Tablas involucradas:**
- `usuario`
- `usuario_rol`
- `rol`

**Campos relevantes (usuario):**
- `id_usuario`
- `email`
- `password_hash`
- `id_sucursal` (UUID - RFC-01: Gestión de origen)
- `activo`

#### 1.6 Estándares Visuales (Login - Patrón WALOOK)

Aunque es una pantalla única (Single Page), debe cumplir:
*   **Centrado:** Formulario en tarjeta blanca centrada (`card-login`) sobre fondo neutro.
*   **Inputs:** Estilo `input-base` (bordes grises suaves, focus azul).
*   **Acción Principal:** Botón "Iniciar Sesión" con estilo `btn-primary` (Azul `#2563EB` o `#3B82F6`), ancho completo (block).
*   **Feedback:** Mensajes de error en rojo (`alert-error`) claros y visibles.


---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO POR ETAPAS

#### 2.1 Pertenencia

Login pertenece a **Etapa 1** ✅ CONFIRMADO

#### 2.2 RF que se cierran en 1.1

- RF-01: Autenticación
- RF-05: Comunicación API
- RF-06: Persistencia básica

#### 2.3 Qué NO se puede tocar

**Reservado a Etapa 5:**
- Auditoría
- Bitácoras
- Seguridad avanzada
- Control normativo

---

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL DE Login.svg (UI → LÓGICA → DATOS)

#### Elementos de la interfaz

| Tipo | Elemento |
|------|----------|
| **Campo** | Usuario / Email |
| **Campo** | Contraseña |
| **Botón** | Iniciar sesión |
| **Enlace** | ¿Olvidaste tu contraseña? |
| **Acción** | Cerrar sesión |

#### Trazabilidad técnica por elemento

**Campo Usuario / Email:**
- Captura correo electrónico
- Validación de formato
- Validación de existencia en tabla `usuario` (campo `email`)

**Campo Contraseña:**
- Captura contraseña
- Validación de comparación de hash
- Tabla `usuario` (campo `password_hash`)

**Botón Iniciar sesión:**
- Acción de validar credenciales
- Tablas: `usuario`, `usuario_rol`, `rol`
- Resultado: crear sesión activa, asociar rol y redirigir al menú

---

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 1.1

#### Reglas cerradas

1. Solo usuarios con `usuario.activo = true` pueden iniciar sesión
2. El email es identificador único y la contraseña se valida por hash
3. La recuperación de contraseña solo aplica a usuarios activos
4. La recuperación reemplaza `password_hash` y no altera rol ni estatus

#### Flujos if/then

```pseudocode
IF credenciales válidas AND usuario.activo = true THEN
    Crear sesión
    Cargar rol
    Redirigir a menú
ELSE
    Mostrar error "Credenciales inválidas"
ENDIF
```

#### Validaciones específicas

**Frontend:**
- Formato de email válido
- Contraseña no vacía
- Longitud mínima de contraseña: 8 caracteres

**Backend:**
- Email existe en BD
- Hash de contraseña coincide
- Usuario está activo
- Generación de token de sesión

#### Endpoints API REST

```
POST   /api/auth/login          # Iniciar sesión
POST   /api/auth/logout         # Cerrar sesión
POST   /api/usuarios/{id}/reset-password  # Restablecer contraseña y enviar correo
GET    /api/auth/session        # Validar sesión activa
```

#### Criterios de Aceptación

- [x] El usuario puede iniciar sesión con email y contraseña
- [x] El sistema valida la sucursal del usuario (idSucursal)
- [x] El sistema utiliza `MessageService` para feedback dinámico (9 tipos)
- [x] El sistema valida que el usuario esté activo
- [x] El sistema crea una sesión persistente
- [x] El sistema redirige al menú principal según el rol
- [x] El usuario puede cerrar sesión
- [x] El usuario puede recuperar su contraseña
- [x] Las credenciales inválidas muestran mensaje de error modal
- [x] La contraseña se almacena como hash (nunca en texto plano)

---

**Última actualización:** 22 de febrero de 2026, 02:55 PM  
**Estado:** Sincronizado con v3.2 ✅  
**Código fuente:** Ver `ETAPA 1 - CODIGO_FUENTE.md`
