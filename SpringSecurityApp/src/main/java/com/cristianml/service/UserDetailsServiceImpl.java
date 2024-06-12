package com.cristianml.service;

import com.cristianml.persistence.entity.UserEntity;
import com.cristianml.persistence.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Ésta clase es el 2do componente de nuestro AuthenticationManager (leer la estructura de Spring Security en TestAuthController),
// se encarga de conectarse con la DB y será inyectada en nuestra configuración de spring security en la parte de nuestro proveedor.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = this.userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        // Creamos una lista de autorizaciones concedidas de tipo SimpleGrantedAuthority para que spring security entienda.
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        // Es en ésta lista debemos agregar los roles que tiene el usuario.
        userEntity.getRoles()
                .forEach( role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Agregamos los permisos
        userEntity.getRoles().stream()
                // Debemos recorrer cada rol para obtener los permisos y eso se debe hacer con un flatMap
                .flatMap(role -> role.getPermissionList().stream())
                // Aquí recorremos cada permiso que tiene cada rol.
                // new SimpleGrantedAuthority(permission.getName() : no necesita que le pasemos el "ROLE_" como con los roles
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // Hacemos el mapeo ya que Spring nos pide retornar un User(objeto de spring security).
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnable(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }
}
