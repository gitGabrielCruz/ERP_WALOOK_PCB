/*
============================================================
Nombre del archivo : ETAPA_0.md
Ruta              : ETAPA_0.md
Tipo              : Documentación Técnica (Etapa 0)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MÉXICO, S.A. de C.V.

Autor             : Ing. Gabriel Amilcar Cruz Canto
Versión           : 1.1 (Sincronizada)
Fecha             : 22 de febrero de 2026
Propósito         : Definir la base técnica, estructura y preparación del sistema.
============================================================
*/


---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 0 - Preparación Técnica del Sistema  
**VERSIÓN:** 1.1  
**FECHA:** 22 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

# ETAPA 0 — Preparación técnica del sistema OMCGC

===
Versión 1.0 — Documento canónico
---
Este documento debe leerse y ejecutarse de forma secuencial.

Objetivo de la etapa
---
Preparar la base técnica del Sistema ERP en la nube para gestión de ópticas OMCGC, asegurando que el entorno, la estructura, las dependencias y la base de datos se encuentren correctamente configurados, verificados y funcionales, sin implementar lógica de negocio ni funcionalidades del sistema.
Esta etapa establece el punto de partida técnico obligatorio para todas las etapas posteriores.

Alcance autorizado
---
En esta etapa se autoriza únicamente:
Creación de la estructura de carpetas definida.
Creación de archivos base y de configuración.
Instalación y verificación de librerías y dependencias.
Preparación de la base de datos con estructura vacía.
Verificación de compilación y arranque del sistema.
Queda estrictamente prohibido:
Implementar lógica de negocio.
Implementar flujos funcionales.
Crear endpoints operativos.
Crear controladores, servicios o repositorios, aunque estén vacíos o como placeholders.
Implementar validaciones funcionales.
Introducir dependencias de etapas futuras.

Orden obligatorio de ejecución
---
Las acciones deben realizarse estrictamente en el siguiente orden:
Crear la estructura de carpetas del proyecto.
Crear los archivos base y de configuración.
Configurar variables de entorno.
Declarar e instalar dependencias.
Preparar la base de datos mediante scripts.
Compilar el proyecto.
Arrancar el sistema.
Validar los criterios de cierre.
No se permite alterar este orden.

Estructura de carpetas obligatoria
---
```
omcgc/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/omcgc/erp/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   └── model/
│   │   │   └── resources/
│   │   │       └── application.properties
│   ├── pom.xml
│
├── frontend/
│   ├── pages/
│   ├── assets/
│   └── index.html
│
├── database/
│   ├── schema/
│   ├── backups/
│   └── scripts/
│       ├── 00_drop_create_db.sql
│       ├── 01_schema_usuarios.sql
│       ├── 04_usuario_prueba.sql
│       └── 05_update_pacientes.sql
│
├── docs/
│   ├── analisis_diseno/
│   ├── diagramas/
│   └── anexos/
│
├── tests/
│   ├── backend/
│   └── frontend/
│
├── config/
│   └── env/
│       ├── local.env
│       ├── test.env
│       └── prod.env
│
└── README.md
```
La estructura debe coincidir exactamente.
No se permite agregar, eliminar o renombrar carpetas.

Stack tecnológico obligatorio
---
Backend
---
Lenguaje: Java 17 (LTS)
Framework base: Spring Boot
---
Gestor de dependencias: Maven
---
Tipo de aplicación: API REST sobre HTTP
---
Formato de intercambio: JSON
---
Base de datos
---
Motor: MySQL 8.x
---
Codificación: utf8mb4
---
Motor de almacenamiento: InnoDB
---
Nombre de BD: db_omcgc_erp
---
Frontend
---
HTML5
---
CSS3
---
JavaScript (ES6+)
Sin frameworks frontend en esta etapa
---

Dependencias obligatorias a declarar y verificar
---
spring-boot-starter
---
spring-boot-starter-web
---
spring-boot-starter-jdbc
---
mysql-connector-j
---
spring-boot-starter-security
---
spring-security-crypto
---
spring-boot-starter-validation
---
spring-boot-starter-logging
---
spring-boot-starter-test
---
Todas deben:
Estar declaradas en pom.xml
---
Resolver sin errores
---
Permitir compilación limpia
---

Preparación de base de datos
---
Se deben crear y verificar:
Script de eliminación y creación de BD.
Script de creación de tablas (estructura vacía).
Script de índices y relaciones.
La base de datos no debe contener datos operativos.

Reglas operativas obligatorias
---
Todo archivo fuente debe incluir el encabezado canónico del proyecto.
No se permite código fuera del alcance de la Etapa 0.
No se permite dependencia de módulos futuros.
No se permite código funcional ni placeholders.
Cada validación debe completarse antes de avanzar.

Gestión de fallos
---
Si alguna verificación falla:
Se debe detener el avance inmediatamente.
Se debe identificar la causa del fallo.
Se deben proporcionar instrucciones claras de corrección.
Se debe reintentar la verificación.
No se permite continuar hasta que el fallo sea resuelto.

Validaciones obligatorias
---
Se debe verificar y confirmar explícitamente:
El proyecto compila sin errores (mvn clean install).
El backend arranca correctamente.
El frontend abre correctamente.
La base de datos existe sin registros.
Todas las dependencias están instaladas.
La estructura de carpetas es estable.
No existe lógica de negocio implementada.

Estado final verificable de la Etapa 0
---
La Etapa 0 se considera finalizada únicamente cuando:
El backend responde en http://localhost:8080.
La base de datos db_omcgc_erp existe sin datos.
No existen endpoints funcionales activos.
No existen reglas de negocio implementadas.
El sistema arranca de forma limpia y reproducible.

Entrega obligatoria al cierre de la etapa
---
Al finalizar la Etapa 0 se debe entregar:
Confirmación explícita de cada validación.
Listado de dependencias instaladas.
Evidencia de compilación y arranque.
Confirmación de ausencia total de lógica de negocio.

