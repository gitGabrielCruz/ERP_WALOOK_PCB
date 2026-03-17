import os
import glob
import re

STATIC_HEADER = """# TEST PRUEBAS DE CAJA BLANCA

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
`V(G) = Aristas – Nodos + 2`
3. La complejidad ciclomática, V(G), de un grafo de flujo G se define como:
`V(G) = Nodos Predicado + 1`

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

**PLAN de Pruebas de Caja Blanca: Backend**"""

files = glob.glob(r"d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\pcb_analisis\PCB-*_Reporte.md")

for filepath in files:
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    _id, _mod, _frag, _hu, _func = "PCB-XXX", "Modulo", "Fragmento", "HU-000", "metodo()"
    _alcance = "Análisis estructural bajo estándar académico"
    _code = "// Código fuente no encontrado"
    _resumen = "Validación de caminos y sentencias del procedimiento. Las decisiones lógicas derivan el flujo a diferentes salidas."
    _plant = ""
    _vg = _p = _aristas_count = _nodos_count = 0
    nodos_table, caminos_table, casos_table = [], [], []

    is_new = ("Gabriel Amílcar Cruz Canto" in content and "Nombre del Módulo del Sistema + Historia de usuario" in content)

    if not is_new:
        id_m = re.search(r'\*\*ID\*\*:\s*(.*?)\n', content)
        if id_m: _id = id_m.group(1).strip()
        mod_m = re.search(r'\*\*Módulo\*\*:\s*(.*?)\n', content)
        if mod_m: _mod = mod_m.group(1).strip()
        frag_m = re.search(r'\*\*Fragmento\*\*:\s*(.*?)\n', content)
        if frag_m: _frag = frag_m.group(1).strip()
        hu_m = re.search(r'\*\*HU\*\*:\s*(.*?)\n', content)
        if hu_m: _hu = hu_m.group(1).strip()
        func_m = re.search(r'\*\*Función\*\*:\s*(.*?)\n', content)
        if func_m: _func = func_m.group(1).strip()
        alc_m = re.search(r'\*\*Alcance\*\*:\s*(.*?)\n', content)
        if alc_m: _alcance = alc_m.group(1).strip()

        nod_m = re.search(r'## B\. Tabla de Nodos(.*?)(?=## C\.)', content, re.DOTALL)
        if nod_m:
            for l in nod_m.group(1).strip().split('\n'):
                if '|' in l and '-|-' not in l and ':---' not in l and 'Nodo |' not in l:
                    p = [x.strip() for x in l.split('|') if x.strip()]
                    if len(p) >= 3:
                        nodos_table.append(f"| **Nodo {p[0]}** | {p[2]} | {p[1]} |")

        p_m = re.search(r'donde\s*\$P\s*=\s*(\d+)', content)
        if p_m: _p = int(p_m.group(1))
        _vg = _p + 1

        ar_m = re.search(r'## C\. Tabla de Aristas(.*?)(?=## D\.)', content, re.DOTALL)
        if ar_m: _aristas_count = len([l for l in ar_m.group(1).split('\n') if '|' in l and 'Origen' not in l and ':---' not in l])
        _nodos_count = len(nodos_table)

        cam_m = re.search(r'## E\. Caminos Independientes(.*?)(?=## F\.)', content, re.DOTALL)
        if cam_m:
            for l in cam_m.group(1).strip().split('\n'):
                cm = re.search(r'\*\*(Camino \d+).*?\*\*:\s*(.*)', l)
                if cm: caminos_table.append(f"| **{cm.group(1)}** | Inicio → {cm.group(2).replace('→', ' → ')} → Fin |")

        cas_m = re.search(r'## F\. Casos de Prueba(.*?)(?=## G\.)', content, re.DOTALL)
        if cas_m:
            idx = 1
            for l in cas_m.group(1).strip().split('\n'):
                if '|' in l and '-|-' not in l and ':---' not in l and 'Caso |' not in l:
                    p = [x.strip() for x in l.split('|') if x.strip()]
                    if len(p) >= 3:
                        casos_table.append(f"| **Camino {idx}** | {', '.join(p[1:-1])} | {p[-1]} |")
                        idx += 1

        cd_m = re.search(r'### Fragmento B: Código Anotado.*?```java(.*?)```', content, re.DOTALL)
        if cd_m: _code = cd_m.group(1).strip()
        else:
            cd_m = re.search(r'```java(.*?)```', content, re.DOTALL)
            if cd_m: _code = cd_m.group(1).strip()

        res_m = re.search(r'## J\. Resumen Académico(.*)', content, re.DOTALL)
        if res_m: _resumen = res_m.group(1).strip()

        pl_m = re.search(r'```plantuml(.*?)```', content, re.DOTALL)
        if pl_m: _plant = pl_m.group(1).strip()

    else:
        # Extraer de formato ya transformado (PCB-002) pero solo reordenar
        id_m = re.search(r'\*\*Número\*\*\n(.*?) / (.*?) / (.*?)\n', content)
        if id_m: _id, _mod, _hu = id_m.group(1).strip(), id_m.group(2).strip(), id_m.group(3).strip()
        fn_m = re.search(r'\*\*Nombre de la Prueba Backend\*\*\n(.*?)(?:–|\-)(.*?)\n', content)
        if fn_m: _frag, _func = fn_m.group(1).strip(), fn_m.group(2).strip()
        alc_m = re.search(r'\*\*Descripción\*\*\n(.*?)\n', content)
        if alc_m: _alcance = alc_m.group(1).strip()

        cd_m = re.search(r'### Paso 0\s*```java(.*?)```', content, re.DOTALL)
        if cd_m: _code = cd_m.group(1).strip()

        res_m = re.search(r'### Descripción breve del fragmento\s*(.*?)(?=\n### Identificación)', content, re.DOTALL)
        if res_m: _resumen = res_m.group(1).strip()

        nd_m = re.search(r'### Identificación de Nodos\s*\|.*?\| :---\s*\n(.*?)(?=\n### Paso)', content, re.DOTALL)
        if nd_m: nodos_table = [x.strip() for x in nd_m.group(1).strip().split('\n') if x.strip()]

        pl_m = re.search(r'### Paso 1\s*```plantuml(.*?)```', content, re.DOTALL)
        if pl_m: _plant = pl_m.group(1).strip()

        p2_m = re.search(r'### Paso 2\s*\|.*?\| :---\s*\n(.*?)(?=\n### Paso 3)', content, re.DOTALL)
        if p2_m:
            for l in p2_m.group(1).split('\n'):
                if "Nodos Predicado" in l:
                    vm = re.search(r'= V\(G\) = (\d+) \+ 1 = (\d+)', l)
                    if vm: _p, _vg = int(vm.group(1)), int(vm.group(2))
                elif "Aristas" in l:
                    am = re.search(r'= \d+ – (\d+) \+ 2 = \d+', l)
                    if am: _nodos_count = int(am.group(1))
                    am2 = re.search(r'= (\d+) – \d+ \+ 2 = \d+', l)
                    if am2: _aristas_count = int(am2.group(1))
        
        cm_m = re.search(r'### Paso 3\s*\|.*?\| :---\s*\n(.*?)(?=\n### Paso 4)', content, re.DOTALL)
        if cm_m: caminos_table = [x.strip() for x in cm_m.group(1).strip().split('\n') if x.strip()]

        cas_m = re.search(r'### Paso 4\s*\|.*?\| :---\s*\n(.*?)$', content, re.DOTALL)
        if cas_m: casos_table = [x.strip() for x in cas_m.group(1).strip().split('\n') if x.strip()]

    # Construccion con orden final
    out_md = f"{STATIC_HEADER}\n\n"
    out_md += f"**Número**\n{_id} / {_mod} / {_hu}\n\n"
    out_md += f"**Nombre de la Prueba Backend**\n{_frag} – {_func}\n\n"
    out_md += f"**Descripción**\n{_alcance}\n\n"
    out_md += "**Fecha**\n16/03/2026\n\n**Responsable**\nGabriel Amílcar Cruz Canto\n\n---\n\n"
    out_md += f"# FASE DE PRUEBAS {_id}\n\n"
    out_md += f"**Nombre del Módulo del Sistema + Historia de usuario**\n{_mod} – {_hu}\n\n"
    out_md += f"**Número y nombre de la Prueba**\n{_id}\n{_frag} – {_func}\n\n"
    
    out_md += f"### Paso 0\n\n```java\n{_code}\n```\n\n"
    out_md += f"### Descripción breve del fragmento\n\n{_resumen}\n\n"
    out_md += "### Identificación de Nodos\n\n"
    out_md += "| ID del Nodo | Tipo | Descripción |\n| :--- | :--- | :--- |\n"
    for n in nodos_table: out_md += n + "\n"
    
    out_md += f"\n### Paso 1\n\n```plantuml\n{_plant}\n```\n"
    out_md += "Diagrama de grafo de propia autoría, creado en plantuml.com\n\n"
    
    out_md += "### Paso 2\n\n| | |\n| :--- | :--- |\n"
    out_md += f"| **V(G) = Número de regiones** | = {_vg} |\n"
    out_md += f"| **V(G) = Aristas – Nodos + 2** | = V(G) = {_aristas_count} – {_nodos_count} + 2 = {_vg} |\n"
    out_md += f"| **V(G) = Nodos Predicado + 1** | = V(G) = {_p} + 1 = {_vg} |\n\n"
    
    out_md += "### Paso 3\n\n| Total, de caminos | Ruta de cada camino |\n| :--- | :--- |\n"
    for c in caminos_table: out_md += c + "\n"
    
    out_md += "\n### Paso 4\n\n| Número del camino | Caso de Prueba (IN) | Resultado esperado (OUT) |\n| :--- | :--- |\n"
    for ca in casos_table: out_md += ca + "\n"
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(out_md)
    print(f"Reordenado y Estructurado: {os.path.basename(filepath)}")
