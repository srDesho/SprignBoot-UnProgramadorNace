package com.cristianml.notes.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notas")
@PreAuthorize("denyAll()")
public class NotasController {

    @GetMapping
    @PreAuthorize("hasAuthority('VER_NOTAS')")
    public String helloNotasGet() {
        return "Hello notas - GET";
    }

    @PostMapping
    public String helloNotasPost() {
        return "Hello notas - POST";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITAR_NOTA')")
    public String helloNotasPut(@PathVariable(name = "id") Long id) {
        return "Hello notas - PUT " + id;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ELIMINAR_NOTA')")
    public String helloDeleteNotas(@PathVariable("id") Long id) {
        return "Hello notas - DELETE " + id;
    }
}
