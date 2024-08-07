package com.cristianml.DAOvsDTO.percistence.repository;

import com.cristianml.DAOvsDTO.percistence.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Vamos a hacer nuestra persistencia con DAO manualmente, ya que no vamos a utilizar la interfaz de JpaRepository.
// Esta es la interfaz que no vamos a utilizar, ya que todo lo hace spring por debajo.

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
}
