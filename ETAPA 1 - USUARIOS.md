---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 1 - Módulo Usuarios, Roles y Permisos  
**VERSIÓN:** 1.0  
**FECHA:** 04 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 👥 ETAPA 1 - MÓDULO USUARIOS, ROLES Y PERMISOS

## 1.3 INCISO 1.3 — USUARIOS, ROLES Y PERMISOS (UsuariosRolesPermisos.svg)

**RF que cubre:** RF-02 Gestión de usuarios, RF-03 Roles y permisos, RF-06 Persistencia básica  

---

### 🔴 PASO 1 — EXTRACCIÓN LITERAL DEL DISEÑO

#### 1.1 Requerimientos funcionales reales

- Gestión completa de usuarios (CRUD)
- Asignación de roles a usuarios
- Configuración granular de permisos por módulo
- Control de acceso basado en roles (RBAC)

#### 1.2 Casos de uso asociados

- **CU-06:** Gestionar usuarios
- **CU-07:** Asignar roles
- **CU-08:** Configurar permisos

---

### 🔴 PASO 2 — CRUCE CON PLAN DE DESARROLLO

#### 2.1 Pertenencia

Usuarios, Roles y Permisos pertenece a **Etapa 1** ✅ CONFIRMADO

#### 2.2 Requerimientos de Seguridad Cerrados
- RF-02: Gestión de usuarios
- RF-03: Roles y permisos
- RF-07: Trazabilidad Forense (Bitácora)
- RF-08: Privacy Engine (Búsqueda sobre datos cifrados)
- RF-06: Persistencia básica

#### 2.3 Límites explícitos
**NO incluye en esta etapa:**
- Seguridad avanzada (2FA, biometría profunda)
- Inteligencia predictiva de amenazas

---

### 🔴 PASO 3 — DESCOMPOSICIÓN REAL

#### Elementos de la interfaz

**Listado de usuarios:**
- Columnas: Usuario (Username), Correo, Rol, Estatus
- Acciones: Ver, Editar, Eliminar

**Panel Detalle:**
- Campos para editar datos generales
- Selector de rol
- Matriz de permisos

**Matriz de Permisos:**
- **Filas:** 12 Módulos del sistema
- **Columnas:** 4 Acciones (Ver, Crear, Editar, Eliminar)
- **Persistencia:** Tabla `permiso`

#### 3.1 Estándares de Diseño e Interfaz (Patrón WALOOK)

Para asegurar consistencia visual y de comportamiento en todo el ERP, se definen las siguientes reglas obligatorias de UX/UI:

1.  **Tablas de Datos:**
    *   **Estilo:** "Cebra" (Striped) con filas inpares blancas y pares gris claro (`#f8fafc`).
    *   **Interacción:** Filas enteras clickeables (`tr:hover`) que abren el detalle.
    *   **Botón de Acción:** Botón rectangular pequeño con texto **"Ver"** (clase `btn-secondary`), evitando iconos aislados.
    *   **Indicador de Estatus:** Punto circular de color + Texto Capitalizado (Ej. `● Activo` en verde, `● Inactivo` en rojo).

2.  **Búsqueda y Filtros:**
    *   **Buscadores:** Comportamiento **Live Search** (evento `input`) que filtra en tiempo real sin necesidad de botón "Buscar".
    *   **Filtros de Estado:** Botones Toggle (Activo/Inactivo).

3.  **Formularios y Botonera:**
    *   **Ubicación:** Botones principales (Guardar, Cancelar, Nuevo) siempre en la esquina inferior derecha.
    *   **Iconografía:**
        *   Guardar: `save`
        *   Nuevo: `add_circle`
        *   Cancelar: Sin ícono (opcional)

4.  **Colores Semánticos en Tablas:**
    *   **Activo:** Texto normal + Punto Verde (`#10B981`).
    *   **Inactivo:** Texto normal + Punto Rojo (`#EF4444`). Estilo de badge/alerta solo para casos críticos.

#### Trazabilidad técnica

**Listado de usuarios:**
```
GET /api/usuarios
    ↓
Backend: UsuarioController.getAllUsuarios()
    ↓
UsuarioService.listar()
    ↓
UsuarioRepository.findAll()
    ↓
Retorna List<Usuario> con roles
    ↓
Frontend: renderizar tabla
```

