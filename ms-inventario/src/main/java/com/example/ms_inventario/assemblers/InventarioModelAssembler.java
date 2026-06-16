package com.example.ms_inventario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_inventario.controller.InventarioController;
import com.example.ms_inventario.entity.Inventario;


@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {
    
    @Override
    public EntityModel<Inventario> toModel(Inventario inventario){
        return EntityModel.of(inventario,
            linkTo(methodOn(InventarioController.class).obtenerInventarioPorTienda(inventario.getTiendaId())).withSelfRel());

    }
}
