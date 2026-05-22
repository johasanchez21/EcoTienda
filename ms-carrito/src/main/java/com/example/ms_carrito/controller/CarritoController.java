package com.example.ms_carrito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_carrito.dto.CarritoDTO;
import com.example.ms_carrito.service.CarritoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @PostMapping("/usuarios/{usuarioId}")
    public ResponseEntity<CarritoDTO.Response> crearCarrito(
            @PathVariable Long usuarioId) {

        log.debug("POST /api/carritos/usuarios/{}", usuarioId);

        CarritoDTO.Response creado = carritoService.crearCarrito(usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<CarritoDTO.Response> obtenerCarritoPorUsuario(
            @PathVariable Long usuarioId) {

        log.debug("GET /api/carritos/usuarios/{}", usuarioId);

        return ResponseEntity.ok(
                carritoService.obtenerCarritoPorUsuario(usuarioId)
        );
    }

    @GetMapping("/{carritoId}")
    public ResponseEntity<CarritoDTO.Response> obtenerCarritoPorId(
            @PathVariable Long carritoId) {

        log.debug("GET /api/carritos/{}", carritoId);

        return ResponseEntity.ok(
                carritoService.obtenerCarritoPorId(carritoId)
        );
    }

    @DeleteMapping("/{carritoId}")
    public ResponseEntity<Void> eliminarCarrito(
            @PathVariable Long carritoId) {

        log.debug("DELETE /api/carritos/{}", carritoId);

        carritoService.eliminarCarrito(carritoId);

        return ResponseEntity.noContent().build();
    }
}
