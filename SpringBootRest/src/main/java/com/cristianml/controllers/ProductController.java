package com.cristianml.controllers;

import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.ProductModel;
import com.cristianml.service.IProductService;
import com.cristianml.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            return  Utilities.generateResponse(HttpStatus.BAD_REQUEST, "No se pudo crear el registro, inténtelo nuevamente.");
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

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO request) {
        // Verificamos si el registro con el id de solicitado existe en la base de datos
        Optional<ProductModel> optional = this.productService.findById(id);
        if (optional.isEmpty()) {
            return Utilities.generateResponse(HttpStatus.NOT_FOUND, "El producto con ese id no existe en la db.");
        }

        // Verificamos si los datos están vacíos o nulos
        if (request.getName().isBlank() || request.getPrice() == null || request.getMaker() == null) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "No se pudo editar el registro, inténtelo nuevamente.");
        }

        // Obtenemos el producto desde el optional.
        ProductModel productModel = optional.get();
        productModel.setName(request.getName());
        productModel.setPrice(request.getPrice());
        productModel.setMaker(request.getMaker());
        this.productService.save(productModel);

        return Utilities.generateResponse(HttpStatus.OK, "Se editó el registro exitosamente.");
    }

    // Eliminar un Producto

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        // Verificamos si existe en la db
        Optional<ProductModel> optional = this.productService.findById(id);
        if (optional.isEmpty()) {
            return Utilities.generateResponse(HttpStatus.NOT_FOUND, "No existe registro con ese ID en la base de datos.");
        }

        // Envolvemos en un try catch para eliminar
        try {
            this.productService.deleteById(id);
            return Utilities.generateResponse(HttpStatus.OK, "Registro eliminado exitosamente.");
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "Falló la ejecución, inténtelo más tarde.");
        }
    }

}
