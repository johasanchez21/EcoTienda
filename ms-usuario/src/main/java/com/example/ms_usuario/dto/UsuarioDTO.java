package com.example.ms_usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UsuarioDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{

        @NotBlank(message = "El RUT es obligatorio")
        @Size(min = 10, max = 12, message = "El RUT debe tener entre 10 y 12 caracteres")
        private String rut;

        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^[\\w\\sáéíóúÁÉÍÓÚñÑ]{2,}\\s+[\\w\\sáéíóúÁÉÍÓÚñÑ]{2,}.*$",
                 message = "El nombre debe contener al menos 2 palabras")
        private String nombre;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Formato de email inválido")
        private String email;

        @NotBlank(message = "El password es obligatorio")
        private String password;

        private Boolean activo;

        private Long rolId;

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String rut;
        private String nombre;
        private String email;
        private Boolean activo;
        private RolDTO rol;
    }
}