Resultado esperado
---
Al concluir esta etapa, el sistema queda técnicamente preparado, estable, reproducible y listo para iniciar la Etapa 1, sin deuda técnica heredada y con control total sobre su base estructural.

1. Definición tecnológica base
---
La definición tecnológica base establece el conjunto de tecnologías, lenguajes, arquitectura y normas técnicas que servirán como fundamento para el desarrollo del sistema ERP en la nube para la gestión de ópticas OMCGC. Esta definición tiene como objetivo garantizar coherencia técnica, trazabilidad académica y viabilidad de implementación, permitiendo que el sistema sea desarrollado de manera incremental por etapas, probado de forma independiente y mantenido sin dependencias innecesarias.
El sistema se desarrollará bajo una arquitectura en capas, utilizando un enfoque cliente–servidor mediante servicios web tipo REST (Representational State Transfer; Transferencia de Estado Representacional), donde el frontend web consumirá servicios expuestos por el backend. Esta separación asegura desacoplamiento entre la interfaz de usuario, la lógica de negocio y la persistencia de datos, facilitando pruebas, mantenimiento y escalabilidad.
Para el backend se empleará el lenguaje de programación Java en su versión 17 (LTS), apoyado por un modelo de desarrollo orientado a objetos y una organización basada en el patrón MVC (Model–View–Controller; Modelo–Vista–Controlador), complementado por una capa de servicios y una capa de acceso a datos. El frontend se implementará utilizando tecnologías web estándar (HTML5, CSS3 y JavaScript), priorizando claridad, compatibilidad y facilidad de integración con los servicios del backend.
La persistencia de la información se realizará mediante el sistema gestor de bases de datos MySQL en su versión 8.x, utilizando una estructura relacional normalizada y scripts SQL controlados para la creación y administración de la base de datos. Se adoptarán convenciones de nombres, estándares de codificación y políticas de seguridad básicas desde las etapas iniciales, con el fin de asegurar integridad, consistencia y trazabilidad entre requerimientos, pantallas, lógica de negocio y base de datos.
Esta definición tecnológica base constituye el punto de partida obligatorio para todas las etapas posteriores del desarrollo, y ninguna implementación podrá contradecir o alterar las decisiones aquí establecidas sin una revisión y validación formal.
1.1 Lenguajes de programación y stack tecnológico
---
Descripción
---
El sistema se desarrollará utilizando un stack tecnológico orientado a la estabilidad, portabilidad y facilidad de mantenimiento, adecuado para un desarrollo incremental por etapas y compatible con un modelo de implementación asistido por un desarrollador de Inteligencia Artificial. La selección de tecnologías se fundamenta en el uso de estándares ampliamente aceptados, versiones con soporte a largo plazo y una clara separación entre capas del sistema, lo que permite pruebas independientes, integración progresiva y control técnico del proyecto.
El backend se implementará como una API de servicios web, responsable de la lógica de negocio, validaciones y acceso a datos. El frontend actuará como cliente web, consumiendo los servicios expuestos por el backend. La persistencia de la información se realizará mediante un sistema gestor de bases de datos relacional, asegurando integridad, consistencia y trazabilidad con los requerimientos definidos en el Análisis y Diseño.

Resumen técnico puntual
---
Backend (Servidor / API)
Lenguaje: Java 17 (LTS)
Tipo de aplicación: API REST
(Representational State Transfer; Transferencia de Estado Representacional)
Protocolo: HTTP / HTTPS
---
Formato de intercambio: JSON
(JavaScript Object Notation; Notación de Objetos de JavaScript)
Frontend (Cliente Web)
Lenguajes: HTML5, CSS3, JavaScript (ES6+)
Enfoque: Interfaz web tradicional consumiendo la API REST
---
Restricción Etapa 0:
Sin uso obligatorio de frameworks frontend pesados
Base de datos
---
Motor: MySQL 8.x
---
Modelo: Relacional
---
Compatibilidad: SQL estándar + extensiones MySQL
---
Codificación: utf8mb4
---
Herramientas base (desarrollo y ejecución)
JDK: Java Development Kit 17
---
Gestor de dependencias / build: Maven (pom.xml)
Servidor de aplicación: Tomcat embebido (si se utiliza framework backend)
Salida / entregable técnico que cierra este subconcepto
---
El backend compila y levanta un servicio HTTP.
El frontend abre en navegador (contenido estático).
MySQL está listo para crear la base de datos y tablas.
El stack tecnológico queda definido y no se modifica en etapas posteriores sin validación formal.
1.2 Arquitectura general
---
Descripción
---
La arquitectura general del sistema define la forma en que se organizan y relacionan sus componentes, con el objetivo de asegurar separación de responsabilidades, facilidad de pruebas por etapas e integración incremental sin dependencias innecesarias. El sistema se implementará bajo un esquema cliente–servidor, donde el frontend web actúa como consumidor de servicios y el backend concentra la lógica de negocio y el acceso a datos.
Se adopta una arquitectura en capas, la cual permite aislar la presentación, la lógica de aplicación y la persistencia de datos, garantizando que cada componente cumpla una función específica. Este enfoque es especialmente adecuado para un desarrollo asistido por un desarrollador de Inteligencia Artificial, ya que permite validar cada etapa de manera independiente, reducir errores de acoplamiento y facilitar el mantenimiento del sistema.
La comunicación entre capas se realiza mediante contratos bien definidos, evitando accesos directos entre componentes que no correspondan a su nivel, y asegurando que cualquier cambio o ampliación futura no afecte el funcionamiento de las partes ya implementadas.

