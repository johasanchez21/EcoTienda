package com.example.ms_pedido.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_pedido.controller.PedidoController;
import com.example.ms_pedido.entity.Pedido;


@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {
    
    @Override
    public EntityModel<Pedido> toModel(Pedido pedido){
        return EntityModel.of(pedido,
            linkTo(methodOn(PedidoController.class).buscarPorId(pedido.getId())).withSelfRel(),
            linkTo(methodOn(PedidoController.class).listarTodos()).withRel("permisos"));
    }
}