**Guardar permisos:**
```
Usuario modifica matriz de permisos
    ↓
Click en "Guardar"
    ↓
POST /api/permisos/usuario/{id}
    ↓
Backend: PermisoController.updatePermisos()
    ↓
BEGIN TRANSACTION
    DELETE FROM permiso WHERE id_usuario = X
    INSERT INTO permiso (valores nuevos)
COMMIT TRANSACTION
    ↓
Frontend: MessageService.mostrar(4, "Exito", "Permisos actualizados")

**Gestión de Seguridad SMTP:**
```
Click en "Seguridad" (Reset Password)
    ↓
MessageService.mostrar(5, "¿Confirmar Reset?", "...") -> Captura Motivo
    ↓
MessageService.procesando("Enviando correo...")
    ↓
POST /api/usuarios/{id}/reset-password
    ↓
Backend: Genera Pwd -> Hashea -> Update DB -> EmailService.send(SMTP)
    ↓
MessageService.mostrar(4, "Seguridad Actualizada", "Clave: XXXX")
```
```

---

### 🔴 PASO 4 — CONTENIDO OPERATIVO DEL INCISO 1.3

#### Funcionalidades

1. **Alta de usuarios**
2. **Edición de usuarios**
3. **Asignación de roles**
4. **Configuración fina de permisos**

#### Reglas cerradas

1. Un usuario debe tener al menos un rol asignado
2. Los permisos se pueden asignar por rol O por usuario individual
3. Los permisos individuales tienen prioridad sobre los del rol
4. No se puede eliminar el último usuario ADMIN del sistema

#### Algoritmo de Resolución de Permisos

**Waterfall Permission Check:**

```pseudocode
FUNCION obtenerPermisos(idUsuario, idModulo):
    // Paso 1: Buscar permiso específico del usuario
    permiso = SELECT * FROM permiso 
              WHERE id_usuario = idUsuario 
              AND id_modulo = idModulo
    
    IF permiso encontrado THEN
        RETORNAR permiso
    ENDIF
    
    // Paso 2: Heredar permiso del rol
    idRol = SELECT id_rol FROM usuario_rol 
            WHERE id_usuario = idUsuario
    
    permiso = SELECT * FROM permiso 
              WHERE id_rol = idRol 
              AND id_modulo = idModulo
    
    IF permiso encontrado THEN
        RETORNAR permiso
    ENDIF
    
    // Paso 3: Denegar por defecto
    RETORNAR {puede_ver: false, puede_crear: false, 
              puede_editar: false, puede_eliminar: false}
FIN FUNCION
```

#### Validaciones específicas

**Frontend:**
- Email único
- Contraseña mínimo 8 caracteres
- Al menos un rol seleccionado
- Confirmación antes de eliminar usuario

**Backend:**
- Email no duplicado
- Hash de contraseña seguro (bcrypt)
- Rol existe en catálogo
- No eliminar último ADMIN

#### Endpoints API REST

```
GET    /api/usuarios                    # Listar todos los usuarios
GET    /api/usuarios/{id}               # Obtener usuario por ID
POST   /api/usuarios                    # Crear nuevo usuario
PUT    /api/usuarios/{id}               # Actualizar usuario
DELETE /api/usuarios/{id}               # Desactivar usuario

GET    /api/roles                       # Listar roles disponibles
GET    /api/modulos                     # Listar módulos del sistema

GET    /api/permisos/usuario/{id}       # Obtener permisos de usuario
POST   /api/permisos/usuario/{id}       # Actualizar permisos de usuario
GET    /api/permisos/rol/{id}           # Obtener permisos de rol
POST   /api/permisos/rol/{id}           # Actualizar permisos de rol
```

#### Modelo de Datos

**Tabla: `usuario`**
```sql
CREATE TABLE usuario (
    id_usuario      VARCHAR(50) PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    activo          BOOLEAN DEFAULT TRUE,
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Base de Datos:** `db_omcgc_erp`

**Tabla: `rol`**
```sql
CREATE TABLE rol (
    id_rol          BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(50) UNIQUE NOT NULL,  -- ADMIN, VENDEDOR, OPTOMETRISTA, CAJA, TALLER, ALMACEN
    descripcion     VARCHAR(200)
);
```

**Tabla: `usuario_rol`**
```sql
CREATE TABLE usuario_rol (
    id_usuario      VARCHAR(50),
    id_rol          BIGINT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);
