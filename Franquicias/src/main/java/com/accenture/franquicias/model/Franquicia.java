package com.accenture.franquicias.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Franquicias")
public class Franquicia {
    @Id
    private String id;
    @Field
    private String nombre;
    @Field
    private List<Sucursal> sucursales;

    public Boolean isEmpty() {
        if ((this.getId() == null || this.getId().isEmpty()) && (this.getNombre() == null || this.getNombre().isEmpty())
                && (this.getSucursales() == null || this.getSucursales().isEmpty())) {
            return true;
        }
        return false;
    }
}