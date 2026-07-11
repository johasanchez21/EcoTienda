package com.example.ms_permiso.service.impl;

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

import com.example.ms_permiso.repository.PermisoRepository;
import com.example.ms_permiso.dto.PermisoDTO;
import com.example.ms_permiso.entity.Permiso;

@ExtendWith(MockitoExtension.class)
public class PermisoServiceImplTest {

    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoServiceImpl permisoService;

    @Test
    void
    crear_CrearPermiso_NombreNoExiste(){

        PermisoDTO.Request request = new PermisoDTO.Request();

        request.setNombre("CREAR_PERMISO");

        Permiso permisoGuardado = new Permiso();

        permisoGuardado.setId(1L);

        permisoGuardado.setNombre("CREAR_PERMISO");

        when(permisoRepository.existsByNombreIgnoreCase("CREAR_PERMISO"))
            .thenReturn(false);

        when(permisoRepository.save(any(Permiso.class)))
            .thenReturn(permisoGuardado);

            PermisoDTO.Response response = permisoService.crear(request);

                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals("CREAR_PERMISO", response.getNombre());

        verify(permisoRepository).existsByNombreIgnoreCase("CREAR_PERMISO");

        verify(permisoRepository).save(any(Permiso.class));
    }

    @Test
    void
    crear_LanzarError_NombreExistente(){

        PermisoDTO.Request request = new PermisoDTO.Request();

        request.setNombre("CREAR_PERMISO");

        when(permisoRepository.existsByNombreIgnoreCase("CREAR_PERMISO"))
            .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,() -> permisoService.crear(request));

        assertEquals("Ya existe un permiso con ese nombre", exception.getMessage());

        verify(permisoRepository).existsByNombreIgnoreCase("CREAR_PERMISO");

        verify(permisoRepository,never()).save(any(Permiso.class));
    }

    @Test
    void
    buscarPorId_retornaPermiso(){

        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("CREAR_PERMISO");

        when(permisoRepository.findById(1L))
            .thenReturn(Optional.of(permiso));

        PermisoDTO.Response response = permisoService.buscarPorId(1L);
        
        assertNotNull(response);
        assertEquals("CREAR_PERMISO", response.getNombre());

        verify(permisoRepository).findById(1L);

    }


    @Test
    void 
    eliminar_EliminarPermiso() {

        when(permisoRepository.existsById(1L))
                .thenReturn(true);

        permisoService.eliminar(1L);

        verify(permisoRepository).existsById(1L);
        verify(permisoRepository).deleteById(1L);
    }
}
    

