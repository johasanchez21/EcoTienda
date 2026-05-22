package com.example.ms_tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_tienda.entity.Tienda;



@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {

    List<Tienda> findByDireccion(String direccion);

    boolean existsByDireccionIgnoreCase(String direccion);

}
