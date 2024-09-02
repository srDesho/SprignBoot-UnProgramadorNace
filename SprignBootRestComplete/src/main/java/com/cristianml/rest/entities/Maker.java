package com.cristianml.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "fabricantes")
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;

    // Para hacer la relación de 1 a muchos, siempre debemos ubicarnos en la que tiene el 1
    // como en este caso 1 Fabricante puede crear muchos Productos.
    // mappedBy: mapeamos la otra entidad, debemos poner el nombre del atributo Maker de la clase Product
    // CascadeType.ALL: Para que haga un movimiento en cascada en la clase Maker y la otra que está asociada.
    // FetchType.LAZY: Para no sobrecargar la memoria, sirve para que cuando hagamos uso de la entidad, no nos devuelva
    // todo lo que contiene en la lista también, solo lo hará cuando llamemos a la lista.
    // orphanRemoval: Si eliminamos un creador(Maker) automáticamente nos eliminará todos los productos de ese creador
    // ya que un producto no puede existir sino existe un creador.
    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    // Esto para que al hacer las peticiones se ignore la lista y no haya problemas con la serialización, ya que
    // estamos usando el FetchType.LAZY
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();

}