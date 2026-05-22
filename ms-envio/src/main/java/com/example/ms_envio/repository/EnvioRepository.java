package com.example.ms_envio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_envio.entity.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    List<Envio> findByPedidoId(Long pedidoId);

    List<Envio> findByEstado(String estado);

    boolean existsByCodigoSeguimientoIgnoreCase(String codigoSeguimiento);
}
