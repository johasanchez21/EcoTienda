package com.example.ms_inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String horario;
}
