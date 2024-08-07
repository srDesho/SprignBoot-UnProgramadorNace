package com.cristianml.modelmapper;

import com.cristianml.modelmapper.dto.PersonCustomDTO;
import com.cristianml.modelmapper.dto.PersonDefaultDTO;
import com.cristianml.modelmapper.entities.PersonEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

// Los mappers nos sirven para convertir un objeto(entidad) a otro(puede ser un DTO).
// Los 3 mappers más usados en la industria según Un Programador Nace(en YT) son:
// 1. MapStruct
// 2. ModelMapper
// 3. Orika Mapper

public class Main {
    /* Model Mapper - Default */
    /* public static void main(String[] args) {

        // Creamos nuestro mapeador.
        ModelMapper modelMapper = new ModelMapper();

        // Creamos una entidad
        PersonEntity personEntity = new PersonEntity(1L, "Cristian", "Montaño", "cristianml@mail", (byte) 25, 'M');
        System.out.println(personEntity);

        // Hacemos el mapeo
        // El primer valor dentro del map es el objeto que queremos mapear.
        // El segundo valor es el tipo de objeto al que vamos a convertir(mapear).
        // Esto funciona solo si los atributos tienen los mismos nombres que la entidad, caso contrario muestra null.
        PersonDefaultDTO personDefaultDTO = modelMapper.map(personEntity, PersonDefaultDTO.class);
        System.out.println(personDefaultDTO);
    } */

    /* Model Mapper - Custom */
    // Aquí vamos a mapear entre 2 objetos, pero que tienen distintos nombres de atributos.
    public static void main(String[] args) {
        ModelMapper modelMapper = new ModelMapper();

        // Definimos el tipo de map, esto hará que sea personalizado.
        TypeMap<PersonEntity, PersonCustomDTO> propertyMapper = modelMapper.createTypeMap(PersonEntity.class, PersonCustomDTO.class);

        // Ahora hacemos el mapeo manual para cada atributo
        propertyMapper.addMapping(PersonEntity::getId, PersonCustomDTO::setIdDTO);
        propertyMapper.addMapping(PersonEntity::getName, PersonCustomDTO::setNameDTO);
        propertyMapper.addMapping(PersonEntity::getLastName, PersonCustomDTO::setLastNameDTO);
        propertyMapper.addMapping(PersonEntity::getEmail, PersonCustomDTO::setEmailDTO);
        propertyMapper.addMapping(PersonEntity::getAge, PersonCustomDTO::setAgeDTO);
        propertyMapper.addMapping(PersonEntity::getGender, PersonCustomDTO::setGenderDTO);

        PersonEntity personEntity = new PersonEntity(1L, "Cristian", "Montaño", "cristianml@mail", (byte) 25, 'M');
        System.out.println(personEntity);

        // Ahora sí mapeamos la entidad a un PersonCustomDTO.
        PersonCustomDTO personCustomDTO = propertyMapper.map(personEntity);
        System.out.println(personCustomDTO);
    }
}