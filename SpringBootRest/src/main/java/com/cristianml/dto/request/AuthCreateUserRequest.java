package com.cristianml.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(@NotBlank String name,
                                    @NotBlank String password,
                                    @NotBlank AuthCreateRoleRequest roleRequest) {
}
