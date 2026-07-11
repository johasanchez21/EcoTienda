package com.example.ms_rol.service.impl;

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

import com.example.ms_rol.client.PermisoClient;
import com.example.ms_rol.dto.PermisoDTO;
import com.example.ms_rol.dto.RolDTO;
import com.example.ms_rol.entity.Rol;
import com.example.ms_rol.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
public class RolServiceImplTest {
    
    @Mock
    private RolRepository rolRepository;

    @Mock
    private PermisoClient permisoClient;

    @InjectMocks
    private RolServiceImpl rolService;

    @Test
    void
    crear_crearRol_PermisoExiste(){
        
        RolDTO.Request request = new RolDTO.Request();

        request.setNombre("Admin");
        request.setPermisoId(1L);

        PermisoDTO permiso = new PermisoDTO();
        permiso.setId(1L);
        permiso.setNombre("Permisos admin");

        Rol rolGuardado = new Rol();
        rolGuardado.setId(1L);
        rolGuardado.setNombre("Admin");
        rolGuardado.setPermisoId(1L);

        when(rolRepository.existsByNombreIgnoreCase("Admin"))
            .thenReturn(false);
            when(permisoClient.buscarPorId(1L))
                .thenReturn(permiso);
        
        when(rolRepository.save(any(Rol.class)))
            .thenReturn(rolGuardado);
            
            RolDTO.Response response = rolService.crear(request);

                assertNotNull(response);
                assertEquals(1L, response.getId());
                assertEquals("Admin", response.getNombre());

                verify(permisoClient).buscarPorId(1L);

                verify(rolRepository).save(any(Rol.class));
    }

    @Test
    void
    crear_LanzarError_RolExistente(){

        RolDTO.Request request = new RolDTO.Request();

        request.setNombre("Admin");
        request.setPermisoId(1L);

        when(rolRepository.existsByNombreIgnoreCase("Admin"))
            .thenReturn(true);
            
            RuntimeException exception = assertThrows(RuntimeException.class,() -> rolService.crear(request));

            assertEquals("Ya existe un rol con ese nombre", exception.getMessage());

            verify(rolRepository,never()).save(any(Rol.class));
    }

    @Test
    void
    buscarPorId_retornaRol(){

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setPermisoId(1L);

        PermisoDTO permiso = new PermisoDTO();
        permiso.setId(1L);
        permiso.setNombre("Permisos admin");

        when(rolRepository.findById(1L))
            .thenReturn(Optional.of(rol));

        when(permisoClient.buscarPorId(1L))
            .thenReturn(permiso);
        
        RolDTO.Response response = rolService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("Admin", response.getNombre());

        verify(rolRepository).findById(1L);
    }

    @Test
    void
    eliminar_EliminarRol(){

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Admin");

        when(rolRepository.findById(1L))
            .thenReturn(Optional.of(rol));

        rolService.eliminar(1L);

        verify(rolRepository).delete(rol);
    }
}
