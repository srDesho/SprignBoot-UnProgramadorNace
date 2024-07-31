package com.cristianml.jpa.associations.unidirectional.one_to_many;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "uni_employee_one_to_many")
@Table(name = "uni_employee_one_to_many")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
