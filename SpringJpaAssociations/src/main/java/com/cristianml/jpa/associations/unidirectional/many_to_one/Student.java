package com.cristianml.jpa.associations.unidirectional.many_to_one;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.

@Data
@AllArgsConstructor
@NoArgsConstructor

// Muchos estudiantes pertenecen a un solo colegio
@Entity(name = "uni_student_many_to_one")
@Table(name = "uni_student_many_to_one")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacemos la relación aquí porque es donde tenemos la relación de muchos a 1(uno es el colegio)
    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "school_id")
    private School school;
}
