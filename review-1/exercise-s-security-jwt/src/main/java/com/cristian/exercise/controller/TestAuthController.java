package com.cristian.exercise.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/method")
// @PreAuthorize("denyAll()")
public class TestAuthController {

    @PreAuthorize("permitAll()")
    // @GetMapping("/get")
    public String helloGet() {
        return "Hello World - GET";
    }

    @PostMapping("/post")
    // @PreAuthorize("hasRole('ADMIN')")
    public String helloPost() {
        return "Hello World - POST";
    }

    @PatchMapping("/patch")
    // @PreAuthorize("hasAuthority('REFACTOR')")
    public String helloPatch() {
        return "Hello World - PATCH";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello World - DELETE";
    }

}
