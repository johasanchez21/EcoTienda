package com.example.ms_inventario.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_inventario.client.ProductoClient;
import com.example.ms_inventario.client.TiendaClient;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.ProductoDTO;
import com.example.ms_inventario.dto.TiendaDTO;
import com.example.ms_inventario.entity.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceImplTest {
    
    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private TiendaClient tiendaClient;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @Test
    void
    crear_CrearInventario_ProductoYtiendaNoExiste(){

        InventarioDTO.Request request = new InventarioDTO.Request();

        request.setStock(20);
        request.setProductoId(1L);
        request.setTiendaId(1L);

        ProductoDTO producto = new ProductoDTO();

        producto.setId(1L);
        producto.setNombre("Botella reutilizable");
        producto.setDescripcion("Botella de materiales reutilizables");
        producto.setPrecio(2500);
        producto.setStock(20);
        producto.setActivo(true);
        producto.setCategoria(1L);

        TiendaDTO tienda = new TiendaDTO();

        tienda.setId(1L);
        tienda.setNombre("EcoTienda Lastarria");
        tienda.setDireccion("José Victorino Lastarria 123");
        tienda.setCiudad("Santiago");
        tienda.setHorario("09:00 - 19:00");

        Inventario inventarioGuardado = new Inventario();

        inventarioGuardado.setId(1L);
        inventarioGuardado.setStock(20);
        inventarioGuardado.setProductoId(1L);
        inventarioGuardado.setTiendaId(1L);

        when(inventarioRepository.existsByProductoIdAndTiendaId(1L, 1L))
            .thenReturn(false);

        when(productoClient.buscarPorId(1L))
            .thenReturn(producto);
        
        when(tiendaClient.buscarPorId(1L))
            .thenReturn(tienda);
        
        when(inventarioRepository.save(any(Inventario.class)))
            .thenReturn(inventarioGuardado);
        
        InventarioDTO.Response response =
            inventarioService.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(20, response.getStock());

        verify(inventarioRepository)
            .existsByProductoIdAndTiendaId(1L, 1L);
        
        verify(productoClient)
            .buscarPorId(1L);
        
        verify(tiendaClient)
            .buscarPorId(1L);
        
        verify(inventarioRepository).save(any(Inventario.class));
    }

    @Test
    void
    crear_LanzarError_ProductoYtiendaExiste(){

        InventarioDTO.Request request = new InventarioDTO.Request();

        request.setStock(20);
        request.setProductoId(1L);
        request.setTiendaId(1L);

        when(inventarioRepository.existsByProductoIdAndTiendaId(1L, 1L))
            .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> inventarioService.crear(request)
                );
        assertEquals(
                "Inventario duplicado",
                exception.getMessage()
        );

        verify(inventarioRepository)
            .existsByProductoIdAndTiendaId(1L, 1L);

        verify(inventarioRepository, never())
                .save(any(Inventario.class));

        verifyNoInteractions(productoClient);
        verifyNoInteractions(tiendaClient);
    }

    @Test
    void
    consultarStock_RetornarInventario() {

        Inventario inventario = new Inventario();

        inventario.setId(1L);
        inventario.setProductoId(1L);
        inventario.setTiendaId(1L);
        inventario.setStock(20);

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Botella reutilizable");

        TiendaDTO tienda = new TiendaDTO();
        tienda.setId(1L);
        tienda.setNombre("EcoTienda Lastarria");

        when(inventarioRepository
                .findByProductoIdAndTiendaId(1L, 1L))
                .thenReturn(Optional.of(inventario));

        when(productoClient.buscarPorId(1L))
                .thenReturn(producto);

        when(tiendaClient.buscarPorId(1L))
                .thenReturn(tienda);

        InventarioDTO.Response response =
                inventarioService.consultarStock(1L, 1L);

        assertNotNull(response);
        assertEquals(20, response.getStock());

        verify(inventarioRepository)
                .findByProductoIdAndTiendaId(1L, 1L);
    }

    @Test
    void
    obtenerInventarioPorTienda_RetornarLista() {

        Inventario inventario1 = new Inventario();
        inventario1.setId(1L);
        inventario1.setProductoId(1L);
        inventario1.setTiendaId(1L);
        inventario1.setStock(20);

        Inventario inventario2 = new Inventario();
        inventario2.setId(2L);
        inventario2.setProductoId(2L);
        inventario2.setTiendaId(1L);
        inventario2.setStock(30);

        when(inventarioRepository.findByTiendaId(1L))
                .thenReturn(List.of(inventario1, inventario2));

        List<InventarioDTO.Response> response =
                inventarioService.obtenerInventarioPorTienda(1L);

        assertEquals(2, response.size());

        verify(inventarioRepository)
                .findByTiendaId(1L);
    }

    @Test
    void
    agregarStock_SumarCantidad() {

        Inventario inventario = new Inventario();

        inventario.setId(1L);
        inventario.setStock(20);

        when(inventarioRepository
                .findByProductoIdAndTiendaId(1L, 1L))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(inventario);

        inventarioService.agregarStock(1L, 1L, 20);

        assertEquals(40, inventario.getStock());

        verify(inventarioRepository)
                .save(inventario);
    }

    @Test
    void
    reducirStock_RestarCantidad() {

        Inventario inventario = new Inventario();

        inventario.setId(1L);
        inventario.setStock(20);

        when(inventarioRepository
                .findByProductoIdAndTiendaId(1L, 1L))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(inventario);

        inventarioService.reducirStock(1L, 1L, 10);

        assertEquals(10, inventario.getStock());

        verify(inventarioRepository)
                .save(inventario);
    }

    @Test
    void
    eliminar_EliminarInventario() {

        Inventario inventario = new Inventario();

        inventario.setId(1L);

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        inventarioService.eliminar(1L);

        verify(inventarioRepository)
                .delete(inventario);
    }
}
