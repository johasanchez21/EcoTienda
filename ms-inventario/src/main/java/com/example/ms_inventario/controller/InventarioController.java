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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<InventarioDTO.Response> crear(
            @Valid @RequestBody InventarioDTO.Request request) {

        log.debug("POST /api/inventarios - body: {}", request);

        InventarioDTO.Response creado = inventarioService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/productos/{productoId}/tiendas/{tiendaId}/agregar-stock")
    public ResponseEntity<InventarioDTO.Response> agregarStock(
            @PathVariable Long productoId,
            @PathVariable Long tiendaId,
            @RequestParam Integer cantidad) {

        log.debug("PUT /api/inventarios/productos/{}/tiendas/{}/agregar-stock?cantidad={}",
                productoId, tiendaId, cantidad);

        return ResponseEntity.ok(
                inventarioService.agregarStock(productoId, tiendaId, cantidad)
        );
    }

    @PutMapping("/productos/{productoId}/tiendas/{tiendaId}/reducir-stock")
    public ResponseEntity<InventarioDTO.Response> reducirStock(
            @PathVariable Long productoId,
            @PathVariable Long tiendaId,
            @RequestParam Integer cantidad) {

        log.debug("PUT /api/inventarios/productos/{}/tiendas/{}/reducir-stock?cantidad={}",
                productoId, tiendaId, cantidad);

        return ResponseEntity.ok(
                inventarioService.reducirStock(productoId, tiendaId, cantidad)
        );
    }

    @GetMapping("/productos/{productoId}/tiendas/{tiendaId}")
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
    public ResponseEntity<List<InventarioDTO.Response>> obtenerInventarioPorTienda(
            @PathVariable Long tiendaId) {

        log.debug("GET /api/inventarios/tiendas/{}", tiendaId);

        return ResponseEntity.ok(
                inventarioService.obtenerInventarioPorTienda(tiendaId)
        );
    }

    @PutMapping("/{inventarioId}/cantidad")
    public ResponseEntity<InventarioDTO.Response> actualizarCantidad(
            @PathVariable Long inventarioId,
            @RequestParam Integer cantidad) {

        log.debug("PUT /api/inventarios/{}/cantidad?cantidad={}",
                inventarioId, cantidad);

        return ResponseEntity.ok(
                inventarioService.actualizarCantidad(inventarioId, cantidad)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/inventarios/{}", id);

        inventarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}