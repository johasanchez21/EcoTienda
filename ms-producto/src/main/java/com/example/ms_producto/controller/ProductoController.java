package com.example.ms_producto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoDTO.Response> crear(
            @Valid @RequestBody ProductoDTO.Request request) {

        log.debug("POST /api/productos - body: {}", request);

        ProductoDTO.Response creado = productoService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO.Response>> listarTodos() {

        log.debug("GET /api/productos");

        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO.Response> buscarPorId(
            @PathVariable Long id) {

        log.debug("GET /api/productos/{}", id);

        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO.Response>> buscarPorCategoria(
            @PathVariable Long categoriaId) {

        log.debug("GET /api/productos/categoria/{}", categoriaId);

        return ResponseEntity.ok(
                productoService.buscarPorCategoria(categoriaId)
        );
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProductoDTO.Response>> buscarActivos() {

        log.debug("GET /api/productos/activos");

        return ResponseEntity.ok(productoService.buscarActivos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO.Response>> buscarPorNombre(
            @RequestParam String nombre) {

        log.debug("GET /api/productos/buscar?nombre={}", nombre);

        return ResponseEntity.ok(
                productoService.buscarPorNombre(nombre)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO.Request request) {

        log.debug("PUT /api/productos/{} - body: {}", id, request);

        return ResponseEntity.ok(
                productoService.actualizar(id, request)
        );
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<ProductoDTO.Response> activar(
            @PathVariable Long id) {

        log.debug("PUT /api/productos/{}/activar", id);

        return ResponseEntity.ok(productoService.activar(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ProductoDTO.Response> desactivar(
            @PathVariable Long id) {

        log.debug("PUT /api/productos/{}/desactivar", id);

        return ResponseEntity.ok(productoService.desactivar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/productos/{}", id);

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
