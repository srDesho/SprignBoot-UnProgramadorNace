package com.cristianml.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Esta clase es para el entrenador
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
// La anotacion @Table se usa cuando quremos poner un nombre específico en la base de datos, pero si no la agregamos
// esta tabla se crea automáticamente con el mismo nombre de la clase, también podemos usarla cuando la existe la tabla
// en la base de datos, debemos poner el nombre idéntico que tiene la tabla.
// @Table(name = "something")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Usamos la anotación column cuando queremos cambiar el nombre de nuestra tabla, en este caso debemos cambiarlo por
    // nomenclatura del lengua SQL, no debe ser camelCase.
    @Column(name = "last_name")
    private String lastName;
    private String nationality;
    private Integer age;
}
