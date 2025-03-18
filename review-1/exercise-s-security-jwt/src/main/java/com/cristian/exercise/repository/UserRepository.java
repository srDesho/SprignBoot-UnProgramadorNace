package com.cristian.exercise.repository;

import com.cristian.exercise.security.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    boolean existsByUsername(String username);
}
