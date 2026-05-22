package com.example.ms_pedido.dto;

import java.util.Date;
import java.util.List;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PedidoDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Long usuarioId;
        @Column(nullable = false, length = 50)
        private String estado;
        @Column(nullable = false, length = 50)
        private Date fecha;
        private List<PedidoDetalleDTO.Request> detalles;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private Integer total;
        private String estado;
        private Date fecha;
        private UsuarioDTO usuario;
        private List<PedidoDetalleDTO.Response> detalles;
    }
}
