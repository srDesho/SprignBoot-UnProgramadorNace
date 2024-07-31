package com.cristianml.jpa.associations.bidirectional.one_to_many;

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

@Entity(name = "bi_department_one_to_many")
@Table(name = "bi_department_one_to_many")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lo ideal es que las listas sean mapeadas por un atributo individual.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "department")
    // Esto lo quitamos, ya que no es necesario porque jpa se encarga de crear los nombres a las columnas relacionadas.
    // @JoinColumn(name = "department_id")
    private List<Employee> employeeList;
}
