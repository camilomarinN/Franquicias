package com.accenture.franquicias.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.franquicias.model.Franquicia;
import com.accenture.franquicias.model.Sucursal;
import com.accenture.franquicias.service.FranquiciaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/Sucursales")
public class SucursalController {

    @Autowired
    private FranquiciaService franquiciaService;

    @PostMapping("/franquicia/{nombreFranquicia}")
    public ResponseEntity<Map<String, Object>> crearSucursalDeFranquicia(
            @PathVariable String nombreFranquicia,
            @RequestBody Sucursal sucursal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Franquicia franquicia = franquiciaService.obtenerFranquiciaPorNombre(nombreFranquicia);

            if (franquicia == null) {
                response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
                response.put("mensaje", "La franquicia no es válida. Verifique que exista en el sistema.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            franquicia.getSucursales().add(sucursal);
            franquiciaService.ActualizarFranquicia(franquicia);

            response.put("HttpStatus", HttpStatus.OK.value());
            response.put("mensaje", "Sucursal creada exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            response.put("HttpStatus", HttpStatus.NOT_FOUND.value());
            response.put("mensaje", "La franquicia no se encontró.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("HttpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("mensaje", "Ocurrió un error creando la nueva sucursal.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/franquicia/{nombreFranquicia}/sucursal/{nombreSucursal}/ActualizarNombre")
    public ResponseEntity<Map<String, Object>> actualizarNombreSucursal(
            @PathVariable String nombreFranquicia,
            @PathVariable String nombreSucursal,
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

        try {
            sucursal.setNombre(nuevoNombre);
            franquiciaService.ActualizarFranquicia(franquicia); // Guardar los cambios
            response.put("HttpStatus", HttpStatus.OK.value());
            response.put("mensaje", "Nombre de la sucursal actualizado exitosamente");
            response.put("respuesta", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("HttpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("mensaje", "Ocurrió un error al actualizar el nombre de la sucursal");
            response.put("respuesta", new HashMap<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
