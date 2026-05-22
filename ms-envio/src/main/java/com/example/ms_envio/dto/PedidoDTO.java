package com.example.ms_envio.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private Integer total;
    private String estado;
    private Date fecha;
    private Object usuario;
    private Object detalles;
}
