package com.example.ms_carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_carrito.dto.UsuarioDTO;

@FeignClient(
        name = "ms-usuario",
        url = "${ms.usuario.url}",
        configuration = FeignClientConfig.class
)
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO buscarPorId(@PathVariable("id") Long id);
}
