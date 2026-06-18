package com.example.ms_permiso.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        request.setNombre("CREAR_USUARIO");

        Permiso permisoGuardado = new Permiso();

        permisoGuardado.setId(1L);

        permisoGuardado.setNombre("CREAR_USUARIO");

        when(permisoRepository.existsByNombreIgnoreCase("CREAR_USUARIO"))
            .thenReturn(false);

        when(permisoRepository.save(any(Permiso.class)))
            .thenReturn(permisoGuardado);

            PermisoDTO.Response response = permisoService.crear(request);

                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals("CREAR_USUARIO", response.getNombre());

        verify(permisoRepository).existsByNombreIgnoreCase("CREAR_USUARIO");

        verify(permisoRepository).save(any(Permiso.class));
    }

    @Test
    void
    crear_LanzarError_NombreExistente(){

        PermisoDTO.Request request = new PermisoDTO.Request();

        request.setNombre("CREAR_USUARIO");

        when(permisoRepository.existsByNombreIgnoreCase("CREAR_USUARIO"))
            .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,() -> permisoService.crear(request));

        assertEquals("Ya existe un permiso con ese nombre", exception.getMessage());

        verify(permisoRepository).existsByNombreIgnoreCase("CREAR_USUARIO");

        verify(permisoRepository,never()).save(any(Permiso.class));
    }
    
}
