package com.example.ms_pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_pedido.entity.PedidoDetalle;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

    List<PedidoDetalle> findByPedidoId(Long pedidoId);

    void deleteByPedidoId(Long pedidoId);
}
