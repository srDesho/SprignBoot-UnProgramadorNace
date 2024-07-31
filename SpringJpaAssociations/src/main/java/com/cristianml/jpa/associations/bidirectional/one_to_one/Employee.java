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

@Entity(name = "bi_employee_one_to_one")
@Table(name = "bi_employee_one_to_one")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacemos la relación con la entidad ParkingSpot

    @OneToOne
    @JoinColumn(name = "parking_spot_id") // esto es para darle el nombre a la columna donde se relaciona el id.
    private ParkingSpot parkingSpot;

}
