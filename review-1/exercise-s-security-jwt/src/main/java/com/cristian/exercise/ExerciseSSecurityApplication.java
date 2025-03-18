package com.cristian.exercise;

import com.cristian.exercise.repository.PermissionRepository;
import com.cristian.exercise.repository.RoleRepository;
import com.cristian.exercise.repository.UserRepository;
import com.cristian.exercise.security.entity.PermissionEntity;
import com.cristian.exercise.security.entity.RoleEntity;
import com.cristian.exercise.security.entity.RoleEnum;
import com.cristian.exercise.security.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
public class ExerciseSSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExerciseSSecurityApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository,
								  PermissionRepository permissionRepository) {
		return args -> {
			try {
				// CREATE PERMISSIONS
				PermissionEntity createPermission = savePermission(permissionRepository, "CREATE");
				PermissionEntity readPermission = savePermission(permissionRepository, "READ");
				PermissionEntity updatePermission = savePermission(permissionRepository, "UPDATE");
				PermissionEntity deletePermission = savePermission(permissionRepository, "DELETE");
				PermissionEntity refactorPermission = savePermission(permissionRepository, "REFACTOR");

				// CREATE ROLES
				RoleEntity roleAdmin = saveRole(roleRepository, RoleEnum.ADMIN, Set.of(createPermission, readPermission, updatePermission, deletePermission));
				RoleEntity roleUser = saveRole(roleRepository, RoleEnum.USER, Set.of(createPermission, readPermission));
				RoleEntity roleInvited = saveRole(roleRepository, RoleEnum.INVITED, Set.of(readPermission));
				RoleEntity roleDeveloper = saveRole(roleRepository, RoleEnum.DEVELOPER, Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

				// CREATE USERS
				saveUser(userRepository, "cristian", "$2a$10$St4/Hww55KDXqLbcLIbO6OXZCUUFPGImPXwI1RrR8PTgfTONItsje", Set.of(roleAdmin));
				saveUser(userRepository, "daniel", "$2a$10$St4/Hww55KDXqLbcLIbO6OXZCUUFPGImPXwI1RrR8PTgfTONItsje", Set.of(roleUser));
				saveUser(userRepository, "andrea", "$2a$10$St4/Hww55KDXqLbcLIbO6OXZCUUFPGImPXwI1RrR8PTgfTONItsje", Set.of(roleInvited));
				saveUser(userRepository, "desho", "$2a$10$St4/Hww55KDXqLbcLIbO6OXZCUUFPGImPXwI1RrR8PTgfTONItsje", Set.of(roleDeveloper));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		};
	}

	@Transactional
	private PermissionEntity savePermission(PermissionRepository repository, String name) {
		if (repository.existsByName(name)) {
			throw new IllegalArgumentException("Permission with name " + name + " already exists.");
		}
		return repository.save(PermissionEntity.builder().name(name).build());
	}

	@Transactional
	private RoleEntity saveRole(RoleRepository repository, RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
		if (repository.existsByRoleEnum(roleEnum)) {
			throw new IllegalArgumentException("Role with name " + roleEnum + " already exists.");
		}
		return repository.save(RoleEntity.builder().roleEnum(roleEnum).permissionEntitySet(permissionList).build());
	}

	@Transactional
	private UserEntity saveUser(UserRepository repository, String username, String password, Set<RoleEntity> roleList) {
		if (repository.existsByUsername(username)) {
			throw new IllegalArgumentException("User with username " + username + " already exists.");
		}
		return repository.save(UserEntity.builder()
				.username(username)
				.password(password)
				.isEnabled(true)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.roleEntitySet(roleList)
				.build());
	}

}
