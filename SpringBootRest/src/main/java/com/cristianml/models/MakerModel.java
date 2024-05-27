package com.cristianml.models;

import jakarta.persistence.*;
import lombok.*;

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
}
