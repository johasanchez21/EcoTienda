package com.example.ms_producto.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_producto.controller.ProductoController;
import com.example.ms_producto.entity.Producto;



@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
    
    @Override
    public EntityModel<Producto> toModel(Producto producto){
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoController.class).buscarPorId(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoController.class).listarTodos()).withRel("productos"));
    }
}
