package com.cristianml.controller;

// Arquitectura de Spring Security

// Cuando hacemos una solicitud "HTTP request" ésta se dirige a la cadena de filtros "Security Filter Chain"
// la cual realiza varios filtro y si todo sale bien, se dirige al DelegatingFilterProxy que es el que nos permite crear
// nuestros propios filtros personalizados y tener nuestras propias validaciones.

// AuthenticationManager

// Es el que gestiona todo lo que tiene que ver con la autenticación de usuarios, valida los datos del usuario.
// Para lograr autenticar correctamente la autenticación, el AuthenticationManager hace uso de Provider (Proveedor)
// por ejemplo tenemos proveedores como:
// 1. Authentication Provider A "User From Database": Que nos permite autenticarnos con un usuario que está registrado en la DB.
// 2. Authentication Provider B: Que podría ser Oauth2.
// 3. Muchos otros más.

// El provider User From DataBase es el más usado, utiliza 2 componentes que son:
// 1. PasswordEncoder: Es el componente que se encarga de codificar las contraseñas y validar de que sean correctas,
// es decir encripta las contraseñas.
// 2. UserDetailsService: Es el componente que se encarga de conectarse a la DB (mongoDB, MySQL, Oracle, etc.)

// Security Context Holder

// Es el que recibe desde el DelegatingFilterProxy toda la solicitud ya filtrada y autenticada en la base de datos con el
// AuthenticationManager, o sea es donde se guarda el usuario que está autenticado y es el que lo mantiene logeado
// para hacer las solicitudes que queramos pero según los permisos que tenga éste usuario (rol)
//
// En caso de que el usuario no esté registrado en la DB el AuthenticationManager le dice al DelegatingFilterProxy
// que nos devuelva una respuesta (HTTP response) de que el usuario fue denegado.
//
// Este Security Context Holder consta de 2 componentes:
// 1. Principal: Es donde se guarda la información general del usuario (contraseñas, correos, etc).
// 2. Authorities: Es donde se guardan los permisos.

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
// @PreAuthorize("denyAll()")
public class TestAuthController {

    // Spring Security nos crea la configuración por defecto con tan sólo agregar la dependencia en pom.xml
    // Para probar estos endpoints en postman:
    // Nos ubicamos en la pestaña authorization y seleccionamos el tipo que necesitamos, en este caso Basic Auth
    // luego agregamos el nombre de usuario y contraseña que configuramos en nuestro application.properties.

    // Creamos controladores simples para hacer las pruebas de la autorizaciones en postman

    // Este es nuestro scrip para ver los roles y permisos de los usuarios:
    /* SELECT
        u.username,
        u.password,
        r.role_name AS role_name,
        p.name AS permission_name
    FROM
        users u
            INNER JOIN
        user_roles ur ON u.id = ur.user_id
            INNER JOIN
        roles r ON ur.role_id = r.id
            INNER JOIN
        role_permission rp ON r.id = rp.role_id
            INNER JOIN
        permissions p ON rp.permission_id = p.id; */
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('READ')")
    public String helloGet(){
        return "Hello World - GET";
    }

    @PostMapping("/post")
    // @PreAuthorize("hasAuthority('CREATE') or hasAuthority('READ')")
    public String helloPost(){
        return "Hello World - POST";
    }

    @PutMapping("/put")
    public String helloPut(){
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    public String helloDelete(){
        return "Hello World - DELETE";
    }

    @PatchMapping("/patch")
    // @PreAuthorize("hasAuthority('REFACTOR')")
    public String helloPatch(){
        return "Hello World - PATCH";
    }

}
