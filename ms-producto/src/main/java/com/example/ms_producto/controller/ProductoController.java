package com.example.ms_producto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    @Operation(summary = "Crear Productos", description = "Crea nuevos productos")
    public ResponseEntity<ProductoDTO.Response> crear(
            @Valid @RequestBody ProductoDTO.Request request) {

        log.debug("POST /api/productos - body: {}", request);

        ProductoDTO.Response creado = productoService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    @Operation(summary = "Obtener Productos", description = "Obtiene todos los productos disponibles")
    public ResponseEntity<List<ProductoDTO.Response>> listarTodos() {

        log.debug("GET /api/productos");

        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Productos por id", description = "Obtiene un producto por una id especifica")
    public ResponseEntity<ProductoDTO.Response> buscarPorId(
            @PathVariable Long id) {

        log.debug("GET /api/productos/{}", id);

        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Obtener Productos por id de categoria", description = "Obtiene un producto por una id especifica de la categroia")
    public ResponseEntity<List<ProductoDTO.Response>> buscarPorCategoria(
            @PathVariable Long categoriaId) {

        log.debug("GET /api/productos/categoria/{}", categoriaId);

        return ResponseEntity.ok(
                productoService.buscarPorCategoria(categoriaId)
        );
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener Productos activos", description = "Obtiene productos disponibles")
    public ResponseEntity<List<ProductoDTO.Response>> buscarActivos() {

        log.debug("GET /api/productos/activos");

        return ResponseEntity.ok(productoService.buscarActivos());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Obtener Nombres de productos", description = "Obtiene productos por su nombre")
    public ResponseEntity<List<ProductoDTO.Response>> buscarPorNombre(
            @RequestParam String nombre) {

        log.debug("GET /api/productos/buscar?nombre={}", nombre);

        return ResponseEntity.ok(
                productoService.buscarPorNombre(nombre)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar productos", description = "Actualiza los productos")
    public ResponseEntity<ProductoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO.Request request) {

        log.debug("PUT /api/productos/{} - body: {}", id, request);

        return ResponseEntity.ok(
                productoService.actualizar(id, request)
        );
    }

    @PutMapping("/{id}/activar")
    @Operation(summary = "Activar productos", description = "Activa los productos disponibles")
    public ResponseEntity<ProductoDTO.Response> activar(
            @PathVariable Long id) {

        log.debug("PUT /api/productos/{}/activar", id);

        return ResponseEntity.ok(productoService.activar(id));
    }

    @PutMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar productos", description = "Desactiva los productos para que no esten disponibles")
    public ResponseEntity<ProductoDTO.Response> desactivar(
            @PathVariable Long id) {

        log.debug("PUT /api/productos/{}/desactivar", id);

        return ResponseEntity.ok(productoService.desactivar(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar productos", description = "Elimina los productos")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/productos/{}", id);

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
