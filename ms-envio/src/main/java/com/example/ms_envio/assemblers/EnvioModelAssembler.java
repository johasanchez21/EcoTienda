package com.example.ms_envio.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_envio.controller.EnvioController;
import com.example.ms_envio.entity.Envio;



@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {
    
    @Override
    public EntityModel<Envio> toModel(Envio envio){
        return EntityModel.of(envio,
            linkTo(methodOn(EnvioController.class).buscarPorId(envio.getId())).withSelfRel(),
            linkTo(methodOn(EnvioController.class).listarTodos()).withRel("permisos"));
    }
}
