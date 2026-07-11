package com.example.ms_tienda.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_tienda.dto.TiendaDTO;
import com.example.ms_tienda.service.TiendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tiendas")
@Tag(name = "Tiendas", description = "Operaciones relacionadas con las tiendas")
@RequiredArgsConstructor
public class TiendaController {
    private final TiendaService tiendaService;

    @GetMapping
    @Operation(summary = "Obtener todas las tiendas", description = "Obtiene todas las tiendas registradas")
    public ResponseEntity<List<TiendaDTO.Response>> listarTodos() {
        log.debug("GET /api/tiendas");
        return ResponseEntity.ok(tiendaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tiendas por id", description = "Obtiene una tienda por una id especifica")
    public ResponseEntity<TiendaDTO.Response> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/tiendas{}", id);
        return ResponseEntity.ok(tiendaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear tiendas", description = "Crea nuevas tiendas")
    public ResponseEntity<TiendaDTO.Response> crear(@Valid @RequestBody TiendaDTO.Request request) {
        log.debug("POST /api/tiendas - body: {}", request);
        TiendaDTO.Response creado = tiendaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); 
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tiendas", description = "Actualiza la informacion de las tiendas")
    public ResponseEntity<TiendaDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TiendaDTO.Request request) {
        log.debug("PUT /api/tiendas{}", id);
        return ResponseEntity.ok(tiendaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tiendas", description = "Elimina las tiendas")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/tiendas{}", id);
        tiendaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
