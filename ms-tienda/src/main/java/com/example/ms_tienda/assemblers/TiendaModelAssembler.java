package com.example.ms_tienda.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_tienda.controller.TiendaController;
import com.example.ms_tienda.entity.Tienda;




@Component
public class TiendaModelAssembler implements RepresentationModelAssembler<Tienda, EntityModel<Tienda>> {
    
    @Override
    public EntityModel<Tienda> toModel(Tienda tienda){
        return EntityModel.of(tienda,
            linkTo(methodOn(TiendaController.class).buscarPorId(tienda.getId())).withSelfRel(),
            linkTo(methodOn(TiendaController.class).listarTodos()).withRel("roles"));
    }
}
