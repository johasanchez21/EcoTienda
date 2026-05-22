package com.example.ms_inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_inventario.dto.ProductoDTO;

@FeignClient(
        name = "ms-producto",
        url = "${ms.producto.url}",
        configuration = FeignClientConfig.class
)
public interface ProductoClient {

    @GetMapping("/api/productos/{id}")
    ProductoDTO buscarPorId(@PathVariable("id") Long id);
}
