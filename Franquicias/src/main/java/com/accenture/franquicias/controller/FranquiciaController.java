package com.accenture.franquicias.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.franquicias.model.Franquicia;
import com.accenture.franquicias.model.Producto;
import com.accenture.franquicias.service.FranquiciaService;
import com.accenture.franquicias.service.ProductoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/franquicias")
public class FranquiciaController {

    @Autowired
    private FranquiciaService franquiciaService;
    private ProductoService productoService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> obtenerFranquicias() {
        List<Franquicia> franquicias = franquiciaService.obtenerFranquicias();
        Map<String, Object> response = new HashMap<>();

        if (franquicias.isEmpty()) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "No se encontraron franquicias");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("HttpStatus", HttpStatus.OK.value());
            response.put("mensaje", "Consulta exitosa");
            response.put("respuesta", franquicias);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> CrearFranquicia(@RequestBody Franquicia franquicia) {
        Franquicia Object = franquiciaService.crearFranquicia(franquicia);
        Map<String, Object> response = new HashMap<>();

        if (Object == null || Object.getNombre() == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "La franquicia enviada no es valida, valide la información");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("HttpStatus", HttpStatus.OK.value());
            response.put("mensaje", "Consulta exitosa");
            response.put("respuesta", Object);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/{nombreFranquicia}/productos/max-stock")
    public ResponseEntity<Map<String, Producto>> obtenerProductosConMasStock(
            @PathVariable String nombreFranquicia) {
        try {
            Map<String, Producto> productosConMasStock = productoService
                    .obtenerProductoConMasStockPorSucursal(nombreFranquicia);
            return ResponseEntity.ok(productosConMasStock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/ActualizarNombre/{nombreFranquicia}")
    public ResponseEntity<Map<String, Object>> actualizarNombreFranquicia(
            @PathVariable String nombreFranquicia,
            @RequestParam String nuevoNombre) {

        Map<String, Object> response = new HashMap<>();

        Franquicia franquicia = franquiciaService.obtenerFranquiciaPorNombre(nombreFranquicia);
        if (franquicia == null) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "Franquicia no encontrada");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            franquicia.setNombre(nuevoNombre);
            franquiciaService.ActualizarFranquicia(franquicia);
            response.put("HttpStatus", HttpStatus.OK.value());
            response.put("mensaje", "Nombre de la franquicia actualizado exitosamente");
            response.put("respuesta", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("HttpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("mensaje", "Ocurrió un error al actualizar el nombre");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
