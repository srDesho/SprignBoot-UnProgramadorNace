package com.cristian.exercise.repository;

import com.cristian.exercise.security.entity.PermissionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {
    boolean existsByName(String name);
}
