package com.example.ms_envio.service;

import java.util.List;

import com.example.ms_envio.dto.EnvioDTO;

public interface EnvioService {

    EnvioDTO.Response crear(EnvioDTO.Request request);

    List<EnvioDTO.Response> listarTodos();

    EnvioDTO.Response buscarPorId(Long id);

    List<EnvioDTO.Response> buscarPorPedido(Long pedidoId);

    List<EnvioDTO.Response> buscarPorEstado(String estado);

    EnvioDTO.Response actualizarEstado(Long id, String estado);

    void eliminar(Long id);
}
