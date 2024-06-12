package com.cristianml.persistence.entity.repository;

import com.cristianml.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    // Creamos método que busca un Usuario por el username
    Optional<UserEntity> findUserEntityByUsername(String username);
}
