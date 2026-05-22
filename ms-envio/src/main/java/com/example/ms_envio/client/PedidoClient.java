package com.example.ms_envio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_envio.dto.PedidoDTO;

@FeignClient(
        name = "ms-pedido",
        url = "${ms.pedido.url}",
        configuration = FeignClientConfig.class
)
public interface PedidoClient {

    @GetMapping("/api/pedidos/{id}")
    PedidoDTO buscarPorId(@PathVariable("id") Long id);
}
