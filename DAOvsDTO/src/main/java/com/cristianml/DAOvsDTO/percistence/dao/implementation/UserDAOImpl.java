package com.cristianml.DAOvsDTO.percistence.dao.implementation;

import com.cristianml.DAOvsDTO.percistence.UserEntity;
import com.cristianml.DAOvsDTO.percistence.dao.interfaces.IUserDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Vamos a hacer nuestra persistencia con DAO manualmente, ya que no vamos a utilizar la interfaz de JpaRepository

@Repository // Con esta anotación indicamos a spring que esta clase trabajará como repositorio.
public class UserDAOImpl implements IUserDAO {
    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity deleteUser(UserEntity userEntity) {
        return null;
    }
}
