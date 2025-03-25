package com.cristian.exercise.security.service;

import com.cristian.exercise.dto.AuthCreateUserRequest;
import com.cristian.exercise.dto.AuthLoginRequest;
import com.cristian.exercise.dto.AuthResponse;
import com.cristian.exercise.repository.RoleRepository;
import com.cristian.exercise.repository.UserRepository;
import com.cristian.exercise.security.entity.RoleEntity;
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
import java.util.Set;
import java.util.stream.Collectors;

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

    // Método para crear usuario
    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        // Obtenemos los datos
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();

        // Obtener entidades de roles de la base de datos
        Set<RoleEntity> roleEntitySet = this.roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest)
                .stream().collect(Collectors.toSet());

        // Verificamos si hay roles existentes.
        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist");
        }

        // Creamos el usuario y guardamos en la db.
        UserEntity userEntity = UserEntity.builder()
                .username(authCreateUserRequest.username())
                .password(passwordEncoder.encode(authCreateUserRequest.password()))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roleEntitySet(roleEntitySet)
                .build();

        UserEntity userCreated = this.userRepository.save(userEntity);

        // Pasamos las authorities a Spring Security
        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

        userCreated.getRoleEntitySet()
                .forEach(roleEntity -> authoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name()))));

        userCreated.getRoleEntitySet().stream()
                .flatMap(roleEntity -> roleEntity.getPermissionEntitySet().stream())
                .forEach(permissionEntity -> authoritiesList.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        // Creamos la autenticación
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authoritiesList);
        // Generamos el token
        String accessToken = this.jwtUtils.createToken(authentication);

        // Retornamos nuestro user dto
        return new AuthResponse(username, "User created successfully", accessToken, true);
    }

}
