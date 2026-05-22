package com.example.ms_permiso.service;

import java.util.List;

import com.example.ms_permiso.dto.PermisoDTO;

public interface PermisoService {
    List<PermisoDTO.Response> listarTodos();

    PermisoDTO.Response buscarPorId(Long id);

    PermisoDTO.Response crear(PermisoDTO.Request request);

    PermisoDTO.Response actualizar(Long id, PermisoDTO.Request request);

    void eliminar(Long id);
}
