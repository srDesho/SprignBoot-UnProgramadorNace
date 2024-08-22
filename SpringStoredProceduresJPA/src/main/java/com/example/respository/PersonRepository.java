package com.example.respository;

import com.example.entity.Person;
import jakarta.persistence.NamedStoredProcedureQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
    // Hacemos uso de la anotación @Procedure simplemente agregamos como atributo el nombre de la SP.
    // @Procedure(name = "verPersonas")
    // List<Person> verPersonas();

    // Forma 4
    // Hacemos uso de la misma anotación @Procedure pero sin darle explícitamente el nombre como atributo.
    // Pero el nombre del método sí o sí debe de ser el mismo que el nombre del SP de la db.
    // @Procedure
    // List<Person> verPersonas();

    /* 2. CON PARÁMETROS */
    // Forma 1

    /*@Procedure(name = "Person.buscarPersona")
    // El nombre del argumento "person_id" debe ser el mismo que definimos en la clase person en la configuración
    // de la anotación @StoredProcedureParameter(name = "person_id)
    // Person buscarPersona(Long person_id);

    // Si no también podemos hacer uso del camelCase normal pero para mapear el nombre lo hacemos con un @Param
    Person buscarPersona(@Param("person_id") Long personId);*/

    // Forma 2
    // En esta forma quitamos la configuración que hicimos en nuestra entidad, esta configuración:
    // Ya que no es necesaria la quitamos

    /*@NamedStoredProcedureQuery(
        // de nombre generalmente se pone el nombre de la clase seguido de un punto y el nombre de nuestro sp de la db.
        name = "Person.buscarPersona",
        // nombre exacto de nuestro SP de la db.
        procedureName = "buscarPersona",
        // Aquí mapeamos nuestra clase que es esta misma
        resultClasses = Person.class,
        // En caso de trabajar con parámetros como en el buscarPersona que se debe pasar un ID, pues agregamos
        // el siguiente atributo dentro de llaves.
        parameters = {
                @StoredProcedureParameter(
                        mode = ParameterMode.IN, // definimos que será parámetro de entrada con el .IN
                        name = "person_id", // definimos un nombre
                        type = Long.class) // definimos el tipo del parámetro, en este caso es un id de tipo Long
        }
)*/

    // Hacemos uso de la anotación @Query
    // el valor será la consulta que se hace para llamar una SP y agregamos el atributo nativeQuery para decirle a JPA
    // que es una consulta nativa de SQL.
    // El parámetro que le pasamos en buscarPerson(:id) debe ir con : y ser igual que el nombre del argumento que definimos.
    // @Query(value = "CALL buscarPersona(:id)", nativeQuery = true)
    // Person buscarPersona(Long id); // el nombre del argumento es id, así que este nombre debe ir en la @Query(anterior línea)

    // Esto es lo mismo solo que hacemos uso del @Param
    /*@Query(value = "CALL buscarPersona(:person_id)", nativeQuery = true)
    Person buscarPersona(@Param("person_id") Long personId);*/

    // Forma 3
    // Hacemos uso de @Procedure con el atributo name mapeando el nombre de nuestro SP.
    /*@Procedure(name = "buscarPersona")
    Person buscarPersona(Long personId);*/

    // Forma 4
    @Procedure
    // Nos aseguramos que el nombre del método sea igual que el nombre definido de nuestra SP de la DB.
    Person buscarPersona(Long idPerson);

    // Trabajamos con los siguientes procedimientos almacenados de nuestra DB.

    // SP insertarPersona
    // @Procedure(procedureName = "insertarPersona")
    // void insertarPersona(String p_name, String p_last_name);

    // Si queremos capturar a la persona insertada en un objeto, debemos cambiar la lógica de nuestro SP en la DT, ejemplo:
    // Este es mi SP:
    /*BEGIN
        INSERT INTO Person (name, last_name) VALUES (p_name, p_last_name);
    END*/

    // Lo cambiamos de la siguiente manera:
    /*
    BEGIN
        DECLARE last_insert_id INT;

        -- Inserta la nueva persona
        INSERT INTO Person (name, last_name) VALUES (p_name, p_last_name);

        -- Obtiene el ID de la persona recién insertada
        SET last_insert_id = LAST_INSERT_ID();

        -- Retorna la persona recién insertada
        SELECT * FROM Person WHERE id = last_insert_id;
    END
    */
    // Ahora hacemos el método que retornará una persona
    @Procedure(procedureName = "insertarPersona")
    Person insertarPersona(String name, String lastName); // No importa el nombre que le damos a los argumentos

    // SP actualizarPersona
    // Debemos ver bien nuestro SP:
    /*
    BEGIN
        UPDATE Person SET name = p_name, last_name = p_last_name WHERE id = p_id;
    END
    */
    // El primer argumento debemo poner el id, luego el name y por último de lastName
    @Procedure
    void actualizarPersona(Long id, String name, String lastName);
}
