package com.example.ms_tienda.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_tienda.dto.TiendaDTO;
import com.example.ms_tienda.entity.Tienda;
import com.example.ms_tienda.repository.TiendaRepository;

@ExtendWith(MockitoExtension.class)
public class TiendaServiceImplTest {
    
    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaServiceImpl tiendaService;

   @Test
    void
    crear_CrearTienda_DireccionNoExiste() {

        TiendaDTO.Request request = new TiendaDTO.Request();

        request.setNombre("EcoTienda Lastarria");
        request.setDireccion("José Victorino Lastarria 123");
        request.setCiudad("Santiago");
        request.setHorario("09:00 - 19:00");

        Tienda tiendaGuardada = new Tienda();

        tiendaGuardada.setId(1L);
        tiendaGuardada.setNombre("EcoTienda Lastarria");
        tiendaGuardada.setDireccion("José Victorino Lastarria 123");
        tiendaGuardada.setCiudad("Santiago");
        tiendaGuardada.setHorario("09:00 - 19:00");

        when(tiendaRepository.existsByDireccionIgnoreCase(
                "José Victorino Lastarria 123"))
                .thenReturn(false);

        when(tiendaRepository.save(any(Tienda.class)))
                .thenReturn(tiendaGuardada);

        TiendaDTO.Response response =
                tiendaService.crear(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("EcoTienda Lastarria",
                response.getNombre());

        verify(tiendaRepository)
                .existsByDireccionIgnoreCase(
                        "José Victorino Lastarria 123");

        verify(tiendaRepository)
                .save(any(Tienda.class));
    }

    @Test
    void
    crear_LanzarError_DireccionExiste() {

        TiendaDTO.Request request = new TiendaDTO.Request();

        request.setNombre("EcoTienda Lastarria");
        request.setDireccion("José Victorino Lastarria 123");
        request.setCiudad("Santiago");
        request.setHorario("09:00 - 19:00");

        when(tiendaRepository.existsByDireccionIgnoreCase(
                "José Victorino Lastarria 123"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> tiendaService.crear(request)
                );

        assertEquals(
                "Ya existe una tienda con esa dirección",
                exception.getMessage()
        );

        verify(tiendaRepository, never())
                .save(any(Tienda.class));
    }

    @Test
    void buscarPorId_DeberiaRetornarTienda() {

        Tienda tienda = new Tienda();

        tienda.setId(1L);
        tienda.setNombre("EcoTienda Lastarria");
        tienda.setDireccion("José Victorino Lastarria 123");
        tienda.setCiudad("Santiago");
        tienda.setHorario("09:00 - 19:00");

        when(tiendaRepository.findById(1L))
                .thenReturn(Optional.of(tienda));

        TiendaDTO.Response response =
                tiendaService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("EcoTienda Lastarria",
                response.getNombre());

        verify(tiendaRepository)
                .findById(1L);
    }

    @Test
    void listarTodas_DeberiaRetornarLista() {

        Tienda tienda1 = new Tienda();
        tienda1.setId(1L);
        tienda1.setNombre("Tienda 1");

        Tienda tienda2 = new Tienda();
        tienda2.setId(2L);
        tienda2.setNombre("Tienda 2");

        when(tiendaRepository.findAll())
                .thenReturn(List.of(tienda1, tienda2));

        List<TiendaDTO.Response> response =
                tiendaService.listarTodos();

        assertEquals(2, response.size());

        verify(tiendaRepository)
                .findAll();
    }

    @Test
    void 
    eliminar_EliminarTienda() {

        when(tiendaRepository.existsById(1L))
                .thenReturn(true);

        tiendaService.eliminar(1L);

        verify(tiendaRepository).existsById(1L);
        verify(tiendaRepository).deleteById(1L);
    }
   
}
