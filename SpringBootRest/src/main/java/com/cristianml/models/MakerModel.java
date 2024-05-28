package com.cristianml.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "fabricante")
public class MakerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;

    // cascade: nos sirve para cuando hagamos algo en la entidad Maker, también la haga en su relación (en este caso Product).
    // FetchType.LAZY: sirve para que no sobrecargue la lista, sólo la llene cuando nosotros solicitemos, por ejemolo con el
    // método .getProducts
    // orphanRemoval = true: sirve para cuando eliminemos un fabricante, los productos también se eliminan porque un producto
    // no puede existir si no existe un creador.
    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductModel> products = new ArrayList<>();
}
