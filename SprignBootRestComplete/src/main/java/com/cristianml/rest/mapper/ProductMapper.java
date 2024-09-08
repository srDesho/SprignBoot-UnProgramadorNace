package com.cristianml.rest.mapper;

import com.cristianml.rest.controllers.dto.ProductDTO;
import com.cristianml.rest.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    // Creamos la siguiente instancia de MakerMapper(mismo nombre de la interfaz) con el nombre en mayúscula.
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class); // Aquí pasamos el nombre de la clase.

    // Creamos el objeto que queremos mapear.
    // El nombre lo creamos con la siguiente nomenclatura o sea primero el nombre del objeto que se obtienen los datos,
    // seguido del To y luego el nombre del objeto que queremos convertir (o sea el objeto que se le seteará los datos).
    ProductDTO productToProductDTO(Product product);

}
