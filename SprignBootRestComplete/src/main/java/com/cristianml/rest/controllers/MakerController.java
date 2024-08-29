package com.cristianml.rest.controllers;

import com.cristianml.rest.controllers.dto.MakerDTO;
import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.service.impl.MakerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/maker")
public class MakerController {

    private final MakerServiceImpl makerService;

    public MakerController(MakerServiceImpl makerService) {
        this.makerService = makerService;
    }

    // Creamos nuestro endpoint para encontrar un maker
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Maker> optionalMaker = this.makerService.findById(id);
        if (optionalMaker.isPresent()) {
            Maker maker = optionalMaker.get();
            // Convert to dto
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
