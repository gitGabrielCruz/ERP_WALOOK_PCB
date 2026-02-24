@echo off
setlocal DisableDelayedExpansion
:: ============================================================
:: VPS_UPDATE_DB.bat v1.0
:: Proyecto: Sistema Web ERP en la nube - OMCGC
:: Proposito: Sincronizar Esquemas y Datos Semilla en la Nube.
:: ============================================================

:: --- VARIABLES ---
set "VPS_HOST=69.6.242.217"
set "VPS_PORT=22022"
set "VPS_USER=root"
set "VPS_PASS=2-8[oEc=H8!NR*"
set "DB_USER=graxsof3_omcgc"
set "DB_PASS=Walook_2026!SecureDB"
set "DB_NAME=graxsof3_omcgc"

set "PLINK=tools\plink.exe"

:: --- BANNER ---
cls
echo.
echo  ============================================================
:: No use accent marks in batch files to avoid encoding issues
echo     Sincronizacion de Base de Datos - VPS ERP WALOOK
echo  ============================================================
echo.

:: --- VERIFICAR ARCHIVOS ---
if not exist "creaciontablas_nube.sql" ( echo [ERROR] No existe creaciontablas_nube.sql & pause & exit /B 1 )
if not exist "registrospruebas_nube.sql" ( echo [ERROR] No existe registrospruebas_nube.sql & pause & exit /B 1 )
if not exist "%PLINK%" ( echo [ERROR] No existe %PLINK%. Ejecute VPS_CONNECT.bat primero. & pause & exit /B 1 )

:: --- SUBIR ARCHIVOS ---
echo  [1/2] Subiendo archivos SQL al VPS...
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /tmp/creaciontablas_nube.sql" < "creaciontablas_nube.sql"
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "cat > /tmp/registrospruebas_nube.sql" < "registrospruebas_nube.sql"
echo  [OK]

:: --- EJECUTAR EN MYSQL ---
echo  [2/2] Ejecutando scripts en MariaDB/MySQL (Cloud)...
echo  (Paso 1: Esquemas)
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "mysql -u %DB_USER% -p'%DB_PASS%' %DB_NAME% < /tmp/creaciontablas_nube.sql && echo [OK SCHEMA]"
echo.
echo  (Paso 2: Datos Semilla)
"%PLINK%" -ssh -P %VPS_PORT% -pw "%VPS_PASS%" -batch %VPS_USER%@%VPS_HOST% "mysql -u %DB_USER% -p'%DB_PASS%' %DB_NAME% < /tmp/registrospruebas_nube.sql && echo [OK DATA]"

echo.
echo  Sincronizacion Terminada Exitosamente.
echo.
pause
