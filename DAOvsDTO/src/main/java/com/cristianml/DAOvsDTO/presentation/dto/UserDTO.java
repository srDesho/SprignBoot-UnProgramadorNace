package com.cristianml.DAOvsDTO.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Hay una teoría que sólo debemos usar la anotación data en los dto.
@NoArgsConstructor
@AllArgsConstructor

// Siempre por convención se debe poner DTO al final del nombre de la clase EjemploDTO, UserDTO, ObjectDTO.
public class UserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private byte age;

}
