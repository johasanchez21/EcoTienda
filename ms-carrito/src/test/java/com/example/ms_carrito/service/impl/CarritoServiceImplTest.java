package com.example.ms_carrito.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_carrito.client.ProductoClient;
import com.example.ms_carrito.client.UsuarioClient;
import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.dto.UsuarioDTO;
import com.example.ms_carrito.entity.Carrito;
import com.example.ms_carrito.repository.CarritoItemRepository;
import com.example.ms_carrito.repository.CarritoRepository;

import jakarta.inject.Inject;

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
        assertEquals("Johann Sanchez", response.getUsuario());

        verify(usuarioClient).buscarPorId(1L);
        verify(carritoRepository).save(any(Carrito.class));
    }


}
