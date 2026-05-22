package com.example.ms_pedido.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PedidoDetalleDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Long productoId;
        @NotNull(message = "La Cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a 0")
        @Column(nullable = false)
        private Integer cantidad;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private Integer cantidad;
        private Integer precioUnitario;
        private ProductoDTO producto;
    }
}
