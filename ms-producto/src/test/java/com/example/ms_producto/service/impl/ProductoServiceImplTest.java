package com.example.ms_producto.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_producto.client.CategoriaClient;
import com.example.ms_producto.dto.CategoriaDTO;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.entity.Producto;
import com.example.ms_producto.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaClient categoriaClient;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void
    crear_CrearProducto_CategoriaExiste() {

        ProductoDTO.Request request = new ProductoDTO.Request();

        request.setNombre("Botella reutilizable");
        request.setDescripcion("Botella ecológica");
        request.setPrecio(12990);
        request.setActivo(true);
        request.setCategoriaId(1L);

        CategoriaDTO categoria = new CategoriaDTO();
        categoria.setId(1L);
        categoria.setNombre("Limpieza");

        Producto productoGuardado = new Producto();

        productoGuardado.setId(1L);
        productoGuardado.setNombre("Botella reutilizable");
        productoGuardado.setDescripcion("Botella ecológica");
        productoGuardado.setPrecio(12990);
        productoGuardado.setActivo(true);
        productoGuardado.setCategoriaId(1L);

        when(categoriaClient.buscarPorId(1L))
            .thenReturn(categoria);

        when(productoRepository.save(any(Producto.class)))
            .thenReturn(productoGuardado);

        ProductoDTO.Response response =
                productoService.crear(request);

            assertNotNull(response);
            assertEquals(1L, response.getId());
            assertEquals("Botella reutilizable", response.getNombre());
            
            verify(categoriaClient).buscarPorId(1L);
            verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void
    buscarPorId_RetornarProducto() {

        Producto producto = new Producto();

        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setDescripcion("Botella ecológica");
        producto.setPrecio(12990);
        producto.setActivo(true);
        producto.setCategoriaId(1L);

        CategoriaDTO categoria =
                new CategoriaDTO(1L, "Productos Ecológicos");

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(categoriaClient.buscarPorId(1L))
                .thenReturn(categoria);

        ProductoDTO.Response response =
                productoService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("Botella reutilizable",
                response.getNombre());

        verify(productoRepository).findById(1L);
    }

    @Test
    void
    desactivar_CambiarEstado() {

        Producto producto = new Producto();

        producto.setId(1L);
        producto.setActivo(true);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(producto);

        productoService.desactivar(1L);

        assertFalse(producto.getActivo());

        verify(productoRepository).save(producto);
    }

    @Test
    void
    activar_CambiarEstado() {

        Producto producto = new Producto();

        producto.setId(1L);
        producto.setActivo(false);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(producto);

        productoService.activar(1L);

        assertTrue(producto.getActivo());

        verify(productoRepository).save(producto);
    }

    @Test
    void 
    eliminar_EliminarProducto() {

        Producto producto = new Producto();

        producto.setId(1L);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        productoService.eliminar(1L);

        verify(productoRepository).delete(producto);
    }
}
