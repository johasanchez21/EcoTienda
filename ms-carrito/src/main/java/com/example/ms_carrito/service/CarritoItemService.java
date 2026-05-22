package com.example.ms_carrito.service;

import java.util.List;

import com.example.ms_carrito.dto.CarritoItemDTO;

public interface CarritoItemService {

    CarritoItemDTO.Response agregarProducto(Long carritoId, Long productoId, Integer cantidad);

    List<CarritoItemDTO.Response> obtenerItemsCarrito(Long carritoId);

    CarritoItemDTO.Response actualizarCantidad(Long itemId, Integer cantidad);

    void eliminarItem(Long itemId);

    void vaciarCarrito(Long carritoId);
}
