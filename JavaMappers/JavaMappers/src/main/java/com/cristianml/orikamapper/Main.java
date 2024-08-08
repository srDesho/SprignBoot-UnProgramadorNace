package com.cristianml.orikamapper;

import com.cristianml.modelmapper.dto.PersonDefaultDTO;
import com.cristianml.orikamapper.dto.PersonCustomDTO;
import com.cristianml.orikamapper.entities.PersonEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class Main {

    /* Orika Mapper - Default */
    // Este mapeo es cuando tenemos los nombres iguales de los atributos de cada objeto mapeado.

    // Antes de empezar a trabajar con Orika Mapper, debemos de hacer una modificación en nuestra Java Virtual Machine(JVM)
    // Porque sino nos dará error.
    // En intellij Idea debemos dirigirnos a lado izquierdo de nuestro botón run en la parte superior.
    // Clic -> clic en edit configuration -> nos dirigimos al main y si no tenemos uno creado y solo nos aparece Current File
    // creamos uno dando clic en el signo + -> depende si tenemos un proyecto de aplicación o maven seleccionamos el que
    // necesitemos -> agregamos nuestro main en el signo $ y aplicamos y guardamos -> luego en el que hemos creado damos
    // clic en los 3 puntos o si está seleccionado damos clic en edit configuration -> clic en modify options -> agregamos
    // esto: --add-opens java.base/java.lang=ALL-UNNAMED en la parte de VM options -> apply -> ok.
    // Con eso le decimos a la Java Virtual Machine que reconozca todos los módulos que no han sido nombrados.

    /* public static void main(String[] args) {
        // Creamos nuestro mapper
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        // Definimos el mapeo.
        mapperFactory.classMap(PersonEntity.class, PersonDefaultDTO.class);

        // Para hacer el mapeo necesitamos el MapperFacade
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();

        // Creamos nuestra entidad Person
        PersonEntity personEntity = new PersonEntity(1L, "Cristian", "Montaño", "cristianml@mail", (byte) 25, 'M');
        System.out.println(personEntity);

        // Completamos el mapeo.
        PersonDefaultDTO personDefaultDTO = mapperFacade.map(personEntity, PersonDefaultDTO.class);
        System.out.println(personDefaultDTO);
    } */

    /* Orika Mapper - Custom */
    // Este mapeo es cuando tenemos nombres distintos de los atributos entre los objetos mapeados.
    public static void main(String[] args) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(PersonEntity.class, PersonCustomDTO.class)
                // Agregamos el método field para mapear cada atributo, el primer parámetro es desde el objeto
                // donde se va a obtener los datos, el sdo es a donde el objeto que se va a setear los datos mapeados.
                .field("id", "idDTO")
                .field("name", "nameDTO")
                .field("lastName", "lastNameDTO")
                .field("email", "emailDTO")
                .field("age", "ageDTO")
                .field("gender", "genderDTO")
                .byDefault() // Para que todo lo que no se ha especificado tenga un funcionamiento por defecto
                .register(); // Para que todo sea registrado.

        // Creamos nuestro MapperFacade
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();

        // Creamos nuestra entidad Person
        PersonEntity personEntity = new PersonEntity(1L, "Cristian", "Montaño", "cristianml@mail", (byte) 25, 'M');
        System.out.println(personEntity);

        // Hacemos nuestro mapeo.
        PersonCustomDTO personCustomDTO = mapperFacade.map(personEntity, PersonCustomDTO.class);
        System.out.println(personCustomDTO);
    }
}
