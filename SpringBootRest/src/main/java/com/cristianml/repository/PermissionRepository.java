package com.cristianml.repository;

import com.cristianml.security.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    boolean existsByName(String name);
}
