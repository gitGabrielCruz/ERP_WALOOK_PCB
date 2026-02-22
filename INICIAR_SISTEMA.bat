@echo off
setlocal EnableDelayedExpansion

:: ============================================================
:: Nombre del archivo : INICIAR_SISTEMA.bat
:: Ruta              : INICIAR_SISTEMA.bat (Raíz del Proyecto)
:: Tipo              : Script de Automatización (Windows Batch)
::
:: Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
:: Empresa           : WALOOK MEXICO, S.A. de C.V.
::
:: Autor             : Gabriel Amílcar Cruz Canto
:: Matrícula         : ES1821003109
:: Programa          : Licenciatura en Ingeniería en Desarrollo de Software
:: Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
:: Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)
::
:: Versión           : v1.1
::
:: Propósito:
:: Orquestar el proceso de compilación (Maven), limpieza de puertos y despliegue
:: del servidor de aplicaciones backend y apertura del cliente frontend.
:: ============================================================

title SISTEMA ERP WALOOK - INICIO AUTOMATICO (PUERTO 9090)
color 1F

:: 1. CONFIGURACION DE RUTAS
set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.2"
set "MAVEN_HOME=C:\apache-maven-3.9.12-bin\apache-maven-3.9.12"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"

echo ==========================================================
echo      SISTEMA ERP WALOOK - INICIO AUTOMATICO
echo ==========================================================
echo.
echo [1/5] Verificando entorno...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] No se detecta Java. Revisa las rutas.
    pause
    exit /b
)

echo [2/5] Deteniendo procesos anteriores (Java)...
taskkill /F /IM java.exe >nul 2>&1

echo [3/5] Compilando Backend (puede tardar un poco)...
cd omcgc\backend
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo [ERROR] La compilacion fallo.
    pause
    exit /b
)

echo [4/5] Iniciando Servidor en PUERTO 9090...
echo       (Para evitar conflictos con XAMPP/Tomcat 8080)
echo.
echo       El navegador se abrira automaticamente en 20 segundos...
echo.

:: Lanza el navegador
start /min cmd /c "timeout /t 20 >nul && start ..\frontend\pages\login.html"

:: Ejecuta el JAR (Mas estable que mvn spring-boot:run)
java -jar target\omcgc-erp-backend-0.0.1-SNAPSHOT.jar

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] El servidor se detuvo inesperadamente.
    pause
)
