package com.example.ms_envio.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_envio.client.PedidoClient;
import com.example.ms_envio.dto.EnvioDTO;
import com.example.ms_envio.dto.PedidoDTO;
import com.example.ms_envio.entity.Envio;
import com.example.ms_envio.repository.EnvioRepository;
import com.example.ms_envio.service.EnvioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvioServiceImpl implements EnvioService {

    private final EnvioRepository envioRepository;
    private final PedidoClient pedidoClient;

    @Override
    public EnvioDTO.Response crear(EnvioDTO.Request request) {

        log.info("Creando envío para pedido id: {}", request.getPedidoId());

        if (envioRepository.existsByCodigoSeguimientoIgnoreCase(request.getCodigoSeguimiento())) {
            throw new RuntimeException("Ya existe un envío con ese código de seguimiento");
        }

        PedidoDTO pedido = pedidoClient.buscarPorId(request.getPedidoId());

        Envio envio = new Envio();
        envio.setCodigoSeguimiento(request.getCodigoSeguimiento());
        envio.setEmpresa(request.getEmpresa());
        envio.setEstado(request.getEstado());
        envio.setPedidoId(request.getPedidoId());

        Envio guardado = envioRepository.save(envio);

        return mapToResponse(guardado, pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvioDTO.Response> listarTodos() {

        return envioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EnvioDTO.Response buscarPorId(Long id) {

        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con id: " + id));

        return mapToResponse(envio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvioDTO.Response> buscarPorPedido(Long pedidoId) {

        return envioRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvioDTO.Response> buscarPorEstado(String estado) {

        return envioRepository.findByEstado(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EnvioDTO.Response actualizarEstado(Long id, String estado) {

        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con id: " + id));

        envio.setEstado(estado);

        return mapToResponse(envioRepository.save(envio));
    }

    @Override
    public void eliminar(Long id) {

        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con id: " + id));

        envioRepository.delete(envio);
    }

    private EnvioDTO.Response mapToResponse(Envio envio) {

        PedidoDTO pedido = pedidoClient.buscarPorId(envio.getPedidoId());

        return mapToResponse(envio, pedido);
    }

    private EnvioDTO.Response mapToResponse(Envio envio, PedidoDTO pedido) {

        return new EnvioDTO.Response(
                envio.getId(),
                envio.getCodigoSeguimiento(),
                envio.getEmpresa(),
                envio.getEstado(),
                pedido
        );
    }
}
