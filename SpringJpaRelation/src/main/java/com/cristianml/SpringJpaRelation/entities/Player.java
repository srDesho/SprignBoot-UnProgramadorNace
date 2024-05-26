package com.cristianml.SpringJpaRelation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Usamos la anotación column cuando queremos cambiar el nombre de nuestra tabla, en este caso debemos cambiarlo por
    // nomenclatura del lenguaje SQL, no debe ser camelCase.
    @Column(name = "last_name")
    private String lastName;
    private String nationality;
    private Integer age;
    private String position;

    // Relacion ManyToOne


}
