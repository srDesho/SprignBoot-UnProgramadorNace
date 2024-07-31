package com.cristianml.jpa.associations.bidirectional.one_to_one;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "bi_parking_spot_one_to_one")
@Table(name = "bi_parking_spot_one_to_one")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacemos la relación bidireccional escribiendo el atributo mappedBy
    // El mappedBy nos indica cuál va a ser la entidad relacionada inversa, o sea que será esta la inversa.
    // pero entre commillas escribimos el mismo nombre que definimos en nuestra entidad principal.
    @OneToOne(mappedBy = "parkingSpot")
    private Employee employee;

}
