package com.cristianml.repository;

import com.cristianml.security.entity.RoleEntity;
import com.cristianml.security.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findRoleEntityByRoleEnum(RoleEnum roleEnum);
    boolean existsByRoleEnum(RoleEnum roleEnum);

    Set<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleRequest);
}
