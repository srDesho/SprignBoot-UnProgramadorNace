package com.example.respository;

import com.example.entity.Person;
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
    @Procedure(name = "Person.verPersonas") // Aquí escribimos el nombre que le dimos en el mapeo de la entidad
    List<Person> verPersonas();

    /* 2. CON PARÁMETROS */

}
