package com.example.ms_pedido.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_pedido.dto.PedidoDTO;
import com.example.ms_pedido.service.PedidoService;

@Slf4j
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO.Response> crear(
            @Valid @RequestBody PedidoDTO.Request request) {

        log.debug("POST /api/pedidos - body: {}", request);

        PedidoDTO.Response creado = pedidoService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO.Response>> listarTodos() {

        log.debug("GET /api/pedidos");

        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO.Response> buscarPorId(
            @PathVariable Long id) {

        log.debug("GET /api/pedidos/{}", id);

        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<PedidoDTO.Response>> buscarPorUsuario(
            @PathVariable Long usuarioId) {

        log.debug("GET /api/pedidos/usuarios/{}", usuarioId);

        return ResponseEntity.ok(pedidoService.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO.Response>> buscarPorEstado(
            @PathVariable String estado) {

        log.debug("GET /api/pedidos/estado/{}", estado);

        return ResponseEntity.ok(pedidoService.buscarPorEstado(estado));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO.Response> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        log.debug("PUT /api/pedidos/{}/estado?estado={}", id, estado);

        return ResponseEntity.ok(
                pedidoService.actualizarEstado(id, estado)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/pedidos/{}", id);

        pedidoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}