package com.example.ms_categoria.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_categoria.controller.CategoriaController;
import com.example.ms_categoria.entity.Categoria;





@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {
    
    @Override
    public EntityModel<Categoria> toModel(Categoria categoria){
        return EntityModel.of(categoria,
            linkTo(methodOn(CategoriaController.class).buscarPorId(categoria.getId())).withSelfRel(),
            linkTo(methodOn(CategoriaController.class).listarTodos()).withRel("roles"));
    }
}
