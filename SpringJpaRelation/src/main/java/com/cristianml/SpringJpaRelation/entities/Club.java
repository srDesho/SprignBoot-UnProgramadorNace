package com.cristianml.SpringJpaRelation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Vamos a trabajar las relaciones con un equipo de fútbol.

// Usando las anotaciones de lombok
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
