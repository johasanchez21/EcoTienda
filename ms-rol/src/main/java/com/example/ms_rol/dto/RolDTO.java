package com.example.ms_rol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RolDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        
        @NotBlank(message = "El nombre del rol es obligatorio")
        private String nombre;
        
        @NotNull(message = "El ID del permiso es obligatorio")
        private Long permisoId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String nombre;
        private PermisoDTO permiso;
        
    }
}
