package com.cristianml.rest.controllers;

import com.cristianml.rest.controllers.dto.MakerDTO;
import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.mapper.MakerMapper;
import com.cristianml.rest.service.IMakerService;
import com.cristianml.rest.service.impl.MakerServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/maker")
public class MakerController {

    private final IMakerService makerService;

    public MakerController(IMakerService makerService) {
        this.makerService = makerService;
    }

    // Creamos nuestro endpoint para encontrar un maker
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Maker> optionalMaker = this.makerService.findById(id);
        if (optionalMaker.isPresent()) {
            Maker maker = optionalMaker.get();
            // Convert to dto

            // Forzamos la carga de la lista de productos con LAZY Loading
            // Hibernate.initialize(maker.getProductList());
            // Haciendo uso del mapstruck para mapear objetos con nombres de atributos iguales
            MakerDTO makerDTO = MakerMapper.INSTANCE.makerToMakerDTO(maker);
            System.out.println("===========" + maker.getName());

            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // EndPoint para obtener lista de makers
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MakerDTO> makerDTOList = makerService.findAll()
                // Convertimos a DTO con .stream
                .stream()
                // Debemos tener creado el .builder en nuestro modelo, en este caso lo creamos con lombok @Builder.
                // Transformamos los elementos de la lista con map para devolver una nueva lista con los elementos modificados.
                .map(MakerMapper.INSTANCE::makerToMakerDTO)
                .toList();

        return ResponseEntity.ok(makerDTOList);
    }
}
