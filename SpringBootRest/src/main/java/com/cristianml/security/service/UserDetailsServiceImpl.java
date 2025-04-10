package com.cristianml.security.service;

import com.cristianml.dto.request.AuthLoginRequest;
import com.cristianml.dto.response.AuthResponse;
import com.cristianml.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
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


}
