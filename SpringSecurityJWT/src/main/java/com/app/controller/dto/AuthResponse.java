package com.app.controller.dto;

// Trabajamos con record para no tener que estar agregando los métodos getter, setter

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Damos el orden a jackson para que serialice la respuesta
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status) {
}
