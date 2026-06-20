package com.example.ms_pedido.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_pedido.client.ProductoClient;
import com.example.ms_pedido.client.UsuarioClient;
import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.dto.PedidoDetalleDTO;
import com.example.ms_pedido.dto.ProductoDTO;
import com.example.ms_pedido.dto.UsuarioDTO;
import com.example.ms_pedido.entity.Pedido;
import com.example.ms_pedido.entity.PedidoDetalle;
import com.example.ms_pedido.repository.PedidoDetalleRepository;
import com.example.ms_pedido.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Test
    void
    crear_CrearPedidoConDetalles() {

        PedidoDetalleDTO.Request detalleRequest = new PedidoDetalleDTO.Request();
        detalleRequest.setProductoId(1L);
        detalleRequest.setCantidad(2);

        PedidoDTO.Request request = new PedidoDTO.Request();
        request.setUsuarioId(1L);
        request.setEstado("PENDIENTE");
        request.setFecha(new Date());
        request.setDetalles(List.of(detalleRequest));

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);
        usuario.setNombre("Cliente Prueba");

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setPrecio(12990);

        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setId(1L);
        pedidoGuardado.setUsuarioId(1L);
        pedidoGuardado.setEstado("PENDIENTE");
        pedidoGuardado.setFecha(request.getFecha());
        pedidoGuardado.setTotal(25980);

        PedidoDetalle detalleGuardado = new PedidoDetalle();
        detalleGuardado.setId(1L);
        detalleGuardado.setPedidoId(1L);
        detalleGuardado.setProductoId(1L);
        detalleGuardado.setCantidad(2);
        detalleGuardado.setPrecioUnitario(12990);

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuario);
        when(productoClient.buscarPorId(1L)).thenReturn(producto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);
        when(pedidoDetalleRepository.save(any(PedidoDetalle.class))).thenReturn(detalleGuardado);

        PedidoDTO.Response response = pedidoService.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(25980, response.getTotal());
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals(1, response.getDetalles().size());

        verify(usuarioClient).buscarPorId(1L);
        verify(productoClient, atLeastOnce()).buscarPorId(1L);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(pedidoDetalleRepository).save(any(PedidoDetalle.class));
    }

    @Test
    void
    buscarPorId_RetornarPedido() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuarioId(1L);
        pedido.setEstado("PENDIENTE");
        pedido.setFecha(new Date());
        pedido.setTotal(12990);

        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setId(1L);
        detalle.setPedidoId(1L);
        detalle.setProductoId(1L);
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(12990);

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);
        usuario.setNombre("Cliente Prueba");

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setPrecio(12990);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuario);
        when(pedidoDetalleRepository.findByPedidoId(1L)).thenReturn(List.of(detalle));
        when(productoClient.buscarPorId(1L)).thenReturn(producto);

        PedidoDTO.Response response = pedidoService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals(1, response.getDetalles().size());

        verify(pedidoRepository).findById(1L);
        verify(usuarioClient).buscarPorId(1L);
        verify(pedidoDetalleRepository).findByPedidoId(1L);
    }

    @Test
    void
    actualizarEstado_CambiarEstado() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuarioId(1L);
        pedido.setEstado("PENDIENTE");
        pedido.setFecha(new Date());
        pedido.setTotal(12990);

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuario);
        when(pedidoDetalleRepository.findByPedidoId(1L)).thenReturn(List.of());

        PedidoDTO.Response response = pedidoService.actualizarEstado(1L, "PAGADO");

        assertNotNull(response);
        assertEquals("PAGADO", response.getEstado());

        verify(pedidoRepository).save(pedido);
    }

    @Test
    void
    eliminar_EliminarPedidoYDetalles() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        pedidoService.eliminar(1L);

        verify(pedidoDetalleRepository).deleteByPedidoId(1L);
        verify(pedidoRepository).delete(pedido);
    }
}