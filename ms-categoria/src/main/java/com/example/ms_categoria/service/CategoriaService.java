package com.example.ms_categoria.service;

import java.util.List;

import com.example.ms_categoria.dto.CategoriaDTO;


public interface CategoriaService {
    
    List<CategoriaDTO.Response> listarTodos();

    CategoriaDTO.Response buscarPorId(Long id);

    CategoriaDTO.Response crear(CategoriaDTO.Request request);

    CategoriaDTO.Response actualizar(Long id, CategoriaDTO.Request request);

    void eliminar(Long id);
}
