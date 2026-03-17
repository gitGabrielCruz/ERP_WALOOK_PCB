import glob
import re
import os

files = glob.glob(r"d:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK_PCB\pcb_analisis\PCB-*_Reporte.md")

for filepath in files:
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    mappings = {}
    
    # Simple dictionary extraction
    for line in content.split('\n'):
        if 'PCB-N' in line and '|' in line:
            m1 = re.search(r'\|\s*\*\*Nodo\s*(\d+)\*\*\s*\|(.*?)PCB-N(\d+)', line, re.IGNORECASE)
            if m1:
                mappings[f"PCB-N{m1.group(3)}"] = f"Nodo {m1.group(1)}"
                continue
            m2 = re.search(r'\|\s*(\d+)\s*\|(.*?)PCB-N(\d+)', line, re.IGNORECASE)
            if m2:
                mappings[f"PCB-N{m2.group(3)}"] = f"Nodo {m2.group(1)}"

    new_content = content
    
    # 1. Eliminar etiquetas PCB-N1 de PlantUML (ej: "3\nPCB-N1" -> "3")
    new_content = re.sub(r'\\nPCB-N\d+', '', new_content)
    
    # 2. Eliminar comentarios en línea del código
    new_content = re.sub(r'\s*//\s*PCB-N\d+', '', new_content)
    new_content = re.sub(r'\s*/\*\s*PCB-N\d+\s*\*/', '', new_content)
    
    # 3. Eliminar los tags rectos [PCB-Nx] de matrices
    new_content = re.sub(r'\s*\[PCB-N\d+\]', '', new_content)
    
    # 4. Eliminar texto explicativo del tag PCB
    new_content = re.sub(r'\s*Identificado con( la etiqueta)?\s*\*\*PCB-N\d+\*\*\.', '', new_content, flags=re.IGNORECASE)
    new_content = re.sub(r'\s*Identificado con( la etiqueta)?\s*PCB-N\d+\.', '', new_content, flags=re.IGNORECASE)

    # 5. Reemplazar exact matches con su correlación de Nodo
    for pcb, nodestr in mappings.items():
        new_content = re.sub(rf'\b{pcb}\b', nodestr, new_content, flags=re.IGNORECASE)

    # Fallback genérico para limpiar cualquier residual
    new_content = re.sub(r'\bPCB-N(\d+)\b', r'Nodo \1', new_content, flags=re.IGNORECASE)

    if content != new_content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Refactorizado: {os.path.basename(filepath)}")
    else:
        print(f"Sin cambios: {os.path.basename(filepath)}")