Resumen técnico puntual
---
Estilo arquitectónico
---
Arquitectura cliente–servidor
---
Arquitectura en capas (Layered Architecture)
Capas del sistema
---
Capa de Presentación
---
Frontend web (HTML, CSS, JavaScript)
Consume servicios del backend vía HTTP
---
No contiene lógica de negocio ni acceso a BD
---
Capa de Controladores
---
Recibe solicitudes HTTP
---
Expone endpoints REST
---
Valida estructura básica de entrada
---
Devuelve respuestas HTTP estandarizadas
---
Capa de Servicios
---
Implementa la lógica de negocio
---
Orquesta procesos entre módulos
---
Gestiona reglas y flujos del sistema
---
Capa de Acceso a Datos
---
DAO / Repository
---
Encapsula sentencias SQL
---
Gestiona operaciones CRUD
---
Capa de Persistencia
---
Base de datos MySQL
---
Tablas, relaciones, índices y restricciones
---
Comunicación entre capas
---
Presentación ↔ Backend: HTTP/HTTPS + JSON
---
Backend ↔ Base de datos: SQL parametrizado
---
Prohibido el acceso directo del frontend a la BD
---
Prohibido que los controladores contengan SQL
---
Restricciones arquitectónicas
---
No se permite saltar capas
---
Cada capa solo se comunica con la capa inmediata inferior
---
Las etapas futuras no pueden introducir dependencias hacia atrás
---
Salida / entregable técnico que cierra este subconcepto
---
Arquitectura definida y documentada
---
Roles claros para cada capa
---
Base preparada para desarrollo incremental por etapas
---
Ningún componente depende de módulos aún no implementados
---
1.3 Patrón de organización del sistema
---
Descripción
---
El sistema se organizará internamente utilizando el patrón MVC (Model–View–Controller; Modelo–Vista–Controlador), complementado por una capa de servicios y una capa de acceso a datos. Este patrón permite una separación clara de responsabilidades entre la representación de la información, el control del flujo de las operaciones y la lógica de negocio, favoreciendo la mantenibilidad, la escalabilidad y la coherencia estructural del sistema.
En el contexto de una aplicación basada en servicios web, el patrón MVC se adapta de manera que la vista se implementa como un cliente web independiente que consume los servicios expuestos por el backend, mientras que los controladores actúan como punto de entrada para las solicitudes del sistema. La capa de servicios concentra las reglas de negocio y coordina las operaciones entre los distintos módulos, y la capa de acceso a datos se encarga exclusivamente de la interacción con la base de datos.
Esta organización facilita el desarrollo incremental por etapas, ya que cada componente puede implementarse y validarse de forma independiente, manteniendo la integridad de las partes previamente construidas y evitando dependencias innecesarias entre módulos.

Resumen técnico puntual (decisión cerrada)
Modelo de organización
---
Patrón: MVC (Model–View–Controller)
Aplicación: MVC adaptado a arquitectura de servicios REST
---
Componentes y responsabilidades
---
Model (Modelo)
Representa las entidades del dominio del sistema (usuarios, clientes, ventas, etc.).
Define atributos y relaciones.
No gestiona lógica de presentación ni acceso directo a la interfaz.
View (Vista)
Implementada mediante el frontend web.
Construida con HTML, CSS y JavaScript.
Consume los servicios REST del backend.
No contiene lógica de negocio ni acceso directo a la base de datos.
Controller (Controlador)
Expone los endpoints del sistema.
Recibe y procesa solicitudes HTTP.
Mapea rutas a operaciones del sistema.
No implementa reglas de negocio.
Service (Capa de servicios)
Implementa la lógica de negocio del sistema.
Orquesta procesos entre módulos.
Aplica reglas, validaciones y flujos operativos.
Actúa como intermediario entre controladores y acceso a datos.
DAO / Repository (Acceso a datos)
Gestiona operaciones CRUD.
Encapsula las sentencias SQL.
Controla la persistencia de la información.
No contiene lógica de negocio.

Reglas de separación obligatorias
---
Los controladores no deben acceder directamente a la base de datos.
Las entidades del modelo no deben gestionar peticiones HTTP.
La capa de servicios no debe generar vistas ni contenido de interfaz.
El frontend no debe contener reglas de negocio ni credenciales del sistema.

Salida / entregable técnico que cierra este subconcepto
---
Organización interna del backend claramente definida.
Separación estricta de responsabilidades por componente.
Base estructural adecuada para integración incremental por etapas.
Prevención de acoplamientos indebidos entre capas del sistema.
Entendido.
Aquí tienes 1.4 COMPLETO, en una sola pieza, coherente, académico, impersonal, sin referencias externas, y alineado al sistema OMCGC.
No hay fragmentos sueltos, no hay repeticiones innecesarias.

1.4 Convenciones de nombres
---
Descripción
---
Las convenciones de nombres definen el esquema de identificación que se utilizará para los distintos elementos que conforman el Sistema ERP en la nube para gestión de ópticas OMCGC. Su finalidad es establecer un lenguaje común que permita identificar de forma clara, consistente y predecible los módulos del sistema, los componentes de software y los elementos de la base de datos, manteniendo coherencia con la estructura funcional definida en el Análisis y Diseño.
La aplicación de un conjunto uniforme de convenciones facilita la comprensión de la arquitectura del sistema, fortalece la trazabilidad entre requerimientos, pantallas de captura y lógica de negocio, y contribuye a la mantenibilidad del proyecto. Asimismo, estas convenciones permiten una integración progresiva de los distintos módulos, ya que cada componente puede relacionarse con el resto del sistema de manera ordenada y sin ambigüedades.
Las convenciones aquí definidas se consideran parte integral de la base técnica del sistema y se mantienen constantes a lo largo de todas las etapas del desarrollo, evitando inconsistencias en la nomenclatura que puedan afectar la validación académica, la integración funcional y la evolución futura del sistema.

Resumen técnico puntual
---
Backend (Java)
Paquetes:
omcgc.erp.<modulo>.<capa>
Ejemplos:
omcgc.erp.usuarios.controller
---
omcgc.erp.clientes.service
---
omcgc.erp.ventas.repository
---
Clases: PascalCase
Ejemplos:
UsuarioController
---
ClienteService
---
VentaRepository
---
Métodos: camelCase
Ejemplos:
crearCliente()
listarVentas()
Variables: prefijo funcional + camelCase
Ejemplos:
vrUsuario
---
vrListaClientes
---
Constantes: MAYUSCULAS_CON_GUION_BAJO
Ejemplo:
MAX_INTENTOS_LOGIN
---

