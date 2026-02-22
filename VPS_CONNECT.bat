@echo off
setlocal DisableDelayedExpansion
:: ============================================================
:: VPS_CONNECT.bat v3.2
:: Proyecto: Sistema Web ERP en la nube - OMCGC
:: Proposito: Conexion SSH directa al VPS con watchdog.
:: Autor: Ing. Gabriel Amilcar Cruz Canto
:: Version: 3.2
:: Fecha: 19 de Febrero de 2026
:: ============================================================

:: --- AUTO-ELEVACION ---
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' ( goto UACPrompt ) else ( goto gotAdmin )
:UACPrompt
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin_vps.vbs"
    echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin_vps.vbs"
    "%temp%\getadmin_vps.vbs"
    exit /B
:gotAdmin
    if exist "%temp%\getadmin_vps.vbs" del "%temp%\getadmin_vps.vbs"
    pushd "%CD%"
    CD /D "%~dp0"

:: --- VARIABLES ---
set "VPS_HOST=69.6.242.217"
set "VPS_PORT=22022"
set "VPS_USER=root"
set "VPS_PASS=2-8[oEc=H8!NR*"
set "TOOLS_DIR=%~dp0tools"
set "PLINK=%TOOLS_DIR%\plink.exe"

:: --- DESCARGAR PLINK ---
if not exist "%TOOLS_DIR%" mkdir "%TOOLS_DIR%"
if not exist "%PLINK%" (
    echo  Descargando plink.exe...
    powershell -NoProfile -ExecutionPolicy Bypass -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://the.earth.li/~sgtatham/putty/latest/w64/plink.exe' -OutFile '%PLINK%' -UseBasicParsing; Write-Host '[OK]' } catch { Write-Host '[ERROR]' }"
    if not exist "%PLINK%" ( echo  Fallo. & pause & exit /B 1 )
)

:: --- HOST KEY ---
reg query "HKCU\Software\SimonTatham\PuTTY\SshHostKeys" /v "rsa2@%VPS_PORT%:%VPS_HOST%" >nul 2>&1
if '%errorlevel%' NEQ '0' (
    echo  Registrando host key...
    echo y| "%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" %VPS_USER%@%VPS_HOST% "exit" >nul 2>&1
    timeout /t 2 /nobreak >nul
)

:: --- BANNER ---
cls
echo.
echo  ============================================================
echo     VPS - ERP WALOOK / OMCGC  [v3.2]
echo     DevLabs Division by Graxsoft
echo  ============================================================
echo.
echo   Servidor .... %VPS_HOST%:%VPS_PORT%
echo   Dominio API . api-vps.graxsoft.com
echo.

:: --- CONVERTIR ARCHIVOS A UNIX (sin CRLF) ---
echo   [1/4] Preparando archivos...
for %%F in (vps_monitor.sh vps_watchdog.sh erp_aliases.sh) do (
    if exist "%TOOLS_DIR%\%%F" (
        powershell -NoProfile -Command "$c=[IO.File]::ReadAllText('%TOOLS_DIR%\%%F');$c=$c-replace\"`r`n\",\"`n\";[IO.File]::WriteAllText('%TEMP%\_%%F',$c,[Text.UTF8Encoding]::new($false))"
    )
)
echo   [OK]

:: --- SUBIR ARCHIVOS ---
echo   [2/4] Subiendo scripts al VPS...

if exist "%TEMP%\_vps_monitor.sh" (
    "%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /tmp/vps_monitor.sh" < "%TEMP%\_vps_monitor.sh"
)
if exist "%TEMP%\_vps_watchdog.sh" (
    "%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /tmp/vps_watchdog.sh" < "%TEMP%\_vps_watchdog.sh"
)
if exist "%TEMP%\_erp_aliases.sh" (
    "%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /root/.erp_aliases" < "%TEMP%\_erp_aliases.sh"
)

:: Limpiar temp
del "%TEMP%\_vps_monitor.sh" 2>nul
del "%TEMP%\_vps_watchdog.sh" 2>nul
del "%TEMP%\_erp_aliases.sh" 2>nul

echo   [OK]

:: --- ACTIVAR WATCHDOG Y ALIASES ---
echo   [3/4] Activando watchdog y aliases...
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "chmod +x /tmp/vps_monitor.sh /tmp/vps_watchdog.sh 2>/dev/null; pkill -f vps_watchdog 2>/dev/null; nohup bash /tmp/vps_watchdog.sh > /dev/null 2>&1 & grep -q erp_aliases /root/.bashrc 2>/dev/null || echo '[ -f /root/.erp_aliases ] && source /root/.erp_aliases' >> /root/.bashrc; echo DONE"
echo   [OK] Watchdog activo (verifica cada 60s)

:: --- CONECTAR ---
echo   [4/4] Conectando al shell...
echo.
echo  ============================================================
echo   COMANDOS RAPIDOS:
echo     monitor          Dashboard en tiempo real
echo     status           Estado servicios
echo     logs             Log del backend
echo     wdlog            Log del watchdog
echo     restart-backend  Reiniciar backend
echo  ============================================================
echo.

:connect
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -no-antispoof %VPS_USER%@%VPS_HOST%

:: --- RECONEXION ---
echo.
echo  [%date% %time%] Conexion cerrada.
echo  Reconectando en 10 segundos... (CTRL+C para cancelar)
echo.
timeout /t 10 /nobreak
goto connect
