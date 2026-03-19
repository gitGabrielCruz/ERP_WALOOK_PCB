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
 * PRUEBA DE CAJA BLANCA: PCB-014
 * UNIDAD: UsuarioService.delete()
 * OBJETIVO: Validar la desactivación lógica del usuario.
 */
public class PCB014_UserDeleteTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete_LogicalDeactivation() {
        System.out.println("\n[PCB-014] >>> INICIO DE PRUEBA: Desactivación Lógica (User)");

        String userId = "UUID-USR-ACTIVE";
        Usuario u = new Usuario();
        u.setId(userId);
        u.setNombre("Usuario Activo");
        u.setEstatus("activo");

        System.out.println("[PCB-014] IN: id=" + userId + ", current_status=" + u.getEstatus());

        // MOCK: El usuario existe
        when(repository.findById(userId)).thenReturn(u);
        // MOCK: El update devuelve el usuario actualizado
        when(repository.update(any(Usuario.class))).thenReturn(u);

        // EJECUCIÓN
        boolean resultado = service.delete(userId);

        // VERIFICACIÓN
        assertTrue(resultado, "El método delete() debería devolver true");
        assertEquals("inactivo", u.getEstatus(), "El estatus debería haber cambiado a 'inactivo'");
        
        // Verificar que se llamó al repositorio para guardar el cambio
        verify(repository, times(1)).update(u);

        System.out.println("[PCB-014] OUT: status_final=" + u.getEstatus() + ", persistencia=OK");
        System.out.println("[PCB-014] EVIDENCIA: Camino de desactivación verificado estructuralmente.\n");
    }
}
