package com.example.ms_carrito.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_carrito.client.UsuarioClient;
import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.dto.UsuarioDTO;
import com.example.ms_carrito.entity.Carrito;
import com.example.ms_carrito.repository.CarritoRepository;
import com.example.ms_carrito.service.CarritoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioClient usuarioClient;

    @Override
    public CarritoDTO.Response crearCarrito(Long usuarioId) {

        log.info("Creando carrito para usuario id: {}", usuarioId);

        if (carritoRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("El usuario ya tiene un carrito");
        }

        UsuarioDTO usuario = usuarioClient.buscarPorId(usuarioId);

        Carrito carrito = new Carrito();
        carrito.setUsuarioId(usuarioId);

        Carrito guardado = carritoRepository.save(carrito);

        return mapToResponse(guardado, usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoDTO.Response obtenerCarritoPorUsuario(Long usuarioId) {

        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para usuario id: " + usuarioId));

        return mapToResponse(carrito);
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoDTO.Response obtenerCarritoPorId(Long carritoId) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + carritoId));

        return mapToResponse(carrito);
    }

    @Override
    public void eliminarCarrito(Long carritoId) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + carritoId));

        carritoRepository.delete(carrito);
    }

    private CarritoDTO.Response mapToResponse(Carrito carrito) {

        UsuarioDTO usuario = usuarioClient.buscarPorId(carrito.getUsuarioId());

        return mapToResponse(carrito, usuario);
    }

    private CarritoDTO.Response mapToResponse(Carrito carrito, UsuarioDTO usuario) {

        return new CarritoDTO.Response(
                carrito.getId(),
                usuario
        );
    }
}
