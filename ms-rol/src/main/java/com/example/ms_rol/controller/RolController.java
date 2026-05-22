package com.example.ms_rol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_rol.dto.RolDTO;
import com.example.ms_rol.service.RolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping
    public ResponseEntity<RolDTO.Response> crear(
            @Valid @RequestBody RolDTO.Request request) {

        log.debug("POST /api/roles - body: {}", request);

        RolDTO.Response creado = rolService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<RolDTO.Response>> listarTodos() {

        log.debug("GET /api/roles");

        return ResponseEntity.ok(rolService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO.Response> buscarPorId(
            @PathVariable Long id) {

        log.debug("GET /api/roles/{}", id);

        return ResponseEntity.ok(rolService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RolDTO.Request request) {

        log.debug("PUT /api/roles/{} - body: {}", id, request);

        return ResponseEntity.ok(
                rolService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/roles/{}", id);

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}