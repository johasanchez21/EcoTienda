package com.example.ms_rol.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_rol.controller.RolController;
import com.example.ms_rol.entity.Rol;


@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol, EntityModel<Rol>> {
    
    @Override
    public EntityModel<Rol> toModel(Rol rol){
        return EntityModel.of(rol,
            linkTo(methodOn(RolController.class).buscarPorId(rol.getId())).withSelfRel(),
            linkTo(methodOn(RolController.class).listarTodos()).withRel("roles"));
    }
}
