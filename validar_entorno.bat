@echo off
echo ==================================================
echo   VALIDACION DE ENTORNO - SISTEMA ERP OMCGC
echo ==================================================
echo.

echo [1/3] Verificando JAVA...
java -version
if %errorlevel% neq 0 (
    echo [X] ERROR: Java no esta instalado o no esta en el PATH.
    echo     Por favor instala JDK 17.
    goto :fin
) else (
    echo [OK] Java detectado.
)
echo.

echo [2/3] Verificando MAVEN...
call mvn -version
if %errorlevel% neq 0 (
    echo [X] ERROR: Maven no esta instalado o no esta en el PATH.
    echo     Por favor instala Apache Maven.
    goto :fin
) else (
    echo [OK] Maven detectado.
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
