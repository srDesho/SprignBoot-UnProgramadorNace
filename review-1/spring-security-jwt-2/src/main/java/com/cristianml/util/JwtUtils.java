package com.cristianml.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("security.jwt.user.generator")
    private String userGenerator;

    // Creamos método para crear token y este recibe las autorizaciones de los usuarios con el objeto Authentication
    public String createToken(Authentication authentication) {
        // 1. Necesitamos crear primero es algoritmo de encriptación.
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        // Obtenemos el usuario que se encuentra autenticado, esto lo hacemos con el método .getPrincipal()
        String username = authentication.getPrincipal().toString();
        // Obtenemos las authorities de esta manera: READ,WRITE,DELETE,ETC
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)// El map me trabaja elemento por elemento
                .collect(Collectors.joining());

        // En este string generamos nuestro token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator) // Aquí va nuestro usuario que genera el token, o sea el backend
                .withSubject(username) // Asignamos el sujeto que viene a ser nuestro usuario autenticado.
                .withClaim("authorities", authorities) // Roles o permisos del usuario, usados para autorización.
                .withIssuedAt(new Date()) // Fecha en que se crea el token
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // Tiempo de expiración del token en milisegundos.
                .withJWTId(UUID.randomUUID().toString()) // Asigna un ID random para el token.
                .withNotBefore(new Date(System.currentTimeMillis())) // Definimos que el token sea válido a partir del momento creado.
                .sign(algorithm); // Definimos la firma encriptada.
        return jwtToken;
    }

}
