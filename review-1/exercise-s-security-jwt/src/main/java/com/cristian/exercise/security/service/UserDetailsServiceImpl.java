package com.cristian.exercise.security.service;

import com.cristian.exercise.dto.AuthLoginRequest;
import com.cristian.exercise.dto.AuthResponse;
import com.cristian.exercise.repository.RoleRepository;
import com.cristian.exercise.repository.UserRepository;
import com.cristian.exercise.security.entity.UserEntity;
import com.cristian.exercise.util.JwtUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not exists."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoleEntitySet()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoleEntitySet().stream()
                .flatMap(role -> role.getPermissionEntitySet().stream())
                        .forEach(permissionEntity -> authorityList.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonLocked(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                authorityList
        );
    }

    // Autenticar usuario con JWT
    public AuthResponse loginUser(AuthLoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        // Obtenemos la autenticación
        Authentication authentication = this.authenticate(username, password);
        // Seteamos la autenticación al contexto de spring security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generamos el token
        String accessToken = this.jwtUtils.createToken(authentication);

        // Retornamos nuestro usuario Autenticado como DTO
        AuthResponse authResponse = new AuthResponse(
                username, "User logged succesfully", accessToken, true);
        return authResponse;
    }

    // Método que obtiene la autenticación con las credenciales desde la db
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        // Validamos que no sea null y que las contraseñas sean iguales.
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username or Password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
