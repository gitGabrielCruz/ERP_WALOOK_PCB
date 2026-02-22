---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** README - Estructura Base del Sistema (Etapa 0)  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# Sistema ERP en la nube para gestión de ópticas OMCGC
## Etapa 0: Preparación técnica del sistema


Este proyecto contiene la estructura base para el desarrollo del ERP de ópticas OMCGC.

### Estructura del proyecto
*   **backend/**: Código fuente de la API REST (Java + Spring Boot).
*   **frontend/**: Interfaz de usuario (HTML/CSS/JS).
*   **database/**: Scripts SQL y modelos de datos.
*   **docs/**: Documentación técnica y anexos.
*   **config/**: Configuraciones de entorno.
*   **tests/**: Pruebas unitarias e integrales.

### Ejecución
1.  **Backend**: Ejecutar `mvn spring-boot:run` desde la carpeta `backend/`.
2.  **Frontend**: Abrir `frontend/index.html` en un navegador web.
3.  **Base de datos**: Ejecutar script `database/scripts/00_drop_create_db.sql` en MySQL.

---
**Nota**: En esta etapa (Etapa 0), el sistema no contiene lógica de negocio.
