@echo off
echo Ejecutando script de creacion de tablas de usuarios...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p123456 < "D:\_sTIC\Documents\_Empresa GraxSofT\_CODE_\ERP_WALOOK\omcgc\database\scripts\02_create_tables_usuarios.sql"
if %ERRORLEVEL% EQU 0 (
    echo Tablas creadas exitosamente!
) else (
    echo Error al crear las tablas
)
pause
