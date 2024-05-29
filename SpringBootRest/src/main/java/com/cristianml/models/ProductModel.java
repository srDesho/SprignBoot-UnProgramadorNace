package com.cristianml.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
// @Builder: Facilita la creación de instancias de clases con muchos atributos sin necesidad de constructores
// con muchos parámetros o setters repetitivos.
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "productos")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "precio")
    // Usamos el tipo de datos BigDecimal para cuando trabajamos con diner, ya que es de tipo decimal y más preciso.
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_fabricante", nullable = false)
    @JsonIgnore // Esto para que Jackson no va a serializar automáticamente sino cuando yo se lo pida.
    // Esto por error a la lista nos la entrega vacía porque la tenemos etiquetada con FetchType.LAZY.
    private MakerModel maker;
}
