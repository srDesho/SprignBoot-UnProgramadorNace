package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
// Usamos la siguiente anotación para que podamos hacer uso de nuestro sp (stored procedure)
// o en otros términos para poder mapear nuestra SP con JPA.
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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "last_name")
    private String lastName;
}
