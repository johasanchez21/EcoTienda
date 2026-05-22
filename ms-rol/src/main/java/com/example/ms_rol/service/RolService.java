package com.example.ms_rol.service;

import java.util.List;

import com.example.ms_rol.dto.RolDTO;

public interface RolService {
    
    RolDTO.Response crear(RolDTO.Request request);

    List<RolDTO.Response> listarTodos();

    RolDTO.Response buscarPorId(Long id);

    RolDTO.Response actualizar(Long id, RolDTO.Request request);

    void eliminar(Long id);

}
