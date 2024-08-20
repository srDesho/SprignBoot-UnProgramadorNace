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
        name = "Person.verPersonas",
        // nombre exacto de nuestro sp de la db.
        procedureName = "verPersonas",
        // Aquí mapeamos nuestra clase que es esta misma
        resultClasses = Person.class
)*/
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "last_name")
    private String lastName;
}
