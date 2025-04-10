package com.cristianml.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Con esta línea le indicamos a jackson que nos debe ordenar como le pedimos
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponse(String username, String message, String jwt, boolean status) {
}
