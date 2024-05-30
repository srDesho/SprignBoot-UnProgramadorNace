package com.cristianml.controllers;

import com.cristianml.controllers.dto.MakerDTO;
import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.ProductModel;
import com.cristianml.service.IProductService;
import com.cristianml.service.impl.ProductServiceImpl;
import com.cristianml.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private IProductService productService;

    // Método para listar productos
    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productDTOList = productService.findAll().stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build())
                .toList();
        // .collect(Collectors.toList()); // Esto por si trabajamos en java 8

        return ResponseEntity.ok(productDTOList);
    }

    // Método para obtener un producto por id
    @GetMapping("/product/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") Long id) {
        // Creamos un optional para obtener el producto
        Optional<ProductModel> optional = this.productService.findById(id);
        // Verificamos si existe el producto
        if (optional.isPresent()) {
            ProductModel product = optional.get();
            // Convertimos la entidad a un modelo en este caso manualmente, ya que ya existen librerias para convertir
            // librerías como MapStruct
            ProductDTO productDTO = ProductDTO
                    .builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

}
