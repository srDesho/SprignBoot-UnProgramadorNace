package com.cristianml.jpa.associations.unidirectional.many_to_many;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Muchos autores puedes escribir muchos autores
@Entity(name = "uni_author_many_to_many")
@Table(name = "uni_author_many_to_one")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
