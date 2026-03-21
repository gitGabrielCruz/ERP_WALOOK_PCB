# TEST PRUEBAS DE CAJA BLANCA - AUTOMATIZADA

| **DATOS DEL ESTUDIANTE** | |
| :--- | :--- |
| **NOMBRE:** | Gabriel Amílcar Cruz Canto |
| **EMPRESA:** | WALOOK MEXICO, S.A. de C.V. |
| **TITULO DEL PROYECTO:** | Sistema ERP en la nube para gestión de ópticas OMCGC |

<br>

| **PLAN DE PRUEBAS DE CAJA BLANCA: BACKEND (MIG-MASTER)** | | | | |
| :--- | :--- | :--- | :--- | :--- |
| **Número** | **Nombre de la Prueba Backend** | **Descripción** | **Fecha** | **Herramienta / Responsable** |
| PCB-001 | Autenticación de usuario | Protocolo de Acceso y Validación de Infraestructura | 09/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-002 | Manejo de Credenciales Inválidas | Interrupción de Seguridad por Fallo de Contraseña | 09/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-003 | Registro de Producto | Validación de Integridad de Campos Obligatorios | 10/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-004 | SKU Autogenerado | Garantía de Unicidad de Identificación Comercial | 10/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-005 | Rango de Fechas (Ventas) | Filtrado de Reporte Operativo de Transacciones | 11/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-006 | Filtro de Sucursal | Segregación de Información por Punto de Venta | 11/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-007 | Kardex de Stock | Protocolo de Integridad Transaccional sobre Saldo | 12/03/2026 | Gabriel Amílcar Cruz Canto |
| PCB-008 | Integridad Fiscal | Validación de Identidad Tributaria y Unicidad RFC | 12/03/2026 | Gabriel Amílcar Cruz Canto |
| :--- | :--- | :--- | :--- | :--- |
| PCB-011 | Registro de Proveedor | Auditoría Estructural de Validación Forense | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-012 | Actualización de Proveedor | Validación de Excepción por RFC Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-013 | Registro de Usuario | Validación de Excepción por Correo Duplicado | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-014 | Baja de Usuario | Validación de Desactivación Lógica (inactivo) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-015 | Reset de Contraseña | Manejo de Excepción por Usuario Inexistente | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-016 | Autenticación Root | Validación de Bypass Administrativo (Local) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-017 | Registro de Movimiento | Validación de Stock Insuficiente (Venta) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-018 | Cálculo de PVP | Validación de Fórmula Financiera (Utilidad) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-019 | Robustez de Auditoría | Normalización de IP Nula (Default 0.0.0.0) | 18/03/2026 | JaCoCo / JUnit 5 |
| PCB-020 | Carga de Diccionario | Validación de Descifrado AES-256 (Binario) | 18/03/2026 | JaCoCo / JUnit 5 |

---

# FASE DE PRUEBAS

| **Nombre del Módulo del Sistema + Historia de usuario** |
| :--- |
| Módulo Proveedores – HU-M05-01 |

| **Número y nombre de la Prueba** |
| :--- |
| PCB-011 / Registro de Proveedor – ProveedorService.create() |

### Paso 0: Súper-Etiquetado del Código (MIG-WBT)

