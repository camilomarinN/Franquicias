package com.accenture.franquicias.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Field
    private String nombre;
    @Field
    private int stock;
}