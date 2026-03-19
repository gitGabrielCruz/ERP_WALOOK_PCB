package com.omcgc.erp.pcb;

import com.omcgc.erp.model.MovimientoInventario;
import com.omcgc.erp.repository.InventarioRepository;
import com.omcgc.erp.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-017
 * UNIDAD: InventarioService.registrarMovimiento()
 * OBJETIVO: Forzar la excepción de stock insuficiente en una salida.
 */
public class PCB017_InvStockErrorTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrar_StockInsufficient() {
        System.out.println("\n[PCB-017] >>> INICIO DE PRUEBA: Stock Insuficiente (Inventario)");

        // MOVIMIENTO DE SALIDA (Venta)
        MovimientoInventario m = new MovimientoInventario();
        m.setIdProducto("PROD-001");
        m.setIdSucursal("SUC-CENTRO");
        m.setIdUsuario("USR-ADMIN");
        m.setTipoMovimiento("SALIDA_VENTA");
        m.setCantidad(55); // Salida de 55 unidades

        System.out.println("[PCB-017] IN: idProducto=" + m.getIdProducto() + ", cant_solicitada=" + m.getCantidad());

        // MOCK: El stock actual es solo de 10 unidades
        when(repository.getCurrentStock("PROD-001", "SUC-CENTRO")).thenReturn(10);

        // EJECUCIÓN Y VERIFICACIÓN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.registrarMovimiento(m, "127.0.0.1");
        }, "Debería lanzar RuntimeException por stock insuficiente");

        assertTrue(exception.getMessage().contains("Stock insuficiente"), 
                   "Mensaje de error incorrecto: " + exception.getMessage());

        System.out.println("[PCB-017] OUT: Excepción capturada - " + exception.getMessage());
        System.out.println("[PCB-017] EVIDENCIA: Camino de excepción por saldo negativo verificado estructuralmente.\n");
    }
}
