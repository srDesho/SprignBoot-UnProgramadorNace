package com.cristianml.notes.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cristianml.notes.dto.AuthCreateUserRequest;
import com.cristianml.notes.dto.AuthLoginRequest;
import com.cristianml.notes.dto.AuthResponse;
import com.cristianml.notes.repository.RoleRepository;
import com.cristianml.notes.repository.UserRepository;
import com.cristianml.notes.security.entity.RoleEntity;
import com.cristianml.notes.security.entity.UserEntity;
import com.cristianml.notes.util.JwtUtils;
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

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findUserEntityByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User with " + username + " doesn't exists."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoleSet()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoleSet().stream().flatMap(roleEntity -> roleEntity.getPermissionSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionEnum().name())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonLocked(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest userRequest) {

        // Extraemos el username y password
        String username = userRequest.username();
        String password = userRequest.password();

        // Creamos la autenticación para el token
        Authentication authentication = this.authenticate(username, password);
        // Si los datos son correctos pasamos la autenticación al contexto de Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generamos el token
        String jwtToken = jwtUtils.createToken(authentication);
        // Creamos el AuthResponse
        return new AuthResponse(username, "User logged successfully", jwtToken, true);
    }

    private Authentication authenticate(String username, String password) {
        // Obtenemos el usuario autenticado
        UserDetails userDetails = this.loadUserByUsername(username);

        // Validamos el userDetails para ver si existe el usuario
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        // Debemos poner bien el orden en matches.
        // Primer argumento: La contraseña en texto plano que el usuario ingresa.
        // Segundo argumento: El hash Bcrypt almacenado, generalmente recuperado de la base de datos.
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        // Extraemos los datos
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();

        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();

        // Verificamos si los roles y permisos existen en la db y lo capturamos en una lista set
        Set<RoleEntity> roleEntitySet = this.roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest);

        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist");
        }

        // Seteamos las credenciales al usuario nuevo que será guardado en la db
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roleSet(roleEntitySet)
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        // Guardamos y capturamos el user
        UserEntity userCreated = this.userRepository.save(userEntity);

        // Agregamos los roles y permisos en el contexto de Spring Security
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRoleSet().forEach(
                roleEntity -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name())))
        );

        userCreated.getRoleSet().stream()
                .flatMap(roleEntity -> roleEntity.getPermissionSet().stream())
                .forEach(permissionEntity -> authorityList.add(new SimpleGrantedAuthority(permissionEntity.getPermissionEnum().name())));

        // Damos acceso
        // Creamos el objeto de authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(),
                authorityList);

        // Creamos el token
        String accessToken = this.jwtUtils.createToken(authentication);

        return new AuthResponse(userCreated.getUsername(), "ser created successfully.", accessToken, true);
    }
}
