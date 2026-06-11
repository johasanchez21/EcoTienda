package com.example.ms_producto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ms_producto.client.CategoriaClient;
import com.example.ms_producto.dto.CategoriaDTO;
import com.example.ms_producto.dto.ProductoDTO;
import com.example.ms_producto.entity.Producto;
import com.example.ms_producto.repository.ProductoRepository;
import com.example.ms_producto.service.ProductoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaClient categoriaClient;

    @Override
    public ProductoDTO.Response crear(ProductoDTO.Request request) {

        log.info("Creando producto: {}", request.getNombre());

        CategoriaDTO categoria = categoriaClient.buscarPorId(request.getCategoriaId());

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setActivo(request.getActivo() != null ? request.getActivo() : true);
        producto.setCategoriaId(request.getCategoriaId());

        Producto guardado = productoRepository.save(producto);

        return mapToResponse(guardado, categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO.Response> listarTodos() {

        return productoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO.Response buscarPorId(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        return mapToResponse(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO.Response> buscarPorCategoria(Long categoriaId) {

        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO.Response> buscarActivos() {

        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO.Response> buscarPorNombre(String nombre) {

        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO.Response actualizar(Long id, ProductoDTO.Request request) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        CategoriaDTO categoria = categoriaClient.buscarPorId(request.getCategoriaId());

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setActivo(request.getActivo() != null ? request.getActivo() : producto.getActivo());
        producto.setCategoriaId(request.getCategoriaId());

        Producto actualizado = productoRepository.save(producto);

        return mapToResponse(actualizado, categoria);
    }

    @Override
    public ProductoDTO.Response activar(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        producto.setActivo(true);

        return mapToResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoDTO.Response desactivar(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        producto.setActivo(false);

        return mapToResponse(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        productoRepository.delete(producto);
    }

    private ProductoDTO.Response mapToResponse(Producto producto) {

        CategoriaDTO categoria = categoriaClient.buscarPorId(producto.getCategoriaId());

        return mapToResponse(producto, categoria);
    }

    private ProductoDTO.Response mapToResponse(Producto producto, CategoriaDTO categoria) {

        return new ProductoDTO.Response(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getActivo(),
                categoria
        );
    }
}
