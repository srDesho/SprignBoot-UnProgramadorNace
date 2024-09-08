package com.cristianml.rest.controllers.dto;

import com.cristianml.rest.entities.Maker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {

        private Long id;
        private String name;
        // BigDecimal es específicamente para trabajar con decimales, pero para monedas porque es de alta precisión.
        private BigDecimal price;
        private Maker maker; // El nombre tiene que ser el mismo que con el que mapeamos en la entidad Maker
    
}
