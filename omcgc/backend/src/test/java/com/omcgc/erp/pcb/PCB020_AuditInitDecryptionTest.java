package com.omcgc.erp.pcb;

import com.omcgc.erp.model.LogPattern;
import com.omcgc.erp.service.AuditPatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-020
 * UNIDAD: AuditPatternService.init() / loadDictionary()
 * OBJETIVO: Verificar el descifrado y carga exitosa de la matriz maestra.
 */
public class PCB020_AuditInitDecryptionTest {

    private AuditPatternService service;

    @BeforeEach
    void setUp() {
        service = new AuditPatternService();
        // Nota: El init() se dispara solo por @PostConstruct en Spring, 
        // aquí lo llamaremos manualmente tras instanciar.
    }

    @Test
    void testInit_DecryptionAndLoad() {
        System.out.println("\n[PCB-020] >>> INICIO DE PRUEBA: Inicialización Cifrada (Audit Dictionary)");

        // EJECUCIÓN: Esto genera el .dat cifrado y luego lo carga descifrado.
        service.init();

        // VERIFICACIÓN vía Reflection (ya que el map es privado)
        @SuppressWarnings("unchecked")
        Map<String, LogPattern> patterns = (Map<String, LogPattern>) ReflectionTestUtils.getField(service, "patterns");

        assertNotNull(patterns, "El mapa de patrones no debe ser nulo");
        assertFalse(patterns.isEmpty(), "El diccionario debería contener patrones cargados");
        
        // Verificar existencia de patrones clave
        assertTrue(patterns.containsKey("AUTH-01"), "Falta el patrón AUTH-01");
        assertTrue(patterns.containsKey("INV-01"), "Falta el patrón INV-01");
        assertTrue(patterns.containsKey("SYS-99"), "Falta el patrón SYS-99");

        System.out.println("[PCB-020] OUT: patterns_count=" + patterns.size() + " (Descifrado OK)");
        System.out.println("[PCB-020] EVIDENCIA: Integridad de la Matriz Maestra cifrada verificada estructuralmente.\n");
    }
}
