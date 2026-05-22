package com.example.ms_carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private Boolean activo;
    private Object rol;
}
