package com.example.ms_tienda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[\\w\\sáéíóúÁÉÍÓÚñÑ]{2,}\\s+[\\w\\sáéíóúÁÉÍÓÚñÑ]{2,}.*$",
             message = "El nombre debe contener al menos 2 palabras")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Column(nullable = false, length = 100)
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 3, max = 100, message = "La ciudad debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String ciudad;

    @NotBlank(message = "El horario es obligatorio")
    @Size(min = 5, max = 150, message = "El horario debe tener entre 5 y 150 caracteres")
    private String horario;
}