package com.cristianml.DAOvsDTO.percistence.dao.interfaces;

import com.cristianml.DAOvsDTO.percistence.UserEntity;

import java.util.List;
import java.util.Optional;

// Vamos a hacer nuestra persistencia con DAO manualmente, ya que no vamos a utilizar la interfaz de JpaRepository

public interface IUserDAO {

    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);

    // En estos siguientes métodos no es necesario enviar los id cuando trabajamos con DAO.
    void saveUser(UserEntity userEntity);
    void updateUser(UserEntity userEntity);
    void deleteUser(UserEntity userEntity);

}
