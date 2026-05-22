package com.example.ms_usuario.service;

import java.util.List;

import com.example.ms_usuario.dto.UsuarioDTO;

public interface UsuarioService {

    UsuarioDTO.Response crear(UsuarioDTO.Request request);

    List<UsuarioDTO.Response> listarTodos();

    UsuarioDTO.Response buscarPorId(Long id);

    UsuarioDTO.Response actualizar(Long id, UsuarioDTO.Request request);

    UsuarioDTO.Response activar(Long id);

    UsuarioDTO.Response desactivar(Long id);

    void eliminar(Long id);
}
