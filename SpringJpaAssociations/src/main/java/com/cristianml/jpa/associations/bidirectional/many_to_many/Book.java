package com.cristianml.jpa.associations.bidirectional.many_to_many;

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

// Muchos libros pertenecen a muchos autores
@Entity(name = "bi_book_many_to_many")
@Table(name = "bi_book_many_to_one")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // La relación en el caso de many to one, la hacemos en la entidad que más nos convenga.
    // Bien podemos crearla aquí o en la entidad Author
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Author.class)
    // Definimos el nombre de la tabla intermedia que se va a generar(muchos a muchos siempre se genera una 3er tabla)
    @JoinTable(name = "book_author_unidirectional", // nombre de la tercer tabla.
    joinColumns = @JoinColumn(name = "book_id"), // nombre de la columna de nuestra entidad actual
    inverseJoinColumns = @JoinColumn(name = "author_id")) // nombre de la columna de la entidad relacionada
    private List<Author> authorList;

    // De nuevo, esta es una relación unidireccional, porque solo una de las entidades puede acceder a la otra,
    // en este caso solo puede acceder a los datos de Author la entidad Book y no viceversa.
}
