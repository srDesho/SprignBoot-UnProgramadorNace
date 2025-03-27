package com.cristianml.notes.repository;

import com.cristianml.notes.security.entity.PermissionEntity;
import com.cristianml.notes.security.entity.PermissionEnum;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

    boolean existsByPermissionEnum(PermissionEnum pEnum);

}
