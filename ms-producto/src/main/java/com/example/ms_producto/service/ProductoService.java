package com.example.ms_producto.service;

import java.util.List;

import com.example.ms_producto.dto.ProductoDTO;

public interface ProductoService {

    ProductoDTO.Response crear(ProductoDTO.Request request);

    List<ProductoDTO.Response> listarTodos();

    ProductoDTO.Response buscarPorId(Long id);

    List<ProductoDTO.Response> buscarPorCategoria(Long categoriaId);

    List<ProductoDTO.Response> buscarActivos();

    List<ProductoDTO.Response> buscarPorNombre(String nombre);

    ProductoDTO.Response actualizar(Long id, ProductoDTO.Request request);

    ProductoDTO.Response activar(Long id);

    ProductoDTO.Response desactivar(Long id);

    void eliminar(Long id);
}
