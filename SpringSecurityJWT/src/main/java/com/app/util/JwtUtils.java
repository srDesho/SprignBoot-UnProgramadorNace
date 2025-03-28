package com.app.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// Esta será nuestra clase de utilería para JWT
@Component
public class JwtUtils {

    // Necesitaremos 2 cosas:
    // 1. Clave Privada.
    // 2. UserGenerator: que será el usuario generador del token.

    // Para la clave primaria, nos dirigimos a una página que genere hash a nuestra clave, ejemplo keycdn generato SHA256
    // generamos el hash copiamos y la asignamos en una variable en nuestro aplication.properties con cualquier nombre.

    @Value("${security.jwt.key.private}")
    private String privateKey;

    // Estos atributos son para garantizar algo nuestra autenticidad de nuestro token.

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    // Método para crear token
    public String createToken(Authentication authentication) {
        // Definimos el tipo de algoritmo con el que se va a encriptar y le pasamos nuestra clave secreta.
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey); // HMAC256 es el más común que se usa
        // Obtenemos el username autenticado desde nuestro objeto Authentication
        String username = authentication.getPrincipal().toString();
        // Obtenemos nuestros permisos en un string donde cada permiso debe estar separado por comas.
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                // el .collect junto con el joining me permiten agregar la coma.
                .collect(Collectors.joining(","));

        // Creamos el token con nuestras propiedades
        String jwtToken = JWT.create()
                // Aquí pasamos nuestro usuario generador o sea nuestro backend
                .withIssuer(this.userGenerator)
                // Agregamos el sujeto al cual le vamos a generar un token
                .withSubject(username)
                // Agregamos los cleams(que es el cuerpo de nuestro token, y en este caso son los permisos)
                .withClaim("authorities", authorities)
                // agregamos la fecha en que se crea el token
                .withIssuedAt(new Date())
                // tiempo de expiración en milisegundos, en este caso agregaremos 30 min para que expire nuestro token
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                // asignamos un id convertido a string al token, este id lo generamos con UUID
                .withJWTId(UUID.randomUUID().toString())
                // definimos desde qué momento el token se va a considerar válido, o sea podemos agregar en cuantas
                // horas o minutos el token va a poder usarse, en este caso será desde el momento que lo creamos.
                .withNotBefore(new Date(System.currentTimeMillis()))
                // agregamos la firma la cual es el algoritmo de encriptación con la firma(clave que creamos con hash)
                .sign(algorithm);
        return jwtToken;
    }

    // Método para decodificar y validar el token
    public DecodedJWT validateToken(String token) {
        // Lo envolvemos en un try catch
        try {
            // Necesitamos el algoritmo al igual que en el método createToken
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            // Creamos el objeto verificador y pasamos el algoritmo en el método .require()
            JWTVerifier verifier = JWT.require(algorithm)
                    // Pasamos el usuario generador de token(el cual es nuestro backend)
                    .withIssuer(this.userGenerator)
                    .build();
            // Creamos el objeto decodificador y verificamos con el método .verify(token)
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalid, not Authorized.");
        }
    }

    // Método para extraer el usuario que viene dentro del token
    public String extractUsername(DecodedJWT decodedJWT) {
        // Para esto necesitamos tener bien configurado nuestro método createToken en la parte que asignamos el usuario
        // con el método .withSubject()
        // debemos retornar un string.
        return decodedJWT.getSubject().toString();
    }

    // Método para extraer un claim de nuestro token, los claims son la parte del cuerpo del token, los atributos.
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    // Devolviendo todos los claims
    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