Frontend (HTML / CSS / JavaScript)
Estructura de carpetas:
pages/ (pantallas)
components/ (componentes reutilizables)
services/ (consumo de API)
assets/ (recursos estáticos)
Archivos HTML: kebab-case
Ejemplos:
login.html
---
clientes.html
---
Archivos JavaScript: kebab-case
Ejemplos:
clientes-service.js
---
ventas-controller.js
---
Clases CSS: kebab-case
Ejemplos:
.form-login
---
.tabla-clientes
---

Base de datos (MySQL)
Base de datos: db_omcgc_erp
---
Tablas: tb_<nombre>
Ejemplos:
tb_usuario
---
tb_cliente
---
tb_venta
---
Campos: snake_case
Ejemplos:
id_usuario
---
fecha_creacion
---
Clave primaria (PK): id_<tabla>
---
Clave foránea (FK): id_<tabla_referenciada>
---
Índices: idx_<tabla>_<campo>
---
Restricciones únicas: uk_<tabla>_<campo>
---

Scripts SQL
---
Creación / reinicio de base de datos:
00_drop_create_db.sql
---
Definición de esquema:
01_schema.sql
---
Índices y relaciones:
02_indexes.sql
---
Datos mínimos de prueba (si aplica):
03_seed_minimo.sql
---

Criterio de cierre del subconcepto
---
Identificadores homogéneos en backend, frontend y base de datos.
Correspondencia clara entre módulos, pantallas y tablas.
Base nominal preparada para la definición de la estructura de carpetas.
Eliminación de ambigüedades en la identificación de componentes del sistema.

1.5 Estándares de Experiencia de Usuario (UX/UI)
---
Descripción
---
Para garantizar la coherencia visual, la facilidad de uso y una apariencia profesional en todos los módulos del sistema, se establece el "Patrón WALOOK", un conjunto de reglas de diseño e interacción obligatorias. Todos los módulos (Usuarios, Clientes, Inventarios, etc.) deben adherirse estrictamente a estos principios para asegurar una curva de aprendizaje mínima y una operación fluida.

Resumen técnico puntual (Normativa UX/UI)
---

**1. Tablas y Listados:**
*   **Estilo:** "Cebra" (Striped) alternando fondo blanco y gris claro `#f8fafc`.
*   **Interacción:** Filas completas clickeables (`tr:hover`) que llevan al detalle del registro.
*   **Acciones:** Botones textuales "Ver", "Editar" (clase `btn-secondary`), evitando iconos crípticos aislados.
*   **Indicadores:** Estatus visual mediante **Punto de Color + Texto** (ej. `● Activo`), evitando "Badges" grandes o invasivos.

**2. Búsqueda y Filtrado:**
*   **Live Search:** Todos los campos de búsqueda textual deben usar el evento `input` para filtrar en tiempo real.
*   **Debounce:** Filtrado implícito reactivo.

**3. Formularios y Botonera:**
*   **Posición:** Botones principales (Guardar, Cancelar, Nuevo) ubicados en la esquina inferior derecha.
*   **Iconografía Estándar:**
    *   Guardar: `save`
    *   Nuevo: `add_circle`
    *   Cancelar: Texto plano o ícono `cancel` si aplica.

**4. Paleta de Colores Semántica:**
*   **Éxito/Activo:** Verde Emerald (`#10B981`)
*   **Error/Inactivo:** Rojo Red (`#EF4444`)
*   **Información:** Azul Blue (`#3B82F6`)
*   **Advertencia:** Amarillo Amber (`#F59E0B`)

**5. Tipografía y Estructura:**
*   Encabezados claros en mayúsculas sostenidas o Capitalize consistente.
*   Layout Master-Detail (Lista Izquierda, Detalle Derecha) preferido para ABMs.



2. Estructura de carpetas del proyecto
---
Descripción
---
La estructura de carpetas del proyecto define la organización física de los archivos que conforman el Sistema ERP en la nube para gestión de ópticas OMCGC. Su objetivo es establecer una disposición lógica y ordenada de los componentes del sistema, alineada con la arquitectura en capas y el patrón de organización definidos previamente, de manera que cada elemento se ubique en un contexto claro según su función y responsabilidad.
Una estructura de carpetas bien definida facilita la navegación del proyecto, permite identificar rápidamente la ubicación de los distintos módulos y componentes, y contribuye a la separación efectiva entre la capa de presentación, la lógica de aplicación, la persistencia de datos y los recursos de apoyo. Asimismo, esta organización favorece el desarrollo incremental por etapas, ya que cada módulo puede incorporarse sin afectar la estructura general del sistema ni los componentes previamente establecidos.
La estructura propuesta también contempla la separación entre código fuente, scripts de base de datos, archivos de configuración, recursos estáticos y documentación técnica, lo que apoya la mantenibilidad del sistema y la validación académica del proyecto. Esta definición sirve como base para la creación de los archivos iniciales del sistema y se mantiene estable a lo largo de todas las etapas del desarrollo, permitiendo integrar nuevos componentes de forma ordenada y controlada.
Perfecto. Aquí tienes el 2.1 COMPLETO, en una sola pieza, integrado y listo para pegar en el documento: descripción + estructura, sin fragmentos sueltos.

