package com.example.ms_inventario.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_inventario.client.ProductoClient;
import com.example.ms_inventario.client.TiendaClient;
import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.dto.ProductoDTO;
import com.example.ms_inventario.dto.TiendaDTO;
import com.example.ms_inventario.entity.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;
import com.example.ms_inventario.service.InventarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoClient productoClient;
    private final TiendaClient tiendaClient;

    @Override
    public InventarioDTO.Response crear(InventarioDTO.Request request) {

        log.info("Creando inventario para producto {} y tienda {}",
                request.getProductoId(), request.getTiendaId());

        if (inventarioRepository.existsByProductoIdAndTiendaId(
                request.getProductoId(), request.getTiendaId())) {
            throw new RuntimeException("Ya existe inventario para ese producto en esa tienda");
        }

        ProductoDTO producto = productoClient.buscarPorId(request.getProductoId());
        TiendaDTO tienda = tiendaClient.buscarPorId(request.getTiendaId());

        Inventario inventario = new Inventario();
        inventario.setProductoId(request.getProductoId());
        inventario.setTiendaId(request.getTiendaId());
        inventario.setStock(request.getStock());

        Inventario guardado = inventarioRepository.save(inventario);

        return mapToResponse(guardado, producto, tienda);
    }

    @Override
    public InventarioDTO.Response agregarStock(Long productoId, Long tiendaId, Integer stock) {

        Inventario inventario = inventarioRepository
                .findByProductoIdAndTiendaId(productoId, tiendaId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        inventario.setStock(inventario.getStock() + stock);

        return mapToResponse(inventarioRepository.save(inventario));
    }

    @Override
    public InventarioDTO.Response reducirStock(Long productoId, Long tiendaId, Integer stock) {

        Inventario inventario = inventarioRepository
                .findByProductoIdAndTiendaId(productoId, tiendaId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        if (inventario.getStock() < stock) {
            throw new RuntimeException("Stock insuficiente");
        }

        inventario.setStock(inventario.getStock() - stock);

        return mapToResponse(inventarioRepository.save(inventario));
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioDTO.Response consultarStock(Long productoId, Long tiendaId) {

        Inventario inventario = inventarioRepository
                .findByProductoIdAndTiendaId(productoId, tiendaId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        return mapToResponse(inventario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioDTO.Response> obtenerInventarioPorTienda(Long tiendaId) {

        return inventarioRepository.findByTiendaId(tiendaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {

        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        inventarioRepository.delete(inventario);
    }

    private InventarioDTO.Response mapToResponse(Inventario inventario) {

        ProductoDTO producto = productoClient.buscarPorId(inventario.getProductoId());
        TiendaDTO tienda = tiendaClient.buscarPorId(inventario.getTiendaId());

        return mapToResponse(inventario, producto, tienda);
    }

    private InventarioDTO.Response mapToResponse(
            Inventario inventario,
            ProductoDTO producto,
            TiendaDTO tienda) {

        return new InventarioDTO.Response(
                inventario.getId(),
                inventario.getStock(),
                producto,
                tienda
        );
    }
}
