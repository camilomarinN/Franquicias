package com.accenture.franquicias.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.accenture.franquicias.model.Sucursal;

public interface SucursalRepository extends MongoRepository<Sucursal, String> {

}