2.1 Árbol de carpetas del proyecto
---
Descripción
---
El árbol de carpetas del proyecto define la jerarquía inicial de directorios que conforman el Sistema ERP en la nube para gestión de ópticas OMCGC. Su propósito es establecer una organización clara y coherente de los archivos del sistema, alineada con la arquitectura en capas y el patrón de organización definidos previamente, permitiendo identificar de forma inmediata la función de cada componente.
La estructura propuesta separa de manera explícita el backend, el frontend, la base de datos, la documentación y las pruebas, evitando acoplamientos innecesarios y facilitando el desarrollo incremental por etapas. Esta jerarquía está diseñada para ser mínima y estable, de modo que el crecimiento del sistema a lo largo de las seis etapas de desarrollo no requiera reestructuraciones profundas ni cambios en la organización base del proyecto.
El directorio raíz omcgc representa el sistema como una unidad única, y a partir de él se distribuyen los distintos componentes según su responsabilidad. Esta organización favorece la mantenibilidad, la trazabilidad académica y la integración progresiva de funcionalidades, manteniendo un orden consistente durante todo el ciclo de desarrollo del sistema.

Estructura de carpetas
---
```
omcgc/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/omcgc/erp/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   └── model/
│   │   │   └── resources/
│   │   │       └── application.properties
│   ├── pom.xml
│
├── frontend/
│   ├── pages/
│   ├── assets/
│   └── index.html
│
├── database/
│   ├── schema/
│   ├── backups/
│   └── scripts/
│       ├── 00_drop_create_db.sql
│       ├── 01_schema.sql
│       ├── 02_indexes.sql
│       └── 03_seed_minimo.sql
│
├── docs/
│   ├── analisis_diseno/
│   ├── diagramas/
│   └── anexos/
│
├── tests/
│   ├── backend/
│   └── frontend/
│
├── config/
│   └── env/
│       ├── local.env
│       ├── test.env
│       └── prod.env
│
└── README.md
```
2.2 Carpeta de backend
---
Descripción
---
La carpeta de backend contiene los elementos responsables de la lógica de aplicación del Sistema ERP en la nube para gestión de ópticas OMCGC. En este espacio se implementan los controladores que reciben las solicitudes del sistema, los servicios que aplican las reglas de negocio, los modelos que representan las entidades del dominio y los componentes de acceso a datos que gestionan la persistencia de la información. Su organización responde a la arquitectura en capas y al patrón de organización definidos previamente, permitiendo un crecimiento progresivo del sistema sin alterar la estructura base.
Estructura
---
backend/
---
```
├── src/main/java/com/omcgc/erp/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   └── model/
├── src/main/resources/
│   ├── application.properties
│   └── logback-spring.xml
└── pom.xml

```
2.3 Carpeta de frontend
---
Descripción
---
La carpeta de frontend agrupa los archivos que conforman la interfaz de usuario del sistema. En ella se implementan las páginas de interacción con el usuario, así como los recursos visuales necesarios para la presentación de la información. Esta carpeta mantiene una separación estricta respecto a la lógica de negocio y la persistencia de datos, actuando exclusivamente como cliente que consume los servicios expuestos por el backend.
Estructura
---
frontend/
---
```
├── pages/
│   ├── login.html
│   ├── menu.html
│   ├── clientes.html
│   ├── citas.html
│   ├── ventas.html
│   └── reportes.html
├── assets/
│   ├── css/
│   ├── js/
│   └── img/
└── index.html

```
2.4 Carpeta de base de datos
---
Descripción
---
La carpeta de base de datos contiene los elementos relacionados con la definición lógica y estructural de la información del sistema. En este espacio se centralizan los componentes que describen el modelo relacional, permitiendo mantener trazabilidad entre el análisis de datos, las pantallas de captura y los procesos definidos. Esta carpeta constituye la base para garantizar la integridad y consistencia de la información a lo largo del desarrollo del sistema. Esta carpeta incluye los subdirectorios schema/, backups/ y scripts/, los cuales permiten separar la definición lógica de la base de datos, los respaldos y los scripts ejecutables.
Estructura
---
database/
---
```
├── schema/
│   ├── modelo_relacional.sql
│   └── diccionario_datos.md
└── backups/

```
2.5 Carpeta de scripts
---
Descripción
---
La carpeta de scripts concentra los archivos ejecutables necesarios para la preparación y configuración del entorno del sistema, particularmente en lo referente a la base de datos. Estos scripts permiten crear, reiniciar y configurar la estructura de datos de forma controlada, asegurando reproducibilidad y coherencia durante las distintas etapas del desarrollo y las pruebas académicas.
Estructura
---
ERP_WALOOK/
---
```
├── creaciontablas.sql
├── creaciontablas_nube.sql
├── registrospruebas.sql
├── registrospruebas_nube.sql
└── INSERT_USUARIO_ADMIN.sql
```
2.6 Carpeta de documentación técnica
---
Descripción
---
La carpeta de documentación técnica resguarda los documentos que describen el análisis, el diseño y las decisiones técnicas del sistema. En este espacio se almacenan los artefactos académicos, diagramas y anexos requeridos para la validación del proyecto, manteniendo una separación clara entre la documentación y el código fuente.
Estructura
---
docs/
---
```
├── analisis_diseno/
├── diagramas/
└── anexos/
```
2.7 Carpeta de pruebas
---
Descripción
---
La carpeta tests agrupa los elementos destinados a la verificación del sistema, organizados en los subdirectorios backend/ y frontend/, permitiendo separar las pruebas correspondientes a cada componente.
Esta carpeta apoya el enfoque de desarrollo incremental, permitiendo realizar pruebas sobre el backend y el frontend de forma organizada, así como documentar los resultados obtenidos. Su existencia facilita la detección temprana de errores y contribuye a la estabilidad del sistema a lo largo de su evolución.
Estructura
---
tests/
---
```
├── backend/
│   ├── unitarias/
│   └── integracion/
├── frontend/
│   ├── funcionales/
│   └── interfaz/
└── evidencias/

```
2.8 Carpeta de configuración
---
Descripción
---
La carpeta config se ubica en la raíz del proyecto y contiene el subdirectorio env/, en el cual se definen los archivos de variables de entorno correspondientes a los distintos contextos de ejecución del sistema.
Esta separación favorece la mantenibilidad y el control del sistema, ya que centraliza los parámetros de configuración y evita la dispersión de valores sensibles o dependientes del entorno dentro de los componentes funcionales del proyecto.
Estructura
---
config/
---
```
├── env/
│   ├── local.env
│   ├── test.env
│   └── prod.env
└── README.md

```
3. Definición de archivos base
---
3.1 Archivos de configuración general
---
Descripción
---
Los archivos de configuración general definen los parámetros básicos de funcionamiento del sistema que no dependen directamente del entorno de ejecución. En estos archivos se establecen propiedades comunes relacionadas con el comportamiento del backend, como puertos de servicio, nombres del sistema, configuraciones generales de la aplicación y ajustes transversales necesarios para su operación.
Estos archivos permiten centralizar configuraciones globales, facilitando la mantenibilidad del sistema y evitando la dispersión de parámetros dentro del código fuente.
Estructura
---
backend/config/
---
```
├── src/main/resources/application.properties
└── src/main/resources/logback-spring.xml

```
3.2 Archivos de entorno (variables de entorno)
Descripción
---
Los archivos de entorno contienen las variables que dependen del contexto de ejecución del sistema, tales como credenciales, rutas específicas, parámetros sensibles y configuraciones diferenciadas por ambiente. Su finalidad es permitir que el sistema se ejecute en distintos entornos sin modificar el código fuente, manteniendo la separación entre lógica de aplicación y configuración.
Estos archivos contribuyen a la seguridad y flexibilidad del sistema, al aislar información sensible y parámetros variables.
Estructura
---
config/env/
---
```
├── local.env
├── test.env
└── prod.env

```
3.3 Archivos de arranque del sistema
---
Descripción
---
Los archivos de arranque del sistema son responsables de iniciar la ejecución del backend y del frontend, así como de cargar las configuraciones necesarias para su funcionamiento. Estos archivos definen el punto de entrada de la aplicación y permiten verificar que el sistema puede iniciar correctamente en un entorno controlado antes de implementar funcionalidades adicionales.
Su correcta definición es fundamental para validar la preparación técnica del sistema en la Etapa 0.
Estructura
---
backend/src/
---
```
├── src/main/java/com/omcgc/erp/MainApplication.java
```
frontend/
---
```
└── index.html

```
3.4 Archivos de conexión a base de datos
---
Descripción
---
Los archivos de conexión a la base de datos gestionan la configuración necesaria para establecer la comunicación entre el backend y el sistema gestor de bases de datos. En ellos se definen los parámetros de conexión, tales como la URL de la base de datos, el usuario, el esquema y las opciones de conexión requeridas para el acceso seguro y controlado a la información.
Estos archivos aseguran una interacción consistente con la base de datos y facilitan la modificación de parámetros de conexión sin alterar la lógica de negocio.
Estructura
---
backend/config/
---
```
├── datasource.properties
```
backend/src/
---
```
├── DatabaseConfig.java

```
3.5 Archivos de control de errores y logs
---
Descripción
---
Los archivos de control de errores y logs permiten registrar eventos relevantes del sistema, tales como errores, advertencias y mensajes informativos. Su propósito es facilitar el monitoreo del comportamiento del sistema, apoyar la detección y diagnóstico de fallos, y documentar la ejecución de los procesos durante las distintas etapas del desarrollo.
Estos archivos contribuyen a la estabilidad y mantenibilidad del sistema, proporcionando información clave para la validación técnica.
Estructura
---
backend/config/
---
```
├── logback.xml
```
backend/logs/
---
```
├── application.log
└── error.log

```
3.6 Archivos README técnicos
---
Descripción
---
Los archivos README técnicos proporcionan una descripción general del sistema y de sus componentes, explicando la estructura del proyecto, los pasos básicos para su ejecución y las consideraciones técnicas relevantes. Estos documentos sirven como guía de referencia para comprender la organización del sistema y el propósito de cada uno de sus elementos.
Su inclusión facilita la comprensión global del proyecto y apoya la revisión académica y técnica del sistema.
Estructura
---
README.md
---
backend/
---
```
└── README.md
```
frontend/
---
```
└── README.md

```
4. Preparación de base de datos
---
4.1 Motor de base de datos seleccionado
---
Descripción
---
El sistema utilizará un sistema gestor de bases de datos relacional que garantice integridad referencial, soporte transaccional y compatibilidad con estándares SQL. La elección del motor prioriza estabilidad, disponibilidad y alineación con el modelo relacional definido en el Análisis y Diseño.
Especificación técnica
---
Motor: MySQL 8.x
---
Modelo: Relacional
---
Codificación: utf8mb4
---
Motor de almacenamiento: InnoDB
---

