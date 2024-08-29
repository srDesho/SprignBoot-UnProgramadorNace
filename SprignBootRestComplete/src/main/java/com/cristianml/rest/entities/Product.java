package com.cristianml.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "Precio")
    // BigDecimal es específicamente para trabajar con decimales, pero para monedas porque es de alta precisión.
    private BigDecimal price;

    // Hacemos la relación con Maker
    @ManyToOne()
    // Le damos el nombre de la clave foránea que se creará en la DB.
    // Con nullable = false, decimos que siempre debe estar esta relación.
    @JoinColumn(name = "id_fabricante", nullable = false)
    // Esto para que al hacer las peticiones se ignore la lista y no haya problemas con la serialización, ya que
    // estamos usando el FetchType.LAZY
    @JsonIgnore
    private Maker maker; // El nombre tiene que ser el mismo que con el que mapeamos en la entidad Maker

}
