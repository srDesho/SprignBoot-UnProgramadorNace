package com.cristianml;

import com.cristianml.repository.PermissionRepository;
import com.cristianml.repository.RoleRepository;
import com.cristianml.repository.UserRepository;
import com.cristianml.security.entity.PermissionEntity;
import com.cristianml.security.entity.RoleEntity;
import com.cristianml.security.entity.RoleEnum;
import com.cristianml.security.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringBootRestApplication {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
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
				saveUser(userRepository, "cristian", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", Set.of(roleAdmin));
				saveUser(userRepository, "daniel", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", Set.of(roleUser));
				saveUser(userRepository, "andrea", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", Set.of(roleInvited));
				saveUser(userRepository, "desho", "$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6", Set.of(roleDeveloper));


			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		};
	}

	@Transactional
	public PermissionEntity savePermission(PermissionRepository pRepository, String name) {
		if (pRepository.existsByName(name)) {
			throw new IllegalArgumentException("Permission with name " + name + " already exists.");
		}
		return permissionRepository.save(PermissionEntity.builder().name(name).build());
	}

	@Transactional
	private RoleEntity saveRole(RoleRepository roleRepository, RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
		if (roleRepository.existsByRoleEnum(roleEnum)) {
			throw new IllegalArgumentException("Role with name " + roleEnum.name() + " already exists.");
		}
		return roleRepository.save(RoleEntity.builder().roleEnum(roleEnum).permissions(permissionList).build());
	}

	@Transactional
	private UserEntity saveUser(UserRepository userRepository, String username, String password, Set<RoleEntity> roles) {
		if (userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("User with name " + username + " already exists.");
		}

		return userRepository.save(UserEntity.builder()
				.username(username)
				.password(password)
				.isEnabled(true)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.roles(roles)
				.build());
	}
}