4.2 Nombre de la base de datos
---
Descripción
---
El nombre de la base de datos identifica de forma única al sistema dentro del entorno de ejecución y mantiene coherencia con la nomenclatura establecida para el proyecto.
Especificación
---
Nombre: db_omcgc_erp
---

4.3 Usuario y permisos de BD
---
Descripción
---
Se define un usuario específico para el acceso del sistema a la base de datos, con permisos controlados para operar sobre el esquema correspondiente, evitando el uso de credenciales administrativas en la operación normal.
Especificación
---
Usuario: usr_omcgc_app
---
Ámbito: db_omcgc_erp
---
Permisos:
SELECT
---
INSERT
---
UPDATE
---
DELETE
---
EXECUTE (si aplica)
Restricción: sin privilegios de administración global
---

4.4 Script de creación de base de datos
---
Descripción
---
Este script crea la base de datos del sistema con la codificación y configuración requeridas. Su ejecución establece el contenedor lógico donde residirá la estructura de datos del sistema.
Estructura / Artefacto
---
ERP_WALOOK/
---
```
└── creaciontablas.sql (Anteriormente: 00_drop_create_db.sql)
```
4.5 Script de creación de tablas (estructura vacía)
Descripción
---
Este script define la estructura relacional del sistema mediante la creación de tablas, claves primarias, claves foráneas y restricciones, sin incluir datos. Permite establecer el esquema base conforme al modelo de datos aprobado.
Estructura / Artefacto
---
ERP_WALOOK/
---
```
└── creaciontablas.sql (Anteriormente: 01_schema.sql)
```
4.6 Script de eliminación / reinicio de BD (uso académico)
Descripción
---
Este script permite eliminar y recrear la base de datos del sistema con fines académicos y de prueba, asegurando un entorno limpio y reproducible para validaciones por etapas.
Estructura / Artefacto
---
ERP_WALOOK/
---
```
└── creaciontablas.sql
```
5. Librerías y dependencias
---
5.1 Librerías base del backend
---
Descripción
---
Las librerías base del backend proporcionan los componentes fundamentales para la construcción y ejecución de la aplicación, incluyendo el manejo de peticiones HTTP, la estructuración del proyecto y el ciclo de vida de la aplicación. Estas dependencias permiten implementar la arquitectura definida y facilitan la organización del código conforme al patrón de organización establecido.
Especificación
---
Lenguaje base: Java 17 (LTS)
Gestor de dependencias: Maven
---
Framework base: Spring Boot (núcleo web y configuración)
Dependencias principales:
spring-boot-starter-web
---
spring-boot-starter
---

