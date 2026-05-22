package com.example.ms_producto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_producto.dto.CategoriaDTO;

@FeignClient(
        name = "ms-categoria",
        url = "${ms.categoria.url}",
        configuration = FeignClientConfig.class
)
public interface CategoriaClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaDTO buscarPorId(@PathVariable("id") Long id);
}