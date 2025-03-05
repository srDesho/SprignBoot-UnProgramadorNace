package com.cristianml.util;

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

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey; // Clave secreta para firma JWT.

    @Value("security.jwt.user.generator")
    private String userGenerator; // Emisor del token (backend).

    // Genera un token JWT para el usuario autenticado.
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey); // Algoritmo de firma.
        String username = authentication.getPrincipal().toString(); // Usuario autenticado.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Extrae roles/permisos.
                .collect(Collectors.joining()); // Combina roles en string.

        return JWT.create()
                .withIssuer(this.userGenerator) // Emisor (backend).
                .withSubject(username) // Sujeto (usuario).
                .withClaim("authorities", authorities) // Roles/permisos.
                .withIssuedAt(new Date()) // Fecha de emisión.
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // Expiración (30 minutos).
                .withJWTId(UUID.randomUUID().toString()) // ID único.
                .withNotBefore(new Date(System.currentTimeMillis())) // Válido desde ahora.
                .sign(algorithm); // Firma el token.
    }

    // Valida y decodifica un token JWT.
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey); // Algoritmo de firma.
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator) // Emisor esperado.
                    .build();
            return verifier.verify(token); // Decodifica si es válido.
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido."); // Lanza excepción si falla.
        }
    }

    // Extrae el nombre de usuario (sujeto) del token decodificado.
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    // Obtiene un claim específico del token decodificado.
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    // Obtiene todos los claims del token decodificado.
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}