```

**Tabla: `modulo`**
```sql
CREATE TABLE modulo (
    id_modulo       BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(100) UNIQUE NOT NULL,
    descripcion     VARCHAR(200)
);
```

**Tabla: `permiso`**
```sql
CREATE TABLE permiso (
    id_permiso      BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_rol          BIGINT,                    -- Nullable (permiso por rol)
    id_usuario      VARCHAR(50),               -- Nullable (permiso por usuario)
    id_modulo       BIGINT NOT NULL,
    puede_ver       BOOLEAN DEFAULT FALSE,
    puede_crear     BOOLEAN DEFAULT FALSE,
    puede_editar    BOOLEAN DEFAULT FALSE,
    puede_eliminar  BOOLEAN DEFAULT FALSE,
    
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_modulo) REFERENCES modulo(id_modulo),
    
    -- Un permiso debe tener rol O usuario (no ambos nulos)
    CHECK (id_rol IS NOT NULL OR id_usuario IS NOT NULL)
);
```

#### Criterios de Aceptación

- [x] El administrador puede crear nuevos usuarios
- [x] El administrador puede editar datos de usuarios existentes
- [x] El administrador puede asignar roles a usuarios
- [x] El administrador puede configurar permisos granulares por módulo
- [x] El sistema valida que el email sea único
- [x] El sistema almacena contraseñas como hash
- [x] Los permisos individuales tienen prioridad sobre los del rol
- [x] El sistema no permite eliminar el último usuario ADMIN
- [x] Los cambios de permisos se reflejan inmediatamente en el sistema
- [x] La interfaz muestra claramente qué permisos tiene cada usuario

---

## 🛡️ 1.3.1 SISTEMA DE AUDITORÍA Y BITÁCORA

### PROPÓSITO
Implementar un núcleo de seguridad inmutable que registre cada operación crítica (CRUD-01, 02, 03) y acceso al sistema, garantizando la trazabilidad forense de los datos.

### COMPONENTES CLAVE

#### 1. PRIVACY ENGINE (Buscador Cifrado)
- **Desafío:** La base de datos está cifrada (AES-256). Una búsqueda SQL tradicional (`LIKE %xyz%`) es imposible.
- **Solución:** Implementación de un motor de búsqueda en memoria ("Privacy Engine") que descifra los registros en tiempo real y permite el filtrado predictivo sobre valores humanos sin comprometer la seguridad en disco.

#### 2. TRAZABILIDAD DINÁMICA (Antes -> Después)
- El sistema detecta automáticamente qué campos cambiaron durante una edición.
- Ejemplo de registro: `- ROL: [VENDEDOR] -> [ADMINISTRADOR]`.
- Identificación real del operador (deja de ser "SISTEMA" para mostrar el nombre del responsable).

#### 3. MOTOR DE SIMETRÍA DE REPORTES
- Visualizador de "Ficha Técnica" con una regla forzada de **60 caracteres por línea**.
- Garantiza que los archivos `.txt` exportados mantengan una estética de caja uniforme y profesional.

### MODELO DE DATOS: `bitacora_seguridad`
```sql
CREATE TABLE bitacora_seguridad (
    id_bitacora     VARCHAR(50) PRIMARY KEY,
    id_usuario      VARCHAR(50),               -- Actor de la acción
    accion          TEXT NOT NULL,              -- Cifrado: Impacto | Cat | Código
    ip_origen       VARCHAR(45),               -- Cifrado
    detalles        TEXT,                       -- Cifrado: Mensaje | Análisis Técnico
    fecha           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
```

### DICCIONARIO BINARIO DE PATRONES (`.dat`)
El sistema utiliza un archivo binario cifrado (`audit_dictionary.dat`) que actúa como la "Matriz Maestra" de verdad, impidiendo que un atacante modifique las reglas de auditoría desde el sistema de archivos.

**Última actualización:** 15 de febrero de 2026, 05:48 PM  
**Estado:** Bitácora Forense Implementada ✅  
**Código fuente:** Ver `ETAPA 1 - CODIGO_FUENTE.md`
