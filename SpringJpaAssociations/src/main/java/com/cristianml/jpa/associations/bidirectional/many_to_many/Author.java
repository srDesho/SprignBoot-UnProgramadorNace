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

// Muchos autores puedes escribir muchos autores
@Entity(name = "bi_author_many_to_many")
@Table(name = "bi_author_many_to_one")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Para hacer la relación bidireccional simplemente creamos la lista de libros
    // y la mapeamos con la entidad principal.
    @ManyToMany(mappedBy = "authorList", fetch = FetchType.LAZY)
    private List<Book> bookList;
}
