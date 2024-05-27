package com.cristianml.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class FootballCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // El parámetro length: nos sirve para indicar el tamaño de caracteres que se puede registrar,
    // El parámetro nullable = false: indica que no se permiten nulos, en caso de que sea true sí es permitido.
    // El parámetro unique = true: indica que no se pueden repetir los datos registrado, ejemplo:
    // si tengo un registro con nombre Santiago, no puede existir otro Santiago, o sea no podemos crearlo.
    // Parámetros que casi no se usan:
    // insertable = true: la columna no se le podrá ingresar datos
    // updatable = false: no se puede cambiar (actualizar) el valor del registro.
    @Column(name = "cuantity_price", length = 10, nullable = false, unique = true)
    private Integer cuantityPrice;

    // El parámetro columnDefinition es para definir el tipo de dato Sql, en el caso de las fechas tipo LocalDate
    // debemos agregarlo para que no de problemas.
    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

}
