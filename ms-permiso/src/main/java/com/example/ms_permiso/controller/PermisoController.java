package com.example.ms_permiso.controller;

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

import com.example.ms_permiso.dto.PermisoDTO;
import com.example.ms_permiso.service.PermisoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {
    
     private final PermisoService permisoService;

     @GetMapping
    public ResponseEntity<List<PermisoDTO.Response>> listarTodos() {
        log.debug("GET /api/permisos");
        return ResponseEntity.ok(permisoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoDTO.Response> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/permisos{}", id);
        return ResponseEntity.ok(permisoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PermisoDTO.Response> crear(@Valid @RequestBody PermisoDTO.Request request) {
        log.debug("POST /api/permisos - body: {}", request);
        PermisoDTO.Response creado = permisoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PermisoDTO.Request request) {
        log.debug("PUT /api/permisos{}", id);
        return ResponseEntity.ok(permisoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/permisos{}", id);
        permisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
