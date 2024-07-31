package com.cristianml.jpa.associations.unidirectional.one_to_one;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Debemos aprender este concepto para saber cómo funciona la asociación.

// La asociación establece si desde mi entidad actual puedo acceder a la otra entidad relacionada.

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "uni_employee_one_to_one")
@Table(name = "uni_employee_one_to_one")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacemos la relación con la entidad ParkingSpot
    // Esta es una relación unidireccional porque solo la entidad Employee puede acceder a la entidad ParkingSpot,
    // mientras que la entidad ParkingSpot no puede acceder a esta entidad actual.

    @OneToOne
    @JoinColumn(name = "parking_spot_id") // esto es para darle el nombre a la columna donde se relaciona el id.
    private ParkingSpot parkingSpot;

}
