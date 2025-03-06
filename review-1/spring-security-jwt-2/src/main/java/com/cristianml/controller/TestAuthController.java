package com.cristianml.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/method")
// Para el uso de las anotaciones de SSecurity debemos tener activado el @EnableMethodSecurity en la classe SecurityConfig.
// @PreAuthorize("denyAll()") // Con PreAuthorize() indicamos que debe haber una autenticación o no.
public class TestAuthController {

    @GetMapping("/get")
    // @PreAuthorize("hasAuthority('CREATE') OR hasAuthority('DELETE')")
    public String helloGet() {
        return "Hello world - GET";
    }

    @PostMapping("/post")
    public String helloPost() {
        return "Hello world - POST";
    }

    @PutMapping("/put")
    public String helloPut() {
        return "Hello world - PUT";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello world - DELETE";
    }

    @PatchMapping("/patch")
    // @PreAuthorize("hasAuthority('REFACTOR')")
    public String helloPatch() {
        return "Hello world - PATCH";
    }
}
