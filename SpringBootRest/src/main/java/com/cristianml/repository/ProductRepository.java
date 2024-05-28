package com.cristianml.repository;

import com.cristianml.models.ProductModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductModel, Long> {

    // Vamos a crear una consulta propia
    // Existen 2 maneras de crearla:
    // 1. Con la anotación @Query con las sentencias de JPQL donde el ?1 viene a ser el 1er parámetro y el ?2 el sgdo.

    // @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2")
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2") // Es lo mismo que la anterior línea
    List<ProductModel> findProductModelByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    // 2. Con Query Methods
    List<ProductModel> findProductModelByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
