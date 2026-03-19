package com.omcgc.erp.pcb;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.service.AuthService;
import com.omcgc.erp.service.DatabaseHealthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-016
 * UNIDAD: AuthService.login()
 * OBJETIVO: Validar el mecanismo de bypass root para desarrollo.
 */
public class PCB016_AuthBypassTest {

    @Mock
    private DatabaseHealthService dbHealthService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_RootBypass() {
        System.out.println("\n[PCB-016] >>> INICIO DE PRUEBA: Bypass de Administración (Root)");

        String email = "root";
        String pass = "root";
        
        System.out.println("[PCB-016] IN: email=" + email + ", pass=*****");

        // MOCK: El estado de la DB (aunque sea irrelevante para el bypass, se llama en createSuperAdminUser)
        when(dbHealthService.isConnected()).thenReturn(true);

        // EJECUCIÓN
        Usuario result = authService.login(email, pass);

        // VERIFICACIÓN
        assertNotNull(result, "El bypass debería devolver un usuario");
        assertEquals("root", result.getEmail(), "El email del usuario retornado debe ser root");
        assertEquals("00000000-0000-0000-0000-000000000000", result.getIdUsuario(), "ID de superadmin incorrecto");
        assertTrue(result.getNombre().contains("SUPER ADMIN"), "Nombre de usuario inconsistente con perfil root");

        System.out.println("[PCB-016] OUT: login=EXITOSO (Bypass Activo)");
        System.out.println("[PCB-016] Usuario: " + result.getNombre() + " [UUID: " + result.getIdUsuario() + "]");
        System.out.println("[PCB-016] EVIDENCIA: Camino de bypass administrativo verificado estructuralmente.\n");
    }
}
