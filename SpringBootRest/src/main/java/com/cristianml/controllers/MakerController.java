package com.cristianml.controllers;

import com.cristianml.controllers.dto.MakerDTO;
import com.cristianml.models.MakerModel;
import com.cristianml.service.impl.MakerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(("/api/v1"))
public class MakerController {

    @Autowired
    private MakerServiceImpl makerService;

    // Método obtener por id
    @GetMapping("/maker/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id ) {
        // Como buena práctica debemos crear un Modelo Maker DTO (data transfer object) porque no se debe retornar una entidad porque
        // están etiquetadas como @Entity en SpringBoot, la debemos crear en un paquete llamado dto, dentro del paquete controllers.

        // Creamos el optional
        Optional<MakerModel> makerOptional = makerService.findById(id);
        // Preguntamos si está presente el optional, o sea si existe
        if (makerOptional.isPresent()) {
            MakerModel maker = makerOptional.get();

            // Convertimos la entidad a un módelo en este caso manualmente, ya que ya existen librerias para convertir
            // librerías como MapStruct
            MakerDTO makerDto = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .products(maker.getProducts())
                    .build();

            return ResponseEntity.ok(makerDto);
        }
        // Si no se encontró
        return ResponseEntity.notFound().build();

    }

}
