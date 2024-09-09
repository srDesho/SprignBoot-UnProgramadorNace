package com.cristianml.rest.controllers;

import com.cristianml.rest.controllers.dto.ProductDTO;
import com.cristianml.rest.entities.Product;
import com.cristianml.rest.mapper.ProductMapper;
import com.cristianml.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
