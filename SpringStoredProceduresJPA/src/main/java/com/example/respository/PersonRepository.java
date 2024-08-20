package com.example.respository;

import com.example.entity.Person;
import jakarta.persistence.NamedStoredProcedureQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    // Existen 2 tipos de procedimientos almacenados.
    /* 1. SIN PARÁMETROS */

    // Forma 1
    // Este método es para el sp verPersonas
    // @Procedure(name = "Person.verPersonas") // Aquí escribimos el nombre que le dimos en el mapeo de la entidad
    // List<Person> verPersonas();

    // Forma 2
    // En esta forma quitamos la configuración que hicimos en nuestra entidad, esta configuración:
    // Ya que no es necesaria la quitamos
/*    @NamedStoredProcedureQuery(
            // de nombre generalmente se pone el nombre de la clase seguido de un punto y el nombre de nuestro sp de la db.
            name = "Person.verPersonas",
            // nombre exacto de nuestro sp de la db.
            procedureName = "verPersonas",
            // Aquí mapeamos nuestra clase que es esta misma
            resultClasses = Person.class
    )*/

    // Vamos a hacer uso de la anotación @Query
    // el valor será la consulta que se hace para llamar una SP y agregamos el atributo nativeQuery para decirle a JPA
    // que es una consulta nativa de SQL.
    // @Query(value = "CALL verPersonas()", nativeQuery = true)
    // List<Person> verPersonas();

    // Forma 3
    // Hacemos uso de la anotación @Procedure
    @Procedure(name = "verPersonas")
    List<Person> verPersonas();

    /* 2. CON PARÁMETROS */

}
