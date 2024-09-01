package com.cristianml.mapstruct.mapper;

import com.cristianml.mapstruct.dto.PersonCustomDTO;
import com.cristianml.mapstruct.dto.PersonDefaultDTO;
import com.cristianml.mapstruct.entities.PersonEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T21:16:18-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDefaultDTO personToPersonDefaultDTO(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        PersonDefaultDTO personDefaultDTO = new PersonDefaultDTO();

        personDefaultDTO.setId( personEntity.getId() );
        personDefaultDTO.setName( personEntity.getName() );
        personDefaultDTO.setLastName( personEntity.getLastName() );
        personDefaultDTO.setEmail( personEntity.getEmail() );
        personDefaultDTO.setAge( personEntity.getAge() );
        personDefaultDTO.setGender( personEntity.getGender() );

        return personDefaultDTO;
    }

    @Override
    public PersonCustomDTO personToPersonCustomDTO(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        PersonCustomDTO personCustomDTO = new PersonCustomDTO();

        personCustomDTO.setIdDTO( personEntity.getId() );
        personCustomDTO.setNameDTO( personEntity.getName() );
        personCustomDTO.setLastNameDTO( personEntity.getLastName() );
        personCustomDTO.setEmailDTO( personEntity.getEmail() );
        personCustomDTO.setAgeDTO( personEntity.getAge() );
        personCustomDTO.setGenderDTO( personEntity.getGender() );

        return personCustomDTO;
    }
}
