package com.example.ms_carrito.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_carrito.dto.CarritoItemDTO;
import com.example.ms_carrito.service.CarritoItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/carrito-items")
@RequiredArgsConstructor
public class CarritoItemController {

    private final CarritoItemService carritoItemService;

    @PostMapping("/carritos/{carritoId}/productos/{productoId}")
    public ResponseEntity<CarritoItemDTO.Response> agregarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId,
            @RequestParam Integer cantidad) {

        log.debug("POST /api/carrito-items/carritos/{}/productos/{}?cantidad={}",
                carritoId, productoId, cantidad);

        CarritoItemDTO.Response creado = carritoItemService.agregarProducto(
                carritoId,
                productoId,
                cantidad
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/carritos/{carritoId}")
    public ResponseEntity<List<CarritoItemDTO.Response>> obtenerItemsCarrito(
            @PathVariable Long carritoId) {

        log.debug("GET /api/carrito-items/carritos/{}", carritoId);

        return ResponseEntity.ok(
                carritoItemService.obtenerItemsCarrito(carritoId)
        );
    }

    @PutMapping("/{itemId}/cantidad")
    public ResponseEntity<CarritoItemDTO.Response> actualizarCantidad(
            @PathVariable Long itemId,
            @RequestParam Integer cantidad) {

        log.debug("PUT /api/carrito-items/{}/cantidad?cantidad={}",
                itemId, cantidad);

        return ResponseEntity.ok(
                carritoItemService.actualizarCantidad(itemId, cantidad)
        );
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> eliminarItem(
            @PathVariable Long itemId) {

        log.debug("DELETE /api/carrito-items/{}", itemId);

        carritoItemService.eliminarItem(itemId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/carritos/{carritoId}")
    public ResponseEntity<Void> vaciarCarrito(
            @PathVariable Long carritoId) {

        log.debug("DELETE /api/carrito-items/carritos/{}", carritoId);

        carritoItemService.vaciarCarrito(carritoId);

        return ResponseEntity.noContent().build();
    }
}
