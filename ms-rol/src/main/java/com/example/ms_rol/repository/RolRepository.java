package com.example.ms_rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_rol.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{

    boolean existsByNombreIgnoreCase(String nombre);
}
