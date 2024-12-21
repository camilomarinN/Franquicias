package com.accenture.franquicias.util;

import com.accenture.franquicias.dto.FranquiciaDTO;
import com.accenture.franquicias.dto.ProductoDTO;
import com.accenture.franquicias.dto.SucursalDTO;
import com.accenture.franquicias.model.Franquicia;
import com.accenture.franquicias.model.Producto;
import com.accenture.franquicias.model.Sucursal;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {

        public static FranquiciaDTO toFranquiciaDTO(Franquicia franquicia) {
                List<SucursalDTO> sucursalesDTO = franquicia.getSucursales()
                                .stream()
                                .map(MapperUtil::toSucursalDTO)
                                .collect(Collectors.toList());
                return new FranquiciaDTO(franquicia.getId(), franquicia.getNombre(), sucursalesDTO);
        }

        public static Franquicia toFranquicia(FranquiciaDTO franquiciaDTO) {
                List<Sucursal> sucursales = franquiciaDTO.getSucursales()
                                .stream()
                                .map(MapperUtil::toSucursal)
                                .collect(Collectors.toList());
                return new Franquicia(franquiciaDTO.getId(), franquiciaDTO.getNombre(), sucursales);
        }

        public static SucursalDTO toSucursalDTO(Sucursal sucursal) {
                List<ProductoDTO> productosDTO = sucursal.getProductos()
                                .stream()
                                .map(MapperUtil::toProductoDTO)
                                .collect(Collectors.toList());
                return new SucursalDTO(sucursal.getNombre(), productosDTO);
        }

        public static Sucursal toSucursal(SucursalDTO sucursalDTO) {
                List<Producto> productos = sucursalDTO.getProductos()
                                .stream()
                                .map(MapperUtil::toProducto)
                                .collect(Collectors.toList());
                return new Sucursal(sucursalDTO.getNombre(), productos);
        }

        public static ProductoDTO toProductoDTO(Producto producto) {
                return new ProductoDTO(producto.getNombre(), producto.getStock());
        }

        public static Producto toProducto(ProductoDTO productoDTO) {
                return new Producto(productoDTO.getNombre(), productoDTO.getStock());
        }
}
