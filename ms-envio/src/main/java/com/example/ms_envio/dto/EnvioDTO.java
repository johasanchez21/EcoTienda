package com.example.ms_envio.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EnvioDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "El código de seguimiento es obligatorio")
        @Column(nullable = false, unique = true, length = 100)
        private String codigoSeguimiento;
        @NotBlank(message = "La empresa es obligatoria")
        @Column(nullable = false, length = 100)
        private String empresa;
        @NotBlank(message = "El estado es obligatorio")
        @Column(nullable = false, length = 50)
        private String estado;
        @Column(nullable = false)
        private Long pedidoId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String codigoSeguimiento;
        private String empresa;
        private String estado;
        private PedidoDTO pedido;
    }
}
