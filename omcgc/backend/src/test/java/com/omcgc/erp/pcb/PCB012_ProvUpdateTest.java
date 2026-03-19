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
import static org.mockito.Mockito.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-012
 * UNIDAD: ProveedorService.update()
 * OBJETIVO: Forzar la excepción de RFC duplicado durante la actualización.
 */
public class PCB012_ProvUpdateTest {

    @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateError_DuplicateRFC() {
        System.out.println("\n[PCB-012] >>> INICIO DE PRUEBA: Excepción de RFC Duplicado (Update)");

        // PROVEEDOR ACTUAL (A ser actualizado)
        Proveedor p = new Proveedor();
        p.setIdProveedor("P-INTERN-01");
        p.setRazonSocial("Proveedor Uno S.A.");
        p.setNombreComercial("Comercial Uno"); // [PCB-FIX] Agregado para pasar validación previa
        p.setRfc("LACC010101ABC"); 
        p.setEmail("test@test.com");
        p.setTelefono("5551234567");
        p.setCondicionPago("Crédito");


        // PROVEEDOR EXISTENTE EN DB (Colisión)
        Proveedor existente = new Proveedor();
        existente.setIdProveedor("P-EXTERN-02"); // ID Diferente
        existente.setRfc("LACC010101ABC");

        System.out.println("[PCB-012] IN: id=" + p.getIdProveedor() + ", rfc=" + p.getRfc());

        // MOCK: El repositorio encuentra un RFC igual en un ID distinto
        when(repository.findByRfc("LACC010101ABC")).thenReturn(existente);

        // EJECUCIÓN Y VERIFICACIÓN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.update(p);
        }, "Debería lanzar IllegalArgumentException por RFC duplicado");

        assertTrue(exception.getMessage().contains("ya está registrado por otro proveedor"), 
                   "Mensaje de error incorrecto: " + exception.getMessage());

        System.out.println("[PCB-012] OUT: Excepción capturada - " + exception.getMessage());
        System.out.println("[PCB-012] EVIDENCIA: Camino de excepción verificado estructuralmente.\n");
    }
}
