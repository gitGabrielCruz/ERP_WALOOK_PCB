param (
    [string]$InputFile,
    [string]$OutputFile
)

# Verificar si el archivo de entrada existe
if (-not (Test-Path $InputFile)) {
    Write-Error "Archivo de entrada no encontrado: $InputFile"
    return
}

# Iniciar Word
$word = New-Object -ComObject Word.Application
$word.Visible = $false
$doc = $word.Documents.Add()
$selection = $word.Selection

# Función para aplicar estilos con fallback
function Set-MyStyle($styleName, $backupSize, $isBold) {
    try {
        $selection.Style = $styleName
    }
    catch {
        $selection.Font.Size = $backupSize
        if ($isBold) { $selection.Font.Bold = 1 }
    }
}

# Leer el contenido del archivo Markdown (UTF8 para evitar ruidos)
$lines = Get-Content $InputFile -Encoding utf8

foreach ($line in $lines) {
    if ($line -like "# *") {
        # Título Principal (Heading 1 o Título 1)
        Set-MyStyle "Título 1" 18 $true
        $selection.TypeText($line.Substring(2))
        $selection.TypeParagraph()
        $selection.Font.Bold = 0 # Reset bold for next lines
    }
    elseif ($line -like "## *") {
        # Título 2
        Set-MyStyle "Título 2" 14 $true
        $selection.TypeText($line.Substring(3))
        $selection.TypeParagraph()
        $selection.Font.Bold = 0
    }
    elseif ($line -like "### *") {
        # Título 3
        Set-MyStyle "Título 3" 12 $true
        $selection.TypeText($line.Substring(4))
        $selection.TypeParagraph()
        $selection.Font.Bold = 0
    }
    elseif ($line -like "|*|*|") {
        # Tablas: por ahora texto monospaciado simple en la prueba
        $selection.Font.Name = "Courier New"
        $selection.Font.Size = 8
        $selection.TypeText($line)
        $selection.TypeParagraph()
        $selection.Font.Name = "Arial" # Volver a normal
        $selection.Font.Size = 11
    }
    else {
        # Texto normal
        $selection.Style = "Normal"
        $selection.TypeText($line)
        $selection.TypeParagraph()
    }
}

# Guardar y cerrar con la ruta absoluta corregida
$absolutePath = [System.IO.Path]::GetFullPath($OutputFile)
$doc.SaveAs($absolutePath)
$doc.Close()
$word.Quit()

Write-Host "Documento generado exitosamente en: $absolutePath"
