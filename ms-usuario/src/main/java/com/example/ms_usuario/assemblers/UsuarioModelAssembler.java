package com.example.ms_usuario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_usuario.controller.UsuarioController;
import com.example.ms_usuario.entity.Usuario;



@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioController.class).buscarPorId(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("usuarios"));
    }
}
