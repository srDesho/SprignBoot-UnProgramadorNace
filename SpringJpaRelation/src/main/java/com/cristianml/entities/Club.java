package com.cristianml.entities;

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
    // cascade = CascadeType.PERSIST: es para cuando persistimos un registro (guardar) esto automáticamente nos hará
    // un comportamiento en cascada y nos implementará el insert tanto en la entidad Club como en Coach.
    // cascade = CascadeType.REMOVE: Si elimino el equipo inmediatamente me elimna el coach relacionado.
    // cascade = CascadeType.MERGE: Si actualizo un registro en tabla Club también actualizará en la tabla coach.

    // Tip: Es recomendable aprender bien las funcionalidades de estos tipos de cascada, porque si no lo usamos bien
    // nos puede ocasionar error a la hora de manipular datos.
    @OneToOne(targetEntity = Coach.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_coach") // Esta anotación nos sirve para especificar un nombre que queremos.
    private Coach coach;

    // Relacion OneToMany
    // El tip para saber qué relación poner es que veamos si la entidad es de uno o muchos, en este caso club es sólo 1
    // o sea un club puede tener muchos jugadores.

    // Pasamos los sgts parámetros:
    // targetEntity: Coach.class: le decimos con qué entidad la vamos a relacionar en este caso con la clase Player.
    // FetchType.LAZY: Si queremos que la carga sea perezosa, o sea que cuando tengamos un objeto Club creado, va a
    // obtener la plantilla de jugadores sólo cuando la solicite yo, o sea si hacemos uso del método getPlayers, nos va
    // a obtener los datos desde la DB.
    // FetchType.EAGLE: A diferencia del LAZY, este ni bien instanciemos un club nos devolverá la lista de players.
    // Muchas veces no se necesita tener la lista de una tabla relacionada, porque por ejemplo si sólo queremos el nombre
    // de nuestra entidad Club, si tengo el FetchType me traerá toda la lista, y esto será un guardado en memoria innecesario.
    // En ese caso hacemos uso de LAZY.
    // mapperdBy = "club": mappeamos con el nombre del objeto relacionado en la otra clase, esto para que jpa indique que
    // es ese atributo será la llave foránea en la clase club.
    @OneToMany(targetEntity = Player.class, fetch = FetchType.LAZY, mappedBy = "club")
    private List<Player> players;

    // Relación ManyToOne
    @ManyToOne(targetEntity = FootballAssociation.class)
    private FootballAssociation footballAssociation;
}
