package com.cristianml.DAOvsDTO.service.implementation;

import com.cristianml.DAOvsDTO.percistence.UserEntity;
import com.cristianml.DAOvsDTO.percistence.dao.interfaces.IUserDAO;
import com.cristianml.DAOvsDTO.presentation.dto.UserDTO;
import com.cristianml.DAOvsDTO.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    // Inyectamos nuestro IUserDAO, ya que es el objeto que se encarga de la persistencia.
    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<UserDTO> findAll() {
        // Creamos nuestro mapper para convertir una entidad a dto o viceversa.
        ModelMapper modelMapper = new ModelMapper();

        return this.userDAO.findAll().stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class)) // Convertimos cada entidad a un DTO
                .collect(Collectors.toList()); // Convertimos a una lista.
    }

    @Override
    public UserDTO findById(Long id) {

        Optional<UserEntity> userEntity = this.userDAO.findById(id);

        if (userEntity.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(userEntity.get(), UserDTO.class);
        } else {
            // Devolvemos un UserDTO vacío
            return new UserDTO();
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
            this.userDAO.saveUser(userEntity);
            return userDTO;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Error al guardar el usuario");
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {

        Optional<UserEntity> userEntity = this.userDAO.findById(id);

        if (userEntity.isPresent()) {
            UserEntity currentUserEntity = userEntity.get();
            // Seteamos los atributos
            currentUserEntity.setName(userDTO.getName());
            currentUserEntity.setLastName(userDTO.getLastName());
            currentUserEntity.setEmail(userDTO.getEmail());
            currentUserEntity.setAge(userDTO.getAge());

            // Actualizamos en la base de datos
            this.userDAO.updateUser(currentUserEntity);

            ModelMapper modelMapper = new ModelMapper();

            // Retornamos el dto.
            return modelMapper.map(currentUserEntity, UserDTO.class);
        } else {
            // Si no encontramos el user en la base de datos, arrojamos una excepción
            throw new IllegalArgumentException("El usuario no existe.");
        }
    }

    @Override
    public String deleteUser(Long id) {

        Optional<UserEntity> userEntity = this.userDAO.findById(id);
        if (userEntity.isPresent()) {
            UserEntity currentUserEntity = userEntity.get();
            this.userDAO.deleteUser(currentUserEntity);
            return "Usuario con Id " + id + " ha sido eliminado.";
        } else {
            return "El usuario con " + id + " no existe.";
        }
    }
}
