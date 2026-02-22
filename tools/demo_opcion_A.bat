@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

:: ============================================================
::  DEMO OPCION A - Barra de titulo con timer en vivo
::  El titulo se actualiza cada segundo mientras trabajas
:: ============================================================

:: Guardar hora de inicio
for /f "tokens=1-3 delims=:." %%a in ("%TIME: =0%") do (
    set "START_H=%%a"
    set "START_M=%%b"
    set "START_S=%%c"
)
set "START_TIME=%TIME:~0,8%"
set "START_DATE=%DATE%"
set "RECONEXIONES=0"

:: Lanzar el proceso que actualiza el titulo en background
start /b powershell -NoProfile -WindowStyle Hidden -Command ^
  "$startTime = Get-Date; $recon = 0; $pid = $PID; " ^
  "while ($true) { " ^
  "  $elapsed = (Get-Date) - $startTime; " ^
  "  $h = [math]::Floor($elapsed.TotalHours).ToString('00'); " ^
  "  $m = $elapsed.Minutes.ToString('00'); " ^
  "  $s = $elapsed.Seconds.ToString('00'); " ^
  "  $now = Get-Date -Format 'dd/MM/yyyy HH:mm:ss'; " ^
  "  $host.UI.RawUI.WindowTitle = " ^
  "    \"[VPS] Conectado | Uptime: ${h}h ${m}m ${s}s | Reconexiones: $recon | $now\"; " ^
  "  Start-Sleep -Seconds 1 " ^
  "}"

cls
echo.
echo  ============================================================
echo    DEMO OPCION A - Barra de Titulo
echo  ============================================================
echo.
echo    Mira la BARRA DE TITULO de esta ventana ^^^
echo    Se actualiza cada segundo con:
echo      - Tiempo activo (uptime)
echo      - Reconexiones
echo      - Fecha y hora actual
echo.
echo    Esto funciona MIENTRAS usas el shell.
echo    Escribe comandos normalmente:
echo.
echo  ============================================================
echo.

:: Simular que el usuario esta en un shell
cmd /k "echo   [Shell activo - escribe 'exit' para salir] & echo."
