package com.cristianml.service;

import com.cristianml.models.ProductModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// Estamos creando una estructura desacoplada, o sea la capa de persistencia sólo se encarga de interactuar con la BD
// y esta capa de Servicio se encarga de la lógica de negocio, ose la lógica de la aplicación.
public interface IProductService {

    List<ProductModel> findAll();

    Optional<ProductModel> findById(Long id);

    void save(ProductModel product);

    void deleteById(Long id);

    // Método para obtener una lista de prodcutos en un rango dado refereido a los precios
    List<ProductModel> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

}
