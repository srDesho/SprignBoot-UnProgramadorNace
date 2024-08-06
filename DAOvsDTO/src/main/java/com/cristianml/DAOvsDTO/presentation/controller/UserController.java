package com.cristianml.DAOvsDTO.presentation.controller;

import com.cristianml.DAOvsDTO.presentation.dto.UserDTO;
import com.cristianml.DAOvsDTO.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    // FindAll
    @GetMapping("/find")
    public ResponseEntity<List<UserDTO>> findAll() {
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }

    // Find by id
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.findById(id), HttpStatus.OK);
    }

    // Create User
    @PostMapping("/create")
    // El @RequestBody indica a spring que el objeto que se va a recibir es una solicitud desde el front(por ejemplo)
    // y spring se encargará de convertir esta solicitud que viene como JSON a un objeto.
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(this.userService.createUser(userDTO), HttpStatus.CREATED);
    }

    // Update User
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return new ResponseEntity<>(this.userService.updateUser(userDTO, id), HttpStatus.OK); // Para los update se usa el .OK
    }

    // Delete User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.NO_CONTENT);

        // Para la operación de eliminación (DELETE), el código de estado HTTP más comúnmente utilizado es
        // HttpStatus.NO_CONTENT (204). Este código de estado indica que la operación fue exitosa, pero no devuelve
        // contenido en el cuerpo de la respuesta, lo cual es apropiado para las operaciones de eliminación.
    }
}
