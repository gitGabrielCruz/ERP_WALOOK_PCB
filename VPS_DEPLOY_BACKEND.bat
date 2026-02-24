@echo off
setlocal DisableDelayedExpansion
:: ============================================================
:: VPS_DEPLOY_BACKEND.bat v1.0
:: Proyecto: Sistema Web ERP en la nube - OMCGC
:: Proposito: Compilar, Subir y Reiniciar Backend en el VPS.
:: ============================================================

:: --- VARIABLES ---
set "VPS_HOST=69.6.242.217"
set "VPS_PORT=22022"
set "VPS_USER=root"
set "VPS_PASS=2-8[oEc=H8!NR*"

set "LOCAL_JAR=omcgc\backend\target\omcgc-erp-backend-0.0.1-SNAPSHOT.jar"
set "REMOTE_JAR=/root/omcgc-erp-backend-0.0.1-SNAPSHOT.jar"
set "PLINK=tools\plink.exe"

:: --- BANNER ---
cls
echo.
echo  ============================================================
echo     Despliegue de Backend - VPS ERP WALOOK
echo  ============================================================
echo.

:: --- VERIFICAR RECURSOS ---
if not exist "%PLINK%" ( echo [ERROR] No existe %PLINK%. Ejecute VPS_CONNECT.bat primero. & pause & exit /B 1 )

:: --- COMPILACION (OPCIONAL/RECOMENDADA) ---
echo  [1/3] Compilando Backend con Maven...
call mvn clean package -DskipTests -f "omcgc\backend\pom.xml"
if %errorlevel% NEQ 0 ( echo [ERROR] Fallo la compilacion. & pause & exit /B 1 )
echo  [OK]

:: --- SUBIR JAR ---
echo  [2/3] Subiendo JAR al VPS (esto puede tardar)...
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > %REMOTE_JAR%" < "%LOCAL_JAR%"
if %errorlevel% NEQ 0 ( echo [ERROR] Fallo la subida. & pause & exit /B 1 )
echo  [OK]

:: --- REINICIAR SERVICIO ---
echo  [3/3] Reiniciando servicio omcgc-erp.service...
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "systemctl restart omcgc-erp.service && echo [OK] Reiniciado exitosamente && systemctl status omcgc-erp.service --no-pager"

echo.
echo  Despliegue Completado con Exito.
echo.
pause
