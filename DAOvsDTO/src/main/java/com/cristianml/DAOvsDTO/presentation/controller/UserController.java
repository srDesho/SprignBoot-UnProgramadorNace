package com.cristianml.DAOvsDTO.presentation.controller;

import com.cristianml.DAOvsDTO.presentation.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    // FindAll
    @GetMapping("/find")
    public ResponseEntity<List<UserDTO>> findAll() {
        return null;
    }

    // Find by id
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return null;
    }

    // Create User
    @PostMapping("/create")
    // El @RequestBody indica a spring que el objeto que se va a recibir es una solicitud desde el front(por ejemplo)
    // y spring se encargará de convertir esta solicitud que viene como JSON a un objeto.
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return null;
    }

    // Update User
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return null;
    }

    // Delete User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return null;
    }
}
