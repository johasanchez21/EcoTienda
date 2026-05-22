package com.example.ms_carrito.service;

import com.example.ms_carrito.dto.CarritoDTO;

public interface CarritoService {

    CarritoDTO.Response crearCarrito(Long usuarioId);

    CarritoDTO.Response obtenerCarritoPorUsuario(Long usuarioId);

    CarritoDTO.Response obtenerCarritoPorId(Long carritoId);

    void eliminarCarrito(Long carritoId);
}
