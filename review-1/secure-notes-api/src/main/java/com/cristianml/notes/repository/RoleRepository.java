package com.cristianml.notes.repository;

import com.cristianml.notes.security.entity.RoleEntity;
import com.cristianml.notes.security.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    boolean existsByRoleEnum(RoleEnum roleEnum);

}
