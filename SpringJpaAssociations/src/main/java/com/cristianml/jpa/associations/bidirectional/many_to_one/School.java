package com.cristianml.jpa.associations.bidirectional.many_to_one;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.

@Data
@AllArgsConstructor
@NoArgsConstructor

// Un colegio puede tener muchos estudiantes
@Entity(name = "bi_school_many_to_one")
@Table(name = "bi_school_many_to_one")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FetchType.LAZY = sirve para que sólo cargue los datos únicamente cuando se usa el llamado a la lista,
    // o sea es una carga perezosa.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    private List<Student> studentList;
}
