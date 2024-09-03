package com.cristianml.rest.mapper;

import com.cristianml.rest.controllers.dto.MakerDTO;
import com.cristianml.rest.entities.Maker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MakerMapper {

    // Creamos la siguiente instancia de MakerMapper(mismo nombre de la interfaz) con el nombre en mayúscula.
    MakerMapper INSTANCE = Mappers.getMapper(MakerMapper.class); // Aquí pasamos el nombre de la clase.

    // Creamos el objeto que queremos mapear.
    // El nombre lo creamos con la siguiente nomenclatura o sea primero el nombre del objeto que se obtienen los datos,
    // seguido del To y luego el nombre del objeto que queremos convertir (o sea el objeto que se le seteará los datos).
    MakerDTO makerToMakerDTO(Maker maker);
    Maker makerDTOToMakerEntity(MakerDTO makerDTO);
}
