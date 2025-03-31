package com.cristianml.notes.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cristianml.notes.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

// Extendemos de la clase Abstracta OncePerRequestFilter para que nos garantice que siempre deber solicitarse un token,
// esta clase nos va a permitir ejecutar el filtro cada vez que se haga una petición.
public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Obtenemos el token
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Validamos el token
        if (jwtToken != null) { // esta es la manera que nos captura un token "Bearer eqerqwer.qwerqwerqwer.qwerqwer"
            // Quitamos la cadena "Bearer "
            jwtToken = jwtToken.substring(7);

            // Validamos y decodificamos nuestro jwt y lo recuperamos en un DecodedJWT
            DecodedJWT decodedJWT = jwtUtils.validarToken(jwtToken);

            // Ya que el token es válido, concedemos la autorización de acceso
            // Obtenemos el usuario
            String username = jwtUtils.extractUsername(decodedJWT);

            // Obtenemos las autorizaciones
            String strAuthorities = jwtUtils.extractClaim(decodedJWT,"authorities").asString();
            // Convertimos a una lista de GrantedAuthorities
            Collection<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities);

            // Seteamos el usuario en el contexto spring security
            SecurityContext context = SecurityContextHolder.getContext();
            // Creamos una instancia de authentication para JWT sin pasar la contraseña por seguridad.
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            // Volvemos a setear el contexto pero ya con la autenticación
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}