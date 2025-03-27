package com.cristianml.notes;

import com.cristianml.notes.repository.PermissionRepository;
import com.cristianml.notes.repository.RoleRepository;
import com.cristianml.notes.repository.UserRepository;
import com.cristianml.notes.security.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
public class SecureNotesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureNotesApiApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository pRepository) {
		return args -> {
			try {

				PermissionEntity verNotasPermission = savePersmission(pRepository, PermissionEnum.VER_NOTAS);
				PermissionEntity crearNotaPermission = savePersmission(pRepository, PermissionEnum.CREAR_NOTA);
				PermissionEntity editarNotaPermission = savePersmission(pRepository, PermissionEnum.EDITAR_NOTA);
				PermissionEntity eliminarNotaPermission = savePersmission(pRepository, PermissionEnum.ELIMINAR_NOTA);
				PermissionEntity adminGestionarUsuariosPermission = savePersmission(pRepository, PermissionEnum.ADMIN_GESTIONAR_USUARIOS);

				RoleEntity userRole = saveRole(roleRepository, RoleEnum.USER, Set.of(
					verNotasPermission, crearNotaPermission, editarNotaPermission, eliminarNotaPermission
				));

				RoleEntity adminRole = saveRole(roleRepository, RoleEnum.ADMIN, Set.of(
					verNotasPermission, crearNotaPermission, editarNotaPermission, eliminarNotaPermission,adminGestionarUsuariosPermission
				));

				UserEntity userEntity = saveUser(userRepository, "cristian", "$2a$10$1zKDSY.iEoMHdaUFCIlWP.2qH91eZdFUKLXkTjShRb/fUcdEj2soO", Set.of(userRole));
				UserEntity adminEntity = saveUser(userRepository, "desho", "$2a$10$1zKDSY.iEoMHdaUFCIlWP.2qH91eZdFUKLXkTjShRb/fUcdEj2soO", Set.of(adminRole));

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		};
	}

	@Transactional
	private PermissionEntity savePersmission(PermissionRepository pRepository, PermissionEnum permissionEnum) {
		if (pRepository.existsByPermissionEnum(permissionEnum)) {
			throw new IllegalArgumentException("Permission with name " + permissionEnum + " already exists.");
		}
		return pRepository.save(PermissionEntity.builder()
						.permissionEnum(permissionEnum)
						.build());
	}

	@Transactional
	private RoleEntity saveRole(RoleRepository roleRepository, RoleEnum roleEnum, Set<PermissionEntity> permissionSet) {
		if (roleRepository.existsByRoleEnum(roleEnum)) {
			throw new IllegalArgumentException("Role with name " + roleEnum + " already exists.");
		}
		return roleRepository.save(RoleEntity.builder()
				.roleEnum(roleEnum)
				.permissionSet(permissionSet)
				.build());
	}

	@Transactional
	private UserEntity saveUser(UserRepository userRepository, String username, String password, Set<RoleEntity> roleSet) {
		if (userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("User with username " + username + " already exists.");
		}
		return userRepository.save(UserEntity.builder()
				.username(username)
				.password(password)
				.isEnabled(true)
				.accountNonLocked(true)
				.accountNonExpired(true)
				.credentialsNonExpired(true)
				.roleSet(roleSet)
				.build());
	}

}
