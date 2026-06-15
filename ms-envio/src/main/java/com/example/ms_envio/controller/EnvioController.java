package com.example.ms_envio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_envio.dto.EnvioDTO;
import com.example.ms_envio.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envios", description = "Operaciones relacionadas con los envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;

    @PostMapping
    @Operation(summary = "Crear nuevos Envios", description = "Crea nuevos Envios")
    public ResponseEntity<EnvioDTO.Response> crear(
            @Valid @RequestBody EnvioDTO.Request request) {

        log.debug("POST /api/envios - body: {}", request);

        EnvioDTO.Response creado = envioService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los envios", description = "Obtiene todos los envios")
    public ResponseEntity<List<EnvioDTO.Response>> listarTodos() {
        return ResponseEntity.ok(envioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envio por id", description = "Obtiene un envio por id especifica")
    public ResponseEntity<EnvioDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.buscarPorId(id));
    }

    @GetMapping("/pedidos/{pedidoId}")
    @Operation(summary = "Obtener por pedido", description = "Obtiene un envio por una id especifica de pedido")
    public ResponseEntity<List<EnvioDTO.Response>> buscarPorPedido(
            @PathVariable Long pedidoId) {

        return ResponseEntity.ok(envioService.buscarPorPedido(pedidoId));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener por estado", description = "Obtiene un envio por una id especifica de estado")
    public ResponseEntity<List<EnvioDTO.Response>> buscarPorEstado(
            @PathVariable String estado) {

        return ResponseEntity.ok(envioService.buscarPorEstado(estado));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado", description = "Actualiza el estado del envio")
    public ResponseEntity<EnvioDTO.Response> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        return ResponseEntity.ok(envioService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envios", description = "Elimina envios")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        envioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}