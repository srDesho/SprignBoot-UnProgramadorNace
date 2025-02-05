package com.cristianml.service;

import com.cristianml.persistence.entity.UserEntity;
import com.cristianml.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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

        // A los roles debemos spring los reconoce al comienzo de cada nombre como "ROLE_" luego concatenamos lo sgte.
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
}
