@echo off
setlocal DisableDelayedExpansion
:: ============================================================
:: VPS_CONNECT_A.bat v4.0
:: Proyecto: Sistema Web ERP en la nube - OMCGC
:: Proposito: Conexion SSH al VPS con keep-alive y timer
::            en barra de titulo.
:: Autor: Ing. Gabriel Amilcar Cruz Canto
:: Version: 4.0-A (Dashboard en barra de titulo)
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
set "SSH_KEY=%USERPROFILE%\.ssh\id_rsa_vps_erp"
set "RECONEXIONES=0"

:: --- VERIFICAR SSH NATIVO ---
where ssh >nul 2>&1
if '%errorlevel%' NEQ '0' (
    echo.
    echo  [ERROR] 'ssh' no encontrado en el sistema.
    echo  Windows 10+ lo trae por defecto. Verifica en:
    echo  Configuracion - Aplicaciones - Caracteristicas opcionales - OpenSSH Client
    echo.
    pause
    exit /B 1
)

:: --- GENERAR LLAVE SSH SI NO EXISTE ---
if not exist "%SSH_KEY%" (
    echo.
    echo  Generando llave SSH para conexion sin contrasena...
    echo  ============================================================
    ssh-keygen -t rsa -b 4096 -f "%SSH_KEY%" -N "" -C "VPS_ERP_WALOOK"
    if not exist "%SSH_KEY%" (
        echo  [ERROR] No se pudo generar la llave SSH.
        pause
        exit /B 1
    )
    echo  [OK] Llave generada: %SSH_KEY%
    echo.
    echo  Copiando llave al VPS...
    echo  Se necesita plink.exe para la configuracion inicial.
    echo  ============================================================

    if not exist "%TOOLS_DIR%" mkdir "%TOOLS_DIR%"
    if not exist "%TOOLS_DIR%\plink.exe" (
        echo  Descargando plink.exe...
        powershell -NoProfile -ExecutionPolicy Bypass -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://the.earth.li/~sgtatham/putty/latest/w64/plink.exe' -OutFile '%TOOLS_DIR%\plink.exe' -UseBasicParsing; Write-Host '[OK]' } catch { Write-Host '[ERROR]' }"
    )

    if exist "%TOOLS_DIR%\plink.exe" (
        :: Registrar host key
        echo y| "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" %VPS_USER%@%VPS_HOST% "exit" >nul 2>&1
        timeout /t 2 /nobreak >nul

        :: Leer la llave publica y subirla
        powershell -NoProfile -ExecutionPolicy Bypass -Command ^
            "$pubKey = (Get-Content '%SSH_KEY%.pub' -Raw).Trim(); " ^
            "$cmd = 'mkdir -p ~/.ssh; chmod 700 ~/.ssh; echo \"' + $pubKey + '\" >> ~/.ssh/authorized_keys; chmod 600 ~/.ssh/authorized_keys; echo KEY_INSTALLED'; " ^
            "& '%TOOLS_DIR%\plink.exe' -ssh -P %VPS_PORT% -pw '%VPS_PASS%' -batch %VPS_USER%@%VPS_HOST% $cmd"

        echo.
        echo  [OK] Llave SSH configurada.
    ) else (
        echo  [AVISO] No se pudo descargar plink.exe.
        echo  La primera conexion pedira contrasena.
    )
    echo.
    timeout /t 3 /nobreak >nul
)

:: --- GUARDAR HORA DE INICIO ---
for /f "tokens=*" %%T in ('powershell -NoProfile -Command "Get-Date -Format 'dd/MM/yyyy HH:mm:ss'"') do set "START_TIME=%%T"

:: --- BANNER ---
cls
echo.
echo  ============================================================
echo     VPS - ERP WALOOK / OMCGC  [v4.0-A]
echo     DevLabs Division by Graxsoft
echo  ============================================================
echo.
echo   Servidor ........ %VPS_HOST%:%VPS_PORT%
echo   Dominio API ..... api-vps.graxsoft.com
echo   Keep-alive ...... 30s
echo   Inicio .......... %START_TIME%
echo.

:: --- CONVERTIR ARCHIVOS A UNIX ---
echo   [1/4] Preparando archivos...
if not exist "%TOOLS_DIR%" mkdir "%TOOLS_DIR%"
for %%F in (vps_monitor.sh vps_watchdog.sh erp_aliases.sh) do (
    if exist "%TOOLS_DIR%\%%F" (
        powershell -NoProfile -Command "$c=[IO.File]::ReadAllText('%TOOLS_DIR%\%%F');$c=$c-replace\"`r`n\",\"`n\";[IO.File]::WriteAllText('%TEMP%\_%%F',$c,[Text.UTF8Encoding]::new($false))"
    )
)
echo   [OK]

