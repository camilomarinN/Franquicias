package com.accenture.franquicias.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaDTO {
    private String id;
    private String nombre;
    private List<SucursalDTO> sucursales;
}