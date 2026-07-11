package com.example.ms_inventario.controller;

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

import com.example.ms_inventario.dto.InventarioDTO;
import com.example.ms_inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/inventarios")
@Tag(name = "Inventario", description = "Operaciones relacionadas con los productos")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    @Operation(summary = "Crear Inventario", description = "Crea nuevos Inventarios de productos")
    public ResponseEntity<InventarioDTO.Response> crear(
            @Valid @RequestBody InventarioDTO.Request request) {

        log.debug("POST /api/inventarios - body: {}", request);

        InventarioDTO.Response creado = inventarioService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/productos/{productoId}/tiendas/{tiendaId}/agregar-stock")
    @Operation(summary = "Agregar Stock", description = "Agrega nuevo stock disponible")
    public ResponseEntity<InventarioDTO.Response> agregarStock(
            @PathVariable Long productoId,
            @PathVariable Long tiendaId,
            @RequestParam Integer stock) {

        log.debug("PUT /api/inventarios/productos/{}/tiendas/{}/agregar-stock?stock={}",
                productoId, tiendaId, stock);

        return ResponseEntity.ok(
                inventarioService.agregarStock(productoId, tiendaId, stock)
        );
    }

    @PutMapping("/productos/{productoId}/tiendas/{tiendaId}/reducir-stock")
    @Operation(summary = "Reducir Stock", description = "Reduce el stock disponible")
    public ResponseEntity<InventarioDTO.Response> reducirStock(
            @PathVariable Long productoId,
            @PathVariable Long tiendaId,
            @RequestParam Integer stock) {

        log.debug("PUT /api/inventarios/productos/{}/tiendas/{}/reducir-stock?stock={}",
                productoId, tiendaId, stock);

        return ResponseEntity.ok(
                inventarioService.reducirStock(productoId, tiendaId, stock)
        );
    }

    @GetMapping("/productos/{productoId}/tiendas/{tiendaId}")
    @Operation(summary = "Consultar Stock", description = "Consulta el stock disponible")
    public ResponseEntity<InventarioDTO.Response> consultarStock(
            @PathVariable Long productoId,
            @PathVariable Long tiendaId) {

        log.debug("GET /api/inventarios/productos/{}/tiendas/{}",
                productoId, tiendaId);

        return ResponseEntity.ok(
                inventarioService.consultarStock(productoId, tiendaId)
        );
    }

    @GetMapping("/tiendas/{tiendaId}")
    @Operation(summary = "Obtener productos de tienda", description = "Obtiene productos por id de tiendas")
    public ResponseEntity<List<InventarioDTO.Response>> obtenerInventarioPorTienda(
            @PathVariable Long tiendaId) {

        log.debug("GET /api/inventarios/tiendas/{}", tiendaId);

        return ResponseEntity.ok(
                inventarioService.obtenerInventarioPorTienda(tiendaId)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina el inventario")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/inventarios/{}", id);

        inventarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}