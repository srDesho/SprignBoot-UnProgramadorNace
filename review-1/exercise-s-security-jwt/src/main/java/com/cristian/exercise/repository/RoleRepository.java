package com.cristian.exercise.repository;

import com.cristian.exercise.security.entity.RoleEntity;
import com.cristian.exercise.security.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    boolean existsByRoleEnum(RoleEnum roleEnum);
    Set<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleName );
}
