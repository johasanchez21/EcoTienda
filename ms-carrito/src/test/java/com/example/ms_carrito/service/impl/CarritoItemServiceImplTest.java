package com.example.ms_carrito.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_carrito.client.ProductoClient;
import com.example.ms_carrito.dto.CarritoItemDTO;
import com.example.ms_carrito.dto.ProductoDTO;
import com.example.ms_carrito.entity.Carrito;
import com.example.ms_carrito.entity.CarritoItem;
import com.example.ms_carrito.repository.CarritoItemRepository;
import com.example.ms_carrito.repository.CarritoRepository;

@ExtendWith(MockitoExtension.class)
public class CarritoItemServiceImplTest {
    
    @Mock
    private CarritoItemRepository carritoItemRepository;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private CarritoItemServiceImpl carritoItemService;

    @Test
    void
    agregarProducto_AgregarProductoAlCarrito() {

        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(2L);

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setPrecio(12990);

        CarritoItem itemGuardado = new CarritoItem();
        itemGuardado.setId(1L);
        itemGuardado.setCarritoId(1L);
        itemGuardado.setProductoId(1L);
        itemGuardado.setCantidad(2);
        itemGuardado.setPrecio(12990);

        when(carritoRepository.findById(1L))
                .thenReturn(Optional.of(carrito));

        when(productoClient.buscarPorId(1L))
                .thenReturn(producto);

        when(carritoItemRepository.findByCarritoIdAndProductoId(1L, 1L))
                .thenReturn(Optional.empty());

        when(carritoItemRepository.save(any(CarritoItem.class)))
                .thenReturn(itemGuardado);

        CarritoItemDTO.Response response =
                carritoItemService.agregarProducto(1L, 1L, 2);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(2, response.getCantidad());
        assertEquals(12990, response.getPrecio());
        assertEquals(1L, response.getProducto().getId());

        verify(carritoRepository).findById(1L);
        verify(productoClient).buscarPorId(1L);
        verify(carritoItemRepository).save(any(CarritoItem.class));
    }

    @Test
    void
    agregarProducto_AumentarCantidad_SiProductoYaExiste() {

        Carrito carrito = new Carrito();
        carrito.setId(1L);

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setPrecio(12990);

        CarritoItem itemExistente = new CarritoItem();
        itemExistente.setId(1L);
        itemExistente.setCarritoId(1L);
        itemExistente.setProductoId(1L);
        itemExistente.setCantidad(2);
        itemExistente.setPrecio(12990);

        when(carritoRepository.findById(1L))
                .thenReturn(Optional.of(carrito));

        when(productoClient.buscarPorId(1L))
                .thenReturn(producto);

        when(carritoItemRepository.findByCarritoIdAndProductoId(1L, 1L))
                .thenReturn(Optional.of(itemExistente));

        when(carritoItemRepository.save(any(CarritoItem.class)))
                .thenReturn(itemExistente);

        CarritoItemDTO.Response response =
                carritoItemService.agregarProducto(1L, 1L, 3);

        assertNotNull(response);
        assertEquals(5, itemExistente.getCantidad());

        verify(carritoItemRepository).save(itemExistente);
    }

    @Test
    void
    agregarProducto_LanzarError_CarritoNoExiste() {

        when(carritoRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> carritoItemService.agregarProducto(99L, 1L, 2)
        );

        assertEquals("Carrito no encontrado con id: 99", exception.getMessage());

        verify(carritoItemRepository, never()).save(any(CarritoItem.class));
        verifyNoInteractions(productoClient);
    }

    @Test
    void
    obtenerItemsCarrito_RetornarLista() {

        CarritoItem item1 = new CarritoItem();
        item1.setId(1L);
        item1.setCarritoId(1L);
        item1.setProductoId(1L);
        item1.setCantidad(2);
        item1.setPrecio(12990);

        CarritoItem item2 = new CarritoItem();
        item2.setId(2L);
        item2.setCarritoId(1L);
        item2.setProductoId(2L);
        item2.setCantidad(1);
        item2.setPrecio(3990);

        ProductoDTO producto1 = new ProductoDTO();
        producto1.setId(1L);
        producto1.setNombre("Botella reutilizable");
        producto1.setPrecio(12990);

        ProductoDTO producto2 = new ProductoDTO();
        producto2.setId(2L);
        producto2.setNombre("Cepillo de bambú");
        producto2.setPrecio(3990);

        when(carritoItemRepository.findByCarritoId(1L))
                .thenReturn(List.of(item1, item2));

        when(productoClient.buscarPorId(1L))
                .thenReturn(producto1);

        when(productoClient.buscarPorId(2L))
                .thenReturn(producto2);

        List<CarritoItemDTO.Response> response =
                carritoItemService.obtenerItemsCarrito(1L);

        assertEquals(2, response.size());

        verify(carritoItemRepository).findByCarritoId(1L);
    }

    @Test
    void
    actualizarCantidad_ActualizarCantidad() {

        CarritoItem item = new CarritoItem();
        item.setId(1L);
        item.setProductoId(1L);
        item.setCantidad(2);
        item.setPrecio(12990);

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setPrecio(12990);

        when(carritoItemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        when(carritoItemRepository.save(any(CarritoItem.class)))
                .thenReturn(item);

        when(productoClient.buscarPorId(1L))
                .thenReturn(producto);

        CarritoItemDTO.Response response =
                carritoItemService.actualizarCantidad(1L, 5);

        assertNotNull(response);
        assertEquals(5, response.getCantidad());

        verify(carritoItemRepository).save(item);
    }

    @Test
    void
    eliminarItem_EliminarItem() {

        CarritoItem item = new CarritoItem();
        item.setId(1L);

        when(carritoItemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        carritoItemService.eliminarItem(1L);

        verify(carritoItemRepository).delete(item);
    }

    @Test
    void vaciarCarrito_EliminarItemsDelCarrito() {

        carritoItemService.vaciarCarrito(1L);

        verify(carritoItemRepository).deleteByCarritoId(1L);
    }
}
