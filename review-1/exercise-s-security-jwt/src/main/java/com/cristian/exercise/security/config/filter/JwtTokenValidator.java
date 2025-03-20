package com.cristian.exercise.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cristian.exercise.util.JwtUtils;
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

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils; // Inyecta la utilidad JWT.

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils; // Constructor para inyectar JwtUtils.
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, // Intercepta cada petición.
                                    @NonNull HttpServletResponse response, // Maneja la respuesta.
                                    @NonNull FilterChain filterChain) throws ServletException, IOException { // Cadena de filtros.

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION); // Obtiene el token del header.

        if (jwtToken != null) { // Verifica si hay token.
            jwtToken = jwtToken.substring(7); // Remueve "Bearer ".

            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken); // Valida el token.
            String username = jwtUtils.extractUsername(decodedJWT); // Extrae el usuario.
            String strAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString(); // Extrae los roles.
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities); // Convierte roles a GrantedAuthority.

            SecurityContext context = SecurityContextHolder.getContext(); // Obtiene el contexto de seguridad.
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities); // Crea autenticación.
            context.setAuthentication(authentication); // Setea la autenticación en el contexto.
            SecurityContextHolder.setContext(context); // Actualiza el contexto de Spring Security.
        }

        filterChain.doFilter(request, response); // Pasa la petición al siguiente filtro/recurso.
    }
}