:: --- DETECTAR MODO DE AUTH ---
echo   [2/4] Verificando autenticacion...
ssh -o StrictHostKeyChecking=no -o BatchMode=yes -o ConnectTimeout=5 -i "%SSH_KEY%" -p %VPS_PORT% %VPS_USER%@%VPS_HOST% "echo OK" >nul 2>&1
if '%errorlevel%' EQU '0' (
    set "USE_SSH_KEY=1"
    echo   [OK] Autenticacion por SSH Key
) else (
    set "USE_SSH_KEY=0"
    echo   [!] Usando plink con password como fallback
    if not exist "%TOOLS_DIR%\plink.exe" (
        echo   Descargando plink.exe...
        powershell -NoProfile -ExecutionPolicy Bypass -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://the.earth.li/~sgtatham/putty/latest/w64/plink.exe' -OutFile '%TOOLS_DIR%\plink.exe' -UseBasicParsing } catch { Write-Host '[ERROR]' }"
    )
    reg query "HKCU\Software\SimonTatham\PuTTY\SshHostKeys" /v "rsa2@%VPS_PORT%:%VPS_HOST%" >nul 2>&1
    if '%errorlevel%' NEQ '0' (
        echo y| "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" %VPS_USER%@%VPS_HOST% "exit" >nul 2>&1
        timeout /t 2 /nobreak >nul
    )
)

:: --- SUBIR ARCHIVOS ---
echo   [3/4] Subiendo scripts al VPS...
if "%USE_SSH_KEY%"=="1" (
    for %%F in (_vps_monitor.sh _vps_watchdog.sh) do (
        if exist "%TEMP%\%%F" scp -o StrictHostKeyChecking=no -i "%SSH_KEY%" -P %VPS_PORT% "%TEMP%\%%F" %VPS_USER%@%VPS_HOST%:/tmp/ >nul 2>&1
    )
    if exist "%TEMP%\_erp_aliases.sh" scp -o StrictHostKeyChecking=no -i "%SSH_KEY%" -P %VPS_PORT% "%TEMP%\_erp_aliases.sh" %VPS_USER%@%VPS_HOST%:/root/.erp_aliases >nul 2>&1

    :: Activar watchdog y aliases
    ssh -o StrictHostKeyChecking=no -i "%SSH_KEY%" -p %VPS_PORT% %VPS_USER%@%VPS_HOST% "chmod +x /tmp/*.sh 2>/dev/null; pkill -f vps_watchdog 2>/dev/null; nohup bash /tmp/vps_watchdog.sh >/dev/null 2>&1 & grep -q erp_aliases /root/.bashrc 2>/dev/null || echo '[ -f /root/.erp_aliases ] && source /root/.erp_aliases' >> /root/.bashrc" >nul 2>&1
) else (
    if exist "%TEMP%\_vps_monitor.sh" "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /tmp/vps_monitor.sh" < "%TEMP%\_vps_monitor.sh"
    if exist "%TEMP%\_vps_watchdog.sh" "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /tmp/vps_watchdog.sh" < "%TEMP%\_vps_watchdog.sh"
    if exist "%TEMP%\_erp_aliases.sh" "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /root/.erp_aliases" < "%TEMP%\_erp_aliases.sh"

    "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "chmod +x /tmp/*.sh 2>/dev/null; pkill -f vps_watchdog 2>/dev/null; nohup bash /tmp/vps_watchdog.sh >/dev/null 2>&1 & grep -q erp_aliases /root/.bashrc 2>/dev/null || echo '[ -f /root/.erp_aliases ] && source /root/.erp_aliases' >> /root/.bashrc; echo DONE"
)

del "%TEMP%\_vps_monitor.sh" 2>nul
del "%TEMP%\_vps_watchdog.sh" 2>nul
del "%TEMP%\_erp_aliases.sh" 2>nul
echo   [OK] Watchdog activo

:: --- INICIAR TIMER EN BARRA DE TITULO ---
echo   [4/4] Conectando...

:: Lanzar actualizador de titulo en background
start /b powershell -NoProfile -WindowStyle Hidden -Command ^
    "$startTime = Get-Date; " ^
    "$reconFile = $env:TEMP + '\vps_recon_a.txt'; " ^
    "while ($true) { " ^
    "  $elapsed = (Get-Date) - $startTime; " ^
    "  $h = [math]::Floor($elapsed.TotalHours).ToString('00'); " ^
    "  $m = $elapsed.Minutes.ToString('00'); " ^
    "  $s = $elapsed.Seconds.ToString('00'); " ^
    "  $now = Get-Date -Format 'dd/MM/yyyy HH:mm:ss'; " ^
    "  $r = 0; " ^
    "  if (Test-Path $reconFile) { try { $r = [int](Get-Content $reconFile -Raw).Trim() } catch {} }; " ^
    "  $host.UI.RawUI.WindowTitle = " ^
    "    '[VPS ERP] Uptime: ' + $h + 'h ' + $m + 'm ' + $s + 's | Reconex: ' + $r + ' | ' + $now; " ^
    "  Start-Sleep -Seconds 1 " ^
    "}"

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

:: Inicializar archivo de reconexiones
echo 0 > "%TEMP%\vps_recon_a.txt"

:connect
if "%USE_SSH_KEY%"=="1" (
    ssh -o StrictHostKeyChecking=no -o ServerAliveInterval=30 -o ServerAliveCountMax=3 -i "%SSH_KEY%" -p %VPS_PORT% %VPS_USER%@%VPS_HOST%
) else (
    "%TOOLS_DIR%\plink.exe" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -no-antispoof %VPS_USER%@%VPS_HOST%
)

:: --- RECONEXION ---
set /a RECONEXIONES+=1
echo %RECONEXIONES% > "%TEMP%\vps_recon_a.txt"
echo.
echo  [%date% %time%] Conexion cerrada. Reconexiones: %RECONEXIONES%
echo  Reconectando en 10 segundos... (CTRL+C para cancelar)
echo.
timeout /t 10 /nobreak
goto connect
