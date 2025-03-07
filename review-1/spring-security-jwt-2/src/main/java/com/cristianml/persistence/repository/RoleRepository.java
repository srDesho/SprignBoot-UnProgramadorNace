package com.cristianml.persistence.repository;

import com.cristianml.persistence.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    // Esta línea me trae sólamente los roles y permisos que existan en la DB dada la lista que enviamos.
    // O sea si la lista contiene 2 elementos y estos 2 elementos existen en la tabla de roles pues nos trae solo los 2.
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

}
