# KI: Protocolo Cognitivo "Duda Cero" para White Box Testing (WBT)

## Metadatos
- **Contexto**: Proyecto Terminal ERP_WALOOK_PCB
- **Materia**: Calidad de Software / Testing Automático
- **Nivel de Severidad**: CRÍTICA (Inquebrantable)

## Fundamentos Epistemológicos del QA
En la Ingeniería de Software rigurosa, un tester/auditor actúa como un garante de certeza matemática y operativa frente a un sistema. Todo reporte de prueba (Caja Blanca o Caja Negra) que no goce de total trazabilidad con el estado determinista del software (la base de datos, los controladores, la memoria) no se considera un informe técnico válido, sino una abstracción didáctica nula para procesos de auditoría del mundo real.

## Reglas Cognitivas para el Agente (DevIAn)

1. **La Condición Lógica no basta, exige el Vector Empírico (Test Data)**
   Construir el Grafo Ciclomático a través del algoritmo de McCabe $V(G) = E - N + 2$ solo certifica que el código fue "analizado matemáticamente". Para demostrar cobertura (Path Coverage), cada "Camino Independiente" listado en los reportes `.md` DEBE ir acompañado de los valores de las variables (IN/OUT) exactos que forzarán el flujo hacia ese nodo predicado.
   
2. **Prohibición Absoluta de Abstracción ("No Inventes Datos")**
   Si la base de datos de pruebas configurada para el sistema (ej. `registrospruebas.sql`) no contiene un registro que pueda detonar físicamente un camino (ej. un usuario con bandera `inactivo` en la BD), el Agente **tiene prohibido inventar o asimilar el dato con placeholders** genéricos (ej. `inactivo@x.com`).
   - El deber del Agente no es "completar el formulario de prueba".
   - El deber del Agente es **reportar el vacío del entorno**, detener el flujo y solicitar al Arquitecto la inyección del Dato Semilla antes de continuar documentando.

3. **Semántica Estricta**
   No confundir los atributos funcionales (variables de estado) de una entidad con identificadores léxicos.
   - Ejemplo erróneo: "La palabra 'inactivo' es un estatus".
   - Ejemplo analizado QA: "La cadena `inactivo@test.com` es un identificador de correo, no el estatus operativo. El verdadero estatus es el estado del campo binario `activo` en la tabla `usuario` en la posición X de memoria."

4. **Auditoría Empírica ("No Asumas, Verifica")**
   Antes de generar tablas de casos de prueba, el algoritmo RAG del modelo debe forzar la retención y escrutinio del archivo fuente maestro (los *Scripts de BD* y *Archivos Locales*) para validar si existe data empírica disponible. Si no hay datos, no hay prueba.

### Acción Resolutoria sobre ERP_WALOOK_PCB
Para la refactorización de los 15 reportes WBT de la aplicación, el Agente y el Arquitecto acuerdan estandarizar la *Tabla F (Casos de Prueba)* sustituyendo toda mención sintética por parámetros y rutas directas extraídas de la base SQL.
