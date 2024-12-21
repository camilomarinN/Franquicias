package com.accenture.franquicias.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    @Field
    private String nombre;
    @Field
    private List<Producto> productos;
}