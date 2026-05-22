package com.example.ms_carrito.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CarritoItemDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{

        @NotNull(message = "El cantidad es obligatorio")
        @Positive(message = "El cantidad debe ser mayor a 0")
        @Column(nullable = false)
        private Integer cantidad;
        
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private Integer cantidad;
        private Integer precio;
        private ProductoDTO producto;
    }
}
