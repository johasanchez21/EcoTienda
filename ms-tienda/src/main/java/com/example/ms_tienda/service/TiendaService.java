package com.example.ms_tienda.service;

import java.util.List;

import com.example.ms_tienda.dto.TiendaDTO;

public interface TiendaService {
    
    TiendaDTO.Response crear(TiendaDTO.Request request);

    List<TiendaDTO.Response> listarTodos();

    TiendaDTO.Response buscarPorId(Long id);

    TiendaDTO.Response actualizar(Long id, TiendaDTO.Request request);

    void eliminar(Long id);
}
