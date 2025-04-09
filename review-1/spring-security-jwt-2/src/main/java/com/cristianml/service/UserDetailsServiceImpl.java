package com.cristianml.service;

import com.cristianml.dto.AuthCreateUserRequest;
import com.cristianml.dto.AuthLoginRequest;
import com.cristianml.dto.AuthResponse;
import com.cristianml.persistence.entity.RoleEntity;
import com.cristianml.persistence.entity.UserEntity;
import com.cristianml.persistence.repository.RoleRepository;
import com.cristianml.persistence.repository.UserRepository;
import com.cristianml.util.JwtUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Creamos el usuario que va a ser buscado en la db
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("El usuario " + username + " no existe.")
        );

        // Debemos obtener la contraseña, roles y permisos.
        // Spring Security maneja los permisos con la Interfaz GrantedAuthority con su implementación SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Los roles de nuestros usuarios debemos agregarlos en la authorityList para decirle a S. Security qué roles y
        // permisos están permitidos y la sesión actual.

        // A los roles spring los reconoce al comienzo de cada nombre como "ROLE_" luego concatenamos lo sgte.
        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Para los permisos debemos hacer uso de Stream, y este no necesita concatenarse con "ROLE_"
        userEntity.getRoles().stream()
                // Debemos recorrer cada rol para obtener los permisos y eso se debe hacer con un flatMap
                .flatMap(role -> role.getPermissionList().stream())
                        // Aquí recorremos cada permiso que tiene cada rol.
                        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // Retornamos el usuario(importamos desde spring security) con todo seteado junto a la authorityList
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

    // Creamos nuestro método para la authentication con JWT
    public AuthResponse loginUser(AuthLoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        // Capturamos el security context holder para setearle nuestra autenticación,
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generamos el token
        String accessToken = jwtUtils.createToken(authentication);

        // Retornamos nuestro usuario autenticado como un DTO
        AuthResponse authResponse = new AuthResponse(username, "User logged successfully.", accessToken, true);
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        // Creamos un UserDetails para capturarlo desde la base de datos con todos sus datos y permisos.
        UserDetails userDetails = this.loadUserByUsername(username);

        // Validamos si sus datos son correctos.
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        // Retornamos el usuario autenticado.
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

        // Verificamos que la lista no esté vacía
        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist");
        }

        // Seteamos las credenciales al usuario nuevo.
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // Debemos guardar la contraseña encriptada
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .roles(roleEntitySet)
                .build();

        // Guardamos en la base de datos y capturamos en una variable.
        UserEntity userCreated = this.userRepository.save(userEntity);

        // Obtenemos los roles y permisos en un ArrayList para pasarlos a spring security
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Roles
        userCreated.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Permisos
        userCreated.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // Damos acceso y autenticación
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorityList);
        String accessToken = jwtUtils.createToken(authentication); // Generamos el token

        // Devolvemos la respuesta que es el usuario creado con su acceso.
        AuthResponse authResponse = new AuthResponse(username, "User create successfully.", accessToken, true);

        return authResponse;
    }
}
