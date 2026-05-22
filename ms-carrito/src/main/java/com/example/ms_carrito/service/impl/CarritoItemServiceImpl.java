package com.example.ms_carrito.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_carrito.client.ProductoClient;
import com.example.ms_carrito.dto.CarritoItemDTO;
import com.example.ms_carrito.dto.ProductoDTO;
import com.example.ms_carrito.entity.CarritoItem;
import com.example.ms_carrito.repository.CarritoItemRepository;
import com.example.ms_carrito.repository.CarritoRepository;
import com.example.ms_carrito.service.CarritoItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarritoItemServiceImpl implements CarritoItemService {

    private final CarritoItemRepository carritoItemRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoClient productoClient;

    @Override
    public CarritoItemDTO.Response agregarProducto(
            Long carritoId,
            Long productoId,
            Integer cantidad) {

        log.info("Agregando producto {} al carrito {}", productoId, carritoId);

        carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + carritoId));

        ProductoDTO producto = productoClient.buscarPorId(productoId);

        CarritoItem item = carritoItemRepository
                .findByCarritoIdAndProductoId(carritoId, productoId)
                .orElse(null);

        if (item != null) {
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecio(producto.getPrecio());

            return mapToResponse(carritoItemRepository.save(item), producto);
        }

        item = new CarritoItem();
        item.setCarritoId(carritoId);
        item.setProductoId(productoId);
        item.setCantidad(cantidad);
        item.setPrecio(producto.getPrecio());

        CarritoItem guardado = carritoItemRepository.save(item);

        return mapToResponse(guardado, producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarritoItemDTO.Response> obtenerItemsCarrito(Long carritoId) {

        return carritoItemRepository.findByCarritoId(carritoId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CarritoItemDTO.Response actualizarCantidad(Long itemId, Integer cantidad) {

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id: " + itemId));

        item.setCantidad(cantidad);

        return mapToResponse(carritoItemRepository.save(item));
    }

    @Override
    public void eliminarItem(Long itemId) {

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id: " + itemId));

        carritoItemRepository.delete(item);
    }

    @Override
    @Transactional
    public void vaciarCarrito(Long carritoId) {

        carritoItemRepository.deleteByCarritoId(carritoId);
    }

    private CarritoItemDTO.Response mapToResponse(CarritoItem item) {

        ProductoDTO producto = productoClient.buscarPorId(item.getProductoId());

        return mapToResponse(item, producto);
    }

    private CarritoItemDTO.Response mapToResponse(CarritoItem item, ProductoDTO producto) {

        return new CarritoItemDTO.Response(
                item.getId(),
                item.getCantidad(),
                item.getPrecio(),
                producto
        );
    }
}
