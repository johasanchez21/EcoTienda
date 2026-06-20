package com.example.ms_envio.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_envio.client.PedidoClient;
import com.example.ms_envio.dto.EnvioDTO;
import com.example.ms_envio.dto.PedidoDTO;
import com.example.ms_envio.entity.Envio;
import com.example.ms_envio.repository.EnvioRepository;

public @ExtendWith(MockitoExtension.class)
class EnvioServiceImplTest {

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private EnvioServiceImpl envioService;

    @Test
    void
    crear_CrearEnvio_CodigoNoExiste() {

        EnvioDTO.Request request = new EnvioDTO.Request();
        request.setCodigoSeguimiento("ENV-0001");
        request.setEmpresa("Chilexpress");
        request.setEstado("PREPARANDO");
        request.setPedidoId(1L);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(25980);

        Envio envioGuardado = new Envio();
        envioGuardado.setId(1L);
        envioGuardado.setCodigoSeguimiento("ENV-0001");
        envioGuardado.setEmpresa("Chilexpress");
        envioGuardado.setEstado("PREPARANDO");
        envioGuardado.setPedidoId(1L);

        when(envioRepository.existsByCodigoSeguimientoIgnoreCase("ENV-0001"))
                .thenReturn(false);

        when(pedidoClient.buscarPorId(1L))
                .thenReturn(pedido);

        when(envioRepository.save(any(Envio.class)))
                .thenReturn(envioGuardado);

        EnvioDTO.Response response = envioService.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ENV-0001", response.getCodigoSeguimiento());
        assertEquals("PREPARANDO", response.getEstado());
        assertEquals(1L, response.getPedido().getId());

        verify(envioRepository).existsByCodigoSeguimientoIgnoreCase("ENV-0001");
        verify(pedidoClient).buscarPorId(1L);
        verify(envioRepository).save(any(Envio.class));
    }

    @Test
    void
    crear_LanzarError_CodigoExiste() {

        EnvioDTO.Request request = new EnvioDTO.Request();
        request.setCodigoSeguimiento("ENV-0001");
        request.setEmpresa("Chilexpress");
        request.setEstado("PREPARANDO");
        request.setPedidoId(1L);

        when(envioRepository.existsByCodigoSeguimientoIgnoreCase("ENV-0001"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> envioService.crear(request)
        );

        assertEquals(
                "Ya existe un envío con ese código de seguimiento",
                exception.getMessage()
        );

        verify(envioRepository).existsByCodigoSeguimientoIgnoreCase("ENV-0001");
        verify(envioRepository, never()).save(any(Envio.class));
        verifyNoInteractions(pedidoClient);
    }

    @Test
    void
    buscarPorId_RetornarEnvio() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setCodigoSeguimiento("ENV-0001");
        envio.setEmpresa("Chilexpress");
        envio.setEstado("PREPARANDO");
        envio.setPedidoId(1L);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setEstado("PENDIENTE");

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        when(pedidoClient.buscarPorId(1L))
                .thenReturn(pedido);

        EnvioDTO.Response response = envioService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("ENV-0001", response.getCodigoSeguimiento());
        assertEquals(1L, response.getPedido().getId());

        verify(envioRepository).findById(1L);
        verify(pedidoClient).buscarPorId(1L);
    }

    @Test
    void
    actualizarEstado_CambiarEstado() {

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setCodigoSeguimiento("ENV-0001");
        envio.setEmpresa("Chilexpress");
        envio.setEstado("PREPARANDO");
        envio.setPedidoId(1L);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        when(envioRepository.save(any(Envio.class)))
                .thenReturn(envio);

        when(pedidoClient.buscarPorId(1L))
                .thenReturn(pedido);

        EnvioDTO.Response response =
                envioService.actualizarEstado(1L, "EN_CAMINO");

        assertNotNull(response);
        assertEquals("EN_CAMINO", response.getEstado());

        verify(envioRepository).save(envio);
    }

    @Test
    void
    eliminar_EliminarEnvio() {

        Envio envio = new Envio();
        envio.setId(1L);

        when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        envioService.eliminar(1L);

        verify(envioRepository).delete(envio);
    }
}
