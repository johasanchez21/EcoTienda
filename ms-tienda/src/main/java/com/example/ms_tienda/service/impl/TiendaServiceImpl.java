package com.example.ms_tienda.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_tienda.dto.TiendaDTO;
import com.example.ms_tienda.entity.Tienda;
import com.example.ms_tienda.repository.TiendaRepository;
import com.example.ms_tienda.service.TiendaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TiendaServiceImpl implements TiendaService {

    private final TiendaRepository tiendaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TiendaDTO.Response> listarTodos(){
        log.info("Listando todas las tiendas");
        return tiendaRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TiendaDTO.Response buscarPorId(Long id){
        log.info("Buscando tiendas por id: {}", id);
        Tienda tienda = tiendaRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Tienda no encontrada con id: {}", id);
                return new RuntimeException("Tienda no encontrada con id:" + id);
            });
        return mapToResponse(tienda);
    }

    @Override
    @Transactional
    public TiendaDTO.Response crear(TiendaDTO.Request request){
        log.info("Creando nueva tienda: {}", request.getDireccion());

        if (tiendaRepository.existsByDireccionIgnoreCase(request.getDireccion())) {
            throw new RuntimeException("Ya existe una tienda en esa direccion: " + request.getDireccion());
        }

        Tienda tienda = new Tienda();
        tienda.setNombre(request.getNombre());
        tienda.setDireccion(request.getDireccion());
        tienda.setCiudad(request.getCiudad());
        tienda.setHorario(request.getHorario());

        Tienda guardado = tiendaRepository.save(tienda);
        log.info("Tienda creada con id: {}", guardado.getId());

        return mapToResponse(guardado);

    }

    @Override
    @Transactional
    public TiendaDTO.Response actualizar(Long id, TiendaDTO.Request request){
        log.info("Actualizando Tienda con id: {}", id);

        Tienda tienda = tiendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrado con id:" + id));
        
        tienda.setNombre(request.getNombre());
        tienda.setDireccion(request.getDireccion());
        tienda.setCiudad(request.getCiudad());
        tienda.setHorario(request.getHorario());
        Tienda actualizado = tiendaRepository.save(tienda);

        log.info("Tienda actualizado: {}", actualizado.getDireccion());
        return mapToResponse(actualizado);

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando Tienda con id: {}", id);

        if (!tiendaRepository.existsById(id)) {
            throw new RuntimeException("Tienda no encontrada con id:" + id);
        }

        tiendaRepository.deleteById(id);
        log.info("Tienda eliminada con id: {}", id);
    }
    
    private TiendaDTO.Response mapToResponse(Tienda tienda) {
        return new TiendaDTO.Response(tienda.getId(), tienda.getNombre(),
        tienda.getDireccion(), tienda.getCiudad(),tienda.getHorario());
    }
}
