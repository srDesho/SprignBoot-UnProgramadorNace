package com.app.config.filter;

import com.app.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

// Esta clase es para crear nuestro filtro y validar nuestro token
// Extendemos de la clase OncePerRequestFilter que nos va a funcionar para que el filtro se ejecute cada vez que
// hagamos una request(petición), con esto se garantiza que siempre debemos enviar un token.
public class JwtTokenValidator extends OncePerRequestFilter {

    // Inyectamos por constructor nuestro JwtUtils
    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    // Agregamos validaciones @NonNull a los 3 objetos
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Obtenemos el token
        // HttpHeaders.AUTHORIZATION debemos importarlo de springframework.http.HttpHeaders
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Validamos el token
        if (jwtToken != null) { // Bearer xczxcvwer.cxvxcvxcwer.xcvxcv -> este es un ejemplo de un token
            // quitamos la cadena "Bearer " de nuestra petición para extraer el token.
            jwtToken = jwtToken.substring(7); // desde el índice 7 porque esa es la longitud de "Bearer ".

            // Validamos y decodificamos nuestro jwt y lo recuperamos en un objeto DecodedJWT
            // para eso usamos el método validateToken de nuestra clase JwtUtils
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            // Ya que el token es válido, concedemos la autorización de acceso
            // Obtenemos el usuario
            String username = jwtUtils.extractUsername(decodedJWT);

            // Obtenemos las autorizaciones
            // debe estar bien configurado nuestro createToken, la parte del método .withClaim
            String strAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            // Convertimos strAuthorities a una lista de GrantedAuthorities, hacemos uso de la clase AuthorityUtils
            // que nos brinda spring security.
            Collection<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities);

            // Seteamos el usuario en el contexto de Spring Security
            SecurityContext context = SecurityContextHolder.getContext();
            // La contraseña no la pasamos por seguridad, no es necesario.
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            context.setAuthentication(authentication);
            // Volvemos a setear el contexto pero ya con la autenticación
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}
