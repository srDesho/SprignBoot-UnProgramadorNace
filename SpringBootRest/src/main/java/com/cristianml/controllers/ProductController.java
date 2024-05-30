package com.cristianml.controllers;

import com.cristianml.controllers.dto.MakerDTO;
import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.ProductModel;
import com.cristianml.service.IProductService;
import com.cristianml.service.impl.ProductServiceImpl;
import com.cristianml.utilities.Utilities;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Guardar un Producto
    @PostMapping("/product")
    // Es buena práctica retornar o recibir solicitudes con un ModeloDTO y no con una entidad, debemos crear nuestro modelo
    // con los mismos atributos que la entidad en un paquete dto.
    public ResponseEntity<Object> saveProduct(@RequestBody ProductDTO request) {
        // Verificamos que los campos no vengan vacíos o nulos
        if (request.getName().isBlank() || request.getPrice() == null || request.getMaker() == null) {
            return  Utilities.generateResponse(HttpStatus.BAD_REQUEST, "No se pudo crear el registro, inténtelo nuevamente");
        }

        // Creamos la entidad
        ProductModel productModel = new ProductModel();
        productModel.setName(request.getName());
        productModel.setPrice(request.getPrice());
        productModel.setMaker(request.getMaker());
        this.productService.save(productModel);

        return Utilities.generateResponse(HttpStatus.CREATED, "Producto creado exitosamente.");
    }

    // Editar un Producto

    // Eliminar un Producto

}
