package com.app.service;

import com.app.controller.dto.AuthCreateUserRequest;
import com.app.controller.dto.AuthLoginRequest;
import com.app.controller.dto.AuthResponse;
import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.RoleRepository;
import com.app.persistence.repository.UserRepository;
import com.app.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
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
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RoleRepository roleRepository;

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

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {

        // Obtenemos los credenciales
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();

        // Obtenemos la lista de roles que viene desde el request
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();

        // Verificamos si los roles de la lista roleRequest existen en la base de datos para que puedan ser seteados
        // a nuestro nuevo usuario.
        Set<RoleEntity> roleEntitySet = this.roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest)
                // Con el método .stream convertimos la lista de String a un Set.
                .stream().collect(Collectors.toSet());

        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist");
        }

        // Seteamos los credenciales al usuario nuevo
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // Encriptamos la contraseña con el passwordEncoder
                .roles(roleEntitySet) // Seteamos los roles que existen en la base de datos
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        // Guardamos el usuario y lo capturamos, la captura va a ser para poder crear un token
        UserEntity userCreated = this.userRepository.save(userEntity);

        // Obtenemos los roles y permisos en un ArrayList
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Primero obtenemos los roles y añadimos a nuestra authorityList
        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Ahora obtenemos los permisos y añadimos a nuestra authorityList
        userCreated.getRoles()
                .stream()
                // Con flatmap obtenemos las listas de permisos de cada rol.
                .flatMap(role -> role.getPermissionList().stream())
                // Iteramos cada permiso para agregar a nuestra lista de authorities y así Spring Security pueda manejarlos.
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // Damos acceso
        // Creamos nuestro objeto de Authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(),
                userCreated.getPassword(), authorityList);

        // Creamos el token
        String accessToken = jwtUtils.createToken(authentication);

        // Definimos la respuesta
        AuthResponse authResponse = new AuthResponse(userCreated.getUsername(), "User created successfully.",
                accessToken, true);

        return authResponse;
    }
}
