package com.cristianml.rest.repository;

import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository

// Trabajamos con los query methods, en este caso el findByPriceBetween nos ayuda a buscar con un rago, debemos definir
// 2 argumentos los cuales serán un mínimo y un máximo.
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Podemos hacer lo mismo con la anotación @Query que es la que nos ayuda a hacer consultas nativas de JPQL.
    // Con los signos indicamos los valores definidos entre los paréntesis.
    @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2")
    List<Product> findProductsByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    // Método para verificar si un maker tiene relación con alguno de los productos
    boolean existsByMaker(Maker maker);

}
