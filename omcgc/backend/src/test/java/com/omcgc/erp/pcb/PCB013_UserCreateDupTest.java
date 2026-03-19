package com.omcgc.erp.pcb;

import com.omcgc.erp.model.Usuario;
import com.omcgc.erp.repository.UsuarioRepository;
import com.omcgc.erp.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * PRUEBA DE CAJA BLANCA: PCB-013
 * UNIDAD: UsuarioService.create()
 * OBJETIVO: Forzar la excepción de correo electrónico duplicado.
 */
public class PCB013_UserCreateDupTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateError_DuplicateEmail() {
        System.out.println("\n[PCB-013] >>> INICIO DE PRUEBA: Correo Electrónico Duplicado (User)");

        // USUARIO NUEVO (A registrar)
        Usuario u = new Usuario();
        u.setNombre("Cajero de Prueba");
        u.setCorreo("caja@test.com");
        u.setUsuario("cajaprueba");

        // USUARIO EXISTENTE EN DB (Colisión)
        Usuario existente = new Usuario();
        existente.setId("UUID-CAJA-01");
        existente.setCorreo("caja@test.com");

        System.out.println("[PCB-013] IN: email=" + u.getCorreo());

        // MOCK: El repositorio encuentra un usuario con ese correo
        when(repository.findByEmail("caja@test.com")).thenReturn(existente);

        // EJECUCIÓN Y VERIFICACIÓN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.create(u);
        }, "Debería lanzar IllegalArgumentException por correo duplicado");

        assertEquals("El correo electrónico ya está registrado", exception.getMessage());

        System.out.println("[PCB-013] OUT: Excepción capturada - " + exception.getMessage());
        System.out.println("[PCB-013] EVIDENCIA: Camino de excepción verificado estructuralmente.\n");
    }
}
