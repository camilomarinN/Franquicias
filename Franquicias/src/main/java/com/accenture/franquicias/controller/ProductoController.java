package com.accenture.franquicias.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.franquicias.model.Franquicia;
import com.accenture.franquicias.model.Producto;
import com.accenture.franquicias.model.Sucursal;
import com.accenture.franquicias.service.FranquiciaService;
import com.accenture.franquicias.service.ProductoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/franquicia/{nombreFranquicia}/sucursal/{sucursalNombre}/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    private FranquiciaService franquiciaService;

    @PostMapping
    public ResponseEntity<Franquicia> agregarProducto(
            @PathVariable String nombreFranquicia,
            @PathVariable String sucursalNombre,
            @RequestBody Producto nuevoProducto) {
        try {
            Franquicia franquiciaActualizada = productoService.agregarProducto(nombreFranquicia, sucursalNombre,
                    nuevoProducto);
            return ResponseEntity.ok(franquiciaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{productoNombre}")
    public ResponseEntity<Franquicia> eliminarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalNombre,
            @PathVariable String productoNombre) {
        try {
            Franquicia franquiciaActualizada = productoService.eliminarProducto(franquiciaId, sucursalNombre,
                    productoNombre);
            return ResponseEntity.ok(franquiciaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/modificarStock/{productoNombre}")
    public ResponseEntity<Map<String, Object>> modificarStockProducto(
            @PathVariable String nombreFranquicia,
            @PathVariable String nombreSucursal,
            @PathVariable String productoNombre,
            @RequestParam int nuevoStock) {

        Map<String, Object> response = new HashMap<>();

        Franquicia franquicia = franquiciaService.obtenerFranquiciaPorNombre(nombreFranquicia);
        if (franquicia == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Franquicia no encontrada");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getNombre().equals(nombreSucursal))
                .findFirst()
                .orElse(null);

        if (sucursal == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Sucursal no encontrada");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Producto producto = sucursal.getProductos().stream()
                .filter(p -> p.getNombre().equals(productoNombre))
                .findFirst()
                .orElse(null);

        if (producto == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Producto no encontrado");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            producto.setStock(nuevoStock);
            franquiciaService.ActualizarFranquicia(franquicia);
            response.put("mensaje", "Stock del producto actualizado exitosamente");
            response.put("respuesta", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("HttpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("mensaje", "Ocurrió un error al actualizar el stock");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ActualizarNombre/{nombreProducto}")
    public ResponseEntity<Map<String, Object>> actualizarNombreProducto(
            @PathVariable String nombreFranquicia,
            @PathVariable String nombreSucursal,
            @PathVariable String nombreProducto,
            @RequestParam String nuevoNombre) {

        Map<String, Object> response = new HashMap<>();

        Franquicia franquicia = franquiciaService.obtenerFranquiciaPorNombre(nombreFranquicia);
        if (franquicia == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Franquicia no encontrada");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getNombre().equals(nombreSucursal))
                .findFirst()
                .orElse(null);

        if (sucursal == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Sucursal no encontrada");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Producto producto = sucursal.getProductos().stream()
                .filter(p -> p.getNombre().equals(nombreProducto))
                .findFirst()
                .orElse(null);

        if (producto == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Producto no encontrado");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            producto.setNombre(nuevoNombre);
            franquiciaService.ActualizarFranquicia(franquicia);
            response.put("HttpStatus", HttpStatus.OK.value());
            response.put("mensaje", "Nombre del producto actualizado exitosamente");
            response.put("respuesta", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("HttpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("mensaje", "Ocurrió un error al actualizar el nombre del producto");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
