package com.example.ms_rol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_rol.dto.RolDTO;
import com.example.ms_rol.service.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Operaciones relacionadas con los roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping
    @Operation(summary = "Crear nuevos roles", description = "Crear nuevos roles disponibles para los usuarios")
    public ResponseEntity<RolDTO.Response> crear(
            @Valid @RequestBody RolDTO.Request request) {

        log.debug("POST /api/roles - body: {}", request);

        RolDTO.Response creado = rolService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Obtiene una lista de todos los roles disponibles para los usuarios")
    public ResponseEntity<List<RolDTO.Response>> listarTodos() {

        log.debug("GET /api/roles");

        return ResponseEntity.ok(rolService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener los roles por id", description = "Obtiene un rol en base a una id especifica")
    public ResponseEntity<RolDTO.Response> buscarPorId(
            @PathVariable Long id) {

        log.debug("GET /api/roles/{}", id);

        return ResponseEntity.ok(rolService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de los roles", description = "Actualiza los datos de los roles disponibles para los usuarios")
    public ResponseEntity<RolDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RolDTO.Request request) {

        log.debug("PUT /api/roles/{} - body: {}", id, request);

        return ResponseEntity.ok(
                rolService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar roles", description = "Elimina los roles disponibles")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.debug("DELETE /api/roles/{}", id);

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}