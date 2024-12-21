package com.accenture.franquicias.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.franquicias.model.Franquicia;
import com.accenture.franquicias.model.Producto;
import com.accenture.franquicias.model.Sucursal;
import com.accenture.franquicias.repository.FranquiciaRepository;

@Service
public class ProductoService {

    @Autowired
    private FranquiciaRepository franquiciaRepository;

    public Franquicia agregarProducto(String nombreFranquicia, String sucursalNombre, Producto nuevoProducto) {
        Franquicia franquicia = franquiciaRepository.obtenerFranquiciaPorNombre(nombreFranquicia);
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getNombre().equals(sucursalNombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada"));
        sucursal.getProductos().add(nuevoProducto);
        return franquiciaRepository.save(franquicia);
    }

    public Franquicia eliminarProducto(String nombreFranquicia, String sucursalNombre, String productoNombre) {
        Franquicia franquicia = franquiciaRepository.obtenerFranquiciaPorNombre(nombreFranquicia);
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getNombre().equals(sucursalNombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada"));
        sucursal.getProductos().removeIf(p -> p.getNombre().equals(productoNombre));
        return franquiciaRepository.save(franquicia);
    }

    public Map<String, Producto> obtenerProductoConMasStockPorSucursal(String nombreFranquicia) {
        Franquicia franquicia = franquiciaRepository.obtenerFranquiciaPorNombre(nombreFranquicia);
        Map<String, Producto> productosPorSucursal = new HashMap<>();
        for (Sucursal sucursal : franquicia.getSucursales()) {
            Producto productoConMasStock = sucursal.getProductos().stream()
                    .max(Comparator.comparingInt(Producto::getStock))
                    .orElse(null);

            productosPorSucursal.put(sucursal.getNombre(), productoConMasStock);
        }

        return productosPorSucursal;
    }
}
