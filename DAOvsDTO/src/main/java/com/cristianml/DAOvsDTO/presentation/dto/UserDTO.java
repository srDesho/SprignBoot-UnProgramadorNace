package com.cristianml.DAOvsDTO.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Hay una teoría que sólo debemos usar la anotación data en los dto.
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private byte age;

}
