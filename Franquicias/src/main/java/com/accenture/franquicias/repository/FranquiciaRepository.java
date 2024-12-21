package com.accenture.franquicias.repository;

import com.accenture.franquicias.model.Franquicia;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FranquiciaRepository extends MongoRepository<Franquicia, String> {
    @Query("{ 'nombre' : ?0 }")
    Franquicia obtenerFranquiciaPorNombre(String nombre);
}
