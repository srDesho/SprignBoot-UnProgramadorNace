package com.cristianml.mapstruct.mapper;

import com.cristianml.mapstruct.dto.PersonCustomDTO;
import com.cristianml.mapstruct.dto.PersonDefaultDTO;
import com.cristianml.mapstruct.entities.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// Agregamos la siguiente anotación.
@Mapper
public interface PersonMapper {

    // Creamos la siguiente instancia de PersonMapper(mismo nombre de la interfaz) con el nombre en mayúscula.
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class); // Aquí pasamos el nombre de la clase.

    // Este primer mapeo va a ser para cuando queremos mapear nombres de atributos iguales entre los 2 objetos a mapear.

    // Creamos el objeto que queremos mapear.
    // El nombre lo creamos con la siguiente nomenclatura o sea primero el nombre del objeto que se obtienen los datos,
    // seguido del To y luego el nombre del objeto que queremos convertir (o sea el objeto que se le seteará los datos).
    PersonDefaultDTO personToPersonDefaultDTO(PersonEntity personEntity); // Parámetro el objeto de partida.

    // =========================================================================================================
    // Ahora para cuando los nombres de los atributos no son iguales de ambos objetos mapeados.

    // Usamos la siguiente anotación @Mapping para hacer el mapeo de cada atributo
    // source: es desde el objeto donde se va a obtener los atributos a setear.
    // target: es el objeto donde serán seteados los atributos (o sea escribimos los atributos del objeto convertido)
    @Mapping(source = "id", target = "idDTO")
    @Mapping(source = "name", target = "nameDTO")
    @Mapping(source = "lastName", target = "lastNameDTO")
    @Mapping(source = "email", target = "emailDTO")
    @Mapping(source = "age", target = "ageDTO")
    @Mapping(source = "gender", target = "genderDTO")
    PersonCustomDTO personToPersonCustomDTO(PersonEntity personEntity);
}
