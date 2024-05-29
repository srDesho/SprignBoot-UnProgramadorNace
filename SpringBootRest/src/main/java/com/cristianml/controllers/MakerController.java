package com.cristianml.controllers;

import com.cristianml.controllers.dto.MakerDTO;
import com.cristianml.models.MakerModel;
import com.cristianml.service.impl.MakerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Método para obtener lista de registros
    @GetMapping("/maker")
    public ResponseEntity<?> getMakers() {
        List<MakerDTO> makerList = makerService.findAll()
                // convertimos con .stream a un makerDTO
                .stream()
                // Debemos tener creado el .builder en nuestro modelo, en este caso lo creamos con lombok @Builder.
                // Transformamos los elementos de la lista con map para devolver una nueva lista con los elementos modificados.
                .map(maker -> MakerDTO.builder()
                        // Seteamos los atributos a cada maker del map
                        .id(maker.getId())
                        .name(maker.getName())
                        .products(maker.getProducts())
                        .build())
                .toList();
                // .collect(Collectors.toList()); Llamamos al to list con esta línea en caso de que trabajemos con java 8.

        // Retornamos nuestra lista convertida
        return ResponseEntity.ok(makerList);
    }

}
