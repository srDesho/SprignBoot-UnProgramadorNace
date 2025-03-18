package com.cristian.exercise.security.service;

import com.cristian.exercise.repository.UserRepository;
import com.cristian.exercise.security.entity.UserEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @NonNull
    private UserRepository userRepository;

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
}
