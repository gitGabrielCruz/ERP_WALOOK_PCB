@echo off
setlocal

:: Configuración de Rutas (Según información del usuario)
set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.2"
set "MAVEN_HOME=C:\apache-maven-3.9.12-bin\apache-maven-3.9.12"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"

echo ==================================================
echo   VALIDACION DE ENTORNO - SISTEMA ERP OMCGC
echo   (Rutas explicitas configuradas temporalmente)
echo ==================================================
echo.

echo [1/3] Verificando JAVA...
java -version
if %errorlevel% neq 0 (
    echo [X] ERROR: No se pudo ejecutar Java. Revisa la ruta: %JAVA_HOME%
    goto :fin
) else (
    echo [OK] Java detectado correctamente.
)
echo.

echo [2/3] Verificando MAVEN...
call mvn -version
if %errorlevel% neq 0 (
    echo [X] ERROR: No se pudo ejecutar Maven. Revisa la ruta: %MAVEN_HOME%
    goto :fin
) else (
    echo [OK] Maven detectado correctamente.
)
echo.

echo [3/3] Intentando compilar el proyecto Backend (Etapa 0)...
cd omcgc\backend
call mvn clean compile
if %errorlevel% neq 0 (
    echo [X] ERROR: La compilacion fallo. Revisa los errores arriba.
) else (
    echo [OK] EL PROYECTO COMPILA CORRECTAMENTE.
    echo.
    echo ==================================================
    echo   ETAPA 0 VALIDADA EXITOSAMENTE
    echo   El sistema esta listo para recibir codigo.
    echo ==================================================
)
cd ..\..

:fin
echo.
pause
