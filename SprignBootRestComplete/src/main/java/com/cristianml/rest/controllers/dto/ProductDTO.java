package com.cristianml.rest.controllers.dto;

import com.cristianml.rest.entities.Maker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

public class ProductDTO {

    public class Product {

        private Long id;
        private String name;
        // BigDecimal es específicamente para trabajar con decimales, pero para monedas porque es de alta precisión.
        private BigDecimal price;
        private Maker maker; // El nombre tiene que ser el mismo que con el que mapeamos en la entidad Maker

    }

}
