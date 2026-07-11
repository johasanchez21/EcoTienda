package com.example.ms_carrito.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_carrito.client.UsuarioClient;
import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.dto.UsuarioDTO;
import com.example.ms_carrito.entity.Carrito;
import com.example.ms_carrito.repository.CarritoRepository;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceImplTest {
    
    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    @Test
    void 
    crearCarrito_CrearCarrito() {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);
        usuario.setRut("12.345.678-8");
        usuario.setNombre("Johann Sanchez");
        usuario.setEmail("johann@gmail.com");
        usuario.setActivo(true);
        usuario.setRol(1L);

        Carrito carritoGuardado = new Carrito();
        carritoGuardado.setId(1L);
        carritoGuardado.setUsuarioId(1L);

        when(usuarioClient.buscarPorId(1L))
                .thenReturn(usuario);

        when(carritoRepository.save(any(Carrito.class)))
                .thenReturn(carritoGuardado);

        CarritoDTO.Response response =
                carritoService.crearCarrito(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUsuario().getId());

        verify(usuarioClient).buscarPorId(1L);
        verify(carritoRepository).save(any(Carrito.class));
    }

    @Test
    void
    crearCarrito_LanzarError_UsuarioNoExiste() {

        when(usuarioClient.buscarPorId(99L))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> carritoService.crearCarrito(99L)
                );

        assertEquals(
                "Usuario no encontrado",
                exception.getMessage()
        );

        verify(carritoRepository, never())
                .save(any(Carrito.class));
    }

    @Test
    void
    obtenerCarritoPorId_RetornarCarrito() {

        Carrito carrito = new Carrito();

        carrito.setId(1L);
        carrito.setUsuarioId(1L);

        when(carritoRepository.findById(1L))
                .thenReturn(Optional.of(carrito));

        CarritoDTO.Response response =
                carritoService.obtenerCarritoPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(carritoRepository)
                .findById(1L);
    }

    @Test
    void
    obtenerCarritoPorUsuario_RetornarCarrito() {

        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(1L);

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);
        usuario.setNombre("Johann");

        when(carritoRepository.findByUsuarioId(1L))
                .thenReturn(Optional.of(carrito));

        when(usuarioClient.buscarPorId(1L))
                .thenReturn(usuario);

        CarritoDTO.Response response =
                carritoService.obtenerCarritoPorUsuario(1L);

        assertNotNull(response);
        assertEquals(1L, response.getUsuario().getId());

        verify(carritoRepository).findByUsuarioId(1L);
        verify(usuarioClient).buscarPorId(1L);
    }

    @Test
    void eliminarCarrito_DeberiaEliminar() {

        Carrito carrito = new Carrito();

        carrito.setId(1L);

        when(carritoRepository.findById(1L))
                .thenReturn(Optional.of(carrito));

        carritoService.eliminarCarrito(1L);

        verify(carritoRepository)
                .delete(carrito);
    }

    @Test
    void 
    crearCarrito_LanzarError_UsuarioYaTieneCarrito() {

        when(carritoRepository.existsByUsuarioId(1L))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> carritoService.crearCarrito(1L)
                );

        assertEquals(
                "El usuario ya posee un carrito",
                exception.getMessage()
        );

        verify(carritoRepository, never())
                .save(any(Carrito.class));
    }
}
