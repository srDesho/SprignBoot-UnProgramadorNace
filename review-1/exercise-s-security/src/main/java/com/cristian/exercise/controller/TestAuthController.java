package com.cristian.exercise.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class TestAuthController {

    @GetMapping("/get")
    public String helloGet() {
        return "Hello World - GET";
    }

    @PostMapping("/post")
    public String helloPost() {
        return "Hello World - POST";
    }

    @PatchMapping("/patch")
    public String helloPatch() {
        return "Hello World - PATCH";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello World - DELETE";
    }

}
