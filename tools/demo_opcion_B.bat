@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

:: ============================================================
::  DEMO OPCION B - Ventana separada con dashboard en vivo
::  Una mini-ventana aparte muestra las metricas
:: ============================================================

:: Lanzar ventana de dashboard separada
start "VPS Dashboard" cmd /c powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$host.UI.RawUI.WindowTitle = 'VPS Dashboard - ERP WALOOK'; " ^
  "$startTime = Get-Date; " ^
  "$recon = 2; " ^
  "$startStr = $startTime.ToString('dd/MM/yyyy HH:mm:ss'); " ^
  "while ($true) { " ^
  "  Clear-Host; " ^
  "  $elapsed = (Get-Date) - $startTime; " ^
  "  $h = [math]::Floor($elapsed.TotalHours).ToString('00'); " ^
  "  $m = $elapsed.Minutes.ToString('00'); " ^
  "  $s = $elapsed.Seconds.ToString('00'); " ^
  "  $now = Get-Date -Format 'dd/MM/yyyy HH:mm:ss'; " ^
  "  Write-Host ''; " ^
  "  Write-Host '  ╔══════════════════════════════════════════════╗' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║   VPS Dashboard — ERP WALOOK / OMCGC        ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║   DevLabs Division by Graxsoft               ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ╠══════════════════════════════════════════════╣' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║' -NoNewline -ForegroundColor Cyan; " ^
  "  Write-Host '  Servidor .......... ' -NoNewline -ForegroundColor Gray; " ^
  "  Write-Host '69.6.242.217:22022' -NoNewline -ForegroundColor White; " ^
  "  Write-Host '    ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║' -NoNewline -ForegroundColor Cyan; " ^
  "  Write-Host '  Conexion inicial .. ' -NoNewline -ForegroundColor Gray; " ^
  "  Write-Host $startStr -NoNewline -ForegroundColor Yellow; " ^
  "  Write-Host ' ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║' -NoNewline -ForegroundColor Cyan; " ^
  "  Write-Host '  Hora actual ....... ' -NoNewline -ForegroundColor Gray; " ^
  "  Write-Host $now -NoNewline -ForegroundColor Green; " ^
  "  Write-Host ' ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║' -NoNewline -ForegroundColor Cyan; " ^
  "  Write-Host '  Tiempo activo ..... ' -NoNewline -ForegroundColor Gray; " ^
  "  Write-Host \"${h}h ${m}m ${s}s\" -NoNewline -ForegroundColor Green; " ^
  "  Write-Host '              ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║' -NoNewline -ForegroundColor Cyan; " ^
  "  Write-Host '  Reconexiones ...... ' -NoNewline -ForegroundColor Gray; " ^
  "  Write-Host $recon -NoNewline -ForegroundColor Yellow; " ^
  "  Write-Host '                    ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ║' -NoNewline -ForegroundColor Cyan; " ^
  "  Write-Host '  Estado ............ ' -NoNewline -ForegroundColor Gray; " ^
  "  Write-Host 'CONECTADO' -NoNewline -ForegroundColor Green; " ^
  "  Write-Host '             ║' -ForegroundColor Cyan; " ^
  "  Write-Host '  ╚══════════════════════════════════════════════╝' -ForegroundColor Cyan; " ^
  "  Write-Host ''; " ^
  "  Write-Host '  Actualiza cada segundo. Cierra con Ctrl+C' -ForegroundColor DarkGray; " ^
  "  Start-Sleep -Seconds 1 " ^
  "}"

:: Esperar a que la ventana del dashboard se abra
timeout /t 2 /nobreak >nul

cls
echo.
echo  ============================================================
echo    DEMO OPCION B - Ventana Separada
echo  ============================================================
echo.
echo    Se abrio una SEGUNDA VENTANA con el dashboard en vivo.
echo    Esta ventana es tu shell de trabajo.
echo.
echo    El dashboard corre independiente y se actualiza cada segundo.
echo    Puedes moverlo a un lado de la pantalla.
echo.
echo  ============================================================
echo.

cmd /k "echo   [Shell activo - escribe 'exit' para salir] & echo."
