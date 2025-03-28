package com.cristianml.notes.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// Definimos esta clase como un componente para que se ejecute cuando spring se inicialice.
@Component
public class JwtUtils {

    // Importamos nuestra key y user
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    // Creamos método para crear token
    public String createToken(Authentication authentication) {
        // Instanciamos un Algorithm para definir el tipo de firma de nuestro JWT.
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        // Obtenemos el usuario autenticado
        String username = authentication.getPrincipal().toString();
        // Obtenemos las authorities divididos por coma
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // Creamos el token con nuestras propiedades
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator) // Usuario generador en este caso el Backend
                .withSubject(username) // Usuario autenticado que se le debe generar el token
                .withClaim("authorities", authorities) // Agregamos las claims, en este caso los permisos.
                .withIssuedAt(new Date()) // Qué fecha se crea el token.
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // Tiempo de duración/expiración del token.
                .withJWTId(UUID.randomUUID().toString()) // Definimos un id Random para cada token.
                .withNotBefore(new Date(System.currentTimeMillis())) // Que el tiempo del token no empieze antes ni después de su creación
                .sign(algorithm);
        return jwtToken;
    }

    // Creamos método para decodificar y validar el token
    public DecodedJWT validarToken(String token) {
        // Envolvemos en un try catch
        try {
            // Instanciamos el algorithm nuevamente
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            // Creamos el objeto verificador y pasamos el algoritmo en el método require.
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            // Creamos el objeto decodificador y verificamos con el método verify(token)
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalid, not authorized.");
        }
    }

    // Método para extraer el usuario que viene dentro del token
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    // Método para extraer un claim, los claims son la parte del cuerpo del token, son atributos.
    public Claim extractClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    // Método para devolver todos los claims
    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
