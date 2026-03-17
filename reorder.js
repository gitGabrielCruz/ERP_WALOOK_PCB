const fs = require('fs');
const path = require('path');

const STATIC_HEADER = `# TEST PRUEBAS DE CAJA BLANCA

## Cobertura de Camino básico

La aplicación de este criterio de cobertura asegura que los casos de prueba diseñados permiten que todas las sentencias del programa sean ejecutadas al menos una vez y que las condiciones sean probadas tanto para su valor verdadero como falso. Una de las técnicas empleadas para aplicar este criterio de cobertura es la Prueba del Camino Básico. 

Esta técnica se basa en obtener una medida de la complejidad del diseño procedimental de un programa (o de la lógica del programa). Esta medida es la complejidad ciclomática de McCabe, y representa un límite superior para el número de casos de prueba que se deben realizar para asegurar que se ejecuta cada camino del programa. 

Los pasos a realizar para aplicar esta técnica son: 

1.- Representar el programa en un grafo de flujo 
2.- Calcular la complejidad ciclomática 
3.- Determinar el conjunto básico de caminos independientes 
4.- Derivar los casos de prueba que fuerzan la ejecución de cada camino.

A continuación, se detallan cada uno de estos pasos:

### 1.- Representar el programa en un grafo de flujo.

El grafo de flujo se utiliza para representar el flujo de control lógico de un programa. Para ello se utilizan los tres elementos siguientes: 

* **Nodos:** representan cero, una o varias sentencias en secuencia. Cada nodo comprende como máximo una sentencia de decisión (bifurcación). 
* **Aristas:** líneas que unen dos nodos. 
* **Regiones:** áreas delimitadas por aristas y nodos. Cuando se contabilizan las regiones de un programa debe incluirse el área externa como una región más. 
* **Nodos predicado:** cuando en una condición aparecen uno o más operadores lógicos (AND, OR, XOR, ...) se crea un nodo distinto por cada una de las condiciones simples. Cada nodo generado de esta forma se denomina nodo predicado. 

Así, cada construcción lógica de un programa tiene una representación. 

### 2.- Calcular la complejidad ciclomática 

La complejidad ciclomática es una métrica del software que proporciona una medida cuantitativa de la complejidad lógica de un programa. En el contexto del método de prueba del camino básico, el valor de la complejidad ciclomática define el número de caminos independientes de dicho programa, y por lo tanto, el número de casos de prueba a realizar. Posteriormente veremos cómo se identifican esos caminos, pero primero veamos cómo se puede calcular la complejidad ciclomática a partir de un grafo de flujo, para obtener el número de caminos a identificar. 

Existen varias formas de calcular la complejidad ciclomática de un programa a partir de un grafo de flujo: 

1. El número de regiones del grafo coincide con la complejidad ciclomática, V(G). 
2. La complejidad ciclomática, V(G), de un grafo de flujo G se define como:
\`V(G) = Aristas – Nodos + 2\`
3. La complejidad ciclomática, V(G), de un grafo de flujo G se define como:
\`V(G) = Nodos Predicado + 1\`

Esta complejidad ciclomática determina el número de casos de prueba que deben ejecutarse para garantizar que todas las sentencias de un programa se han ejecutado al menos una vez, y que cada condición se habrá ejecutado en sus vertientes verdadera y falsa.

### 3.- Determinar el conjunto básico de caminos independientes. 

Un camino independiente es cualquier camino del programa que introduce, por lo menos, un nuevo conjunto de sentencias de proceso o una condición, respecto a los caminos existentes. 

El conjunto de caminos independientes de un grafo no es único. 

Camino 1: 1-10 
Camino 2: 1-2-4-8-1-9 
Camino 3: 1-2-3-5-7-8-1-9 
Camino 4: 1-2-5-6-7-8-1-9 

### 4.- Derivar los casos de prueba que fuerzan la ejecución de cada camino. 

**Fuentes:**
- Wietzenfeld, A. (2005). Ingeniería de Software Orientada a Objetos con UML, Java e Internet. México, D.F.: Thomson.
- Pressman, Roger S. (2010). Estrategia de Pruebas de Software. En Ingeniería de Software un Enfoque Práctico. México, D.F.: McGraw-Hill.

---

# TEST PRUEBAS DE CAJA BLANCA

**NOMBRE DEL ESTUDIANTE**
Gabriel Amílcar Cruz Canto

**EMPRESA**
WALOOK MEXICO, S.A. de C.V.

**TITULO DEL PROYECTO**
Sistema ERP en la nube para gestión de ópticas OMCGC – WALOOK MEXICO, S.A. de C.V.

**PLAN de Pruebas de Caja Blanca: Backend**`;

