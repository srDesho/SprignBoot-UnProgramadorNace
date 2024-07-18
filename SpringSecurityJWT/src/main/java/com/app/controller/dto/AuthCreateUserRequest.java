package com.app.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

// Este DTO es para crear un nuevo ususario
public record AuthCreateUserRequest(@NotBlank String username,
                             @NotBlank String password,
                             // este objeto debemos crearlo y será para enviar los roles
                             @Valid AuthCreateRoleRequest roleRequest
                             ) {

}
