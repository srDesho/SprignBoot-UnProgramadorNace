package com.cristianml.security.service;

import com.cristianml.dto.request.AuthCreateUserRequest;
import com.cristianml.dto.request.AuthLoginRequest;
import com.cristianml.dto.response.AuthResponse;
import com.cristianml.repository.RoleRepository;
import com.cristianml.repository.UserRepository;
import com.cristianml.security.entity.RoleEntity;
import com.cristianml.security.entity.UserEntity;
import com.cristianml.utilities.JwtUtils;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not exists."));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(roleEntity -> roleEntity.getPermissions().stream())
                .forEach(permissionEntity -> authorities.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonLocked(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                authorities
        );
    }

    // Creamos nuestro método para la autenticación con JWT
    public AuthResponse loginUser(AuthLoginRequest request) {
        String username = request.username();
        String password = request.password();

        Authentication authentication = this.authenticate(username, password);
        // Capturamos el security context holder para setearle nuestra autenticación.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generamos el token
        String accessToken = this.jwtUtils.createToken(authentication);

        // Retornamos nuestro usuario autenticado como un DTO
        AuthResponse authResponse = new AuthResponse(username, "User logged successfully.", accessToken, true);
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        // Creamos un userDetails para capturarlo desde la Base de datos con todos sus datos y permisos.
        UserDetails userDetails = this.loadUserByUsername(username);

        // Validamos si sus datos son correctos.
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        // Retornamos el usuario Autenticado.
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();
        // Hacemos que los roles concuerden con los de la base de datos para no tener problemas, y lo hacemos
        // con esto: stream().collect(Collectors.toSet() convertimos la lista a un set
        // usando nuestro repositorio RoleRepository
        Set<RoleEntity> roleEntitySet = this.roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        // Verificamos que la lista no esté vacía.
        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist");
        }

        // Setemaos las credenciales al nuevo usuario.
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roles(roleEntitySet)
                .build();

        // Guardamos en la base de datos y capturamos en una variable.
        UserEntity userCreated = this.userRepository.save(userEntity);

        // Obtenemos los roles y permisos para agregarlos a Spring Security.
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Roles
        userCreated.getRoles().forEach(roleEntity -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name()))));

        // Permisos
        userCreated.getRoles().stream()
                .flatMap(roleEntity -> roleEntity.getPermissions().stream())
                .forEach(permissionEntity -> authorityList.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        // Damos acceso y autenticación
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorityList);
        String accessToken = jwtUtils.createToken(authentication);

        // Devolvemos la respuesta que es el usuario creado con su acceso.
        AuthResponse authResponse = new AuthResponse(username, "User create successfully.", accessToken, true);
        return authResponse;
    }
}
