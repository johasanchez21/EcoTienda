package com.example.ms_inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_inventario.dto.TiendaDTO;

@FeignClient(
        name = "ms-tienda",
        url = "${ms.tienda.url}",
        configuration = FeignClientConfig.class
)
public interface TiendaClient {

    @GetMapping("/api/tiendas/{id}")
    TiendaDTO buscarPorId(@PathVariable("id") Long id);
}