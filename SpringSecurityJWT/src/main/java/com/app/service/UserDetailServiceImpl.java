package com.app.service;

import com.app.controller.dto.AuthLoginRequest;
import com.app.controller.dto.AuthResponse;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import com.app.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }

    // Creamos método loginUser para nuestro token
    // Es aquí donde vamos a crear la magia de generar nuestro token de acceso.
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        // Necesitamos obtener los credenciales del usuario
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        // El método authenticate valida nuestras credenciales y devuelve el acceso si es correcto todo.
        Authentication authentication = this.authenticate(username, password);
        // Si las credenciales son correctas, llamamos al SecurityContextHolder para pasarle el usuario autenticado.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generamos el token
        String accessToken = this.jwtUtils.createToken(authentication);
        // Creamos el objeto que vamos a retornar
        AuthResponse authResponse = new AuthResponse(username, "User loged successfully.", accessToken, true);
        return authResponse;
    }

    // Creamos el método authenticate que es el que se va a encargar que las credenciales sean correctas.
    // Con los parámetros que recibe, debmos buscar al usuario en la db.
    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        // Validamos el userDetails para ver si existe el usuario.
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }
        // Validamos la contraseña
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        // Si todo es correctamente validado entonces retornamos nuestra autenticación
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
