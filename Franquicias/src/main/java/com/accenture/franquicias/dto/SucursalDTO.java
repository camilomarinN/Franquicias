package com.accenture.franquicias.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    private String nombre;
    private List<ProductoDTO> productos;
}