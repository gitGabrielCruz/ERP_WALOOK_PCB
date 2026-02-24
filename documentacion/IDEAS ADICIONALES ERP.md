# 💡 IDEAS ADICIONALES ERP - WALOOK MÉXICO
Este documento sirve como repositorio de backlog, mejoras y correcciones futuras identificadas durante el desarrollo bajo la metodología IDS v5.2.

---

## 📋 REGISTRO DE IDEAS / MEJORAS

| ID | FECHA/HORA | MÓDULO | SECCIÓN | IDEA | DESCRIPCIÓN TÉCNICA / LOG | ESTATUS |
|:---|:---|:---|:---|:---|:---|:---|
| **OPT-001** | 2026-02-22 13:30 | INVENTARIOS | CONTROL | Botón de Auditoría | Implementar un motor de comparación que sume algebraicamente el Kardex y lo compare con la tabla `existencia`. Debe resaltar discrepancias ("Magia del Kardex"). | [/] EN PROCESO |
| **OPT-002** | 2026-02-22 13:30 | INVENTARIOS | CATÁLOGO | Ajuste Operativo vs Contable | Sincronización automática o manual tras auditoría para que el saldo operativo se ajuste al contable (o viceversa) con una póliza de ajuste compensatorio. | [ ] PENDIENTE |
| **OPT-003** | 2026-02-22 13:30 | GENERAL | UI/UX | Monitor de Sesión Extendido | Añadir a `BITACORA_LOG.md` el seguimiento de tiempos de respuesta por cada petición crítica para métricas de performance. | [ ] PENDIENTE |

---

## 🛠️ NOTAS DE INGENIERÍA
- No borrar entradas, marcarlas como `[x] COMPLETADO`.
- Cada entrada debe contener el contexto suficiente para que el agente DevIAn pueda codificarla sin investigación extra.
