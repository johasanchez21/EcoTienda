package com.example.ms_rol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_rol.client.PermisoClient;
import com.example.ms_rol.dto.PermisoDTO;
import com.example.ms_rol.dto.RolDTO;
import com.example.ms_rol.entity.Rol;
import com.example.ms_rol.repository.RolRepository;
import com.example.ms_rol.service.RolService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final PermisoClient permisoClient;

    @Override
    public RolDTO.Response crear(RolDTO.Request request) {

        log.info("Creando nuevo rol: {}", request.getNombre());

        if (rolRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe un rol con ese nombre: " + request.getNombre());
        }

        PermisoDTO permiso = permisoClient.buscarPorId(request.getPermisoId());

        Rol rol = new Rol();
        rol.setNombre(request.getNombre());
        rol.setPermisoId(request.getPermisoId());

        Rol guardado = rolRepository.save(rol);

        log.info("Rol creado con id: {}", guardado.getId());

        return mapToResponse(guardado, permiso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO.Response> listarTodos() {

        log.info("Listando todos los roles");

        return rolRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO.Response buscarPorId(Long id) {

        log.info("Buscando rol con id: {}", id);

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        return mapToResponse(rol);
    }

    @Override
    public RolDTO.Response actualizar(Long id, RolDTO.Request request) {

        log.info("Actualizando rol con id: {}", id);

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        PermisoDTO permiso = permisoClient.buscarPorId(request.getPermisoId());

        rol.setNombre(request.getNombre());
        rol.setPermisoId(request.getPermisoId());

        Rol actualizado = rolRepository.save(rol);

        return mapToResponse(actualizado, permiso);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando rol con id: {}", id);

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        rolRepository.delete(rol);
    }

    private RolDTO.Response mapToResponse(Rol rol) {

        PermisoDTO permiso = permisoClient.buscarPorId(rol.getPermisoId());

        return new RolDTO.Response(
                rol.getId(),
                rol.getNombre(),
                permiso
        );
    }

    private RolDTO.Response mapToResponse(Rol rol, PermisoDTO permiso) {

        return new RolDTO.Response(
                rol.getId(),
                rol.getNombre(),
                permiso
        );
    }
}
