package com.cristianml.jpa.associations.unidirectional.one_to_many;

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

@Entity(name = "uni_department_one_to_many")
@Table(name = "uni_department_one_to_many")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacemos la relación unidireccional desde esta entidad porque es la que lleva 1 a muchos
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Employee.class)
    @JoinColumn(name = "department_id")
    private List<Employee> employeeList;
    // Lo mismo aquí, solo es una relación unidireccional porque solo desde aquí se puede acceder a employees.
}
