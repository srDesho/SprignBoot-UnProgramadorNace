package com.cristianml;

import com.cristianml.persistence.entity.PermissionEntity;
import com.cristianml.persistence.entity.RoleEntity;
import com.cristianml.persistence.entity.RoleEnum;
import com.cristianml.persistence.entity.UserEntity;
import com.cristianml.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApp2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApp2Application.class, args);
	}

	// Creamos un bean
	@Bean
	@Transactional
	// Esta función nos permitirá cargar datos desde métodos.
	// Inyectamos nuestro repository.
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {

			// Creamos los permisos
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			// Creamos los Roles
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission, readPermission))
					.build();

			RoleEntity roleInvited = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
					.build();

			// Creamos los usuarios
			UserEntity userCristian = UserEntity.builder()
					.username("cristian")
					.password("$2a$10$4eB7Zdc/DSkvZiXyGhzmLuiJFGEMGeVqdm0C/toqimNHREST.0qua")
					.isEnabled(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userDaniel = UserEntity.builder()
					.username("daniel")
					.password("$2a$10$4eB7Zdc/DSkvZiXyGhzmLuiJFGEMGeVqdm0C/toqimNHREST.0qua")
					.isEnabled(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity userAndrea = UserEntity.builder()
					.username("andrea")
					.password("$2a$10$4eB7Zdc/DSkvZiXyGhzmLuiJFGEMGeVqdm0C/toqimNHREST.0qua")
					.isEnabled(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(roleInvited))
					.build();

			UserEntity userDesho = UserEntity.builder()
					.username("desho")
					.password("$2a$10$4eB7Zdc/DSkvZiXyGhzmLuiJFGEMGeVqdm0C/toqimNHREST.0qua")
					.isEnabled(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();

			// Guardamos a todos los usuarios con el método saveAll() de JPA
			userRepository.saveAll(List.of(userCristian, userDaniel, userAndrea, userDesho));
		};
	}
}
