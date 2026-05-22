package com.example.ms_carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CarritoDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private UsuarioDTO usuario;
    }
}