const dir = 'd:\\_sTIC\\Documents\\_Empresa GraxSofT\\_CODE_\\ERP_WALOOK_PCB\\pcb_analisis';
const files = fs.readdirSync(dir).filter(f => f.startsWith('PCB-') && f.endsWith('_Reporte.md'));

files.forEach(file => {
    const filepath = path.join(dir, file);
    const content = fs.readFileSync(filepath, 'utf8');

    let _id = "PCB-XXX", _mod = "Modulo", _frag = "Fragmento", _hu = "HU-000", _func = "metodo()";
    let _alcance = "Análisis estructural bajo estándar académico";
    let _code = "// Código fuente no encontrado";
    let _resumen = "Validación de caminos y sentencias del procedimiento.";
    let _plant = "";
    let _vg = 0, _p = 0, _aristas_count = 0, _nodos_count = 0;
    let nodos_table = [], caminos_table = [], casos_table = [];

    const is_new = content.includes("Gabriel Amílcar Cruz Canto") && content.includes("Nombre del Módulo del Sistema + Historia de usuario");

    if (!is_new) {
        let m = content.match(/\*\*ID\*\*:\s*(.*?)\n/); if(m) _id = m[1].trim();
        m = content.match(/\*\*Módulo\*\*:\s*(.*?)\n/); if(m) _mod = m[1].trim();
        m = content.match(/\*\*Fragmento\*\*:\s*(.*?)\n/); if(m) _frag = m[1].trim();
        m = content.match(/\*\*HU\*\*:\s*(.*?)\n/); if(m) _hu = m[1].trim();
        m = content.match(/\*\*Función\*\*:\s*(.*?)\n/); if(m) _func = m[1].trim();
        m = content.match(/\*\*Alcance\*\*:\s*(.*?)\n/); if(m) _alcance = m[1].trim();

        const nod_m = content.match(/## B\. Tabla de Nodos(.*?)(?=## C\.)/s);
        if (nod_m) {
            nod_m[1].trim().split('\n').forEach(l => {
                if(l.includes('|') && !l.includes('-|-') && !l.includes(':---') && !l.includes('Nodo |')) {
                    const p = l.split('|').map(x => x.trim()).filter(x => x);
                    if(p.length >= 3) nodos_table.push(`| **Nodo ${p[0]}** | ${p[2]} | ${p[1]} |`);
                }
            });
        }

        m = content.match(/donde\s*\$P\s*=\s*(\d+)/); 
        if(m) _p = parseInt(m[1]);
        _vg = _p + 1;

        const ar_m = content.match(/## C\. Tabla de Aristas(.*?)(?=## D\.)/s);
        if (ar_m) {
            let ac = 0;
            ar_m[1].split('\n').forEach(l => { if(l.includes('|') && !l.includes('Origen') && !l.includes(':---')) ac++; });
            _aristas_count = ac;
        }
        _nodos_count = nodos_table.length;

        const cam_m = content.match(/## E\. Caminos Independientes(.*?)(?=## F\.)/s);
        if (cam_m) {
            cam_m[1].trim().split('\n').forEach(l => {
                let cm = l.match(/\*\*(Camino \d+).*?\*\*:\s*(.*)/);
                if(cm) caminos_table.push(`| **${cm[1]}** | Inicio → ${cm[2].replace(/→/g, ' → ')} → Fin |`);
            });
        }

        const cas_m = content.match(/## F\. Casos de Prueba(.*?)(?=## G\.)/s);
        if (cas_m) {
            let idx = 1;
            cas_m[1].trim().split('\n').forEach(l => {
                if(l.includes('|') && !l.includes('-|-') && !l.includes(':---') && !l.includes('Caso |')) {
                    const p = l.split('|').map(x => x.trim()).filter(x => x);
                    if(p.length >= 3) {
                        const out = p.pop();
                        p.shift(); // remove CPx
                        casos_table.push(`| **Camino ${idx}** | ${p.join(', ')} | ${out} |`);
                        idx++;
                    }
                }
            });
        }

        let cd_m = content.match(/### Fragmento B: Código Anotado.*?```[a-z]*(.*?)```/s);
        if(cd_m) _code = cd_m[1].trim();
        else {
            cd_m = content.match(/```[a-z]*(.*?)```/s);
            if(cd_m) _code = cd_m[1].trim();
        }

        let res_m = content.match(/## J\. Resumen Académico(.*)/s);
        if(res_m) _resumen = res_m[1].trim();

        let pl_m = content.match(/```plantuml(.*?)```/s);
        if(pl_m) _plant = pl_m[1].trim();

    } else {
        // Parse from new format (reorder existing vars)
        let m = content.match(/\*\*Número\*\*\n(.*?) \/ (.*?) \/ (.*?)\n/);
        if(m) { _id = m[1].trim(); _mod = m[2].trim(); _hu = m[3].trim(); }
        
        m = content.match(/\*\*Nombre de la Prueba Backend\*\*\n(.*?)(?:–|\-)(.*?)\n/);
        if (m) { _frag = m[1].trim(); _func = m[2].trim(); }
        else {
            m = content.match(/\*\*Nombre de la Prueba Backend\*\*\n(.*?)\s+([a-zA-Z0-9_\.]+\(.*\))/);
            if(m) { _frag = m[1].trim(); _func = m[2].trim(); }
        }

        m = content.match(/\*\*Descripción\*\*\n(.*?)\n\n/s);
        if(m) _alcance = m[1].trim();

        m = content.match(/### Paso 0\s*```[a-z]*(.*?)```/s);
        if(m) _code = m[1].trim();

        m = content.match(/### Descripción breve del fragmento\s*(.*?)(?=\n### Identificación)/s);
        if(m) _resumen = m[1].trim();

        m = content.match(/### Identificación de Nodos\s*\|.*?\| :---\s*\n(.*?)(?=\n### Paso)/s);
        if(m) nodos_table = m[1].trim().split('\n').filter(x => x.trim().length > 0);

        m = content.match(/### Paso 1\s*```plantuml(.*?)```/s);
        if(m) _plant = m[1].trim();

        m = content.match(/### Paso 2\s*\|.*?\| :---\s*\n(.*?)(?=\n### Paso 3)/s);
        if(m) {
            m[1].split('\n').forEach(l => {
                if (l.includes("Nodos Predicado")) {
                    let vm = l.match(/= V\(G\) = (\d+) \+ 1 = (\d+)/);
                    if(vm) { _p = parseInt(vm[1]); _vg = parseInt(vm[2]); }
                } else if (l.includes("Aristas")) {
                    let am = l.match(/= (\d+) – (\d+) \+ 2 = \d+/);
                    if(am) { _aristas_count = parseInt(am[1]); _nodos_count = parseInt(am[2]); }
                }
            });
        }
        
        m = content.match(/### Paso 3\s*\|.*?\| :---\s*\n(.*?)(?=\n### Paso 4)/s);
        if(m) caminos_table = m[1].trim().split('\n').filter(x => x.trim());

        m = content.match(/### Paso 4\s*\|.*?\| :---\s*\n(.*?)$/s);
        if(m) casos_table = m[1].trim().split('\n').filter(x => x.trim());
    }

    let out_md = `${STATIC_HEADER}\n\n`;
    out_md += `**Número**\n${_id} / ${_mod} / ${_hu}\n\n`;
    out_md += `**Nombre de la Prueba Backend**\n${_frag} – ${_func}\n\n`;
    out_md += `**Descripción**\n${_alcance}\n\n`;
    out_md += "**Fecha**\n16/03/2026\n\n**Responsable**\nGabriel Amílcar Cruz Canto\n\n---\n\n";
    
    out_md += `# FASE DE PRUEBAS ${_id}\n\n`;
    out_md += `**Nombre del Módulo del Sistema + Historia de usuario**\n${_mod} – ${_hu}\n\n`;
    out_md += `**Número y nombre de la Prueba**\n${_id}\n${_frag} – ${_func}\n\n`;
    
    out_md += `### Paso 0\n\n\`\`\`java\n${_code}\n\`\`\`\n\n`;
    
    out_md += `### Descripción breve del fragmento\n\n${_resumen}\n\n`;
    
    out_md += "### Identificación de Nodos\n\n";
    out_md += "| ID del Nodo | Tipo | Descripción |\n| :--- | :--- | :--- |\n";
    nodos_table.forEach(n => out_md += n.trim() + "\n");
    
    out_md += `\n### Paso 1\n\n\`\`\`plantuml\n${_plant}\n\`\`\`\n`;
    out_md += "Diagrama de grafo de propia autoría, creado en plantuml.com\n\n";
    
    out_md += "### Paso 2\n\n| | |\n| :--- | :--- |\n";
    out_md += `| **V(G) = Número de regiones** | = ${_vg} |\n`;
    out_md += `| **V(G) = Aristas – Nodos + 2** | = V(G) = ${_aristas_count} – ${_nodos_count} + 2 = ${_vg} |\n`;
    out_md += `| **V(G) = Nodos Predicado + 1** | = V(G) = ${_p} + 1 = ${_vg} |\n\n`;
    
    out_md += "### Paso 3\n\n| Total, de caminos | Ruta de cada camino |\n| :--- | :--- |\n";
    caminos_table.forEach(c => out_md += c.trim() + "\n");
    
    out_md += "\n### Paso 4\n\n| Número del camino | Caso de Prueba (IN) | Resultado esperado (OUT) |\n| :--- | :--- |\n";
    casos_table.forEach(ca => out_md += ca.trim() + "\n");
    
    fs.writeFileSync(filepath, out_md, 'utf8');
    console.log("Reordenado y Estructurado: " + file);
});