```java
    private void validarProveedor(Proveedor p, boolean esActualizacion) { // [N1: INICIO]
        if (p.getRazonSocial() == null || p.getRazonSocial().trim().isEmpty()) { // [N2] -> [SI: N3] [NO: N4]
            throw new IllegalArgumentException("Razón Social obligatoria."); // [N3: EXC]
        }
        if (p.getRfc() == null || p.getRfc().trim().isEmpty()) { // [N4] -> [SI: N5] [NO: N6]
            throw new IllegalArgumentException("RFC obligatorio."); // [N5: EXC]
        }
        if (p.getCondicionPago() == null || p.getCondicionPago().trim().isEmpty()) { // [N6] -> [SI: N7] [NO: N8]
            throw new IllegalArgumentException("Condición de Pago obligatoria."); // [N7: EXC]
        }
        if (p.getNombreComercial() == null || p.getNombreComercial().trim().isEmpty()) { // [N8] -> [SI: N9] [NO: N10]
            throw new IllegalArgumentException("Nombre Comercial obligatorio."); // [N9: EXC]
        }
        if (p.getCorreo() == null || p.getCorreo().trim().isEmpty()) { // [N10] -> [SI: N11] [NO: N12]
            throw new IllegalArgumentException("Correo obligatorio."); // [N11: EXC]
        }
        if (!p.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // [N12] -> [SI: N13] [NO: N14]
            throw new IllegalArgumentException("Formato email inválido."); // [N13: EXC]
        }
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty()) { // [N14] -> [SI: N15] [NO: N16]
            throw new IllegalArgumentException("Teléfono obligatorio."); // [N15: EXC]
        }
        if (p.getTelefono().length() != 10) { // [N16] -> [SI: N17] [NO: N18]
            throw new IllegalArgumentException("Teléfono debe ser de 10 dígitos."); // [N17: EXC]
        }
        if (p.getRfc().length() < 12) { // [N18] -> [SI: N19] [NO: N20]
            throw new IllegalArgumentException("RFC < 12 caracteres."); // [N19: EXC]
        }
        if (p.getRfc().length() > 13) { // [N20] -> [SI: N21] [NO: N22]
            throw new IllegalArgumentException("RFC > 13 caracteres."); // [N21: EXC]
        }
        if (!p.getRfc().matches("^[A-Z&Ññ]{3,4}\\d{6}[A-Z0-9]{3}$")) { // [N22] -> [SI: N23] [NO: N24]
            throw new IllegalArgumentException("Formato RFC inválido."); // [N23: EXC]
        }
        Proveedor existente = proveedorRepository.findByRfc(p.getRfc()); // [N24]
        if (existente != null) { // [N25] -> [SI: N26] [NO: N30]
            if (esActualizacion) { // [N26] -> [SI: N27] [NO: N29]
                if (!existente.getId().equals(p.getId())) { // [N27] -> [SI: N28] [NO: N30]
                    throw new IllegalArgumentException("RFC ya registrado por otro."); // [N28: EXC]
                }
            } else {
                throw new IllegalArgumentException("RFC ya registrado."); // [N29: EXC]
            }
        }
    } // [N30] -> [F: FIN]
```

### Paso 2: Complejidad Ciclomática McCabe $V(G)$

*   **V(G) = Número de regiones** = (14 internas + 1 externa) = **15**
*   **V(G) = Aristas – Nodos + 2** = (44 – 31 + 2) = **15**
*   **V(G) = Nodos Predicado + 1** = (14 + 1) = **15**

### Paso 3: Caminos Independientes (Basis Paths)

| Camino | Ruta Forense |
| :--- | :--- |
| **C1** | I -> N2(T) -> N3 -> F |
| **C2** | I -> N2(F) -> N4(T) -> N5 -> F |
| **C3** | I -> N2(F) -> N4(F) -> N6(T) -> N7 -> F |
| **C4** | I -> N2(F) -> N4(F) -> N6(F) -> N8(T) -> N9 -> F |
| **C5** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(T) -> N11 -> F |
| **C6** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(T) -> N13 -> F |
| **C7** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(T) -> N15 -> F |
| **C8** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(T) -> N17 -> F |
| **C9** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(T) -> N19 -> F |
| **C10** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(T) -> N21 -> F |
| **C11** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(T) -> N23 -> F |
| **C12** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(T) -> N26(T) -> N27(T) -> N28 -> F |
| **C13** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(T) -> N26(F) -> N29 -> F |
| **C14** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(T) -> N26(T) -> N27(F) -> N30 -> F |
| **C15** | I -> N2(F) -> N4(F) -> N6(F) -> N8(F) -> N10(F) -> N12(F) -> N14(F) -> N16(F) -> N18(F) -> N20(F) -> N22(F) -> N24 -> N25(F) -> N30 -> F |

### Paso 4: Matriz de Automatización (Duda Cero)

| Camino | Caso de Prueba | Resultado |
| :--- | :--- | :--- |
| **C1** | Razón Social Nula | IllegalArgumentException |
| **C2** | RFC Nulo | IllegalArgumentException |
| **C3** | Condición Pago Nula | IllegalArgumentException |
| **C4** | Nombre Comercial Nulo | IllegalArgumentException |
| **C5** | Email Nulo | IllegalArgumentException |
| **C6** | Formato Email Inválido | IllegalArgumentException |
| **C7** | Teléfono Nulo | IllegalArgumentException |
| **C8** | Teléfono Corto | IllegalArgumentException |
| **C9** | RFC Longitud < 12 | IllegalArgumentException |
| **C10** | RFC Longitud > 13 | IllegalArgumentException |
| **C11** | Formato RFC Inválido | IllegalArgumentException |
| **C12** | RFC Duplicado (Otro) | IllegalArgumentException |
| **C13** | RFC Duplicado (Alta) | IllegalArgumentException |
| **C14** | Éxito (Actualización) | **SUCCESS** |
| **C15** | **Éxito (Alta Nueva)** | **SUCCESS** |
