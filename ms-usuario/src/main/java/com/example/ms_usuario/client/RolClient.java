package com.example.ms_usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_usuario.dto.RolDTO;

@FeignClient(
        name = "ms-rol",
        url = "${ms.rol.url}",
        configuration = FeignClientConfig.class
)
public interface RolClient {

    @GetMapping("/api/roles/{id}")
    RolDTO buscarPorId(@PathVariable("id") Long id);
}
