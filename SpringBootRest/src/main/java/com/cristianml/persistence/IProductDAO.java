package com.cristianml.persistence;

import com.cristianml.models.ProductModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// Esta interfaz es para definir la funcionalidad que va a tener nuestra aplicación.

public interface IProductDAO {

    List<ProductModel> findAll();

    Optional<ProductModel> findById(Long id);

    void save(ProductModel product);

    void deleteById(Long id);

    // Método para obtener una lista de prodcutos en un rango dado refereido a los precios
    List<ProductModel> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

}
