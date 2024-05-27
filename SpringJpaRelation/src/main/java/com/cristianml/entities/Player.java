package com.cristianml.entities;

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
    // El tip para saber qué relación poner es que veamos si la entidad es de uno o muchos, en este caso player es de muchos
    // o sea muchos jugadores sólo pueden pertenecer a un equipo(club).

    // Pasamos los siguientes parámetros:
    // targetEntity: Club.class: le decimos con qué entidad la vamos a relacionar en este caso con la clase Club.
    // Si es necesario pasamos un CascadeType, en este caso no lo usaremos.
    @ManyToOne(targetEntity = Club.class)
    @JoinColumn(name = "id_club")
    private Club club;

}
