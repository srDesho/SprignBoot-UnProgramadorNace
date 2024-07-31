package com.cristianml.jpa.associations.bidirectional.one_to_many;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "bi_employee_one_to_many")
@Table(name = "bi_employee_one_to_many")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacemos la relación para indicar que es una relación bidireccional
    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id") // Definimos nombre de la columna
    private Department department;
}
