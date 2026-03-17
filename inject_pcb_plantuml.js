const fs = require('fs');
const path = require('path');

const dir = 'd:\\_sTIC\\Documents\\_Empresa GraxSofT\\_CODE_\\ERP_WALOOK_PCB\\pcb_analisis';
const files = fs.readdirSync(dir).filter(f => f.startsWith('PCB-') && f.endsWith('_Reporte.md'));

files.forEach(file => {
    const filepath = path.join(dir, file);
    let content = fs.readFileSync(filepath, 'utf8');

    // Mapeo inverso: Sabemos que en las tablas F y de Nodos existe el concepto de "PCB-N"
    // Pero lo borramos en el paso previo. Afortunadamente restauramos el código base.
    
    // Paso 1: En la tabla de Identificación de Nodos (Paso 0 / anterior), necesitamos 
    // asegurarnos de que el texto mantenga su PCB-N. 
    // Como restauramos el archivo con git y luego le aplicamos reorder_js,
    // el reorder_js que ejecutamos en el paso anterior NO borró los PCB-N, 
    // porque ese fue el script 'purge_pcb.py' que eliminamos.
    
    // Lo único que falta es inyectar el salto de línea en los diagramas PlantUML
    // para que la imagen se compile exactamente como "2 \n PCB-N1" (Imagen 1 del user)
    
    // Buscar en PlantUML los nodos que son predicados según la tabla de nodos
    const match_tabla = content.match(/### Identificación de Nodos(.*?)(?=### Paso 1)/s);
    if (match_tabla) {
        let pcb_map = {}; // { '3': 'PCB-N1', '5': 'PCB-N2' }
        match_tabla[1].split('\n').forEach(line => {
            let m = line.match(/\|\s*\*\*Nodo\s*(\d+)\*\*(?:.*?)(PCB-N\d+)/);
            if (m) {
                pcb_map[m[1]] = m[2];
            }
        });

        // Ahora ir al bloque PlantUML y actualizar las etiquetas de los nodos predicados
        const plant_m = content.match(/```plantuml(.*?)```/s);
        if (plant_m) {
            let plant_content = plant_m[1];
            
            for (const [nodo_num, pcb_n] of Object.entries(pcb_map)) {
                // Buscamos: N3 [label="3"] y lo cambiamos a N3 [label="3\nPCB-N1"]
                let regex = new RegExp(`N${nodo_num}\\s+\\[label="${nodo_num}"\\]`, 'g');
                plant_content = plant_content.replace(regex, `N${nodo_num} [label="${nodo_num}\\n${pcb_n}"]`);
            }
            
            content = content.replace(plant_m[1], plant_content);
        }
    }
    
    fs.writeFileSync(filepath, content, 'utf8');
    console.log("Reinyección PCB-N en PlantUML finalizada: " + file);
});
