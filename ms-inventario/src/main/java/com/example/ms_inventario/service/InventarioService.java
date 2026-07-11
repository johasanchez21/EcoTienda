package com.example.ms_inventario.service;

import java.util.List;

import com.example.ms_inventario.dto.InventarioDTO;

public interface InventarioService {

    InventarioDTO.Response crear(InventarioDTO.Request request);

    InventarioDTO.Response agregarStock(Long productoId, Long tiendaId, Integer stock);

    InventarioDTO.Response reducirStock(Long productoId, Long tiendaId, Integer cantidad);

    InventarioDTO.Response consultarStock(Long productoId, Long tiendaId);

    List<InventarioDTO.Response> obtenerInventarioPorTienda(Long tiendaId);
    
    void eliminar(Long id);
}
