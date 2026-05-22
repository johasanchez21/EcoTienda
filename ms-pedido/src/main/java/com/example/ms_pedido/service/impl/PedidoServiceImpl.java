package com.example.ms_pedido.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_pedido.client.ProductoClient;
import com.example.ms_pedido.client.UsuarioClient;
import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.dto.PedidoDetalleDTO;
import com.example.ms_pedido.dto.ProductoDTO;
import com.example.ms_pedido.dto.UsuarioDTO;
import com.example.ms_pedido.entity.Pedido;
import com.example.ms_pedido.entity.PedidoDetalle;
import com.example.ms_pedido.repository.PedidoDetalleRepository;
import com.example.ms_pedido.repository.PedidoRepository;
import com.example.ms_pedido.service.PedidoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDetalleRepository pedidoDetalleRepository;

    private final UsuarioClient usuarioClient;
    private final ProductoClient productoClient;

    @Override
    public PedidoDTO.Response crear(PedidoDTO.Request request) {

        log.info("Creando pedido para usuario {}", request.getUsuarioId());

        UsuarioDTO usuario = usuarioClient.buscarPorId(request.getUsuarioId());

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(request.getUsuarioId());
        pedido.setEstado(request.getEstado());
        pedido.setFecha(request.getFecha());

        int total = 0;

        for (PedidoDetalleDTO.Request detalle : request.getDetalles()) {

            ProductoDTO producto =
                    productoClient.buscarPorId(detalle.getProductoId());

            total += producto.getPrecio() * detalle.getCantidad();
        }

        pedido.setTotal(total);

        Pedido guardado = pedidoRepository.save(pedido);

        List<PedidoDetalle> detallesGuardados = new ArrayList<>();

        for (PedidoDetalleDTO.Request detalle : request.getDetalles()) {

            ProductoDTO producto =
                    productoClient.buscarPorId(detalle.getProductoId());

            PedidoDetalle pedidoDetalle = new PedidoDetalle();

            pedidoDetalle.setPedidoId(guardado.getId());
            pedidoDetalle.setProductoId(detalle.getProductoId());
            pedidoDetalle.setCantidad(detalle.getCantidad());
            pedidoDetalle.setPrecioUnitario(producto.getPrecio());

            detallesGuardados.add(
                    pedidoDetalleRepository.save(pedidoDetalle)
            );
        }

        return mapToResponse(guardado, usuario, detallesGuardados);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO.Response> listarTodos() {

        return pedidoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO.Response buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado con id: " + id));

        return mapToResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO.Response> buscarPorUsuario(Long usuarioId) {

        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO.Response> buscarPorEstado(String estado) {

        return pedidoRepository.findByEstado(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO.Response actualizarEstado(Long id, String estado) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado con id: " + id));

        pedido.setEstado(estado);

        return mapToResponse(
                pedidoRepository.save(pedido)
        );
    }

    @Override
    public void eliminar(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado con id: " + id));

        pedidoDetalleRepository.deleteByPedidoId(id);

        pedidoRepository.delete(pedido);
    }

    private PedidoDTO.Response mapToResponse(Pedido pedido) {

        UsuarioDTO usuario =
                usuarioClient.buscarPorId(pedido.getUsuarioId());

        List<PedidoDetalle> detalles =
                pedidoDetalleRepository.findByPedidoId(pedido.getId());

        return mapToResponse(pedido, usuario, detalles);
    }

    private PedidoDTO.Response mapToResponse(
            Pedido pedido,
            UsuarioDTO usuario,
            List<PedidoDetalle> detalles) {

        List<PedidoDetalleDTO.Response> detallesResponse =
                detalles.stream()
                        .map(this::mapDetalleResponse)
                        .collect(Collectors.toList());

        return new PedidoDTO.Response(
                pedido.getId(),
                pedido.getTotal(),
                pedido.getEstado(),
                pedido.getFecha(),
                usuario,
                detallesResponse
        );
    }

    private PedidoDetalleDTO.Response mapDetalleResponse(
            PedidoDetalle detalle) {

        ProductoDTO producto =
                productoClient.buscarPorId(detalle.getProductoId());

        return new PedidoDetalleDTO.Response(
                detalle.getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                producto
        );
    }
}
