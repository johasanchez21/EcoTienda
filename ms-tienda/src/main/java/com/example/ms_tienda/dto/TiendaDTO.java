package com.example.ms_tienda.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TiendaDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @NotBlank(message = "El nombre de la tienda es obligatorio")
        private String nombre;

        @NotBlank(message = "La direccion es obligatoria")
        private String direccion;

        @NotBlank(message = "La ciudad es obligatoria")
        private String ciudad;

        @NotBlank(message = "El horario es obligatorio")
        private String horario;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String nombre;
        private String dirreccion;
        private String ciudad;
        private String horario;
    }
}
