package com.cristianml.SpringJpaRelation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    // Hacemos la relación con OneToOne con las siguientes configuraciones:
    // targetEntity = Coach.class: le decimos con qué entidad la vamos a relacionar en este caso con la clase Coach.
    // cascade = CacadeType.PERSIST: es para cuando persistimos un registro (guardar) esto automáticamente nos hará
    // un comportamiento en cascada y nos implementará el insert tanto en la entidad Club como en Coach.
    // cascade = CacadeType.REMOVE: Si elimino el equipo inmediatamente me elimna el coach relacionado.
    // cascade = CacadeType.MERGE: Si actualizo un registro en tabla Club tambieén actualizará en la tabla coach.

    // Tip: Es recomendable aprender bien las funcionalidades de estos tipos de cascada, porque si no lo usamos bien
    // nos puede ocasionar error a la hora de manipular datos.
    @OneToOne(targetEntity = Coach.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_coach") // Esta anotación nos sirve para especificar un nombre que queremos.
    private Coach coach;



}
