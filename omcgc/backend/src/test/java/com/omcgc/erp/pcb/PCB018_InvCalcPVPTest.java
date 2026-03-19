package com.omcgc.erp.pcb;

import com.omcgc.erp.model.Producto;
import com.omcgc.erp.repository.InventarioRepository;
import com.omcgc.erp.service.BitacoraService;
import com.omcgc.erp.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-018
 * UNIDAD: InventarioService.saveProduct()
 * OBJETIVO: Validar el cálculo automático del Precio de Venta (PVP).
 */
public class PCB018_InvCalcPVPTest {

    @Mock
    private InventarioRepository repository;

    @Mock
    private BitacoraService bitacoraService;

    @InjectMocks
    private InventarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct_CalculatePVP() {
        System.out.println("\n[PCB-018] >>> INICIO DE PRUEBA: Cálculo Dinámico de PVP (Inventario)");

        // PRODUCTO CON COSTO Y UTILIDAD
        Producto p = new Producto();
        p.setNombre("Lente Contacto Premium");
        p.setSku("75000001");
        p.setCostoUnitario(new BigDecimal("100.00"));
        p.setPorcentajeUtilidad(new BigDecimal("30.00")); // 30% utilidad
        p.setIdUsuarioOperacion("USR-ADMIN");

        System.out.println("[PCB-018] IN: costo=" + p.getCostoUnitario() + ", utilidad=" + p.getPorcentajeUtilidad() + "%");

        // EJECUCIÓN
        service.saveProduct(p, "127.0.0.1");

        // VERIFICACIÓN
        // 100 * (1 + 0.30) = 130.00
        assertNotNull(p.getPrecioVenta(), "El Precio de Venta no debería ser nulo");
        assertEquals(new BigDecimal("130.00"), p.getPrecioVenta(), "El cálculo de PVP es incorrecto");
        
        // Verificar que se autogeneró un ID si no tenía
        assertNotNull(p.getIdProducto());

        // Verificar persistencia
        verify(repository, times(1)).save(p);

        System.out.println("[PCB-018] OUT: PVP_Calculado=" + p.getPrecioVenta() + " (Match 100%)");
        System.out.println("[PCB-018] EVIDENCIA: Camino de cálculo financiero verificado estructuralmente.\n");
    }
}
