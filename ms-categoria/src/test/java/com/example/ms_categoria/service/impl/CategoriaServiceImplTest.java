package com.example.ms_categoria.service.impl;

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

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.entity.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void
    crear_CrearCategoria_NombreNoExiste(){
        CategoriaDTO.Request request = new CategoriaDTO.Request();

        request.setNombre("CREAR_CATEGORIA");

        Categoria categoriaGuardado = new Categoria();

        categoriaGuardado.setId(1L);

        categoriaGuardado.setNombre("CREAR_CATEGORIA");

        when(categoriaRepository.existsByNombreIgnoreCase("CREAR_CATEGORIA"))
            .thenReturn(false);

        when(categoriaRepository.save(any(Categoria.class)))
            .thenReturn(categoriaGuardado);

            CategoriaDTO.Response response = categoriaService.crear(request);

            assertNotNull(response);
            assertEquals(1L, response.getId());
            assertEquals("CREAR_CATEGORIA", response.getNombre());
        
            verify(categoriaRepository).existsByNombreIgnoreCase("CREAR_CATEGORIA");

            verify(categoriaRepository).save(any(Categoria.class));

    }

    @Test
    void
    crear_LanzarError_NombreExistente(){
        
        CategoriaDTO.Request request = new CategoriaDTO.Request();

        request.setNombre("CREAR_CATEGORIA");

        when(categoriaRepository.existsByNombreIgnoreCase("CREAR_CATEGORIA"))
            .thenReturn(true);
        
        RuntimeException exception = assertThrows(RuntimeException.class,() -> categoriaService.crear(request));
          
        assertEquals("Ya existe una categoria con ese nombre", exception.getMessage());

        verify(categoriaRepository).existsByNombreIgnoreCase("CREAR_CATEGORIA");

        verify(categoriaRepository,never()).save(any(Categoria.class));
    }

    @Test
    void
    buscarPorId_retornaCategoria(){

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("CREAR_CATEGORIA");

        when(categoriaRepository.findById(1L))
            .thenReturn(Optional.of(categoria));

        CategoriaDTO.Response response = categoriaService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("CREAR_CATEGORIA", response.getNombre());

        verify(categoriaRepository).findById(1L);
    }

    @Test
    void
    eliminar_EliminarCategoria(){

        when(categoriaRepository.existsById(1L))
            .thenReturn(true);

        categoriaService.eliminar(1L);
        
        verify(categoriaRepository).existsById(1L);
        verify(categoriaRepository).deleteById(1L);
    }
    
}
