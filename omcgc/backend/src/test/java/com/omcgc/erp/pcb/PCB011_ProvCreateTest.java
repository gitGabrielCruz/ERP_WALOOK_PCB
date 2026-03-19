package com.omcgc.erp.pcb;

import com.omcgc.erp.model.Proveedor;
import com.omcgc.erp.repository.ProveedorRepository;
import com.omcgc.erp.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-011
 * UNIDAD: ProveedorService.create()
 * OBJETIVO: Validar el camino de éxito en el registro de un nuevo proveedor.
 * TÉCNICA: JUnit 5 + Mockito + JaCoCo
 */
public class PCB011_ProvCreateTest {

    @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSuccess_CaminoNormal() {
        System.out.println("\n[PCB-011] >>> INICIO DE PRUEBA: Registro Exitoso de Proveedor");
        
        // DATOS DE ENTRADA (MIG-WBT Tabla F)
        Proveedor p = new Proveedor();
        p.setRazonSocial("Lentes AC S.A. de C.V.");
        p.setNombreComercial("Lentes AC");
        p.setRfc("LACC010101ABC");
        p.setEmail("contacto@lentesac.com");
        p.setTelefono("5551234567");
        p.setCondicionPago("Contado");
        p.setEstatus("ACTIVO");

        System.out.println("[PCB-011] IN: RFC=" + p.getRfc() + ", Email=" + p.getEmail());

        // MOCK: El RFC no existe en base de datos
        when(repository.findByRfc("LACC010101ABC")).thenReturn(null);
        // MOCK: Inserción exitosa (1 fila afectada)
        when(repository.save(any(Proveedor.class))).thenReturn(1);

        // EJECUCIÓN
        Proveedor result = service.create(p);

        // VALIDACIÓN (OUT)
        assertNotNull(result, "El proveedor no debería ser nulo tras el registro");
        assertEquals("LACC010101ABC", result.getRfc());
        
        System.out.println("[PCB-011] OUT: Proveedor ID=" + result.getIdProveedor() + " (SUCCESS)");
        System.out.println("[PCB-011] EVIDENCIA: Camino de éxito verificado estructuralmente.\n");
    }
}
