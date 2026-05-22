package com.example.ms_categoria.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.entity.Categoria;
import com.example.ms_categoria.repository.CategoriaRepository;
import com.example.ms_categoria.service.CategoriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService{

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO.Response> listarTodos(){
        log.info("Listando todos las categorias");
        return categoriaRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO.Response buscarPorId(Long id){
        log.info("Buscando categoria por id: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> {
                log.error("categoria no encontrada con id: {}", id);
                return new RuntimeException("Categoria no encontrado con id:" + id);
            });
        return mapToResponse(categoria);
    }
    
    @Override
    @Transactional
    public CategoriaDTO.Response crear(CategoriaDTO.Request request){
        log.info("Creando nueva Categoria: {}", request.getNombre());

        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe una categoria con ese nombre: " + request.getNombre());
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());

        Categoria guardado = categoriaRepository.save(categoria);
        log.info("Categoria creada con id: {}", guardado.getId());

        return mapToResponse(guardado);

    }

    @Override
    @Transactional
    public CategoriaDTO.Response actualizar(Long id, CategoriaDTO.Request request){
        log.info("Actualizando Categoria con id: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id:" + id));
        
        categoria.setNombre(request.getNombre());
        Categoria actualizado = categoriaRepository.save(categoria);

        log.info("Categoria actualizado: {}", actualizado.getNombre());
        return mapToResponse(actualizado);

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando Categoria con id: {}", id);

        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con id:" + id);
        }

        categoriaRepository.deleteById(id);
        log.info("Categoria eliminada con id: {}", id);
    }


    private CategoriaDTO.Response mapToResponse(Categoria categoria) {
        return new CategoriaDTO.Response(categoria.getId(), categoria.getNombre());
    }
}
