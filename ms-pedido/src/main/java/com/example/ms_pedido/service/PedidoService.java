package com.example.ms_pedido.service;

import java.util.List;

import com.example.ms_pedido.dto.PedidoDTO;

public interface PedidoService {

    PedidoDTO.Response crear(PedidoDTO.Request request);

    List<PedidoDTO.Response> listarTodos();

    PedidoDTO.Response buscarPorId(Long id);

    List<PedidoDTO.Response> buscarPorUsuario(Long usuarioId);

    List<PedidoDTO.Response> buscarPorEstado(String estado);

    PedidoDTO.Response actualizarEstado(Long id, String estado);

    void eliminar(Long id);
}
