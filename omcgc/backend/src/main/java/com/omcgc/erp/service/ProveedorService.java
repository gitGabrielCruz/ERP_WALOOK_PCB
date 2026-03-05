/*
============================================================
Nombre del archivo : ProveedorService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/ProveedorService.java
Tipo              : Backend (Service)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amilcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Capa de lógica de negocio para Proveedores.
Maneja las validaciones RNF y reglas de negocio antes de persistir datos, 
asegurando la integridad de la cadena de suministro.

Trazabilidad y Mapeo Funcional:
------------------------------------------------------------
1. HU-M05-01 (Registrar proveedor):
   - Validación de reglas de negocio para altas (Unicidad RFC).
2. RNF-06 (Validaciones operativas):
   - Verificación de formatos y campos obligatorios.
3. RF-08 (Gestión de proveedores):
   - Orquestación del flujo de datos entre controlador y repositorio.
============================================================
*/

package com.omcgc.erp.service;

import com.omcgc.erp.model.Proveedor;
import com.omcgc.erp.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Proveedor findById(String id) {
        return proveedorRepository.findById(id);
    }

    public List<Proveedor> search(String query) {
        return proveedorRepository.search(query);
    }

    public List<Proveedor> findByEstatus(String estatus) {
        return proveedorRepository.findByEstatus(estatus);
    }

    /**
     * Resumen (summary):
     * Crea un nuevo proveedor aplicando validaciones de negocio.
     * 
     * Excepciones (throws):
     * 
     * @throws IllegalArgumentException Si el RFC ya existe o los datos son
     *                                  inválidos.
     */
    public Proveedor create(Proveedor proveedor) {
        validarProveedor(proveedor, false); // false = es nuevo

        int resultado = proveedorRepository.save(proveedor);
        if (resultado > 0) {
            return proveedor; // Retorna el objeto (con ID generado si aplica)
        }
        throw new RuntimeException("Error al insertar el proveedor en base de datos.");
    }

    /**
     * Resumen (summary):
     * Actualiza un proveedor existente.
     * 
     * Excepciones (throws):
     * 
     * @throws IllegalArgumentException Si el RFC duplicado pertenece a otro
     *                                  registro.
     */
    public Proveedor update(Proveedor proveedor) {
        validarProveedor(proveedor, true); // true = es actualización

        int resultado = proveedorRepository.update(proveedor);
        if (resultado > 0) {
            return proveedor;
        }
        throw new RuntimeException(
                "No se pudo actualizar el proveedor. Posiblemente no existe ID: " + proveedor.getIdProveedor());
    }

    /**
     * Resumen (summary):
     * Desactiva un proveedor.
     */
    public boolean delete(String id) {
        return proveedorRepository.deleteLogical(id) > 0;
    }

    // ==========================================
    // REGLAS DE NEGOCIO PRIVADAS
    // ==========================================

    private void validarProveedor(Proveedor p, boolean esActualizacion) {
        // 1. Validar campos obligatorios básicos
        if (p.getRazonSocial() == null || p.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Razón Social: La Razón Social es obligatoria.");
        }
        if (p.getRfc() == null || p.getRfc().trim().isEmpty()) {
            throw new IllegalArgumentException("RFC: El RFC es obligatorio.");
        }
        if (p.getCondicionPago() == null || p.getCondicionPago().trim().isEmpty()) {
            throw new IllegalArgumentException("Condición de Pago: Debe seleccionar una Condición de Pago.");
        }

        // 2. Validar Nombre Comercial (obligatorio)
        if (p.getNombreComercial() == null || p.getNombreComercial().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre Comercial: El Nombre Comercial es obligatorio.");
        }

        // 3. Validar Email (obligatorio y formato válido)
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Correo Electrónico: El correo electrónico es obligatorio.");
        }
        String emailPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!p.getEmail().matches(emailPattern)) {
            throw new IllegalArgumentException(
                    "Correo Electrónico: El formato del correo no es válido (ej: ejemplo@dominio.com).");
        }

        // 4. Validar Teléfono (obligatorio y debe tener 10 dígitos)
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("Teléfono: El teléfono es obligatorio.");
        }
        String telefonoLimpio = p.getTelefono().replaceAll("\\D", "");
        if (telefonoLimpio.length() != 10) {
            throw new IllegalArgumentException("Teléfono: El teléfono debe contener exactamente 10 dígitos.");
        }

        // 5. Validar Longitud y Formato RFC (12 o 13 caracteres)
        String rfcLimpio = p.getRfc().trim().toUpperCase();
        p.setRfc(rfcLimpio);

        if (rfcLimpio.length() < 12 || rfcLimpio.length() > 13) {
            throw new IllegalArgumentException("RFC: El RFC debe tener 12 o 13 caracteres.");
        }

        // Validar formato RFC con regex
        // Formato: 3-4 letras + 6 dígitos + 3 caracteres alfanuméricos
        String rfcPattern = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$";
        if (!rfcLimpio.matches(rfcPattern)) {
            throw new IllegalArgumentException("RFC: El formato del RFC no es válido (ej: ABC123456789).");
        }

        // 6. Validar Unicidad del RFC
        Proveedor existente = proveedorRepository.findByRfc(rfcLimpio);
        if (existente != null) {
            if (esActualizacion) {
                // Si es update, permitir si el ID es el mismo
                if (!existente.getIdProveedor().equals(p.getIdProveedor())) {
                    throw new IllegalArgumentException(
                            "RFC: El RFC " + rfcLimpio + " ya está registrado por otro proveedor.");
                }
            } else {
                // Si es nuevo, error directo
                throw new IllegalArgumentException("RFC: El RFC " + rfcLimpio + " ya está registrado.");
            }
        }
    }
}
