package com.cristianml.repository;

import com.cristianml.security.entity.RoleEntity;
import com.cristianml.security.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findRoleEntityByRoleEnum(RoleEnum roleEnum);
    boolean existsByRoleEnum(RoleEnum roleEnum);
}
