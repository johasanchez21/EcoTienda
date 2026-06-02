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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/V2/permisos")
@Tag(name = "Permisos", description = "Operaciones relacionadas con los permisos")
@RequiredArgsConstructor
public class PermisoControllerV2 {
    
     private final PermisoService permisoService;

    @GetMapping
    @Operation(summary = "Obtener todos los permisos", description = "Obtiene una lista de todos los permisos de los usuarios")
    public ResponseEntity<List<PermisoDTO.Response>> listarTodos() {
        log.debug("GET /api/permisos");
        return ResponseEntity.ok(permisoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener los permisos por id", description = "Obtiene un permiso en base a una id especifica")

    public ResponseEntity<PermisoDTO.Response> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/permisos{}", id);
        return ResponseEntity.ok(permisoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevos permisos", description = "Crea nuevos permisos disponbles para los usuarios")
    public ResponseEntity<PermisoDTO.Response> crear(@Valid @RequestBody PermisoDTO.Request request) {
        log.debug("POST /api/permisos - body: {}", request);
        PermisoDTO.Response creado = permisoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); 
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de los permisos", description = "Actualiza los datos de los permisos disponibles para los usuarios")
    public ResponseEntity<PermisoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PermisoDTO.Request request) {
        log.debug("PUT /api/permisos{}", id);
        return ResponseEntity.ok(permisoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar los permisos", description = "Elimina los permisos disponibles para los usuarios")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/permisos{}", id);
        permisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
