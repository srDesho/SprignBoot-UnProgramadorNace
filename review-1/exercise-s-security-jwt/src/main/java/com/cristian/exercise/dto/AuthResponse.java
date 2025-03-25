package com.cristian.exercise.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Damos el orden a jackson para que serialice la respuesta
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status) {
}
