package com.example.ms_usuario.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ms_usuario.client.RolClient;
import com.example.ms_usuario.dto.RolDTO;
import com.example.ms_usuario.dto.UsuarioDTO;
import com.example.ms_usuario.entity.Usuario;
import com.example.ms_usuario.repository.UsuarioRepository;
import com.example.ms_usuario.service.UsuarioService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolClient rolClient;

    @Override
    public UsuarioDTO.Response crear(UsuarioDTO.Request request) {

        log.info("Creando usuario: {}", request.getEmail());

        if (usuarioRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("Ya existe un usuario con ese rut");
        }

        RolDTO rol = rolClient.buscarPorId(request.getRolId());

        Usuario usuario = new Usuario();
        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);
        usuario.setRolId(request.getRolId());

        Usuario guardado = usuarioRepository.save(usuario);

        return mapToResponse(guardado, rol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> listarTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO.Response buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioDTO.Response actualizar(Long id, UsuarioDTO.Request request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        RolDTO rol = rolClient.buscarPorId(request.getRolId());

        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : usuario.getActivo());
        usuario.setRolId(request.getRolId());

        Usuario actualizado = usuarioRepository.save(usuario);

        return mapToResponse(actualizado, rol);
    }

    @Override
    public UsuarioDTO.Response activar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setActivo(true);

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO.Response desactivar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setActivo(false);

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuarioRepository.delete(usuario);
    }

    private UsuarioDTO.Response mapToResponse(Usuario usuario) {

        RolDTO rol = rolClient.buscarPorId(usuario.getRolId());

        return mapToResponse(usuario, rol);
    }

    private UsuarioDTO.Response mapToResponse(Usuario usuario, RolDTO rol) {

        return new UsuarioDTO.Response(
                usuario.getId(),
                usuario.getRut(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getActivo(),
                rol
        );
    }
}
