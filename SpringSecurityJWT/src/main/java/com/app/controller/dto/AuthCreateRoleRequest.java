package com.app.controller.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

// Este DTO es para la creación de los roles
// necesitamos enviar la lista de los roles como parámetro
// el size solo lo pusimos porque así quisimos que en este proyecto un usuario no pueda tener más de 3 roles.
@Validated // esto para que se pueda validar esta clase.
public record AuthCreateRoleRequest(@Size(max = 3, message = "The user cannot have more than 3 roles")
                                    List<String> roleListName) {
}
