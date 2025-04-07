package com.cristianml.controllers;

import com.cristianml.controllers.dto.MakerDTO;
import com.cristianml.models.MakerModel;
import com.cristianml.service.IMakerService;
import com.cristianml.service.IProductService;
import com.cristianml.service.impl.MakerServiceImpl;
import com.cristianml.utilities.Utilities;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MakerController {

    @Autowired
    private IMakerService makerService;

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

            // Convertimos la entidad a un modelo en este caso manualmente, ya que ya existen librerias para convertir
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

    // Método para Guardar Maker
    @PostMapping("/maker")
    // Siempre que retornamos o recibamos un objeto debe se un DTO
    public ResponseEntity<Object> saveMaker(@RequestBody MakerDTO request) {
       // Verificamos que el nombre no venga vacío
        if (request.getName().isBlank()) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío,");
        }

        // Guardamos en la base de datos convirtiendo a un MakerModel, porque nuestro método .save recibe una entidad.
        this.makerService.save(MakerModel.builder().name(request.getName()).build());
        return Utilities.generateResponse(HttpStatus.CREATED, "Se ha creado el producto exitosamente.");
    }

    // Método para editar un Maker
    @PutMapping("/maker/{id}")
    public ResponseEntity<Object> updateMaker(@PathVariable("id") Long id, @RequestBody MakerDTO request) {
        // Creamos un Oprional para buscar el Maker por id
        Optional<MakerModel> optional = this.makerService.findById(id);

        // Verificamos si existe un maker con el id dado
        if (optional.isPresent()) {
            if (request.getName().isBlank()) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío,");
            }
            // Obtenemos la entidad que encontro en optional
            MakerModel makerModel = optional.get();
            makerModel.setName(request.getName());
            this.makerService.save(makerModel);

            return Utilities.generateResponse(HttpStatus.OK, "Se editó el registro exitosamente.");
        }
            // Si no existe el registro
            return Utilities.generateResponse(HttpStatus.NOT_FOUND, "No existe registro en la db con ese id.");
    }

    // Método para eliminar por id
    // Inyectamos el producto service para saber si existen registros con un producto que deseamos eliminar
    @Autowired
    private IProductService productService;

    @DeleteMapping("/maker/{id}")
    public ResponseEntity<Object> deleteMaker (@PathVariable("id") Long id) {
        // Creamos optional para verificar que exista en la db
        Optional<MakerModel> optional = this.makerService.findById(id);
        if (optional.isEmpty()) {
            return Utilities.generateResponse(HttpStatus.NOT_FOUND, "No existe registro con ese ID en la base de datos.");
        }

        // Verificamos si tiene el maker tiene relación registros relacionados con productos
        if (this.productService.existsRegisterByMaker(id)) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST
                    , "No se puede eliminar el registro debido a que la categoría tiene relación con la tabla productos");
        }

        try {
            this.makerService.deleteById(id);
            return Utilities.generateResponse(HttpStatus.OK, "Registro eliminado exitosamente.");

        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "Falló la ejecución, por favor inténtelo más tarde.");
        }
    }
}
