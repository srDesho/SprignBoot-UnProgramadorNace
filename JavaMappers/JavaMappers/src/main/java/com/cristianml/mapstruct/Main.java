package com.cristianml.mapstruct;

import com.cristianml.mapstruct.dto.PersonCustomDTO;
import com.cristianml.mapstruct.dto.PersonDefaultDTO;
import com.cristianml.mapstruct.entities.PersonEntity;
import com.cristianml.mapstruct.mapper.PersonMapper;
import org.mapstruct.Mapping;

public class Main {

    /* MapStruct - Default */
    // Este mapeo es cuando tenemos los nombres iguales de los atributos de cada objeto mapeado.

    // MapStruct funciona de una forma diferente a los otros mappers, debemos trabajar con Interfaces.
    // Entonces debemos crear un directorio llamado mapper(en el cual irán las interfaces).
    public static void main(String[] args) {

        // Creamos la entidad y mostramos
        PersonEntity personEntity = new PersonEntity(1L, "Cristian", "Montaño", "cristianml@mail", (byte) 25, 'M');
        System.out.println(personEntity);

        // Hacemos el mapeo usando la interfaz que creamos en nuestro paquete mapper
        PersonDefaultDTO personDefaultDTO = PersonMapper.INSTANCE.personToPersonDefaultDTO(personEntity);
        System.out.println(personDefaultDTO);

        // ============================================================================================================
        System.out.println("\n======================================== CUSTOM ========================================");
        // Ahora para cuando los nombres de los atributos no son iguales de ambos objetos mapeados.
        // Creamos otra entidad
        PersonEntity personEntityDan = new PersonEntity(2L, "Daniel", "Lopez", "danielml@mail", (byte) 23, 'M');
        System.out.println(personEntityDan);

        // Hacemos el mapeo con el método personToPersonCustomDTO que creamos en nuestra interfaz PersonMapper.
        PersonCustomDTO personCustomDTO = PersonMapper.INSTANCE.personToPersonCustomDTO(personEntityDan);
        System.out.println(personCustomDTO);
    }
}
