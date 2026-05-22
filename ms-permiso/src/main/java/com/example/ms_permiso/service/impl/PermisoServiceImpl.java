package com.example.ms_permiso.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_permiso.dto.PermisoDTO;
import com.example.ms_permiso.entity.Permiso;
import com.example.ms_permiso.repository.PermisoRepository;
import com.example.ms_permiso.service.PermisoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements PermisoService{
    
    private final PermisoRepository permisoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermisoDTO.Response> listarTodos(){
        log.info("Listando todos los permisos");
        return permisoRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PermisoDTO.Response buscarPorId(Long id){
        log.info("Buscando permisos por id: {}", id);
        Permiso permiso = permisoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Permiso no encontrado con id: {}", id);
                return new RuntimeException("Permiso no encontrado con id:" + id);
            });
        return mapToResponse(permiso);
    }
    
    @Override
    @Transactional
    public PermisoDTO.Response crear(PermisoDTO.Request request){
        log.info("Creando nuevo Permiso: {}", request.getNombre());

        if (permisoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe un permiso con ese nombre: " + request.getNombre());
        }

        Permiso permiso = new Permiso();
        permiso.setNombre(request.getNombre());

        Permiso guardado = permisoRepository.save(permiso);
        log.info("Permiso creado con id: {}", guardado.getId());

        return mapToResponse(guardado);

    }

    @Override
    @Transactional
    public PermisoDTO.Response actualizar(Long id, PermisoDTO.Request request){
        log.info("Actualizando Permiso con id: {}", id);

        Permiso permiso = permisoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado con id:" + id));
        
        permiso.setNombre(request.getNombre());
        Permiso actualizado = permisoRepository.save(permiso);

        log.info("Permiso actualizado: {}", actualizado.getNombre());
        return mapToResponse(actualizado);

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando Permiso con id: {}", id);

        if (!permisoRepository.existsById(id)) {
            throw new RuntimeException("Permiso no encontrado con id:" + id);
        }

        permisoRepository.deleteById(id);
        log.info("Permiso eliminado con id: {}", id);
    }

    private PermisoDTO.Response mapToResponse(Permiso permiso) {
        return new PermisoDTO.Response(permiso.getId(), permiso.getNombre());
    }
}
