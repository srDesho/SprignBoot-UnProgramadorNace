package com.cristianml.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Con nullable = false, indicamos que no puede ser nulo
    // con updatable = false, indicamos que no se pueden actualizar los permisos creados
    @Column(unique = true, nullable = false, updatable = false)
    private String name;

}
