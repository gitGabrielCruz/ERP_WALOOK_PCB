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
 * PRUEBA DE CAJA BLANCA: PCB-015
 * UNIDAD: UsuarioService.resetPassword()
 * OBJETIVO: Validar el manejo de error cuando el usuario no existe.
 */
public class PCB015_UserResetErrorTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResetPassword_UserNotFound() {
        System.out.println("\n[PCB-015] >>> INICIO DE PRUEBA: Reset Password Error (User Not Found)");

        String invalidId = "UUID-NON-EXISTENT";
        System.out.println("[PCB-015] IN: id=" + invalidId);

        // MOCK: El usuario NO existe en la base de datos
        when(repository.findById(invalidId)).thenReturn(null);

        // EJECUCIÓN
        String resultado = service.resetPassword(invalidId);

        // VERIFICACIÓN
        assertNull(resultado, "El método debería devolver null si el usuario no existe");
        
        // Verificar que no se intentó generar clave ni guardar cambios
        verify(repository, times(1)).findById(invalidId);
        verify(repository, never()).update(any(Usuario.class));

        System.out.println("[PCB-015] OUT: resultado=null (Manejo de error OK)");
        System.out.println("[PCB-015] EVIDENCIA: Camino de excepción por ID inexistente verificado estructuralmente.\n");
    }
}
