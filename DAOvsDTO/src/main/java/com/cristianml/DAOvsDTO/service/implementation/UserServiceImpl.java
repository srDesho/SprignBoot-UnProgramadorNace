package com.cristianml.DAOvsDTO.service.implementation;

import com.cristianml.DAOvsDTO.presentation.dto.UserDTO;
import com.cristianml.DAOvsDTO.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Override
    public List<UserDTO> findAll() {
        return List.of();
    }

    @Override
    public UserDTO findById(Long id) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        return null;
    }

    @Override
    public String deleteUser(Long id) {
        return "";
    }
}
