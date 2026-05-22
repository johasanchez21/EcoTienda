package com.example.ms_categoria.controller;

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

import com.example.ms_categoria.dto.CategoriaDTO;
import com.example.ms_categoria.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

     @GetMapping
    public ResponseEntity<List<CategoriaDTO.Response>> listarTodos() {
        log.debug("GET /api/categorias");
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO.Response> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/categorias{}", id);
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO.Response> crear(@Valid @RequestBody CategoriaDTO.Request request) {
        log.debug("POST /api/categorias - body: {}", request);
        CategoriaDTO.Response creado = categoriaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO.Request request) {
        log.debug("PUT /api/categorias{}", id);
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/categorias{}", id);
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
