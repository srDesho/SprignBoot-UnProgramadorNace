package com.cristianml.rest.persistence;

import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductDAO {

    void save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);

    // Pra trabajar con los query methods
    // Es para obtener una lista por el precio en un rango dado
    List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

}
