package com.cristianml.security.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cristianml.utilities.JwtUtils;
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

// Esta clase es para validar nuestro token, es un filtro que asignaremos a nuestras capas de securityFilterChain
// Vamos a extender de OncePerRequestFilter para que se ejecute el filtro cada vez que se hará una petición,
// y vamos a agregar su método doFilterInternal.
public class JwtTokenValidator extends OncePerRequestFilter {

    // Inyectamos nuestro JwtUtils para que podamos trabajar sobre él.
    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Obtenemos el token con el header
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Validamos el token
        if (jwtToken != null) {
            // Extraemos sólo el token y excluímos el "Bearer ", ya que un token se manda así:
            // Bearer xczcvcxvzxc.cvzxcvxcv.cxvxzcv
            // Hacemos esto con el substring e iniciamos en la posición siete porque así se excluye el "Bearer " completo.
            jwtToken = jwtToken.substring(7);

            // Verificamos el token
            DecodedJWT decodedJWT = this.jwtUtils.validateToken(jwtToken);
            // Obtenemos el nombre de usuarios
            String username = this.jwtUtils.extractUsername(decodedJWT);
            // Obtenemos los permisos como string
            String strAuthorities = this.jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            // Extraemos los permisos en una Collection que va a recibir cualquier cosa(?) que extiende de GrantedAuthority
            // Usamos la clase AuthorityUtils que nos brinda spring security
            // con su método .commaSeparatedStringToAuthorityList() que es para separar las authorities que tenemos por coma.
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities);

            // Seteamos el usuario en el contexto de Spring security
            // Obtenemos el contexto de la siguiente manera.
            SecurityContext context = SecurityContextHolder.getContext();
            // Creamos la autenticación UsernamePasswordAuthenticationToken, no hace falta enviar la contraseña por seguridad.
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authentication);
            // Seteamos el contexto
            SecurityContextHolder.setContext(context);
        }

        // Continúa la cadena de filtros, y por consiguiente rechaza la solicitud.
        filterChain.doFilter(request, response);
    }
}
