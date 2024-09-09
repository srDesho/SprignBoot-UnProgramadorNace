package com.cristianml.rest.controllers;

import com.cristianml.rest.controllers.dto.ProductDTO;
import com.cristianml.rest.entities.Product;
import com.cristianml.rest.mapper.ProductMapper;
import com.cristianml.rest.service.IProductService;
import com.cristianml.rest.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // findById
    @GetMapping("/product/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        // Verificamos si existe en la db
        Optional<Product> optionalProduct = this.productService.findById(id);
        if (optionalProduct.isPresent()) {
            // Obtenemos el producto y convertimos a DTO
            Product product = optionalProduct.get();

            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    // findAll
    @GetMapping("/product")
    public ResponseEntity<?> findAll() {
        // Creamos la lista y obtenemos los registros de la db
        List<ProductDTO> productDTOList = productService.findAll().stream()
                .map(ProductMapper.INSTANCE::productToProductDTO)
                .toList();

        return ResponseEntity.ok(productDTOList);
    }

    // Add product
    @PostMapping("/product")
    public ResponseEntity<Object> saveProduct(@RequestBody ProductDTO request) {
        // Verificamos que los datos no vengan vacíos o nulos
        if (request.getName().isBlank() || request.getPrice() == null || request.getMaker() == null) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "No se pudo crear el registro, inténtelo nuevamente.");
        }
        
        // Convertimos el DTO a Entidad
        Product product = ProductMapper.INSTANCE.productDTOToProduct(request);
        this.productService.save(product);
        return Utilities.generateResponse(HttpStatus.CREATED, "Producto creado exitosamente.");
    }
}
