/*
============================================================
Nombre del archivo : ClienteService.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/service/ClienteService.java
Tipo              : Backend (Servicio de Negocio)

Proyecto          : Sistema ERP en la nube para gestión de ópticas OMCGC
Empresa           : WALOOK MEXICO, S.A. de C.V.

Autor             : Gabriel Amílcar Cruz Canto
Matrícula         : ES1821003109
Programa          : Licenciatura en Ingeniería en Desarrollo de Software
Unidad didáctica  : Proyecto Terminal I / Proyecto Terminal II
Periodo académico : PT1 – PT2 (Agosto 2025 – Enero 2026)

Versión           : v1.0

Propósito:
Lógica de negocio para la gestión de clientes (pacientes).
Controla el flujo de registro, validaciones de integridad fiscal
(RFC único, formato) y coherencia de datos.

Trazabilidad:
- Reglas de negocio para HU-M06-01 (RNF-06: Validaciones de RFC/Email).
- Coordina repositorio para HU-M06-02 (Búsqueda).
============================================================
*/
package com.omcgc.erp.service;

import com.omcgc.erp.model.Paciente;
import com.omcgc.erp.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> buscarClientes(String busqueda, String rfc, String estatus) {
        return pacienteRepository.findByFiltros(busqueda, rfc, estatus);
    }

    public Paciente obtenerPorId(String id) {
        return pacienteRepository.findById(id);
    }

    public Paciente guardarCliente(Paciente cliente) {
        // Validaciones de Negocio
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        // Validación RFC Unico (si aplica)
        if (cliente.getRfc() != null && !cliente.getRfc().isEmpty()) {
            Paciente existente = pacienteRepository.findByRfc(cliente.getRfc());
            if (existente != null && !existente.getIdPaciente().equals(cliente.getIdPaciente())) {
                throw new IllegalArgumentException("El RFC ya está registrado con otro cliente.");
            }
        }

        // Formato Apellidos (si viene null por ser Persona Moral, poner string vacío)
        if (cliente.getApellidos() == null) {
            cliente.setApellidos("");
        }

        if (cliente.getEstatus() == null) {
            cliente.setEstatus("ACTIVO");
        }

        pacienteRepository.save(cliente);
        return cliente;
    }
}
