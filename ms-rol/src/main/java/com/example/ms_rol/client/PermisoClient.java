package com.example.ms_rol.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_rol.dto.PermisoDTO;

@FeignClient(
        name = "ms-permiso",
        url = "${ms.permiso.url}",
        configuration = FeignClientConfig.class
)
public interface PermisoClient {

    @GetMapping("/api/permisos/{id}")
    PermisoDTO buscarPorId(@PathVariable("id") Long id);
}
