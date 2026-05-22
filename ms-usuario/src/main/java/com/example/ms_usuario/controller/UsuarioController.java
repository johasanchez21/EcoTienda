package com.example.ms_usuario.controller;

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

import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO.Response> crear(
            @Valid @RequestBody UsuarioDTO.Request request) {

        log.debug("POST /api/usuarios - body: {}", request);

        UsuarioDTO.Response creado = usuarioService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO.Response>> listarTodos() {

        log.debug("GET /api/usuarios");

        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO.Response> buscarPorId(
            @PathVariable Long id) {

        log.debug("GET /api/usuarios/{}", id);

        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO.Request request) {

        log.debug("PUT /api/usuarios/{} - body: {}", id, request);

        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<UsuarioDTO.Response> activar(
            @PathVariable Long id) {

        log.debug("PUT /api/usuarios/{}/activar", id);

        return ResponseEntity.ok(usuarioService.activar(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioDTO.Response> desactivar(
            @PathVariable Long id) {

        log.debug("PUT /api/usuarios/{}/desactivar", id);

        return ResponseEntity.ok(usuarioService.desactivar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/usuarios/{}", id);

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}