5.2 Librerías de conexión a base de datos
---
Descripción
---
Las librerías de conexión a base de datos permiten establecer y gestionar la comunicación entre el backend y el sistema gestor de bases de datos. Estas dependencias facilitan el acceso controlado a la información persistente, asegurando consistencia y estabilidad en las operaciones de lectura y escritura.
Especificación
---
Driver JDBC: MySQL Connector/J
---
Dependencias:
mysql-connector-j
---
spring-boot-starter-jdbc
---

5.3 Librerías de seguridad (hash, sesiones, tokens)
Descripción
---
Las librerías de seguridad proporcionan los mecanismos necesarios para proteger el acceso al sistema, gestionar credenciales y controlar las sesiones de los usuarios. Estas dependencias permiten aplicar buenas prácticas de seguridad desde las primeras etapas del desarrollo, garantizando la confidencialidad y la integridad de la información.
Especificación
---
Hash de contraseñas: BCrypt
---
Gestión de seguridad: Spring Security
---
Dependencias:
spring-boot-starter-security
---
spring-security-crypto
---
Soporte para tokens (si aplica):
jjwt (JSON Web Token)

5.4 Librerías de validación
---
Descripción
---
Las librerías de validación permiten verificar que los datos recibidos por el sistema cumplan con las reglas básicas de formato, obligatoriedad y consistencia antes de ser procesados o almacenados. Estas dependencias contribuyen a la calidad de la información y reducen errores en la ejecución del sistema.
Especificación
---
Validación de datos: Bean Validation
---
Implementación: Hibernate Validator
---
Dependencias:
spring-boot-starter-validation
---

5.5 Librerías de logs
---
Descripción
---
Las librerías de logs permiten registrar eventos relevantes durante la ejecución del sistema, tales como mensajes informativos, advertencias y errores. Su uso facilita el seguimiento del comportamiento de la aplicación y apoya la detección y análisis de fallos durante el desarrollo y las pruebas.
Especificación
---
Framework de logging: Logback
---
API de logging: SLF4J
---
Dependencias:
spring-boot-starter-logging
---

5.6 Librerías de pruebas
---
Descripción
---
Las librerías de pruebas proporcionan los elementos necesarios para verificar el correcto funcionamiento del sistema mediante pruebas unitarias e integrales. Estas dependencias permiten validar los componentes implementados en cada etapa del desarrollo, asegurando que las funcionalidades existentes continúen operando conforme a lo definido.
Especificación
---
Framework de pruebas: JUnit 5
---
Pruebas de integración: Spring Test
---
Dependencias:
spring-boot-starter-test
---

6. Configuración de entorno de desarrollo
---
6.1 Versión del lenguaje
---
Descripción
---
La versión del lenguaje define la base técnica sobre la cual se desarrolla el sistema, garantizando compatibilidad con las librerías seleccionadas, estabilidad en el tiempo y soporte adecuado durante todo el ciclo del proyecto. La elección de una versión con soporte a largo plazo asegura consistencia entre las distintas etapas del desarrollo y evita problemas de incompatibilidad.
Especificación
---
Lenguaje: Java
---
Versión: Java 17 (LTS)
Compatibilidad mínima: JDK 17 o superior
---
Ámbito: backend del sistema
---

6.2 Gestor de dependencias
---
Descripción
---
El gestor de dependencias permite administrar de forma centralizada las librerías utilizadas por el sistema, controlando versiones, resolviendo dependencias transitivas y facilitando la construcción del proyecto. Su uso asegura reproducibilidad del entorno y coherencia entre los distintos módulos del backend.
Especificación
---
Gestor: Maven
---
Archivo principal: pom.xml
---
Funciones principales:
Gestión de dependencias
---
Compilación del proyecto
---
Ejecución de pruebas
---
Empaquetado de la aplicación
---

6.3 Configuración local
---
Descripción
---
La configuración local define los parámetros necesarios para ejecutar el sistema en un entorno de desarrollo individual. Esta configuración permite levantar el backend, acceder a la base de datos local y probar las funcionalidades implementadas sin afectar otros entornos.
Especificación
---
Archivo de entorno: local.env
---
Base de datos: MySQL local
---
Puerto de backend: configurable (por ejemplo, 8080)
Logs: habilitados en nivel informativo
---
Frontend: ejecución directa en navegador
---

6.4 Configuración para pruebas
---
Descripción
---
La configuración para pruebas establece un entorno controlado destinado a la validación del sistema mediante pruebas unitarias e integrales. Este entorno permite verificar que los componentes existentes continúan funcionando correctamente conforme se incorporan nuevas funcionalidades.
Especificación
---
Archivo de entorno: test.env
---
Base de datos: esquema de prueba independiente
---
Datos: controlados o simulados
---
Logs: nivel de detalle ampliado
---
Ejecución: automatizada mediante el gestor de dependencias
---

