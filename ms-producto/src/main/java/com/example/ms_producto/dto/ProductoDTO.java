package com.example.ms_producto.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductoDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
    
    @NotBlank(message = "El nombre del permiso es obligatorio")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @NotBlank(message = "El descripcion del permiso es obligatorio")
    @Column(nullable = false, unique = true, length = 100)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Integer precio;

    @NotNull(message = "El stock es obligatorio")
    @Positive(message = "El stock debe ser mayor a 0")
    @Column(nullable = false)
    private Integer stock;
    
    private Boolean activo;

    private Long categoriaId;
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String nombre;
        private String descripcion;
        private Integer precio;
        private Integer stock;
        private Boolean activo;
        private CategoriaDTO categoria;
    }
}
