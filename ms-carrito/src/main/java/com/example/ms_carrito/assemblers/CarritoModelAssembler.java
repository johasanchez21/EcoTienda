package com.example.ms_carrito.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_carrito.controller.CarritoController;
import com.example.ms_carrito.entity.Carrito;


@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {
    
    @Override
    public EntityModel<Carrito> toModel(Carrito carrito){
        return EntityModel.of(carrito,
            linkTo(methodOn(CarritoController.class).obtenerCarritoPorId(carrito.getId())).withSelfRel(),
            linkTo(methodOn(CarritoController.class).obtenerCarritoPorUsuario(carrito.getId())).withSelfRel());
    }
}
