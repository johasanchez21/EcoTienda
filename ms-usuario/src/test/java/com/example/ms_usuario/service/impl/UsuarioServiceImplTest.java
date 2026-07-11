package com.example.ms_usuario.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_usuario.client.RolClient;
import com.example.ms_usuario.dto.RolDTO;
import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.entity.Usuario;
import com.example.ms_usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolClient rolClient;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    
    @Test
    void
    crear_CrearUsuario_RutNoExiste() {

        UsuarioDTO.Request request = new UsuarioDTO.Request();

        request.setRut("12.345.678-8");
        request.setNombre("Johann Sanchez");
        request.setEmail("johann@gmail.com");
        request.setPassword("admin123");
        request.setActivo(true);
        request.setRolId(1L);

        RolDTO rol = new RolDTO();
        rol.setId(1L);
        rol.setNombre("ADMIN");

        Usuario usuarioGuardado = new Usuario();

        usuarioGuardado.setId(1L);
        usuarioGuardado.setRut("12.345.678-8");
        usuarioGuardado.setNombre("Johann Sanchez");
        usuarioGuardado.setEmail("johann@gmail.com");
        usuarioGuardado.setPassword("admin123");
        usuarioGuardado.setActivo(true);
        usuarioGuardado.setRolId(1L);

        when(usuarioRepository.existsByRut("12.345.678-8"))
                .thenReturn(false);

        when(rolClient.buscarPorId(1L))
                .thenReturn(rol);

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioGuardado);

        UsuarioDTO.Response response =
                usuarioService.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Johann Sanchez", response.getNombre());

        verify(usuarioRepository)
                .existsByRut("12.345.678-8");

        verify(rolClient)
                .buscarPorId(1L);

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void 
    crear_LanzarError_RutExiste() {

        UsuarioDTO.Request request = new UsuarioDTO.Request();

        request.setRut("12.345.678-8");
        request.setNombre("Johann Sanchez");
        request.setEmail("johann@gmail.com");
        request.setPassword("admin123");
        request.setActivo(true);
        request.setRolId(1L);

        when(usuarioRepository.existsByRut("12.345.678-8"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> usuarioService.crear(request)
                );

        assertEquals(
                "Ya existe un usuario con ese RUT",
                exception.getMessage()
        );

        verify(usuarioRepository)
                .existsByRut("12.345.678-8");

        verify(usuarioRepository, never())
                .save(any(Usuario.class));

        verifyNoInteractions(rolClient);
    }

    @Test
    void
    activar_CambiarEstado() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setActivo(false);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        usuarioService.activar(1L);

        assertTrue(usuario.getActivo());

        verify(usuarioRepository)
                .save(usuario);
    }

    @Test
    void desactivar_CambiarEstado() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        usuarioService.desactivar(1L);

        assertFalse(usuario.getActivo());

        verify(usuarioRepository)
                .save(usuario);
    }

    @Test
    void
    eliminar_EliminarUsuario() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        usuarioService.eliminar(1L);

        verify(usuarioRepository)
                .delete(usuario);
    }
}