6.5 Configuración para despliegue académico
---
Descripción
---
La configuración para despliegue académico define los parámetros necesarios para ejecutar el sistema en un entorno de demostración o evaluación, manteniendo condiciones controladas y reproducibles. Este entorno permite validar el funcionamiento integral del sistema sin requerir infraestructura productiva.
Especificación
---
Archivo de entorno: prod.env (uso académico)
Base de datos: instancia dedicada para evaluación
---
Logs: nivel controlado
---
Seguridad: credenciales configuradas por entorno
---
Objetivo: demostración funcional y validación académica
---

7. Reglas operativas para el desarrollador IA
---
7.1 Uso obligatorio del encabezado canónico
---
Descripción
---
Todo archivo fuente generado como parte del sistema debe incluir un encabezado canónico estandarizado que identifique el proyecto, el propósito del archivo, su alcance funcional y su relación con la etapa de desarrollo correspondiente. Este encabezado garantiza trazabilidad, control técnico y coherencia documental a lo largo del ciclo de vida del sistema.
El encabezado canónico permite verificar que cada archivo cumple con los lineamientos definidos en el Análisis y Diseño y que su contenido se encuentra alineado con el alcance autorizado para la etapa en la que fue creado.

7.2 Prohibición de código fuera de etapa
---
Descripción
---
No se permite la implementación de funcionalidades, estructuras, validaciones o dependencias que correspondan a etapas futuras del plan de desarrollo. Cada etapa tiene un alcance funcional claramente delimitado, y cualquier código que exceda dicho alcance se considera inválido.
Esta regla asegura que el sistema evolucione de forma controlada, evita sobre-implementación temprana y facilita la validación académica y técnica de cada etapa de manera independiente.

7.3 Regla de integración incremental
---
Descripción
---
Toda funcionalidad implementada debe integrarse de forma incremental con los componentes existentes del sistema, respetando la arquitectura, las convenciones de nombres y las estructuras previamente definidas. Cada incremento debe ser funcional por sí mismo y no debe requerir ajustes posteriores para operar correctamente dentro del sistema.
Esta regla garantiza que el sistema mantenga estabilidad conforme crece y que cada avance represente un incremento verificable y utilizable.

7.4 Regla de no dependencia futura
---
Descripción
---
Ningún componente implementado en una etapa determinada puede depender de módulos, servicios, tablas o configuraciones que aún no hayan sido definidos o implementados. Toda dependencia debe corresponder exclusivamente a elementos ya existentes y validados en etapas previas.
Esta regla previene acoplamientos indebidos, reduce riesgos de integración y asegura que cada etapa pueda evaluarse de forma autónoma.

7.5 Regla de validación antes de avanzar
---
Descripción
---
Antes de avanzar a la siguiente etapa del desarrollo, se debe verificar que la etapa actual cumple con su alcance definido, que los componentes implementados funcionan conforme a lo esperado y que no existen errores que comprometan la estabilidad del sistema.
La validación previa es un requisito obligatorio para autorizar el avance de etapa y constituye un mecanismo de control técnico y académico que asegura la calidad y coherencia del sistema en su conjunto.


8. Criterios de cierre de la Etapa 0
---
La Etapa 0 se considera concluida únicamente cuando se cumplen de manera íntegra los criterios técnicos definidos en este apartado. Estos criterios permiten validar que el sistema cuenta con una base técnica sólida, reproducible y estable, sin haber incorporado aún lógica de negocio, y que se encuentra preparado para iniciar el desarrollo funcional por etapas.
El cumplimiento de estos criterios asegura que el entorno, la estructura y las configuraciones iniciales del sistema han sido correctamente establecidas, y que cualquier desarrollo posterior se realizará sobre una base controlada y verificable.

8.1 Proyecto compila / arranca
---
Descripción
---
El proyecto debe compilar correctamente y permitir el arranque del sistema sin errores críticos. El backend debe iniciar su servicio base y el frontend debe poder abrirse en un navegador, confirmando que la configuración general y los archivos de arranque son funcionales.
Criterio de aceptación
---
El backend inicia sin errores de compilación.
El servicio base queda activo en el puerto configurado.
El frontend puede visualizarse correctamente.

8.2 Base de datos creada sin datos
---
Descripción
---
La base de datos del sistema debe poder crearse correctamente utilizando los scripts definidos, estableciendo únicamente la estructura vacía conforme al modelo de datos aprobado, sin registros de información operativa.
Criterio de aceptación
---
La base de datos existe con el nombre definido.
Las tablas, relaciones y restricciones se crean correctamente.
No existen datos cargados en las tablas.

8.3 Estructura de carpetas estable
---
Descripción
---
La estructura de carpetas definida para el proyecto debe encontrarse completa, organizada y conforme a lo establecido en la Etapa 0, sin modificaciones no autorizadas ni estructuras adicionales innecesarias.
Criterio de aceptación
---
Todas las carpetas definidas existen.
No hay carpetas duplicadas o fuera de la estructura aprobada.
La estructura permite crecimiento por etapas sin reorganización.

8.4 Dependencias instaladas
---
Descripción
---
Todas las librerías y dependencias definidas para la Etapa 0 deben encontrarse correctamente instaladas y resueltas, permitiendo la compilación y ejecución del sistema sin conflictos de versiones.
Criterio de aceptación
---
El gestor de dependencias resuelve correctamente todas las librerías.
No existen errores de dependencias faltantes o incompatibles.
El proyecto puede compilarse de forma limpia.

8.5 Sin lógica de negocio implementada
---
Descripción
---
En la Etapa 0 no debe existir implementación de lógica de negocio, validaciones funcionales ni flujos operativos propios del sistema. El objetivo de esta etapa es exclusivamente preparar la base técnica y estructural del proyecto.
Criterio de aceptación
---
No existen reglas de negocio codificadas.
No se implementan procesos funcionales del sistema.
Los componentes creados son únicamente estructurales o de configuración.

