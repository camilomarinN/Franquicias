package com.accenture.franquicias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.franquicias.model.Franquicia;
import com.accenture.franquicias.repository.FranquiciaRepository;

public class SucursalService {

    @Autowired
    private FranquiciaRepository franquiciaRepository;

    public List<Franquicia> obtenerFranquicias() {
        return franquiciaRepository.findAll();
    }

    public Franquicia crearFranquicia(Franquicia franquicia) {
        return franquiciaRepository.insert(franquicia);
    }

    public Franquicia ActualizarFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }

    public void eliminarFranquicia(String id) {
        franquiciaRepository.deleteById(id);
    }

}
