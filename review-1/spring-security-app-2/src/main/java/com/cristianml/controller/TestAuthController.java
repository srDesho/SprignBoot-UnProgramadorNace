package com.cristianml.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
// Para el uso de las anotaciones de SSecurity debemos tener activado el @EnableMethodSecurity en la classe SecurityConfig.
@PreAuthorize("denyAll()") // Con PreAuthorize() indicamos que debe haber una autenticación o no.
public class TestAuthController {

    @GetMapping("/hello")
    @PreAuthorize("permitAll()")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/hello-secured")
    @PreAuthorize("hasAuthority('READ')")
    public String helloSecured() {
        return "Hello world Secured";
    }

    @GetMapping("/hello-secured2")
    @PreAuthorize("hasAuthority('CREATE')")
    public String helloSecured2() {
        return "Hello world Secured 2";
    }

}
